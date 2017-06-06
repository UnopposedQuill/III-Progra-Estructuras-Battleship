/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tercera.progra;

/**
 *
 * @author Esteban
 */
public class TerceraProgra {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GrafoObjetos grafo = new GrafoObjetos(225);
        
        System.out.println(grafo.agregarNuevoVertice(new Templo(Orientacion.Horizontal, 0, 0)));
        System.out.println(grafo.agregarNuevoVertice(new Conector(0, 1)));
        System.out.println(grafo.agregarNuevoVertice(new Conector(0, 2)));
        System.out.println(grafo.agregarNuevoVertice(new Armeria(Orientacion.Horizontal, 0, 3)));
        
        System.out.println(grafo.agregarNuevaConexion(grafo.obtenerElementoIndice(1), grafo.obtenerElementoIndice(0), 99));
        System.out.println(grafo.agregarNuevaConexion(grafo.obtenerElementoIndice(1), grafo.obtenerElementoIndice(3), 99));
        System.out.println(grafo.agregarNuevaConexion(grafo.obtenerElementoIndice(2), grafo.obtenerElementoIndice(3), 99));
        grafo.RevisarConexiones(grafo.obtenerElementoIndice(1));
        System.out.println("Mismo tipo");
        grafo.visitarAdyacentesMismoTipo(grafo.obtenerElementoIndice(2));
        
        
    }
    
}
