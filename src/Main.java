import controller.CharityTrackerController;

/**
 * Main: Application Entry Point (MVC Architecture)
 * 
 * MVC PATTERN: Entry Point Layer
 * 
 * RESPONSIBILITY: 
 * - Initialize the application
 * - Create and run the main controller
 * - Handle top-level error management
 * 
 * This refactored main class demonstrates:
 * - Clean separation of concerns (MVC)
 * - No business logic
 * - No view rendering (delegated to ConsoleView)
 * - No database access (delegated to DAO)
 * - Simple orchestration only
 */
public class Main {
    
    public static void main(String[] args) {
        try {
            // Create and run the main controller
            // Controller will orchestrate all MVC components
            CharityTrackerController controller = new CharityTrackerController();
            controller.run();
            
        } catch (Exception e) {
            System.err.println("✗ Application Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
