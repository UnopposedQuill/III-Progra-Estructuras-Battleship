/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iii.progra.estructuras;

/**
 *
 * @author Esteban
 */
public abstract class Fabrica extends Elemento{
    
    /**
     * La orientación en la que está colocada la fábrica, si es true, entonces la fábrica está de forma horizontal
     */
    protected boolean orientacion;
    
    protected Coordenada coordenadaAuxiliar;
    
    public Fabrica(boolean orientacion, int posicionX, int posicionY) {
        super(posicionX, posicionY);
        this.orientacion = orientacion;
        //dependiendo de la orientación recibida tomará una u otra forma
        if(this.orientacion)this.coordenadaAuxiliar = new Coordenada(posicionX, posicionY+1);
        else this.coordenadaAuxiliar = new Coordenada(posicionX, posicionY+1);
    }

    public boolean getOrientacion() {
        return orientacion;
    }

    public Coordenada getCoordenadaAuxiliar() {
        return coordenadaAuxiliar;
    }
    
    
}
