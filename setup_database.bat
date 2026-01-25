@echo off
REM MySQL Database Setup Script for Contact Management System
REM Creates contact_db database and contacts table with root/root credentials

echo.
echo ========================================
echo   MySQL Database Setup
echo ========================================
echo.

REM Check if MySQL is in PATH
mysql --version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: MySQL command not found in PATH
    echo.
    echo MySQL needs to be in your system PATH.
    echo.
    echo Option 1: Add MySQL to PATH
    echo   Usually at: C:\Program Files\MySQL\MySQL Server 8.0\bin
    echo.
    echo Option 2: Use full path
    echo   "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql" -u root -p"root" < database_setup.sql
    echo.
    pause
    exit /b 1
)

echo Connecting to MySQL with root/root...
echo Creating database and tables...
echo.

REM Run the SQL setup script
mysql -u root -p"root" < database_setup.sql

if %errorlevel% equ 0 (
    echo.
    echo ========================================
    echo ✓ Database setup successful!
    echo ========================================
    echo.
    echo The following were created:
    echo - Database: contact_db
    echo - Table: contacts
    echo - Sample data inserted
    echo.
    echo Ready to run the application!
    echo.
) else (
    echo.
    echo ========================================
    echo ✗ Database setup failed!
    echo ========================================
    echo.
    echo Troubleshooting:
    echo 1. Ensure MySQL server is running
    echo 2. Verify password "root" is correct
    echo 3. Check if MySQL is in system PATH
    echo.
    echo To manually set up:
    echo   mysql -u root -p"root" < database_setup.sql
    echo.
)

pause
