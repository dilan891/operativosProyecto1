package Estructura;

import Estructura.NodoP;

/**
 *
 * @author Dilan891
 * @param <T>
 */
public class ListaP<T> {

    private int length;
    private NodoP<T> head;

    public ListaP() {
        this.length = 0;
        this.head = null;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public NodoP<T> getHead() {
        return head;
    }

    public void setHead(NodoP<T> head) {
        this.head = head;
    }

    public boolean isEmpty() {
        return getHead() == null;
    }

    public void printLista() {
        NodoP<T> pointer = getHead();
        while (pointer != null) {
            System.out.print("[" + pointer.getElement() + "]");
            pointer = pointer.getNext();
        }
        System.out.println("\nTamanio de la lista: " + length);
    }

    public void insertBegin(T element) {
        NodoP<T> node = new NodoP<>(element);
        if (isEmpty()) {
            setHead(node);
        } else {
            node.setNext(getHead());
            setHead(node);
        }
        length++;
    }

    public void insertFinal(T element) {
        NodoP<T> node = new NodoP<>(element);
        if (isEmpty()) {
            setHead(node);
        } else {
            NodoP<T> pointer = getHead();
            while (pointer.getNext() != null) {
                pointer = pointer.getNext();
            }
            pointer.setNext(node);
        }
        length++;
    }
    
    public NodoP<T> deleteBegin() {
        if (isEmpty()) {
            System.out.println("List is empty");
            return null;
        } else {
            NodoP<T> pointer = getHead();
            setHead(pointer.getNext());
            length--;
            return pointer;
        }
    }
    
    public void deleteComplete() {
        setHead(null);
        setLength(0);
        System.out.println("Todos los elementos de la lista han sido eliminados.");
    }
}
