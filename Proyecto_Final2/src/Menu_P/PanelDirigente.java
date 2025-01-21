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

// Clase Dirigente
class Dirigente {
    private String nombre;
    private int edad;
    private String cedula;
    private String fechaEleccion;
    private double patrimonio;
    private String cargo;

    public Dirigente(String nombre, int edad, String cedula, String fechaEleccion, double patrimonio, String cargo) {
        this.nombre = nombre;
        this.edad = edad;
        this.cedula = cedula;
        this.fechaEleccion = fechaEleccion;
        this.patrimonio = patrimonio;
        this.cargo = cargo;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public String getCedula() {
        return cedula;
    }

    public String getFechaEleccion() {
        return fechaEleccion;
    }

    public double getPatrimonio() {
        return patrimonio;
    }

    public String getCargo() {
        return cargo;
    }
}

// Clase principal PanelDirigente
public class PanelDirigente extends JPanel implements ActionListener {
    private List<Dirigente> listaDirigentes;
    private DefaultTableModel modeloTabla4;
    private JTextField txtNombre, txtPatrimonio, txtAnioEleccion, txtEdad, txtCedula;
    private JButton btnRegistrar;
    private JTable tablaDirigentes;
    private JComboBox<String> cmbCargo, cmbDia, cmbMes;
    private JComboBox<String> cmbPresidentesExternos; // ComboBox externo para presidentes en PanelEquipo

    public PanelDirigente(DefaultTableModel modeloTabla4, JComboBox<String> cmbPresidentesExternos) {
        this.listaDirigentes = new ArrayList<>();
        this.modeloTabla4 = modeloTabla4;
        this.cmbPresidentesExternos = cmbPresidentesExternos;

        setLayout(null);

        // Panel de registro
        JLabel lbNombre = new JLabel("Nombre Completo");
        lbNombre.setBounds(50, 20, 120, 20);
        add(lbNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(180, 20, 150, 20);
        add(txtNombre);

        JLabel lbEdad = new JLabel("Edad");
        lbEdad.setBounds(370, 20, 50, 20);
        add(lbEdad);

        txtEdad = new JTextField();
        txtEdad.setBounds(410, 20, 50, 20);
        add(txtEdad);

        JLabel lbCargo = new JLabel("Cargo");
        lbCargo.setBounds(370, 50, 120, 20);
        add(lbCargo);

        cmbCargo = new JComboBox<>(new String[]{
                "Director Técnico (Entrenador)",
                "Asistentes técnicos",
                "Presidente",
                "Vicepresidente",
                "Vocal",
                "Médico"
        });
        cmbCargo.setBounds(410, 50, 200, 20);
        add(cmbCargo);

        JLabel lbCedula = new JLabel("Cédula");
        lbCedula.setBounds(50, 50, 120, 20);
        add(lbCedula);

        txtCedula = new JTextField();
        txtCedula.setBounds(180, 50, 150, 20);
        add(txtCedula);

        JLabel lbPatrimonio = new JLabel("Patrimonio ($)");
        lbPatrimonio.setBounds(50, 80, 120, 20);
        add(lbPatrimonio);

        txtPatrimonio = new JTextField();
        txtPatrimonio.setBounds(180, 80, 150, 20);
        add(txtPatrimonio);

        JLabel lbFechaEleccion = new JLabel("Fecha de Elección");
        lbFechaEleccion.setBounds(370, 80, 120, 20);
        add(lbFechaEleccion);

        cmbDia = new JComboBox<>();
        for (int i = 1; i <= 31; i++) {
            cmbDia.addItem(String.valueOf(i));
        }
        cmbDia.setBounds(490, 80, 50, 20);
        add(cmbDia);

        cmbMes = new JComboBox<>(new String[]{
                "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        });
        cmbMes.setBounds(550, 80, 100, 20);
        add(cmbMes);

        txtAnioEleccion = new JTextField();
        txtAnioEleccion.setBounds(670, 80, 80, 20);
        add(txtAnioEleccion);
        txtAnioEleccion.setText("Año");

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(650, 15, 100, 30);
        add(btnRegistrar);
        btnRegistrar.addActionListener(this);

        // Configurar modelo de la tabla
        this.modeloTabla4.addColumn("Nombre");
        this.modeloTabla4.addColumn("Cargo");
        this.modeloTabla4.addColumn("Edad");
        this.modeloTabla4.addColumn("Cédula");
        this.modeloTabla4.addColumn("Patrimonio");
        this.modeloTabla4.addColumn("Fecha de Elección");

        // Tabla de dirigentes
        tablaDirigentes = new JTable(modeloTabla4) {
            @Override
            protected void processMouseEvent(java.awt.event.MouseEvent e) {
                // Deshabilitar cualquier interacción con clics
            }
        };
        tablaDirigentes.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(tablaDirigentes);
        scrollPane.setBounds(40, 130, 1050, 290);
        add(scrollPane);

        cargarDatosDesdeArchivo();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRegistrar) {
            String nombre = txtNombre.getText();
            String cargo = (String) cmbCargo.getSelectedItem();
            String edadTexto = txtEdad.getText();
            String cedula = txtCedula.getText();
            String patrimonioTexto = txtPatrimonio.getText();
            String dia = (String) cmbDia.getSelectedItem();
            String mes = (String) cmbMes.getSelectedItem();
            String anio = txtAnioEleccion.getText();

            if (nombre.isEmpty() || cargo.isEmpty() || edadTexto.isEmpty() || cedula.isEmpty() || patrimonioTexto.isEmpty() || dia.isEmpty() || mes.isEmpty() || anio.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.",
                        "Error al agregar los datos", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int edad = Integer.parseInt(edadTexto);
                double patrimonio = Double.parseDouble(patrimonioTexto);

                int anioNumero = Integer.parseInt(anio);
                if (anioNumero < 1900 || anioNumero > 2025) {
                    JOptionPane.showMessageDialog(this, "Por favor, ingrese un año válido entre 1900 y 2025.",
                            "Error al agregar los datos", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (edad < 18 || edad > 90) {
                    JOptionPane.showMessageDialog(this, "Por favor, ingrese una edad válida",
                            "Error al agregar los datos", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String fechaEleccion = dia + " de " + mes + " del " + anio;

                modeloTabla4.addRow(new Object[]{nombre, cargo, edad, cedula, String.format("$%.2f", patrimonio), fechaEleccion});
                listaDirigentes.add(new Dirigente(nombre, edad, cedula, fechaEleccion, patrimonio, cargo));

                // Agregar presidente al ComboBox externo
                if (cargo.equals("Presidente")) {
                    cmbPresidentesExternos.addItem(nombre);
                }

                limpiarCampos();
                guardarDatosEnArchivos();
                JOptionPane.showMessageDialog(this, "Dirigente registrado exitosamente.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Edad y patrimonio deben ser números válidos.",
                        "Error al agregar los datos", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarCampos() {
        //txt
        txtNombre.setText("");
        txtEdad.setText("");
        txtCedula.setText("");
        txtPatrimonio.setText("");
        txtAnioEleccion.setText("Año");
        //cmb
        cmbCargo.setSelectedIndex(0);
        cmbDia.setSelectedIndex(0);
        cmbMes.setSelectedIndex(0);

    }

    private void guardarDatosEnArchivos() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tabla_dirigentes.txt"))) {
            for (int i = 0; i < modeloTabla4.getRowCount(); i++) {
                writer.write(
                        modeloTabla4.getValueAt(i, 0) + "," +
                                modeloTabla4.getValueAt(i, 1) + "," +
                                modeloTabla4.getValueAt(i, 2) + "," +
                                modeloTabla4.getValueAt(i, 3) + "," +
                                modeloTabla4.getValueAt(i, 4) + "," +
                                modeloTabla4.getValueAt(i, 5)
                );
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Datos guardados en 'tabla_dirigentes.txt'.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar los datos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatosDesdeArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("tabla_dirigentes.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                modeloTabla4.addRow(data);

                // Agregar presidentes del archivo al ComboBox externo
                if (data[1].equals("Presidente")) {
                    cmbPresidentesExternos.addItem(data[0]);
                }
            }
            //JOptionPane.showMessageDialog(this, "Datos cargados exitosamente.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizarContenido() {
        // Lógica para actualizar la tabla o el contenido
        modeloTabla4.fireTableDataChanged();
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla4;
    }
}
