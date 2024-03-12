/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import gui.Home;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Dell
 */
public class MyMethods {

    //upload your image file to a desired dirrectory
    public static void copyUploadedImage(File sourceFile, String copyToPath) {

        File targetDirectory = new File(copyToPath); // Replace with your target directory path eg - "F:\\GitHubNew\\POS_test2\\src\\product_images"

        // Check if the source file exists
        if (sourceFile.exists()) {
            // Create the target directory if it doesn't exist
            if (!targetDirectory.exists()) {
                targetDirectory.mkdirs();
            }

            try {
                // Construct the target file path
                Path targetPath = targetDirectory.toPath().resolve(sourceFile.getName());

                // Move the file to the new location
                Files.copy(sourceFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);

                System.out.println("File moved successfully to: " + targetPath.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Source file does not exist.");
        }
    }

    //image chooser
    public static File chooseImage() {
        //Create a file chooser
        final JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.image", "jpg", "gif", "png", "jpeg");
        fc.addChoosableFileFilter(filter);

        //In response to a button click:
        int returnVal = fc.showOpenDialog(new Home());

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            return file;
        } else {
            return null;
        }
    }

    //image setter
    public static ImageIcon resizeImage(int width, int height, String path) {
        ImageIcon image = null;
        byte[] pic = null;

        if (path != null) {
            image = new ImageIcon(path);
        } else {
            image = new ImageIcon(pic);
        }

        Image img = image.getImage();
        Image adjestedImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(adjestedImage);
        return icon;
    }

  

    //loadpopupmenu on search
    public static void loadPopup(JPopupMenu popupMenu, JTextField textField, HashMap<String, String> list, Home home) {
        popupMenu.setVisible(false);
        popupMenu.removeAll();

        for (String name : list.keySet()) {
            JMenuItem jMenuItem = new javax.swing.JMenuItem();
            jMenuItem.setFont(new java.awt.Font("Iskoola Pota", 0, 16));
            jMenuItem.setText(name);

            jMenuItem.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    textField.setText(jMenuItem.getText());
                    if (home != null) {
                        home.setBarcode(list.get(name));
                        home.loadProducts();
                        home.focusQuantity();
                    }
                }
            });

            popupMenu.add(jMenuItem);
        }
        popupMenu.show(textField, 0, textField.getHeight());
        
    }

    //validation for emptyfields
    public static boolean isInputEmpty(JFrame frame, JTextField field, boolean warn) {
        if (field.getText().trim().isEmpty()) {
            if (warn) {
                JOptionPane.showMessageDialog(frame, "Please enter " + field.getAccessibleContext().getAccessibleName(), "Warning", JOptionPane.WARNING_MESSAGE);
            }

            return true;
        } else {
            return false;
        }
    }

    //validation for numerical values
    public static boolean isNumeric(JFrame frame, String value) {
        if (value.matches("\\d+(\\.\\d+)?")) {
            return true;
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid value", "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

    //extract numbers
    public static String extractNumbers(String input) {
        
        boolean validate = true;

        if (countChar(input, '.') == 1) {
            for (int i = 0; i < input.length(); i++) {
                if (input.charAt(i) == '.') {
                    if (i == (input.length() - 1)) {
                        validate = false;
                    } else {
                        validate = true;
                    }
                }
            }
        }

        if (validate) {
            // Define a regular expression pattern to match digits (0-9)
            Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");

            // Create a Matcher object to find the matches
            Matcher matcher = pattern.matcher(input);

            StringBuilder result = new StringBuilder();

            // Find and append all matches to the result
            while (matcher.find()) {
                result.append(matcher.group());
            }

            return result.toString();
        }
        return input;
    }

    //count specific character
    public static int countChar(String text, char c) {
        int count = 0;

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == c) {
                count++;
            }
        }

        return count;
    }
    
    //styleUI
    public static void styleUI(){
        UIManager.put( "TextComponent.arc", 10 );
        UIManager.put( "ScrollBar.showButtons", true );
        
        UIManager.put( "Table.rowHeight", 40 );
        UIManager.put( "Table.showHorizontalLines", true );
        UIManager.put( "Table.showVerticalLines", true );
        UIManager.put( "Table.font", new java.awt.Font("Iskoola Pota", 0, 16) );
        UIManager.put( "Table.cellMargins", new java.awt.Insets(2, 10, 2, 6));
        UIManager.put( "Table.intercellSpacing", new java.awt.Dimension(2, 0));
        UIManager.put( "Table.alternateRowColor",new Color(232, 239, 249));
        UIManager.put( "Table.selectionBackground",new Color(204,204,204));
        UIManager.put( "Table.selectionForeground",Color.BLACK);
        
        UIManager.put( "TableHeader.font",new java.awt.Font("Segoe UI Semibold", 0, 18));
        UIManager.put( "TableHeader.background",new Color(232, 239, 249));
    }
    
    
}
