package javafx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

import dao.EventDAO;
import dao.DonorDAO;
import dao.PledgeDAO;
import model.Event;
import model.Donor;
import javafx.util.AnimationUtils;

import java.util.ArrayList;

/**
 * Dashboard Controller - Display overview and quick actions
 */
public class DashboardController {
    
    @FXML
    private Label totalEventsValue;
    @FXML
    private Label totalEventsChange;
    @FXML
    private Label totalDonorsValue;
    @FXML
    private Label totalDonorsChange;
    @FXML
    private Label targetAmountValue;
    @FXML
    private Label targetAmountChange;
    @FXML
    private Label collectedAmountValue;
    @FXML
    private Label collectedAmountChange;
    
    @FXML
    private TableView<Event> recentEventsTable;
    @FXML
    private TableColumn<Event, Integer> eventIdColumn;
    @FXML
    private TableColumn<Event, String> eventNameColumn;
    @FXML
    private TableColumn<Event, Double> targetColumn;
    @FXML
    private TableColumn<Event, Double> collectedColumn;
    @FXML
    private TableColumn<Event, Double> progressColumn;
    
    @FXML
    private VBox createEventCard;
    @FXML
    private VBox registerDonorCard;
    @FXML
    private VBox recordDonationCard;
    
    private EventDAO eventDAO;
    private DonorDAO donorDAO;
    private PledgeDAO pledgeDAO;
    
    /**
     * Initialize controller
     */
    @FXML
    public void initialize() {
        eventDAO = new EventDAO();
        donorDAO = new DonorDAO();
        pledgeDAO = new PledgeDAO();
        
        setupTableColumns();
        loadDashboardData();
        setupCardAnimations();
    }
    
    /**
     * Setup table columns
     */
    private void setupTableColumns() {
        eventIdColumn.setCellValueFactory(new PropertyValueFactory<>("eventId"));
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        targetColumn.setCellValueFactory(new PropertyValueFactory<>("targetAmount"));
        collectedColumn.setCellValueFactory(new PropertyValueFactory<>("collectedAmount"));
    }
    
    /**
     * Load dashboard data from database
     */
    private void loadDashboardData() {
        // Get all events and donors
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
        
        // Update stat labels
        totalEventsValue.setText(String.valueOf(totalEvents));
        totalDonorsValue.setText(String.valueOf(totalDonors));
        targetAmountValue.setText("₹" + String.format("%.0f", totalTarget));
        collectedAmountValue.setText("₹" + String.format("%.0f", totalCollected));
        
        // Calculate achievement percentage
        double achievementPercent = totalTarget > 0 ? (totalCollected / totalTarget) * 100 : 0;
        collectedAmountChange.setText(String.format("%.1f%% achieved", achievementPercent));
        
        // Load recent events in table
        ObservableList<Event> eventList = FXCollections.observableArrayList(events);
        recentEventsTable.setItems(eventList);
    }
    
    /**
     * Setup animation on cards
     */
    private void setupCardAnimations() {
        // Fade in cards with animation
        createEventCard.setOpacity(0);
        registerDonorCard.setOpacity(0);
        recordDonationCard.setOpacity(0);
        
        FadeTransition fadeIn1 = AnimationUtils.fadeIn(createEventCard, 400);
        fadeIn1.play();
        
        FadeTransition fadeIn2 = AnimationUtils.fadeIn(registerDonorCard, 400);
        fadeIn2.setDelay(Duration.millis(100));
        fadeIn2.play();
        
        FadeTransition fadeIn3 = AnimationUtils.fadeIn(recordDonationCard, 400);
        fadeIn3.setDelay(Duration.millis(200));
        fadeIn3.play();
    }
    
    /**
     * Handle create event card click
     */
    @FXML
    public void handleCreateEvent() {
        // Navigate to create event screen
        MainController main = getMainController();
        if (main != null) {
            main.showCreateEvent();
        }
    }
    
    /**
     * Handle register donor card click
     */
    @FXML
    public void handleRegisterDonor() {
        // Navigate to register donor screen
        MainController main = getMainController();
        if (main != null) {
            main.showRegisterDonor();
        }
    }
    
    /**
     * Handle record donation card click
     */
    @FXML
    public void handleRecordDonation() {
        // Navigate to record donation screen
        MainController main = getMainController();
        if (main != null) {
            main.showRecordDonation();
        }
    }
    
    /**
     * Handle view all events button
     */
    @FXML
    public void handleViewAllEvents() {
        MainController main = getMainController();
        if (main != null) {
            main.showViewEvents();
        }
    }
    
    /**
     * Get main controller from scene
     */
    private MainController getMainController() {
        // This will be implemented in the main app loading
        return null;
    }
}
