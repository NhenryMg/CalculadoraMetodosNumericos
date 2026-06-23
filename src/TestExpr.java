public class TestExpr {
    public static void main(String[] args) {
        String[][] tests = {
            // {expresion, xval, resultado_esperado}
            {"x^3-4x^2-10",   "4", "4^3 - 4*4^2 - 10 = 64 - 64 - 10 = -10"},
            {"x^3 - 4*x^2 - 10", "4", "misma que arriba = -10"},
            {"x^3-4x^2-10",   "2", "8 - 16 - 10 = -18"},
            {"2x^2+3x-5",     "1", "2+3-5 = 0"},
            {"sin(x)",         "0", "sin(0) = 0"},
            {"x*x - 4",        "2", "0"},
        };

        for (String[] t : tests) {
            double x = Double.parseDouble(t[1]);
            try {
                double result = new ExpressionParser(t[0]).parse(x);
                System.out.printf("f(x)=%-25s  x=%-4s  => %12.6f    [%s]%n",
                    t[0], t[1], result, t[2]);
            } catch (Exception e) {
                System.out.printf("ERROR f(x)=%s  x=%s : %s%n", t[0], t[1], e.getMessage());
            }
        }

        // Prueba de biseccion con x^3-4x^2-10
        System.out.println("\n--- Biseccion x^3-4x^2-10 en [4, 5], tol=1e-6 ---");
        CalculadoraLogica logica = new CalculadoraLogica();
        double raiz = logica.biseccion(
            x -> new ExpressionParser("x^3-4x^2-10").parse(x),
            4.0, 5.0, 1e-6
        );
        System.out.printf("Raiz encontrada: %.10f%n", raiz);
        // Verificar: f(raiz) ~ 0
        double fRaiz = new ExpressionParser("x^3-4x^2-10").parse(raiz);
        System.out.printf("f(raiz) = %.2e  (debe ser ~0)%n", fRaiz);
    }
}
