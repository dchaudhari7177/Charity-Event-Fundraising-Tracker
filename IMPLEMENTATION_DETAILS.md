# Implementation Details & Code Walkthrough

## 📝 Project Summary

**Project Name:** Charity Event Fundraising Tracker  
**Purpose:** Learn Object-Oriented Programming through practical application  
**Language:** Java  
**Lines of Code:** ~700 lines  
**Classes:** 5  
**Compilation Status:** ✅ Successfully compiled

## 🏗️ Architecture Overview

### Design Pattern: Manager Pattern
- Individual classes handle data (Event, Donor, Pledge)
- Central manager handles business logic (FundraisingSystem)
- Clean separation of concerns

### Encapsulation Implementation

```java
// Example from Event.java
public class Event {
    private String eventId;           // PRIVATE - hidden from outside
    private String eventName;         // PRIVATE - hidden from outside
    private double collectedAmount;   // PRIVATE - hidden from outside
    
    // PUBLIC GETTERS - controlled read access
    public String getEventId() {
        return eventId;
    }
    
    // PUBLIC SETTERS - controlled write access with validation
    public void setTargetAmount(double targetAmount) {
        if (targetAmount > 0) {
            this.targetAmount = targetAmount;
        } else {
            System.out.println("Target amount must be positive!");
        }
    }
}
```

**Why This Matters:**
- Data is protected from invalid states
- Changes can be made to internal implementation without affecting users
- Validation happens in one place

## 📚 Detailed Class Descriptions

### 1. Event.java (Model Class)

**Responsibilities:**
- Store event information
- Track collected donations
- Calculate progress percentage

**Key Attributes:**
```
eventId        : String      → EVT1, EVT2, EVT3...
eventName      : String      → "Medical Camp", "Education Fund"
targetAmount   : double      → Target fundraising goal
description    : String      → Event details
collectedAmount: double      → Money collected so far (0 initially)
```

**Important Methods:**
```java
addFunds(double amount)
// Purpose: Add donation to event
// Validation: amount > 0
// Updates: collectedAmount

getProgressPercentage(): double
// Purpose: Calculate progress towards goal
// Formula: (collectedAmount / targetAmount) * 100
// Returns: 0-100 (percentage)
```

**Flow Example:**
```
1. Event created: Medical Camp, Target = 100,000, Collected = 0
2. Donation of 10,000 → addFunds(10000) → Collected = 10,000
3. Progress = (10,000 / 100,000) * 100 = 10%
4. Another donation of 15,000 → Collected = 25,000, Progress = 25%
```

### 2. Donor.java (Model Class)

**Responsibilities:**
- Store donor information
- Maintain contact details

**Key Attributes:**
```
donorId      : String  → DOR1, DOR2, DOR3...
donorName    : String  → Donor's name
email        : String  → Contact email
phoneNumber  : String  → Contact phone
```

**Design Choices:**
- Phone number stored as String (not int) to preserve leading zeros
- All fields immutable after creation (no setDonorId, setChangeId)
- Only mutable: name, email, phone (updatable info)

### 3. Pledge.java (Transaction Class)

**Responsibilities:**
- Record a donation transaction
- Create audit trail

**Key Attributes:**
```
pledgeId    : String  → PLD1, PLD2, PLD3...
donorId     : String  → Foreign key to Donor
eventId     : String  → Foreign key to Event
pledgeAmount: double  → Donation amount
pledgeDate  : String  → Date (YYYY-MM-DD format)
```

**Why Separate Class:**
- Normalization: Avoids storing same pledge data in multiple places
- Audit Trail: Can query all donations to an event
- Flexibility: Can extend with payment status, receipts, etc.

### 4. FundraisingSystem.java (Manager/Controller Class)

**Responsibilities:**
- Manage collections of Events, Donors, Pledges
- Implement business logic
- Validate inputs before processing
- Coordinate between classes

**Key Attributes:**
```java
ArrayList<Event> events;      // All events in system
ArrayList<Donor> donors;      // All registered donors
ArrayList<Pledge> pledges;    // All donations recorded
int eventCounter;             // For auto-generating EVT1, EVT2...
int donorCounter;             // For auto-generating DOR1, DOR2...
int pledgeCounter;            // For auto-generating PLD1, PLD2...
```

**Major Methods:**

```java
// EVENT MANAGEMENT
addEvent(String name, double target, String desc): boolean
├─ Validate: name ≠ empty, target > 0
├─ Generate: eventId = "EVT" + eventCounter++
├─ Create: new Event(eventId, name, target, desc)
└─ Store: events.add(event)

getEventById(String eventId): Event
├─ Search: Loop through events ArrayList
└─ Return: Event object or null

displayAllEvents(): void
├─ Check: if events.isEmpty() return
└─ Display: All events with progress info


// DONOR MANAGEMENT
addDonor(String name, String email, String phone): boolean
├─ Validate: name ≠ empty
├─ Generate: donorId = "DOR" + donorCounter++
├─ Create: new Donor(donorId, name, email, phone)
└─ Store: donors.add(donor)

getDonorById(String donorId): Donor
├─ Search: Loop through donors ArrayList
└─ Return: Donor object or null


// PLEDGE MANAGEMENT
makePledge(String donorId, String eventId, double amount): boolean
├─ Validate:
│  ├─ amount > 0 ❌ if not, return false
│  ├─ getDonorById(donorId) ❌ if null, return false
│  └─ getEventById(eventId) ❌ if null, return false
├─ Generate: pledgeId = "PLD" + pledgeCounter++
├─ Get: Current date with LocalDate.now()
├─ Create: new Pledge(pledgeId, donorId, eventId, amount, date)
├─ Store: pledges.add(pledge)
├─ Update: event.addFunds(amount) ← IMPORTANT
└─ Return: true (success)

displayDonorsForEvent(String eventId): void
├─ Get: Event by eventId
├─ Find: All pledges for this eventId
├─ Loop: For each pledge, find corresponding donor
└─ Display: Donor name, amount, date
```

**Key Design Pattern:**
```
User Input (Main)
        ↓
   FundraisingSystem
   ├─ Validate input
   ├─ Check business rules
   ├─ Update models
   ├─ Update relationships
   └─ Success/Error message
        ↓
    Back to User
```

### 5. Main.java (View/Controller Class)

**Responsibilities:**
- Display menus
- Get user input
- Call FundraisingSystem methods
- Display results

**Architecture:**
```
┌─────────────────────────┐
│    main() Loop Start    │
└────────────┬────────────┘
             │
      ┌──────▼──────┐
      │ Display Menu│
      └──────┬──────┘
             │
      ┌──────▼──────────────────┐
      │ Get User Choice (1-8)   │
      └──────┬─────────────────┐
             │                 │
             ▼                 ▼
    ┌─────────────┐    ┌──────────┐
    │ Choice 1-7: │    │Choice 8: │
    │Call Function│    │ Exit Loop│
    └─────────────┘    └──────────┘
             │
      ┌──────▼───────────────────┐
      │ Call System Function     │
      └──────┬───────────────────┘
             │
      ┌──────▼─────────────────────┐
      │ Display Result/Error       │
      └──────┬─────────────────────┘
             │
      ┌──────▼──────────────────┐
      │ Loop back to Menu       │
      └─────────────────────────┘
```

**Input Handling:**
```java
private static int getIntInput() {
    try {
        return scanner.nextInt();
    } catch (Exception e) {
        scanner.nextLine();  // Clear buffer if invalid
        return -1;           // Return -1 for invalid input
    }
}
// This prevents program crash from invalid number input
```

## 🔄 Data Flow Examples

### Scenario 1: Creating and Donating to an Event

```
Step 1: User chooses "Create Event"
        Main.createEvent()
        ├─ Asks for name, amount, description
        ├─ Calls system.addEvent(name, amount, desc)
        └─ FundraisingSystem:
           ├─ Validates inputs
           ├─ Generates eventId = "EVT1"
           ├─ Creates Event object
           ├─ Adds to events ArrayList
           └─ Prints success message

Step 2: User chooses "Make Donation"
        Main.makeDonation()
        ├─ Asks for donorId, eventId, amount
        ├─ Calls system.makePledge(donorId, eventId, amount)
        └─ FundraisingSystem:
           ├─ Validates: amount > 0
           ├─ Validates: donor exists
           ├─ Validates: event exists
           ├─ Creates Pledge object
           ├─ Adds to pledges ArrayList
           ├─ Calls event.addFunds(amount)
           │  └─ Event: collectedAmount += amount
           └─ Prints success message

Result: Event.collectedAmount updated, progress changed
```

### Scenario 2: Viewing Event Donors

```
User chooses "View Donors for Event"
        Main.viewDonorsForEvent()
        ├─ Asks for eventId
        └─ Calls system.displayDonorsForEvent(eventId)
           └─ FundraisingSystem:
              ├─ Gets Event by eventId
              ├─ Finds all pledges for this eventId:
              │  └─ Loop: for (Pledge p : pledges)
              │     if (p.getEventId().equals(eventId))
              ├─ For each pledge:
              │  ├─ Get Donor from donors ArrayList
              │  ├─ Display donor name and amount
              │  └─ Sum total
              └─ Print: Donor list with total
```

## 🎯 Validation Strategy

### Layer 1: Input Validation (Main)
- Check for invalid menu choices
- Try-catch for number parsing

### Layer 2: Business Logic Validation (FundraisingSystem)
- Check for positive amounts
- Verify existence of donors/events
- Check for empty strings

### Layer 3: Setter Validation (Individual Classes)
- Event: Reject negative targetAmount
- Pledge: Reject negative pledgeAmount

**Benefits:**
- Multiple layers catch errors
- Clear error messages
- Graceful failure (program doesn't crash)

## 🔑 Key Algorithms

### 1. Progress Calculation
```java
public double getProgressPercentage() {
    if (targetAmount == 0) {     // Safety check
        return 0;
    }
    return (collectedAmount / targetAmount) * 100;
}

// Example:
// Target: 100000
// Collected: 35000
// Progress: (35000 / 100000) * 100 = 35%
```

### 2. Finding Event by ID
```java
public Event getEventById(String eventId) {
    for (Event event : events) {           // Loop through ArrayList
        if (event.getEventId().equals(eventId)) {  // Compare IDs
            return event;                  // Found it
        }
    }
    return null;                           // Not found
}

// Time Complexity: O(n) where n = number of events
// Space Complexity: O(1)
```

### 3. Finding All Pledges for an Event
```java
public ArrayList<Pledge> getPledgesForEvent(String eventId) {
    ArrayList<Pledge> eventPledges = new ArrayList<>();
    for (Pledge pledge : pledges) {                    // Loop all pledges
        if (pledge.getEventId().equals(eventId)) {
            eventPledges.add(pledge);                  // Add to result
        }
    }
    return eventPledges;
}

// Creates new ArrayList with filtered results
// Original pledges ArrayList unchanged
```

## 📊 Collections Usage

### ArrayList vs Array

```
Array:
│ Size:        Fixed at creation
│ Growth:      Cannot grow
│ Usage:       Simple, fixed collections

ArrayList:
│ Size:        Dynamic, grows as needed
│ Growth:      Auto-resizes internally
│ Usage:       Perfect for unknown quantity
│ Operations:  add(), get(), remove(), etc.
```

**Why ArrayList for This Project:**
```
events ArrayList
├─ Don't know how many events initially
├─ Can add continuously
├─ Can search, iterate, display
└─ More convenient than managing array size
```

## 🎨 Code Quality Metrics

| Aspect | Implementation |
|--------|-----------------|
| Readability | Clear variable names, comments |
| Maintainability | Each class has single responsibility |
| Testability | Methods can be tested independently |
| Scalability | Can add features without changing core |
| Error Handling | Validation at multiple levels |
| Documentation | Comprehensive comments |

## 🚀 Performance Characteristics

| Operation | Time | Space | Notes |
|-----------|------|-------|-------|
| Add Event | O(1) | O(1) | ArrayList.add() is constant |
| Search Event | O(n) | O(1) | Linear search through list |
| Add Pledge | O(1) | O(1) | Validation then add |
| Get Event Pledges | O(n) | O(m) | n = pledges, m = result size |
| Display All Events | O(n) | O(1) | Where n = number of events |

*Current implementation fine for 100-1000s of records. For millions, would need database.*

## 🔮 Extensibility Points

### Easy to Add:

1. **Pledge Status:**
   ```java
   public class Pledge {
       private String status;  // "pending", "completed", "cancelled"
   }
   ```

2. **Event Categories:**
   ```java
   public class Event {
       private String category;  // "medical", "education", "environment"
   }
   ```

3. **Donor Pledge History:**
   ```java
   // Add method to FundraisingSystem
   public ArrayList<Pledge> getPledgesByDonor(String donorId)
   ```

4. **Export to File:**
   ```java
   public void exportEventReport(String eventId, String filename)
   ```

## 📋 Summary

**What This Project Teaches:**
✅ Class design and responsibility
✅ Encapsulation and access control
✅ ArrayList and collections
✅ Method organization
✅ Input validation
✅ Business logic implementation
✅ User interface design
✅ Error handling

**Code Metrics:**
- Total Lines: ~700
- Code Lines: ~650 (excluding comments)
- Comments: ~50
- Comment Ratio: 7%
- Average Method Length: 8 lines

**Compilation & Execution:**
- Compile: `javac -d bin src\*.java`
- Run: `java -cp bin Main`
- Classes Generated: 5
- Status: ✅ Ready to use

This complete, modular implementation provides an excellent foundation for learning OOP! 🎓
