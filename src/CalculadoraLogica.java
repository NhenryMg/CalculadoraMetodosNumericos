import java.util.function.Function;

public class CalculadoraLogica {

    private String ultimoProcedimiento = "";

    public String getUltimoProcedimiento() {
        return ultimoProcedimiento;
    }

    // --- Interpolación ---

    public double interpolacionCuadratica(double x0, double y0, double x1, double y1, double x2, double y2, double x) {
        double term0 = y0 * ((x - x1) * (x - x2)) / ((x0 - x1) * (x0 - x2));
        double term1 = y1 * ((x - x0) * (x - x2)) / ((x1 - x0) * (x1 - x2));
        double term2 = y2 * ((x - x0) * (x - x1)) / ((x2 - x0) * (x2 - x1));
        ultimoProcedimiento = "Procedimiento Interpolación Cuadrática:\n" +
                "L0(x) = " + y0 + " * ((" + x + "-" + x1 + ")*(" + x + "-" + x2 + ")) / ((" + x0 + "-" + x1 + ")*(" + x0 + "-" + x2 + ")) = " + term0 + "\n" +
                "L1(x) = " + y1 + " * ((" + x + "-" + x0 + ")*(" + x + "-" + x2 + ")) / ((" + x1 + "-" + x0 + ")*(" + x1 + "-" + x2 + ")) = " + term1 + "\n" +
                "L2(x) = " + y2 + " * ((" + x + "-" + x0 + ")*(" + x + "-" + x1 + ")) / ((" + x2 + "-" + x0 + ")*(" + x2 + "-" + x1 + ")) = " + term2 + "\n" +
                "Resultado = " + term0 + " + " + term1 + " + " + term2 + " = " + (term0 + term1 + term2);
        return term0 + term1 + term2;
    }

    public double lagrange(double[] x, double[] y, double targetX) {
        double result = 0.0;
        int n = x.length;
        StringBuilder sb = new StringBuilder("Procedimiento Interpolación Lagrange:\n");
        for (int i = 0; i < n; i++) {
            double term = y[i];
            sb.append("L").append(i).append("(x) = ").append(y[i]);
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    term *= (targetX - x[j]) / (x[i] - x[j]);
                    sb.append(" * ((").append(targetX).append("-").append(x[j]).append(")/(").append(x[i]).append("-").append(x[j]).append(")");
                }
            }
            sb.append(" = ").append(term).append("\n");
            result += term;
        }
        sb.append("Resultado = ").append(result);
        ultimoProcedimiento = sb.toString();
        return result;
    }

    public double newton(double[] x, double[] y, double targetX) {
        int n = x.length;
        double[][] table = new double[n][n];
        for (int i = 0; i < n; i++) table[i][0] = y[i];
        StringBuilder sb = new StringBuilder("Procedimiento Interpolación Newton:\nTabla de diferencias divididas:\n");
        for (int j = 1; j < n; j++) {
            for (int i = 0; i < n - j; i++) {
                table[i][j] = (table[i + 1][j - 1] - table[i][j - 1]) / (x[i + j] - x[i]);
            }
        }
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n - i; j++) sb.append(String.format("%8.4f ", table[i][j]));
            sb.append("\n");
        }
        double result = table[0][0];
        double term = 1.0;
        sb.append("Resultado = ").append(table[0][0]);
        for (int i = 1; i < n; i++) {
            term *= (targetX - x[i - 1]);
            result += table[0][i] * term;
            sb.append(" + (").append(table[0][i]).append(" * ").append(term).append(")");
        }
        sb.append(" = ").append(result);
        ultimoProcedimiento = sb.toString();
        return result;
    }

    // --- Raíces ---

    public double biseccion(Function<Double, Double> f, double a, double b, double tol) {
        if (f.apply(a) * f.apply(b) > 0) {
            throw new IllegalArgumentException("La función debe tener signos opuestos en los extremos del intervalo [a, b] (f(a) * f(b) <= 0)");
        }
        
        StringBuilder sb = new StringBuilder("Procedimiento Bisección:\n");
        sb.append(String.format("%-4s | %-12s | %-12s | %-12s | %-12s | %-12s\n", "Iter", "a", "b", "c", "f(c)", "Error"));
        sb.append("-------------------------------------------------------------------------------------\n");
        
        double c = a;
        int iter = 0;
        int maxIter = 1000;
        
        while ((b - a) / 2.0 > tol && iter < maxIter) {
            c = (a + b) / 2.0;
            double fa = f.apply(a);
            double fc = f.apply(c);
            double error = (b - a) / 2.0;
            
            sb.append(String.format("%-4d | %-12.8f | %-12.8f | %-12.8f | %-12.8f | %-12.8f\n", iter, a, b, c, fc, error));
            
            if (fc == 0.0) {
                break;
            }
            if (fa * fc < 0) {
                b = c;
            } else {
                a = c;
            }
            iter++;
        }
        
        c = (a + b) / 2.0;
        sb.append("\nResultado final: x = ").append(c);
        ultimoProcedimiento = sb.toString();
        return c;
    }

    public double newtonRaphson(Function<Double, Double> f, Function<Double, Double> df, double x0, double tol) {
        double x = x0;
        int maxIter = 1000;
        int iter = 0;
        
        StringBuilder sb = new StringBuilder("Procedimiento Newton-Raphson:\n");
        sb.append(String.format("%-4s | %-12s | %-12s | %-12s | %-12s | %-12s\n", "Iter", "x_i", "f(x_i)", "f'(x_i)", "x_{i+1}", "Error"));
        sb.append("-------------------------------------------------------------------------------------\n");
        
        while (iter < maxIter) {
            double fx = f.apply(x);
            double dfx = df.apply(x);
            
            if (Math.abs(dfx) < 1e-12) {
                throw new ArithmeticException("Derivada cero en x = " + x);
            }
            
            double xNext = x - fx / dfx;
            double error = Math.abs(xNext - x);
            
            sb.append(String.format("%-4d | %-12.8f | %-12.8f | %-12.8f | %-12.8f | %-12.8f\n", iter, x, fx, dfx, xNext, error));
            
            if (error < tol) {
                sb.append("\nResultado final: x = ").append(xNext);
                ultimoProcedimiento = sb.toString();
                return xNext;
            }
            
            x = xNext;
            iter++;
        }
        
        throw new RuntimeException("No convergió después de " + maxIter + " iteraciones");
    }

    public double secante(Function<Double, Double> f, double x0, double x1, double tol) {
        StringBuilder sb = new StringBuilder("Procedimiento Secante:\n");
        sb.append(String.format("%-4s | %-12s | %-12s | %-12s | %-12s\n", "Iter", "x0", "x1", "x2", "Error"));
        sb.append("-------------------------------------------------------------------------\n");
        
        double x2 = x1;
        int iter = 0;
        int maxIter = 1000;
        
        while (Math.abs(x1 - x0) > tol && iter < maxIter) {
            double fx0 = f.apply(x0);
            double fx1 = f.apply(x1);
            
            if (Math.abs(fx1 - fx0) < 1e-12) {
                throw new ArithmeticException("División por cero en el método de la Secante (f(x1) - f(x0) aprox. 0)");
            }
            
            x2 = x1 - fx1 * (x1 - x0) / (fx1 - fx0);
            double error = Math.abs(x1 - x0);
            sb.append(String.format("%-4d | %-12.8f | %-12.8f | %-12.8f | %-12.8f\n", iter, x0, x1, x2, error));
            
            x0 = x1;
            x1 = x2;
            iter++;
        }
        
        sb.append("\nResultado final: x = ").append(x1);
        ultimoProcedimiento = sb.toString();
        return x1;
    }

    // --- Matrices ---

    public double[][] invertirMatriz(double[][] matrix) {
        int n = matrix.length;
        double[][] augmented = new double[n][2 * n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(matrix[i], 0, augmented[i], 0, n);
            augmented[i][i + n] = 1.0;
        }
        for (int i = 0; i < n; i++) {
            double pivot = augmented[i][i];
            for (int j = 0; j < 2 * n; j++) augmented[i][j] /= pivot;
            for (int k = 0; k < n; k++) {
                if (k != i) {
                    double factor = augmented[k][i];
                    for (int j = 0; j < 2 * n; j++) augmented[k][j] -= factor * augmented[i][j];
                }
            }
        }
        double[][] inverse = new double[n][n];
        for (int i = 0; i < n; i++) System.arraycopy(augmented[i], n, inverse[i], 0, n);
        return inverse;
    }
}
