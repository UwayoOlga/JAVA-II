import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class Department {
    private int id;
    private String name;

    public Department(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name; // Display department name in the JComboBox
    }
}

public class ModuleApplicationForm {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Module application form");

        frame.setSize(400, 400);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel lblId = new JLabel("Student ID");
        lblId.setBounds(10, 10, 100, 20);
        frame.add(lblId);

        JLabel lblDepartment = new JLabel("Department");
        lblDepartment.setBounds(10, 40, 100, 20);
        frame.add(lblDepartment);

        JLabel lblModule = new JLabel("Module");
        lblModule.setBounds(10, 70, 100, 20);
        frame.add(lblModule);

        JLabel lblReg = new JLabel("Reg fee");
        lblReg.setBounds(10, 100, 100, 20);
        frame.add(lblReg);

        JTextField txtId = new JTextField();
        txtId.setBounds(120, 10, 200, 20);
        frame.add(txtId);

        JComboBox<Department> comboDepartment = new JComboBox<>();
        comboDepartment.setBounds(120, 40, 200, 20);
        frame.add(comboDepartment);

        JComboBox<String> comboModule = new JComboBox<>();
        comboModule.setBounds(120, 70, 200, 20);
        frame.add(comboModule);

        JTextField txtReg = new JTextField();
        txtReg.setBounds(120, 100, 200, 20);
        frame.add(txtReg);

        JButton btnApply = new JButton("Apply");
        btnApply.setBounds(120, 130, 200, 20);
        btnApply.setForeground(Color.BLUE);
        frame.add(btnApply);

        // Populate Department ComboBox
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment2", "root", "California123!")) {
            String query = "SELECT DEPARTMENT_ID, DEPARTMENT_NAME FROM DEPARTMENT";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int dptId = rs.getInt("DEPARTMENT_ID");
                String dptName = rs.getString("DEPARTMENT_NAME");
                Department dpt = new Department(dptId, dptName);
                comboDepartment.addItem(dpt);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading departments: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Populate Module ComboBox based on selected Department
        comboDepartment.addActionListener(e -> {
            Department selectedDept = (Department) comboDepartment.getSelectedItem();
            if (selectedDept != null) {
                comboModule.removeAllItems(); // Clear existing items
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment2", "root", "California123!")) {
                    String query = "SELECT MODULE_NAME FROM MODULE WHERE DEPARTMENT_ID = ?";
                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, selectedDept.getId());
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) {
                        String moduleName = rs.getString("MODULE_NAME");
                        comboModule.addItem(moduleName);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error loading modules: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Apply Button ActionListener
        btnApply.addActionListener(e -> {
            String id = txtId.getText();
            Department department = (Department) comboDepartment.getSelectedItem();
            String module = (String) comboModule.getSelectedItem();
            String reg = txtReg.getText();

            if (id.isEmpty() || reg.isEmpty() || department == null || module == null) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment2", "root", "California123!")) {
                String query = "INSERT INTO module_applications (id, department, module, reg) VALUES (?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, id);
                pstmt.setString(2, department.getName());
                pstmt.setString(3, module);
                pstmt.setString(4, reg);

                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(frame, "Application submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error connecting to the database: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.setVisible(true);
    }
}