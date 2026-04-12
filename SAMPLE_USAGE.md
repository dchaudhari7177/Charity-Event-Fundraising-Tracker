# Sample Usage & Test Scenarios

## Scenario 1: Complete Workflow

### Step 1: Create Events
```
Choose: 1 (Create New Event)

--- CREATE NEW EVENT ---
Enter Event Name: Medical Camps for Rural Areas
Enter Target Amount (Rs.): 75000
Enter Event Description: Medical camps to provide free healthcare to rural villages

Output:
✓ Event 'Medical Camps for Rural Areas' created successfully with ID: EVT1
```

### Step 2: Create More Events
```
Choose: 1 (Create New Event)

--- CREATE NEW EVENT ---
Enter Event Name: Child Education Fund
Enter Target Amount (Rs.): 50000
Enter Event Description: Scholarship program for underprivileged children

Output:
✓ Event 'Child Education Fund' created successfully with ID: EVT2
```

### Step 3: Register Donors
```
Choose: 2 (Register Donor)

--- REGISTER NEW DONOR ---
Enter Donor Name: Amit Patel
Enter Email: amit.patel@company.com
Enter Phone Number: 9876543210

Output:
✓ Donor 'Amit Patel' registered successfully with ID: DOR1
```

### Step 4: Register More Donors
```
Choose: 2 (Register Donor)

--- REGISTER NEW DONOR ---
Enter Donor Name: Neha Sharma
Enter Email: neha.sharma@email.com
Enter Phone Number: 9765432109

Output:
✓ Donor 'Neha Sharma' registered successfully with ID: DOR2
```

### Step 5: Make First Donation
```
Choose: 3 (Make Donation to Event)

--- MAKE DONATION ---
Enter Donor ID (e.g., DOR1): DOR1
Enter Event ID (e.g., EVT1): EVT1
Enter Donation Amount (Rs.): 15000

Output:
✓ Donation of Rs. 15000 recorded successfully!
Pledge ID: PLD1
```

### Step 6: Make More Donations
```
Choose: 3 (Make Donation to Event)

--- MAKE DONATION ---
Enter Donor ID (e.g., DOR1): DOR2
Enter Event ID (e.g., EVT1): EVT1
Enter Donation Amount (Rs.): 20000

Output:
✓ Donation of Rs. 20000 recorded successfully!
Pledge ID: PLD2
```

### Step 7: Another Donation to Different Event
```
Choose: 3 (Make Donation to Event)

--- MAKE DONATION ---
Enter Donor ID (e.g., DOR1): DOR1
Enter Event ID (e.g., EVT1): EVT2
Enter Donation Amount (Rs.): 10000

Output:
✓ Donation of Rs. 10000 recorded successfully!
Pledge ID: PLD3
```

## Scenario 2: View All Events

```
Choose: 4 (View All Events)

Output:
========== ALL EVENTS ==========

ID: EVT1
Name: Medical Camps for Rural Areas
Description: Medical camps to provide free healthcare to rural villages
Target: Rs. 75000
Collected: Rs. 35000
Progress: 46.67%
Remaining: Rs. 40000

ID: EVT2
Name: Child Education Fund
Description: Scholarship program for underprivileged children
Target: Rs. 50000
Collected: Rs. 10000
Progress: 20.00%
Remaining: Rs. 40000

================================
```

## Scenario 3: View Donors for Specific Event

```
Choose: 5 (View Donors of Specific Event)

--- VIEW DONORS FOR EVENT ---
Enter Event ID (e.g., EVT1): EVT1

Output:
========== DONORS FOR EVENT: Medical Camps for Rural Areas ==========

Donor Name: Amit Patel
Email: amit.patel@company.com
Amount Donated: Rs. 15000
Date: 2026-04-12

Donor Name: Neha Sharma
Email: neha.sharma@email.com
Amount Donated: Rs. 20000
Date: 2026-04-12

--- Total Donors: 2
Total Amount: Rs. 35000
======================================
```

## Scenario 4: View All Donors

```
Choose: 6 (View All Donors)

Output:
========== ALL DONORS ==========

ID: DOR1
Name: Amit Patel
Email: amit.patel@company.com
Phone: 9876543210

ID: DOR2
Name: Neha Sharma
Email: neha.sharma@email.com
Phone: 9765432109

================================
```

## Scenario 5: View Statistics

```
Choose: 7 (View Statistics)

Output:
╔════════════════════════════════════════╗
║         SYSTEM STATISTICS              ║
╠════════════════════════════════════════╣
║ Total Events:   2                      ║
║ Total Donors:   2                      ║
║ Total Pledges:  3                      ║
╚════════════════════════════════════════╝
```

## Error Handling Scenarios

### Invalid Input 1: Negative Donation Amount
```
Choose: 3 (Make Donation to Event)

--- MAKE DONATION ---
Enter Donor ID (e.g., DOR1): DOR1
Enter Event ID (e.g., EVT1): EVT1
Enter Donation Amount (Rs.): -5000

Output:
✗ Donation amount must be positive!
```

### Invalid Input 2: Non-existent Donor
```
Choose: 3 (Make Donation to Event)

--- MAKE DONATION ---
Enter Donor ID (e.g., DOR1): DOR99
Enter Event ID (e.g., EVT1): EVT1
Enter Donation Amount (Rs.): 1000

Output:
✗ Donor not found!
```

### Invalid Input 3: Non-existent Event
```
Choose: 3 (Make Donation to Event)

--- MAKE DONATION ---
Enter Donor ID (e.g., DOR1): DOR1
Enter Event ID (e.g., EVT1): EVT99
Enter Donation Amount (Rs.): 1000

Output:
✗ Event not found!
```

### Invalid Input 4: Empty Event Name
```
Choose: 1 (Create New Event)

--- CREATE NEW EVENT ---
Enter Event Name: [Just press Enter]
Enter Target Amount (Rs.): 50000
Enter Event Description: ...

Output:
✗ Event name cannot be empty!
```

### Invalid Input 5: Zero/Negative Target Amount
```
Choose: 1 (Create New Event)

--- CREATE NEW EVENT ---
Enter Event Name: Test Event
Enter Target Amount (Rs.): -10000
Enter Event Description: ...

Output:
✗ Target amount must be positive!
```

## Test Case Summary

| ID | Test Case | Input | Expected Output | Status |
|----|-----------|-------|-----------------|--------|
| TC1 | Create Event | Valid data | Event created with ID | ✓ PASS |
| TC2 | Register Donor | Valid data | Donor created with ID | ✓ PASS |
| TC3 | Make Donation | Valid donor, event, amount | Pledge created | ✓ PASS |
| TC4 | View Events | - | All events displayed | ✓ PASS |
| TC5 | View Donors | - | All donors listed | ✓ PASS |
| TC6 | View Event Donors | Valid event ID | Donors for event shown | ✓ PASS |
| TC7 | Statistics | - | System stats shown | ✓ PASS |
| TC8 | Negative Amount | Negative donation | Error message | ✓ PASS |
| TC9 | Invalid Donor | Non-existent donor ID | "Donor not found!" | ✓ PASS |
| TC10 | Invalid Event | Non-existent event ID | "Event not found!" | ✓ PASS |

## Expected System Behavior

### After Complete Workflow:
```
System Status:
- Total Events: 2
- Total Donors: 2
- Total Pledges: 3
- Total Amount Collected: Rs. 45000
- Events with Progress:
  * EVT1: 46.67% (35000/75000)
  * EVT2: 20% (10000/50000)
```

## Notes for Testing

1. All IDs are auto-generated starting from 1
2. Dates are automatically set to current date
3. Progress percentage updates in real-time
4. No data persistence (data lost when program ends)
5. All validations are performed before data storage
6. Error messages are user-friendly and descriptive
