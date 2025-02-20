package Dao;

/**
 *
 * @author Shinnhwi
 */
public class Semaphore {
     private int permits;

    public Semaphore(int permits) {
        this.permits = permits;
    }

    // Método para adquirir un permiso
    public synchronized void acquire() throws InterruptedException {
        while (permits <= 0) {
            wait();
        }
        permits--;
        System.out.println(Thread.currentThread().getName() + " adquirió permiso. Permisos restantes: " + permits);
    }

    // Método para liberar un permiso
    public synchronized void release() {
        permits++;
        System.out.println(Thread.currentThread().getName() + " liberó permiso. Permisos disponibles: " + permits);
        notify(); // Notifica a uno de los hilos en espera
    }
}
