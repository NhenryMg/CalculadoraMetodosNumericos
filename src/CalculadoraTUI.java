import java.util.Scanner;

public class CalculadoraTUI {
    private Scanner scanner;

    public CalculadoraTUI() {
        this.scanner = new Scanner(System.in);
    }

    public int mostrarMenu() {
        System.out.println("\n--- Calculadora Numérica ---");
        System.out.println("1. Interpolación Cuadrática");
        System.out.println("2. Interpolación Lagrange");
        System.out.println("3. Interpolación Newton");
        System.out.println("4. Bisección");
        System.out.println("5. Newton-Raphson");
        System.out.println("6. Secante");
        System.out.println("7. Inversión de Matriz");
        System.out.print("Selecciona una opción: ");
        return scanner.nextInt();
    }

    public int getInt(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextInt();
    }

    public double getDouble(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextDouble();
    }

    public double[] getArray(String prompt, int n) {
        double[] arr = new double[n];
        System.out.print(prompt + " (" + n + " valores): ");
        for (int i = 0; i < n; i++) arr[i] = scanner.nextDouble();
        return arr;
    }

    public double[][] getMatriz(int n) {
        double[][] mat = new double[n][n];
        System.out.println("Ingresa la matriz " + n + "x" + n + ":");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                mat[i][j] = scanner.nextDouble();
            }
        }
        return mat;
    }

    public void mostrarResultado(double res) { System.out.println("Resultado: " + res); }
    public void mostrarProcedimiento(String proc) { System.out.println(proc); }
    public void mostrarMatriz(double[][] mat) {
        System.out.println("Resultado (Matriz Inversa):");
        for (double[] row : mat) {
            for (double val : row) System.out.printf("%8.4f ", val);
            System.out.println();
        }
    }
}
