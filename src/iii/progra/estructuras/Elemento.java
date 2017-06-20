/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iii.progra.estructuras;

import java.util.*;

/**
 * Los elementos serán los tipos de elementos que habrán en el grafo del mundo, estos son abstractos para
 * rendir código, los que importan son los que heredan de este
 * @author Esteban
 */
public abstract class Elemento implements java.io.Serializable{
 
    
    /**
     * La coordenada del elemento dentro de la matriz
     */
    protected Coordenada coordenada;
    
    /**
     * Las conexiones del elemento dentro del grafo
     */
    protected ArrayList<Conexion> conexiones;
    
    AristaGrafo x;
    /**
     * Crea un nuevo elemento a partir de una coordenada X y Y
     * @param posicionX La coordenada en el eje X del elemento
     * @param posicionY La coordenada en el eje Y del elemento
     */
    public Elemento(int posicionX, int posicionY) {
        this.coordenada = new Coordenada(posicionX, posicionY);
        this.conexiones = new ArrayList<>();
    }
    
    public int getPosicionX() {
        return this.coordenada.getX();
    }

    public int getPosicionY() {
        return this.coordenada.getY();
    }

    /**
     * Consigue la coordenada básica de donde está insertado en la matriz
     * @return Una coordenada que representa un X,Y de donde estaría el objeto en la matriz
     */
    public Coordenada getCoordenadas() {
        return coordenada;
    }
    
    /**
     * Consigue todas las conexiones del elemento con los otros elementos del grafo
     * @return Un ArrayList con todas las conexiones del elemento con los demás elementos del grafo
     */
    public ArrayList<Conexion> getConexiones() {
        return conexiones;
    }
    
    /**
     * Un objeto equivale a otro si su coordenada básica es la misma
     * @param obj El objeto con el cuál compararlo
     * @return True si sus coordendadas básicas son equivalentes
     */
    @Override
    public boolean equals(Object obj){
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() == obj.getClass()) {
            final Elemento other = (Elemento) obj;
            return this.coordenada.equals(other.coordenada);
        }
        if (obj.getClass() == Coordenada.class){
            return this.coordenada.equals(obj);
        }
        return false;
    }
    
    @Override
    public int hashCode(){
        return this.coordenada.hashCode();
    }
}
