# 📝 ACADEMIC MINI PROJECT REPORT

## Charity Event Fundraising Tracker - OOAD Implementation

---

## TABLE OF CONTENTS

1. Problem Statement
2. Key Features
3. System Architecture
4. Use Case Analysis
5. Class Diagram Analysis
6. Activity & State Diagrams
7. Design Principles (SOLID)
8. Design Patterns
9. MVC Architecture
10. Implementation Details
11. Conclusion

---

## 1. PROBLEM STATEMENT

### Background
Charity organizations struggle to manage fundraising events efficiently. They need a system to:
- Track multiple fundraising events
- Manage donor information
- Record donations and track progress
- Monitor fundraising goals

### Objective
Design and implement a **Charity Event Fundraising Tracker** that demonstrates:
- **Object-Oriented Analysis & Design (OOAD)** principles
- **SOLID** design principles
- **Design patterns** (Factory, Singleton, Observer, Facade)
- **MVC architecture**
- **UML modeling**
- **Database persistence** with JDBC

### Scope
The system supports:
- Creating and managing charity events
- Registering donors
- Recording donations
- Tracking progress
- Viewing statistics
- Event notifications

---

## 2. KEY FEATURES

| Feature | Description |
|---------|-------------|
| **Event Management** | Create, view, and manage charity events with target amounts |
| **Donor Registration** | Register donors with email validation |
| **Donation Tracking** | Record donations and track event progress |
| **Progress Monitoring** | Real-time progress percentage and visual representation |
| **Observer Notifications** | Notify observers when goals are reached |
| **Statistics Dashboard** | View system-wide statistics and analytics |
| **Data Persistence** | MySQL database with JDBC for data storage |
| **MVC Architecture** | Clean separation of concerns |
| **Error Handling** | Comprehensive validation and error management |

---

## 3. SYSTEM ARCHITECTURE

### 3.1 Layered Architecture

```
┌─────────────────────────────────────┐
│         View Layer (MVC)            │
│     ConsoleView (UI Rendering)      │
└─────────────────────────────────────┘
             ↕ (displays)
┌─────────────────────────────────────┐
│      Controller Layer (MVC)         │
│  CharityTrackerController           │
│  (Request Handling & Orchestration) │
└─────────────────────────────────────┘
             ↕ (calls)
┌─────────────────────────────────────┐
│      Service Layer (Facade)         │
│  EventService, DonorService, etc.   │
│  (Business Logic & Validation)      │
└─────────────────────────────────────┘
             ↕ (uses)
┌─────────────────────────────────────┐
│ Observer Layer (Design Pattern)      │
│  DonationEventManager & Observers   │
│  (Event Notification System)        │
└─────────────────────────────────────┘
             ↕ (calls)
┌─────────────────────────────────────┐
│      DAO Layer (Data Access)        │
│  EventDAO, DonorDAO, PledgeDAO      │
│  (Database Operations)              │
└─────────────────────────────────────┘
             ↕ (uses JDBC)
┌─────────────────────────────────────┐
│     Model Layer (POJO)              │
│  Event, Donor, Pledge               │
│  (Data Representation)              │
└─────────────────────────────────────┘
             ↕ 
┌─────────────────────────────────────┐
│    Database Connection (Singleton)  │
│      DBConnection (JDBC)            │
│    (Connection Management)          │
└─────────────────────────────────────┘
             ↕
┌─────────────────────────────────────┐
│        MySQL Database               │
│  (tables: events, donors, pledges)  │
└─────────────────────────────────────┘
```

### 3.2 Package Structure

```
src/
├── Main.java ...................... Entry point
├── model/ ......................... POJO classes
│   ├── Event.java
│   ├── Donor.java
│   └── Pledge.java
├── controller/ .................... MVC Controller
│   └── CharityTrackerController.java
├── view/ .......................... MVC View
│   └── ConsoleView.java
├── service/ ....................... Business logic (Facade)
│   ├── EventService.java
│   ├── DonorService.java
│   ├── PledgeService.java
│   └── ServiceFactory.java (Factory Pattern)
├── dao/ ........................... Data Access Objects
│   ├── EventDAO.java
│   ├── DonorDAO.java
│   └── PledgeDAO.java
├── db/ ............................ Database management
│   └── DBConnection.java (Singleton)
├── observer/ ...................... Observer Pattern
│   ├── DonationObserver.java
│   ├── DonationEventManager.java
│   └── EventProgressTracker.java
└── util/ .......................... Utilities
```

---

## 4. USE CASE ANALYSIS

### 4.1 Actors

1. **Admin:** System administrator who creates events and manages the system
2. **Donor:** A person who registers and makes donations to events

### 4.2 Use Cases

| Use Case | Actor | Description |
|----------|-------|-------------|
| Create Event | Admin | Admin creates a new fundraising event with target amount |
| Register Donor | Donor | Donor registers with name, email, and phone |
| Make Donation | Donor | Donor makes a donation to an event |
| View Event Progress | Donor | View progress percentage and donor list |
| View All Events | Donor | View all fundraising events |
| View All Donors | Admin | View all registered donors |
| View Statistics | Admin | View system-wide statistics |
| Delete Event | Admin | Admin deletes completed or cancelled events |
| Receive Notifications | Donor | Notify when goal is reached |

### 4.3 Primary Flow: Make Donation

```
1. Donor enters Donor ID
2. System verifies donor exists
3. Donor enters Event ID
4. System verifies event exists
5. Donor enters donation amount
6. System validates amount > 0
7. Create Pledge record
8. Save to database
9. Update Event collected amount
10. Calculate progress percentage
11. Notify DonationObservers
12. If progress >= 100%, notify goal reached
13. Display success confirmation
```

---

## 5. CLASS DIAGRAM ANALYSIS

### 5.1 Model Layer

**Event.java**
- Represents a charity event
- Attributes: eventId, eventName, targetAmount, collectedAmount, description
- Responsibilities: Store event data and calculate progress

**Donor.java**
- Represents a donor/contributor
- Attributes: donorId, donorName, email, phoneNumber
- Responsibilities: Store donor information

**Pledge.java**
- Represents a donation transaction
- Attributes: pledgeId, donorId, eventId, pledgeAmount, pledgeDate
- Responsibilities: Record donation details

### 5.2 DAO Layer

**EventDAO.java**
- Methods: addEvent, getEventById, getAllEvents, updateCollectedAmount, deleteEvent
- Responsibility: All Event-related database operations
- Uses PreparedStatement for SQL injection prevention

**DonorDAO.java**
- Methods: addDonor, getDonorById, getAllDonors, getDonorByName, deleteDonor
- Responsibility: All Donor-related database operations
- Supports donor search by name

**PledgeDAO.java**
- Methods: addPledge, getPledgeById, getPledgesByEvent, getPledgesByDonor, getTotalPledgedForEvent
- Responsibility: All Pledge-related database operations
- Complex queries for donation analysis

### 5.3 Service Layer (Facade Pattern)

**EventService.java**
- Facades EventDAO
- Adds business logic: validation, error handling
- Methods: createEvent, getEvent, updateCollectedAmount, deleteEvent

**DonorService.java**
- Facades DonorDAO
- Adds validation: email format verification
- Methods: registerDonor, getDonor, findDonorByName, deleteDonor

**PledgeService.java**
- Facades PledgeDAO and EventDAO
- Coordinates donation process
- Triggers observer notifications
- Methods: makeDonation, getPledgesForEvent, getTotalPledgedForEvent

**ServiceFactory.java**
- Creates and caches Service instances
- Implements Factory Method pattern
- Provides centralized service access

### 5.4 Observer Pattern

**DonationObserver.java (Interface)**
- Defines observer contract
- Methods: onDonationReceived, onGoalReached, onProgressUpdated

**EventProgressTracker.java (Concrete Observer)**
- Implements observer notifications
- Displays donation updates and progress
- Shows achievement messages

**DonationEventManager.java (Observable)**
- Manages observer list
- Implements Singleton pattern
- Notifies observers of events

### 5.5 MVC Components

**ConsoleView.java**
- Handles all UI display logic
- Methods: displayMainMenu, displayAllEvents, inputEventName, etc.
- Separate from business logic

**CharityTrackerController.java**
- Orchestrates MVC flow
- Handles user request routing
- Calls services and updates view
- Registers observersfor notifications

---

## 6. ACTIVITY & STATE DIAGRAMS

### 6.1 Activity Diagrams

**Create Event Activity:**
- User Input → Validation → Service Call → DAO Operation → Confirmation

**Make Donation Activity:**
- User Input → Donor/Event Verification → Pledge Creation → DB Update → Observer Notification → Confirmation

### 6.2 State Diagrams

**Event States:**
- Created → Active (first donation) → Completed (goal reached)

**Pledge States:**
- Pending → Recorded → Confirmed → Notified

**System States:**
- Idle → ProcessingInput → Idle
- Error states for validation failures

---

## 7. DESIGN PRINCIPLES (SOLID)

### 7.1 Single Responsibility Principle (SRP)

**Implementation:**
- Each class has a single, well-defined responsibility
- EventService handles event logic only
- EventDAO handles database access only
- ConsoleView handles UI only

**Example:**
```
❌ BEFORE: Main.java handled UI + business logic + database
✓ AFTER:  
  - ConsoleView: UI only
  - EventService: Business logic only
  - EventDAO: Database only
```

**Benefit:**
- Easy to test and maintain
- Changes in UI don't affect business logic
- Reduced code coupling

### 7.2 Open/Closed Principle (OCP)

**Implementation:**
- Services are open for extension (new business rules)
- Closed for modification (interfaces define contracts)
- Observer pattern allows adding new observers without modifying existing code

**Example:**
```java
// OPEN for EXTENSION
public class NewCustomObserver implements DonationObserver {
    // Add new observer behavior
}

// No modification needed to DonationEventManager!
eventManager.subscribe(new NewCustomObserver());
```

**Benefit:**
- New features without modifying existing code
- Reduced risk of breaking existing functionality
- Better maintainability

### 7.3 Liskov Substitution Principle (LSP)

**Implementation:**
- All DAOs have consistent interfaces
- All Services follow same pattern
- Observers are interchangeable

**Example:**
```java
// LSP: Any observer can replace another
DonationObserver tracker = new EventProgressTracker("System");
DonationObserver notifier = new EmailNotifier();
// Both work identically from manager's perspective
```

**Benefit:**
- Flexible component replacement
- Extensible design
- Polymorphic behavior

### 7.4 Dependency Inversion Principle (DIP)

**Implementation:**
- High-level modules (Controller) depend on abstractions (ServiceFactory)
- Not on low-level details (DAOs)
- ServiceFactory abstracts service creation

**Example:**
```java
// DEPENDS on ServiceFactory (abstraction)
// NOT on EventService directly
EventService service = ServiceFactory.getEventService();
```

**Benefit:**
- Loose coupling between layers
- Easy to swap implementations
- Better testability (can mock services)

### 7.5 Interface Segregation Principle (ISP)

**Implementation:**
- DonationObserver interface has specific methods
- Clients only depend on methods they use
- Services have focused interfaces

**Benefit:**
- Smaller, more manageable interfaces
- Clients not forced to implement unused methods
- Cleaner code contracts

---

## 8. DESIGN PATTERNS

### 8.1 Singleton Pattern

**Location:** DBConnection.java, DonationEventManager.java

**Purpose:** Ensure only one instance exists

**Implementation:**
```java
private static DBConnection instance;

public static synchronized DBConnection getInstance() {
    if (instance == null) {
        instance = new DBConnection();
    }
    return instance;
}
```

**Benefits:**
- Centralized resource management
- Thread-safe access
- Reduced memory overhead

### 8.2 Facade Pattern

**Location:** Service layer (EventService, DonorService, PledgeService)

**Purpose:** Simplify complex DAO interactions

**Implementation:**
```java
public class EventService {
    private EventDAO eventDAO;
    // Simplifies:
    public boolean createEvent(...) {
        // Validation
        // Business logic
        // DAO call
    }
}
```

**Benefits:**
- Hides complexity
- Centralized validation
- Decouples view from DAO

### 8.3 Factory Pattern

**Location:** ServiceFactory.java

**Purpose:** Centralized creation of service objects

**Implementation:**
```java
public static EventService getEventService() {
    if (eventService == null) {
        eventService = new EventService();
    }
    return eventService;
}
```

**Benefits:**
- Centralized object creation
- Easy to add caching
- Consistent service instantiation

### 8.4 Observer Pattern

**Location:** DonationObserver.java, DonationEventManager.java, EventProgressTracker.java

**Purpose:** Notify multiple observers of events

**Implementation:**
```java
public void subscribe(DonationObserver observer) {
    observers.add(observer);
}

public void notifyDonationReceived(Pledge pledge, Event event) {
    for (DonationObserver observer : observers) {
        observer.onDonationReceived(pledge, event);
    }
}
```

**Benefits:**
- Loose coupling between components
- Dynamic observer registration
- Multiple subscribers to events

### 8.5 MVC Pattern

**Location:** Main, CharityTrackerController, ConsoleView, Services

**Purpose:** Separate presentation, business logic, and data

**Benefits:**
- Clear separation of concerns
- Testable components
- Easy to change UI without affecting logic

---

## 9. MVC ARCHITECTURE

### 9.1 Model Layer

**Responsibility:** Represent data entities

**Components:**
- Event, Donor, Pledge (POJO classes)
- No business logic
- Only data storage and getters/setters

### 9.2 View Layer

**Responsibility:** Render output and capture user input

**Components:**
- ConsoleView.java
- Menu displays
- Input methods
- Error/success messages

**Key Methods:**
```java
displayMainMenu()
inputEventName()
displayAllEvents()
inputDonationAmount()
```

### 9.3 Controller Layer

**Responsibility:** Orchestrate user requests and coordinate View/Model/Service

**Components:**
- CharityTrackerController.java
- Request routing
- Service calling
- View updates

**Flow:**
```
User Input (View) → Controller → Service → DAO → Database → Service → View Display
```

**Key Methods:**
```java
run() // Main loop
handleCreateEvent()
handleMakeDonation()
handleViewAllEvents()
```

### 9.4 MVC Data Flow

```
┌─────────────────┐
│   User Input    │
│   (Console)     │
└────────┬────────┘
         │
         ▼
┌─────────────────────────────────┐
│   ConsoleView (View)            │
│ - Capture input                 │
│ - Display menus                 │
└────────┬────────────────────────┘
         │
         ▼
┌──────────────────────────────────────┐
│ CharityTrackerController (Controller)│
│ - Route requests                     │
│ - Call appropriate service           │
│ - Call view methods                  │
└────────┬─────────────────────────────┘
         │
         ▼
┌──────────────────────────────────────┐
│ Services (Business Logic)            │
│ - Validate inputs                    │
│ - Execute business logic             │
│ - Call DAOs                          │
│ - Trigger observers                  │
└────────┬─────────────────────────────┘
         │
         ▼
┌──────────────────────────────────────┐
│ DAO Layer (Data Access)              │
│ - Execute SQL queries                │
│ - Return data to service             │
└────────┬─────────────────────────────┘
         │
         ▼
┌──────────────────────────────────────┐
│ MySQL Database                       │
│ - Persist data                       │
└──────────────────────────────────────┘
```

---

## 10. IMPLEMENTATION DETAILS

### 10.1 Database Schema

**events Table**
```sql
CREATE TABLE events (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    target_amount DOUBLE NOT NULL,
    collected_amount DOUBLE DEFAULT 0,
    UNIQUE INDEX idx_name (name)
);
```

**donors Table**
```sql
CREATE TABLE donors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone_number VARCHAR(15),
    UNIQUE INDEX idx_email (email)
);
```

**pledges Table**
```sql
CREATE TABLE pledges (
    id INT AUTO_INCREMENT PRIMARY KEY,
    donor_id INT NOT NULL,
    event_id INT NOT NULL,
    amount DOUBLE NOT NULL,
    pledge_date DATE NOT NULL,
    FOREIGN KEY (donor_id) REFERENCES donors(id) ON DELETE CASCADE,
    FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE,
    INDEX idx_donor (donor_id),
    INDEX idx_event (event_id)
);
```

### 10.2 Key Technologies

| Technology | Purpose |
|-----------|---------|
| Java | Programming language |
| MySQL | Data persistence |
| JDBC | Database connectivity |
| PreparedStatement | SQL injection prevention |
| Scanner | User input |

### 10.3 Security Features

1. **SQL Injection Prevention:** All queries use PreparedStatement
2. **Input Validation:** All inputs validated in service layer
3. **Email Format Validation:** Regex pattern matching
4. **Amount Validation:** Positive values only
5. **Referential Integrity:** Foreign key constraints

### 10.4 Error Handling

- Try-catch blocks in DAO operations
- Input validation in service layer
- User-friendly error messages in view
- Graceful database connection closing

---

## 11. CONCLUSION

### 11.1 Achievements

✓ **OOAD Principles:** Demonstrates clear OOAD methodology with layered architecture

✓ **SOLID Principles:** All five SOLID principles implemented

✓ **Design Patterns:** Four design patterns (Singleton, Factory, Facade, Observer)

✓ **MVC Architecture:** Clean separation between Model, View, and Controller

✓ **UML Modeling:** Complete use case, class, activity, and state diagrams

✓ **Database Integration:** JDBC with MySQL for data persistence

✓ **Code Quality:** Well-commented, organized, follows conventions

### 11.2 Learning Outcomes

1. **Object-Oriented Design:** Understanding of classes, inheritance, polymorphism
2. **Design Patterns:** Practical implementation of industry-standard patterns
3. **Layered Architecture:** Benefits of separation of concerns
4. **Database Design:** SQL schema design and JDBC programming
5. **SOLID Principles:** Writing maintainable, extensible code

### 11.3 Future Enhancements

- [ ] Add web UI (Spring Boot + Angular)
- [ ] Add authentication and role-based access
- [ ] Add email notifications
- [ ] Add data export (PDF/CSV)
- [ ] Add search and filter functionality
- [ ] Add performance optimization with indexes
- [ ] Add transaction support for multi-step operations
- [ ] Add REST API for external integrations

### 11.4 References

- Design Patterns: Elements of Reusable Object-Oriented Software (Gang of Four)
- Clean Code: A Handbook of Agile Software Craftsmanship
- UML Distilled: A Brief Guide to the Standard Object Modeling Language
- SOLID Principles: Wikipedia and Martin's Clean Architecture

---

**Project Completion Date:** April 2026
**Student:** [Your Name]
**Institution:** [Your College/University]
**Course:** Object-Oriented Analysis & Design (OOAD)

---

## APPENDIX: How to Run

### Prerequisites
- Java JDK 8+
- MySQL Server
- mysql-connector-j JAR driver

### Compilation
```bash
javac -cp ".;lib/mysql-connector-j-9.6.0.jar" -d bin \
    src/Main.java \
    src/model/*.java \
    src/dao/*.java \
    src/db/*.java \
    src/service/*.java \
    src/controller/*.java \
    src/view/*.java \
    src/observer/*.java
```

### Execution
```bash
java -cp "bin;lib/mysql-connector-j-9.6.0.jar" Main
```

### Sample Data Entry
1. Create Event: "Clean Water Initiative" - Target: 50000
2. Register Donor: "John Doe" - john@example.com
3. Make Donation: Donor 1 → Event 1 → Amount 5000
4. View Progress: See event progress and donation details

---

**END OF REPORT**
