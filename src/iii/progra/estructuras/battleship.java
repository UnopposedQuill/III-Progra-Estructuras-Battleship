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
public class battleship {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        GrafoObjetos o = new GrafoObjetos();
        o.agregarVertice(new Conector(0, 0));
        o.agregarVertice(new FuenteEnergia(25, 0, 0));
        System.out.println(o.poseeFuenteDeEnergia());
    }
}
