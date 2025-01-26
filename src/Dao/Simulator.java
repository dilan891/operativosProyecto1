package Dao;

import java.util.List;

/**
 *
 * @author Dilan891
 */
public class Simulator {
    private List<ParametersProcess> procesos;
    private Configuration configuracion;

    @Override
    public String toString() {
        return "Simulator{" + "procesos=" + procesos + ", configuracion=" + configuracion + '}';
    }
    
    //constructor
    public Simulator(List<ParametersProcess> procesos, Configuration configuracion) {
        this.procesos = procesos;
        this.configuracion = configuracion;
    }
    
    //getter and setters
    public List<ParametersProcess> getProcesos() {
        return procesos;
    }

    public void setProcesos(List<ParametersProcess> procesos) {
        this.procesos = procesos;
    }

    public Configuration getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(Configuration configuracion) {
        this.configuracion = configuracion;
    }
    
    
}
