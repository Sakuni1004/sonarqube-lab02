package example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    
    private static final String DB_URL = "jdbc:h2:mem:testdb";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";
    
    @BeforeAll
    static void setupDatabase() throws SQLException {
        // Set environment variables for UserService
        setEnv("DB_URL", DB_URL);
        setEnv("DB_USER", DB_USER);
        setEnv("DB_PASSWORD", DB_PASSWORD);
        
        // Create test table
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE users (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), email VARCHAR(255))");
            stmt.execute("INSERT INTO users (name, email) VALUES ('admin', 'admin@example.com')");
            stmt.execute("INSERT INTO users (name, email) VALUES ('testuser', 'test@example.com')");
        }
    }
    
    @AfterAll
    static void teardownDatabase() throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS users");
        }
    }
    
    private static void setEnv(String key, String value) {
        try {
            Class<?> processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
            java.lang.reflect.Field theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
            theEnvironmentField.setAccessible(true);
            @SuppressWarnings("unchecked")
            java.util.Map<String, String> env = (java.util.Map<String, String>) theEnvironmentField.get(null);
            env.put(key, value);
        } catch (Exception e) {
            // Fallback - this might not work on all JVMs
            System.setProperty(key, value);
        }
    }
    
    @Test
    void testFindUserAfterDelete() throws Exception {
        UserService service = new UserService();
        service.deleteUser("admin");
        // After delete, finding should still not throw
        assertDoesNotThrow(() -> service.findUser("admin"));
    }
    
    @Test
    void testInvalidDatabaseConnection() {
        // Temporarily change DB_URL to invalid
        setEnv("DB_URL", "jdbc:h2:mem:invalid_db_that_does_not_exist");
        UserService service = new UserService();
        
        // Should throw UserServiceException
        assertThrows(UserServiceException.class, () -> service.findUser("admin"));
        
        // Restore valid URL
        setEnv("DB_URL", DB_URL);
    }
}