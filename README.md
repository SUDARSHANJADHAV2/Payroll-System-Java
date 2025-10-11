# Payroll Management System

A modernized **Java Swing Desktop Application** for Employee Payroll Management, built with Maven and featuring a MySQL database integration. This system provides a complete solution for managing employee records, attendance, salaries, and payslips, with an improved user interface and a new dark mode theme.

![Java](https://img.shields.io/badge/Java-8%2B-orange)
![MySQL](https://img.shields.io/badge/MySQL-8.0%2B-blue)
![Maven](https://img.shields.io/badge/Build-Maven--red)
![License](https://img.shields.io/badge/License-MIT-yellow)

## 🚀 Features

- **Modern GUI:** A clean and intuitive user interface with a new dark mode theme.
- **Maven Build:** A fully automated build process using Maven for easy dependency management.
- **Connection Pooling:** High-performance database connection pooling with HikariCP.
- **Secure Authentication:** Role-based login with SQL injection protection.
- **Employee Management:** Add, update, delete, and view employee records.
- **Salary Management:** Calculate salaries with HRA, DA, Medical, and PF components.
- **Attendance Tracking:** Mark and view employee attendance records.
- **Payslip Generation:** Generate detailed payslips for employees.
- **Cross-Platform Utilities:** Launch Notepad, Calculator, and a web browser from within the application.

## 📋 Prerequisites

- **Java Development Kit (JDK) 8 or higher**
- **Apache Maven 3.6 or higher**
- **MySQL Server 5.7 or higher**

## 🛠️ Installation & Setup

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/your-username/payroll-system.git
    cd payroll-system
    ```

2.  **Configure the database:**
    -   Open `src/main/resources/config/database.properties` and update the database URL, username, and password.
    -   Create the database in MySQL:
        ```sql
        CREATE DATABASE payroll_system;
        ```

3.  **Build and run the application:**
    ```bash
    mvn clean install
    java -jar target/payroll-system-3.1.0.jar
    ```

## 🎯 Usage Guide

-   **Login:** Use the default credentials `admin` / `admin123` to log in.
-   **Toggle Theme:** Go to `System -> Toggle Theme` to switch between light and dark mode.
-   **Employee Management:** Use the `Master` menu to manage employees and salaries.
-   **Update Records:** Use the `Update` menu to update employee and salary information.
-   **Generate Reports:** Use the `Reports` menu to generate payslips and view attendance records.

## 📊 Database Schema

The database schema is automatically created and populated with sample data on the first run. The schema is defined in `src/main/resources/database/schema.sql`, and the sample data is in `src/main/resources/database/sample_data.sql`.

## 🤝 Contributing

Contributions are welcome! Please fork the repository and submit a pull request with your changes.

## 📄 License

This project is licensed under the **MIT License**. See the [LICENSE](LICENSE) file for details.