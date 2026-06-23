/**
 * Parser de expresiones matemáticas con descenso recursivo.
 *
 * Soporta:
 *   - Operadores: +  -  *  /  ^ (potencia)
 *   - Multiplicación implícita: 4x, 2x^2, 3(x+1), (x+1)(x-1)
 *   - Variable: x
 *   - Constantes: pi, e
 *   - Funciones: sin, cos, tan, asin, acos, atan, log/ln, log10, exp, sqrt, abs
 *   - Prefijo "Math.": Math.sin, Math.sqrt, etc.
 *
 * Ejemplos válidos:
 *   x^3 - 4x^2 - 10
 *   2*x + sin(x)
 *   x*x - 4
 *   exp(x) - 3*x
 */
public class ExpressionParser {
    private final String str;
    private int pos = -1, ch;

    public ExpressionParser(String str) {
        // Normalizar: eliminar espacios extra
        this.str = str.trim();
    }

    private void nextChar() {
        ch = (++pos < str.length()) ? str.charAt(pos) : -1;
    }

    /** Consume el carácter indicado (saltando espacios) y avanza. */
    private boolean eat(int charToEat) {
        while (ch == ' ') nextChar();
        if (ch == charToEat) {
            nextChar();
            return true;
        }
        return false;
    }

    /** Retorna el carácter actual sin consumirlo (saltando espacios). */
    private int peek() {
        int i = pos;
        while (i < str.length() && str.charAt(i) == ' ') i++;
        return (i < str.length()) ? str.charAt(i) : -1;
    }

    /** Punto de entrada. Reinicia y parsea la expresión completa. */
    public double parse(double xVal) {
        pos = -1;
        nextChar();
        double v = parseExpression(xVal);
        // Saltar espacios finales
        while (ch == ' ') nextChar();
        if (pos < str.length()) {
            throw new RuntimeException("Carácter inesperado '" + (char)ch + "' en posición " + pos);
        }
        return v;
    }

    // Nivel 1: suma / resta
    private double parseExpression(double xVal) {
        double x = parseTerm(xVal);
        for (;;) {
            while (ch == ' ') nextChar();
            if      (eat('+')) x += parseTerm(xVal);
            else if (eat('-')) x -= parseTerm(xVal);
            else return x;
        }
    }

    // Nivel 2: multiplicación / división (explícita e implícita)
    private double parseTerm(double xVal) {
        double x = parsePower(xVal);
        for (;;) {
            while (ch == ' ') nextChar();
            if (eat('*')) {
                x *= parsePower(xVal);
            } else if (eat('/')) {
                double divisor = parsePower(xVal);
                if (divisor == 0.0) throw new ArithmeticException("División por cero");
                x /= divisor;
            } else if (isImplicitMultiplyStart()) {
                // Multiplicación implícita: 4x, 2(x+1), 3sin(x), etc.
                x *= parsePower(xVal);
            } else {
                return x;
            }
        }
    }

    /**
     * Determina si el siguiente token puede comenzar una multiplicación implícita.
     * Válido si el siguiente carácter (sin espacios) es:
     *   - una letra (variable o función): e.g. "4x", "3sin(x)"
     *   - un paréntesis abierto: e.g. "3(x+1)"
     */
    private boolean isImplicitMultiplyStart() {
        while (ch == ' ') nextChar();
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == '(' ;
    }

    // Nivel 3: potencia (^), asociativa por la derecha
    private double parsePower(double xVal) {
        double x = parseUnary(xVal);
        while (ch == ' ') nextChar();
        if (eat('^')) {
            // Asociatividad derecha: a^b^c = a^(b^c)
            x = Math.pow(x, parsePower(xVal));
        }
        return x;
    }

    // Nivel 4: signo unario (+/-)
    private double parseUnary(double xVal) {
        while (ch == ' ') nextChar();
        if (eat('+')) return parseUnary(xVal);
        if (eat('-')) return -parseUnary(xVal);
        return parseAtom(xVal);
    }

    // Nivel 5: átomos — número, variable, función, subexpresión entre paréntesis
    private double parseAtom(double xVal) {
        while (ch == ' ') nextChar();
        double x;
        int startPos = this.pos;

        if (eat('(')) {
            // Subexpresión entre paréntesis
            x = parseExpression(xVal);
            while (ch == ' ') nextChar();
            if (!eat(')')) throw new RuntimeException("Falta ')' para cerrar paréntesis");
        } else if ((ch >= '0' && ch <= '9') || ch == '.') {
            // Número literal (entero o decimal)
            while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
            x = Double.parseDouble(str.substring(startPos, this.pos));
        } else if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == '_') {
            // Identificador: variable, constante o nombre de función
            // Permitir puntos para reconocer "Math.sin" como un token
            while ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')
                    || (ch >= '0' && ch <= '9') || ch == '_' || ch == '.') nextChar();

            String name = str.substring(startPos, this.pos);

            // Quitar prefijo "Math."
            if (name.startsWith("Math.") || name.startsWith("math.")) {
                name = name.substring(5);
            }

            if (name.equalsIgnoreCase("x")) {
                x = xVal;
            } else if (name.equalsIgnoreCase("pi")) {
                x = Math.PI;
            } else if (name.equalsIgnoreCase("e")) {
                x = Math.E;
            } else {
                // Es una función — leer su argumento
                double arg;
                while (ch == ' ') nextChar();
                if (eat('(')) {
                    arg = parseExpression(xVal);
                    while (ch == ' ') nextChar();
                    if (!eat(')')) throw new RuntimeException("Falta ')' después de " + name + "(");
                } else {
                    // Sin paréntesis: e.g. sin x (solo para casos simples)
                    arg = parsePower(xVal);
                }

                switch (name.toLowerCase()) {
                    case "sin":   x = Math.sin(arg);   break;
                    case "cos":   x = Math.cos(arg);   break;
                    case "tan":   x = Math.tan(arg);   break;
                    case "asin":  x = Math.asin(arg);  break;
                    case "acos":  x = Math.acos(arg);  break;
                    case "atan":  x = Math.atan(arg);  break;
                    case "log":   x = Math.log(arg);   break;
                    case "ln":    x = Math.log(arg);   break;
                    case "log10": x = Math.log10(arg); break;
                    case "exp":   x = Math.exp(arg);   break;
                    case "sqrt":  x = Math.sqrt(arg);  break;
                    case "abs":   x = Math.abs(arg);   break;
                    default: throw new RuntimeException(
                        "Función o variable desconocida: '" + name + "'");
                }
            }
        } else {
            throw new RuntimeException(
                "Se esperaba un número, variable o '(' pero se encontró '"
                + (char)ch + "' en posición " + pos);
        }

        return x;
    }
}
