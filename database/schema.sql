-- Payroll System Database Schema
-- Create database if it doesn't exist
CREATE DATABASE IF NOT EXISTS payroll_system;
USE payroll_system;

-- Create login table for authentication
CREATE TABLE IF NOT EXISTS login (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'admin',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create employee table
CREATE TABLE IF NOT EXISTS employee (
    emp_id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    gender ENUM('Male', 'Female') NOT NULL,
    address TEXT,
    state VARCHAR(50),
    city VARCHAR(50),
    email VARCHAR(100),
    phone VARCHAR(15),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create salary table
CREATE TABLE IF NOT EXISTS salary (
    emp_id VARCHAR(10) PRIMARY KEY,
    basic_salary DECIMAL(10,2) DEFAULT 0.00,
    hra DECIMAL(10,2) DEFAULT 0.00,
    da DECIMAL(10,2) DEFAULT 0.00,
    med DECIMAL(10,2) DEFAULT 0.00,
    pf DECIMAL(10,2) DEFAULT 0.00,
    advance DECIMAL(10,2) DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (emp_id) REFERENCES employee(emp_id) ON DELETE CASCADE
);

-- Create attendance table
CREATE TABLE IF NOT EXISTS attendance (
    id INT PRIMARY KEY AUTO_INCREMENT,
    emp_id VARCHAR(10) NOT NULL,
    date DATE NOT NULL,
    first_half ENUM('Present', 'Absent') DEFAULT 'Present',
    second_half ENUM('Present', 'Absent') DEFAULT 'Present',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (emp_id) REFERENCES employee(emp_id) ON DELETE CASCADE,
    UNIQUE KEY unique_emp_date (emp_id, date)
);

-- Create payslip table for generated payslips
CREATE TABLE IF NOT EXISTS payslip (
    id INT PRIMARY KEY AUTO_INCREMENT,
    emp_id VARCHAR(10) NOT NULL,
    month VARCHAR(20) NOT NULL,
    year INT NOT NULL,
    total_salary DECIMAL(10,2),
    generated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (emp_id) REFERENCES employee(emp_id) ON DELETE CASCADE
);

-- Create indexes for better performance
CREATE INDEX idx_employee_name ON employee(name);
CREATE INDEX idx_attendance_date ON attendance(date);
CREATE INDEX idx_payslip_month_year ON payslip(month, year);