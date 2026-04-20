package service;

import model.Admin;
import dao.AdminDAO;
import java.util.List;

/**
 * Admin Service - Business logic for admin operations
 * 
 * DESIGN PATTERN: Facade & Service Layer (Structural & Architectural)
 * PURPOSE: Provide centralized business logic for admin operations
 * 
 * SOLID PRINCIPLES APPLIED:
 * 1. Single Responsibility: Only handles admin business logic
 * 2. Open/Closed: Easy to add new admin operations without modifying clients
 * 3. Dependency Inversion: Depends on AdminDAO abstraction
 */
public class AdminService {
    private AdminDAO adminDAO;
    
    public AdminService() {
        this.adminDAO = new AdminDAO();
    }
    
    /**
     * Authenticate admin with credentials
     * 
     * @param username Admin username
     * @param password Admin password
     * @return Admin object if authentication successful, null otherwise
     */
    public Admin login(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            System.out.println("❌ Username and password cannot be empty");
            return null;
        }
        
        Admin admin = adminDAO.authenticateAdmin(username, password);
        
        if (admin != null) {
            System.out.println("✓ Admin login successful: " + admin.getFullName());
        } else {
            System.out.println("❌ Invalid username or password");
        }
        
        return admin;
    }
    
    /**
     * Get admin by ID
     */
    public Admin getAdminById(int adminId) {
        return adminDAO.getAdminById(adminId);
    }
    
    /**
     * Register new admin (for admin registration)
     */
    public int registerAdmin(String username, String password, String email, String fullName) {
        if (username == null || username.isEmpty()) {
            System.out.println("❌ Username cannot be empty");
            return -1;
        }
        
        if (adminDAO.usernameExists(username)) {
            System.out.println("❌ Username already exists");
            return -1;
        }
        
        Admin admin = new Admin(username, password, email, fullName);
        return adminDAO.addAdmin(admin);
    }
    
    /**
     * Get all admins
     */
    public List<Admin> getAllAdmins() {
        return adminDAO.getAllAdmins();
    }
    
    /**
     * Update admin information
     */
    public boolean updateAdmin(Admin admin) {
        return adminDAO.updateAdmin(admin);
    }
    
    /**
     * Delete admin
     */
    public boolean deleteAdmin(int adminId) {
        return adminDAO.deleteAdmin(adminId);
    }
    
    /**
     * Validate admin credentials
     */
    public boolean isValidPassword(String password) {
        return password != null && password.length() >= 4;
    }
}
