import db.DBConnection;
import ui.MainWindow;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;

/**
 * SwingMain - Entry point for the Swing GUI version of the application
 * Initializes dark theme and launches the main window
 */
public class SwingMain {
    
    public static void main(String[] args) {
        // Initialize database on startup
        System.out.println("? Initializing database...");
        try {
            DBConnection.getConnection();
            System.out.println("✓ Database initialized successfully!");
        } catch (Exception e) {
            System.err.println("✗ Database initialization failed: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        
        // Set dark theme (FlatLaf)
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
            System.out.println("✓ Dark theme applied!");
        } catch (Exception ex) {
            System.out.println("⚠ Could not apply FlatLaf theme, using default look and feel");
            ex.printStackTrace();
        }
        
        // Launch GUI on EDT
        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow();
            window.setVisible(true);
            System.out.println("✓ Application started successfully!");
        });
    }
}
