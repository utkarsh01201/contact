# FIX: Access Denied Error - Complete Solution

## Problem
Your application is getting: **"Access denied for user 'root'@'localhost' (using password: NO)"**

This means either:
1. MySQL server is not running
2. Your MySQL credentials are wrong
3. MySQL is not installed or not in PATH

## Solution - Step by Step

### Step 1: Check if MySQL is Installed and Running

**Windows:**
1. Open **Services** (Press `Win + R`, type `services.msc`)
2. Look for **MySQL80** (or similar MySQL version)
3. If stopped, right-click and select **Start**
4. If not listed, MySQL needs to be installed

**Alternatively, try:**
```powershell
# Check if MySQL is in PATH
mysql --version

# If not found, MySQL is not in system PATH
# You need to install MySQL or add it to PATH
```

### Step 2: Find Your MySQL Installation

**Common MySQL Installation Locations:**

**Windows:**
- `C:\Program Files\MySQL\MySQL Server 8.0\bin`
- `C:\Program Files (x86)\MySQL\MySQL Server 8.0\bin`

**To verify where MySQL is installed:**
1. Open **Control Panel** → **Programs** → **Programs and Features**
2. Look for MySQL Server
3. Check the installation path

### Step 3: Update Your Password in config.properties

Edit `config.properties` and set the correct password:

```properties
db.url=jdbc:mysql://localhost:3306/contact_db
db.user=root
db.password=YOUR_ACTUAL_PASSWORD
db.driver=com.mysql.cj.jdbc.Driver
```

**What password to use?**
- The password you set during MySQL installation
- If you don't remember, you'll need to reset MySQL root password
- If you selected "no password", change password to empty string: `db.password=`

### Step 4: Create Database and Table

**If you haven't created the database yet:**

**Option A: Using MySQL Command Line**
```bash
mysql -u root -p
# Enter your MySQL password
```

Then run these SQL commands:
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

**Option B: Run SQL file (if MySQL is in PATH)**
```bash
mysql -u root -p YOUR_PASSWORD < database_setup.sql
```

### Step 5: Test Connection

After fixing the above, run:

**Windows:**
```bash
compile.bat
run.bat
```

**Linux/Mac:**
```bash
./compile.sh
./run.sh
```

## If MySQL is Not in PATH (Windows Users)

1. **Add MySQL to PATH:**
   - Find MySQL bin folder (usually `C:\Program Files\MySQL\MySQL Server 8.0\bin`)
   - Open **Environment Variables** (Win + R → `sysdm.cpl`)
   - Click **Environment Variables** button
   - Select **PATH** → **Edit**
   - Click **New** and add: `C:\Program Files\MySQL\MySQL Server 8.0\bin`
   - Click **OK** → **OK** → **OK**
   - Restart PowerShell/Command Prompt

2. **Or use Full Path:**
   ```powershell
   "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql" -u root -p
   ```

## If MySQL is Not Installed

**Download and Install MySQL:**
1. Go to https://dev.mysql.com/downloads/mysql/
2. Download MySQL Community Server (8.0 or higher)
3. Run installer
4. Choose **Setup Type: Developer Default**
5. Follow installation wizard
6. Remember the **root password** you set!
7. Complete installation
8. MySQL Server should auto-start

## Quick Checklist

- [ ] MySQL Server is installed
- [ ] MySQL Service is running (check Services)
- [ ] Database `contact_db` is created
- [ ] Table `contacts` is created
- [ ] Password in `config.properties` matches your MySQL password
- [ ] Java can access config.properties file
- [ ] MySQL JDBC driver is in `lib/` folder

## Common Passwords to Try

If you don't remember your MySQL password, try:
- Leave blank: `db.password=`
- Common: `db.password=root`
- Common: `db.password=password`
- Common: `db.password=12345`

If none work, you'll need to reset the MySQL root password (search: "Reset MySQL Root Password Windows").

## Still Getting Error?

Look at the error message in the console:
- **"Connection refused"** → MySQL server not running
- **"Access denied"** → Wrong password in config.properties
- **"Unknown database"** → Run database_setup.sql
- **"Table doesn't exist"** → Run database_setup.sql

Good luck! 🚀
