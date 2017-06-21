/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iii.progra.estructuras;

import gui.JFrameGuerraIslas;
import java.io.*;
import java.util.*;
import java.net.*;
import java.util.logging.*;

/**
 *
 * @author Esteban
 */
public class Jugador extends Thread implements java.io.Serializable{//para poder enviarlo al servidor

    /**
     * El grafo de cada jugador
     */
    private final GrafoObjetos grafoPropio; 
    
    /**
     * Cosas administrativas del servidor
     */
    private String direccionServidor = "localhost";//localhost por defecto
    private int puertoServidor, numeroJugador;
    
    /**
     * Para permitir que el servidor le envíe cosas al cliente
     */
    private String IP;
    
    /**
     * Para el juego
     */
    private int dineroJugador,cantidadAcero;
    private final ArrayList<Arma> armasJugador;//las armas que posee el jugador
    private final String nombreJugador;//el nombre del jugador es el que lo representará en todas las operaciones dentro del juego
    //PS: con el tiempo podría ajustarse y usar la IP, pero mejor no complicar esto por el momento

    private JFrameGuerraIslas ventanaPropia;
    
    public Jugador(String nombreJugador) throws UnknownHostException{
        this.nombreJugador = nombreJugador;
        this.armasJugador = new ArrayList<>();//sin armas al inicio
        this.cantidadAcero = 0;//sin acero al inicio
        this.puertoServidor = 5000;//puerto 5000 por defecto
        this.numeroJugador = -1;//-1 para "no emparejado"
        this.dineroJugador = 4000;//inicia con $4000
        this.grafoPropio = new GrafoObjetos();
        this.IP = InetAddress.getLocalHost().getHostAddress();
    }
    
    public Jugador(String nombreJugador, String direccionServidor, int puertoServidor) throws UnknownHostException{
        this.nombreJugador = nombreJugador;
        this.direccionServidor = direccionServidor;
        this.armasJugador = new ArrayList<>();//sin armas al inicio
        this.cantidadAcero = 0;//sin acero al inicio
        this.puertoServidor = puertoServidor;//puerto 5000 por defecto
        this.numeroJugador = -1;//-1 para "no emparejado"
        this.dineroJugador = 4000;//inicia con $4000
        this.grafoPropio = new GrafoObjetos();
        this.IP = InetAddress.getLocalHost().getHostAddress();
    }

    public String getIP() {
        return IP;
    }

    public GrafoObjetos getGrafoPropio() {
        return grafoPropio;
    }

    public String getDireccionServidor() {
        return direccionServidor;
    }

    public int getPuertoServidor() {
        return puertoServidor;
    }

    public ArrayList<Arma> getArmasJugador() {
        return armasJugador;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }
    
    public int getNumeroJugador() {
        return numeroJugador;
    }

    public void setNumeroJugador(int numeroJugador) {
        this.numeroJugador = numeroJugador;
    }

    public int getDineroJugador() {
        return dineroJugador;
    }

    public void setDineroJugador(int dineroJugador) {
        this.dineroJugador = dineroJugador;
    }

    public int getCantidadAcero() {
        return cantidadAcero;
    }

    public void setCantidadAcero(int cantidadAcero) {
        this.cantidadAcero = cantidadAcero;
    }

    public boolean agregarArma(Arma armaAAgregar){
        return this.armasJugador.add(armaAAgregar);
    }

    public void setDireccionServidor(String direccionServidor) {
        this.direccionServidor = direccionServidor;
    }

    public void setPuertoServidor(int puertoServidor) {
        this.puertoServidor = puertoServidor;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.nombreJugador);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() == obj.getClass()) {//si estoy comparando jugadores
            final Jugador other = (Jugador) obj;
            return this.nombreJugador.equals(other.nombreJugador);
        }
        if (obj.getClass() == String.class){//si estoy buscando un jugador por su nombre
            return this.nombreJugador.equals(obj);
        }
        return false;
    }
    
    public Mensaje realizarPeticion(Mensaje aEnviar){
        for (int i = 0; i < 3; i++) {
            try{
                System.out.println("Conectándose al servidor especificado");
                Socket socketConexion = new Socket(this.direccionServidor, this.puertoServidor);
                System.out.println("Estableciendo conexiones con el servidor");
                InputStream conexionEntrada = socketConexion.getInputStream();
                ObjectInputStream flujoDeEntrada = new ObjectInputStream(conexionEntrada);
                OutputStream conexionSalida = socketConexion.getOutputStream();
                ObjectOutputStream flujoDeSalida = new ObjectOutputStream(conexionSalida);
                System.out.println("Enviando mensaje");
                flujoDeSalida.writeObject(aEnviar);
                try{
                    System.out.println("Recibiendo Mensaje");
                    return (Mensaje) flujoDeEntrada.readObject();
                }catch(ClassNotFoundException | ClassCastException exc){
                    System.out.println("Ocurrió un error a la hora de averiguar el mensaje retornado");
                    return null;
                }
            }catch(IOException exc){
                Logger.getLogger(Jugador.class.getName()).log(Level.SEVERE, null, exc);
                System.out.println("Algo salió mal al intentar conectarse al servidor: " + this.direccionServidor + " " + this.puertoServidor);
            }
        }
        System.out.println("Se rindió al no poder conectarse al servidor");
        return null;
    }
    
    @Override
    public void run(){
        while(true){
            try {
                ServerSocket serverSocketActualizaciones = new ServerSocket(5001);//puerto auxiliar para recibir
                System.out.println("Esperando mensajes del servidor");
                Socket recibirDatos = serverSocketActualizaciones.accept();//recibo datos
                System.out.println("Datos llegados");
                InputStream conexionEntrada = recibirDatos.getInputStream();
                ObjectInputStream flujoDeEntrada = new ObjectInputStream(conexionEntrada);
                System.out.println("Averiguando mensaje");
                Mensaje mensajeRecibido = (Mensaje)flujoDeEntrada.readObject();
                switch(mensajeRecibido.getTipoDelMensaje()){
                    case notificarJugadores:{//el servidor acaba de notificar cambios
                        //tiene que actualizarse todo
                        System.out.println("Actualizando tablero");
                        this.ventanaPropia.actualizarTablero(this.realizarPeticion(new Mensaje(TipoMensaje.actualizarTablas, this.nombreJugador)));
                        //this.realizarPeticion(new Mensaje(TipoMensaje.actualizarTablas, null));
                    }
                    case enviarMensaje:{
                        System.out.println("Nuevo mensaje de chat");
                        this.ventanaPropia.mostrarMensajeEnChat((String)mensajeRecibido.getDatoDeSolicitud());
                    }
                }
                flujoDeEntrada.close();
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("No se captó nada");
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
