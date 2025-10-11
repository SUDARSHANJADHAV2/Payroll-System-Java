import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseUtils {

    public static List<Map<String, Object>> executeQuery(String query, Object... parameters) {
        List<Map<String, Object>> results = new ArrayList<>();
        try (Connection conn = Conn.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            for (int i = 0; i < parameters.length; i++) {
                pstmt.setObject(i + 1, parameters[i]);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public static int executeUpdate(String query, Object... parameters) {
        int affectedRows = 0;
        try (Connection conn = Conn.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            for (int i = 0; i < parameters.length; i++) {
                pstmt.setObject(i + 1, parameters[i]);
            }

            affectedRows = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    public static boolean recordExists(String tableName, String whereClause, Object... parameters) {
        String query = "SELECT COUNT(*) FROM " + tableName + " WHERE " + whereClause;
        List<Map<String, Object>> results = executeQuery(query, parameters);

        if (!results.isEmpty()) {
            Object count = results.get(0).values().iterator().next();
            return ((Number) count).intValue() > 0;
        }

        return false;
    }

    public static String getNextEmployeeId() {
        String query = "SELECT emp_id FROM employee ORDER BY emp_id DESC LIMIT 1";
        List<Map<String, Object>> results = executeQuery(query);

        if (!results.isEmpty()) {
            String lastId = (String) results.get(0).get("emp_id");
            int number = Integer.parseInt(lastId.substring(3)) + 1;
            return "EMP" + String.format("%03d", number);
        } else {
            return "EMP001";
        }
    }

    public static boolean testConnection() {
        try (Connection conn = Conn.getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}