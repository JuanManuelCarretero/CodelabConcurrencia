// Solución ejercicio 4 - wait/notify
import java.util.LinkedList;
import java.util.Queue;

class Buffer {
    private Queue<Integer> queue = new LinkedList<>();
    private int capacidad = 5;

    public synchronized void producir(int valor) throws InterruptedException {
        while (queue.size() == capacidad) wait();
        queue.add(valor);
        System.out.println("Producido: " + valor);
        notifyAll();
    }

    public synchronized int consumir() throws InterruptedException {
        while (queue.isEmpty()) wait();
        int valor = queue.remove();
        System.out.println("Consumido: " + valor);
        notifyAll();
        return valor;
    }
}

public class Main {
    public static void main(String[] args) {
        Buffer buffer = new Buffer();

        Thread productor = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try { buffer.producir(i); Thread.sleep(200); } catch (InterruptedException e) {}
            }
        });

        Thread consumidor = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try { buffer.consumir(); Thread.sleep(400); } catch (InterruptedException e) {}
            }
        });

        productor.start();
        consumidor.start();
    }
}
