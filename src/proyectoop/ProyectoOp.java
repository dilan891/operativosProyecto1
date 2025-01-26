/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyectoop;

import Dao.Simulator;
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
       
    }
    
}
