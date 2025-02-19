package Estructura;

import Estructura.Nodo;

/**
 *
 * @author Dilan891
 * @param <T>
 */
public class Lista<T> {

    private int length;
    private T head;

    public Lista() {
        this.length = 0;
        this.head = null;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public T getHead() {
        return head;
    }

    public void setHead(T head) {
        this.head = head;
    }

    public boolean isEmpty() {
        return getHead() == null;
    }

    public void printLista() {
        Nodo pointer = (Nodo) getHead();
        while (pointer != null) {
            System.out.print("[" + pointer.getElement() + "]");
            pointer = (Nodo) pointer.getNext();
        }
        System.out.println("\nTamanio de la lista: " + length);
    }

    public void insertBegin(int element) {
        Nodo node = new Nodo(element);
        if (isEmpty()) {
            setHead((T) node);
        } else {
            node.setNext(getHead());
            setHead((T) node);
        }
        length++;
    }

    public Cola insertarListaEnCola(Lista<T> lista, Cola<T> cola) {
        if (lista.isEmpty()) {
            System.out.println("La lista está vacía, no hay elementos para encolar.");
            return null;
        }

        Nodo<T> pointer = (Nodo<T>) lista.getHead(); // Obtén el primer nodo de la lista
        while (pointer != null) {
            T elemento = (T) pointer.getElement(); // Obtén el elemento del nodo actual
            cola.encolar(elemento); // Encola el elemento en la cola
            pointer = (Nodo<T>) pointer.getNext(); // Avanza al siguiente nodo
        }

        System.out.println("Elementos de la lista insertados en la cola correctamente.");
        return cola;
    }

    public void insertFinal(Object element) {
        Nodo node = new Nodo(element);
        if (isEmpty()) {
            setHead((T) node);
        } else {
            Nodo pointer = (Nodo) getHead();
            while (pointer.getNext() != null) {
                pointer = (Nodo) pointer.getNext();
            }
            pointer.setNext(node);
        }
        length++;

    }

    public void insertInIndex(int element, int index) {
        Nodo node = new Nodo(element);
        if (isEmpty()) {
            System.out.println("Lista Vacia");
        } else {
            if (index == 0) {
                insertBegin(element);
            } else {
                if (index < length) {
                    Nodo pointer = (Nodo) getHead();
                    int count = 0;

                    while (pointer.getNext() != null && count < index - 1) {
                        pointer = (Nodo) pointer.getNext();

                        count++;
                    }
                    node.setNext(pointer.getNext());
                    pointer.setNext(node);

                    length++;

                } else {
                    System.out.println("Error in index");
                }
            }

        }
    }

    public Nodo deleteBegin() {
        if (isEmpty()) {
            System.out.println("List is empty");
        } else {
            Nodo pointer = (Nodo) getHead();
            setHead((T) pointer.getNext());
            length--;
            return pointer;

        }
        return null;
    }

    public Nodo deleteFinal() {
        Nodo pointer = (Nodo) getHead();
        if (isEmpty()) {
            System.out.println("List is empty");
        } else {
            if (length > 1) {
                while (((Nodo) pointer.getNext()).getNext() != null) {
                    pointer = (Nodo) pointer.getNext();
                }
                pointer.setNext(null);

                length--;
                return pointer;
            } else {
                deleteBegin();
                return pointer;
            }

        }
        return null;
    }

    public Nodo deleteIndex(int index) {
        Nodo pointer = (Nodo) getHead();
        if (isEmpty()) {
            System.out.println("List is empty");
        } else {
            if (index > length) {
                System.out.println("Error in index!");
            } else {
                if (index == 0) {
                    deleteBegin();
                    return pointer;
                } else {
                    Nodo pointer2;
                    int count = 0;
                    while (((Nodo) pointer.getNext()).getNext() != null && count < index - 1) {
                        pointer = (Nodo) pointer.getNext();
                        count++;
                    }
                    pointer2 = (Nodo) pointer.getNext();
                    pointer.setNext(pointer2.getNext());
                    length--;

                    return pointer;

                }

            }
        }
        return null;
    }

    public void deleteComplete() {
        if (isEmpty()) {
            System.out.println("La lista ya está vacía.");
            return;
        }

        // Restablecer la cabeza de la lista a null
        setHead(null);

        // Restablecer el tamaño de la lista a 0
        setLength(0);

        System.out.println("Todos los elementos de la lista han sido eliminados.");
    }

    public void invertirLista() {
        if (isEmpty()) {
            System.out.println("List is Empty!");
        } else {
            Nodo pointer = (Nodo) getHead();
            Nodo newPointer = null;

            while (pointer != null) {
                Nodo pointerNext = (Nodo) pointer.getNext();
                pointer.setNext(newPointer);
                newPointer = pointer;
                pointer = pointerNext;
            }

            setHead((T) newPointer);
        }
    }

    public Object get(int index) {
        if (isEmpty()) {
            System.out.println("List is Empty!");
            return null;
        } else {
            if (index > length) {
                System.out.println("Error in index!");
                return null;
            } else {
                Nodo pointer = (Nodo) getHead();
                int count = 0;
                while (pointer != null && count < index) {
                    pointer = (Nodo) pointer.getNext();
                    count++;
                }
                return pointer.getElement();
            }
        }
    }
}
