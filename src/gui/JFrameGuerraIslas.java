/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;
import iii.progra.estructuras.*;
import java.net.UnknownHostException;
import java.util.logging.*;

/**
 *
 * @author Esteban
 */
public class JFrameGuerraIslas extends JFrame implements java.io.Serializable{

    private Jugador jugadorPropio;
    private ArrayList<Jugador> enemigos;
    private int tableroActual;
    
    /**
     * Creates new form JFrameGuerraMundos
     */
    public JFrameGuerraIslas() {
        initComponents();
        
        //Para que las celdas estén mejor
        this.Mundo.setRowHeight(42);
        this.tableroActual = 0;
        
        String nombreJugador = JOptionPane.showInputDialog("Inserte el nombre del jugador");
        String IP = JOptionPane.showInputDialog("Inserte el IP del servidor");
        try {
            this.jugadorPropio = new Jugador(nombreJugador,IP, 5000);
            
            Mensaje m = this.jugadorPropio.realizarPeticion(new Mensaje(TipoMensaje.activado, null));
            boolean offline = !(boolean)(m.getDatoDeRespuesta());
            this.Desconectado.setVisible(offline);
            this.cambiarHost.setVisible(offline);
            this.dibujarTablero(this.jugadorPropio.getGrafoPropio().generarMatriz());
            this.jugadorPropio.start();
        } catch (UnknownHostException ex) {
            Logger.getLogger(JFrameGuerraIslas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /*
        System.out.println(grafo.agregarNuevoVertice(new Templo(Orientacion.Horizontal, 0, 0)));
        System.out.println(grafo.agregarNuevoVertice(new Conector(0, 1)));
        System.out.println(grafo.agregarNuevoVertice(new Conector(0, 2)));
        System.out.println(grafo.agregarNuevoVertice(new Armeria(Orientacion.Horizontal, 0, 3)));
        System.out.println(grafo.agregarNuevoVertice(new Mina(Orientacion.Horizontal, 0, 6)));
        System.out.println(grafo.agregarNuevoVertice(new AgujeroNegro(14, 14)));
        
        System.out.println(grafo.agregarNuevaConexion(grafo.obtenerElementoIndice(1), grafo.obtenerElementoIndice(0), 99));
        System.out.println(grafo.agregarNuevaConexion(grafo.obtenerElementoIndice(1), grafo.obtenerElementoIndice(3), 99));
        System.out.println(grafo.agregarNuevaConexion(grafo.obtenerElementoIndice(2), grafo.obtenerElementoIndice(3), 99));
        grafo.RevisarConexiones(grafo.obtenerElementoIndice(1));
        System.out.println("Mismo tipo");
        grafo.visitarAdyacentesMismoTipo(grafo.obtenerElementoIndice(2));
        */
        
        
    }

    private void dibujarTablero(Object[][] datosGuia){
        
        
        //aquí irán los datos que serán dibujados en la tabla
        Object[][] datos = new Object[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                Object objetoGuia = datosGuia[i][j];
                if(objetoGuia instanceof TipoFabrica){//este es un if de validación, sino, revienta todo
                    //ahora creo el botón a renderizar
                    JButton botonARenderizar = new JButton("");
                    //le seteo la imagen al botón
                    botonARenderizar.setIcon(new ImageIcon(getClass().getResource((TipoFabrica.fakeToString((TipoFabrica)objetoGuia)))));
                    //lo ingreso en la tabla
                    datos[i][j] = botonARenderizar;
                }//si no calza en tipo fabrica, entonces se retorna y se lava las manos
                else{
                    datos[i][j] = new JButton("Desconocido");
                }
            }
        }
                
        
        //aquí irán los datos de las columnas
        String [] columnas = new String[15];
        //las columnas irán mostradas con la posición, ¿no?
        for (int i = 0; i < 15; i++) {
            columnas[i] = String.valueOf(i);
        }
        
        
        DefaultTableModel dtm = new DefaultTableModel(datos, columnas){ //a partir de aquí me pongo a modificar algunos aspectos del modelo
            
            //con esto defino los tipos por fuerza que va a tener el modelo
            Class[] tipos = new Class[]{
            //estos son los tipos de datos que van a ir en cada columna, NO MODIFICAR,
            //esta es el alma del renderizador
            JButton.class,
            JButton.class,
            JButton.class,
            JButton.class,
            JButton.class, //son 15 columnas, 15 clases
            
            JButton.class,
            JButton.class,
            JButton.class,
            JButton.class,
            JButton.class,
            
            JButton.class,
            JButton.class,
            JButton.class,
            JButton.class,
            JButton.class,};
            
            //este override es para modificar lo que consigue al intentar la tabla
            @Override
            public Class getColumnClass(int columnIndex){
                //Este método es invocado por el CellRenderer para saber que dibujar en la celda,
                //observen que estamos retornando la clase que definimos de antemano.
                return tipos[columnIndex];
            }
            
            @Override
            public boolean isCellEditable(int row, int column){
                //Sobrescribo este método para evitar que la columna que contiene los botones sea editada
                return !(this.getColumnClass(column).equals(JButton.class));
            }
            
        };
        this.Mundo.setModel(dtm);//aquí le ingreso a la tabla todos los cambios
        
        //esta es la parte que decide QUÉ DEBE DIBUJAR
        this.Mundo.setDefaultRenderer(JButton.class, new TableCellRenderer(){
            /**
             * Este método sólo se encarga de una sóla y simple idiotez:
             * retornar lo que entra xD
             * El asunto es que a la hora de pintar la tabla dibuje el objeto tal
             * y como entra, en lugar de hacerlo asquerosamente de manera default 
             * Resumen: hace que aparezca un botón cuando aparece un botón en la matriz de datos
             */
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object objeto, boolean estaSeleccionado, boolean tieneElFoco, int fila, int columna) {
                return (Component) objeto;
            }
        });
        //aquí sólo resta hacer el panel que contendrá la tabla junto con el mouse listener
        
        //para definir las accionas al dar click en la tabla
        this.Mundo.addMouseListener(new MouseAdapter() {
            /*
            el mouse listener se coloca en la tabla y no en la ventana, así logro saber en qué celda se dio
            clic, en lugar de buscar por el frame al que pertenece
            el asunto es que tengo que capturar cuando el mouse clickea en el botón de mostrar el mapa
            */
            @Override
            public void mouseClicked(MouseEvent e){   
                
                //esto consigue la fila y columna del evento
                int fila = Mundo.rowAtPoint(e.getPoint());
                int columna = Mundo.columnAtPoint(e.getPoint());
                
                /**
                 * Pregunto si hizo clic sobre la celda que contiene un botón, 
                 * este if puede quitarlo si quiere, pero a mi parecer mejor dejarlo
                 */
                if (Mundo.getModel().getColumnClass(columna).equals(JButton.class)) {
                    //significa que sí dio en el botón, por lo que hago que dibuje el mapa
                    //ingresar aquí lo que se desea que suceda al presionar uno de los botones
                    System.out.println("Se presionó el botón en la posición: " + fila + ", " + columna);
                    comprarFabrica(fila, columna);
                }
            }
        });
    }
    
    private void comprarFabrica(int fila, int columna){
        Object [][] datosTabla = this.jugadorPropio.getGrafoPropio().generarMatriz();
            if((TipoFabrica)datosTabla[fila][columna] == TipoFabrica.BLANK){
                ArrayList <Object> datosAEnviar = new ArrayList<>();
                datosAEnviar.add(this.jugadorPropio.getNombreJugador());
                Conector conector = null;
                boolean orientacionFabrica = this.Orientacion.isSelected();
                if(this.TipoFabricaComprar.getSelectedIndex() < 5){
                    conector = (Conector)JOptionPane.showInputDialog(null, "Elija un conector",
                                "Conectar Elemento", JOptionPane.QUESTION_MESSAGE, null,
                                this.jugadorPropio.getGrafoPropio().getConectoresDisponibles().toArray(), 0);
                    /*orientacionFabrica = (Orientacion)JOptionPane.showInputDialog(null, "Elija la orientación de la fábrica",
                                "Orientación de la Fábrica", JOptionPane.QUESTION_MESSAGE, null,
                                Orientacion.values(), 0);
                    */
                }
                switch(this.TipoFabricaComprar.getSelectedIndex()){                    
                    case 0:{
                        //Armeria
                        Armeria armeria = new Armeria(orientacionFabrica, fila, columna);
                        datosAEnviar.add(armeria);
                        datosAEnviar.add(conector.getCoordenadas());
                        break;
                    }
                    case 1:{
                        //Mina
                        int cantidadAcero = 0;
                        int cadenciaAcero = 0;
                        while(true){
                            try{
                                cantidadAcero = Integer.parseInt((String)JOptionPane.showInputDialog("Seleccione la cantidad de acero que hará la mina", 50));
                                cadenciaAcero = Integer.parseInt(JOptionPane.showInputDialog("Seleccione la cantidad de tiempo que le tomará hacerlos (segundos)", 60));
                                break;
                            }catch(NullPointerException | NumberFormatException exc){
                                JOptionPane.showMessageDialog(null, "Ingrese un número válido", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        Mina mina = new Mina(orientacionFabrica, fila, columna, this.jugadorPropio, cadenciaAcero, cantidadAcero);// new Mina(cantidadAcero, cadenciaAcero, orientacionFabrica, xSeleccionado, ySeleccionado);
                        datosAEnviar.add(mina);
                        datosAEnviar.add(conector.getCoordenadas());
                        break;
                    }
                    case 2:{
                        //Mercado
                        Mercado mercado = new Mercado(orientacionFabrica, fila, columna);
                        datosAEnviar.add(mercado);
                        datosAEnviar.add(conector.getCoordenadas());
                        break;
                    }
                    case 3:{
                        //Templo
                        Templo templo = new Templo(orientacionFabrica, fila, columna);
                        datosAEnviar.add(templo);
                        datosAEnviar.add(conector.getCoordenadas());
                        break;
                    }
                    case 4:{
                        //Fuente de energía
                        int cantidadDeEnergia = 10;
                        try{
                            cantidadDeEnergia = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad de energía que tendrá la fuente de energía"));
                        }catch(NumberFormatException ex){}
                        
                        FuenteEnergia mundo = new FuenteEnergia(cantidadDeEnergia,fila, columna);
                        datosAEnviar.add(mundo);
                        break;
                    }
                    case 5:{
                        //Conector
                        FuenteEnergia fuenteEnergia = (FuenteEnergia)JOptionPane.showInputDialog(null, "Elija un mundo",
                                "Conectar Elemento", JOptionPane.QUESTION_MESSAGE, null,
                                this.jugadorPropio.getGrafoPropio().getFuentesEnergiaDisponibles().toArray(), 0);
                        Conector conectorNuevo = new Conector(fila, columna);
                        datosAEnviar.add(conectorNuevo);
                        datosAEnviar.add(fuenteEnergia.getCoordenadas());
                        break;
                    }
                }
                this.jugadorPropio.realizarPeticion(new Mensaje(TipoMensaje.nuevoElemento, datosAEnviar));
            }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bMisil = new javax.swing.JButton();
        bMultiShot = new javax.swing.JButton();
        bBomba = new javax.swing.JButton();
        bComboShot = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        Mundo = new javax.swing.JTable();
        lDineroActual = new javax.swing.JLabel();
        LabelTurno = new javax.swing.JLabel();
        TipoFabricaComprar = new javax.swing.JComboBox<>();
        FabricaComprar = new javax.swing.JButton();
        NumeroMundo = new javax.swing.JLabel();
        MundoAnterior = new javax.swing.JButton();
        MundoSiguiente = new javax.swing.JButton();
        TextFieldChat = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        EnviarMensaje = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        TextAreaChat = new javax.swing.JTextArea();
        EncolarDuo = new javax.swing.JButton();
        EncolarTrio = new javax.swing.JButton();
        EncolarCuarteto = new javax.swing.JButton();
        Desconectado = new javax.swing.JLabel();
        cambiarHost = new javax.swing.JButton();
        Orientacion = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bMisil.setText("Misil");

        bMultiShot.setText("MultiShot");
        bMultiShot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bMultiShotActionPerformed(evt);
            }
        });

        bBomba.setText("Bomba");

        bComboShot.setText("ComboShot");

        Mundo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10", "Title 11", "Title 12", "Title 13", "Title 14", "Title 15"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(Mundo);
        if (Mundo.getColumnModel().getColumnCount() > 0) {
            Mundo.getColumnModel().getColumn(0).setResizable(false);
        }

        lDineroActual.setText("Dinero Actual: 4000$");

        LabelTurno.setText("Turno del Jugador: Jugador 1");

        TipoFabricaComprar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Armeria", "Mina", "Mercado", "Templo", "Mundo", "Conector" }));

        FabricaComprar.setText("Comprar");
        FabricaComprar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FabricaComprarActionPerformed(evt);
            }
        });

        NumeroMundo.setText("Número de Mundo: 0");

        MundoAnterior.setText("Mundo Anterior");

        MundoSiguiente.setText("Mundo Siguiente");

        TextFieldChat.setText("Mensaje");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Jugador 1", "Jugador 2", "Jugador 3", "Jugador 1,2", "Jugador 1,3", "Jugador 2,3", "Jugador 1,2,3" }));

        EnviarMensaje.setText("Enviar");
        EnviarMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnviarMensajeActionPerformed(evt);
            }
        });

        TextAreaChat.setEditable(false);
        TextAreaChat.setColumns(20);
        TextAreaChat.setRows(5);
        jScrollPane3.setViewportView(TextAreaChat);

        EncolarDuo.setText("Encolarse en Dúo");
        EncolarDuo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EncolarDuoActionPerformed(evt);
            }
        });

        EncolarTrio.setText("Encolarse en Trío");
        EncolarTrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EncolarTrioActionPerformed(evt);
            }
        });

        EncolarCuarteto.setText("Encolarse en Cuarteto");
        EncolarCuarteto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EncolarCuartetoActionPerformed(evt);
            }
        });

        Desconectado.setText("Desconectado");

        cambiarHost.setText("Cambiar Huesped");
        cambiarHost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cambiarHostActionPerformed(evt);
            }
        });

        Orientacion.setText("Orientacion");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(EnviarMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(bComboShot, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                                    .addComponent(bBomba, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(bMultiShot, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(bMisil, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(TipoFabricaComprar, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(FabricaComprar))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(Orientacion)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(MundoAnterior)))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(TextFieldChat, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 893, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(EncolarCuarteto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(EncolarTrio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(EncolarDuo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Desconectado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(MundoSiguiente)
                                    .addComponent(LabelTurno, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(NumeroMundo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lDineroActual, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(cambiarHost, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(NumeroMundo, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LabelTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lDineroActual, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(EncolarDuo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(EncolarTrio, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(EncolarCuarteto, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(MundoSiguiente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Desconectado, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cambiarHost)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bMisil, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bMultiShot, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bBomba, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bComboShot, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TipoFabricaComprar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(FabricaComprar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(MundoAnterior)
                            .addComponent(Orientacion))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TextFieldChat, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBox1)
                            .addComponent(EnviarMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bMultiShotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bMultiShotActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bMultiShotActionPerformed

    private void EnviarMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EnviarMensajeActionPerformed
        // TODO add your handling code here:
        ArrayList <Object> arrayAEnviar = new ArrayList();
        this.jugadorPropio.realizarPeticion(new Mensaje(TipoMensaje.enviarMensaje, this.TextFieldChat.getText()));
    }//GEN-LAST:event_EnviarMensajeActionPerformed

    private void EncolarDuoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EncolarDuoActionPerformed
        // TODO add your handling code here:
        ArrayList <Object> arrayAEnviar = new ArrayList();
        arrayAEnviar.add(this.jugadorPropio);
        arrayAEnviar.add(2);
        this.jugadorPropio.realizarPeticion(new Mensaje(TipoMensaje.unirseACola, arrayAEnviar));
    }//GEN-LAST:event_EncolarDuoActionPerformed

    private void EncolarTrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EncolarTrioActionPerformed
        // TODO add your handling code here:
        ArrayList <Object> arrayAEnviar = new ArrayList();
        arrayAEnviar.add(this);
        arrayAEnviar.add(3);
        this.jugadorPropio.realizarPeticion(new Mensaje(TipoMensaje.unirseACola, arrayAEnviar));
    }//GEN-LAST:event_EncolarTrioActionPerformed

    private void EncolarCuartetoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EncolarCuartetoActionPerformed
        // TODO add your handling code here:
        ArrayList <Object> arrayAEnviar = new ArrayList();
        arrayAEnviar.add(this);
        arrayAEnviar.add(4);
        this.jugadorPropio.realizarPeticion(new Mensaje(TipoMensaje.unirseACola, arrayAEnviar));
    }//GEN-LAST:event_EncolarCuartetoActionPerformed

    private void cambiarHostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cambiarHostActionPerformed
        // TODO add your handling code here:
        this.jugadorPropio.setDireccionServidor((String)JOptionPane.showInputDialog("Ingrese el nuevo huésped"));
        boolean offline = !(boolean)(this.jugadorPropio.realizarPeticion(new Mensaje(TipoMensaje.activado, null)).getDatoDeRespuesta());
        this.Desconectado.setVisible(offline);
        this.cambiarHost.setVisible(offline);
    }//GEN-LAST:event_cambiarHostActionPerformed

    private void FabricaComprarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FabricaComprarActionPerformed
        // TODO add your handling code here:
        if(this.Mundo.getSelectedColumn() != -1 && this.Mundo.getSelectedRow() != -1){
            //había alguna localización seleccionada
            //Object objetoEnCelda = this.Mundo.getModel().getValueAt(this.Mundo.getSelectedRow(), this.Mundo.getSelectedColumn());
            int xSeleccionado = this.Mundo.getSelectedRow();
            int ySeleccionado = this.Mundo.getSelectedColumn();
            Object [][] datosTabla = this.jugadorPropio.getGrafoPropio().generarMatriz();
            if((TipoFabrica)datosTabla[xSeleccionado][ySeleccionado] == TipoFabrica.BLANK){
                ArrayList <Object> datosAEnviar = new ArrayList<>();
                datosAEnviar.add(this.jugadorPropio.getNombreJugador());
                Conector conector = null;
                boolean orientacionFabrica = this.Orientacion.isSelected();
                if(this.TipoFabricaComprar.getSelectedIndex() < 5){
                    conector = (Conector)JOptionPane.showInputDialog(null, "Elija un conector",
                                "Conectar Elemento", JOptionPane.QUESTION_MESSAGE, null,
                                this.jugadorPropio.getGrafoPropio().getConectoresDisponibles().toArray(), 0);
                    /*orientacionFabrica = (Orientacion)JOptionPane.showInputDialog(null, "Elija la orientación de la fábrica",
                                "Orientación de la Fábrica", JOptionPane.QUESTION_MESSAGE, null,
                                Orientacion.values(), 0);
                    */
                }
                switch(this.TipoFabricaComprar.getSelectedIndex()){                    
                    case 0:{
                        //Armeria
                        Armeria armeria = new Armeria(orientacionFabrica, xSeleccionado, ySeleccionado);
                        datosAEnviar.add(armeria);
                        datosAEnviar.add(conector.getCoordenadas());
                        break;
                    }
                    case 1:{
                        //Mina
                        int cantidadAcero = 0;
                        int cadenciaAcero = 0;
                        while(true){
                            try{
                                cantidadAcero = Integer.parseInt((String)JOptionPane.showInputDialog("Seleccione la cantidad de acero que hará la mina", 50));
                                cadenciaAcero = Integer.parseInt(JOptionPane.showInputDialog("Seleccione la cantidad de tiempo que le tomará hacerlos (segundos)", 60));
                                break;
                            }catch(NullPointerException | NumberFormatException exc){
                                JOptionPane.showMessageDialog(null, "Ingrese un número válido", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        Mina mina = new Mina(orientacionFabrica, xSeleccionado, ySeleccionado, this.jugadorPropio, cadenciaAcero, cantidadAcero);// new Mina(cantidadAcero, cadenciaAcero, orientacionFabrica, xSeleccionado, ySeleccionado);
                        datosAEnviar.add(mina);
                        datosAEnviar.add(conector.getCoordenadas());
                        break;
                    }
                    case 2:{
                        //Mercado
                        Mercado mercado = new Mercado(orientacionFabrica, xSeleccionado, ySeleccionado);
                        datosAEnviar.add(mercado);
                        datosAEnviar.add(conector.getCoordenadas());
                        break;
                    }
                    case 3:{
                        //Templo
                        Templo templo = new Templo(orientacionFabrica, xSeleccionado, ySeleccionado);
                        datosAEnviar.add(templo);
                        datosAEnviar.add(conector.getCoordenadas());
                        break;
                    }
                    case 4:{
                        //Fuente de energía
                        int cantidadDeEnergia = 10;
                        try{
                            cantidadDeEnergia = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad de energía que tendrá la fuente de energía"));
                        }catch(NumberFormatException ex){}
                        
                        FuenteEnergia mundo = new FuenteEnergia(cantidadDeEnergia,xSeleccionado, ySeleccionado);
                        datosAEnviar.add(mundo);
                        break;
                    }
                    case 5:{
                        //Conector
                        FuenteEnergia fuenteEnergia = (FuenteEnergia)JOptionPane.showInputDialog(null, "Elija un mundo",
                                "Conectar Elemento", JOptionPane.QUESTION_MESSAGE, null,
                                this.jugadorPropio.getGrafoPropio().getFuentesEnergiaDisponibles().toArray(), 0);
                        Conector conectorNuevo = new Conector(xSeleccionado, ySeleccionado);
                        datosAEnviar.add(conectorNuevo);
                        datosAEnviar.add(fuenteEnergia.getCoordenadas());
                        break;
                    }
                }
                this.jugadorPropio.realizarPeticion(new Mensaje(TipoMensaje.nuevoElemento, datosAEnviar));
            }
        }
    }//GEN-LAST:event_FabricaComprarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrameGuerraIslas().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Desconectado;
    private javax.swing.JButton EncolarCuarteto;
    private javax.swing.JButton EncolarDuo;
    private javax.swing.JButton EncolarTrio;
    private javax.swing.JButton EnviarMensaje;
    private javax.swing.JButton FabricaComprar;
    private javax.swing.JLabel LabelTurno;
    private javax.swing.JTable Mundo;
    private javax.swing.JButton MundoAnterior;
    private javax.swing.JButton MundoSiguiente;
    private javax.swing.JLabel NumeroMundo;
    private javax.swing.JCheckBox Orientacion;
    private javax.swing.JTextArea TextAreaChat;
    private javax.swing.JTextField TextFieldChat;
    private javax.swing.JComboBox<String> TipoFabricaComprar;
    private javax.swing.JButton bBomba;
    private javax.swing.JButton bComboShot;
    private javax.swing.JButton bMisil;
    private javax.swing.JButton bMultiShot;
    private javax.swing.JButton cambiarHost;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lDineroActual;
    // End of variables declaration//GEN-END:variables

    public void actualizarTablero(Mensaje mensajeConActualizaciones){
        try {
            //aquí se van a actualizar los cambios que se hayan hecho
            Partida partidaRecibida = (Partida)mensajeConActualizaciones.getDatoDeRespuesta();
            this.jugadorPropio = partidaRecibida.getJugadoresPartida().get(partidaRecibida.getJugadoresPartida().indexOf(new Jugador(this.jugadorPropio.getNombreJugador())));
            int i = 0;
            for (int j = 0; j < enemigos.size(); j++) {
                Jugador get = enemigos.get(j);
                this.enemigos.set(i, get);
                i++;
            }
            if(this.tableroActual == this.jugadorPropio.getNumeroJugador()){
                this.dibujarTablero(this.jugadorPropio.getGrafoPropio().generarMatriz());
            }
            else{
                for (int j = 0; j < enemigos.size(); j++) {
                    Jugador get = enemigos.get(j);
                    if(get.getNumeroJugador() == this.tableroActual){
                        this.dibujarTablero(get.getGrafoPropio().generarMatriz());
                        break;
                    }
                }
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(JFrameGuerraIslas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mostrarMensajeEnChat(String mensajeConActualizaciones){
        //aquí se va a actualizar el chatsito
        this.TextAreaChat.setText(this.TextAreaChat.getText().concat("\n".concat(mensajeConActualizaciones)));
    }
    
    
}
