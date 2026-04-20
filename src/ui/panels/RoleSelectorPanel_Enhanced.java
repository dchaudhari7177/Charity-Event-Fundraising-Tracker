package ui.panels;

import ui.UIConstants_Enhanced;
import ui.EnhancedComponents;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Role Selector Panel - Choose between User and Admin access
 */
public class RoleSelectorPanel_Enhanced extends JPanel {
    
    private Runnable onUserSelected;
    private Runnable onAdminLoginSelected;
    
    public RoleSelectorPanel_Enhanced() {
        setBackground(UIConstants_Enhanced.BG_PRIMARY);
        setLayout(new GridBagLayout());
        
        // Create center panel
        JPanel centerPanel = createCenterPanel();
        add(centerPanel);
    }
    
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(UIConstants_Enhanced.BG_SECONDARY);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(
            UIConstants_Enhanced.PADDING_XXL,
            UIConstants_Enhanced.PADDING_XXL,
            UIConstants_Enhanced.PADDING_XXL,
            UIConstants_Enhanced.PADDING_XXL
        ));
        
        // Set size
        panel.setMaximumSize(new Dimension(500, 600));
        panel.setPreferredSize(new Dimension(500, 600));
        
        // Title
        JLabel titleLabel = new JLabel("🎯 Charity Event Fundraiser");
        titleLabel.setFont(UIConstants_Enhanced.FONT_TITLE);
        titleLabel.setForeground(UIConstants_Enhanced.ACCENT_GREEN);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);
        
        panel.add(Box.createVerticalStrut(UIConstants_Enhanced.PADDING_SMALL));
        
        // Subtitle
        JLabel subtitleLabel = new JLabel("Select Your Role");
        subtitleLabel.setFont(UIConstants_Enhanced.FONT_SUBHEADING);
        subtitleLabel.setForeground(UIConstants_Enhanced.TEXT_SECONDARY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(subtitleLabel);
        
        panel.add(Box.createVerticalStrut(UIConstants_Enhanced.PADDING_XLARGE));
        
        // User Button
        JPanel userCard = createRoleCard(
            "👥 User Access",
            "Browse events, make donations\nNo login required",
            UIConstants_Enhanced.ACCENT_CYAN,
            () -> {
                if (onUserSelected != null) onUserSelected.run();
            }
        );
        panel.add(userCard);
        panel.add(Box.createVerticalStrut(UIConstants_Enhanced.PADDING_XLARGE));
        
        // Admin Button
        JPanel adminCard = createRoleCard(
            "🔐 Admin Login",
            "Manage events, view statistics\nSecure access required",
            UIConstants_Enhanced.ACCENT_RED,
            () -> {
                if (onAdminLoginSelected != null) onAdminLoginSelected.run();
            }
        );
        panel.add(adminCard);
        
        panel.add(Box.createVerticalGlue());
        
        // Info section
        JLabel infoLabel = new JLabel("<html><center><b>Admin Credentials:</b><br/>Username: admin<br/>Password: admin123</center></html>");
        infoLabel.setForeground(UIConstants_Enhanced.TEXT_HINT);
        infoLabel.setFont(UIConstants_Enhanced.FONT_SMALL);
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(infoLabel);
        
        return panel;
    }
    
    private JPanel createRoleCard(String title, String description, Color accentColor, Runnable action) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Shadow
                g2.setColor(UIConstants_Enhanced.SHADOW_COLOR);
                g2.fillRoundRect(2, 4, getWidth() - 4, getHeight() - 4,
                    UIConstants_Enhanced.BORDER_RADIUS_LARGE, UIConstants_Enhanced.BORDER_RADIUS_LARGE);
                
                // Card background
                g2.setColor(UIConstants_Enhanced.BG_TERTIARY);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1,
                    UIConstants_Enhanced.BORDER_RADIUS_LARGE, UIConstants_Enhanced.BORDER_RADIUS_LARGE);
                
                // Left accent
                g2.setColor(accentColor);
                g2.fillRect(0, 0, 6, getHeight() - 1);
                
                // Border
                g2.setColor(accentColor);
                g2.setStroke(new BasicStroke(2.0f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1,
                    UIConstants_Enhanced.BORDER_RADIUS_LARGE, UIConstants_Enhanced.BORDER_RADIUS_LARGE);
                
                super.paintComponent(g);
            }
        };
        
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(UIConstants_Enhanced.BG_TERTIARY);
        card.setBorder(new EmptyBorder(UIConstants_Enhanced.PADDING_LARGE, UIConstants_Enhanced.PADDING_LARGE,
            UIConstants_Enhanced.PADDING_LARGE, UIConstants_Enhanced.PADDING_LARGE));
        card.setPreferredSize(new Dimension(Integer.MAX_VALUE, 120));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(UIConstants_Enhanced.FONT_SUBHEADING);
        titleLabel.setForeground(accentColor);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Description
        JLabel descLabel = new JLabel("<html>" + description.replace("\n", "<br/>") + "</html>");
        descLabel.setFont(UIConstants_Enhanced.FONT_SMALL);
        descLabel.setForeground(UIConstants_Enhanced.TEXT_SECONDARY);
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(UIConstants_Enhanced.PADDING_SMALL));
        card.add(descLabel);
        
        // Click listener
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (action != null) action.run();
            }
            
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                card.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });
        
        return card;
    }
    
    public void setOnUserSelected(Runnable callback) {
        this.onUserSelected = callback;
    }
    
    public void setOnAdminLoginSelected(Runnable callback) {
        this.onAdminLoginSelected = callback;
    }
}
