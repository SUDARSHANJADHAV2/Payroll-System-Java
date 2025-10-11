import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Database Utility class for common database operations
 * Provides secure, reusable methods for database access
 */
public class DatabaseUtils {

    /**
     * Execute a SELECT query and return results as a list of maps
     * 
     * @param query      SQL SELECT query with placeholders
     * @param parameters Parameters for the query placeholders
     * @return List of result rows as key-value maps
     */
    public static List<Map<String, Object>> executeQuery(String query, Object... parameters) {
        List<Map<String, Object>> results = new ArrayList<>();
        Conn conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = new Conn();
            pstmt = conn.prepareStatement(query);

            // Set parameters
            for (int i = 0; i < parameters.length; i++) {
                pstmt.setObject(i + 1, parameters[i]);
            }

            rs = pstmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = rs.getObject(i);
                    row.put(columnName, value);
                }
                results.add(row);
            }

        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }

        return results;
    }

    /**
     * Execute an INSERT, UPDATE, or DELETE query
     * 
     * @param query      SQL query with placeholders
     * @param parameters Parameters for the query placeholders
     * @return Number of affected rows
     */
    public static int executeUpdate(String query, Object... parameters) {
        int affectedRows = 0;
        Conn conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = new Conn();
            pstmt = conn.prepareStatement(query);

            // Set parameters
            for (int i = 0; i < parameters.length; i++) {
                pstmt.setObject(i + 1, parameters[i]);
            }

            affectedRows = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error executing update: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(null, pstmt, conn);
        }

        return affectedRows;
    }

    /**
     * Check if a record exists in the database
     * 
     * @param tableName   Name of the table
     * @param whereClause WHERE clause (without WHERE keyword)
     * @param parameters  Parameters for the WHERE clause
     * @return true if record exists, false otherwise
     */
    public static boolean recordExists(String tableName, String whereClause, Object... parameters) {
        String query = "SELECT COUNT(*) FROM " + tableName + " WHERE " + whereClause;
        List<Map<String, Object>> results = executeQuery(query, parameters);

        if (!results.isEmpty()) {
            Object count = results.get(0).values().iterator().next();
            return ((Number) count).intValue() > 0;
        }

        return false;
    }

    /**
     * Get the next available employee ID
     * 
     * @return Next employee ID in format EMP####
     */
    public static String getNextEmployeeId() {
        String query = "SELECT emp_id FROM employee WHERE emp_id LIKE 'EMP%' ORDER BY emp_id DESC LIMIT 1";
        List<Map<String, Object>> results = executeQuery(query);

        if (!results.isEmpty()) {
            String lastId = (String) results.get(0).get("emp_id");
            int number = Integer.parseInt(lastId.substring(3)) + 1;
            return "EMP" + String.format("%04d", number);
        } else {
            return "EMP0001";
        }
    }

    /**
     * Close database resources safely
     * 
     * @param rs   ResultSet to close
     * @param stmt Statement to close
     * @param conn Connection to close
     */
    private static void closeResources(ResultSet rs, PreparedStatement stmt, Conn conn) {
        try {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            System.err.println("Error closing database resources: " + e.getMessage());
        }
    }

    /**
     * Test database connection
     * 
     * @return true if connection is successful, false otherwise
     */
    public static boolean testConnection() {
        Conn conn = null;
        try {
            conn = new Conn();
            return conn.getConnection() != null && !conn.getConnection().isClosed();
        } catch (SQLException e) {
            System.err.println("Database connection test failed: " + e.getMessage());
            return false;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
}