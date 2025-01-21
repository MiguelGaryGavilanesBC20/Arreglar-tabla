package Menu_P;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.io.BufferedReader;
import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConsultaPanel2 extends JPanel {
    private DefaultTableModel modeloTabla2_1;
    private String titulo;

    public ConsultaPanel2(DefaultTableModel modeloTabla2_1, String titulo) {
        this.modeloTabla2_1 = modeloTabla2_1;
        this.titulo = titulo;
        
        setLayout(null);

        // Título
        JLabel tituloLabel = new JLabel(titulo, JLabel.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 18));
        tituloLabel.setBounds(10, 10, 900, 30);
        add(tituloLabel);

        // Crear la tabla y añadirla al panel
        JTable tabla = new JTable(modeloTabla2_1);
        tabla.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(170, 50, 700, 340);
        add(scrollPane);

        // Panel para botones y campo de texto
        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setBounds(120, 400, 60, 25);
        add(lblBuscar);

        JTextField txtBuscar = new JTextField();
        txtBuscar.setBounds(180, 400, 200, 25);
        add(txtBuscar);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(390, 400, 100, 25);
        add(btnBuscar);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(510, 400, 100, 25);
        add(btnGuardar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(630, 400, 100, 25);
        add(btnEliminar);

        JButton btnModificar = new JButton("Modificar");
        btnModificar.setBounds(750, 400, 100, 25);
        add(btnModificar);

        // Acciones para los botones
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tabla.getSelectedRow();
                if (filaSeleccionada != -1) {
                    modeloTabla2_1.removeRow(filaSeleccionada);
                    JOptionPane.showMessageDialog(ConsultaPanel2.this, "Fila eliminada correctamente.");
                } else {
                    JOptionPane.showMessageDialog(ConsultaPanel2.this, "Por favor, seleccione una fila para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tabla.getSelectedRow();
                if (filaSeleccionada != -1) {
                    for (int i = 0; i < tabla.getColumnCount(); i++) {
                        String nuevoValor = JOptionPane.showInputDialog(ConsultaPanel2.this, "Modificar " + modeloTabla2_1.getColumnName(i),
                                modeloTabla2_1.getValueAt(filaSeleccionada, i));
                        if (nuevoValor != null) {
                            modeloTabla2_1.setValueAt(nuevoValor, filaSeleccionada, i);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(ConsultaPanel2.this, "Por favor, seleccione una fila para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
            
            btnBuscar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String searchText = txtBuscar.getText().toLowerCase();
                    boolean found = false;
                    
                    // Recorrer todas las filas y buscar en todas las columnas
                    for (int i = 0; i < modeloTabla2_1.getRowCount(); i++) {
                        for (int j = 0; j < modeloTabla2_1.getColumnCount(); j++) {
                            String value = modeloTabla2_1.getValueAt(i, j).toString().toLowerCase();
                            if (value.contains(searchText)) {
                                // Seleccionar la fila que contiene el texto buscado
                                tabla.setRowSelectionInterval(i, i);
                                found = true;
                                break;
                            }
                        }
                        if (found) break;
                    }
                    
                    if (!found) {
                        JOptionPane.showMessageDialog(ConsultaPanel2.this, "No se encontraron resultados para la búsqueda.", "No encontrado", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            });
            btnGuardar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("tabla_equipos.txt"))) {
                        for (int i = 0; i < modeloTabla2_1.getRowCount(); i++) {
                            writer.write(
                                    modeloTabla2_1.getValueAt(i, 0) + "," +
                                    modeloTabla2_1.getValueAt(i, 1) + "," +
                                    modeloTabla2_1.getValueAt(i, 2) 
                            );
                            writer.newLine();
                        }
                        JOptionPane.showMessageDialog(ConsultaPanel2.this, "Datos guardados en 'tabla_equipos.txt'.");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(ConsultaPanel2.this, "Error al guardar los datos: " + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            

        
    }

    /*private void cargarDatosDesdeArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("tabla_arbitros.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                modeloTabla4.addRow(data);
            }
            //JOptionPane.showMessageDialog(this, "Datos cargados exitosamente.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }*/

}

