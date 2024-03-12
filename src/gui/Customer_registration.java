/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import java.awt.Color;
import java.sql.ResultSet;
import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.MyMethods;
import models.Mysql;

/**
 *
 * @author Dell
 */
public class Customer_registration extends javax.swing.JPanel {

    public static Logger logger = Logger.getLogger("pos1");

    private Home home;

    private String count;

    private JDialog dailog;

    public void setDiaload(JDialog dailog) {
        this.dailog = dailog;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    /**
     * Creates new form Customer_registration
     */
    public Customer_registration() {
        initComponents();
        jButton2.setEnabled(false);
        loadCutomertable();
          

    }

    private void reset() {
        jButton2.setEnabled(false);
        jButton1.setEnabled(true);
        jTextField1.setText("");
        jTextField2.setText("");
        jTable1.clearSelection();
        jComboBox1.setSelectedIndex(0);
        loadCutomertable();

    }

    private void loadCutomertable() {
        int invoiceCount = 0;

        String mobile = jTextField1.getText();
        String name = jTextField2.getText();
        String order = String.valueOf(jComboBox1.getSelectedIndex());
        String qury = " ORDER BY name";
        if (order.equals("0")) {
            qury += " ASC";
        } else {
            qury += " DESC";

        }
        System.out.println("SELECT * FROM `customer_registration` WHERE `mobile` LIKE '%" + mobile + "%' AND  " + qury + "");
        try {
            ResultSet resultset = Mysql.execute("SELECT * FROM `customer_registration` WHERE `mobile` LIKE '%" + mobile + "%'  AND `name` LIKE '%" + name + "%'   " + qury + "");

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

            model.setRowCount(0);

            while (resultset.next()) {

                Vector<String> vector = new Vector<>();

                vector.add(resultset.getString("id"));
                vector.add(resultset.getString("mobile"));
                vector.add(resultset.getString("name"));

                ResultSet resultset2 = Mysql.execute("SELECT COUNT(*) AS ID FROM `invoice` INNER JOIN `customer_registration` ON `invoice`.`customer_registration_id`=`customer_registration`.`id` WHERE `customer_registration_id`='" + resultset.getString("id") + "'");

                if (resultset2.next()) {
                    vector.add(resultset2.getString("ID"));

                } else {
                    vector.add("0");

                }
                vector.add(resultset.getString("balance_amount"));

                model.addRow(vector);
                invoiceCount++;

            }
            count = String.valueOf(invoiceCount);
            jLabel2.setText("(" + count + ")");
            jTable1.setModel(model);
        } catch (Exception e) {
            logger.log(Level.WARNING, "customer  table load", e);

            e.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel7 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setMinimumSize(new java.awt.Dimension(1000, 500));
        setLayout(new java.awt.BorderLayout(0, 5));

        jPanel1.setMaximumSize(new java.awt.Dimension(2147483647, 100));
        jPanel1.setMinimumSize(new java.awt.Dimension(793, 100));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.BorderLayout(40, 0));

        jPanel4.setOpaque(false);
        jPanel4.setPreferredSize(new java.awt.Dimension(300, 100));
        jPanel4.setLayout(new java.awt.GridLayout(2, 1, 0, 5));

        jButton1.setBackground(new java.awt.Color(0, 153, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Create account");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jPanel4.add(jButton1);

        jButton2.setBackground(new java.awt.Color(0, 153, 255));
        jButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Update account");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton2);

        jPanel1.add(jPanel4, java.awt.BorderLayout.LINE_END);

        jPanel5.setOpaque(false);
        jPanel5.setPreferredSize(new java.awt.Dimension(970, 100));
        jPanel5.setLayout(new java.awt.GridLayout(1, 2, 40, 0));

        jPanel6.setOpaque(false);
        jPanel6.setPreferredSize(new java.awt.Dimension(465, 100));
        jPanel6.setLayout(new java.awt.GridLayout(2, 1, 0, 5));

        jPanel8.setOpaque(false);
        jPanel8.setLayout(new java.awt.BorderLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel3.setText("Mobile");
        jLabel3.setPreferredSize(new java.awt.Dimension(70, 16));
        jPanel8.add(jLabel3, java.awt.BorderLayout.LINE_START);

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        jPanel8.add(jTextField1, java.awt.BorderLayout.CENTER);

        jPanel6.add(jPanel8);

        jPanel13.setOpaque(false);
        jPanel13.setLayout(new java.awt.BorderLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel4.setText("Sort by");
        jLabel4.setPreferredSize(new java.awt.Dimension(70, 16));
        jPanel13.add(jLabel4, java.awt.BorderLayout.LINE_START);

        jPanel14.setOpaque(false);
        jPanel14.setLayout(new java.awt.BorderLayout(5, 0));

        jButton3.setBackground(new java.awt.Color(0, 153, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/reload1.png"))); // NOI18N
        jButton3.setPreferredSize(new java.awt.Dimension(50, 31));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel14.add(jButton3, java.awt.BorderLayout.LINE_END);

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Name Acending", "Name Decending" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jPanel14.add(jComboBox1, java.awt.BorderLayout.CENTER);

        jPanel13.add(jPanel14, java.awt.BorderLayout.CENTER);

        jPanel6.add(jPanel13);

        jPanel5.add(jPanel6);

        jPanel7.setOpaque(false);
        jPanel7.setLayout(new java.awt.GridLayout(2, 1, 0, 5));

        jPanel10.setOpaque(false);
        jPanel10.setLayout(new java.awt.BorderLayout());

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel5.setText("Name");
        jLabel5.setPreferredSize(new java.awt.Dimension(70, 16));
        jPanel10.add(jLabel5, java.awt.BorderLayout.LINE_START);

        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });
        jPanel10.add(jTextField2, java.awt.BorderLayout.CENTER);

        jPanel7.add(jPanel10);

        jPanel5.add(jPanel7);

        jPanel1.add(jPanel5, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.NORTH);
        jPanel1.getAccessibleContext().setAccessibleName("");

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(1270, 40));
        jPanel3.setLayout(new java.awt.BorderLayout(5, 0));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel1.setText("Customer list");
        jLabel1.setMaximumSize(new java.awt.Dimension(150, 25));
        jPanel3.add(jLabel1, java.awt.BorderLayout.LINE_START);

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel2.setText("(0)");
        jPanel3.add(jLabel2, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Mobile", "Name", "Total invoices", "Balance"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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
            jTable1.getColumnModel().getColumn(0).setMinWidth(0);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        String mobile = jTextField1.getText();
        String name = jTextField2.getText();

        if (mobile.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter mobile number", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!mobile.matches("^07[01245678]{1}[0-9]{7}$")) {
            JOptionPane.showMessageDialog(this, "Invalid Mobile Number", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter customer name", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (name.length() >= 50) {
            JOptionPane.showMessageDialog(this, "Name length cannot exeed 50 characters", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {
                ResultSet r = Mysql.execute("SELECT * FROM `customer_registration` WHERE `mobile`='" + mobile + "' ");

                if (r.next()) {
                    JOptionPane.showMessageDialog(this, "Customer alredy registred", "Warrning", JOptionPane.WARNING_MESSAGE);
                } else {

                    Mysql.execute("INSERT INTO `customer_registration` (`mobile`,`name`,`balance_amount`)  VALUES('" + mobile + "','" + name + "','0')");
                    JOptionPane.showMessageDialog(this, "Successfully added customer ! ", "Successfully", JOptionPane.INFORMATION_MESSAGE);
                    reset();
                }
            } catch (Exception e) {
                logger.log(Level.WARNING, "customer  data insert", e);
                e.printStackTrace();
            }

        }
    }//GEN-LAST:event_jButton1MouseClicked

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        int row = jTable1.getSelectedRow();

        if (evt.getClickCount() == 2 && home != null) {
            home.setCustomer_id(String.valueOf(jTable1.getValueAt(row, 0)));
            home.getjTextField3().setText(String.valueOf(jTable1.getValueAt(row, 2)));
            home.setLoanbalance(Double.parseDouble(String.valueOf(jTable1.getValueAt(row, 4))));
            home.calculation2();
            dailog.dispose();
        }

        jTextField1.setText(String.valueOf(jTable1.getValueAt(row, 1)));
        jTextField2.setText(String.valueOf(jTable1.getValueAt(row, 2)));
        jButton2.setEnabled(true);
        jButton1.setEnabled(false);


    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        int row = jTable1.getSelectedRow();

        String mobile = jTextField1.getText();
        String name = jTextField2.getText();

        if (mobile.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter mobile number", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!mobile.matches("^07[01245678]{1}[0-9]{7}$")) {
            JOptionPane.showMessageDialog(this, "Invalid Mobile Number", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter customer name", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (name.length() >= 50) {
            JOptionPane.showMessageDialog(this, "Name length cannot exeed 50 characters", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {

                if (mobile.equals(String.valueOf(jTable1.getValueAt(row, 1))) && name.equals(String.valueOf(jTable1.getValueAt(row, 2)))) {
                    JOptionPane.showMessageDialog(this, "No any changes detected !", "Error", JOptionPane.ERROR_MESSAGE);
                } else {

                    ResultSet r = Mysql.execute("SELECT * FROM `customer_registration` WHERE `mobile`='" + mobile + "' AND `id`!='" + String.valueOf(jTable1.getValueAt(row, 0)) + "' ");

                    if (r.next()) {
                        JOptionPane.showMessageDialog(this, "Mobile number alredy registred", "Warrning", JOptionPane.WARNING_MESSAGE);
                    } else {

                        Mysql.execute("UPDATE `customer_registration` SET `mobile`='" + mobile + "', `name`='" + name + "' WHERE `id`='" + String.valueOf(jTable1.getValueAt(row, 0)) + "'");
                        JOptionPane.showMessageDialog(this, "Successfully updated customer ! ", "Successfully", JOptionPane.INFORMATION_MESSAGE);
                        reset();

                    }
                }
            } catch (Exception e) {
                logger.log(Level.WARNING, "customer  data update", e);
                e.printStackTrace();
            }

        }


    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        reset();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged

        loadCutomertable();


    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased

        loadCutomertable();

    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased

        int row = jTable1.getSelectedRow();

        if (row == -1) {
            loadCutomertable();
        }

    }//GEN-LAST:event_jTextField2KeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
