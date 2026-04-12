package model;

/**
 * Donor Model Class - POJO (Plain Old Java Object)
 * Represents a person who donates to charity events
 */
public class Donor {
    private int donorId;
    private String donorName;
    private String email;
    private String phoneNumber;

    // Constructor with all fields
    public Donor(int donorId, String donorName, String email, String phoneNumber) {
        this.donorId = donorId;
        this.donorName = donorName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Constructor without ID (for new donors)
    public Donor(String donorName, String email, String phoneNumber) {
        this.donorName = donorName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Getters
    public int getDonorId() {
        return donorId;
    }

    public String getDonorName() {
        return donorName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Setters
    public void setDonorId(int donorId) {
        this.donorId = donorId;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Display donor details
    public void displayDonorDetails() {
        System.out.println("\n--- Donor Details ---");
        System.out.println("Donor ID: " + donorId);
        System.out.println("Name: " + donorName);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phoneNumber);
    }

    @Override
    public String toString() {
        return "Donor{" +
                "donorId=" + donorId +
                ", donorName='" + donorName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
