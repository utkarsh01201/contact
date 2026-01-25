# How to Fix: Access Denied Error

## Current Status
✗ **The password 'root' is incorrect for your MySQL server**

The error shows: `Access denied for user 'root'@'localhost' (using password: YES)`

This means:
- MySQL is running ✓
- Connection attempt is being made ✓
- But the password is WRONG ✗

## Solution Options

### Option 1: Use a Different Password

Try one of these common passwords in `config.properties`:

1. **No password (empty):**
   ```properties
   db.password=
   ```
   Then run: `java ConfigValidator` to verify

2. **Try 'password':**
   ```properties
   db.password=password
   ```

3. **Try '12345':**
   ```properties
   db.password=12345
   ```

4. **Try your system password**

### Option 2: Find Your MySQL Password

If you installed MySQL recently and set a password:
1. Check your installation notes
2. Check MySQL Workbench if installed (it might show saved connection)
3. Check Windows Event Viewer if MySQL was set up with a default password

### Option 3: Reset MySQL Root Password (Windows)

**Step 1: Stop MySQL Service**
```powershell
# Open Command Prompt as Administrator
net stop MySQL80
```

**Step 2: Start MySQL without password check**
```powershell
"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysqld" --skip-grant-tables
```

**Step 3: Connect and reset password**
```powershell
# Open a new Command Prompt as Administrator
mysql -u root

# In MySQL prompt:
FLUSH PRIVILEGES;
ALTER USER 'root'@'localhost' IDENTIFIED BY 'newpassword';
EXIT;
```

**Step 4: Restart MySQL normally**
```powershell
net start MySQL80
```

**Step 5: Update config.properties**
```properties
db.password=newpassword
```

### Option 4: Reinstall MySQL with Known Password

1. Uninstall MySQL (Control Panel → Programs → Uninstall)
2. Delete `C:\ProgramData\MySQL` folder
3. Reinstall MySQL from https://dev.mysql.com/downloads/mysql/
4. During installation, SET A PASSWORD YOU REMEMBER
5. Update `config.properties` with that password

## Quick Test

After updating the password:

**Windows:**
```bash
java ConfigValidator
```

If it says "✓ All checks passed!", then run:
```bash
run.bat
```

**Linux/Mac:**
```bash
java ConfigValidator
./run.sh
```

## Debugging Steps

**Step 1: Test MySQL connection from command line**
```bash
# If MySQL is in your PATH:
mysql -u root -p
# Enter password when prompted

# If MySQL is NOT in PATH, use full path:
"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql" -u root -p
```

**Step 2: If you get "Welcome to MySQL monitor"**
- Connection works! Your password is correct
- Exit: `EXIT;`
- Update `config.properties` with this password

**Step 3: If "Access denied"**
- Wrong password
- Try Option 1-3 above

## Common Mistakes

❌ **Don't forget to restart Java app after changing password**
- Change config.properties
- Close any running instances
- Run again

❌ **Spaces in password not handled correctly**
- If password has spaces, make sure to quote it properly
- Example: `db.password=my password`

❌ **Password file already has leading/trailing spaces**
```properties
# WRONG:
db.password= root 

# RIGHT:
db.password=root
```

## Still Stuck?

Try this diagnostic again:
```bash
java -cp ".;lib/mysql-connector-j-9.6.0.jar" MySQLDiagnostic
```

The error message will tell you exactly what's wrong:
- **Access denied** → Wrong password
- **Can't connect** → MySQL not running
- **Unknown database** → Need to create contact_db
- **Table doesn't exist** → Need to run database_setup.sql

## Once Fixed

1. Update `config.properties`
2. Run: `java ConfigValidator` (should show ✓ All checks passed!)
3. Run: `run.bat` (Windows) or `./run.sh` (Linux/Mac)
4. Application should start!

Good luck! 🚀
