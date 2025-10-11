import java.sql.*;

/**
 * Sample Data Inserter for Payroll System
 * Inserts sample employees and salary data
 */
public class SampleDataInserter {
    
    public static void main(String[] args) {
        insertSampleData();
    }
    
    public static void insertSampleData() {
        Conn conn = null;
        try {
            conn = new Conn();
            Connection connection = conn.getConnection();
            
            if (connection == null) {
                System.err.println("Failed to establish database connection");
                return;
            }
            
            System.out.println("=== INSERTING SAMPLE DATA ===");
            
            // Insert sample employees
            String insertEmployee = "INSERT INTO employee (emp_id, name, gender, address, state, city, email, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(insertEmployee);
            
            String[][] employees = {
                {"EMP001", "John Smith", "Male", "123 Main St", "California", "Los Angeles", "john.smith@company.com", "555-0101"},
                {"EMP002", "Jane Doe", "Female", "456 Oak Ave", "New York", "New York City", "jane.doe@company.com", "555-0102"},
                {"EMP003", "Robert Johnson", "Male", "789 Pine St", "Texas", "Houston", "robert.johnson@company.com", "555-0103"},
                {"EMP004", "Sarah Williams", "Female", "321 Elm St", "Florida", "Miami", "sarah.williams@company.com", "555-0104"},
                {"EMP005", "Michael Brown", "Male", "654 Maple Ave", "Illinois", "Chicago", "michael.brown@company.com", "555-0105"}
            };
            
            for (String[] emp : employees) {
                try {
                    for (int i = 0; i < emp.length; i++) {
                        pstmt.setString(i + 1, emp[i]);
                    }
                    pstmt.executeUpdate();
                    System.out.println("Inserted employee: " + emp[1] + " (" + emp[0] + ")");
                } catch (SQLException e) {
                    if (e.getErrorCode() == 23505) { // Duplicate key
                        System.out.println("Employee " + emp[1] + " already exists, skipping...");
                    } else {
                        System.out.println("Error inserting " + emp[1] + ": " + e.getMessage());
                    }
                }
            }
            pstmt.close();
            
            // Insert sample salary data
            String insertSalary = "INSERT INTO salary (emp_id, basic_salary, hra, da, med, pf, advance) VALUES (?, ?, ?, ?, ?, ?, ?)";
            pstmt = connection.prepareStatement(insertSalary);
            
            String[][] salaries = {
                {"EMP001", "50000.00", "5000.00", "2500.00", "1000.00", "2000.00", "0.00"},
                {"EMP002", "55000.00", "5500.00", "2750.00", "1000.00", "2200.00", "1000.00"},
                {"EMP003", "48000.00", "4800.00", "2400.00", "1000.00", "1920.00", "500.00"},
                {"EMP004", "52000.00", "5200.00", "2600.00", "1000.00", "2080.00", "0.00"},
                {"EMP005", "60000.00", "6000.00", "3000.00", "1000.00", "2400.00", "2000.00"}
            };
            
            for (String[] sal : salaries) {
                try {
                    for (int i = 0; i < sal.length; i++) {
                        pstmt.setString(i + 1, sal[i]);
                    }
                    pstmt.executeUpdate();
                    System.out.println("Inserted salary for: " + sal[0]);
                } catch (SQLException e) {
                    if (e.getErrorCode() == 23505) { // Duplicate key
                        System.out.println("Salary for " + sal[0] + " already exists, skipping...");
                    } else {
                        System.out.println("Error inserting salary for " + sal[0] + ": " + e.getMessage());
                    }
                }
            }
            pstmt.close();
            
            System.out.println("\nSample data insertion completed!");
            
        } catch (Exception e) {
            System.err.println("Error inserting sample data: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
}
