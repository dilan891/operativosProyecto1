package Dao;

/**
 *
 * @author Dilan891
 */
public class ParametersProcess {
    private String name;
    private Integer instructionCant;
    private String type;
    
    private Integer cyclesExeption;
    private Integer cyclesSatifaction;

    @Override
    public String toString() {
        return "ParametersProcess{" + "name=" + name + ", instructionCant=" + instructionCant + ", type=" + type + ", cyclesExeption=" + cyclesExeption + ", cyclesSatifaction=" + cyclesSatifaction + '}';
    }

    //constructor io_bound
    public ParametersProcess(String name, Integer instructionCant, String type, Integer numberOfCyclesE, Integer numberOfCyclesS) {
        this.name = name;
        this.instructionCant = instructionCant;
        this.type = type;
        this.cyclesExeption = numberOfCyclesE;
        this.cyclesSatifaction = numberOfCyclesS;
    }

    public ParametersProcess() {
    }
    
    //constructor cpu_bound
    public ParametersProcess(String name, Integer instructionCant, String type) {
        this.name = name;
        this.instructionCant = instructionCant;
        this.type = type;
        this.cyclesExeption = null;
        this.cyclesSatifaction = null;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getInstructionCant() {
        return instructionCant;
    }

    public void setInstructionCant(Integer instructionCant) {
        this.instructionCant = instructionCant;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNumberOfCyclesE() {
        return cyclesExeption;
    }

    public void setNumberOfCyclesE(Integer numberOfCyclesE) {
        this.cyclesExeption = numberOfCyclesE;
    }

    public Integer getNumberOfCyclesS() {
        return cyclesSatifaction;
    }

    public void setNumberOfCyclesS(Integer numberOfCyclesS) {
        this.cyclesSatifaction = numberOfCyclesS;
    }
  
}
