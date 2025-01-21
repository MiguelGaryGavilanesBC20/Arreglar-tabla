package Menu_P;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class DialogoSeleccionJugadores extends JDialog implements ActionListener {
    private JComboBox<String> comboJugadores1;
    private JComboBox<String> comboJugadores2;
    private JTextField txtAmarillas, txtRojas;
    private JButton btnGuardar, btnCancelar;
    private MenuFrame menuFrame;
    private Panel_Partidos panelPartidos;

    public DialogoSeleccionJugadores(JFrame parent, MenuFrame menuFrame, Panel_Partidos panelPartidos, String equipo1, String equipo2) {
        super(parent, "Seleccionar Jugadores", true);
        this.menuFrame = menuFrame;
        this.panelPartidos = panelPartidos;

        setLayout(null);
        setSize(400, 400);
        setLocationRelativeTo(parent);

        JLabel lblJugador1 = new JLabel("Jugadores de " + equipo1 + ":");
        lblJugador1.setBounds(30, 30, 150, 20);
        add(lblJugador1);

        comboJugadores1 = new JComboBox<>();
        cargarJugadores(equipo1, comboJugadores1);
        comboJugadores1.setBounds(180, 30, 200, 20);
        add(comboJugadores1);

        JLabel lblJugador2 = new JLabel("Jugadores de " + equipo2 + ":");
        lblJugador2.setBounds(30, 70, 150, 20);
        add(lblJugador2);

        comboJugadores2 = new JComboBox<>();
        cargarJugadores(equipo2, comboJugadores2);
        comboJugadores2.setBounds(180, 70, 200, 20);
        add(comboJugadores2);

        JLabel lblAmarillas = new JLabel("Tarjetas Amarillas:");
        lblAmarillas.setBounds(30, 110, 150, 20);
        add(lblAmarillas);

        txtAmarillas = new JTextField();
        txtAmarillas.setBounds(180, 110, 50, 20);
        add(txtAmarillas);

        JLabel lblRojas = new JLabel("Tarjetas Rojas:");
        lblRojas.setBounds(30, 150, 150, 20);
        add(lblRojas);

        txtRojas = new JTextField();
        txtRojas.setBounds(180, 150, 50, 20);
        add(txtRojas);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(50, 300, 100, 30);
        add(btnGuardar);
        btnGuardar.addActionListener(this);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(200, 300, 100, 30);
        add(btnCancelar);
        btnCancelar.addActionListener(this);
    }

    private void cargarJugadores(String equipo, JComboBox<String> comboBox) {
        DefaultTableModel modeloTablaOculta = menuFrame.getModeloTablaOculta();
        for (int i = 0; i < modeloTablaOculta.getRowCount(); i++) {
            String nombreEquipo = (String) modeloTablaOculta.getValueAt(i, 0);
            if (nombreEquipo.equals(equipo)) {
                String nombreJugador = (String) modeloTablaOculta.getValueAt(i, 2);
                comboBox.addItem(nombreJugador);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnGuardar) {
            String nombreJugador1 = (String) comboJugadores1.getSelectedItem();
            String nombreJugador2 = (String) comboJugadores2.getSelectedItem();
            String amarillasTexto = txtAmarillas.getText().trim();
            String rojasTexto = txtRojas.getText().trim();

            if (nombreJugador1 == null || nombreJugador2 == null || amarillasTexto.isEmpty() || rojasTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int amarillas = Integer.parseInt(amarillasTexto);
                int rojas = Integer.parseInt(rojasTexto);

                Panel_Jugador.actualizarTarjetasJugador(nombreJugador1, amarillas, rojas);
                Panel_Jugador.actualizarTarjetasJugador(nombreJugador2, amarillas, rojas);
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese números válidos para las tarjetas.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == btnCancelar) {
            dispose();
        }
    }
}
