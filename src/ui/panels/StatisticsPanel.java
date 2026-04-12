package ui.panels;

import ui.UIConstants;
import ui.CustomComponents;

import javax.swing.*;
import java.awt.*;

/**
 * Statistics Panel - Display system-wide statistics
 */
public class StatisticsPanel extends JPanel {
    
    private JLabel totalEventsLabel;
    private JLabel totalDonorsLabel;
    private JLabel totalPledgesLabel;
    private JLabel totalFundsLabel;
    private JButton backButton;
    
    private Runnable onBack;
    
    public StatisticsPanel() {
        setBackground(UIConstants.BG_PRIMARY);
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Content
        JPanel contentPanel = createContentPanel();
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(UIConstants.BG_SECONDARY);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(
            UIConstants.PADDING_LARGE, UIConstants.PADDING_LARGE,
            UIConstants.PADDING_MEDIUM, UIConstants.PADDING_LARGE
        ));
        
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        
        backButton = CustomComponents.createSecondaryButton("← Back");
        backButton.setPreferredSize(new Dimension(100, UIConstants.BUTTON_HEIGHT));
        backButton.setMaximumSize(new Dimension(100, UIConstants.BUTTON_HEIGHT));
        backButton.addActionListener(e -> {
            if (onBack != null) onBack.run();
        });
        
        JLabel titleLabel = CustomComponents.createTitleLabel("System Statistics");
        
        headerPanel.add(backButton);
        headerPanel.add(Box.createHorizontalStrut(UIConstants.PADDING_LARGE));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createHorizontalGlue());
        
        return headerPanel;
    }
    
    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(UIConstants.BG_PRIMARY);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(
            UIConstants.PADDING_LARGE, UIConstants.PADDING_LARGE,
            UIConstants.PADDING_LARGE, UIConstants.PADDING_LARGE
        ));
        contentPanel.setLayout(new GridLayout(2, 2, UIConstants.PADDING_LARGE, UIConstants.PADDING_LARGE));
        
        // Total Events Card (value label is at index 2 due to BoxLayout spacers)
        JPanel eventsCard = createStatCard("📋 Total Events", "0", UIConstants.ACCENT_GREEN);
        totalEventsLabel = (JLabel) eventsCard.getComponent(2);
        
        // Total Donors Card
        JPanel donorsCard = createStatCard("👥 Total Donors", "0", UIConstants.ACCENT_CYAN);
        totalDonorsLabel = (JLabel) donorsCard.getComponent(2);
        
        // Total Pledges Card
        JPanel pledgesCard = createStatCard("💰 Total Donations", "0", UIConstants.PROGRESS_BLUE);
        totalPledgesLabel = (JLabel) pledgesCard.getComponent(2);
        
        // Total Funds Card
        JPanel fundsCard = createStatCard("💵 Total Funds Raised", "₹0.00", UIConstants.SUCCESS_GREEN);
        totalFundsLabel = (JLabel) fundsCard.getComponent(2);
        
        contentPanel.add(eventsCard);
        contentPanel.add(donorsCard);
        contentPanel.add(pledgesCard);
        contentPanel.add(fundsCard);
        
        return contentPanel;
    }
    
    /**
     * Create a statistics card
     */
    private JPanel createStatCard(String title, String value, Color accentColor) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(UIConstants.BG_SECONDARY);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), UIConstants.BORDER_RADIUS, UIConstants.BORDER_RADIUS);
                
                // Accent line
                g2.setColor(accentColor);
                g2.fillRect(0, 0, 8, getHeight());
                
                super.paintComponent(g);
            }
        };
        
        card.setOpaque(false);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(
            UIConstants.PADDING_LARGE, UIConstants.PADDING_LARGE,
            UIConstants.PADDING_LARGE, UIConstants.PADDING_LARGE
        ));
        
        // Title
        JLabel titleLabel = CustomComponents.createBodyLabel(title);
        titleLabel.setFont(UIConstants.FONT_SUBHEADING);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Value
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font(UIConstants.FONT_FAMILY, Font.BOLD, 36));
        valueLabel.setForeground(accentColor);
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(UIConstants.PADDING_SMALL));
        card.add(valueLabel);
        card.add(Box.createVerticalGlue());
        
        return card;
    }
    
    /**
     * Update statistics values
     */
    public void updateStatistics(int totalEvents, int totalDonors, int totalPledges, double totalFunds) {
        totalEventsLabel.setText(String.valueOf(totalEvents));
        totalDonorsLabel.setText(String.valueOf(totalDonors));
        totalPledgesLabel.setText(String.valueOf(totalPledges));
        totalFundsLabel.setText(String.format("₹%.2f", totalFunds));
    }
    
    public void setOnBack(Runnable callback) { this.onBack = callback; }
}
