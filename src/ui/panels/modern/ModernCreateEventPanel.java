package ui.panels.modern;

import ui.ModernUIConstants;
import ui.components.ModernComponents;
import ui.components.ModernPanelTemplates;
import dao.EventDAO;
import model.Event;

import javax.swing.*;
import java.awt.*;

/**
 * Modern Create Event Panel - Premium form for creating new events
 * Features card layout, smooth inputs, and form validation
 */
public class ModernCreateEventPanel extends JPanel {
    
    private Runnable onBackClick;
    private EventDAO eventDAO;
    
    // Form components
    private ModernComponents.ModernTextField eventNameField;
    private ModernComponents.ModernTextField targetAmountField;
    private JTextArea descriptionArea;
    private ModernComponents.ModernButton createButton;
    private ModernComponents.ModernButton resetButton;
    private ModernComponents.ModernButton backButton;
    private JLabel errorLabel;
    
    public ModernCreateEventPanel() {
        this.eventDAO = new EventDAO();
        setBackground(ModernUIConstants.BG_PRIMARY);
        setLayout(new GridBagLayout());
        
        initializeComponents();
        layoutComponents();
        setupListeners();
    }
    
    private void initializeComponents() {
        eventNameField = new ModernComponents.ModernTextField("", 20);
        targetAmountField = new ModernComponents.ModernTextField("", 20);
        
        descriptionArea = new JTextArea(5, 20);
        descriptionArea.setBackground(ModernUIConstants.BG_SECONDARY);
        descriptionArea.setForeground(ModernUIConstants.TEXT_PRIMARY);
        descriptionArea.setFont(ModernUIConstants.FONT_BODY);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setBorder(BorderFactory.createLineBorder(
            ModernUIConstants.BORDER_LIGHT, 1));
        
        createButton = new ModernComponents.ModernButton("Create Event",
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
        gbc.insets = new Insets(ModernUIConstants.SPACING_L, ModernUIConstants.SPACING_L,
                               ModernUIConstants.SPACING_L, ModernUIConstants.SPACING_L);
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        
        // Centered card
        ModernPanelTemplates.CenteredCardPanel centeredPanel = new ModernPanelTemplates.CenteredCardPanel();
        ModernComponents.ModernCardPanel card = centeredPanel.getCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        
        // Header
        JLabel titleLabel = new ModernComponents.ModernLabel("Create New Event",
            ModernUIConstants.FONT_SIZE_L, true);
        titleLabel.setForeground(ModernUIConstants.ACCENT_CYAN);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(titleLabel);
        
        card.add(Box.createVerticalStrut(ModernUIConstants.SPACING_L));
        
        // Form panel
        ModernPanelTemplates.FormPanel formPanel = new ModernPanelTemplates.FormPanel();
        formPanel.addField("Event Name", eventNameField);
        formPanel.addField("Target Amount (₹)", targetAmountField);
        
        JScrollPane descScrollPane = new JScrollPane(descriptionArea);
        descScrollPane.setBorder(BorderFactory.createLineBorder(
            ModernUIConstants.BORDER_LIGHT, 1));
        formPanel.addField("Description", descScrollPane);
        
        card.add(formPanel);
        
        // Error label
        card.add(Box.createVerticalStrut(ModernUIConstants.SPACING_M));
        card.add(errorLabel);
        
        // Buttons
        card.add(Box.createVerticalStrut(ModernUIConstants.SPACING_L));
        
        ModernPanelTemplates.ActionButtonGroup buttonGroup = new ModernPanelTemplates.ActionButtonGroup();
        createButton.addActionListener(e -> createEvent());
        resetButton.addActionListener(e -> resetForm());
        backButton.addActionListener(e -> {
            if (onBackClick != null) onBackClick.run();
        });
        
        buttonGroup.add(createButton);
        buttonGroup.add(resetButton);
        buttonGroup.add(backButton);
        
        card.add(buttonGroup);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        
        add(centeredPanel, gbc);
    }
    
    private void setupListeners() {
        // Input validation
        eventNameField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                errorLabel.setText("");
            }
        });
    }
    
    private void createEvent() {
        errorLabel.setText("");
        
        String eventName = eventNameField.getText().trim();
        String targetAmountStr = targetAmountField.getText().trim();
        String description = descriptionArea.getText().trim();
        
        // Validation
        if (eventName.isEmpty()) {
            errorLabel.setText("Event name is required");
            return;
        }
        
        if (targetAmountStr.isEmpty()) {
            errorLabel.setText("Target amount is required");
            return;
        }
        
        try {
            double targetAmount = Double.parseDouble(targetAmountStr);
            if (targetAmount <= 0) {
                errorLabel.setText("Target amount must be positive");
                return;
            }
            
            Event event = new Event(eventName, targetAmount, description);
            boolean success = eventDAO.addEvent(event);
            
            if (success) {
                JOptionPane.showMessageDialog(this,
                    "Event created successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                resetForm();
            } else {
                errorLabel.setText("Failed to create event. Please try again.");
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("Target amount must be a valid number");
        }
    }
    
    private void resetForm() {
        eventNameField.setText("");
        targetAmountField.setText("");
        descriptionArea.setText("");
        errorLabel.setText("");
    }
    
    public void setOnBackClick(Runnable callback) {
        this.onBackClick = callback;
    }
}
