import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * FundraisingSystem - Manager class that handles all the business logic
 * This class manages Events, Donors, and Pledges
 * Encapsulation: Private ArrayList collections with controlled access methods
 */
public class FundraisingSystem {
    private ArrayList<Event> events;
    private ArrayList<Donor> donors;
    private ArrayList<Pledge> pledges;
    private int eventCounter;
    private int donorCounter;
    private int pledgeCounter;

    // Constructor
    public FundraisingSystem() {
        this.events = new ArrayList<>();
        this.donors = new ArrayList<>();
        this.pledges = new ArrayList<>();
        this.eventCounter = 1;
        this.donorCounter = 1;
        this.pledgeCounter = 1;
    }

    // ====== EVENT MANAGEMENT ======

    // Method to create and add a new event
    public boolean addEvent(String eventName, double targetAmount, String description) {
        if (eventName == null || eventName.isEmpty()) {
            System.out.println("Event name cannot be empty!");
            return false;
        }
        if (targetAmount <= 0) {
            System.out.println("Target amount must be positive!");
            return false;
        }
        String eventId = "EVT" + eventCounter++;
        Event event = new Event(eventId, eventName, targetAmount, description);
        events.add(event);
        System.out.println("Event '" + eventName + "' created successfully with ID: " + eventId);
        return true;
    }

    // Method to get an event by ID
    public Event getEventById(String eventId) {
        for (Event event : events) {
            if (event.getEventId().equals(eventId)) {
                return event;
            }
        }
        return null;
    }

    // Method to display all events
    public void displayAllEvents() {
        if (events.isEmpty()) {
            System.out.println("\nNo events found!");
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

    // ====== DONOR MANAGEMENT ======

    // Method to register a new donor
    public boolean addDonor(String donorName, String email, String phoneNumber) {
        if (donorName == null || donorName.isEmpty()) {
            System.out.println("Donor name cannot be empty!");
            return false;
        }
        String donorId = "DOR" + donorCounter++;
        Donor donor = new Donor(donorId, donorName, email, phoneNumber);
        donors.add(donor);
        System.out.println("Donor '" + donorName + "' registered successfully with ID: " + donorId);
        return true;
    }

    // Method to get a donor by ID
    public Donor getDonorById(String donorId) {
        for (Donor donor : donors) {
            if (donor.getDonorId().equals(donorId)) {
                return donor;
            }
        }
        return null;
    }

    // Method to display all donors
    public void displayAllDonors() {
        if (donors.isEmpty()) {
            System.out.println("\nNo donors found!");
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

    // ====== PLEDGE MANAGEMENT ======

    // Method to make a donation (create a pledge)
    public boolean makePledge(String donorId, String eventId, double amount) {
        // Validation
        if (amount <= 0) {
            System.out.println("Donation amount must be positive!");
            return false;
        }

        // Check if donor exists
        Donor donor = getDonorById(donorId);
        if (donor == null) {
            System.out.println("Donor not found!");
            return false;
        }

        // Check if event exists
        Event event = getEventById(eventId);
        if (event == null) {
            System.out.println("Event not found!");
            return false;
        }

        // Create pledge
        String pledgeId = "PLD" + pledgeCounter++;
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateStr = currentDate.format(formatter);

        Pledge pledge = new Pledge(pledgeId, donorId, eventId, amount, dateStr);
        pledges.add(pledge);

        // Add funds to event
        event.addFunds(amount);

        System.out.println("Donation of Rs. " + amount + " recorded successfully!");
        System.out.println("Pledge ID: " + pledgeId);
        return true;
    }

    // Method to get all pledges for a specific event
    public ArrayList<Pledge> getPledgesForEvent(String eventId) {
        ArrayList<Pledge> eventPledges = new ArrayList<>();
        for (Pledge pledge : pledges) {
            if (pledge.getEventId().equals(eventId)) {
                eventPledges.add(pledge);
            }
        }
        return eventPledges;
    }

    // Method to display donors for a specific event
    public void displayDonorsForEvent(String eventId) {
        Event event = getEventById(eventId);
        if (event == null) {
            System.out.println("Event not found!");
            return;
        }

        ArrayList<Pledge> eventPledges = getPledgesForEvent(eventId);
        if (eventPledges.isEmpty()) {
            System.out.println("\nNo donors for this event yet!");
            return;
        }

        System.out.println("\n========== DONORS FOR EVENT: " + event.getEventName() + " ==========");
        double totalAmount = 0;
        for (Pledge pledge : eventPledges) {
            Donor donor = getDonorById(pledge.getDonorId());
            if (donor != null) {
                System.out.println("\nDonor Name: " + donor.getDonorName());
                System.out.println("Email: " + donor.getEmail());
                System.out.println("Amount Donated: Rs. " + pledge.getPledgeAmount());
                System.out.println("Date: " + pledge.getPledgeDate());
                totalAmount += pledge.getPledgeAmount();
            }
        }
        System.out.println("\n--- Total Donors: " + eventPledges.size());
        System.out.println("Total Amount: Rs. " + totalAmount);
        System.out.println("======================================");
    }

    // Method to display all pledges
    public void displayAllPledges() {
        if (pledges.isEmpty()) {
            System.out.println("\nNo pledges found!");
            return;
        }
        System.out.println("\n========== ALL PLEDGES ==========");
        for (Pledge pledge : pledges) {
            System.out.println(pledge);
        }
        System.out.println("\n================================");
    }

    // Method to get total pledges count
    public int getTotalEventsCount() {
        return events.size();
    }

    public int getTotalDonorsCount() {
        return donors.size();
    }

    public int getTotalPledgesCount() {
        return pledges.size();
    }
}
