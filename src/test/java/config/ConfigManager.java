package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigManager {
    private static final String CONFIG_FILE = "config.properties";
    private static final Properties PROPERTIES = loadProperties();

    private ConfigManager() {
        throw new UnsupportedOperationException("ConfigManager is a utility class and cannot be instantiated");
    }

    public static String getBaseUri() {
        return getRequiredProperty("base.uri");
    }

    public static String getEmail() {
        return getRequiredProperty("auth.email");
    }

    public static String getPassword() {
        return getRequiredProperty("auth.password");
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();

        try (InputStream inputStream = ConfigManager.class
                .getClassLoader()
                .getResourceAsStream(CONFIG_FILE)) {

            if (inputStream == null) {
                throw new IllegalStateException(
                        "Configuration file '" + CONFIG_FILE + "' was not found on the classpath. "
                                + "Expected location: src/test/resources/" + CONFIG_FILE);
            }

            properties.load(inputStream);
            return properties;
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to load configuration file '" + CONFIG_FILE + "'", exception);
        }
    }

    private static String getRequiredProperty(String key) {
        String value = PROPERTIES.getProperty(key);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Required configuration property is missing or blank: " + key);
        }

        return value.trim();
    }
}
