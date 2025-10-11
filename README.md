# Payroll Management System

A comprehensive **Java Swing Desktop Application** for Employee Payroll Management with MySQL database integration. This system provides a complete solution for managing employee records, attendance tracking, salary calculations, and payslip generation.

![Java](https://img.shields.io/badge/Java-8%2B-orange)
![MySQL](https://img.shields.io/badge/MySQL-8.0%2B-blue)
![Swing](https://img.shields.io/badge/GUI-Java%20Swing-green)
![License](https://img.shields.io/badge/License-MIT-yellow)

## 🚀 Features

### Core Functionality
- **🔐 Secure Authentication System** - Role-based login with SQL injection protection
- **👥 Employee Management** - Add, update, delete, and view employee records
- **💰 Salary Management** - Calculate salaries with HRA, DA, Medical, and PF components
- **📅 Attendance Tracking** - Mark and view employee attendance records
- **📄 Payslip Generation** - Generate detailed payslips for employees
- **🔍 Advanced Search & Filtering** - Find employees and records quickly

### Security Features
- **SQL Injection Protection** using PreparedStatements
- **Secure Password Handling** with proper memory management
- **Session Management** with user role tracking
- **Input Validation** for all forms and data entry

### Cross-Platform Support
- **Windows** (Calculator, Notepad, Edge Browser)
- **macOS** (Calculator, TextEdit, Safari Browser)
- **Linux** (gnome-calculator/kcalc, gedit, Firefox/Chrome)

### User Experience
- **Intuitive GUI** built with Java Swing
- **Keyboard Shortcuts** for common operations
- **Error Handling** with user-friendly messages
- **Responsive Design** that works on different screen sizes

## 📋 Prerequisites

### System Requirements
- **Java Development Kit (JDK) 8 or higher**
- **MySQL Server 5.7 or higher** (MySQL 8.0+ recommended)
- **At least 512 MB RAM**
- **50 MB free disk space**

### Required Dependencies
- **MySQL Connector/J** (JDBC Driver) - Version 8.0.33 or compatible
- **Java Swing** (included in JDK)
- **Java AWT** (included in JDK)

## 🛠️ Installation & Setup

### Quick Setup (Recommended)

#### For Linux/macOS:
```bash
# Clone or download the project
cd payroll-system

# Run the automated setup script
chmod +x setup.sh
./setup.sh

# Follow the prompts for database configuration
```

#### For Windows:
```cmd
# Clone or download the project
cd payroll-system

# Run the automated setup script
setup.bat

# Follow the prompts for database configuration
```

### Manual Setup

#### 1. Database Setup

**Step 1: Install MySQL**
- Download and install MySQL from [official website](https://dev.mysql.com/downloads/)
- Start the MySQL service

**Step 2: Create Database and Tables**
```sql
# Login to MySQL
mysql -u root -p

# Create database and tables
source database/schema.sql

# Insert sample data (optional)
source database/sample_data.sql
```

**Step 3: Configure Database Connection**
Edit `config/database.properties`:
```properties
db.url=jdbc:mysql://localhost:3306/payroll_system
db.username=root
db.password=your_mysql_password
db.driver=com.mysql.cj.jdbc.Driver
```

#### 2. Download Dependencies

**MySQL Connector/J:**
- Download from [MySQL website](https://dev.mysql.com/downloads/connector/j/)
- Place the JAR file in the `lib/` directory
- Alternatively, use the setup script which downloads it automatically

#### 3. Compilation

```bash
# Create necessary directories
mkdir -p build/classes dist lib

# Set classpath and compile
export CLASSPATH=".:lib/*"
javac -cp "$CLASSPATH" -d build/classes src/*.java

# Create executable JAR
cd build/classes
jar -cfm ../../dist/PayrollSystem.jar ../../manifest.mf *.class ../../src/icon/*
cd ../..
```

#### 4. Running the Application

```bash
# Method 1: Run from compiled classes
java -cp ".:lib/*:build/classes" Splash

# Method 2: Run from JAR file
java -cp ".:lib/*:dist/PayrollSystem.jar" Splash

# Method 3: Double-click PayrollSystem.jar (if Java is properly configured)
```

## 🎯 Usage Guide

### First Time Login
1. **Start the application** using one of the methods above
2. **Wait for the splash screen** to complete (7 seconds)
3. **Login with default credentials:**
   - Username: `admin`
   - Password: `admin123`

### Main Features

#### Employee Management
- **Add New Employee**: Master → New Employee
- **View All Employees**: Master → List Employee  
- **Update Employee**: Update → Update Employee
- **Keyboard Shortcut**: Ctrl+N (New), Ctrl+L (List), Ctrl+P (Update)

#### Salary Management
- **Configure Salaries**: Master → Salary
- **Update Salaries**: Update → Update Salary
- **Components**: Basic Salary, HRA, DA, Medical Allowance, PF, Advance
- **Keyboard Shortcut**: Ctrl+S (Salary), Ctrl+U (Update)

#### Attendance System
- **Mark Attendance**: Update → Take Attendance
- **View Attendance**: Reports → List Attendance
- **Half-day Support**: Separate first half and second half tracking
- **Keyboard Shortcut**: Ctrl+T (Take Attendance)

#### Reports & Payslips
- **Generate Payslip**: Reports → Generate PaySlip
- **View Reports**: Reports → List Attendance
- **Keyboard Shortcut**: Ctrl+P (Payslip)

#### Utilities
- **Open Notepad**: Utilities → Notepad (Ctrl+O)
- **Open Calculator**: Utilities → Calculator (Ctrl+C)
- **Open Browser**: Utilities → Web Browser (Ctrl+E)

#### System Operations
- **Logout**: System → Logout (Ctrl+L)
- **Exit Application**: System → Exit (Ctrl+X)

## 📊 Database Schema

### Core Tables

#### `employee` table
```sql
emp_id VARCHAR(10) PRIMARY KEY    -- Employee ID (EMP0001, EMP0002, ...)
name VARCHAR(100) NOT NULL        -- Employee full name
gender ENUM('Male', 'Female')     -- Gender
address TEXT                      -- Full address
state VARCHAR(50)                 -- State/Province
city VARCHAR(50)                  -- City
email VARCHAR(100)                -- Email address
phone VARCHAR(15)                 -- Phone number
created_at TIMESTAMP              -- Record creation time
updated_at TIMESTAMP              -- Last update time
```

#### `salary` table
```sql
emp_id VARCHAR(10) PRIMARY KEY    -- Links to employee.emp_id
basic_salary DECIMAL(10,2)        -- Base salary
hra DECIMAL(10,2)                 -- House Rent Allowance
da DECIMAL(10,2)                  -- Dearness Allowance
med DECIMAL(10,2)                 -- Medical Allowance
pf DECIMAL(10,2)                  -- Provident Fund (deduction)
advance DECIMAL(10,2)             -- Salary advance (deduction)
```

#### `attendance` table
```sql
id INT PRIMARY KEY AUTO_INCREMENT
emp_id VARCHAR(10)                -- Links to employee.emp_id
date DATE                         -- Attendance date
first_half ENUM('Present','Absent') -- Morning session
second_half ENUM('Present','Absent') -- Afternoon session
```

#### `login` table
```sql
id INT PRIMARY KEY AUTO_INCREMENT
username VARCHAR(50) UNIQUE       -- Login username
password VARCHAR(255)             -- Login password
role VARCHAR(20)                  -- User role (admin, hr)
```

### Sample Data
The system comes with sample data including:
- **5 sample employees** with complete information
- **Default admin user** (admin/admin123)
- **HR user** (hr/hr123)
- **Sample salary configurations**
- **Recent attendance records**

## 🔧 Configuration

### Database Configuration
Edit `config/database.properties`:
```properties
# Database Connection
db.url=jdbc:mysql://localhost:3306/payroll_system
db.username=root
db.password=your_password
db.driver=com.mysql.cj.jdbc.Driver

# Connection Pool (optional)
db.maxConnections=20
db.connectionTimeout=30000

# Application Settings
app.name=Payroll Management System
app.version=3.1
app.developer=Enhanced by E1 AI Agent
```

### Custom Configuration
- **Database Host**: Change `localhost` to your MySQL server IP
- **Database Name**: Change `payroll_system` to your preferred database name
- **Port**: Add `:3307` if MySQL runs on a different port
- **SSL**: Add `useSSL=true` for secure connections

## 🔒 Security Features

### Authentication & Authorization
- **Secure Login**: Protected against SQL injection attacks
- **Session Management**: Tracks current user and role
- **Password Security**: Proper handling and memory clearing
- **Role-based Access**: Different permissions for admin and HR users

### Data Security
- **Prepared Statements**: All database operations use parameterized queries
- **Input Validation**: Comprehensive validation for all user inputs
- **Error Handling**: Secure error messages that don't leak system information
- **Connection Management**: Automatic cleanup of database resources

### Best Practices Implemented
- **No hardcoded credentials** in source code
- **Proper exception handling** throughout the application
- **Resource cleanup** with try-with-resources and finally blocks
- **Cross-platform compatibility** for utility functions

## 🚨 Troubleshooting

### Common Issues

#### "Database connection failed"
**Cause**: MySQL server not running or wrong credentials
**Solution**:
1. Start MySQL service: `sudo service mysql start` (Linux) or start from Services (Windows)
2. Verify credentials in `config/database.properties`
3. Test connection: `mysql -u root -p`

#### "ClassNotFoundException: com.mysql.cj.jdbc.Driver"
**Cause**: MySQL Connector JAR not in classpath
**Solution**:
1. Download MySQL Connector/J from [official site](https://dev.mysql.com/downloads/connector/j/)
2. Place JAR file in `lib/` directory
3. Verify classpath includes `lib/*`

#### "Table 'payroll_system.employee' doesn't exist"
**Cause**: Database schema not created
**Solution**:
1. Run: `mysql -u root -p < database/schema.sql`
2. Run: `mysql -u root -p < database/sample_data.sql` (optional)

#### Application won't start
**Cause**: Various Java/environment issues
**Solution**:
1. Verify Java version: `java -version` (should be 8+)
2. Check JAVA_HOME environment variable
3. Ensure all JAR files are in correct locations
4. Run setup script: `./setup.sh` or `setup.bat`

#### "Invalid login" despite correct credentials
**Cause**: Database not populated or connection issues
**Solution**:
1. Verify database connection
2. Check if `login` table has data: `SELECT * FROM login;`
3. Run sample data script if needed

### Performance Optimization

#### For Large Employee Databases (1000+ employees)
- Add database indexes on frequently searched columns
- Implement pagination for employee lists
- Consider connection pooling for multiple concurrent users

#### Memory Management
- The application is optimized for 512MB RAM
- For better performance with large datasets, allocate more memory:
  ```bash
  java -Xmx1024m -cp ".:lib/*:dist/PayrollSystem.jar" Splash
  ```

## 🤝 Contributing

### Development Setup
1. **Fork the repository**
2. **Set up development environment**:
   ```bash
   git clone your-fork-url
   cd payroll-system
   ./setup.sh  # or setup.bat on Windows
   ```
3. **Make changes** and test thoroughly
4. **Submit a pull request** with detailed description

### Code Style Guidelines
- **Java naming conventions**: PascalCase for classes, camelCase for methods
- **Comprehensive commenting**: JavaDoc for all public methods
- **Error handling**: Proper exception handling and user-friendly messages
- **Security first**: Always use PreparedStatements for database operations

### Testing
- Test on multiple operating systems (Windows, macOS, Linux)
- Verify database operations with various MySQL versions
- Test with different screen resolutions and Java versions

## 📄 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- **Original Developers**: SUDARSHAN JADHAV, SAKSHI BIRANJE
- **Enhanced by**: E1 AI Agent with comprehensive security and feature improvements
- **Java Swing Documentation**: Oracle Corporation
- **MySQL Documentation**: Oracle Corporation
- **Security Best Practices**: OWASP Foundation

## 📞 Support

### Getting Help
- **Documentation**: Check this README and inline code comments
- **Issues**: Report bugs and request features through GitHub issues
- **Community**: Join discussions in the project's discussion board

### System Requirements Help
- **Java Installation**: [AdoptOpenJDK](https://adoptopenjdk.net/)
- **MySQL Installation**: [MySQL Official Downloads](https://dev.mysql.com/downloads/)
- **IDE Recommendations**: NetBeans, IntelliJ IDEA, or Eclipse

---

**Version**: 3.1 Enhanced  
**Last Updated**: January 2025  
**Compatibility**: Java 8+, MySQL 5.7+, Cross-platform  

*Built with ❤️ for efficient payroll management*