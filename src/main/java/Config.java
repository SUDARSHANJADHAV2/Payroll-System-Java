import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static Config instance;
    private final Properties properties;
    private static final String CONFIG_FILE = "config/database.properties";

    private Config() {
        properties = new Properties();
        loadProperties();
    }

    public static synchronized Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    private void loadProperties() {
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Error: Configuration file not found or cannot be read: " + CONFIG_FILE);
            System.err.println("Please ensure the file exists and has the correct permissions.");
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public String getDatabaseUrl() {
        return getProperty("db.url", "jdbc:mysql://localhost:3306/payroll_system");
    }

    public String getDatabaseUsername() {
        return getProperty("db.username", "root");
    }



    public String getDatabasePassword() {
        return getProperty("db.password", "");
    }

    public String getDatabaseDriver() {
        return getProperty("db.driver", "com.mysql.cj.jdbc.Driver");
    }

    public String getAppName() {
        return getProperty("app.name", "Payroll System");
    }

    public String getAppVersion() {
        return getProperty("app.version", "3.1");
    }
}