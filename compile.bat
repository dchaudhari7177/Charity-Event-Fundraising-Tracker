@echo off
REM Charity Event Fundraising Tracker - Compilation Script

echo ===============================================
echo Charity Event Fundraising Tracker Compiler
echo ===============================================

REM Navigate to the project directory
cd /d "%~dp0"

REM Create bin directory if it doesn't exist
if not exist bin mkdir bin

echo.
echo Compiling Java files...
echo.

REM Compile all Java files from src directory to bin directory
javac -d bin src\*.java

if errorlevel 1 (
    echo.
    echo Compilation FAILED!
    echo.
    pause
    exit /b 1
)

echo.
echo Compilation successful!
echo.
echo Running the program...
echo ===============================================
echo.

REM Run the program
java -cp bin Main

pause
