# Codelab Hilos en Java (FP DAM)

**Público objetivo:** Alumnado de FP DAM con conocimientos básicos de Java (clases, métodos, bucles).

**Objetivo del codelab:** Aprender de forma práctica a crear hilos, detectar y corregir condiciones de carrera, usar `synchronized` y coordinar hilos con `wait()`/`notify()`. Cada ejercicio incluye un archivo base con `// TODO:` que el alumno debe completar, y una solución en la carpeta `soluciones/`.

---

## Estructura del repositorio
```
codelab-hilos-en-java-fp-dam/
├── README.md
├── .gitignore
├── run_all.sh
├── ejercicio-1-basico/
│   └── Main.java           # Starter con TODOs
├── ejercicio-2-condicion-carrera/
│   └── Main.java           # Starter con TODOs
├── ejercicio-3-synchronized/
│   └── Main.java           # Starter con TODOs
├── ejercicio-4-productor-consumidor/
│   └── Main.java           # Starter con TODOs
└── soluciones/
    ├── ejercicio-1-basico/Main.java
    ├── ejercicio-2-condicion-carrera/Main.java
    ├── ejercicio-3-synchronized/Main.java
    └── ejercicio-4-productor-consumidor/Main.java
```

---

## Instrucciones generales para el alumnado
1. Clona o descarga este repositorio.  
2. Abre el proyecto en tu IDE favorito (IntelliJ, Eclipse o VS Code).  
3. Para cada ejercicio: abre el archivo `Main.java` en la carpeta del ejercicio y completa las secciones marcadas con `// TODO:` siguiendo las indicaciones del README.  
4. Ejecuta el código y verifica que el comportamiento coincide con lo pedido.  
5. Una vez completado, comprueba tu solución con los ficheros en la carpeta `soluciones/`.

---

## Ejercicio 1 — Hilo básico: crear y ejecutar hilos
**Objetivo:** Entender cómo crear hilos con `Thread` y `Runnable`, y observar la ejecución concurrente (salidas intercaladas).

**Pasos (en el archivo base):**
1. Crear una `Runnable` que imprima del 1 al 5 con una pequeña pausa (`Thread.sleep(150)`).  
2. Crear **dos hilos** que ejecuten la misma tarea y asignarles nombres distintos.  
3. Iniciar ambos hilos y observar la salida varias veces.

**Verifica:** la salida deberá mostrar líneas intercaladas de `Hilo-A` y `Hilo-B` (no necesariamente en el mismo orden cada ejecución).

**Ayuda:** en `ejercicio-1-basico/Main.java` encontrarás `// TODO:` que indica dónde crear los hilos.

---

## Ejercicio 2 — Condición de carrera
**Objetivo:** Reproducir una condición de carrera sobre un contador compartido.

**Pasos (en el archivo base):**
1. Usa la clase `Contador` con un campo `int count = 0` y un método `incrementar()` que haga `count++`. (ya está en el base).  
2. Lanza dos hilos que llamen `incrementar()` 1000 veces cada uno.  
3. Ejecuta varias veces y anota el valor final (debería ser frecuentemente < 2000).

**Verifica:** el valor final suele ser menor que 2000; explica por qué.

**Ayuda:** el archivo base contiene el escenario; completa los TODOs si faltan pasos de invocación de hilos o `join()`.

---

## Ejercicio 3 — `synchronized`
**Objetivo:** Corregir la condición de carrera usando `synchronized` o `AtomicInteger`.

**Pasos (en el archivo base):**
1. Modifica la clase `ContadorSeguro` para que el método `incrementar()` sea `synchronized`.  
2. Ejecuta el mismo test de dos hilos con 1000 iteraciones cada uno.  
3. Verifica que el resultado sea 2000 consistentemente.

**Verifica:** el valor final debe ser siempre 2000.

---

## Ejercicio 4 — Productor-Consumidor con wait/notify
**Objetivo:** Implementar un buffer limitado y coordinar productores y consumidores con `wait()`/`notifyAll()`.

**Pasos (en el archivo base):**
1. Implementa métodos `producir(int valor)` y `consumir()` que usen `synchronized`, `wait()` y `notifyAll()`.  
2. Crea un productor y un consumidor que produzcan/consuman 10 elementos.  
3. Ejecuta y observa la secuencia de producción/consumo.

**Reto opcional:** sustituir la implementación por `ArrayBlockingQueue` (ver soluciones).

---

## Evaluación (para el profesor)
- Ejercicio 1: el alumno crea dos hilos y explica por qué las salidas se intercalan.  
- Ejercicio 2: evidencia de condición de carrera (valor final < 2000).  
- Ejercicio 3: corrección usando `synchronized` o `AtomicInteger`.  
- Ejercicio 4: buffer correcto sin pérdida ni duplicados; entiende wait/notify.

---

Si quieres, puedo además **subir este repo a un GitHub tuyo** (si me das acceso o una URL con permisos) o **generar un ZIP listo para compartir**.  
