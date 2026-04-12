# Charity Event Fundraising Tracker

A simple, modular Java project demonstrating Object-Oriented Programming (OOP) concepts including encapsulation, abstraction, and collection management.

## Project Structure

```
schmss/
├── src/
│   ├── Event.java                 # Event class
│   ├── Donor.java                 # Donor class
│   ├── Pledge.java                # Pledge/Donation class
│   ├── FundraisingSystem.java      # Manager class (Business Logic)
│   └── Main.java                  # Menu-driven interface
├── bin/                           # Compiled .class files
├── README.md                      # This file
└── compile.bat                    # Batch script for Windows compilation
```

## Classes Overview

### 1. **Event.java**
Represents a charity event with the following properties:
- `eventId`: Unique identifier (auto-generated)
- `eventName`: Name of the event
- `targetAmount`: Fundraising goal
- `description`: Event description
- `collectedAmount`: Total funds collected

**Key Methods:**
- `addFunds()`: Add donations to the event
- `getProgressPercentage()`: Calculate progress towards target
- `displayEventDetails()`: Show event information

### 2. **Donor.java**
Represents a person who can donate to events:
- `donorId`: Unique identifier (auto-generated)
- `donorName`: Name of donor
- `email`: Email address
- `phoneNumber`: Phone number

**Key Methods:**
- Getters and Setters for all properties
- `displayDonorDetails()`: Show donor information

### 3. **Pledge.java**
Represents a donation/pledge from a donor to an event:
- `pledgeId`: Unique identifier (auto-generated)
- `donorId`: Reference to donor
- `eventId`: Reference to event
- `pledgeAmount`: Donation amount
- `pledgeDate`: Date of donation

**Key Methods:**
- Getters and Setters for all properties
- `displayPledgeDetails()`: Show pledge information

### 4. **FundraisingSystem.java** (Manager Class)
Central manager class that handles all business logic:

**Collections Used:**
- `ArrayList<Event>` - Store all events
- `ArrayList<Donor>` - Store all donors
- `ArrayList<Pledge>` - Store all pledges

**Key Methods:**
- Event Management: `addEvent()`, `getEventById()`, `displayAllEvents()`
- Donor Management: `addDonor()`, `getDonorById()`, `displayAllDonors()`
- Pledge Management: `makePledge()`, `getPledgesForEvent()`, `displayDonorsForEvent()`
- Validation: Input validation for all operations

### 5. **Main.java**
Provides a user-friendly, menu-driven interface:

**Menu Options:**
1. Create New Event
2. Register Donor
3. Make Donation to Event
4. View All Events
5. View Donors of Specific Event
6. View All Donors
7. View Statistics
8. Exit

## OOP Concepts Implemented

✅ **Encapsulation**
- Private fields with public getters/setters
- Data hiding and controlled access

✅ **Abstraction**
- Complex logic hidden in FundraisingSystem
- User interacts through simple Main interface

✅ **Modularity**
- Each class has single responsibility
- Classes are independent and reusable

✅ **Collections**
- ArrayList for dynamic storage
- Flexible size management

✅ **Validation**
- Input validation (positive amounts)
- Existence checking (donor/event)
- User-friendly error messages

## How to Compile and Run

### Option 1: Using Batch Script (Windows)
```
1. Navigate to the project folder: cd c:\Users\Dipak\Desktop\OOAD\schmss
2. Run: compile.bat
3. The program will compile and run automatically
```

### Option 2: Manual Compilation (Windows Command Prompt)
```bash
# Navigate to project directory
cd c:\Users\Dipak\Desktop\OOAD\schmss

# Compile all Java files
javac -d bin src/*.java

# Run the program
java -cp bin Main
```

### Option 3: Using PowerShell
```powershell
# Navigate to project directory
cd c:\Users\Dipak\Desktop\OOAD\schmss

# Compile all Java files
javac -d bin src\*.java

# Run the program
java -cp bin Main
```

## Sample Usage Workflow

### Step 1: Create Events
```
Enter: 1 (Create New Event)
Event Name: AIDS Awareness 2026
Target Amount: 50000
Description: Fundraiser for AIDS awareness and prevention
Result: Event EVT1 created
```

### Step 2: Register Donors
```
Enter: 2 (Register Donor)
Donor Name: Raj Kumar
Email: raj@email.com
Phone: 9876543210
Result: Donor DOR1 registered
```

### Step 3: Make Donations
```
Enter: 3 (Make Donation)
Donor ID: DOR1
Event ID: EVT1
Amount: 5000
Result: Donation recorded, Pledge ID: PLD1
```

### Step 4: View Events
```
Enter: 4 (View All Events)
Shows all events with:
- Event name and ID
- Target and collected amounts
- Progress percentage
- Remaining amount needed
```

### Step 5: View Event Donors
```
Enter: 5 (View Donors for Event)
Event ID: EVT1
Shows all donors who contributed to the event
```

## Features

✅ **Create Events**: Define fundraising goals and descriptions
✅ **Register Donors**: Manage donor information
✅ **Make Donations**: Track pledges with automatic date recording
✅ **Track Progress**: Real-time calculation of achievement percentage
✅ **View Reports**: Multiple views of events, donors, and pledges
✅ **Validation**: Prevents invalid data entry
✅ **In-Memory Storage**: No database required
✅ **User-Friendly Interface**: Menu-driven CLI

## Technical Details

- **Language**: Java 8+
- **Data Structures**: ArrayList
- **Date Format**: YYYY-MM-DD (auto-generated)
- **ID Format**: 
  - Events: EVT1, EVT2, EVT3...
  - Donors: DOR1, DOR2, DOR3...
  - Pledges: PLD1, PLD2, PLD3...

## Key Validations

| Validation | Rule |
|------------|------|
| Event Name | Cannot be empty |
| Target Amount | Must be positive |
| Donor Name | Cannot be empty |
| Donation Amount | Must be positive |
| Donor/Event Check | Must exist in system |

## Extensibility

This project can be extended with:
- File I/O for data persistence
- Database integration
- Web UI using Spring Boot
- Advanced reporting features
- Email notifications
- Export to PDF/Excel

## Learning Outcomes

This project demonstrates:
- How to design classes with responsibilities
- Proper use of encapsulation and access modifiers
- Managing collections with ArrayList
- Input validation and error handling
- Menu-driven console application design
- Real-world business logic modeling

## Notes

- All IDs are auto-generated
- Progress percentage is calculated in real-time
- Donations are recorded with the current date
- No external dependencies required
- Beginner-friendly code with comments

Enjoy learning OOP with the Charity Event Fundraising Tracker! 🎯
