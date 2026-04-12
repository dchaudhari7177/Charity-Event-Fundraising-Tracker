/**
 * Donor class represents a person who donates to charity events
 * Encapsulation: Private fields with public getters and setters
 */
public class Donor {
    private String donorId;
    private String donorName;
    private String email;
    private String phoneNumber;

    // Constructor
    public Donor(String donorId, String donorName, String email, String phoneNumber) {
        this.donorId = donorId;
        this.donorName = donorName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Getters
    public String getDonorId() {
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
    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Method to display donor details
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
                "donorId='" + donorId + '\'' +
                ", donorName='" + donorName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
