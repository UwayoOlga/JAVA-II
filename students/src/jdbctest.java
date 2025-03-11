import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class jdbctest {

    public static void main(String[] args) {
        // Database credentials
        String url = "jdbc:mysql://localhost:3306/students"; // Update with your DB URL
        String username = "root";  // Update with your DB username
        String password = "California123!";  // Update with your DB password

        // Initialize the connection object
        Connection connection = null;

        try {
            // Load the MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection to the database
            connection = DriverManager.getConnection(url, username, password);

            // Check if the connection is valid
            if (connection != null) {
                System.out.println("Successfully connected to the MySQL database!");
            } else {
                System.out.println("Failed to connect to the MySQL database.");
            }

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("SQL Exception occurred while connecting to the database.");
            e.printStackTrace();
        } finally {
            // Close the connection if it is open
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Error while closing the connection.");
                e.printStackTrace();
            }
        }
    }
}
