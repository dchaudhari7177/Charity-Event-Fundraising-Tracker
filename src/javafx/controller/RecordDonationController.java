package javafx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import dao.DonorDAO;
import dao.EventDAO;
import dao.PledgeDAO;
import model.Donor;
import model.Event;
import model.Pledge;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Record Donation Controller - Handle donation recording form
 */
public class RecordDonationController {
    
    @FXML
    private ComboBox<String> donorComboBox;
    
    @FXML
    private ComboBox<String> eventComboBox;
    
    @FXML
    private TextField amountField;
    
    @FXML
    private DatePicker datePicker;
    
    @FXML
    private Label donorError;
    
    @FXML
    private Label eventError;
    
    @FXML
    private Label amountError;
    
    @FXML
    private Label dateError;
    
    @FXML
    private Label successMessage;
    
    private DonorDAO donorDAO;
    private EventDAO eventDAO;
    private PledgeDAO pledgeDAO;
    
    private ArrayList<Donor> donors;
    private ArrayList<Event> events;
    
    /**
     * Initialize controller
     */
    @FXML
    public void initialize() {
        donorDAO = new DonorDAO();
        eventDAO = new EventDAO();
        pledgeDAO = new PledgeDAO();
        
        loadDonorsAndEvents();
        datePicker.setValue(LocalDate.now());
        clearErrors();
    }
    
    /**
     * Load donors and events from database
     */
    private void loadDonorsAndEvents() {
        // Load donors
        donors = donorDAO.getAllDonors();
        ObservableList<String> donorNames = FXCollections.observableArrayList();
        for (Donor donor : donors) {
            donorNames.add(donor.getDonorId() + " - " + donor.getDonorName());
        }
        donorComboBox.setItems(donorNames);
        
        // Load events
        events = eventDAO.getAllEvents();
        ObservableList<String> eventNames = FXCollections.observableArrayList();
        for (Event event : events) {
            eventNames.add(event.getEventId() + " - " + event.getEventName());
        }
        eventComboBox.setItems(eventNames);
    }
    
    /**
     * Handle record donation button
     */
    @FXML
    public void handleRecordDonation() {
        // Clear previous messages
        clearErrors();
        successMessage.setText("");
        
        // Validate form
        if (!validateForm()) {
            return;
        }
        
        try {
            // Get selected donor and event
            int donorId = extractId(donorComboBox.getValue());
            int eventId = extractId(eventComboBox.getValue());
            
            // Get amount
            double amount = Double.parseDouble(amountField.getText().trim());
            
            // Get date
            LocalDate date = datePicker.getValue();
            String dateString = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            
            // Create pledge object
            Pledge pledge = new Pledge(donorId, eventId, amount, dateString);
            
            // Save to database
            boolean success = pledgeDAO.addPledge(pledge);
            
            if (success) {
                // Update event collected amount
                Event event = eventDAO.getEventById(eventId);
                if (event != null) {
                    double newCollected = event.getCollectedAmount() + amount;
                    eventDAO.updateCollectedAmount(eventId, amount);
                }
                
                successMessage.setText("✓ Donation recorded successfully!");
                successMessage.setStyle("-fx-text-fill: #10FF82;");
                handleClear();
            } else {
                successMessage.setText("✗ Failed to record donation. Please try again.");
                successMessage.setStyle("-fx-text-fill: #FF4787;");
            }
            
        } catch (NumberFormatException e) {
            amountError.setText("Invalid amount format");
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
        
        // Validate donor
        if (donorComboBox.getValue() == null || donorComboBox.getValue().isEmpty()) {
            donorError.setText("Please select a donor");
            isValid = false;
        }
        
        // Validate event
        if (eventComboBox.getValue() == null || eventComboBox.getValue().isEmpty()) {
            eventError.setText("Please select an event");
            isValid = false;
        }
        
        // Validate amount
        try {
            double amount = Double.parseDouble(amountField.getText().trim());
            if (amount <= 0) {
                amountError.setText("Amount must be greater than 0");
                isValid = false;
            }
        } catch (NumberFormatException e) {
            amountError.setText("Enter a valid amount");
            isValid = false;
        }
        
        // Validate date
        if (datePicker.getValue() == null) {
            dateError.setText("Please select a date");
            isValid = false;
        }
        
        return isValid;
    }
    
    /**
     * Extract ID from combo box value (format: "id - name")
     */
    private int extractId(String value) {
        if (value == null || value.isEmpty()) {
            return -1;
        }
        return Integer.parseInt(value.split(" - ")[0]);
    }
    
    /**
     * Clear all errors
     */
    private void clearErrors() {
        donorError.setText("");
        eventError.setText("");
        amountError.setText("");
        dateError.setText("");
    }
    
    /**
     * Clear all fields
     */
    @FXML
    public void handleClear() {
        donorComboBox.setValue(null);
        eventComboBox.setValue(null);
        amountField.clear();
        datePicker.setValue(LocalDate.now());
        clearErrors();
        successMessage.setText("");
    }
}
