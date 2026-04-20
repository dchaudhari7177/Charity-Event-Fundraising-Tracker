import db.DBConnection;
import ui.MainWindow_Enhanced;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;

/**
 * SwingMain - Entry point for the Enhanced Swing GUI version
 * Launches a professional, modern dark-themed fundraising application
 */
public class SwingMain {
    
    public static void main(String[] args) {
        // Initialize database on startup
        System.out.println("⚙ Initializing database...");
        try {
            DBConnection.getConnection();
            DBConnection.initializeDatabase(); // Create tables and default admin
            System.out.println("✓ Database initialized successfully!");
        } catch (Exception e) {
            System.err.println("✗ Database initialization failed: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        
        // Set dark theme (FlatLaf)
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
            System.out.println("✓ Premium dark theme applied!");
        } catch (Exception ex) {
            System.out.println("⚠ Could not apply FlatLaf theme, using default look and feel");
            ex.printStackTrace();
        }
        
        // Launch GUI on EDT with enhanced version
        SwingUtilities.invokeLater(() -> {
            new MainWindow_Enhanced();
            System.out.println("✓ Professional UI initialized and ready!");
        });
    }
}
