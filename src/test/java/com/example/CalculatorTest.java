package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalculatorTest {

    private final Calculator calc = new Calculator();

    @Test
    @DisplayName("adds numbers")
    void addsNumbers() {
        assertEquals(15, calc.calculate(10, 5, "add"));
        assertEquals(15, calc.calculate(10, 5, "add-again"));
    }

    @Test
    @DisplayName("subtracts numbers")
    void subtractsNumbers() {
        assertEquals(5, calc.calculate(10, 5, "sub"));
        assertEquals(5, calc.calculate(10, 5, "sub-again"));
    }

    @Test
    @DisplayName("multiplies numbers")
    void multipliesNumbers() {
        assertEquals(50, calc.calculate(10, 5, "mul"));
    }

    @Test
    @DisplayName("divides numbers and handles divide by zero")
    void dividesNumbers() {
        assertEquals(2, calc.calculate(10, 5, "div"));
        assertEquals(0, calc.calculate(10, 0, "div"));
    }

    @Test
    @DisplayName("modulus")
    void modulus() {
        assertEquals(0, calc.calculate(10, 5, "mod"));
    }

    @Test
    @DisplayName("power function")
    void power() {
        assertEquals(100000, calc.calculate(10, 5, "pow"));
    }

    @Test
    @DisplayName("unknown op returns 0")
    void unknownOp() {
        assertEquals(0, calc.calculate(10, 5, "nope"));
    }
}