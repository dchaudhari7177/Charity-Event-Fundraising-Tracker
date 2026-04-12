package model;

/**
 * Event Model Class - POJO (Plain Old Java Object)
 * Represents a charity event with data persistence in database
 */
public class Event {
    private int eventId;
    private String eventName;
    private double targetAmount;
    private String description;
    private double collectedAmount;

    // Constructor with all fields
    public Event(int eventId, String eventName, double targetAmount, String description, double collectedAmount) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.targetAmount = targetAmount;
        this.description = description;
        this.collectedAmount = collectedAmount;
    }

    // Constructor without ID (for new events)
    public Event(String eventName, double targetAmount, String description) {
        this.eventName = eventName;
        this.targetAmount = targetAmount;
        this.description = description;
        this.collectedAmount = 0.0;
    }

    // Getters
    public int getEventId() {
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
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setTargetAmount(double targetAmount) {
        if (targetAmount > 0) {
            this.targetAmount = targetAmount;
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCollectedAmount(double collectedAmount) {
        this.collectedAmount = collectedAmount;
    }

    // Calculate progress percentage
    public double getProgressPercentage() {
        if (targetAmount == 0) {
            return 0;
        }
        return (collectedAmount / targetAmount) * 100;
    }

    // Display event details
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
                "eventId=" + eventId +
                ", eventName='" + eventName + '\'' +
                ", targetAmount=" + targetAmount +
                ", collectedAmount=" + collectedAmount +
                ", progress=" + String.format("%.2f", getProgressPercentage()) + "%" +
                '}';
    }
}
