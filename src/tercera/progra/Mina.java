/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tercera.progra;

/**
 * Esta es la clase de las minas del mundo
 * @author esteban
 */
public class Mina extends Fabrica{

    private int cantidadProduccion;
    private int cadenciaProduccion;
    private HiloMina hiloPropio;

    /**
     * Este constructor inicializa la mina con una cierta cantidad de producción y cadencia de producción,
     * además de su localización en la tabla
     * @param cantidadProduccion La cantidad de acero que producirá pasado un lapso de tiempo
     * @param cadenciaProduccion El lapso de tiempo que le toma producir la cantidad de producción en segundos
     * @param orientacionFabrica La orientación que tendrá la fábrica dentro de la tabla
     * @param posicionX La posición x donde estará colocado en la tabla
     * @param posicionY La posición y donde estará colocado en la tabla
     */
    public Mina(int cantidadProduccion, int cadenciaProduccion, Orientacion orientacionFabrica, int posicionX, int posicionY) {
        super(orientacionFabrica, posicionX, posicionY);
        this.cantidadProduccion = cantidadProduccion;
        this.cadenciaProduccion = cadenciaProduccion;
        this.hiloPropio = new HiloMina(this);
    }

    /**
     * Este constructor inicializa la mina con una cierta cantidad de producción y cadencia de producción
     * por defecto además de su localización en la tabla     
     * @param orientacionFabrica La orientación que tendrá la fábrica dentro de la tabla
     * @param posicionX La posición x donde estará colocado en la tabla
     * @param posicionY La posición y donde estará colocado en la tabla
     */
    public Mina(Orientacion orientacionFabrica, int posicionX, int posicionY) {
        super(orientacionFabrica, posicionX, posicionY);
        this.cantidadProduccion = 50;
        this.cadenciaProduccion = 60;
    }

    public int getCantidadProduccion() {
        return cantidadProduccion;
    }

    public void setCantidadProduccion(int cantidadProduccion) {
        this.cantidadProduccion = cantidadProduccion;
    }

    public int getCadenciaProduccion() {
        return cadenciaProduccion;
    }

    public void setCadenciaProduccion(int cadenciaProduccion) {
        this.cadenciaProduccion = cadenciaProduccion;
    }
}
