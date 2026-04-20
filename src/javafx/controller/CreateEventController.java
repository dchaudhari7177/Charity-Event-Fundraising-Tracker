package javafx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;

import dao.EventDAO;
import model.Event;

/**
 * Create Event Controller - Handle event creation form
 */
public class CreateEventController {
    
    @FXML
    private TextField eventNameField;
    
    @FXML
    private TextField targetAmountField;
    
    @FXML
    private TextArea descriptionArea;
    
    @FXML
    private Label eventNameError;
    
    @FXML
    private Label targetAmountError;
    
    @FXML
    private Label descriptionError;
    
    @FXML
    private Label successMessage;
    
    private EventDAO eventDAO;
    
    /**
     * Initialize controller
     */
    @FXML
    public void initialize() {
        eventDAO = new EventDAO();
        clearErrors();
    }
    
    /**
     * Handle create event button
     */
    @FXML
    public void handleCreateEvent() {
        // Clear previous messages
        clearErrors();
        successMessage.setText("");
        
        // Validate form
        if (!validateForm()) {
            return;
        }
        
        try {
            // Get form data
            String eventName = eventNameField.getText().trim();
            String description = descriptionArea.getText().trim();
            double targetAmount = Double.parseDouble(targetAmountField.getText().trim());
            
            // Create event object
            Event event = new Event(eventName, targetAmount, description);
            
            // Save to database
            boolean success = eventDAO.addEvent(event);
            
            if (success) {
                successMessage.setText("✓ Event created successfully!");
                successMessage.setStyle("-fx-text-fill: #10FF82;");
                handleClear();
            } else {
                successMessage.setText("✗ Failed to create event. Please try again.");
                successMessage.setStyle("-fx-text-fill: #FF4787;");
            }
            
        } catch (NumberFormatException e) {
            targetAmountError.setText("Invalid amount format");
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
        
        // Validate event name
        if (eventNameField.getText().trim().isEmpty()) {
            eventNameError.setText("Event name is required");
            isValid = false;
        }
        
        // Validate target amount
        try {
            double amount = Double.parseDouble(targetAmountField.getText().trim());
            if (amount <= 0) {
                targetAmountError.setText("Amount must be greater than 0");
                isValid = false;
            }
        } catch (NumberFormatException e) {
            targetAmountError.setText("Enter a valid amount");
            isValid = false;
        }
        
        return isValid;
    }
    
    /**
     * Clear all errors
     */
    private void clearErrors() {
        eventNameError.setText("");
        targetAmountError.setText("");
        descriptionError.setText("");
    }
    
    /**
     * Clear all fields
     */
    @FXML
    public void handleClear() {
        eventNameField.clear();
        targetAmountField.clear();
        descriptionArea.clear();
        clearErrors();
        successMessage.setText("");
    }
}
