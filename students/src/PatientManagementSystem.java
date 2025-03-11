import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class PatientManagementSystem extends JFrame {
    // Declare components as instance variables for access in event listeners
    private JTextField patientNameField, patientIdField, daysAdmittedField, totalBillField;
    private JComboBox<String> diseaseTypeComboBox;
    private JCheckBox healthInsuranceCheckBox;

    public PatientManagementSystem() {
        setTitle("Patient Management System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(255, 182, 193));

        // Labels with darker pink foreground
        JLabel patientNameLabel = new JLabel("Patient Name:");
        patientNameLabel.setBounds(50, 50, 150, 25);
        patientNameLabel.setForeground(new Color(199, 21, 133));
        JLabel patientIdLabel = new JLabel("Patient ID:");
        patientIdLabel.setBounds(50, 80, 150, 25);
        patientIdLabel.setForeground(new Color(199, 21, 133));
        JLabel diseaseTypeLabel = new JLabel("Disease Type:");
        diseaseTypeLabel.setBounds(50, 110, 150, 25);
        diseaseTypeLabel.setForeground(new Color(199, 21, 133));
        JLabel daysAdmittedLabel = new JLabel("Days Admitted:");
        daysAdmittedLabel.setBounds(50, 140, 150, 25);
        daysAdmittedLabel.setForeground(new Color(199, 21, 133));
        JLabel healthInsuranceLabel = new JLabel("I have Health Insurance:");
        healthInsuranceLabel.setBounds(50, 170, 150, 25);
        healthInsuranceLabel.setForeground(new Color(199, 21, 133));
        JLabel totalBillLabel = new JLabel("Total Bill:");
        totalBillLabel.setBounds(50, 200, 150, 25);
        totalBillLabel.setForeground(new Color(199, 21, 133));

        // Text Fields
        patientNameField = new JTextField();
        patientNameField.setBounds(250, 50, 200, 25);
        patientIdField = new JTextField();
        patientIdField.setBounds(250, 80, 200, 25);
        daysAdmittedField = new JTextField();
        daysAdmittedField.setBounds(250, 140, 200, 25);
        totalBillField = new JTextField();
        totalBillField.setBounds(250, 200, 200, 25);
        totalBillField.setEditable(false);
        // Combo Box for Disease Type
        diseaseTypeComboBox = new JComboBox<>(new String[]{"General Checkup", "Surgery", "Maternity"});
        diseaseTypeComboBox.setBounds(250, 110, 200, 25);

        // Checkbox for Health Insurance
        healthInsuranceCheckBox = new JCheckBox();
        healthInsuranceCheckBox.setBounds(250, 170, 20, 25);
        healthInsuranceCheckBox.setBackground(new Color(255, 182, 193)); // Light pink background

        // Submit Button
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(250, 270, 100, 30);
        submitButton.setBackground(new Color(199, 21, 133)); // Darker pink background
        submitButton.setForeground(Color.WHITE); // White text

        // Add components to the JFrame
        add(patientNameLabel);
        add(patientNameField);
        add(patientIdLabel);
        add(patientIdField);
        add(diseaseTypeLabel);
        add(diseaseTypeComboBox);
        add(daysAdmittedLabel);
        add(daysAdmittedField);
        add(healthInsuranceLabel);
        add(healthInsuranceCheckBox);
        add(totalBillLabel);
        add(totalBillField);
        add(submitButton);

        // Event Listener for Disease Type and Days Admitted
        diseaseTypeComboBox.addActionListener(e -> calculateTotalBill());
        daysAdmittedField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                calculateTotalBill();
            }
        });
        healthInsuranceCheckBox.addActionListener(e -> calculateTotalBill());

        // Event Listener for Submit Button
        submitButton.addActionListener(e -> {
            String patientName = patientNameField.getText();
            String patientId = patientIdField.getText();
            String diseaseType = (String) diseaseTypeComboBox.getSelectedItem();
            String daysAdmitted = daysAdmittedField.getText();
            boolean hasHealthInsurance = healthInsuranceCheckBox.isSelected();
            String totalBill = totalBillField.getText();

            // Validate input
            if (!validateInput(patientName, patientId, daysAdmitted)) {
                return; // Stop if validation fails
            }

            // Save data to the database
            saveToDatabase(patientName, patientId, diseaseType, daysAdmitted, hasHealthInsurance, totalBill);
        });

        setVisible(true);
    }

    // Method to calculate Total Bill
    private void calculateTotalBill() {
        try {
            String diseaseType = (String) diseaseTypeComboBox.getSelectedItem();
            int daysAdmitted = Integer.parseInt(daysAdmittedField.getText());
            boolean hasHealthInsurance = healthInsuranceCheckBox.isSelected();

            double treatmentCost = 0;
            switch (diseaseType) {
                case "General Checkup":
                    treatmentCost = 100;
                    break;
                case "Surgery":
                    treatmentCost = 3000;
                    break;
                case "Maternity":
                    treatmentCost = 2000;
                    break;
            }

            // Add $50 per extra day if daysAdmitted > 1
            if (daysAdmitted > 1) {
                treatmentCost += (daysAdmitted - 1) * 50;
            }

            // Apply 30% discount if health insurance is checked
            if (hasHealthInsurance) {
                treatmentCost *= 0.7;
            }

            totalBillField.setText(String.valueOf(treatmentCost));
        } catch (NumberFormatException ex) {
            totalBillField.setText("");
        }
    }

    // Method to validate input
    private boolean validateInput(String patientName, String patientId, String daysAdmitted) {
        // Validate Patient Name
        if (!Pattern.matches("[a-zA-Z ]+", patientName)) {
            JOptionPane.showMessageDialog(this, "Patient Name must contain only alphabetic characters and spaces.");
            return false;
        }

        // Validate Patient ID
        if (patientId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Patient ID cannot be empty.");
            return false;
        }

        // Validate Days Admitted
        try {
            int days = Integer.parseInt(daysAdmitted);
            if (days <= 0) {
                JOptionPane.showMessageDialog(this, "Days Admitted must be a positive integer.");
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Days Admitted must be a valid integer.");
            return false;
        }

        return true; // All validations passed
    }

    private void saveToDatabase(String patientName, String patientId, String diseaseType, String daysAdmitted, boolean hasHealthInsurance, String totalBill) {

        String url = "jdbc:mysql://localhost:3306/students";
        String user = "root";
        String password = "California123!";

        String query = "INSERT INTO patients (patient_name, patient_id, disease_type, days_admitted, has_health_insurance, total_bill) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Set parameters for the query
            pstmt.setString(1, patientName);
            pstmt.setString(2, patientId);
            pstmt.setString(3, diseaseType);
            pstmt.setInt(4, Integer.parseInt(daysAdmitted));
            pstmt.setBoolean(5, hasHealthInsurance);
            pstmt.setDouble(6, Double.parseDouble(totalBill));

            // Execute the query
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Patient data saved successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving patient data: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new PatientManagementSystem();
    }
}