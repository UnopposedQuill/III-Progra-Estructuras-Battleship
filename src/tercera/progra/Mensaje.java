/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tercera.progra;

import java.io.Serializable;

/**
 *
 * @author Esteban
 */
public class Mensaje implements Serializable{
    private TipoMensaje tipoDelMensaje;
    private Object datoDeSolicitud;
    private Object datoDeRespuesta;

    public Mensaje(TipoMensaje tipoDelMensaje, Object datoDeSolicitud) {
        this.tipoDelMensaje = tipoDelMensaje;
        this.datoDeSolicitud = datoDeSolicitud;
        this.datoDeRespuesta = null;
    }

    public TipoMensaje getTipoDelMensaje() {
        return tipoDelMensaje;
    }

    public Object getDatoDeSolicitud() {
        return datoDeSolicitud;
    }

    public Object getDatoDeRespuesta() {
        return datoDeRespuesta;
    }

    public void setDatoDeRespuesta(Object datoDeRespuesta) {
        this.datoDeRespuesta = datoDeRespuesta;
    }
    
    
}