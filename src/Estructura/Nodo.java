package Estructura;

/**
 *
 * @author Dilan891
 * @param <T>
 */
public class Nodo<T> {
    private Object element;
    private T next;

    public Nodo(Object element) {
        this.element = element;
        this.next = null;
    }

    public Object getElement() {
        return element;
    }

    public void setElement(Object element) {
        this.element = element;
    }

    public T getNext() {
        return next;
    }

    public void setNext(T next) {
        this.next = next;
    }
    
}
