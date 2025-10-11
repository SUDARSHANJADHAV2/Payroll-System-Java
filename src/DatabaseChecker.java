import java.sql.*;

/**
 * Database Checker for Payroll System
 * Checks what data is actually in the database
 */
public class DatabaseChecker {
    
    public static void main(String[] args) {
        checkDatabase();
    }
    
    public static void checkDatabase() {
        Conn conn = null;
        try {
            conn = new Conn();
            Connection connection = conn.getConnection();
            
            if (connection == null) {
                System.err.println("Failed to establish database connection");
                return;
            }
            
            System.out.println("=== DATABASE CHECK ===");
            
            // Check if login table exists and has data
            try {
                String query = "SELECT COUNT(*) as count FROM login";
                PreparedStatement pstmt = connection.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery();
                
                if (rs.next()) {
                    int count = rs.getInt("count");
                    System.out.println("Login table has " + count + " records");
                    
                    if (count > 0) {
                        // Show all login records
                        query = "SELECT username, password, role FROM login";
                        pstmt = connection.prepareStatement(query);
                        rs = pstmt.executeQuery();
                        
                        System.out.println("\nLogin records:");
                        while (rs.next()) {
                            System.out.println("Username: " + rs.getString("username") + 
                                             ", Password: " + rs.getString("password") + 
                                             ", Role: " + rs.getString("role"));
                        }
                    } else {
                        System.out.println("No login records found! Inserting default users...");
                        insertDefaultUsers(connection);
                    }
                }
                rs.close();
                pstmt.close();
                
            } catch (SQLException e) {
                System.out.println("Login table doesn't exist or has issues: " + e.getMessage());
                System.out.println("Creating login table and inserting default users...");
                createLoginTable(connection);
                insertDefaultUsers(connection);
            }
            
            // Check employee table
            try {
                String query = "SELECT COUNT(*) as count FROM employee";
                PreparedStatement pstmt = connection.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery();
                
                if (rs.next()) {
                    int count = rs.getInt("count");
                    System.out.println("\nEmployee table has " + count + " records");
                }
                rs.close();
                pstmt.close();
            } catch (SQLException e) {
                System.out.println("Employee table issue: " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.err.println("Error checking database: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    private static void createLoginTable(Connection conn) throws SQLException {
        String createLoginTable = "CREATE TABLE IF NOT EXISTS login (" +
                "id INT PRIMARY KEY IDENTITY, " +
                "username VARCHAR(50) NOT NULL UNIQUE, " +
                "password VARCHAR(255) NOT NULL, " +
                "role VARCHAR(20) DEFAULT 'admin', " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
        
        conn.createStatement().execute(createLoginTable);
    }
    
    private static void insertDefaultUsers(Connection conn) throws SQLException {
        String insertUser = "INSERT INTO login (username, password, role) VALUES (?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(insertUser);
        
        // Insert admin user
        pstmt.setString(1, "admin");
        pstmt.setString(2, "admin123");
        pstmt.setString(3, "admin");
        pstmt.executeUpdate();
        
        // Insert hr user
        pstmt.setString(1, "hr");
        pstmt.setString(2, "hr123");
        pstmt.setString(3, "hr");
        pstmt.executeUpdate();
        
        pstmt.close();
        System.out.println("Default users inserted successfully");
    }
}
