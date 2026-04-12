package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

/**
 * Database Connection Manager Class
 * Manages MySQL database connections using JDBC
 * Singleton pattern ensures only one connection at a time
 */
public class DBConnection {
    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/charity_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    
    private static Connection connection = null;

    /**
     * Get a connection to the database
     * Creates connection if not exists, returns existing if already connected
     * 
     * @return Connection object
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Load MySQL JDBC Driver
                Class.forName(DB_DRIVER);
                
                // Create new connection
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                System.out.println("✓ Database connection established successfully!");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("❌ MySQL JDBC Driver not found!");
            System.out.println("   Error: " + e.getMessage());
            System.out.println("   Make sure mysql-connector-java JAR is in lib/ folder");
        } catch (SQLException e) {
            System.out.println("❌ Database connection failed!");
            System.out.println("   Error: " + e.getMessage());
            System.out.println("   Ensure MySQL is running and credentials are correct");
        }
        return connection;
    }

    /**
     * Close the database connection
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✓ Database connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error closing connection: " + e.getMessage());
        }
    }

    /**
     * Initialize database and create tables if they don't exist
     */
    public static void initializeDatabase() {
        Connection conn = getConnection();
        if (conn == null) {
            System.out.println("❌ Cannot initialize database - no connection!");
            return;
        }

        try {
            Statement stmt = conn.createStatement();

            // Create events table
            String createEventsTable = "CREATE TABLE IF NOT EXISTS events (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "description TEXT, " +
                    "target_amount DOUBLE NOT NULL, " +
                    "collected_amount DOUBLE DEFAULT 0)";
            stmt.executeUpdate(createEventsTable);
            System.out.println("✓ Events table ready.");

            // Create donors table
            String createDonorsTable = "CREATE TABLE IF NOT EXISTS donors (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "email VARCHAR(100), " +
                    "phone_number VARCHAR(15))";
            stmt.executeUpdate(createDonorsTable);
            System.out.println("✓ Donors table ready.");

            // Create pledges table
            String createPledgesTable = "CREATE TABLE IF NOT EXISTS pledges (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "donor_id INT NOT NULL, " +
                    "event_id INT NOT NULL, " +
                    "amount DOUBLE NOT NULL, " +
                    "pledge_date DATE NOT NULL, " +
                    "FOREIGN KEY (donor_id) REFERENCES donors(id), " +
                    "FOREIGN KEY (event_id) REFERENCES events(id))";
            stmt.executeUpdate(createPledgesTable);
            System.out.println("✓ Pledges table ready.");

            stmt.close();
            System.out.println("\n✓ Database initialization complete!\n");

        } catch (SQLException e) {
            System.out.println("❌ Error initializing database: " + e.getMessage());
        }
    }

    /**
     * Check if database connection is active
     * 
     * @return true if connected, false otherwise
     */
    public static boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
