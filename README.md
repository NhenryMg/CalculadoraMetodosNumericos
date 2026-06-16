# 📊 Calculadora de Métodos Numéricos

![Java Version](https://img.shields.io/badge/Java-21%2B-orange)
![License](https://img.shields.io/badge/License-MIT-blue)
![GUI](https://img.shields.io/badge/GUI-Swing-green)

Calculadora interactiva para métodos numéricos desarrollada en **Java**, con interfaz gráfica basada en **Swing** y versión de consola. Permite realizar cálculos de interpolación, encontrar raíces de ecuaciones y trabajar con matrices de forma sencilla e intuitiva.

---

## ✨ Características

### 📐 Interpolación

- **Interpolación Cuadrática** — A partir de 3 puntos.
- **Interpolación de Lagrange** — Para N puntos.
- **Interpolación de Newton** — Mediante diferencias divididas.

### 🔍 Raíces de Ecuaciones

- **Método de Bisección** — Búsqueda iterativa de raíces.
- **Newton-Raphson** — Método de la tangente.
- **Método de la Secante** — Aproximación sucesiva sin derivadas.

### 📊 Álgebra Lineal

- **Inversión de Matrices** — Cálculo de matrices inversas.

### 🎨 Interfaz Gráfica

- Interfaz intuitiva y amigable.
- Panel de parámetros dinámico.
- Visualización detallada del procedimiento.
- Resultados organizados y fáciles de interpretar.
- Diseño moderno y adaptable.

---

## 🚀 Instalación y Ejecución

### 1️⃣ Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/CalculadoraMetodosNumericos.git
cd CalculadoraMetodosNumericos/src
```

### 2️⃣ Compilar el proyecto

```bash
javac *.java
```

Si tienes problemas con versiones de Java:

```bash
del *.class
javac *.java
```

### 3️⃣ Ejecutar la aplicación

#### 📱 Interfaz Gráfica (Recomendada)

```bash
java CalculadoraGUI
```

#### 🖥️ Versión de Consola

```bash
java InterpolacionApp
```

### ⚡ Compilar y ejecutar rápidamente

```bash
javac *.java && java CalculadoraGUI
```

---

## 🖥️ Uso de la Aplicación

1. Selecciona el método numérico desde el menú desplegable.
2. Ingresa los parámetros solicitados.
3. Presiona el botón **Calcular**.
4. Observa el resultado generado.
5. Revisa el procedimiento detallado mostrado por la aplicación.

---

## 📌 Ejemplo de Uso

### Interpolación Cuadrática

**Puntos de entrada:**

```text
(1,2)
(2,4)
(3,6)
```

**Valor a interpolar:**

```text
x = 2.5
```

**Resultado esperado:**

```text
5.0
```

---

## 📁 Estructura del Proyecto

```text
CalculadoraMetodosNumericos/
│
├── src/
│   ├── CalculadoraLogica.java
│   ├── CalculadoraTUI.java
│   ├── InterpolacionApp.java
│   ├── CalculadoraGUI.java
│   └── ejecutar.bat
│
├── README.md
└── LICENSE
```

---

## 🛠️ Tecnologías Utilizadas

| Tecnología | Descripción |
|------------|------------|
| Java 21+ | Lenguaje principal |
| Swing | Interfaz gráfica |
| JavaScript Engine | Evaluación dinámica de funciones matemáticas |

---

## 👨‍💻 Desarrollo

### Cómo contribuir

1. Haz un Fork del proyecto.
2. Crea una nueva rama:

```bash
git checkout -b feature/AmazingFeature
```

3. Realiza tus cambios.
4. Haz Commit:

```bash
git commit -m "Add some AmazingFeature"
```

5. Sube tus cambios:

```bash
git push origin feature/AmazingFeature
```

6. Abre un Pull Request.

---

## 🐞 Reportar Problemas

Si encuentras errores o tienes sugerencias de mejora, puedes abrir un **Issue** en el repositorio correspondiente.

---

## 👥 Créditos y Agradecimientos

### Desarrollador Principal

| Desarrollador | Rol |
|--------------|-----|
| NhenryMg | Desarrollador Principal |
| MiguelAngel861 | Desarrollador Principal |

### Agradecimientos

- A la comunidad de Java por su documentación y soporte.
- A todos los usuarios y colaboradores que han ayudado a mejorar esta herramienta.
- A quienes utilizan este proyecto para aprender y fortalecer sus conocimientos en métodos numéricos.

---

## 📄 Licencia

Este proyecto se distribuye bajo la **Licencia MIT**.

Consulta el archivo:

```text
LICENSE
```

para más información.

---

## 📞 Contacto

### GitHub

**MiguelAngel861**

---

## 🌟 Mensaje Final

¡Gracias por utilizar la **Calculadora de Métodos Numéricos**!

Esperamos que esta herramienta sea de gran utilidad para tus estudios, proyectos académicos y aprendizaje de métodos numéricos.

Si te resultó útil, considera darle una ⭐ al repositorio.

**Última actualización:** Junio 2026

---

## 📝 Solución de Problemas

### Error: "major version 65 is newer than 64"

```bash
del *.class
javac *.java
```

Actualiza tu versión de Java o recompila utilizando la versión instalada.

---

### Error: "Could not find or load main class"

```bash
cd src
java CalculadoraGUI
```

Asegúrate de encontrarte dentro del directorio correcto y de ejecutar la clase sin la extensión `.class`.

---

### La GUI no se abre

```bash
javac -cp . *.java
java CalculadoraGUI
```

---

## 🧮 Funciones Matemáticas Soportadas

Para los métodos que utilizan funciones matemáticas:

### Potencias

```java
x*x - 4
```

### Seno

```java
Math.sin(x)
```

### Coseno

```java
Math.cos(x)
```

### Exponencial

```java
Math.exp(x)
```

### Logaritmo Natural

```java
Math.log(x)
```

### Raíz Cuadrada

```java
Math.sqrt(x)
```

---

## 🎉 ¡Disfruta Calculando!

Desarrollado con ❤️ en Java para facilitar el aprendizaje y aplicación de los métodos numéricos.
