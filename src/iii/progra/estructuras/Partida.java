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
public class Partida implements java.io.Serializable{

    /**
     * Los jugadores que están jugando la partida
     */
    private ArrayList <Jugador> jugadoresPartida;
    
    /**
     * Cada uno tiene un turno en el cual puede atacar a otros
     */
    private int turno;

    public Partida() {
        this.jugadoresPartida = new ArrayList<>();
        this.turno = -1;
    }

    public boolean iniciarPartida(){
        if(this.jugadoresPartida.size() >= 2){//si hay menos de 2 jugadores no arrancará la partida
            return false;//retorna que no la arrancó
        }
        this.turno = 0;//la arranca
        return true;
    }

    /**
     * Esta función lo que hace es que si el turno es diferente a -1 (la partida está iniciada)
     * entonces hace que el turno sea del siguiente jugador
     */
    public void nextTurno(){
        if(this.turno != -1){
            if(this.turno++ == this.jugadoresPartida.size()-1){//si es el turno del último jugador entonces debo refrescar el turno
                this.turno = 0;
            }
        }
    }
    
    public ArrayList<Jugador> getJugadoresPartida() {
        return jugadoresPartida;
    }

    public int getTurno() {
        return turno;
    }
    
    public Jugador getJugador(String nombreJugador){
        for (int i = 0; i < jugadoresPartida.size(); i++) {
            Jugador get = jugadoresPartida.get(i);
            if(get.getNombreJugador().equals(nombreJugador)){
                return get;
            }
        }
        return null;
    }
    
    public boolean agregarJugadores(ArrayList<Jugador> jugadores){
        if(turno != -1){//la partida ya habia sido iniciada
            return false;
        }
        return this.jugadoresPartida.addAll(jugadores);
    }
}
