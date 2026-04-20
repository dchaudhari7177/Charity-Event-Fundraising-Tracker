package ui.util;

import java.awt.*;
import java.awt.geom.*;

/**
 * Graphics Utilities - Custom rendering for modern UI effects
 * Handles rounded corners, shadows, gradients, and glows
 */
public class GraphicsUtils {
    
    /**
     * Draw a rounded rectangle with fill
     */
    public static void drawRoundedRect(Graphics2D g2d, int x, int y, int width, int height, 
                                       int radius, Color color) {
        g2d.setColor(color);
        g2d.fillRoundRect(x, y, width, height, radius, radius);
    }
    
    /**
     * Draw a rounded rectangle with stroke
     */
    public static void drawRoundedRectStroke(Graphics2D g2d, int x, int y, int width, int height, 
                                            int radius, Color color, int strokeWidth) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(strokeWidth));
        g2d.drawRoundRect(x, y, width, height, radius, radius);
    }
    
    /**
     * Draw a rounded rectangle with gradient fill
     */
    public static void drawGradientRoundedRect(Graphics2D g2d, int x, int y, int width, int height,
                                              int radius, Color startColor, Color endColor) {
        GradientPaint gradient = new GradientPaint(x, y, startColor, x, y + height, endColor);
        g2d.setPaint(gradient);
        g2d.fillRoundRect(x, y, width, height, radius, radius);
    }
    
    /**
     * Draw a shadow effect around a rounded rectangle
     */
    public static void drawShadow(Graphics2D g2d, int x, int y, int width, int height, 
                                  int radius, int shadowSize, Color shadowColor) {
        // Draw shadow in multiple passes for smooth effect
        for (int i = shadowSize; i > 0; i--) {
            float alpha = (float)(1.0 / shadowSize) * (shadowSize - i);
            Color shadowWithAlpha = new Color(
                shadowColor.getRed() / 255f,
                shadowColor.getGreen() / 255f,
                shadowColor.getBlue() / 255f,
                alpha
            );
            g2d.setColor(shadowWithAlpha);
            g2d.fillRoundRect(x + i, y + i, width - i * 2, height - i * 2, radius, radius);
        }
    }
    
    /**
     * Draw a glow effect
     */
    public static void drawGlow(Graphics2D g2d, int x, int y, int width, int height, 
                               int radius, int glowSize, Color glowColor) {
        Graphics2D g2 = (Graphics2D) g2d.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw glow
        for (int i = glowSize; i > 0; i--) {
            float alpha = (float)(1.0 / glowSize) * (glowSize - i);
            Color glow = new Color(
                glowColor.getRed() / 255f,
                glowColor.getGreen() / 255f,
                glowColor.getBlue() / 255f,
                alpha * 0.5f
            );
            g2.setColor(glow);
            g2.fillRoundRect(x - i, y - i, width + i * 2, height + i * 2, radius + i, radius + i);
        }
        
        g2.dispose();
    }
    
    /**
     * Draw a line with gradient
     */
    public static void drawGradientLine(Graphics2D g2d, int x1, int y1, int x2, int y2, 
                                       Color startColor, Color endColor, int strokeWidth) {
        GradientPaint gradient = new GradientPaint(x1, y1, startColor, x2, y2, endColor);
        g2d.setPaint(gradient);
        g2d.setStroke(new BasicStroke(strokeWidth));
        g2d.drawLine(x1, y1, x2, y2);
    }
    
    /**
     * Apply anti-aliasing and enable rendering hints
     */
    public static void enableHighQualityRendering(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    }
    
    /**
     * Create a Shape for rounded rectangle
     */
    public static Shape createRoundedRectShape(int x, int y, int width, int height, int radius) {
        return new RoundRectangle2D.Double(x, y, width, height, radius, radius);
    }
    
    /**
     * Draw string with shadow effect
     */
    public static void drawShadowString(Graphics2D g2d, String text, int x, int y, 
                                       Color textColor, Color shadowColor, int shadowOffset) {
        // Draw shadow
        g2d.setColor(shadowColor);
        g2d.drawString(text, x + shadowOffset, y + shadowOffset);
        
        // Draw text
        g2d.setColor(textColor);
        g2d.drawString(text, x, y);
    }
    
    /**
     * Fill a rounded rectangle with anti-aliasing
     */
    public static void fillRoundedRect(Graphics2D g2d, int x, int y, int width, int height, 
                                       int radius, Color color) {
        enableHighQualityRendering(g2d);
        g2d.setColor(color);
        g2d.fillRoundRect(x, y, width, height, radius, radius);
    }
    
    /**
     * Create a multi-stop gradient
     */
    public static Paint createMultiGradient(int x, int y, int width, int height, 
                                           Color[] colors, float[] fractions, int direction) {
        if (direction == 0) { // Vertical
            return new LinearGradientPaint(x, y, x, y + height, fractions, colors);
        } else { // Horizontal
            return new LinearGradientPaint(x, y, x + width, y, fractions, colors);
        }
    }
}
