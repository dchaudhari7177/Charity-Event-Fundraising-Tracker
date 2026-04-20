package ui.panels.modern;

import ui.ModernUIConstants;
import ui.components.ModernComponents;
import ui.components.ModernPanelTemplates;
import dao.EventDAO;
import dao.DonorDAO;
import dao.PledgeDAO;
import model.Event;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Modern Statistics Panel - Premium analytics dashboard
 * Features stats cards with modern design, animations, and comprehensive data
 */
public class ModernStatisticsPanel extends JPanel {
    
    private Runnable onBackClick;
    private EventDAO eventDAO;
    private DonorDAO donorDAO;
    private PledgeDAO pledgeDAO;
    
    private ModernComponents.ModernButton backButton;
    private ModernComponents.ModernButton refreshButton;
    
    public ModernStatisticsPanel() {
        this.eventDAO = new EventDAO();
        this.donorDAO = new DonorDAO();
        this.pledgeDAO = new PledgeDAO();
        
        setBackground(ModernUIConstants.BG_PRIMARY);
        setLayout(new BorderLayout());
        
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createContentPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel header = new JPanel();
        header.setBackground(ModernUIConstants.BG_SECONDARY);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(BorderFactory.createEmptyBorder(
            ModernUIConstants.SPACING_XL,
            ModernUIConstants.SPACING_XL,
            ModernUIConstants.SPACING_XL,
            ModernUIConstants.SPACING_XL
        ));
        
        JLabel titleLabel = new ModernComponents.ModernLabel("Analytics & Statistics",
            ModernUIConstants.FONT_SIZE_L, true);
        titleLabel.setForeground(ModernUIConstants.ACCENT_CYAN);
        
        JLabel subtitleLabel = new ModernComponents.ModernLabel(
            "Comprehensive fundraising overview and insights",
            ModernUIConstants.FONT_SIZE_S, false);
        subtitleLabel.setForeground(ModernUIConstants.TEXT_SECONDARY);
        
        header.add(titleLabel);
        header.add(Box.createVerticalStrut(ModernUIConstants.SPACING_S));
        header.add(subtitleLabel);
        
        return header;
    }
    
    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(
            ModernUIConstants.SPACING_XL,
            ModernUIConstants.SPACING_XL,
            ModernUIConstants.SPACING_XL,
            ModernUIConstants.SPACING_XL
        ));
        
        // Stats cards grid
        contentPanel.add(createStatsCardsPanel());
        contentPanel.add(Box.createVerticalStrut(ModernUIConstants.SPACING_XL));
        contentPanel.add(createEventDetailsPanel());
        
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(scrollPane);
        
        return wrapper;
    }
    
    private JPanel createStatsCardsPanel() {
        JPanel cardsPanel = new JPanel();
        cardsPanel.setOpaque(false);
        cardsPanel.setLayout(new GridLayout(2, 2, ModernUIConstants.SPACING_XL, ModernUIConstants.SPACING_XL));
        cardsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
        
        // Calculate stats
        List<Event> events = eventDAO.getAllEvents();
        List<String> donors = donorDAO.getAllDonors().stream()
            .map(d -> d.getDonorName())
            .collect(java.util.stream.Collectors.toList());
        
        double totalTarget = 0;
        double totalCollected = 0;
        
        for (Event event : events) {
            totalTarget += event.getTargetAmount();
            totalCollected += event.getCollectedAmount();
        }
        
        double achievementPercent = totalTarget > 0 ? (totalCollected / totalTarget) * 100 : 0;
        
        // Total Events Card
        ModernPanelTemplates.StatsCard eventsCard = new ModernPanelTemplates.StatsCard(
            "Total Events",
            String.valueOf(events.size()),
            events.size() > 0 ? "Active fundraising" : "No events yet"
        );
        cardsPanel.add(eventsCard);
        
        // Total Donors Card
        ModernPanelTemplates.StatsCard donorsCard = new ModernPanelTemplates.StatsCard(
            "Total Donors",
            String.valueOf(donors.size()),
            donors.size() > 0 ? "Supporting the cause" : "No donors yet"
        );
        cardsPanel.add(donorsCard);
        
        // Target Amount Card
        ModernPanelTemplates.StatsCard targetCard = new ModernPanelTemplates.StatsCard(
            "Target Amount",
            "₹" + String.format("%.2f", totalTarget),
            "Combined fundraising goal"
        );
        cardsPanel.add(targetCard);
        
        // Collected Amount Card
        ModernPanelTemplates.StatsCard collectedCard = new ModernPanelTemplates.StatsCard(
            "Amount Collected",
            "₹" + String.format("%.2f", totalCollected),
            String.format("%.1f%% of target", achievementPercent)
        );
        cardsPanel.add(collectedCard);
        
        return cardsPanel;
    }
    
    private JPanel createEventDetailsPanel() {
        ModernPanelTemplates.TitleCardPanel detailsCard = new ModernPanelTemplates.TitleCardPanel("Event Progress");
        
        List<Event> events = eventDAO.getAllEvents();
        
        if (events.isEmpty()) {
            JLabel emptyLabel = new ModernComponents.ModernLabel("No events yet", 
                ModernUIConstants.FONT_SIZE_S, false);
            emptyLabel.setForeground(ModernUIConstants.TEXT_SECONDARY);
            detailsCard.getContentPanel().add(emptyLabel);
        } else {
            for (Event event : events) {
                JPanel eventPanel = createEventProgressItem(event);
                detailsCard.getContentPanel().add(eventPanel);
                detailsCard.getContentPanel().add(Box.createVerticalStrut(ModernUIConstants.SPACING_L));
            }
        }
        
        return detailsCard;
    }
    
    private JPanel createEventProgressItem(Event event) {
        JPanel itemPanel = new JPanel();
        itemPanel.setOpaque(false);
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        itemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        
        // Event name and progress
        JLabel eventNameLabel = new ModernComponents.ModernLabel(
            event.getEventName(),
            ModernUIConstants.FONT_SIZE_S,
            true
        );
        eventNameLabel.setForeground(ModernUIConstants.ACCENT_CYAN);
        
        JLabel amountLabel = new ModernComponents.ModernLabel(
            String.format("₹%.2f / ₹%.2f", event.getCollectedAmount(), event.getTargetAmount()),
            ModernUIConstants.FONT_SIZE_XS,
            false
        );
        amountLabel.setForeground(ModernUIConstants.TEXT_SECONDARY);
        
        // Progress bar
        ModernComponents.ModernProgressBar progressBar = new ModernComponents.ModernProgressBar();
        progressBar.setValue((int)event.getProgressPercentage());
        progressBar.setString(String.format("%.1f%%", event.getProgressPercentage()));
        progressBar.setStringPainted(true);
        progressBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 8));
        
        itemPanel.add(eventNameLabel);
        itemPanel.add(Box.createVerticalStrut(ModernUIConstants.SPACING_S));
        itemPanel.add(amountLabel);
        itemPanel.add(Box.createVerticalStrut(ModernUIConstants.SPACING_M));
        itemPanel.add(progressBar);
        
        return itemPanel;
    }
    
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, ModernUIConstants.SPACING_L, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(
            0,
            ModernUIConstants.SPACING_XL,
            ModernUIConstants.SPACING_XL,
            ModernUIConstants.SPACING_XL
        ));
        
        refreshButton = new ModernComponents.ModernButton("Refresh",
            ModernUIConstants.ACCENT_GREEN, ModernUIConstants.lighten(ModernUIConstants.ACCENT_GREEN, 0.15));
        refreshButton.addActionListener(e -> repaint());
        
        backButton = new ModernComponents.ModernButton("Back",
            ModernUIConstants.TEXT_TERTIARY, ModernUIConstants.TEXT_SECONDARY);
        backButton.addActionListener(e -> {
            if (onBackClick != null) onBackClick.run();
        });
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);
        
        return buttonPanel;
    }
    
    public void setOnBackClick(Runnable callback) {
        this.onBackClick = callback;
    }
}
