package Estructura;

/**
 *
 * @author Dilan891
 * @param <T>
 */
public class Cola<T> {
    private Nodo<T> frente; // Referencia al primer nodo de la cola
    private Nodo<T> fin;    // Referencia al último nodo de la cola
    private int tamaño;     // Tamaño de la cola

    // Constructor para inicializar una cola vacía
    public Cola() {
        this.frente = null;
        this.fin = null;
        this.tamaño = 0;
    }

    // Método para encolar un elemento
    public void encolar(T elemento) {
        Nodo<T> nuevoNodo = new Nodo<>(elemento);
        if (estaVacia()) {
            frente = nuevoNodo;
        } else {
            fin.setNext((T) nuevoNodo);
        }
        fin = nuevoNodo;
        tamaño++;
    }

    // Método para desencolar un elemento
    public T desencolar() {
        if (estaVacia()) {
            throw new IllegalStateException("La cola está vacía");
        }
        T elemento = (T) frente.getElement();
        frente = (Nodo<T>) frente.getNext();
        if (frente == null) {
            fin = null;
        }
        tamaño--;
        return elemento;
    }

    // Método para ver el primer elemento de la cola sin desencolarlo
    public T verPrimero() {
        if (estaVacia()) {
            throw new IllegalStateException("La cola está vacía");
        }
        return (T) frente.getElement();
    }

    // Método para verificar si la cola está vacía
    public boolean estaVacia() {
        return frente == null;
    }

    // Método para obtener el tamaño de la cola
    public int getTamaño() {
        return tamaño;
    }

    // Método para imprimir la cola (opcional, para depuración)
    public void imprimirCola() {
        Nodo<T> actual = frente;
        while (actual != null) {
            System.out.print(actual.getElement() + " ");
            actual = (Nodo<T>) actual.getNext();
        }
        System.out.println();
    }
}