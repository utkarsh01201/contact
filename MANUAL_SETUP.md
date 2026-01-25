# IMPORTANT: Manual MySQL Setup Required

Your MySQL server appears to have a password that's not in the standard list.

## Step-by-Step Manual Setup

### Step 1: Find MySQL Installation
- Windows: Look for MySQL in `C:\Program Files\MySQL\` or `C:\Program Files (x86)\MySQL\`
- Common path: `C:\Program Files\MySQL\MySQL Server 8.0\bin`

### Step 2: Open MySQL Command Prompt
```
Option A: Navigate to bin folder and double-click mysql.exe
Option B: Open Command Prompt as Administrator and type:
   "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql" -u root -p
```

### Step 3: Enter Your Password
When prompted for password, enter the password you set during MySQL installation.

### Step 4: Create Database and Table
Once in MySQL, copy-paste this entire block:

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

-- Add your own contacts after setup

SELECT * FROM contacts;
```

### Step 5: Exit MySQL
```sql
EXIT;
```

### Step 6: Update config.properties
1. Open file: `config.properties`
2. Find the line: `db.password=`
3. Update it with YOUR password:
   ```
   db.password=YOUR_PASSWORD_HERE
   ```
   (Replace YOUR_PASSWORD_HERE with the actual password)

### Step 7: Run the Application
```bash
# Windows:
run.bat

# Linux/Mac:
./run.sh
```

---

## What if I forgot my MySQL password?

### Option A: Reset It (Windows)
1. Stop MySQL: `net stop MySQL80`
2. Start without password: `"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysqld" --skip-grant-tables`
3. In new Command Prompt: `mysql -u root`
4. Run these commands:
   ```sql
   FLUSH PRIVILEGES;
   ALTER USER 'root'@'localhost' IDENTIFIED BY 'newpassword';
   ```
5. Set `db.password=newpassword` in config.properties

### Option B: Reinstall MySQL
1. Uninstall MySQL
2. Delete `C:\ProgramData\MySQL`
3. Reinstall and SET A PASSWORD YOU REMEMBER

---

## Need Help?

**MySQL not installed?**
- Download from: https://dev.mysql.com/downloads/mysql/
- Install version 8.0 or higher

**MySQL not running?**
- Windows: Open Services (services.msc) and start MySQL80
- Make sure it shows "Running"

**Still having issues?**
1. Run: `java ConfigValidator` - Shows detailed setup status
2. Run: `java -cp ".;lib/mysql-connector-j-9.6.0.jar" MySQLDiagnostic` - Shows connection errors
3. Check PASSWORD_FIX.md for more troubleshooting

Once password is set in config.properties, run: `run.bat`
