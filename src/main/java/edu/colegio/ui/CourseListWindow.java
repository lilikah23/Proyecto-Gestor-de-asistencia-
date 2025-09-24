package edu.colegio.ui;

import edu.colegio.model.CursoResumen;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class CourseListWindow extends JFrame {

    public CourseListWindow(List<CursoResumen> resumenes, SortCoursesDialog.TipoOrden orden) {
        setTitle("Lista de Cursos - Resumen de Asistencia");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        switch (orden) {
            case MEJOR_ASISTENCIA:
                resumenes.sort(Comparator.comparingDouble(CursoResumen::getPorcentajeAsistencia).reversed());
                break;
            case PEOR_ASISTENCIA:
                resumenes.sort(Comparator.comparingDouble(CursoResumen::getPorcentajeAsistencia));
                break;
            case MAS_CRITICOS:
                resumenes.sort(Comparator.comparingInt(CursoResumen::getAlumnosCriticos).reversed());
                break;
            case ALFABETICO:
                resumenes.sort(Comparator.comparing(CursoResumen::getNombreCurso));
                break;
        }

        String[] columnas = {"Curso", "N° Alumnos", "% Asistencia", "Alumnos Críticos"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for (CursoResumen r : resumenes) {
            model.addRow(new Object[]{
                    r.getNombreCurso(),
                    r.getNumeroAlumnos(),
                    String.format("%.2f%%", r.getPorcentajeAsistencia()),
                    r.getAlumnosCriticos()
            });
        }

        JTable table = new JTable(model);
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                CursoResumen resumenFila = resumenes.get(table.convertRowIndexToModel(row));
                if (resumenFila.getPorcentajeAsistencia() < 85.0 && !isSelected) {
                    c.setBackground(new Color(255, 224, 224));
                } else {
                    c.setBackground(Color.WHITE);
                }
                c.setForeground(Color.BLACK);
                return c;
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);
    }
}