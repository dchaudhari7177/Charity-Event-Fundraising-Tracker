package view;

import model.Event;
import model.Donor;
import model.Pledge;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * ConsoleView: View layer for console-based UI
 * 
 * MVC PATTERN: View Component
 * RESPONSIBILITY: Render output and capture user input
 * 
 * SOLID PRINCIPLE: Single Responsibility
 * - Only responsible for UI presentation
 * - Delegates business logic to Controller
 * - No direct database access
 * 
 * Benefits:
 * - Easy to change UI (could replace with GUI)
 * - Clear separation of concerns
 * - Testable UI components
 */
public class ConsoleView {
    private Scanner scanner;
    
    public ConsoleView() {
        this.scanner = new Scanner(System.in);
    }
    
    // ==================== MENU DISPLAYS ====================
    
    public void displayMainMenu() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║         MAIN MENU                              ║");
        System.out.println("╠════════════════════════════════════════════════╣");
        System.out.println("║ 1. Create New Event                            ║");
        System.out.println("║ 2. Register Donor                              ║");
        System.out.println("║ 3. Make Donation to Event                      ║");
        System.out.println("║ 4. View All Events                             ║");
        System.out.println("║ 5. View Donors of Specific Event               ║");
        System.out.println("║ 6. View All Donors                             ║");
        System.out.println("║ 7. View Statistics                             ║");
        System.out.println("║ 8. Exit                                        ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.print("Enter your choice: ");
    }
    
    // ==================== EVENT DISPLAYS ====================
    
    public void displayCreateEventForm() {
        System.out.println("\n┌─ CREATE NEW EVENT ────────────────────────────┐");
    }
    
    public String inputEventName() {
        System.out.print("│ Enter Event Name: ");
        return scanner.nextLine().trim();
    }
    
    public double inputTargetAmount() {
        System.out.print("│ Enter Target Amount (Rs.): ");
        return getDoubleInput();
    }
    
    public String inputEventDescription() {
        System.out.print("│ Enter Event Description: ");
        return scanner.nextLine().trim();
    }
    
    public void displayEventCreated(String eventName) {
        System.out.println("│ ✓ Event created successfully!");
        System.out.println("└───────────────────────────────────────────────┘");
    }
    
    public void displayAllEvents(ArrayList<Event> events) {
        if (events.isEmpty()) {
            System.out.println("\n✗ No events found");
            return;
        }
        
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║              ALL EVENTS                                 ║");
        System.out.println("╠════════════════════════════════════════════════════════╣");
        
        for (Event event : events) {
            displayEventDetails(event);
            System.out.println("├────────────────────────────────────────────────────────┤");
        }
        
        System.out.println("╚════════════════════════════════════════════════════════╝");
    }
    
    private void displayEventDetails(Event event) {
        String progressBar = generateProgressBar((int) event.getProgressPercentage());
        
        System.out.println("│ ID: " + event.getEventId());
        System.out.println("│ Name: " + event.getEventName());
        System.out.println("│ Target: ₹" + String.format("%.2f", event.getTargetAmount()));
        System.out.println("│ Collected: ₹" + String.format("%.2f", event.getCollectedAmount()));
        System.out.println("│ Progress: " + progressBar + " " + 
                          String.format("%.1f%%", event.getProgressPercentage()));
        System.out.println("│ Description: " + event.getDescription());
    }
    
    // ==================== DONOR DISPLAYS ====================
    
    public void displayRegisterDonorForm() {
        System.out.println("\n┌─ REGISTER DONOR ──────────────────────────────┐");
    }
    
    public String inputDonorName() {
        System.out.print("│ Enter Donor Name: ");
        return scanner.nextLine().trim();
    }
    
    public String inputDonorEmail() {
        System.out.print("│ Enter Email: ");
        return scanner.nextLine().trim();
    }
    
    public String inputDonorPhone() {
        System.out.print("│ Enter Phone Number: ");
        return scanner.nextLine().trim();
    }
    
    public void displayDonorRegistered(String donorName) {
        System.out.println("│ ✓ Donor registered successfully!");
        System.out.println("└───────────────────────────────────────────────┘");
    }
    
    public void displayAllDonors(ArrayList<Donor> donors) {
        if (donors.isEmpty()) {
            System.out.println("\n✗ No donors found");
            return;
        }
        
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║              ALL DONORS                                 ║");
        System.out.println("╠════════════════════════════════════════════════════════╣");
        
        for (Donor donor : donors) {
            System.out.println("│ ID: " + donor.getDonorId() + " | Name: " + donor.getDonorName());
            System.out.println("│ Email: " + (donor.getEmail() != null ? donor.getEmail() : "N/A"));
            System.out.println("│ Phone: " + (donor.getPhoneNumber() != null ? donor.getPhoneNumber() : "N/A"));
            System.out.println("├────────────────────────────────────────────────────────┤");
        }
        
        System.out.println("╚════════════════════════════════════════════════════════╝");
    }
    
    // ==================== DONATION DISPLAYS ====================
    
    public void displayMakeDonationForm() {
        System.out.println("\n┌─ MAKE DONATION ───────────────────────────────┐");
    }
    
    public int inputDonorId() {
        System.out.print("│ Enter Donor ID: ");
        return getIntInput();
    }
    
    public int inputEventId() {
        System.out.print("│ Enter Event ID: ");
        return getIntInput();
    }
    
    public double inputDonationAmount() {
        System.out.print("│ Enter Donation Amount (Rs.): ");
        return getDoubleInput();
    }
    
    public String inputDonationDate() {
        System.out.print("│ Enter Date (YYYY-MM-DD): ");
        return scanner.nextLine().trim();
    }
    
    public void displayDonationMade() {
        System.out.println("│ ✓ Donation recorded!");
        System.out.println("└───────────────────────────────────────────────┘");
    }
    
    public void displayDonorsForEvent(int eventId, ArrayList<Pledge> pledges) {
        if (pledges.isEmpty()) {
            System.out.println("\n✗ No donations found for this event");
            return;
        }
        
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║         DONORS FOR EVENT " + eventId + "                             ║");
        System.out.println("╠════════════════════════════════════════════════════════╣");
        
        for (Pledge pledge : pledges) {
            System.out.println("│ Donor ID: " + pledge.getDonorId());
            System.out.println("│ Amount: ₹" + String.format("%.2f", pledge.getPledgeAmount()));
            System.out.println("│ Date: " + pledge.getPledgeDate());
            System.out.println("├────────────────────────────────────────────────────────┤");
        }
        
        System.out.println("╚════════════════════════════════════════════════════════╝");
    }
    
    // ==================== STATISTICS DISPLAYS ====================
    
    public void displayStatistics(int totalEvents, int totalDonors, int totalPledges, 
                                  double totalFundsCollected) {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║              SYSTEM STATISTICS                          ║");
        System.out.println("╠════════════════════════════════════════════════════════╣");
        System.out.println("│ Total Events: " + totalEvents);
        System.out.println("│ Total Donors: " + totalDonors);
        System.out.println("│ Total Donations: " + totalPledges);
        System.out.println("│ Total Funds Collected: ₹" + String.format("%.2f", totalFundsCollected));
        System.out.println("╚════════════════════════════════════════════════════════╝");
    }
    
    // ==================== ERROR DISPLAYS ====================
    
    public void displayError(String message) {
        System.out.println("\n✗ Error: " + message);
    }
    
    public void displaySuccess(String message) {
        System.out.println("\n✓ " + message);
    }
    
    // ==================== INPUT HELPERS ====================
    
    public int getIntInput() {
        try {
            int input = Integer.parseInt(scanner.nextLine().trim());
            return input;
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid input. Please enter a number.");
            return -1;
        }
    }
    
    public double getDoubleInput() {
        try {
            double input = Double.parseDouble(scanner.nextLine().trim());
            return input;
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid input. Please enter a valid number.");
            return -1;
        }
    }
    
    public void displayExitMessage() {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║       Thank you for using the Charity Tracker!         ║");
        System.out.println("║                  Goodbye!                              ║");
        System.out.println("╚════════════════════════════════════════════════════════╝\n");
    }
    
    /**
     * Helper: Generate visual progress bar
     */
    private String generateProgressBar(int percentage) {
        int filled = percentage / 5;  // 20 chars = 100%
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < 20; i++) {
            bar.append(i < filled ? "█" : "░");
        }
        bar.append("]");
        return bar.toString();
    }
    
    public void closeScanner() {
        scanner.close();
    }
}
