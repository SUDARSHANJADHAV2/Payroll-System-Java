import java.sql.*;

/**
 * Test class to verify data loading
 */
public class TestDataLoader {
    
    public static void main(String[] args) {
        testEmployeeData();
        testAttendanceData();
    }
    
    private static void testEmployeeData() {
        System.out.println("=== Testing Employee Data ===");
        Conn conn = null;
        try {
            conn = new Conn();
            String query = "SELECT emp_id, name, gender FROM employee";
            ResultSet rs = conn.s.executeQuery(query);
            
            int count = 0;
            while (rs.next()) {
                count++;
                System.out.println("Employee " + count + ": " + 
                    rs.getString("emp_id") + " - " + 
                    rs.getString("name") + " (" + 
                    rs.getString("gender") + ")");
            }
            rs.close();
            System.out.println("Total employees: " + count);
            
        } catch (Exception e) {
            System.err.println("Error loading employee data: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) conn.close();
        }
    }
    
    private static void testAttendanceData() {
        System.out.println("\n=== Testing Attendance Data ===");
        Conn conn = null;
        try {
            conn = new Conn();
            String query = "SELECT emp_id, date, first_half, second_half FROM attendance";
            ResultSet rs = conn.s.executeQuery(query);
            
            int count = 0;
            while (rs.next()) {
                count++;
                System.out.println("Attendance " + count + ": " + 
                    rs.getString("emp_id") + " - " + 
                    rs.getString("date") + " (" + 
                    rs.getString("first_half") + "/" + 
                    rs.getString("second_half") + ")");
            }
            rs.close();
            System.out.println("Total attendance records: " + count);
            
        } catch (Exception e) {
            System.err.println("Error loading attendance data: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) conn.close();
        }
    }
}
