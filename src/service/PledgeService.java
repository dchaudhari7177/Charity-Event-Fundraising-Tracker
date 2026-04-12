package service;

import model.Pledge;
import model.Event;
import dao.PledgeDAO;
import dao.EventDAO;
import observer.DonationEventManager;
import java.util.ArrayList;

/**
 * PledgeService: Service layer for Pledge (Donation) operations
 * 
 * DESIGN PATTERN: Facade (Structural)
 * SOLID PRINCIPLES: All SOLID principles applied
 * 
 * Handles donation business logic:
 * - Validates donations
 * - Updates event collected amount
 * - Notifies observers of donation events
 */
public class PledgeService {
    private PledgeDAO pledgeDAO;
    private EventDAO eventDAO;
    private DonationEventManager eventManager;
    
    public PledgeService() {
        this.pledgeDAO = new PledgeDAO();
        this.eventDAO = new EventDAO();
        this.eventManager = DonationEventManager.getInstance();
    }
    
    /**
     * Make a donation to an event
     * Business Logic: Validates, saves, and notifies observers
     */
    public boolean makeDonation(int donorId, int eventId, double amount, String date) {
        // Business validation
        if (amount <= 0) {
            System.out.println("✗ Donation amount must be positive");
            return false;
        }
        
        // Check if event exists
        Event event = eventDAO.getEventById(eventId);
        if (event == null) {
            System.out.println("✗ Event not found");
            return false;
        }
        
        // Create pledge
        Pledge pledge = new Pledge(donorId, eventId, amount, date);
        
        // Save pledge
        if (!pledgeDAO.addPledge(pledge)) {
            System.out.println("✗ Failed to save pledge");
            return false;
        }
        
        // Update event collected amount
        eventDAO.updateCollectedAmount(eventId, amount);
        
        // Refresh event data
        event = eventDAO.getEventById(eventId);
        
        // Notify observers - OBSERVER PATTERN in action
        eventManager.notifyDonationReceived(pledge, event);
        
        // Check if goal reached
        if (event.getProgressPercentage() >= 100) {
            eventManager.notifyGoalReached(event);
        } else {
            eventManager.notifyProgressUpdated(event, event.getProgressPercentage());
        }
        
        return true;
    }
    
    /**
     * Get pledge by ID
     */
    public Pledge getPledge(int pledgeId) {
        return pledgeDAO.getPledgeById(pledgeId);
    }
    
    /**
     * Get all pledges for an event
     */
    public ArrayList<Pledge> getPledgesForEvent(int eventId) {
        return pledgeDAO.getPledgesByEvent(eventId);
    }
    
    /**
     * Get all pledges by a donor
     */
    public ArrayList<Pledge> getPledgesByDonor(int donorId) {
        return pledgeDAO.getPledgesByDonor(donorId);
    }
    
    /**
     * Get total amount pledged for an event
     */
    public double getTotalPledgedForEvent(int eventId) {
        return pledgeDAO.getTotalPledgedForEvent(eventId);
    }
    
    /**
     * Get all pledges
     */
    public ArrayList<Pledge> getAllPledges() {
        return pledgeDAO.getAllPledges();
    }
    
    /**
     * Get total number of pledges
     */
    public int getTotalPledges() {
        return pledgeDAO.getPledgeCount();
    }
    
    /**
     * Delete a pledge
     */
    public boolean deletePledge(int pledgeId) {
        return pledgeDAO.deletePledge(pledgeId);
    }
}
