/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tercera.progra;

import java.util.*;

/**
 * Esta es la clase de los conectores que conectarán un mundo con algunas o ninguna fábrica
 * @author esteban
 */
public class Conector extends Elemento{
    
    private HashSet <Fabrica> conexiones;
    private Mundo mundoPropio;
  
    /**
     * Este constructor crea un nuevo constructor sin ninguna referencia a ninuna fábrica, pero requiere
     * una referencia a un mundo, el mundo al cual estará conectado
     * @param posicionX La posición x donde estará colocado en la tabla
     * @param posicionY La posición y donde estará colocado en la tabla
     * @param mundo Este es el mundo con el cual estará conectando las fábricas
     */
    public Conector(int posicionX, int posicionY, Mundo mundo) {
        super(posicionX, posicionY);
        conexiones = new HashSet<>();
        this.mundoPropio = mundo;
    }

    public Conector(int posicionX, int posicionY) {
        super(posicionX, posicionY);
        conexiones = new HashSet<>();
        mundoPropio = null;
    }
    
    /**
     * Retorna un HashSet con las conexiones que posee el conector
     * @return Un HashSet conteniendo referencias a fábricas con las que el conector está conectado
     */
    public HashSet<Fabrica> getConexiones() {
        return conexiones;
    }
    
    /**
     * Este método agrega una conexión a la lista de conexiones del conector
     * @param fabrica La fabrica que va a conectar el conector
     * @return True si fue agregado, False si ya estaba
     */
    public boolean agregarConeccion(Fabrica fabrica){
        return this.conexiones.add(fabrica);
    }
    
    /**
     * Este método remueve una conexión de la lista de conexiones
     * @param fabrica La fábrica a la cual el conector no va a conectar más
     * @return True si estaba en la lista y fue removido exitosamente, False en cualquier otro caso
     */
    public boolean removerConeccion(Fabrica fabrica){
        if(!this.conexiones.contains(fabrica)){
            return false;
        }
        return this.conexiones.remove(fabrica);
    }

    public Mundo getMundoPropio() {
        return mundoPropio;
    }

    public void setMundoPropio(Mundo mundoPropio) {
        this.mundoPropio = mundoPropio;
    }
}
