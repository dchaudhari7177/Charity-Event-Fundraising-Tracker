package ui;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * Custom Styled Components - Reusable UI components with modern styling
 */
public class CustomComponents {
    
    // ==================== STYLED BUTTON ====================
    
    /**
     * Create a primary action button with hover effects
     */
    public static JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        styleButton(button, UIConstants.ACCENT_GREEN);
        return button;
    }
    
    /**
     * Create a secondary action button
     */
    public static JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        styleButton(button, UIConstants.ACCENT_CYAN);
        return button;
    }
    
    /**
     * Create a danger button (for delete/exit)
     */
    public static JButton createDangerButton(String text) {
        JButton button = new JButton(text);
        styleButton(button, UIConstants.ACCENT_RED);
        return button;
    }
    
    /**
     * Apply consistent styling to buttons
     */
    private static void styleButton(JButton button, Color baseColor) {
        button.setFont(UIConstants.FONT_BUTTON);
        button.setPreferredSize(new Dimension(UIConstants.BUTTON_WIDTH, UIConstants.BUTTON_HEIGHT));
        button.setForeground(UIConstants.TEXT_PRIMARY);
        button.setBackground(baseColor);
        button.setBorder(new RoundedBorder(UIConstants.BORDER_RADIUS, baseColor));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(false);
        
        // Hover effect
        Color hoverColor = UIConstants.darker(baseColor, 0.85f);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
                button.setBorder(new RoundedBorder(UIConstants.BORDER_RADIUS, hoverColor));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(baseColor);
                button.setBorder(new RoundedBorder(UIConstants.BORDER_RADIUS, baseColor));
            }
        });
    }
    
    // ==================== STYLED TEXT FIELD ====================
    
    /**
     * Create a styled text field
     */
    public static JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField(placeholder);
        field.setFont(UIConstants.FONT_BODY);
        field.setPreferredSize(new Dimension(300, UIConstants.FIELD_HEIGHT));
        field.setBackground(UIConstants.BG_TERTIARY);
        field.setForeground(UIConstants.TEXT_PRIMARY);
        field.setCaretColor(UIConstants.ACCENT_GREEN);
        field.setBorder(new RoundedBorder(6, UIConstants.ACCENT_GREEN));
        field.setMargin(new Insets(UIConstants.PADDING_SMALL, UIConstants.PADDING_SMALL, 
                                   UIConstants.PADDING_SMALL, UIConstants.PADDING_SMALL));
        return field;
    }
    
    /**
     * Create a styled password field
     */
    public static JPasswordField createStyledPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField(placeholder);
        field.setFont(UIConstants.FONT_BODY);
        field.setPreferredSize(new Dimension(300, UIConstants.FIELD_HEIGHT));
        field.setBackground(UIConstants.BG_TERTIARY);
        field.setForeground(UIConstants.TEXT_PRIMARY);
        field.setCaretColor(UIConstants.ACCENT_GREEN);
        field.setBorder(new RoundedBorder(6, UIConstants.ACCENT_GREEN));
        field.setMargin(new Insets(UIConstants.PADDING_SMALL, UIConstants.PADDING_SMALL, 
                                   UIConstants.PADDING_SMALL, UIConstants.PADDING_SMALL));
        return field;
    }
    
    /**
     * Create a styled combo box
     */
    public static <T> JComboBox<T> createStyledComboBox(T[] items) {
        JComboBox<T> comboBox = new JComboBox<>(items);
        comboBox.setFont(UIConstants.FONT_BODY);
        comboBox.setPreferredSize(new Dimension(300, UIConstants.FIELD_HEIGHT));
        comboBox.setBackground(UIConstants.BG_TERTIARY);
        comboBox.setForeground(UIConstants.TEXT_PRIMARY);
        comboBox.setBorder(new RoundedBorder(6, UIConstants.ACCENT_GREEN));
        return comboBox;
    }
    
    // ==================== STYLED LABEL ====================
    
    /**
     * Create a label with consistent styling
     */
    public static JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }
    
    /**
     * Create a title label
     */
    public static JLabel createTitleLabel(String text) {
        return createLabel(text, UIConstants.FONT_TITLE, UIConstants.TEXT_PRIMARY);
    }
    
    /**
     * Create a heading label
     */
    public static JLabel createHeadingLabel(String text) {
        return createLabel(text, UIConstants.FONT_HEADING, UIConstants.TEXT_PRIMARY);
    }
    
    /**
     * Create a body text label
     */
    public static JLabel createBodyLabel(String text) {
        return createLabel(text, UIConstants.FONT_BODY, UIConstants.TEXT_SECONDARY);
    }
    
    // ==================== CARD PANEL ====================
    
    /**
     * Create a styled card panel
     */
    public static JPanel createCard(String title, int width, int height) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UIConstants.BG_SECONDARY);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), UIConstants.BORDER_RADIUS, UIConstants.BORDER_RADIUS);
                super.paintComponent(g);
            }
        };
        
        card.setPreferredSize(new Dimension(width, height));
        card.setMaximumSize(new Dimension(width, height));
        card.setMinimumSize(new Dimension(width, height));
        card.setOpaque(false);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(
            UIConstants.PADDING_MEDIUM, UIConstants.PADDING_MEDIUM,
            UIConstants.PADDING_MEDIUM, UIConstants.PADDING_MEDIUM
        ));
        
        JLabel titleLabel = createHeadingLabel(title);
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(UIConstants.PADDING_SMALL));
        
        return card;
    }
    
    /**
     * Create a button card for dashboard
     */
    public static JPanel createButtonCard(String title, String description, Runnable onClick) {
        JPanel card = new JPanel() {
            private boolean isHovered = false;
            
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                Color bg = isHovered ? UIConstants.BG_TERTIARY : UIConstants.BG_SECONDARY;
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), UIConstants.BORDER_RADIUS, UIConstants.BORDER_RADIUS);
                
                // Border
                g2.setColor(UIConstants.ACCENT_GREEN);
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, UIConstants.BORDER_RADIUS, UIConstants.BORDER_RADIUS);
                
                super.paintComponent(g);
            }
        };
        
        card.setPreferredSize(new Dimension(UIConstants.CARD_WIDTH, UIConstants.CARD_HEIGHT));
        card.setMaximumSize(new Dimension(UIConstants.CARD_WIDTH, UIConstants.CARD_HEIGHT));
        card.setOpaque(false);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(
            UIConstants.PADDING_MEDIUM, UIConstants.PADDING_MEDIUM,
            UIConstants.PADDING_MEDIUM, UIConstants.PADDING_MEDIUM
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                ((JPanel) e.getSource()).repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                ((JPanel) e.getSource()).repaint();
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                onClick.run();
            }
        });
        
        // Title
        JLabel titleLabel = createHeadingLabel(title);
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(UIConstants.PADDING_TINY));
        
        // Description
        JLabel descLabel = createBodyLabel(description);
        descLabel.setFont(UIConstants.FONT_SMALL);
        card.add(descLabel);
        
        return card;
    }
    
    // ==================== SEPARATOR ====================
    
    /**
     * Create a styled separator
     */
    public static JSeparator createStyledSeparator() {
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(UIConstants.BG_TERTIARY);
        return separator;
    }
    
    // ==================== ROUNDED BORDER ====================
    
    /**
     * Custom border with rounded corners
     */
    public static class RoundedBorder extends AbstractBorder {
        private int radius;
        private Color color;
        
        public RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }
        
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
        
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(5, 5, 5, 5);
        }
    }
}
