import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class registrationform extends JFrame { 
    private JTextField firstNameField, lastNameField, dobField, feesField;
    private JRadioButton maleRadioButton, femaleRadioButton;
    private JComboBox<String> courseComboBox;
    private JCheckBox termsCheckBox;

    public registrationform() {
        setTitle("Student Registration System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);  
        getContentPane().setBackground(new Color(255, 182, 193));  
 
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setBounds(50, 50, 150, 25);
        firstNameLabel.setForeground(new Color(199, 21, 133));  
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setBounds(50, 80, 150, 25);
        lastNameLabel.setForeground(new Color(199, 21, 133));  
        JLabel dobLabel = new JLabel("Date of Birth (YYYY-MM-DD):");
        dobLabel.setBounds(50, 110, 150, 25);
        dobLabel.setForeground(new Color(199, 21, 133));  
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(50, 140, 150, 25);
        genderLabel.setForeground(new Color(199, 21, 133));  
        JLabel courseLabel = new JLabel("Selected Course:");
        courseLabel.setBounds(50, 170, 150, 25);
        courseLabel.setForeground(new Color(199, 21, 133));  
        JLabel feesLabel = new JLabel("Fees:");
        feesLabel.setBounds(50, 200, 150, 25);
        feesLabel.setForeground(new Color(199, 21, 133));  
        JLabel termsLabel = new JLabel("Terms and Conditions:");
        termsLabel.setBounds(50, 230, 150, 25);
        termsLabel.setForeground(new Color(199, 21, 133)); 
        
        firstNameField = new JTextField();
        firstNameField.setBounds(250, 50, 200, 25);
        lastNameField = new JTextField();
        lastNameField.setBounds(250, 80, 200, 25);
        dobField = new JTextField();
        dobField.setBounds(250, 110, 200, 25);
        feesField = new JTextField();
        feesField.setBounds(250, 200, 200, 25);
        feesField.setEditable(false); 
        
        maleRadioButton = new JRadioButton("Male");
        maleRadioButton.setBounds(250, 140, 80, 25);
        maleRadioButton.setBackground(new Color(255, 182, 193));  
        femaleRadioButton = new JRadioButton("Female");
        femaleRadioButton.setBounds(330, 140, 80, 25);
        femaleRadioButton.setBackground(new Color(255, 182, 193));  
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);
 
        courseComboBox = new JComboBox<>(new String[]{"Java", "Python", "C++"});
        courseComboBox.setBounds(250, 170, 200, 25);
 
        termsCheckBox = new JCheckBox("Accept Terms and Conditions");
        termsCheckBox.setBounds(250, 230, 200, 25);
        termsCheckBox.setBackground(new Color(255, 182, 193)); 
        
        JButton saveButton = new JButton("Save");
        saveButton.setBounds(250, 270, 100, 30);
        saveButton.setBackground(new Color(199, 21, 133)); 
        saveButton.setForeground(Color.WHITE); 
        
        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(360, 270, 100, 30);
        clearButton.setBackground(new Color(199, 21, 133)); 
        clearButton.setForeground(Color.WHITE); 
        
        add(firstNameLabel);
        add(firstNameField);
        add(lastNameLabel);
        add(lastNameField);
        add(dobLabel);
        add(dobField);
        add(genderLabel);
        add(maleRadioButton);
        add(femaleRadioButton);
        add(courseLabel);
        add(courseComboBox);
        add(feesLabel);
        add(feesField);
        add(termsLabel);
        add(termsCheckBox);
        add(saveButton);
        add(clearButton);
 
        courseComboBox.addActionListener(e -> calculateFees(courseComboBox, feesField));

        
        saveButton.addActionListener(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String dob = dobField.getText();
            String gender = maleRadioButton.isSelected() ? "Male" : "Female";
            String course = (String) courseComboBox.getSelectedItem();
            String fees = feesField.getText();
            boolean termsAccepted = termsCheckBox.isSelected();
 
            if (!validateInput(firstName, lastName, dob, gender, termsAccepted)) {
                return;  
            }
 
            saveToDatabase(firstName, lastName, dob, gender, course, fees);
        });
 
        clearButton.addActionListener(e -> clearForm());

        setVisible(true);
    }
 
    private void calculateFees(JComboBox<String> courseComboBox, JTextField feesField) {
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
 
    private boolean validateInput(String firstName, String lastName, String dob, String gender, boolean termsAccepted) {
        
        if (!Pattern.matches("[a-zA-Z ]+", firstName)) {
            JOptionPane.showMessageDialog(this, "First Name must contain only alphabetic characters and spaces.");
            return false;
        }
 
        if (!Pattern.matches("[a-zA-Z ]+", lastName)) {
            JOptionPane.showMessageDialog(this, "Last Name must contain only alphabetic characters and spaces.");
            return false;
        }
 
        if (!Pattern.matches("\\d{4}-\\d{2}-\\d{2}", dob)) {
            JOptionPane.showMessageDialog(this, "Date of Birth must be in the format YYYY-MM-DD.");
            return false;
        }
 
        if (!gender.equals("Male") && !gender.equals("Female")) {
            JOptionPane.showMessageDialog(this, "Please select a gender.");
            return false;
        }
 
        if (!termsAccepted) {
            JOptionPane.showMessageDialog(this, "Please accept the terms and conditions.");
            return false;
        }

        return true; 
    }
 
    private void saveToDatabase(String firstName, String lastName, String dob, String gender, String course, String fees) {
         
        String url = "jdbc:mysql://localhost:3306/students";  
        String user = "root";  
        String password = "California123!";
        
        String query = "INSERT INTO students (first_name, last_name, date_of_birth, gender, course, fees) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
 
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, dob);
            pstmt.setString(4, gender);
            pstmt.setString(5, course);
            pstmt.setDouble(6, Double.parseDouble(fees));

            // Execute the query
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Student data saved successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving student data: " + ex.getMessage());
        }
    }

    // Method to clear the form
    private void clearForm() {
        firstNameField.setText("");
        lastNameField.setText("");
        dobField.setText("");
        maleRadioButton.setSelected(false);
        femaleRadioButton.setSelected(false);
        courseComboBox.setSelectedIndex(0); // Reset to first item
        feesField.setText("");
        termsCheckBox.setSelected(false);
    }

    public static void main(String[] args) {
        new registrationform();
    }
}
