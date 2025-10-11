import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DatabaseInitializer {

    public static void initializeDatabase() {
        try (Connection connection = Conn.getConnection()) {
            if (!tableExists(connection, "employee")) {
                System.out.println("Initializing database schema...");
                executeSqlScript(connection, "database/h2_schema.sql");
                System.out.println("Database schema initialized successfully!");
            } else {
                System.out.println("Database already initialized.");
            }
            if (isTableEmpty(connection, "login")) {
                System.out.println("Inserting sample data...");
                executeSqlScript(connection, "database/h2_sample_data.sql");
                System.out.println("Sample data inserted successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static boolean tableExists(Connection conn, String tableName) throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();
        try (ResultSet rs = metaData.getTables(null, null, tableName, null)) {
            return rs.next();
        }
    }

    private static boolean isTableEmpty(Connection conn, String tableName) throws SQLException {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName)) {
            return rs.next() && rs.getInt(1) == 0;
        }
    }

    private static void executeSqlScript(Connection conn, String filePath) throws SQLException {
        try (InputStream is = DatabaseInitializer.class.getClassLoader().getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is));
             Statement stmt = conn.createStatement()) {
            String script = reader.lines().collect(Collectors.joining("\n"));
            for (String sql : script.split(";")) {
                if (!sql.trim().isEmpty()) {
                    stmt.execute(sql);
                }
            }
        } catch (Exception e) {
            throw new SQLException("Failed to execute SQL script: " + filePath, e);
        }
    }
}