package ui.panels;

import ui.UIConstants_Enhanced;
import ui.EnhancedComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * Enhanced Dashboard Panel - Professional centered layout with animated action cards
 */
public class DashboardPanel_Enhanced extends JPanel {
    
    private Runnable onCreateEvent, onRegisterDonor, onMakeDonation, onViewEvents, onViewDonors, onViewStatistics;
    
    public DashboardPanel_Enhanced() {
        setBackground(UIConstants_Enhanced.BG_PRIMARY);
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Content - Centered with action cards
        JPanel contentPanel = createContentPanel();
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private JPanel createHeaderPanel() {
        JPanel header = new JPanel();
        header.setBackground(UIConstants_Enhanced.BG_SECONDARY);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(BorderFactory.createEmptyBorder(
            UIConstants_Enhanced.PADDING_XXL,
            UIConstants_Enhanced.PADDING_XXL,
            UIConstants_Enhanced.PADDING_XXL,
            UIConstants_Enhanced.PADDING_XXL
        ));
        
        // Main title
        JLabel titleLabel = EnhancedComponents.createHeadingLabel("🎯 Charity Event Fundraiser");
        titleLabel.setFont(UIConstants_Enhanced.FONT_TITLE);
        titleLabel.setForeground(UIConstants_Enhanced.ACCENT_GREEN);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Subtitle
        JLabel subtitleLabel = new JLabel("Professional Donor & Event Management System");
        subtitleLabel.setFont(UIConstants_Enhanced.FONT_SUBHEADING);
        subtitleLabel.setForeground(UIConstants_Enhanced.TEXT_SECONDARY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        header.add(titleLabel);
        header.add(Box.createVerticalStrut(UIConstants_Enhanced.PADDING_SMALL));
        header.add(subtitleLabel);
        
        return header;
    }
    
    private JPanel createContentPanel() {
        JPanel content = new JPanel();
        content.setBackground(UIConstants_Enhanced.BG_PRIMARY);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(
            UIConstants_Enhanced.PADDING_XLARGE,
            UIConstants_Enhanced.PADDING_XLARGE,
            UIConstants_Enhanced.PADDING_XLARGE,
            UIConstants_Enhanced.PADDING_XLARGE
        ));
        
        // Action cards grid (2x3 centered)
        JPanel cardsPanel = new JPanel();
        cardsPanel.setLayout(new GridLayout(2, 3, UIConstants_Enhanced.MARGIN_XLARGE, UIConstants_Enhanced.MARGIN_XLARGE));
        cardsPanel.setBackground(UIConstants_Enhanced.BG_PRIMARY);
        cardsPanel.setMaximumSize(new Dimension(UIConstants_Enhanced.WINDOW_WIDTH - 80, 400));
        
        cardsPanel.add(createActionCard("📋 Create Event", "Add a new fundraising event", UIConstants_Enhanced.ACCENT_GREEN, () -> {
            if (onCreateEvent != null) onCreateEvent.run();
        }));
        
        cardsPanel.add(createActionCard("👥 Register Donor", "Register a new donor", UIConstants_Enhanced.ACCENT_CYAN, () -> {
            if (onRegisterDonor != null) onRegisterDonor.run();
        }));
        
        cardsPanel.add(createActionCard("💰 Make Donation", "Record a donation pledge", UIConstants_Enhanced.PROGRESS_BLUE, () -> {
            if (onMakeDonation != null) onMakeDonation.run();
        }));
        
        cardsPanel.add(createActionCard("📊 View Events", "See all registered events", UIConstants_Enhanced.ACCENT_YELLOW, () -> {
            if (onViewEvents != null) onViewEvents.run();
        }));
        
        cardsPanel.add(createActionCard("📞 View Donors", "Browse all donors", UIConstants_Enhanced.ACCENT_PURPLE, () -> {
            if (onViewDonors != null) onViewDonors.run();
        }));
        
        cardsPanel.add(createActionCard("📈 Statistics", "View fundraising analytics", UIConstants_Enhanced.SUCCESS_GREEN, () -> {
            if (onViewStatistics != null) onViewStatistics.run();
        }));
        
        content.add(cardsPanel);
        content.add(Box.createVerticalGlue());
        
        return content;
    }
    
    /**
     * Create an animated action card
     */
    private JPanel createActionCard(String title, String description, Color accentColor, Runnable action) {
        JPanel card = new JPanel() {
            private boolean isHovered = false;
            private int shadowOffset = 2;
            
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Shadow effect
                g2.setColor(UIConstants_Enhanced.SHADOW_COLOR);
                g2.fillRoundRect(2, shadowOffset + 2, getWidth() - 4, getHeight() - 4, 
                    UIConstants_Enhanced.BORDER_RADIUS_LARGE, UIConstants_Enhanced.BORDER_RADIUS_LARGE);
                
                // Card background
                g2.setColor(UIConstants_Enhanced.BG_SECONDARY);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 
                    UIConstants_Enhanced.BORDER_RADIUS_LARGE, UIConstants_Enhanced.BORDER_RADIUS_LARGE);
                
                // Left accent border
                g2.setColor(accentColor);
                g2.fillRect(0, 0, 6, getHeight() - 1);
                
                // Card border
                g2.setColor(isHovered ? accentColor : UIConstants_Enhanced.BORDER_LIGHT);
                g2.setStroke(new BasicStroke(isHovered ? 2.0f : 1.0f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 
                    UIConstants_Enhanced.BORDER_RADIUS_LARGE, UIConstants_Enhanced.BORDER_RADIUS_LARGE);
                
                super.paintComponent(g);
            }
        };
        
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(UIConstants_Enhanced.BG_SECONDARY);
        card.setBorder(BorderFactory.createEmptyBorder(UIConstants_Enhanced.PADDING_LARGE, UIConstants_Enhanced.PADDING_LARGE, UIConstants_Enhanced.PADDING_LARGE, UIConstants_Enhanced.PADDING_LARGE));
        card.setPreferredSize(new Dimension(250, 140));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(UIConstants_Enhanced.FONT_SUBHEADING);
        titleLabel.setForeground(accentColor);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Description
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(UIConstants_Enhanced.FONT_SMALL);
        descLabel.setForeground(UIConstants_Enhanced.TEXT_SECONDARY);
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(UIConstants_Enhanced.PADDING_SMALL));
        card.add(descLabel);
        card.add(Box.createVerticalGlue());
        
        // Hover effect
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setCursor(new Cursor(Cursor.HAND_CURSOR));
                card.repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                card.repaint();
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                if (action != null) action.run();
            }
        });
        
        return card;
    }
    
    // Setters for callbacks
    public void setOnCreateEvent(Runnable callback) { this.onCreateEvent = callback; }
    public void setOnRegisterDonor(Runnable callback) { this.onRegisterDonor = callback; }
    public void setOnMakeDonation(Runnable callback) { this.onMakeDonation = callback; }
    public void setOnViewEvents(Runnable callback) { this.onViewEvents = callback; }
    public void setOnViewDonors(Runnable callback) { this.onViewDonors = callback; }
    public void setOnViewStatistics(Runnable callback) { this.onViewStatistics = callback; }
}
