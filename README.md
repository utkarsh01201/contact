# Contact Management System - Setup Guide

## Project Structure
```
contact_db/
├── Contact.java          # Contact model class
├── DBConnection.java     # Database connection utility with config support
├── ContactDAO.java       # Data Access Object for database operations
├── MainApp.java          # Main application with menu-driven interface
├── config.properties     # Database configuration file
├── database_setup.sql    # SQL script to create database and table
├── compile.bat          # Windows compile script
├── run.bat              # Windows run script
├── compile.sh           # Linux/Mac compile script
├── run.sh               # Linux/Mac run script
├── .gitignore           # Git ignore file
└── lib/
    └── mysql-connector-j-9.6.0.jar
```

## Features
-  Add new contacts
-  View all contacts
-  Search contacts by name or email
-  Update contact information
-  Delete contacts
-  View individual contact details
-  Display statistics
-  Platform independent (Windows, Linux, Mac)
-  Easy database configuration
-  Better error handling and messages

## Prerequisites
- Java JDK 8 or higher
- MySQL Server (version 5.7 or higher)
- MySQL JDBC Driver (included in lib/)

## Setup Instructions

### Step 1: Create Database and Table

**Option A: Using MySQL Command Line**
```bash
mysql -u root -p
```

Then paste the following SQL commands:
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

**Option B: Using database_setup.sql**
```bash
mysql -u root -p contact_db < database_setup.sql
```

### Step 2: Update Database Configuration

Edit `config.properties` and update your MySQL credentials:
```properties
db.url=jdbc:mysql://localhost:3306/contact_db
db.user=root
db.password=your_password
db.driver=com.mysql.cj.jdbc.Driver
```

### Step 3: Compile the Project

**On Windows:**
```bash
compile.bat
```

**On Linux/Mac:**
```bash
chmod +x compile.sh
./compile.sh
```

**Or manually:**
```bash
javac -cp ".;lib/mysql-connector-j-9.6.0.jar" *.java    # Windows
javac -cp ".:lib/mysql-connector-j-9.6.0.jar" *.java    # Linux/Mac
```

### Step 4: Run the Application

**On Windows:**
```bash
run.bat
```

**On Linux/Mac:**
```bash
chmod +x run.sh
./run.sh
```

**Or manually:**
```bash
java -cp ".;lib/mysql-connector-j-9.6.0.jar" MainApp     # Windows
java -cp ".:lib/mysql-connector-j-9.6.0.jar" MainApp     # Linux/Mac
```

## Usage

Once the application starts, you'll see a menu:

```
========================================
   CONTACT MANAGEMENT SYSTEM
========================================
1. Add New Contact
2. View All Contacts
3. Search Contact
4. Update Contact
5. Delete Contact
6. View Contact Details
7. View Statistics
8. Exit
========================================
```

### Example Workflow:
1. Select option 1 to add a new contact
2. Enter: Name, Email, Phone, Address
3. Select option 2 to view all contacts
4. Select option 3 to search by name or email
5. Select option 4 to update contact information
6. Select option 5 to delete a contact
7. Select option 8 to exit

## Class Descriptions

### Contact.java
- Model class representing a contact entity
- Properties: id, name, email, phone, address

### DBConnection.java
- Utility class for database connection management
- Loads configuration from config.properties
- Methods: getConnection(), closeConnection(), testConnection()
- Provides detailed error messages for debugging

### ContactDAO.java
- Data Access Object for CRUD operations
- Methods: 
  - addContact() - Add new contact
  - getAllContacts() - Retrieve all contacts
  - getContactById() - Get specific contact
  - updateContact() - Update contact info
  - deleteContact() - Remove contact
  - searchByName() - Search by name
  - searchByEmail() - Search by email
  - getTotalContacts() - Count total contacts

### MainApp.java
- Menu-driven main application
- Handles user input and validation
- Displays formatted output
- Calls ContactDAO methods for database operations

## Database Schema

```
Table: contacts
┌─────────────────┬──────────────────┬──────────┐
│ Column          │ Type             │ Extra    │
├─────────────────┼──────────────────┼──────────┤
│ id              │ INT              │ PRIMARY  │
│ name            │ VARCHAR(100)     │ NOT NULL │
│ email           │ VARCHAR(100)     │ UNIQUE   │
│ phone           │ VARCHAR(15)      │ UNIQUE   │
│ address         │ VARCHAR(200)     │          │
│ created_at      │ TIMESTAMP        │ DEFAULT  │
│ updated_at      │ TIMESTAMP        │ DEFAULT  │
└─────────────────┴──────────────────┴──────────┘
```

## Troubleshooting

### Error: "access denied" / Connection failed
**Solution:**
1. Ensure MySQL server is running
2. Check your credentials in config.properties
3. Run this command to verify: `mysql -u root -p`

### Error: Database 'contact_db' doesn't exist
**Solution:**
1. Run database_setup.sql file
2. Or manually execute the CREATE DATABASE command

### Error: "config.properties not found"
**Solution:**
1. Ensure config.properties is in the project root directory
2. Place it in the same folder as the Java files

### Error: MySQL JDBC Driver not found
**Solution:**
1. Check that mysql-connector-j-9.6.0.jar is in the lib/ folder
2. Verify the jar file name matches exactly

### Error: Connection timeout
**Solution:**
1. Check if MySQL server is running on port 3306
2. Verify firewall isn't blocking the connection
3. Check if database URL is correct in config.properties

## GitHub Setup

### To push this project to GitHub:

1. Initialize git repository (if not already done):
```bash
git init
```

2. Add all files except compiled classes and IDE files (already in .gitignore):
```bash
git add .
```

3. Create initial commit:
```bash
git commit -m "Initial commit: Contact Management System"
```

4. Add remote repository:
```bash
git remote add origin https://github.com/your-username/contact-management.git
```

5. Push to GitHub:
```bash
git branch -M main
git push -u origin main
```

## Notes
- All email and phone numbers are unique in the database
- Empty fields in search/update will skip those fields
- Timestamps are automatically managed by the database
- The application is fully platform independent
- Configuration is centralized in config.properties
- All sensitive credentials should be updated in config.properties

## Support
For issues or questions, please check:
1. MySQL server status
2. Database and table existence
3. Credentials in config.properties
4. Error messages in console output

