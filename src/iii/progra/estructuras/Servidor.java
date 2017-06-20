/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iii.progra.estructuras;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
//import java.util.logging.Level;
//import java.util.logging.Logger;

/**
 * Esta es la clase del servidor.
 * @author Esteban
 */
public class Servidor extends Thread{
    
    private boolean activo;
    private boolean pausado;
    private int contador = 0;
    private ArrayList<Partida> partidasEnCurso;
    private ArrayList<Jugador> jugadoresEsperaDuo,jugadoresEsperaTrio,jugadoresEsperaCuarteto;
    private String logs;
    //Campos de las conexiones del servidor
    private ServerSocket serverSocket;
    private OutputStream conexionSalida;
    private ObjectOutputStream flujoDeSalida;
    private InputStream conexionEntrada;
    private ObjectInputStream flujoDeEntrada;
    
    /**
     * Crea un servidor nuevo a partir del nombre de un archivo XML por defecto que está dentro de la carpeta del proyecto
     */
    public Servidor() {
        this.activo = true;
        this.pausado = false;
        this.partidasEnCurso = new ArrayList<>();
        this.jugadoresEsperaDuo = new ArrayList<>();
        this.jugadoresEsperaTrio = new ArrayList<>();
        this.jugadoresEsperaCuarteto = new ArrayList<>();
        this.logs = "";
    }
    
    /**
     * Este método lo que hace es que deshace todas las conexiones y puertos que haya definido el servidor
     */
    public void asesinarServidor(){
        try {
            this.serverSocket.close();
            System.out.println("Servidor eliminado con éxito");
        } catch (IOException ex) {
            System.out.println("Error al eliminar el servidor");
        }
    }
    
    /**
     * Esto es para que el servidor se detenga completamente, esté o no en ejecución
     * @return El estado en el que quedó el servidor
     */
    public boolean pararServidor(){
        this.activo = false;
        this.pausado = false;
        return this.activo;
    }
    
    /**
     * Esto habilita el servidor, pero NO lo pone en marcha
     * @return El estado final en el que quedó el servidor
     */
    public boolean activarServidor(){
        this.activo = true;
        return this.activo;
    }
    
    /**
     * Esto es para pausar el servidor, de modo que al remover la pausa el servidor pueda reanudar todo rápidamente
     * @return El estado final en el que quedó el servidor
     */
    public boolean pausarServidor(){
        this.pausado = true;
        return this.pausado;
    }
    
    /**
     * Para remover el estado de pausa del servidor
     * @return El estado final en el que quedó el servidor
     */
    public boolean desPausarServidor(){
        this.pausado = false;
        return this.pausado;
    }
    
    /**
     * Esto es para habilitar el servidor, y hacer que esté en ejecución en un sólo paso
     */
    public void correrServidor(){
        this.activarServidor();
        try {
            //nuevo servidor
            this.serverSocket = new ServerSocket(5000);
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.start();
    }

    /**
     * Para saber si el servidor está o no activado
     * @return True si el servidor está activado, false en el otro caso
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * Para saber si el servidor está o no pausado
     * @return True si el servidor está pausado, false en el otro caso
     */
    public boolean isPausado() {
        return pausado;
    }

    public ArrayList<Partida> getPartidasEnCurso() {
        return partidasEnCurso;
    }

    public ArrayList<Jugador> getJugadoresEsperaDuo() {
        return jugadoresEsperaDuo;
    }

    public ArrayList<Jugador> getJugadoresEsperaTrio() {
        return jugadoresEsperaTrio;
    }

    public ArrayList<Jugador> getJugadoresEsperaCuarteto() {
        return jugadoresEsperaCuarteto;
    }

    public String getLogs() {
        return logs;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
    
    /**
     * Este método es el método que controla el servidor, lo que hace este método es que controla todas las nuevas conexiones
     * desde y hacia el servidor
     */
    @Override
    public void run(){
        try{
            //que esté corriendo mientras el servidor esté activo
            while(this.activo){
                System.out.println("Servidor en espera por una nueva conexión");
                Socket socketNuevo = serverSocket.accept();//consigo el nuevo socket que haya deseado conectarse
                System.out.println("Detectada nueva conexión");
                //ahora las conexiones de entrada y de salida del servidor
                System.out.println("Haciendo nuevas conexiones");
                this.conexionSalida = socketNuevo.getOutputStream();
                this.flujoDeSalida = new ObjectOutputStream(conexionSalida);
                this.conexionEntrada = socketNuevo.getInputStream();
                this.flujoDeEntrada = new ObjectInputStream(conexionEntrada);
                System.out.println("Averiguando Mensaje");
                //ya tengo las conexiones hechas, ahora tengo que ver qué hago con lo que el cliente le envió al servidor
                try{
                    Mensaje mensajeRecibido = (Mensaje)flujoDeEntrada.readObject();//consigo el mensaje enviado (o intento hacerlo)
                    System.out.println("Atendiendo Petición");
                    atenderPeticion(mensajeRecibido, socketNuevo);//hago que el servidor atienda la petición
                }catch(ClassNotFoundException | ClassCastException excep){
                    System.out.println("Ocurrió un error a la hora de averiguar el mensaje enviado");
                }
                //Esto es en caso de que el administrador desee pausar el servidor
                while(this.pausado){
                    try{
                        System.out.println("Servidor en pausa");
                        Thread.sleep(1000);
                    }catch(InterruptedException except){
                        System.out.println("Hubo un error durante la espera");
                    }
                }
            }
        }catch(IOException exception){
            System.out.println("Hubo un problema al intentar conectar, o se eliminó el servidor");
        }
    }
    
    /**
     * Este es el método que se encarga de averiguar qué exactamente traía el mensaje, así como de retornar una respuesta
     * @param mensajeAAtender El mensaje recibido que se desea atender
     * @param socketPeticion El socket del cual se recibió la conexión
     */
    private void atenderPeticion(Mensaje mensajeAAtender, Socket socketPeticion){
        try{
            switch(mensajeAAtender.getTipoDelMensaje()){
                case actualizarTablas:{
                    System.out.println("Se desea actualizar las tablas de cada jugador");
                    mensajeAAtender.setDatoDeRespuesta(this.encontrarPartidaDelJugador((Jugador)mensajeAAtender.getDatoDeSolicitud()));
                    try{
                        this.flujoDeSalida.writeObject(mensajeAAtender);
                        System.out.println("Mensaje enviado de vuelta correctamente");
                    } catch (IOException ex) {
                        Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
                case activado:{
                    System.out.println("Se desea averiguar si el servidor está activo");
                    mensajeAAtender.setDatoDeRespuesta(true);
                    try{
                        this.flujoDeSalida.writeObject(mensajeAAtender);
                        System.out.println("Mensaje enviado de vuelta correctamente");
                    } catch (IOException ex) {
                        Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
                case atacarJugador:{
                    System.out.println("Se desea agregar un daño a un jugador");
                    Ataque ataque = (Ataque)mensajeAAtender.getDatoDeSolicitud();
                    Partida partidaAModificar = this.encontrarPartidaDelJugador(ataque.getBlancoDelAtaque());
                    System.out.println("Se consiguieron correctamente los datos del ataque");
                    if(ataque.getBlancoDelAtaque().getGrafoPropio().isDanhable()){
                        int resultado = partidaAModificar.getJugadores().get(partidaAModificar.getJugadores().indexOf(ataque.getBlancoDelAtaque())).getGrafoPropio().agregarDanhos(ataque.getCoordenadaDeAtaque());
                        switch (resultado) {
                            case 1:
                                System.out.println("Se agregaron los daños correctamente");
                                break;
                            case 0:
                                System.out.println("No se agregaron correctamente los daños");
                                break;
                            case 2:
                                System.out.println("Se agregaron los daños correctamente y pegó en un agujero negro");
                                break;
                            case 3:
                                System.out.println("No se agregaron correctamente los daños pero se pegó en un agujero negro");
                                break;
                            default:
                                break;
                        }
                        mensajeAAtender.setDatoDeRespuesta(resultado);
                    }
                    else{
                        ataque.getBlancoDelAtaque().getGrafoPropio().reducirDanhable();
                        mensajeAAtender.setDatoDeRespuesta(false);
                    }
                    try{
                        this.flujoDeSalida.writeObject(mensajeAAtender);
                        System.out.println("Mensaje enviado de vuelta correctamente");
                    } catch (IOException ex) {
                        Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
                case unirseACola:{
                    System.out.println("Se desea unirse a una cola");
                    ArrayList <Object> datosMensaje= (ArrayList<Object>)mensajeAAtender.getDatoDeSolicitud();
                    Jugador posibleNuevoJugador = (Jugador)datosMensaje.get(0);
                    if(this.encontrarPartidaDelJugador(posibleNuevoJugador) == null){//primero verifico si no estaba en otra partida
                        System.out.println("El jugador no estaba en partida");
                        switch((int)datosMensaje.get(1)){
                            case 2:{
                                this.jugadoresEsperaDuo.add(posibleNuevoJugador);
                                if(this.jugadoresEsperaDuo.size() >= 2){
                                    System.out.println("Hay suficientes jugadores en la cola: " + 2);
                                    ArrayList<Jugador> jugadoresAEmparejar = new ArrayList<>();
                                    for (int i = 0; i < 2; i++) {
                                        Jugador jugadorAEmparejar = this.jugadoresEsperaDuo.remove(0);
                                        jugadorAEmparejar.setNumeroJugador(i);
                                        jugadoresAEmparejar.add(jugadorAEmparejar);
                                    }
                                    boolean resultado = this.partidasEnCurso.add(new Partida(jugadoresAEmparejar));
                                    if(resultado){
                                        this.notificarUsuariosPartida(this.partidasEnCurso.get(this.partidasEnCurso.size()-1));
                                    }
                                    System.out.println("Emparejados");
                                }
                                break;
                            }
                            case 3:{
                                this.jugadoresEsperaTrio.add(posibleNuevoJugador);
                                if(this.jugadoresEsperaTrio.size() >= 3){
                                    System.out.println("Hay suficientes jugadores en la cola: " + 3);
                                    emparejar(3);
                                    System.out.println("Emparejados");
                                }
                                break;
                            }
                            case 4:{
                                this.jugadoresEsperaCuarteto.add(posibleNuevoJugador);
                                if(this.jugadoresEsperaCuarteto.size() >= 4){
                                    System.out.println("Hay suficientes jugadores en la cola: " + 4);
                                    emparejar(4);
                                    System.out.println("Emparejados");
                                }
                                break;
                            }
                        }
                        mensajeAAtender.setDatoDeRespuesta(true);
                    }
                    else{
                        mensajeAAtender.setDatoDeRespuesta(false);
                    }
                    System.out.println("Enviando mensaje de vuelta");
                    try{
                        this.flujoDeSalida.writeObject(mensajeAAtender);
                        System.out.println("Mensaje enviado de vuelta correctamente");
                    } catch (IOException ex) {
                        Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
                case nuevaArma:{
                    System.out.println("Se desea agregar una nueva arma");
                    ArrayList <Object> datosMensaje = (ArrayList<Object>)mensajeAAtender.getDatoDeSolicitud();
                    Partida partidaAModificar = this.encontrarPartidaDelJugador((Jugador)datosMensaje.get(0));
                    Arma armaAAgregar = (Arma)datosMensaje.get(1);
                    System.out.println("Agregando Arma");
                    boolean resultado = partidaAModificar.getJugadores().get(partidaAModificar.getJugadores().indexOf((Jugador)datosMensaje.get(0))).agregarArma(armaAAgregar);
                    if(resultado){
                        System.out.println("Agregado Correcto");
                    }
                    else{
                        System.out.println("Agregado Incorrecto");
                    }
                    mensajeAAtender.setDatoDeRespuesta(resultado);
                    try{
                        this.flujoDeSalida.writeObject(mensajeAAtender);
                        System.out.println("Mensaje enviado de vuelta correctamente");
                    } catch (IOException ex) {
                        Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
                case nuevoElemento:{
                    System.out.println("Se desea agregar un nuevo elemento");
                    ArrayList <Object> datosMensaje = (ArrayList<Object>)mensajeAAtender.getDatoDeSolicitud();
                    Partida partidaAModificar = this.encontrarPartidaDelJugador((Jugador)datosMensaje.get(0));
                    Jugador jugadorAModificar = partidaAModificar.getJugadores().get(partidaAModificar.getJugadores().indexOf((Jugador)datosMensaje.get(0)));
                    if(datosMensaje.get(1) instanceof Comodin){
                        if(jugadorAModificar.getGrafoPropio().buscarTemplo()){
                            if(jugadorAModificar.getGrafoPropio().isDanhable()){
                                Comodin comodinAAplicar = (Comodin)datosMensaje.get(1);
                                jugadorAModificar.getGrafoPropio().setIsDanhable(comodinAAplicar.getGolpesRestantes());
                                mensajeAAtender.setDatoDeRespuesta(true);
                                try{
                                    this.flujoDeSalida.writeObject(mensajeAAtender);
                                    System.out.println("Mensaje enviado de vuelta correctamente");    
                                } catch (IOException ex) {
                                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                return;
                            }
                        }
                        mensajeAAtender.setDatoDeRespuesta(false);
                        try{
                            this.flujoDeSalida.writeObject(mensajeAAtender);
                            System.out.println("Mensaje enviado de vuelta correctamente");
                        } catch (IOException ex) {
                            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        return;
                    }
                    Elemento elementoAAgregar = (Elemento)datosMensaje.get(1);
                    Coordenada posicionConector = (Coordenada)datosMensaje.get(2);
                    if(elementoAAgregar instanceof Fabrica){
                        System.out.println("Fábrica");
                        mensajeAAtender.setDatoDeRespuesta(false);
                    }
                    else{
                        
                        System.out.println("Elemento");
                        jugadorAModificar.getGrafoPropio().agregarNuevoVertice(elementoAAgregar);
                        mensajeAAtender.setDatoDeRespuesta(true);
                    }
                    
                    try{
                        this.flujoDeSalida.writeObject(mensajeAAtender);
                        System.out.println("Mensaje enviado de vuelta correctamente");
                    } catch (IOException ex) {
                        Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
                case enviarMensaje:{
                    System.out.println("Se desea enviar un mensaje de chat");
                    ArrayList <Object> datosMensaje = (ArrayList<Object>)mensajeAAtender.getDatoDeSolicitud();
                    ArrayList <Jugador> jugadoresAEnviarMensaje = (ArrayList<Jugador>)datosMensaje.get(0);
                    for (int i = 0; i < jugadoresAEnviarMensaje.size(); i++) {
                        Jugador get = jugadoresAEnviarMensaje.get(i); 
                        try {
                            OutputStream conexionSalidaSocket = new Socket(get.getIP(),5001).getOutputStream();
                            ObjectOutputStream canalEscritura = new ObjectOutputStream(conexionSalidaSocket);
                            canalEscritura.writeObject(new Mensaje(TipoMensaje.notificarJugadores, null));
                            System.out.println("Mensaje enviado de vuelta correctamente");
                        } catch (IOException ex) {
                            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                }
                case obtenerPuertoServer: {
                     mensajeAAtender.setDatoDeRespuesta(this.contador);
                     this.contador++;
                     try{
                        this.flujoDeSalida.writeUnshared(mensajeAAtender);
                        System.out.println("Mensaje enviado de vuelta correctamente");
                    } catch (IOException ex) {
                        Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
            }
        }catch(ClassCastException exc){
            System.out.println("Ocurrió un error a la hora de descifrar alguno de los datos en una solicitud del tipo: " + mensajeAAtender.getTipoDelMensaje().getRepString());
        }
    }
    
    /**
     * Este método se encarga de emparejar los jugadores en partidas
     * @param tipoEmparejamiento El tamaño de la partida
     * @return Si se logró agregar la partida
     */
    private boolean emparejar(int tipoEmparejamiento){
        ArrayList <Jugador> jugadoresAEmparejar = new ArrayList();
        switch(tipoEmparejamiento){
            case 2:{
                for (int i = 0; i < tipoEmparejamiento; i++) {
                    Jugador jugadorAEmparejar = this.jugadoresEsperaDuo.remove(0);
                    jugadorAEmparejar.setNumeroJugador(i);
                    jugadoresAEmparejar.add(jugadorAEmparejar);
                }
                break;
            }
            case 3:{
                for (int i = 0; i < tipoEmparejamiento; i++) {
                    Jugador jugadorAEmparejar = this.jugadoresEsperaTrio.remove(0);
                    jugadorAEmparejar.setNumeroJugador(i);
                    jugadoresAEmparejar.add(jugadorAEmparejar);
                }
                break;
            }
            case 4:{
                for (int i = 0; i < tipoEmparejamiento; i++) {
                    Jugador jugadorAEmparejar = this.jugadoresEsperaCuarteto.remove(0);
                    jugadorAEmparejar.setNumeroJugador(i);
                    jugadoresAEmparejar.add(jugadorAEmparejar);
                }
                break;
            }
        }
        boolean resultado = this.partidasEnCurso.add(new Partida(jugadoresAEmparejar));
        if(resultado){
            this.notificarUsuariosPartida(this.partidasEnCurso.get(this.partidasEnCurso.size()-1));
        }
        return resultado;
    }
    
    /**
     * Este método busca entre todas las partidas en curso por un determinado jugador, y retorna la partida en la está
     * este método es usado en servidor para que sepa cuál partida debe modificar
     * @param jugadorABuscar El jugador a buscar
     * @return La partida en la que encontró el jugador, o null, si no encontró el jugador
     */
    private Partida encontrarPartidaDelJugador(Jugador jugadorABuscar){
        for (int i = 0; i < partidasEnCurso.size(); i++) {
            Partida getPartida = partidasEnCurso.get(i);
            for (int j = 0; j < getPartida.getJugadores().size(); j++) {
                Jugador getJugador = getPartida.getJugadores().get(j);
                if(jugadorABuscar.equals(getJugador)){
                    return getPartida;
                }
            }
        }
        return null;
    }
    
    /**
     * Este método lo que hace es notificar a los jugadores de una partida que hubo algún cambio
     * @param partidaANotificar La partida a ser notificada
     */
    public void notificarUsuariosPartida(Partida partidaANotificar){
        for (int i = 0; i < partidaANotificar.getJugadores().size(); i++) {
            Jugador get = partidaANotificar.getJugadores().get(i);
            try {
                OutputStream conexionSalidaSocket = new Socket(get.getIP(),5000).getOutputStream();
                ObjectOutputStream canalEscritura = new ObjectOutputStream(conexionSalidaSocket);
                canalEscritura.writeObject(new Mensaje(TipoMensaje.notificarJugadores, null));
                System.out.println("Mensaje enviado de vuelta correctamente");
            } catch (IOException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public String toString() {
        return "Servidor: \n" + "Activo: " + activo + ", pausado: " + pausado;
    }
}