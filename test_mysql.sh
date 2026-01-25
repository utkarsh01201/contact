#!/bin/bash
# Test MySQL Connection on Linux/Mac

echo "========================================"
echo "   MySQL Connection Test"
echo "========================================"
echo ""

echo "Checking MySQL installation..."
mysql --version
if [ $? -ne 0 ]; then
    echo "Error: MySQL is not installed or not in PATH"
    echo "Please install MySQL and add it to system PATH"
    exit 1
fi

echo ""
echo "Attempting to connect to MySQL..."
echo "Please enter your MySQL password when prompted:"
echo ""

mysql -u root -p -h localhost -e "SELECT VERSION();"

if [ $? -ne 0 ]; then
    echo ""
    echo "Connection failed! Please check:"
    echo "1. MySQL server is running"
    echo "2. Your password is correct"
    echo "3. MySQL is listening on localhost:3306"
    exit 1
fi

echo ""
echo "MySQL connection successful!"
echo ""
echo "Now checking if contact_db exists..."
mysql -u root -p -h localhost -e "USE contact_db; SHOW TABLES;"

if [ $? -ne 0 ]; then
    echo ""
    echo "Database contact_db not found!"
    echo "Running setup script..."
    mysql -u root -p < database_setup.sql
fi

echo ""
echo "All checks complete!"
