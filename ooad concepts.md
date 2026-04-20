# OOAD Concepts Used In This Project

This document lists the Object-Oriented Analysis and Design (OOAD) concepts used in the Charity Event Fundraising Tracker and maps each concept to concrete implementation locations.

## 1) Domain Analysis and Conceptual Modeling

The core domain concepts identified during analysis are:

- Event
- Donor
- Pledge (Donation)

These are modeled as entity classes:

- src/model/Event.java
- src/model/Donor.java
- src/model/Pledge.java

Design intent:

- Event stores fundraising goal and collected amount.
- Donor stores contributor identity and contact details.
- Pledge links a donor to an event with amount and date.

## 2) Object Modeling (Classes, Objects, State, Behavior)

Each domain class has:

- State (private fields)
- Behavior (methods such as progress calculation, display, validation-aware setters)

Examples:

- Event state + behavior in src/model/Event.java
- Progress calculation in Event.getProgressPercentage() in src/model/Event.java
- Pledge amount rule in Pledge.setPledgeAmount() in src/model/Pledge.java

## 3) Encapsulation

Encapsulation is implemented by:

- Private attributes in model classes
- Controlled access through getters/setters
- Validation checks inside mutator methods

Where:

- src/model/Event.java
- src/model/Donor.java
- src/model/Pledge.java

## 4) Abstraction

Abstraction appears as layered APIs where upper layers call simplified operations without seeing low-level details:

- Controller does not write SQL.
- Service layer exposes business operations.
- DAO layer hides JDBC details.

Where:

- Controller abstraction usage: src/controller/CharityTrackerController.java
- Service APIs: src/service/EventService.java, src/service/DonorService.java, src/service/PledgeService.java
- Persistence abstraction: src/dao/EventDAO.java, src/dao/DonorDAO.java, src/dao/PledgeDAO.java

## 5) Inheritance

Inheritance is used strongly in UI classes (Swing hierarchy):

- MainWindow_Enhanced extends JFrame
- MainWindow extends JFrame
- Panels extend JPanel
- Custom rounded border extends AbstractBorder

Where:

- src/ui/MainWindow_Enhanced.java
- src/ui/MainWindow.java
- src/ui/panels/*.java
- src/ui/CustomComponents.java

## 6) Polymorphism

Two major forms are used:

1. Interface-based polymorphism
- DonationObserver interface has multiple implementations.

2. Method overriding polymorphism
- Observer callback methods are overridden.
- Swing rendering methods are overridden in custom components.

Where:

- Interface: src/observer/DonationObserver.java
- Concrete observer: src/observer/EventProgressTracker.java
- UI observers: src/ui/panels/ViewEventsPanel_Enhanced.java, src/ui/panels/StatisticsPanel_Enhanced.java
- Overridden painting/rendering: src/ui/CustomComponents.java, src/ui/panels/StatisticsPanel_Enhanced.java

## 7) Class Relationships (Association, Dependency, Aggregation/Composition)

### 7.1 Association

Pledge is associated with Donor and Event via donorId and eventId.

Where:

- src/model/Pledge.java
- Database mapping in src/db/DBConnection.java (foreign keys in pledges table)

### 7.2 Dependency

Dependencies are explicit across layers:

- Controller depends on services and view.
- Services depend on DAOs.
- DAOs depend on DBConnection.

Where:

- src/controller/CharityTrackerController.java
- src/service/*.java
- src/dao/*.java
- src/db/DBConnection.java

### 7.3 Aggregation-like relationship

DonationEventManager manages a collection of observers.

Where:

- observers list in src/observer/DonationEventManager.java

### 7.4 Composition-like construction

Controller creates/owns key runtime collaborators in constructor (view, services via factory, event manager access, observer subscription).

Where:

- src/controller/CharityTrackerController.java

## 8) Architectural Design: Layered + MVC

The architecture follows layered responsibilities with MVC organization:

- View layer: console/swing presentation
- Controller layer: orchestration and request flow
- Service layer: business logic
- DAO layer: data access
- DB layer: connection + schema setup

Where:

- Console MVC path:
  - Entry: src/Main.java
  - Controller: src/controller/CharityTrackerController.java
  - View: src/view/ConsoleView.java
- Swing path:
  - Entry: src/SwingMain.java
  - Main UI frame: src/ui/MainWindow_Enhanced.java
  - UI panels: src/ui/panels/*.java

## 9) Design Patterns Implemented

## 9.1 DAO Pattern

Purpose:

- Isolate SQL/JDBC from business logic.

Where:

- src/dao/EventDAO.java
- src/dao/DonorDAO.java
- src/dao/PledgeDAO.java

Evidence:

- CRUD queries and PreparedStatement usage in all DAO classes.

## 9.2 Facade-style Service Layer

Purpose:

- Provide simplified business operations to controller/UI.
- Hide multi-step DAO interactions.

Where:

- src/service/EventService.java
- src/service/DonorService.java
- src/service/PledgeService.java

Evidence:

- PledgeService.makeDonation() validates input, writes pledge, updates event total, triggers observer notifications.

## 9.3 Factory Pattern

Purpose:

- Centralized service object creation.

Where:

- src/service/ServiceFactory.java

Evidence:

- getEventService(), getDonorService(), getPledgeService() methods.

## 9.4 Singleton Pattern

Purpose:

- Single shared instance for global coordination/resource management.

Where:

- src/db/DBConnection.java (shared DB connection)
- src/observer/DonationEventManager.java (single event manager)

Evidence:

- Static instance fields and global access methods.

## 9.5 Observer Pattern

Purpose:

- Event-driven updates with loose coupling.

Roles:

- Subject/Publisher: DonationEventManager
- Observer contract: DonationObserver
- Concrete observers: EventProgressTracker, enhanced UI panels

Where:

- src/observer/DonationEventManager.java
- src/observer/DonationObserver.java
- src/observer/EventProgressTracker.java
- src/ui/panels/ViewEventsPanel_Enhanced.java
- src/ui/panels/StatisticsPanel_Enhanced.java
- Trigger point in service: src/service/PledgeService.java

## 10) SOLID Principles Applied

## 10.1 SRP (Single Responsibility Principle)

- Model classes focus on data + limited domain behavior.
- DAOs focus on persistence.
- Services focus on business rules.
- View focuses on presentation.
- Controller focuses on flow/orchestration.

Where:

- src/model/*.java
- src/dao/*.java
- src/service/*.java
- src/view/ConsoleView.java
- src/controller/CharityTrackerController.java

## 10.2 OCP (Open/Closed Principle)

- Observer mechanism allows adding new observers without changing publisher logic.

Where:

- src/observer/DonationObserver.java
- src/observer/DonationEventManager.java

## 10.3 LSP (Liskov Substitution Principle)

- Implementations of DonationObserver are substitutable wherever DonationObserver is expected.

Where:

- src/observer/EventProgressTracker.java
- src/ui/panels/ViewEventsPanel_Enhanced.java
- src/ui/panels/StatisticsPanel_Enhanced.java

## 10.4 ISP (Interface Segregation Principle)

- DonationObserver is a focused, event-specific interface for donation lifecycle events.

Where:

- src/observer/DonationObserver.java

## 10.5 DIP (Dependency Inversion Principle)

Partially achieved:

- Controller depends on service factory access instead of constructing all dependencies directly.
- Full inversion is partial because services still instantiate concrete DAOs.

Where:

- src/controller/CharityTrackerController.java
- src/service/EventService.java
- src/service/DonorService.java
- src/service/PledgeService.java

## 11) Data Design and Relational Mapping

The data model is mapped to relational tables:

- events
- donors
- pledges

With referential integrity:

- pledges.donor_id -> donors.id
- pledges.event_id -> events.id

Where:

- Schema creation in src/db/DBConnection.java
- CRUD mapping in src/dao/*.java

## 12) Validation and Defensive Design

Validation is present at multiple layers:

- Controller input guards (id/amount checks)
- Service business validation (empty names, positive amount, email format)
- Domain setter validation (target amount, pledge amount)

Where:

- src/controller/CharityTrackerController.java
- src/service/EventService.java
- src/service/DonorService.java
- src/service/PledgeService.java
- src/model/Event.java
- src/model/Pledge.java

## 13) Error Handling and Robustness

- DAO methods use try/catch and fail-safe returns.
- DB initialization handles unavailable database scenarios.

Where:

- src/dao/*.java
- src/db/DBConnection.java

## 14) Event-Driven UI Synchronization (Enhanced Swing)

Enhanced UI components subscribe as observers and refresh data automatically after donation events.

Where:

- src/ui/panels/ViewEventsPanel_Enhanced.java
- src/ui/panels/StatisticsPanel_Enhanced.java
- src/service/PledgeService.java

## 15) Navigation State Management (Swing)

CardLayout is used as a simple screen-state manager for switching between application screens.

Where:

- src/ui/MainWindow_Enhanced.java
- src/ui/MainWindow.java

## 16) UML and OOAD Documentation Artifacts

The project includes explicit UML and analysis/design documentation:

- Use Case coverage
- Class model coverage
- Architecture flow and layer mapping
- Pattern explanation sections

Where:

- OOAD_ACADEMIC_REPORT.md
- UML_DIAGRAMS.md
- CLASS_DIAGRAM.md
- PROJECT_STRUCTURE_COMPLETE.md

## 17) Practical Notes For Viva

1. Strongest demonstrated concepts:
- MVC layering
- DAO separation
- Observer event flow
- Factory/Singleton usage
- SRP and OCP

2. Honest improvement point:
- DIP is partial because services directly create DAOs instead of depending on DAO interfaces/injection.

3. Clear end-to-end OOAD flow in one sentence:
- User input (View) -> Controller -> Service (business rules) -> DAO (persistence) -> Observer notifications -> updated View.
