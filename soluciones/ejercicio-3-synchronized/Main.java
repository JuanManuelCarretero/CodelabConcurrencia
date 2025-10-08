// Solución ejercicio 3 - synchronized
class ContadorSeguro {
    int count = 0;

    synchronized void incrementar() {
        count++;
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ContadorSeguro c = new ContadorSeguro();

        Runnable tarea = () -> {
            for (int i = 0; i < 1000; i++) c.incrementar();
        };

        Thread t1 = new Thread(tarea, "Hilo-1");
        Thread t2 = new Thread(tarea, "Hilo-2");

        t1.start(); t2.start();
        t1.join(); t2.join();

        System.out.println("Valor final seguro (esperado 2000): " + c.count);
    }
}
