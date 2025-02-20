package Dao;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author shinhwi
 */
public class AutoId {
    private static final AtomicInteger counter = new AtomicInteger(0);

    public static int generateID() {
        return counter.incrementAndGet();
    }

    public static void main(String[] args) {
        // Ejemplo de uso
        System.out.println("ID de proceso: " + generateID());
        System.out.println("ID de proceso: " + generateID());
        System.out.println("ID de proceso: " + generateID());
    }

}
