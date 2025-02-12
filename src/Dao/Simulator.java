package Dao;

import Estructura.Cola;
import Estructura.Lista;
import Estructura.Nodo;
import java.util.List;

/**
 *
 * @author Dilan891
 */
public class Simulator {

    private Cola listos;
    private Cola bloqueados;
    private Configuration configuracion;
    Lista<CPU> cpus;
    //PoliticaPlanificacion politicaActual;

    //constructor
    public Simulator(Configuration configuracion) {
        this.configuracion = configuracion;
    }

    public Simulator(Lista<Proceso> procesos, Configuration configuracion) {
        this.configuracion = configuracion;
        this.listos = new Cola(); // Inicializa la cola de procesos listos

        this.listos = procesos.insertarListaEnCola(procesos, listos);
        
        this.listos.imprimirCola();
        // Inicializa las otras colas y estructuras necesarias
        this.bloqueados = new Cola();
        this.cpus = new Lista<>();

    }


    public Configuration getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(Configuration configuracion) {
        this.configuracion = configuracion;
    }

    public Cola getListos() {
        return listos;
    }

    public void setListos(Cola listos) {
        this.listos = listos;
    }

    public Cola getBloqueados() {
        return bloqueados;
    }

    public void setBloqueados(Cola bloqueados) {
        this.bloqueados = bloqueados;
    }

}
