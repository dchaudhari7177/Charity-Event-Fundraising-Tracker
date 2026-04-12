# 📊 UML Diagrams - Charity Event Fundraising Tracker

## 1. USE CASE DIAGRAM

```plantuml
@startuml Use_Case_Diagram
left to right direction

actor Admin
actor Donor

rectangle System {
  usecase UC1 as "Create Event"
  usecase UC2 as "Register Donor"
  usecase UC3 as "Make Donation"
  usecase UC4 as "View Event Progress"
  usecase UC5 as "View All Events"
  usecase UC6 as "View All Donors"
  usecase UC7 as "View Statistics"
  usecase UC8 as "Delete Event"
  usecase UC9 as "Receive Notifications"
}

Admin --> UC1
Admin --> UC8
Admin --> UC7

Donor --> UC2
Donor --> UC3
Donor --> UC4
Donor --> UC5
Donor --> UC6

UC3 --> UC9 : <<include>>
UC3 --> UC4 : <<include>>
@enduml
```

**Explanation:**
- **Admin Actor:** Creates events, manages system, deletes outdated events, views statistics
- **Donor Actor:** Registers, makes donations, views event progress and other donors
- **Use Cases:** 9 main operations supported by the system
- **Include Relationship:** Donation (UC3) includes progress update (UC4) and notification (UC9)

---

## 2. CLASS DIAGRAM

```plantuml
@startuml Class_Diagram

' Model Classes
class Event {
  - eventId: int
  - eventName: String
  - targetAmount: double
  - collectedAmount: double
  - description: String
  ---
  + getEventId(): int
  + getEventName(): String
  + getProgressPercentage(): double
  + getCollectedAmount(): double
}

class Donor {
  - donorId: int
  - donorName: String
  - email: String
  - phoneNumber: String
  ---
  + getDonorId(): int
  + getDonorName(): String
  + getEmail(): String
}

class Pledge {
  - pledgeId: int
  - donorId: int
  - eventId: int
  - pledgeAmount: double
  - pledgeDate: String
  ---
  + getPledgeId(): int
  + getPledgeAmount(): double
  + getDonorId(): int
}

' DAO Classes
class EventDAO {
  - eventDAO: EventDAO
  ---
  + addEvent(event): boolean
  + getEventById(id): Event
  + getAllEvents(): List<Event>
  + updateCollectedAmount(id, amount): boolean
  + deleteEvent(id): boolean
}

class DonorDAO {
  - donorDAO: DonorDAO
  ---
  + addDonor(donor): boolean
  + getDonorById(id): Donor
  + getAllDonors(): List<Donor>
  + getDonorByName(name): Donor
}

class PledgeDAO {
  - pledgeDAO: PledgeDAO
  ---
  + addPledge(pledge): boolean
  + getPledgeById(id): Pledge
  + getPledgesByEvent(eventId): List<Pledge>
  + getPledgesByDonor(donorId): List<Pledge>
  + getTotalPledgedForEvent(eventId): double
}

' Database Connection
class DBConnection {
  - {static} connection: Connection
  - {static} instance: DBConnection
  ---
  - {static} DBConnection()
  + {static} getConnection(): Connection
  + {static} closeConnection(): void
  + {static} initializeDatabase(): void
}

' Service Layer
class EventService {
  - eventDAO: EventDAO
  ---
  + createEvent(name, target, desc): boolean
  + getEvent(id): Event
  + getAllEvents(): List<Event>
  + updateCollectedAmount(id, amount): boolean
}

class DonorService {
  - donorDAO: DonorDAO
  ---
  + registerDonor(name, email, phone): boolean
  + getDonor(id): Donor
  + getAllDonors(): List<Donor>
  + findDonorByName(name): Donor
}

class PledgeService {
  - pledgeDAO: PledgeDAO
  - eventDAO: EventDAO
  - eventManager: DonationEventManager
  ---
  + makeDonation(donorId, eventId, amount, date): boolean
  + getPledgesForEvent(eventId): List<Pledge>
  + getTotalPledgedForEvent(eventId): double
}

class ServiceFactory {
  - {static} eventService: EventService
  - {static} donorService: DonorService
  - {static} pledgeService: PledgeService
  ---
  - ServiceFactory()
  + {static} getEventService(): EventService
  + {static} getDonorService(): DonorService
  + {static} getPledgeService(): PledgeService
}

' Observer Pattern
interface DonationObserver {
  + onDonationReceived(pledge, event): void
  + onGoalReached(event): void
  + onProgressUpdated(event, percentage): void
}

class EventProgressTracker {
  - name: String
  ---
  + onDonationReceived(pledge, event): void
  + onGoalReached(event): void
  + onProgressUpdated(event, percentage): void
}

class DonationEventManager {
  - {static} instance: DonationEventManager
  - observers: List<DonationObserver>
  ---
  + {static} getInstance(): DonationEventManager
  + subscribe(observer): void
  + unsubscribe(observer): void
  + notifyDonationReceived(pledge, event): void
  + notifyGoalReached(event): void
}

' MVC Components
class ConsoleView {
  - scanner: Scanner
  ---
  + displayMainMenu(): void
  + displayAllEvents(events): void
  + displayAllDonors(donors): void
  + inputEventName(): String
  + inputDonationAmount(): double
}

class CharityTrackerController {
  - eventService: EventService
  - donorService: DonorService
  - pledgeService: PledgeService
  - view: ConsoleView
  - eventManager: DonationEventManager
  ---
  + run(): void
  - handleCreateEvent(): void
  - handleRegisterDonor(): void
  - handleMakeDonation(): void
  - handleViewAllEvents(): void
}

' Relationships
EventDAO --> DBConnection
DonorDAO --> DBConnection
PledgeDAO --> DBConnection

EventService --> EventDAO
DonorService --> DonorDAO
PledgeService --> PledgeDAO
PledgeService --> EventDAO
PledgeService --> DonationEventManager

EventService --> Event
DonorService --> Donor
PledgeService --> Pledge

EventProgressTracker --|> DonationObserver
DonationEventManager --> DonationObserver

CharityTrackerController --> EventService
CharityTrackerController --> DonorService
CharityTrackerController --> PledgeService
CharityTrackerController --> ConsoleView
CharityTrackerController --> DonationEventManager

@enduml
```

**Explanation:**
- **Model Layer:** Event, Donor, Pledge (POJO classes)
- **DAO Layer:** EventDAO, DonorDAO, PledgeDAO (database operations)
- **Service Layer:** EventService, DonorService, PledgeService (business logic with Facade pattern)
- **Controller:** CharityTrackerController (orchestrates MVC)
- **View:** ConsoleView (user interface)
- **Observer:** DonationEventManager + DonationObserver (observer pattern)
- **Relationships:** Show dependencies and message flow

---

## 3. ACTIVITY DIAGRAMS

### 3.1 Create Event Activity Diagram

```plantuml
@startuml CreateEvent_Activity

start
:User selects "Create Event";
:Enter Event Name;
:Enter Target Amount;
:Enter Description;
if (All inputs valid?) then (yes)
  :Call EventService.createEvent();
  :Service validates inputs;
  :Create Event object;
  if (Database insertion successful?) then (yes)
    :Display success message;
    :Event created in database;
  else (no)
    :Display error message;
  endif
else (no)
  :Display validation error;
  :Ask user to re-enter;
endif
:Return to Main Menu;
stop

@enduml
```

### 3.2 Register Donor Activity Diagram

```plantuml
@startuml RegisterDonor_Activity

start
:User selects "Register Donor";
:Enter Donor Name;
:Enter Email;
:Enter Phone Number;
if (Name is not empty?) then (yes)
  if (Email format valid?) then (yes)
    :Call DonorService.registerDonor();
    :Service validates inputs;
    :Create Donor object;
    if (Database insertion successful?) then (yes)
      :Display success message;
      :Donor registered in database;
    else (no)
      :Display error message;
    endif
  else (no)
    :Display email validation error;
  endif
else (no)
  :Display name validation error;
endif
:Return to Main Menu;
stop

@enduml
```

### 3.3 Make Donation Activity Diagram

```plantuml
@startuml MakeDonation_Activity

start
:User selects "Make Donation";
:Enter Donor ID;
if (Donor exists?) then (yes)
  :Enter Event ID;
  if (Event exists?) then (yes)
    :Enter Donation Amount;
    if (Amount > 0?) then (yes)
      :Enter Date;
      :Call PledgeService.makeDonation();
      :Create Pledge object;
      :Save Pledge to database;
      :Update Event collected amount;
      :Calculate progress percentage;
      :Notify DonationObservers;
      if (Progress >= 100%?) then (yes)
        :Notify goal reached;
        :Display achievement message;
      else (no)
        :Display progress update;
      endif
      :Display donation confirmation;
    else (no)
      :Display amount validation error;
    endif
  else (no)
    :Display event not found error;
  endif
else (no)
  :Display donor not found error;
endif
:Return to Main Menu;
stop

@enduml
```

### 3.4 View Event Progress Activity Diagram

```plantuml
@startuml ViewEventProgress_Activity

start
:User selects "View Event Progress";
:Enter Event ID;
if (Event exists?) then (yes)
  :Retrieve Event from database;
  :Get all pledges for event;
  if (Pledges exist?) then (yes)
    :Calculate total collected;
    :Calculate progress percentage;
    :Generate progress bar;
    :Display event details;
    :Display progress visualization;
    :For each pledge:
      :Display donor name;
      :Display donation amount;
      :Display donation date;
    endfor
    :Display total contributors;
  else (no)
    :Display "no donations yet" message;
  endif
else (no)
  :Display event not found error;
endif
:Return to Main Menu;
stop

@enduml
```

---

## 4. STATE DIAGRAMS

### 4.1 Event State Diagram

```plantuml
@startuml Event_States

[*] --> Created: Create Event\n(eventId assigned)

Created --> Active: First Donation\nReceived\n(collected_amount > 0)

Active --> Completed: Goal Reached\n(collected >= target)

Active --> Active: Donation\nReceived\n(amount < target)

Completed --> [*]: Archive/Delete Event

@enduml
```

### 4.2 Donation (Pledge) State Diagram

```plantuml
@startuml Pledge_States

[*] --> Pending: Donation\nInitiated

Pending --> Recorded: Saved to\nDatabase

Recorded --> Confirmed: Event\nUpdated

Confirmed --> [*]: Notification\nSent

@enduml
```

### 4.3 Donor State Diagram

```plantuml
@startuml Donor_States

[*] --> Registered: Register\nDonor

Registered --> Active: Make\nDonation

Active --> Active: Additional\nDonations

Active --> Inactive: No Activity\n(time-based)

Inactive --> Active: New\nDonation

@enduml
```

### 4.4 System State Diagram

```plantuml
@startuml System_States

[*] --> Idle: Application\nStarted

Idle --> ProcessingInput: User Input\nReceived

ProcessingInput --> Idle: Operation\nComplete

ProcessingInput --> Error: Validation\nFailed

Error --> Idle: Error\nHandled

Idle --> Shutdown: User\nSelects Exit

Shutdown --> [*]: Database\nClosed

@enduml
```

---

## Summary

- **Use Case Diagram:** Shows actors and their interactions with the system
- **Class Diagram:** Shows all classes, attributes, methods, and relationships
- **Activity Diagrams:** Show step-by-step flows for key operations
- **State Diagrams:** Show state transitions for events, donations, donors, and system

All diagrams follow UML standards and are rendered in PlantUML format.
