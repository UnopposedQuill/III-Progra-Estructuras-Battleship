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
public class Mina extends Fabrica{

    /**
     * El hilo que va creando acero
     */
    private HiloMina hiloMina;
    
    public Mina(boolean orientacion, int posicionX, int posicionY, Jugador jugadorAModificar){
        super(orientacion, posicionX, posicionY);
        this.hiloMina = new HiloMina(jugadorAModificar);
    }

    public Mina(boolean orientacion, int posicionX, int posicionY, Jugador jugadorAModificar, int tiempoProduccion, int cantidadProduccion){
        super(orientacion, posicionX, posicionY);
        this.hiloMina = hiloMina = new HiloMina(tiempoProduccion, cantidadProduccion, jugadorAModificar);
        this.hiloMina.start();//arranca la producción de acero
    }
}
