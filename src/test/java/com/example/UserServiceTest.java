package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserServiceTest {

    private UserService service;

    @BeforeEach
    void setup() throws SQLException {
        String url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
        try (Connection conn = DriverManager.getConnection(url, "sa", "");
             Statement st = conn.createStatement()) {
            st.execute("DROP TABLE IF EXISTS users");
            st.execute("CREATE TABLE users (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), email VARCHAR(255))");
            st.execute("INSERT INTO users(name, email) VALUES ('admin','admin@example.com')");
        }
        service = new UserService(url, "sa", "");
    }

    @Test
    @DisplayName("findUser executes select")
    void findUserRuns() throws SQLException {
        service.findUser("admin");
        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
             Statement st = conn.createStatement()) {
            var rs = st.executeQuery("SELECT COUNT(*) FROM users WHERE name='admin'");
            rs.next();
            assertEquals(1, rs.getInt(1));
        }
    }

    @Test
    @DisplayName("deleteUser deletes rows")
    void deleteUserRuns() throws SQLException {
        service.deleteUser("admin");
        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
             Statement st = conn.createStatement()) {
            var rs = st.executeQuery("SELECT COUNT(*) FROM users WHERE name='admin'");
            rs.next();
            assertEquals(0, rs.getInt(1));
        }
    }
}