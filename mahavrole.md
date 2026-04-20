# 📚 VIVA PREPARATION GUIDE
## Service Layer, Design Patterns, Business Logic & Observer Notifications

**Project:** Charity Event Fundraising Tracker  
**Date:** April 20, 2026  
**Purpose:** OOAD Concepts Deep Dive

---

## 📋 TABLE OF CONTENTS

1. [Service Layer - Facade Pattern](#service-layer--facade-pattern)
2. [Design Patterns Implemented](#design-patterns-implemented)
3. [Business Logic Flow](#business-logic-flow)
4. [Observer Pattern - Detailed](#observer-pattern--detailed)
5. [VIVA Key Questions & Answers](#viva-key-questions--answers)
6. [Quick Reference](#quick-reference)

---

## 🎯 SERVICE LAYER - FACADE PATTERN

### Overview

The **Service Layer** acts as a **Facade** that simplifies complex business operations by hiding database (DAO) and domain logic from the Controller.

```
┌──────────────────────────────────────────────────────────┐
│                   LAYERED ARCHITECTURE                   │
├──────────────────────────────────────────────────────────┤
│  Controller Layer                                        │
│  (CharityTrackerController)                              │
│         ↓ (calls)                                        │
│  Service Layer (FACADE)  ← Simplifies complexity         │
│  ├─ EventService                                         │
│  ├─ DonorService                                         │
│  └─ PledgeService                                        │
│         ↓ (uses)                                         │
│  DAO Layer (Data Access)                                 │
│  ├─ EventDAO                                             │
│  ├─ DonorDAO                                             │
│  └─ PledgeDAO                                            │
│         ↓ (executes SQL)                                 │
│  Database Layer (MySQL)                                  │
└──────────────────────────────────────────────────────────┘
```

### Three Core Services

---

### **1️⃣ EventService** 
**Location:** `src/service/EventService.java`

**Purpose:** Manage all event-related business logic

```java
public class EventService {
    private EventDAO eventDAO;
    
    // Method 1: Create Event with Validation
    public boolean createEvent(String name, double targetAmount, String description) {
        // BUSINESS LOGIC: Validate inputs
        if (name == null || name.trim().isEmpty()) {
            System.out.println("✗ Event name cannot be empty");
            return false;
        }
        
        if (targetAmount <= 0) {
            System.out.println("✗ Target amount must be positive");
            return false;
        }
        
        // Create and delegate to DAO
        Event event = new Event(name, targetAmount, description);
        return eventDAO.addEvent(event);
    }
    
    // Method 2: Retrieve Event
    public Event getEvent(int eventId) {
        return eventDAO.getEventById(eventId);
    }
    
    // Method 3: Get All Events
    public ArrayList<Event> getAllEvents() {
        return eventDAO.getAllEvents();
    }
    
    // Method 4: Update Collected Amount
    public boolean updateCollectedAmount(int eventId, double amount) {
        if (amount < 0) {
            System.out.println("✗ Donation amount cannot be negative");
            return false;
        }
        // Validation passed, update via DAO
        return eventDAO.updateCollectedAmount(eventId, amount);
    }
}
```

**Business Logic:**
- ✅ Name validation (non-empty check)
- ✅ Amount validation (must be positive)
- ✅ Ensures data consistency before database operations
- ✅ Hides SQL details from Controller

---

### **2️⃣ DonorService**
**Location:** `src/service/DonorService.java`

**Purpose:** Handle donor registration and validation

```java
public class DonorService {
    private DonorDAO donorDAO;
    
    // Method 1: Register New Donor
    public boolean registerDonor(String name, String email, String phone) {
        // Business Logic: Email validation
        if (!isValidEmail(email)) {
            System.out.println("✗ Invalid email format");
            return false;
        }
        
        // Create donor and save
        Donor donor = new Donor(name, email, phone);
        return donorDAO.addDonor(donor);
    }
    
    // Method 2: Email Validation Helper
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    
    // Method 3: Get Donor
    public Donor getDonor(int donorId) {
        return donorDAO.getDonorById(donorId);
    }
    
    // Method 4: Find Donor by Name
    public Donor findDonorByName(String name) {
        return donorDAO.getDonorByName(name);
    }
    
    // Method 5: Delete Donor
    public boolean deleteDonor(int donorId) {
        return donorDAO.deleteDonor(donorId);
    }
}
```

**Business Logic:**
- ✅ Email format validation using regex
- ✅ Prevents invalid email entries
- ✅ Phone number validation
- ✅ Duplicate prevention capabilities

---

### **3️⃣ PledgeService** ⭐ MOST IMPORTANT
**Location:** `src/service/PledgeService.java`

**Purpose:** Coordinate complete donation process + Observer notifications

```java
public class PledgeService {
    private PledgeDAO pledgeDAO;
    private EventDAO eventDAO;
    private DonationEventManager eventManager;  // Observer manager
    
    /**
     * CORE BUSINESS LOGIC: Make Donation
     * This is the most important method for VIVA!
     */
    public boolean makeDonation(int donorId, int eventId, double amount, String date) {
        
        // ========== STEP 1: VALIDATE AMOUNT ==========
        if (amount <= 0) {
            System.out.println("✗ Donation amount must be positive");
            return false;
        }
        
        // ========== STEP 2: VERIFY EVENT EXISTS ==========
        Event event = eventDAO.getEventById(eventId);
        if (event == null) {
            System.out.println("✗ Event not found");
            return false;
        }
        
        // ========== STEP 3: CREATE PLEDGE OBJECT ==========
        Pledge pledge = new Pledge(donorId, eventId, amount, date);
        
        // ========== STEP 4: SAVE TO DATABASE ==========
        if (!pledgeDAO.addPledge(pledge)) {
            System.out.println("✗ Failed to save pledge");
            return false;
        }
        
        // ========== STEP 5: UPDATE EVENT COLLECTED AMOUNT ==========
        eventDAO.updateCollectedAmount(eventId, amount);
        
        // ========== STEP 6: REFRESH EVENT DATA ==========
        event = eventDAO.getEventById(eventId);  // Important: Get fresh data
        
        // ========== STEP 7: TRIGGER OBSERVER NOTIFICATIONS ==========
        eventManager.notifyDonationReceived(pledge, event);
        
        // ========== STEP 8: CHECK GOAL COMPLETION ==========
        if (event.getProgressPercentage() >= 100) {
            eventManager.notifyGoalReached(event);  // Special notification
        } else {
            eventManager.notifyProgressUpdated(event, event.getProgressPercentage());
        }
        
        // ========== STEP 9: RETURN SUCCESS ==========
        return true;
    }
    
    // Other methods...
    public Pledge getPledge(int pledgeId) {
        return pledgeDAO.getPledgeById(pledgeId);
    }
    
    public ArrayList<Pledge> getPledgesForEvent(int eventId) {
        return pledgeDAO.getPledgesByEvent(eventId);
    }
    
    public ArrayList<Pledge> getPledgesByDonor(int donorId) {
        return pledgeDAO.getPledgesByDonor(donorId);
    }
    
    public double getTotalPledgedForEvent(int eventId) {
        return pledgeDAO.getTotalPledgedForEvent(eventId);
    }
}
```

**Key Business Rules:**
| Rule | Implementation | VIVA Question |
|------|---|---|
| Amount > 0 | `if (amount <= 0) return false;` | Why reject zero/negative? → Data integrity |
| Event exists | `Event event = eventDAO.getEventById(eventId);` | Why verify? → Referential integrity |
| Cascade updates | Save pledge → Update event → Refresh data | Why refresh? → Accurate progress calculation |
| Goal detection | `if (event.getProgressPercentage() >= 100)` | What happens at 100%? → Trigger goal notification |

---

## 🎨 DESIGN PATTERNS IMPLEMENTED

### Pattern Overview

| # | Pattern Name | Type | Purpose | Location |
|---|---|---|---|---|
| 1 | Facade | Structural | Simplify complex operations | Service Layer |
| 2 | Observer | Behavioral | Loose coupling for notifications | Observer Layer |
| 3 | Singleton | Creational | Single global instance | DonationEventManager, DBConnection |
| 4 | Factory | Creational | Centralized creation | ServiceFactory |
| 5 | MVC | Architectural | Separation of concerns | Overall architecture |

---

### **PATTERN 1: FACADE PATTERN** 🏛️

**What is it?**
Provides a single, simplified interface to a complex subsystem.

**Why use it?**
- Hide database complexity from business logic
- Reduce coupling between layers
- Simplify method calls for Controller

**Example: PledgeService.makeDonation()**

```
WITHOUT Facade (Bad):
Controller must:
├─ Validate amount
├─ Check event exists
├─ Create pledge object
├─ Call pledgeDAO.addPledge()
├─ Call eventDAO.updateCollectedAmount()
├─ Get fresh event data
├─ Call observer manager methods
├─ Check progress
└─ Handle all errors

WITH Facade (Good):
Controller just calls:
    pledgeService.makeDonation(donorId, eventId, amount, date)
    
Everything else is hidden inside PledgeService!
```

**SOLID Principle:** Single Responsibility Principle
- Service only handles donation logic
- DAO only handles database
- Controller only handles flow

---

### **PATTERN 2: OBSERVER PATTERN** 👁️⭐

**Core Concept:** Notify multiple objects about state changes without tight coupling

**Three Components:**

#### **A. Observer Interface** 📋
```java
// Location: src/observer/DonationObserver.java

public interface DonationObserver {
    
    /**
     * Called when donation is received
     * ALL observers implement this method
     */
    void onDonationReceived(Pledge pledge, Event event);
    
    /**
     * Called when event goal is reached
     * Special milestone notification
     */
    void onGoalReached(Event event);
    
    /**
     * Called when progress updates
     * For non-goal-reached donations
     */
    void onProgressUpdated(Event event, double percentageComplete);
}
```

**Why Interface?**
- Defines contract that all observers must follow
- Allows PledgeService to work with ANY observer
- Facilitates loose coupling

---

#### **B. Concrete Observer** 👀
```java
// Location: src/observer/EventProgressTracker.java

public class EventProgressTracker implements DonationObserver {
    
    private String name;
    
    public EventProgressTracker(String name) {
        this.name = name;
    }
    
    @Override
    public void onDonationReceived(Pledge pledge, Event event) {
        // WHAT: Display donation notification
        System.out.println("\n┌─ DONATION UPDATE ─────────────────────────────┐");
        System.out.println("│ Tracker: " + name);
        System.out.println("│ Event: " + event.getEventName());
        System.out.println("│ New Donation: ₹" + String.format("%.2f", pledge.getPledgeAmount()));
        System.out.println("│ Total Collected: ₹" + String.format("%.2f", event.getCollectedAmount()));
        System.out.println("└───────────────────────────────────────────────┘");
    }
    
    @Override
    public void onGoalReached(Event event) {
        // WHAT: Display celebration when goal is reached
        System.out.println("\n╔═════════════════════════════════════════════════╗");
        System.out.println("║         🎉 GOAL REACHED! 🎉                     ║");
        System.out.println("║ Event: " + event.getEventName());
        System.out.println("║ Target: ₹" + String.format("%.2f", event.getTargetAmount()) + " ACHIEVED");
        System.out.println("╚═════════════════════════════════════════════════╝");
    }
    
    @Override
    public void onProgressUpdated(Event event, double percentageComplete) {
        // WHAT: Display progress bar
        String progressBar = generateProgressBar((int) percentageComplete);
        System.out.println("\n→ [" + event.getEventName() + "] Progress: " + progressBar + 
                          " " + String.format("%.1f%%", percentageComplete));
    }
    
    private String generateProgressBar(int percentage) {
        // Helper: Create visual progress bar
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < 20; i++) {
            if (i < percentage / 5) {
                bar.append("█");
            } else {
                bar.append("░");
            }
        }
        bar.append("]");
        return bar.toString();
    }
}
```

**What This Observer Does:**
- Displays donation updates
- Shows celebration when goal reached
- Renders visual progress bar

**Can you add more observers?**
✅ YES! Email notifier, SMS notifier, Database logger, etc.
They all implement `DonationObserver` and register with `DonationEventManager`

---

#### **C. Observable (Manager)** 📡
```java
// Location: src/observer/DonationEventManager.java

public class DonationEventManager {
    
    // ===== SINGLETON PATTERN =====
    private static DonationEventManager instance;
    
    private List<DonationObserver> observers;  // List of all subscribed observers
    
    // Singleton implementation
    private DonationEventManager() {
        observers = new ArrayList<>();
    }
    
    public static synchronized DonationEventManager getInstance() {
        if (instance == null) {
            instance = new DonationEventManager();
        }
        return instance;
    }
    
    // ===== OBSERVER MANAGEMENT =====
    
    /**
     * Register an observer (Subscribe)
     */
    public void subscribe(DonationObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            System.out.println("✓ Observer registered: " + observer.getClass().getSimpleName());
        }
    }
    
    /**
     * Unregister an observer (Unsubscribe)
     */
    public void unsubscribe(DonationObserver observer) {
        if (observers.remove(observer)) {
            System.out.println("✓ Observer unregistered: " + observer.getClass().getSimpleName());
        }
    }
    
    // ===== NOTIFICATION METHODS =====
    
    /**
     * Notify all observers: Donation received
     * Called from: PledgeService.makeDonation()
     */
    public void notifyDonationReceived(Pledge pledge, Event event) {
        for (DonationObserver observer : observers) {
            observer.onDonationReceived(pledge, event);
        }
    }
    
    /**
     * Notify all observers: Goal reached!
     * Called from: PledgeService.makeDonation() when progress >= 100%
     */
    public void notifyGoalReached(Event event) {
        for (DonationObserver observer : observers) {
            observer.onGoalReached(event);
        }
    }
    
    /**
     * Notify all observers: Progress updated
     * Called from: PledgeService.makeDonation() when progress < 100%
     */
    public void notifyProgressUpdated(Event event, double percentageComplete) {
        for (DonationObserver observer : observers) {
            observer.onProgressUpdated(event, percentageComplete);
        }
    }
    
    /**
     * Get observer count
     */
    public int getObserverCount() {
        return observers.size();
    }
}
```

**How Observer Manager Works:**
```
1. PledgeService creates donation
2. Calls eventManager.notifyDonationReceived()
3. Manager iterates through all observers
4. Calls observer.onDonationReceived() on each
5. Each observer handles notification independently
```

**Why This Design?**
- ✅ Loose Coupling: PledgeService doesn't know about specific observers
- ✅ Open/Closed: Add new observers without modifying PledgeService
- ✅ Single Responsibility: Each observer handles its own notification type
- ✅ Testability: Easy to mock observer manager

---

### **PATTERN 3: SINGLETON PATTERN** 🔒

**Where:** DonationEventManager and DBConnection

**Purpose:** Ensure only ONE instance exists globally

```java
public class DonationEventManager {
    
    // Static holder of the single instance
    private static DonationEventManager instance;
    
    // Private constructor prevents external instantiation
    private DonationEventManager() {
        observers = new ArrayList<>();
    }
    
    // Synchronized method ensures thread safety
    public static synchronized DonationEventManager getInstance() {
        if (instance == null) {
            instance = new DonationEventManager();
        }
        return instance;
    }
}
```

**Usage:**
```java
// Always use getInstance() to get the same instance
DonationEventManager manager1 = DonationEventManager.getInstance();
DonationEventManager manager2 = DonationEventManager.getInstance();

// manager1 and manager2 point to SAME object
assert manager1 == manager2;  // TRUE!
```

**Why Singleton for Observer Manager?**
| Reason | Explanation |
|---|---|
| Global State | All components must access same observer list |
| Consistency | Ensures all notifications are coordinated |
| Thread Safety | Provides synchronized access to observers |
| Easy Access | No need to pass reference around |

---

### **PATTERN 4: FACTORY PATTERN** 🏭

**Location:** `src/service/ServiceFactory.java`

**Purpose:** Centralized creation and caching of service instances

```java
public class ServiceFactory {
    
    // Private constructor prevents direct instantiation
    private ServiceFactory() {
    }
    
    // Static holders for cached service instances
    private static EventService eventService;
    private static DonorService donorService;
    private static PledgeService pledgeService;
    
    /**
     * Factory Method: Get EventService
     * If not created, create. If already created, return cached.
     */
    public static EventService getEventService() {
        if (eventService == null) {
            eventService = new EventService();
        }
        return eventService;
    }
    
    /**
     * Factory Method: Get DonorService
     */
    public static DonorService getDonorService() {
        if (donorService == null) {
            donorService = new DonorService();
        }
        return donorService;
    }
    
    /**
     * Factory Method: Get PledgeService
     */
    public static PledgeService getPledgeService() {
        if (pledgeService == null) {
            pledgeService = new PledgeService();
        }
        return pledgeService;
    }
}
```

**Usage in Controller:**
```java
public class CharityTrackerController {
    public CharityTrackerController() {
        // Get services from factory (not direct creation)
        this.eventService = ServiceFactory.getEventService();
        this.donorService = ServiceFactory.getDonorService();
        this.pledgeService = ServiceFactory.getPledgeService();
    }
}
```

**Benefits:**
| Benefit | Explanation |
|---|---|
| Centralized Creation | One place to create services |
| Caching/Pooling | Can easily add caching later |
| Dependency Management | Easy to swap implementations |
| Testability | Can create mock factory for testing |

---

### **PATTERN 5: MVC PATTERN** 🏛️

**Architectural Pattern:** Separates application into three interconnected components

```
┌─────────────────────────────────────────────────────────────┐
│                    MVC ARCHITECTURE                         │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│ REQUEST FLOW:                                              │
│                                                             │
│  1. USER INPUT                                             │
│     ↓                                                      │
│  2. CONTROLLER (CharityTrackerController)                  │
│     ├─ Gets input from View                               │
│     ├─ Calls appropriate Service                          │
│     └─ Updates View with results                          │
│     ↓                                                      │
│  3. SERVICE (EventService, DonorService, PledgeService)   │
│     ├─ Applies business logic                             │
│     ├─ Validates data                                     │
│     └─ Calls DAO for persistence                          │
│     ↓                                                      │
│  4. DAO (EventDAO, DonorDAO, PledgeDAO)                    │
│     ├─ Executes SQL queries                               │
│     └─ Returns data to Service                            │
│     ↓                                                      │
│  5. DATABASE (MySQL)                                      │
│     └─ Stores/retrieves data                              │
│                                                             │
│ RESPONSE FLOW (Vice versa):                                │
│  Database → DAO → Service → Controller → View → User       │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

**Responsibilities:**

| Component | Location | Responsibility |
|---|---|---|
| **View** | `src/view/ConsoleView.java` | Display information & get user input |
| **Controller** | `src/controller/CharityTrackerController.java` | Route requests, call services, update view |
| **Service** | `src/service/` | Business logic, validation, orchestration |
| **DAO** | `src/dao/` | Database operations, SQL queries |
| **Model** | `src/model/` | Data representation (POJO) |

---

## 💼 BUSINESS LOGIC FLOW

### Complete Donation Process

```
┌─────────────────────────────────────────────────────────────┐
│           DONATION WORKFLOW - STEP BY STEP                  │
└─────────────────────────────────────────────────────────────┘

STEP 1: USER INPUT
    ↓
    User enters: Donor ID, Event ID, Amount, Date
    Controller: handleMakeDonation()

STEP 2: CALL SERVICE
    ↓
    Controller calls:
    pledgeService.makeDonation(donorId, eventId, amount, date)

STEP 3: VALIDATION (Inside PledgeService)
    ├─ Check amount > 0?
    │  ├─ NO → Return false (show error message)
    │  └─ YES → Continue
    │
    └─ Check event exists?
       ├─ NO → Return false (show error message)
       └─ YES → Continue

STEP 4: CREATE PLEDGE OBJECT
    ↓
    Pledge pledge = new Pledge(donorId, eventId, amount, date)
    (Object created in memory, not in database yet)

STEP 5: SAVE TO DATABASE
    ↓
    pledgeDAO.addPledge(pledge)
    ├─ Executes SQL: INSERT INTO pledges ...
    ├─ Returns success/failure
    └─ On failure → Return false

STEP 6: UPDATE EVENT COLLECTED AMOUNT
    ↓
    eventDAO.updateCollectedAmount(eventId, amount)
    ├─ Executes SQL: UPDATE events SET collectedAmount = ...
    └─ Event: 900 → 950 (example)

STEP 7: REFRESH EVENT DATA
    ↓
    event = eventDAO.getEventById(eventId)
    (Important! Gets fresh data from database)
    ├─ Old: Event {collected: 900}
    └─ New: Event {collected: 950}

STEP 8: NOTIFY OBSERVERS - DONATION RECEIVED
    ↓
    eventManager.notifyDonationReceived(pledge, event)
    ├─ Observer 1: Display donation amount
    ├─ Observer 2: Display running total
    └─ Observer 3: Log to database
    
    Output:
    ┌─ DONATION UPDATE ─────────────────────┐
    │ Event: School Renovation             │
    │ New Donation: ₹500                   │
    │ Total Collected: ₹950                │
    └───────────────────────────────────────┘

STEP 9: CHECK GOAL COMPLETION
    ↓
    if (event.getProgressPercentage() >= 100)
    ├─ YES (Goal reached):
    │   └─ eventManager.notifyGoalReached(event)
    │       Output:
    │       ╔═══════════════════════════════╗
    │       ║     🎉 GOAL REACHED! 🎉      ║
    │       ║ Target: ₹1000 ACHIEVED        ║
    │       ╚═══════════════════════════════╝
    │
    └─ NO (Still collecting):
        └─ eventManager.notifyProgressUpdated(event, progress)
            Output:
            → Progress: [█████░░░░░░░░░░░░░] 95.0%

STEP 10: RETURN SUCCESS TO USER
    ↓
    Return true
    ↓
    Controller displays: "✓ Donation successful!"
```

---

### **Real-World Example**

**Scenario:** 
- Event target: ₹1000
- Event current: ₹900 collected
- Donor gives: ₹150
- New total: ₹1050 (105% = GOAL REACHED!)

**Execution Flow:**

```java
// In handleMakeDonation()
pledgeService.makeDonation(3, 5, 150.00, "2026-04-20");

// Inside PledgeService.makeDonation()
// Step 1: Validate amount
if (150 <= 0) return false;  // 150 > 0 ✓ Pass

// Step 2: Check event exists
Event event = eventDAO.getEventById(5);
if (event == null) return false;  // Event exists ✓ Pass

// Step 3: Create pledge
Pledge pledge = new Pledge(3, 5, 150.00, "2026-04-20");

// Step 4: Save to DB
pledgeDAO.addPledge(pledge);  // ✓ Saved

// Step 5: Update event
eventDAO.updateCollectedAmount(5, 150.00);
// SQL: UPDATE events SET collectedAmount = 900 + 150 WHERE eventId = 5

// Step 6: Refresh
event = eventDAO.getEventById(5);  // event.collected = 1050

// Step 7: Notify donation received
eventManager.notifyDonationReceived(pledge, event);
// Output: Shows ₹150 donation + ₹1050 total

// Step 8: Check goal
double progress = (1050 / 1000) * 100;  // 105%
if (progress >= 100) {  // TRUE!
    eventManager.notifyGoalReached(event);
    // Output: 🎉 GOAL REACHED!
}

return true;  // Success!
```

---

### **Business Logic Rules Summary**

| Rule | Code | Reason | VIVA Answer |
|------|------|--------|---|
| Amount > 0 | `if(amount <= 0) return false;` | Ensure positive donations | Data integrity |
| Event exists | `Event e = eventDAO.getEventById()` | Prevent orphaned pledges | Referential integrity |
| Cascade updates | Save → Update → Refresh | Ensure correct state | Accuracy |
| Goal = 100% | `if(progress >= 100)` | Trigger milestone notification | User achievement feedback |
| Refresh event | `event = eventDAO.getEventById()` | Get latest progress | Prevent stale data |

---

## 🔔 OBSERVER PATTERN - DETAILED ANALYSIS

### How It Works - Visual Diagram

```
┌─────────────────────────────────────────────────────────────┐
│           OBSERVER PATTERN EXECUTION                        │
└─────────────────────────────────────────────────────────────┘

REGISTRATION PHASE:
    
    Controller:
        ↓
        EventProgressTracker tracker = new EventProgressTracker("System");
        ↓
        eventManager.subscribe(tracker);
        ↓
        eventManager.observers = [tracker]

NOTIFICATION PHASE (When donation is made):

    PledgeService.makeDonation():
        ↓
        eventManager.notifyDonationReceived(pledge, event)
        ↓
        for (DonationObserver observer : observers) {
            observer.onDonationReceived(pledge, event);  ← calls this
        }
        ↓
        EventProgressTracker.onDonationReceived():
            Display: "DONATION UPDATE: ₹500 received"
        ↓
    eventManager.notifyGoalReached(event)  [if goal reached]
        ↓
        EventProgressTracker.onGoalReached():
            Display: "🎉 GOAL REACHED!"
```

### The Three Notification Types

#### **Notification 1: onDonationReceived()**

```java
// WHEN: Every time donation is made
// TRIGGERED BY: eventManager.notifyDonationReceived(pledge, event)
// WHO RECEIVES IT: All subscribed observers

@Override
public void onDonationReceived(Pledge pledge, Event event) {
    // ACTION: Display donation details
    System.out.println("\n┌─ DONATION UPDATE ─────────────────────────────┐");
    System.out.println("│ Tracker: " + name);
    System.out.println("│ Event: " + event.getEventName());
    System.out.println("│ New Donation: ₹" + pledge.getPledgeAmount());
    System.out.println("│ Total Collected: ₹" + event.getCollectedAmount());
    System.out.println("└───────────────────────────────────────────────┘");
}
```

**Example Output:**
```
┌─ DONATION UPDATE ─────────────────────────────┐
│ Tracker: System Tracker
│ Event: School Renovation Project
│ New Donation: ₹500.00
│ Total Collected: ₹2500.00
└───────────────────────────────────────────────┘
```

---

#### **Notification 2: onGoalReached()**

```java
// WHEN: When event progress reaches 100% or more
// TRIGGERED BY: eventManager.notifyGoalReached(event)
// WHO RECEIVES IT: All subscribed observers

@Override
public void onGoalReached(Event event) {
    // ACTION: Display celebration message
    System.out.println("\n╔═════════════════════════════════════════════════╗");
    System.out.println("║         🎉 GOAL REACHED! 🎉                     ║");
    System.out.println("║ Event: " + event.getEventName());
    System.out.println("║ Target: ₹" + event.getTargetAmount() + " ACHIEVED");
    System.out.println("╚═════════════════════════════════════════════════╝");
}
```

**Example Output:**
```
╔═════════════════════════════════════════════════╗
║         🎉 GOAL REACHED! 🎉                     ║
║ Event: School Renovation Project
║ Target: ₹1000.00 ACHIEVED
╚═════════════════════════════════════════════════╝
```

---

#### **Notification 3: onProgressUpdated()**

```java
// WHEN: When progress is < 100% (not goal reached)
// TRIGGERED BY: eventManager.notifyProgressUpdated(event, percentage)
// WHO RECEIVES IT: All subscribed observers

@Override
public void onProgressUpdated(Event event, double percentageComplete) {
    // ACTION: Display progress bar
    String progressBar = generateProgressBar((int) percentageComplete);
    System.out.println("\n→ [" + event.getEventName() + "] Progress: " + 
                      progressBar + " " + String.format("%.1f%%", percentageComplete));
}

private String generateProgressBar(int percentage) {
    StringBuilder bar = new StringBuilder("[");
    for (int i = 0; i < 20; i++) {
        if (i < percentage / 5) {
            bar.append("█");  // Filled block
        } else {
            bar.append("░");  // Empty block
        }
    }
    bar.append("]");
    return bar.toString();
}
```

**Example Output:**
```
→ [School Renovation Project] Progress: [████████░░░░░░░░░░] 80.0%
```

---

### Loose Coupling Benefit

**BEFORE Observer Pattern (Tight Coupling):** ❌

```java
// PledgeService directly depends on EventProgressTracker
public class PledgeService {
    private EventProgressTracker tracker;
    
    public void makeDonation(...) {
        // Save donation
        pledgeDAO.addPledge(pledge);
        
        // PROBLEM: Directly call tracker
        tracker.displayDonationUpdate(pledge, event);
        tracker.displayGoalReached(event);
        
        // PROBLEMS:
        // 1. PledgeService knows about EventProgressTracker
        // 2. Can't add new observers without modifying PledgeService
        // 3. Hard to test PledgeService (must mock tracker)
        // 4. Tight coupling!
    }
}
```

**AFTER Observer Pattern (Loose Coupling):** ✅

```java
// PledgeService works with ANY observer (interface-based)
public class PledgeService {
    private DonationEventManager eventManager;  // Works with manager, not specific observer
    
    public void makeDonation(...) {
        // Save donation
        pledgeDAO.addPledge(pledge);
        
        // SOLUTION: Notify through manager
        eventManager.notifyDonationReceived(pledge, event);
        eventManager.notifyGoalReached(event);
        
        // BENEFITS:
        // 1. PledgeService doesn't know about specific observers
        // 2. Can add new observers without modifying PledgeService
        // 3. Easy to test (mock the manager)
        // 4. Loose coupling!
    }
}
```

**Adding a New Observer (Easy with loose coupling):**

```java
// Create new observer (Email Notifier)
public class EmailNotifier implements DonationObserver {
    @Override
    public void onDonationReceived(Pledge pledge, Event event) {
        sendEmail("admin@charity.com", "New donation of ₹" + pledge.getPledgeAmount());
    }
    
    @Override
    public void onGoalReached(Event event) {
        sendEmail("admin@charity.com", "Goal reached for " + event.getEventName());
    }
    
    @Override
    public void onProgressUpdated(Event event, double percentage) {
        // Email notification for milestones
    }
}

// Register it (in Controller)
emailNotifier = new EmailNotifier();
eventManager.subscribe(emailNotifier);

// NO CHANGES NEEDED TO:
// - PledgeService
// - DonationEventManager
// - EventProgressTracker
```

---

## ❓ VIVA KEY QUESTIONS & ANSWERS

### Q1: What is the Service Layer and why use it?

**Answer:**
The Service Layer is a **Facade Pattern** that provides simplified business operations and hides complexity from the Controller.

**Example:** Instead of Controller doing:
```java
// Without Service Layer (tightly coupled)
try {
    Pledge p = new Pledge(dId, eId, amt, date);
    pledgeDAO.addPledge(p);
    eventDAO.updateCollectedAmount(eId, amt);
    Event e = eventDAO.getEventById(eId);
    notifyObservers(p, e);
} catch (Exception ex) {
    // error handling
}
```

Controller simply calls:
```java
// With Service Layer (abstracted)
pledgeService.makeDonation(dId, eId, amt, date);
```

**Benefits:**
- Hides database complexity
- Centralizes business logic
- Easy to test
- Reduces Controller responsibility

---

### Q2: How many Design Patterns are used in this project?

**Answer:**
**Five major design patterns:**

1. **Facade Pattern** - Service layer simplifies complex DAO operations
2. **Observer Pattern** - Notifies observers of donation events without tight coupling
3. **Singleton Pattern** - DonationEventManager & DBConnection ensure single global instance
4. **Factory Pattern** - ServiceFactory creates and caches service instances
5. **MVC Pattern** - Architectural pattern separating View, Controller, and Model

| Pattern | Type | Purpose |
|---|---|---|
| Facade | Structural | Simplify complex operations |
| Observer | Behavioral | Loose coupling + notifications |
| Singleton | Creational | Single global instance |
| Factory | Creational | Centralized object creation |
| MVC | Architectural | Separation of concerns |

---

### Q3: Explain the Observer Pattern implementation.

**Answer:**
Observer Pattern has three components:

**1. Observer Interface (Contract):**
```java
public interface DonationObserver {
    void onDonationReceived(Pledge pledge, Event event);
    void onGoalReached(Event event);
    void onProgressUpdated(Event event, double percentageComplete);
}
```
Defines what all observers must implement.

**2. Concrete Observer (Implementation):**
```java
public class EventProgressTracker implements DonationObserver {
    // Implements the three callback methods
    // Displays notifications on console
}
```
Performs specific actions when events occur.

**3. Observable (Manager):**
```java
public class DonationEventManager {
    private List<DonationObserver> observers;  // Maintains list
    
    public void subscribe(DonationObserver observer) {
        observers.add(observer);  // Register
    }
    
    public void notifyDonationReceived(Pledge pledge, Event event) {
        for (DonationObserver observer : observers) {
            observer.onDonationReceived(pledge, event);  // Notify all
        }
    }
}
```
Manages observers and triggers notifications.

**Execution Flow:**
```
1. Observer subscribes to manager
2. When event occurs, PledgeService calls manager's notify method
3. Manager iterates all observers
4. Manager calls observer's callback method
5. Each observer handles notification independently
```

**Why This Pattern?**
- ✅ Loose coupling: PledgeService doesn't know about specific observers
- ✅ Open/Closed: Can add new observers without modifying existing code
- ✅ Single Responsibility: Each observer handles its own notification
- ✅ Easy testing: Can mock observer manager

---

### Q4: What business logic validates donations?

**Answer:**
PledgeService.makeDonation() validates:

```java
// Validation 1: Amount must be positive
if (amount <= 0) {
    System.out.println("✗ Donation amount must be positive");
    return false;
}

// Validation 2: Event must exist
Event event = eventDAO.getEventById(eventId);
if (event == null) {
    System.out.println("✗ Event not found");
    return false;
}

// Implicit: Donor existence (when creating Pledge)
// Implicit: Amount is numeric (enforced by type system)
```

**Why these validations?**
| Validation | Reason |
|---|---|
| Amount > 0 | Ensure positive donations; prevent data corruption |
| Event exists | Prevent orphaned pledges; maintain referential integrity |
| Donor exists | Ensure donor-pledge association is valid |

---

### Q5: What happens when a goal is reached?

**Answer:**
When event progress reaches 100%, a special notification is triggered:

```java
// In PledgeService.makeDonation()
if (event.getProgressPercentage() >= 100) {
    eventManager.notifyGoalReached(event);  // Special notification
}
```

**What notifyGoalReached() does:**
```java
public void notifyGoalReached(Event event) {
    for (DonationObserver observer : observers) {
        observer.onGoalReached(event);  // All observers notified
    }
}
```

**Observer displays celebratory message:**
```
╔═════════════════════════════════════════════════╗
║         🎉 GOAL REACHED! 🎉                     ║
║ Event: School Renovation Project
║ Target: ₹1000.00 ACHIEVED
╚═════════════════════════════════════════════════╝
```

**Why separate notification?**
- Milestone achievement recognition
- Different user experience (celebration vs progress)
- Can trigger special actions (email, awards, etc.)

---

### Q6: Why use Singleton for DonationEventManager?

**Answer:**
Singleton ensures **only one instance** of DonationEventManager exists globally.

**Why this matters:**

```
WITHOUT Singleton (PROBLEM):
    instance1 = new DonationEventManager();
    instance2 = new DonationEventManager();
    
    instance1.subscribe(tracker);  // Registered to instance1
    instance2.notifyDonationReceived();  // Notifies only instance2's observers
    
    Result: Notifications lost! tracker won't receive them!

WITH Singleton (SOLUTION):
    instance1 = DonationEventManager.getInstance();
    instance2 = DonationEventManager.getInstance();
    
    assert instance1 == instance2;  // TRUE! Same object
    
    instance1.subscribe(tracker);  // Registered globally
    instance2.notifyDonationReceived();  // Notifies all observers
    
    Result: tracker receives notification!
```

**Other benefits:**
- ✅ Global coordination of all notifications
- ✅ Thread-safe access (using synchronized)
- ✅ Prevents duplicate observer registrations
- ✅ Consistent state across application

---

## 📊 QUICK REFERENCE

### Service Methods Cheat Sheet

```java
// === EVENT SERVICE ===
eventService.createEvent(name, amount, description);     // Create event
eventService.getEvent(eventId);                          // Get one event
eventService.getAllEvents();                             // Get all events
eventService.updateCollectedAmount(eventId, amount);     // Add funds

// === DONOR SERVICE ===
donorService.registerDonor(name, email, phone);          // Register donor
donorService.getDonor(donorId);                          // Get one donor
donorService.findDonorByName(name);                      // Search donor
donorService.deleteDonor(donorId);                       // Remove donor

// === PLEDGE SERVICE ===
pledgeService.makeDonation(donorId, eventId, amount, date);  // Core method ⭐
pledgeService.getPledgesForEvent(eventId);                   // Get donations
pledgeService.getTotalPledgedForEvent(eventId);             // Sum of donations

// === OBSERVER MANAGER ===
DonationEventManager.getInstance();                      // Get singleton
eventManager.subscribe(observer);                        // Register observer
eventManager.notifyDonationReceived(pledge, event);      // Notify all
eventManager.notifyGoalReached(event);                   // Goal notification
eventManager.notifyProgressUpdated(event, progress);     // Progress notification
```

---

### Design Patterns Quick Reference

```java
// === FACADE ===
pledgeService.makeDonation(...)  // Hides: DAO + validation + observer notifications

// === OBSERVER ===
public interface DonationObserver {
    void onDonationReceived(Pledge, Event);
    void onGoalReached(Event);
    void onProgressUpdated(Event, double);
}

// === SINGLETON ===
DonationEventManager.getInstance()  // Always same instance

// === FACTORY ===
ServiceFactory.getEventService()    // Centralized creation

// === MVC ===
Controller → Service → DAO → Database
```

---

### SOLID Principles Applied

| Principle | In Project | Example |
|---|---|---|
| **S** - Single Responsibility | Each service has one job | EventService only handles events |
| **O** - Open/Closed | Open for extension, closed for modification | Can add new observers without modifying PledgeService |
| **L** - Liskov Substitution | Services can be replaced | EventService interface replaces actual implementation |
| **I** - Interface Segregation | Small focused interfaces | DonationObserver has clear contract |
| **D** - Dependency Inversion | Depend on abstractions | PledgeService depends on DonationEventManager (abstraction) |

---

### Common VIVA Traps to Avoid

| Trap | Wrong Answer | Correct Answer |
|---|---|---|
| "Why service layer?" | "For database access" | For business logic abstraction & validation |
| "Observer benefits?" | "Tight coupling" | Loose coupling & flexibility |
| "Singleton purpose?" | "Prevent multiple objects" | Ensure global coordination of notifications |
| "Pattern count?" | Just one (MVC) | Five patterns total |
| "When goal reached?" | On calculation | When progress >= 100% with special notification |

---

## 🎓 Final Tips for VIVA

1. **Understand WHY**, not just WHAT
   - Don't just memorize patterns, explain their purpose
   - Connect patterns to SOLID principles

2. **Use Code Examples**
   - Reference actual code from project
   - Show execution flow with concrete data

3. **Focus on Business Logic**
   - PledgeService.makeDonation() is the core
   - Explain each step and its purpose

4. **Explain Observer Pattern Deeply**
   - This is usually the key question
   - Show loose coupling benefit
   - Explain subscription & notification

5. **Connect to OOAD Concepts**
   - Encapsulation: Private fields, getters/setters
   - Abstraction: Service layer hides details
   - Inheritance: Observer interface implementation
   - Polymorphism: Multiple observer implementations
   - Association: Classes work together

6. **Prepare Real Scenarios**
   - Trace through complete donation process
   - Show observer notifications step-by-step
   - Demonstrate cascading updates

---

**Good luck with your VIVA! You've got this! 🚀**
