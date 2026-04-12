#!/bin/bash
# Charity Event Fundraising Tracker - Compilation Script for Linux/Mac

echo "========================================"
echo "Charity Event Fundraising Tracker"
echo "========================================"

# Navigate to the project directory
cd "$(dirname "$0")"

# Create bin directory if it doesn't exist
mkdir -p bin

echo ""
echo "Compiling Java files..."
echo ""

# Compile all Java files from src directory to bin directory
javac -d bin src/*.java

if [ $? -ne 0 ]; then
    echo ""
    echo "Compilation FAILED!"
    echo ""
    exit 1
fi

echo ""
echo "Compilation successful!"
echo ""
echo "Running the program..."
echo "========================================"
echo ""

# Run the program
java -cp bin Main

