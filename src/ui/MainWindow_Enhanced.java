package ui;

import ui.panels.*;
import model.Admin;
import dao.*;

import javax.swing.*;
import java.awt.*;

/**
 * Enhanced Main Window - Professional application frame with dual user/admin modes
 */
public class MainWindow_Enhanced extends JFrame {
    
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel navBarPanel;
    private JLabel modeLabel;
    private JButton logoutButton;
    private Admin currentAdmin;
    private boolean isAdminMode = false;
    
    // Panels
    private RoleSelectorPanel_Enhanced roleSelectorPanel;
    private LoginPanel_Enhanced loginPanel;
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
        
        // Create navbar
        navBarPanel = createNavBar();
        add(navBarPanel, BorderLayout.NORTH);
        
        // Initialize CardLayout for main content
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(UIConstants_Enhanced.BG_PRIMARY);
        
        // Create and add all panels
        initializePanels();
        
        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }
    
    private JPanel createNavBar() {
        JPanel navbar = new JPanel();
        navbar.setBackground(UIConstants_Enhanced.BG_SECONDARY);
        navbar.setLayout(new BorderLayout());
        navbar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UIConstants_Enhanced.BORDER_COLOR));
        navbar.setPreferredSize(new Dimension(getWidth(), 50));
        
        // Left - Title
        JLabel titleLabel = new JLabel("🎯 Charity Event Fundraiser");
        titleLabel.setFont(UIConstants_Enhanced.FONT_SUBHEADING);
        titleLabel.setForeground(UIConstants_Enhanced.ACCENT_GREEN);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, UIConstants_Enhanced.PADDING_LARGE, 0, 0));
        navbar.add(titleLabel, BorderLayout.WEST);
        
        // Right - Mode and Logout
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(UIConstants_Enhanced.BG_SECONDARY);
        rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, UIConstants_Enhanced.PADDING_LARGE, 0));
        
        modeLabel = new JLabel("👥 User Mode");
        modeLabel.setFont(UIConstants_Enhanced.FONT_BODY);
        modeLabel.setForeground(UIConstants_Enhanced.TEXT_SECONDARY);
        rightPanel.add(modeLabel);
        
        logoutButton = EnhancedComponents.createAnimatedSecondaryButton("🚪 Change Role");
        logoutButton.setVisible(false); // Hidden initially
        logoutButton.addActionListener(e -> onChangeRole());
        rightPanel.add(logoutButton);
        
        navbar.add(rightPanel, BorderLayout.EAST);
        
        return navbar;
    }
    
    private void initializePanels() {
        // Role Selector Panel - Show first
        roleSelectorPanel = new RoleSelectorPanel_Enhanced();
        roleSelectorPanel.setOnUserSelected(() -> onUserModeSelected());
        roleSelectorPanel.setOnAdminLoginSelected(() -> onAdminModeSelected());
        mainPanel.add(roleSelectorPanel, "roleSelector");
        
        // Login Panel - For admin login
        loginPanel = new LoginPanel_Enhanced();
        loginPanel.setOnLoginSuccess(() -> onAdminLogin());
        mainPanel.add(loginPanel, "login");
        
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
        
        // Show role selector first
        showPanel("roleSelector");
    }
    
    /**
     * Called when user selects User mode
     */
    private void onUserModeSelected() {
        isAdminMode = false;
        currentAdmin = null;
        
        modeLabel.setText("👥 User Mode");
        logoutButton.setVisible(false);
        
        // Disable admin features
        dashboardPanel.setAdminMode(false);
        viewEventsPanel.setAdminMode(false);
        statisticsPanel.setAdminMode(false);
        
        setTitle("🎯 Charity Event Fundraiser - User Mode");
        showPanel("dashboard");
    }
    
    /**
     * Called when user selects Admin login
     */
    private void onAdminModeSelected() {
        loginPanel.clearFields();
        showPanel("login");
    }
    
    /**
     * Called when admin successfully logs in
     */
    private void onAdminLogin() {
        currentAdmin = loginPanel.getCurrentAdmin();
        
        if (currentAdmin != null) {
            isAdminMode = true;
            String adminName = currentAdmin.getFullName();
            
            modeLabel.setText("🔐 Admin: " + adminName);
            logoutButton.setVisible(true);
            
            // Enable admin features
            dashboardPanel.setAdminMode(true);
            viewEventsPanel.setAdminMode(true);
            statisticsPanel.setAdminMode(true);
            
            setTitle("🎯 Charity Event Fundraiser - Admin Mode | " + adminName);
            showPanel("dashboard");
        }
    }
    
    /**
     * Called when user clicks Change Role
     */
    private void onChangeRole() {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Switch to role selection?\nYou will be logged out.",
            "Confirm",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            currentAdmin = null;
            isAdminMode = false;
            modeLabel.setText("👥 User Mode");
            logoutButton.setVisible(false);
            showPanel("roleSelector");
        }
    }
    
    /**
     * Show a panel by name
     */
    private void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }
    
    /**
     * Get current logged-in admin
     */
    public Admin getCurrentAdmin() {
        return currentAdmin;
    }
    
    /**
     * Check if in admin mode
     */
    public boolean isAdminMode() {
        return isAdminMode;
    }
}
