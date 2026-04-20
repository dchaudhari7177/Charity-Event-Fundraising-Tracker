package ui.panels.modern;

import ui.ModernUIConstants;
import ui.components.ModernComponents;
import ui.components.ModernPanelTemplates;
import dao.DonorDAO;
import model.Donor;

import javax.swing.*;
import java.awt.*;

/**
 * Modern Register Donor Panel - Premium form for registering donors
 * Features validation, smooth inputs, and modern styling
 */
public class ModernRegisterDonorPanel extends JPanel {
    
    private Runnable onBackClick;
    private DonorDAO donorDAO;
    
    // Form components
    private ModernComponents.ModernTextField donorNameField;
    private ModernComponents.ModernTextField emailField;
    private ModernComponents.ModernTextField phoneField;
    private ModernComponents.ModernButton registerButton;
    private ModernComponents.ModernButton resetButton;
    private ModernComponents.ModernButton backButton;
    private JLabel errorLabel;
    
    public ModernRegisterDonorPanel() {
        this.donorDAO = new DonorDAO();
        setBackground(ModernUIConstants.BG_PRIMARY);
        setLayout(new GridBagLayout());
        
        initializeComponents();
        layoutComponents();
    }
    
    private void initializeComponents() {
        donorNameField = new ModernComponents.ModernTextField("", 20);
        emailField = new ModernComponents.ModernTextField("", 20);
        phoneField = new ModernComponents.ModernTextField("", 20);
        
        registerButton = new ModernComponents.ModernButton("Register Donor",
            ModernUIConstants.ACCENT_CYAN, ModernUIConstants.lighten(ModernUIConstants.ACCENT_CYAN, 0.15));
        
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
        JLabel titleLabel = new ModernComponents.ModernLabel("Register New Donor",
            ModernUIConstants.FONT_SIZE_L, true);
        titleLabel.setForeground(ModernUIConstants.ACCENT_CYAN);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(titleLabel);
        
        card.add(Box.createVerticalStrut(ModernUIConstants.SPACING_L));
        
        // Form panel
        ModernPanelTemplates.FormPanel formPanel = new ModernPanelTemplates.FormPanel();
        formPanel.addField("Full Name", donorNameField);
        formPanel.addField("Email Address", emailField);
        formPanel.addField("Phone Number", phoneField);
        
        card.add(formPanel);
        
        // Error label
        card.add(Box.createVerticalStrut(ModernUIConstants.SPACING_M));
        card.add(errorLabel);
        
        // Buttons
        card.add(Box.createVerticalStrut(ModernUIConstants.SPACING_L));
        
        ModernPanelTemplates.ActionButtonGroup buttonGroup = new ModernPanelTemplates.ActionButtonGroup();
        registerButton.addActionListener(e -> registerDonor());
        resetButton.addActionListener(e -> resetForm());
        backButton.addActionListener(e -> {
            if (onBackClick != null) onBackClick.run();
        });
        
        buttonGroup.add(registerButton);
        buttonGroup.add(resetButton);
        buttonGroup.add(backButton);
        
        card.add(buttonGroup);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        add(centeredPanel, gbc);
    }
    
    private void registerDonor() {
        errorLabel.setText("");
        
        String donorName = donorNameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        
        // Validation
        if (donorName.isEmpty()) {
            errorLabel.setText("Donor name is required");
            return;
        }
        
        if (email.isEmpty()) {
            errorLabel.setText("Email is required");
            return;
        }
        
        if (!isValidEmail(email)) {
            errorLabel.setText("Invalid email format");
            return;
        }
        
        if (phone.isEmpty()) {
            errorLabel.setText("Phone number is required");
            return;
        }
        
        if (!isValidPhone(phone)) {
            errorLabel.setText("Invalid phone format");
            return;
        }
        
        try {
            Donor donor = new Donor(donorName, email, phone);
            boolean success = donorDAO.addDonor(donor);
            
            if (success) {
                JOptionPane.showMessageDialog(this,
                    "Donor registered successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                resetForm();
            } else {
                errorLabel.setText("Failed to register donor. Please try again.");
            }
        } catch (Exception e) {
            errorLabel.setText("Error registering donor: " + e.getMessage());
        }
    }
    
    private void resetForm() {
        donorNameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        errorLabel.setText("");
    }
    
    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }
    
    private boolean isValidPhone(String phone) {
        return phone.matches("\\d+") && phone.length() >= 10;
    }
    
    public void setOnBackClick(Runnable callback) {
        this.onBackClick = callback;
    }
}
