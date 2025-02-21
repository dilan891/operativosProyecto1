package Dao;

import Dao.Semaphore;
import GUI.Classes.Ejecucion;

/**
 *
 * @author Dilan891
 */
public class Proceso implements Runnable {

    private String name; // nombre del proceso
    private Integer instructionCant; //cantidad de instrucciones del proceso
    private String type; //tipo del proceso io_bound o cpu_bound
    private int processID;       // ID único del proceso

    private Integer cyclesExeption;
    private Integer cyclesSatifaction;

    private Simulator simulator; //clase simulador a la que pertenece
    private Semaphore semaforo; // Referencia al semáforo
    private Ejecucion ventana; //ventana donde se esta ejecuntado el proceso

    private String status;

    private int ciclosEjecutados = 0;

    @Override
    public String toString() {
        return "ParametersProcess{" + "name=" + name + ", instructionCant=" + instructionCant + ", type=" + type + ", cyclesExeption=" + cyclesExeption + ", cyclesSatifaction=" + cyclesSatifaction + '}';
    }

    //constructor io_bound
    public Proceso(String name, Integer instructionCant, String type, Integer numberOfCyclesE, Integer numberOfCyclesS) {
        this.name = name;
        this.instructionCant = instructionCant;
        this.type = type;
        this.cyclesExeption = numberOfCyclesE;
        this.cyclesSatifaction = numberOfCyclesS;
        this.status = "Ready";
    }

    public Proceso() {
        this.status = "Ready";
    }

    //constructor cpu_bound
    public Proceso(String name, Integer instructionCant, String type) {
        this.name = name;
        this.instructionCant = instructionCant;
        this.type = type;
        this.cyclesExeption = null;
        this.cyclesSatifaction = null;
        this.status = "Ready";
    }

    public void ejecutar(Configuration config) {
        System.out.println("Ejecutando proceso: " + name);
        this.changeStatus("Running");

        int cicleDuration = config.getCycleDuration(); // Duración de cada ciclo en milisegundos
        int quantum = 2000;

        if (this.type.equals("cpu_bound")) {
            int tiempoEjecutado = 0;
            for (int i = ciclosEjecutados + 1; i <= this.instructionCant; i++) {
                System.out.println("Instruccion " + i + " ejecutada.");
                try {
                    Thread.sleep(cicleDuration);
                    tiempoEjecutado += cicleDuration;
                } catch (InterruptedException e) {
                    System.out.println("Proceso interrumpido.");
                    return;
                }
                ciclosEjecutados++;

                // Si Round Robin está activado y el quantum se agotó, interrumpir el proceso
                if (simulator.getPlanificationType().equals("Round Robin") && tiempoEjecutado >= quantum) {
                    System.out.println("Quantum agotado, proceso interrumpido y enviado a la cola de listos.");
                    this.changeStatus("Ready");
                    simulator.getListos().encolar(this);
                    ventana.actualizarJListConCola(simulator.getListos(), ventana.getColaListos());
                    return; // Salir para permitir que otros procesos se ejecuten
                }
            }
        } else if (this.type.equals("io_bound")) {
            // Si la planificación es Round Robin, solo ejecuta hasta que se acabe el quantum
            int instruccionesEjecutadasEnEsteTurno = 0;

            while (ciclosEjecutados < this.instructionCant) {
                for (int i = 0; i < this.cyclesExeption && ciclosEjecutados < this.instructionCant; i++) {
                    System.out.println("Instruccion " + (ciclosEjecutados + 1) + " ejecutada.");
                    try {
                        Thread.sleep(cicleDuration);
                    } catch (InterruptedException e) {
                        System.out.println("Proceso interrumpido.");
                        return;
                    }
                    ciclosEjecutados++;
                    instruccionesEjecutadasEnEsteTurno++;

                    // Si estamos en Round Robin y se agota el quantum, interrumpimos
                    if (simulator.getPlanificationType().equals("Round Robin") && instruccionesEjecutadasEnEsteTurno >= quantum) {
                        System.out.println("Quantum agotado, proceso interrumpido y enviado a la cola de listos.");
                        this.changeStatus("Ready");
                        simulator.getListos().encolar(this);
                        ventana.actualizarJListConCola(simulator.getListos(), ventana.getColaListos());
                        return; // Sale para que el proceso pueda volver a ejecutarse después
                    }
                }
                //Cambia el estado a bloqueado
                this.changeStatus("Blocked");
                // Simula la espera por E/S
                System.out.println("Proceso en espera de E/S...");
                ///simulator.procesoTerminado(this);
                try {
                    Thread.sleep(this.cyclesSatifaction * cicleDuration);
                } catch (InterruptedException e) {
                    System.out.println("Proceso interrumpido durante la espera de E/S.");
                    return;
                }
                //Lo elimna de la cola de bloquedos
                simulator.eliminarDeColaBloq(this.processID);
                ventana.actualizarColaBloqueados();

                //Vuelve a insertarlo en la cola de listos
                simulator.getListos().encolar(this);
                if (simulator.getPlanificationType() == "SJF") {
                    simulator.reordenarColaSJF();
                }
                ventana.actualizarJListConCola(simulator.getListos(), ventana.getColaListos());

                // Después de la espera de E/S, cambia el estado a "Ready" y lo mueve a la cola de listos
                this.changeStatus("Ready");
                //moverAColaDeListos(this);

                // 🚫 Terminar el hilo aquí para que el proceso no siga ejecutándose automáticamente
                return;
            }
        }
        System.out.println("PROCESO FINALIZADO: " + this.name);
        this.changeStatus("Terminated");
    }

    @Override
    public void run() {
        try {
            if (semaforo == null) {
                ejecutar(this.simulator.getConfiguracion());
            } else {
                // Antes de entrar a la sección crítica, adquirir el semáforo
                semaforo.acquire();
                ejecutar(this.simulator.getConfiguracion());
            }
        } catch (InterruptedException e) {
            System.out.println("Proceso " + name + " interrumpido.");
        } finally {
            // Al terminar, notificar al simulador
            if (simulator != null) {
                simulator.procesoTerminado(this);
            }
            // Liberar el semáforo después de terminar la ejecución crítica
            if (semaforo != null) {
                semaforo.release();
            }
        }
    }

    private void changeStatus(String status) {
        this.status = status;
        switch (status) {
            case "Blocked":
                //Inserta en cola de bloqueados
                simulator.getBloqueados().encolar(this);
                this.ventana.actualizarColaBloqueados();
                break;
            default:
                break;
        }

        if (this.ventana != null) {
            //Actualiza la ui de la cola de Bloqueados
            ventana.actualizarEstadoPorID(processID, this.status, simulator);
        }

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

    public void setSemaforo(Semaphore semaforo) {
        this.semaforo = semaforo;
    }

    public int getProcessID() {
        return processID;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatusInRunning() {
        System.out.println("cambia el status");
        this.status = "Running";
    }

    public void setVentana(Ejecucion ventana) {
        this.ventana = ventana;
    }

    public Simulator getSimulator() {
        return simulator;
    }

    public void setSimulator(Simulator simulator) {
        this.simulator = simulator;
    }

    public Integer getCyclesExeption() {
        return cyclesExeption;
    }

    public void setCyclesExeption(Integer cyclesExeption) {
        this.cyclesExeption = cyclesExeption;
    }

    public Integer getCyclesSatifaction() {
        return cyclesSatifaction;
    }

    public void setCyclesSatifaction(Integer cyclesSatifaction) {
        this.cyclesSatifaction = cyclesSatifaction;
    }

}
