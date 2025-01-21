package Menu_P;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class MenuFrame extends JFrame implements ActionListener {
    private JMenuBar mibarra;
    private JMenu acerca, archivo, editar, logo;
    private JMenuItem info_p, participantes, salir, p_principal;
    private JMenuItem nuevo_op1, nuevo_op2, nuevo_op3, nuevo_op4,nuevo_op5;
    private JMenuItem consult_op1, consult_op2, consult_op3, consult_op4,consult_op5;
    private JLabel m_general,labelPrincipal;

    private Panel_Jugador formularioPanel;
    private Panel_Equipo formularioPanel2;
    private PanelArbitro formularioPanel3;
    private PanelDirigente formularioPanel4;
    private Panel_Partidos formularioPanel5;

    private DefaultTableModel modeloTablaOculta;
    private JTable tablaOculta;


    private int contadorJugadores = 1;
    private Map<String, Integer> contadoresPorEquipo; // Mapa para los contadores de cada equipo

    public MenuFrame() {

        contadoresPorEquipo = new HashMap<>();
        setTitle("Programa de Campeonato de Fútbol");
        setLayout(new BorderLayout());
        setBounds(100, 100, 1150, 1500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DefaultTableModel modeloTabla1 = new DefaultTableModel();
        DefaultTableModel modeloTabla2 = new DefaultTableModel();
        DefaultTableModel modeloTabla3 = new DefaultTableModel();
        DefaultTableModel modeloTabla4 = new DefaultTableModel();
        DefaultTableModel modeloTabla5 = new DefaultTableModel();
        // Inicializar la tabla oculta
        modeloTablaOculta = new DefaultTableModel();
        modeloTablaOculta.addColumn("Nombre del Equipo");
        modeloTablaOculta.addColumn("Categoría");
        modeloTablaOculta.addColumn("Nombre del Jugador");
        modeloTablaOculta.addColumn("Número de Jugador");

        tablaOculta = new JTable(modeloTablaOculta);
        tablaOculta.setPreferredScrollableViewportSize(new Dimension(0, 0));
        // Crear panel oculto para la tabla
        JPanel panelOculto = new JPanel();
        panelOculto.add(tablaOculta);
        // Añadir el panel oculto al JFrame fuera de la vista
        add(panelOculto, BorderLayout.SOUTH);
        panelOculto.setVisible(false);

        cargarDatosDesdeArchivo();
        JComboBox<String> cmbPresidentesExternos = new JComboBox<>();

        formularioPanel = new Panel_Jugador(modeloTabla1, this);
        add(formularioPanel);
        formularioPanel2 = new Panel_Equipo(modeloTabla2, formularioPanel, this);
        add(formularioPanel2);
        formularioPanel3 = new PanelArbitro(modeloTabla3);
        add(formularioPanel3);
        formularioPanel4 = new PanelDirigente(modeloTabla4, cmbPresidentesExternos);
        add(formularioPanel4);
        formularioPanel5 = new Panel_Partidos(modeloTabla5);
        add(formularioPanel5);


        // Panel inicial
        m_general = new JLabel("");
        labelPrincipal = new JLabel("BIENVENIDO AL PROGRAMA DE EQUIPOS");
        add(labelPrincipal);
        labelPrincipal.setVisible(true);
        labelPrincipal.setBounds(290,400,1000,100);
        labelPrincipal.setFont(new Font("OCR A Extended", Font.PLAIN, 25));

        ImageIcon imagen = new ImageIcon("src/IMAGENES/IMAGEN GRUPO3.jpg");
        JLabel labelFoto = new JLabel(imagen);
        labelFoto.setBounds(175, 0, 750, 500);
        m_general.add(labelFoto);
        m_general.setVisible(true);
        add(m_general, BorderLayout.CENTER);

        // Configurar la barra de menú
        configurarMenu();
        // Guardar datos al cerrar
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                guardarDatosEnArchivo();
            }
        });
        setVisible(true);
    }

    private void configurarMenu() {
        mibarra = new JMenuBar();

        archivo = new JMenu("  Archivo  ");
        editar = new JMenu("  Editar  ");
        acerca = new JMenu("  Acerca de...  ");
        logo = new JMenu("   ");

        JMenu nuevo = new JMenu("Nuevo  ");
        nuevo_op1 = new JMenuItem("Jugador");
        nuevo_op1.addActionListener(this);
        nuevo_op2 = new JMenuItem("Equipo");
        nuevo_op2.addActionListener(this);
        nuevo_op3 = new JMenuItem("Arbitro");
        nuevo_op3.addActionListener(this);
        nuevo_op4 = new JMenuItem("Dirigente");
        nuevo_op4.addActionListener(this);
        nuevo_op5 = new JMenuItem("Partido");
        nuevo_op5.addActionListener(this);

        salir = new JMenuItem("Salir");
        salir.addActionListener(this);

        p_principal = new JMenuItem("Inicio");
        p_principal.addActionListener(this);

        info_p = new JMenuItem("Información del Programa");
        info_p.addActionListener(this);

        participantes = new JMenuItem("Autores");
        participantes.addActionListener(this);

        JMenu consulta = new JMenu("Consulta  ");
        consult_op1 = new JMenuItem("Jugadores");
        consult_op1.addActionListener(this);
        consult_op2 = new JMenuItem("Equipos");
        consult_op2.addActionListener(this);
        consult_op3 = new JMenuItem("Árbitros");
        consult_op3.addActionListener(this);
        consult_op4 = new JMenuItem("Dirigentes");
        consult_op5 = new JMenuItem("Partidos");
        consult_op5.addActionListener(this);

        mibarra.add(logo);
        mibarra.add(archivo);
        mibarra.add(editar);
        mibarra.add(acerca);

        archivo.add(p_principal);
        archivo.add(nuevo);
        archivo.add(salir);

        nuevo.add(nuevo_op1);
        nuevo.add(nuevo_op2);
        nuevo.add(nuevo_op3);
        nuevo.add(nuevo_op4);
        nuevo.add(nuevo_op5);

        editar.add(consulta);
        consulta.add(consult_op1);
        consulta.add(consult_op2);
        consulta.add(consult_op3);
        consulta.add(consult_op4);
        consulta.add(consult_op5);

        acerca.add(info_p);
        acerca.add(participantes);

        setJMenuBar(mibarra);
    }

    public int obtenerNumeroJugador(String nombreEquipo) {
        int contador = contadoresPorEquipo.getOrDefault(nombreEquipo, 0) + 1;
        contadoresPorEquipo.put(nombreEquipo, contador);
        System.out.println("Número de jugador asignado para el equipo " + nombreEquipo + ": " + contador);
        return contador;
    }

    public void agregarFilaTablaOculta(Object[] data) {
        modeloTablaOculta.addRow(data);
        guardarDatosEnArchivo(); // Guardar los datos cada vez que se agregue una fila nueva
    }

    public void guardarDatosEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tabla_equipos.txt"))) {
            for (int i = 0; i < modeloTablaOculta.getRowCount(); i++) {
                for (int j = 0; j < modeloTablaOculta.getColumnCount(); j++) {
                    writer.write(modeloTablaOculta.getValueAt(i, j).toString());
                    if (j < modeloTablaOculta.getColumnCount() - 1) {
                        writer.write(",");
                    }
                }
                writer.newLine();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar los datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatosDesdeArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("tabla_equipos.txt"))) {
            String line;

            // Leer las líneas para cargar los datos y encontrar el mayor número de jugador por equipo
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                // Eliminar posibles espacios en blanco
                for (int i = 0; i < data.length; i++) {
                    data[i] = data[i].trim();
                }

                // Verificar que la línea tenga al menos 3 columnas
                if (data.length >= 3) {
                    String nombreEquipo = data[0];

                    // Añadir un número de jugador si falta
                    if (data.length < 4) {
                        int contador = contadoresPorEquipo.getOrDefault(nombreEquipo, 0) + 1;
                        contadoresPorEquipo.put(nombreEquipo, contador);
                        data = Arrays.copyOf(data, 4);
                        data[3] = String.valueOf(contadoresPorEquipo.get(nombreEquipo));
                    } else {
                        try {
                            // Verificar el número de jugador en el índice correcto
                            int numeroJugador = Integer.parseInt(data[3]);
                            contadoresPorEquipo.put(nombreEquipo, Math.max(contadoresPorEquipo.getOrDefault(nombreEquipo, 0), numeroJugador));
                        } catch (NumberFormatException ex) {
                            System.out.println("Error al leer el número de jugador en la línea: " + line);
                            int contador = contadoresPorEquipo.getOrDefault(nombreEquipo, 0) + 1;
                            contadoresPorEquipo.put(nombreEquipo, contador);
                            data[3] = String.valueOf(contadoresPorEquipo.get(nombreEquipo));
                        }
                    }
                    modeloTablaOculta.addRow(data);
                } else {
                    System.out.println("Línea de datos inválida (menos de 3 columnas): " + line);  // Mensaje de depuración detallado
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        System.out.println("Contadores de jugadores por equipo: " + contadoresPorEquipo);  // Mensaje de depuración
    }



    public DefaultTableModel getModeloTablaOculta() {
        return modeloTablaOculta;
    }
    public JTable getTablaOculta() {
        return tablaOculta;
    }
    public JScrollPane getScrollPaneOculto() {
        return new JScrollPane(tablaOculta);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nuevo_op1) {
            mostrarPanel(formularioPanel);
        } else if (e.getSource() == nuevo_op2) {
            mostrarPanel(formularioPanel2);
        } else if (e.getSource() == nuevo_op3) {
            mostrarPanel(formularioPanel3);
        } else if (e.getSource() == nuevo_op4) {
            mostrarPanel(formularioPanel4);
        }else if (e.getSource() == nuevo_op5) {
            mostrarPanel(formularioPanel5);
        } else if (e.getSource() == salir) {
            System.exit(0);
        } else if (e.getSource() == p_principal) {
            getContentPane().removeAll();
            add(m_general, BorderLayout.CENTER);
            revalidate();
            repaint();
        } else if (e.getSource() == info_p) {
            JOptionPane.showMessageDialog(this,
                    "Programa de campeonato creado por el Grupo C\nMateria: Programación Orientada a Objetos.\nMateria impartida por el Ing. Crespo Mendoza Roberto.",
                    "Información del Programa", JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getSource() == participantes) {
            JOptionPane.showMessageDialog(this,
                    "Jose Agapito Hernandez Vega\nMiguel Eduardo Loyola Mora\nGary Johao Zuñiga Saltos\nCristhian Alejandro Gavilanes Sanchez\nChristian Alexander Bone Arias",
                    "Autores", JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getSource() == consult_op1) {
            ConsultaPanel1 consultaJugadores = new ConsultaPanel1(formularioPanel.getModeloTabla(), "DATOS DE LOS JUGADORES");
            mostrarPanel(consultaJugadores);
        } else if (e.getSource() == consult_op2) {
            ConsultaPanel2 consultaEquipos = new ConsultaPanel2(formularioPanel2.getModeloTabla(), "DATOS DE LOS EQUIPOS");
            mostrarPanel(consultaEquipos);
        } else if (e.getSource() == consult_op3) {
            ConsultaPanel3 consultaArbitros = new ConsultaPanel3(formularioPanel3.getModeloTabla(), "DATOS DE LOS ÁRBITROS");
            mostrarPanel(consultaArbitros);
        } else if (e.getSource() == consult_op4) {
            ConsultaPanel4 consultaDirigentes = new ConsultaPanel4(formularioPanel4.getModeloTabla(), "DATOS DE LOS DIRIGENTES");
            mostrarPanel(consultaDirigentes);
        }else if (e.getSource() == consult_op5) {
            ConsultaPanel5 consultaPartidos = new ConsultaPanel5(formularioPanel5.getModeloTabla(), "DATOS DE LOS PARTIDOS");
            mostrarPanel(consultaPartidos);
            this.setTitle("Programa de Campeonato de Fútbol - Editar - Consulta - Partidos ");
        }
    }

    private void mostrarPanel(JPanel panel) {
        getContentPane().removeAll();
        add(panel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

}