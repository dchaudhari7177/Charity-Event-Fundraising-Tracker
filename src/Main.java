import dao.EventDAO;
import dao.DonorDAO;
import dao.PledgeDAO;
import model.Event;
import model.Donor;
import model.Pledge;
import db.DBConnection;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Main class - Entry point of the Charity Event Fundraising Tracker (Database Version)
 * Uses DAO pattern with MySQL database persistence
 * Provides menu-driven interface for user interaction
 */
public class Main {
    private static EventDAO eventDAO;
    private static DonorDAO donorDAO;
    private static PledgeDAO pledgeDAO;
    private static Scanner scanner;

    public static void main(String[] args) {
        // Initialize DAOs
        eventDAO = new EventDAO();
        donorDAO = new DonorDAO();
        pledgeDAO = new PledgeDAO();
        scanner = new Scanner(System.in);

        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║   CHARITY EVENT FUNDRAISING TRACKER - DATABASE VERSION     ║");
        System.out.println("║          (Using MySQL with JDBC and DAO Pattern)          ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");

        // Initialize database and create tables if needed
        System.out.println("\n→ Initializing database...");
        DBConnection.initializeDatabase();

        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput();

            switch (choice) {
                case 1:
                    createEvent();
                    break;
                case 2:
                    registerDonor();
                    break;
                case 3:
                    makeDonation();
                    break;
                case 4:
                    viewAllEvents();
                    break;
                case 5:
                    viewDonorsForEvent();
                    break;
                case 6:
                    viewAllDonors();
                    break;
                case 7:
                    displayStatistics();
                    break;
                case 8:
                    System.out.println("\n👋 Closing database connection...");
                    DBConnection.closeConnection();
                    System.out.println("Thank you for using Charity Event Fundraising Tracker!");
                    System.out.println("Goodbye!\n");
                    running = false;
                    break;
                default:
                    System.out.println("❌ Invalid choice! Please enter a valid option (1-8).");
            }
        }
        scanner.close();
    }

    // Display main menu
    private static void displayMainMenu() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║         MAIN MENU                          ║");
        System.out.println("╠════════════════════════════════════════════╣");
        System.out.println("║ 1. Create New Event                        ║");
        System.out.println("║ 2. Register Donor                          ║");
        System.out.println("║ 3. Make Donation to Event                  ║");
        System.out.println("║ 4. View All Events                         ║");
        System.out.println("║ 5. View Donors of Specific Event           ║");
        System.out.println("║ 6. View All Donors                         ║");
        System.out.println("║ 7. View Statistics                         ║");
        System.out.println("║ 8. Exit                                    ║");
        System.out.println("╚════════════════════════════════════════════╝");
        System.out.print("Enter your choice: ");
    }

    // Create a new event
    private static void createEvent() {
        System.out.println("\n--- CREATE NEW EVENT ---");
        System.out.print("Enter Event Name: ");
        scanner.nextLine(); // Consume newline
        String eventName = scanner.nextLine();

        if (eventName.isEmpty()) {
            System.out.println("❌ Event name cannot be empty!");
            return;
        }

        System.out.print("Enter Target Amount (Rs.): ");
        double targetAmount = getDoubleInput();

        if (targetAmount <= 0) {
            System.out.println("❌ Target amount must be positive!");
            return;
        }

        System.out.print("Enter Event Description: ");
        String description = scanner.nextLine();

        // Create event object and save to database
        Event event = new Event(eventName, targetAmount, description);
        if (eventDAO.addEvent(event)) {
            System.out.println("✓ Event '" + eventName + "' created successfully!");
        } else {
            System.out.println("❌ Failed to create event!");
        }
    }

    // Register a new donor
    private static void registerDonor() {
        System.out.println("\n--- REGISTER NEW DONOR ---");
        System.out.print("Enter Donor Name: ");
        scanner.nextLine(); // Consume newline
        String donorName = scanner.nextLine();

        if (donorName.isEmpty()) {
            System.out.println("❌ Donor name cannot be empty!");
            return;
        }

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Phone Number: ");
        String phoneNumber = scanner.nextLine();

        // Create donor object and save to database
        Donor donor = new Donor(donorName, email, phoneNumber);
        if (donorDAO.addDonor(donor)) {
            System.out.println("✓ Donor '" + donorName + "' registered successfully!");
        } else {
            System.out.println("❌ Failed to register donor!");
        }
    }

    // Make a donation
    private static void makeDonation() {
        System.out.println("\n--- MAKE DONATION ---");

        System.out.print("Enter Donor ID: ");
        scanner.nextLine(); // Consume newline
        int donorId = getIntInput();

        // Check if donor exists
        Donor donor = donorDAO.getDonorById(donorId);
        if (donor == null) {
            System.out.println("❌ Donor not found!");
            return;
        }
        System.out.println("✓ Donor found: " + donor.getDonorName());

        System.out.print("Enter Event ID: ");
        int eventId = getIntInput();

        // Check if event exists
        Event event = eventDAO.getEventById(eventId);
        if (event == null) {
            System.out.println("❌ Event not found!");
            return;
        }
        System.out.println("✓ Event found: " + event.getEventName());

        System.out.print("Enter Donation Amount (Rs.): ");
        double amount = getDoubleInput();

        if (amount <= 0) {
            System.out.println("❌ Donation amount must be positive!");
            return;
        }

        // Create pledge
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateStr = currentDate.format(formatter);

        Pledge pledge = new Pledge(donorId, eventId, amount, dateStr);

        // Add pledge and update event collected amount
        if (pledgeDAO.addPledge(pledge)) {
            if (eventDAO.updateCollectedAmount(eventId, amount)) {
                System.out.println("✓ Donation of Rs. " + amount + " recorded successfully!");
                System.out.println("  Pledge recorded for " + donor.getDonorName() + " to " + event.getEventName());
            } else {
                System.out.println("❌ Failed to update event amount!");
            }
        } else {
            System.out.println("❌ Failed to record donation!");
        }
    }

    // View all events
    private static void viewAllEvents() {
        ArrayList<Event> events = eventDAO.getAllEvents();

        if (events.isEmpty()) {
            System.out.println("\n❌ No events found!");
            return;
        }

        System.out.println("\n========== ALL EVENTS ==========");
        for (Event event : events) {
            System.out.println("\nID: " + event.getEventId());
            System.out.println("Name: " + event.getEventName());
            System.out.println("Description: " + event.getDescription());
            System.out.println("Target: Rs. " + event.getTargetAmount());
            System.out.println("Collected: Rs. " + event.getCollectedAmount());
            System.out.printf("Progress: %.2f%%\n", event.getProgressPercentage());
            System.out.println("Remaining: Rs. " + (event.getTargetAmount() - event.getCollectedAmount()));
        }
        System.out.println("\n================================");
    }

    // View donors for a specific event
    private static void viewDonorsForEvent() {
        ArrayList<Event> events = eventDAO.getAllEvents();

        if (events.isEmpty()) {
            System.out.println("\n❌ No events available!");
            return;
        }

        System.out.println("\n--- VIEW DONORS FOR EVENT ---");
        System.out.print("Enter Event ID: ");
        scanner.nextLine(); // Consume newline
        int eventId = getIntInput();

        Event event = eventDAO.getEventById(eventId);
        if (event == null) {
            System.out.println("❌ Event not found!");
            return;
        }

        ArrayList<Pledge> pledges = pledgeDAO.getPledgesByEvent(eventId);

        if (pledges.isEmpty()) {
            System.out.println("\n❌ No donors for this event yet!");
            return;
        }

        System.out.println("\n========== DONORS FOR EVENT: " + event.getEventName() + " ==========");
        double totalAmount = 0;
        for (Pledge pledge : pledges) {
            Donor donor = donorDAO.getDonorById(pledge.getDonorId());
            if (donor != null) {
                System.out.println("\nDonor Name: " + donor.getDonorName());
                System.out.println("Email: " + donor.getEmail());
                System.out.println("Amount Donated: Rs. " + pledge.getPledgeAmount());
                System.out.println("Date: " + pledge.getPledgeDate());
                totalAmount += pledge.getPledgeAmount();
            }
        }
        System.out.println("\n--- Total Donors: " + pledges.size());
        System.out.println("Total Amount: Rs. " + totalAmount);
        System.out.println("======================================");
    }

    // View all donors
    private static void viewAllDonors() {
        ArrayList<Donor> donors = donorDAO.getAllDonors();

        if (donors.isEmpty()) {
            System.out.println("\n❌ No donors found!");
            return;
        }

        System.out.println("\n========== ALL DONORS ==========");
        for (Donor donor : donors) {
            System.out.println("\nID: " + donor.getDonorId());
            System.out.println("Name: " + donor.getDonorName());
            System.out.println("Email: " + donor.getEmail());
            System.out.println("Phone: " + donor.getPhoneNumber());
        }
        System.out.println("\n================================");
    }

    // Display statistics
    private static void displayStatistics() {
        int eventCount = eventDAO.getEventCount();
        int donorCount = donorDAO.getDonorCount();
        int pledgeCount = pledgeDAO.getPledgeCount();

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         SYSTEM STATISTICS              ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ Total Events: " + String.format("%3d", eventCount) + "                      ║");
        System.out.println("║ Total Donors: " + String.format("%3d", donorCount) + "                      ║");
        System.out.println("║ Total Pledges: " + String.format("%3d", pledgeCount) + "                     ║");

        // Calculate total amount pledged
        ArrayList<Pledge> allPledges = pledgeDAO.getAllPledges();
        double totalPledged = 0;
        for (Pledge pledge : allPledges) {
            totalPledged += pledge.getPledgeAmount();
        }
        System.out.println("║ Total Amount Pledged: Rs. " + String.format("%.2f", totalPledged) + "    ║");
        System.out.println("╚════════════════════════════════════════╝");
    }

    // Utility method to get integer input
    private static int getIntInput() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine(); // Clear the buffer
            return -1;
        }
    }

    // Utility method to get double input
    private static double getDoubleInput() {
        try {
            double value = scanner.nextDouble();
            scanner.nextLine(); // Clear the buffer
            return value;
        } catch (Exception e) {
            scanner.nextLine(); // Clear the buffer
            return -1;
        }
    }
}
