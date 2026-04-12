#!/bin/bash
# Charity Event Fundraising Tracker - Database Version Compilation Script
# Compiles Java files with DAO pattern (MySQL + JDBC) - Linux/Mac

echo "========================================"
echo "Charity Event Fundraising Tracker"
echo "Database Version (MySQL + JDBC)"
echo "========================================"

# Navigate to the project directory
cd "$(dirname "$0")"

# Create bin directory if it doesn't exist
mkdir -p bin

echo ""
echo "Checking for MySQL JDBC Driver in lib/ folder..."

# Check if MySQL driver exists
if ! ls lib/mysql-connector-j-*.jar 1> /dev/null 2>&1; then
    echo ""
    echo "WARNING: MySQL JDBC Driver not found!"
    echo "Please download mysql-connector-j JAR file and place it in lib/ folder"
    echo "Download from: https://dev.mysql.com/downloads/connector/j/"
    echo ""
    exit 1
fi

echo ""
echo "Compiling Java files with JDBC support..."
echo ""

# Find the JDBC driver
JDBC_JAR=$(ls lib/mysql-connector-j-*.jar | head -1)

# Compile all Java files with CLASSPATH
javac -cp "$JDBC_JAR" -d bin src/Main.java src/model/*.java src/dao/*.java src/db/*.java

if [ $? -ne 0 ]; then
    echo ""
    echo "Compilation FAILED!"
    echo ""
    exit 1
fi

echo ""
echo "✓ Compilation successful!"
echo "✓ All classes compiled with database support"
echo ""
echo "Running the program..."
echo "========================================"
echo ""

# Run the program with JDBC driver in classpath
java -cp bin:"$JDBC_JAR" Main
