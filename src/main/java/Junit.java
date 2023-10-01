import java.util.stream.DoubleStream;

public class Junit {
    static double add(double... operands) {
        return DoubleStream.of(operands)
                .sum();
    }

    /**
     *
     * @param operands Values of the operands
     * @return multiply
     */
    static double multiply(double... operands) {
        return DoubleStream.of(operands)
                .reduce(1, (a, b) -> a * b);
    }
}
