import java.sql.*;

/**
 * Login Test for Payroll System
 * Tests the login functionality
 */
public class LoginTest {
    
    public static void main(String[] args) {
        testLogin();
    }
    
    public static void testLogin() {
        Conn conn = null;
        try {
            conn = new Conn();
            Connection connection = conn.getConnection();
            
            if (connection == null) {
                System.err.println("Failed to establish database connection");
                return;
            }
            
            System.out.println("=== LOGIN TEST ===");
            
            // Test admin login
            String query = "SELECT username, role FROM login WHERE username = ? AND password = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, "admin");
            pstmt.setString(2, "admin123");
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("✅ Admin login test PASSED");
                System.out.println("   Username: " + rs.getString("username"));
                System.out.println("   Role: " + rs.getString("role"));
            } else {
                System.out.println("❌ Admin login test FAILED");
            }
            rs.close();
            
            // Test HR login
            pstmt.setString(1, "hr");
            pstmt.setString(2, "hr123");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("✅ HR login test PASSED");
                System.out.println("   Username: " + rs.getString("username"));
                System.out.println("   Role: " + rs.getString("role"));
            } else {
                System.out.println("❌ HR login test FAILED");
            }
            rs.close();
            
            // Test invalid login
            pstmt.setString(1, "invalid");
            pstmt.setString(2, "invalid");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("❌ Invalid login test FAILED (should not find user)");
            } else {
                System.out.println("✅ Invalid login test PASSED (correctly rejected)");
            }
            rs.close();
            
            pstmt.close();
            
            System.out.println("\nLogin tests completed!");
            
        } catch (Exception e) {
            System.err.println("Error testing login: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
}
