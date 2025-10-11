#!/bin/bash

# Payroll System Setup Script
# This script helps set up the Java Payroll Management System

echo "=========================================="
echo "    Payroll System Setup Script"
echo "=========================================="
echo

# Function to check if a command exists
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Check Java installation
echo "1. Checking Java installation..."
if command_exists java && command_exists javac; then
    java_version=$(java -version 2>&1 | grep -oP 'version "1\.\K\d+|version "\K\d+')
    echo "   ✓ Java found (version: $java_version)"
    if [ "$java_version" -lt 8 ]; then
        echo "   ⚠ Warning: Java 8 or higher is recommended"
    fi
else
    echo "   ✗ Java not found. Please install Java 8 or higher"
    echo "     Download from: https://adoptopenjdk.net/"
    exit 1
fi

# Check MySQL installation
echo "2. Checking MySQL installation..."
if command_exists mysql; then
    echo "   ✓ MySQL client found"
else
    echo "   ⚠ MySQL client not found"
    echo "     Please install MySQL: https://dev.mysql.com/downloads/"
fi

# Create necessary directories
echo "3. Creating directory structure..."
mkdir -p build/classes
mkdir -p dist/lib
mkdir -p logs
echo "   ✓ Directories created"

# Copy configuration file
echo "4. Setting up configuration..."
if [ ! -f "config/database.properties" ]; then
    echo "   ✗ Configuration file not found!"
    echo "     Please ensure config/database.properties exists"
    exit 1
else
    echo "   ✓ Configuration file found"
fi

# Download MySQL Connector if needed
echo "5. Checking MySQL Connector..."
MYSQL_CONNECTOR="mysql-connector-java-8.0.33.jar"
LIB_DIR="lib"
mkdir -p $LIB_DIR

if [ ! -f "$LIB_DIR/$MYSQL_CONNECTOR" ]; then
    echo "   Downloading MySQL Connector..."
    curl -o "$LIB_DIR/$MYSQL_CONNECTOR" "https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.33/mysql-connector-java-8.0.33.jar"
    if [ $? -eq 0 ]; then
        echo "   ✓ MySQL Connector downloaded"
    else
        echo "   ⚠ Failed to download MySQL Connector automatically"
        echo "     Please download manually from: https://dev.mysql.com/downloads/connector/j/"
    fi
else
    echo "   ✓ MySQL Connector found"
fi

# Compile Java files
echo "6. Compiling Java source files..."
CLASSPATH=".:$LIB_DIR/*"
javac -cp "$CLASSPATH" -d build/classes src/*.java
if [ $? -eq 0 ]; then
    echo "   ✓ Compilation successful"
else
    echo "   ✗ Compilation failed"
    exit 1
fi

# Create executable JAR
echo "7. Creating executable JAR..."
cd build/classes
jar -cfm ../../dist/PayrollSystem.jar ../../manifest.mf *.class ../../src/icon
cd ../..

if [ -f "dist/PayrollSystem.jar" ]; then
    echo "   ✓ JAR file created: dist/PayrollSystem.jar"
else
    echo "   ⚠ JAR creation may have failed"
fi

# Database setup prompt
echo "8. Database setup..."
echo "   Please ensure MySQL is running and execute the following:"
echo "   1. Create database: mysql -u root -p < database/schema.sql"
echo "   2. Insert sample data: mysql -u root -p < database/sample_data.sql"
echo "   3. Update database credentials in config/database.properties"

echo
echo "=========================================="
echo "    Setup Complete!"
echo "=========================================="
echo
echo "To run the application:"
echo "  Method 1: java -cp \".:lib/*:dist/PayrollSystem.jar\" Splash"
echo "  Method 2: Double-click dist/PayrollSystem.jar (if configured)"
echo
echo "Default login credentials:"
echo "  Username: admin"
echo "  Password: admin123"
echo
echo "Make sure to:"
echo "  1. Configure your database connection in config/database.properties"
echo "  2. Run the database setup scripts in the database/ directory"
echo "  3. Start MySQL service before running the application"
echo