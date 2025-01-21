package Menu_P;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Partido {
    private String equipoLocal;
    private String equipoVisitante;
    private String marcador;
    private int amarillas;
    private int rojas;

    public Partido(String equipoLocal, String equipoVisitante, String marcador, int amarillas, int rojas) {
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.marcador = marcador;
        this.amarillas = amarillas;
        this.rojas = rojas;
    }

    public String getEquipoLocal() {
        return equipoLocal;
    }

    public String getEquipoVisitante() {
        return equipoVisitante;
    }

    public String getMarcador() {
        return marcador;
    }

    public int getAmarillas() {
        return amarillas;
    }

    public int getRojas() {
        return rojas;
    }
}

public class Panel_Partidos extends JPanel implements ActionListener {
    private List<Partido> listaPartidos;
    private DefaultTableModel modeloTabla5;
    private JComboBox<String> comboLocal, comboVisitante;
    private JTextField textoMarcadorLocal, textoMarcadorVisitante;
    private JTextField textoAmarillas, textoRojas;
    private JButton botonRegistrarPartido,botonActualizarEquipos;;
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

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonRegistrarPartido) {
            String nombreLocal = (String) comboLocal.getSelectedItem();
            String nombreVisitante = (String) comboVisitante.getSelectedItem();
            String marcadorLocal = textoMarcadorLocal.getText().trim();
            String marcadorVisitante = textoMarcadorVisitante.getText().trim();
            String amarillasTexto = textoAmarillas.getText().trim();
            String rojasTexto = textoRojas.getText().trim();

            if (nombreLocal == null || nombreVisitante == null || marcadorLocal.isEmpty() || marcadorVisitante.isEmpty() || amarillasTexto.isEmpty() || rojasTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (nombreLocal.equals(nombreVisitante)) {
                JOptionPane.showMessageDialog(this, "El equipo local y visitante no pueden ser iguales.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int marcadorL = Integer.parseInt(marcadorLocal);
                int marcadorV = Integer.parseInt(marcadorVisitante);
                int amarillas = Integer.parseInt(amarillasTexto);
                int rojas = Integer.parseInt(rojasTexto);

                String marcador = marcadorL + " - " + marcadorV;

                listaPartidos.add(new Partido(nombreLocal, nombreVisitante, marcador, amarillas, rojas));
                modeloTabla5.addRow(new Object[]{nombreLocal, nombreVisitante, marcador, amarillas, rojas});
                guardarPartidos();

                textoMarcadorLocal.setText("");
                textoMarcadorVisitante.setText("");
                textoAmarillas.setText("");
                textoRojas.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese números válidos para los marcadores y tarjetas.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }else if (e.getSource() == botonActualizarEquipos) {
            actualizarComboEquipos(); // Actualizar los ComboBox de equipos
        }
    }

    private void guardarPartidos() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tabla_partidos.txt"))) {
            for (int i = 0; i < modeloTabla5.getRowCount(); i++) {
                writer.write(
                        modeloTabla5.getValueAt(i, 0) + "," +
                        modeloTabla5.getValueAt(i, 1) + "," +
                        modeloTabla5.getValueAt(i, 2) + "," +
                        modeloTabla5.getValueAt(i, 3) + "," +
                        modeloTabla5.getValueAt(i, 4));
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Partidos guardados correctamente.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar los partidos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarPartidos() {
        try (BufferedReader reader = new BufferedReader(new FileReader("tabla_partidos.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] datos = line.split(",");
                if (datos.length == 5) {
                    modeloTabla5.addRow(datos);
                }
            }
        } catch (IOException ex) {
            // Ignorar si el archivo no existe
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

    private void cargarDatosDesdeArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("tabla_partidos.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                modeloTabla5.addRow(data);

                // Agregar presidentes del archivo al ComboBox externo
                if (data[1].equals("Presidente")) {
                    comboLocal.addItem(data[0]);
                }
            }
            //JOptionPane.showMessageDialog(this, "Datos cargados exitosamente.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla5;
    }
}
