/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 * Esta clase sólo debe ser usada para renderizar una tabla de mundo, el BLANK significa que la casilla debe estar
 * en blanco, las cuatro variantes de las fábricas son debido a que tiene que haber verticales u horizontales y cúál de todas es
 * @author esteban
 */
public enum TipoFabrica {
    BLANK, MERCADOh1, MINAh1, ARMERIAh1, TEMPLOh1, MERCADOv1, MINAv1, ARMERIAv1, TEMPLOv1, MERCADOh2, MINAh2, ARMERIAh2, TEMPLOh2,MERCADOv2, MINAv2, ARMERIAv2, TEMPLOv2, CONECTOR, FUENTE, REMOLINO;
}
