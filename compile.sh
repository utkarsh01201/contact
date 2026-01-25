#!/bin/bash
# Shell script to compile Contact Management System on Linux/Mac

echo "========================================"
echo "  Compiling Contact Management System"
echo "========================================"

if [ ! -f "lib/mysql-connector-j-9.6.0.jar" ]; then
    echo "Error: mysql-connector-j-9.6.0.jar not found in lib folder"
    exit 1
fi

javac -cp ".:lib/mysql-connector-j-9.6.0.jar" *.java

if [ $? -ne 0 ]; then
    echo "Compilation failed!"
    exit 1
fi

echo ""
echo "Compilation successful!"
echo "To run the application, execute: ./run.sh"
