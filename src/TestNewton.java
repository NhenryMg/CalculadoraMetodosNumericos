public class TestNewton {
    public static void main(String[] args) {
        CalculadoraLogica logica = new CalculadoraLogica();
        
        // Test Bisección
        System.out.println("Testing Bisección:");
        java.util.function.Function<Double, Double> fB = x -> {
            try {
                return new ExpressionParser("x*x - 4").parse(x);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        double resB = logica.biseccion(fB, 0.0, 3.0, 0.0001);
        System.out.println("Bisección x*x - 4 in [0, 3] with tol 0.0001: " + resB);
        System.out.println(logica.getUltimoProcedimiento());
        System.out.println();

        // Test Newton-Raphson
        System.out.println("Testing Newton-Raphson:");
        java.util.function.Function<Double, Double> fN = x -> {
            try {
                return new ExpressionParser("x^2 - 4").parse(x);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        java.util.function.Function<Double, Double> dfN = x -> {
            try {
                return new ExpressionParser("2*x").parse(x);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        double resN = logica.newtonRaphson(fN, dfN, 3.0, 0.0001);
        System.out.println("Newton-Raphson x^2 - 4 with x0=3, tol 0.0001: " + resN);
        System.out.println(logica.getUltimoProcedimiento());
        System.out.println();
        
        // Test Newton-Raphson with exp
        System.out.println("Testing Newton-Raphson with exp(x) - 3*x:");
        java.util.function.Function<Double, Double> fExp = x -> {
            try {
                return new ExpressionParser("exp(x) - 3*x").parse(x);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        java.util.function.Function<Double, Double> dfExp = x -> {
            try {
                return new ExpressionParser("exp(x) - 3").parse(x);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        double resExp = logica.newtonRaphson(fExp, dfExp, 1.0, 0.0001);
        System.out.println("Newton-Raphson exp(x) - 3*x with x0=1, tol 0.0001: " + resExp);
        System.out.println(logica.getUltimoProcedimiento());
    }
}
