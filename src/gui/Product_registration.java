/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import static gui.Customer_registration.logger;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.FileSystems;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import models.MyMethods;
import models.Mysql;
import models.ProductObject;

/**
 *
 * @author Dell
 */
public class Product_registration extends javax.swing.JPanel {

    private HashMap<String, ProductObject> productMap = new HashMap<>(); //to keep all products

    /**
     * Creates new form Product_registration
     */
    public Product_registration() {
        initComponents();
        setDefaultImage();
        loadProducts();
        jButton4.setEnabled(false);
    }

    private void setDefaultImage() {
        file = null;
        String userDirectory = FileSystems.getDefault().getPath("")
                .toAbsolutePath()
                .toString();

        String newpath = userDirectory.substring(0, userDirectory.lastIndexOf("\\"));

        String path = newpath + "\\src\\product_images\\file_upload.png";
        jLabel1.setIcon(MyMethods.resizeImage(220, 164, path));
    }

    private void loadProducts() {
        productMap.clear();
        jPanel23.removeAll();
        try {

            String id = jTextField1.getText();

            ResultSet result = Mysql.execute("SELECT* FROM  `products` WHERE `product_id` LIKE '%" + id + "%' ");
//            System.out.println("SELECT* FROM  `products` WHERE `product_id` LIKE '%" + id + "%' ");
            while (result.next()) {
                Product_card product = new Product_card();

                product.setImage(result.getString("img_url"));
                product.setProductName(result.getString("name"));
                product.setBrand(result.getString("brand"));
                product.setCompany(result.getString("company"));
                product.setBarcode(result.getString("product_id"));
                product.setQuantity(result.getString("qty"));
                product.setBuyingPrice(result.getString("buying_price"));
                product.setOurPrice(result.getString("our_price"));
                product.setUnitPrice(result.getString("selling_price"));

                ProductObject productObject = new ProductObject();
                productObject.setImage(result.getString("img_url"));
                productObject.setProduct_name(result.getString("name"));
                productObject.setBrand(result.getString("brand"));
                productObject.setCompany(result.getString("company"));
                productObject.setBarcode(result.getString("product_id"));
                productObject.setQuantity(Double.parseDouble(result.getString("qty")));
                productObject.setBuying_price(Double.parseDouble(result.getString("buying_price")));
                productObject.setOur_price(Double.parseDouble(result.getString("our_price")));
                productObject.setSelling_price(Double.parseDouble(result.getString("selling_price")));

                productMap.put(result.getString("product_id"), productObject);

                product.setProductRegistration(this);

                jPanel23.add(product);
                jPanel23.revalidate();
                jPanel23.repaint();

            }

        } catch (Exception e) {
            logger.log(Level.WARNING, "Product table load", e);
            e.printStackTrace();
        }

//        for (ProductObject item : productMap.values()) {
//            Product_card product = new Product_card();
//
//            product.setImage(item.getImage());
//            product.setProductName(item.getProduct_name());
//            product.setBrand(item.getBrand());
//            product.setCompany(item.getCompany());
//            product.setBarcode(item.getBarcode());
//            product.setQuantity(String.valueOf(item.getQuantity()));
//            product.setBuyingPrice(String.valueOf(item.getBuying_price()));
//            product.setOurPrice(String.valueOf(item.getOur_price()));
//            product.setUnitPrice(String.valueOf(item.getSelling_price()));
//
//            product.setProductRegistration(this);
//            jPanel23.add(product);
//            jPanel23.revalidate();
//            jPanel23.repaint();
//        }
    }

    public void disableBarcodeField() {
        jTextField1.setEnabled(false);
        jButton4.grabFocus();
    }

    public void setProductDetails(String barcode) {

        ProductObject product = productMap.get(barcode);

        if (product != null) {
            jButton4.setEnabled(true);
            jButton2.setEnabled(false);
            jTextField1.setText(barcode);
            jTextField2.setText(product.getProduct_name());
            jTextField3.setText(product.getBrand());
            jFormattedTextField4.setText(String.valueOf(product.getBuying_price()));
            jTextField4.setText(product.getCompany());
            jFormattedTextField2.setText(String.valueOf(product.getOur_price()));
            jFormattedTextField1.setText(String.valueOf(product.getSelling_price()));
            jFormattedTextField3.setText(String.valueOf(product.getQuantity()));

            String userDirectory = FileSystems.getDefault().getPath("")
                    .toAbsolutePath()
                    .toString();

            String newpath = userDirectory.substring(0, userDirectory.lastIndexOf(""));
            String url = newpath + "\\product_image\\";
            String path = url + product.getImage();
            jLabel1.setIcon(MyMethods.resizeImage(220, 164, path));

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jFormattedTextField4 = new javax.swing.JFormattedTextField();
        jPanel14 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jPanel15 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jPanel16 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jFormattedTextField3 = new javax.swing.JFormattedTextField();
        jPanel17 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel23 = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 10, 10));
        setMinimumSize(new java.awt.Dimension(1280, 629));
        setPreferredSize(new java.awt.Dimension(1280, 340));
        setLayout(new java.awt.BorderLayout(0, 20));

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(1280, 230));
        jPanel1.setLayout(new java.awt.BorderLayout(20, 0));

        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 230));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete-25.png"))); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 40, 40));

        jLabel1.setBackground(new java.awt.Color(204, 204, 204));
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel1.setMaximumSize(new java.awt.Dimension(200, 150));
        jLabel1.setOpaque(true);
        jLabel1.setPreferredSize(new java.awt.Dimension(220, 165));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jButton2.setBackground(new java.awt.Color(0, 153, 255));
        jButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Add to products");
        jButton2.setPreferredSize(new java.awt.Dimension(166, 42));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 188, 288, -1));

        jPanel1.add(jPanel3, java.awt.BorderLayout.LINE_END);

        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 0));
        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridLayout(1, 2, 20, 0));

        jPanel5.setOpaque(false);
        jPanel5.setLayout(new java.awt.GridLayout(5, 1, 0, 5));

        jPanel8.setOpaque(false);
        jPanel8.setLayout(new java.awt.BorderLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel2.setText("Barcode");
        jLabel2.setPreferredSize(new java.awt.Dimension(120, 0));
        jPanel8.add(jLabel2, java.awt.BorderLayout.LINE_START);

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        jPanel8.add(jTextField1, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel8);

        jPanel9.setOpaque(false);
        jPanel9.setLayout(new java.awt.BorderLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel3.setText("Product name");
        jLabel3.setPreferredSize(new java.awt.Dimension(120, 25));
        jPanel9.add(jLabel3, java.awt.BorderLayout.LINE_START);

        jTextField2.setFont(new java.awt.Font("Iskoola Pota", 0, 18)); // NOI18N
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField2KeyPressed(evt);
            }
        });
        jPanel9.add(jTextField2, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel9);

        jPanel10.setOpaque(false);
        jPanel10.setLayout(new java.awt.BorderLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel4.setText("Brand");
        jLabel4.setPreferredSize(new java.awt.Dimension(120, 0));
        jPanel10.add(jLabel4, java.awt.BorderLayout.LINE_START);

        jTextField3.setFont(new java.awt.Font("Iskoola Pota", 0, 18)); // NOI18N
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField3KeyPressed(evt);
            }
        });
        jPanel10.add(jTextField3, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel10);

        jPanel11.setOpaque(false);
        jPanel11.setLayout(new java.awt.BorderLayout());

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel5.setText("Company");
        jLabel5.setPreferredSize(new java.awt.Dimension(120, 0));
        jPanel11.add(jLabel5, java.awt.BorderLayout.LINE_START);

        jTextField4.setFont(new java.awt.Font("Iskoola Pota", 0, 18)); // NOI18N
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField4KeyPressed(evt);
            }
        });
        jPanel11.add(jTextField4, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel11);

        jPanel12.setOpaque(false);
        jPanel12.setLayout(new java.awt.BorderLayout());

        jButton3.setBackground(new java.awt.Color(0, 153, 255));
        jButton3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Clear input");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel12.add(jButton3, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel12);

        jPanel4.add(jPanel5);

        jPanel7.setOpaque(false);
        jPanel7.setLayout(new java.awt.GridLayout(5, 1, 0, 5));

        jPanel13.setOpaque(false);
        jPanel13.setLayout(new java.awt.BorderLayout());

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel6.setText("Buying price");
        jLabel6.setPreferredSize(new java.awt.Dimension(120, 0));
        jPanel13.add(jLabel6, java.awt.BorderLayout.LINE_START);

        jFormattedTextField4.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jFormattedTextField4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jFormattedTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField4KeyPressed(evt);
            }
        });
        jPanel13.add(jFormattedTextField4, java.awt.BorderLayout.CENTER);

        jPanel7.add(jPanel13);

        jPanel14.setOpaque(false);
        jPanel14.setLayout(new java.awt.BorderLayout());

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel7.setText("Selling price");
        jLabel7.setPreferredSize(new java.awt.Dimension(120, 0));
        jPanel14.add(jLabel7, java.awt.BorderLayout.LINE_START);

        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jFormattedTextField1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jFormattedTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField1KeyPressed(evt);
            }
        });
        jPanel14.add(jFormattedTextField1, java.awt.BorderLayout.CENTER);

        jPanel7.add(jPanel14);

        jPanel15.setOpaque(false);
        jPanel15.setLayout(new java.awt.BorderLayout());

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel8.setText("Our price");
        jLabel8.setPreferredSize(new java.awt.Dimension(120, 0));
        jPanel15.add(jLabel8, java.awt.BorderLayout.LINE_START);

        jFormattedTextField2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jFormattedTextField2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jFormattedTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField2ActionPerformed(evt);
            }
        });
        jFormattedTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField2KeyPressed(evt);
            }
        });
        jPanel15.add(jFormattedTextField2, java.awt.BorderLayout.CENTER);

        jPanel7.add(jPanel15);

        jPanel16.setOpaque(false);
        jPanel16.setLayout(new java.awt.BorderLayout());

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel9.setText("Quantity");
        jLabel9.setPreferredSize(new java.awt.Dimension(120, 0));
        jPanel16.add(jLabel9, java.awt.BorderLayout.LINE_START);

        jFormattedTextField3.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jFormattedTextField3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jFormattedTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField3KeyPressed(evt);
            }
        });
        jPanel16.add(jFormattedTextField3, java.awt.BorderLayout.CENTER);

        jPanel7.add(jPanel16);

        jPanel17.setOpaque(false);
        jPanel17.setLayout(new java.awt.BorderLayout());

        jButton4.setBackground(new java.awt.Color(0, 153, 255));
        jButton4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Update product");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel17.add(jButton4, java.awt.BorderLayout.CENTER);

        jPanel7.add(jPanel17);

        jPanel4.add(jPanel7);

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.BorderLayout(0, 5));

        jPanel18.setBackground(new java.awt.Color(226, 226, 226));
        jPanel18.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 10));
        jPanel18.setLayout(new java.awt.BorderLayout());

        jPanel6.setOpaque(false);
        jPanel6.setPreferredSize(new java.awt.Dimension(750, 60));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jLabel12.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel12.setText("Product");
        jLabel12.setPreferredSize(new java.awt.Dimension(350, 25));
        jPanel6.add(jLabel12, java.awt.BorderLayout.WEST);

        jPanel19.setOpaque(false);
        jPanel19.setLayout(new java.awt.BorderLayout());

        jLabel13.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel13.setText("Company");
        jPanel19.add(jLabel13, java.awt.BorderLayout.CENTER);

        jLabel15.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Barcode");
        jLabel15.setPreferredSize(new java.awt.Dimension(150, 25));
        jPanel19.add(jLabel15, java.awt.BorderLayout.LINE_END);

        jPanel6.add(jPanel19, java.awt.BorderLayout.CENTER);

        jPanel18.add(jPanel6, java.awt.BorderLayout.WEST);

        jPanel21.setOpaque(false);
        jPanel21.setLayout(new java.awt.BorderLayout(10, 0));

        jLabel14.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel14.setText("Quantity");
        jPanel21.add(jLabel14, java.awt.BorderLayout.LINE_START);

        jPanel22.setOpaque(false);
        jPanel22.setLayout(new java.awt.GridLayout(1, 3, 10, 0));

        jLabel11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Buying price");
        jPanel22.add(jLabel11);

        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Our price");
        jPanel22.add(jLabel10);

        jLabel16.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Selling price");
        jPanel22.add(jLabel16);

        jPanel21.add(jPanel22, java.awt.BorderLayout.CENTER);

        jPanel18.add(jPanel21, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel18, java.awt.BorderLayout.PAGE_START);

        jScrollPane1.setBorder(null);

        jPanel23.setBackground(new java.awt.Color(239, 242, 247));
        jPanel23.setLayout(new javax.swing.BoxLayout(jPanel23, javax.swing.BoxLayout.PAGE_AXIS));
        jScrollPane1.setViewportView(jPanel23);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    File file;

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        file = MyMethods.chooseImage();
        if (file != null) {
            String path = file.getAbsolutePath();
            jLabel1.setIcon(MyMethods.resizeImage(jLabel1.getWidth(), jLabel1.getHeight(), path));
        } else {
            JOptionPane.showMessageDialog(this, "No file selected");
        }
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        try {

            String userDirectory = FileSystems.getDefault().getPath("")
                    .toAbsolutePath()
                    .toString();

            String newpath = userDirectory.substring(0, userDirectory.lastIndexOf(""));
            String url = newpath + "\\product_image";

            String id = jTextField1.getText().trim();
            String product_name = jTextField2.getText().trim();
            String brand = jTextField3.getText().trim();
            String company = jTextField4.getText().trim();
            String selling_price = jFormattedTextField1.getText().trim();
            String our_orice = jFormattedTextField2.getText().trim();
            String qty = jFormattedTextField3.getText().trim();
            String buying_price = jFormattedTextField4.getText().trim();

            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter product barcode", "Warrning", JOptionPane.WARNING_MESSAGE);

            } else if (id.length() > 30) {
                JOptionPane.showMessageDialog(this, "Please product barcode must not exeede 30 digits", "Warrning", JOptionPane.WARNING_MESSAGE);

            } else if (product_name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter product name", "Warrning", JOptionPane.WARNING_MESSAGE);

            } else if (buying_price.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter product buying price", "Warrning", JOptionPane.WARNING_MESSAGE);

            } else if (selling_price.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter product selling price", "Warrning", JOptionPane.WARNING_MESSAGE);

            } else if (our_orice.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter product our price", "Warrning", JOptionPane.WARNING_MESSAGE);

            }  else {

                if (brand.isEmpty()) {
                    brand = "No brand";
                }

                if (company.isEmpty()) {
                    company = "No company";
                }
                if (qty.isEmpty()) {
                    qty = "1000000";
                }
                String path = "";

                if (file == null) {
                    path = "no_product.png";
                } else {
                    path = file.getName();
                }

                Mysql.execute("INSERT INTO `products` (`product_id`,`name`,`brand`,`company`,`qty`,`buying_price`,`selling_price`,`our_price`,`img_url`)"
                        + "VALUES('" + id + "','" + product_name + "','" + brand + "','" + company + "','" + qty + "','" + buying_price + "','" + selling_price + "','" + our_orice + "','" + path + "') ");

                if (file != null) {

                    MyMethods.copyUploadedImage(file, url);
                }

                loadProducts();
                jTextField1.setText("");
                jTextField2.setText("");
                jTextField3.setText("");
                jTextField4.setText("");
                jFormattedTextField4.setText("");
                jFormattedTextField1.setText("");
                jFormattedTextField2.setText("");
                jFormattedTextField3.setText("");

                setDefaultImage();
                JOptionPane.showMessageDialog(this, "Successfully added product ! ", "Successfully", JOptionPane.INFORMATION_MESSAGE);

            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Product Registartion", e);
            e.printStackTrace();

        }
        loadProducts();
    }//GEN-LAST:event_jButton2ActionPerformed

    private boolean imagecheck = true;

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        imagecheck = false;
        file = null;
        setDefaultImage();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        jButton4.setEnabled(false);
        jButton2.setEnabled(true);
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jFormattedTextField4.setText("");
        jFormattedTextField1.setText("");
        jFormattedTextField2.setText("");
        jFormattedTextField3.setText("");
        //image
        setDefaultImage();
        loadProducts();
        jTextField1.setEnabled(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        try {

            String userDirectory = FileSystems.getDefault().getPath("")
                    .toAbsolutePath()
                    .toString();

            String newpath = userDirectory.substring(0, userDirectory.lastIndexOf(""));
            String url = newpath + "\\product_image";

            String id = jTextField1.getText();
            String product_name = jTextField2.getText();
            String brand = jTextField3.getText();
            String company = jTextField4.getText();
            String selling_price = jFormattedTextField1.getText();
            String our_orice = jFormattedTextField2.getText();
            String qty = jFormattedTextField3.getText();
            String buying_price = jFormattedTextField4.getText();

            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter product barcode", "Warrning", JOptionPane.WARNING_MESSAGE);

            } else if (product_name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter product name", "Warrning", JOptionPane.WARNING_MESSAGE);

            } else if (brand.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter product brand", "Warrning", JOptionPane.WARNING_MESSAGE);

            } else if (company.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter product company", "Warrning", JOptionPane.WARNING_MESSAGE);

            } else if (buying_price.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter product buying price", "Warrning", JOptionPane.WARNING_MESSAGE);

            } else if (selling_price.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter product selling price", "Warrning", JOptionPane.WARNING_MESSAGE);

            } else if (our_orice.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter product our price", "Warrning", JOptionPane.WARNING_MESSAGE);

            } else if (qty.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter product qty", "Warrning", JOptionPane.WARNING_MESSAGE);

            } else {
                boolean ok = false;
                if (imagecheck && file == null) {
                    Mysql.execute("UPDATE `products` SET `name`='" + product_name + "', `brand`='" + brand + "', `company`='" + company + "', `qty`='" + qty + "', `buying_price`='" + buying_price + "', `selling_price`=  '" + selling_price + "',"
                            + " `our_price`='" + our_orice + "'  WHERE  `product_id`='" + id + "'");
                    ok = true;

                } else {

                    String path = "";

                    if (file == null) {
                        path = "no_product.png";
                    } else {
                        path = file.getName();
                    }
                    Mysql.execute("UPDATE `products` SET `name`='" + product_name + "', `brand`='" + brand + "', `company`='" + company + "', `qty`='" + qty + "', `buying_price`='" + buying_price + "', `selling_price`=  '" + selling_price + "',"
                            + " `our_price`='" + our_orice + "', `img_url`= '" + path + "' WHERE  `product_id`='" + id + "'");

                    if (file != null) {
                        MyMethods.copyUploadedImage(file, url);
                    }

                    ok = true;

                }

                if (ok) {
                    loadProducts();
                    jTextField1.setText("");
                    jTextField2.setText("");
                    jTextField3.setText("");
                    jTextField4.setText("");
                    jFormattedTextField4.setText("");
                    jFormattedTextField1.setText("");
                    jFormattedTextField2.setText("");
                    jFormattedTextField3.setText("");

                    setDefaultImage();
                    JOptionPane.showMessageDialog(this, "Successfully updated product ! ", "Successfully", JOptionPane.INFORMATION_MESSAGE);

                    jButton2.setEnabled(true);
                    jButton4.setEnabled(false);
                }
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Product Registartion", e);
            e.printStackTrace();

        }
        loadProducts();
        jTextField1.setEnabled(true);

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        loadProducts();
        setProductDetails(jTextField1.getText());


    }//GEN-LAST:event_jTextField1KeyReleased

    private void jFormattedTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        // TODO add your handling code here:
        navigateByKey(evt, "barcode");
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jTextField2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyPressed
        // TODO add your handling code here:
        navigateByKey(evt, "name");
    }//GEN-LAST:event_jTextField2KeyPressed

    private void jTextField3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyPressed
        // TODO add your handling code here:
        navigateByKey(evt, "brand");
    }//GEN-LAST:event_jTextField3KeyPressed

    private void jTextField4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyPressed
        // TODO add your handling code here:
        navigateByKey(evt, "company");
    }//GEN-LAST:event_jTextField4KeyPressed

    private void jFormattedTextField4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField4KeyPressed
        // TODO add your handling code here:
        navigateByKey(evt, "buyingPrice");
    }//GEN-LAST:event_jFormattedTextField4KeyPressed

    private void jFormattedTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField1KeyPressed
        // TODO add your handling code here:
        navigateByKey(evt, "sellingPrice");
    }//GEN-LAST:event_jFormattedTextField1KeyPressed

    private void jFormattedTextField2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField2KeyPressed
        // TODO add your handling code here:
        navigateByKey(evt, "ourPrice");
    }//GEN-LAST:event_jFormattedTextField2KeyPressed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jFormattedTextField3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField3KeyPressed
        // TODO add your handling code here:
        navigateByKey(evt, "qty");
    }//GEN-LAST:event_jFormattedTextField3KeyPressed

    private void navigateByKey(java.awt.event.KeyEvent evt, String currentField) {
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                if (currentField.equals("barcode")) {
                    jFormattedTextField4.grabFocus();
                } else if (currentField.equals("name")) {
                    jFormattedTextField1.grabFocus();
                } else if (currentField.equals("brand")) {
                    jFormattedTextField2.grabFocus();
                } else if (currentField.equals("company")) {
                    jFormattedTextField3.grabFocus();
                }
                break;
            case KeyEvent.VK_LEFT:
                if (currentField.equals("buyingPrice")) {
                    jTextField1.grabFocus();
                } else if (currentField.equals("sellingPrice")) {
                    jTextField2.grabFocus();
                } else if (currentField.equals("ourPrice")) {
                    jTextField3.grabFocus();
                } else if (currentField.equals("qty")) {
                    jTextField4.grabFocus();
                }
                break;
            case KeyEvent.VK_DOWN:
                if (currentField.equals("barcode")) {
                    jTextField2.grabFocus();
                } else if (currentField.equals("name")) {
                    jTextField3.grabFocus();
                } else if (currentField.equals("brand")) {
                     jTextField4.grabFocus();
                } else if (currentField.equals("company")) {
                    jFormattedTextField4.grabFocus();
                } else if (currentField.equals("buyingPrice")) {
                     jFormattedTextField1.grabFocus();
                } else if (currentField.equals("sellingPrice")) {
                    jFormattedTextField2.grabFocus();
                } else if (currentField.equals("ourPrice")) {
                    jFormattedTextField3.grabFocus();
                }
                break;
            case KeyEvent.VK_UP:
                if (currentField.equals("name")) {
                    jTextField1.grabFocus();
                } else if (currentField.equals("brand")) {
                     jTextField2.grabFocus();
                } else if (currentField.equals("company")) {
                    jTextField3.grabFocus();
                } else if (currentField.equals("buyingPrice")) {
                     jTextField4.grabFocus();
                } else if (currentField.equals("sellingPrice")) {
                    jFormattedTextField4.grabFocus();
                } else if (currentField.equals("ourPrice")) {
                    jFormattedTextField1.grabFocus();
                } else if (currentField.equals("qty")) {
                    jFormattedTextField2.grabFocus();
                }
                break;

        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JFormattedTextField jFormattedTextField3;
    private javax.swing.JFormattedTextField jFormattedTextField4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
