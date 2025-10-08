# Codelab Hilos en Java (FP DAM)

**Público objetivo:** Alumnado de FP DAM con conocimientos básicos de Java (clases, métodos, bucles).

**Objetivo del codelab:** Aprender, paso a paso, los conceptos prácticos de concurrencia en Java: creación de hilos, condiciones de carrera, sincronización con `synchronized`, comunicación entre hilos con `wait()`/`notifyAll()`, y alternativas de la API `java.util.concurrent` (por ejemplo `BlockingQueue` y `AtomicInteger`).

---

## Antes de empezar (requisitos)
- Java JDK 11 o superior instalado. (`java -version`, `javac -version`)
- IDE recomendado: IntelliJ IDEA (Community) o VS Code con extensión Java.
- Conocimientos previos: clases, métodos, bucles (`for`, `while`), excepciones básicas.

---

## Cómo usar el repositorio
1. Clona o descarga el ZIP y abre la carpeta `codelab-hilos-en-java-fp-dam` en tu IDE.
2. Cada ejercicio está en su propia carpeta (`ejercicio-1-basico`, ..., `ejercicio-4-productor-consumidor`).
3. En cada carpeta hay un `Main.java` **starter** con comentarios `// TODO:` que debes completar siguiendo las instrucciones de este README.
4. Después de completar un ejercicio puedes comparar tu solución con la versión en `soluciones/` (solo para verificar y entender las respuestas).
5. Para ejecutar todos los ejercicios desde la terminal (opcional): `chmod +x run_all.sh && ./run_all.sh`. Ten en cuenta que los starters contienen TODOs y pueden no compilar hasta completarlos.

---

# EJERCICIOS (concepto + pasos detallados para el alumno)

---

## Ejercicio 1 — Hilo básico: crear y ejecutar hilos

**Conceptos clave (breve):**
- Un *hilo* (`Thread`) es una unidad de ejecución.  
- `Runnable` es una interfaz funcional que define la tarea a ejecutar.  
- `start()` inicia un hilo y llama internamente a `run()`. No llamar a `run()` directamente si queremos ejecución concurrente.  
- `Thread.sleep(ms)` pausa el hilo actual sin bloquear otros hilos.  
- `Thread.currentThread().getName()` devuelve el nombre del hilo.

**Objetivo del ejercicio:**
Crear dos hilos que ejecuten la misma tarea y observar cómo sus salidas se intercalan (concurrency).

**Archivo base:** `ejercicio-1-basico/Main.java` (contiene `// TODO:`)

**Pasos para el alumno (hacer en el archivo base):**
1. Localiza el bloque `Runnable tarea = () -> { ... };` que ya imprime números del 1 al 5.  
2. **TODO 1:** Crea dos objetos `Thread` llamados `t1` y `t2` usando esa `tarea`. Asigna nombres distintos con `new Thread(tarea, "Hilo-A")` y `new Thread(tarea, "Hilo-B")`.  
3. **TODO 2:** Arranca ambos hilos con `t1.start(); t2.start();`.  
4. Ejecuta la clase `Main`. Ejecuta varias veces (3–5) y observa la salida: debería mostrar líneas de ambos hilos mezcladas, por ejemplo:
   ```
   Hilo-A: 1
   Hilo-B: 1
   Hilo-A: 2
   Hilo-B: 2
   ...
   ```
   El orden no es determinista; esa es la idea.
5. (Opcional) Añade `t1.join(); t2.join();` en `main` para esperar a que ambos terminen antes de que el programa finalice. Observa qué cambia (no cambia el intercalado, pero garantizas que el programa espere).

**Pistas / errores comunes:**
- Si llamas a `tarea.run()` en vez de `t1.start()`, la ejecución será secuencial (en el hilo principal).  
- `Thread.sleep` lanza `InterruptedException`: envuélvelo en `try/catch` dentro del `Runnable`.

**Preguntas para reflexionar (checkpoint):**
- ¿Por qué las salidas se intercalan?  
- ¿Cómo cambiaría el comportamiento si `sleep` fuera más largo en uno de los hilos?

---

## Ejercicio 2 — Condición de carrera (race condition)

**Conceptos clave (breve):**
- Una *condición de carrera* ocurre cuando varios hilos acceden y modifican un recurso compartido sin sincronización.  
- `count++` NO es atómico: se compone de leer, incrementar y escribir. Dos hilos pueden leer el mismo valor y escribir el resultado, perdiendo un incremento.

**Objetivo del ejercicio:**
Reproducir la condición de carrera usando un contador compartido incrementado por dos hilos.

**Archivo base:** `ejercicio-2-condicion-carrera/Main.java`

**Pasos para el alumno (hacer en el archivo base):**
1. Observa la clase `Contador` con `int count = 0` y `void incrementar() { count++; }`. Esta implementación está intencionadamente sin proteger.  
2. **TODO 1:** Crea una `Runnable` que llame `c.incrementar()` 1000 veces (por ejemplo con un bucle `for (int i = 0; i < 1000; i++) c.incrementar();`).  
3. **TODO 2:** Crea y arranca dos hilos `t1` y `t2` que ejecuten esa `Runnable`. Usa `t1.join()` y `t2.join()` para esperar su fin.  
4. Ejecuta el programa **variadas veces** (10 ejecuciones seguidas) y apunta los valores finales mostrados. Probablemente verás valores distintos y con frecuencia menores de `2000`.  
5. (Opcional) Incrementa las iteraciones a `100_000` y vuelve a ejecutar para ver cómo la probabilidad y el efecto cambian.

**Pistas / errores comunes:**
- Observa que aun cuando `count` sea `volatile`, `count++` no sería atómico. `volatile` no sustituye a la sincronización en este caso.

**Preguntas para reflexionar (checkpoint):**
- Explica por qué el resultado es menor que 2000.  
- ¿Qué parte de la operación `count++` causa la pérdida?

**Pequeño reto:** implementa un registro temporal dentro de `incrementar()` que imprima la lectura y la escritura (solo para entender el interleaving — ten cuidado con el gran volumen de salida).

---

## Ejercicio 3 — `synchronized` (solución)

**Conceptos clave (breve):**
- `synchronized` es una forma sencilla de garantizar exclusión mutua: solo un hilo puede ejecutar un bloque o método sincronizado sobre el mismo monitor (objeto) a la vez.  
- Se puede sincronizar un método (`synchronized void metodo()`) o un bloque (`synchronized(this) { ... }`).  
- Alternativas: `AtomicInteger`, `ReentrantLock` y otras clases en `java.util.concurrent`.

**Objetivo del ejercicio:**
Usar `synchronized` para proteger el contador y eliminar la condición de carrera.

**Archivo base:** `ejercicio-3-synchronized/Main.java`

**Pasos para el alumno (hacer en el archivo base):**
1. Localiza la clase `ContadorSeguro` con `void incrementar()`.  
2. **TODO 1:** Protege el método utilizando `synchronized`:
   ```java
   synchronized void incrementar() {
       count++;
   }
   ```
   (o alternativamente usa `synchronized(this)` dentro del método).  
3. **TODO 2:** En `main`, crea la `Runnable` y lanza dos hilos que ejecuten 1000 incrementos cada uno, exactamente como en el ejercicio 2. Usa `join()` para esperar.  
4. Ejecuta repetidamente; el resultado debería ser siempre `2000`.

**Pistas / errores comunes:**
- Si sincronizas en objetos distintos (por ejemplo `synchronized (new Object())` dentro del método), no protegerás el recurso — todos los hilos usarán monitores diferentes. Asegúrate de sincronizar sobre el **mismo** objeto (por ejemplo el propio `this` o el objeto `ContadorSeguro` compartido).

**Checkpoint / discusión:**
- ¿Qué coste tiene proteger con `synchronized`? ¿Qué pasa si el método hace mucho trabajo dentro del bloque sincronizado? (habla de granularidad de la sincronización).

**Reto opcional:** reescribe la solución usando `java.util.concurrent.atomic.AtomicInteger` y compara la simplicidad y el rendimiento.

---

## Ejercicio 4 — Productor-Consumidor con `wait()` / `notifyAll()`

**Conceptos clave (breve):**
- `wait()` y `notify()/notifyAll()` permiten que los hilos cooperen usando el monitor de un objeto.  
- Siempre se debe llamar `wait()` dentro de un bucle `while(condición)` para manejar *spurious wakeups* y revalidar la condición.  
- `notify()` despierta un hilo en espera; `notifyAll()` despierta a todos — en patrones más complejos `notifyAll()` suele ser más seguro.  
- `BlockingQueue` (ej. `ArrayBlockingQueue`) es una alternativa de más alto nivel que maneja bloqueo internamente y evita errores comunes.

**Objetivo del ejercicio:**
Implementar un buffer limitado (cola) y coordinar la producción/consumo con `wait()`/`notifyAll()`.

**Archivo base:** `ejercicio-4-productor-consumidor/Main.java` (incluye `Buffer` con métodos TODO)

**Pasos para el alumno (hacer en el archivo base):**
1. Abre la clase `Buffer` y observa `Queue<Integer> queue` y `int capacidad = 5`.  
2. **TODO 1 — Producir:** Implementa `public synchronized void producir(int valor) throws InterruptedException` así:
   - Mientras la cola esté llena (`while (queue.size() == capacidad) wait();`) espera.  
   - Añade el valor (`queue.add(valor);`) y muestra `System.out.println("Producido: " + valor);`.  
   - Llama a `notifyAll()` al final para despertar consumidores.  
3. **TODO 2 — Consumir:** Implementa `public synchronized int consumir() throws InterruptedException` así:
   - Mientras la cola esté vacía (`while (queue.isEmpty()) wait();`) espera.  
   - Extrae un valor (`int v = queue.remove();`), imprime `Consumido: v` y `notifyAll()`. Devuelve `v`.  
4. En el `main` ya hay `Thread productor` y `Thread consumidor` creados; **TODO 3:** arranca ambos `productor.start(); consumidor.start();`.  
5. Ejecuta el programa y observa la secuencia de `Producido:` y `Consumido:`. Cambia `capacidad` a `1` y vuelve a ejecutar para observar el efecto de bloqueo.  
6. Reto opcional: añade **dos productores** y **dos consumidores** y verifica que la implementación sigue siendo correcta.

**Pistas / errores comunes:**
- No usar `if` en lugar de `while` antes de `wait()`: las notificaciones pueden ser espurias o despierten hilos que no deben continuar.  
- Olvidar `notifyAll()` puede causar que algunos hilos queden permanentemente bloqueados en ciertas condiciones.  
- Evita hacer trabajo prolongado dentro de los bloques sincronizados (reduce concurrencia).

**Alternativa segura (solución):** ver `soluciones/ejercicio-4-productor-consumidor` con implementación que usa `BlockingQueue` — muy recomendable en producción.

---

## Entrega y verificación
- Cada alumno debe subir un ZIP con los `Main.java` completados o entregar un enlace al repositorio con commits claros.  
- Añadir un archivo `respuestas.md` con las respuestas a los checkpoints y capturas de salida (o pegar la salida de terminal).

---

## Opciones adicionales que puedo preparar (elige una)
1. Añadir **pistas inline** en cada starter (`// PISTA:`) sin dar la solución completa.  
2. Generar una **rúbrica de evaluación** en Markdown para imprimir o usar en la plataforma del centro.  

Dime 1 o 2 (o ninguna) y lo preparo inmediatamente.
