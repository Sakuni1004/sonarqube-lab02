package com.example;

/**
 * Runtime wrapper for SQL access errors to avoid leaking SQLExceptions from suppliers.
 */
public class DatabaseAccessException extends RuntimeException {

    public DatabaseAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}