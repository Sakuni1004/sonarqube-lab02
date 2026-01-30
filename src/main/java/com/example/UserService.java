package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

// Custom exception for UserService
class UserServiceException extends Exception {
    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

public class UserService {

    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    // DB credentials from environment
    private static final String DB_URL = System.getenv("DB_URL");
    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");

    // Find user by name
    public void findUser(String username) throws UserServiceException {
        String query = "SELECT id, name, email FROM users WHERE name = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                boolean hasResults = false;

                while (rs.next()) {
                    hasResults = true;
                    final String name = rs.getString("name");
                    final String email = rs.getString("email");

                    // Lazy logging, evaluated only if INFO enabled
                    logger.info(() -> String.format("User found: %s, Email: %s", name, email));
                }

                if (!hasResults) {
                    logger.info(() -> String.format("No user found with name: %s", username));
                }
            }

        } catch (SQLException e) {
            throw new UserServiceException(String.format("Error finding user: %s", username), e);
        }
    }

    // Delete user by name
    public void deleteUser(String username) throws UserServiceException {
        String query = "DELETE FROM users WHERE name = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);
            int rowsAffected = ps.executeUpdate();

            // Lazy logging
            logger.info(() -> String.format("Deleted %d user(s) with name: %s", rowsAffected, username));

        } catch (SQLException e) {
            throw new UserServiceException(String.format("Error deleting user: %s", username), e);
        }
    }
}
