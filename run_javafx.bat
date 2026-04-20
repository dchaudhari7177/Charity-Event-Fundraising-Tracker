@echo off
REM ====================================================
REM Charity Tracker - JavaFX Run Script
REM Windows PowerShell Version
REM ====================================================

echo.
echo ========== CHARITY TRACKER - JAVAFX APPLICATION ==========
echo.

REM Check if bin directory exists
if not exist bin (
    echo Error: bin directory not found!
    echo Please compile the application first using compile_javafx.bat
    pause
    exit /b 1
)

REM Check if JavaFX SDK exists
if not exist lib\javafx-sdk-21.0.2\lib (
    echo Error: JavaFX SDK not found at lib\javafx-sdk-21.0.2\
    echo.
    echo Please:
    echo 1. Download JavaFX SDK 21.0.2 from https://gluonhq.com/products/javafx/
    echo 2. Extract to lib\javafx-sdk-21.0.2\
    echo.
    pause
    exit /b 1
)

REM Check if MySQL driver exists
if not exist lib\mysql-connector-j-9.6.0.jar (
    echo Error: MySQL driver not found!
    pause
    exit /b 1
)

echo Starting Charity Tracker Application...
echo.

REM Run the application
java -cp "bin;lib\mysql-connector-j-9.6.0.jar;lib\javafx-sdk-21.0.2\lib\*" ^
  --module-path lib\javafx-sdk-21.0.2\lib ^
  --add-modules javafx.controls,javafx.fxml,javafx.graphics ^
  javafx.app.CharityTrackerApp

if %errorlevel% equ 0 (
    echo.
    echo ✓ Application closed successfully
) else (
    echo.
    echo ✗ Application failed to run!
    echo.
    echo Troubleshooting:
    echo 1. Ensure MySQL database is running on localhost:3306
    echo 2. Check database credentials (root/root)
    echo 3. Verify FXML files are in bin\javafx\fxml\
    echo 4. Check CSS files are in bin\javafx\resources\
    echo.
)

pause
