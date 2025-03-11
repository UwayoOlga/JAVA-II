import javax.swing.*;

public class registrationform extends JFrame {
    public registrationform() {
        setTitle("Student Registration System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // Use absolute positioning

        // Labels
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setBounds(50, 50, 150, 25);
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setBounds(50, 80, 150, 25);
        JLabel dobLabel = new JLabel("Date of Birth:");
        dobLabel.setBounds(50, 110, 150, 25);
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(50, 140, 150, 25);
        JLabel courseLabel = new JLabel("Selected Course:");
        courseLabel.setBounds(50, 170, 150, 25);
        JLabel feesLabel = new JLabel("Fees:");
        feesLabel.setBounds(50, 200, 150, 25);
        JLabel termsLabel = new JLabel("Terms and Conditions:");
        termsLabel.setBounds(50, 230, 150, 25);

        // Text Fields
        JTextField firstNameField = new JTextField();
        firstNameField.setBounds(250, 50, 200, 25);
        JTextField lastNameField = new JTextField();
        lastNameField.setBounds(250, 80, 200, 25);
        JTextField dobField = new JTextField();
        dobField.setBounds(250, 110, 200, 25);
        JTextField feesField = new JTextField();
        feesField.setBounds(250, 200, 200, 25);
        feesField.setEditable(false); // Fees field should not be editable

        // Gender Radio Buttons
        JRadioButton maleRadioButton = new JRadioButton("Male");
        maleRadioButton.setBounds(250, 140, 80, 25);
        JRadioButton femaleRadioButton = new JRadioButton("Female");
        femaleRadioButton.setBounds(330, 140, 80, 25);
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);

        // Course Combo Box
        JComboBox<String> courseComboBox = new JComboBox<>(new String[]{"Java", "Python", "C++"});
        courseComboBox.setBounds(250, 170, 200, 25);

        // Terms and Conditions Checkbox
        JCheckBox termsCheckBox = new JCheckBox("Accept Terms and Conditions");
        termsCheckBox.setBounds(250, 230, 200, 25);

        // Save Button
        JButton saveButton = new JButton("Save");
        saveButton.setBounds(250, 270, 100, 30);

        // Add components to the JFrame
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

        // Event Listener for Course Selection
        courseComboBox.addActionListener(e -> calculateFees(courseComboBox, feesField));

        // Event Listener for Save Button
        saveButton.addActionListener(e -> saveStudentData(
                firstNameField.getText(),
                lastNameField.getText(),
                dobField.getText(),
                maleRadioButton.isSelected() ? "Male" : "Female",
                (String) courseComboBox.getSelectedItem(),
                feesField.getText(),
                termsCheckBox.isSelected()
        ));

        setVisible(true);
    }

    // Method to calculate fees based on course selection
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

    // Method to save student data (dummy implementation)
    private void saveStudentData(String firstName, String lastName, String dob, String gender, String course, String fees, boolean termsAccepted) {
        if (firstName.isEmpty() || lastName.isEmpty() || dob.isEmpty() || (!gender.equals("Male") && !gender.equals("Female"))) {
            JOptionPane.showMessageDialog(this, "Please fill all fields and select a gender.");
            return;
        }
        if (!termsAccepted) {
            JOptionPane.showMessageDialog(this, "Please accept the terms and conditions.");
            return;
        }
        JOptionPane.showMessageDialog(this, "Student data saved successfully!");
    }

    public static void main(String[] args) {
        new registrationform();
    }
}