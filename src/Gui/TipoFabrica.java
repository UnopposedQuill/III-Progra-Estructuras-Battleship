/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

/**
 * Esta clase sólo debe ser usada para renderizar una tabla de mundo, el BLANK significa que la casilla debe estar
 * en blanco
 * @author esteban
 */
public enum TipoFabrica {
    
    AGUJERO,ARMERIAH1,ARMERIAH2,ARMERIAV1,ARMERIAV2,CONECTOR,CONECTOR2,CONECTOR3,MINAH1,MINAH2,MINAV1,MINAV2,MUNDO1,MUNDO2,MUNDO3,MUNDO4,TEMPLOH1,TEMPLOH2,TEMPLOV1,TEMPLOV2,BLANK,EXPLOSION1,EXPLOSION2,EXPLOSION3,MERCADOH1,MERCADOH2,MERCADOV1,MERCADOV2,DISPARADO;

    public static String fakeToString(TipoFabrica tipo) {
        switch(tipo){
            case AGUJERO:{
                return "agujeroNegro.jpg";
            }
            case ARMERIAH1:{
                return "armeriaH1.png";
            }
            case ARMERIAH2:{
                return "armeriaH2.png";
            }
            case ARMERIAV1:{
                return "armeriaV1.png";
            }
            case ARMERIAV2:{
                return "armeriaV2.jpg";
            }
            case CONECTOR:{
                return "conector.jpg";
            }
            case CONECTOR2:{
                return "conector2.jpg";
            }
            case CONECTOR3:{
                return "conector3.jpg";
            }
            case MINAH1:{
                return "minaH1.jpg";
            }
            case MINAH2:{
                return "minaH2.jpg";
            }
            case MINAV1:{
                return "minaV1.jpg";
            }
            case MINAV2:{
                return "minaV2.jpg";
            }
            case MUNDO1:{
                return "tierra1.jpg";
            }
            case MUNDO2:{
                return "tierra2.jpg";
            }
            case MUNDO3:{
                return "tierra3.jpg";
            }
            case MUNDO4:{
                return "tierra4.jpg";
            }
            case TEMPLOH1:{
                return "temploH1.jpg";
            }
            case TEMPLOH2:{
                return "temploH2.jpg";
            }
            case TEMPLOV1:{
                return "temploV1.jpg";
            }
            case TEMPLOV2:{
                return "temploV2.jpg";
            }
            case EXPLOSION1:{
                return "explosion1.jpg";
            }
            case EXPLOSION2:{
                return "explosion2.png";
            }
            case EXPLOSION3:{
                return "explosion3.png";
            }
            case MERCADOH1:{
                return "mercadoH1.jpg";
            }
            case MERCADOH2:{
                return "mercadoH2.jpg";
            }
            case MERCADOV1:{
                return "mercadoV1.jpg";
            }
            case MERCADOV2:{
                return "mercadoV2.jpg";
            }
            case DISPARADO:{
                return "target.png";
            }
            default:{
                return "cvacio.GIF";
            }
        }
    }
}
