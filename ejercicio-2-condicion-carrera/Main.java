// Ejercicio 2 - Condición de carrera (starter)
class Contador {
    int count = 0;

    void incrementar() {
        // NOTA: esta implementación NO es segura frente a concurrencia.
        count++;
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Contador c = new Contador();

        // TODO: crear una tarea que llame c.incrementar() 1000 veces
        // TODO: crear y lanzar dos hilos que ejecuten la tarea
        // TODO: esperar a que terminen (join) y mostrar el valor final

        System.out.println("Valor final (esperado 2000): " + c.count);
    }
}
