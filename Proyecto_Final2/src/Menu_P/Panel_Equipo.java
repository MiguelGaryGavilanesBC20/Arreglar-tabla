package Menu_P;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Equipo {
    private String nombre;
    private String categoria;
    private String presidente;
    private List<Jugador> listaJugadores;

    public Equipo(String nombre, String categoria, String presidente) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.presidente = presidente;
        // this.listaJugadores = new ArrayList<>();
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPresidente() {
        return presidente;
    }

    public void setPresidente(String presidente) {
        this.presidente = presidente;
    }

    public List<Jugador> getJugadores() {
        return listaJugadores;
    }

}


public class Panel_Equipo extends JPanel implements ActionListener {

    private JButton botonRegistrar, botonRegistrarPresidente,botonActualizarPresidentes;
    private JLabel label1, label3, label4, labelPresidente;
    private JTextField texto1, textoPresidenteNombre;
    private DefaultTableModel modeloTabla2;
    private JTable tabla;
    private JScrollPane scrollPane;
    private JComboBox<String>  comboCategoria, comboPresidente;
    //private List<Jugador> listaJugadores;
    private List<Equipo>listaEquipos;
    private Panel_Jugador panelJugador;
    private MenuFrame menuFrame;

    public Panel_Equipo(DefaultTableModel modeloTabla2, Panel_Jugador panelJugador, MenuFrame menuFrame) {
        System.out.println("Inicializando Panel_Equipo"); // Mensaje de depuración
        this.listaEquipos = new ArrayList<>();
        this.modeloTabla2 = modeloTabla2;
        this.panelJugador = panelJugador; // Guardamos la referencia al Panel_Jugador
        this.menuFrame = menuFrame;
        setLayout(null);

        // Campo para registrar el equipo
        label1 = new JLabel("Nombre del Equipo");
        label1.setBounds(50, 20, 120, 20);
        add(label1);

        texto1 = new JTextField();
        texto1.setBounds(180, 20, 170, 20);
        add(texto1);

        label3 = new JLabel("Categoría");
        label3.setBounds(370, 20, 80, 20);
        add(label3);

        comboCategoria = new JComboBox<>(new String[]{"Primera", "Segunda"});
        comboCategoria.setBounds(450, 20, 100, 20);
        add(comboCategoria);

        label4 = new JLabel("Presidente");
        label4.setBounds(50, 50, 80, 20);
        add(label4);


        actualizarComboPresidente();  // Cargar presidentes del archivo

        botonRegistrar = new JButton("Registrar Equipo");
        botonRegistrar.setBounds(370, 45, 180, 30);
        add(botonRegistrar);
        botonRegistrar.addActionListener(this);

       /*  botonActualizar = new JButton("Actualizar");
        botonActualizar.setBounds(300, 50, 100, 30);
        add(botonActualizar);
        botonActualizar.addActionListener(this);*/

        // Campos para registrar presidente
        labelPresidente = new JLabel("Nuevo Presidente");
        labelPresidente.setBounds(580, 20, 150, 20);
        add(labelPresidente);

        textoPresidenteNombre = new JTextField();
        textoPresidenteNombre.setBounds(700, 20, 130, 20);
        add(textoPresidenteNombre);

        botonRegistrarPresidente = new JButton("Registrar Presidente");
        botonRegistrarPresidente.setBounds(850, 15, 160, 30);
        add(botonRegistrarPresidente);
        botonRegistrarPresidente.addActionListener(this);

        // Botón para actualizar los presidentes en el ComboBox
        botonActualizarPresidentes = new JButton("Actualizar Presidentes");
        botonActualizarPresidentes.setBounds(850, 60, 160, 30);
        add(botonActualizarPresidentes);
        botonActualizarPresidentes.addActionListener(this);

        // Tabla para equipos
        this.modeloTabla2 = modeloTabla2;
        modeloTabla2.addColumn("Nombre");
        modeloTabla2.addColumn("Categoría");
        modeloTabla2.addColumn("Presidente");

        tabla = new JTable(modeloTabla2);
        scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(200, 100, 650, 320);
        add(scrollPane);

        cargarDatosDesdeArchivo();
    }


    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == botonRegistrar) {

            String nombre = primeraletra(texto1.getText());
            String categoria = obtenerSeleccionComboBox(comboCategoria);
            String presidente = obtenerSeleccionComboBox(comboPresidente);

            if (nombre.isEmpty() || categoria.isEmpty() || presidente.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.",
                        "Error al agregar los datos", JOptionPane.ERROR_MESSAGE);
            } else {


                listaEquipos.add(new Equipo(nombre, categoria, presidente));

                modeloTabla2.addRow(new Object[]{nombre, categoria, presidente});

                System.out.println("Agregando equipo a la tabla oculta en MenuFrame: " + nombre); // Mensaje de depuración
                menuFrame.getModeloTablaOculta().addRow(new Object[]{nombre, categoria, presidente}); // Actualizamos la tabla oculta

                panelJugador.agregarEquipoEnComboBox(nombre);

                texto1.setText("");  // Limpiar el campo de texto
                //listaJugadores.clear();  // Limpiar la lista de jugadores después de registrar el equipo
                guardarDatosEnArchivos();
            }
            //}else if (e.getSource() == btnGuardarNombres) {
            //guardarNombresEnArchivo();

        } else if (e.getSource() == botonRegistrarPresidente) {
            // Registrar el nuevo presidente
            String nombre_apellidos_p = textoPresidenteNombre.getText().trim();

            if (nombre_apellidos_p.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Por favor, complete todos los campos del presidente.",
                        "Error al agregar el presidente", JOptionPane.ERROR_MESSAGE);
            } else {

                Presidente presidente = new Presidente(nombre_apellidos_p);
                Presidente.guardarPresidente(presidente);
                actualizarComboPresidente();  // Actualizar la lista de presidentes
                JOptionPane.showMessageDialog(this,
                        "Presidente registrado correctamente.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        }else if (e.getSource() == botonActualizarPresidentes) {
            // Actualizar el ComboBox de presidentes
            actualizarComboPresidente();}
    }

    private String primeraletra(String palabra) {
        if (palabra == null || palabra.isEmpty()) {
            return palabra;
        }
        return palabra.substring(0, 1).toUpperCase() + palabra.substring(1).toLowerCase();
    }

    private String obtenerSeleccionComboBox(JComboBox<String> comboBox) {
        Object seleccionado = comboBox.getSelectedItem();
        return seleccionado != null ? seleccionado.toString() : "";
    }

    private ArrayList<String> cargarNombresDesdeArchivo(String archivo) {
        ArrayList<String> listaNombres = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    listaNombres.add(linea.trim()); // Agregar líneas no vacías
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al leer el archivo: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return listaNombres;
    }


    public void actualizarComboPresidente() {
        if (comboPresidente == null) {
            comboPresidente = new JComboBox<>();
            comboPresidente.setBounds(180, 50, 170, 20);
            add(comboPresidente);
        }
        comboPresidente.removeAllItems();

        // Cargar datos desde nombres_presidentes.txt
        ArrayList<String> listaPresidentes = cargarNombresDesdeArchivo("nombres_presidentes.txt");
        for (String nombre : listaPresidentes) {
            if (nombre != null && !nombre.isEmpty()) {
                comboPresidente.addItem(nombre.trim());
            }
        }

        // Cargar datos desde tabla_dirigentes.txt donde posición 1 sea "Presidente"
        ArrayList<String> listaDirigentes = cargarNombresDesdeArchivo("tabla_dirigentes.txt");
        for (String linea : listaDirigentes) {
            if (linea != null && !linea.isEmpty()) {
                String[] elementos = linea.split(","); // Separar por comas
                if (elementos.length > 1) {
                    String posicion1 = elementos[1].trim(); // Obtener la posición 1
                    if (posicion1.equalsIgnoreCase("Presidente")) { // Validar si es "Presidente"
                        String posicion0 = elementos[0].trim(); // Obtener la posición 0
                        if (!posicion0.isEmpty()) {
                            comboPresidente.addItem(posicion0); // Añadir al JComboBox
                        }
                    }
                }
            }
        }

        // Mostrar mensaje si no hay datos
        if (comboPresidente.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "No se encontraron presidentes en los archivos.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla2;
    }

    private void guardarDatosEnArchivos() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tabla_equipos.txt"))) {
            for (int i = 0; i < modeloTabla2.getRowCount(); i++) {
                writer.write(
                        modeloTabla2.getValueAt(i, 0) + "," +
                                modeloTabla2.getValueAt(i, 1) + "," +
                                modeloTabla2.getValueAt(i, 2)
                );
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Datos guardados en 'tabla_equipos.txt'.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar los datos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatosDesdeArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("tabla_equipos.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                modeloTabla2.addRow(data);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}