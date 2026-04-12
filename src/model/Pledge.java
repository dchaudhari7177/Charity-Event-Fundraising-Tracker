package model;

/**
 * Pledge Model Class - POJO (Plain Old Java Object)
 * Represents a donation/pledge from a donor to an event
 */
public class Pledge {
    private int pledgeId;
    private int donorId;
    private int eventId;
    private double pledgeAmount;
    private String pledgeDate;

    // Constructor with all fields
    public Pledge(int pledgeId, int donorId, int eventId, double pledgeAmount, String pledgeDate) {
        this.pledgeId = pledgeId;
        this.donorId = donorId;
        this.eventId = eventId;
        this.pledgeAmount = pledgeAmount;
        this.pledgeDate = pledgeDate;
    }

    // Constructor without ID (for new pledges)
    public Pledge(int donorId, int eventId, double pledgeAmount, String pledgeDate) {
        this.donorId = donorId;
        this.eventId = eventId;
        this.pledgeAmount = pledgeAmount;
        this.pledgeDate = pledgeDate;
    }

    // Getters
    public int getPledgeId() {
        return pledgeId;
    }

    public int getDonorId() {
        return donorId;
    }

    public int getEventId() {
        return eventId;
    }

    public double getPledgeAmount() {
        return pledgeAmount;
    }

    public String getPledgeDate() {
        return pledgeDate;
    }

    // Setters
    public void setPledgeId(int pledgeId) {
        this.pledgeId = pledgeId;
    }

    public void setDonorId(int donorId) {
        this.donorId = donorId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void setPledgeAmount(double pledgeAmount) {
        if (pledgeAmount > 0) {
            this.pledgeAmount = pledgeAmount;
        } else {
            System.out.println("Pledge amount must be positive!");
        }
    }

    public void setPledgeDate(String pledgeDate) {
        this.pledgeDate = pledgeDate;
    }

    // Display pledge details
    public void displayPledgeDetails() {
        System.out.println("\nPledge ID: " + pledgeId);
        System.out.println("Donor ID: " + donorId);
        System.out.println("Event ID: " + eventId);
        System.out.println("Amount: Rs. " + pledgeAmount);
        System.out.println("Date: " + pledgeDate);
    }

    @Override
    public String toString() {
        return "Pledge{" +
                "pledgeId=" + pledgeId +
                ", donorId=" + donorId +
                ", eventId=" + eventId +
                ", pledgeAmount=" + pledgeAmount +
                ", pledgeDate='" + pledgeDate + '\'' +
                '}';
    }
}
