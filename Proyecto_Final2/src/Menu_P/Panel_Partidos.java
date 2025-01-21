package Menu_P;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Partido {
    private String nombreLocal;
    private String nombreVisitante;
    private String marcador;
    private int amarillas;
    private int rojas;

    public Partido(String nombreLocal, String nombreVisitante, String marcador, int amarillas, int rojas) {
        this.nombreLocal = nombreLocal;
        this.nombreVisitante = nombreVisitante;
        this.marcador = marcador;
        this.amarillas = amarillas;
        this.rojas = rojas;
    }

    // Getters y Setters
    public String getNombreLocal() {
        return nombreLocal;
    }

    public void setNombreLocal(String nombreLocal) {
        this.nombreLocal = nombreLocal;
    }

    public String getNombreVisitante() {
        return nombreVisitante;
    }

    public void setNombreVisitante(String nombreVisitante) {
        this.nombreVisitante = nombreVisitante;
    }

    public String getMarcador() {
        return marcador;
    }

    public void setMarcador(String marcador) {
        this.marcador = marcador;
    }

    public int getAmarillas() {
        return amarillas;
    }

    public void setAmarillas(int amarillas) {
        this.amarillas = amarillas;
    }

    public int getRojas() {
        return rojas;
    }

    public void setRojas(int rojas) {
        this.rojas = rojas;
    }
}


public class Panel_Partidos extends JPanel implements ActionListener {
    private List<Partido> listaPartidos;
    private JComboBox<String> comboLocal, comboVisitante;
    private JTextField textoMarcadorLocal, textoMarcadorVisitante;
    private JTextField textoAmarillas, textoRojas;
    private JButton botonRegistrarPartido,botonActualizarEquipos;
    private DefaultTableModel modeloTabla5;
    private JTable tablaPartidos;
    private JScrollPane scrollPane;

    public Panel_Partidos(DefaultTableModel modeloTabla5) {
        this.listaPartidos = new ArrayList<>();
        setLayout(null);

        JLabel labelLocal = new JLabel("Equipo Local:");
        labelLocal.setBounds(50, 20, 100, 20);
        add(labelLocal);

        JLabel labelVisitante = new JLabel("Equipo Visitante:");
        labelVisitante.setBounds(350, 20, 120, 20);
        add(labelVisitante);

        comboLocal = new JComboBox<>();
        comboLocal.setBounds(150, 20, 150, 20);
        add(comboLocal);

        comboVisitante = new JComboBox<>();
        comboVisitante.setBounds(470, 20, 150, 20);
        add(comboVisitante);

        JLabel labelMarcador = new JLabel("Marcador (Local - Visitante):");
        labelMarcador.setBounds(50, 60, 200, 20);
        add(labelMarcador);

        textoMarcadorLocal = new JTextField();
        textoMarcadorLocal.setBounds(250, 60, 50, 20);
        add(textoMarcadorLocal);

        textoMarcadorVisitante = new JTextField();
        textoMarcadorVisitante.setBounds(310, 60, 50, 20);
        add(textoMarcadorVisitante);

        JLabel labelAmarillas = new JLabel("Amarillas:");
        labelAmarillas.setBounds(390, 60, 100, 20);
        add(labelAmarillas);

        textoAmarillas = new JTextField();
        textoAmarillas.setBounds(480, 60, 50, 20);
        add(textoAmarillas);

        JLabel labelRojas = new JLabel("Rojas:");
        labelRojas.setBounds(550, 60, 50, 20);
        add(labelRojas);

        textoRojas = new JTextField();
        textoRojas.setBounds(600, 60, 50, 20);
        add(textoRojas);

        botonRegistrarPartido = new JButton("Registrar Partido");
        botonRegistrarPartido.setBounds(680, 55, 250, 30);
        add(botonRegistrarPartido);
        botonRegistrarPartido.addActionListener(this);

        // Botón para actualizar equipos
        botonActualizarEquipos = new JButton("Actualizar Equipos");
        botonActualizarEquipos.setBounds(630, 20, 150, 30);
        add(botonActualizarEquipos);
        botonActualizarEquipos.addActionListener(this);

        modeloTabla5 = new DefaultTableModel();
        modeloTabla5.addColumn("Local");
        modeloTabla5.addColumn("Visitante");
        modeloTabla5.addColumn("Marcador");
        modeloTabla5.addColumn("Amarillas");
        modeloTabla5.addColumn("Rojas");

        this.modeloTabla5 = modeloTabla5;
        tablaPartidos = new JTable(modeloTabla5);
        tablaPartidos.getTableHeader().setReorderingAllowed(false);
        scrollPane = new JScrollPane(tablaPartidos);
        scrollPane.setBounds(150, 130, 750, 320);
        add(scrollPane);

        cargarPartidos();
        actualizarComboEquipos();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonRegistrarPartido) {
            String nombreLocal = (String) comboLocal.getSelectedItem();
            String nombreVisitante = (String) comboVisitante.getSelectedItem();
            String marcadorLocal = textoMarcadorLocal.getText().trim();
            String marcadorVisitante = textoMarcadorVisitante.getText().trim();

            if (nombreLocal == null || nombreVisitante == null || marcadorLocal.isEmpty() || marcadorVisitante.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos del partido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (nombreLocal.equals(nombreVisitante)) {
                JOptionPane.showMessageDialog(this, "El equipo local y visitante no pueden ser iguales.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                MenuFrame menuFrame = (MenuFrame) SwingUtilities.getWindowAncestor(this);
                DialogoSeleccionJugadores dialogo = new DialogoSeleccionJugadores((JFrame) menuFrame, menuFrame, this, nombreLocal, nombreVisitante);
                dialogo.setVisible(true);

                // Procesar el registro del partido
                int marcadorL = Integer.parseInt(marcadorLocal);
                int marcadorV = Integer.parseInt(marcadorVisitante);
                String marcador = marcadorL + " - " + marcadorV;

                int amarillas = Integer.parseInt(textoAmarillas.getText().trim());
                int rojas = Integer.parseInt(textoRojas.getText().trim());
                listaPartidos.add(new Partido(nombreLocal, nombreVisitante, marcador, amarillas, rojas));
                modeloTabla5.addRow(new Object[]{nombreLocal, nombreVisitante, marcador, amarillas, rojas});
                guardarPartidos();

                textoMarcadorLocal.setText("");
                textoMarcadorVisitante.setText("");
                textoAmarillas.setText("");
                textoRojas.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese números válidos para los marcadores.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == botonActualizarEquipos) {
            actualizarComboEquipos(); // Actualizar los ComboBox de equipos
        }
    }



    // Método para actualizar tarjetas de un jugador
    public static void actualizarTarjetasJugador(String nombreJugador, int amarillas, int rojas) {
        List<Jugador> jugadores = cargarJugadoresDesdeArchivo(); // Cargar jugadores desde archivo
        // Buscar y actualizar el jugador específico
        for (Jugador jugador : jugadores) {
            if (jugador.getNombre().equals(nombreJugador)) {
                jugador.setTarjetasAmarillas(jugador.getTarjetasAmarillas() + amarillas);
                jugador.setTarjetasRojas(jugador.getTarjetasRojas() + rojas);
                break;
            }
        }
        guardarJugadoresEnArchivo(jugadores); // Guardar los jugadores actualizados
    }


    // Método para cargar jugadores desde el archivo
    public static List<Jugador> cargarJugadoresDesdeArchivo() {
        List<Jugador> jugadores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("tabla_jugadores.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] datos = line.split(",");
                if (datos.length == 9) {
                    jugadores.add(new Jugador(datos[0], Integer.parseInt(datos[1]), datos[2], datos[3],
                            Integer.parseInt(datos[4]), Integer.parseInt(datos[5]), Integer.parseInt(datos[6]),
                            Integer.parseInt(datos[7]), datos[8]));
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar los jugadores: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return jugadores;
    }

    // Método para guardar jugadores en archivo
    public static void guardarJugadoresEnArchivo(List<Jugador> jugadores) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tabla_jugadores.txt"))) {
            for (Jugador jugador : jugadores) {
                writer.write(jugador.getNombre() + "," + jugador.getEdad() + "," + jugador.getCedula() + "," +
                        jugador.getPosicion() + "," + jugador.getGoles() + "," + jugador.getTarjetasAmarillas() + "," +
                        jugador.getTarjetasRojas() + "," + jugador.getMinutosJugados() + "," + jugador.getFechaContrato());
                writer.newLine();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar los jugadores: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }




    private void guardarPartidos() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tabla_partidos.txt"))) {
            for (Partido partido : listaPartidos) {
                writer.write(partido.getNombreLocal() + "," + partido.getNombreVisitante() + "," +
                        partido.getMarcador() + "," + partido.getAmarillas() + "," + partido.getRojas());
                writer.newLine();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar los partidos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void cargarPartidos() {
        listaPartidos.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("tabla_partidos.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] datos = line.split(",");
                if (datos.length == 5) { // Asegúrate de que el archivo tenga 5 columnas
                    Partido partido = new Partido(datos[0], datos[1], datos[2],
                            Integer.parseInt(datos[3]), Integer.parseInt(datos[4]));
                    listaPartidos.add(partido); modeloTabla5.addRow(new Object[]{datos[0], datos[1], datos[2], datos[3], datos[4]});
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los partidos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static List<String> cargarNombresEquipos() {
        List<String> nombresEquipos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("tabla_equipos.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 0) {
                    nombresEquipos.add(data[0]); // Solo agregar el nombre del equipo
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return nombresEquipos;
    }

    // Método para actualizar el ComboBox con los nombres de los equipos
    public void actualizarComboEquipos() {
        if (comboLocal == null) {
            comboLocal = new JComboBox<>();
            comboLocal.setBounds(150, 20, 150, 20);
            add(comboLocal);
        }

        if (comboVisitante == null) {
            comboVisitante = new JComboBox<>();
            comboVisitante.setBounds(350, 20, 120, 20);
            add(comboVisitante);
        }
        comboLocal.removeAllItems();
        comboVisitante.removeAllItems();
        // Cargar datos desde el archivo de nombres de equipos
        List<String> nombresEquipos = cargarNombresEquipos();
        for (String nombre : nombresEquipos) {
            comboLocal.addItem(nombre.trim());
        }
        for (String nombre : nombresEquipos) {
            comboVisitante.addItem(nombre.trim());
        }
    }

    public void agregarEquipoEnComboBox(String nombre) {
        System.out.println("Agregando equipo al ComboBox: " + nombre);

        // Mensaje de depuración
        comboLocal.addItem(nombre); // Agregar el nombre del equipo al ComboBox
        comboVisitante.addItem(nombre);
    }
    public DefaultTableModel getModeloTabla() {
        return modeloTabla5;
    }
}
