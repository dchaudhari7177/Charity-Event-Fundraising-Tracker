package ui.panels.modern;

import ui.ModernUIConstants;
import ui.components.ModernComponents;
import ui.components.ModernPanelTemplates;

import javax.swing.*;
import java.awt.*;

/**
 * Modern Dashboard Panel - Premium main menu with action cards
 * Features hover effects, smooth animations, and modern card layouts
 */
public class ModernDashboardPanel extends JPanel {
    
    private Runnable onCreateEvent;
    private Runnable onRegisterDonor;
    private Runnable onMakeDonation;
    private Runnable onViewEvents;
    private Runnable onViewDonors;
    private Runnable onViewStatistics;
    
    public ModernDashboardPanel() {
        setBackground(ModernUIConstants.BG_PRIMARY);
        setLayout(new BorderLayout());
        
        // Add header
        add(createHeaderPanel(), BorderLayout.NORTH);
        
        // Add content
        add(createContentPanel(), BorderLayout.CENTER);
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
        
        // Title
        JLabel titleLabel = new ModernComponents.ModernLabel(
            "Charity Event Fundraising Tracker",
            ModernUIConstants.FONT_SIZE_L,
            true
        );
        titleLabel.setForeground(ModernUIConstants.ACCENT_CYAN);
        
        // Subtitle
        JLabel subtitleLabel = new ModernComponents.ModernLabel(
            "Dashboard - Manage your charity events with ease",
            ModernUIConstants.FONT_SIZE_S,
            false
        );
        subtitleLabel.setForeground(ModernUIConstants.TEXT_SECONDARY);
        
        header.add(titleLabel);
        header.add(Box.createVerticalStrut(ModernUIConstants.SPACING_S));
        header.add(subtitleLabel);
        
        return header;
    }
    
    private JPanel createContentPanel() {
        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new GridBagLayout());
        content.setBorder(BorderFactory.createEmptyBorder(
            ModernUIConstants.SPACING_XL,
            ModernUIConstants.SPACING_XL,
            ModernUIConstants.SPACING_XL,
            ModernUIConstants.SPACING_XL
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(
            ModernUIConstants.SPACING_L,
            ModernUIConstants.SPACING_L,
            ModernUIConstants.SPACING_L,
            ModernUIConstants.SPACING_L
        );
        gbc.weightx = 1;
        
        // Create event cards
        gbc.gridy = 0;
        content.add(createActionCard("Create Event", 
            "Add a new charity event", 
            ModernUIConstants.ACCENT_GREEN,
            () -> onCreateEvent.run()), gbc);
        
        gbc.gridy = 1;
        content.add(createActionCard("Register Donor", 
            "Add a new donor to the system", 
            ModernUIConstants.ACCENT_CYAN,
            () -> onRegisterDonor.run()), gbc);
        
        gbc.gridy = 2;
        content.add(createActionCard("Make Donation", 
            "Record a new donation", 
            ModernUIConstants.ACCENT_PURPLE,
            () -> onMakeDonation.run()), gbc);
        
        gbc.gridy = 3;
        content.add(createActionCard("View Events", 
            "See all charity events", 
            ModernUIConstants.ACCENT_YELLOW,
            () -> onViewEvents.run()), gbc);
        
        gbc.gridy = 4;
        content.add(createActionCard("View Donors", 
            "See all registered donors", 
            ModernUIConstants.ACCENT_PINK,
            () -> onViewDonors.run()), gbc);
        
        gbc.gridy = 5;
        content.add(createActionCard("Statistics", 
            "View fundraising analytics", 
            ModernUIConstants.ACCENT_GREEN,
            () -> onViewStatistics.run()), gbc);
        
        // Add vertical glue to push cards to top
        gbc.gridy = 6;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        content.add(Box.createGlue(), gbc);
        
        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(scrollPane, BorderLayout.CENTER);
        return wrapper;
    }
    
    private JPanel createActionCard(String title, String description, 
                                   Color accentColor, Runnable action) {
        JPanel card = new ModernComponents.ModernCardPanel();
        card.setLayout(new BorderLayout(ModernUIConstants.SPACING_L, ModernUIConstants.SPACING_L));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        card.setPreferredSize(new Dimension(Integer.MAX_VALUE, 100));
        
        // Left side - content
        JPanel leftPanel = new JPanel();
        leftPanel.setOpaque(false);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        
        JLabel titleLabel = new ModernComponents.ModernLabel(title, 
            ModernUIConstants.FONT_SIZE_M, true);
        titleLabel.setForeground(accentColor);
        
        JLabel descLabel = new ModernComponents.ModernLabel(description, 
            ModernUIConstants.FONT_SIZE_XS, false);
        descLabel.setForeground(ModernUIConstants.TEXT_SECONDARY);
        
        leftPanel.add(titleLabel);
        leftPanel.add(Box.createVerticalStrut(ModernUIConstants.SPACING_S));
        leftPanel.add(descLabel);
        
        card.add(leftPanel, BorderLayout.CENTER);
        
        // Right side - button
        ModernComponents.ModernButton button = new ModernComponents.ModernButton(
            "Open", accentColor, ModernUIConstants.lighten(accentColor, 0.15));
        button.setPreferredSize(new Dimension(100, ModernUIConstants.BUTTON_HEIGHT));
        button.addActionListener(e -> action.run());
        
        JPanel rightPanel = new JPanel();
        rightPanel.setOpaque(false);
        rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(button);
        
        card.add(rightPanel, BorderLayout.EAST);
        
        // Add mouse hover effect
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                card.setBackground(ModernUIConstants.BG_TERTIARY);
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                card.setBackground(ModernUIConstants.BG_SECONDARY);
            }
        });
        
        return card;
    }
    
    // ==================== CALLBACK SETTERS ====================
    
    public void setOnCreateEvent(Runnable callback) {
        this.onCreateEvent = callback;
    }
    
    public void setOnRegisterDonor(Runnable callback) {
        this.onRegisterDonor = callback;
    }
    
    public void setOnMakeDonation(Runnable callback) {
        this.onMakeDonation = callback;
    }
    
    public void setOnViewEvents(Runnable callback) {
        this.onViewEvents = callback;
    }
    
    public void setOnViewDonors(Runnable callback) {
        this.onViewDonors = callback;
    }
    
    public void setOnViewStatistics(Runnable callback) {
        this.onViewStatistics = callback;
    }
}
