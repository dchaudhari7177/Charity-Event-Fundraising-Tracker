package dao;

import model.Event;
import db.DBConnection;
import java.sql.*;
import java.util.ArrayList;

/**
 * Event Data Access Object (DAO)
 * Handles all database operations for Event entities
 * Uses JDBC with PreparedStatement to prevent SQL injection
 */
public class EventDAO {

    /**
     * Add a new event to the database
     * 
     * @param event Event object containing event details
     * @return true if event added successfully, false otherwise
     */
    public boolean addEvent(Event event) {
        String sql = "INSERT INTO events (name, description, target_amount, collected_amount) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, event.getEventName());
            pstmt.setString(2, event.getDescription());
            pstmt.setDouble(3, event.getTargetAmount());
            pstmt.setDouble(4, event.getCollectedAmount());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("❌ Error adding event: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get an event by ID
     * 
     * @param eventId ID of the event to retrieve
     * @return Event object if found, null otherwise
     */
    public Event getEventById(int eventId) {
        String sql = "SELECT * FROM events WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, eventId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Event(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("target_amount"),
                    rs.getString("description"),
                    rs.getDouble("collected_amount")
                );
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving event: " + e.getMessage());
        }
        
        return null;
    }

    /**
     * Get all events from the database
     * 
     * @return ArrayList of all events
     */
    public ArrayList<Event> getAllEvents() {
        ArrayList<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Event event = new Event(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("target_amount"),
                    rs.getString("description"),
                    rs.getDouble("collected_amount")
                );
                events.add(event);
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving events: " + e.getMessage());
        }
        
        return events;
    }

    /**
     * Update the collected amount for an event
     * Used when a new donation is made
     * 
     * @param eventId ID of the event
     * @param amount Amount to add to collected_amount
     * @return true if update successful, false otherwise
     */
    public boolean updateCollectedAmount(int eventId, double amount) {
        // First get current amount
        Event event = getEventById(eventId);
        if (event == null) {
            System.out.println("❌ Event not found!");
            return false;
        }
        
        // Update with new total
        double newTotal = event.getCollectedAmount() + amount;
        String sql = "UPDATE events SET collected_amount = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, newTotal);
            pstmt.setInt(2, eventId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("❌ Error updating event amount: " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete an event from the database
     * 
     * @param eventId ID of the event to delete
     * @return true if deleted successfully, false otherwise
     */
    public boolean deleteEvent(int eventId) {
        String sql = "DELETE FROM events WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, eventId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("❌ Error deleting event: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get total count of events in database
     * 
     * @return Number of events
     */
    public int getEventCount() {
        String sql = "SELECT COUNT(*) as count FROM events";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt("count");
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error counting events: " + e.getMessage());
        }
        
        return 0;
    }
}
