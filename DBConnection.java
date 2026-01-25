import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DBConnection {
    private static String URL;
    private static String USER;
    private static String PASSWORD;
    private static String DRIVER;

    static {
        try {
            loadConfiguration();
        } catch (Exception e) {
            System.err.println("========================================");
            System.err.println("  SETUP ERROR - Database Configuration");
            System.err.println("========================================");
            System.err.println("\n✗ Failed to load database configuration!");
            System.err.println("Error: " + e.getMessage());
            System.err.println("\n=== QUICK FIX ===");
            System.err.println("1. Make sure 'config.properties' file exists");
            System.err.println("2. File should be in: " + System.getProperty("user.dir"));
            System.err.println("3. Update these values in config.properties:");
            System.err.println("   db.url=jdbc:mysql://localhost:3306/contact_db");
            System.err.println("   db.user=root");
            System.err.println("   db.password=YOUR_PASSWORD");
            System.err.println("\n=== MYSQL SETUP ===");
            System.err.println("4. Start MySQL server:");
            System.err.println("   Windows: net start MySQL80");
            System.err.println("   Linux:   sudo service mysql start");
            System.err.println("   Mac:     brew services start mysql");
            System.err.println("\n5. Create database:");
            System.err.println("   mysql -u root -p");
            System.err.println("   CREATE DATABASE contact_db;");
            System.err.println("   Run: database_setup.sql");
            System.err.println("========================================");
            System.exit(1);
        }
    }

    private static void loadConfiguration() throws Exception {
        Properties props = new Properties();
        
        // Try to load from current directory first
        String configPath = "config.properties";
        if (Files.exists(Paths.get(configPath))) {
            try (FileInputStream fis = new FileInputStream(configPath)) {
                props.load(fis);
            }
        } else {
            // Try from classpath as fallback
            try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("config.properties")) {
                if (input != null) {
                    props.load(input);
                } else {
                    throw new Exception("config.properties not found in " + System.getProperty("user.dir"));
                }
            }
        }

        URL = props.getProperty("db.url", "jdbc:mysql://localhost:3306/contact_db");
        USER = props.getProperty("db.user", "root");
        PASSWORD = props.getProperty("db.password", "");
        DRIVER = props.getProperty("db.driver", "com.mysql.cj.jdbc.Driver");

        if (URL == null || URL.isEmpty()) {
            throw new Exception("db.url not configured in config.properties");
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("✗ MySQL JDBC Driver not found!");
            System.err.println("  Ensure mysql-connector-j-9.6.0.jar is in lib/ folder");
            System.err.println("  Current classpath setup:");
            System.err.println("  Windows: -cp \".;lib/mysql-connector-j-9.6.0.jar\"");
            System.err.println("  Linux:   -cp \".:lib/mysql-connector-j-9.6.0.jar\"");
            throw new SQLException("JDBC Driver not found");
        } catch (SQLException e) {
            System.err.println("\n========================================");
            System.err.println("  ✗ DATABASE CONNECTION FAILED");
            System.err.println("========================================");
            System.err.println("Error: " + e.getMessage());
            System.err.println("\nTroubleshooting checklist:");
            System.err.println("□ MySQL Server running? (Check Windows Services)");
            System.err.println("□ Database 'contact_db' created?");
            System.err.println("□ Username correct? (config.properties: db.user)");
            System.err.println("□ Password correct? (config.properties: db.password)");
            System.err.println("□ Database URL correct? (config.properties: db.url)");
            System.err.println("\nTo fix:");
            System.err.println("1. Open config.properties and verify credentials");
            System.err.println("2. Start MySQL: net start MySQL80 (Windows)");
            System.err.println("3. Create database: mysql -u root -p < database_setup.sql");
            System.err.println("========================================\n");
            throw e;
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    public static void testConnection() {
        try (Connection conn = getConnection()) {
            System.out.println("✓ Database connection successful!");
        } catch (SQLException e) {
            System.out.println("✗ Database connection failed!");
            System.exit(1);
        }
    }
}
