/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iii.progra.estructuras;

/**
 * Esta es la clase de la arma, donde sólo se tendrán 4 tipos, definidos como enteros, aunque podría
 * hacer que tuvieran un enum
 * @author esteban
 */
public class Arma implements java.io.Serializable{
    private final int tipoArma;    

    public Arma(int tipoArma) {
        this.tipoArma = tipoArma;
    }
    
    public int getTipoArma(){
        return this.tipoArma;
    }
}