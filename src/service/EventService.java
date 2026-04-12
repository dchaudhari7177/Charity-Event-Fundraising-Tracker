package service;

import model.Event;
import dao.EventDAO;
import java.util.ArrayList;

/**
 * EventService: Service layer for Event operations
 * 
 * DESIGN PATTERN: Facade (Structural)
 * PURPOSE: Simplify complex DAO interactions
 * 
 * SOLID PRINCIPLES APPLIED:
 * 1. Single Responsibility Principle:
 *    - Only handles event business logic, not database details
 *    - Delegates database operations to EventDAO
 * 
 * 2. Open/Closed Principle:
 *    - Open for extension (can add new business logic)
 *    - Closed for modification (DAO implementation hidden)
 * 
 * 3. Dependency Inversion Principle:
 *    - Depends on abstractions (could use EventDAO interface)
 *    - High-level modules don't depend on low-level details
 */
public class EventService {
    private EventDAO eventDAO;
    
    public EventService() {
        this.eventDAO = new EventDAO();
    }
    
    /**
     * Create a new event
     * Business Logic: Validates inputs before saving
     */
    public boolean createEvent(String name, double targetAmount, String description) {
        // Business validation
        if (name == null || name.trim().isEmpty()) {
            System.out.println("✗ Event name cannot be empty");
            return false;
        }
        
        if (targetAmount <= 0) {
            System.out.println("✗ Target amount must be positive");
            return false;
        }
        
        // Create and save event
        Event event = new Event(name, targetAmount, description);
        return eventDAO.addEvent(event);
    }
    
    /**
     * Get event by ID
     */
    public Event getEvent(int eventId) {
        return eventDAO.getEventById(eventId);
    }
    
    /**
     * Get all events
     */
    public ArrayList<Event> getAllEvents() {
        return eventDAO.getAllEvents();
    }
    
    /**
     * Update collected amount for an event
     * Business Logic: Ensures non-negative amounts
     */
    public boolean updateCollectedAmount(int eventId, double amount) {
        if (amount < 0) {
            System.out.println("✗ Donation amount cannot be negative");
            return false;
        }
        
        Event event = eventDAO.getEventById(eventId);
        if (event == null) {
            System.out.println("✗ Event not found");
            return false;
        }
        
        return eventDAO.updateCollectedAmount(eventId, amount);
    }
    
    /**
     * Delete event
     */
    public boolean deleteEvent(int eventId) {
        return eventDAO.deleteEvent(eventId);
    }
    
    /**
     * Get total number of events
     */
    public int getTotalEvents() {
        return eventDAO.getEventCount();
    }
    
    /**
     * Get event progress as percentage
     */
    public double getEventProgress(int eventId) {
        Event event = eventDAO.getEventById(eventId);
        if (event == null) return 0.0;
        return event.getProgressPercentage();
    }
}
