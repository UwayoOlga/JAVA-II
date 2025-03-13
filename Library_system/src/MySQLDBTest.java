import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDBTest {

    public static void main(String[] args) { 
        String url = "jdbc:mysql://localhost:3306/employee_management";  
        String username = "root";   
        String password = "California123!";  
        Connection connection = null;

        try { 
            Class.forName("com.mysql.cj.jdbc.Driver");
 
            connection = DriverManager.getConnection(url, username, password);
 
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
