/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tercera.progra;

import Gui.JFrameGuerraMundos;
import java.io.*;
import java.util.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 * Esta es la estructura que controlará los grafos, así como todas las acciones del juego
 * @author esteban
 */
public class Jugador extends Thread implements Serializable{
    
    private final String nombreJugador;
    /*
    private OutputStream conexionSalida;
    private ObjectOutputStream flujoDeSalida;
    private InputStream conexionEntrada;
    private ObjectInputStream flujoDeEntrada;
    */
    private String IP;
    private GrafoObjetos grafoPropio;
    private int dineroJugador;
    private int aceroJugador;
    private int numeroJugador;
    private int puertoServer;
    private String huesped = "localhost";
    private int puerto = 5000;
    private final ArrayList<Arma> armasJugador = new ArrayList<>();
    private JFrameGuerraMundos ventanaPropia;

    public Jugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
        this.IP = null;
    }
    
    public Jugador(String nombreJugador, GrafoObjetos grafoPropio, String huesped,int puertoServer) {
        this.nombreJugador = nombreJugador;
        this.grafoPropio = grafoPropio;
        this.aceroJugador = 0;
        this.dineroJugador = 4000;
        this.numeroJugador = -1;
        this.huesped = huesped;
        this.puertoServer = puertoServer;
        try { 
            this.IP = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Jugador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public GrafoObjetos getGrafoPropio() {
        return grafoPropio;
    }

    public void setGrafoPropio(GrafoObjetos grafoPropio) {
        this.grafoPropio = grafoPropio;
    }

    public int getDineroJugador() {
        return dineroJugador;
    }

    public void setDineroJugador(int dineroJugador) {
        this.dineroJugador = dineroJugador;
    }

    public int getAceroJugador() {
        return aceroJugador;
    }

    public void setAceroJugador(int aceroJugador) {
        this.aceroJugador = aceroJugador;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public ArrayList<Arma> getArmasJugador() {
        return armasJugador;
    }

    public boolean agregarArma(Arma armaAAgregar){
        return this.armasJugador.add(armaAAgregar);
    }
    
    public int getNumeroJugador() {
        return numeroJugador;
    }

    public void setNumeroJugador(int numeroJugador) {
        this.numeroJugador = numeroJugador;
    }

    public String getHuesped() {
        return huesped;
    }

    public String getIP() {
        return IP;
    }

    public void setHuesped(String huesped) {
        this.huesped = huesped;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.nombreJugador);
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
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Jugador other = (Jugador) obj;
        return !Objects.equals(this.IP, other.IP);
    }

    /**
     * El run del hilo va a ser el que actualiza qué ha recibido el Jugador
     */
    @Override
    public void run() {
        Mensaje recibido = (Mensaje)realizarPeticion(new Mensaje(TipoMensaje.obtenerPuertoServer, null));
        this.puertoServer = 2000 + (int)recibido.getDatoDeRespuesta();
        while(true){
            try {
                ServerSocket serverSocketActualizaciones = new ServerSocket(puertoServer);
                Socket recibirDatos = serverSocketActualizaciones.accept();//recibo datos
                InputStream conexionEntrada = recibirDatos.getInputStream();
                ObjectInputStream flujoDeEntrada = new ObjectInputStream(conexionEntrada);
                
                Mensaje mensajeRecibido = (Mensaje)flujoDeEntrada.readObject();
                switch(mensajeRecibido.getTipoDelMensaje()){
                    case notificarJugadores:{//el servidor acaba de notificar cambios
                        //tiene que actualizarse todo
                        this.realizarPeticion(new Mensaje(TipoMensaje.actualizarTablas, null));
                    }
                    case intercambio:{
                        this.ventanaPropia.actualizarTablero(this.realizarPeticion(new Mensaje(TipoMensaje.actualizarTablas, this)));
                    }
                    case enviarMensaje:{
                        this.ventanaPropia.mostrarMensajeEnChat((String)mensajeRecibido.getDatoDeSolicitud());
                    }
                }
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("No se captó nada");
            }
        }
    }
    
    public Mensaje realizarPeticion(Mensaje aEnviar){
        for (int i = 0; i < 3; i++) {
            try{
                System.out.println("Conectándose al servidor especificado");
                Socket socketConexion = new Socket(this.huesped, this.puerto);
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
                System.out.println("Algo salió mal al intentar conectarse al servidor: " + this.huesped + " " + this.puerto);
            }
        }
        System.out.println("Se rindió al no poder conectarse al servidor");
        return null;
    }
}
