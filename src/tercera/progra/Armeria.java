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
public class Armeria extends Fabrica{

    public Armeria(Orientacion orientacionFabrica, int posicionX, int posicionY) {
        super(orientacionFabrica, posicionX, posicionY);
    }
    
    public Arma crearArma(int tipoArma){
        return new Arma(tipoArma);
    }
}