#!/bin/bash
# Shell script to run Contact Management System on Linux/Mac

echo "========================================"
echo "  Contact Management System"
echo "========================================"
echo ""

if [ ! -f "*.class" ] 2>/dev/null; then
    # Check if any .class file exists
    if ! ls *.class &> /dev/null; then
        echo "Error: Compiled classes not found. Run compile.sh first."
        exit 1
    fi
fi

if [ ! -f "config.properties" ]; then
    echo "Error: config.properties not found!"
    exit 1
fi

java -cp ".:lib/mysql-connector-j-9.6.0.jar" MainApp
