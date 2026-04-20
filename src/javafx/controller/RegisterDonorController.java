package javafx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import dao.DonorDAO;
import model.Donor;

/**
 * Register Donor Controller - Handle donor registration form
 */
public class RegisterDonorController {
    
    @FXML
    private TextField donorNameField;
    
    @FXML
    private TextField emailField;
    
    @FXML
    private TextField phoneField;
    
    @FXML
    private Label donorNameError;
    
    @FXML
    private Label emailError;
    
    @FXML
    private Label phoneError;
    
    @FXML
    private Label successMessage;
    
    private DonorDAO donorDAO;
    
    /**
     * Initialize controller
     */
    @FXML
    public void initialize() {
        donorDAO = new DonorDAO();
        clearErrors();
    }
    
    /**
     * Handle register donor button
     */
    @FXML
    public void handleRegisterDonor() {
        // Clear previous messages
        clearErrors();
        successMessage.setText("");
        
        // Validate form
        if (!validateForm()) {
            return;
        }
        
        try {
            // Get form data
            String donorName = donorNameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            
            // Create donor object
            Donor donor = new Donor(donorName, email, phone);
            
            // Save to database
            boolean success = donorDAO.addDonor(donor);
            
            if (success) {
                successMessage.setText("✓ Donor registered successfully!");
                successMessage.setStyle("-fx-text-fill: #10FF82;");
                handleClear();
            } else {
                successMessage.setText("✗ Failed to register donor. Please try again.");
                successMessage.setStyle("-fx-text-fill: #FF4787;");
            }
            
        } catch (Exception e) {
            successMessage.setText("Error: " + e.getMessage());
            successMessage.setStyle("-fx-text-fill: #FF4787;");
        }
    }
    
    /**
     * Validate form fields
     */
    private boolean validateForm() {
        boolean isValid = true;
        
        // Validate donor name
        if (donorNameField.getText().trim().isEmpty()) {
            donorNameError.setText("Donor name is required");
            isValid = false;
        }
        
        // Validate email
        String email = emailField.getText().trim();
        if (email.isEmpty()) {
            emailError.setText("Email is required");
            isValid = false;
        } else if (!email.contains("@") || !email.contains(".")) {
            emailError.setText("Enter a valid email address");
            isValid = false;
        }
        
        // Validate phone
        String phone = phoneField.getText().trim();
        if (phone.isEmpty()) {
            phoneError.setText("Phone number is required");
            isValid = false;
        } else if (phone.replaceAll("[^0-9]", "").length() < 10) {
            phoneError.setText("Phone number must have at least 10 digits");
            isValid = false;
        }
        
        return isValid;
    }
    
    /**
     * Clear all errors
     */
    private void clearErrors() {
        donorNameError.setText("");
        emailError.setText("");
        phoneError.setText("");
    }
    
    /**
     * Clear all fields
     */
    @FXML
    public void handleClear() {
        donorNameField.clear();
        emailField.clear();
        phoneField.clear();
        clearErrors();
        successMessage.setText("");
    }
}
