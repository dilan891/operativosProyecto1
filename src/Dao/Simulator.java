package Dao;

import Estructura.Lista;

/**
 *
 * @author Dilan891
 */
public class Simulator {
    private Lista<ParametersProcess> procesos;
    private Configuration configuracion;

    @Override
    public String toString() {
        return "Simulator{" + "procesos=" + procesos + ", configuracion=" + configuracion + '}';
    }
    
    //constructor
    public Simulator(Lista<ParametersProcess> procesos, Configuration configuracion) {
        this.procesos = procesos;
        this.configuracion = configuracion;
    }
    
    //getter and setters
    public Lista<ParametersProcess> getProcesos() {
        return procesos;
    }

    public void setProcesos(Lista<ParametersProcess> procesos) {
        this.procesos = procesos;
    }

    public Configuration getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(Configuration configuracion) {
        this.configuracion = configuracion;
    }
    
    
}
