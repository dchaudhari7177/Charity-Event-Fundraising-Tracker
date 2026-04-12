package ui;

import java.awt.*;

/**
 * UI Constants - Centralized styling for the entire application
 * Modern dark theme with green accents
 */
public class UIConstants {
    
    // ==================== COLORS ====================
    
    // Primary colors (Dark Theme)
    public static final Color BG_PRIMARY = new Color(30, 30, 47);      // #1e1e2f - Main background
    public static final Color BG_SECONDARY = new Color(45, 45, 65);    // #2d2d41 - Card background
    public static final Color BG_TERTIARY = new Color(60, 60, 85);     // #3c3c55 - Hover background
    
    // Accent colors
    public static final Color ACCENT_GREEN = new Color(76, 175, 80);   // #4CAF50 - Primary accent
    public static final Color ACCENT_CYAN = new Color(0, 173, 181);    // #00ADB5 - Secondary accent
    public static final Color ACCENT_RED = new Color(244, 67, 54);     // #F44336 - Error/Warning
    public static final Color ACCENT_ORANGE = new Color(255, 152, 0);  // #FF9800 - Warning
    
    // Text colors
    public static final Color TEXT_PRIMARY = new Color(255, 255, 255); // #FFFFFF - Main text
    public static final Color TEXT_SECONDARY = new Color(180, 180, 200); // #B4B4C8 - Secondary text
    public static final Color TEXT_MUTED = new Color(120, 120, 140);   // #787C8C - Muted text
    
    // Status colors
    public static final Color SUCCESS_GREEN = new Color(102, 187, 106); // #66BB6A
    public static final Color PROGRESS_BLUE = new Color(66, 165, 245); // #42A5F5
    
    // ==================== FONTS ====================
    
    public static final String FONT_FAMILY = "Segoe UI";
    
    public static final Font FONT_TITLE = new Font(FONT_FAMILY, Font.BOLD, 24);
    public static final Font FONT_HEADING = new Font(FONT_FAMILY, Font.BOLD, 18);
    public static final Font FONT_SUBHEADING = new Font(FONT_FAMILY, Font.BOLD, 14);
    public static final Font FONT_BODY = new Font(FONT_FAMILY, Font.PLAIN, 13);
    public static final Font FONT_SMALL = new Font(FONT_FAMILY, Font.PLAIN, 11);
    public static final Font FONT_BUTTON = new Font(FONT_FAMILY, Font.BOLD, 13);
    
    // ==================== DIMENSIONS ====================
    
    // Spacing & Padding
    public static final int PADDING_LARGE = 20;
    public static final int PADDING_MEDIUM = 15;
    public static final int PADDING_SMALL = 10;
    public static final int PADDING_TINY = 5;
    
    // Component sizes
    public static final int BUTTON_HEIGHT = 40;
    public static final int BUTTON_WIDTH = 180;
    public static final int CARD_WIDTH = 250;
    public static final int CARD_HEIGHT = 120;
    public static final int FIELD_HEIGHT = 35;
    public static final int BORDER_RADIUS = 8;
    
    // Window sizes
    public static final int WINDOW_WIDTH = 1200;
    public static final int WINDOW_HEIGHT = 700;
    
    // ==================== UTILITY METHODS ====================
    
    /**
     * Create a darker shade of a color (for hover effects)
     */
    public static Color darker(Color color, float factor) {
        return new Color(
            Math.max((int)(color.getRed() * factor), 0),
            Math.max((int)(color.getGreen() * factor), 0),
            Math.max((int)(color.getBlue() * factor), 0),
            color.getAlpha()
        );
    }
    
    /**
     * Create a lighter shade of a color
     */
    public static Color lighter(Color color, float factor) {
        return new Color(
            Math.min((int)(color.getRed() + (255 - color.getRed()) * factor), 255),
            Math.min((int)(color.getGreen() + (255 - color.getGreen()) * factor), 255),
            Math.min((int)(color.getBlue() + (255 - color.getBlue()) * factor), 255),
            color.getAlpha()
        );
    }
}
