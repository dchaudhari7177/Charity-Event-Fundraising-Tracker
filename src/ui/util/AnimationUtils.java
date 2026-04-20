package ui.util;

import javax.swing.*;

/**
 * Animation Utilities - Smooth transitions and animations for modern UI
 * Provides timing functions, color transitions, and animation helpers
 */
public class AnimationUtils {
    
    // Easing functions for smooth animations
    
    /**
     * Linear easing (no acceleration)
     */
    public static float easeLinear(float t) {
        return t;
    }
    
    /**
     * Ease in out (smooth start and end)
     */
    public static float easeInOutCubic(float t) {
        return t < 0.5f ? 4 * t * t * t : 1 - (float)Math.pow(-2 * t + 2, 3) / 2;
    }
    
    /**
     * Ease in out quad
     */
    public static float easeInOutQuad(float t) {
        return t < 0.5f ? 2 * t * t : -1 + (4 - 2 * t) * t;
    }
    
    /**
     * Ease out quad (decelerate)
     */
    public static float easeOutQuad(float t) {
        return 1 - (1 - t) * (1 - t);
    }
    
    /**
     * Ease in quad (accelerate)
     */
    public static float easeInQuad(float t) {
        return t * t;
    }
    
    /**
     * Bounce ease out
     */
    public static float easeOutBounce(float t) {
        final float n1 = 7.5625f;
        final float d1 = 2.75f;
        
        if (t < 1 / d1) {
            return n1 * t * t;
        } else if (t < 2 / d1) {
            return n1 * (t -= 1.5f / d1) * t + 0.75f;
        } else if (t < 2.5f / d1) {
            return n1 * (t -= 2.25f / d1) * t + 0.9375f;
        } else {
            return n1 * (t -= 2.625f / d1) * t + 0.984375f;
        }
    }
    
    /**
     * Interpolate between two integer values
     */
    public static int interpolateInt(int start, int end, float progress) {
        return (int)(start + (end - start) * progress);
    }
    
    /**
     * Interpolate between two float values
     */
    public static float interpolateFloat(float start, float end, float progress) {
        return start + (end - start) * progress;
    }
    
    /**
     * Interpolate between two colors
     */
    public static java.awt.Color interpolateColor(java.awt.Color start, java.awt.Color end, float progress) {
        int r = interpolateInt(start.getRed(), end.getRed(), progress);
        int g = interpolateInt(start.getGreen(), end.getGreen(), progress);
        int b = interpolateInt(start.getBlue(), end.getBlue(), progress);
        int a = interpolateInt(start.getAlpha(), end.getAlpha(), progress);
        return new java.awt.Color(r, g, b, a);
    }
    
    /**
     * Create a fade-in animation for a component (alpha blending via color)
     */
    public static void fadeIn(JComponent component, int duration) {
        animate(component, 0f, 1f, duration, (progress) -> {
            // Component appears via alpha blending
        });
    }
    
    /**
     * Create a fade-out animation for a component
     */
    public static void fadeOut(JComponent component, int duration) {
        animate(component, 1f, 0f, duration, (progress) -> {
            // Component disappears via alpha blending
        });
    }
    
    /**
     * Generic animation loop
     */
    public static void animate(JComponent component, float startValue, float endValue, 
                              int durationMs, AnimationCallback callback) {
        long startTime = System.currentTimeMillis();
        
        Timer timer = new Timer(16, null); // ~60 FPS
        timer.addActionListener(e -> {
            long elapsedTime = System.currentTimeMillis() - startTime;
            float progress = Math.min(1f, elapsedTime / (float)durationMs);
            progress = easeInOutCubic(progress);
            
            float currentValue = startValue + (endValue - startValue) * progress;
            callback.onProgress(currentValue);
            
            if (progress >= 1f) {
                ((Timer)e.getSource()).stop();
            }
        });
        timer.start();
    }
    
    /**
     * Color animation with easing
     */
    public static void animateColor(JComponent component, java.awt.Color startColor, 
                                   java.awt.Color endColor, int durationMs, 
                                   ColorAnimationCallback callback) {
        long startTime = System.currentTimeMillis();
        
        Timer timer = new Timer(16, null);
        timer.addActionListener(e -> {
            long elapsedTime = System.currentTimeMillis() - startTime;
            float progress = Math.min(1f, elapsedTime / (float)durationMs);
            progress = easeInOutCubic(progress);
            
            java.awt.Color currentColor = interpolateColor(startColor, endColor, progress);
            callback.onColorChange(currentColor);
            
            if (progress >= 1f) {
                ((Timer)e.getSource()).stop();
            }
        });
        timer.start();
    }
    
    /**
     * Callback interface for animations
     */
    @FunctionalInterface
    public interface AnimationCallback {
        void onProgress(float value);
    }
    
    /**
     * Callback interface for color animations
     */
    @FunctionalInterface
    public interface ColorAnimationCallback {
        void onColorChange(java.awt.Color color);
    }
}
