import java.io.*;
import java.util.Properties;

/**
 * Configuration Manager for Payroll System
 * Handles loading and accessing configuration properties
 */
public class Config {
    private static Config instance;
    private Properties properties;
    private static final String CONFIG_FILE = "config/database.properties";

    private Config() {
        loadProperties();
    }

    /**
     * Get singleton instance of Config
     * 
     * @return Config instance
     */
    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    /**
     * Load properties from configuration file
     */
    private void loadProperties() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                // Try to load from file system if not found in classpath
                try (FileInputStream fileInput = new FileInputStream(CONFIG_FILE)) {
                    properties.load(fileInput);
                } catch (IOException e) {
                    // Set default properties if config file not found
                    setDefaultProperties();
                    System.err.println("Warning: Configuration file not found. Using default settings.");
                }
            } else {
                properties.load(input);
            }
        } catch (IOException e) {
            setDefaultProperties();
            System.err.println("Error loading configuration: " + e.getMessage());
        }
    }

    /**
     * Set default properties if configuration file is not available
     */
    private void setDefaultProperties() {
        properties.setProperty("db.url", "jdbc:mysql://localhost:3306/payroll_system");
        properties.setProperty("db.username", "root");
        properties.setProperty("db.password", "mysql123");
        properties.setProperty("db.driver", "com.mysql.cj.jdbc.Driver");
        properties.setProperty("app.name", "Payroll Management System");
        properties.setProperty("app.version", "3.1");
    }

    /**
     * Get property value by key
     * 
     * @param key Property key
     * @return Property value
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Get property value with default fallback
     * 
     * @param key          Property key
     * @param defaultValue Default value if key not found
     * @return Property value or default
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    // Convenience methods for commonly used properties
    public String getDatabaseUrl() {
        return getProperty("db.url");
    }

    public String getDatabaseUsername() {
        return getProperty("db.username");
    }

    public String getDatabasePassword() {
        return getProperty("db.password");
    }

    public String getDatabaseDriver() {
        return getProperty("db.driver");
    }

    public String getAppName() {
        return getProperty("app.name", "Payroll System");
    }

    public String getAppVersion() {
        return getProperty("app.version", "3.1");
    }
}