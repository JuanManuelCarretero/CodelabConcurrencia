// Ejercicio 1 - Hilo básico (starter)
public class Main {
    public static void main(String[] args) {
        // TODO: Crear una tarea Runnable que imprima del 1 al 5
        // con una pausa de 150 ms entre números.
        // TODO: Crear DOS hilos que ejecuten esa misma tarea y asignarles nombres diferentes.
        // TODO: Iniciar ambos hilos.

        // Ejemplo parcial para guiar (no eliminar, completar lo que falta):
        Runnable tarea = () -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + ": " + i);
                try { Thread.sleep(150); } catch (InterruptedException e) {}
            }
        };

        // TODO: crear hilos t1 y t2 usando 'tarea' y arrancarlos
    }
}
