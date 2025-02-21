package Estructura;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author Dilan891
 * @param <T>
 */
public class Cola<T> implements Iterable<T> {
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
    
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Nodo<T> actual = frente;

            @Override
            public boolean hasNext() {
                return actual != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No hay más elementos en la cola");
                }
                T elemento = (T) actual.getElement();
                actual = (Nodo<T>) actual.getNext();
                return elemento;
            }
        };
    }
    
    public boolean isEmpty(){
        return frente == null;
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

    // Crea una copia de la cola actual en una nueva cola
    public Cola createCopyCola(){
        Cola<T> nuevaCola = new Cola<>();
        Nodo<T> actual = frente;
        while (actual != null) {
            nuevaCola.encolar((T) actual.getElement());
            actual = (Nodo<T>) actual.getNext();
        }
        return nuevaCola;
    }
}