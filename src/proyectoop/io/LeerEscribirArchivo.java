package proyectoop.io;

import Dao.Configuration;
import Dao.Proceso;
import Dao.Simulator;
import Estructura.Lista;
import java.io.BufferedWriter;
import java.io.FileWriter;

import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

/**
 *
 * @author Dilan891
 */
public class LeerEscribirArchivo {

    private String fileDir;

    public LeerEscribirArchivo(String fileDir) {
        this.fileDir = fileDir;
    }

    public Simulator LeerArchivo() {
        try {
            System.out.println("Cargando datos");
            //verifica si el archivo existe, si no existe lo crea
            if (!Files.exists(Path.of(fileDir))) {
                System.out.println("El archivo no existe, creando archivo");
                Files.createFile(Path.of(fileDir));
                EscribirDefaultData();
                return null;
            }
            List<String> lineas = Files.readAllLines(Path.of(fileDir)); //Cambiar a Lista
            Configuration configuration = new Configuration(0, 0);
            Lista<Proceso> processList = new Lista();
            //verifica si el archivo esta vacio
            if (lineas.isEmpty()) {
                System.out.println("El archivo esta vacio");
                EscribirDefaultData();
            }
            for (int i = 0; i < lineas.size(); i++) {
                //System.out.println(lineas.get(i));
                switch (i) {
                    case 0:
                    case 3:
                        // Si i es 0 o 3, simplemente continuamos al siguiente ciclo
                        continue;
                    case 1:
                        // Si i es 1, procesamos la configuración de numCores
                        String[] linea1 = lineas.get(i).split(": ");
                        configuration.setNumCores(Integer.valueOf(linea1[1]));
                        break;
                    case 2:
                        // Si i es 2, procesamos la configuración de cycleDuration
                        String[] linea2 = lineas.get(i).split(": ");
                        configuration.setCycleDuration(Integer.valueOf(linea2[1]));
                        break;
                    default:
                        // En el caso de que i no sea 0, 1, 2 o 3
                        Proceso process = new Proceso();
                        String[] linea = lineas.get(i).split(", ");
                        for (int j = 0; j < linea.length; j++) {
                            System.out.println(linea[j]);
                            if (j == 0) {
                                String name = linea[j].split("name: ")[1];
                                System.out.println(name);
                                process.setName(name);
                            } else if (j == 1) {
                                String instruction = linea[j].split("instruction: ")[1];
                                process.setInstructionCant(Integer.parseInt(instruction));
                            } else if (j == 2) {
                                String type = linea[j].split("type: ")[1];
                                process.setType(type);
                            } else if (j == 3 && process.getType().equals("io_bound")) {
                                String cycle_exeption = linea[j].split("cycle_exeption: ")[1];
                                process.setNumberOfCyclesE(Integer.parseInt(cycle_exeption));
                            } else if (j == 4 && process.getType().equals("io_bound")) {
                                String cycle = linea[j].split("cycle_satisfaction: ")[1];
                                process.setNumberOfCyclesS(Integer.parseInt(cycle));
                            }
                        }
                        // Agregamos el proceso a la lista de procesos
                        processList.insertFinal(process);
                        break;
                }
            }
            //System.out.println(configuration);
            processList.printLista();
            
            Simulator simulador = new Simulator(processList, configuration);
            //System.out.println(simulador);
            //simulador.getProcesos().printLista();
            return simulador;
        } catch (IOException e) {
            System.out.println("El archivo no se encontro");
            e.printStackTrace();
        }
        return null;
    }

    private void EscribirDefaultData() {
        try {
            String[] lines = {
                "configuration:",
                "num_cpu: 2",
                "duration_cycle: 1000",
                "Procesos:",
                "- name: P1, instruction: 10, type: cpu_bound",
                "- name: P2, instruction: 10, type: io_bound, cycle_exeption: 5, cycle_satisfaction: 10"
            };
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileDir, true))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void EscribirData(Lista<Proceso> lista){
        try {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileDir, false))) {
                writer.write("configuration:");
                writer.newLine();
                writer.write("num_cpu: 2");
                writer.newLine();
                writer.write("duration_cycle: 1000");
                writer.newLine();
                writer.write("Procesos:");
                writer.newLine();
                for (int i = 0; i < lista.getLength(); i++) {
                    Proceso proceso = (Proceso) lista.get(i);
                    writer.write("- name: " + proceso.getName() + ", instruction: " + proceso.getInstructionCant() + ", type: " + proceso.getType());
                    if (proceso.getType().equals("io_bound")) {
                        writer.write(", cycle_exeption: " + proceso.getNumberOfCyclesE() + ", cycle_satisfaction: " + proceso.getNumberOfCyclesS());
                    }
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFileName() {
        return fileDir;
    }

}
