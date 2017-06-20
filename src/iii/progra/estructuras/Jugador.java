/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iii.progra.estructuras;

import java.util.ArrayList;

/**
 *
 * @author Esteban
 */
public class Jugador {

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
     * Para el juego
     */
    private int dineroJugador,cantidadAcero;
    private final ArrayList<Arma> armasJugador;//las armas que posee el jugador
    private final String nombreJugador;//el nombre del jugador es el que lo representará en todas las operaciones dentro del juego
    //PS: con el tiempo podría ajustarse y usar la IP, pero mejor no complicar esto por el momento

    public Jugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
        this.armasJugador = new ArrayList<>();//sin armas al inicio
        this.cantidadAcero = 0;//sin acero al inicio
        this.puertoServidor = 5000;//puerto 5000 por defecto
        this.numeroJugador = -1;//-1 para "no emparejado"
        this.dineroJugador = 4000;//inicia con $4000
        this.grafoPropio = new GrafoObjetos();
    }
    
    public Jugador(String nombreJugador, String direccionServidor) {
        this.nombreJugador = nombreJugador;
        this.direccionServidor = direccionServidor;
        this.armasJugador = new ArrayList<>();//sin armas al inicio
        this.cantidadAcero = 0;//sin acero al inicio
        this.puertoServidor = 5000;//puerto 5000 por defecto
        this.numeroJugador = -1;//-1 para "no emparejado"
        this.dineroJugador = 4000;//inicia con $4000
        this.grafoPropio = new GrafoObjetos();
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
    
    
}
