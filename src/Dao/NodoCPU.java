package Dao;

/**
 *
 * @author Dilan891
 */
public class NodoCPU{
    private Proceso procesoActual;
    private int ciclosPorInstruccion;
    private NodoCPU next;

    public Proceso getProcesoActual() {
        return procesoActual;
    }

    public void setProcesoActual(Proceso procesoActual) {
        this.procesoActual = procesoActual;
    }

    public int getCiclosPorInstruccion() {
        return ciclosPorInstruccion;
    }

    public void setCiclosPorInstruccion(int ciclosPorInstruccion) {
        this.ciclosPorInstruccion = ciclosPorInstruccion;
    }

    public NodoCPU getNext() {
        return next;
    }

    public void setNext(NodoCPU next) {
        this.next = next;
    }
    
}
