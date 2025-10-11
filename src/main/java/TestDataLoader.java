import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDataLoader {

    public static void main(String[] args) {
        testEmployeeData();
        testAttendanceData();
    }

    private static void testEmployeeData() {
        System.out.println("=== Testing Employee Data ===");
        try (Connection conn = Conn.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT emp_id, name, gender FROM employee")) {

            int count = 0;
            while (rs.next()) {
                count++;
                System.out.println("Employee " + count + ": " +
                        rs.getString("emp_id") + " - " +
                        rs.getString("name") + " (" +
                        rs.getString("gender") + ")");
            }
            System.out.println("Total employees: " + count);

        } catch (SQLException e) {
            System.err.println("Error loading employee data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testAttendanceData() {
        System.out.println("\n=== Testing Attendance Data ===");
        try (Connection conn = Conn.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT emp_id, date, first_half, second_half FROM attendance")) {

            int count = 0;
            while (rs.next()) {
                count++;
                System.out.println("Attendance " + count + ": " +
                        rs.getString("emp_id") + " - " +
                        rs.getString("date") + " (" +
                        rs.getString("first_half") + "/" +
                        rs.getString("second_half") + ")");
            }
            System.out.println("Total attendance records: " + count);

        } catch (SQLException e) {
            System.err.println("Error loading attendance data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}