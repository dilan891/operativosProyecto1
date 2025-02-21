package Dao;

import Estructura.Cola;
import Estructura.Lista;
import Estructura.Nodo;
import GUI.Classes.Ejecucion;

import Estructura.ListaP;
import Estructura.NodoP;

/**
 *
 * @author Dilan891
 */
public class Simulator {

    private Cola listos;
    private Cola bloqueados;
    private Configuration configuracion;
    Lista<Nodo> cpus;
    private boolean ejecutando = false;
    private Ejecucion ventana;
    String planificationType = "";
    // PoliticaPlanificacion politicaActual;

    // constructor
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

        // Desencolar un elemento de la cola de listos y encolarlo en la cola de
        // bloqueados
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

        // Desencolar un elemento de la cola de listos y encolarlo en la cola de
        // bloqueados
        Proceso proceso = (Proceso) this.bloqueados.desencolar(); // Desencolar el primer proceso de la cola de listos
        listos.encolar(proceso); // Encolar el proceso en la cola de bloqueados

        System.out.println("Proceso movido de listos a bloqueados: " + proceso.getName());
    }

    // Este método se llama cuando un proceso termina su ejecución
    public synchronized void procesoTerminado(Proceso procesoFinalizado) {
        // Aquí puedes actualizar el estado o liberar recursos asociados al procesoFinalizado

        // Verificar si hay procesos en la cola listos para ejecutarse
        if (!listos.estaVacia()) {
            Proceso siguiente = (Proceso) listos.desencolar();
            cpus.insertBegin(siguiente);
            siguiente.setProcessID(AutoId.generateID());
            siguiente.setSimulator(this);
            siguiente.setVentana(ventana);
            Thread hilo = new Thread(siguiente);
            hilo.start();
        }
        // Opcional: Actualizar la interfaz gráfica o registros
        //Actualiza la lista de listos
        ventana.actualizarCPUsPorList(ventana.getListaCpu(), this, cpus);

        //Actualiza la cola de listos
        ventana.actualizarJListConCola(listos, ventana.getColaListos());
    }

    public void ejecutarProcesos(Ejecucion ventana) {
        // Verificar si la cola de listos está vacía
        if (listos.estaVacia()) {
            System.out.println("La cola de listos está vacía. No hay elementos para ejecutar.");
            return;
        }

        // Verificar si la lista de CPUs está vacía
        if (cpus.isEmpty()) {
            System.out.println("La lista de CPUs está vacía. No hay CPUs disponibles para ejecutar procesos.");
            return;
        }

        this.ejecutando = true;
        // cantidad de nucleos
        int cantCores = configuracion.getNumCores();

        // Colaca el proceso de la cola de listos en la lista de cpus
        Nodo nodoActual = cpus.getHead();
        // Iteramos hasta el número de núcleos o hasta que no queden procesos en la cola
        for (int i = 0; i < cantCores && !listos.isEmpty(); i++) {
            // Desencolamos un proceso de la cola de listos
            Proceso proceso = (Proceso) listos.desencolar();
            //aqui se asigna id de cada proceso
            proceso.setProcessID(AutoId.generateID());

            // Si existe un nodo para este núcleo, asignamos el proceso
            if (nodoActual != null) {
                nodoActual.setElement(proceso);
                // Avanzamos al siguiente núcleo
                nodoActual = (Nodo) nodoActual.getNext();
            }
        }

        System.out.println("Se ha insertado la lista de CPUs");
        //cpus.printLista()

        //clase semaforo
        Semaphore semaforo = new Semaphore(3);
        //ejecuta cada proceso en un hilo distinto
        for (int i = 0; i < cantCores; i++) {
            Proceso proceso = (Proceso) cpus.get(i);
            proceso.setSimulator(this);
            proceso.setVentana(ventana);
            proceso.setSemaforo(semaforo);
            if (proceso != null) {
                Thread hilo = new Thread(proceso);
                hilo.start();
            }
        }

        //Actualiza la lsita de listos
        ventana.actualizarCPUsPorList(ventana.getListaCpu(), this, cpus);

        //Actualiza la cola de listos
        ventana.actualizarJListConCola(listos, ventana.getColaListos());
    }

    /*public void detenerHilos() {
        for (Thread hilo : listaHilos) {
            if (hilo != null && hilo.isAlive()) {
                hilo.interrupt();  // Solicita la interrupción del hilo
            }
        }
    }*/

    public void eliminarDeColaBloq(int Id) {
        Cola aux = new Cola();
        System.out.println("Se eliminara de la cola de bloqueados: " + Id);
        // Extrae todos los procesos y encola solo los que no tienen el id a eliminar.
        while (!bloqueados.estaVacia()) {
            Proceso proceso = (Proceso) bloqueados.desencolar();
            System.out.println("Procesando: " + proceso.getProcessID());
            if (proceso.getProcessID() != Id) {
                aux.encolar(proceso);
            }
        }
        System.out.println("Finalizo la eliminacion");

        // En lugar de reasignar, vacía la cola original y vuelve a llenarla con aux.
        while (!bloqueados.estaVacia()) {
            bloqueados.desencolar();
        }
        while (!aux.estaVacia()) {
            bloqueados.encolar(aux.desencolar());
        }
    }

    //reordena la cola para que los procesos con menor numero de instrucciones esten al principio
    public void reordenarColaSJF() {
        // Creamos una lista temporal para almacenar los procesos de forma ordenada.
        ListaP<Proceso> listaTemporal = new ListaP<>();

        // Extraemos todos los procesos de la cola e insertamos ordenadamente en la lista temporal.
        while (!listos.estaVacia()) {
            Proceso p = (Proceso) listos.desencolar();
            insertarOrdenado(listaTemporal, p);
        }

        // Volvemos a encolar los procesos en la cola, en el orden ordenado.
        NodoP actual = listaTemporal.getHead();
        while (actual != null) {
            listos.encolar(actual.getElement());
            actual = actual.getNext();
        }

    }

    // Reordena la cola para que los procesos con menor tiempo restante de ejecución estén al principio
    public void reordenarColaSRT() {
        // Creamos una lista temporal para almacenar los procesos de forma ordenada.
        ListaP<Proceso> listaTemporal = new ListaP<>();

        // Extraemos todos los procesos de la cola e insertamos ordenadamente en la lista temporal.
        while (!listos.estaVacia()) {
            Proceso p = (Proceso) listos.desencolar();
            insertarOrdenadoSRT(listaTemporal, p);
        }

        // Volvemos a encolar los procesos en la cola, en el orden ordenado.
        NodoP actual = listaTemporal.getHead();
        while (actual != null) {
            listos.encolar(actual.getElement());
            actual = actual.getNext();
        }
    }

    // Ordena de forma ascendente según los ciclos restantes de ejecución (instructionCant - ciclosEjecutados).
    public static void insertarOrdenadoSRT(ListaP<Proceso> lista, Proceso p) {
        // Calcula el tiempo restante en ciclos: cantidad de instrucciones - ciclos ejecutados
        int tiempoRestante = p.getInstructionCant() - p.getCiclosEjecutados();

        // Si la lista está vacía o el proceso p tiene menos ciclos restantes que el primer elemento,
        // se inserta al inicio.
        NodoP cabeza = lista.getHead();
        if (lista.isEmpty() || tiempoRestante < ((Proceso) cabeza.getElement()).getInstructionCant() - ((Proceso) cabeza.getElement()).getCiclosEjecutados()) {
            lista.insertBegin(p);
            return;
        }

        // De lo contrario, se busca la posición correcta.
        NodoP actual = lista.getHead();
        while (actual.getNext() != null
                && (tiempoRestante >= ((Proceso) actual.getNext().getElement()).getInstructionCant() - ((Proceso) actual.getNext().getElement()).getCiclosEjecutados())) {
            actual = actual.getNext();
        }

        // Se crea un nuevo nodo para p e se inserta después de 'actual'
        NodoP nuevoNodo = new NodoP(p);
        nuevoNodo.setNext(actual.getNext());
        actual.setNext(nuevoNodo);
    }

    // Reordena la cola para que los procesos con el mayor ratio de respuesta estén al principio
    public void reordenarColaHRRN() {
        // Creamos una lista temporal para almacenar los procesos de forma ordenada.
        ListaP<Proceso> listaTemporal = new ListaP<>();

        // Extraemos todos los procesos de la cola e insertamos ordenadamente en la lista temporal.
        while (!listos.estaVacia()) {
            Proceso p = (Proceso) listos.desencolar();
            insertarOrdenadoHRRN(listaTemporal, p);
        }

        // Volvemos a encolar los procesos en la cola, en el orden ordenado.
        NodoP actual = listaTemporal.getHead();
        while (actual != null) {
            listos.encolar(actual.getElement());
            actual = actual.getNext();
        }
    }

// Método auxiliar para insertar un proceso en orden en la listaTemporal según HRRN
    public static void insertarOrdenadoHRRN(ListaP<Proceso> lista, Proceso p) {
        // Calcula el tiempo de espera
        int tiempoEspera = p.getTiempoEspera();

        float hrrn = (float) (tiempoEspera + p.getInstructionCant()) / p.getInstructionCant();

        // Si la lista está vacía o el proceso p tiene un mayor ratio de respuesta que el primer elemento,
        // se inserta al inicio.
        NodoP cabeza = lista.getHead();
        if (lista.isEmpty() || hrrn > calcularHRRN((Proceso) cabeza.getElement())) {
            lista.insertBegin(p);
            return;
        }

        // De lo contrario, se busca la posición correcta.
        NodoP actual = lista.getHead();
        while (actual.getNext() != null
                && hrrn <= calcularHRRN((Proceso) actual.getNext().getElement())) {
            actual = actual.getNext();
        }

        // Se crea un nuevo nodo para p e se inserta después de 'actual'
        NodoP nuevoNodo = new NodoP(p);
        nuevoNodo.setNext(actual.getNext());
        actual.setNext(nuevoNodo);
    }

// Método auxiliar para calcular el ratio de respuesta
    public static float calcularHRRN(Proceso p) {
        int tiempoEspera = p.getTiempoEspera();
        return (float) (tiempoEspera + p.getInstructionCant()) / p.getInstructionCant();
    }

    public void actualizarTiempoEsperaEnCola() {
        Nodo actual = listos.getHead();
        while (actual != null) {
            Proceso p = (Proceso) actual.getElement();
            p.incrementarTiempoEspera(1); // Incrementa en 1 unidad (puede ser 1 ciclo o 1 milisegundo, según tu lógica)
            actual = (Nodo) actual.getNext();
        }
    }

    // Método auxiliar para insertar un proceso en orden en la listaTemporal
    // Ordena de forma ascendente según instructionCant.
    public static void insertarOrdenado(ListaP<Proceso> lista, Proceso p) {
        // Si la lista está vacía o el proceso p tiene menos instrucciones que el primer elemento,
        // se inserta al inicio.
        NodoP cabeza = lista.getHead();
        if (lista.isEmpty() || p.getInstructionCant() < ((Proceso) cabeza.getElement()).getInstructionCant()) {
            lista.insertBegin(p);
            return;
        }

        // De lo contrario, se busca la posición correcta.
        NodoP actual = lista.getHead();
        while (actual.getNext() != null
                && ((Proceso) actual.getNext().getElement()).getInstructionCant() <= p.getInstructionCant()) {
            actual = actual.getNext();
        }

        // Se crea un nuevo nodo para p e se inserta después de 'actual'
        NodoP nuevoNodo = new NodoP(p);
        nuevoNodo.setNext(actual.getNext());
        actual.setNext(nuevoNodo);
    }

    public void createDeafultCpus(int nCPUS) {
        this.cpus.deleteComplete();
        for (int i = 0; i < nCPUS; i++) {
            this.cpus.insertFinal("core " + (i + 1) + ": N/A");
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

    public Lista<Nodo> getCpus() {
        return cpus;
    }

    public void setCpus(Lista<Nodo> cpus) {
        this.cpus = cpus;
    }

    public void setVentana(Ejecucion ventana) {
        this.ventana = ventana;
    }

    public boolean isEjecutando() {
        return ejecutando;
    }

    public void setEjecutando(boolean ejecutando) {
        this.ejecutando = ejecutando;
    }

    public String getPlanificationType() {
        return planificationType;
    }

    public void setPlanificationType(String planificationType) {
        this.planificationType = planificationType;
    }

}
