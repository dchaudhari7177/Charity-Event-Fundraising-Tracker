package observer;

import model.Event;
import model.Pledge;

/**
 * Observer Pattern: DonationObserver Interface
 * 
 * DESIGN PATTERN: Observer (Behavioral)
 * PURPOSE: Implement event-driven updates
 * 
 * Allows observers to subscribe to donation events and get notified
 * when donations are made, enabling loose coupling between components.
 * 
 * This satisfies:
 * - Open/Closed Principle: New observers can be added without modifying existing code
 * - Dependency Inversion Principle: High-level modules depend on abstractions, not concretions
 */
public interface DonationObserver {
    
    /**
     * Called when a new donation is made
     * @param pledge The donation that was made
     * @param event The event receiving the donation
     */
    void onDonationReceived(Pledge pledge, Event event);
    
    /**
     * Called when an event reaches its target goal
     * @param event The event that reached its goal
     */
    void onGoalReached(Event event);
    
    /**
     * Called when event progress is updated
     * @param event The event with updated progress
     * @param percentageComplete Current fundraising percentage
     */
    void onProgressUpdated(Event event, double percentageComplete);
}
