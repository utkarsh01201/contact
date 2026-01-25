import java.sql.*;

public class QuickSetup {
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   Quick MySQL Setup & Test");
        System.out.println("========================================\n");

        // Try different password combinations
        String[] passwords = {"", "root", "password", "12345", "mysql"};
        String url = "jdbc:mysql://localhost:3306";
        String user = "root";
        
        System.out.println("Testing MySQL connections...\n");
        
        for (String password : passwords) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection(url, user, password);
                
                System.out.println("✓ SUCCESS! Connected with password: '" + password + "'");
                
                // Create database
                Statement stmt = conn.createStatement();
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS contact_db");
                System.out.println("✓ Database 'contact_db' created/exists");
                
                // Use database
                conn.setCatalog("contact_db");
                
                // Create table
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
                System.out.println("✓ Table 'contacts' created/exists");
                
                conn.close();
                
                System.out.println("\n========================================");
                System.out.println("✓ ALL SET! Your password is: '" + password + "'");
                System.out.println("========================================");
                System.out.println("\nUpdate config.properties:");
                System.out.println("db.password=" + password);
                System.out.println("\nThen run: run.bat");
                return;
                
            } catch (SQLException e) {
                // Continue to next password
            } catch (Exception e) {
                System.out.println("✗ Error: " + e.getMessage());
            }
        }
        
        System.out.println("========================================");
        System.out.println("✗ Could not connect with any standard password");
        System.out.println("========================================");
        System.out.println("\nPlease manually:");
        System.out.println("1. Open MySQL Command Prompt");
        System.out.println("2. Run: mysql -u root -p");
        System.out.println("3. Enter your password when prompted");
        System.out.println("4. If successful, run: database_setup.sql");
        System.out.println("5. Update config.properties with correct password");
    }
}
