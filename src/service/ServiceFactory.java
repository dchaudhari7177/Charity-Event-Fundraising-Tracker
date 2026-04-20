package service;

/**
 * ServiceFactory: Factory for creating Service instances
 * 
 * DESIGN PATTERN: Factory Method (Creational)
 * PURPOSE: Centralized creation of service objects
 * 
 * SOLID PRINCIPLES APPLIED:
 * 1. Single Responsibility: Only creates service instances
 * 2. Open/Closed: Easy to add new services without modifying existing code
 * 3. Dependency Inversion: Clients depend on factory abstraction
 * 
 * Benefits:
 * - Centralized object creation
 * - Easy to add caching/pooling
 * - Easy to add alternative implementations
 * - Simplifies dependency injection
 */
public class ServiceFactory {
    
    // Private constructor to prevent instantiation
    private ServiceFactory() {
    }
    
    // Service instances (could add caching if needed)
    private static EventService eventService;
    private static DonorService donorService;
    private static PledgeService pledgeService;
    private static AdminService adminService;
    
    /**
     * Factory Method: Get or create EventService
     * Implements Singleton pattern for services
     */
    public static EventService getEventService() {
        if (eventService == null) {
            eventService = new EventService();
        }
        return eventService;
    }
    
    /**
     * Factory Method: Get or create DonorService
     */
    public static DonorService getDonorService() {
        if (donorService == null) {
            donorService = new DonorService();
        }
        return donorService;
    }
    
    /**
     * Factory Method: Get or create PledgeService
     */
    public static PledgeService getPledgeService() {
        if (pledgeService == null) {
            pledgeService = new PledgeService();
        }
        return pledgeService;
    }
    
    /**
     * Factory Method: Get or create AdminService
     */
    public static AdminService getAdminService() {
        if (adminService == null) {
            adminService = new AdminService();
        }
        return adminService;
    }
    
    /**
     * Reset all services (useful for testing)
     */
    public static void reset() {
        eventService = null;
        donorService = null;
        pledgeService = null;
        adminService = null;
    }
}
