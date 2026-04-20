package javafx.util;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Animation Utilities for smooth transitions and effects
 */
public class AnimationUtils {
    
    /**
     * Fade in animation
     */
    public static FadeTransition fadeIn(Node node, double duration) {
        FadeTransition fade = new FadeTransition(Duration.millis(duration), node);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);
        return fade;
    }
    
    /**
     * Fade out animation
     */
    public static FadeTransition fadeOut(Node node, double duration) {
        FadeTransition fade = new FadeTransition(Duration.millis(duration), node);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        return fade;
    }
    
    /**
     * Scale up animation (hover effect)
     */
    public static ScaleTransition scaleUp(Node node, double duration, double scale) {
        ScaleTransition scale_trans = new ScaleTransition(Duration.millis(duration), node);
        scale_trans.setToX(scale);
        scale_trans.setToY(scale);
        return scale_trans;
    }
    
    /**
     * Scale down animation
     */
    public static ScaleTransition scaleDown(Node node, double duration, double scale) {
        ScaleTransition scale_trans = new ScaleTransition(Duration.millis(duration), node);
        scale_trans.setToX(scale);
        scale_trans.setToY(scale);
        return scale_trans;
    }
    
    /**
     * Slide in from left
     */
    public static TranslateTransition slideInLeft(Node node, double duration, double distance) {
        TranslateTransition slide = new TranslateTransition(Duration.millis(duration), node);
        slide.setFromX(-distance);
        slide.setToX(0);
        return slide;
    }
    
    /**
     * Slide in from right
     */
    public static TranslateTransition slideInRight(Node node, double duration, double distance) {
        TranslateTransition slide = new TranslateTransition(Duration.millis(duration), node);
        slide.setFromX(distance);
        slide.setToX(0);
        return slide;
    }
    
    /**
     * Slide out to left
     */
    public static TranslateTransition slideOutLeft(Node node, double duration, double distance) {
        TranslateTransition slide = new TranslateTransition(Duration.millis(duration), node);
        slide.setFromX(0);
        slide.setToX(-distance);
        return slide;
    }
    
    /**
     * Slide out to right
     */
    public static TranslateTransition slideOutRight(Node node, double duration, double distance) {
        TranslateTransition slide = new TranslateTransition(Duration.millis(duration), node);
        slide.setFromX(0);
        slide.setToX(distance);
        return slide;
    }
    
    /**
     * Rotate animation
     */
    public static RotateTransition rotate(Node node, double duration, double angle) {
        RotateTransition rotate = new RotateTransition(Duration.millis(duration), node);
        rotate.setByAngle(angle);
        return rotate;
    }
    
    /**
     * Combined fade in and slide from top
     */
    public static ParallelTransition fadeInSlideDown(Node node, double duration) {
        FadeTransition fade = fadeIn(node, duration);
        TranslateTransition slide = new TranslateTransition(Duration.millis(duration), node);
        slide.setFromY(-30);
        slide.setToY(0);
        
        ParallelTransition parallel = new ParallelTransition(fade, slide);
        return parallel;
    }
    
    /**
     * Glow pulse animation effect
     */
    public static ScaleTransition pulse(Node node, double duration) {
        ScaleTransition pulse = new ScaleTransition(Duration.millis(duration), node);
        pulse.setFromX(1.0);
        pulse.setToX(1.05);
        pulse.setFromY(1.0);
        pulse.setToY(1.05);
        pulse.setCycleCount(2);
        pulse.setAutoReverse(true);
        return pulse;
    }
}
