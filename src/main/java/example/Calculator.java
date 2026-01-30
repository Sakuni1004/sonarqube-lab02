package example;

public class Calculator {

    private static final String ADD = "add";
    private static final String SUB = "sub";
    private static final String MUL = "mul";
    private static final String DIV = "div";
    private static final String MOD = "mod";
    private static final String POW = "pow";

    public int calculate(int a, int b, String op) {
        switch (op) {
            case ADD:
                return add(a, b);

            case SUB:
                return sub(a, b);

            case MUL:
                return mul(a, b);

            case DIV:
                return div(a, b);

            case MOD:
                return mod(a, b);

            case POW:
                return power(a, b);

            default:
                throw new UnsupportedOperationException("Unsupported operation: " + op);
        }
    }

    private int add(int a, int b) {
        return a + b;
    }

    private int sub(int a, int b) {
        return a - b;
    }

    private int mul(int a, int b) {
        return a * b;
    }

    private int div(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Division by zero");
        }
        return a / b;
    }

    private int mod(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Modulo by zero");
        }
        return a % b;
    }

    private int power(int a, int b) {
        if (b < 0) {
            throw new IllegalArgumentException("Negative exponent not supported");
        }
        int result = 1;
        for (int i = 0; i < b; i++) {
            result *= a;
        }
        return result;
    }
}
