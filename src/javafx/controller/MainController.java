package javafx.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Main Controller - Handles application navigation and sidebar
 */
public class MainController {
    
    @FXML
    private StackPane contentPane;
    
    @FXML
    private VBox sidebarBox;
    
    @FXML
    private Button navDashboard;
    @FXML
    private Button navCreateEvent;
    @FXML
    private Button navRegisterDonor;
    @FXML
    private Button navRecordDonation;
    @FXML
    private Button navViewEvents;
    @FXML
    private Button navViewDonors;
    @FXML
    private Button navStatistics;
    
    /**
     * Initialize controller
     */
    @FXML
    public void initialize() {
        showDashboard();
    }
    
    /**
     * Show Dashboard screen
     */
    @FXML
    public void showDashboard() {
        updateNavButtons(navDashboard);
        loadFXML("dashboard.fxml");
    }
    
    /**
     * Show Create Event screen
     */
    @FXML
    public void showCreateEvent() {
        updateNavButtons(navCreateEvent);
        loadFXML("create-event.fxml");
    }
    
    /**
     * Show Register Donor screen
     */
    @FXML
    public void showRegisterDonor() {
        updateNavButtons(navRegisterDonor);
        loadFXML("register-donor.fxml");
    }
    
    /**
     * Show Record Donation screen
     */
    @FXML
    public void showRecordDonation() {
        updateNavButtons(navRecordDonation);
        loadFXML("record-donation.fxml");
    }
    
    /**
     * Show View Events screen
     */
    @FXML
    public void showViewEvents() {
        updateNavButtons(navViewEvents);
        loadFXML("events-table.fxml");
    }
    
    /**
     * Show View Donors screen
     */
    @FXML
    public void showViewDonors() {
        updateNavButtons(navViewDonors);
        loadFXML("donors-table.fxml");
    }
    
    /**
     * Show Statistics screen
     */
    @FXML
    public void showStatistics() {
        updateNavButtons(navStatistics);
        loadFXML("statistics.fxml");
    }
    
    /**
     * Load FXML file and display in content pane
     */
    private void loadFXML(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafx/fxml/" + fxmlFile));
            Node content = loader.load();
            contentPane.getChildren().clear();
            contentPane.getChildren().add(content);
        } catch (IOException e) {
            System.err.println("Error loading FXML: " + fxmlFile);
            e.printStackTrace();
        }
    }
    
    /**
     * Update navigation button styles
     */
    private void updateNavButtons(Button activeButton) {
        // Remove active class from all buttons
        navDashboard.getStyleClass().remove("active");
        navCreateEvent.getStyleClass().remove("active");
        navRegisterDonor.getStyleClass().remove("active");
        navRecordDonation.getStyleClass().remove("active");
        navViewEvents.getStyleClass().remove("active");
        navViewDonors.getStyleClass().remove("active");
        navStatistics.getStyleClass().remove("active");
        
        // Add active class to current button
        activeButton.getStyleClass().add("active");
    }
}
