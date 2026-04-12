# 🏗️ SOLID Principles & Design Patterns - Detailed Explanation

## TABLE OF CONTENTS
1. SOLID Principles (5 Principles Explained)
2. Design Patterns (4 Patterns Implemented)
3. Code Examples
4. Benefits Analysis

---

## PART 1: SOLID PRINCIPLES

### ✅ 1. Single Responsibility Principle (SRP)

**Definition:** A class should have one, and only one, reason to change.

**In Our Project:**

#### ❌ BEFORE (Bad):
```java
// Main.java was doing EVERYTHING!
public class Main {
    public static void main(String[] args) {
        // UI Display code
        System.out.println("Menu...");
        
        // User Input capture
        int choice = scanner.nextInt();
        
        // Business Logic
        if (choice == 1) {
            Event event = new Event(name, amount, desc);
            // ... lots of validation
        }
        
        // Database operations
        eventDAO.addEvent(event);
        
        // Output display
        System.out.println("Success!");
    }
}
```

**Problems:**
- 1000+ lines in single class
- Multiple reasons to change (UI, business logic, database)
- Hard to test
- Hard to maintain

#### ✅ AFTER (Good):
```java
// ConsoleView.java - ONLY handles UI
public class ConsoleView {
    public void displayMainMenu() { /* ... */ }
    public void displaySuccess(String msg) { /* ... */ }
    public String inputEventName() { /* ... */ }
}

// EventService.java - ONLY handles business logic
public class EventService {
    public boolean createEvent(String name, double target, String desc) {
        // Validation
        // Business logic
        // Delegates to DAO
    }
}

// EventDAO.java - ONLY handles database
public class EventDAO {
    public boolean addEvent(Event event) {
        // SQL operations
    }
}

// CharityTrackerController.java - ONLY orchestrates
public class CharityTrackerController {
    public void run() {
        view.displayMainMenu();
        int choice = view.getIntInput();
        eventService.createEvent(...);
    }
}
```

**Benefits:**
- ✓ Each class has single responsibility
- ✓ Easy to test each component
- ✓ Changes in UI don't affect database
- ✓ 400 lines split into multiple focused classes

---

### ✅ 2. Open/Closed Principle (OCP)

**Definition:** Software entities should be open for extension but closed for modification.

**In Our Project:**

#### ❌ BEFORE (Bad):
```java
// EventService.java - Hard-coded to event handling
public class EventService {
    // If we want to add Donor notifications, we modify this class
    public boolean createEvent(...) {
        // ... code
        // Add donor feature requires modifying this class
        notifyDonorAboutEvent();  // NEW - requires change
    }
    
    // Adding new business rule requires modifying existing methods
}
```

**Problem:** Every new feature requires modifying existing code → higher risk

#### ✅ AFTER (Good):
```java
// Observer Pattern - OPEN for extension without modification
public interface DonationObserver {
    void onDonationReceived(Pledge pledge, Event event);
    void onGoalReached(Event event);
}

// NEW observer - NO modification to existing code
public class EmailNotifier implements DonationObserver {
    public void onDonationReceived(Pledge pledge, Event event) {
        // Send email
    }
}

// EventProgressTracker - another observer
public class EventProgressTracker implements DonationObserver {
    public void onDonationReceived(Pledge pledge, Event event) {
        // Update progress display
    }
}

// Observable - supports both without change
public class DonationEventManager {
    public void subscribe(DonationObserver observer) {
        observers.add(observer);
    }
}

// NEW FEATURE: Add SMS notifier - no modification!
public class SMSNotifier implements DonationObserver {
    // Implements methods
}

// Just subscribe:
eventManager.subscribe(new SMSNotifier());
```

**Benefits:**
- ✓ OPEN for new observers
- ✓ CLOSED - no modification to existing code
- ✓ Reduced risk of breaking features
- ✓ Easy to test new observers in isolation

**Code Location:** `src/observer/`

---

### ✅ 3. Liskov Substitution Principle (LSP)

**Definition:** Subtypes must be substitutable for their base types.

**In Our Project:**

#### ❌ BEFORE (Bad):
```java
// Different observers with different interfaces
public interface Observer1 {
    void notify();
}

public interface Observer2 {
    void update(Event e);
}

// Can't substitute one for other!
Observer1 o1 = new SomeObserver();
Observer2 o2 = o1;  // COMPILE ERROR!
```

#### ✅ AFTER (Good):
```java
// All observers have same interface
public interface DonationObserver {
    void onDonationReceived(Pledge pledge, Event event);
    void onGoalReached(Event event);
    void onProgressUpdated(Event event, double percentage);
}

// Different implementations - ALL substitutable
public class EventProgressTracker implements DonationObserver {
    public void onDonationReceived(Pledge pledge, Event event) { /* ... */ }
    public void onGoalReached(Event event) { /* ... */ }
    public void onProgressUpdated(Event event, double percentage) { /* ... */ }
}

public class EmailNotifier implements DonationObserver {
    public void onDonationReceived(Pledge pledge, Event event) { /* ... */ }
    public void onGoalReached(Event event) { /* ... */ }
    public void onProgressUpdated(Event event, double percentage) { /* ... */ }
}

// Manager works with ANY observer
public class DonationEventManager {
    List<DonationObserver> observers; // ANY implementation works!
    
    public void subscribe(DonationObserver observer) {
        observers.add(observer);
    }
}

// Usage - observers are INTERCHANGEABLE
DonationObserver o1 = new EventProgressTracker("System");
DonationObserver o2 = new EmailNotifier("admin@example.com");
eventManager.subscribe(o1);
eventManager.subscribe(o2);
// BOTH work identically!
```

**Benefits:**
- ✓ Any observer can replace another
- ✓ Manager doesn't need to know implementation details
- ✓ Extensible design
- ✓ Contract-based programming

**Code Location:** `src/observer/`

---

### ✅ 4. Interface Segregation Principle (ISP)

**Definition:** Clients should not be forced to depend on interfaces they do not use.

**In Our Project:**

#### ❌ BEFORE (Bad):
```java
// ONE INTERFACE with 20+ methods
public interface Repository {
    // Event methods
    Event getEvent(int id);
    void saveEvent(Event e);
    void deleteEvent(int id);
    List<Event> getAllEvents();
    
    // Donor methods
    Donor getDonor(int id);
    void saveDonor(Donor d);
    void deleteDonor(int id);
    List<Donor> getAllDonors();
    
    // Pledge methods
    Pledge getPledge(int id);
    void savePledge(Pledge p);
    void deletePledge(int id);
    List<Pledge> getAllPledges();
    
    // Many more...
}

// Client needing only events is forced to implement all 20+ methods!
public class EventManager implements Repository {
    public Event getEvent(int id) { /* impl */ }
    public void saveEvent(Event e) { /* impl */ }
    // ... must implement Donor methods even if not used
    public Donor getDonor(int id) { throw new UnsupportedOperationException(); }
    public void saveDonor(Donor d) { throw new UnsupportedOperationException(); }
    // ... and 15 more unused methods!
}
```

**Problem:** Classes forced to implement methods they don't use

#### ✅ AFTER (Good):
```java
// SEGREGATED interfaces - only what's needed
public interface EventRepository {
    Event getEvent(int id);
    void saveEvent(Event e);
    void deleteEvent(int id);
    List<Event> getAllEvents();
}

public interface DonorRepository {
    Donor getDonor(int id);
    void saveDonor(Donor d);
    void deleteDonor(int id);
    List<Donor> getAllDonors();
}

public interface PledgeRepository {
    Pledge getPledge(int id);
    void savePledge(Pledge p);
    void deletePledge(int id);
    List<Pledge> getAllPledges();
}

// Focused observer interface
public interface DonationObserver {
    void onDonationReceived(Pledge pledge, Event event);
    void onGoalReached(Event event);
    void onProgressUpdated(Event event, double percentage);
}

// Client only implements what it needs
public class EventProgressTracker implements DonationObserver {
    // Only 3 methods needed - implements exactly 3!
    public void onDonationReceived(Pledge pledge, Event event) { /* */ }
    public void onGoalReached(Event event) { /* */ }
    public void onProgressUpdated(Event event, double percentage) { /* */ }
}

// Another observer - also implements just what it needs
public class EmailNotifier implements DonationObserver {
    // Same 3 methods
    public void onDonationReceived(Pledge pledge, Event event) { /* */ }
    public void onGoalReached(Event event) { /* */ }
    public void onProgressUpdated(Event event, double percentage) { /* */ }
}
```

**Benefits:**
- ✓ Cleaner interfaces
- ✓ Clients not forced to implement unnecessary methods
- ✓ Reduced coupling
- ✓ Easier to test

**Code Location:** `src/observer/DonationObserver.java`

---

### ✅ 5. Dependency Inversion Principle (DIP)

**Definition:** High-level modules should not depend on low-level modules. Both should depend on abstractions.

**In Our Project:**

#### ❌ BEFORE (Bad):
```java
// HIGH-LEVEL module (Controller) depends on LOW-LEVEL modules (DAOs)
public class CharityTrackerController {
    // Direct dependency on concrete DAOs
    private EventDAO eventDAO;      // Concrete
    private DonorDAO donorDAO;      // Concrete
    private PledgeDAO pledgeDAO;    // Concrete
    
    public void run() {
        eventDAO = new EventDAO();      // Tightly coupled
        donorDAO = new DonorDAO();      // Tightly coupled
        pledgeDAO = new PledgeDAO();    // Tightly coupled
    }
}

// Problems:
// - If EventDAO changes constructor, controller breaks
// - Can't test with mock EventDAO
// - High coupling between layers
```

#### ✅ AFTER (Good):
```java
// HIGH-LEVEL module (Controller) depends on ABSTRACTION (ServiceFactory)
// LOW-LEVEL modules (DAOs) also depend on same abstraction
public class CharityTrackerController {
    // Dependency on abstraction (Factory)
    private EventService eventService;
    private DonorService donorService;
    private PledgeService pledgeService;
    
    public CharityTrackerController() {
        // Get services from Factory (abstraction)
        this.eventService = ServiceFactory.getEventService();
        this.donorService = ServiceFactory.getDonorService();
        this.pledgeService = ServiceFactory.getPledgeService();
    }
}

// ServiceFactory (ABSTRACTION) - middle layer
public class ServiceFactory {
    private static EventService eventService;
    private static DonorService donorService;
    private static PledgeService pledgeService;
    
    public static EventService getEventService() {
        if (eventService == null) {
            eventService = new EventService();
        }
        return eventService;
    }
    // ... similar for others
}

// DEPENDENCY FLOW:
// High-level (Controller) 
//   ↓ depends on
// Abstraction (ServiceFactory)
//   ↓ depends on
// Low-level (EventService, DAOs)

// BENEFITS:
// - Controller doesn't know about DAOs
// - Can swap DAOs without changing Controller
// - Easy to test with mock services
// - Loose coupling
```

**Benefits:**
- ✓ Decoupled layers
- ✓ Easy to mock for testing
- ✓ Easy to swap implementations
- ✓ High-level modules don't break with low-level changes

**Code Location:** `src/service/ServiceFactory.java`

---

## PART 2: DESIGN PATTERNS

### 🔄 1. Singleton Pattern - DBConnection & DonationEventManager

**Purpose:** Ensure only ONE instance of critical resources

**Our Implementation:**
```java
public class DBConnection {
    private static DBConnection instance;  // Single instance
    
    private DBConnection() {                // Private constructor
        // Prevents: new DBConnection();
    }
    
    public static synchronized DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }
}

// Usage
DBConnection db1 = DBConnection.getInstance();
DBConnection db2 = DBConnection.getInstance();
// db1 == db2 (same object!)
```

**Where Used:**
- `DBConnection`: One database connection
- `DonationEventManager`: One observer manager

**Benefits:**
- ✓ Centralized resource
- ✓ Thread-safe access
- ✓ Memory efficient (single instance)
- ✓ Consistent state

**Code Location:** `src/db/DBConnection.java`, `src/observer/DonationEventManager.java`

---

### 📦 2. Factory Pattern - ServiceFactory

**Purpose:** Centralized creation of service objects

**Our Implementation:**
```java
public class ServiceFactory {
    private static EventService eventService;
    private static DonorService donorService;
    private static PledgeService pledgeService;
    
    public static EventService getEventService() {
        if (eventService == null) {
            eventService = new EventService();
        }
        return eventService;
    }
    
    public static DonorService getDonorService() {
        if (donorService == null) {
            donorService = new DonorService();
        }
        return donorService;
    }
    
    // ... similar for PledgeService
}

// Usage
EventService service1 = ServiceFactory.getEventService();
EventService service2 = ServiceFactory.getEventService();
// service1 == service2 (cached singleton)
```

**Where Used:** All service creation in Controller

**Benefits:**
- ✓ Centralized creation logic
- ✓ Easy caching of services
- ✓ Can add alternative implementations
- ✓ Simplifies dependency injection

**Code Location:** `src/service/ServiceFactory.java`

---

### 🎭 3. Facade Pattern - Service Layer

**Purpose:** Simplify complex interactions between DAOs

**Our Implementation:**
```java
// FACADE - Simplifies DAO interactions
public class PledgeService {
    private PledgeDAO pledgeDAO;
    private EventDAO eventDAO;
    private DonationEventManager eventManager;
    
    public boolean makeDonation(int donorId, int eventId, 
                                double amount, String date) {
        // VALIDATION
        // - Check amount >= 0
        // - Check event exists
        // - Check donor exists
        
        // BUSINESS LOGIC
        int result = pledgeDAO.addPledge(...);
        if (result > 0) {
            eventDAO.updateCollectedAmount(eventId, amount);
            eventManager.notifyDonationReceived(...);
        }
        return result > 0;
    }
}

// BEFORE using Facade - Controller had to do all this:
if (amount <= 0) error();
Event e = eventDAO.getEventById(eventId);
if (e == null) error();
int r = pledgeDAO.addPledge(...);
if (r > 0) eventDAO.updateCollectedAmount(eventId, amount);
eventManager.notify...

// AFTER - Simple one-line call
pledgeService.makeDonation(donorId, eventId, amount, date);
```

**Where Used:** EventService, DonorService, PledgeService

**Benefits:**
- ✓ Hides complexity
- ✓ Centralized validation
- ✓ Business logic in one place
- ✓ Decouples view from DAO

**Code Location:** `src/service/*.java`

---

### 👀 4. Observer Pattern - Event Notifications

**Purpose:** Notify multiple observers of state changes (donations, goal reached)

**Our Implementation:**
```java
// OBSERVER INTERFACE
public interface DonationObserver {
    void onDonationReceived(Pledge pledge, Event event);
    void onGoalReached(Event event);
    void onProgressUpdated(Event event, double percentage);
}

// CONCRETE OBSERVERS
public class EventProgressTracker implements DonationObserver {
    public void onDonationReceived(Pledge pledge, Event event) {
        System.out.println("$ " + pledge.getAmount() + " received!");
    }
    
    public void onGoalReached(Event event) {
        System.out.println("🎉 Goal reached for " + event.getName());
    }
    
    public void onProgressUpdated(Event event, double percentage) {
        System.out.println("Progress: " + percentage + "%");
    }
}

// OBSERVABLE - manages observers
public class DonationEventManager {
    private List<DonationObserver> observers = new ArrayList<>();
    
    public void subscribe(DonationObserver observer) {
        observers.add(observer);
    }
    
    public void notifyDonationReceived(Pledge p, Event e) {
        for (DonationObserver o : observers) {
            o.onDonationReceived(p, e);  // ALL observers notified
        }
    }
    
    public void notifyGoalReached(Event e) {
        for (DonationObserver o : observers) {
            o.onGoalReached(e);
        }
    }
}

// USAGE
DonationEventManager manager = DonationEventManager.getInstance();
manager.subscribe(new EventProgressTracker("System"));
manager.subscribe(new EmailNotifier("admin@example.com"));

// When donation happens:
manager.notifyDonationReceived(pledge, event);
// ➜ BOTH observers get notified automatically!
```

**Where Used:** `src/observer/`

**Benefits:**
- ✓ Loose coupling between subjects and observers
- ✓ Dynamic observer registration
- ✓ Multiple subscribers
- ✓ Automatic notifications

**Code Location:** `src/observer/`

---

## PART 3: PATTERN COMBINATIONS

### How Patterns Work Together

```
Controller (coordinates all)
    ↓
ServiceFactory (Factory Pattern)
    ↓
Services (Facade Pattern)
    ├─→ DAOs
    ├─→ DonationEventManager (Singleton)
    │   ├─→ DonationObserver (Observer interface)
    │   ├─→ EventProgressTracker (Observer impl)
    │   └─→ Multiple subscribers
    └─→ DBConnection (Singleton)
```

**Example: User Makes Donation**

```
1. User Input (View)
   ↓
2. Controller.handleMakeDonation()
   ↓
3. ServiceFactory.getPledgeService() (Factory)
   ↓
4. PledgeService.makeDonation() (Facade)
   ├─ Validate inputs
   ├─ DAO.addPledge() → Save to DB
   ├─ EventDAO.updateCollectedAmount()
   └─ DonationEventManager.getInstance() (Singleton)
      └─ notifyDonationReceived() (Observer)
         ├─ EventProgressTracker.onDonationReceived()
         ├─ EmailNotifier.onDonationReceived()
         └─ [Any other subscribers]
   ↓
5. View displays result
```

---

## SUMMARY TABLE

| Principle | Location | Benefit |
|-----------|----------|---------|
| SRP | Each class has one job | Easy to maintain |
| OCP | Observer allows extension | Add features without changes |
| LSP | Observers are interchangeable | Flexible design |
| ISP | Focused interfaces | Clean contracts |
| DIP | ServiceFactory abstraction | Loose coupling |

| Pattern | Location | Benefit |
|---------|----------|---------|
| Singleton | DBConnection, DonationEventManager | Single resource |
| Factory | ServiceFactory | Centralized creation |
| Facade | Service layer | Simplified interactions |
| Observer | Donation notifications | Loose coupling |

---

**All principles and patterns work together to create a maintainable, extensible, professional application!**
