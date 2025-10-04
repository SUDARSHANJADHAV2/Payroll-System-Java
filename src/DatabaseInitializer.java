import java.sql.*;
import java.io.*;

/**
 * Database Initializer for Payroll System
 * Creates tables and inserts sample data if database is empty
 */
public class DatabaseInitializer {
    
    public static void main(String[] args) {
        initializeDatabase();
    }
    
    public static void initializeDatabase() {
        Conn conn = null;
        try {
            conn = new Conn();
            Connection connection = conn.getConnection();
            
            if (connection == null) {
                System.err.println("Failed to establish database connection");
                return;
            }
            
            // Check if tables exist
            if (!tableExists(connection, "employee")) {
                System.out.println("Initializing database schema...");
                createTables(connection);
                insertSampleData(connection);
                System.out.println("Database initialized successfully!");
            } else {
                System.out.println("Database already initialized.");
            }
            
        } catch (Exception e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    private static boolean tableExists(Connection conn, String tableName) throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet rs = metaData.getTables(null, null, tableName.toUpperCase(), null);
        boolean exists = rs.next();
        rs.close();
        return exists;
    }
    
    private static void createTables(Connection conn) throws SQLException {
        String[] createStatements = {
            // Create login table
            "CREATE TABLE IF NOT EXISTS login (" +
            "id INT PRIMARY KEY AUTO_INCREMENT, " +
            "username VARCHAR(50) NOT NULL UNIQUE, " +
            "password VARCHAR(255) NOT NULL, " +
            "role VARCHAR(20) DEFAULT 'admin', " +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
            "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)",
            
            // Create employee table
            "CREATE TABLE IF NOT EXISTS employee (" +
            "emp_id VARCHAR(10) PRIMARY KEY, " +
            "name VARCHAR(100) NOT NULL, " +
            "gender VARCHAR(10) NOT NULL, " +
            "address TEXT, " +
            "state VARCHAR(50), " +
            "city VARCHAR(50), " +
            "email VARCHAR(100), " +
            "phone VARCHAR(15), " +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
            "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)",
            
            // Create salary table
            "CREATE TABLE IF NOT EXISTS salary (" +
            "emp_id VARCHAR(10) PRIMARY KEY, " +
            "basic_salary DECIMAL(10,2) DEFAULT 0.00, " +
            "hra DECIMAL(10,2) DEFAULT 0.00, " +
            "da DECIMAL(10,2) DEFAULT 0.00, " +
            "med DECIMAL(10,2) DEFAULT 0.00, " +
            "pf DECIMAL(10,2) DEFAULT 0.00, " +
            "advance DECIMAL(10,2) DEFAULT 0.00, " +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
            "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
            "FOREIGN KEY (emp_id) REFERENCES employee(emp_id) ON DELETE CASCADE)",
            
            // Create attendance table
            "CREATE TABLE IF NOT EXISTS attendance (" +
            "id INT PRIMARY KEY AUTO_INCREMENT, " +
            "emp_id VARCHAR(10) NOT NULL, " +
            "date DATE NOT NULL, " +
            "first_half VARCHAR(10) DEFAULT 'Present', " +
            "second_half VARCHAR(10) DEFAULT 'Present', " +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
            "FOREIGN KEY (emp_id) REFERENCES employee(emp_id) ON DELETE CASCADE, " +
            "CONSTRAINT unique_emp_date UNIQUE (emp_id, date))",
            
            // Create payslip table
            "CREATE TABLE IF NOT EXISTS payslip (" +
            "id INT PRIMARY KEY AUTO_INCREMENT, " +
            "emp_id VARCHAR(10) NOT NULL, " +
            "month VARCHAR(20) NOT NULL, " +
            "year INT NOT NULL, " +
            "total_salary DECIMAL(10,2), " +
            "generated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
            "FOREIGN KEY (emp_id) REFERENCES employee(emp_id) ON DELETE CASCADE)"
        };
        
        Statement stmt = conn.createStatement();
        for (String sql : createStatements) {
            stmt.execute(sql);
        }
        stmt.close();
    }
    
    private static void insertSampleData(Connection conn) throws SQLException {
        // Insert default users
        String insertUsers = "INSERT INTO login (username, password, role) VALUES (?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(insertUsers);
        
        pstmt.setString(1, "admin");
        pstmt.setString(2, "admin123");
        pstmt.setString(3, "admin");
        pstmt.executeUpdate();
        
        pstmt.setString(1, "hr");
        pstmt.setString(2, "hr123");
        pstmt.setString(3, "hr");
        pstmt.executeUpdate();
        pstmt.close();
        
        // Insert sample employees
        String insertEmployees = "INSERT INTO employee (emp_id, name, gender, address, state, city, email, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        pstmt = conn.prepareStatement(insertEmployees);
        
        String[][] employees = {
            {"EMP001", "John Smith", "Male", "123 Main St", "California", "Los Angeles", "john.smith@company.com", "555-0101"},
            {"EMP002", "Jane Doe", "Female", "456 Oak Ave", "New York", "New York City", "jane.doe@company.com", "555-0102"},
            {"EMP003", "Robert Johnson", "Male", "789 Pine St", "Texas", "Houston", "robert.johnson@company.com", "555-0103"},
            {"EMP004", "Sarah Williams", "Female", "321 Elm St", "Florida", "Miami", "sarah.williams@company.com", "555-0104"},
            {"EMP005", "Michael Brown", "Male", "654 Maple Ave", "Illinois", "Chicago", "michael.brown@company.com", "555-0105"}
        };
        
        for (String[] emp : employees) {
            for (int i = 0; i < emp.length; i++) {
                pstmt.setString(i + 1, emp[i]);
            }
            pstmt.executeUpdate();
        }
        pstmt.close();
        
        // Insert sample salary data
        String insertSalaries = "INSERT INTO salary (emp_id, basic_salary, hra, da, med, pf, advance) VALUES (?, ?, ?, ?, ?, ?, ?)";
        pstmt = conn.prepareStatement(insertSalaries);
        
        String[][] salaries = {
            {"EMP001", "50000.00", "5000.00", "2500.00", "1000.00", "2000.00", "0.00"},
            {"EMP002", "55000.00", "5500.00", "2750.00", "1000.00", "2200.00", "1000.00"},
            {"EMP003", "48000.00", "4800.00", "2400.00", "1000.00", "1920.00", "500.00"},
            {"EMP004", "52000.00", "5200.00", "2600.00", "1000.00", "2080.00", "0.00"},
            {"EMP005", "60000.00", "6000.00", "3000.00", "1000.00", "2400.00", "2000.00"}
        };
        
        for (String[] sal : salaries) {
            for (int i = 0; i < sal.length; i++) {
                pstmt.setString(i + 1, sal[i]);
            }
            pstmt.executeUpdate();
        }
        pstmt.close();
        
        System.out.println("Sample data inserted successfully!");
    }
}
