package service;

import model.Donor;
import dao.DonorDAO;
import java.util.ArrayList;

/**
 * DonorService: Service layer for Donor operations
 * 
 * DESIGN PATTERN: Facade (Structural)
 * SOLID PRINCIPLES: Single Responsibility, Open/Closed, Dependency Inversion
 * 
 * Handles business logic for donor management while delegating
 * database operations to DonorDAO.
 */
public class DonorService {
    private DonorDAO donorDAO;
    
    public DonorService() {
        this.donorDAO = new DonorDAO();
    }
    
    /**
     * Register a new donor
     * Business Logic: Validates inputs and email format
     */
    public boolean registerDonor(String name, String email, String phone) {
        // Business validation
        if (name == null || name.trim().isEmpty()) {
            System.out.println("✗ Donor name cannot be empty");
            return false;
        }
        
        if (email != null && !email.isEmpty() && !isValidEmail(email)) {
            System.out.println("✗ Email format invalid");
            return false;
        }
        
        // Create and save donor
        Donor donor = new Donor(name, email, phone);
        return donorDAO.addDonor(donor);
    }
    
    /**
     * Get donor by ID
     */
    public Donor getDonor(int donorId) {
        return donorDAO.getDonorById(donorId);
    }
    
    /**
     * Get all donors
     */
    public ArrayList<Donor> getAllDonors() {
        return donorDAO.getAllDonors();
    }
    
    /**
     * Search donor by name
     */
    public Donor findDonorByName(String name) {
        return donorDAO.getDonorByName(name);
    }
    
    /**
     * Delete donor
     */
    public boolean deleteDonor(int donorId) {
        return donorDAO.deleteDonor(donorId);
    }
    
    /**
     * Get total number of donors
     */
    public int getTotalDonors() {
        return donorDAO.getDonorCount();
    }
    
    /**
     * Validate email format
     * Business Logic: Simple email validation
     */
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}
