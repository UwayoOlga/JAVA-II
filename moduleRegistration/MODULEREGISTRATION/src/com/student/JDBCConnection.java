package com.student;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class JDBCConnection {
	public JDBCConnection() {
		String dbUrl = "jdbc:oracle:thin:@localhost:1522/xepdb1";
		String dbUsername = "JAVAAPP";
		String dbPassword = "Oracle_123";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			this.conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
		}catch(ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
		}catch(SQLException se) {
			JOptionPane.showMessageDialog(null, se, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	private Connection conn;

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}
}
