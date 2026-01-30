package com.example;

import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    static void run(UserService service, Calculator calc) throws SQLException {
        logger.info("Calculation result: {}", calc.calculate(10, 5, "add-again"));
        service.findUser("admin");
        service.deleteUser("admin"); // NEW dangerous call
    }

    public static void main(String[] args) throws SQLException {
        run(new UserService(), new Calculator());
    }
}