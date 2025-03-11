import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class registrationform extends JFrame {
    // UI Components
    private JTextField firstNameField, lastNameField, dobField, feesField;
    private JCheckBox maleCheckBox, femaleCheckBox, termsCheckBox;
    private JComboBox<String> courseComboBox;
    private JButton saveButton;

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/student_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    public registrationform() {
        // Set up the JFrame
        setTitle("Student Registration System");
        setSize(500, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 2));

        // Initialize components
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        dobField = new JTextField();
        feesField = new JTextField();
        feesField.setEditable(false); // Fees field should not be editable
        maleCheckBox = new JCheckBox("Male");
        femaleCheckBox = new JCheckBox("Female");
        termsCheckBox = new JCheckBox("Accept Terms and Conditions");
        courseComboBox = new JComboBox<>(new String[]{"Java", "Python", "C++"});
        saveButton = new JButton("Save");

        // Add components to the JFrame
        add(new JLabel("First Name:"));
        add(firstNameField);
        add(new JLabel("Last Name:"));
        add(lastNameField);
        add(new JLabel("Date of Birth (YYYY-MM-DD):"));
        add(dobField);
        add(new JLabel("Gender:"));
        add(maleCheckBox);
        add(new JLabel(""));
        add(femaleCheckBox);
        add(new JLabel("Selected Course:"));
        add(courseComboBox);
        add(new JLabel("Fees:"));
        add(feesField);
        add(new JLabel(""));
        add(termsCheckBox);
        add(new JLabel(""));
        add(saveButton);

        // Add event listeners
        courseComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateFees();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveStudentData();
            }
        });

        // Display the JFrame
        setVisible(true);
    }

    // Method to calculate fees based on course selection
    private void calculateFees() {
        String selectedCourse = (String) courseComboBox.getSelectedItem();
        switch (selectedCourse) {
            case "Java":
                feesField.setText("5000");
                break;
            case "Python":
                feesField.setText("450");
                break;
            case "C++":
                feesField.setText("400");
                break;
        }
    }

    // Method to save student data to the database
    private void saveStudentData() {
        // Validate input
        if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() ||
                dobField.getText().isEmpty() || (!maleCheckBox.isSelected() && !femaleCheckBox.isSelected()) ||
                feesField.getText().isEmpty() || !termsCheckBox.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields and accept terms and conditions.");
            return;
        }

        // Get input values
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String dob = dobField.getText();
        String gender = maleCheckBox.isSelected() ? "Male" : "Female";
        String course = (String) courseComboBox.getSelectedItem();
        double fees = Double.parseDouble(feesField.getText());

        // Save to database
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO students (first_name, last_name, date_of_birth, gender, course, fees) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, dob);
            pstmt.setString(4, gender);
            pstmt.setString(5, course);
            pstmt.setDouble(6, fees);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Student data saved successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving student data: " + ex.getMessage());
        }
    }

    // Main method to run the application
    public static void main(String[] args) {
        new registrationform();
    }
}