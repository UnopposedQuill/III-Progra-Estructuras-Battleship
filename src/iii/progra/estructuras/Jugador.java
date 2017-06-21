/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iii.progra.estructuras;

import java.util.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

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
}
