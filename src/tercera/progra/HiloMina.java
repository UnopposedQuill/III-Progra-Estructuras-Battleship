/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tercera.progra;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author esteban
 */
public class HiloMina extends Thread{

    private Mina minaPropia;
    private boolean activo;
    private boolean pausa;

    public HiloMina(Mina minaPropia) {
        this.minaPropia = minaPropia;
        this.activo = false;
        this.pausa = false;
    }
    
    @Override
    public void run(){
        while(this.activo){
            //no está definido adonde irán los materiales
            System.out.println("No implementado");
            while(this.pausa){
                try {
                    sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(HiloMina.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
