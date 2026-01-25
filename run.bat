@echo off
REM Batch file to run Contact Management System on Windows

echo ========================================
echo   Contact Management System
echo ========================================
echo.

if not exist "*.class" (
    echo Error: Compiled classes not found. Run compile.bat first.
    pause
    exit /b 1
)

if not exist "config.properties" (
    echo Error: config.properties not found!
    pause
    exit /b 1
)

java -cp ".;lib/mysql-connector-j-9.6.0.jar" MainApp

pause
