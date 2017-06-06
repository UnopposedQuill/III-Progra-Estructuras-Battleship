/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tercera.progra;

/**
 * Los elementos serán los tipos de elementos que habrán en el grafo del mundo, estos son abstractos para
 * rendir código, los que importan son los que heredan de este
 * @author esteban
 */
public abstract class Elemento implements java.io.Serializable{

    /**
     * La posicion del objeto en el grafo
     */
    protected int posicionGrafo;
    
    protected Coordenada coordenadas;
    
    public Elemento(int posicionX, int posicionY) {
        posicionGrafo = 0;
        coordenadas = new Coordenada(posicionX, posicionY);
    }
    
    public Elemento(int posicionX, int posicionY, int posicionGrafo) {
        this.coordenadas = new Coordenada(posicionX, posicionY);
        this.posicionGrafo = posicionGrafo;
    }
    
    public int getPosicionX() {
        return this.coordenadas.getX();
    }

    public int getPosicionY() {
        return this.coordenadas.getY();
    }

    public int getPosicionGrafo() {
        return posicionGrafo;
    }

    public void setPosicionGrafo(int posicionGrafo) {
        this.posicionGrafo = posicionGrafo;
    }

    public Coordenada getCoordenadas() {
        return coordenadas;
    }
    
    
}
