package observer;

import model.Event;
import model.Pledge;
import java.util.ArrayList;
import java.util.List;

/**
 * Observable: Manages donation observers and notifies them of events
 * 
 * DESIGN PATTERN: Observer (Behavioral) - Implementation
 * 
 * This class maintains a list of observers and notifies them when:
 * - A donation is received
 * - An event goal is reached
 * - Progress is updated
 * 
 * It implements the Singleton pattern to ensure only one instance manages notifications.
 */
public class DonationEventManager {
    private static DonationEventManager instance;
    private List<DonationObserver> observers;
    
    // Singleton Pattern
    private DonationEventManager() {
        observers = new ArrayList<>();
    }
    
    /**
     * DESIGN PATTERN: Singleton
     * Ensures only one instance of DonationEventManager exists
     * @return Singleton instance
     */
    public static synchronized DonationEventManager getInstance() {
        if (instance == null) {
            instance = new DonationEventManager();
        }
        return instance;
    }
    
    /**
     * Register an observer
     * SOLID PRINCIPLE: Open/Closed - Observable is open for extension (adding observers)
     */
    public void subscribe(DonationObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            System.out.println("✓ Observer registered: " + observer.getClass().getSimpleName());
        }
    }
    
    /**
     * Unregister an observer
     */
    public void unsubscribe(DonationObserver observer) {
        if (observers.remove(observer)) {
            System.out.println("✓ Observer unregistered: " + observer.getClass().getSimpleName());
        }
    }
    
    /**
     * Notify all observers about a new donation
     */
    public void notifyDonationReceived(Pledge pledge, Event event) {
        for (DonationObserver observer : observers) {
            observer.onDonationReceived(pledge, event);
        }
    }
    
    /**
     * Notify all observers when goal is reached
     */
    public void notifyGoalReached(Event event) {
        for (DonationObserver observer : observers) {
            observer.onGoalReached(event);
        }
    }
    
    /**
     * Notify all observers about progress update
     */
    public void notifyProgressUpdated(Event event, double percentageComplete) {
        for (DonationObserver observer : observers) {
            observer.onProgressUpdated(event, percentageComplete);
        }
    }
    
    /**
     * Get number of registered observers
     */
    public int getObserverCount() {
        return observers.size();
    }
}
