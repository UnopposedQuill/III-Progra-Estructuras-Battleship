package tercera.progra;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author esteban
 */
public class Templo extends Fabrica{

    private int tiempo;
    private Comodin comodin;
    
    public Templo(Orientacion orientacionFabrica, int posicionX, int posicionY) {
        super(orientacionFabrica, posicionX, posicionY);
        comodin = new Comodin();
    }
    
    public boolean activarComodin(){
        if(tiempo != 0){
            return false;
        }
        if(this.comodin == null){
            return false;
        }
        this.comodin = new Comodin();
        this.tiempo = 300;
        return true;
    }

    public int getTiempo() {
        return tiempo;
    }

    public Comodin getComodin() {
        return comodin;
    }
}