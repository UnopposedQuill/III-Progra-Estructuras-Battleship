/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iii.progra.estructuras;

//import gui.TipoFabrica; //para poder dibujar bien una matriz de los componentes visuales necesitados
import java.util.*;


/**
 *
 * @author Esteban
 */
public class GrafoObjetos implements java.io.Serializable{
    
    /**
     * Este entero dirá si es dañable, si es mayor a 0 es indañable, se va reduciendo por 1 con el tiempo
     */
    private int isDanhable;

    /**
     * En este ArrayList se van a guardar temporalmente los daños recibidos hasta que se procesen
     */
    private ArrayList<Coordenada> danhos;
    
    /**
     * En este Arraylist se guardarán temporalmente los componentes que van a explotar
     */
    private ArrayList<Elemento> componentesExplotar;
}
