package ui.panels;

import ui.UIConstants;
import ui.CustomComponents;
import model.Event;

import javax.swing.*;
import java.awt.*;

/**
 * Create Event Form Panel
 */
public class CreateEventPanel extends JPanel {
    
    private JTextField eventNameField;
    private JTextField targetAmountField;
    private JTextArea descriptionArea;
    private JButton submitButton;
    private JButton backButton;
    
    private Runnable onBack;
    private java.util.function.Consumer<Event> onSubmit;
    
    public CreateEventPanel() {
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
        
        JLabel titleLabel = CustomComponents.createTitleLabel("Create New Event");
        
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
        
        // Event Name
        JLabel nameLabel = CustomComponents.createHeadingLabel("Event Name");
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        eventNameField = CustomComponents.createStyledTextField("Enter event name");
        eventNameField.setMaximumSize(new Dimension(500, UIConstants.FIELD_HEIGHT));
        
        formContent.add(nameLabel);
        formContent.add(Box.createVerticalStrut(UIConstants.PADDING_SMALL));
        formContent.add(eventNameField);
        formContent.add(Box.createVerticalStrut(UIConstants.PADDING_LARGE));
        
        // Target Amount
        JLabel targetLabel = CustomComponents.createHeadingLabel("Target Amount (₹)");
        targetLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        targetAmountField = CustomComponents.createStyledTextField("Enter target amount");
        targetAmountField.setMaximumSize(new Dimension(500, UIConstants.FIELD_HEIGHT));
        
        formContent.add(targetLabel);
        formContent.add(Box.createVerticalStrut(UIConstants.PADDING_SMALL));
        formContent.add(targetAmountField);
        formContent.add(Box.createVerticalStrut(UIConstants.PADDING_LARGE));
        
        // Description
        JLabel descLabel = CustomComponents.createHeadingLabel("Description");
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        descriptionArea = new JTextArea(5, 40);
        descriptionArea.setFont(UIConstants.FONT_BODY);
        descriptionArea.setBackground(UIConstants.BG_TERTIARY);
        descriptionArea.setForeground(UIConstants.TEXT_PRIMARY);
        descriptionArea.setCaretColor(UIConstants.ACCENT_GREEN);
        descriptionArea.setBorder(new CustomComponents.RoundedBorder(6, UIConstants.ACCENT_GREEN));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setMargin(new Insets(UIConstants.PADDING_SMALL, UIConstants.PADDING_SMALL, 
                                             UIConstants.PADDING_SMALL, UIConstants.PADDING_SMALL));
        
        JScrollPane descScrollPane = new JScrollPane(descriptionArea);
        descScrollPane.setPreferredSize(new Dimension(500, 120));
        descScrollPane.setMaximumSize(new Dimension(500, 120));
        descScrollPane.getViewport().setBackground(UIConstants.BG_TERTIARY);
        descScrollPane.setBorder(null);
        
        formContent.add(descLabel);
        formContent.add(Box.createVerticalStrut(UIConstants.PADDING_SMALL));
        formContent.add(descScrollPane);
        formContent.add(Box.createVerticalStrut(UIConstants.PADDING_LARGE));
        
        // Submit Button
        submitButton = CustomComponents.createPrimaryButton("Create Event");
        submitButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        submitButton.addActionListener(e -> handleSubmit());
        
        formContent.add(submitButton);
        formContent.add(Box.createVerticalGlue());
        
        formPanel.setLayout(new BorderLayout());
        formPanel.add(formContent, BorderLayout.NORTH);
        
        return formPanel;
    }
    
    private void handleSubmit() {
        String name = eventNameField.getText().trim();
        String targetStr = targetAmountField.getText().trim();
        String description = descriptionArea.getText().trim();
        
        if (name.isEmpty() || targetStr.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            double target = Double.parseDouble(targetStr);
            if (target <= 0) {
                JOptionPane.showMessageDialog(this, "Target amount must be positive!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Event event = new Event(name, target, description);
            if (onSubmit != null) {
                onSubmit.accept(event);
            }
            
            // Clear fields
            eventNameField.setText("");
            targetAmountField.setText("");
            descriptionArea.setText("");
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for target amount!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void setOnBack(Runnable callback) { this.onBack = callback; }
    public void setOnSubmit(java.util.function.Consumer<Event> callback) { this.onSubmit = callback; }
}
