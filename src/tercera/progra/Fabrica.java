/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tercera.progra;

/**
 * Esta es la subclase de elemento donde la orientación importa, como las minas, armerías, templo y mercado
 * @author esteban
 */
public abstract class Fabrica extends Elemento{
    
    protected Orientacion orientacionFabrica;

    protected Coordenada coordenadaExtra;
    
    public Fabrica(Orientacion orientacionFabrica, int posicionX, int posicionY) {
        super(posicionX, posicionY);
        this.orientacionFabrica = orientacionFabrica;
        
        if (orientacionFabrica == Orientacion.Horizontal)
            coordenadaExtra = new Coordenada(posicionX+1, posicionY);
        else
            coordenadaExtra = new Coordenada(posicionX, posicionY+1);
    }

    public Orientacion getOrientacionFabrica() {
        return orientacionFabrica;
    }

    public Coordenada getCoordenadaExtra() {
        return coordenadaExtra;
    }
    
    
}