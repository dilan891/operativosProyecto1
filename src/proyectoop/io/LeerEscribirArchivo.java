package proyectoop.io;

import Dao.Configuration;
import Dao.ParametersProcess;
import Dao.Simulator;

import java.util.ArrayList;
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
            List<String> lineas = Files.readAllLines(Path.of(fileDir));
            Configuration configuration = new Configuration(0, 0);
            List<ParametersProcess> processList = new ArrayList<>();
            for (int i = 0; i < lineas.size(); i++) {
                System.out.println(lineas.get(i));
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
                        ParametersProcess process = new ParametersProcess();
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
                            }else if(j == 3 && process.getType().equals("io_bound")){
                                String cycle_exeption = linea[j].split("cycle_exeption: ")[1];
                                process.setNumberOfCyclesE(Integer.parseInt(cycle_exeption));
                            }else if(j == 4 && process.getType().equals("io_bound")){
                                String cycle = linea[j].split("cycle_satisfaction: ")[1];
                                process.setNumberOfCyclesS(Integer.parseInt(cycle));
                            }
                        }
                        // Agregamos el proceso a la lista de procesos
                        processList.add(process);
                        break;
                }
            }
            System.out.println(configuration);
            System.out.println(processList);
            Simulator simulador = new Simulator(processList, configuration);
            System.out.println(simulador);
            return simulador;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getFileName() {
        return fileDir;
    }

}
