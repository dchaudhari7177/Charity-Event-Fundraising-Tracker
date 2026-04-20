# JavaFX Charity Tracker Application

## Overview

Modern JavaFX application for Charity Event Management System with a premium dark theme, smooth animations, and responsive design.

## ✨ Features

### 1. **Modern UI Design**
- Dark theme with deep navy background (#0D111B)
- Neon accent colors: Cyan (#00F1FE), Green (#10FF82), Purple (#C864FF), Pink (#FF4787)
- Professional typography with hierarchy
- Soft shadows and rounded corners (15px default)

### 2. **Responsive Layouts**
- Sidebar navigation with active states
- VBox, HBox, GridPane for responsive design
- No absolute positioning
- Adapts to different window sizes

### 3. **Animated Transitions**
- Fade-in animations on screen load
- Hover scale effects on buttons
- Smooth slide transitions
- Pulse animations on cards

### 4. **Dashboard Screen**
- Quick stats cards (Events, Donors, Target Amount, Collected Amount)
- Quick action cards for common tasks
- Recent events table
- Real-time data updates

### 5. **Form Screens**
- **Create Event**: Add new charity events with target amounts
- **Register Donor**: Register new donors with validation
- **Record Donation**: Link donors to events and record amounts
- Input validation with error messages
- Success notifications

### 6. **Data Visualization**
- Events Table with search and filtering
- Donors Table with sorting
- Statistics Dashboard with metrics
- Progress bars for event tracking
- Dynamic calculations

### 7. **Backend Integration**
- Full integration with existing DAO layer
- MySQL database connectivity
- CRUD operations for all entities
- Transactional support

## 📁 Directory Structure

```
src/javafx/
├── app/
│   └── CharityTrackerApp.java              # Main application entry point
├── controller/
│   ├── MainController.java                 # Navigation & sidebar logic
│   ├── DashboardController.java            # Dashboard functionality
│   ├── CreateEventController.java          # Event creation form
│   ├── RegisterDonorController.java        # Donor registration form
│   ├── RecordDonationController.java       # Donation recording form
│   ├── EventsTableController.java          # Events table functionality
│   ├── DonorsTableController.java          # Donors table functionality
│   └── StatisticsController.java           # Statistics & analytics
├── fxml/
│   ├── main.fxml                           # Main window layout with sidebar
│   ├── dashboard.fxml                      # Dashboard screen
│   ├── create-event.fxml                   # Create event form
│   ├── register-donor.fxml                 # Register donor form
│   ├── record-donation.fxml                # Record donation form
│   ├── events-table.fxml                   # Events table view
│   ├── donors-table.fxml                   # Donors table view
│   └── statistics.fxml                     # Statistics dashboard
├── resources/
│   └── styles.css                          # Global CSS stylesheet
└── util/
    ├── ThemeConstants.java                 # Color palette & constants
    └── AnimationUtils.java                 # Animation utilities

```

## 🎨 Color Palette

| Color | Hex | Usage |
|-------|-----|-------|
| Background Primary | #0D111B | Main background |
| Background Secondary | #141B2D | Cards and containers |
| Accent Cyan | #00F1FE | Primary buttons, highlights |
| Accent Green | #10FF82 | Success states |
| Accent Purple | #C864FF | Secondary buttons |
| Accent Pink | #FF4787 | Error/danger states |
| Accent Gold | #FFD700 | Warning states |
| Text Primary | #E8EAED | Main text |
| Text Secondary | #9CA3AF | Subtitle text |

## 🎬 Animation Effects

1. **Fade In/Out** - 300-400ms smooth opacity transitions
2. **Scale** - Hover effects on buttons (1.02x scale)
3. **Slide** - Screen transitions from left/right
4. **Pulse** - Emphasis animation on cards
5. **Rotate** - Loading spinner effect

## 🔧 Technology Stack

- **Language**: Java 11+
- **UI Framework**: JavaFX 21.0.1+
- **Layout**: FXML + CSS
- **Database**: MySQL 5.7+
- **Backend**: Existing DAO layer
- **Build**: javac command-line

## 🚀 Quick Start

### 1. Download JavaFX SDK
```bash
# Visit: https://gluonhq.com/products/javafx/
# Extract to: lib/javafx-sdk-21.0.1/
```

### 2. Compile
```bash
javac -cp "bin;lib\mysql-connector-j-9.6.0.jar;lib\javafx-sdk-21.0.1\lib\*" -d bin src\javafx\app\CharityTrackerApp.java ...
```

### 3. Run
```bash
java -cp "bin;lib\mysql-connector-j-9.6.0.jar;lib\javafx-sdk-21.0.1\lib\*" ^
  --module-path lib\javafx-sdk-21.0.1\lib ^
  --add-modules javafx.controls,javafx.fxml,javafx.graphics ^
  javafx.app.CharityTrackerApp
```

See [JAVAFX_COMPILATION_GUIDE.md](JAVAFX_COMPILATION_GUIDE.md) for detailed instructions.

## 📋 Screen Overview

### Dashboard
- Overview of key metrics
- Quick action cards for common tasks
- Recent events table
- Navigation to all features

### Create Event
- Form to create new charity events
- Fields: Event Name, Target Amount, Description
- Validation and error messages
- Success notification

### Register Donor
- Form to register new donors
- Fields: Full Name, Email, Phone Number
- Email and phone validation
- Success notification

### Record Donation
- Dropdown selection of donor and event
- Amount input field
- Date picker
- Automatic event amount update

### View Events
- Table with all events
- Search functionality
- Shows: ID, Name, Target, Collected, Progress
- Refresh button for real-time updates

### View Donors
- Table with all donors
- Search functionality
- Shows: ID, Name, Email, Phone
- Refresh button

### Statistics
- Key metrics cards
- Event progress tracking
- Progress bars with color indicators
- Achievement percentage calculation

## 🔌 Backend Integration

### Data Models Used
- `model.Event` - Event entity with target and collected amounts
- `model.Donor` - Donor entity with contact information
- `model.Pledge` - Pledge/donation entity linking donors to events

### DAO Methods Used
- `EventDAO.addEvent(event)` - Create event
- `EventDAO.getAllEvents()` - Get all events
- `EventDAO.getEventById(id)` - Get specific event
- `EventDAO.updateCollectedAmount(id, amount)` - Update event amounts
- `DonorDAO.addDonor(donor)` - Register donor
- `DonorDAO.getAllDonors()` - Get all donors
- `PledgeDAO.addPledge(pledge)` - Record donation
- `DBConnection.getConnection()` - MySQL connectivity

## 🎯 MVC Architecture

- **Model**: Event, Donor, Pledge classes (existing)
- **View**: FXML files defining UI structure
- **Controller**: Java classes handling logic
  - `MainController` - Route management
  - Specific controllers for each screen
  - Event handlers for user interactions

## 🛡️ Input Validation

### Create Event
- ✓ Event name required
- ✓ Target amount required and > 0
- ✓ Description optional

### Register Donor
- ✓ Full name required
- ✓ Email required and valid format
- ✓ Phone required with 10+ digits

### Record Donation
- ✓ Donor selection required
- ✓ Event selection required
- ✓ Amount required and > 0
- ✓ Date auto-filled with today's date

## 📊 Calculations

- **Achievement %** = (Collected / Target) * 100
- **Progress Bar** = Min(Collected / Target, 1.0)
- **Color Coding**: Red (0-50%), Yellow (50-75%), Cyan (75-99%), Green (100%+)

## 🎨 CSS Styling

- **Global Styles**: Root, buttons, text fields, labels
- **Component Styles**: Cards, tables, progress bars, dialogs
- **State Styles**: Hover, focus, active, disabled
- **Theme Integration**: All colors use defined constants

## 🐛 Debugging

- Database connection messages logged to console
- Validation errors displayed inline
- Success/error notifications shown to user
- Check MySQL connection for any issues

## 📈 Future Enhancements

- Light theme toggle
- Export to PDF/CSV
- Advanced filtering and sorting
- Real-time data sync
- User authentication
- Donation history tracking
- Event reminders
- Donor communication features

## 📝 Notes

- All existing backend code remains unchanged
- Database schema is compatible
- Application uses JDBC connection pooling via DAOs
- CSS can be customized for branding
- FXML provides clean separation of UI and logic

## 📄 License

Part of OOAD (Object-Oriented Analysis and Design) project.

---

**Version**: 1.0 - JavaFX Edition
**Status**: Production Ready
**Last Updated**: April 2024
