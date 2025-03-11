import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OnlineShoppingCart extends JFrame {
    // Declare components as instance variables for access in event listeners
    private JTextField customerNameField, productNameField, priceField, quantityField, totalAmountField;
    private JComboBox<String> productCategoryComboBox;

    public OnlineShoppingCart() {
        setTitle("Online Shopping Cart");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // Use absolute positioning

        // Labels
        JLabel customerNameLabel = new JLabel("Customer Name:");
        customerNameLabel.setBounds(50, 50, 150, 25);
        JLabel productCategoryLabel = new JLabel("Product Category:");
        productCategoryLabel.setBounds(50, 80, 150, 25);
        JLabel productNameLabel = new JLabel("Product Name:");
        productNameLabel.setBounds(50, 110, 150, 25);
        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(50, 140, 150, 25);
        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setBounds(50, 170, 150, 25);
        JLabel totalAmountLabel = new JLabel("Total Amount:");
        totalAmountLabel.setBounds(50, 200, 150, 25);

        // Text Fields
        customerNameField = new JTextField();
        customerNameField.setBounds(250, 50, 200, 25);
        productNameField = new JTextField();
        productNameField.setBounds(250, 110, 200, 25);
        priceField = new JTextField();
        priceField.setBounds(250, 140, 200, 25);
        quantityField = new JTextField();
        quantityField.setBounds(250, 170, 200, 25);
        totalAmountField = new JTextField();
        totalAmountField.setBounds(250, 200, 200, 25);
        totalAmountField.setEditable(false); // Total Amount field should not be editable

        // Combo Box for Product Category
        productCategoryComboBox = new JComboBox<>(new String[]{"Electronics", "Clothing", "Groceries"});
        productCategoryComboBox.setBounds(250, 80, 200, 25);

        // Submit Button
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(250, 270, 100, 30);

        // Add components to the JFrame
        add(customerNameLabel);
        add(customerNameField);
        add(productCategoryLabel);
        add(productCategoryComboBox);
        add(productNameLabel);
        add(productNameField);
        add(priceLabel);
        add(priceField);
        add(quantityLabel);
        add(quantityField);
        add(totalAmountLabel);
        add(totalAmountField);
        add(submitButton);

        // Event Listener for Quantity Field
        quantityField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                calculateTotalAmount();
            }
        });

        // Event Listener for Submit Button
        submitButton.addActionListener(e -> {
            String customerName = customerNameField.getText();
            String productCategory = (String) productCategoryComboBox.getSelectedItem();
            String productName = productNameField.getText();
            String price = priceField.getText();
            String quantity = quantityField.getText();
            String totalAmount = totalAmountField.getText();

            if (customerName.isEmpty() || productName.isEmpty() || price.isEmpty() || quantity.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
                return;
            }

            // Save order to the database
            saveOrderToDatabase(customerName, productCategory, productName, price, quantity, totalAmount);
        });

        setVisible(true);
    }

    // Method to calculate Total Amount
    private void calculateTotalAmount() {
        try {
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            double totalAmount = price * quantity;
            totalAmountField.setText(String.valueOf(totalAmount));
        } catch (NumberFormatException ex) {
            totalAmountField.setText(""); // Clear if input is invalid
        }
    }

    // Method to save order to the database
    private void saveOrderToDatabase(String customerName, String productCategory, String productName, String price, String quantity, String totalAmount) {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/students"; // Update with your database name
        String user = "root"; // Update with your database username
        String password = "California123!"; // Update with your database password

        // SQL query to insert data
        String query = "INSERT INTO orders (customer_name, product_category, product_name, price, quantity, total_amount) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Set parameters for the query
            pstmt.setString(1, customerName);
            pstmt.setString(2, productCategory);
            pstmt.setString(3, productName);
            pstmt.setDouble(4, Double.parseDouble(price));
            pstmt.setInt(5, Integer.parseInt(quantity));
            pstmt.setDouble(6, Double.parseDouble(totalAmount));

            // Execute the query
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