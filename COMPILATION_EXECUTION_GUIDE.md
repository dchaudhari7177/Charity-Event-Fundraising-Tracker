# 🚀 COMPILATION & EXECUTION GUIDE

## COMPLETE PROJECT STRUCTURE

```
schmss/
├── src/
│   ├── Main.java ........................ Entry point (MVC pattern)
│   │
│   ├── model/ ........................... POJO Classes
│   │   ├── Event.java
│   │   ├── Donor.java
│   │   └── Pledge.java
│   │
│   ├── controller/ ...................... MVC Controller Layer
│   │   └── CharityTrackerController.java
│   │
│   ├── view/ ............................ MVC View Layer  
│   │   └── ConsoleView.java
│   │
│   ├── service/ ......................... Business Logic & Facade
│   │   ├── EventService.java
│   │   ├── DonorService.java
│   │   ├── PledgeService.java
│   │   └── ServiceFactory.java (Factory Pattern)
│   │
│   ├── dao/ ............................. Data Access Objects
│   │   ├── EventDAO.java
│   │   ├── DonorDAO.java
│   │   └── PledgeDAO.java
│   │
│   ├── db/ .............................. Database Management
│   │   └── DBConnection.java (Singleton Pattern)
│   │
│   └── observer/ ........................ Observer Pattern
│       ├── DonationObserver.java (Interface)
│       ├── EventProgressTracker.java (Concrete Observer)
│       └── DonationEventManager.java (Observable - Singleton)
│
├── lib/ ................................. External Libraries
│   └── mysql-connector-j-9.6.0.jar (JDBC Driver - YOU MUST ADD THIS)
│
├── bin/ ................................. Compiled .class files (auto-generated)
│
└── Documentation/
    ├── OOAD_ACADEMIC_REPORT.md ........ Academic report
    ├── UML_DIAGRAMS.md ................ UML specifications
    ├── SOLID_AND_PATTERNS_DETAILED.md  Pattern explanations
    └── [Other guides]
```

---

## 📋 PRE-REQUISITES

### 1. System Requirements
- Java JDK 8 or higher
- MySQL Server (or Docker container)
- JDBC Driver (mysql-connector-j)
- ~500MB disk space

### 2. Install Java JDK
**Windows/Mac/Linux:**
- Download from: https://www.oracle.com/java/technologies/downloads/
- Install and verify: `java -version`

### 3. MySQL Setup
**Option A: Local MySQL**
- Download from: https://dev.mysql.com/downloads/mysql/
- Install and start service
- Create database: `CREATE DATABASE charity_db;`

**Option B: Docker (Recommended - Easier)**
```bash
docker run --name mysql_container \
  -e MYSQL_ROOT_PASSWORD=root \
  -p 3306:3306 \
  -d mysql:latest

# Wait 30 seconds for startup!
```

### 4. JDBC Driver
**CRITICAL:** Must be version 9.6.0!

- Download: https://dev.mysql.com/downloads/connector/j/
- Select "Platform Independent" version
- Extract `mysql-connector-j-9.6.0.jar`
- Place in: `lib/` folder (create if not exists)

**Verify:**
```bash
dir lib\mysql-connector-j-9.6.0.jar  # Windows
ls lib/mysql-connector-j-9.6.0.jar   # Mac/Linux
```

---

## 🔧 COMPILATION

### Step 1: Verify Folder Structure

```bash
cd c:\Users\Dipak\Desktop\OOAD\schmss
```

Check files exist:
```bash
dir src\Main.java
dir src\model\*.java
dir src\dao\*.java
dir src\db\*.java
dir src\service\*.java
dir src\controller\*.java
dir src\view\*.java
dir src\observer\*.java
dir lib\mysql-connector-j-9.6.0.jar
```

All files should exist! ✓

### Step 2: Clean Previous Builds (Optional)

```bash
# Windows
rmdir /s /q bin
mkdir bin

# Mac/Linux
rm -rf bin
mkdir bin
```

### Step 3: Compile All Classes

**Windows:**
```powershell
javac -cp ".;lib\mysql-connector-j-9.6.0.jar" -d bin `
    src\Main.java `
    src\model\*.java `
    src\dao\*.java `
    src\db\*.java `
    src\service\*.java `
    src\controller\*.java `
    src\view\*.java `
    src\observer\*.java
```

**One-line Windows (PowerShell):**
```powershell
javac -cp ".;lib\mysql-connector-j-9.6.0.jar" -d bin src\Main.java src\model\*.java src\dao\*.java src\db\*.java src\service\*.java src\controller\*.java src\view\*.java src\observer\*.java
```

**Mac/Linux:**
```bash
javac -cp ".:lib/mysql-connector-j-9.6.0.jar" -d bin \
    src/Main.java \
    src/model/*.java \
    src/dao/*.java \
    src/db/*.java \
    src/service/*.java \
    src/controller/*.java \
    src/view/*.java \
    src/observer/*.java
```

### Step 4: Verify Compilation

**Windows:**
```powershell
dir bin\*.class -Recurse
```

**Mac/Linux:**
```bash
find bin -name "*.class" | wc -l
```

Expected output: ~25 .class files

**If you see errors, check:**
1. JDBC jar file exists and path is correct
2. All source files exist
3. Java version is 8+

---

## ▶️ EXECUTION

### Prerequisites Check

1. **MySQL is running:**
```bash
# Windows
netstat -ano | findstr :3306

# Mac/Linux  
lsof -i :3306
```

Should show MySQL listening on :3306

2. **JDBC driver is in lib:**
```bash
# Windows
dir lib\mysql-connector-j-9.6.0.jar

# Mac/Linux
ls lib/mysql-connector-j-9.6.0.jar
```

3. **Compiled classes exist:**
```bash
# Windows
dir bin\Main.class

# Mac/Linux
ls bin/Main.class
```

### Run Application

**Windows (PowerShell):**
```powershell
java -cp "bin;lib\mysql-connector-j-9.6.0.jar" Main
```

**Mac/Linux (Bash):**
```bash
java -cp "bin:lib/mysql-connector-j-9.6.0.jar" Main
```

### Expected Startup Output

```
╔════════════════════════════════════════════════════════════╗
║   CHARITY EVENT FUNDRAISING TRACKER - DATABASE VERSION  ║
║      (MVC Architecture with Design Patterns)            ║
╚════════════════════════════════════════════════════════════╝

→ Initializing database...
? Database connection established successfully!
? Events table ready.
? Donors table ready.
? Pledges table ready.
? Database initialization complete!


╔════════════════════════════════════════════╗
║         MAIN MENU                          ║
╠════════════════════════════════════════════╣
║ 1. Create New Event                        ║
║ 2. Register Donor                          ║
║ 3. Make Donation to Event                  ║
║ 4. View All Events                         ║
║ 5. View Donors of Specific Event           ║
║ 6. View All Donors                         ║
║ 7. View Statistics                         ║
║ 8. Exit                                    ║
╚════════════════════════════════════════════╝
Enter your choice:
```

---

## 🧪 TESTING THE APPLICATION

### Test Scenario: Complete Workflow

**Step 1: Create Event**
```
Enter your choice: 1

┌─ CREATE NEW EVENT ────────────────────────────┐
│ Enter Event Name: Clean Water Initiative
│ Enter Target Amount (Rs.): 100000
│ Enter Description: Providing clean water access to rural areas
│ ✓ Event created successfully!
└───────────────────────────────────────────────┘
```

**Step 2: Register Donor**
```
Enter your choice: 2

┌─ REGISTER DONOR ──────────────────────────────┐
│ Enter Donor Name: John Doe
│ Enter Email: john@example.com
│ Enter Phone Number: 9876543210
│ ✓ Donor registered successfully!
└───────────────────────────────────────────────┘
```

**Step 3: Make Donation**
```
Enter your choice: 3

┌─ MAKE DONATION ───────────────────────────────┐
│ Enter Donor ID: 1
│ Enter Event ID: 1
│ Enter Donation Amount (Rs.): 25000
│ Enter Date (YYYY-MM-DD): 2026-04-12
│ ✓ Donation recorded!

┌─ DONATION UPDATE ─────────────────────────────┐
│ Tracker: System Tracker
│ Event: Clean Water Initiative
│ New Donation: ₹25000.00
│ Total Collected: ₹25000.00
└───────────────────────────────────────────────┘

→ [Clean Water Initiative] Progress: [█████░░░░░░░░░░░░░░] 25.0%
└───────────────────────────────────────────────┘
```

**Step 4: View All Events**
```
Enter your choice: 4

╔════════════════════════════════════════════════════════╗
║              ALL EVENTS                                 ║
╠════════════════════════════════════════════════════════╣
│ ID: 1
│ Name: Clean Water Initiative
│ Target: ₹100000.00
│ Collected: ₹25000.00
│ Progress: [█████░░░░░░░░░░░░░░] 25.0%
│ Description: Providing clean water access to rural areas
├────────────────────────────────────────────────────────┤
╚════════════════════════════════════════════════════════╝
```

**Step 5: Make Another Donation**
```
Enter your choice: 3
Enter Donor ID: 1
Enter Event ID: 1
Enter Donation Amount (Rs.): 75000

┌─ DONATION UPDATE ─────────────────────────────┐
│ Tracker: System Tracker
│ Event: Clean Water Initiative
│ New Donation: ₹75000.00
│ Total Collected: ₹100000.00
└───────────────────────────────────────────────┘

╔═════════════════════════════════════════════════╗
║         🎉 GOAL REACHED! 🎉                     ║
║ Event: Clean Water Initiative
║ Target: ₹100000.00 ACHIEVED
╚═════════════════════════════════════════════════╝
```

**Step 6: View Statistics**
```
Enter your choice: 7

╔════════════════════════════════════════════════════════╗
║              SYSTEM STATISTICS                          ║
╠════════════════════════════════════════════════════════╣
│ Total Events: 1
│ Total Donors: 1
│ Total Donations: 2
│ Total Funds Collected: ₹100000.00
╚════════════════════════════════════════════════════════╝
```

**Step 7: Exit**
```
Enter your choice: 8

╔════════════════════════════════════════════════════════╗
║       Thank you for using the Charity Tracker!         ║
║                  Goodbye!                              ║
╚════════════════════════════════════════════════════════╝
```

---

## 🐛 TROUBLESHOOTING

### Problem: "mysql-connector-j JAR not found"

**Cause:** JDBC driver missing or wrong path

**Fix:**
```bash
# 1. Download from: https://dev.mysql.com/downloads/connector/j/
# 2. Extract and place in lib/ folder
# 3. Verify:
dir lib\mysql-connector-j-9.6.0.jar

# 4. Check exact filename - must be 9.6.0!
# If different version:
#   - Download correct version OR
#   - Update command to use your version
#   - Example: lib\mysql-connector-j-8.0.33.jar
```

### Problem: "Connection refused"

**Cause:** MySQL not running

**Fix:**
```bash
# Check if running
netstat -ano | findstr :3306

# If not running, start it:
docker start mysql_container

# Or start local MySQL service
# Windows: Services → MySQL → Start
# Mac: MySQL Workbench → Preferences → Start Server
# Linux: sudo systemctl start mysql
```

### Problem: Port 3306 already in use

**Cause:** MySQL already running or another service using port

**Fix:**
```bash
# Check what's using the port
netstat -ano | findstr :3306

# Either stop existing MySQL:
docker stop mysql_container

# Or use different port in DBConnection.java
# Line: jdbc:mysql://localhost:3307/charity_db  (change 3306 to 3307)
```

### Problem: "Cannot find symbol: EventDAO"

**Cause:** DAO files not compiled

**Fix:**
```bash
# Verify all source files compiled:
dir bin\dao\*.class
dir bin\model\*.class

# If empty, recompile with explicit paths:
javac -cp ".;lib\mysql-connector-j-9.6.0.jar" -d bin ^
  src\*\*.java src\Main.java
```

### Problem: "Class not found: CharityTrackerController"

**Cause:** Controller not compiled or not in classpath

**Fix:**
```bash
# Check if compiled:
dir bin\controller\*.class

# If missing, recompile EVERYTHING:
javac -cp ".;lib\mysql-connector-j-9.6.0.jar" -d bin `
  src\Main.java src\model\*.java src\dao\*.java src\db\*.java `
  src\service\*.java src\controller\*.java src\view\*.java `
  src\observer\*.java
```

---

## 📊 VERIFYING MVC ARCHITECTURE

To verify file is using MVC, check these locations exist:

1. **Model layer:**
   - [ ] `bin/model/Event.class`
   - [ ] `bin/model/Donor.class`
   - [ ] `bin/model/Pledge.class`

2. **View layer:**
   - [ ] `bin/view/ConsoleView.class`

3. **Controller layer:**
   - [ ] `bin/controller/CharityTrackerController.class`

4. **Service layer (Facade):**
   - [ ] `bin/service/EventService.class`
   - [ ] `bin/service/DonorService.class`
   - [ ] `bin/service/PledgeService.class`
   - [ ] `bin/service/ServiceFactory.class`

5. **DAO layer:**
   - [ ] `bin/dao/EventDAO.class`
   - [ ] `bin/dao/DonorDAO.class`
   - [ ] `bin/dao/PledgeDAO.class`

6. **Observer pattern:**
   - [ ] `bin/observer/DonationObserver.class`
   - [ ] `bin/observer/EventProgressTracker.class`
   - [ ] `bin/observer/DonationEventManager.class`

7. **Database (Singleton):**
   - [ ] `bin/db/DBConnection.class`

If all exist → **Architecture is properly implemented!** ✓

---

## 📦 CREATING STANDALONE JAR

To package as standalone JAR:

```bash
# Create manifest
echo Main-Class: Main > manifest.txt
echo Class-Path: lib/mysql-connector-j-9.6.0.jar >> manifest.txt

# Create JAR
jar cvfm charity-tracker.jar manifest.txt -C bin .

# Run JAR
java -cp "charity-tracker.jar;lib\mysql-connector-j-9.6.0.jar" Main
```

---

## ✅ FINAL CHECKLIST

Before submitting project:

- [ ] All Java files compile without errors
- [ ] All classes are in correct packages (model/, dao/, service/, etc.)
- [ ] Database initializes on startup
- [ ] Can create events
- [ ] Can register donors
- [ ] Can make donations
- [ ] Progress bar updates
- [ ] Goal reached notification works
- [ ] Statistics display correctly
- [ ] Application exits cleanly
- [ ] JDBC driver is java-connector-j-9.6.0.jar
- [ ] MySQL is running
- [ ] All documentation files present
- [ ] UML diagrams are included
- [ ] Academic report is complete
- [ ] SOLID principles are documented
- [ ] Design patterns are documented

---

**Ready to submit!** 🎉
