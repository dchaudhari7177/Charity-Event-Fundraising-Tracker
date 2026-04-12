package dao;

import model.Pledge;
import model.Donor;
import db.DBConnection;
import java.sql.*;
import java.util.ArrayList;

/**
 * Pledge Data Access Object (DAO)
 * Handles all database operations for Pledge entities
 * Uses JDBC with PreparedStatement to prevent SQL injection
 */
public class PledgeDAO {

    /**
     * Add a new pledge (donation) to the database
     * 
     * @param pledge Pledge object containing pledge details
     * @return true if pledge added successfully, false otherwise
     */
    public boolean addPledge(Pledge pledge) {
        String sql = "INSERT INTO pledges (donor_id, event_id, amount, pledge_date) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, pledge.getDonorId());
            pstmt.setInt(2, pledge.getEventId());
            pstmt.setDouble(3, pledge.getPledgeAmount());
            pstmt.setString(4, pledge.getPledgeDate());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("❌ Error adding pledge: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get a pledge by ID
     * 
     * @param pledgeId ID of the pledge to retrieve
     * @return Pledge object if found, null otherwise
     */
    public Pledge getPledgeById(int pledgeId) {
        String sql = "SELECT * FROM pledges WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, pledgeId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Pledge(
                    rs.getInt("id"),
                    rs.getInt("donor_id"),
                    rs.getInt("event_id"),
                    rs.getDouble("amount"),
                    rs.getString("pledge_date")
                );
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving pledge: " + e.getMessage());
        }
        
        return null;
    }

    /**
     * Get all pledges for a specific event
     * 
     * @param eventId ID of the event
     * @return ArrayList of pledges for the event
     */
    public ArrayList<Pledge> getPledgesByEvent(int eventId) {
        ArrayList<Pledge> pledges = new ArrayList<>();
        String sql = "SELECT * FROM pledges WHERE event_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, eventId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Pledge pledge = new Pledge(
                    rs.getInt("id"),
                    rs.getInt("donor_id"),
                    rs.getInt("event_id"),
                    rs.getDouble("amount"),
                    rs.getString("pledge_date")
                );
                pledges.add(pledge);
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving pledges: " + e.getMessage());
        }
        
        return pledges;
    }

    /**
     * Get all pledges from a specific donor
     * 
     * @param donorId ID of the donor
     * @return ArrayList of pledges from the donor
     */
    public ArrayList<Pledge> getPledgesByDonor(int donorId) {
        ArrayList<Pledge> pledges = new ArrayList<>();
        String sql = "SELECT * FROM pledges WHERE donor_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, donorId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Pledge pledge = new Pledge(
                    rs.getInt("id"),
                    rs.getInt("donor_id"),
                    rs.getInt("event_id"),
                    rs.getDouble("amount"),
                    rs.getString("pledge_date")
                );
                pledges.add(pledge);
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving pledges: " + e.getMessage());
        }
        
        return pledges;
    }

    /**
     * Get all pledges from the database
     * 
     * @return ArrayList of all pledges
     */
    public ArrayList<Pledge> getAllPledges() {
        ArrayList<Pledge> pledges = new ArrayList<>();
        String sql = "SELECT * FROM pledges";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Pledge pledge = new Pledge(
                    rs.getInt("id"),
                    rs.getInt("donor_id"),
                    rs.getInt("event_id"),
                    rs.getDouble("amount"),
                    rs.getString("pledge_date")
                );
                pledges.add(pledge);
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving pledges: " + e.getMessage());
        }
        
        return pledges;
    }

    /**
     * Get total amount pledged to an event
     * 
     * @param eventId ID of the event
     * @return Total amount pledged
     */
    public double getTotalPledgedForEvent(int eventId) {
        String sql = "SELECT SUM(amount) as total FROM pledges WHERE event_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, eventId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                double total = rs.getDouble("total");
                return total >= 0 ? total : 0;
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error calculating total: " + e.getMessage());
        }
        
        return 0;
    }

    /**
     * Get total count of pledges in database
     * 
     * @return Number of pledges
     */
    public int getPledgeCount() {
        String sql = "SELECT COUNT(*) as count FROM pledges";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt("count");
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error counting pledges: " + e.getMessage());
        }
        
        return 0;
    }

    /**
     * Delete a pledge from the database
     * 
     * @param pledgeId ID of the pledge to delete
     * @return true if deleted successfully, false otherwise
     */
    public boolean deletePledge(int pledgeId) {
        String sql = "DELETE FROM pledges WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, pledgeId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("❌ Error deleting pledge: " + e.getMessage());
            return false;
        }
    }
}
