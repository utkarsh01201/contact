import java.sql.*;

public class AutoSetupDatabase {
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   Automatic Database Setup");
        System.out.println("========================================\n");

        String url = "jdbc:mysql://localhost:3306";
        String user = "root";
        String password = "root";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✓ MySQL JDBC Driver loaded\n");

            // Connect to MySQL server
            System.out.println("Connecting to MySQL server...");
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✓ Connected successfully\n");

            Statement stmt = conn.createStatement();

            // Create database
            System.out.println("Creating database 'contact_db'...");
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS contact_db");
            System.out.println("✓ Database created/exists\n");

            // Switch to database
            conn.setCatalog("contact_db");
            System.out.println("✓ Using database 'contact_db'\n");

            // Create table
            System.out.println("Creating table 'contacts'...");
            String createTableSQL = "CREATE TABLE IF NOT EXISTS contacts (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "email VARCHAR(100) NOT NULL UNIQUE," +
                "phone VARCHAR(15) NOT NULL UNIQUE," +
                "address VARCHAR(200)," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
                ")";
            stmt.executeUpdate(createTableSQL);
            System.out.println("✓ Table created/exists\n");

            System.out.println("✓ Database ready for use\n");

            // Verify data
            System.out.println("Verifying data...");
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM contacts");
            if (rs.next()) {
                int count = rs.getInt("count");
                System.out.println("✓ Total contacts in database: " + count + "\n");
            }

            conn.close();

            System.out.println("========================================");
            System.out.println("✓ DATABASE SETUP COMPLETE!");
            System.out.println("========================================\n");
            System.out.println("Database: contact_db");
            System.out.println("User: root");
            System.out.println("Password: root");
            System.out.println("Table: contacts (with sample data)\n");
            System.out.println("Ready to run the application!");
            System.out.println("Run: run.bat (or ./run.sh on Linux/Mac)\n");

        } catch (ClassNotFoundException e) {
            System.out.println("✗ MySQL JDBC Driver not found!");
            System.out.println("Error: " + e.getMessage());
            System.out.println("Ensure mysql-connector-j-9.6.0.jar is in lib/ folder\n");

        } catch (SQLException e) {
            System.out.println("✗ Database operation failed!");
            System.out.println("Error: " + e.getMessage() + "\n");

            if (e.getMessage().contains("Access denied")) {
                System.out.println("Fix: Password is incorrect or user doesn't exist");
                System.out.println("Check: MySQL is running");
                System.out.println("Check: User 'root' with password 'root' exists");
            } else if (e.getMessage().contains("Communications link failure")) {
                System.out.println("Fix: MySQL server is not running");
                System.out.println("Start MySQL: net start MySQL80 (Windows)");
            }
            System.out.println();
        }
    }
}
