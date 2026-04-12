package controller;

import service.*;
import view.ConsoleView;
import model.*;
import observer.*;
import db.DBConnection;
import java.util.ArrayList;

/**
 * CharityTrackerController: Main Application Controller
 * 
 * MVC PATTERN: Controller Component
 * RESPONSIBILITY: Orchestrate user input, service calls, and view updates
 * 
 * SOLID PRINCIPLES APPLIED:
 * 1. Single Responsibility: Only handles user request routing
 * 2. Open/Closed: Can add new operations without modifying core logic
 * 3. Liskov Substitution: Services can be replaced with alternatives
 * 4. Dependency Inversion: Depends on service abstractions (could use interfaces)
 * 
 * DESIGN PATTERNS USED:
 * - MVC: Separates presentation, business logic, and data access
 * - Facade: ServiceFactory provides simplified access to services
 * - Observer: Notifies observers of donation events
 * - Singleton: Uses singleton services
 * 
 * Flow:
 * 1. Controller receives input from View
 * 2. Calls appropriate Service method
 * 3. Service processes business logic
 * 4. Service calls DAO for data persistence
 * 5. Controller displays results through View
 */
public class CharityTrackerController {
    
    // Services (obtained from Factory)
    private EventService eventService;
    private DonorService donorService;
    private PledgeService pledgeService;
    
    // Components
    private ConsoleView view;
    private DonationEventManager eventManager;
    
    public CharityTrackerController() {
        // Initialize services using Factory pattern
        this.eventService = ServiceFactory.getEventService();
        this.donorService = ServiceFactory.getDonorService();
        this.pledgeService = ServiceFactory.getPledgeService();
        
        // Initialize view
        this.view = new ConsoleView();
        
        // Get observer manager
        this.eventManager = DonationEventManager.getInstance();
        
        // Register progress tracker as observer
        EventProgressTracker tracker = new EventProgressTracker("System Tracker");
        eventManager.subscribe(tracker);
    }
    
    /**
     * Run the main application loop
     */
    public void run() {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║   CHARITY EVENT FUNDRAISING TRACKER - DATABASE VERSION  ║");
        System.out.println("║      (MVC Architecture with Design Patterns)            ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        
        // Initialize database
        System.out.println("\n→ Initializing database...");
        DBConnection.initializeDatabase();
        
        boolean running = true;
        while (running) {
            view.displayMainMenu();
            int choice = view.getIntInput();
            
            switch (choice) {
                case 1:
                    handleCreateEvent();
                    break;
                case 2:
                    handleRegisterDonor();
                    break;
                case 3:
                    handleMakeDonation();
                    break;
                case 4:
                    handleViewAllEvents();
                    break;
                case 5:
                    handleViewDonorsForEvent();
                    break;
                case 6:
                    handleViewAllDonors();
                    break;
                case 7:
                    handleViewStatistics();
                    break;
                case 8:
                    running = false;
                    break;
                default:
                    view.displayError("Invalid choice. Please try again.");
            }
        }
        
        cleanup();
    }
    
    // ==================== EVENT OPERATIONS ====================
    
    /**
     * Handle: Create Event
     * FLOW: View Input → Validation → Service → DAO → View Output
     */
    private void handleCreateEvent() {
        view.displayCreateEventForm();
        
        String name = view.inputEventName();
        if (name.isEmpty()) {
            view.displayError("Event name cannot be empty");
            return;
        }
        
        double targetAmount = view.inputTargetAmount();
        if (targetAmount < 0) {
            view.displayError("Please enter a valid amount");
            return;
        }
        
        String description = view.inputEventDescription();
        
        // Call service (Service handles validation and DAO call)
        if (eventService.createEvent(name, targetAmount, description)) {
            view.displayEventCreated(name);
        } else {
            view.displayError("Failed to create event");
        }
    }
    
    /**
     * Handle: View All Events
     */
    private void handleViewAllEvents() {
        ArrayList<Event> events = eventService.getAllEvents();
        view.displayAllEvents(events);
    }
    
    // ==================== DONOR OPERATIONS ====================
    
    /**
     * Handle: Register Donor
     * FLOW: View Input → Validation → Service → DAO → View Output
     */
    private void handleRegisterDonor() {
        view.displayRegisterDonorForm();
        
        String name = view.inputDonorName();
        if (name.isEmpty()) {
            view.displayError("Donor name cannot be empty");
            return;
        }
        
        String email = view.inputDonorEmail();
        String phone = view.inputDonorPhone();
        
        // Call service (Service handles validation)
        if (donorService.registerDonor(name, email, phone)) {
            view.displayDonorRegistered(name);
        } else {
            view.displayError("Failed to register donor");
        }
    }
    
    /**
     * Handle: View All Donors
     */
    private void handleViewAllDonors() {
        ArrayList<Donor> donors = donorService.getAllDonors();
        view.displayAllDonors(donors);
    }
    
    // ==================== DONATION OPERATIONS ====================
    
    /**
     * Handle: Make Donation
     * FLOW: View Input → Validation → Service → DAO → Observer Notification → View Output
     * 
     * This operation demonstrates:
     * - MVC Integration: View → Controller → Service → DAO
     * - Observer Pattern: Notifies observers of donation
     * - Business Logic: Service handles donation state
     */
    private void handleMakeDonation() {
        view.displayMakeDonationForm();
        
        int donorId = view.inputDonorId();
        if (donorId <= 0) {
            view.displayError("Invalid donor ID");
            return;
        }
        
        // Verify donor exists
        if (donorService.getDonor(donorId) == null) {
            view.displayError("Donor not found");
            return;
        }
        
        int eventId = view.inputEventId();
        if (eventId <= 0) {
            view.displayError("Invalid event ID");
            return;
        }
        
        // Verify event exists
        if (eventService.getEvent(eventId) == null) {
            view.displayError("Event not found");
            return;
        }
        
        double amount = view.inputDonationAmount();
        if (amount < 0) {
            view.displayError("Please enter a valid amount");
            return;
        }
        
        String date = view.inputDonationDate();
        
        // Call service (handles donation + observer notification)
        if (pledgeService.makeDonation(donorId, eventId, amount, date)) {
            view.displayDonationMade();
        } else {
            view.displayError("Failed to make donation");
        }
    }
    
    /**
     * Handle: View Donors for Specific Event
     */
    private void handleViewDonorsForEvent() {
        System.out.print("\nEnter Event ID: ");
        int eventId = view.getIntInput();
        
        if (eventService.getEvent(eventId) == null) {
            view.displayError("Event not found");
            return;
        }
        
        ArrayList<Pledge> pledges = pledgeService.getPledgesForEvent(eventId);
        view.displayDonorsForEvent(eventId, pledges);
    }
    
    // ==================== STATISTICS ====================
    
    /**
     * Handle: View Statistics
     */
    private void handleViewStatistics() {
        int totalEvents = eventService.getTotalEvents();
        int totalDonors = donorService.getTotalDonors();
        int totalPledges = pledgeService.getTotalPledges();
        
        // Calculate total funds
        double totalFunds = 0;
        ArrayList<Event> events = eventService.getAllEvents();
        for (Event event : events) {
            totalFunds += event.getCollectedAmount();
        }
        
        view.displayStatistics(totalEvents, totalDonors, totalPledges, totalFunds);
    }
    
    // ==================== CLEANUP ====================
    
    /**
     * Cleanup: Close connections and resources
     */
    private void cleanup() {
        DBConnection.closeConnection();
        view.displayExitMessage();
        view.closeScanner();
    }
}
