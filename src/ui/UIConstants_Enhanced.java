package ui;

import java.awt.*;

/**
 * UI Constants - Premium Professional Styling with Animations
 * Modern dark theme with smooth effects and animations
 */
public class UIConstants_Enhanced {
    
    // ==================== COLORS ====================
    // Background Colors - Premium Dark Theme
    public static final Color BG_PRIMARY = new Color(15, 15, 30);       // Ultra dark base
    public static final Color BG_SECONDARY = new Color(35, 35, 60);    // Secondary surface
    public static final Color BG_TERTIARY = new Color(50, 50, 80);     // Tertiary card
    public static final Color SURFACE = new Color(40, 40, 65);         // Panel surface
    public static final Color OVERLAY = new Color(0, 0, 0, 100);       // Semi-transparent overlay
    
    // Accent Colors - Professional Gradient Palette
    public static final Color ACCENT_GREEN = new Color(76, 175, 80);   // Success
    public static final Color ACCENT_GREEN_LIGHT = new Color(129, 199, 132); // Lighter green
    public static final Color ACCENT_CYAN = new Color(0, 188, 212);    // Info
    public static final Color ACCENT_CYAN_LIGHT = new Color(77, 215, 255); // Lighter cyan
    public static final Color ACCENT_RED = new Color(244, 67, 54);     // Error
    public static final Color ACCENT_YELLOW = new Color(255, 193, 7);  // Warning
    public static final Color ACCENT_BLUE = new Color(63, 81, 181);    // Primary
    public static final Color ACCENT_PURPLE = new Color(156, 39, 176); // Purple
    public static final Color SUCCESS_GREEN = new Color(102, 187, 106); // Success
    public static final Color PROGRESS_BLUE = new Color(66, 165, 245);  // Progress
    public static final Color DANGER_RED = new Color(229, 57, 53);     // Vibrant red
    
    // Text Colors - Premium Contrast
    public static final Color TEXT_PRIMARY = new Color(240, 240, 250);   // Main text
    public static final Color TEXT_SECONDARY = new Color(190, 190, 210);  // Secondary text
    public static final Color TEXT_HINT = new Color(140, 140, 160);      // Hint text
    public static final Color TEXT_DISABLED = new Color(100, 100, 120);  // Disabled text
    
    // Border & Shadow Colors
    public static final Color BORDER_COLOR = new Color(100, 100, 140);    // Border
    public static final Color BORDER_LIGHT = new Color(120, 120, 160);  // Light border
    public static final Color BORDER_FOCUS = new Color(76, 175, 80);    // Focus border (green)
    public static final Color SHADOW_COLOR = new Color(0, 0, 0, 80);    // Shadow
    
    // ==================== FONTS ====================
    public static final String FONT_FAMILY = "Segoe UI";
    public static final String FONT_MONO = "Consolas";
    public static final Font FONT_TITLE = new Font(FONT_FAMILY, Font.BOLD, 32);
    public static final Font FONT_HEADING = new Font(FONT_FAMILY, Font.BOLD, 22);
    public static final Font FONT_SUBHEADING = new Font(FONT_FAMILY, Font.BOLD, 15);
    public static final Font FONT_BODY = new Font(FONT_FAMILY, Font.PLAIN, 12);
    public static final Font FONT_LABEL = new Font(FONT_FAMILY, Font.BOLD, 11);
    public static final Font FONT_SMALL = new Font(FONT_FAMILY, Font.PLAIN, 10);
    public static final Font FONT_BUTTON = new Font(FONT_FAMILY, Font.BOLD, 13);
    public static final Font FONT_CAPTION = new Font(FONT_FAMILY, Font.PLAIN, 9);
    
    // ==================== DIMENSIONS ====================
    public static final int BUTTON_WIDTH = 160;
    public static final int BUTTON_HEIGHT = 44;
    public static final int BUTTON_SMALL_HEIGHT = 36;
    public static final int BUTTON_LARGE_HEIGHT = 50;
    public static final int FIELD_HEIGHT = 42;
    public static final int FIELD_LARGE_HEIGHT = 50;
    public static final int BORDER_RADIUS = 12;
    public static final int BORDER_RADIUS_SMALL = 6;
    public static final int BORDER_RADIUS_LARGE = 16;
    public static final int PADDING_SMALL = 8;
    public static final int PADDING_MEDIUM = 12;
    public static final int PADDING_LARGE = 16;
    public static final int PADDING_XLARGE = 24;
    public static final int PADDING_XXL = 32;
    public static final int WINDOW_WIDTH = 1400;
    public static final int WINDOW_HEIGHT = 900;
    
    // ==================== SPACING ====================
    public static final int MARGIN_SMALL = 8;
    public static final int MARGIN_MEDIUM = 12;
    public static final int MARGIN_LARGE = 20;
    public static final int MARGIN_XLARGE = 30;
    public static final int MARGIN_XXL = 40;
    
    // ==================== ANIMATIONS ====================
    public static final int ANIMATION_DURATION_FAST = 150;      // Fast animations (ms)
    public static final int ANIMATION_DURATION_NORMAL = 300;    // Normal animations (ms)
    public static final int ANIMATION_DURATION_SLOW = 500;      // Slow animations (ms)
    public static final int ANIMATION_DURATION_VERY_SLOW = 800; // Very slow animations (ms)
    public static final float ANIMATION_EASE_OUT = 0.3f;        // Easing factor
    public static final int HOVER_SCALE = 2;                    // Shadow increase on hover (px)
    public static final int TRANSITION_DELAY = 50;              // Transition delay (ms)
    
    // ==================== EFFECTS ====================
    public static final int SHADOW_OFFSET_X = 0;
    public static final int SHADOW_OFFSET_Y = 2;
    public static final int SHADOW_BLUR = 8;
    public static final int SHADOW_OFFSET_HOVER_Y = 4;
    public static final int SHADOW_BLUR_HOVER = 12;
    public static final float OPACITY_NORMAL = 1.0f;
    public static final float OPACITY_HOVER = 0.95f;
    public static final float OPACITY_DISABLED = 0.5f;
}
