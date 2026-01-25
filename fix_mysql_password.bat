@echo off
REM Fix MySQL root user and set password to "root"
REM Run this as Administrator

echo.
echo ========================================
echo   MySQL Root Password Setup
echo ========================================
echo.
echo This script will:
echo 1. Stop MySQL service
echo 2. Start it without password check
echo 3. Set root password to 'root'
echo 4. Restart MySQL normally
echo.
echo REQUIRES: Administrator privileges
echo.
pause

REM Stop MySQL
echo Stopping MySQL service...
net stop MySQL80

if %errorlevel% neq 0 (
    echo Error: Could not stop MySQL service
    echo Make sure you're running as Administrator
    pause
    exit /b 1
)

timeout /t 2 /nobreak

REM Start MySQL without password check
echo Starting MySQL without password check...
start "" "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysqld" --skip-grant-tables

timeout /t 3 /nobreak

REM Connect and set password
echo Setting password...
echo FLUSH PRIVILEGES; > temp_setup.sql
echo ALTER USER 'root'@'localhost' IDENTIFIED BY 'root'; >> temp_setup.sql

"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql" -u root < temp_setup.sql

timeout /t 2 /nobreak

REM Kill the MySQL process
taskkill /IM mysqld.exe /F

timeout /t 2 /nobreak

REM Start MySQL normally
echo Starting MySQL service normally...
net start MySQL80

timeout /t 3 /nobreak

REM Verify
echo Verifying password...
"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql" -u root -p"root" -e "SELECT VERSION();"

if %errorlevel% equ 0 (
    echo.
    echo ========================================
    echo ✓ Password set successfully!
    echo ========================================
    echo Root password is now: 'root'
    echo.
) else (
    echo.
    echo ========================================
    echo ✗ Password setup may have failed
    echo ========================================
    echo.
)

REM Clean up
del temp_setup.sql 2>nul

pause
