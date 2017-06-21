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
public class Ataque {
    
    private String blancoDelAtaque;
    private ArrayList<Coordenada> coordenadaDeAtaque;
    
    public Ataque(ArrayList<Coordenada> coordenadaDeAtaque, String blancoDelAtaque) {
        this.coordenadaDeAtaque = coordenadaDeAtaque;
        this.blancoDelAtaque = blancoDelAtaque;
    }

    public ArrayList<Coordenada> getCoordenadaDeAtaque() {
        return coordenadaDeAtaque;
    }

    public String getBlancoDelAtaque() {
        return blancoDelAtaque;
    }
    
}
