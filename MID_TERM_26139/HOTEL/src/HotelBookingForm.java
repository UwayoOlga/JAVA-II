import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class HotelBookingForm extends JFrame {

    private JTextField clientIdField, firstNameField, lastNameField, mobilePhoneField, roomRateField,
            checkInDateField, checkOutDateField, numberOfNightsField, taxField, discountField, totalPriceField;
    private JComboBox<String> roomTypeComboBox;

    public HotelBookingForm() {
        setTitle("Hotel Booking Form");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel clientIdLabel = new JLabel("Client ID:");
        clientIdLabel.setBounds(50, 50, 150, 25);
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setBounds(50, 80, 150, 25);
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setBounds(50, 110, 150, 25);
        JLabel mobilePhoneLabel = new JLabel("Mobile Phone:");
        mobilePhoneLabel.setBounds(50, 140, 150, 25);
        JLabel roomTypeLabel = new JLabel("Room Type:");
        roomTypeLabel.setBounds(50, 170, 150, 25);
        JLabel roomRateLabel = new JLabel("Room Rate:");
        roomRateLabel.setBounds(50, 200, 150, 25);
        JLabel checkInDateLabel = new JLabel("Check-In Date (YYYY-MM-DD):");
        checkInDateLabel.setBounds(50, 230, 150, 25);
        JLabel checkOutDateLabel = new JLabel("Check-Out Date (YYYY-MM-DD):");
        checkOutDateLabel.setBounds(50, 260, 150, 25);
        JLabel numberOfNightsLabel = new JLabel("Number of Nights:");
        numberOfNightsLabel.setBounds(50, 290, 150, 25);
        JLabel taxLabel = new JLabel("Tax:");
        taxLabel.setBounds(50, 320, 150, 25);
        JLabel discountLabel = new JLabel("Discount:");
        discountLabel.setBounds(50, 350, 150, 25);
        JLabel totalPriceLabel = new JLabel("Total Price:");
        totalPriceLabel.setBounds(50, 380, 150, 25);

        clientIdField = new JTextField();
        clientIdField.setBounds(250, 50, 200, 25);
        firstNameField = new JTextField();
        firstNameField.setBounds(250, 80, 200, 25);
        lastNameField = new JTextField();
        lastNameField.setBounds(250, 110, 200, 25);
        mobilePhoneField = new JTextField();
        mobilePhoneField.setBounds(250, 140, 200, 25);
        roomRateField = new JTextField();
        roomRateField.setBounds(250, 200, 200, 25);
        roomRateField.setEditable(false);
        checkInDateField = new JTextField();
        checkInDateField.setBounds(250, 230, 200, 25);
        checkOutDateField = new JTextField();
        checkOutDateField.setBounds(250, 260, 200, 25);
        numberOfNightsField = new JTextField();
        numberOfNightsField.setBounds(250, 290, 200, 25);
        numberOfNightsField.setEditable(false);
        taxField = new JTextField();
        taxField.setBounds(250, 320, 200, 25);
        taxField.setEditable(false);
        discountField = new JTextField();
        discountField.setBounds(250, 350, 200, 25);
        discountField.setEditable(false);
        totalPriceField = new JTextField();
        totalPriceField.setBounds(250, 380, 200, 25);
        totalPriceField.setEditable(false);

        roomTypeComboBox = new JComboBox<>(new String[]{"Single", "Double", "Suite"});
        roomTypeComboBox.setBounds(250, 170, 200, 25);

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(250, 420, 100, 30);
        saveButton.setForeground(Color.black);

        add(clientIdLabel);
        add(clientIdField);
        add(firstNameLabel);
        add(firstNameField);
        add(lastNameLabel);
        add(lastNameField);
        add(mobilePhoneLabel);
        add(mobilePhoneField);
        add(roomTypeLabel);
        add(roomTypeComboBox);
        add(roomRateLabel);
        add(roomRateField);
        add(checkInDateLabel);
        add(checkInDateField);
        add(checkOutDateLabel);
        add(checkOutDateField);
        add(numberOfNightsLabel);
        add(numberOfNightsField);
        add(taxLabel);
        add(taxField);
        add(discountLabel);
        add(discountField);
        add(totalPriceLabel);
        add(totalPriceField);
        add(saveButton);

        // Event Listener for Room Type Combo Box
        roomTypeComboBox.addActionListener(e -> calculateRoomRate());

        // Event Listener for Check-Out Date Field
        checkOutDateField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                calculateNumberOfNights();
                calculateTaxDiscountAndTotalPrice();
            }
        });

        // Event Listener for Save Button
        saveButton.addActionListener(e -> {
            String clientId = clientIdField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String mobilePhone = mobilePhoneField.getText();
            String roomType = (String) roomTypeComboBox.getSelectedItem();
            String roomRate = roomRateField.getText();
            String checkInDate = checkInDateField.getText();
            String checkOutDate = checkOutDateField.getText();
            String numberOfNights = numberOfNightsField.getText();
            String tax = taxField.getText();
            String discount = discountField.getText();
            String totalPrice = totalPriceField.getText();

            // Validate input
            if (!validateInput(clientId, firstName, lastName, mobilePhone, checkInDate, checkOutDate)) {
                return; // Stop if validation fails
            }

            // Save data to the database
            saveToDatabase(clientId, firstName, lastName, mobilePhone, roomType, roomRate, checkInDate, checkOutDate, numberOfNights, tax, discount, totalPrice);
        });

        setVisible(true);
    }

    // Method to calculate Room Rate
    private void calculateRoomRate() {
        String roomType = (String) roomTypeComboBox.getSelectedItem();
        double roomRate = 0;
        switch (roomType) {
            case "Single":
                roomRate = 70000;
                break;
            case "Double":
                roomRate = 120000;
                break;
            case "Suite":
                roomRate = 190000;
                break;
        }
        roomRateField.setText(String.valueOf(roomRate));
    }

    // Method to calculate Number of Nights
    private void calculateNumberOfNights() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date checkInDate = dateFormat.parse(checkInDateField.getText());
            Date checkOutDate = dateFormat.parse(checkOutDateField.getText());

            long difference = checkOutDate.getTime() - checkInDate.getTime();
            int numberOfNights = (int) (difference / (1000 * 60 * 60 * 24));

            numberOfNightsField.setText(String.valueOf(numberOfNights));
        } catch (ParseException ex) {
            numberOfNightsField.setText("");
        }
    }

    // Method to calculate Tax, Discount, and Total Price
    private void calculateTaxDiscountAndTotalPrice() {
        try {
            double roomRate = Double.parseDouble(roomRateField.getText());
            int numberOfNights = Integer.parseInt(numberOfNightsField.getText());

            double tax = (roomRate * numberOfNights * 5) / 100;
            double discount = (numberOfNights >= 7) ? (roomRate * numberOfNights * 10) / 100 : 0;
            double totalPrice = (roomRate * numberOfNights) + tax - discount;

            taxField.setText(String.valueOf(tax));
            discountField.setText(String.valueOf(discount));
            totalPriceField.setText(String.valueOf(totalPrice));
        } catch (NumberFormatException ex) {
            taxField.setText("");
            discountField.setText("");
            totalPriceField.setText("");
        }
    }

    // Method to validate input
    private boolean validateInput(String clientId, String firstName, String lastName, String mobilePhone, String checkInDate, String checkOutDate) {
        // Validate Client ID
        if (!Pattern.matches("\\d+", clientId)) {
            JOptionPane.showMessageDialog(this, "Client ID must be a number.");
            return false;
        }

        // Validate First Name and Last Name
        if (!Pattern.matches("[a-zA-Z ]+", firstName) || !Pattern.matches("[a-zA-Z ]+", lastName)) {
            JOptionPane.showMessageDialog(this, "First Name and Last Name must contain only alphabetic characters and spaces.");
            return false;
        }

        // Validate Mobile Phone
        if (!Pattern.matches("07\\d{8}", mobilePhone)) {
            JOptionPane.showMessageDialog(this, "Mobile Phone must start with '07' and have 10 digits.");
            return false;
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            dateFormat.parse(checkInDate);
            dateFormat.parse(checkOutDate);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Check-In and Check-Out Dates must be in the format YYYY-MM-DD.");
            return false;
        }

        return true;
    }

    private void saveToDatabase(String clientId, String firstName, String lastName, String mobilePhone, String roomType, String roomRate, String checkInDate, String checkOutDate, String numberOfNights, String tax, String discount, String totalPrice) {

        String url = "jdbc:mysql://localhost:3306/booking_db";
        String user = "root";
        String password = "California123!";

        String query = "INSERT INTO booking_data (client_id, first_name, last_name, mobile_phone, room_type, room_rate, check_in_date, check_out_date, number_of_nights, tax, discount, total_price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, Integer.parseInt(clientId));
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, mobilePhone);
            pstmt.setString(5, roomType);
            pstmt.setDouble(6, Double.parseDouble(roomRate));
            pstmt.setString(7, checkInDate);
            pstmt.setString(8, checkOutDate);
            pstmt.setInt(9, Integer.parseInt(numberOfNights));
            pstmt.setDouble(10, Double.parseDouble(tax));
            pstmt.setDouble(11, Double.parseDouble(discount));
            pstmt.setDouble(12, Double.parseDouble(totalPrice));

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Booking data saved successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving booking data: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new HotelBookingForm();
    }
}