package ui.panels;

import ui.UIConstants;
import ui.CustomComponents;
import model.Donor;

import javax.swing.*;
import java.awt.*;

/**
 * Register Donor Form Panel
 */
public class RegisterDonorPanel extends JPanel {
    
    private JTextField donorNameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JButton submitButton;
    private JButton backButton;
    
    private Runnable onBack;
    private java.util.function.Consumer<Donor> onSubmit;
    
    public RegisterDonorPanel() {
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
        
        JLabel titleLabel = CustomComponents.createTitleLabel("Register New Donor");
        
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
        
        // Donor Name
        JLabel nameLabel = CustomComponents.createHeadingLabel("Full Name");
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        donorNameField = CustomComponents.createStyledTextField("Enter full name");
        donorNameField.setMaximumSize(new Dimension(500, UIConstants.FIELD_HEIGHT));
        
        formContent.add(nameLabel);
        formContent.add(Box.createVerticalStrut(UIConstants.PADDING_SMALL));
        formContent.add(donorNameField);
        formContent.add(Box.createVerticalStrut(UIConstants.PADDING_LARGE));
        
        // Email
        JLabel emailLabel = CustomComponents.createHeadingLabel("Email Address");
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        emailField = CustomComponents.createStyledTextField("Enter email address");
        emailField.setMaximumSize(new Dimension(500, UIConstants.FIELD_HEIGHT));
        
        formContent.add(emailLabel);
        formContent.add(Box.createVerticalStrut(UIConstants.PADDING_SMALL));
        formContent.add(emailField);
        formContent.add(Box.createVerticalStrut(UIConstants.PADDING_LARGE));
        
        // Phone
        JLabel phoneLabel = CustomComponents.createHeadingLabel("Phone Number");
        phoneLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        phoneField = CustomComponents.createStyledTextField("Enter phone number");
        phoneField.setMaximumSize(new Dimension(500, UIConstants.FIELD_HEIGHT));
        
        formContent.add(phoneLabel);
        formContent.add(Box.createVerticalStrut(UIConstants.PADDING_SMALL));
        formContent.add(phoneField);
        formContent.add(Box.createVerticalStrut(UIConstants.PADDING_LARGE));
        
        // Submit Button
        submitButton = CustomComponents.createPrimaryButton("Register Donor");
        submitButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        submitButton.addActionListener(e -> handleSubmit());
        
        formContent.add(submitButton);
        formContent.add(Box.createVerticalGlue());
        
        formPanel.setLayout(new BorderLayout());
        formPanel.add(formContent, BorderLayout.NORTH);
        
        return formPanel;
    }
    
    private void handleSubmit() {
        String name = donorNameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Basic email validation
        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Donor donor = new Donor(name, email, phone);
        if (onSubmit != null) {
            onSubmit.accept(donor);
        }
        
        // Clear fields
        donorNameField.setText("");
        emailField.setText("");
        phoneField.setText("");
    }
    
    public void setOnBack(Runnable callback) { this.onBack = callback; }
    public void setOnSubmit(java.util.function.Consumer<Donor> callback) { this.onSubmit = callback; }
}
