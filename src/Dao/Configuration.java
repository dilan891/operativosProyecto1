package Dao;

/**
 *
 * @author Dilan891
 */
public class Configuration {
    private Integer cycleDuration;
    private Integer numCores;

     // Constructor
    public Configuration(int duracionCiclo, int numProcesadores) {
        this.cycleDuration = duracionCiclo;
        this.numCores = numProcesadores;
    }

    @Override
    public String toString() {
        return "Configuration{" + "cycleDuration=" + cycleDuration + ", numCores=" + numCores + '}';
    }
    
    
    public Integer getCycleDuration() {
        return cycleDuration;
    }

    public void setCycleDuration(Integer cycleDuration) {
        this.cycleDuration = cycleDuration;
    }

    public Integer getNumCores() {
        return numCores;
    }

    public void setNumCores(Integer numCores) {
        this.numCores = numCores;
    }
        
}
