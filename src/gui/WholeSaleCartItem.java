/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import java.awt.event.KeyEvent;

/**
 *
 * @author Dell
 */
public class WholeSaleCartItem extends javax.swing.JPanel {

    private WholeSale sale;

    public void setSale(WholeSale sale) {
        this.sale = sale;
    }
    /**
     * Creates new form WholeSaleCartItem
     */
    public WholeSaleCartItem() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 5, 0));
        setMaximumSize(new java.awt.Dimension(32767, 56));
        setPreferredSize(new java.awt.Dimension(1069, 56));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        jPanel3.setBackground(new java.awt.Color(239, 242, 247));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 242, 247)));
        jPanel3.setPreferredSize(new java.awt.Dimension(1069, 50));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel3MouseExited(evt);
            }
        });
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel6.setOpaque(false);
        jPanel6.setPreferredSize(new java.awt.Dimension(50, 50));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete-25.png"))); // NOI18N
        jButton1.setMnemonic('\n');
        jButton1.setBorderPainted(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton1, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel6, java.awt.BorderLayout.LINE_END);

        jLabel1.setFont(new java.awt.Font("Iskoola Pota", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("#");
        jLabel1.setPreferredSize(new java.awt.Dimension(50, 16));
        jPanel3.add(jLabel1, java.awt.BorderLayout.LINE_START);

        jPanel5.setOpaque(false);
        jPanel5.setLayout(new java.awt.BorderLayout());

        jLabel2.setFont(new java.awt.Font("Iskoola Pota", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("ITEM");
        jPanel5.add(jLabel2, java.awt.BorderLayout.CENTER);

        jPanel7.setOpaque(false);
        jPanel7.setPreferredSize(new java.awt.Dimension(500, 50));
        jPanel7.setLayout(new java.awt.GridLayout(1, 3));

        jLabel3.setFont(new java.awt.Font("Iskoola Pota", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("QUANTITY");
        jPanel7.add(jLabel3);

        jLabel4.setFont(new java.awt.Font("Iskoola Pota", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("SELLING PRICE");
        jPanel7.add(jLabel4);

        jLabel5.setFont(new java.awt.Font("Iskoola Pota", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("TOTAL");
        jPanel7.add(jLabel5);

        jPanel5.add(jPanel7, java.awt.BorderLayout.LINE_END);

        jPanel3.add(jPanel5, java.awt.BorderLayout.CENTER);

        add(jPanel3);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        sale.removeItem(jLabel2.getText());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jPanel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseEntered
        // TODO add your handling code here:
        jPanel3.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,153,255), 1), javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0)));
    }//GEN-LAST:event_jPanel3MouseEntered

    private void jPanel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseExited
        // TODO add your handling code here:
        jPanel3.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239,242,247), 1), javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0)));
    }//GEN-LAST:event_jPanel3MouseExited

    
    public void setItemNumber(String number){
        jLabel1.setText(number);
    }
    public void setProductName(String name){
        jLabel2.setText(name);
    }
    public void setQuantity(String qty){
        jLabel3.setText(qty);
    }
    public void setPrice(String price){
        jLabel4.setText(price);
    }
    public void setTotal(String total){
        jLabel5.setText(total);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    // End of variables declaration//GEN-END:variables
}