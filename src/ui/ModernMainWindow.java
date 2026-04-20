package ui;

import ui.panels.modern.*;
import ui.components.ModernComponents;
import dao.EventDAO;
import dao.DonorDAO;
import dao.PledgeDAO;

import javax.swing.*;
import java.awt.*;

/**
 * Modern Main Window - Premium dark theme UI with smooth transitions
 * Central frame managing all modern UI panels with CardLayout navigation
 */
public class ModernMainWindow extends JFrame {
    
    // Modern Panels
    private ModernDashboardPanel dashboardPanel;
    private ModernCreateEventPanel createEventPanel;
    private ModernRegisterDonorPanel registerDonorPanel;
    private ModernMakeDonationPanel makeDonationPanel;
    private ModernViewEventsPanel viewEventsPanel;
    private ModernViewDonorsPanel viewDonorsPanel;
    private ModernStatisticsPanel statisticsPanel;
    
    // Navigation
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    // DAOs
    private EventDAO eventDAO;
    private DonorDAO donorDAO;
    private PledgeDAO pledgeDAO;
    
    public ModernMainWindow() {
        initWindow();
        initializeDAOs();
        createMainPanel();
        initializePanels();
        setupNavigation();
        showDashboard();
    }
    
    private void initWindow() {
        setTitle("Charity Event Fundraising Tracker - Premium Edition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(ModernUIConstants.WINDOW_WIDTH, ModernUIConstants.WINDOW_HEIGHT);
        setMinimumSize(new Dimension(
            ModernUIConstants.WINDOW_MIN_WIDTH,
            ModernUIConstants.WINDOW_MIN_HEIGHT
        ));
        setLocationRelativeTo(null);
        setResizable(true);
        
        // Set modern appearance
        setBackground(ModernUIConstants.BG_PRIMARY);
        
        // Enable high quality rendering
        System.setProperty("swing.aatext", "true");
        System.setProperty("apple.awt.textantialiasing", "true");
    }
    
    private void initializeDAOs() {
        eventDAO = new EventDAO();
        donorDAO = new DonorDAO();
        pledgeDAO = new PledgeDAO();
    }
    
    private void createMainPanel() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(ModernUIConstants.BG_PRIMARY);
        add(mainPanel);
    }
    
    private void initializePanels() {
        // Dashboard
        dashboardPanel = new ModernDashboardPanel();
        mainPanel.add(dashboardPanel, "Dashboard");
        
        // Create Event
        createEventPanel = new ModernCreateEventPanel();
        mainPanel.add(createEventPanel, "CreateEvent");
        
        // Register Donor
        registerDonorPanel = new ModernRegisterDonorPanel();
        mainPanel.add(registerDonorPanel, "RegisterDonor");
        
        // Make Donation
        makeDonationPanel = new ModernMakeDonationPanel();
        mainPanel.add(makeDonationPanel, "MakeDonation");
        
        // View Events
        viewEventsPanel = new ModernViewEventsPanel();
        mainPanel.add(viewEventsPanel, "ViewEvents");
        
        // View Donors
        viewDonorsPanel = new ModernViewDonorsPanel();
        mainPanel.add(viewDonorsPanel, "ViewDonors");
        
        // Statistics
        statisticsPanel = new ModernStatisticsPanel();
        mainPanel.add(statisticsPanel, "Statistics");
    }
    
    private void setupNavigation() {
        // Dashboard navigation
        dashboardPanel.setOnCreateEvent(() -> showPanel("CreateEvent"));
        dashboardPanel.setOnRegisterDonor(() -> showPanel("RegisterDonor"));
        dashboardPanel.setOnMakeDonation(() -> showPanel("MakeDonation"));
        dashboardPanel.setOnViewEvents(() -> showPanel("ViewEvents"));
        dashboardPanel.setOnViewDonors(() -> showPanel("ViewDonors"));
        dashboardPanel.setOnViewStatistics(() -> showPanel("Statistics"));
        
        // Create Event navigation
        createEventPanel.setOnBackClick(() -> showPanel("Dashboard"));
        
        // Register Donor navigation
        registerDonorPanel.setOnBackClick(() -> showPanel("Dashboard"));
        
        // Make Donation navigation
        makeDonationPanel.setOnBackClick(() -> showPanel("Dashboard"));
        
        // View Events navigation
        viewEventsPanel.setOnBackClick(() -> showPanel("Dashboard"));
        
        // View Donors navigation
        viewDonorsPanel.setOnBackClick(() -> showPanel("Dashboard"));
        
        // Statistics navigation
        statisticsPanel.setOnBackClick(() -> showPanel("Dashboard"));
    }
    
    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }
    
    public void showDashboard() {
        showPanel("Dashboard");
    }
}
