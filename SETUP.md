# QUICK START GUIDE - Contact Management System

## Before Running - IMPORTANT!

### 1. MySQL Setup (First Time Only)

Make sure MySQL is installed and running on your machine.

**Windows Users:**
```bash
# Start MySQL service (if not auto-started)
net start MySQL80
```

**Mac Users:**
```bash
# If using Homebrew
brew services start mysql
```

**Linux Users:**
```bash
sudo service mysql start
```

### 2. Create Database

Open MySQL command line and run these commands:

```bash
mysql -u root -p
# Enter your MySQL password when prompted
```

Then paste these commands:
```sql
CREATE DATABASE IF NOT EXISTS contact_db;
USE contact_db;

CREATE TABLE IF NOT EXISTS contacts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(15) NOT NULL UNIQUE,
    address VARCHAR(200),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

**Or run the SQL file directly:**
```bash
mysql -u root -p contact_db < database_setup.sql
```

### 3. Update Configuration

Edit `config.properties` with your MySQL credentials:

```properties
db.url=jdbc:mysql://localhost:3306/contact_db
db.user=root
db.password=YOUR_PASSWORD_HERE
db.driver=com.mysql.cj.jdbc.Driver
```

## How to Run

### Windows Users:
```bash
compile.bat
run.bat
```

### Linux/Mac Users:
```bash
chmod +x compile.sh run.sh
./compile.sh
./run.sh
```

### Manual Compilation:
```bash
# Windows
javac -cp ".;lib/mysql-connector-j-9.6.0.jar" *.java
java -cp ".;lib/mysql-connector-j-9.6.0.jar" MainApp

# Linux/Mac
javac -cp ".:lib/mysql-connector-j-9.6.0.jar" *.java
java -cp ".:lib/mysql-connector-j-9.6.0.jar" MainApp
```

## Common Issues & Solutions

### ❌ "Error: Database connection failed"
**Solution:**
1. Verify MySQL server is running
2. Check your password in config.properties is correct
3. Ensure database 'contact_db' exists

### ❌ "Error: config.properties not found"
**Solution:**
Make sure config.properties is in the same directory as the .java files

### ❌ "Error: MySQL JDBC Driver not found"
**Solution:**
Check that `lib/mysql-connector-j-9.6.0.jar` exists

### ❌ "Permission denied" (Linux/Mac)
**Solution:**
```bash
chmod +x compile.sh run.sh
```

## Features
- ✅ Add, View, Search, Update, Delete Contacts
- ✅ Search by Name or Email
- ✅ View Contact Statistics
- ✅ Real-time Database Operations
- ✅ Cross-Platform (Windows, Linux, Mac)
- ✅ Easy Configuration via config.properties

## File Description

| File | Purpose |
|------|---------|
| Contact.java | Contact model/entity class |
| DBConnection.java | Database connection manager |
| ContactDAO.java | Database operations (CRUD) |
| MainApp.java | Main menu-driven application |
| config.properties | Database configuration file |
| database_setup.sql | SQL script to create database |
| compile.bat/sh | Compilation scripts |
| run.bat/sh | Execution scripts |
| .gitignore | Files to ignore in git |

## Need Help?

1. Check README.md for detailed documentation
2. Verify all 4 Java files are present
3. Ensure MySQL is running
4. Check config.properties for correct credentials
5. Run compile script to check for compilation errors

Good luck! 🚀
