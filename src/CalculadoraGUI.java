import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class CalculadoraGUI extends JFrame {
    
    // Componentes principales
    private CalculadoraLogica logica;
    private JTextArea areaResultado;
    private JTextArea areaProcedimiento;
    private JComboBox<String> comboMetodos;
    private JPanel panelParametros;
    
    public CalculadoraGUI() {
        logica = new CalculadoraLogica();
        configurarVentana();
        crearMenu();
        crearPanelPrincipal();
        setVisible(true);
    }
    
    private void configurarVentana() {
        setTitle("Calculadora de Métodos Numéricos");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 242, 245));
    }
    
    private void crearMenu() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.setAccelerator(KeyStroke.getKeyStroke("ctrl Q"));
        itemSalir.addActionListener(e -> System.exit(0));
        menuArchivo.add(itemSalir);
        
        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem itemAcerca = new JMenuItem("Acerca de");
        itemAcerca.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                "Calculadora de Métodos Numéricos\nVersión 2.0\n\n" +
                "Métodos disponibles:\n" +
                "• Interpolación (Cuadrática, Lagrange, Newton)\n" +
                "• Raíces (Bisección, Newton-Raphson, Secante)\n" +
                "• Álgebra Lineal (Inversión de Matrices)",
                "Acerca de",
                JOptionPane.INFORMATION_MESSAGE);
        });
        menuAyuda.add(itemAcerca);
        
        menuBar.add(menuArchivo);
        menuBar.add(menuAyuda);
        setJMenuBar(menuBar);
    }
    
    private void crearPanelPrincipal() {
        // Panel Superior - Selección de método
        JPanel panelSuperior = crearPanelSeleccion();
        add(panelSuperior, BorderLayout.NORTH);
        
        // Panel Central - Dividido en dos columnas
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(400);
        splitPane.setResizeWeight(0.4);
        
        // Panel Izquierdo - Parámetros
        JPanel panelIzquierdo = crearPanelParametros();
        
        // Panel Derecho - Resultados
        JPanel panelDerecho = crearPanelResultados();
        
        splitPane.setLeftComponent(panelIzquierdo);
        splitPane.setRightComponent(panelDerecho);
        
        add(splitPane, BorderLayout.CENTER);
        
        // Panel Inferior - Botones de acción
        JPanel panelInferior = crearPanelAcciones();
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    private JPanel crearPanelSeleccion() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(new Color(52, 73, 94));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel label = new JLabel("Selecciona método:");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(label);
        
        String[] metodos = {
            "Interpolación Cuadrática",
            "Interpolación Lagrange",
            "Interpolación Newton",
            "Bisección",
            "Newton-Raphson",
            "Secante",
            "Inversión de Matriz"
        };
        
        comboMetodos = new JComboBox<>(metodos);
        comboMetodos.setFont(new Font("Arial", Font.PLAIN, 13));
        comboMetodos.setPreferredSize(new Dimension(250, 30));
        comboMetodos.addActionListener(e -> actualizarParametros());
        panel.add(comboMetodos);
        
        return panel;
    }
    
    private JPanel crearPanelParametros() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titulo = new JLabel("Parámetros");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panel.add(titulo, BorderLayout.NORTH);
        
        panelParametros = new JPanel();
        panelParametros.setLayout(new BoxLayout(panelParametros, BoxLayout.Y_AXIS));
        panelParametros.setBackground(Color.WHITE);
        panelParametros.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JScrollPane scroll = new JScrollPane(panelParametros);
        scroll.setBorder(null);
        panel.add(scroll, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelResultados() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Área de resultado
        JPanel panelResultado = new JPanel(new BorderLayout());
        panelResultado.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            "Resultado",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14)
        ));
        
        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Monospaced", Font.BOLD, 24));
        areaResultado.setBackground(new Color(245, 245, 245));
        areaResultado.setForeground(new Color(46, 204, 113));
        areaResultado.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        areaResultado.setText("Esperando cálculo...");
        panelResultado.add(areaResultado, BorderLayout.CENTER);
        
        // Área de procedimiento
        JPanel panelProcedimiento = new JPanel(new BorderLayout());
        panelProcedimiento.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(155, 89, 182), 2),
            "Procedimiento",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14)
        ));
        
        areaProcedimiento = new JTextArea();
        areaProcedimiento.setEditable(false);
        areaProcedimiento.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaProcedimiento.setBackground(new Color(44, 62, 80));
        areaProcedimiento.setForeground(new Color(46, 204, 113));
        areaProcedimiento.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        areaProcedimiento.setText("Los pasos del cálculo aparecerán aquí...");
        
        JScrollPane scrollProcedimiento = new JScrollPane(areaProcedimiento);
        scrollProcedimiento.setPreferredSize(new Dimension(0, 200));
        panelProcedimiento.add(scrollProcedimiento, BorderLayout.CENTER);
        
        // Dividir panel derecho en resultado y procedimiento
        JSplitPane splitDerecho = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitDerecho.setDividerLocation(250);
        splitDerecho.setTopComponent(panelResultado);
        splitDerecho.setBottomComponent(panelProcedimiento);
        
        panel.add(splitDerecho, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelAcciones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBackground(new Color(236, 240, 241));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Botón Calcular
        JButton btnCalcular = new JButton("Calcular");
        btnCalcular.setFont(new Font("Arial", Font.BOLD, 14));
        btnCalcular.setBackground(new Color(46, 204, 113));
        btnCalcular.setForeground(Color.WHITE);
        btnCalcular.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        btnCalcular.setFocusPainted(false);
        btnCalcular.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCalcular.addActionListener(e -> calcular());
        panel.add(btnCalcular);
        
        // Botón Limpiar
        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setFont(new Font("Arial", Font.BOLD, 14));
        btnLimpiar.setBackground(new Color(231, 76, 60));
        btnLimpiar.setForeground(Color.WHITE);
        btnLimpiar.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLimpiar.addActionListener(e -> limpiarCampos());
        panel.add(btnLimpiar);
        
        // Botón Exportar
        JButton btnExportar = new JButton("Exportar");
        btnExportar.setFont(new Font("Arial", Font.BOLD, 14));
        btnExportar.setBackground(new Color(52, 152, 219));
        btnExportar.setForeground(Color.WHITE);
        btnExportar.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        btnExportar.setFocusPainted(false);
        btnExportar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExportar.addActionListener(e -> exportarResultado());
        panel.add(btnExportar);
        
        return panel;
    }
    
    private void actualizarParametros() {
        panelParametros.removeAll();
        int seleccion = comboMetodos.getSelectedIndex();
        
        switch (seleccion) {
            case 0: // Interpolación Cuadrática
                agregarCampos(new String[]{"x0", "y0", "x1", "y1", "x2", "y2", "x"});
                break;
            case 1: // Lagrange
                agregarCampoConDescripcion("n", "Número de puntos", "3");
                agregarCampos(new String[]{"x (separados por coma)", "y (separados por coma)", "x a interpolar"});
                break;
            case 2: // Newton
                agregarCampoConDescripcion("n", "Número de puntos", "3");
                agregarCampos(new String[]{"x (separados por coma)", "y (separados por coma)", "x a interpolar"});
                break;
            case 3: // Bisección
                agregarCampos(new String[]{"a", "b", "Tolerancia"});
                agregarCampoConDescripcion("funcion", "Función (ej: x*x-4)", "x*x-4");
                break;
            case 4: // Newton-Raphson
                agregarCampos(new String[]{"x0", "Tolerancia"});
                agregarCampoConDescripcion("funcion", "Función (ej: x*x-4)", "x*x-4");
                agregarCampoConDescripcion("derivada", "Derivada (ej: 2*x)", "2*x");
                break;
            case 5: // Secante
                agregarCampos(new String[]{"x0", "x1", "Tolerancia"});
                agregarCampoConDescripcion("funcion", "Función (ej: x*x-4)", "x*x-4");
                break;
            case 6: // Inversión de Matriz
                agregarCampoConDescripcion("n", "Dimensión de la matriz (n)", "3");
                agregarCampoConDescripcion("matriz", "Matriz (números separados por espacio)", "1 2 3 4 5 6 7 8 9");
                break;
        }
        
        panelParametros.revalidate();
        panelParametros.repaint();
    }
    
    private void agregarCampos(String[] labels) {
        for (String label : labels) {
            agregarCampo(label, label, "");
        }
    }
    
    private void agregarCampo(String label, String tooltip, String defaultValue) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        
        JLabel jlabel = new JLabel(label + ":");
        jlabel.setFont(new Font("Arial", Font.PLAIN, 13));
        panel.add(jlabel, BorderLayout.WEST);
        
        JTextField campo = new JTextField(defaultValue);
        campo.setName(label);
        campo.setFont(new Font("Arial", Font.PLAIN, 13));
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        campo.setToolTipText(tooltip);
        panel.add(campo, BorderLayout.CENTER);
        
        panelParametros.add(panel);
    }
    
    private void agregarCampoConDescripcion(String name, String label, String defaultValue) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        
        JLabel jlabel = new JLabel(label + ":");
        jlabel.setFont(new Font("Arial", Font.PLAIN, 13));
        panel.add(jlabel, BorderLayout.WEST);
        
        JTextField campo = new JTextField(defaultValue);
        campo.setName(name);
        campo.setFont(new Font("Arial", Font.PLAIN, 13));
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        panel.add(campo, BorderLayout.CENTER);
        
        panelParametros.add(panel);
    }
    
    private void calcular() {
        try {
            int seleccion = comboMetodos.getSelectedIndex();
            double resultado = 0;
            String procedimiento = "";
            
            // Obtener valores de los campos
            Component[] components = panelParametros.getComponents();
            Map<String, String> valores = new HashMap<>();
            
            for (Component comp : components) {
                if (comp instanceof JPanel) {
                    JPanel panel = (JPanel) comp;
                    Component[] subComponents = panel.getComponents();
                    for (Component sub : subComponents) {
                        if (sub instanceof JTextField) {
                            JTextField field = (JTextField) sub;
                            valores.put(field.getName(), field.getText().trim());
                            break;
                        }
                    }
                }
            }
            
            switch (seleccion) {
                case 0: // Interpolación Cuadrática
                    double[] d = parseDoubles(valores, new String[]{"x0", "y0", "x1", "y1", "x2", "y2", "x"});
                    resultado = logica.interpolacionCuadratica(d[0], d[1], d[2], d[3], d[4], d[5], d[6]);
                    procedimiento = logica.getUltimoProcedimiento();
                    break;
                    
                case 1: // Lagrange
                    int nL = Integer.parseInt(valores.get("n"));
                    double[] xL = parseArray(valores.get("x (separados por coma)"), nL);
                    double[] yL = parseArray(valores.get("y (separados por coma)"), nL);
                    double targetXL = Double.parseDouble(valores.get("x a interpolar"));
                    resultado = logica.lagrange(xL, yL, targetXL);
                    procedimiento = logica.getUltimoProcedimiento();
                    break;
                    
                case 2: // Newton
                    int nN = Integer.parseInt(valores.get("n"));
                    double[] xN = parseArray(valores.get("x (separados por coma)"), nN);
                    double[] yN = parseArray(valores.get("y (separados por coma)"), nN);
                    double targetXN = Double.parseDouble(valores.get("x a interpolar"));
                    resultado = logica.newton(xN, yN, targetXN);
                    procedimiento = logica.getUltimoProcedimiento();
                    break;
                    
                case 3: // Bisección
                    double a = Double.parseDouble(valores.get("a"));
                    double b = Double.parseDouble(valores.get("b"));
                    double tol = Double.parseDouble(valores.get("Tolerancia"));
                    String funcionStr = valores.get("funcion");
                    resultado = logica.biseccion(crearFuncion(funcionStr), a, b, tol);
                    procedimiento = logica.getUltimoProcedimiento();
                    break;
                    
                case 4: // Newton-Raphson
                    double x0NR = Double.parseDouble(valores.get("x0"));
                    double tolNR = Double.parseDouble(valores.get("Tolerancia"));
                    String fStr = valores.get("funcion");
                    String dfStr = valores.get("derivada");
                    resultado = logica.newtonRaphson(
                        crearFuncion(fStr), 
                        crearFuncion(dfStr), 
                        x0NR, 
                        tolNR
                    );
                    procedimiento = logica.getUltimoProcedimiento();
                    break;
                    
                case 5: // Secante
                    double x0S = Double.parseDouble(valores.get("x0"));
                    double x1S = Double.parseDouble(valores.get("x1"));
                    double tolS = Double.parseDouble(valores.get("Tolerancia"));
                    String fSStr = valores.get("funcion");
                    resultado = logica.secante(crearFuncion(fSStr), x0S, x1S, tolS);
                    procedimiento = logica.getUltimoProcedimiento();
                    break;
                    
                case 6: // Inversión de Matriz
                    int nM = Integer.parseInt(valores.get("n"));
                    double[][] mat = parseMatriz(valores.get("matriz"), nM);
                    double[][] inv = logica.invertirMatriz(mat);
                    mostrarMatrizEnDialogo(inv);
                    procedimiento = "Matriz invertida exitosamente";
                    break;
            }
            
            // Mostrar resultados
            if (seleccion != 6) {
                areaResultado.setText(String.format("%.10f", resultado));
                areaResultado.setForeground(new Color(46, 204, 113));
            }
            areaProcedimiento.setText(procedimiento);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error en el cálculo:\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            areaResultado.setText("❌ Error");
            areaResultado.setForeground(Color.RED);
        }
    }
    
    // Métodos auxiliares para parsing
    private double[] parseDoubles(Map<String, String> valores, String[] keys) {
        double[] result = new double[keys.length];
        for (int i = 0; i < keys.length; i++) {
            result[i] = Double.parseDouble(valores.get(keys[i]));
        }
        return result;
    }
    
    private double[] parseArray(String str, int n) {
        String[] parts = str.split(",");
        double[] result = new double[n];
        for (int i = 0; i < n && i < parts.length; i++) {
            result[i] = Double.parseDouble(parts[i].trim());
        }
        return result;
    }
    
    private double[][] parseMatriz(String str, int n) {
        String[] parts = str.trim().split("\\s+");
        double[][] mat = new double[n][n];
        int idx = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                mat[i][j] = Double.parseDouble(parts[idx++]);
            }
        }
        return mat;
    }
    
    private java.util.function.Function<Double, Double> crearFuncion(String expresion) {
        return (x) -> {
            try {
                return new ExpressionParser(expresion).parse(x);
            } catch (Exception e) {
                throw new RuntimeException("Error evaluando función: " + e.getMessage());
            }
        };
    }
    
    private void limpiarCampos() {
        Component[] components = panelParametros.getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                Component[] subComponents = panel.getComponents();
                for (Component sub : subComponents) {
                    if (sub instanceof JTextField) {
                        ((JTextField) sub).setText("");
                    }
                }
            }
        }
        areaResultado.setText("Esperando cálculo...");
        areaResultado.setForeground(new Color(46, 204, 113));
        areaProcedimiento.setText("Los pasos del cálculo aparecerán aquí...");
    }
    
    private void exportarResultado() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar resultado");
        int result = fileChooser.showSaveDialog(this);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                java.io.File file = fileChooser.getSelectedFile();
                java.io.FileWriter writer = new java.io.FileWriter(file);
                writer.write("=== RESULTADO ===\n");
                writer.write("Resultado: " + areaResultado.getText() + "\n\n");
                writer.write("=== PROCEDIMIENTO ===\n");
                writer.write(areaProcedimiento.getText());
                writer.close();
                JOptionPane.showMessageDialog(this,
                    "Resultado exportado exitosamente a:\n" + file.getPath(),
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error al exportar: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void mostrarMatrizEnDialogo(double[][] matriz) {
        JDialog dialog = new JDialog(this, "Matriz Inversa", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        
        JPanel panelMatriz = new JPanel(new GridLayout(matriz.length, matriz[0].length, 10, 10));
        panelMatriz.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        for (double[] row : matriz) {
            for (double val : row) {
                JTextField field = new JTextField(String.format("%.6f", val));
                field.setEditable(false);
                field.setHorizontalAlignment(JTextField.CENTER);
                field.setFont(new Font("Monospaced", Font.BOLD, 14));
                field.setBackground(Color.WHITE);
                panelMatriz.add(field);
            }
        }
        
        JScrollPane scroll = new JScrollPane(panelMatriz);
        dialog.add(scroll, BorderLayout.CENTER);
        
        JPanel panelBoton = new JPanel();
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dialog.dispose());
        panelBoton.add(btnCerrar);
        dialog.add(panelBoton, BorderLayout.SOUTH);
        
        dialog.setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculadoraGUI());
    }
}