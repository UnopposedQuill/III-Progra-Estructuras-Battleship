/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import tercera.progra.*;
        
/**
 *
 * @author esteban
 */
public class HiloActualizaServidor extends Thread{
    private final Servidor servidorAPintar; 
    private final JframeServidor ventanaAPintar;

    public HiloActualizaServidor(Servidor servidorAPintar, JframeServidor ventanaAPintar) {
        this.servidorAPintar = servidorAPintar;
        this.ventanaAPintar = ventanaAPintar;
    }
    
    @Override
    public void run() {
        while(true){
            this.ventanaAPintar.pintarLogs(this.servidorAPintar.getLogs());
        }
    }
    
    
}
