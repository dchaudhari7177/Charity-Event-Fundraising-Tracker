@echo off
REM ====================================================
REM Charity Tracker - JavaFX Compilation Script
REM Windows PowerShell Version
REM ====================================================

echo.
echo ========== CHARITY TRACKER - JAVAFX COMPILATION ==========
echo.

REM Check if bin directory exists
if not exist bin (
    echo Creating bin directory...
    mkdir bin
)

REM Create subdirectories for FXML and CSS
if not exist bin\javafx\fxml (
    echo Creating FXML directory...
    mkdir bin\javafx\fxml
)
if not exist bin\javafx\resources (
    echo Creating resources directory...
    mkdir bin\javafx\resources
)

REM Copy FXML files
echo.
echo Copying FXML files...
xcopy /Y src\javafx\fxml\*.fxml bin\javafx\fxml\ >nul
if %errorlevel% equ 0 (
    echo ✓ FXML files copied successfully
) else (
    echo ✗ Failed to copy FXML files
)

REM Copy CSS files
echo.
echo Copying CSS files...
xcopy /Y src\javafx\resources\*.css bin\javafx\resources\ >nul
if %errorlevel% equ 0 (
    echo ✓ CSS files copied successfully
) else (
    echo ✗ Failed to copy CSS files
)

REM Compile Java files
echo.
echo Compiling Java files...
echo.

setlocal enabledelayedexpansion

javac -cp "bin;lib\mysql-connector-j-9.6.0.jar;lib\javafx-sdk-21.0.2\lib\*" -d bin ^
  src\javafx\app\CharityTrackerApp.java ^
  src\javafx\controller\MainController.java ^
  src\javafx\controller\DashboardController.java ^
  src\javafx\controller\CreateEventController.java ^
  src\javafx\controller\RegisterDonorController.java ^
  src\javafx\controller\RecordDonationController.java ^
  src\javafx\controller\EventsTableController.java ^
  src\javafx\controller\DonorsTableController.java ^
  src\javafx\controller\StatisticsController.java ^
  src\javafx\util\ThemeConstants.java ^
  src\javafx\util\AnimationUtils.java ^
  src\model\Donor.java ^
  src\model\Event.java ^
  src\model\Pledge.java ^
  src\dao\DonorDAO.java ^
  src\dao\EventDAO.java ^
  src\dao\PledgeDAO.java ^
  src\db\DBConnection.java

if %errorlevel% equ 0 (
    echo.
    echo ====================================================
    echo ✓ COMPILATION SUCCESSFUL!
    echo ====================================================
    echo.
    echo To run the application, execute:
    echo.
    echo java -cp "bin;lib\mysql-connector-j-9.6.0.jar;lib\javafx-sdk-21.0.2\lib\*" ^
    echo   --module-path lib\javafx-sdk-21.0.2\lib ^
    echo   --add-modules javafx.controls,javafx.fxml,javafx.graphics ^
    echo   javafx.app.CharityTrackerApp
    echo.
) else (
    echo.
    echo ====================================================
    echo ✗ COMPILATION FAILED!
    echo ====================================================
    echo.
    echo Please check:
    echo 1. JavaFX SDK is in lib\javafx-sdk-21.0.1\
    echo 2. MySQL driver jar is in lib\
    echo 3. All source files exist
    echo.
    exit /b 1
)

endlocal

pause
