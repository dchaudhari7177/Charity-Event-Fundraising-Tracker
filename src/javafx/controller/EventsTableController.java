package javafx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import dao.EventDAO;
import model.Event;

import java.util.ArrayList;

/**
 * Events Table Controller - Display all events in a table
 */
public class EventsTableController {
    
    @FXML
    private TableView<Event> eventsTable;
    
    @FXML
    private TableColumn<Event, Integer> idColumn;
    
    @FXML
    private TableColumn<Event, String> nameColumn;
    
    @FXML
    private TableColumn<Event, String> descriptionColumn;
    
    @FXML
    private TableColumn<Event, Double> targetColumn;
    
    @FXML
    private TableColumn<Event, Double> collectedColumn;
    
    @FXML
    private TableColumn<Event, Double> progressColumn;
    
    @FXML
    private TextField searchField;
    
    @FXML
    private Label totalCountLabel;
    
    private EventDAO eventDAO;
    private ArrayList<Event> allEvents;
    
    /**
     * Initialize controller
     */
    @FXML
    public void initialize() {
        eventDAO = new EventDAO();
        setupTableColumns();
        loadEvents();
        
        // Add search filter
        searchField.textProperty().addListener((obs, oldVal, newVal) -> filterEvents(newVal));
    }
    
    /**
     * Setup table columns with cell factories
     */
    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("eventId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        
        // Custom cell factories for formatting
        targetColumn.setCellValueFactory(new PropertyValueFactory<>("targetAmount"));
        targetColumn.setCellFactory(col -> new FormattedTableCell("₹"));
        
        collectedColumn.setCellValueFactory(new PropertyValueFactory<>("collectedAmount"));
        collectedColumn.setCellFactory(col -> new FormattedTableCell("₹"));
        
        progressColumn.setCellValueFactory(cellData -> {
            Event event = cellData.getValue();
            double progress = event.getTargetAmount() > 0 
                ? (event.getCollectedAmount() / event.getTargetAmount()) * 100 
                : 0;
            javafx.beans.value.ObservableValue<Double> ov = 
                new javafx.beans.property.SimpleObjectProperty<>(progress);
            return ov;
        });
        progressColumn.setCellFactory(col -> new FormattedTableCell("%"));
    }
    
    /**
     * Load events from database
     */
    private void loadEvents() {
        allEvents = eventDAO.getAllEvents();
        displayEvents(allEvents);
    }
    
    /**
     * Display events in table
     */
    private void displayEvents(ArrayList<Event> events) {
        ObservableList<Event> observableList = FXCollections.observableArrayList(events);
        eventsTable.setItems(observableList);
        totalCountLabel.setText("Total: " + events.size() + " events");
    }
    
    /**
     * Filter events based on search query
     */
    private void filterEvents(String query) {
        if (query == null || query.isEmpty()) {
            displayEvents(allEvents);
        } else {
            ArrayList<Event> filtered = new ArrayList<>();
            String lowerQuery = query.toLowerCase();
            
            for (Event event : allEvents) {
                if (event.getEventName().toLowerCase().contains(lowerQuery) ||
                    event.getDescription().toLowerCase().contains(lowerQuery) ||
                    String.valueOf(event.getEventId()).contains(query)) {
                    filtered.add(event);
                }
            }
            
            displayEvents(filtered);
        }
    }
    
    /**
     * Handle refresh button
     */
    @FXML
    public void handleRefresh() {
        loadEvents();
        searchField.clear();
    }
    
    /**
     * Custom table cell for formatting numbers
     */
    private static class FormattedTableCell extends javafx.scene.control.TableCell<Event, Double> {
        private String prefix;
        
        public FormattedTableCell(String prefix) {
            this.prefix = prefix;
        }
        
        @Override
        protected void updateItem(Double item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
                if ("%".equals(prefix)) {
                    setText(String.format("%.1f%%", item));
                } else {
                    setText(prefix + String.format("%.0f", item));
                }
            }
        }
    }
}
