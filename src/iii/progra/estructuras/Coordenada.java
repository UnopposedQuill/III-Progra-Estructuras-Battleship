/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iii.progra.estructuras;

/**
 * Este objeto representa 1 punto en una matriz, la matriz del juego
 * @author Esteban
 */
public class Coordenada {
    
    //coordenadas X,Y de la coordenada
    private final int x;
    private final int y;

    /**
     * Este constructor inicializa el punto usando una dirección X y una dirección Y especificada
     * @param x La dirección X del punto
     * @param y La dirección Y del punto
     */
    public Coordenada(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Retorna un entero que representa la dirección X de la coordenada
     * @return Un entero
     */
    public int getX() {
        return x;
    }

    /**
     * Retorna un entero que representa la dirección Y de la coordenada
     * @return Un entero
     */
    public int getY() {
        return y;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.x;
        hash = 53 * hash + this.y;
        return hash;
    }

    /**
     * Este método averigua si un punto es equivalente a algún otro,
     * Un Punto es equivalente si tanto su coordenada X como su coordenada Y son equivalentes
     * @param obj El otro punto con el cuál se va a comparar
     * @return True si son equivalentes, False en el otro caso
     */
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
        final Coordenada other = (Coordenada) obj;
        if (this.x != other.x) {
            return false;
        }
        return this.y == other.y;
    }
}
