package Estructura;

/**
 *
 * @author Dilan891
 * @param <T>
 */
public class NodoP<T> {
    private T element;
    private NodoP<T> next;

    public NodoP(T element) {
        this.element = element;
        this.next = null;
    }

    public T getElement() {
        return element;
    }

    public NodoP<T> getNext() {
        return next;
    }

    public void setNext(NodoP<T> next) {
        this.next = next;
    }
}
