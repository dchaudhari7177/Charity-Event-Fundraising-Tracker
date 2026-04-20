package model;

/**
 * Admin Model Class - POJO for Admin users
 * Represents an administrator with credentials
 */
public class Admin {
    private int adminId;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private boolean isActive;

    // Constructor with all fields
    public Admin(int adminId, String username, String password, String email, String fullName, boolean isActive) {
        this.adminId = adminId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.isActive = isActive;
    }

    // Constructor without ID (for new admins)
    public Admin(String username, String password, String email, String fullName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.isActive = true;
    }

    // Getters
    public int getAdminId() {
        return adminId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public boolean isActive() {
        return isActive;
    }

    // Setters
    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
