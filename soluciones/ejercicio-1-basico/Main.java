// Solución ejercicio 1 - dos hilos
public class Main {
    public static void main(String[] args) {
        Runnable tarea = () -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + ": " + i);
                try { Thread.sleep(150); } catch (InterruptedException e) {}
            }
        };

        Thread t1 = new Thread(tarea, "Hilo-A");
        Thread t2 = new Thread(tarea, "Hilo-B");

        t1.start();
        t2.start();
    }
}
