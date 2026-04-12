# 🎨 SWING GUI SETUP GUIDE - FLATLAF DARK THEME

## Overview

The modern Swing GUI has been created with:
- **FlatLaf** dark theme for sleek appearance
- **Rounded buttons** with hover effects
- **Styled tables** with alternating row colors
- **Modern panels** with card-based design
- **Dark color scheme** (#1e1e2f background)
- **Green & Cyan accents** for interactive elements

---

## 📋 Project Structure

```
src/
├── SwingMain.java ..................... Entry point for Swing GUI
├── ui/
│   ├── UIConstants.java ............... Colors, fonts, styling constants
│   ├── CustomComponents.java .......... Reusable styled components
│   ├── MainWindow.java ................ Main frame managing all panels
│   └── panels/
│       ├── DashboardPanel.java ........ Main menu with action cards
│       ├── CreateEventPanel.java ...... Create event form
│       ├── RegisterDonorPanel.java .... Register donor form
│       ├── MakeDonationPanel.java ..... Make donation form
│       ├── ViewEventsPanel.java ....... Events table view
│       ├── ViewDonorsPanel.java ....... Donors table view
│       └── StatisticsPanel.java ....... Statistics dashboard
├── model/ ............................ POJO classes (existing)
├── dao/ .............................. Data access objects (existing)
├── db/ ............................... Database connection (existing)
└── controller/ ....................... Business logic (existing)
```

---

## 🔧 STEP 1: Download FlatLaf JAR

FlatLaf is a modern look and feel library for Swing.

### Option A: Download Manually

1. Go to: https://www.formdev.com/flatlaf/
2. Download: `flatlaf-3.x.jar`
3. Place in: `lib/flatlaf-3.x.jar`

### Option B: Use Maven/Gradle

Add to your `pom.xml`:
```xml
<dependency>
    <groupId>com.formdev</groupId>
    <artifactId>flatlaf</artifactId>
    <version>3.4.1</version>
</dependency>
```

### Verify FlatLaf is in lib folder:
```powershell
dir lib\flatlaf*.jar
```

Should show: `flatlaf-3.x.jar`

---

## ✅ STEP 2: Verify UI Files Are Created

Check that all UI files exist:

```powershell
dir src\ui\
dir src\ui\panels\

# Should see:
# UIConstants.java
# CustomComponents.java
# MainWindow.java
# panels/DashboardPanel.java
# panels/CreateEventPanel.java
# panels/RegisterDonorPanel.java
# panels/MakeDonationPanel.java
# panels/ViewEventsPanel.java
# panels/ViewDonorsPanel.java
# panels/StatisticsPanel.java

# And entry point:
# src\SwingMain.java
```

---

## 🔨 STEP 3: Compile Swing UI

### Full Compilation with UI:

**Windows (PowerShell):**
```powershell
javac -cp ".;lib\mysql-connector-j-9.6.0.jar;lib\flatlaf-3.4.1.jar" -d bin `
    src\SwingMain.java `
    src\ui\*.java `
    src\ui\panels\*.java `
    src\Main.java `
    src\model\*.java `
    src\dao\*.java `
    src\db\*.java `
    src\controller\*.java `
    src\view\*.java `
    src\service\*.java `
    src\observer\*.java
```

**Mac/Linux (Bash):**
```bash
javac -cp ".:lib/mysql-connector-j-9.6.0.jar:lib/flatlaf-3.4.1.jar" -d bin \
    src/SwingMain.java \
    src/ui/*.java \
    src/ui/panels/*.java \
    src/Main.java \
    src/model/*.java \
    src/dao/*.java \
    src/db/*.java \
    src/controller/*.java \
    src/view/*.java \
    src/service/*.java \
    src/observer/*.java
```

### Verify Compilation:
```powershell
dir bin\ui\*.class
dir bin\ui\panels\*.class
```

Should show compiled UI classes.

---

## ▶️ STEP 4: Run Swing GUI

### Windows (PowerShell):
```powershell
java -cp "bin;lib\mysql-connector-j-9.6.0.jar;lib\flatlaf-3.4.1.jar" SwingMain
```

### Mac/Linux (Bash):
```bash
java -cp "bin:lib/mysql-connector-j-9.6.0.jar:lib/flatlaf-3.4.1.jar" SwingMain
```

### Expected Output:
```
? Initializing database...
✓ Database initialized successfully!
✓ Dark theme applied!
✓ Application started successfully!
```

Then a **modern dark-themed window** should appear with:
- Dashboard with 6 action cards
- Dark background (#1e1e2f)
- Green accent buttons
- Smooth transitions between screens

---

## 🎨 UI Features

### Dashboard Screen
```
┌─────────────────────────────────────┐
│  CHARITY EVENT FUNDRAISING TRACKER   │
│  Dashboard - Manage your charities   │
├─────────────────────────────────────┤
│  ┌────────────┐ ┌────────────────┐   │
│  │ 📋 Create  │ │ 👤 Register    │   │
│  │   Event    │ │    Donor       │   │
│  └────────────┘ └────────────────┘   │
│  ┌────────────┐ ┌────────────────┐   │
│  │ 💰 Make    │ │ 📊 View Events │   │
│  │ Donation   │ │                │   │
│  └────────────┘ └────────────────┘   │
│  ┌────────────┐ ┌────────────────┐   │
│  │ 👥 View    │ │ 📈 Statistics  │   │
│  │ Donors     │ │                │   │
│  └────────────┘ └────────────────┘   │
└─────────────────────────────────────┘
```

### Form Screens
- Clean form layouts with styled text fields
- Rounded input boxes with green borders
- Hover effects on buttons
- Error/success notifications

### Table Screens  
- Styled JTable with alternating row colors
- Dark theme with proper contrast
- Sortable columns
- Centered/aligned data

### Statistics Screen
- Large, colorful stat cards
- Green, cyan, and blue accents
- Real-time data from database

---

## 🎯 Color Scheme

```
Background:     #1e1e2f (Dark purple-ish)
Secondary BG:   #2d2d41
Tertiary BG:    #3c3c55

Accent Primary: #4CAF50 (Green)
Accent Secondary: #00ADB5 (Cyan)
Accent Error:   #F44336 (Red)

Text Primary:   #FFFFFF (White)
Text Secondary: #B4B4C8 (Light gray)
Text Muted:     #787C8C (Dark gray)
```

---

## 🔧 Troubleshooting

### Error: "Cannot find FlatLaf"
```
✗ Error: package com.formdev.flatlaf does not exist
```
**Solution:** Download FlatLaf JAR and add it to lib/ folder

### Error: "Cannot find SwingMain class"
```
✗ SwingMain not found
```
**Solution:** Make sure src/SwingMain.java is compiled to bin/

### Error: "ClassNotFoundException"
```
✗ java.lang.ClassNotFoundException
```
**Solution:** Add all JARs to classpath:
```powershell
java -cp "bin;lib\mysql-connector-j-9.6.0.jar;lib\flatlaf-3.4.1.jar" SwingMain
```

### GUI looks ugly / no dark theme
**Solution:** Verify FlatLaf is in classpath and correctly applied
```java
// In SwingMain.java, this should work:
UIManager.setLookAndFeel(new FlatDarkLaf());
```

---

## 📁 Quick File Locations

**Main Entry Point:**
- `src/SwingMain.java` - Run this to start the GUI

**UI Components:**
- `src/ui/UIConstants.java` - Colors and styling
- `src/ui/CustomComponents.java` - Buttons, fields, etc.
- `src/ui/MainWindow.java` - Main application frame
- `src/ui/panels/*.java` - Individual screen panels

**Dependencies:**
- `lib/mysql-connector-j-9.6.0.jar` - MySQL JDBC
- `lib/flatlaf-3.4.1.jar` - Modern look and feel

---

## 🚀 Quick Commands

**Compile Everything:**
```powershell
javac -cp ".;lib\mysql-connector-j-9.6.0.jar;lib\flatlaf-3.4.1.jar" -d bin src\SwingMain.java src\ui\*.java src\ui\panels\*.java src\Main.java src\model\*.java src\dao\*.java src\db\*.java src\controller\*.java src\view\*.java src\service\*.java src\observer\*.java
```

**Run GUI:**
```powershell
java -cp "bin;lib\mysql-connector-j-9.6.0.jar;lib\flatlaf-3.4.1.jar" SwingMain
```

**Run Console Version (Original):**
```powershell
java -cp "bin;lib\mysql-connector-j-9.6.0.jar" Main
```

---

## 📱 UI Workflow

```
SwingMain (Entry Point)
    ↓
MainWindow (Main Frame)
    ↓
CardLayout manages multiple panels:
    ├─ DashboardPanel (Menu)
    │   ├─ → CreateEventPanel
    │   ├─ → RegisterDonorPanel
    │   ├─ → MakeDonationPanel
    │   ├─ → ViewEventsPanel
    │   ├─ → ViewDonorsPanel
    │   └─ → StatisticsPanel
    │
    └─ All panels → back to Dashboard
```

---

## ✨ Features Implemented

✅ Dark theme (FlatLaf)
✅ Modern rounded buttons
✅ Hover effects
✅ Styled text fields with green borders
✅ Proper spacing and alignment
✅ Styled tables with alternating colors
✅ Statistics dashboard with colored cards
✅ Seamless panel switching (CardLayout)
✅ Form validation
✅ Success/error notifications
✅ Database integration
✅ MVC architecture maintained

---

## 📚 Next Steps

1. Download FlatLaf JAR to lib/
2. Compile all files
3. Run SwingMain
4. Test all features through GUI
5. Optionally customize colors in UIConstants.java

---

**Enjoy your modern Swing GUI!** 🎨✨
