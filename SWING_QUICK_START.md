# ⚡ SWING GUI - QUICK REFERENCE

## 📥 SETUP (5 MINUTES)

### 1. Download FlatLaf
```
URL: https://www.formdev.com/flatlaf/
Download: flatlaf-3.4.1.jar
Save to: lib/flatlaf-3.4.1.jar
```

### 2. Verify Files
```powershell
dir src\ui\*.java           # Should show UIConstants.java, CustomComponents.java, MainWindow.java
dir src\ui\panels\          # Should show 7 panel files
dir src\SwingMain.java      # Should exist
dir lib\flatlaf-3.4.1.jar   # Should exist
```

### 3. Compile
```powershell
javac -cp ".;lib\mysql-connector-j-9.6.0.jar;lib\flatlaf-3.4.1.jar" -d bin `
    src\SwingMain.java src\ui\*.java src\ui\panels\*.java `
    src\Main.java src\model\*.java src\dao\*.java src\db\*.java `
    src\controller\*.java src\view\*.java src\service\*.java src\observer\*.java
```

### 4. Run
```powershell
java -cp "bin;lib\mysql-connector-j-9.6.0.jar;lib\flatlaf-3.4.1.jar" SwingMain
```

---

## 🎨 UI SCREENS

| Screen | File | Purpose |
|--------|------|---------|
| Dashboard | DashboardPanel.java | Main menu (6 cards) |
| Create Event | CreateEventPanel.java | Add new event |
| Register Donor | RegisterDonorPanel.java | Add new donor |
| Make Donation | MakeDonationPanel.java | Record donation |
| View Events | ViewEventsPanel.java | Events table |
| View Donors | ViewDonorsPanel.java | Donors table |
| Statistics | StatisticsPanel.java | Stats dashboard |

---

## 📂 FILE STRUCTURE

```
src/ui/
├── UIConstants.java ........... Colors, fonts, dimensions
├── CustomComponents.java ...... Buttons, fields, cards
├── MainWindow.java ............ Main frame
└── panels/
    ├── DashboardPanel.java
    ├── CreateEventPanel.java
    ├── RegisterDonorPanel.java
    ├── MakeDonationPanel.java
    ├── ViewEventsPanel.java
    ├── ViewDonorsPanel.java
    └── StatisticsPanel.java

src/SwingMain.java ............. Entry point
```

---

## 🎨 COLORS

| Element | Color | Code |
|---------|-------|------|
| Background | Dark Purple | #1e1e2f |
| Secondary BG | Medium Purple | #2d2d41 |
| Tertiary BG | Light Purple | #3c3c55 |
| Primary Accent | Green | #4CAF50 |
| Secondary Accent | Cyan | #00ADB5 |
| Error | Red | #F44336 |
| Text Primary | White | #FFFFFF |
| Text Secondary | Light Gray | #B4B4C8 |

---

## ⌨️ FONTS

- Family: Segoe UI
- Title: 24pt bold
- Heading: 18pt bold
- Body: 13pt regular
- Small: 11pt regular
- Button: 13pt bold

---

## 🔄 NAVIGATION

```
Dashboard
├─ Create Event → Save → Dashboard
├─ Register Donor → Save → Dashboard
├─ Make Donation → Save → Dashboard
├─ View Events → Back → Dashboard
├─ View Donors → Back → Dashboard
└─ Statistics → Back → Dashboard
```

---

## 🧪 QUICK TEST

1. **Run GUI**
   ```powershell
   java -cp "bin;lib\mysql-connector-j-9.6.0.jar;lib\flatlaf-3.4.1.jar" SwingMain
   ```

2. **Click "Create Event"**
   - Name: "Clean Water"
   - Amount: 100000
   - Description: "Water project"
   - Click "Create Event"

3. **Click "Register Donor"**
   - Name: "John Doe"
   - Email: john@example.com
   - Phone: 9876543210
   - Click "Register Donor"

4. **Click "Make Donation"**
   - Select donor: "1 - John Doe"
   - Select event: "1 - Clean Water"
   - Amount: 25000
   - Click "Make Donation"

5. **Click "View Events"**
   - See event with 25% progress

6. **Click "View Donors"**
   - See registered donor

7. **Click "Statistics"**
   - See totals: 1 event, 1 donor, 1 donation, ₹25000

---

## ❌ TROUBLESHOOTING

| Problem | Solution |
|---------|----------|
| FlatLaf not found | Download from https://www.formdev.com/flatlaf/ and place in lib/ |
| SwingMain not found | Compile src/SwingMain.java |
| Class not found | Verify all files compiled to bin/ |
| GUI looks ugly | Ensure FlatLaf is in classpath |
| Database error | Check MySQL is running on localhost:3306 |

---

## 📚 DOCUMENTATION

- `SWING_GUI_SETUP.md` - Complete setup guide
- `SWING_UI_COMPLETE.md` - Full implementation details  
- `UIConstants.java` - All styling constants
- `CustomComponents.java` - Component definitions

---

## ✨ FEATURES

✅ Dark theme with FlatLaf
✅ Rounded buttons with hover effects
✅ Styled text fields
✅ Professional tables
✅ Statistics dashboard
✅ Form validation
✅ MVC architecture
✅ Smooth navigation
✅ Real-time database updates

---

## 📊 STATS

- UI Files: 11
- Lines of Code: ~1,500
- Screens: 7
- Colors: 10+
- Fonts: 6
- Components: 10+

---

## 🚀 ONE-LINER

**Full compile and run:**
```powershell
javac -cp ".;lib\mysql-connector-j-9.6.0.jar;lib\flatlaf-3.4.1.jar" -d bin src\SwingMain.java src\ui\*.java src\ui\panels\*.java src\Main.java src\model\*.java src\dao\*.java src\db\*.java src\controller\*.java src\view\*.java src\service\*.java src\observer\*.java ; java -cp "bin;lib\mysql-connector-j-9.6.0.jar;lib\flatlaf-3.4.1.jar" SwingMain
```

---

**Ready to launch!** 🎉
