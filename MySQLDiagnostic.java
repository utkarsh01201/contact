import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MySQLDiagnostic {
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   MySQL Diagnostic Tool");
        System.out.println("========================================\n");

        // Try to connect
        System.out.println("Attempting to load MySQL JDBC Driver...");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✓ MySQL JDBC Driver loaded successfully\n");
        } catch (ClassNotFoundException e) {
            System.out.println("✗ MySQL JDBC Driver NOT found");
            System.out.println("  Error: " + e.getMessage());
            return;
        }

        System.out.println("Attempting database connection...\n");
        try {
            java.sql.Connection conn = java.sql.DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/contact_db", 
                "root", 
                "root"
            );
            System.out.println("✓ SUCCESS! Connected to MySQL!");
            System.out.println("Database: contact_db");
            System.out.println("User: root");
            
            // Check if table exists
            System.out.println("\nChecking for 'contacts' table...");
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM contacts");
            if (rs.next()) {
                System.out.println("✓ Table 'contacts' exists");
                System.out.println("  Total records: " + rs.getInt("count"));
            }
            
            conn.close();
            System.out.println("\n✓ Application is ready to run!");
            System.out.println("Execute: run.bat (Windows) or ./run.sh (Linux/Mac)");
            
        } catch (com.mysql.cj.jdbc.exceptions.CommunicationsException e) {
            System.out.println("✗ Cannot connect to MySQL Server!");
            System.out.println("Error: " + e.getMessage());
            System.out.println("\nFix:");
            System.out.println("1. MySQL Server is NOT running or not on localhost:3306");
            System.out.println("2. Windows: Open Services and start 'MySQL80'");
            System.out.println("3. Or check your network connection");
            
        } catch (java.sql.SQLException e) {
            String message = e.getMessage();
            System.out.println("✗ Database Error!");
            System.out.println("Error: " + message);
            
            if (message.contains("Access denied")) {
                System.out.println("\nFix: Password is INCORRECT");
                System.out.println("1. Edit config.properties");
                System.out.println("2. Change db.password to your actual MySQL password");
                System.out.println("3. If MySQL has no password, set: db.password=");
                
            } else if (message.contains("Unknown database")) {
                System.out.println("\nFix: Database 'contact_db' does NOT exist");
                System.out.println("1. Run: mysql -u root -p");
                System.out.println("2. Execute: CREATE DATABASE contact_db;");
                System.out.println("3. Or run database_setup.sql");
                
            } else if (message.contains("Table")) {
                System.out.println("\nFix: Table 'contacts' does NOT exist");
                System.out.println("1. Run database_setup.sql");
                
            } else {
                System.out.println("\nFix: " + e.getClass().getSimpleName());
            }
        }

        System.out.println("\n========================================");
    }
}
