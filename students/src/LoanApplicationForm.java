 
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class LoanApplicationForm extends JFrame { 
    private JTextField memberIdField, firstNameField, lastNameField, contributionField, maxAllowedAmountField,
            requestedAmountField, capitalField, interestField, capitalInterestField;
    private JComboBox<String> loanTypeComboBox;
    private JComboBox<Integer> loanPeriodComboBox;

    public LoanApplicationForm() {
        setTitle("Loan Application Form");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); 
        getContentPane().setBackground(new Color(255, 182, 193)); 
 
        JLabel memberIdLabel = new JLabel("Member ID:");
        memberIdLabel.setBounds(50, 50, 150, 25);
        memberIdLabel.setForeground(new Color(199, 21, 133));  
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setBounds(50, 80, 150, 25);
        firstNameLabel.setForeground(new Color(199, 21, 133));  
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setBounds(50, 110, 150, 25);
        lastNameLabel.setForeground(new Color(199, 21, 133)); 
        JLabel loanTypeLabel = new JLabel("Loan Type:");
        loanTypeLabel.setBounds(50, 140, 150, 25);
        loanTypeLabel.setForeground(new Color(199, 21, 133));  
        JLabel contributionLabel = new JLabel("Contribution:");
        contributionLabel.setBounds(50, 170, 150, 25);
        contributionLabel.setForeground(new Color(199, 21, 133));  
        JLabel maxAllowedAmountLabel = new JLabel("Max. Allowed Amount:");
        maxAllowedAmountLabel.setBounds(50, 200, 150, 25);
        maxAllowedAmountLabel.setForeground(new Color(199, 21, 133));  
        JLabel loanPeriodLabel = new JLabel("Loan Period (Years):");
        loanPeriodLabel.setBounds(50, 230, 150, 25);
        loanPeriodLabel.setForeground(new Color(199, 21, 133));  
        JLabel requestedAmountLabel = new JLabel("Requested Amount:");
        requestedAmountLabel.setBounds(50, 260, 150, 25);
        requestedAmountLabel.setForeground(new Color(199, 21, 133));  
        JLabel capitalLabel = new JLabel("Capital:");
        capitalLabel.setBounds(50, 290, 150, 25);
        capitalLabel.setForeground(new Color(199, 21, 133));  
        JLabel interestLabel = new JLabel("Interest:");
        interestLabel.setBounds(50, 320, 150, 25);
        interestLabel.setForeground(new Color(199, 21, 133));  
        JLabel capitalInterestLabel = new JLabel("Capital + Interest:");
        capitalInterestLabel.setBounds(50, 350, 150, 25);
        capitalInterestLabel.setForeground(new Color(199, 21, 133));  
  
        memberIdField = new JTextField();
        memberIdField.setBounds(250, 50, 200, 25);
        firstNameField = new JTextField();
        firstNameField.setBounds(250, 80, 200, 25);
        lastNameField = new JTextField();
        lastNameField.setBounds(250, 110, 200, 25);
        contributionField = new JTextField();
        contributionField.setBounds(250, 170, 200, 25);
        maxAllowedAmountField = new JTextField();
        maxAllowedAmountField.setBounds(250, 200, 200, 25);
        maxAllowedAmountField.setEditable(false);  
        requestedAmountField = new JTextField();
        requestedAmountField.setBounds(250, 260, 200, 25);
        capitalField = new JTextField();
        capitalField.setBounds(250, 290, 200, 25);
        capitalField.setEditable(false);  
        interestField = new JTextField();
        interestField.setBounds(250, 320, 200, 25);
        interestField.setEditable(false);  
        capitalInterestField = new JTextField();
        capitalInterestField.setBounds(250, 350, 200, 25);
        capitalInterestField.setEditable(false);  
        loanTypeComboBox = new JComboBox<>(new String[]{"Personal", "Student", "Home", "Payday"});
        loanTypeComboBox.setBounds(250, 140, 200, 25);
 
        loanPeriodComboBox = new JComboBox<>(new Integer[]{1, 2, 3});
        loanPeriodComboBox.setBounds(250, 230, 200, 25);
 
        JButton saveButton = new JButton("Save");
        saveButton.setBounds(250, 400, 100, 30);
        saveButton.setBackground(new Color(199, 21, 133));  
        saveButton.setForeground(Color.WHITE); 
        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(360, 400, 100, 30);
        clearButton.setBackground(new Color(199, 21, 133));  
        clearButton.setForeground(Color.WHITE);
        
        add(memberIdLabel);
        add(memberIdField);
        add(firstNameLabel);
        add(firstNameField);
        add(lastNameLabel);
        add(lastNameField);
        add(loanTypeLabel);
        add(loanTypeComboBox);
        add(contributionLabel);
        add(contributionField);
        add(maxAllowedAmountLabel);
        add(maxAllowedAmountField);
        add(loanPeriodLabel);
        add(loanPeriodComboBox);
        add(requestedAmountLabel);
        add(requestedAmountField);
        add(capitalLabel);
        add(capitalField);
        add(interestLabel);
        add(interestField);
        add(capitalInterestLabel);
        add(capitalInterestField);
        add(saveButton);
        add(clearButton);

         
        contributionField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                calculateMaxAllowedAmount();
            }
        });

        
        requestedAmountField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                calculateCapitalAndInterest();
            }
        });

        // Event Listener for Save Button
        saveButton.addActionListener(e -> {
            String memberId = memberIdField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String loanType = (String) loanTypeComboBox.getSelectedItem();
            String contribution = contributionField.getText();
            String maxAllowedAmount = maxAllowedAmountField.getText();
            int loanPeriod = (int) loanPeriodComboBox.getSelectedItem();
            String requestedAmount = requestedAmountField.getText();
            String capital = capitalField.getText();
            String interest = interestField.getText();
            String capitalInterest = capitalInterestField.getText();

            // Validate input
            if (!validateInput(memberId, firstName, lastName, contribution, requestedAmount)) {
                return; // Stop if validation fails
            }

            // Save data to the database
            saveToDatabase(memberId, firstName, lastName, loanType, contribution, maxAllowedAmount, loanPeriod, requestedAmount, capital, interest, capitalInterest);
        });

        // Event Listener for Clear Button
        clearButton.addActionListener(e -> clearForm());

        setVisible(true);
    }

    // Method to calculate Max. Allowed Amount
    private void calculateMaxAllowedAmount() {
        try {
            double contribution = Double.parseDouble(contributionField.getText());
            double maxAllowedAmount = 5 * contribution;
            maxAllowedAmountField.setText(String.valueOf(maxAllowedAmount));
        } catch (NumberFormatException ex) {
            maxAllowedAmountField.setText(""); // Clear if input is invalid
        }
    }

    // Method to calculate Capital and Interest
    private void calculateCapitalAndInterest() {
        try {
            double requestedAmount = Double.parseDouble(requestedAmountField.getText());
            int loanPeriod = (int) loanPeriodComboBox.getSelectedItem();

            double capital = requestedAmount / (loanPeriod * 12);
            double interest = (requestedAmount * 0.05) / (loanPeriod * 12);
            double capitalInterest = capital + interest;

            capitalField.setText(String.valueOf(capital));
            interestField.setText(String.valueOf(interest));
            capitalInterestField.setText(String.valueOf(capitalInterest));
        } catch (NumberFormatException ex) {
            capitalField.setText("");
            interestField.setText("");
            capitalInterestField.setText("");
        }
    }

    // Method to validate input
    private boolean validateInput(String memberId, String firstName, String lastName, String contribution, String requestedAmount) {
        // Validate Member ID
        if (!Pattern.matches("\\d+", memberId)) {
            JOptionPane.showMessageDialog(this, "Member ID must be a number.");
            return false;
        }

        // Validate First Name and Last Name
        if (!Pattern.matches("[a-zA-Z ]+", firstName) || !Pattern.matches("[a-zA-Z ]+", lastName)) {
            JOptionPane.showMessageDialog(this, "First Name and Last Name must contain only alphabetic characters and spaces.");
            return false;
        }

        // Validate Contribution
        try {
            double contributionValue = Double.parseDouble(contribution);
            if (contributionValue <= 0) {
                JOptionPane.showMessageDialog(this, "Contribution must be a positive number.");
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Contribution must be a valid number.");
            return false;
        }

        // Validate Requested Amount
        try {
            double requestedAmountValue = Double.parseDouble(requestedAmount);
            double maxAllowedAmountValue = Double.parseDouble(maxAllowedAmountField.getText());
            if (requestedAmountValue < 1000 || requestedAmountValue > maxAllowedAmountValue) {
                JOptionPane.showMessageDialog(this, "Requested Amount must be at least 1,000 and cannot exceed Max. Allowed Amount.");
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Requested Amount must be a valid number.");
            return false;
        }

        return true; // All validations passed
    }

    // Method to save data to the database
    private void saveToDatabase(String memberId, String firstName, String lastName, String loanType, String contribution, String maxAllowedAmount, int loanPeriod, String requestedAmount, String capital, String interest, String capitalInterest) {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/XYXWelfare"; // Update with your database name
        String user = "root"; // Update with your database username
        String password = "California123!"; // Update with your database password

        // SQL query to insert data
        String query = "INSERT INTO LOAN_DETAILS (MEMBER_ID, FIRST_NAME, LAST_NAME, LOAN_TYPE, CONTRIBUTION, MAX_LOAN_AMOUNT, LOAN_PERIOD, REQUESTED_AMOUNT, CAPITAL, INTEREST, CAPITAL_INTEREST) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Set parameters for the query
            pstmt.setInt(1, Integer.parseInt(memberId));
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, loanType);
            pstmt.setDouble(5, Double.parseDouble(contribution));
            pstmt.setDouble(6, Double.parseDouble(maxAllowedAmount));
            pstmt.setInt(7, loanPeriod);
            pstmt.setDouble(8, Double.parseDouble(requestedAmount));
            pstmt.setDouble(9, Double.parseDouble(capital));
            pstmt.setDouble(10, Double.parseDouble(interest));
            pstmt.setDouble(11, Double.parseDouble(capitalInterest));

            // Execute the query
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Loan details saved successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving loan details: " + ex.getMessage());
        }
    }

    // Method to clear the form
    private void clearForm() {
        memberIdField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        contributionField.setText("");
        maxAllowedAmountField.setText("");
        requestedAmountField.setText("");
        capitalField.setText("");
        interestField.setText("");
        capitalInterestField.setText("");
        loanTypeComboBox.setSelectedIndex(0); // Reset to first item
        loanPeriodComboBox.setSelectedIndex(0); // Reset to first item
    }

    public static void main(String[] args) {
        new LoanApplicationForm();
    }
} */

/*
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class LoanApplicationForm extends JFrame {
    // Declare components as instance variables
    private JTextField memberIdField, firstNameField, lastNameField, contributionField, maxAllowedAmountField,
            loanPeriodField, requestedAmountField, capitalField, interestField, capitalInterestField;
    private JComboBox<String> loanTypeComboBox;

    public LoanApplicationForm() {
        setTitle("Loan Application Form");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // Use absolute positioning

        // Set light pink background for the JFrame
        getContentPane().setBackground(new Color(255, 182, 193)); // Light pink

        // Labels with darker pink foreground
        JLabel memberIdLabel = new JLabel("Member ID:");
        memberIdLabel.setBounds(50, 50, 150, 25);
        memberIdLabel.setForeground(new Color(199, 21, 133)); // Darker pink
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setBounds(50, 80, 150, 25);
        firstNameLabel.setForeground(new Color(199, 21, 133)); // Darker pink
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setBounds(50, 110, 150, 25);
        lastNameLabel.setForeground(new Color(199, 21, 133)); // Darker pink
        JLabel loanTypeLabel = new JLabel("Loan Type:");
        loanTypeLabel.setBounds(50, 140, 150, 25);
        loanTypeLabel.setForeground(new Color(199, 21, 133)); // Darker pink
        JLabel contributionLabel = new JLabel("Contribution:");
        contributionLabel.setBounds(50, 170, 150, 25);
        contributionLabel.setForeground(new Color(199, 21, 133)); // Darker pink
        JLabel maxAllowedAmountLabel = new JLabel("Max. Allowed Amount:");
        maxAllowedAmountLabel.setBounds(50, 200, 150, 25);
        maxAllowedAmountLabel.setForeground(new Color(199, 21, 133)); // Darker pink
        JLabel loanPeriodLabel = new JLabel("Loan Period (Years):");
        loanPeriodLabel.setBounds(50, 230, 150, 25);
        loanPeriodLabel.setForeground(new Color(199, 21, 133)); // Darker pink
        JLabel requestedAmountLabel = new JLabel("Requested Amount:");
        requestedAmountLabel.setBounds(50, 260, 150, 25);
        requestedAmountLabel.setForeground(new Color(199, 21, 133)); // Darker pink
        JLabel capitalLabel = new JLabel("Capital:");
        capitalLabel.setBounds(50, 290, 150, 25);
        capitalLabel.setForeground(new Color(199, 21, 133)); // Darker pink
        JLabel interestLabel = new JLabel("Interest:");
        interestLabel.setBounds(50, 320, 150, 25);
        interestLabel.setForeground(new Color(199, 21, 133)); // Darker pink
        JLabel capitalInterestLabel = new JLabel("Capital + Interest:");
        capitalInterestLabel.setBounds(50, 350, 150, 25);
        capitalInterestLabel.setForeground(new Color(199, 21, 133)); // Darker pink

        // Text Fields
        memberIdField = new JTextField();
        memberIdField.setBounds(250, 50, 200, 25);
        firstNameField = new JTextField();
        firstNameField.setBounds(250, 80, 200, 25);
        lastNameField = new JTextField();
        lastNameField.setBounds(250, 110, 200, 25);
        contributionField = new JTextField();
        contributionField.setBounds(250, 170, 200, 25);
        maxAllowedAmountField = new JTextField();
        maxAllowedAmountField.setBounds(250, 200, 200, 25);
        maxAllowedAmountField.setEditable(false); // Non-editable
        loanPeriodField = new JTextField();
        loanPeriodField.setBounds(250, 230, 200, 25);
        requestedAmountField = new JTextField();
        requestedAmountField.setBounds(250, 260, 200, 25);
        capitalField = new JTextField();
        capitalField.setBounds(250, 290, 200, 25);
        capitalField.setEditable(false); // Non-editable
        interestField = new JTextField();
        interestField.setBounds(250, 320, 200, 25);
        interestField.setEditable(false); // Non-editable
        capitalInterestField = new JTextField();
        capitalInterestField.setBounds(250, 350, 200, 25);
        capitalInterestField.setEditable(false); // Non-editable

        // Combo Box for Loan Type
        loanTypeComboBox = new JComboBox<>(new String[]{"Personal", "Student", "Home", "Payday"});
        loanTypeComboBox.setBounds(250, 140, 200, 25);

        // Save Button
        JButton saveButton = new JButton("Save");
        saveButton.setBounds(250, 400, 100, 30);
        saveButton.setBackground(new Color(199, 21, 133)); // Darker pink background
        saveButton.setForeground(Color.WHITE); // White text

        // Add components to the JFrame
        add(memberIdLabel);
        add(memberIdField);
        add(firstNameLabel);
        add(firstNameField);
        add(lastNameLabel);
        add(lastNameField);
        add(loanTypeLabel);
        add(loanTypeComboBox);
        add(contributionLabel);
        add(contributionField);
        add(maxAllowedAmountLabel);
        add(maxAllowedAmountField);
        add(loanPeriodLabel);
        add(loanPeriodField);
        add(requestedAmountLabel);
        add(requestedAmountField);
        add(capitalLabel);
        add(capitalField);
        add(interestLabel);
        add(interestField);
        add(capitalInterestLabel);
        add(capitalInterestField);
        add(saveButton);

        // Event Listener for Contribution Field
        contributionField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                calculateMaxAllowedAmount();
            }
        });

        // Event Listener for Loan Period Field
        loanPeriodField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                validateLoanPeriod();
                calculateCapitalAndInterest();
            }
        });

        // Event Listener for Requested Amount Field
        requestedAmountField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                calculateCapitalAndInterest();
            }
        });

        // Event Listener for Save Button
        saveButton.addActionListener(e -> {
            String memberId = memberIdField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String loanType = (String) loanTypeComboBox.getSelectedItem();
            String contribution = contributionField.getText();
            String maxAllowedAmount = maxAllowedAmountField.getText();
            String loanPeriod = loanPeriodField.getText();
            String requestedAmount = requestedAmountField.getText();
            String capital = capitalField.getText();
            String interest = interestField.getText();
            String capitalInterest = capitalInterestField.getText();

            // Validate input
            if (!validateInput(memberId, firstName, lastName, contribution, requestedAmount, loanPeriod)) {
                return; // Stop if validation fails
            }

            // Save data to the database
            saveToDatabase(memberId, firstName, lastName, loanType, contribution, maxAllowedAmount, loanPeriod, requestedAmount, capital, interest, capitalInterest);
        });

        setVisible(true);
    }

    // Method to calculate Max. Allowed Amount
    private void calculateMaxAllowedAmount() {
        try {
            double contribution = Double.parseDouble(contributionField.getText());
            double maxAllowedAmount = 5 * contribution;
            maxAllowedAmountField.setText(String.valueOf(maxAllowedAmount));
        } catch (NumberFormatException ex) {
            maxAllowedAmountField.setText(""); // Clear if input is invalid
        }
    }

    // Method to validate Loan Period
    private void validateLoanPeriod() {
        String loanPeriod = loanPeriodField.getText();
        if (!loanPeriod.matches("[1-3]")) {
            JOptionPane.showMessageDialog(this, "Loan Period must be 1, 2, or 3.");
            loanPeriodField.setText(""); // Clear invalid input
        }
    }

    // Method to calculate Capital and Interest
    private void calculateCapitalAndInterest() {
        try {
            double requestedAmount = Double.parseDouble(requestedAmountField.getText());
            int loanPeriod = Integer.parseInt(loanPeriodField.getText());

            double capital = requestedAmount / (loanPeriod * 12);
            double interest = (requestedAmount * 0.05) / (loanPeriod * 12);
            double capitalInterest = capital + interest;

            capitalField.setText(String.valueOf(capital));
            interestField.setText(String.valueOf(interest));
            capitalInterestField.setText(String.valueOf(capitalInterest));
        } catch (NumberFormatException ex) {
            capitalField.setText("");
            interestField.setText("");
            capitalInterestField.setText("");
        }
    }

    // Method to validate input
    private boolean validateInput(String memberId, String firstName, String lastName, String contribution, String requestedAmount, String loanPeriod) {
        // Validate Member ID
        if (!Pattern.matches("\\d+", memberId)) {
            JOptionPane.showMessageDialog(this, "Member ID must be a number.");
            return false;
        }

        // Validate First Name and Last Name
        if (!Pattern.matches("[a-zA-Z ]+", firstName) || !Pattern.matches("[a-zA-Z ]+", lastName)) {
            JOptionPane.showMessageDialog(this, "First Name and Last Name must contain only alphabetic characters and spaces.");
            return false;
        }

        // Validate Contribution
        try {
            double contributionValue = Double.parseDouble(contribution);
            if (contributionValue <= 0) {
                JOptionPane.showMessageDialog(this, "Contribution must be a positive number.");
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Contribution must be a valid number.");
            return false;
        }

        // Validate Loan Period
        if (!loanPeriod.matches("[1-3]")) {
            JOptionPane.showMessageDialog(this, "Loan Period must be 1, 2, or 3.");
            return false;
        }

        // Validate Requested Amount
        try {
            double requestedAmountValue = Double.parseDouble(requestedAmount);
            double maxAllowedAmountValue = Double.parseDouble(maxAllowedAmountField.getText());
            if (requestedAmountValue < 1000 || requestedAmountValue > maxAllowedAmountValue) {
                JOptionPane.showMessageDialog(this, "Requested Amount must be at least 1,000 and cannot exceed Max. Allowed Amount.");
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Requested Amount must be a valid number.");
            return false;
        }

        return true; // All validations passed
    }

    // Method to save data to the database
    private void saveToDatabase(String memberId, String firstName, String lastName, String loanType, String contribution, String maxAllowedAmount, String loanPeriod, String requestedAmount, String capital, String interest, String capitalInterest) {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/XYXWelfare"; // Update with your database name
        String user = "root"; // Update with your database username
        String password = "California123!"; // Update with your database password

        // SQL query to insert data
        String query = "INSERT INTO LOAN_DETAILS (MEMBER_ID, FIRST_NAME, LAST_NAME, LOAN_TYPE, CONTRIBUTION, MAX_LOAN_AMOUNT, LOAN_PERIOD, REQUESTED_AMOUNT, CAPITAL, INTEREST, CAPITAL_INTEREST) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Set parameters for the query
            pstmt.setInt(1, Integer.parseInt(memberId));
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, loanType);
            pstmt.setDouble(5, Double.parseDouble(contribution));
            pstmt.setDouble(6, Double.parseDouble(maxAllowedAmount));
            pstmt.setInt(7, Integer.parseInt(loanPeriod));
            pstmt.setDouble(8, Double.parseDouble(requestedAmount));
            pstmt.setDouble(9, Double.parseDouble(capital));
            pstmt.setDouble(10, Double.parseDouble(interest));
            pstmt.setDouble(11, Double.parseDouble(capitalInterest));

            // Execute the query
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Loan details saved successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving loan details: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new LoanApplicationForm();
    }
}
 */
