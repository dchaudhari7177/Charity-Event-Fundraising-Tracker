package ui.panels.modern;

import ui.ModernUIConstants;
import ui.components.ModernComponents;
import ui.components.ModernPanelTemplates;
import dao.DonorDAO;
import dao.EventDAO;
import dao.PledgeDAO;
import model.Donor;
import model.Event;
import model.Pledge;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Modern Make Donation Panel - Premium form for recording donations
 * Features dropdown selection, validation, and smooth interactions
 */
public class ModernMakeDonationPanel extends JPanel {
    
    private Runnable onBackClick;
    private DonorDAO donorDAO;
    private EventDAO eventDAO;
    private PledgeDAO pledgeDAO;
    
    // Form components
    private ModernComponents.ModernComboBox<String> donorComboBox;
    private ModernComponents.ModernComboBox<String> eventComboBox;
    private ModernComponents.ModernTextField amountField;
    private ModernComponents.ModernButton donateButton;
    private ModernComponents.ModernButton resetButton;
    private ModernComponents.ModernButton backButton;
    private JLabel errorLabel;
    
    private List<Donor> donors;
    private List<Event> events;
    
    public ModernMakeDonationPanel() {
        this.donorDAO = new DonorDAO();
        this.eventDAO = new EventDAO();
        this.pledgeDAO = new PledgeDAO();
        
        setBackground(ModernUIConstants.BG_PRIMARY);
        setLayout(new GridBagLayout());
        
        loadData();
        initializeComponents();
        layoutComponents();
    }
    
    private void loadData() {
        donors = donorDAO.getAllDonors();
        events = eventDAO.getAllEvents();
    }
    
    private void initializeComponents() {
        // Donor combo box
        String[] donorNames = new String[donors.size()];
        for (int i = 0; i < donors.size(); i++) {
            donorNames[i] = donors.get(i).getDonorName();
        }
        donorComboBox = new ModernComponents.ModernComboBox<>(donorNames);
        
        // Event combo box
        String[] eventNames = new String[events.size()];
        for (int i = 0; i < events.size(); i++) {
            eventNames[i] = events.get(i).getEventName();
        }
        eventComboBox = new ModernComponents.ModernComboBox<>(eventNames);
        
        // Amount field
        amountField = new ModernComponents.ModernTextField("", 20);
        
        // Buttons
        donateButton = new ModernComponents.ModernButton("Make Donation",
            ModernUIConstants.ACCENT_GREEN, ModernUIConstants.lighten(ModernUIConstants.ACCENT_GREEN, 0.15));
        
        resetButton = new ModernComponents.ModernButton("Reset",
            ModernUIConstants.ACCENT_PURPLE, ModernUIConstants.lighten(ModernUIConstants.ACCENT_PURPLE, 0.15));
        
        backButton = new ModernComponents.ModernButton("Back",
            ModernUIConstants.TEXT_TERTIARY, ModernUIConstants.TEXT_SECONDARY);
        
        errorLabel = new JLabel();
        errorLabel.setForeground(ModernUIConstants.ERROR);
        errorLabel.setFont(ModernUIConstants.FONT_SMALL);
    }
    
    private void layoutComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        
        // Centered card
        ModernPanelTemplates.CenteredCardPanel centeredPanel = new ModernPanelTemplates.CenteredCardPanel();
        ModernComponents.ModernCardPanel card = centeredPanel.getCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        
        // Header
        JLabel titleLabel = new ModernComponents.ModernLabel("Make a Donation",
            ModernUIConstants.FONT_SIZE_L, true);
        titleLabel.setForeground(ModernUIConstants.ACCENT_CYAN);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(titleLabel);
        
        card.add(Box.createVerticalStrut(ModernUIConstants.SPACING_L));
        
        // Form panel
        ModernPanelTemplates.FormPanel formPanel = new ModernPanelTemplates.FormPanel();
        formPanel.addField("Select Donor", donorComboBox);
        formPanel.addField("Select Event", eventComboBox);
        formPanel.addField("Donation Amount (₹)", amountField);
        
        card.add(formPanel);
        
        // Error label
        card.add(Box.createVerticalStrut(ModernUIConstants.SPACING_M));
        card.add(errorLabel);
        
        // Buttons
        card.add(Box.createVerticalStrut(ModernUIConstants.SPACING_L));
        
        ModernPanelTemplates.ActionButtonGroup buttonGroup = new ModernPanelTemplates.ActionButtonGroup();
        donateButton.addActionListener(e -> makeDonation());
        resetButton.addActionListener(e -> resetForm());
        backButton.addActionListener(e -> {
            if (onBackClick != null) onBackClick.run();
        });
        
        buttonGroup.add(donateButton);
        buttonGroup.add(resetButton);
        buttonGroup.add(backButton);
        
        card.add(buttonGroup);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        add(centeredPanel, gbc);
    }
    
    private void makeDonation() {
        errorLabel.setText("");
        
        if (donors.isEmpty()) {
            errorLabel.setText("No donors available");
            return;
        }
        
        if (events.isEmpty()) {
            errorLabel.setText("No events available");
            return;
        }
        
        String amountStr = amountField.getText().trim();
        
        if (amountStr.isEmpty()) {
            errorLabel.setText("Amount is required");
            return;
        }
        
        try {
            int donorIndex = donorComboBox.getSelectedIndex();
            int eventIndex = eventComboBox.getSelectedIndex();
            double amount = Double.parseDouble(amountStr);
            
            if (amount <= 0) {
                errorLabel.setText("Amount must be positive");
                return;
            }
            
            Donor donor = donors.get(donorIndex);
            Event event = events.get(eventIndex);
            
            // Get current date for pledge
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            String currentDate = sdf.format(new java.util.Date());
            
            Pledge pledge = new Pledge(donor.getDonorId(), event.getEventId(), amount, currentDate);
            boolean success = pledgeDAO.addPledge(pledge);
            
            if (success) {
                // Update event funds
                double newCollected = event.getCollectedAmount() + amount;
                event.setCollectedAmount(newCollected);
                eventDAO.updateCollectedAmount(event.getEventId(), newCollected);
                
                JOptionPane.showMessageDialog(this,
                    "Donation recorded successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                resetForm();
            } else {
                errorLabel.setText("Failed to record donation. Please try again.");
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("Amount must be a valid number");
        } catch (Exception e) {
            errorLabel.setText("Error recording donation: " + e.getMessage());
        }
    }
    
    private void resetForm() {
        if (donors.size() > 0) donorComboBox.setSelectedIndex(0);
        if (events.size() > 0) eventComboBox.setSelectedIndex(0);
        amountField.setText("");
        errorLabel.setText("");
    }
    
    public void setOnBackClick(Runnable callback) {
        this.onBackClick = callback;
    }
}
