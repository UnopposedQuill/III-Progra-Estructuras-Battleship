/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tercera.progra;

import java.util.*;

/**
 *
 * @author esteban
 */
public class Partida implements java.io.Serializable{
    
    private ArrayList<Jugador> jugadores;
    private int turnoJugador;

    public Partida(ArrayList<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public int getTurnoJugador() {
        return turnoJugador;
    }

    public void siguienteTurno(){
        this.turnoJugador++;
        if(this.turnoJugador >= this.jugadores.size()){
            this.turnoJugador = 0;
        }
    }
}
