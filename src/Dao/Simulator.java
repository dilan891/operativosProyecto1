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

    public void moverListosABloqueados() {
        // Verificar si la cola de listos está vacía
        if (listos.estaVacia()) {
            System.out.println("La cola de listos está vacía. No hay elementos para mover.");
            return;
        }

        // Desencolar un elemento de la cola de listos y encolarlo en la cola de bloqueados
        Proceso proceso = (Proceso) this.listos.desencolar(); // Desencolar el primer proceso de la cola de listos
        bloqueados.encolar(proceso); // Encolar el proceso en la cola de bloqueados

        System.out.println("Proceso movido de listos a bloqueados: " + proceso.getName());
    }

    public void moverBloqueadosAListos() {
        // Verificar si la cola de listos está vacía
        if (bloqueados.estaVacia()) {
            System.out.println("La cola de listos está vacía. No hay elementos para mover.");
            return;
        }

        // Desencolar un elemento de la cola de listos y encolarlo en la cola de bloqueados
        Proceso proceso = (Proceso) this.bloqueados.desencolar(); // Desencolar el primer proceso de la cola de listos
        listos.encolar(proceso); // Encolar el proceso en la cola de bloqueados

        System.out.println("Proceso movido de listos a bloqueados: " + proceso.getName());
    }

    public void createDeafultCpus(int nCPUS) {
        this.cpus.deleteComplete();
        for (int i = 0; i < nCPUS; i++) {
            this.cpus.insertFinal("cpu");
        }
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

    public Lista<CPU> getCpus() {
        return cpus;
    }

    public void setCpus(Lista<CPU> cpus) {
        this.cpus = cpus;
    }

}
