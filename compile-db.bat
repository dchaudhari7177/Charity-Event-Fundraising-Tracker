@echo off
REM Charity Event Fundraising Tracker - Database Version Compilation Script
REM Compiles Java files with DAO pattern (MySQL + JDBC)

echo ===============================================
echo Charity Event Fundraising Tracker (DB Version)
echo Compiling with JDBC Support
echo ===============================================

REM Navigate to the project directory
cd /d "%~dp0"

REM Create bin directory if it doesn't exist
if not exist bin mkdir bin

echo.
echo Checking for MySQL JDBC Driver in lib/ folder...
if not exist "lib\mysql-connector-j-*.jar" (
    echo.
    echo WARNING: MySQL JDBC Driver not found!
    echo Please download mysql-connector-j JAR file and place it in lib/ folder
    echo Download from: https://dev.mysql.com/downloads/connector/j/
    echo.
    pause
    exit /b 1
)

echo.
echo Compiling Java files with JDBC support...
echo.

REM Set CLASSPATH to include JDBC driver
for %%f in (lib\mysql-connector-j-*.jar) do set CLASSPATH=%%f

REM Compile all Java files from src directory to bin directory
javac -cp "%CLASSPATH%" -d bin src\Main.java src\model\*.java src\dao\*.java src\db\*.java

if errorlevel 1 (
    echo.
    echo Compilation FAILED!
    echo.
    pause
    exit /b 1
)

echo.
echo ✓ Compilation successful!
echo ✓ All classes compiled with database support
echo.
echo Running the program...
echo ===============================================
echo.

REM Run the program with JDBC driver in classpath
java -cp bin;"%CLASSPATH%" Main

pause
