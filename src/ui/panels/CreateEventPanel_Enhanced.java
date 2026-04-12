package ui.panels;

import ui.UIConstants_Enhanced;
import ui.EnhancedComponents;
import model.Event;
import dao.EventDAO;
import java.awt.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Enhanced Create Event Panel - Centered professional form
 */
public class CreateEventPanel_Enhanced extends JPanel {
    
    private JTextField nameField;
    private JTextField targetAmountField;
    private JTextArea descriptionArea;
    private Runnable onBack;
    private EventDAO eventDAO;
    
    public CreateEventPanel_Enhanced() {
        setBackground(UIConstants_Enhanced.BG_PRIMARY);
        setLayout(new BorderLayout());
        
        // Header with back button
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Centered form
        JPanel formContainer = createFormContainer();
        add(new JScrollPane(formContainer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
        
        eventDAO = new EventDAO();
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
        
        // Title
        JLabel titleLabel = EnhancedComponents.createHeadingLabel("📋 Create New Event");
        titleLabel.setForeground(UIConstants_Enhanced.ACCENT_GREEN);
        header.add(titleLabel, BorderLayout.WEST);
        
        // Back button
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
        
        // Centered form panel with max width
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(UIConstants_Enhanced.BG_PRIMARY);
        formPanel.setMaximumSize(new Dimension(600, 500));
        
        // Event Name Field
        JPanel nameSection = createFormSection("Event Name");
        nameField = EnhancedComponents.createAnimatedTextField("e.g., Cancer Awareness Drive");
        nameField.setMaximumSize(new Dimension(500, UIConstants_Enhanced.FIELD_HEIGHT));
        nameSection.add(nameField);
        formPanel.add(nameSection);
        formPanel.add(Box.createVerticalStrut(UIConstants_Enhanced.PADDING_XLARGE));
        
        // Target Amount Field
        JPanel targetSection = createFormSection("Target Amount (₹)");
        targetAmountField = EnhancedComponents.createAnimatedTextField("e.g., 50000");
        targetAmountField.setMaximumSize(new Dimension(500, UIConstants_Enhanced.FIELD_HEIGHT));
        targetSection.add(targetAmountField);
        formPanel.add(targetSection);
        formPanel.add(Box.createVerticalStrut(UIConstants_Enhanced.PADDING_XLARGE));
        
        // Description Field
        JPanel descSection = createFormSection("Description");
        descriptionArea = EnhancedComponents.createStyledTextArea(4, 50);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        scrollPane.setBackground(UIConstants_Enhanced.BG_PRIMARY);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        descSection.add(scrollPane);
        formPanel.add(descSection);
        formPanel.add(Box.createVerticalStrut(UIConstants_Enhanced.PADDING_XLARGE));
        
        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(UIConstants_Enhanced.BG_PRIMARY);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton createButton = EnhancedComponents.createAnimatedPrimaryButton("✓ Create Event");
        createButton.addActionListener(e -> createEvent());
        
        JButton resetButton = EnhancedComponents.createAnimatedSecondaryButton("⟲ Reset");
        resetButton.addActionListener(e -> clearFields());
        
        buttonPanel.add(createButton);
        buttonPanel.add(Box.createHorizontalStrut(UIConstants_Enhanced.PADDING_LARGE));
        buttonPanel.add(resetButton);
        
        formPanel.add(buttonPanel);
        
        // Add form to centered container
        container.add(formPanel);
        container.add(Box.createVerticalGlue());
        
        return container;
    }
    
    /**
     * Create a labeled form section for better organization
     */
    private JPanel createFormSection(String label) {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(UIConstants_Enhanced.BG_PRIMARY);
        
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(UIConstants_Enhanced.FONT_LABEL);
        labelComponent.setForeground(UIConstants_Enhanced.ACCENT_GREEN);
        labelComponent.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        section.add(labelComponent);
        section.add(Box.createVerticalStrut(UIConstants_Enhanced.PADDING_SMALL));
        
        return section;
    }
    
    private void createEvent() {
        // Validation
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Event name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            double targetAmount = Double.parseDouble(targetAmountField.getText().trim());
            if (targetAmount <= 0) {
                JOptionPane.showMessageDialog(this, "Target amount must be positive!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid target amount!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Create event
        Event event = new Event(
            0,
            nameField.getText().trim(),
            Double.parseDouble(targetAmountField.getText()),
            descriptionArea.getText().trim(),
            0
        );
        
        eventDAO.addEvent(event);
        JOptionPane.showMessageDialog(this, "✓ Event created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        clearFields();
    }
    
    private void clearFields() {
        nameField.setText("");
        targetAmountField.setText("");
        descriptionArea.setText("");
    }
    
    public void setOnBack(Runnable callback) { this.onBack = callback; }
}
