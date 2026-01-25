import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileInputStream;
import java.util.Properties;

public class ConfigValidator {
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   Configuration Validator");
        System.out.println("========================================\n");

        boolean allGood = true;

        // Check Java version
        System.out.println("1. Java Version:");
        String javaVersion = System.getProperty("java.version");
        System.out.println("   ✓ Java " + javaVersion);

        // Check config.properties exists
        System.out.println("\n2. Configuration File:");
        File configFile = new File("config.properties");
        if (!configFile.exists()) {
            System.out.println("   ✗ config.properties NOT FOUND");
            System.out.println("   Location: " + configFile.getAbsolutePath());
            allGood = false;
        } else {
            System.out.println("   ✓ config.properties found");
            System.out.println("   Location: " + configFile.getAbsolutePath());

            // Check config values
            try {
                Properties props = new Properties();
                props.load(new FileInputStream(configFile));

                System.out.println("\n3. Database Configuration:");
                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String password = props.getProperty("db.password");
                String driver = props.getProperty("db.driver");

                System.out.println("   URL: " + (url != null ? url : "NOT SET"));
                System.out.println("   User: " + (user != null ? user : "NOT SET"));
                System.out.println("   Password: " + (password != null && !password.isEmpty() ? "***SET***" : "EMPTY"));
                System.out.println("   Driver: " + (driver != null ? driver : "NOT SET"));

                if (url == null || url.isEmpty()) {
                    System.out.println("   ✗ db.url is empty!");
                    allGood = false;
                } else if (!url.contains("localhost") && !url.contains("127.0.0.1")) {
                    System.out.println("   ⚠ db.url is not localhost - make sure server is accessible");
                }

                if (user == null || user.isEmpty()) {
                    System.out.println("   ✗ db.user is empty!");
                    allGood = false;
                }

                // Warning if password is empty
                if (password == null || password.isEmpty()) {
                    System.out.println("   ⚠ WARNING: db.password is EMPTY");
                    System.out.println("     This may work only if MySQL root has no password");
                }
            } catch (Exception e) {
                System.out.println("   ✗ Error reading config.properties: " + e.getMessage());
                allGood = false;
            }
        }

        // Check compiled files
        System.out.println("\n4. Compiled Classes:");
        String[] classFiles = {"Contact.class", "ContactDAO.class", "DBConnection.class", "MainApp.class"};
        boolean allClassesExist = true;
        for (String className : classFiles) {
            if (Files.exists(Paths.get(className))) {
                System.out.println("   ✓ " + className);
            } else {
                System.out.println("   ✗ " + className + " NOT FOUND");
                allClassesExist = false;
                allGood = false;
            }
        }
        
        if (!allClassesExist) {
            System.out.println("   Run: javac -cp \".;lib/mysql-connector-j-9.6.0.jar\" *.java");
        }

        // Check JDBC driver
        System.out.println("\n5. JDBC Driver:");
        if (Files.exists(Paths.get("lib/mysql-connector-j-9.6.0.jar"))) {
            System.out.println("   ✓ mysql-connector-j-9.6.0.jar found");
        } else {
            System.out.println("   ✗ mysql-connector-j-9.6.0.jar NOT FOUND");
            System.out.println("   Location: " + new File("lib").getAbsolutePath());
            allGood = false;
        }

        // Check database_setup.sql
        System.out.println("\n6. Database Setup Script:");
        if (Files.exists(Paths.get("database_setup.sql"))) {
            System.out.println("   ✓ database_setup.sql found");
        } else {
            System.out.println("   ✗ database_setup.sql NOT FOUND");
        }

        // Summary
        System.out.println("\n========================================");
        if (allGood) {
            System.out.println("✓ All checks passed!");
            System.out.println("You should be ready to run the application.");
            System.out.println("\nNext: Make sure MySQL server is running!");
            System.out.println("Then run: run.bat (Windows) or ./run.sh (Linux/Mac)");
        } else {
            System.out.println("✗ Some checks failed!");
            System.out.println("Please fix the issues above and try again.");
        }
        System.out.println("========================================");
    }
}
