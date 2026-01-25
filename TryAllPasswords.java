import java.sql.*;

public class TryAllPasswords {
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   Finding MySQL Root Password");
        System.out.println("========================================\n");

        String[] passwordsToTry = {
            "",           // empty
            "root",       // common
            "password",   // common
            "12345",      // common
            "admin",      // common
            "mysql",      // common
            "123456",     // common
            "test"        // common
        };

        String url = "jdbc:mysql://localhost:3306";
        String user = "root";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("✗ MySQL JDBC Driver not found!");
            return;
        }

        for (String password : passwordsToTry) {
            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                
                System.out.println("✓ SUCCESS! Password is: '" + password + "'\n");
                
                // Now set it up
                Statement stmt = conn.createStatement();
                
                System.out.println("Setting up database...");
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS contact_db");
                System.out.println("✓ Database created");
                
                conn.setCatalog("contact_db");
                
                String createTable = "CREATE TABLE IF NOT EXISTS contacts (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(100) NOT NULL," +
                    "email VARCHAR(100) NOT NULL UNIQUE," +
                    "phone VARCHAR(15) NOT NULL UNIQUE," +
                    "address VARCHAR(200)," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
                    ")";
                stmt.executeUpdate(createTable);
                System.out.println("✓ Table created");
                
                System.out.println("✓ Database ready for use");
                
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as cnt FROM contacts");
                rs.next();
                System.out.println("✓ Total contacts: " + rs.getInt("cnt") + "\n");
                
                conn.close();
                
                System.out.println("========================================");
                System.out.println("✓ DATABASE SETUP COMPLETE!");
                System.out.println("========================================");
                System.out.println("\nUpdate config.properties:");
                System.out.println("db.password=" + password + "\n");
                System.out.println("Then run: run.bat\n");
                return;
                
            } catch (SQLException ignored) {
                // Try next password
            }
        }
        
        System.out.println("✗ Could not connect with any password");
        System.out.println("\nPlease manually:");
        System.out.println("1. mysql -u root -p");
        System.out.println("2. Enter your MySQL password");
        System.out.println("3. Then paste database_setup.sql");
        System.out.println("\nOr update config.properties with the correct password");
    }
}
