package ui.panels;

import ui.UIConstants_Enhanced;
import ui.EnhancedComponents;
import model.Donor;
import dao.DonorDAO;
import java.awt.*;

import javax.swing.*;
import java.awt.*;

/**
 * Enhanced Register Donor Panel - Centered professional form
 */
public class RegisterDonorPanel_Enhanced extends JPanel {
    
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private Runnable onBack;
    private DonorDAO donorDAO;
    
    public RegisterDonorPanel_Enhanced() {
        setBackground(UIConstants_Enhanced.BG_PRIMARY);
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Form
        JPanel formContainer = createFormContainer();
        add(new JScrollPane(formContainer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
        
        donorDAO = new DonorDAO();
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
        
        JLabel titleLabel = EnhancedComponents.createHeadingLabel("👥 Register New Donor");
        titleLabel.setForeground(UIConstants_Enhanced.ACCENT_CYAN);
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
        
        // Full Name
        JPanel nameSection = createFormSection("Full Name");
        nameField = EnhancedComponents.createAnimatedTextField("e.g., John Doe");
        nameField.setMaximumSize(new Dimension(500, UIConstants_Enhanced.FIELD_HEIGHT));
        nameSection.add(nameField);
        formPanel.add(nameSection);
        formPanel.add(Box.createVerticalStrut(UIConstants_Enhanced.PADDING_XLARGE));
        
        // Email
        JPanel emailSection = createFormSection("Email Address");
        emailField = EnhancedComponents.createAnimatedTextField("e.g., john@example.com");
        emailField.setMaximumSize(new Dimension(500, UIConstants_Enhanced.FIELD_HEIGHT));
        emailSection.add(emailField);
        formPanel.add(emailSection);
        formPanel.add(Box.createVerticalStrut(UIConstants_Enhanced.PADDING_XLARGE));
        
        // Phone
        JPanel phoneSection = createFormSection("Phone Number");
        phoneField = EnhancedComponents.createAnimatedTextField("e.g., 9876543210");
        phoneField.setMaximumSize(new Dimension(500, UIConstants_Enhanced.FIELD_HEIGHT));
        phoneSection.add(phoneField);
        formPanel.add(phoneSection);
        formPanel.add(Box.createVerticalStrut(UIConstants_Enhanced.PADDING_XLARGE));
        
        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(UIConstants_Enhanced.BG_PRIMARY);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton registerButton = EnhancedComponents.createAnimatedPrimaryButton("✓ Register Donor");
        registerButton.addActionListener(e -> registerDonor());
        
        JButton resetButton = EnhancedComponents.createAnimatedSecondaryButton("⟲ Reset");
        resetButton.addActionListener(e -> clearFields());
        
        buttonPanel.add(registerButton);
        buttonPanel.add(Box.createHorizontalStrut(UIConstants_Enhanced.PADDING_LARGE));
        buttonPanel.add(resetButton);
        
        formPanel.add(buttonPanel);
        
        container.add(formPanel);
        container.add(Box.createVerticalGlue());
        
        return container;
    }
    
    private JPanel createFormSection(String label) {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(UIConstants_Enhanced.BG_PRIMARY);
        
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(UIConstants_Enhanced.FONT_LABEL);
        labelComponent.setForeground(UIConstants_Enhanced.ACCENT_CYAN);
        labelComponent.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        section.add(labelComponent);
        section.add(Box.createVerticalStrut(UIConstants_Enhanced.PADDING_SMALL));
        
        return section;
    }
    
    private void registerDonor() {
        // Validation
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (emailField.getText().trim().isEmpty() || !emailField.getText().contains("@")) {
            JOptionPane.showMessageDialog(this, "Valid email required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (phoneField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Phone number cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Create donor
        Donor donor = new Donor(
            0,
            nameField.getText().trim(),
            emailField.getText().trim(),
            phoneField.getText().trim()
        );
        
        donorDAO.addDonor(donor);
        JOptionPane.showMessageDialog(this, "✓ Donor registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        clearFields();
    }
    
    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
    }
    
    public void setOnBack(Runnable callback) { this.onBack = callback; }
}
