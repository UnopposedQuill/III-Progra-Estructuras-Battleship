/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iii.progra.estructuras;

/**
 * Esta clase guarda una conexión con algún otro elemento
 * @author Esteban
 */
public class Conexion {

    private final Elemento elementoConectado;
    private final int distanciaX,distanciaY;

    /**
     * Construye una nueva conexión utilizando el elemento pasado por parámetro
     * @param elementoConectado El elemento al que apunta la conexión
     * @param distanciaX La distancia en el eje X
     * @param distanciaY La distancia en el eje Y
     */
    public Conexion(Elemento elementoConectado, int distanciaX, int distanciaY) {
        this.elementoConectado = elementoConectado;
        this.distanciaX = distanciaX;
        this.distanciaY = distanciaY;
    }

    /**
     * Consigue el elemento con el cual está conectada la conexión
     * @return El elemento que está dentro de la conexión
     */
    public Elemento getElementoConectado() {
        return elementoConectado;
    }

    /**
     * La distancia con el eje X
     * @return Un entero que representa la distancia en el eje X del elemento conectado
     */
    public int getDistanciaX() {
        return distanciaX;
    }

    /**
     * La distancia con el eje X
     * @return Un entero que representa la distancia en el eje X del elemento conectado
     */
    public int getDistanciaY() {
        return distanciaY;
    }    
}
