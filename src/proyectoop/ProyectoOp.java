package proyectoop;

import Dao.Simulator;
import GUI.Classes.Menu;
import java.io.File;
import proyectoop.io.LeerEscribirArchivo;

/**
 *
 * @author Dilan891
 */
public class ProyectoOp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        String userHome = System.getProperty("user.home");
        File documentosDir = new File(userHome, "Desktop");
        File archivoConfig = new File(documentosDir, "configuration_simulator.txt");
        
        LeerEscribirArchivo data = new LeerEscribirArchivo(archivoConfig.getAbsolutePath());
        Simulator simulatorData = data.LeerArchivo();
       
        //Abre la interfaz grafica
        Menu menu = new Menu(simulatorData);
        menu.setVisible(true);

    }
    
}
