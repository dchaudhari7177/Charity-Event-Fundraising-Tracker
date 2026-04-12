package dao;

import model.Donor;
import db.DBConnection;
import java.sql.*;
import java.util.ArrayList;

/**
 * Donor Data Access Object (DAO)
 * Handles all database operations for Donor entities
 * Uses JDBC with PreparedStatement to prevent SQL injection
 */
public class DonorDAO {

    /**
     * Add a new donor to the database
     * 
     * @param donor Donor object containing donor details
     * @return true if donor added successfully, false otherwise
     */
    public boolean addDonor(Donor donor) {
        String sql = "INSERT INTO donors (name, email, phone_number) VALUES (?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, donor.getDonorName());
            pstmt.setString(2, donor.getEmail());
            pstmt.setString(3, donor.getPhoneNumber());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("❌ Error adding donor: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get a donor by ID
     * 
     * @param donorId ID of the donor to retrieve
     * @return Donor object if found, null otherwise
     */
    public Donor getDonorById(int donorId) {
        String sql = "SELECT * FROM donors WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, donorId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Donor(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone_number")
                );
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving donor: " + e.getMessage());
        }
        
        return null;
    }

    /**
     * Get all donors from the database
     * 
     * @return ArrayList of all donors
     */
    public ArrayList<Donor> getAllDonors() {
        ArrayList<Donor> donors = new ArrayList<>();
        String sql = "SELECT * FROM donors";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Donor donor = new Donor(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone_number")
                );
                donors.add(donor);
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving donors: " + e.getMessage());
        }
        
        return donors;
    }

    /**
     * Get donor by name (useful for display)
     * 
     * @param donorName Name of the donor
     * @return Donor object if found, null otherwise
     */
    public Donor getDonorByName(String donorName) {
        String sql = "SELECT * FROM donors WHERE name = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, donorName);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Donor(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone_number")
                );
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving donor: " + e.getMessage());
        }
        
        return null;
    }

    /**
     * Delete a donor from the database
     * 
     * @param donorId ID of the donor to delete
     * @return true if deleted successfully, false otherwise
     */
    public boolean deleteDonor(int donorId) {
        String sql = "DELETE FROM donors WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, donorId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("❌ Error deleting donor: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get total count of donors in database
     * 
     * @return Number of donors
     */
    public int getDonorCount() {
        String sql = "SELECT COUNT(*) as count FROM donors";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt("count");
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error counting donors: " + e.getMessage());
        }
        
        return 0;
    }
}
