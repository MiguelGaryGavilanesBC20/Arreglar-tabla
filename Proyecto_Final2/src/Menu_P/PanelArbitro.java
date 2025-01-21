package Menu_P;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Arbitro {
    private String nombre;
    private int edad;
    private int cedula;
    

    public Arbitro(String nombre, int edad, int cedula) {
        this.nombre = nombre;
        this.edad = edad;
        this.cedula = cedula;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

}




public class PanelArbitro extends JPanel implements ActionListener {
    private List<Arbitro> listaArbitros;
    private DefaultTableModel modeloTabla3;
    private JTextField txtNombre,txtEdad,txtCedula;
    private JLabel lbNombre, lbEdad, lbCedula;
    private JButton btnRegistrar;
    private JTable tablaArbitros;
    private JScrollPane scrollPane;

    public PanelArbitro(DefaultTableModel modeloTabla3) {
        this.listaArbitros = new ArrayList<>();
        
        setLayout(null);

        // Panel de registro
        JPanel panelRegistro = new JPanel();
        panelRegistro.setLayout(new GridLayout(3, 2, 10, 10));

        lbNombre = new JLabel("Nombre Completo");
        lbNombre.setBounds(50, 20, 120, 20);
        add(lbNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(180, 20, 150, 20);
        add(txtNombre);

        lbEdad = new JLabel("Edad");
        lbEdad.setBounds(370, 20, 50, 20);
        add(lbEdad);

        txtEdad = new JTextField();
        txtEdad.setBounds(410, 20, 50, 20);
        add(txtEdad);

        lbCedula = new JLabel("Cedula");
        lbCedula.setBounds(50, 50, 100, 20);
        add(lbCedula);

        txtCedula = new JTextField();
        txtCedula.setBounds(180, 50, 150, 20);
        add(txtCedula);

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(500, 15, 100, 30);
        add(btnRegistrar);
        btnRegistrar.addActionListener(this);

        
        modeloTabla3 = new DefaultTableModel();
        modeloTabla3.addColumn("Nombre");
        modeloTabla3.addColumn("Edad");
        modeloTabla3.addColumn("Cedula");

        // Tabla de árbitros
        this.modeloTabla3 = modeloTabla3;
        tablaArbitros = new JTable(modeloTabla3) {
            @Override
            protected void processMouseEvent(java.awt.event.MouseEvent e) {
                // Deshabilitar cualquier interacción con clics
            }
        };
        tablaArbitros.getTableHeader().setReorderingAllowed(false);
        scrollPane = new JScrollPane(tablaArbitros);
        scrollPane.setBounds(150, 100, 750, 320);
        add(scrollPane);
        cargarDatosDesdeArchivo();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRegistrar) {
            String nombre = txtNombre.getText();
            String edadtxt = txtEdad.getText();
            String cedulatxt = txtCedula.getText();

            // Validar campos
            if (nombre.isEmpty() || edadtxt.isEmpty() || cedulatxt.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.","Error al agregar los datos",JOptionPane.ERROR_MESSAGE);
                return;
            }else{
                
                int edad = Integer.parseInt(edadtxt);
                int cedula = Integer.parseInt(cedulatxt);
                
                if (edad <= 0 || cedula <= 0) {
                    JOptionPane.showMessageDialog(this, "La edad y la cédula deben ser numeros", "Error al agregar los datos", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                    
                // Agregar árbitro a la tabla
                modeloTabla3.addRow(new Object[]{nombre, edad, cedula, 0});
                
                Arbitro nuevoArbitro = new Arbitro(nombre, edad, cedula);
                listaArbitros.add(nuevoArbitro);

                // Limpiar campos
                txtNombre.setText("");
                txtEdad.setText("");
                txtCedula.setText("");
                guardarDatosEnArchivos();
            }
        }
    }

    public List<Arbitro> getListaArbitros() {
      return listaArbitros;
    }
    private void guardarDatosEnArchivos() {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("tabla_arbitros.txt"))) {
                for (int i = 0; i < modeloTabla3.getRowCount(); i++) {
                    writer.write(
                            modeloTabla3.getValueAt(i, 0) + "," +
                            modeloTabla3.getValueAt(i, 1) + "," +
                            modeloTabla3.getValueAt(i, 2) 
                    );
                    writer.newLine();
                }
                JOptionPane.showMessageDialog(this, "Datos guardados en 'tabla_arbitros.txt'.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar los datos: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
    }

    private void cargarDatosDesdeArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("tabla_arbitros.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                modeloTabla3.addRow(data);
            }
            //JOptionPane.showMessageDialog(this, "Datos cargados exitosamente.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizarContenido() {
        // Lógica para actualizar la tabla o el contenido
        modeloTabla3.fireTableDataChanged();
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla3;
    }
}
