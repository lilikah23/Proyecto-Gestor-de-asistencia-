package edu.colegio.ui;

import edu.colegio.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SearchStudentWindow extends JFrame {
    private Colegio colegio;

    public SearchStudentWindow(Colegio colegio) {
        this.colegio = colegio;
        setTitle("Buscar alumno por RUT");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String rut = JOptionPane.showInputDialog(this, "Ingrese RUT (ej: 12345678-9):");
        if (rut == null || rut.trim().isEmpty()) {
            dispose();
            return;
        }

        Alumno a = colegio.getAlumnosMap().get(rut);
        if (a == null) {
            JOptionPane.showMessageDialog(this, "Alumno no encontrado: " + rut);
            dispose();
            return;
        }

        JLabel info = new JLabel("  Alumno: " + a.getNombre() + " - Curso: " + a.getCursoId());
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Fecha", "Estado", "Justificada", "Motivo"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // La tabla no será editable
            }
        };
        JTable table = new JTable(model);

        // Bucle para llenar la tabla con los filtros aplicados
        for (Asistencia as : a.getAsistencias()) {
            
            // <-- CAMBIO 1: Filtro para mostrar solo desde marzo (mes 3) a diciembre (mes 12)
            int mes = as.getFecha().getMonthValue();
            if (mes >= 3 && mes <= 12) {
                
                boolean just = false;
                String motivo = "";
                
                if (as instanceof AsistenciaConJustificacion) {
                    AsistenciaConJustificacion ac = (AsistenciaConJustificacion) as;
                    just = ac.isJustificada();
                    motivo = ac.getMotivo();
                }

                // <-- CAMBIO 2: Convertir el valor booleano 'just' a "Sí" o "No"
                String justificadaTexto = just ? "Sí" : "No";

                model.addRow(new Object[]{as.getFecha(), as.getEstado(), justificadaTexto, motivo});
            }
        }

        add(info, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
