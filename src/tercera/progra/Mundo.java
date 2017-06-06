/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tercera.progra;

/**
 *
 * @author esteban
 */
public class Mundo extends Elemento{
    Coordenada coordenada2;
    Coordenada coordenada3;
    Coordenada coordenada4;
    public Mundo(int posicionX, int posicionY) {
        super(posicionX, posicionY);
        coordenada2 = new Coordenada(posicionX+1, posicionY);
        coordenada3 = new Coordenada(posicionX, posicionY+1);
        coordenada4 = new Coordenada(posicionX+1, posicionY+1);
    }

    public Coordenada getCoordenada2() {
        return coordenada2;
    }

    public Coordenada getCoordenada3() {
        return coordenada3;
    }

    public Coordenada getCoordenada4() {
        return coordenada4;
    }
    
}
