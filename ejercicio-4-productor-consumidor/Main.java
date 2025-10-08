// Ejercicio 4 - Productor/Consumidor con wait/notify (starter)
import java.util.LinkedList;
import java.util.Queue;

class Buffer {
    private Queue<Integer> queue = new LinkedList<>();
    private int capacidad = 5;

    // TODO: implementar producir(int valor) usando synchronized, wait() y notifyAll()
    public void producir(int valor) throws InterruptedException {
        // TODO: bloquear si la cola está llena, añadir el valor y notificar
    }

    // TODO: implementar consumir() que espere si la cola está vacía y devuelva un valor
    public int consumir() throws InterruptedException {
        // TODO: esperar si está vacía, eliminar un elemento y notificar
        return -1;
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

        // TODO: arrancar productor y consumidor
    }
}
