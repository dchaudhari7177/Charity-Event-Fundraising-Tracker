package ui;

import javax.swing.*;

/**
 * Modern Swing Main - Entry point for the modern UI application
 * Launches the premium dark-themed Charity Event Fundraising Tracker
 */
public class ModernSwingMain {
    
    public static void main(String[] args) {
        // Enable anti-aliasing and high-quality rendering
        System.setProperty("swing.aatext", "true");
        System.setProperty("apple.awt.textantialiasing", "true");
        System.setProperty("awt.useSystemAAFontSettings", "on");
        
        // Launch on EDT
        SwingUtilities.invokeLater(() -> {
            ModernMainWindow window = new ModernMainWindow();
            window.setVisible(true);
        });
    }
}
