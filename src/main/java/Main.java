public class Main {
    public static void main(String[] args) {
        double product = multiply(5.0, 3.0);
        System.out.println("Product: " + product); // Output: Product: 24.0
    }

    /**
     *
     * @param operands the values
     * @author SS
     * @return result, product of three numbers.
     */
    public static double multiply(double... operands) {
        if (operands.length == 0) {
            throw new IllegalArgumentException("At least one operand is required.");
        }

        double result = 1.0; // Initialize the result to 1

        for (double operand : operands) {
            result *= operand; // Multiply each operand with the current result
        }

        return result;
    }
}