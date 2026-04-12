package ui.panels;

import ui.UIConstants;
import ui.CustomComponents;
import model.Pledge;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

/**
 * Make Donation Form Panel
 */
public class MakeDonationPanel extends JPanel {
    
    private JComboBox<String> donorComboBox;
    private JComboBox<String> eventComboBox;
    private JTextField amountField;
    private JButton submitButton;
    private JButton backButton;
    
    private Runnable onBack;
    private java.util.function.Consumer<Pledge> onSubmit;
    
    public MakeDonationPanel() {
        setBackground(UIConstants.BG_PRIMARY);
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Form
        JPanel formPanel = createFormPanel();
        add(formPanel, BorderLayout.CENTER);
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
        
        JLabel titleLabel = CustomComponents.createTitleLabel("Make a Donation");
        
        headerPanel.add(backButton);
        headerPanel.add(Box.createHorizontalStrut(UIConstants.PADDING_LARGE));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createHorizontalGlue());
        
        return headerPanel;
    }
    
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setBackground(UIConstants.BG_PRIMARY);
        formPanel.setBorder(BorderFactory.createEmptyBorder(
            UIConstants.PADDING_LARGE, UIConstants.PADDING_LARGE,
            UIConstants.PADDING_LARGE, UIConstants.PADDING_LARGE
        ));
        
        JPanel formContent = new JPanel();
        formContent.setBackground(UIConstants.BG_PRIMARY);
        formContent.setLayout(new BoxLayout(formContent, BoxLayout.Y_AXIS));
        formContent.setMaximumSize(new Dimension(600, 400));
        
        // Donor Selection
        JLabel donorLabel = CustomComponents.createHeadingLabel("Select Donor");
        donorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        donorComboBox = CustomComponents.createStyledComboBox(new String[]{"-- Select a Donor --"});
        donorComboBox.setMaximumSize(new Dimension(500, UIConstants.FIELD_HEIGHT));
        
        formContent.add(donorLabel);
        formContent.add(Box.createVerticalStrut(UIConstants.PADDING_SMALL));
        formContent.add(donorComboBox);
        formContent.add(Box.createVerticalStrut(UIConstants.PADDING_LARGE));
        
        // Event Selection
        JLabel eventLabel = CustomComponents.createHeadingLabel("Select Event");
        eventLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        eventComboBox = CustomComponents.createStyledComboBox(new String[]{"-- Select an Event --"});
        eventComboBox.setMaximumSize(new Dimension(500, UIConstants.FIELD_HEIGHT));
        
        formContent.add(eventLabel);
        formContent.add(Box.createVerticalStrut(UIConstants.PADDING_SMALL));
        formContent.add(eventComboBox);
        formContent.add(Box.createVerticalStrut(UIConstants.PADDING_LARGE));
        
        // Amount
        JLabel amountLabel = CustomComponents.createHeadingLabel("Donation Amount (₹)");
        amountLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        amountField = CustomComponents.createStyledTextField("Enter donation amount");
        amountField.setMaximumSize(new Dimension(500, UIConstants.FIELD_HEIGHT));
        
        formContent.add(amountLabel);
        formContent.add(Box.createVerticalStrut(UIConstants.PADDING_SMALL));
        formContent.add(amountField);
        formContent.add(Box.createVerticalStrut(UIConstants.PADDING_LARGE));
        
        // Submit Button
        submitButton = CustomComponents.createPrimaryButton("Make Donation");
        submitButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        submitButton.addActionListener(e -> handleSubmit());
        
        formContent.add(submitButton);
        formContent.add(Box.createVerticalGlue());
        
        formPanel.setLayout(new BorderLayout());
        formPanel.add(formContent, BorderLayout.NORTH);
        
        return formPanel;
    }
    
    private void handleSubmit() {
        if (donorComboBox.getSelectedIndex() == 0 || eventComboBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Please select a donor and event!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String amountStr = amountField.getText().trim();
        if (amountStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a donation amount!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            double amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Donation amount must be positive!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Extract IDs from combo box selections
            String donorStr = donorComboBox.getSelectedItem().toString();
            String eventStr = eventComboBox.getSelectedItem().toString();
            
            int donorId = Integer.parseInt(donorStr.split(" - ")[0]);
            int eventId = Integer.parseInt(eventStr.split(" - ")[0]);
            
            Pledge pledge = new Pledge(donorId, eventId, amount, LocalDate.now().toString());
            if (onSubmit != null) {
                onSubmit.accept(pledge);
            }
            
            // Clear fields
            donorComboBox.setSelectedIndex(0);
            eventComboBox.setSelectedIndex(0);
            amountField.setText("");
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void setDonors(java.util.List<String> donors) {
        donorComboBox.removeAllItems();
        donorComboBox.addItem("-- Select a Donor --");
        for (String donor : donors) {
            donorComboBox.addItem(donor);
        }
    }
    
    public void setEvents(java.util.List<String> events) {
        eventComboBox.removeAllItems();
        eventComboBox.addItem("-- Select an Event --");
        for (String event : events) {
            eventComboBox.addItem(event);
        }
    }
    
    public void setOnBack(Runnable callback) { this.onBack = callback; }
    public void setOnSubmit(java.util.function.Consumer<Pledge> callback) { this.onSubmit = callback; }
}
