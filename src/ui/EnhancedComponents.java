package ui;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * Enhanced Custom Components with Smooth Animations
 * Provides animated buttons, fields, and styled components
 */
public class EnhancedComponents {
    
    // ==================== ANIMATED BUTTONS ====================
    
    /**
     * Create a professional primary button with hover animation
     */
    public static JButton createAnimatedPrimaryButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                
                // Gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, UIConstants_Enhanced.ACCENT_GREEN,
                    0, getHeight(), UIConstants_Enhanced.ACCENT_GREEN_LIGHT
                );
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIConstants_Enhanced.BORDER_RADIUS, UIConstants_Enhanced.BORDER_RADIUS);
                
                // Border
                g2.setColor(UIConstants_Enhanced.BORDER_LIGHT);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIConstants_Enhanced.BORDER_RADIUS, UIConstants_Enhanced.BORDER_RADIUS);
                
                // Text
                super.paintComponent(g);
            }
        };
        styleButton(button, UIConstants_Enhanced.ACCENT_GREEN);
        return button;
    }
    
    /**
     * Create a professional secondary button with hover animation
     */
    public static JButton createAnimatedSecondaryButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                
                // Gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, UIConstants_Enhanced.ACCENT_CYAN,
                    0, getHeight(), UIConstants_Enhanced.ACCENT_CYAN_LIGHT
                );
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIConstants_Enhanced.BORDER_RADIUS, UIConstants_Enhanced.BORDER_RADIUS);
                
                // Border
                g2.setColor(UIConstants_Enhanced.ACCENT_CYAN);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIConstants_Enhanced.BORDER_RADIUS, UIConstants_Enhanced.BORDER_RADIUS);
                
                super.paintComponent(g);
            }
        };
        styleButton(button, UIConstants_Enhanced.ACCENT_CYAN);
        return button;
    }
    
    /**
     * Create a danger button for destructive actions
     */
    public static JButton createAnimatedDangerButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, UIConstants_Enhanced.ACCENT_RED,
                    0, getHeight(), UIConstants_Enhanced.DANGER_RED
                );
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIConstants_Enhanced.BORDER_RADIUS, UIConstants_Enhanced.BORDER_RADIUS);
                
                // Border
                g2.setColor(UIConstants_Enhanced.DANGER_RED);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIConstants_Enhanced.BORDER_RADIUS, UIConstants_Enhanced.BORDER_RADIUS);
                
                super.paintComponent(g);
            }
        };
        styleButton(button, UIConstants_Enhanced.ACCENT_RED);
        return button;
    }
    
    /**
     * Common button styling with hover effects
     */
    private static void styleButton(JButton button, Color accentColor) {
        button.setFont(UIConstants_Enhanced.FONT_BUTTON);
        button.setForeground(UIConstants_Enhanced.TEXT_PRIMARY);
        button.setBackground(UIConstants_Enhanced.BG_SECONDARY);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(UIConstants_Enhanced.BUTTON_WIDTH, UIConstants_Enhanced.BUTTON_HEIGHT));
        
        // Hover and click effects
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(new Color(255, 255, 255, 230));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(UIConstants_Enhanced.TEXT_PRIMARY);
            }
        });
    }
    
    // ==================== ANIMATED TEXT FIELDS ====================
    
    /**
     * Create a styled text field with animated focus border
     */
    public static JTextField createAnimatedTextField(String placeholder) {
        JTextField field = new JTextField(placeholder) {
            private boolean isFocused = false;
            
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Background
                g2.setColor(UIConstants_Enhanced.BG_SECONDARY);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIConstants_Enhanced.BORDER_RADIUS_SMALL, UIConstants_Enhanced.BORDER_RADIUS_SMALL);
                
                // Border with color change on focus
                if (isFocused) {
                    g2.setColor(UIConstants_Enhanced.ACCENT_GREEN);
                    g2.setStroke(new BasicStroke(2.0f));
                } else {
                    g2.setColor(UIConstants_Enhanced.BORDER_COLOR);
                    g2.setStroke(new BasicStroke(1.0f));
                }
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIConstants_Enhanced.BORDER_RADIUS_SMALL, UIConstants_Enhanced.BORDER_RADIUS_SMALL);
                
                super.paintComponent(g);
            }
        };
        
        field.setFont(UIConstants_Enhanced.FONT_BODY);
        field.setForeground(UIConstants_Enhanced.TEXT_PRIMARY);
        field.setCaretColor(UIConstants_Enhanced.ACCENT_GREEN);
        field.setOpaque(false);
        field.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        field.setPreferredSize(new Dimension(300, UIConstants_Enhanced.FIELD_HEIGHT));
        field.setBackground(UIConstants_Enhanced.BG_SECONDARY);
        
        // Focus animation
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                field.repaint();
            }
            
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                field.repaint();
            }
        });
        
        return field;
    }
    
    /**
     * Create a styled password field
     */
    public static JPasswordField createAnimatedPasswordField() {
        JPasswordField field = new JPasswordField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Background
                g2.setColor(UIConstants_Enhanced.BG_SECONDARY);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIConstants_Enhanced.BORDER_RADIUS_SMALL, UIConstants_Enhanced.BORDER_RADIUS_SMALL);
                
                // Border
                g2.setColor(UIConstants_Enhanced.BORDER_COLOR);
                g2.setStroke(new BasicStroke(1.0f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIConstants_Enhanced.BORDER_RADIUS_SMALL, UIConstants_Enhanced.BORDER_RADIUS_SMALL);
                
                super.paintComponent(g);
            }
        };
        
        field.setFont(UIConstants_Enhanced.FONT_BODY);
        field.setForeground(UIConstants_Enhanced.TEXT_PRIMARY);
        field.setCaretColor(UIConstants_Enhanced.ACCENT_GREEN);
        field.setOpaque(false);
        field.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        field.setPreferredSize(new Dimension(300, UIConstants_Enhanced.FIELD_HEIGHT));
        
        return field;
    }
    
    // ==================== ANIMATED LABELS ====================
    
    /**
     * Create a styled label
     */
    public static JLabel createStyledLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }
    
    /**
     * Create body text label
     */
    public static JLabel createBodyLabel(String text) {
        return createStyledLabel(text, UIConstants_Enhanced.FONT_BODY, UIConstants_Enhanced.TEXT_PRIMARY);
    }
    
    /**
     * Create heading label
     */
    public static JLabel createHeadingLabel(String text) {
        return createStyledLabel(text, UIConstants_Enhanced.FONT_HEADING, UIConstants_Enhanced.TEXT_PRIMARY);
    }
    
    // ==================== ANIMATED PANELS ====================
    
    /**
     * Create a styled card panel with hover effect
     */
    public static JPanel createAnimatedCard() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Background with shadow effect
                g2.setColor(UIConstants_Enhanced.SHADOW_COLOR);
                g2.fillRoundRect(1, 2, getWidth() - 2, getHeight() - 2, UIConstants_Enhanced.BORDER_RADIUS, UIConstants_Enhanced.BORDER_RADIUS);
                
                // Main background
                g2.setColor(UIConstants_Enhanced.BG_SECONDARY);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIConstants_Enhanced.BORDER_RADIUS, UIConstants_Enhanced.BORDER_RADIUS);
                
                // Border
                g2.setColor(UIConstants_Enhanced.BORDER_LIGHT);
                g2.setStroke(new BasicStroke(0.5f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIConstants_Enhanced.BORDER_RADIUS, UIConstants_Enhanced.BORDER_RADIUS);
                
                super.paintComponent(g);
            }
        };
    }
    
    /**
     * Create a centered form panel with padding
     */
    public static JPanel createFormPanel(Component... components) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(UIConstants_Enhanced.BG_PRIMARY);
        panel.setBorder(BorderFactory.createEmptyBorder(
            UIConstants_Enhanced.PADDING_XXL,
            UIConstants_Enhanced.PADDING_XXL,
            UIConstants_Enhanced.PADDING_XXL,
            UIConstants_Enhanced.PADDING_XXL
        ));
        
        // Add components with spacing
        for (Component comp : components) {
            if (comp instanceof JComponent) {
                ((JComponent) comp).setAlignmentX(Component.CENTER_ALIGNMENT);
            }
            panel.add(comp);
            panel.add(Box.createVerticalStrut(UIConstants_Enhanced.PADDING_LARGE));
        }
        
        panel.add(Box.createVerticalGlue());
        return panel;
    }
    
    /**
     * Create a centered container panel
     */
    public static JPanel createCenteredPanel(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(UIConstants_Enhanced.BG_PRIMARY);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setAlignmentY(Component.CENTER_ALIGNMENT);
        return panel;
    }
    
    // ==================== ANIMATED BORDERS ====================
    
    /**
     * Create an animated rounded border
     */
    public static AbstractBorder createAnimatedRoundedBorder(Color color, int thickness) {
        return new AbstractBorder() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.setStroke(new BasicStroke(thickness));
                g2.drawRoundRect(x, y, width - 1, height - 1, UIConstants_Enhanced.BORDER_RADIUS, UIConstants_Enhanced.BORDER_RADIUS);
            }
            
            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(5, 5, 5, 5);
            }
        };
    }
    
    // ==================== TEXT AREA ====================
    
    /**
     * Create a styled text area
     */
    public static JTextArea createStyledTextArea(int rows, int cols) {
        JTextArea area = new JTextArea(rows, cols);
        area.setFont(UIConstants_Enhanced.FONT_BODY);
        area.setForeground(UIConstants_Enhanced.TEXT_PRIMARY);
        area.setBackground(UIConstants_Enhanced.BG_SECONDARY);
        area.setCaretColor(UIConstants_Enhanced.ACCENT_GREEN);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createCompoundBorder(
            createAnimatedRoundedBorder(UIConstants_Enhanced.BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        return area;
    }
    
    // ==================== SPINNER ====================
    
    /**
     * Create loading spinner component
     */
    public static JLabel createLoadingSpinner() {
        JLabel spinner = new JLabel("⟳");
        spinner.setFont(new Font(UIConstants_Enhanced.FONT_FAMILY, Font.BOLD, 24));
        spinner.setForeground(UIConstants_Enhanced.ACCENT_GREEN);
        return spinner;
    }
}
