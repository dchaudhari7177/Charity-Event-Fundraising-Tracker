package ui;

import ui.panels.*;
import dao.EventDAO;
import dao.DonorDAO;
import dao.PledgeDAO;
import model.Event;
import model.Donor;
import model.Pledge;

import javax.swing.*;
import java.awt.*;

/**
 * Main Window - Central JFrame that manages all UI panels
 * Follows MVC pattern - switches between different views
 */
public class MainWindow extends JFrame {
    
    // Panels
    private DashboardPanel dashboardPanel;
    private CreateEventPanel createEventPanel;
    private RegisterDonorPanel registerDonorPanel;
    private MakeDonationPanel makeDonationPanel;
    private ViewEventsPanel viewEventsPanel;
    private ViewDonorsPanel viewDonorsPanel;
    private StatisticsPanel statisticsPanel;
    
    // Card layout for switching panels
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    // DAOs
    private EventDAO eventDAO;
    private DonorDAO donorDAO;
    private PledgeDAO pledgeDAO;
    
    public MainWindow() {
        setTitle("Charity Event Fundraising Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(UIConstants.WINDOW_WIDTH, UIConstants.WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(true);
        
        // Initialize DAOs
        eventDAO = new EventDAO();
        donorDAO = new DonorDAO();
        pledgeDAO = new PledgeDAO();
        
        // Create main panel with CardLayout for switching views
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(UIConstants.BG_PRIMARY);
        
        // Initialize all panels
        initializePanels();
        
        // Add main panel to frame
        add(mainPanel);
        
        // Show dashboard first
        showDashboard();
    }
    
    private void initializePanels() {
        // Dashboard
        dashboardPanel = new DashboardPanel();
        setupDashboardCallbacks();
        mainPanel.add(dashboardPanel, "Dashboard");
        
        // Create Event
        createEventPanel = new CreateEventPanel();
        setupCreateEventCallbacks();
        mainPanel.add(createEventPanel, "CreateEvent");
        
        // Register Donor
        registerDonorPanel = new RegisterDonorPanel();
        setupRegisterDonorCallbacks();
        mainPanel.add(registerDonorPanel, "RegisterDonor");
        
        // Make Donation
        makeDonationPanel = new MakeDonationPanel();
        setupMakeDonationCallbacks();
        mainPanel.add(makeDonationPanel, "MakeDonation");
        
        // View Events
        viewEventsPanel = new ViewEventsPanel();
        setupViewEventsCallbacks();
        mainPanel.add(viewEventsPanel, "ViewEvents");
        
        // View Donors
        viewDonorsPanel = new ViewDonorsPanel();
        setupViewDonorsCallbacks();
        mainPanel.add(viewDonorsPanel, "ViewDonors");
        
        // Statistics
        statisticsPanel = new StatisticsPanel();
        setupStatisticsCallbacks();
        mainPanel.add(statisticsPanel, "Statistics");
    }
    
    private void setupDashboardCallbacks() {
        dashboardPanel.setOnCreateEvent(this::showCreateEvent);
        dashboardPanel.setOnRegisterDonor(this::showRegisterDonor);
        dashboardPanel.setOnMakeDonation(this::showMakeDonation);
        dashboardPanel.setOnViewEvents(this::showViewEvents);
        dashboardPanel.setOnViewDonors(this::showViewDonors);
        dashboardPanel.setOnViewStatistics(this::showStatistics);
    }
    
    private void setupCreateEventCallbacks() {
        createEventPanel.setOnBack(this::showDashboard);
        createEventPanel.setOnSubmit(event -> {
            try {
                eventDAO.addEvent(event);
                JOptionPane.showMessageDialog(this, "✓ Event created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                showDashboard();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    private void setupRegisterDonorCallbacks() {
        registerDonorPanel.setOnBack(this::showDashboard);
        registerDonorPanel.setOnSubmit(donor -> {
            try {
                donorDAO.addDonor(donor);
                JOptionPane.showMessageDialog(this, "✓ Donor registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                showDashboard();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    private void setupMakeDonationCallbacks() {
        makeDonationPanel.setOnBack(this::showDashboard);
        makeDonationPanel.setOnSubmit(pledge -> {
            try {
                pledgeDAO.addPledge(pledge);
                
                // Update event's collected amount
                Event event = eventDAO.getEventById(pledge.getEventId());
                if (event != null) {
                    event.setCollectedAmount(event.getCollectedAmount() + pledge.getPledgeAmount());
                    eventDAO.updateCollectedAmount(event.getEventId(), event.getCollectedAmount());
                }
                
                JOptionPane.showMessageDialog(this, "✓ Donation recorded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                showDashboard();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    private void setupViewEventsCallbacks() {
        viewEventsPanel.setOnBack(this::showDashboard);
    }
    
    private void setupViewDonorsCallbacks() {
        viewDonorsPanel.setOnBack(this::showDashboard);
    }
    
    private void setupStatisticsCallbacks() {
        statisticsPanel.setOnBack(this::showDashboard);
    }
    
    // Navigation methods
    private void showDashboard() {
        cardLayout.show(mainPanel, "Dashboard");
    }
    
    private void showCreateEvent() {
        cardLayout.show(mainPanel, "CreateEvent");
    }
    
    private void showRegisterDonor() {
        cardLayout.show(mainPanel, "RegisterDonor");
    }
    
    private void showMakeDonation() {
        // Load donors and events for combo boxes
        try {
            java.util.List<Donor> donors = donorDAO.getAllDonors();
            java.util.List<Event> events = eventDAO.getAllEvents();
            
            java.util.List<String> donorList = new java.util.ArrayList<>();
            for (Donor d : donors) {
                donorList.add(d.getDonorId() + " - " + d.getDonorName());
            }
            
            java.util.List<String> eventList = new java.util.ArrayList<>();
            for (Event e : events) {
                eventList.add(e.getEventId() + " - " + e.getEventName());
            }
            
            makeDonationPanel.setDonors(donorList);
            makeDonationPanel.setEvents(eventList);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        cardLayout.show(mainPanel, "MakeDonation");
    }
    
    private void showViewEvents() {
        try {
            java.util.List<Event> events = eventDAO.getAllEvents();
            viewEventsPanel.loadEvents(events);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading events: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        cardLayout.show(mainPanel, "ViewEvents");
    }
    
    private void showViewDonors() {
        try {
            java.util.List<Donor> donors = donorDAO.getAllDonors();
            viewDonorsPanel.loadDonors(donors);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading donors: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        cardLayout.show(mainPanel, "ViewDonors");
    }
    
    private void showStatistics() {
        try {
            int totalEvents = eventDAO.getAllEvents().size();
            int totalDonors = donorDAO.getAllDonors().size();
            java.util.List<Pledge> pledges = pledgeDAO.getAllPledges();
            int totalPledges = pledges.size();
            
            double totalFunds = 0;
            for (Pledge p : pledges) {
                totalFunds += p.getPledgeAmount();
            }
            
            statisticsPanel.updateStatistics(totalEvents, totalDonors, totalPledges, totalFunds);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading statistics: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        cardLayout.show(mainPanel, "Statistics");
    }
}
