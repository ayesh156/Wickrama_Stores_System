/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import static gui.Customer_registration.logger;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.FileSystems;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.xml.crypto.Data;
import models.MyMethods;
import models.Mysql;
import models.ProductObject;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Dell
 */
public class Home extends javax.swing.JFrame {

    private String customer_id;
    private double loanbalance;
    public static String user;

    private HashMap<String, String> productNameMap = new HashMap<>(); //to keep product names with IDss

    public void setUser(String user) {
        // Split the string using a comma (,) as the delimiter
        String[] parts = user.split("@");
        jLabel4.setText("Cashier: " + parts[0]);
        this.user = user;
    }

    public void focusQuantity() {
        jTextField6.grabFocus();
    }
    
    public Border getDefaultBorder(){
        return jButton1.getBorder();
    }

    /**
     * @return the loanbalance
     */
    public double getLoanbalance() {
        return loanbalance;
    }

    /**
     * @param loanbalance the loanbalance to set
     */
    public void setLoanbalance(double loanbalance) {

        this.loanbalance = loanbalance;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    /**
     * @param customer_id the customer_id to set
     */
    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public JTextField getjTextField3() {
        return jTextField3;
    }

    private HashMap<String, ProductObject> productMap = new HashMap<>(); //to keep all products
    private HashMap<String, ProductObject> CartMap = new HashMap<>();    //to keep cart items. This should be emptied once after invoicing process is done
    Color defaultButtonColor, defaultTextColor;

    public void setBarcode(String barcode) {
        jTextField2.setText(barcode);
    }

    public void setProductName(String barcode) {
        if (productMap.containsKey(barcode)) {
            jTextField1.setText(productMap.get(barcode).getProduct_name());
            jTextField6.grabFocus();
        } else {
            jTextField1.setText("");
        }
    }

    public void removeItem(String barcode) {
        CartMap.remove(barcode);
    }

    public void updateCart(String barcode, String quantity) {
        if (CartMap.containsKey(barcode)) {
            ProductObject item = CartMap.get(barcode);
            item.setBuyingQuantity(quantity);
            item.setItemTotal(item.getOur_price() * Double.parseDouble(item.getBuyingQuantity()));
        }
        loadCartItems();
        calculation();
        calculation2();
    }

    /**
     * Creates new form Home
     */
    public Home() {
        initComponents();
        addStyle();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        try {
            ImageIcon imageIcon = new ImageIcon(Home.class.getResource("/resources/icon.png"));
            this.setIconImage(imageIcon.getImage());
        } catch (Exception e) {
            logger.log(Level.WARNING, "Image Icon", e);
            e.printStackTrace();
        }
        setLogo();
        runClock();
        loadProducts();
        jTextField3.setText("Unkown");
        customer_id = "1";
    }

    private void addStyle() {

        jTextField2.putClientProperty("JTextField.placeholderText", "Enter barcode");

        jTextField2.putClientProperty("JTextField.showClearButton", true);
        jTextField1.putClientProperty("JTextField.showClearButton", true);

    }
    private boolean paymentEntered = false;
    private double discount = 0;

    private double Total = 0;

    public void calculation2() {
        double newbalane;
        double balane;
        double pyable;
//        String Total = jLabel23.getText();
        newbalane = (loanbalance - loanbalance - loanbalance) + Total;

        jLabel23.setText(String.valueOf(newbalane));
        calculation();
    }

    public void calculation() {
        String discount = jTextField4.getText().trim();
        String payment = jTextField5.getText().trim();

        boolean inputError = false;
        if (!discount.matches("\\d+(\\.\\d+)?")) {
            if (discount.equals("")) {
                paymentEntered = false;
                jTextField4.setForeground(defaultTextColor);
                discount = "0";
            } else {
                inputError = true;
                jTextField4.setForeground(Color.RED);
            }
            paymentEntered = false;
        } else {
            paymentEntered = true;

            jTextField4.setForeground(defaultTextColor);
        }

        if (!payment.matches("\\d+(\\.\\d+)?")) {
            if (payment.equals("")) {
                this.discount = 0;
                jTextField5.setForeground(defaultTextColor);
                payment = "0";
            } else {
                inputError = true;
                jTextField5.setForeground(Color.RED);
            }
        } else {
            this.discount = Double.parseDouble(jTextField5.getText());
            jTextField5.setForeground(defaultTextColor);
        }

        if (inputError) {

            if (extractNumbers(discount).equals("") || extractNumbers(discount).equals("0")) {
                jTextField4.setText("");
            } else {
                jTextField4.setText(extractNumbers(discount));
            }
            if (extractNumbers(payment).equals("") || extractNumbers(payment).equals("0")) {
                jTextField5.setText("");
            } else {
                jTextField5.setText(extractNumbers(payment));
            }

            jTextField4.setForeground(defaultTextColor);
            jTextField5.setForeground(defaultTextColor);
        } else {

            double newbalane;
            double balane;
            double pyable;
            String Total = jLabel23.getText();
            String balance = jLabel25.getText();
            String payable = jLabel24.getText();

            pyable = Double.parseDouble(Total) - Double.parseDouble(discount);
            payable = String.valueOf(pyable);
            jLabel24.setText(payable);

            balane = Double.parseDouble(payment) - Double.parseDouble(payable);
            jLabel25.setText(String.valueOf(balane));

        }

    }

    public static String extractNumbers(String input) {
        // Define a regular expression pattern to match digits (0-9)
        Pattern pattern = Pattern.compile("\\d+");

        // Create a Matcher object to find the matches
        Matcher matcher = pattern.matcher(input);

        StringBuilder result = new StringBuilder();

        // Find and append all matches to the result
        while (matcher.find()) {
            result.append(matcher.group());
        }

        return result.toString();
    }

    private boolean setProductDetails(String barcode) {

        ProductObject product = productMap.get(barcode);

        if (product != null) {

            jTextField1.setText(product.getProduct_name());
            return true;

        } else {
            return false;
        }
    }

    public HashMap<String, String> loadProducts() {

        jPanel16.removeAll();
        productMap.clear();
        productNameMap.clear();

        try {
            String id = jTextField2.getText();
            String name = jTextField1.getText();
            String limit = "";
            if (id.equals("") && name.equals("")) {

            } else {
                limit = " LIMIT 10 ";
            }
            ResultSet reseults = Mysql.execute("SELECT * FROM `products` WHERE `product_id` LIKE '%" + id + "%' AND `name` LIKE '%" + name + "%' " + limit);

            Vector<String> productNameVector = new Vector<>();

            while (reseults.next()) {
//                System.out.println(reseults.getString("product_id"));
                ProductObject product1 = new ProductObject();
                product1.setBarcode(reseults.getString("product_id"));
                product1.setBrand(reseults.getString("brand"));
                product1.setImage(reseults.getString("img_url"));
                product1.setProduct_name(reseults.getString("name"));
                product1.setQuantity(reseults.getDouble("qty"));
                product1.setBuying_price(reseults.getDouble("buying_price"));
                product1.setSelling_price(reseults.getDouble("selling_price"));
                product1.setOur_price(reseults.getDouble("our_price"));
                product1.setDiscount(product1.getSelling_price() - product1.getOur_price());
                productMap.put(product1.getBarcode(), product1);    //adding the objects to the hashmap

                productNameMap.put(product1.getProduct_name(), product1.getBarcode());    //adding the Product names and IDs to the hashmap

                productNameVector.add(product1.getProduct_name());

            }

            for (ProductObject item : productMap.values()) {
//                System.out.println(item.getBarcode());

                Product product = new Product();
                product.setImage(item.getImage());
                product.setBarcode(item.getBarcode());
                product.setQuantity(String.valueOf(item.getQuantity()));
                product.setUnitprice(String.valueOf(item.getSelling_price()));
                product.setHome(this);

                jPanel16.add(product);
                jPanel16.revalidate();
                jPanel16.repaint();
            }
            if (productMap.isEmpty()) {
                jPanel16.removeAll();
            }
            return productNameMap;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Invoice Product table load", e);
            e.printStackTrace();
            return null;
        }

    }

    private void setLogo() {

        String userDirectory = FileSystems.getDefault().getPath("")
                .toAbsolutePath()
                .toString();

        String newpath = userDirectory.substring(0, userDirectory.lastIndexOf("\\"));

        String path = newpath + "\\src\\img\\logo.jpg";

        jLabel1.setIcon(MyMethods.resizeImage(60, 60, path));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jTextField6 = new javax.swing.JTextField();
        jPanel34 = new javax.swing.JPanel();
        jTextField2 = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel17 = new javax.swing.JPanel();
        jTextField3 = new javax.swing.JTextField();
        jPanel33 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel16 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel22 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jPanel30 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jPanel31 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jMenu7 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("<html>\n<span style=\"color: #800080;\">\n <b>Nebula Infinite</b>\n</span>\n</html>");
        setMinimumSize(new java.awt.Dimension(1260, 700));

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(232, 239, 249));
        jPanel3.setMaximumSize(new java.awt.Dimension(32767, 60));
        jPanel3.setMinimumSize(new java.awt.Dimension(100, 60));
        jPanel3.setPreferredSize(new java.awt.Dimension(1260, 60));
        jPanel3.setLayout(new java.awt.GridLayout(1, 3));

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridLayout(1, 0));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 36)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("10:30AM");
        jPanel4.add(jLabel3);

        jPanel3.add(jPanel4);

        jPanel5.setOpaque(false);
        jPanel5.setLayout(new java.awt.BorderLayout());

        jLabel1.setBackground(new java.awt.Color(153, 153, 153));
        jLabel1.setMaximumSize(new java.awt.Dimension(60, 60));
        jLabel1.setMinimumSize(new java.awt.Dimension(60, 60));
        jLabel1.setOpaque(true);
        jLabel1.setPreferredSize(new java.awt.Dimension(60, 60));
        jPanel5.add(jLabel1, java.awt.BorderLayout.LINE_START);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Wickrama Stores");
        jPanel5.add(jLabel2, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel5);

        jPanel6.setToolTipText("");
        jPanel6.setOpaque(false);
        jPanel6.setLayout(new java.awt.GridLayout(1, 0));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("User: Cashier");
        jPanel6.add(jLabel4);

        jPanel3.add(jPanel6);

        jPanel2.add(jPanel3, java.awt.BorderLayout.NORTH);

        jPanel32.setBackground(new java.awt.Color(255, 255, 255));
        jPanel32.setPreferredSize(new java.awt.Dimension(1290, 629));
        jPanel32.setLayout(new javax.swing.BoxLayout(jPanel32, javax.swing.BoxLayout.LINE_AXIS));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jPanel7.setLayout(new java.awt.GridLayout(1, 2, 10, 0));

        jPanel11.setMinimumSize(new java.awt.Dimension(0, 50));
        jPanel11.setLayout(new java.awt.GridLayout(3, 1, 0, 5));

        jPanel8.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 10));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 153, 255));
        jLabel6.setText("Barcode");
        jLabel6.setPreferredSize(new java.awt.Dimension(130, 25));
        jPanel8.add(jLabel6, java.awt.BorderLayout.LINE_START);

        jPanel13.setLayout(new java.awt.BorderLayout(5, 0));

        jPanel14.setPreferredSize(new java.awt.Dimension(160, 45));
        jPanel14.setRequestFocusEnabled(false);
        jPanel14.setLayout(new java.awt.BorderLayout());

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add-35.png"))); // NOI18N
        jButton1.setAlignmentY(0.0F);
        jButton1.setPreferredSize(new java.awt.Dimension(60, 22));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton1KeyPressed(evt);
            }
        });
        jPanel14.add(jButton1, java.awt.BorderLayout.LINE_END);

        jTextField6.setEditable(false);
        jTextField6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField6.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });
        jTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField6KeyPressed(evt);
            }
        });
        jPanel14.add(jTextField6, java.awt.BorderLayout.CENTER);

        jPanel13.add(jPanel14, java.awt.BorderLayout.EAST);

        jPanel34.setLayout(new java.awt.GridLayout(1, 2, 5, 0));

        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField2.setPreferredSize(new java.awt.Dimension(250, 22));
        jTextField2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField2FocusGained(evt);
            }
        });
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField2KeyTyped(evt);
            }
        });
        jPanel34.add(jTextField2);
        jTextField2.getAccessibleContext().setAccessibleName("Barcode");

        jPanel13.add(jPanel34, java.awt.BorderLayout.CENTER);

        jPanel8.add(jPanel13, java.awt.BorderLayout.CENTER);

        jPanel11.add(jPanel8);

        jPanel12.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 10));
        jPanel12.setName(""); // NOI18N
        jPanel12.setLayout(new java.awt.BorderLayout());

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 153, 255));
        jLabel5.setText("Product Name");
        jLabel5.setPreferredSize(new java.awt.Dimension(130, 25));
        jPanel12.add(jLabel5, java.awt.BorderLayout.LINE_START);

        jTextField1.setFont(new java.awt.Font("Iskoola Pota", 0, 18)); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });
        jPanel12.add(jTextField1, java.awt.BorderLayout.CENTER);

        jPanel11.add(jPanel12);

        jPanel17.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 10));
        jPanel17.setLayout(new java.awt.BorderLayout(5, 0));

        jTextField3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel17.add(jTextField3, java.awt.BorderLayout.CENTER);

        jPanel33.setPreferredSize(new java.awt.Dimension(163, 31));
        jPanel33.setLayout(new java.awt.BorderLayout(5, 0));

        jButton4.setBackground(new java.awt.Color(0, 153, 255));
        jButton4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Select");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel33.add(jButton4, java.awt.BorderLayout.CENTER);

        jButton5.setBackground(new java.awt.Color(0, 153, 255));
        jButton5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/reload1.png"))); // NOI18N
        jButton5.setPreferredSize(new java.awt.Dimension(50, 31));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel33.add(jButton5, java.awt.BorderLayout.EAST);

        jPanel17.add(jPanel33, java.awt.BorderLayout.EAST);

        jLabel11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 153, 255));
        jLabel11.setText("Customer");
        jLabel11.setPreferredSize(new java.awt.Dimension(125, 25));
        jPanel17.add(jLabel11, java.awt.BorderLayout.LINE_START);

        jPanel11.add(jPanel17);

        jPanel15.setBackground(new java.awt.Color(226, 226, 226));

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel7.setText("Products");

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Barcode");

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Quantity");

        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel10.setText("Unit price");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(103, 103, 103)
                .addComponent(jLabel9)
                .addGap(65, 65, 65)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(jLabel9)))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jScrollPane2.setBorder(null);

        jPanel16.setLayout(new javax.swing.BoxLayout(jPanel16, javax.swing.BoxLayout.PAGE_AXIS));
        jScrollPane2.setViewportView(jPanel16);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel7.add(jPanel9);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jPanel18.setBackground(new java.awt.Color(239, 242, 247));
        jPanel18.setLayout(new java.awt.BorderLayout());

        jPanel19.setOpaque(false);
        jPanel19.setPreferredSize(new java.awt.Dimension(420, 70));
        jPanel19.setLayout(new java.awt.BorderLayout());

        jLabel13.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 153, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("TOTAL");
        jLabel13.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 10));
        jLabel13.setPreferredSize(new java.awt.Dimension(100, 25));
        jPanel19.add(jLabel13, java.awt.BorderLayout.LINE_END);

        jLabel14.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 153, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("QTY");
        jLabel14.setPreferredSize(new java.awt.Dimension(70, 25));
        jPanel19.add(jLabel14, java.awt.BorderLayout.LINE_START);

        jPanel21.setOpaque(false);
        jPanel21.setLayout(new java.awt.BorderLayout());

        jLabel15.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 153, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("BRAND");
        jLabel15.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 0));
        jLabel15.setPreferredSize(new java.awt.Dimension(130, 25));
        jPanel21.add(jLabel15, java.awt.BorderLayout.LINE_START);

        jLabel16.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 153, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("<html><p>PRICE</p></html> ");
        jPanel21.add(jLabel16, java.awt.BorderLayout.CENTER);

        jPanel19.add(jPanel21, java.awt.BorderLayout.CENTER);

        jPanel18.add(jPanel19, java.awt.BorderLayout.LINE_END);

        jPanel23.setOpaque(false);
        jPanel23.setLayout(new java.awt.BorderLayout());

        jLabel17.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 153, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("#");
        jLabel17.setPreferredSize(new java.awt.Dimension(50, 25));
        jPanel23.add(jLabel17, java.awt.BorderLayout.LINE_START);

        jLabel18.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 153, 255));
        jLabel18.setText("ITEMS (0)");
        jPanel23.add(jLabel18, java.awt.BorderLayout.CENTER);

        jPanel18.add(jPanel23, java.awt.BorderLayout.CENTER);

        jScrollPane1.setBorder(null);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jPanel22.setBackground(new java.awt.Color(239, 242, 247));
        jPanel22.setLayout(new javax.swing.BoxLayout(jPanel22, javax.swing.BoxLayout.PAGE_AXIS));
        jScrollPane1.setViewportView(jPanel22);

        jPanel20.setBackground(new java.awt.Color(153, 204, 255));
        jPanel20.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        jPanel20.setLayout(new java.awt.GridLayout(1, 2, 10, 0));

        jPanel24.setOpaque(false);
        jPanel24.setLayout(new java.awt.GridLayout(3, 1, 0, 5));

        jPanel26.setOpaque(false);
        jPanel26.setLayout(new java.awt.BorderLayout());

        jLabel12.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel12.setText("Total");
        jLabel12.setPreferredSize(new java.awt.Dimension(120, 25));
        jPanel26.add(jLabel12, java.awt.BorderLayout.LINE_START);

        jLabel23.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel23.setText("0.0");
        jLabel23.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 40));
        jPanel26.add(jLabel23, java.awt.BorderLayout.CENTER);

        jPanel24.add(jPanel26);

        jPanel27.setOpaque(false);
        jPanel27.setLayout(new java.awt.BorderLayout());

        jLabel19.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel19.setText("Total payable");
        jLabel19.setPreferredSize(new java.awt.Dimension(120, 25));
        jPanel27.add(jLabel19, java.awt.BorderLayout.LINE_START);

        jLabel24.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel24.setText("0.0");
        jLabel24.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 40));
        jPanel27.add(jLabel24, java.awt.BorderLayout.CENTER);

        jPanel24.add(jPanel27);

        jPanel28.setOpaque(false);
        jPanel28.setLayout(new java.awt.BorderLayout());

        jLabel20.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel20.setText("Balance");
        jLabel20.setPreferredSize(new java.awt.Dimension(120, 25));
        jPanel28.add(jLabel20, java.awt.BorderLayout.LINE_START);

        jLabel25.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel25.setText("0.0");
        jLabel25.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 40));
        jPanel28.add(jLabel25, java.awt.BorderLayout.CENTER);

        jPanel24.add(jPanel28);

        jPanel20.add(jPanel24);

        jPanel25.setOpaque(false);
        jPanel25.setLayout(new java.awt.GridLayout(3, 1, 0, 5));

        jPanel29.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel29.setOpaque(false);
        jPanel29.setLayout(new java.awt.BorderLayout(5, 0));

        jLabel21.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel21.setText("Discount");
        jLabel21.setPreferredSize(new java.awt.Dimension(120, 25));
        jPanel29.add(jLabel21, java.awt.BorderLayout.LINE_START);

        jTextField4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jTextField4.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField4KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });
        jPanel29.add(jTextField4, java.awt.BorderLayout.CENTER);

        jPanel25.add(jPanel29);

        jPanel30.setOpaque(false);
        jPanel30.setLayout(new java.awt.BorderLayout(5, 0));

        jLabel22.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel22.setText("Payment");
        jLabel22.setPreferredSize(new java.awt.Dimension(120, 25));
        jPanel30.add(jLabel22, java.awt.BorderLayout.LINE_START);

        jTextField5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jTextField5.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField5KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField5KeyReleased(evt);
            }
        });
        jPanel30.add(jTextField5, java.awt.BorderLayout.CENTER);

        jPanel25.add(jPanel30);

        jPanel31.setOpaque(false);
        jPanel31.setLayout(new java.awt.BorderLayout(5, 0));

        jButton2.setBackground(new java.awt.Color(153, 204, 255));
        jButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(204, 0, 51));
        jButton2.setText("Reset");
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 51), 2));
        jButton2.setPreferredSize(new java.awt.Dimension(120, 22));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel31.add(jButton2, java.awt.BorderLayout.LINE_START);

        jButton3.setBackground(new java.awt.Color(0, 153, 255));
        jButton3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Print Invoice");
        jButton3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 255), 2, true));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel31.add(jButton3, java.awt.BorderLayout.CENTER);

        jPanel25.add(jPanel31);

        jPanel20.add(jPanel25);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel7.add(jPanel10);

        jPanel32.add(jPanel7);

        jPanel2.add(jPanel32, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2);

        jMenuBar1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/invoice2-20.png"))); // NOI18N
        jMenu1.setText("Invoice");
        jMenu1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu1MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu1);

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/sale-20.png"))); // NOI18N
        jMenu3.setText("Whole sale");
        jMenu3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jMenu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu3MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu3);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/products-20.png"))); // NOI18N
        jMenu2.setText("Products");
        jMenu2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu2MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu2);

        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/registration-20.png"))); // NOI18N
        jMenu4.setText("Customer registration");
        jMenu4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jMenu4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu4MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu4);

        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/invoice-18.png"))); // NOI18N
        jMenu5.setText("Invoice history");
        jMenu5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jMenu5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu5MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu5);

        jMenu6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/medical-history-20.png"))); // NOI18N
        jMenu6.setText("Whole sale history");
        jMenu6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jMenu6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu6MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu6);

        jMenu7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/new-window-20.png"))); // NOI18N
        jMenu7.setText("New window");
        jMenu7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jMenu7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu7MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu7);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1306, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        addToCart(jTextField2.getText().trim(), jTextField6.getText().trim());
        jTextField2.grabFocus();
        loadProducts();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void addToCart(String barcode, String qty) {

        ProductObject product = productMap.get(barcode);
        try {
            //checking, if their a product for the entered barcode
            if (product != null) {
                //checking if the quantity is invalid or 0

                if (qty.equals("") || qty.isEmpty()) {
                    qty = "1";
                }
                
                if (Double.parseDouble(qty) == 0) {
                    JOptionPane.showMessageDialog(this, "Please enter valid quantity", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                ResultSet r = Mysql.execute("SELECT * FROM products WHERE product_id='" + barcode + "'");

                String pqty = "0";
                if (r.next()) {
                    pqty = r.getString("qty");
                    product.setOur_price(Double.parseDouble(r.getString("our_price")));
                }

                if (CartMap.containsKey(barcode)) {
                    product.setBuyingQuantity(String.valueOf(Double.parseDouble(CartMap.get(barcode).getBuyingQuantity()) + Double.parseDouble(qty)));
                } else {
                    product.setBuyingQuantity(qty);
                }

                product.setItemTotal(Double.parseDouble(product.getBuyingQuantity()) * product.getOur_price());
                product.setTotalDiscount(Double.parseDouble(product.getBuyingQuantity()) * product.getDiscount());
                product.setBarcode(barcode);

                Date timestamp = new Date();
                product.setTimestamp(timestamp);

                CartMap.put(barcode, product);

                jTextField1.setText("");
                jTextField2.setText("");
                this.qty = "1";
                jTextField6.setText("");

                System.out.println(CartMap.get(barcode).getDiscount()); //for testing
                System.out.println(CartMap.get(barcode).getTotalDiscount()); //for testing

            } else {
                JOptionPane.showMessageDialog(this, "Please Select Correct product", "Warning", JOptionPane.WARNING_MESSAGE);

            }

            loadCartItems();
            calculation();
            calculation2();
        } catch (Exception e) {
            logger.log(Level.WARNING, "invoic add cartmap ", e);
            e.printStackTrace();
        }

    }
    private void jMenu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu2MouseClicked
        // TODO add your handling code here:
        Product_registration products = new Product_registration();
        jPanel32.removeAll();
        jPanel32.add(products);
        jPanel32.revalidate();
        jPanel32.repaint();

        jLabel2.setText("Products");
    }//GEN-LAST:event_jMenu2MouseClicked

    private void jMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MouseClicked
        // TODO add your handling code here:
        jPanel32.removeAll();
        jPanel32.add(jPanel7);
        jPanel32.revalidate();
        jPanel32.repaint();

        jLabel2.setText("Wickrama Stores");
        loadProducts();
    }//GEN-LAST:event_jMenu1MouseClicked

    private void jMenu4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu4MouseClicked
        // TODO add your handling code here:
        Customer_registration customerRegistration = new Customer_registration();
        jPanel32.removeAll();
        jPanel32.add(customerRegistration);
        jPanel32.revalidate();
        jPanel32.repaint();

        jLabel2.setText("Customers");
    }//GEN-LAST:event_jMenu4MouseClicked

    private void jMenu5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu5MouseClicked
        // TODO add your handling code here:
        Invoice_history history = new Invoice_history();
        jPanel32.removeAll();
        jPanel32.add(history);
        jPanel32.revalidate();
        jPanel32.repaint();

        jLabel2.setText("Invoice History");
    }//GEN-LAST:event_jMenu5MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        Customer_registration customerRegistration = new Customer_registration();
        Dialog dialog = new Dialog(this, true);
        customerRegistration.setHome(this);
        dialog.add(customerRegistration);
        customerRegistration.setDiaload(dialog);
        dialog.setTitle("Select Or Register Customers");
        dialog.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        jTextField1.setText("");
        jTextField2.setText("");
        jTextField2.grabFocus();
        jTextField3.setText("Unkown");
        this.qty = "1";
        jTextField6.setText("");
        this.loanbalance = 0;
        calculation2();
        loadProducts();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        String id = jTextField2.getText().trim();

        if (evt.getKeyCode() != KeyEvent.VK_LEFT) {
            setProductName(id);
            if (!setProductDetails(id)) {
                searchScale(id);
            }
            loadProducts();
        }

    }//GEN-LAST:event_jTextField2KeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        CartMap.clear();
        loadCartItems();

        jLabel25.setText("0.00");
        jLabel24.setText("0.00");
        jLabel23.setText("0.00");
        jTextField3.setText("Unkown");
        jTextField4.setText("");
        jTextField5.setText("");
        this.Total = 0;
        this.loanbalance = 0;
        calculation();
        jTextField4.setText("0");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        printInvoice();

    }//GEN-LAST:event_jButton3ActionPerformed

    private void printInvoice() {
        double balance = Double.parseDouble(jLabel25.getText());
        System.out.println(balance);
        boolean check = true;
        if (CartMap.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Products", "Warning", JOptionPane.WARNING_MESSAGE);
            jTextField2.grabFocus();
            check = false;
        } else if (balance < 0) {
            long response = JOptionPane.showConfirmDialog(this, "Your payment is low, will you continue?", "Conformation", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {

                if (jTextField3.getText().equals("Unkown")) {
                    check = false;
                    JOptionPane.showMessageDialog(this, "Please select Customer", "Warning", JOptionPane.WARNING_MESSAGE);

                } else {
                    check = true;

                }

            } else {
                check = false;

            }

        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy");
        LocalDateTime now = LocalDateTime.MAX.now();

        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("MM");
        LocalDateTime now1 = LocalDateTime.MAX.now();

        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd");
        LocalDateTime now2 = LocalDateTime.MAX.now();

        DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("HH");
        LocalDateTime now3 = LocalDateTime.MAX.now();

        DateTimeFormatter dtf4 = DateTimeFormatter.ofPattern("mm");
        LocalDateTime now4 = LocalDateTime.MAX.now();

        DateTimeFormatter dtf5 = DateTimeFormatter.ofPattern("ss");
        LocalDateTime now5 = LocalDateTime.MAX.now();

        DateTimeFormatter dtf6 = DateTimeFormatter.ofPattern("SSS");
        LocalDateTime now6 = LocalDateTime.MAX.now();

        String date = dtf.format(now);
        String date2 = dtf1.format(now1);
        String date3 = dtf2.format(now2);
        String date4 = dtf3.format(now3);
        String date5 = dtf4.format(now4);
        String date6 = dtf5.format(now5);
        String date7 = dtf6.format(now6);

        String date1 = date.substring(3);

        String roundedTime = date1 + date2 + date3 + date4 + date5 + date6 + date7;
//        String roundedTime = curdate.substring(0, curdate.length() - 2);

        String discountValue = jTextField4.getText();
        if (discountValue == null || discountValue.isEmpty()) {
            discountValue = "0.00";
        }

        if (check) {
            try {
                DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime nowd = LocalDateTime.MAX.now();
                String dated = dt.format(nowd);

                String datep = new SimpleDateFormat("yyyy-MM-dd hh:mm a").format(new Date());

                Mysql.execute("INSERT INTO `invoice` (`id`,`date`,`paid_amount`,`customer_registration_id`,`discount`)"
                        + "VALUES('" + roundedTime + "','" + dated + "','" + jTextField5.getText() + "','" + customer_id + "','" + discountValue + "')");

                if (balance >= 0) {
                    balance = 0;
                }

                Mysql.execute("UPDATE customer_registration SET  balance_amount='" + String.valueOf(balance) + "'  WHERE id='" + customer_id + "' ");

                int productCount = 0;

                for (ProductObject product : CartMap.values()) {
                    Mysql.execute("INSERT INTO `invoice_item`  (`products_product_id`,`product_name`,`brand`,`qty`,`buying_price`,`selling_price`,`our_price`,`invoice_id`) "
                            + "VALUES('" + product.getBarcode() + "','"+product.getProduct_name()+"','"+product.getBrand()+"','" + product.getBuyingQuantity() + "','" + product.getBuying_price() + "','"+product.getSelling_price()+"','" + product.getOur_price() + "','" + roundedTime + "')  ");

                    Mysql.execute("UPDATE products SET qty=qty-'" + product.getBuyingQuantity() + "' WHERE product_id='" + product.getBarcode() + "'");

                    productCount++;

                }

                String userDirectory = FileSystems.getDefault().getPath("").toAbsolutePath().toString();
//
                String newpath = userDirectory.substring(0, userDirectory.lastIndexOf("\\"));
//                System.out.println(newpath);
//
                String url = newpath + "\\src\\reports\\WikcramaInvoice.jasper";
//                String url = "src/reports/WikcramaInvoice.jasper";
//
//                //bean collection vector for datasource of the report
                Vector<ProductObject> vector = new Vector<>();

                double totalDiscount = 0;
                //re odering the hashmap according to time - start
                List<Map.Entry<String, ProductObject>> list = new ArrayList<>(CartMap.entrySet());

                // Sort the list based on values using a custom comparator
                Collections.sort(list, (entry1, entry2) -> entry1.getValue().getTimestamp().compareTo(entry2.getValue().getTimestamp()));

                // Create a LinkedHashMap to preserve the order
                Map<String, ProductObject> sortedMap = new LinkedHashMap<>();
                for (Map.Entry<String, ProductObject> entry : list) {
                    sortedMap.put(entry.getKey(), entry.getValue());
                }
                for (Map.Entry<String, ProductObject> entry : sortedMap.entrySet()) {
                    ProductObject item = entry.getValue();
                    totalDiscount += item.getTotalDiscount();
                    vector.add(item);
                }

                totalDiscount += Double.parseDouble(discountValue);

                java.util.HashMap<String, Object> parameters = new HashMap<>();

                parameters.put("Parameter1", datep);
                parameters.put("Parameter2", roundedTime);
                parameters.put("Parameter3", this.user); //user

                parameters.put("Parameter4", jLabel23.getText() + "0"); //total
                parameters.put("Parameter5", jTextField5.getText() + ".00"); //payment amount
                parameters.put("Parameter6", jLabel25.getText() + "0"); //balance
                parameters.put("Parameter7", "Rs:" + String.valueOf(totalDiscount)); //total discount
                parameters.put("Parameter8", String.valueOf(productCount)); // Product Count
//
////           CartMap
                JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(vector);
                JREmptyDataSource s2 = new JREmptyDataSource();
//
                JasperPrint report = JasperFillManager.fillReport(url, parameters, datasource);
                JasperPrintManager.printReport(report, false); //print report dirrectly
//                JasperExportManager.exportReportToPdfFile(report, "report.pdf");

//                JasperViewer.viewReport(report, false);

                Total = 0;
                jLabel25.setText("0.00");
                jLabel24.setText("0.00");
                jTextField5.setText("");
                jTextField1.setText("");
                jTextField4.setText("");
                jTextField2.setText("");
                jTextField3.setText("Unkown");
                customer_id = "1";
                jTextField2.grabFocus();

                CartMap.clear();
                loadCartItems();
                calculation();
                loadProducts();

            } catch (Exception e) {
                logger.log(Level.WARNING, "print Invoice ", e);
                e.printStackTrace();
            }

        }

    }
    private void jTextField2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyTyped
        // TODO add your handling code here:

        String id = jTextField2.getText().trim();
//        productMap.clear();
        if (setProductDetails(id)) {
            evt.consume();
            jTextField6.grabFocus();
        } else {
            searchScale(id);
        }
        loadProducts();

    }//GEN-LAST:event_jTextField2KeyTyped

    private void searchScale(String barcode) {
        String id = barcode;
        if (id.length() == 10 && MyMethods.isNumeric(null, id)) {
            String productID = id.substring(0, 5) + "00000";
            double quantity = Double.parseDouble(id.substring(5)) / 1000;
            System.out.println(productID + " " + quantity);
            jTextField2.setText(productID);
            loadProducts();
            if (productMap.containsKey(productID) && quantity > 0) {
                addToCart(productID, "" + quantity);
                jTextField2.grabFocus();
            } else {
                jTextField2.setText(id);
            }
        }

    }

    private void navigateByKey(java.awt.event.KeyEvent evt, String currentField) {
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                if (currentField.equals("barcode")) {
                    jTextField6.grabFocus();
                } else if (currentField.equals("qty")) {
                    jButton1.grabFocus();
                } else if (currentField.equals("add")) {
                    jTextField4.grabFocus();
                }
                break;
            case KeyEvent.VK_LEFT:
                if (currentField.equals("qty")) {
                    jTextField2.grabFocus();
                } else if (currentField.equals("add")) {
                    jTextField6.grabFocus();
                }
                break;
            case KeyEvent.VK_DOWN:
                if (currentField.equals("barcode")) {
                    jTextField1.grabFocus();
                } else if (currentField.equals("name")) {
                    jTextField4.grabFocus();
                } else {
                    jTextField4.grabFocus();
                }
                break;
            case KeyEvent.VK_UP:
                if (currentField.equals("name")) {
                    jTextField2.grabFocus();
                }

        }
    }
    private void jTextField2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyPressed
        // TODO add your handling code here:
//        if (evt.getKeyCode() == KeyEvent.VK_ENTER && !jTextField2.getText().trim().isEmpty()) {
//            addToCart();
//        }

        navigateByKey(evt, "barcode");
    }//GEN-LAST:event_jTextField2KeyPressed

    private void jButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyPressed
        // TODO add your handling code here:
        navigateByKey(evt, "add");
    }//GEN-LAST:event_jButton1KeyPressed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyPressed
        // TODO add your handling code here:
        int key = evt.getKeyCode();
        if (key == KeyEvent.VK_ENTER || key == KeyEvent.VK_DOWN) {
            jTextField5.grabFocus();
        }

        if (key == KeyEvent.VK_UP) {
            jTextField2.grabFocus();
        }
    }//GEN-LAST:event_jTextField4KeyPressed

    private void jTextField5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyPressed
        // TODO add your handling code here:
        int key = evt.getKeyCode();
        if (key == KeyEvent.VK_UP) {
            jTextField4.grabFocus();
        }

        if (key == KeyEvent.VK_ENTER) {
            printInvoice();
        }
    }//GEN-LAST:event_jTextField5KeyPressed

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
        // TODO add your handling code here:
        calculation();
    }//GEN-LAST:event_jTextField4KeyReleased

    private void jTextField5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyReleased
        // TODO add your handling code here:
        calculation();
    }//GEN-LAST:event_jTextField5KeyReleased

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // TODO add your handling code here:

        if (evt.getKeyCode() != KeyEvent.VK_ESCAPE) {
            MyMethods.loadPopup(jPopupMenu1, jTextField1, loadProducts(), this);
            if (jTextField1.getText().equals("")) {
                jPopupMenu1.setVisible(false);
            }
        } else {
            jPopupMenu1.setVisible(false);
        }

        if (evt.getKeyCode() != KeyEvent.VK_DOWN && evt.getKeyCode() != KeyEvent.VK_UP) {
            jTextField1.grabFocus();
        }

    }//GEN-LAST:event_jTextField1KeyReleased

    private void jMenu3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu3MouseClicked
        // TODO add your handling code here:
        WholeSale wholesale = new WholeSale();
        wholesale.setHome(this);
        jPanel32.removeAll();
        jPanel32.add(wholesale);
        jPanel32.revalidate();
        jPanel32.repaint();

        jLabel2.setText("Whole Sale");
    }//GEN-LAST:event_jMenu3MouseClicked

    private void jMenu6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu6MouseClicked
        // TODO add your handling code here:
        WholeSaleHistory wholesale = new WholeSaleHistory();
        jPanel32.removeAll();
        jPanel32.add(wholesale);
        jPanel32.revalidate();
        jPanel32.repaint();

        jLabel2.setText("Whole Sale history");
    }//GEN-LAST:event_jMenu6MouseClicked

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
//         TODO add your handling code here:
        jTextField2.setText("");
    }//GEN-LAST:event_jTextField1KeyTyped

    private void jMenu7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu7MouseClicked
        // TODO add your handling code here:
        Home home = new Home();

        home.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        home.setTitle("<html>\n"
                + "<span style=\"color: #800080;\">\n"
                + " <b>Sub window</b>\n"
                + "</span>\n"
                + "</html>");

        home.setVisible(true);
        home.setUser(user);
    }//GEN-LAST:event_jMenu7MouseClicked

    private void jTextField2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField2FocusGained
        // TODO add your handling code here:
        jScrollPane1.getVerticalScrollBar().setValue(jScrollPane1.getVerticalScrollBar().getMaximum());
    }//GEN-LAST:event_jTextField2FocusGained

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        // TODO add your handling code here:
        if (!jPopupMenu1.isVisible()) {
            navigateByKey(evt, "name");
        }
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jTextField6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyPressed
        // TODO add your handling code here:

        navigateByKey(evt, "qty");

        if (checkKeyEvent(evt)) {
            if (jTextField6.getText().equals("")) {
                this.qty = String.valueOf(evt.getKeyChar());
            } else {
                this.qty += String.valueOf(evt.getKeyChar());
            }

        }

        switch (evt.getKeyCode()) {
            case KeyEvent.VK_DELETE:
                this.qty = "";
                break;
            case KeyEvent.VK_BACK_SPACE:
                if (this.qty.length() > 0) {
                    this.qty = this.qty.substring(0, this.qty.length() - 1);
                }
                break;
            case KeyEvent.VK_ADD:
                this.qty = String.valueOf(Double.parseDouble(qty) + 1);
                break;
            case KeyEvent.VK_SUBTRACT:
                if (Double.parseDouble(qty) > 1) {
                    this.qty = String.valueOf(Double.parseDouble(qty) - 1);
                }
                break;
            case KeyEvent.VK_MINUS:
                if (Double.parseDouble(qty) > 1) {
                    this.qty = String.valueOf(Double.parseDouble(qty) - 1);
                }
        }

        jTextField6.setText(this.qty);
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            addToCart(jTextField2.getText().trim(), jTextField6.getText().trim());
            jTextField2.grabFocus();
            loadProducts();
        }

    }//GEN-LAST:event_jTextField6KeyPressed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jTextField6ActionPerformed

    public void loadCartItems() {
//        double total = 0; //to keep the gross total

        if (!CartMap.isEmpty()) {
            int itemNumber = 1;
            jPanel22.removeAll();
            Total = 0;

            List<Map.Entry<String, ProductObject>> list = new ArrayList<>(CartMap.entrySet());

            // Sort the list based on values using a custom comparator
            Collections.sort(list, (entry1, entry2) -> entry1.getValue().getTimestamp().compareTo(entry2.getValue().getTimestamp()));

            // Create a LinkedHashMap to preserve the order
            Map<String, ProductObject> sortedMap = new LinkedHashMap<>();
            for (Map.Entry<String, ProductObject> entry : list) {
                sortedMap.put(entry.getKey(), entry.getValue());
            }
            for (Map.Entry<String, ProductObject> entry : sortedMap.entrySet()) {
                ProductObject product = entry.getValue();
                product.setItemNumber(String.valueOf(itemNumber));
                CartItem item = new CartItem();
                item.setItemNumber(String.valueOf(itemNumber));
                item.setProductName(product.getProduct_name());
                item.setQuantity(product.getBuyingQuantity());
                item.setBrand(product.getBrand());
                item.setUnitPrice(String.valueOf(product.getOur_price()));
                item.setTotal(String.valueOf(product.getItemTotal()));
                item.setHome(this);
                item.setBarcode(product.getBarcode());
                jPanel22.add(item);
                itemNumber++;
                Total += product.getItemTotal();
            }
        } else {
            jPanel22.removeAll();
            Total = 0;
        }

        jLabel18.setText("ITEMS (" + String.valueOf(CartMap.size()) + ")"); //setting the number of items in the cart
        jLabel23.setText(String.valueOf(Total)); //setting the gross total for the items in the cart

        jPanel22.revalidate();
        jPanel22.repaint();
    }

    /**
     * @param args the command line arguments
     */
    private void runClock() {
        Thread thred1 = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        jLabel3.setText(new SimpleDateFormat("hh:mm:ss a").format(new Date()));
                        Thread.sleep(1000);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        thred1.start();
    }

    private String qty = "1";

    private boolean checkKeyEvent(KeyEvent evt) {

        switch (evt.getKeyChar()) {
            case '0':
                return true;
            case '1':
                return true;
            case '2':
                return true;
            case '3':
                return true;
            case '4':
                return true;
            case '5':
                return true;
            case '6':
                return true;
            case '7':
                return true;
            case '8':
                return true;
            case '9':
                return true;
            case '.':
                return true;
            default:
                return false;
        }
    }

    public static void main(String args[]) {
        FlatMacLightLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenuBar jMenuBar1;
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
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}
