public class InterpolacionApp {
    public static void main(String[] args) {
        CalculadoraLogica logica = new CalculadoraLogica();
        CalculadoraTUI tui = new CalculadoraTUI();
        int opcion = tui.mostrarMenu();

        switch (opcion) {
            case 1:
                double[] dInt = tui.getArray("Ingresa x0, y0, x1, y1, x2, y2 y x", 7);
                double res1 = logica.interpolacionCuadratica(dInt[0], dInt[1], dInt[2], dInt[3], dInt[4], dInt[5], dInt[6]);
                tui.mostrarProcedimiento(logica.getUltimoProcedimiento());
                tui.mostrarResultado(res1);
                break;
            case 2:
                int nL = tui.getInt("Número de puntos");
                double[] xL = tui.getArray("x", nL);
                double[] yL = tui.getArray("y", nL);
                double targetXL = tui.getDouble("x a interpolar");
                double res2 = logica.lagrange(xL, yL, targetXL);
                tui.mostrarProcedimiento(logica.getUltimoProcedimiento());
                tui.mostrarResultado(res2);
                break;
            case 3:
                int nN = tui.getInt("Número de puntos");
                double[] xN = tui.getArray("x", nN);
                double[] yN = tui.getArray("y", nN);
                double targetXN = tui.getDouble("x a interpolar");
                double res3 = logica.newton(xN, yN, targetXN);
                tui.mostrarProcedimiento(logica.getUltimoProcedimiento());
                tui.mostrarResultado(res3);
                break;
            case 4:
                double a = tui.getDouble("a");
                double b = tui.getDouble("b");
                tui.mostrarResultado(logica.biseccion(x -> x * x - 4, a, b, 0.0001));
                break;
            case 5:
                double x0 = tui.getDouble("x0");
                tui.mostrarResultado(logica.newtonRaphson(x -> x * x - 4, x -> 2 * x, x0, 0.0001));
                break;
            case 6:
                double s0 = tui.getDouble("x0");
                double s1 = tui.getDouble("x1");
                tui.mostrarResultado(logica.secante(x -> x * x - 4, s0, s1, 0.0001));
                break;
            case 7:
                int nM = tui.getInt("Dimensión de la matriz (nxn)");
                double[][] mat = tui.getMatriz(nM);
                tui.mostrarMatriz(logica.invertirMatriz(mat));
                break;
            default:
                System.out.println("Opción no válida");
        }
    }
}
