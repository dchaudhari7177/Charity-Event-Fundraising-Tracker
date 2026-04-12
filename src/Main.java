import java.util.Scanner;

/**
 * Main class - Entry point of the Charity Event Fundraising Tracker
 * Provides a menu-driven interface for user interaction
 */
public class Main {
    private static FundraisingSystem system;
    private static Scanner scanner;

    public static void main(String[] args) {
        system = new FundraisingSystem();
        scanner = new Scanner(System.in);

        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║   CHARITY EVENT FUNDRAISING TRACKER SYSTEM                ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");

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
                    viewEvents();
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
                    System.out.println("\nThank you for using Charity Event Fundraising Tracker!");
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice! Please enter a valid option.");
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

        System.out.print("Enter Target Amount (Rs.): ");
        double targetAmount = getDoubleInput();

        System.out.print("Enter Event Description: ");
        String description = scanner.nextLine();

        system.addEvent(eventName, targetAmount, description);
    }

    // Register a new donor
    private static void registerDonor() {
        System.out.println("\n--- REGISTER NEW DONOR ---");
        System.out.print("Enter Donor Name: ");
        scanner.nextLine(); // Consume newline
        String donorName = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Phone Number: ");
        String phoneNumber = scanner.nextLine();

        system.addDonor(donorName, email, phoneNumber);
    }

    // Make a donation
    private static void makeDonation() {
        System.out.println("\n--- MAKE DONATION ---");

        System.out.print("Enter Donor ID (e.g., DOR1): ");
        scanner.nextLine(); // Consume newline
        String donorId = scanner.nextLine();

        System.out.print("Enter Event ID (e.g., EVT1): ");
        String eventId = scanner.nextLine();

        System.out.print("Enter Donation Amount (Rs.): ");
        double amount = getDoubleInput();

        system.makePledge(donorId, eventId, amount);
    }

    // View all events
    private static void viewEvents() {
        if (system.getTotalEventsCount() == 0) {
            System.out.println("\nNo events created yet!");
        } else {
            system.displayAllEvents();
        }
    }

    // View donors for a specific event
    private static void viewDonorsForEvent() {
        if (system.getTotalEventsCount() == 0) {
            System.out.println("\nNo events available!");
            return;
        }

        System.out.println("\n--- VIEW DONORS FOR EVENT ---");
        System.out.print("Enter Event ID (e.g., EVT1): ");
        scanner.nextLine(); // Consume newline
        String eventId = scanner.nextLine();

        system.displayDonorsForEvent(eventId);
    }

    // View all donors
    private static void viewAllDonors() {
        if (system.getTotalDonorsCount() == 0) {
            System.out.println("\nNo donors registered yet!");
        } else {
            system.displayAllDonors();
        }
    }

    // Display statistics
    private static void displayStatistics() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         SYSTEM STATISTICS              ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ Total Events: " + String.format("%3d", system.getTotalEventsCount()) + "                      ║");
        System.out.println("║ Total Donors: " + String.format("%3d", system.getTotalDonorsCount()) + "                      ║");
        System.out.println("║ Total Pledges: " + String.format("%3d", system.getTotalPledgesCount()) + "                     ║");
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
            return value;
        } catch (Exception e) {
            scanner.nextLine(); // Clear the buffer
            return -1;
        }
    }
}
