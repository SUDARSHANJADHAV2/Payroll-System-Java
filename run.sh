#!/bin/bash

# Payroll System Startup Script
# This script starts the Payroll Management System

echo "Starting Payroll Management System..."
echo "======================================"

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java not found. Please install Java 8 or higher."
    exit 1
fi

# Check if JAR file exists
if [ ! -f "dist/PayrollSystem.jar" ]; then
    echo "Error: PayrollSystem.jar not found."
    echo "Please run setup.sh first to compile the application."
    exit 1
fi

# Set classpath including MySQL connector
CLASSPATH=".:lib/*:dist/PayrollSystem.jar"

# Start the application
java -cp "$CLASSPATH" Splash

echo "Application closed."