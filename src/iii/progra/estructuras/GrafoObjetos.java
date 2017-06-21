/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iii.progra.estructuras;

//import gui.TipoFabrica; //para poder dibujar bien una matriz de los componentes visuales necesitados
import gui.TipoFabrica;
import java.util.*;

/**
 * Los nodos que estarán insertados dentro del grafo, que poseerán un elemento dentro, estas aristas estarán dentro de una lista doble
 * @author Esteban
 */
class AristaGrafo implements java.io.Serializable{
    private Elemento elementoArista;
    private AristaGrafo anterior,siguiente;

    /**
     * Crea una nueva arista basada en una vieja arista ya creada anteriormente
     * @param elementoArista El elemento que poseerá la arista en su interior
     */
    public AristaGrafo(Elemento elementoArista) {
        this.elementoArista = elementoArista;
        this.anterior = this.siguiente = null;
    }

    public AristaGrafo getAnterior() {
        return anterior;
    }

    public void setAnterior(AristaGrafo anterior) {
        this.anterior = anterior;
    }

    public AristaGrafo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(AristaGrafo siguiente) {
        this.siguiente = siguiente;
    }

    public Elemento getElementoArista() {
        return elementoArista;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.elementoArista);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() == obj.getClass()) {//si estoy comparando dos aristas, entonces comparo los elementos
            final AristaGrafo other = (AristaGrafo) obj;
            return Objects.equals(this.elementoArista, other.elementoArista);
        }
        if (obj.getClass() == Coordenada.class){//si estoy comparando una arista con una coordenada, entonces debo comparar la coordenada de la arista con la coordenada ingresada
            return this.elementoArista.equals(obj);
        }
        return false;
    }
}

/**
 * La clase que será un grafo utilizando la estructura de lista de listas
 * @author Esteban
 */
public class GrafoObjetos implements java.io.Serializable{
    
    /**
     * Este entero dirá si es dañable, si es mayor a 0 es indañable, se va reduciendo por 1 con el tiempo
     */
    private int isDanhable;

    /**
     * En este ArrayList se van a guardar temporalmente los daños recibidos hasta que se procesen
     */
    private final ArrayList<Coordenada> danhos;
    
    /**
     * En este Arraylist se guardarán temporalmente los componentes que van a explotar
     */
    private final ArrayList<Elemento> componentesExplotar;
    
    /**
     * los vértices del grafo
     */
    private final ArrayList<AristaGrafo> verticesGrafo;

    public GrafoObjetos(){
        this.componentesExplotar = new ArrayList<>();
        this.danhos = new ArrayList<>();
        this.isDanhable = 0;
        this.verticesGrafo = new ArrayList<>();
    }
    
    public void agregarVertice(Elemento elementoAAgregar){
        if(elementoAAgregar instanceof Fabrica && this.poseeConector()){//si es una fábrica ocupo que haya algún conector previo
            Fabrica fabrica = (Fabrica)elementoAAgregar;
            if(this.isColocable(Fabrica.class, fabrica.getCoordenadas(), fabrica.getOrientacion())){//si es colocable, entonces lo agrego
                this.verticesGrafo.add(new AristaGrafo(elementoAAgregar));//no hay problema si lo agrego como un elemento, o como fábrica
            }
        }
        //o es una fuente de energía o un conector
        else if(elementoAAgregar instanceof Conector && this.isColocable(Conector.class, elementoAAgregar.getCoordenadas()) && this.poseeFuenteDeEnergia()){//si es un conector, es posible colocarlo y ya había una fuente de energía existente, lo agrego
            this.verticesGrafo.add(new AristaGrafo(elementoAAgregar));//lo agrego
        }
        else if(elementoAAgregar instanceof FuenteEnergia && this.isColocable(FuenteEnergia.class, elementoAAgregar.getCoordenadas())){//podría ser una fuente de energía, agrego si es colocable
            this.verticesGrafo.add(new AristaGrafo(elementoAAgregar));//lo agrego
        }
    }
    
    public void agregarConexion(Elemento desde,Elemento hacia){
        if(this.verticesGrafo.indexOf(desde) != -1){//si el desde ya existía, tiene que haber existido desde antes
            desde.conexiones.add(new Conexion(hacia, desde.getPosicionX()-hacia.getPosicionX(), desde.getPosicionX()-hacia.getPosicionY()));
            if(this.verticesGrafo.indexOf(hacia) == -1){//se desea hacer una conexión con un elemento que no está en el grafo todavía, así que hago la conexión y luego lo inserto
                this.verticesGrafo.add(new AristaGrafo(hacia));//lo agrego
            }
        }
    }
    
    public FuenteEnergia getFuenteDeEnergia(){
        for (int i = 0; i < this.verticesGrafo.size(); i++) {
            AristaGrafo get = this.verticesGrafo.get(i);
            if((get.getElementoArista()) instanceof FuenteEnergia) return (FuenteEnergia)get.getElementoArista();//el primero que sea un mundo
        }
        return null;
    }
    
    public Elemento getElemento(Coordenada coordenadaABuscar){
        try{
            return this.verticesGrafo.get(this.verticesGrafo.indexOf(coordenadaABuscar)).getElementoArista();
        }catch(IndexOutOfBoundsException ex){
            return null;
        }
    }
    
    public boolean poseeFuenteDeEnergia(){
        return this.verticesGrafo.stream().anyMatch(t -> t.getElementoArista() instanceof FuenteEnergia); 
    }
    
    public boolean poseeConector(){
        return this.verticesGrafo.stream().anyMatch(t -> t.getElementoArista() instanceof Conector); 
    }
    
    public boolean poseeTemplo(){
        return this.verticesGrafo.stream().anyMatch(t -> t.getElementoArista() instanceof Templo);
    }
    
    public boolean poseeArmeria(){
        return this.verticesGrafo.stream().anyMatch(t -> t.getElementoArista() instanceof Armeria);
    }
    
    /**
     * Revisa si una clase es colocable en las coordenadas especificadas
     * @param claseAColocar La clase que se colocaría
     * @param coordenadasDeColocacion Donde se colocaría la clase
     * @param orientacionDeColocacion La orientación donde se colocaría la fábrica
     * @return True si hay espacio, False si hay otro elemento ocupando el espacio
     */
    public boolean isColocable(Class<? extends Fabrica> claseAColocar, Coordenada coordenadasDeColocacion, boolean orientacionDeColocacion){
        if(this.isOcupado(coordenadasDeColocacion)) return false;//si el base estaba ocupado, false
        //reviso si tengo que revisar el horizontal o el vertical, 
        if(orientacionDeColocacion)return this.isOcupado(new Coordenada(coordenadasDeColocacion.getX(), coordenadasDeColocacion.getY()+1));
        //sino, retorno si está ocupada la vertical
        return this.isOcupado(new Coordenada(coordenadasDeColocacion.getX(), coordenadasDeColocacion.getY()+1));
    }
    
    /**
     * Revisa si una clase es colocable en las coordenadas especificadas
     * @param claseAColocar La clase que se colocaría
     * @param coordenadasDeColocacion Donde se colocaría la clase
     * @return True si hay espacio, False si hay otro elemento ocupando el espacio
     */
    public boolean isColocable(Class<? extends Elemento> claseAColocar, Coordenada coordenadasDeColocacion){
        if(this.isOcupado(coordenadasDeColocacion)) return false;//si el base estaba ocupado
        //el base no estaba ocupado, si es un conector es colocable
        if(claseAColocar == Conector.class)return true;//si era un conector es colocable completamente
        //ahora reviso si era una Fuente de energía o una fábrica
        if(claseAColocar == Fabrica.class)return false;//las fábricas NO se puede saber si son colocables desde este método
        //es una Fuente de energía
        Coordenada[] c = {
            new Coordenada(coordenadasDeColocacion.getX(), coordenadasDeColocacion.getY()+1),
            new Coordenada(coordenadasDeColocacion.getX()+1, coordenadasDeColocacion.getY()),
            new Coordenada(coordenadasDeColocacion.getX()+1, coordenadasDeColocacion.getY()+1)
        };//un arreglo de coordenadas que tengo que revisar, pues una Fábrica de energía toma 4 casillas
        for (int i = 0; i < 3; i++) {
            if(this.isOcupado(c[i]))return false;//hubo una sola coordenada ocupada
        }
        return true;//había espacio
    }
    
    /**
     * Este método revisa si una coordenada dentro del grafo está ocupada
     * @param coordenadaARevisar La coordenada a ser revisada
     * @return True si está ocupada, False si ningún otro componente posee una coordendada allí
     */
    public boolean isOcupado(Coordenada coordenadaARevisar){
        for (int i = 0; i < verticesGrafo.size(); i++) {
            Elemento get = verticesGrafo.get(i).getElementoArista();
            if(get.getCoordenadas().equals(coordenadaARevisar))return true;//si equivale, la coordenada ya estaba ocupada,retorno true
            //si no, entonces sólo puedo descartar la coordenada básica
            if(get instanceof FuenteEnergia){//reviso si es una fuente de energía
                FuenteEnergia fuente = (FuenteEnergia)get;//convierto a fuente de energía
                for (int j = 0; j < 3; j++) {
                    if(fuente.getCoordenadasRestantes()[j].equals(coordenadaARevisar))return true; //reviso si alguna de las 3 coordenadas coincide
                }
            }
            else if(get instanceof Fabrica){//podría ser un conector o una fábrica
                //es una fábrica, sólo falta revisar la coordenada auxiliar
                Fabrica fabrica = (Fabrica)get;
                if(fabrica.coordenadaAuxiliar.equals(coordenadaARevisar))return true;//si es igual retorno true
            }
            //si es un conector entonces sé que no importa en estos if, pues ya revisé la coordenada básica
        }
        return false;//revisé todos, y ninguno estaba ocupado
    }
    
    /**
     * Este método averigua si una coordenada equivale a otro ya existente dentro del grafo
     * @param coordenadaBuscar La coordenada a buscar
     * @return True si una coordenada equivale a alguno
     */
    public boolean verticeExiste(Coordenada coordenadaBuscar){
        return this.verticesGrafo.contains(null);
    }
    
    public boolean isDanhable(){
        return this.isDanhable == 0;
    }
    
    public boolean agregarDanhos(ArrayList<Coordenada> danhos){
        return this.danhos.addAll(danhos);
    }
    
    public void reducirDanhable(){
        if(this.isDanhable != 0){
            this.isDanhable--;
        }
    }

    public void setIsDanhable(int isDanhable) {
        this.isDanhable = isDanhable;
    }
    
    public Object[][]generarMatriz(){
        Object[][] datosRetornar = new Object[15][15];
        for (int i = 0; i < this.verticesGrafo.size(); i++) {
            Elemento get = this.verticesGrafo.get(i).getElementoArista();
            if(get instanceof FuenteEnergia){
                FuenteEnergia f = (FuenteEnergia)get;
                datosRetornar[f.getCoordenadas().getX()][f.getCoordenadas().getY()] = TipoFabrica.FUENTE;
                for (Coordenada coordenadasRestante : f.getCoordenadasRestantes()) {
                    datosRetornar[coordenadasRestante.getX()][coordenadasRestante.getY()] = TipoFabrica.FUENTE;
                }
            }
            else if(get instanceof Conector){
                datosRetornar[get.getCoordenadas().getX()][get.getCoordenadas().getY()] = TipoFabrica.CONECTOR;
            }
            else if(get instanceof Remolino){
                datosRetornar[get.getCoordenadas().getX()][get.getCoordenadas().getY()] = TipoFabrica.REMOLINO;
            }
        }
        return datosRetornar;
    }
    
    public ArrayList<Conector> getConectoresDisponibles(){
        ArrayList<Conector> listaRetornar = new ArrayList<>();
        for (int i = 0; i < this.verticesGrafo.size(); i++) {
            Elemento get = this.verticesGrafo.get(i).getElementoArista();
            if(get instanceof Conector){
                listaRetornar.add((Conector)get);
            }
        }
        return listaRetornar;
    }
    
    public ArrayList<FuenteEnergia> getFuentesEnergiaDisponibles(){
        ArrayList<FuenteEnergia> listaRetornar = new ArrayList<>();
        for (int i = 0; i < this.verticesGrafo.size(); i++) {
            Elemento get = this.verticesGrafo.get(i).getElementoArista();
            if(get instanceof FuenteEnergia){
                listaRetornar.add((FuenteEnergia)get);
            }
        }
        return listaRetornar;
    }
}
