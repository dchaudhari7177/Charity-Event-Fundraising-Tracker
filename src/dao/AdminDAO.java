package dao;

import model.Admin;
import db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Admin Data Access Object (DAO) - Handles admin database operations
 * Provides CRUD operations and authentication for admins
 */
public class AdminDAO {
    
    /**
     * Authenticate admin with username and password
     * 
     * @param username Admin username
     * @param password Admin password
     * @return Admin object if authentication successful, null otherwise
     */
    public Admin authenticateAdmin(String username, String password) {
        String query = "SELECT * FROM admins WHERE username = ? AND password = ? AND is_active = 1";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Admin admin = new Admin(
                    rs.getInt("admin_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("full_name"),
                    rs.getBoolean("is_active")
                );
                return admin;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error authenticating admin: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Get admin by ID
     */
    public Admin getAdminById(int adminId) {
        String query = "SELECT * FROM admins WHERE admin_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, adminId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Admin(
                    rs.getInt("admin_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("full_name"),
                    rs.getBoolean("is_active")
                );
            }
        } catch (SQLException e) {
            System.err.println("❌ Error retrieving admin: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Add new admin to database
     */
    public int addAdmin(Admin admin) {
        String query = "INSERT INTO admins (username, password, email, full_name, is_active) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, admin.getUsername());
            pstmt.setString(2, admin.getPassword());
            pstmt.setString(3, admin.getEmail());
            pstmt.setString(4, admin.getFullName());
            pstmt.setBoolean(5, admin.isActive());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    admin.setAdminId(generatedId);
                    System.out.println("✓ Admin added successfully with ID: " + generatedId);
                    return generatedId;
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error adding admin: " + e.getMessage());
        }
        
        return -1;
    }
    
    /**
     * Get all admins
     */
    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        String query = "SELECT * FROM admins";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Admin admin = new Admin(
                    rs.getInt("admin_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("full_name"),
                    rs.getBoolean("is_active")
                );
                admins.add(admin);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error retrieving all admins: " + e.getMessage());
        }
        
        return admins;
    }
    
    /**
     * Update admin information
     */
    public boolean updateAdmin(Admin admin) {
        String query = "UPDATE admins SET username = ?, password = ?, email = ?, full_name = ?, is_active = ? WHERE admin_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, admin.getUsername());
            pstmt.setString(2, admin.getPassword());
            pstmt.setString(3, admin.getEmail());
            pstmt.setString(4, admin.getFullName());
            pstmt.setBoolean(5, admin.isActive());
            pstmt.setInt(6, admin.getAdminId());
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✓ Admin updated successfully");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error updating admin: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Delete admin by ID
     */
    public boolean deleteAdmin(int adminId) {
        String query = "DELETE FROM admins WHERE admin_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, adminId);
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                System.out.println("✓ Admin deleted successfully");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error deleting admin: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Check if username already exists
     */
    public boolean usernameExists(String username) {
        String query = "SELECT COUNT(*) FROM admins WHERE username = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error checking username: " + e.getMessage());
        }
        
        return false;
    }
}
