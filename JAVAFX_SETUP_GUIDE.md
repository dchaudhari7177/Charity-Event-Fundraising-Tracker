# JavaFX Application - Complete Setup Guide

## Step 1: Download JavaFX SDK 21.0.2

JavaFX SDK 21.0.2 is compatible with Java 21 (which you have).

1. Visit: **https://gluonhq.com/products/javafx/**
2. Click "Download"  
3. Select **JavaFX SDK 21.0.2** for Windows
4. Extract to: `lib/javafx-sdk-21.0.2/`

Your directory structure should look like:
```
lib/
├── mysql-connector-j-9.6.0.jar
└── javafx-sdk-21.0.2/
    ├── lib/
    │   ├── javafx.controls.jar
    │   ├── javafx.fxml.jar
    │   ├── javafx.graphics.jar
    │   └── ... (other jars)
    ├── bin/
    └── ... (other files)
```

## Step 2: Verify Database Setup

### MySQL Database Requirements

1. **MySQL must be running** on your system
2. **Connection details:**
   - Host: `localhost`
   - Port: `3306`
   - Username: `root`
   - Password: `root`

### Verify Database Connection

Run this to test:
```bash
mysql -h localhost -u root -proot -e "SELECT 1;"
```

Should output: `1`

### Database Schema

The application uses these tables (should already exist):
- `donors` (id, name, email, phone_number)
- `events` (id, name, description, target_amount, collected_amount)
- `pledges` (id, donor_id, event_id, amount, pledge_date)

If tables don't exist, create them:
```sql
CREATE DATABASE IF NOT EXISTS charity_db;
USE charity_db;

CREATE TABLE donors (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100),
  phone_number VARCHAR(20)
);

CREATE TABLE events (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  description TEXT,
  target_amount DECIMAL(10, 2),
  collected_amount DECIMAL(10, 2) DEFAULT 0
);

CREATE TABLE pledges (
  id INT AUTO_INCREMENT PRIMARY KEY,
  donor_id INT NOT NULL,
  event_id INT NOT NULL,
  amount DECIMAL(10, 2),
  pledge_date DATE,
  FOREIGN KEY (donor_id) REFERENCES donors(id),
  FOREIGN KEY (event_id) REFERENCES events(id)
);
```

## Step 3: Prepare Project Directory

Ensure your directory structure is:
```
schmss/
├── src/
│   ├── javafx/                 (NEW)
│   │   ├── app/
│   │   ├── controller/
│   │   ├── fxml/
│   │   ├── resources/
│   │   └── util/
│   ├── model/
│   ├── dao/
│   ├── db/
│   └── ... (other packages)
├── lib/
│   ├── javafx-sdk-21.0.1/      (NEW - extracted ZIP)
│   └── mysql-connector-j-9.6.0.jar
├── bin/                        (auto-created)
├── compile_javafx.bat          (NEW)
├── run_javafx.bat              (NEW)
└── ... (documentation files)
```

## Step 4: Compile the Application

### Option A: Use Batch Script (Windows - RECOMMENDED)

```bash
cd C:\Users\Dipak\Desktop\OOAD\schmss
compile_javafx.bat
```

### Option B: Manual Compilation (PowerShell)

```powershell
cd C:\Users\Dipak\Desktop\OOAD\schmss

# Copy resources
xcopy /Y src\javafx\fxml\*.fxml bin\javafx\fxml\
xcopy /Y src\javafx\resources\*.css bin\javafx\resources\

# Compile
javac -cp "bin;lib\mysql-connector-j-9.6.0.jar;lib\javafx-sdk-21.0.1\lib\*" -d bin `
  src\javafx\app\CharityTrackerApp.java `
  src\javafx\controller\MainController.java `
  src\javafx\controller\DashboardController.java `
  src\javafx\controller\CreateEventController.java `
  src\javafx\controller\RegisterDonorController.java `
  src\javafx\controller\RecordDonationController.java `
  src\javafx\controller\EventsTableController.java `
  src\javafx\controller\DonorsTableController.java `
  src\javafx\controller\StatisticsController.java `
  src\javafx\util\ThemeConstants.java `
  src\javafx\util\AnimationUtils.java `
  src\model\Donor.java `
  src\model\Event.java `
  src\model\Pledge.java `
  src\dao\DonorDAO.java `
  src\dao\EventDAO.java `
  src\dao\PledgeDAO.java `
  src\db\DBConnection.java
```

### Troubleshooting Compilation

**Error: Cannot find symbol "javafx"**
- Solution: Ensure `lib/javafx-sdk-21.0.1/` exists with full path to `lib/` subdirectory

**Error: Cannot find file "*.fxml"**
- Solution: Create `bin/javafx/fxml/` directory manually and copy FXML files

**Error: Compilation succeeded but no class files**
- Solution: Check that `-d bin` parameter is correct, bin directory has write permissions

## Step 5: Run the Application

### Option A: Use Batch Script (Windows - RECOMMENDED)

```bash
run_javafx.bat
```

### Option B: Manual Execution (PowerShell)

```powershell
cd C:\Users\Dipak\Desktop\OOAD\schmss

java -cp "bin;lib\mysql-connector-j-9.6.0.jar;lib\javafx-sdk-21.0.1\lib\*" `
  --module-path lib\javafx-sdk-21.0.1\lib `
  --add-modules javafx.controls,javafx.fxml,javafx.graphics `
  javafx.app.CharityTrackerApp
```

## Step 6: Verify Application

After startup, you should see:
1. Window titled: "Charity Tracker - Event Management System"
2. Sidebar with navigation buttons
3. Dashboard screen with stat cards
4. "✓ Charity Tracker JavaFX Application Started" in console

## Common Issues & Solutions

### Issue 1: "File not found: javafx-sdk-21.0.1"
**Solution**: 
- Download JavaFX from https://gluonhq.com/products/javafx/
- Extract to `lib/javafx-sdk-21.0.1/`
- Verify path: `lib/javafx-sdk-21.0.1/lib/javafx.controls.jar` should exist

### Issue 2: "Cannot connect to database"
**Solution**:
- Start MySQL service: `net start MySQL80` (or your MySQL service name)
- Test connection: `mysql -h localhost -u root -proot -e "SELECT 1;"`
- Check credentials in `src/db/DBConnection.java`

### Issue 3: "Module not found: javafx.controls"
**Solution**:
- Ensure `--module-path` and `--add-modules` are in run command
- Check JavaFX SDK version is 21.0.1+

### Issue 4: "FXML Load Exception"
**Solution**:
- Verify FXML files are in `bin/javafx/fxml/` directory
- Check file names match exactly (case-sensitive on Linux/Mac)

### Issue 5: Window appears but no content
**Solution**:
- Verify CSS files are in `bin/javafx/resources/` directory
- Check browser console for errors
- Restart application

### Issue 6: "Application stopped unexpectedly"
**Solution**:
- Check console output for error messages
- Verify MySQL is running: `mysql -h localhost -u root -proot -e "SHOW TABLES;"`
- Check all DAO classes can connect to database

## Linux/Mac Instructions

### Compile (Bash)

```bash
cd /path/to/schmss

# Copy resources
cp src/javafx/fxml/*.fxml bin/javafx/fxml/
cp src/javafx/resources/*.css bin/javafx/resources/

# Compile
javac -cp "bin:lib/mysql-connector-j-9.6.0.jar:lib/javafx-sdk-21.0.2/lib/*" -d bin \
  src/javafx/app/CharityTrackerApp.java \
  src/javafx/controller/*.java \
  src/javafx/util/*.java \
  src/model/*.java \
  src/dao/*.java \
  src/db/*.java
```

### Run (Bash)

```bash
java -cp "bin:lib/mysql-connector-j-9.6.0.jar:lib/javafx-sdk-21.0.2/lib/*" \
  --module-path lib/javafx-sdk-21.0.2/lib \
  --add-modules javafx.controls,javafx.fxml,javafx.graphics \
  javafx.app.CharityTrackerApp
```

## Features Checklist

After successful startup, verify:
- ✓ Sidebar navigation visible
- ✓ Dashboard with 4 stat cards
- ✓ Quick action cards clickable
- ✓ Can navigate to all 7 screens
- ✓ Forms have validation
- ✓ Tables load data from database
- ✓ Dark theme with neon colors
- ✓ Hover effects on buttons
- ✓ Animations smooth (fade-in on load)

## Additional Resources

- **JavaFX Documentation**: https://openjfx.io/
- **FXML Documentation**: https://openjfx.io/javadoc/21/
- **CSS Reference**: https://openjfx.io/javadoc/21/javafx.graphics/javafx/scene/doc-files/cssref.html
- **MySQL Documentation**: https://dev.mysql.com/doc/

## Getting Help

If you encounter issues:
1. Check console output for specific error messages
2. Review log files in `logs/` directory (if created)
3. Verify all dependencies are installed
4. Check that database schema matches expected structure
5. Ensure ports are not blocked (MySQL on 3306)

---

**Created**: April 2024
**JavaFX Version**: 21.0.1+
**Java Version**: 11+
**Status**: Production Ready
