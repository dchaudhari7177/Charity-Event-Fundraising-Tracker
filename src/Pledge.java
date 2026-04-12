/**
 * Pledge class represents a donation/pledge from a donor to an event
 * Encapsulation: Private fields with public getters and setters
 */
public class Pledge {
    private String pledgeId;
    private String donorId;
    private String eventId;
    private double pledgeAmount;
    private String pledgeDate;

    // Constructor
    public Pledge(String pledgeId, String donorId, String eventId, double pledgeAmount, String pledgeDate) {
        this.pledgeId = pledgeId;
        this.donorId = donorId;
        this.eventId = eventId;
        this.pledgeAmount = pledgeAmount;
        this.pledgeDate = pledgeDate;
    }

    // Getters
    public String getPledgeId() {
        return pledgeId;
    }

    public String getDonorId() {
        return donorId;
    }

    public String getEventId() {
        return eventId;
    }

    public double getPledgeAmount() {
        return pledgeAmount;
    }

    public String getPledgeDate() {
        return pledgeDate;
    }

    // Setters
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

    // Method to display pledge details
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
                "pledgeId='" + pledgeId + '\'' +
                ", donorId='" + donorId + '\'' +
                ", eventId='" + eventId + '\'' +
                ", pledgeAmount=" + pledgeAmount +
                ", pledgeDate='" + pledgeDate + '\'' +
                '}';
    }
}
