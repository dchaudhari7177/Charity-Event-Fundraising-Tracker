package ui.components;

import ui.ModernUIConstants;
import ui.util.AnimationUtils;
import ui.util.GraphicsUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Modern Custom Components - Production-grade styled UI elements
 * Includes animated buttons, input fields, cards, and more
 */
public class ModernComponents {
    
    // ==================== MODERN BUTTON ====================
    
    public static class ModernButton extends JButton {
        private Color startColor;
        private Color endColor;
        private Color hoverColor;
        private Color pressedColor;
        private boolean isHovered = false;
        private boolean isPressed = false;
        private int cornerRadius = ModernUIConstants.BORDER_RADIUS_M;
        
        public ModernButton(String text) {
            this(text, ModernUIConstants.ACCENT_CYAN, ModernUIConstants.ACCENT_GREEN);
        }
        
        public ModernButton(String text, Color startColor, Color endColor) {
            super(text);
            this.startColor = startColor;
            this.endColor = endColor;
            this.hoverColor = ModernUIConstants.lighten(endColor, 0.15);
            this.pressedColor = ModernUIConstants.darken(endColor, 0.2);
            
            setupStyle();
        }
        
        private void setupStyle() {
            setFont(ModernUIConstants.FONT_BUTTON);
            setForeground(ModernUIConstants.TEXT_PRIMARY);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setOpaque(false);
            setPreferredSize(new Dimension(120, ModernUIConstants.BUTTON_HEIGHT));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            // Add listeners for hover and press effects
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    isHovered = true;
                    repaint();
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    isHovered = false;
                    isPressed = false;
                    repaint();
                }
                
                @Override
                public void mousePressed(MouseEvent e) {
                    isPressed = true;
                    repaint();
                }
                
                @Override
                public void mouseReleased(MouseEvent e) {
                    isPressed = false;
                    repaint();
                }
            });
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            GraphicsUtils.enableHighQualityRendering(g2d);
            
            int width = getWidth();
            int height = getHeight();
            
            // Determine colors based on state
            Color currentEndColor = endColor;
            if (isPressed) {
                currentEndColor = pressedColor;
            } else if (isHovered) {
                currentEndColor = hoverColor;
            }
            
            // Draw gradient background
            GradientPaint gradient = new GradientPaint(0, 0, startColor, width, height, currentEndColor);
            g2d.setPaint(gradient);
            g2d.fillRoundRect(0, 0, width, height, cornerRadius, cornerRadius);
            
            // Draw glow effect when hovered
            if (isHovered) {
                g2d.setColor(ModernUIConstants.GLOW_COLOR);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, width - 2, height - 2, cornerRadius, cornerRadius);
            }
            
            // Draw text
            FontMetrics fm = g2d.getFontMetrics();
            String text = getText();
            int x = (width - fm.stringWidth(text)) / 2;
            int y = ((height - fm.getHeight()) / 2) + fm.getAscent();
            
            g2d.setColor(ModernUIConstants.TEXT_PRIMARY);
            g2d.setFont(getFont());
            g2d.drawString(text, x, y);
            
            g2d.dispose();
        }
    }
    
    // ==================== MODERN TEXT FIELD ====================
    
    public static class ModernTextField extends JTextField {
        private Color focusColor = ModernUIConstants.ACCENT_CYAN;
        private Color defaultBorderColor = ModernUIConstants.BORDER_LIGHT;
        private boolean isFocused = false;
        private int cornerRadius = ModernUIConstants.BORDER_RADIUS_S;
        private int borderWidth = 2;
        
        public ModernTextField() {
            this("", 15);
        }
        
        public ModernTextField(String text, int columns) {
            super(text, columns);
            setupStyle();
        }
        
        private void setupStyle() {
            setBackground(ModernUIConstants.BG_SECONDARY);
            setForeground(ModernUIConstants.TEXT_PRIMARY);
            setCaretColor(ModernUIConstants.ACCENT_CYAN);
            setFont(ModernUIConstants.FONT_BODY);
            setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
            setOpaque(false);
            setPreferredSize(new Dimension(200, ModernUIConstants.INPUT_HEIGHT));
            
            addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    isFocused = true;
                    repaint();
                }
                
                @Override
                public void focusLost(FocusEvent e) {
                    isFocused = false;
                    repaint();
                }
            });
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            GraphicsUtils.enableHighQualityRendering(g2d);
            
            int width = getWidth();
            int height = getHeight();
            
            // Draw background
            g2d.setColor(ModernUIConstants.BG_SECONDARY);
            g2d.fillRoundRect(0, 0, width, height, cornerRadius, cornerRadius);
            
            // Draw border with glow effect when focused
            if (isFocused) {
                g2d.setColor(focusColor);
                g2d.setStroke(new BasicStroke(borderWidth));
                g2d.drawRoundRect(borderWidth / 2, borderWidth / 2, 
                                width - borderWidth, height - borderWidth, 
                                cornerRadius, cornerRadius);
                
                // Draw glow
                g2d.setColor(ModernUIConstants.withAlpha(focusColor, 50));
                for (int i = 0; i < 3; i++) {
                    g2d.drawRoundRect(-i, -i, width + i * 2, height + i * 2, 
                                    cornerRadius + i, cornerRadius + i);
                }
            } else {
                g2d.setColor(defaultBorderColor);
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRoundRect(0, 0, width - 1, height - 1, cornerRadius, cornerRadius);
            }
            
            g2d.dispose();
            super.paintComponent(g);
        }
    }
    
    // ==================== MODERN CARD PANEL ====================
    
    public static class ModernCardPanel extends JPanel {
        private int cornerRadius = ModernUIConstants.BORDER_RADIUS_L;
        private int shadowSize = 8;
        private Color shadowColor = ModernUIConstants.SHADOW_COLOR;
        
        public ModernCardPanel() {
            super();
            setBackground(ModernUIConstants.BG_SECONDARY);
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(
                ModernUIConstants.SPACING_L,
                ModernUIConstants.SPACING_L,
                ModernUIConstants.SPACING_L,
                ModernUIConstants.SPACING_L
            ));
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            GraphicsUtils.enableHighQualityRendering(g2d);
            
            int width = getWidth();
            int height = getHeight();
            
            // Draw shadow
            GraphicsUtils.drawShadow(g2d, 0, 0, width, height, 
                                    cornerRadius, shadowSize, shadowColor);
            
            // Draw card background
            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, width, height, cornerRadius, cornerRadius);
            
            // Draw subtle border
            g2d.setColor(ModernUIConstants.BORDER_LIGHT);
            g2d.setStroke(new BasicStroke(1));
            g2d.drawRoundRect(0, 0, width - 1, height - 1, cornerRadius, cornerRadius);
            
            g2d.dispose();
            super.paintComponent(g);
        }
    }
    
    // ==================== MODERN LABEL ====================
    
    public static class ModernLabel extends JLabel {
        public ModernLabel(String text, int fontSize, boolean bold) {
            super(text);
            Font font = new Font(ModernUIConstants.FONT_PRIMARY, bold ? Font.BOLD : Font.PLAIN, fontSize);
            setFont(font);
            setForeground(ModernUIConstants.TEXT_PRIMARY);
        }
        
        public ModernLabel(String text) {
            this(text, ModernUIConstants.FONT_SIZE_S, false);
        }
    }
    
    // ==================== MODERN COMBO BOX ====================
    
    public static class ModernComboBox<T> extends JComboBox<T> {
        public ModernComboBox(T[] items) {
            super(items);
            setupStyle();
        }
        
        private void setupStyle() {
            setBackground(ModernUIConstants.BG_SECONDARY);
            setForeground(ModernUIConstants.TEXT_PRIMARY);
            setFont(ModernUIConstants.FONT_BODY);
            setFocusable(true);
            setPreferredSize(new Dimension(200, ModernUIConstants.INPUT_HEIGHT));
            
            // Custom renderer for dropdown
            setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, 
                                                             int index, boolean isSelected, 
                                                             boolean cellHasFocus) {
                    JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                    
                    if (isSelected) {
                        label.setBackground(ModernUIConstants.BG_TERTIARY);
                        label.setForeground(ModernUIConstants.ACCENT_CYAN);
                    } else {
                        label.setBackground(ModernUIConstants.BG_SECONDARY);
                        label.setForeground(ModernUIConstants.TEXT_PRIMARY);
                    }
                    
                    label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                    return label;
                }
            });
        }
    }
    
    // ==================== MODERN PROGRESS BAR ====================
    
    public static class ModernProgressBar extends JProgressBar {
        private Color progressColor = ModernUIConstants.ACCENT_GREEN;
        private Color backgroundColor = ModernUIConstants.BG_TERTIARY;
        
        public ModernProgressBar() {
            super();
            setupStyle();
        }
        
        private void setupStyle() {
            setOpaque(false);
            setPreferredSize(new Dimension(200, 8));
            setForeground(progressColor);
            setBackground(backgroundColor);
            setBorder(BorderFactory.createEmptyBorder());
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            GraphicsUtils.enableHighQualityRendering(g2d);
            
            int width = getWidth();
            int height = getHeight();
            
            // Draw background
            g2d.setColor(backgroundColor);
            g2d.fillRoundRect(0, 0, width, height, height, height);
            
            // Draw progress
            int progress = (int) ((getPercentComplete()) * width);
            g2d.setColor(progressColor);
            g2d.fillRoundRect(0, 0, progress, height, height, height);
            
            g2d.dispose();
        }
    }
    
    // ==================== MODERN SEPARATOR ====================
    
    public static class ModernSeparator extends JPanel {
        private Color lineColor = ModernUIConstants.BORDER_LIGHT;
        
        public ModernSeparator() {
            setOpaque(false);
            setPreferredSize(new Dimension(100, 1));
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(lineColor);
            g2d.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
            g2d.dispose();
        }
    }
}
