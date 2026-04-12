package ui;

import ui.panels.*;
import dao.*;

import javax.swing.*;
import java.awt.*;

/**
 * Enhanced Main Window - Professional application frame with all enhanced panels
 */
public class MainWindow_Enhanced extends JFrame {
    
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    private DashboardPanel_Enhanced dashboardPanel;
    private CreateEventPanel_Enhanced createEventPanel;
    private RegisterDonorPanel_Enhanced registerDonorPanel;
    private MakeDonationPanel_Enhanced makeDonationPanel;
    private ViewEventsPanel_Enhanced viewEventsPanel;
    private ViewDonorsPanel_Enhanced viewDonorsPanel;
    private StatisticsPanel_Enhanced statisticsPanel;
    
    public MainWindow_Enhanced() {
        setTitle("🎯 Charity Event Fundraiser - Professional Edition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(UIConstants_Enhanced.WINDOW_WIDTH, UIConstants_Enhanced.WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Initialize CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(UIConstants_Enhanced.BG_PRIMARY);
        
        // Create and add all panels
        initializePanels();
        
        add(mainPanel);
        setVisible(true);
    }
    
    private void initializePanels() {
        // Dashboard
        dashboardPanel = new DashboardPanel_Enhanced();
        dashboardPanel.setOnCreateEvent(() -> showPanel("createEvent"));
        dashboardPanel.setOnRegisterDonor(() -> showPanel("registerDonor"));
        dashboardPanel.setOnMakeDonation(() -> showPanel("makeDonation"));
        dashboardPanel.setOnViewEvents(() -> showPanel("viewEvents"));
        dashboardPanel.setOnViewDonors(() -> showPanel("viewDonors"));
        dashboardPanel.setOnViewStatistics(() -> showPanel("statistics"));
        mainPanel.add(dashboardPanel, "dashboard");
        
        // Create Event
        createEventPanel = new CreateEventPanel_Enhanced();
        createEventPanel.setOnBack(() -> showPanel("dashboard"));
        mainPanel.add(createEventPanel, "createEvent");
        
        // Register Donor
        registerDonorPanel = new RegisterDonorPanel_Enhanced();
        registerDonorPanel.setOnBack(() -> showPanel("dashboard"));
        mainPanel.add(registerDonorPanel, "registerDonor");
        
        // Make Donation
        makeDonationPanel = new MakeDonationPanel_Enhanced();
        makeDonationPanel.setOnBack(() -> showPanel("dashboard"));
        mainPanel.add(makeDonationPanel, "makeDonation");
        
        // View Events
        viewEventsPanel = new ViewEventsPanel_Enhanced();
        viewEventsPanel.setOnBack(() -> showPanel("dashboard"));
        mainPanel.add(viewEventsPanel, "viewEvents");
        
        // View Donors
        viewDonorsPanel = new ViewDonorsPanel_Enhanced();
        viewDonorsPanel.setOnBack(() -> showPanel("dashboard"));
        mainPanel.add(viewDonorsPanel, "viewDonors");
        
        // Statistics
        statisticsPanel = new StatisticsPanel_Enhanced();
        statisticsPanel.setOnBack(() -> showPanel("dashboard"));
        mainPanel.add(statisticsPanel, "statistics");
        
        // Show dashboard first
        showPanel("dashboard");
    }
    
    /**
     * Show a panel by name
     */
    private void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }
}
