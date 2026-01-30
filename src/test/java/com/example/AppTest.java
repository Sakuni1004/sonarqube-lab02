package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AppTest {

    static class DummyUserService extends UserService {
        boolean findCalled;
        boolean deleteCalled;

        @Override
        public void findUser(String username) {
            findCalled = true;
        }

        @Override
        public void deleteUser(String username) {
            deleteCalled = true;
        }
    }

    static class TrackingCalculator extends Calculator {
        boolean called;
        String operation;
        int lastResult;

        @Override
        public int calculate(int a, int b, String operation) {
            called = true;
            this.operation = operation;
            lastResult = super.calculate(a, b, operation);
            return lastResult;
        }
    }

    @Test
    @DisplayName("App run completes with injected services")
    void appRun() throws Exception {
        DummyUserService service = new DummyUserService();
        TrackingCalculator calc = new TrackingCalculator();

        App.run(service, calc);

        assertTrue(service.findCalled, "findUser should be invoked");
        assertTrue(service.deleteCalled, "deleteUser should be invoked");
        assertTrue(calc.called, "calculate should be invoked");
        assertEquals("add-again", calc.operation);
        assertEquals(15, calc.lastResult);
    }
}