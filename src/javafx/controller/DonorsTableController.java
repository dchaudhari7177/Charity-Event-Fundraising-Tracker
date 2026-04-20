package javafx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import dao.DonorDAO;
import model.Donor;

import java.util.ArrayList;

/**
 * Donors Table Controller - Display all donors in a table
 */
public class DonorsTableController {
    
    @FXML
    private TableView<Donor> donorsTable;
    
    @FXML
    private TableColumn<Donor, Integer> idColumn;
    
    @FXML
    private TableColumn<Donor, String> nameColumn;
    
    @FXML
    private TableColumn<Donor, String> emailColumn;
    
    @FXML
    private TableColumn<Donor, String> phoneColumn;
    
    @FXML
    private TextField searchField;
    
    @FXML
    private Label totalCountLabel;
    
    private DonorDAO donorDAO;
    private ArrayList<Donor> allDonors;
    
    /**
     * Initialize controller
     */
    @FXML
    public void initialize() {
        donorDAO = new DonorDAO();
        setupTableColumns();
        loadDonors();
        
        // Add search filter
        searchField.textProperty().addListener((obs, oldVal, newVal) -> filterDonors(newVal));
    }
    
    /**
     * Setup table columns with cell factories
     */
    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("donorId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("donorName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    }
    
    /**
     * Load donors from database
     */
    private void loadDonors() {
        allDonors = donorDAO.getAllDonors();
        displayDonors(allDonors);
    }
    
    /**
     * Display donors in table
     */
    private void displayDonors(ArrayList<Donor> donors) {
        ObservableList<Donor> observableList = FXCollections.observableArrayList(donors);
        donorsTable.setItems(observableList);
        totalCountLabel.setText("Total: " + donors.size() + " donors");
    }
    
    /**
     * Filter donors based on search query
     */
    private void filterDonors(String query) {
        if (query == null || query.isEmpty()) {
            displayDonors(allDonors);
        } else {
            ArrayList<Donor> filtered = new ArrayList<>();
            String lowerQuery = query.toLowerCase();
            
            for (Donor donor : allDonors) {
                if (donor.getDonorName().toLowerCase().contains(lowerQuery) ||
                    donor.getEmail().toLowerCase().contains(lowerQuery) ||
                    donor.getPhoneNumber().contains(query) ||
                    String.valueOf(donor.getDonorId()).contains(query)) {
                    filtered.add(donor);
                }
            }
            
            displayDonors(filtered);
        }
    }
    
    /**
     * Handle refresh button
     */
    @FXML
    public void handleRefresh() {
        loadDonors();
        searchField.clear();
    }
}
