package com.student;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class ModuleApplicationForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtStudentId;
	private JTextField txtRegFee;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModuleApplicationForm frame = new ModuleApplicationForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ModuleApplicationForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 669, 411);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Student Id");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblNewLabel.setBounds(48, 33, 200, 40);
		contentPane.add(lblNewLabel);
		
		JLabel lblDepartment = new JLabel("Department");
		lblDepartment.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblDepartment.setBounds(48, 93, 200, 40);
		contentPane.add(lblDepartment);
		
		JLabel lblModule = new JLabel("Module");
		lblModule.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblModule.setBounds(48, 161, 200, 40);
		contentPane.add(lblModule);
		
		JLabel lblRegFee = new JLabel("Reg. Fee");
		lblRegFee.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblRegFee.setBounds(48, 221, 200, 40);
		contentPane.add(lblRegFee);
		
		txtStudentId = new JTextField();
		txtStudentId.setFont(new Font("Tahoma", Font.PLAIN, 28));
		txtStudentId.setBounds(258, 33, 274, 40);
		contentPane.add(txtStudentId);
		txtStudentId.setColumns(10);
		JComboBox<Module> comboModule = new JComboBox<>();
		txtRegFee = new JTextField();
		comboModule.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				//If a module has been changed, update txtRegFee text field 
				if(e.getStateChange() == ItemEvent.SELECTED) {
					Module selModuleObj = (Module)comboModule.getSelectedItem();
					Long regFee = selModuleObj.getRegFee();
					txtRegFee.setText(regFee.toString());
					
				}
			}
		});
		JComboBox<Department> comboDepartment = new JComboBox<>();
		comboDepartment.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				// Check if a department has been changed 
				//and load all modules in it  
				if(e.getStateChange() == ItemEvent.SELECTED) {
					Department selDptObj = (Department)comboDepartment.getSelectedItem();
					Integer selDptId = selDptObj.getDepartmentId();
					// load all those Modules available in selected dptm
					try {
						JDBCConnection conObj = new JDBCConnection();
						Connection conn = conObj.getConn();
						String sql = "SELECT MODULE_CODE, DEPARTMENT_ID, "
								+ "TITLE, REG_FEE FROM MODULE "
								+ "WHERE DEPARTMENT_ID = "+selDptId+" "
								+ "ORDER BY TITLE";
						Statement stm = conn.createStatement();
						ResultSet rs = stm.executeQuery(sql);
						comboModule.removeAllItems();
						Module initModule = new Module();
						initModule.setModuleCode("0");
						initModule.setDepartmentId(0);
						initModule.setTitle("--please select--");
						initModule.setRegFee(0L);
						comboModule.addItem(initModule);
						while(rs.next()) {
							String mCode = rs.getString(1);
							Integer dptId = rs.getInt(2);
							String title = rs.getString(3);
							Long regFee = rs.getLong(4); 
							Module mObj = new Module();
							mObj.setModuleCode(mCode);
							mObj.setDepartmentId(dptId);
							mObj.setTitle(title);
							mObj.setRegFee(regFee);
							comboModule.addItem(mObj);
						}
					}catch(Exception ex1) {
						System.out.println("Error$$$$$$ "+ ex1.getMessage());
					}
					
				}
			}
		});
		// Load Department from DB and add them all to 
		//the dptm combo box
		try {
			JDBCConnection conObj = new JDBCConnection();
			Connection conn = conObj.getConn();
			String sql = "SELECT DEPARTMENT_ID, DEPARTMENT_NAME "
					+ "FROM DEPARTMENT";
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			Department intDpt = new Department();
			intDpt.setDepartmentId(0);
			intDpt.setDepartmentName("--please select--");
			//Initially, add --please select-- option to department combobox
			comboDepartment.addItem(intDpt);
			while(rs.next()) {
				//Loop through all departments 
				// and add department Id and name to the comboDepartment 
				Integer dptId = rs.getInt("DEPARTMENT_ID");
				String dptName = rs.getString("DEPARTMENT_NAME");
				Department dptObj = new Department();
				dptObj.setDepartmentId(dptId);
				dptObj.setDepartmentName(dptName);
				comboDepartment.addItem(dptObj);
			}
			
		}catch(Exception ex) {
			System.out.println("Error occured:::::: " + ex.getMessage());
		}
		
		
		comboDepartment.setBounds(258, 93, 274, 40);
		contentPane.add(comboDepartment);
		

		comboModule.setBounds(258, 150, 274, 40);
		contentPane.add(comboModule);
		
		
		txtRegFee.setEditable(false);
		txtRegFee.setBounds(258, 221, 274, 37);
		contentPane.add(txtRegFee);
		txtRegFee.setColumns(10);
		
		JButton btnApply = new JButton("APPLY");
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnApply.setForeground(new Color(0, 128, 255));
		btnApply.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnApply.setBounds(288, 310, 158, 54);
		contentPane.add(btnApply);
	}

}
