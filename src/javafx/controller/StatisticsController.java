package javafx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;

import dao.EventDAO;
import dao.DonorDAO;
import model.Event;
import model.Donor;

import java.util.ArrayList;

/**
 * Statistics Controller - Display analytics and statistics
 */
public class StatisticsController {
    
    @FXML
    private Label metricsEvents;
    
    @FXML
    private Label metricsDonors;
    
    @FXML
    private Label metricsTarget;
    
    @FXML
    private Label metricsCollected;
    
    @FXML
    private Label metricsAchievement;
    
    @FXML
    private VBox eventProgressContainer;
    
    private EventDAO eventDAO;
    private DonorDAO donorDAO;
    
    /**
     * Initialize controller
     */
    @FXML
    public void initialize() {
        eventDAO = new EventDAO();
        donorDAO = new DonorDAO();
        loadStatistics();
    }
    
    /**
     * Load statistics from database
     */
    private void loadStatistics() {
        ArrayList<Event> events = eventDAO.getAllEvents();
        ArrayList<Donor> donors = donorDAO.getAllDonors();
        
        // Calculate totals
        int totalEvents = events.size();
        int totalDonors = donors.size();
        double totalTarget = 0;
        double totalCollected = 0;
        
        for (Event event : events) {
            totalTarget += event.getTargetAmount();
            totalCollected += event.getCollectedAmount();
        }
        
        // Update metric labels
        metricsEvents.setText(String.valueOf(totalEvents));
        metricsDonors.setText(String.valueOf(totalDonors));
        metricsTarget.setText("₹" + String.format("%.0f", totalTarget));
        metricsCollected.setText("₹" + String.format("%.0f", totalCollected));
        
        // Calculate achievement
        double achievementPercent = totalTarget > 0 ? (totalCollected / totalTarget) * 100 : 0;
        metricsAchievement.setText(String.format("%.1f%% achieved", achievementPercent));
        
        // Load event progress bars
        loadEventProgress(events);
    }
    
    /**
     * Load event progress visualization
     */
    private void loadEventProgress(ArrayList<Event> events) {
        eventProgressContainer.getChildren().clear();
        
        if (events.isEmpty()) {
            Label noEventsLabel = new Label("No events yet. Create an event to get started.");
            noEventsLabel.setStyle("-fx-text-fill: #9CA3AF;");
            eventProgressContainer.getChildren().add(noEventsLabel);
            return;
        }
        
        for (Event event : events) {
            VBox eventItem = createEventProgressItem(event);
            eventProgressContainer.getChildren().add(eventItem);
        }
    }
    
    /**
     * Create event progress item
     */
    private VBox createEventProgressItem(Event event) {
        VBox container = new VBox();
        container.setSpacing(8);
        container.setPadding(new Insets(12, 0, 12, 0));
        container.setStyle("-fx-border-color: #1F2937; -fx-border-width: 0 0 1 0;");
        
        // Event name and stats
        Label eventLabel = new Label(event.getEventId() + ". " + event.getEventName());
        eventLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #E8EAED;");
        
        // Progress info
        double progress = event.getTargetAmount() > 0 
            ? (event.getCollectedAmount() / event.getTargetAmount()) * 100 
            : 0;
        
        Label statsLabel = new Label(String.format(
            "₹%.0f / ₹%.0f (%.1f%%)", 
            event.getCollectedAmount(), 
            event.getTargetAmount(), 
            progress
        ));
        statsLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #9CA3AF;");
        
        // Progress bar
        ProgressBar progressBar = new ProgressBar(Math.min(progress / 100, 1.0));
        progressBar.setStyle("-fx-min-height: 6px;");
        progressBar.setPrefHeight(6);
        
        // Set color based on progress
        if (progress >= 100) {
            progressBar.setStyle("-fx-min-height: 6px; -fx-control-inner-background: #10FF82;");
        } else if (progress >= 75) {
            progressBar.setStyle("-fx-min-height: 6px; -fx-control-inner-background: #00F1FE;");
        } else if (progress >= 50) {
            progressBar.setStyle("-fx-min-height: 6px; -fx-control-inner-background: #FFD700;");
        } else {
            progressBar.setStyle("-fx-min-height: 6px; -fx-control-inner-background: #FF4787;");
        }
        
        container.getChildren().addAll(eventLabel, statsLabel, progressBar);
        return container;
    }
    
    /**
     * Handle refresh button
     */
    @FXML
    public void handleRefresh() {
        loadStatistics();
    }
}
