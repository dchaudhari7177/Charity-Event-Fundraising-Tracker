# Quick Start Guide

## 📦 Project Overview

Charity Event Fundraising Tracker is a beginner-friendly Java project that demonstrates core Object-Oriented Programming (OOP) concepts through a practical real-world scenario.

## 🚀 Quick Start (5 Minutes)

### Windows (Recommended)

#### Option 1: Using Batch Script (Easiest)
```
1. Open Command Prompt
2. Navigate to: cd c:\Users\Dipak\Desktop\OOAD\schmss
3. Run: compile.bat
4. Program will auto-compile and run!
```

#### Option 2: Using PowerShell
```powershell
cd c:\Users\Dipak\Desktop\OOAD\schmss
javac -d bin src\*.java
java -cp bin Main
```

### Linux/Mac

```bash
cd /path/to/schmss
chmod +x compile.sh
./compile.sh
```

## 📂 Project Structure

```
schmss/
├── src/                      # Source files
│   ├── Event.java           # Event class
│   ├── Donor.java           # Donor class
│   ├── Pledge.java          # Pledge/Donation class
│   ├── FundraisingSystem.java # Manager class
│   └── Main.java            # Menu interface
├── bin/                     # Compiled files (auto-created)
├── README.md               # Full documentation
├── CLASS_DIAGRAM.md        # Architecture & design
├── SAMPLE_USAGE.md         # Examples & test cases
├── QUICK_START.md          # This file
├── compile.bat             # Windows batch script
└── compile.sh              # Linux/Mac shell script
```

## 🎯 First Test Run

### Sample Session (Copy-Paste)

```
When asked for menu choice:
1. Press: 1
   Event Name: Medical Camp
   Target Amount: 50000
   Description: Free medical services

2. Press: 2
   Donor Name: John Doe
   Email: john@example.com
   Phone: 9876543210

3. Press: 3
   Donor ID: DOR1
   Event ID: EVT1
   Amount: 5000

4. Press: 4
   (View all events)

5. Press: 8
   (Exit)
```

## 🔑 Key Features

| Feature | Description |
|---------|-------------|
| **Create Events** | Set up fundraising goals with target amounts |
| **Register Donors** | Maintain donor contact information |
| **Track Donations** | Record pledges with automatic date stamping |
| **View Progress** | See percentage of target achieved |
| **List Donors** | View who donated to each event |
| **Statistics** | Quick overview of system status |
| **Validation** | Input checking for data integrity |
| **Auto IDs** | Automatic unique ID generation |

## 💡 OOP Concepts Demonstrated

### 1. **Encapsulation**
```java
private String eventName;    // Private field
public String getEventName() { // Public getter
    return eventName;
}
```

### 2. **Single Responsibility**
- Event: Manages event data
- Donor: Manages donor data
- Pledge: Manages donation data
- FundraisingSystem: Coordinates logic
- Main: Handles user interface

### 3. **Collections (ArrayList)**
```java
private ArrayList<Event> events;
private ArrayList<Donor> donors;
private ArrayList<Pledge> pledges;
```

### 4. **Validation**
```java
if (targetAmount <= 0) {
    System.out.println("Target amount must be positive!");
    return false;
}
```

## 📋 Menu System

```
Main Menu:
1. Create New Event        ─→ Add a new charity event
2. Register Donor          ─→ Add a new donor
3. Make Donation           ─→ Record a pledge/donation
4. View All Events         ─→ See all events with progress
5. View Donors of Event    ─→ See who donated to an event
6. View All Donors         ─→ See complete donor list
7. View Statistics         ─→ System overview
8. Exit                    ─→ Close application
```

## 🔍 Understanding the Code

### File: Event.java (128 lines)
**Purpose:** Represents a charity event
- **Properties:** name, target, collected amount
- **Key Method:** `getProgressPercentage()` - calculates fundraising progress
- **Validation:** Prevents negative amounts

### File: Donor.java (82 lines)
**Purpose:** Represents a donor
- **Properties:** name, email, phone
- **Provides:** Getters/setters for all fields

### File: Pledge.java (88 lines)
**Purpose:** Represents a donation record
- **Properties:** donor ID, event ID, amount, date
- **Purpose:** Creates audit trail of donations

### File: FundraisingSystem.java (216 lines)
**Purpose:** Manager class - handles all business logic
- **Collections:** Manages events, donors, pledges (ArrayLists)
- **Methods:** 20+ methods for CRUD operations
- **Validation:** Checks all user inputs

### File: Main.java (196 lines)
**Purpose:** User interface with menu system
- **Interaction:** Menu-driven console interface
- **Flow:** Loops until user exits

## 🧪 Test the System

### Test 1: Create Event
```
Menu: 1
Name: Blood Donation Drive
Amount: 30000
Description: Community blood donation campaign
Expected: Event created (EVT1)
```

### Test 2: Register Donor
```
Menu: 2
Name: Alice Smith
Email: alice@email.com
Phone: 9876543210
Expected: Donor created (DOR1)
```

### Test 3: Make Donation
```
Menu: 3
Donor ID: DOR1
Event ID: EVT1
Amount: 5000
Expected: Pledge created (PLD1), fund updated
```

### Test 4: Error Handling
```
Menu: 3
Donor ID: DOR99 (non-existent)
Expected: "Donor not found!"
```

## 📊 Real-World Scenario

**Imagine you're building this for a real NGO:**

1. **Day 1:** Create 3 charity events
2. **Day 2:** Register 5 donors
3. **Day 3-7:** Process donations daily
4. **End of Month:** View progress on each event

**System Output After Month:**
```
Event 1: Medical Camp
- Target: 100,000
- Collected: 78,500
- Progress: 78.5%
- Donors: 12
- Avg Donation: 6,541
```

## 🎓 Learning Path

1. **Start Here:** Read this Quick Start Guide
2. **Run Program:** Compile and run with test data
3. **Explore Code:** Read Event.java, understand getters/setters
4. **Study Logic:** Review FundraisingSystem.java
5. **Modify:** Try adding new features
6. **Reference:** Check CLASS_DIAGRAM.md for architecture

## 🔧 Common Issues & Solutions

| Issue | Solution |
|-------|----------|
| "javac not found" | Install Java JDK (not JRE) |
| Compilation error | Check file encoding is UTF-8 |
| Path issues | Use absolute path or cd to project |
| "Main class not found" | Ensure bin directory has compiled .class files |

## 💻 Code Quality

✅ **Clean Code Principles:**
- Meaningful variable names
- Single responsibility per class
- Comprehensive comments  
- No code duplication
- Proper error messages

✅ **OOP Best Practices:**
- Private fields with public accessors
- Validation in setters
- Logical grouping of methods
- Clear separation of concerns

## 📈 Next Steps for Enhancement

Once comfortable with this project, you can add:
- File I/O (save/load data)
- Database integration (MySQL/SQLite)
- Advanced search and filtering
- Report generation (PDF/Excel)
- Email notifications
- Web interface (Spring Boot)
- Unit tests (JUnit)

## 🤝 Learning Resources

**OOP Concepts:**
- Encapsulation: Private fields with public methods
- Abstraction: Complex logic hidden in manager class
- Modularity: Each class is independent
- Collections: ArrayList for dynamic storage

**Java Features Used:**
- Access modifiers: private, public
- Collections: ArrayList, generics
- Date format: LocalDate, DateTimeFormatter
- Scanner: Console input
- String formatting: printf, String.format()

## ✨ Summary

This project provides:
- ✅ Practical OOP example
- ✅ Real-world scenario (charity fundraising)
- ✅ Modular, clean code
- ✅ Error handling & validation
- ✅ Easy to understand & modify
- ✅ Great learning material

**Total Lines of Code:** ~700 lines
**Classes:** 5 classes
**Complexity:** Beginner-friendly
**Time to Understand:** 2-3 hours

Start with the compile step, test with sample data, then explore the code! 🎯
