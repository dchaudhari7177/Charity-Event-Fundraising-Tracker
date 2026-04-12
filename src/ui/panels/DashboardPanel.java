package ui.panels;

import ui.UIConstants;
import ui.CustomComponents;

import javax.swing.*;
import java.awt.*;

/**
 * Dashboard Panel - Main menu with action cards
 */
public class DashboardPanel extends JPanel {
    
    private Runnable onCreateEvent;
    private Runnable onRegisterDonor;
    private Runnable onMakeDonation;
    private Runnable onViewEvents;
    private Runnable onViewDonors;
    private Runnable onViewStatistics;
    
    public DashboardPanel() {
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
            UIConstants.PADDING_LARGE, UIConstants.PADDING_LARGE
        ));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        
        JLabel titleLabel = CustomComponents.createTitleLabel("Charity Event Fundraising Tracker");
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel subtitleLabel = CustomComponents.createBodyLabel("Dashboard - Manage your charity events");
        subtitleLabel.setFont(UIConstants.FONT_SMALL);
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(UIConstants.PADDING_SMALL));
        headerPanel.add(subtitleLabel);
        
        return headerPanel;
    }
    
    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(UIConstants.BG_PRIMARY);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(
            UIConstants.PADDING_LARGE, UIConstants.PADDING_LARGE,
            UIConstants.PADDING_LARGE, UIConstants.PADDING_LARGE
        ));
        
        // Create a scrollable panel for cards
        JPanel cardContainer = new JPanel();
        cardContainer.setBackground(UIConstants.BG_PRIMARY);
        cardContainer.setLayout(new GridLayout(2, 3, UIConstants.PADDING_LARGE, UIConstants.PADDING_LARGE));
        
        // Create Event Card
        JPanel createEventCard = CustomComponents.createButtonCard(
            "📋 Create Event",
            "Start a new charity event",
            () -> {
                if (onCreateEvent != null) onCreateEvent.run();
            }
        );
        
        // Register Donor Card
        JPanel registerDonorCard = CustomComponents.createButtonCard(
            "👤 Register Donor",
            "Add a new donor to the system",
            () -> {
                if (onRegisterDonor != null) onRegisterDonor.run();
            }
        );
        
        // Make Donation Card
        JPanel makeDonationCard = CustomComponents.createButtonCard(
            "💰 Make Donation",
            "Record a donation for an event",
            () -> {
                if (onMakeDonation != null) onMakeDonation.run();
            }
        );
        
        // View Events Card
        JPanel viewEventsCard = CustomComponents.createButtonCard(
            "📊 View Events",
            "See all charity events and their progress",
            () -> {
                if (onViewEvents != null) onViewEvents.run();
            }
        );
        
        // View Donors Card
        JPanel viewDonorsCard = CustomComponents.createButtonCard(
            "👥 View Donors",
            "See all registered donors",
            () -> {
                if (onViewDonors != null) onViewDonors.run();
            }
        );
        
        // View Statistics Card
        JPanel statsCard = CustomComponents.createButtonCard(
            "📈 Statistics",
            "View system-wide statistics",
            () -> {
                if (onViewStatistics != null) onViewStatistics.run();
            }
        );
        
        cardContainer.add(createEventCard);
        cardContainer.add(registerDonorCard);
        cardContainer.add(makeDonationCard);
        cardContainer.add(viewEventsCard);
        cardContainer.add(viewDonorsCard);
        cardContainer.add(statsCard);
        
        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(cardContainer);
        scrollPane.setBackground(UIConstants.BG_PRIMARY);
        scrollPane.getViewport().setBackground(UIConstants.BG_PRIMARY);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        return contentPanel;
    }
    
    // Setters for action callbacks
    public void setOnCreateEvent(Runnable callback) { this.onCreateEvent = callback; }
    public void setOnRegisterDonor(Runnable callback) { this.onRegisterDonor = callback; }
    public void setOnMakeDonation(Runnable callback) { this.onMakeDonation = callback; }
    public void setOnViewEvents(Runnable callback) { this.onViewEvents = callback; }
    public void setOnViewDonors(Runnable callback) { this.onViewDonors = callback; }
    public void setOnViewStatistics(Runnable callback) { this.onViewStatistics = callback; }
}
