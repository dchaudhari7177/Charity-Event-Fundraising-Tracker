package ui.panels;

import ui.UIConstants_Enhanced;
import ui.EnhancedComponents;
import model.Donor;
import model.Event;
import model.Pledge;
import dao.DonorDAO;
import dao.EventDAO;
import dao.PledgeDAO;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import model.Pledge;

/**
 * Enhanced Make Donation Panel - Centered professional form with dropdowns
 */
public class MakeDonationPanel_Enhanced extends JPanel {
    
    private JComboBox<Donor> donorCombo;
    private JComboBox<Event> eventCombo;
    private JTextField amountField;
    private Runnable onBack;
    private DonorDAO donorDAO;
    private EventDAO eventDAO;
    private PledgeDAO pledgeDAO;
    
    public MakeDonationPanel_Enhanced() {
        setBackground(UIConstants_Enhanced.BG_PRIMARY);
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Form
        JPanel formContainer = createFormContainer();
        add(new JScrollPane(formContainer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
        
        donorDAO = new DonorDAO();
        eventDAO = new EventDAO();
        pledgeDAO = new PledgeDAO();
        
        loadData();
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
        
        JLabel titleLabel = EnhancedComponents.createHeadingLabel("💰 Record Donation");
        titleLabel.setForeground(UIConstants_Enhanced.PROGRESS_BLUE);
        header.add(titleLabel, BorderLayout.WEST);
        
        JButton backButton = EnhancedComponents.createAnimatedSecondaryButton("← Back");
        backButton.addActionListener(e -> {
            if (onBack != null) onBack.run();
        });
        header.add(backButton, BorderLayout.EAST);
        
        return header;
    }
    
    private JPanel createFormContainer() {
        JPanel container = new JPanel();
        container.setBackground(UIConstants_Enhanced.BG_PRIMARY);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(
            UIConstants_Enhanced.PADDING_XLARGE,
            UIConstants_Enhanced.PADDING_XLARGE,
            UIConstants_Enhanced.PADDING_XLARGE,
            UIConstants_Enhanced.PADDING_XLARGE
        ));
        
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(UIConstants_Enhanced.BG_PRIMARY);
        formPanel.setMaximumSize(new Dimension(600, 500));
        
        // Donor Selector
        JPanel donorSection = createFormSection("Select Donor");
        donorCombo = (JComboBox<Donor>) (JComboBox<?>) createStyledComboBox();
        donorCombo.setMaximumSize(new Dimension(500, UIConstants_Enhanced.FIELD_HEIGHT));
        donorSection.add(donorCombo);
        formPanel.add(donorSection);
        formPanel.add(Box.createVerticalStrut(UIConstants_Enhanced.PADDING_XLARGE));
        
        // Event Selector
        JPanel eventSection = createFormSection("Select Event");
        eventCombo = (JComboBox<Event>) (JComboBox<?>) createStyledComboBox();
        eventCombo.setMaximumSize(new Dimension(500, UIConstants_Enhanced.FIELD_HEIGHT));
        eventSection.add(eventCombo);
        formPanel.add(eventSection);
        formPanel.add(Box.createVerticalStrut(UIConstants_Enhanced.PADDING_XLARGE));
        
        // Donation Amount
        JPanel amountSection = createFormSection("Donation Amount (₹)");
        amountField = EnhancedComponents.createAnimatedTextField("e.g., 5000");
        amountField.setMaximumSize(new Dimension(500, UIConstants_Enhanced.FIELD_HEIGHT));
        amountSection.add(amountField);
        formPanel.add(amountSection);
        formPanel.add(Box.createVerticalStrut(UIConstants_Enhanced.PADDING_XLARGE));
        
        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(UIConstants_Enhanced.BG_PRIMARY);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton donateButton = EnhancedComponents.createAnimatedPrimaryButton("✓ Record Donation");
        donateButton.addActionListener(e -> recordDonation());
        
        JButton resetButton = EnhancedComponents.createAnimatedSecondaryButton("⟲ Reset");
        resetButton.addActionListener(e -> clearFields());
        
        buttonPanel.add(donateButton);
        buttonPanel.add(Box.createHorizontalStrut(UIConstants_Enhanced.PADDING_LARGE));
        buttonPanel.add(resetButton);
        
        formPanel.add(buttonPanel);
        
        container.add(formPanel);
        container.add(Box.createVerticalGlue());
        
        return container;
    }
    
    /**
     * Create a styled combo box
     */
    private <T> JComboBox<T> createStyledComboBox() {
        JComboBox<T> combo = new JComboBox<>();
        combo.setFont(UIConstants_Enhanced.FONT_BODY);
        combo.setForeground(UIConstants_Enhanced.TEXT_PRIMARY);
        combo.setBackground(UIConstants_Enhanced.BG_SECONDARY);
        return combo;
    }
    
    private JPanel createFormSection(String label) {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(UIConstants_Enhanced.BG_PRIMARY);
        
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(UIConstants_Enhanced.FONT_LABEL);
        labelComponent.setForeground(UIConstants_Enhanced.PROGRESS_BLUE);
        labelComponent.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        section.add(labelComponent);
        section.add(Box.createVerticalStrut(UIConstants_Enhanced.PADDING_SMALL));
        
        return section;
    }
    
    private void loadData() {
        // Load donors
        List<Donor> donors = donorDAO.getAllDonors();
        donorCombo.removeAllItems();
        if (donors.isEmpty()) {
            donorCombo.addItem(new Donor(0, "No donors available", "", ""));
            donorCombo.setEnabled(false);
        } else {
            for (Donor donor : donors) {
                donorCombo.addItem(donor);
            }
        }
        
        // Load events
        List<Event> events = eventDAO.getAllEvents();
        eventCombo.removeAllItems();
        if (events.isEmpty()) {
            eventCombo.addItem(new Event(0, "No events available", 0, "", 0));
            eventCombo.setEnabled(false);
        } else {
            for (Event event : events) {
                eventCombo.addItem(event);
            }
        }
    }
    
    private void recordDonation() {
        if (donorCombo.getItemCount() == 0 || !donorCombo.isEnabled()) {
            JOptionPane.showMessageDialog(this, "No donors available!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (eventCombo.getItemCount() == 0 || !eventCombo.isEnabled()) {
            JOptionPane.showMessageDialog(this, "No events available!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            double amount = Double.parseDouble(amountField.getText().trim());
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Amount must be positive!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Donor donor = (Donor) donorCombo.getSelectedItem();
            Event event = (Event) eventCombo.getSelectedItem();
            
            pledgeDAO.addPledge(new Pledge(donor.getDonorId(), event.getEventId(), amount, java.time.LocalDate.now().toString()));
            JOptionPane.showMessageDialog(this, "✓ Donation recorded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            loadData();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearFields() {
        amountField.setText("");
    }
    
    public void setOnBack(Runnable callback) { this.onBack = callback; }
}
