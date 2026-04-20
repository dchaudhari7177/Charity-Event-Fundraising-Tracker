package ui;

import java.awt.*;

/**
 * Modern UI Constants - Premium dark theme with sophisticated design system
 * Implements modern design principles with gradients, shadows, and smooth transitions
 */
public class ModernUIConstants {
    
    // ==================== DESIGN TOKENS ====================
    
    // Primary Colors - Deep Navy Dark Theme
    public static final Color BG_PRIMARY = new Color(13, 17, 27);        // #0D111B - Deep background
    public static final Color BG_SECONDARY = new Color(25, 32, 50);      // #192032 - Cards/panels
    public static final Color BG_TERTIARY = new Color(35, 45, 70);       // #232D46 - Hover state
    public static final Color BG_ELEVATED = new Color(45, 55, 85);       // #2D3755 - Most elevated
    
    // Accent Colors - Modern Neon Palette
    public static final Color ACCENT_CYAN = new Color(0, 241, 254);      // #00F1FE - Neon cyan
    public static final Color ACCENT_GREEN = new Color(16, 255, 130);    // #10FF82 - Neon green
    public static final Color ACCENT_PURPLE = new Color(200, 100, 255);  // #C864FF - Purple
    public static final Color ACCENT_PINK = new Color(255, 71, 135);     // #FF4787 - Pink
    public static final Color ACCENT_YELLOW = new Color(255, 215, 0);    // #FFD700 - Gold
    
    // Semantic Colors
    public static final Color SUCCESS = new Color(16, 255, 130);          // Green for success
    public static final Color ERROR = new Color(255, 71, 135);            // Red/Pink for errors
    public static final Color WARNING = new Color(255, 193, 7);           // Yellow for warnings
    public static final Color INFO = new Color(0, 241, 254);              // Cyan for info
    
    // Text Colors
    public static final Color TEXT_PRIMARY = new Color(255, 255, 255);    // #FFFFFF - Main text
    public static final Color TEXT_SECONDARY = new Color(200, 205, 220); // #C8CDDC - Secondary text
    public static final Color TEXT_TERTIARY = new Color(140, 150, 170);   // #8C96AA - Tertiary text
    public static final Color TEXT_DISABLED = new Color(90, 100, 120);    // #5A6478 - Disabled text
    
    // Border Colors
    public static final Color BORDER_LIGHT = new Color(60, 75, 110);      // #3C4B6E - Light border
    public static final Color BORDER_ACCENT = new Color(0, 241, 254);     // Accent border
    
    // Shadow & Gradient Support
    public static final Color SHADOW_COLOR = new Color(0, 0, 0, 80);      // Semi-transparent black
    public static final Color GLOW_COLOR = new Color(0, 241, 254, 100);   // Cyan glow
    
    // ==================== FONTS - MODERN TYPOGRAPHY ====================
    
    // Font families (fallback chain)
    public static final String FONT_PRIMARY = "Segoe UI";
    public static final String FONT_SECONDARY = "Poppins";
    public static final String FONT_MONOSPACE = "JetBrains Mono";
    
    // Font sizes
    public static final int FONT_SIZE_XL = 32;      // Page titles
    public static final int FONT_SIZE_L = 24;       // Section titles
    public static final int FONT_SIZE_M = 16;       // Headings
    public static final int FONT_SIZE_S = 14;       // Body text
    public static final int FONT_SIZE_XS = 12;      // Small text
    public static final int FONT_SIZE_XXS = 11;     // Tiny text
    
    // Font instances
    public static final Font FONT_TITLE_XL = new Font(FONT_PRIMARY, Font.BOLD, FONT_SIZE_XL);
    public static final Font FONT_TITLE_L = new Font(FONT_PRIMARY, Font.BOLD, FONT_SIZE_L);
    public static final Font FONT_TITLE_M = new Font(FONT_PRIMARY, Font.BOLD, FONT_SIZE_M);
    public static final Font FONT_HEADING = new Font(FONT_PRIMARY, Font.BOLD, 18);
    public static final Font FONT_SUBHEADING = new Font(FONT_PRIMARY, Font.BOLD, 14);
    public static final Font FONT_BODY = new Font(FONT_PRIMARY, Font.PLAIN, FONT_SIZE_S);
    public static final Font FONT_BODY_BOLD = new Font(FONT_PRIMARY, Font.BOLD, FONT_SIZE_S);
    public static final Font FONT_SMALL = new Font(FONT_PRIMARY, Font.PLAIN, FONT_SIZE_XS);
    public static final Font FONT_SMALL_BOLD = new Font(FONT_PRIMARY, Font.BOLD, FONT_SIZE_XS);
    public static final Font FONT_BUTTON = new Font(FONT_PRIMARY, Font.BOLD, 13);
    public static final Font FONT_LABEL = new Font(FONT_PRIMARY, Font.PLAIN, 12);
    public static final Font FONT_CODE = new Font(FONT_MONOSPACE, Font.PLAIN, 11);
    
    // ==================== SPACING & LAYOUT ====================
    
    // Padding values (8px base unit)
    public static final int SPACING_XS = 4;
    public static final int SPACING_S = 8;
    public static final int SPACING_M = 12;
    public static final int SPACING_L = 16;
    public static final int SPACING_XL = 24;
    public static final int SPACING_XXL = 32;
    
    // Border radius (corner rounding)
    public static final int BORDER_RADIUS_S = 8;
    public static final int BORDER_RADIUS_M = 12;
    public static final int BORDER_RADIUS_L = 16;
    public static final int BORDER_RADIUS_XL = 20;
    
    // Component dimensions
    public static final int BUTTON_HEIGHT = 40;
    public static final int BUTTON_HEIGHT_SMALL = 32;
    public static final int INPUT_HEIGHT = 40;
    public static final int INPUT_HEIGHT_SMALL = 32;
    public static final int ICON_SIZE_SMALL = 16;
    public static final int ICON_SIZE_MEDIUM = 24;
    public static final int ICON_SIZE_LARGE = 32;
    
    // ==================== WINDOW DIMENSIONS ====================
    
    public static final int WINDOW_WIDTH = 1200;
    public static final int WINDOW_HEIGHT = 800;
    public static final int WINDOW_MIN_WIDTH = 800;
    public static final int WINDOW_MIN_HEIGHT = 600;
    
    // ==================== ANIMATION SETTINGS ====================
    
    public static final int ANIMATION_DURATION_FAST = 150;      // Quick interactions
    public static final int ANIMATION_DURATION_NORMAL = 300;    // Standard animations
    public static final int ANIMATION_DURATION_SLOW = 500;      // Entrance animations
    
    // ==================== SHADOW & DEPTH ====================
    
    public static final int SHADOW_OFFSET_X = 0;
    public static final int SHADOW_OFFSET_Y = 4;
    public static final int SHADOW_BLUR_RADIUS = 8;
    
    public static final int SHADOW_OFFSET_X_ELEVATED = 0;
    public static final int SHADOW_OFFSET_Y_ELEVATED = 8;
    public static final int SHADOW_BLUR_RADIUS_ELEVATED = 16;
    
    // ==================== TABLE STYLING ====================
    
    public static final Color TABLE_ROW_ALTERNATE = new Color(25, 32, 50);
    public static final Color TABLE_ROW_HOVER = new Color(45, 60, 95);
    public static final Color TABLE_HEADER_BG = new Color(0, 241, 254, 15);
    public static final Color TABLE_GRID_COLOR = new Color(60, 75, 110);
    
    // ==================== TRANSITION CURVES ====================
    
    // Easing values (0.0 to 1.0)
    public static final float EASING_LINEAR = 0.0f;
    public static final float EASING_EASE_IN_OUT = 0.4f;
    
    // ==================== BREAKPOINTS ====================
    
    public static final int BREAKPOINT_MOBILE = 480;
    public static final int BREAKPOINT_TABLET = 768;
    public static final int BREAKPOINT_DESKTOP = 1024;
    
    // ==================== Z-INDEX LAYERS ====================
    
    public static final int Z_INDEX_BACKGROUND = 0;
    public static final int Z_INDEX_DEFAULT = 1;
    public static final int Z_INDEX_ELEVATED = 2;
    public static final int Z_INDEX_MODAL = 100;
    
    // ==================== HELPER METHODS ====================
    
    /**
     * Create a gradient paint from top to bottom
     */
    public static GradientPaint createVerticalGradient(Color start, Color end, int height) {
        return new GradientPaint(0, 0, start, 0, height, end);
    }
    
    /**
     * Create a gradient paint from left to right
     */
    public static GradientPaint createHorizontalGradient(Color start, Color end, int width) {
        return new GradientPaint(0, 0, start, width, 0, end);
    }
    
    /**
     * Create a semi-transparent color
     */
    public static Color withAlpha(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }
    
    /**
     * Lighten a color by percentage (0-1)
     */
    public static Color lighten(Color color, double amount) {
        int r = Math.min((int)(color.getRed() * (1 + amount)), 255);
        int g = Math.min((int)(color.getGreen() * (1 + amount)), 255);
        int b = Math.min((int)(color.getBlue() * (1 + amount)), 255);
        return new Color(r, g, b, color.getAlpha());
    }
    
    /**
     * Darken a color by percentage (0-1)
     */
    public static Color darken(Color color, double amount) {
        int r = Math.max((int)(color.getRed() * (1 - amount)), 0);
        int g = Math.max((int)(color.getGreen() * (1 - amount)), 0);
        int b = Math.max((int)(color.getBlue() * (1 - amount)), 0);
        return new Color(r, g, b, color.getAlpha());
    }
}
