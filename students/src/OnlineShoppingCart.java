import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class OnlineShoppingCart extends JFrame {
     
    private JTextField customerNameField, product, priceField, quantityField, totalAmountField;
    private JComboBox<String> productCategoryComboBox;
 
    public OnlineShoppingCart() {
        setTitle("Online Shopping Cart");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(255, 182, 193));
 
        JLabel customerNameLabel = new JLabel("Customer Name:");
        customerNameLabel.setBounds(50, 50, 150, 25);
        customerNameLabel.setForeground(new Color(199, 21, 133));
        JLabel productCategoryLabel = new JLabel("Product Category:");
        productCategoryLabel.setBounds(50, 80, 150, 25);
        productCategoryLabel.setForeground(new Color(199, 21, 133));
        JLabel productNameLabel = new JLabel("Product Name:");
        productNameLabel.setBounds(50, 110, 150, 25);
        productNameLabel.setForeground(new Color(199, 21, 133));
        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(50, 140, 150, 25);
        priceLabel.setForeground(new Color(199, 21, 133));
        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setBounds(50, 170, 150, 25);
        quantityLabel.setForeground(new Color(199, 21, 133));
        JLabel totalAmountLabel = new JLabel("Total Amount:");
        totalAmountLabel.setBounds(50, 200, 150, 25);
        totalAmountLabel.setForeground(new Color(199, 21, 133));
 
        customerNameField = new JTextField();
        customerNameField.setBounds(250, 50, 200, 25);
        product = new JTextField();
        product.setBounds(250, 110, 200, 25);
        priceField = new JTextField();
        priceField.setBounds(250, 140, 200, 25);
        quantityField = new JTextField();
        quantityField.setBounds(250, 170, 200, 25);
        totalAmountField = new JTextField();
        totalAmountField.setBounds(250, 200, 200, 25);
        totalAmountField.setEditable(false);
 
        productCategoryComboBox = new JComboBox<>(new String[]{"Electronics", "Clothing", "Groceries"});
        productCategoryComboBox.setBounds(250, 80, 200, 25);
 
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(250, 270, 100, 30);
        submitButton.setBackground(new Color(199, 21, 133));
        submitButton.setForeground(Color.WHITE);
 
        add(customerNameLabel);
        add(customerNameField);
        add(productCategoryLabel);
        add(productCategoryComboBox);
        add(productNameLabel);
        add(product);
        add(priceLabel);
        add(priceField);
        add(quantityLabel);
        add(quantityField);
        add(totalAmountLabel);
        add(totalAmountField);
        add(submitButton);
 
        quantityField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                calculateTotalAmount();
            }
        });
 
        submitButton.addActionListener(e -> {
            String customerName = customerNameField.getText();
            String productCategory = (String) productCategoryComboBox.getSelectedItem();
            String productName = product.getText();
            String price = priceField.getText();
            String quantity = quantityField.getText();
            String totalAmount = totalAmountField.getText();
 
            if (!validateInput(customerName, productName, price, quantity)) {
                return;
            }
 
            saveOrderToDatabase(customerName, productCategory, productName, price, quantity, totalAmount);
        });

        setVisible(true);
    }
 
    private void calculateTotalAmount() {
        try {
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            double totalAmount = price * quantity;
            totalAmountField.setText(String.valueOf(totalAmount));
        } catch (NumberFormatException ex) {
            totalAmountField.setText(""); 
        }
    }
 
    private boolean validateInput(String customerName, String productName, String price, String quantity) {
       
        if (!Pattern.matches("[a-zA-Z ]+", customerName)) {
            JOptionPane.showMessageDialog(this, "Customer Name must contain only alphabetic characters and spaces.");
            return false;
        }
 
        if (productName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Product Name cannot be empty.");
            return false;
        }
 
        try {
            double priceValue = Double.parseDouble(price);
            if (priceValue <= 0) {
                JOptionPane.showMessageDialog(this, "Price must be a positive number.");
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Price must be a valid number.");
            return false;
        }
 
        try {
            int quantityValue = Integer.parseInt(quantity);
            if (quantityValue <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity must be a positive integer.");
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantity must be a valid integer.");
            return false;
        }

        return true;  
    }
 
    private void saveOrderToDatabase(String customerName, String productCategory, String productName, String price, String quantity, String totalAmount) {
        
        String url = "jdbc:mysql://localhost:3306/students";
        String user = "root";
        String password = "California123!";
 
        String query = "INSERT INTO orders (customer_name, product_category, product_name, price, quantity, total_amount) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
 
            pstmt.setString(1, customerName);
            pstmt.setString(2, productCategory);
            pstmt.setString(3, productName);
            pstmt.setDouble(4, Double.parseDouble(price));
            pstmt.setInt(5, Integer.parseInt(quantity));
            pstmt.setDouble(6, Double.parseDouble(totalAmount));
 
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Order saved successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving order: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new OnlineShoppingCart();
    }
}
