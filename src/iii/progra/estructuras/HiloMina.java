/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iii.progra.estructuras;

/**
 * Este hilo es el que hace el funcionamiento interno de la creación de acero de una mina
 * @author Esteban
 */
public class HiloMina extends Thread{
    
    private int tiempoDeProduccion;//tiempo que dura haciendo la cantidad de acero que crea
    private int cantidadProduccion;//cuánto acero crea cada vez que concluye el tiempoDeProduccion
    private boolean isActive;//variable que se revisará con el tiempo para saber cuándo detener el hilo, esto es administrativo
    
    private final Jugador jugadorAModificar;//a cuál jugador le dará el acero

    public HiloMina(Jugador jugadorAModificar) {
        this.jugadorAModificar = jugadorAModificar;
        this.cantidadProduccion = 50;//50 KG de acero
        this.tiempoDeProduccion = 60;//producidos cada 60 segundos
    }

    public HiloMina(int tiempoDeProduccion, int cantidadProduccion, Jugador jugadorAModificar) {
        this.tiempoDeProduccion = tiempoDeProduccion;
        this.cantidadProduccion = cantidadProduccion;
        this.jugadorAModificar = jugadorAModificar;
    }

    public int getTiempoDeProduccion() {
        return tiempoDeProduccion;
    }

    public void setTiempoDeProduccion(int tiempoDeProduccion) {
        this.tiempoDeProduccion = tiempoDeProduccion;
    }

    public int getCantidadProduccion() {
        return cantidadProduccion;
    }

    public void setCantidadProduccion(int cantidadProduccion) {
        this.cantidadProduccion = cantidadProduccion;
    }

    public Jugador getJugadorAModificar() {
        return jugadorAModificar;
    }
    
    @Override
    public void run(){
        while(this.isActive){
            this.jugadorAModificar.setCantidadAcero(this.jugadorAModificar.getCantidadAcero() + this.cantidadProduccion);
            try{
                sleep(this.tiempoDeProduccion);
            }catch(InterruptedException ex){}
        }
    }
}
