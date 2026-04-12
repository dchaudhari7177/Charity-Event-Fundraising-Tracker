# Class Diagram - Charity Event Fundraising Tracker

## UML Class Diagram

```
┌─────────────────────────────────────┐
│            Event                    │
├─────────────────────────────────────┤
│ - eventId: String                   │
│ - eventName: String                 │
│ - targetAmount: double              │
│ - description: String               │
│ - collectedAmount: double           │
├─────────────────────────────────────┤
│ + Event(...)                        │
│ + getEventId(): String              │
│ + getEventName(): String            │
│ + getTargetAmount(): double         │
│ + getDescription(): String          │
│ + getCollectedAmount(): double      │
│ + addFunds(amount): void            │
│ + getProgressPercentage(): double   │
│ + displayEventDetails(): void       │
└─────────────────────────────────────┘
         ▲                    ▲
         │ uses               │ uses
         │                    │
┌────────┴────────┐  ┌────────┴─────┐
│                 │  │               │
│  FundraisingSystem              │
│  (Manager Class)                │
├─────────────────────────────────┤
│ - events: ArrayList<Event>      │
│ - donors: ArrayList<Donor>      │
│ - pledges: ArrayList<Pledge>    │
│ - eventCounter: int             │
│ - donorCounter: int             │
│ - pledgeCounter: int            │
├─────────────────────────────────┤
│ EVENT METHODS:                  │
│ + addEvent(): boolean           │
│ + getEventById(): Event         │
│ + displayAllEvents(): void      │
│                                 │
│ DONOR METHODS:                  │
│ + addDonor(): boolean           │
│ + getDonorById(): Donor         │
│ + displayAllDonors(): void      │
│                                 │
│ PLEDGE METHODS:                 │
│ + makePledge(): boolean         │
│ + getPledgesForEvent(): List    │
│ + displayDonorsForEvent(): void │
│ + displayAllPledges(): void     │
│                                 │
│ STATISTICS:                     │
│ + getTotalEventsCount(): int    │
│ + getTotalDonorsCount(): int    │
│ + getTotalPledgesCount(): int   │
└─────────────────────────────────┘


┌─────────────────────────────────────┐
│            Donor                    │
├─────────────────────────────────────┤
│ - donorId: String                   │
│ - donorName: String                 │
│ - email: String                     │
│ - phoneNumber: String               │
├─────────────────────────────────────┤
│ + Donor(...)                        │
│ + getDonorId(): String              │
│ + getDonorName(): String            │
│ + getEmail(): String                │
│ + getPhoneNumber(): String          │
│ + setDonorName(): void              │
│ + setEmail(): void                  │
│ + setPhoneNumber(): void            │
│ + displayDonorDetails(): void       │
└─────────────────────────────────────┘


┌─────────────────────────────────────┐
│            Pledge                   │
├─────────────────────────────────────┤
│ - pledgeId: String                  │
│ - donorId: String (FK)              │
│ - eventId: String (FK)              │
│ - pledgeAmount: double              │
│ - pledgeDate: String                │
├─────────────────────────────────────┤
│ + Pledge(...)                       │
│ + getPledgeId(): String             │
│ + getDonorId(): String              │
│ + getEventId(): String              │
│ + getPledgeAmount(): double         │
│ + getPledgeDate(): String           │
│ + setPledgeAmount(): void           │
│ + setPledgeDate(): void             │
│ + displayPledgeDetails(): void      │
└─────────────────────────────────────┘


┌─────────────────────────────────────┐
│             Main                    │
├─────────────────────────────────────┤
│ - system: FundraisingSystem         │
│ - scanner: Scanner                  │
├─────────────────────────────────────┤
│ + main(args: String[]): void        │
│ - displayMainMenu(): void           │
│ - createEvent(): void               │
│ - registerDonor(): void             │
│ - makeDonation(): void              │
│ - viewEvents(): void                │
│ - viewDonorsForEvent(): void        │
│ - viewAllDonors(): void             │
│ - displayStatistics(): void         │
│ - getIntInput(): int                │
│ - getDoubleInput(): double          │
└─────────────────────────────────────┘
```

## Relationships

| From | To | Type | Description |
|------|----|----|-------------|
| FundraisingSystem | Event | Composition | Manages collection of events |
| FundraisingSystem | Donor | Composition | Manages collection of donors |
| FundraisingSystem | Pledge | Composition | Manages collection of pledges |
| Pledge | Event | Association | References an event (foreign key) |
| Pledge | Donor | Association | References a donor (foreign key) |
| Main | FundraisingSystem | Dependency | Uses system for all operations |

## Data Flow

```
┌─────────────────────────────────────────────────────────────┐
│                    USER (Main)                              │
└────────────────────┬────────────────────────────────────────┘
                     │
                     ▼
         ┌──────────────────────┐
         │  FundraisingSystem   │ (Business Logic)
         │   (Manager)          │
         └──────────────────────┘
                │    │    │
        ┌───────┘    │    └────────┬──────────┐
        │            │             │          │
        ▼            ▼             ▼          ▼
    ┌────────┐  ┌───────┐    ┌────────┐  ┌────────┐
    │ Events │  │Donors │    │Pledges │  │Validation
    │(ArrayList)│(ArrayList) │(ArrayList)│
    └────────┘  └───────┘    └────────┘  └────────┘
```

## Key Design Principles Used

### 1. **Single Responsibility Principle (SRP)**
- Each class has one clear purpose
- Event: data about an event
- Donor: data about a donor
- Pledge: data about a donation
- FundraisingSystem: business logic and management
- Main: user interface

### 2. **Encapsulation**
- All fields are private
- Public getters and setters provide controlled access
- Input validation in setters

### 3. **Dependency Management**
- Main depends on FundraisingSystem
- FundraisingSystem manages Event, Donor, and Pledge
- No circular dependencies

### 4. **Composition over Inheritance**
- FundraisingSystem composes ArrayList of objects
- Flexible and maintainable

### 5. **Separation of Concerns**
- Data classes (Event, Donor, Pledge): store data and basic operations
- Manager class (FundraisingSystem): business logic
- UI class (Main): user interaction

## ID Management

```
Event IDs:    EVT1, EVT2, EVT3, ... (Auto-incremented)
Donor IDs:    DOR1, DOR2, DOR3, ... (Auto-incremented)
Pledge IDs:   PLD1, PLD2, PLD3, ... (Auto-incremented)
```

## Example Data Structure in Memory

```
FundraisingSystem
│
├─ events: [
│   Event(EVT1, "AIDS Awareness", 50000, "...", 15000),
│   Event(EVT2, "Cancer Relief", 100000, "...", 45000)
│  ]
│
├─ donors: [
│   Donor(DOR1, "Raj Kumar", "raj@email.com", "9876543210"),
│   Donor(DOR2, "Priya Singh", "priya@email.com", "9765432109")
│  ]
│
└─ pledges: [
    Pledge(PLD1, DOR1, EVT1, 5000, "2026-04-12"),
    Pledge(PLD2, DOR2, EVT1, 10000, "2026-04-12"),
    Pledge(PLD3, DOR1, EVT2, 25000, "2026-04-12")
   ]
```

## Validation Rules

```
Event Creation:
  ├─ eventName ≠ null and ≠ ""
  └─ targetAmount > 0

Donor Registration:
  └─ donorName ≠ null and ≠ ""

Pledge Creation:
  ├─ pledgeAmount > 0
  ├─ donorId exists in system
  ├─ eventId exists in system
  └─ All conditions met → add funds to event
```
