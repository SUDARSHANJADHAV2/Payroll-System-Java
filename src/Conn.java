import java.sql.*;
import javax.swing.JOptionPane;

/**
 * Database Connection Manager for Payroll System
 * Handles secure database connections with proper error handling
 */
public class Conn {
    private Connection connection;
    private Statement statement;
    private Config config;

    public Conn() {
        config = Config.getInstance();
        connect();
    }

    /**
     * Establish database connection
     */
    private void connect() {
        try {
            // Load the modern MySQL driver
            Class.forName(config.getDatabaseDriver());

            // Create connection with proper URL parameters
            String url = config.getDatabaseUrl() + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            connection = DriverManager.getConnection(
                    url,
                    config.getDatabaseUsername(),
                    config.getDatabasePassword());

            statement = connection.createStatement();

            // Update deprecated properties for backward compatibility
            updateDeprecatedProperties();

            System.out.println("Database connection established successfully.");

        } catch (ClassNotFoundException e) {
            showError("Database driver not found: " + e.getMessage());
        } catch (SQLException e) {
            showError("Database connection failed: " + e.getMessage());
        } catch (Exception e) {
            showError("Unexpected error: " + e.getMessage());
        }
    }

    /**
     * Get database connection
     * 
     * @return Connection object
     */
    public Connection getConnection() {
        try {
            // Check if connection is still valid
            if (connection == null || connection.isClosed()) {
                connect();
            }
        } catch (SQLException e) {
            showError("Error checking connection: " + e.getMessage());
        }
        return connection;
    }

    /**
     * Get statement object
     * 
     * @return Statement object
     */
    public Statement getStatement() {
        return statement;
    }

    /**
     * Create a prepared statement for secure database operations
     * 
     * @param sql SQL query with placeholders
     * @return PreparedStatement object
     */
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return getConnection().prepareStatement(sql);
    }

    /**
     * Close database connection and resources
     */
    public void close() {
        try {
            if (statement != null && !statement.isClosed()) {
                statement.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
            System.out.println("Database connection closed.");
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }

    /**
     * Show error message to user
     * 
     * @param message Error message
     */
    private void showError(String message) {
        System.err.println(message);
        JOptionPane.showMessageDialog(null,
                "Database Error: " + message,
                "Connection Error",
                JOptionPane.ERROR_MESSAGE);
    }

    // Backward compatibility - keep old property names but mark as deprecated
    @Deprecated
    public Connection c = null;
    @Deprecated
    public Statement s = null;

    // Update deprecated properties when connection is established
    private void updateDeprecatedProperties() {
        this.c = this.connection;
        this.s = this.statement;
    }
}
