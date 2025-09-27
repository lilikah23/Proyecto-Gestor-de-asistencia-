package edu.colegio.ui;

import edu.colegio.model.*;
import edu.colegio.persistence.PersistenceManager;
import edu.colegio.persistence.ReporteManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
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
        
        JButton btnGenerarReporte = createButton("Generar Reporte de Curso", () -> {
    // Lógica para seleccionar un curso y generar el reporte
    try {
        List<Curso> cursos = colegio.getCursosPorAnio(year);
        if (cursos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay cursos en este año para generar reportes.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Curso cursoSeleccionado = (Curso) JOptionPane.showInputDialog(this, "Seleccione un curso para el reporte:",
                "Generar Reporte", JOptionPane.QUESTION_MESSAGE, null, cursos.toArray(), cursos.get(0));
        
        if (cursoSeleccionado != null) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar Reporte");
            fileChooser.setSelectedFile(new File("Reporte_" + cursoSeleccionado.getId() + ".txt"));

            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File archivoParaGuardar = fileChooser.getSelectedFile();
                ReporteManager.generarReporteAsistenciaCurso(cursoSeleccionado, archivoParaGuardar.getAbsolutePath());
                JOptionPane.showMessageDialog(this, "Reporte generado con éxito en:\n" + archivoParaGuardar.getAbsolutePath());
            }
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al generar el reporte: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
});

// Añade el botón al panel
panel.add(btnGenerarReporte);
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