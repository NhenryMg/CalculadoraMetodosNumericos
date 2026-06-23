public class TestSecante {
    public static void main(String[] args) {
        // Ecuacion: x^2 - 6 = 2  =>  f(x) = x^2 - 8 = 0
        // Raices reales: x = +sqrt(8) ≈ 2.8284  y  x = -sqrt(8) ≈ -2.8284

        System.out.println("Ecuacion: x^2 - 6 = 2  =>  f(x) = x^2 - 8");
        System.out.printf("Raiz exacta: sqrt(8) = %.10f%n%n", Math.sqrt(8));

        // Verificar que el parser evalua correctamente
        double f2 = new ExpressionParser("x^2-8").parse(2.0);
        double f3 = new ExpressionParser("x^2-8").parse(3.0);
        System.out.printf("f(2) = x^2-8 con x=2 => %.4f  (esperado: -4)%n", f2);
        System.out.printf("f(3) = x^2-8 con x=3 => %.4f  (esperado: 1)%n%n", f3);

        // Aplicar metodo de la secante
        CalculadoraLogica logica = new CalculadoraLogica();

        System.out.println("=== Raiz positiva (x0=2, x1=3) ===");
        double raizPos = logica.secante(
            x -> new ExpressionParser("x^2-8").parse(x),
            2.0, 3.0, 1e-6
        );
        System.out.printf("Raiz positiva: %.10f%n", raizPos);
        System.out.printf("Verificacion f(raiz) = %.2e (debe ser ~0)%n%n",
            new ExpressionParser("x^2-8").parse(raizPos));
        System.out.println(logica.getUltimoProcedimiento());

        System.out.println("\n=== Raiz negativa (x0=-2, x1=-3) ===");
        double raizNeg = logica.secante(
            x -> new ExpressionParser("x^2-8").parse(x),
            -2.0, -3.0, 1e-6
        );
        System.out.printf("Raiz negativa: %.10f%n", raizNeg);
        System.out.printf("Verificacion f(raiz) = %.2e (debe ser ~0)%n%n",
            new ExpressionParser("x^2-8").parse(raizNeg));
        System.out.println(logica.getUltimoProcedimiento());
    }
}
