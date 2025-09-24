package edu.colegio.ui;

import edu.colegio.model.*;
import edu.colegio.persistence.PersistenceManager;

import javax.swing.*;
import java.awt.*;

public class ModifyCourseWindow extends JFrame {
    private final Colegio colegio;
    private final PersistenceManager pm;
    private final int year;

    private JComboBox<Curso> cbCursos; // Cambiado a JComboBox<Curso>
    private JTextField txtNuevoNombre;

    public ModifyCourseWindow(Colegio colegio, PersistenceManager pm, int year) {
        this.colegio = colegio;
        this.pm = pm;
        this.year = year;

        // <-- CAMBIO: Se actualiza el título de la ventana
        setTitle("Modificar Nombre de Curso - Año " + year);
        setSize(400, 180);
        setLayout(new BorderLayout(10, 10));

        // --- Paneles con mejor layout y márgenes ---
        JPanel mainPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        mainPanel.add(new JLabel("Curso a modificar:"));
        cbCursos = new JComboBox<>();
        for (Curso c : colegio.getCursosPorAnio(year)) {
            cbCursos.addItem(c); // Añade el objeto Curso directamente
        }
        mainPanel.add(cbCursos);

        // <-- CAMBIO: Se actualiza el texto de la etiqueta
        mainPanel.add(new JLabel("Nuevo nombre:"));
        txtNuevoNombre = new JTextField();
        mainPanel.add(txtNuevoNombre);

        // Panel inferior: botones
        JPanel bottomPanel = new JPanel();
        JButton btnGuardar = new JButton("Guardar cambios");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> {
            Curso cursoSeleccionado = (Curso) cbCursos.getSelectedItem();
            String nuevoNombre = txtNuevoNombre.getText().trim();

            if (cursoSeleccionado == null || nuevoNombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecciona un curso y escribe un nuevo nombre.");
                return;
            }

            try {
                // La lógica ahora es más simple gracias a que el JComboBox maneja el objeto
                cursoSeleccionado.setId(nuevoNombre);
                pm.saveYear(colegio, year);
                
                JOptionPane.showMessageDialog(this, "Nombre del curso actualizado y guardado.");
                
                // Refrescar el ComboBox para que muestre el cambio
                cbCursos.repaint();
                
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
            }
        });

        btnCancelar.addActionListener(e -> dispose());

        bottomPanel.add(btnGuardar);
        bottomPanel.add(btnCancelar);

        // Añadir paneles
        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}