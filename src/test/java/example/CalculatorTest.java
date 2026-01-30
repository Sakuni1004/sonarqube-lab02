package example;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    
    private final Calculator calculator = new Calculator();
    
    @Test
    void testAdd() {
        assertEquals(5, calculator.calculate(2, 3, "add"));
        assertEquals(-1, calculator.calculate(-3, 2, "add"));
    }
    
    @Test
    void testSub() {
        assertEquals(1, calculator.calculate(3, 2, "sub"));
        assertEquals(-5, calculator.calculate(-3, 2, "sub"));
    }
    
    @Test
    void testMul() {
        assertEquals(6, calculator.calculate(2, 3, "mul"));
        assertEquals(0, calculator.calculate(0, 5, "mul"));
    }
    
    @Test
    void testDiv() {
        assertEquals(2, calculator.calculate(6, 3, "div"));
        assertEquals(-2, calculator.calculate(-6, 3, "div"));
    }
    
    @Test
    void testDivByZero() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class, 
            () -> calculator.calculate(5, 0, "div")
        );
        assertEquals("Division by zero", exception.getMessage());
    }
    
    @Test
    void testMod() {
        assertEquals(1, calculator.calculate(7, 3, "mod"));
        assertEquals(0, calculator.calculate(6, 3, "mod"));
    }
    
    @Test
    void testModByZero() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class, 
            () -> calculator.calculate(5, 0, "mod")
        );
        assertEquals("Modulo by zero", exception.getMessage());
    }
    
    @Test
    void testPower() {
        assertEquals(8, calculator.calculate(2, 3, "pow"));
        assertEquals(1, calculator.calculate(5, 0, "pow"));
        assertEquals(0, calculator.calculate(0, 5, "pow"));
    }
    
    @Test
    void testPowerNegativeExponent() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class, 
            () -> calculator.calculate(2, -1, "pow")
        );
        assertEquals("Negative exponent not supported", exception.getMessage());
    }
    
    @Test
    void testUnsupportedOperation() {
        UnsupportedOperationException exception = assertThrows(
            UnsupportedOperationException.class, 
            () -> calculator.calculate(1, 2, "invalid")
        );
        assertTrue(exception.getMessage().contains("Unsupported operation"));
    }
}