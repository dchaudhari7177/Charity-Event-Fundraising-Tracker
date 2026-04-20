package ui.panels;

import ui.UIConstants_Enhanced;
import ui.EnhancedComponents;
import dao.EventDAO;
import dao.DonorDAO;
import dao.PledgeDAO;
import model.Pledge;
import model.Event;
import observer.DonationObserver;
import observer.DonationEventManager;
import java.awt.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Enhanced Statistics Panel - Animated stat cards with real-time data and observer pattern
 */
public class StatisticsPanel_Enhanced extends JPanel implements DonationObserver {
    
    private JLabel totalEventsLabel;
    private JLabel totalDonorsLabel;
    private JLabel totalPledgesLabel;
    private JLabel totalFundsLabel;
    private Runnable onBack;
    
    public StatisticsPanel_Enhanced() {
        setBackground(UIConstants_Enhanced.BG_PRIMARY);
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Content with stat cards
        JPanel contentPanel = createContentPanel();
        add(contentPanel, BorderLayout.CENTER);
        
        loadStatistics();
        
        // Subscribe to donation events - Observer Pattern
        DonationEventManager.getInstance().subscribe(this);
    }
    
    private JPanel createHeaderPanel() {
        JPanel header = new JPanel();
        header.setBackground(UIConstants_Enhanced.BG_SECONDARY);
        header.setLayout(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(
            UIConstants_Enhanced.PADDING_LARGE,
            UIConstants_Enhanced.PADDING_LARGE,
            UIConstants_Enhanced.PADDING_LARGE,
            UIConstants_Enhanced.PADDING_LARGE
        ));
        
        JLabel titleLabel = EnhancedComponents.createHeadingLabel("📈 Fundraising Analytics");
        titleLabel.setForeground(UIConstants_Enhanced.SUCCESS_GREEN);
        header.add(titleLabel, BorderLayout.WEST);
        
        JButton backButton = EnhancedComponents.createAnimatedSecondaryButton("← Back");
        backButton.addActionListener(e -> {
            if (onBack != null) onBack.run();
        });
        header.add(backButton, BorderLayout.EAST);
        
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
        
        // Stat cards grid (2x2 centered)
        JPanel cardsPanel = new JPanel();
        cardsPanel.setLayout(new GridLayout(2, 2, UIConstants_Enhanced.MARGIN_XLARGE, UIConstants_Enhanced.MARGIN_XLARGE));
        cardsPanel.setBackground(UIConstants_Enhanced.BG_PRIMARY);
        cardsPanel.setMaximumSize(new Dimension(800, 300));
        cardsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Stat cards
        cardsPanel.add(createStatCard("📋 Total Events", "0", UIConstants_Enhanced.ACCENT_GREEN));
        cardsPanel.add(createStatCard("👥 Total Donors", "0", UIConstants_Enhanced.ACCENT_CYAN));
        cardsPanel.add(createStatCard("💰 Total Donations", "0", UIConstants_Enhanced.PROGRESS_BLUE));
        cardsPanel.add(createStatCard("💵 Funds Raised", "₹0.00", UIConstants_Enhanced.SUCCESS_GREEN));
        
        content.add(cardsPanel);
        content.add(Box.createVerticalGlue());
        
        return content;
    }
    
    /**
     * Create an animated stat card
     */
    private JPanel createStatCard(String title, String value, Color accentColor) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Shadow effect
                g2.setColor(UIConstants_Enhanced.SHADOW_COLOR);
                g2.fillRoundRect(2, 4, getWidth() - 4, getHeight() - 4, 
                    UIConstants_Enhanced.BORDER_RADIUS_LARGE, UIConstants_Enhanced.BORDER_RADIUS_LARGE);
                
                // Card background
                g2.setColor(UIConstants_Enhanced.BG_SECONDARY);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 
                    UIConstants_Enhanced.BORDER_RADIUS_LARGE, UIConstants_Enhanced.BORDER_RADIUS_LARGE);
                
                // Accent border (top)
                g2.setColor(accentColor);
                g2.fillRect(0, 0, getWidth() - 1, 5);
                
                // Card border
                g2.setColor(UIConstants_Enhanced.BORDER_LIGHT);
                g2.setStroke(new BasicStroke(0.8f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 
                    UIConstants_Enhanced.BORDER_RADIUS_LARGE, UIConstants_Enhanced.BORDER_RADIUS_LARGE);
                
                super.paintComponent(g);
            }
        };
        
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(UIConstants_Enhanced.BG_SECONDARY);
        card.setBorder(BorderFactory.createEmptyBorder(
            UIConstants_Enhanced.PADDING_XLARGE,
            UIConstants_Enhanced.PADDING_XLARGE,
            UIConstants_Enhanced.PADDING_XLARGE,
            UIConstants_Enhanced.PADDING_XLARGE
        ));
        card.setPreferredSize(new Dimension(300, 180));
        
        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(UIConstants_Enhanced.FONT_SUBHEADING);
        titleLabel.setForeground(accentColor);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Value
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font(UIConstants_Enhanced.FONT_FAMILY, Font.BOLD, 40));
        valueLabel.setForeground(accentColor);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(UIConstants_Enhanced.PADDING_LARGE));
        card.add(valueLabel);
        
        // Store reference for updates
        if (title.contains("Events")) {
            totalEventsLabel = valueLabel;
        } else if (title.contains("Donors")) {
            totalDonorsLabel = valueLabel;
        } else if (title.contains("Donations")) {
            totalPledgesLabel = valueLabel;
        } else if (title.contains("Raised")) {
            totalFundsLabel = valueLabel;
        }
        
        return card;
    }
    
    private void loadStatistics() {
        EventDAO eventDAO = new EventDAO();
        DonorDAO donorDAO = new DonorDAO();
        PledgeDAO pledgeDAO = new PledgeDAO();
        
        int totalEvents = eventDAO.getAllEvents().size();
        int totalDonors = donorDAO.getAllDonors().size();
        int totalPledges = pledgeDAO.getAllPledges().size();
        double totalFunds = pledgeDAO.getAllPledges().stream()
            .mapToDouble(p -> p.getPledgeAmount())
            .sum();
        
        if (totalEventsLabel != null) totalEventsLabel.setText(String.valueOf(totalEvents));
        if (totalDonorsLabel != null) totalDonorsLabel.setText(String.valueOf(totalDonors));
        if (totalPledgesLabel != null) totalPledgesLabel.setText(String.valueOf(totalPledges));
        if (totalFundsLabel != null) totalFundsLabel.setText(String.format("₹%.2f", totalFunds));
    }
    
    /**
     * Observer Pattern: Called when a donation is received
     * Refreshes statistics to show updated totals
     */
    @Override
    public void onDonationReceived(Pledge pledge, Event event) {
        loadStatistics();
    }
    
    /**
     * Observer Pattern: Called when a goal is reached
     * Refreshes statistics display
     */
    @Override
    public void onGoalReached(Event event) {
        loadStatistics();
    }
    
    /**
     * Observer Pattern: Called when event progress is updated
     * Refreshes statistics
     */
    @Override
    public void onProgressUpdated(Event event, double percentageComplete) {
        loadStatistics();
    }
    
    public void setOnBack(Runnable callback) { this.onBack = callback; }
    
    /**
     * Set admin mode (currently just a placeholder for future admin-specific statistics features)
     */
    public void setAdminMode(boolean isAdmin) {
        // Can add admin-specific UI changes here in the future
    }
}
