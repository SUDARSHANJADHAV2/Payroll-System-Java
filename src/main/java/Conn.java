import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conn {
    private static HikariDataSource dataSource;

    static {
        try {
            Config config = Config.getInstance();
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(config.getDatabaseUrl());
            hikariConfig.setUsername(config.getDatabaseUsername());
            hikariConfig.setPassword(config.getDatabasePassword());
            hikariConfig.setDriverClassName(config.getDatabaseDriver());
            hikariConfig.setMaximumPoolSize(10);
            hikariConfig.setMinimumIdle(5);
            hikariConfig.setConnectionTimeout(30000);
            hikariConfig.setIdleTimeout(600000);
            hikariConfig.setMaxLifetime(1800000);
            dataSource = new HikariDataSource(hikariConfig);
        } catch (Exception e) {
            showError("Failed to initialize database connection pool: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    private static void showError(String message) {
        System.err.println(message);
        JOptionPane.showMessageDialog(null, "Database Error: " + message, "Connection Error", JOptionPane.ERROR_MESSAGE);
    }
}