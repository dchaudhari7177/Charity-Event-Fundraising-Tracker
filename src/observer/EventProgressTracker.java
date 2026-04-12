package observer;

import model.Event;
import model.Pledge;

/**
 * Concrete Observer: EventProgressTracker
 * 
 * DESIGN PATTERN: Observer (Behavioral) - Concrete Implementation
 * SOLID PRINCIPLE: Single Responsibility - Only responsible for tracking and displaying progress
 * 
 * Tracks event progress and notifies when milestones are reached
 */
public class EventProgressTracker implements DonationObserver {
    
    private String name;
    
    public EventProgressTracker(String name) {
        this.name = name;
    }
    
    @Override
    public void onDonationReceived(Pledge pledge, Event event) {
        System.out.println("\n┌─ DONATION UPDATE ─────────────────────────────┐");
        System.out.println("│ Tracker: " + name);
        System.out.println("│ Event: " + event.getEventName());
        System.out.println("│ New Donation: ₹" + String.format("%.2f", pledge.getPledgeAmount()));
        System.out.println("│ Total Collected: ₹" + String.format("%.2f", event.getCollectedAmount()));
        System.out.println("└───────────────────────────────────────────────┘");
    }
    
    @Override
    public void onGoalReached(Event event) {
        System.out.println("\n╔═════════════════════════════════════════════════╗");
        System.out.println("║         🎉 GOAL REACHED! 🎉                     ║");
        System.out.println("║ Event: " + event.getEventName());
        System.out.println("║ Target: ₹" + String.format("%.2f", event.getTargetAmount()) + " ACHIEVED");
        System.out.println("╚═════════════════════════════════════════════════╝");
    }
    
    @Override
    public void onProgressUpdated(Event event, double percentageComplete) {
        String progressBar = generateProgressBar((int) percentageComplete);
        System.out.println("\n→ [" + event.getEventName() + "] Progress: " + progressBar + " " + 
                          String.format("%.1f%%", percentageComplete));
    }
    
    /**
     * Helper: Generate visual progress bar
     */
    private String generateProgressBar(int percentage) {
        int filled = percentage / 5;  // 20 chars = 100%
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < 20; i++) {
            bar.append(i < filled ? "█" : "░");
        }
        bar.append("]");
        return bar.toString();
    }
    
    public String getName() {
        return name;
    }
}
