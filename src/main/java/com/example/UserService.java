package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// Custom exception for UserService
class UserServiceException extends Exception {
    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

public class UserService {
    // Use environment variables or a config file for DB credentials
    private static final String DB_URL = System.getenv("DB_URL");
    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");

    public void findUser(String username) throws UserServiceException {
        String query = "SELECT id, name, email FROM users WHERE name = ?"; // avoid SELECT *
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);

        } catch (SQLException e) {
            throw new UserServiceException(String.format("Error finding user: %s", username), e);
        }
    }

    public void deleteUser(String username) throws UserServiceException {
        String query = "DELETE FROM users WHERE name = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);


        } catch (SQLException e) {
            throw new UserServiceException(String.format("Error deleting user: %s", username), e);
        }
    }
}

