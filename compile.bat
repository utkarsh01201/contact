@echo off
REM Batch file to compile Contact Management System on Windows

echo ========================================
echo   Compiling Contact Management System
echo ========================================

if not exist "lib\mysql-connector-j-9.6.0.jar" (
    echo Error: mysql-connector-j-9.6.0.jar not found in lib folder
    pause
    exit /b 1
)

javac -cp ".;lib/mysql-connector-j-9.6.0.jar" *.java

if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo.
echo Compilation successful!
echo To run the application, execute: run.bat
pause
