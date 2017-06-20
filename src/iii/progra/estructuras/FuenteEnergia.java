/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iii.progra.estructuras;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Esteban
 */
public class FuenteEnergia extends Elemento{

    /**
     * Esta cantidad de energía por ahora no se usa en nada, pero se planea que una fuente sólo pueda mantener una X cantidad de elementos
     */
    private final int cantidadEnergia;

    /**
     * Las 3 coordenadas restantes dentro del grafo de la fuente de energía.
     * La primera posición será la esquina superior derecha.
     * La segunda posición será la esquina inferior izquierda.
     * La tercera posición será la esquina inferior derecha.
     */
    private final Coordenada[] coordenadasRestantes;
    
    public FuenteEnergia(int cantidadEnergia, int posicionX, int posicionY) {
        super(posicionX, posicionY);
        this.cantidadEnergia = cantidadEnergia;
        this.coordenadasRestantes = new Coordenada[3];
        this.coordenadasRestantes[0] = new Coordenada(posicionX, posicionY+1);
        this.coordenadasRestantes[0] = new Coordenada(posicionX+1, posicionY);
        this.coordenadasRestantes[0] = new Coordenada(posicionX+1, posicionY+1);
    }

    public int getCantidadEnergia() {
        return cantidadEnergia;
    }

    /**
     * Retorna las coordenadas restantes que no son la coordenada básica obligatoria
     * @return Un arreglo de 3 posiciones que representan las otras 3 coordenadas
     * La primera posición será la esquina superior derecha.
     * La segunda posición será la esquina inferior izquierda.
     * La tercera posición será la esquina inferior derecha.
     */
    public Coordenada[] getCoordenadasRestantes() {
        return coordenadasRestantes;
    }
}
