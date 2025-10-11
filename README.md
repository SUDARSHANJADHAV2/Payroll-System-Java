# Payroll Management System

This is a comprehensive desktop application for managing employee payroll, built using Java Swing. It provides a user-friendly interface for managing employees, salaries, attendance, and generating payslips.

## Features

*   **Employee Management:** Add, update, and view employee details.
*   **Salary Management:** Manage salary components for each employee.
*   **Attendance Tracking:** Record daily attendance for employees.
*   **Payslip Generation:** Generate monthly payslips for all employees.
*   **Secure Login:** Role-based access control with hashed password storage.
*   **Theme Switching:** Toggle between light and dark themes.

## Technologies Used

*   **Java Swing:** For the graphical user interface.
*   **H2 Database:** An embedded, in-memory database for data storage.
*   **Maven:** For project build and dependency management.
*   **HikariCP:** For high-performance JDBC connection pooling.
*   **jBCrypt:** For hashing and securing user passwords.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

*   Java Development Kit (JDK) 8 or higher
*   Apache Maven

### Cloning the Repository

First, clone the repository to your local machine:

```bash
git clone https://github.com/your-username/payroll-system.git
cd payroll-system
```

### Building the Project

Use Maven to build the project. This will download all the necessary dependencies and compile the source code.

```bash
mvn clean install
```

This command will generate a JAR file in the `target` directory named `payroll-system-3.1.0.jar`.

### Running the Application

Once the project is built, you can run the application using the following command:

```bash
java -jar target/payroll-system-3.1.0.jar
```

The application will start, and the database will be automatically initialized with a default schema and sample data.

### Default Login Credentials

The application comes with two default users:

*   **Username:** `admin`
*   **Password:** `admin123`

*   **Username:** `hr`
*   **Password:** `hr123`

Upon the first successful login with these credentials, the passwords will be automatically hashed and updated in the database for enhanced security.