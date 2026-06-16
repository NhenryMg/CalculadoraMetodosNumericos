# 📊 Calculadora de Métodos Numéricos

![Java Version](https://img.shields.io/badge/Java-21%2B-orange)
![License](https://img.shields.io/badge/License-MIT-blue)
![GUI](https://img.shields.io/badge/GUI-Swing-green)

Calculadora interactiva para métodos numéricos con interfaz gráfica (Swing) y versión de consola. Permite realizar cálculos de interpolación, encontrar raíces de ecuaciones y operaciones con matrices.

---

## ✨ Características

### 📐 Interpolación
- **Interpolación Cuadrática** - A partir de 3 puntos
- **Interpolación de Lagrange** - Para N puntos
- **Interpolación de Newton** - Con diferencias divididas

### 🔍 Raíces de Ecuaciones
- **Bisección** - Método de bisección para encontrar raíces
- **Newton-Raphson** - Método de la tangente
- **Secante** - Método de la secante

### 📊 Álgebra Lineal
- **Inversión de Matrices** - Cálculo de matriz inversa

### 🎨 Características de la GUI
- Interfaz intuitiva y amigable
- Panel de parámetros dinámico
- Visualización detallada del procedimiento
- Exportación de resultados
- Diseño moderno y responsive

---

## 🚀 Requisitos

- **Java JDK 21** o superior
- **Windows / Linux / macOS**

### Verificar versión de Java
```bash
java -version
javac -version


## 🚀 Instalación y Ejecución

### 1. Clonar o descargar el proyecto

```bash
git clone https://github.com/tu-usuario/CalculadoraMetodosNumericos.git
cd CalculadoraMetodosNumericos/src
2. Compilar el proyecto
bash
# Compilar todos los archivos
javac *.java
Si tienes problemas con versiones de Java, usa:

bash
# Limpiar compilados anteriores y recompilar
del *.class
javac *.java
3. Ejecutar la aplicación
📱 Interfaz Gráfica (Recomendada)
bash
java CalculadoraGUI
🖥️ Versión de Consola
bash
java InterpolacionApp
4. Atajo para compilar y ejecutar
bash
javac *.java && java CalculadoraGUI
🖥️ Uso de la Interfaz Gráfica
Seleccionar método - Elige el método numérico del menú desplegable

Ingresar parámetros - Completa los campos según el método seleccionado

Calcular - Haz clic en el botón "Calcular"

Ver resultados - El resultado se muestra en el panel derecho

Revisar procedimiento - Los pasos del cálculo se muestran detalladamente

Ejemplo de uso
Para Interpolación Cuadrática:

Ingresa los puntos: (1,2), (2,4), (3,6)

Ingresa el valor a interpolar: x = 2.5

Resultado esperado: 5.0

🔧 Solución de problemas comunes
Error: "major version 65 is newer than 64"
bash
# Actualizar Java o recompilar con tu versión actual
del *.class
javac *.java
Error: "Could not find or load main class"
bash
# Asegúrate de estar en el directorio correcto
cd src
# Ejecuta sin la extensión .class
java CalculadoraGUI
La GUI no se abre
bash
# Compilar con todas las dependencias
javac -cp . *.java
java CalculadoraGUI
Funciones matemáticas soportadas
Para métodos que requieren funciones (Bisección, Newton-Raphson, Secante):

✅ x*x - 4 - Potencia

✅ Math.sin(x) - Seno

✅ Math.cos(x) - Coseno

✅ Math.exp(x) - Exponencial

✅ Math.log(x) - Logaritmo natural

✅ Math.sqrt(x) - Raíz cuadrada
