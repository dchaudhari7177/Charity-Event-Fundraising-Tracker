# 🔧 Troubleshooting Guide

## Issue 1: "javac: command not found" or "javac is not recognized"

**Problem:** Java compiler is not installed or not in PATH

**Solution:**

### Windows:
1. Download Java JDK (not JRE) from oracle.com or jdk.java.net
2. Install Java JDK
3. Add Java to PATH:
   - Right-click "This PC" → Properties
   - Click "Advanced system settings"
   - Click "Environment Variables"
   - Under "System variables", click "New"
   - Variable name: `JAVA_HOME`
   - Variable value: `C:\Program Files\Java\jdk-17` (your Java path)
   - Click OK
4. Verify: Open new Command Prompt, type `javac -version`

### Mac/Linux:
```bash
# Check if Java is installed
java -version
javac -version

# If not installed
# Mac: brew install openjdk@17
# Linux: sudo apt-get install openjdk-17-jdk
```

---

## Issue 2: Files Won't Compile - Syntax Error

**Problem:** Error like "error: ';' expected"

**Solutions:**

1. **Check file encoding (UTF-8):**
   ```bash
   # Windows: Check if file has BOM
   # Delete and recreate if needed
   ```

2. **Verify Java syntax:**
   - Every class definition needs `{}`
   - Every method needs `{}`
   - Every statement ends with `;`
   - Check for unclosed `{` or `}`

3. **Common mistakes:**
   ```java
   // ❌ WRONG
   public class Event {
       private String name
   }  // Missing semicolon

   // ✅ CORRECT
   public class Event {
       private String name;
   }
   ```

4. **If still stuck:**
   ```bash
   # Delete all .class files and recompile
   del /s bin\*.class
   javac -d bin src\*.java
   ```

---

## Issue 3: Compile Successful but "Main class not found" when running

**Problem:** Classes compiled but Main class not found

**Causes & Solutions:**

1. **Wrong classpath:**
   ```bash
   # ❌ WRONG
   java Main

   # ✅ CORRECT
   java -cp bin Main
   ```

2. **Main.class not in bin directory:**
   ```bash
   # Verify all .class files exist
   dir bin

   # Should show:
   # Event.class
   # Donor.class
   # Pledge.class
   # FundraisingSystem.class
   # Main.class
   ```

3. **Recompile everything:**
   ```bash
   javac -d bin src\*.java
   java -cp bin Main
   ```

---

## Issue 4: Program Crashes During Runtime

**Problem:** Program runs but crashes with an error

**Common causes:**

1. **NullPointerException:**
   ```
   Error: Cannot read field "eventId" because "event" is null
   ```
   **Solution:** Don't try to use IDs that don't exist
   - For donors, use DOR1, DOR2, etc. (not DOR99)
   - For events, use EVT1, EVT2, etc. (not EVT99)

2. **Input Mismatch Exception:**
   ```
   Error: java.util.InputMismatchException
   ```
   **Solution:** You entered text when a number was expected
   - When asked for amount, enter only numbers
   - When asked for name, enter only text

3. **ArrayIndexOutOfBoundsException:**
   ```
   Error: Index out of range
   ```
   **Solution:** Corruption in ArrayList
   - Restart the program
   - If persists, recompile

---

## Issue 5: Menu Loops Forever or Acts Strange

**Problem:** Program seems stuck or menu behaves oddly

**Causes:**

1. **Leftover input in buffer:**
   ```
   Solution: The program skips menu and jumps to input
   This happens with scanner.nextInt() followed by scanner.nextLine()
   ✓ Already handled in code (scanner.nextLine() calls)
   ```

2. **Infinite loop:**
   ```bash
   Solution: Press Ctrl+C to stop
   Then run again with compile.bat
   ```

---

## Issue 6: Modified Code Won't Recompile

**Problem:** After editing a .java file, compile fails

**Solution:**

1. **Check closing braces match:**
   ```java
   // Every { must have a matching }
   // Use proper indentation to see mismatches
   ```

2. **Check method signatures:**
   ```java
   // ❌ WRONG
   public void addEvent(String name, amount) { }

   // ✅ CORRECT
   public void addEvent(String name, double amount) { }
   ```

3. **Clean and rebuild:**
   ```bash
   # Delete bin directory
   rmdir /s bin
   mkdir bin
   # Recompile
   javac -d bin src\*.java
   ```

---

## Issue 7: Collections Not Storing Data

**Problem:** Added data doesn't appear when viewing

**Causes:**

1. **Using different IDs:**
   - First event created: EVT1
   - If you look for EVT2 that doesn't exist, it won't show

2. **Data cleared between runs:**
   - All data is in memory only
   - Restarting program = clear data
   - This is normal behavior (no database)

3. **Progress not updating:**
   ```java
   // Make sure donation is to same event
   Create Event: EVT1
   Make Donation to EVT1  ✓ (will update)
   Make Donation to EVT2  ✗ (EVT1 won't change)
   ```

---

## Issue 8: Validation Not Working

**Problem:** The program accepts negative amounts or invalid data

**Cause:** Validation might have been disabled accidentally

**Fix:**
```java
// Check FundraisingSystem.java has these checks:
if (amount <= 0) {
    System.out.println("Amount must be positive!");
    return false;
}
```

If missing, add it back to the makePledge() method.

---

## Issue 9: Wrong Output Format

**Problem:** Numbers show with too many decimals

**Example:**
```
Expected: Amount: Rs. 5000
Actual: Amount: Rs. 5000.0
```

**Solution:** Already handled with String.format() in code. If not working:
```java
// Use:
System.out.printf("Amount: Rs. %.2f\n", amount);

// Not:
System.out.println("Amount: Rs. " + amount);
```

---

## Issue 10: Cannot Create Event/Donor with Special Characters

**Problem:** Program accepts but doesn't work with names like "O'Brien"

**Cause:** String handling issue

**Solution:** This should work. If not:
1. Recompile
2. If persists, file might have encoding issue

---

## Quick Diagnostic Checklist

- [ ] `javac -version` works (Java installed)
- [ ] `cd c:\Users\Dipak\Desktop\OOAD\schmss` changes to project
- [ ] `compile.bat` runs without errors
- [ ] `java -cp bin Main` starts the program
- [ ] Menu displays (1-8 options visible)
- [ ] Can create event (Option 1)
- [ ] Can register donor (Option 2)
- [ ] Can make donation (Option 3)
- [ ] Can view events (Option 4)
- [ ] Can exit cleanly (Option 8)

## Verification Steps

### Step 1: Verify Java Installation
```bash
java -version
javac -version
```
Should show version 8 or higher

### Step 2: Verify Project Files
```bash
cd c:\Users\Dipak\Desktop\OOAD\schmss
dir src\*.java
```
Should show 5 files: Event.java, Donor.java, Pledge.java, etc.

### Step 3: Clean Compile
```bash
rmdir /s bin
mkdir bin
javac -d bin src\*.java
echo SUCCESS!
```

### Step 4: Run Program
```bash
java -cp bin Main
# You should see the welcome message
# Try creating an event
```

---

## Getting Help

If you're stuck:

1. **Read the error message carefully** - Java error messages are usually very specific
2. **Google the error** - 99% of Java errors have solutions online
3. **Check syntax** - Most issues are missing `;`, mismatched `{}`, or type mismatches
4. **Review IMPLEMENTATION_DETAILS.md** - Explains all the code logic
5. **Look at SAMPLE_USAGE.md** - Shows expected behavior

---

## Common Error Messages & Solutions

| Error | Cause | Solution |
|-------|-------|----------|
| "cannot find symbol" | Typo in variable name | Check spelling |
| "incompatible types" | Wrong data type | Check variable type |
| "error: ';' expected" | Missing semicolon | Add `;` at end of statement |
| "class Event is public, should be declared in a file named Event.java" | File name mismatch | Rename file to match class |
| "'.class' expected" | Extra characters in filename | Remove spaces/special chars |
| InputMismatchException | Wrong input type | Enter number when asked, not text |
| NullPointerException | Using non-existent object | Check if donor/event exists |

---

## Performance Issues

| Issue | Cause | Solution |
|-------|-------|----------|
| Program slow | Too many entries | Expected for 100K+ records; use database |
| Memory usage high | ArrayList growing large | Normal; restart to clear |
| Compilation slow | First time | Normal; subsequent compiles faster |

---

## Platform-Specific Issues

### Windows:
- Use `\` in paths or `/` (both work)
- Use `del` for delete, `dir` for list
- compile.bat is recommended

### Mac/Linux:
- Use `/` in paths
- Use `rm` for delete, `ls` for list
- Use compile.sh or manual commands

---

## If All Else Fails

1. Delete everything and re-download
2. Check Java version is 8+
3. Try a different IDE (VS Code, IntelliJ, Eclipse)
4. Create fresh src directory with new files
5. Recompile from scratch

---

## Testing Each Feature

### Test Event Creation:
1. Menu: 1
2. Name: "Test"
3. Amount: 1000
4. Desc: "test"
5. Should see: "Event 'Test' created successfully"

### Test Donor Registration:
1. Menu: 2
2. Name: "John"
3. Email: "john@test.com"
4. Phone: "1234567890"
5. Should see: "Donor 'John' registered successfully"

### Test Donation:
1. Menu: 3
2. Donor ID: "DOR1"
3. Event ID: "EVT1"
4. Amount: 100
5. Should see: "Donation of Rs. 100 recorded successfully"

### Test Viewing:
1. Menu: 4
2. Should see: Events with progress %
3. Menu: 5
4. Event ID: "EVT1"
5. Should see: Donor list

---

## Success Indicators

✅ Program compiled without errors
✅ Program runs without crashing
✅ Menu displays all 8 options
✅ Can create event with auto-generated ID
✅ Can register donor with auto-generated ID
✅ Can make donation and see progress update
✅ Can view all events with progress percentage
✅ Can see donor list for specific event
✅ Validation prevents negative amounts
✅ Program exits cleanly on menu option 8

If all above work → **Project is working perfectly!** 🎉

---

## Prevention Tips

- ✅ Always recompile after making changes
- ✅ Use proper file names (exact case matching)
- ✅ Keep backup copies of working code
- ✅ Read error messages fully
- ✅ Test after each change
- ✅ Don't ignore compiler warnings
- ✅ Keep bin directory for fast recompass

---

**Need help? Check files in this order:**
1. QUICK_START.md - Basic setup
2. SAMPLE_USAGE.md - Expected behavior
3. IMPLEMENTATION_DETAILS.md - How it works
4. This file - Specific problems

Good luck! 🚀
