package edu.colegio.ui;

import edu.colegio.model.*;
import edu.colegio.persistence.PersistenceManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class MainMenuWindow extends JFrame {
    private Colegio colegio;
    private PersistenceManager pm;
    private int year;

    public MainMenuWindow(Colegio c, PersistenceManager p, int year) {
        this.colegio = c;
        this.pm = p;
        this.year = year;

        setTitle("Menú Principal - Año " + year);
        setSize(380, 350);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new GridLayout(0, 1, 10, 10));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                guardarYSalir();
            }
        });

        // --- Botones del Menú ---
        
        // <-- CAMBIO: La acción de este botón ahora es más compleja
        JButton btnListar = createButton("Listar cursos", () -> {
            // 1. Mostrar el diálogo de ordenación
            SortCoursesDialog dialog = new SortCoursesDialog(this);
            dialog.setVisible(true);
            
            // 2. Si el usuario seleccionó una opción (no canceló)
            SortCoursesDialog.TipoOrden orden = dialog.getSeleccion();
            if (orden != null) {
                // 3. Generar el resumen de datos (umbral de 85% para alumnos críticos)
                List<CursoResumen> resumenes = colegio.generarResumenCursos(this.year, 85.0);
                
                // 4. Abrir la ventana de la lista con los datos y la ordenación
                CourseListWindow clw = new CourseListWindow(resumenes, orden);
                clw.setVisible(true);
            }
        });

        JButton btnMostrar = createButton("Mostrar curso", () -> new CourseDetailWindow(colegio).setVisible(true));
        JButton btnAsistencia = createButton("Registrar asistencia", () -> new AttendanceWindow(colegio, pm, year).setVisible(true));
        JButton btnModificar = createButton("Modificar nombre curso", () -> new ModifyCourseWindow(colegio, pm, year).setVisible(true));
        JButton btnBuscar = createButton("Buscar alumno por RUT", () -> new SearchStudentWindow(colegio).setVisible(true));
        JButton btnSalir = createButton("Salir y guardar", this::guardarYSalir);

        // Panel para mejor estética
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(btnListar);
        panel.add(btnMostrar);
        panel.add(btnAsistencia);
        panel.add(btnModificar);
        panel.add(btnBuscar);
        panel.add(btnSalir);

        add(panel);
        setLocationRelativeTo(null);
    }

    private JButton createButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.addActionListener(e -> action.run());
        return button;
    }
    
    private void guardarYSalir() {
        try {
            pm.saveYear(colegio, year);
            JOptionPane.showMessageDialog(this, "Datos guardados en data/" + year);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
        }
        dispose();
    }
}