/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tercera.progra;

import java.util.ArrayList;

/**
 *
 * @author esteban
 */
public class Ataque {
    private final ArrayList<Coordenada> coordenadaDeAtaque;
    private final Jugador blancoDelAtaque;

    public Ataque(ArrayList<Coordenada> coordenadaDeAtaque, Jugador blancoDelAtaque) {
        this.coordenadaDeAtaque = coordenadaDeAtaque;
        this.blancoDelAtaque = blancoDelAtaque;
    }

    public ArrayList<Coordenada> getCoordenadaDeAtaque() {
        return coordenadaDeAtaque;
    }

    public Jugador getBlancoDelAtaque() {
        return blancoDelAtaque;
    }
    
    
}
