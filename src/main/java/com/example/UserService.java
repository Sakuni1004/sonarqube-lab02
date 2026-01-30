package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.function.Supplier;

public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    // SECURITY ISSUE: Hardcoded credentials
    private final String jdbcUrl;
    private final String user;
    private final String password;
    private final Supplier<Connection> connectionSupplier;

    public UserService() {
        this("jdbc:mysql://localhost/db", "root", "admin123");
    }

    public UserService(String jdbcUrl, String user, String password) {
        this.jdbcUrl = jdbcUrl;
        this.user = user;
        this.password = password;
        this.connectionSupplier = () -> {
            try {
                return DriverManager.getConnection(this.jdbcUrl, this.user, this.password);
            } catch (SQLException e) {
                throw new DatabaseAccessException("Failed to obtain connection", e);
            }
        };
    }

    // SQL INJECTION: Fixed with PreparedStatement
    public void findUser(String username) throws SQLException {
        try (Connection conn = connectionSupplier.get();
             PreparedStatement pst = conn.prepareStatement(
                "SELECT id, name, email FROM users WHERE name = ?")) {

            pst.setString(1, username);
            pst.execute();
        }
    }

    // SQL INJECTION: Fixed with PreparedStatement 
    public void deleteUser(String username) throws SQLException { 
        try (Connection conn = connectionSupplier.get();
             PreparedStatement pst = conn.prepareStatement(
                "DELETE FROM users WHERE name = ?")) {

            pst.setString(1, username);
            pst.execute();
        }
    } 

    // SMELL: Unused method
    public void notUsed() {
        logger.debug("I am never called");
    }
}