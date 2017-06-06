/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tercera.progra;

import java.util.Random;

/**
 *
 * @author esteban
 */
public class Comodin {
    
    private int golpesRestantes;

    public Comodin() {
        Random randomizador = new Random();
        int random = ((int) (randomizador.nextDouble() * 6 + randomizador.nextDouble())) + 6;
        this.golpesRestantes = random;
    }

    public int getGolpesRestantes() {
        return golpesRestantes;
    }
}
