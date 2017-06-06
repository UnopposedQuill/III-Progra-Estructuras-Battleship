/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tercera.progra;

/**
 * Esta es la clase de la arma, donde sólo se tendrán 4 tipos, definidos como enteros, aunque podría
 * hacer que tuvieran un enum
 * @author esteban
 */
public class Arma implements java.io.Serializable{
    private int tipoArma;

    public Arma(int tipoArma) {
        this.tipoArma = tipoArma;
    }
}