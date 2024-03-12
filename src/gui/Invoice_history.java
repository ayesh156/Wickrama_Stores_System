/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import static gui.Customer_registration.logger;
import java.awt.Color;
import java.io.FileReader;
import java.nio.file.FileSystems;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import javax.swing.table.DefaultTableModel;
import models.MyMethods;
import models.Mysql;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Dell
 */
public class Invoice_history extends javax.swing.JPanel {

    /**
     * Creates new form Invoice_history
     */
    public Invoice_history() {
        initComponents();

        jTextField4.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        loadInvoice();
        calprofit();

    }

    private void calprofit() {
        String invoiceID = jTextField1.getText().trim();
        String customerMobile = jFormattedTextField1.getText().trim();
        String date = jTextField4.getText().trim();

        try {

            ResultSet resultset = Mysql.execute("SELECT * FROM invoice INNER JOIN customer_registration ON customer_registration.id = invoice.customer_registration_id "
                    + "WHERE invoice.id LIKE '%" + invoiceID + "%' AND mobile LIKE '%" + customerMobile + "%' "
                    + "AND date LIKE '%" + date + "%'");

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            Set<String> processedCustomers = new HashSet<>();
            double totalEarnings = 0;
            double totalProfit = 0;
            double totalLoanAmount = 0;

            while (resultset.next()) {
                Vector<String> vector = new Vector<>();

                vector.add(resultset.getString("id"));
                vector.add(resultset.getString("mobile"));

                String inputDTime = resultset.getString("date");

                try {
                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date idate = inputFormat.parse(inputDTime);

                    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                    String formattedDTime = outputFormat.format(idate);

                    vector.add(formattedDTime);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                vector.add(resultset.getString("paid_amount"));
                vector.add(resultset.getString("discount"));

                //serch items to calculate total of each nvoice
                double loanAmount = 0;
                double buyingTotal = 0;
                double invoiceTotal = 0;
                double discountTotal = 0;

                ResultSet resultset2 = Mysql.execute("SELECT * FROM invoice_item WHERE invoice_id='" + resultset.getString("id") + "'");

                String customerId = null;

                while (resultset2.next()) {

                    buyingTotal += resultset2.getDouble("qty") * resultset2.getDouble("buying_price");
                    invoiceTotal += resultset2.getDouble("qty") * resultset2.getDouble("our_price");

                    customerId = resultset.getString("customer_registration_id");

                }

                discountTotal += resultset.getDouble("discount");

                if (!processedCustomers.contains(customerId)) {

                    loanAmount += resultset.getDouble("customer_registration.balance_amount");

                    processedCustomers.add(customerId);

                    totalLoanAmount += loanAmount;
                }

                totalProfit += ((invoiceTotal - buyingTotal) - discountTotal) + loanAmount;
                totalEarnings += (invoiceTotal - discountTotal) + loanAmount;

                vector.add(String.valueOf(invoiceTotal));

                model.addRow(vector);
            }

            jLabel9.setText(String.valueOf(totalProfit));
            jLabel2.setText(String.valueOf(totalEarnings));
            jLabel11.setText(String.valueOf(totalLoanAmount));
            jLabel12.setText("Invoices (" + model.getRowCount() + ")");

        } catch (Exception e) {
            logger.log(Level.WARNING, "loadInvoice_history", e);
            e.printStackTrace();
        }
    }

    private void loadInvoice() {

        try {
            double total = 0;
            String invoid = jTextField1.getText();
            String mobile = jFormattedTextField1.getText();
//            String name = jTextField3.getText();
            String date = jTextField4.getText();

            ResultSet results = Mysql.execute("SELECT * FROM invoice INNER JOIN customer_registration ON invoice.customer_registration_id=customer_registration.id"
                    + " WHERE customer_registration.mobile LIKE '%" + mobile + "%' AND customer_registration.name LIKE '%%' "
                    + "  AND invoice.date LIKE '%" + date + "%'  AND invoice.id LIKE '%" + invoid + "%'    ");
            DefaultTableModel mode = (DefaultTableModel) jTable1.getModel();
            mode.setNumRows(0);
            int count = 0;

            while (results.next()) {

                ResultSet results1 = Mysql.execute("SELECT * FROM invoice_item WHERE invoice_id='" + results.getString("invoice.id") + "' ");

                while (results1.next()) {

                    total += Double.parseDouble(results1.getString("qty")) * Double.parseDouble(results1.getString("our_price"));
                }

                Vector<String> v = new Vector<>();

                v.add(results.getString("invoice.id"));
                v.add(results.getString("customer_registration.mobile"));
                v.add(results.getString("date"));
                v.add(results.getString("paid_amount"));
                v.add(results.getString("discount"));
                v.add(String.valueOf(total));
                total = 0;
                mode.addRow(v);
                count++;

            }

            jTable1.setModel(mode);

        } catch (Exception e) {
            logger.log(Level.WARNING, "load invoice table", e);
            e.printStackTrace();
        }

    }

    private void loadInvoice_item() {

        try {
            String qury = "WHERE";
            int row = jTable1.getSelectedRow();

            if (row != -1) {
                qury += " `invoice_id`='" + jTable1.getValueAt(row, 0) + "'";
            } else {
                qury += " `invoice_id`=''";

            }

            ResultSet results = Mysql.execute("SELECT * FROM `invoice_item` INNER JOIN `products` ON `invoice_item`.`products_product_id`=`products`.`product_id` " + qury + "");
            DefaultTableModel mode = (DefaultTableModel) jTable2.getModel();
            mode.setNumRows(0);
            int count = 0;
            while (results.next()) {
                Vector<String> v = new Vector<>();
                v.add(results.getString("products.product_id"));
                v.add(results.getString("invoice_item.brand"));
                v.add(results.getString("invoice_item.product_name"));
                v.add(results.getString("invoice_item.qty"));
                v.add(results.getString("invoice_item.buying_price"));
                v.add(results.getString("invoice_item.our_price"));
                v.add(results.getString("invoice_item.selling_price"));

                double total;
                double qty = Double.parseDouble(results.getString("invoice_item.qty"));
                double rpice = Double.parseDouble(results.getString("invoice_item.our_price"));

                total = qty * rpice;

                v.add(String.valueOf(total));

                mode.addRow(v);
                count++;
            }
            jTable2.setModel(mode);
            jLabel8.setText("Invoice Item (" + count + ")");

        } catch (Exception e) {
            logger.log(Level.WARNING, "load invoice item table", e);
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(1280, 629));
        setLayout(new java.awt.BorderLayout());

        jSplitPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 10, 10));
        jSplitPane1.setDividerLocation(400);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanel1.setMinimumSize(new java.awt.Dimension(393, 50));
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(1280, 300));
        jPanel1.setLayout(new java.awt.BorderLayout(10, 0));

        jPanel3.setMinimumSize(new java.awt.Dimension(289, 0));
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(350, 300));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel16.setPreferredSize(new java.awt.Dimension(100, 350));
        jPanel16.setLayout(new java.awt.BorderLayout());

        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 10));
        jPanel4.setOpaque(false);
        jPanel4.setPreferredSize(new java.awt.Dimension(400, 90));
        jPanel4.setLayout(new java.awt.GridLayout(3, 0));

        jPanel11.setOpaque(false);
        jPanel11.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 255));
        jLabel1.setText("Total earnings");
        jPanel11.add(jLabel1, java.awt.BorderLayout.LINE_START);

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("0.00");
        jPanel11.add(jLabel2, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel11);

        jPanel12.setOpaque(false);
        jPanel12.setLayout(new java.awt.BorderLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 255));
        jLabel3.setText("Total profit");
        jPanel12.add(jLabel3, java.awt.BorderLayout.LINE_START);

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 153, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("0.00");
        jPanel12.add(jLabel9, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel12);

        jPanel14.setOpaque(false);
        jPanel14.setPreferredSize(new java.awt.Dimension(350, 100));
        jPanel14.setLayout(new java.awt.BorderLayout());

        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Pending payments");
        jPanel14.add(jLabel10, java.awt.BorderLayout.LINE_START);

        jLabel11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("0.00");
        jPanel14.add(jLabel11, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel14);

        jPanel16.add(jPanel4, java.awt.BorderLayout.SOUTH);

        jPanel5.setBackground(new java.awt.Color(153, 204, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 10, 10));
        jPanel5.setLayout(new java.awt.GridLayout(5, 1));

        jPanel8.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 5, 0));
        jPanel8.setOpaque(false);
        jPanel8.setLayout(new java.awt.BorderLayout());

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Search Invoices");
        jLabel6.setPreferredSize(new java.awt.Dimension(100, 0));
        jPanel8.add(jLabel6, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel8);

        jPanel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 5, 0));
        jPanel6.setOpaque(false);
        jPanel6.setLayout(new java.awt.BorderLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Invoice ID");
        jLabel4.setPreferredSize(new java.awt.Dimension(100, 0));
        jPanel6.add(jLabel4, java.awt.BorderLayout.LINE_START);

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        jPanel6.add(jTextField1, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel6);

        jPanel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 5, 0));
        jPanel7.setOpaque(false);
        jPanel7.setLayout(new java.awt.BorderLayout());

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Mobile");
        jLabel5.setPreferredSize(new java.awt.Dimension(100, 0));
        jPanel7.add(jLabel5, java.awt.BorderLayout.LINE_START);

        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jFormattedTextField1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jFormattedTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField1ActionPerformed(evt);
            }
        });
        jFormattedTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jFormattedTextField1KeyReleased(evt);
            }
        });
        jPanel7.add(jFormattedTextField1, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel7);

        jPanel9.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 5, 0));
        jPanel9.setOpaque(false);
        jPanel9.setLayout(new java.awt.BorderLayout());

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Date");
        jLabel7.setPreferredSize(new java.awt.Dimension(100, 0));
        jPanel9.add(jLabel7, java.awt.BorderLayout.LINE_START);

        jTextField4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField4KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });
        jPanel9.add(jTextField4, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel9);

        jPanel10.setOpaque(false);
        jPanel10.setLayout(new java.awt.BorderLayout(5, 0));

        jButton1.setBackground(new java.awt.Color(0, 153, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Search Invoices");
        jButton1.setPreferredSize(new java.awt.Dimension(190, 37));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel10.add(jButton1, java.awt.BorderLayout.WEST);

        jButton3.setBackground(new java.awt.Color(51, 51, 51));
        jButton3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Print");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel10.add(jButton3, java.awt.BorderLayout.CENTER);

        jButton2.setBackground(new java.awt.Color(0, 153, 255));
        jButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/reload1.png"))); // NOI18N
        jButton2.setPreferredSize(new java.awt.Dimension(45, 31));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel10.add(jButton2, java.awt.BorderLayout.LINE_END);

        jPanel5.add(jPanel10);

        jPanel16.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel16, java.awt.BorderLayout.NORTH);

        jPanel1.add(jPanel3, java.awt.BorderLayout.LINE_END);

        jPanel15.setOpaque(false);
        jPanel15.setLayout(new java.awt.BorderLayout());

        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Invoice ID", "Cus. mobile", "Date & time", "Paid amount", "Discount", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(40);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(5);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(5);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(10);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(10);
        }

        jPanel15.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jLabel12.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Invoices (0)");
        jPanel15.add(jLabel12, java.awt.BorderLayout.PAGE_START);

        jPanel1.add(jPanel15, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(jPanel1);

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.BorderLayout());

        jTable2.setFont(new java.awt.Font("Iskoola Pota", 0, 18)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product ID", "Brand", "Name", "Quantity", "Buying Price", "Our price", "Selling price", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setRowHeight(40);
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setPreferredWidth(5);
            jTable2.getColumnModel().getColumn(5).setPreferredWidth(5);
            jTable2.getColumnModel().getColumn(7).setPreferredWidth(10);
        }

        jPanel2.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel13.setOpaque(false);
        jPanel13.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel13.setLayout(new java.awt.BorderLayout());

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Invoice Item (0)");
        jLabel8.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel8.setPreferredSize(new java.awt.Dimension(129, 30));
        jPanel13.add(jLabel8, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel13, java.awt.BorderLayout.NORTH);

        jSplitPane1.setRightComponent(jPanel2);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        loadInvoice_item();
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jTable1.clearSelection();
        jTextField1.setText("");
        jFormattedTextField1.setText("");
        jTextField4.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        loadInvoice();
        loadInvoice_item();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        loadInvoice();
        calprofit();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        loadInvoice();
        calprofit();
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jFormattedTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField1ActionPerformed

    }//GEN-LAST:event_jFormattedTextField1ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyPressed


    }//GEN-LAST:event_jTextField4KeyPressed

    private void jFormattedTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField1KeyReleased
        loadInvoice();
        calprofit();
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField1KeyReleased

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
        // TODO add your handling code here:
        loadInvoice();
        calprofit();
    }//GEN-LAST:event_jTextField4KeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        try {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            double netTotal = 0;
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                netTotal += Double.parseDouble(String.valueOf(jTable1.getValueAt(i, 5)));
            }

            //            WE CAN USE ONLY NEDBEANS IDE
//            String userDirectory = FileSystems.getDefault().getPath("").toAbsolutePath().toString();
//
//            String url = userDirectory + "\\src\\reports\\wikrama_invoice_report.jasper";
//
//            String tempUrl = "src/reports/wikrama_invoice_report.jasper"; // for testing
//            String suburl = "src/reports/wikrama_invoice_items.jasper"; // for testing
//            
//             WE CAN USE AFTER BUILD
            String userDirectory = FileSystems.getDefault()
                    .getPath("")
                    .toAbsolutePath()
                    .toString();

            String newpath = userDirectory.substring(0, userDirectory.lastIndexOf("\\"));
//            System.out.println(newpath);

            String tempUrl = newpath + "\\src\\reports\\wikrama_invoice_report.jasper";
            String suburl = newpath + "\\src\\reports\\wikrama_invoice_items.jasper";

//accessng JSON config file - start
            String JSONurl = userDirectory + "\\lib\\databs.json";
            Object obj = new JSONParser().parse(new FileReader(JSONurl));
            JSONObject j = (JSONObject) obj;

            String db = String.valueOf(j.get("databaseName"));
            String username = String.valueOf(j.get("username"));
            String password = String.valueOf(j.get("password"));

//accessng JSON config file - end
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db, username, password);

            java.util.HashMap<String, Object> parameters = new HashMap<>();

            parameters.put("Parameter5", String.valueOf(netTotal));
            parameters.put("Parameter7", date);
            parameters.put("Parameter4", jLabel11.getText()); //pending payments
            parameters.put("Parameter8", jLabel9.getText());    //total profit
            parameters.put("Parameter3", jLabel2.getText());    //total earnings

            JRTableModelDataSource datasource = new JRTableModelDataSource(jTable1.getModel());

            JasperFillManager.fillReport(suburl, parameters, connection);
            JasperPrint report = JasperFillManager.fillReport(tempUrl, parameters, datasource);
            //JasperPrintManager.printReport(report, false); //prirent report dirrectly
            JasperViewer.viewReport(report, false); //for testing

        } catch (Exception e) {
            logger.log(Level.WARNING, "print_invoice_history_btn", e);
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
