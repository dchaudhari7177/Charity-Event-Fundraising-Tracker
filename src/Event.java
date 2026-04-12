/**
 * Event class represents a charity event
 * Encapsulation: Private fields with public getters and setters
 */
public class Event {
    private String eventId;
    private String eventName;
    private double targetAmount;
    private String description;
    private double collectedAmount;

    // Constructor
    public Event(String eventId, String eventName, double targetAmount, String description) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.targetAmount = targetAmount;
        this.description = description;
        this.collectedAmount = 0.0;
    }

    // Getters
    public String getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public String getDescription() {
        return description;
    }

    public double getCollectedAmount() {
        return collectedAmount;
    }

    // Setters
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setTargetAmount(double targetAmount) {
        if (targetAmount > 0) {
            this.targetAmount = targetAmount;
        } else {
            System.out.println("Target amount must be positive!");
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Method to add funds to the event
    public void addFunds(double amount) {
        if (amount > 0) {
            this.collectedAmount += amount;
        } else {
            System.out.println("Donation amount must be positive!");
        }
    }

    // Method to calculate progress percentage
    public double getProgressPercentage() {
        if (targetAmount == 0) {
            return 0;
        }
        return (collectedAmount / targetAmount) * 100;
    }

    // Method to display event details
    public void displayEventDetails() {
        System.out.println("\n--- Event Details ---");
        System.out.println("Event ID: " + eventId);
        System.out.println("Event Name: " + eventName);
        System.out.println("Description: " + description);
        System.out.println("Target Amount: Rs. " + targetAmount);
        System.out.println("Collected Amount: Rs. " + collectedAmount);
        System.out.printf("Progress: %.2f%%\n", getProgressPercentage());
        System.out.println("Remaining: Rs. " + (targetAmount - collectedAmount));
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId='" + eventId + '\'' +
                ", eventName='" + eventName + '\'' +
                ", targetAmount=" + targetAmount +
                ", collectedAmount=" + collectedAmount +
                ", progress=" + String.format("%.2f", getProgressPercentage()) + "%" +
                '}';
    }
}
