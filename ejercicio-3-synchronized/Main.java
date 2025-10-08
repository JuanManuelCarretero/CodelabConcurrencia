// Ejercicio 3 - synchronized (starter)
class ContadorSeguro {
    int count = 0;

    // TODO: proteger este método con 'synchronized' para que sea seguro en concurrencia
    void incrementar() {
        count++;
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ContadorSeguro c = new ContadorSeguro();

        // TODO: crear dos hilos que llamen a c.incrementar() 1000 veces cada uno
        // y comprobar que el valor final es 2000
    }
}
