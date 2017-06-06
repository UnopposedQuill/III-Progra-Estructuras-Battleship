/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tercera.progra;

import Gui.TipoFabrica;
import java.io.Serializable;
import java.util.*;

/**
 *
 * @author Esteban
 */
public class GrafoObjetos implements Serializable{
    int cantidadVertices;
    private Elemento[] vertices;
    private int[][] matrizAdyacencia;
    private boolean[] visitados;
    private ArrayList<Coordenada> danhos;
    private ArrayList<Elemento> componentesExplotar;
    private int isDanhable;
    
    /**
     * 
     * @param cantidadVertices
     */
    public GrafoObjetos(int cantidadVertices) {
        this.cantidadVertices = cantidadVertices;
        matrizAdyacencia = new int[cantidadVertices][cantidadVertices];
        vertices = new Elemento[cantidadVertices];
        visitados = new boolean[cantidadVertices];
        componentesExplotar = new ArrayList();
        for (int i = 0; i < cantidadVertices; i++){
            vertices[i] = null;
            visitados[i] = false;
            for (int j = 0; j < cantidadVertices; j++){
                matrizAdyacencia[i][j] = 0;
            }
        }
        this.danhos = new ArrayList<>();
    }

    public void reducirDanhable(){
        if(this.isDanhable > 0){
            this.isDanhable--;
        }
    }
    
    public boolean isDanhable(){
        return this.isDanhable != 0;
    }

    public void setIsDanhable(int isDanhable) {
        this.isDanhable = isDanhable;
    }
    
    public ArrayList<Coordenada> getDanhos() {
        return danhos;
    }
    
    /**
     * Este método agrega nuevos daños a la lista de daños
     * @param coordenadaDelDanho La coordenada donde está el daño
     * @return True si se insertó correctamente, False en el otro csao
     */
    public int agregarDanhos(ArrayList<Coordenada> coordenadaDelDanho){
        boolean disparoAgujeroNegro = false;
        boolean recibenDanhos = false;
        int respuesta = 0;
        for (int i = 0; i < coordenadaDelDanho.size(); i++){
            for (int j = 0; j < cantidadVertices ; j++){
                if(vertices[j] != null){
                    if (coordenadaDelDanho.get(i).equals(vertices[j].getCoordenadas())){
                        //Encontré un componente con esas coordenadas
                        if (vertices[j] instanceof AgujeroNegro)
                            disparoAgujeroNegro = true;//Encontró un agujero negro en las coordenadas de disparo
                    }
                }
            }
        }
        
        if (danhos.addAll(coordenadaDelDanho))
            recibenDanhos = true;
        
        if (recibenDanhos == false && disparoAgujeroNegro == false)
            return 0;//Error al actualizar datos y no pega en agujero negro
        else if (recibenDanhos == true && disparoAgujeroNegro == false)
            return 1;//Agrega los datos pero no pega en agujero negro
        else if (recibenDanhos == true && disparoAgujeroNegro == true)
            return 2;//Agrega los datos y pega en agujero negro
        else
            return 3;//Error al actualizar datos y pega en agujero negro
    }
    
    public boolean agregarNuevoVertice (Elemento nuevoVertice){
        for (int i = 0; i < cantidadVertices; i++){
            if(vertices[i] == null){
                nuevoVertice.setPosicionGrafo(i);
                vertices[i] = nuevoVertice;
                
                return true;//Lo agregó de manera exitosa
            }
        }
        return false;//No encontró espacio disponible
    }
    /**
     * Elimina un Vertice/Elemento, también elimina todas las posibles conexiones
     * @param eliminarVertice
     * @return 
     */
    public boolean eliminarVertice (Elemento eliminarVertice){
        if (vertices[eliminarVertice.getPosicionGrafo()] != null){
            eliminarConexiones(eliminarVertice);
            vertices[eliminarVertice.getPosicionGrafo()] = null;
            return true;
        }
        return false;//Ya existía
    }
    public int calcularDistancia (Elemento desde, Elemento hasta){
        int distanciaFinal = 0;
        return distanciaFinal;
    }
    /**
     * Agrega un nuevo vertice y al mismo tiempo raliza la conexión con el segundo parámetro
     * @param nuevoDesde
     * @param hasta
     * @return 
     */
    public boolean agregarNuevoVertice (Elemento nuevoDesde, Elemento hasta){
        for (int i = 0; i < cantidadVertices; i++){
            if(vertices[i] == null){
                nuevoDesde.setPosicionGrafo(i);
                vertices[i] = nuevoDesde;
                return agregarNuevaConexion(nuevoDesde, hasta, 0); //Lo agregó de manera exitosa y además logró hacer la conexión
            }
        }
        return false;//No encontró espacio disponible
    }
    /**
     * Agrega una nueva conexion desde un elemento hasta otro
     * @param desde
     * @param hasta
     * @param distancia
     * @return 
     */
    public boolean agregarNuevaConexion (Elemento desde, Elemento hasta, int distancia){
        //Me aseguro de que existen los vertices
        int desdeLogico = desde.getPosicionGrafo();
        int hastaLogico = hasta.getPosicionGrafo();
        if (vertices[desdeLogico] != null && vertices[hastaLogico] != null){
            if (matrizAdyacencia [desdeLogico][hastaLogico] == 0){
                matrizAdyacencia [desdeLogico][hastaLogico] = distancia;//Agrego la conexión
                return true;
            }
        }
        return false;
    }
    /**
     * Recibe los X y  Y y busca un conector en esas posiciones para realizar la conexion
     * @param desde
     * @param posicionX
     * @param posicionY
     * @return 
     */
    public boolean agregarNuevaConexionConectorXY (Elemento desde, int posicionX, int posicionY){
        //Me aseguro de que existen los vertices
        int desdeLogico = desde.getPosicionGrafo();
        Conector encontrado = obtenerConectorXY(posicionX, posicionY);
        if (vertices[desdeLogico] != null && encontrado != null){
            return agregarNuevaConexion (vertices[desdeLogico], encontrado, 0);
        }
        return false;
    }
    /**
     * Conexion de un componente a otro
     * @param desde
     * @param posicionX
     * @param posicionY
     * @return 
     */
    public boolean agregarNuevaConexionXY (Elemento desde, int posicionX, int posicionY){
        //Me aseguro de que existen los vertices
        int desdeLogico = desde.getPosicionGrafo();
        Elemento encontrado = obtenerXY(posicionX, posicionY);
        if (vertices[desdeLogico] != null && encontrado != null){
            return agregarNuevaConexion (vertices[desdeLogico], encontrado, 0);
        }
        return false;
    }
    public Conector obtenerConectorXY(int posicionX, int posicionY){
        Coordenada coordenada = new Coordenada (posicionX, posicionY);
        for (int i = 0; i < cantidadVertices; i++){
            if (vertices[i].getCoordenadas().equals(coordenada)){
                if (vertices[i] instanceof Conector)
                    return (Conector) vertices[i];
                else
                    return null;
            } 
        }
        return null;
    }
    public Elemento obtenerXY(int posicionX, int posicionY){
        Coordenada coordenada = new Coordenada (posicionX, posicionY);
        for (int i = 0; i < cantidadVertices; i++){
            if (vertices[i].getCoordenadas().equals(coordenada)){
                return vertices[i];
            } 
        }
        return null;
    }
    /**
     * Revisa las conexiones del elemento
     * @param desde
     * @return 
     */
    @SuppressWarnings("empty-statement")
    public int RevisarConexiones (Elemento desde){
        int cantidadConexiones = 0;
        int desdeLogico = desde.getPosicionGrafo();
        int indiceVisual;
        int indiceVisual2 = desdeLogico+1;
        //Me aseguro de que existen los vertices
        if (vertices[desdeLogico] != null){
            for (int i = 0; i < cantidadVertices; i++){
                if(matrizAdyacencia[desdeLogico][i] != 0){
                    cantidadConexiones++;
                    indiceVisual = i+1;
                    System.out.println("Conexión desde " + indiceVisual2 + " hasta " + indiceVisual);
                }
            }
            
        }
        return cantidadConexiones;
    }
    
    public int visitarAdyacentes (Elemento desde){
        int cantidadConexiones = 0;
        int desdeLogico = desde.getPosicionGrafo();
        //Me aseguro de que existen los vertices
        if (vertices[desdeLogico] != null){
            for (int i = 0; i < cantidadVertices; i++){
                if(matrizAdyacencia[desdeLogico][i] != 0){
                    cantidadConexiones++;
                    visitados[i] = true;
                }
            }
            
        }
        return cantidadConexiones;
    }
   
    /**
     * Visita todos los que están conectados a un componente en específico
     * En este caso el componenente será un mundo
     * @param desde
     * @return 
     */
    public int visitarConectados (Elemento desde){
        int cantidadConexiones = 0;
        int desdeLogico = desde.getPosicionGrafo();
        //Me aseguro de que existen los vertices
        if (vertices[desdeLogico] != null){
            for (int i = 0; i < cantidadVertices; i++){
                if(matrizAdyacencia[desdeLogico][i] != 0){
                    cantidadConexiones++;
                    visitados[i] = true;
                    if (vertices[i] instanceof Conector){
                        //Encontré un conector, llamada recursiva
                        visitarConectados ((Elemento) vertices[desdeLogico]);
                    }
                }
            }
            
        }
        return cantidadConexiones;
    }
    
    
    
    public int visitarAdyacentesMismoTipo (Object tipo){
        int cantidadConexiones = 0;
        //Me aseguro de que existen los vertices
        for (int i = 0; i < cantidadVertices; i++){
            if (vertices[i] != null && vertices[i] instanceof Conector){
                System.out.println("ASDASDASD");
                RevisarConexiones((Elemento) vertices[i]);
                for (int j = 0; j < cantidadVertices; j++){
                    if(matrizAdyacencia[i][j] != 0){
                        cantidadConexiones++;
                        visitados[i] = true;
                    }
                }
            }
        }
        
        return cantidadConexiones;
    }
    /**
     * Elimina la conexión entre dos elementos
     * @param desde
     * @param hasta
     * @return 
     */
    public boolean eliminarConexion (Elemento desde, Elemento hasta){
        //Me aseguro de que existen los vertices
        int desdeLogico = desde.getPosicionGrafo();
        int hastaLogico = hasta.getPosicionGrafo();
        if (vertices[desdeLogico] != null && vertices[hastaLogico] != null){
            if (matrizAdyacencia [desdeLogico][hastaLogico] != 0){
                matrizAdyacencia [desdeLogico][hastaLogico] = 0;//Elimino la conexión
                return true;
            }
        }
        return false;
    }
    /**
     * Elimina TODAS las conexiones del componente
     * @param componente
     * @return 
     */
    public boolean eliminarConexiones (Elemento componente){
        int posicionComponente = componente.getPosicionGrafo();
        //Me aseguro de que existen los vertices
        if (vertices[posicionComponente] != null){
            for (int i = 0; i < cantidadVertices; i++){
                if(matrizAdyacencia[posicionComponente][i] != 0){
                    matrizAdyacencia[posicionComponente][i] = 0;
                }
                if(matrizAdyacencia[i][posicionComponente] != 0){
                    matrizAdyacencia[i][posicionComponente] = 0;
                }
            }
            return true;//Se eliminaron las conexiones
        }
        return false;//No existe dicho componente
    }
    
    public void limpiarVisitados (){
        for (int i = 0; i < cantidadVertices; i++){
            visitados[i] = false;
        }
    }
    /**
     * Obtiene el elemento según el índice deen la matriz de vectores
     * @param indice
     * @return 
     */
    public Elemento obtenerElementoIndice (int indice){
        return (Elemento)vertices[indice];
    }
    
    public ArrayList<Conector> obtenerConectoresDisponibles(){
        ArrayList<Conector> conectores = new ArrayList<>();
        Conector conectorEncontrado;
        //Busco un mundo para empezar a visitar
        for (int i = 0; i < cantidadVertices; i++){
            if (vertices[i] instanceof Conector){
                conectorEncontrado = (Conector)vertices[i];
                conectores.add(conectorEncontrado);
            } 
        }
        return conectores;
    }
    
    public ArrayList<Mundo> obtenerMundosDisponibles(){
        ArrayList<Mundo> conectores = new ArrayList<>();
        Mundo mundoEncontrado;
        //Busco un mundo para empezar a visitar
        for (int i = 0; i < cantidadVertices; i++){
            if (vertices[i] instanceof Mundo){
                mundoEncontrado = (Mundo)vertices[i];
                conectores.add(mundoEncontrado);
            } 
        }
        return conectores;
    }
    
    /**
     * Esta funcion va  a revisar el array de daños y el de vectores para encontrar si se ha destruido un componente, se debe llamar después de cada disparo
     */
    public Elemento[] revisarDestruccionComponentes(){
    //private Elemento[] vertices;
    //private int[][] matrizAdyacencia;
    //private boolean[] visitados;
    //private ArrayList<Coordenada> danhos;
        Fabrica fabrica;
        boolean danhoFabrica;
        Elemento[] componentesExplotar = new Elemento[cantidadVertices];
        Mundo mundo;
        int indiceExplotar = 0;
        boolean danhoMundo;
        boolean danhoMundo2;
        boolean danhoMundo3;
        boolean danhoMundo4;
        for (int i = 0; i < cantidadVertices; i++){
            //Para todos los vertices
            if (vertices[i] != null){
                danhoFabrica = false;

                danhoMundo = false;
                danhoMundo2 = false;
                danhoMundo3 = false;
                danhoMundo4 = false;
                for (int j = 0; j < danhos.size(); j++){
                    //Para todos los daños recibidos
                    
                    if (vertices[i] instanceof Fabrica){
                        //2x1 horizontal o vertical
                        fabrica = (Fabrica) vertices[i];
                        
                        if (danhos.get(j).equals(fabrica.getCoordenadas()) || danhos.get(j).equals(fabrica.getCoordenadaExtra())  && danhoFabrica){
                            //Se ha destruido la fábrica
                            //Debo explotar en estas coordenadas
                            //Después debo de eliminarlo del grafo
                            componentesExplotar[indiceExplotar] = vertices[i];
                            indiceExplotar++;
                        }
                        else{
                            danhoFabrica = true;
                        }
                    }
                    else if (vertices[i] instanceof Mundo){
                        //4x4
                        mundo = (Mundo) vertices[i];
                        if (danhos.get(j).equals(mundo.getCoordenadas())){
                             danhoMundo = true;
                        }
                        else if (danhos.get(j).equals(mundo.getCoordenada2())){
                            danhoMundo2 = true;
                        }
                        else if (danhos.get(j).equals(mundo.getCoordenada3())){
                            danhoMundo3 = true;
                        }
                        else if (danhos.get(j).equals(mundo.getCoordenada4())){
                            danhoMundo4 = true;
                        }
                        
                        if (danhoMundo && danhoMundo2 && danhoMundo3 && danhoMundo4){
                            //Se ha destruido el mundo
                            //Debo explotar en estas coordenadas
                            //Después debo de eliminarlo del grafo
                            componentesExplotar[indiceExplotar] = vertices[i];
                            indiceExplotar++;
                        }
                    }
                    else{
                        //1x1
                        if (danhos.get(j).equals(vertices[i].getCoordenadas())){
                            //Se ha destruido la fábrica
                            //Debo explotar en estas coordenadas
                            componentesExplotar[indiceExplotar] = vertices[i];
                            indiceExplotar++;
                        }
                    }

                }
            }
            
        }
        
        return componentesExplotar;
    }
    
    public Object[][] generarMatriz (){
        Object[][] datosGuia = new Object[15][15];
        int posicionX;
        int posicionY;
        for (int i = 0; i < cantidadVertices; i++){
            if(vertices[i] != null){
                posicionX = vertices[i].getPosicionX();
                posicionY = vertices[i].getPosicionY();
                if (revisarDanhos(posicionX, posicionY))
                    datosGuia[posicionX][posicionY] = TipoFabrica.EXPLOSION1;
                else if (vertices[i] instanceof AgujeroNegro)
                    datosGuia[posicionX][posicionY] = TipoFabrica.AGUJERO;
                else if (vertices[i] instanceof Armeria){
                    Armeria elementoPintar = (Armeria)vertices[i];
                    if (elementoPintar.getOrientacionFabrica() == Orientacion.Horizontal){
                        datosGuia[posicionX][posicionY] = TipoFabrica.ARMERIAH1;
                        
                        if (revisarDanhos(posicionX+1, posicionY))
                            datosGuia[posicionX+1][posicionY] = TipoFabrica.EXPLOSION1;
                        else
                            datosGuia[posicionX+1][posicionY] = TipoFabrica.ARMERIAH2;
                    }
                    else if (elementoPintar.getOrientacionFabrica() == Orientacion.Vertical){
                        datosGuia[posicionX][posicionY] = TipoFabrica.ARMERIAV1;
                        
                        if (revisarDanhos(posicionX, posicionY+1))
                            datosGuia[posicionX][posicionY+1] = TipoFabrica.EXPLOSION1;
                        else
                            datosGuia[posicionX][posicionY+1] = TipoFabrica.ARMERIAV2;
                    }
                    
                }
                else if (vertices[i] instanceof Conector){
                    datosGuia[posicionX][posicionY] = TipoFabrica.CONECTOR;
                }
                else if (vertices[i] instanceof Mina){
                    Mina elementoPintar = (Mina)vertices[i];
                    if (elementoPintar.getOrientacionFabrica() == Orientacion.Horizontal){
                        datosGuia[posicionX][posicionY] = TipoFabrica.MINAH1;
                        
                        if (revisarDanhos(posicionX+1, posicionY))
                            datosGuia[posicionX+1][posicionY] = TipoFabrica.EXPLOSION1;
                        else
                            datosGuia[posicionX+1][posicionY] = TipoFabrica.MINAH2;
                    }
                    else if (elementoPintar.getOrientacionFabrica() == Orientacion.Vertical){
                        datosGuia[posicionX][posicionY] = TipoFabrica.MINAV1;
                        
                        if (revisarDanhos(posicionX, posicionY+1))
                            datosGuia[posicionX][posicionY+1] = TipoFabrica.EXPLOSION1;
                        else
                            datosGuia[posicionX][posicionY+1] = TipoFabrica.MINAV2;
                    }
                }
                else if (vertices[i] instanceof Mundo){
                    datosGuia[posicionX][posicionY] = TipoFabrica.MUNDO1;
                    
                    if (revisarDanhos(posicionX+1, posicionY))
                        datosGuia[posicionX+1][posicionY] = TipoFabrica.EXPLOSION1;
                    else
                        datosGuia[posicionX+1][posicionY] = TipoFabrica.MUNDO2;
                    
                    if (revisarDanhos(posicionX, posicionY+1))
                        datosGuia[posicionX][posicionY+1] = TipoFabrica.EXPLOSION1;
                    else
                        datosGuia[posicionX][posicionY+1] = TipoFabrica.MUNDO3;
                    
                    if (revisarDanhos(posicionX+1, posicionY+1))
                        datosGuia[posicionX+1][posicionY+1] = TipoFabrica.EXPLOSION1;
                    else
                        datosGuia[posicionX+1][posicionY+1] = TipoFabrica.MUNDO4;
                }
                else if (vertices[i] instanceof Templo){
                    Templo elementoPintar = (Templo)vertices[i];
                    if (elementoPintar.getOrientacionFabrica() == Orientacion.Horizontal){
                        datosGuia[posicionX][posicionY] = TipoFabrica.TEMPLOH1;
                        
                        if (revisarDanhos(posicionX+1, posicionY))
                            datosGuia[posicionX+1][posicionY] = TipoFabrica.EXPLOSION1;
                        else
                            datosGuia[posicionX+1][posicionY] = TipoFabrica.TEMPLOH2;
                    }
                    else if (elementoPintar.getOrientacionFabrica() == Orientacion.Vertical){
                        datosGuia[posicionX][posicionY] = TipoFabrica.TEMPLOV1;
                        
                        if (revisarDanhos(posicionX, posicionY+1))
                            datosGuia[posicionX][posicionY+1] = TipoFabrica.EXPLOSION1;
                        else
                            datosGuia[posicionX][posicionY+1] = TipoFabrica.TEMPLOV2;
                    }
                }
                else if (vertices[i] instanceof Mercado){
                    Mercado elementoPintar = (Mercado)vertices[i];
                    if (elementoPintar.getOrientacionFabrica() == Orientacion.Horizontal){
                        datosGuia[posicionX][posicionY] = TipoFabrica.MERCADOH1;
                        
                        if (revisarDanhos(posicionX+1, posicionY))
                            datosGuia[posicionX+1][posicionY] = TipoFabrica.EXPLOSION1;
                        else
                            datosGuia[posicionX+1][posicionY] = TipoFabrica.MERCADOH2;
                    }
                    else if (elementoPintar.getOrientacionFabrica() == Orientacion.Vertical){
                        datosGuia[posicionX][posicionY] = TipoFabrica.MERCADOV1;
                        
                        if (revisarDanhos(posicionX, posicionY+1))
                            datosGuia[posicionX][posicionY+1] = TipoFabrica.EXPLOSION1;
                        else
                            datosGuia[posicionX][posicionY+1] = TipoFabrica.MERCADOV2;
                    }
                }
            }
        }
        
        return datosGuia;
    }
    
    public Elemento[] obtenerDesconectadosMundo(){
        Elemento[] desconectadosMundo;
        Mundo mundoEncontrado;
        //Busco un mundo para empezar a visitar
        for (int i = 0; i < cantidadVertices; i++){
            if (vertices[i] instanceof Mundo){
                mundoEncontrado = (Mundo)vertices[i];
                visitarConectados(mundoEncontrado);
            } 
        }
        //Ya visite a los que estaban conectados
        desconectadosMundo = obtenerNoVisitado();
        return desconectadosMundo;
    }
    
    public Elemento[] obtenerVisitado(){
        Elemento[] componentesVisitados = new Elemento[cantidadVertices];
        int contador = 0;
        
        for (int i = 0; i < cantidadVertices; i++){
            componentesVisitados[i] = null;
        }
        
        for (int i = 0; i < cantidadVertices; i++){
            if (visitados[i]){
                componentesVisitados[contador] = vertices[i];
            } 
        }
        return componentesVisitados;
    }
    public Elemento[] obtenerNoVisitado(){
        Elemento[] componentesVisitados = new Elemento[cantidadVertices];
        int contador = 0;
        
        for (int i = 0; i < cantidadVertices; i++){
            componentesVisitados[i] = null;
        }
        
        for (int i = 0; i < cantidadVertices; i++){
            if (!visitados[i]){
                componentesVisitados[contador] = vertices[i];
            } 
        }
        return componentesVisitados;
    }
    public Object[][] generarMatrizDesconectadosConectadosMundo (){
        Object[][] datosGuia = new Object[15][15];
        
        Elemento[] noVisitado = obtenerDesconectadosMundo();
        
        int posicionX;
        int posicionY;
        for (int i = 0; i < cantidadVertices; i++){
            if(noVisitado[i] != null){
                posicionX = noVisitado[i].getPosicionX();
                posicionY = noVisitado[i].getPosicionY();
                if (revisarDanhos(posicionX, posicionY))
                    datosGuia[posicionX][posicionY] = TipoFabrica.EXPLOSION1;
                else if (noVisitado[i] instanceof AgujeroNegro)
                    datosGuia[posicionX][posicionY] = TipoFabrica.AGUJERO;
                else if (noVisitado[i] instanceof Armeria){
                    Armeria elementoPintar = (Armeria)noVisitado[i];
                    if (elementoPintar.getOrientacionFabrica() == Orientacion.Horizontal){
                        datosGuia[posicionX][posicionY] = TipoFabrica.ARMERIAH1;
                        
                        if (revisarDanhos(posicionX+1, posicionY))
                            datosGuia[posicionX+1][posicionY] = TipoFabrica.EXPLOSION1;
                        else
                            datosGuia[posicionX+1][posicionY] = TipoFabrica.ARMERIAH2;
                    }
                    else if (elementoPintar.getOrientacionFabrica() == Orientacion.Vertical){
                        datosGuia[posicionX][posicionY] = TipoFabrica.ARMERIAV1;
                        
                        if (revisarDanhos(posicionX, posicionY+1))
                            datosGuia[posicionX][posicionY+1] = TipoFabrica.EXPLOSION1;
                        else
                            datosGuia[posicionX][posicionY+1] = TipoFabrica.ARMERIAV2;
                    }
                    
                }
                else if (noVisitado[i] instanceof Conector){
                    datosGuia[posicionX][posicionY] = TipoFabrica.CONECTOR;
                }
                else if (noVisitado[i] instanceof Mina){
                    Mina elementoPintar = (Mina)noVisitado[i];
                    if (elementoPintar.getOrientacionFabrica() == Orientacion.Horizontal){
                        datosGuia[posicionX][posicionY] = TipoFabrica.MINAH1;
                        
                        if (revisarDanhos(posicionX+1, posicionY))
                            datosGuia[posicionX+1][posicionY] = TipoFabrica.EXPLOSION1;
                        else
                            datosGuia[posicionX+1][posicionY] = TipoFabrica.MINAH2;
                    }
                    else if (elementoPintar.getOrientacionFabrica() == Orientacion.Vertical){
                        datosGuia[posicionX][posicionY] = TipoFabrica.MINAV1;
                        
                        if (revisarDanhos(posicionX, posicionY+1))
                            datosGuia[posicionX][posicionY+1] = TipoFabrica.EXPLOSION1;
                        else
                            datosGuia[posicionX][posicionY+1] = TipoFabrica.MINAV2;
                    }
                }
                else if (noVisitado[i] instanceof Mundo){
                    datosGuia[posicionX][posicionY] = TipoFabrica.MUNDO1;
                    
                    if (revisarDanhos(posicionX+1, posicionY))
                        datosGuia[posicionX+1][posicionY] = TipoFabrica.EXPLOSION1;
                    else
                        datosGuia[posicionX+1][posicionY] = TipoFabrica.MUNDO2;
                    
                    if (revisarDanhos(posicionX, posicionY+1))
                        datosGuia[posicionX][posicionY+1] = TipoFabrica.EXPLOSION1;
                    else
                        datosGuia[posicionX][posicionY+1] = TipoFabrica.MUNDO3;
                    
                    if (revisarDanhos(posicionX+1, posicionY+1))
                        datosGuia[posicionX+1][posicionY+1] = TipoFabrica.EXPLOSION1;
                    else
                        datosGuia[posicionX+1][posicionY+1] = TipoFabrica.MUNDO4;
                }
                else if (noVisitado[i] instanceof Templo){
                    Templo elementoPintar = (Templo)noVisitado[i];
                    if (elementoPintar.getOrientacionFabrica() == Orientacion.Horizontal){
                        datosGuia[posicionX][posicionY] = TipoFabrica.TEMPLOH1;
                        
                        if (revisarDanhos(posicionX+1, posicionY))
                            datosGuia[posicionX+1][posicionY] = TipoFabrica.EXPLOSION1;
                        else
                            datosGuia[posicionX+1][posicionY] = TipoFabrica.TEMPLOH2;
                    }
                    else if (elementoPintar.getOrientacionFabrica() == Orientacion.Vertical){
                        datosGuia[posicionX][posicionY] = TipoFabrica.TEMPLOV1;
                        
                        if (revisarDanhos(posicionX, posicionY+1))
                            datosGuia[posicionX][posicionY+1] = TipoFabrica.EXPLOSION1;
                        else
                            datosGuia[posicionX][posicionY+1] = TipoFabrica.TEMPLOV2;
                    }
                }
                else if (noVisitado[i] instanceof Mercado){
                    Mercado elementoPintar = (Mercado)noVisitado[i];
                    if (elementoPintar.getOrientacionFabrica() == Orientacion.Horizontal){
                        datosGuia[posicionX][posicionY] = TipoFabrica.MERCADOH1;
                        
                        if (revisarDanhos(posicionX+1, posicionY))
                            datosGuia[posicionX+1][posicionY] = TipoFabrica.EXPLOSION1;
                        else
                            datosGuia[posicionX+1][posicionY] = TipoFabrica.MERCADOH2;
                    }
                    else if (elementoPintar.getOrientacionFabrica() == Orientacion.Vertical){
                        datosGuia[posicionX][posicionY] = TipoFabrica.MERCADOV1;
                        
                        if (revisarDanhos(posicionX, posicionY+1))
                            datosGuia[posicionX][posicionY+1] = TipoFabrica.EXPLOSION1;
                        else
                            datosGuia[posicionX][posicionY+1] = TipoFabrica.MERCADOV2;
                    }
                }
            }
        }
        return datosGuia;
    }
    
    public boolean revisarDanhos(int x, int y){
        
        for (int i = 0; i < danhos.size(); i++){
            if (danhos.get(i).getX() == x && danhos.get(i).getY() == y){
                //Hay daño entonces lo indico
                return true;
            }
        }
        return false;
    }
    
    public boolean buscarMina(){
        for (int i = 0; i < cantidadVertices; i++){
            if (vertices[i] != null && vertices[i] instanceof Mina){
                return true;
            }
        }
        return false;
    }
    
    public boolean buscarArmeria(){
        for (int i = 0; i < cantidadVertices; i++){
            if (vertices[i] != null && vertices[i] instanceof Armeria){
                return true;
            }
        }
        return false;
    }
    
    public boolean buscarMercado(){
        for (int i = 0; i < cantidadVertices; i++){
            if (vertices[i] != null && vertices[i] instanceof Mercado){
                return true;
            }
        }
        return false;
    }
    
    public boolean buscarTemplo(){
        for (int i = 0; i < cantidadVertices; i++){
            if (vertices[i] != null && vertices[i] instanceof Templo){
                return true;
            }
        }
        return false;
    }
}
