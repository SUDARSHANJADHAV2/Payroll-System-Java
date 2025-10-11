# Project Analysis Report: Payroll Management System

This document provides an analysis of the Payroll Management System, including its architecture, workflow, and a breakdown of its components.

## 1. Project Architecture

The Payroll Management System is a monolithic desktop application built with **Java Swing**. It follows a simple three-tier architecture:

*   **Presentation Tier (UI):** This layer is composed of Java Swing components. All the `JFrame` classes (`Login`, `Project`, `NewEmployee`, etc.) reside in this tier. It's responsible for displaying information to the user and capturing user input.
*   **Business Logic Tier:** This layer contains the application's core logic. It processes data between the presentation and data access tiers. Classes like `DatabaseInitializer` and the action listeners within the UI classes are part of this tier.
*   **Data Access Tier:** This layer is responsible for all communication with the database. The `Conn` class, which manages the database connection pool, is the primary component of this tier. SQL queries are embedded within the UI classes, which is not ideal, but it's a common pattern in smaller Swing applications.

## 2. Technology Stack

*   **Core Language:** Java 8
*   **UI Framework:** Java Swing
*   **Build & Dependency Management:** Apache Maven
*   **Database:** H2 (Embedded)
*   **Connection Pooling:** HikariCP
*   **Password Hashing:** jBCrypt

## 3. Workflow and Sequence of Operations

1.  **Application Startup:** The application is launched via the `Splash` class's `main` method.
2.  **Splash Screen:** A `SplashScreen` is displayed to the user for a few seconds.
3.  **Database Initialization:** While the splash screen is visible, the `DatabaseInitializer` class checks if the database exists. If not, it creates the schema and inserts sample data from the `h2_schema.sql` and `h2_sample_data.sql` files.
4.  **Login:** The `Login` window is displayed. The user enters their credentials.
5.  **Authentication:** The `Login` class verifies the credentials against the `login` table in the database. It uses **BCrypt** to check the hashed password. For backward compatibility, it can also handle plain text passwords and will automatically hash them upon the first successful login.
6.  **Main Application:** Upon successful login, the main `Project` window is displayed. This window contains a menu bar with all the application's features.
7.  **User Interaction:** The user can interact with the application through the menu bar, which opens various frames for managing employees, salaries, attendance, and generating reports.

## 4. File-by-File Breakdown

### Core Application Logic

*   `Splash.java`: The main entry point of the application. It initializes the theme and shows the splash screen.
*   `SplashScreen.java`: A simple frame that displays an image for a few seconds and then initializes the database and the login window.
*   `Login.java`: Handles user authentication. It uses BCrypt for password hashing and verification.
*   `Project.java`: The main application window after a successful login. It contains the menu bar and provides access to all the application's features.
*   `UserSession.java`: A static class that manages the currently logged-in user's session information (username and role).

### Database and Configuration

*   `Config.java`: A singleton class that loads the application's configuration from `config/database.properties`.
*   `Conn.java`: Manages the database connection pool using HikariCP.
*   `DatabaseInitializer.java`: Initializes the database schema and sample data on the first run.
*   `src/main/resources/config/database.properties`: The configuration file for the database connection.
*   `src/main/resources/database/h2_schema.sql`: The SQL script for creating the database schema.
*   `src/main/resources/database/h2_sample_data.sql`: The SQL script for inserting sample data into the database.

### Feature Modules (UI and Logic)

*   `NewEmployee.java`: A frame for adding new employees.
*   `ListEmployee.java`: A frame that displays a list of all employees.
*   `UpdateEmployee.java`: A frame for updating the details of an existing employee.
*   `Salary.java`: A frame for managing the salary components of an employee.
*   `UpdateSalary.java`: A frame for updating the salary of an employee.
*   `TakeAttendance.java`: A frame for recording employee attendance.
*   `ListAttendance.java`: A frame that displays the attendance records.
*   `PaySlip.java`: A frame for generating payslips.
*   `ThemeManager.java`: A utility class for switching between light and dark themes.
*   `DatabaseUtils.java`: A utility class for database-related operations, such as getting the next employee ID.