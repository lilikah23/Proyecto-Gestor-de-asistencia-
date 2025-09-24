package edu.colegio.ui;

import edu.colegio.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CourseDetailWindow extends JFrame {
    public CourseDetailWindow(Colegio colegio){
        setTitle("Mostrar curso");
        setSize(600,400);

        JComboBox<Integer> cbAnios = new JComboBox<>(colegio.getAniosMap().keySet().toArray(new Integer[0]));
        JComboBox<String> cbCursos = new JComboBox<>();
        DefaultTableModel model = new DefaultTableModel(new Object[]{"RUT","Nombre"},0);
        JTable table = new JTable(model);

        cbAnios.addActionListener(e -> {
            cbCursos.removeAllItems();
            Integer an = (Integer) cbAnios.getSelectedItem();
            if(an!=null){
                for(Curso c: colegio.getCursosPorAnio(an)) cbCursos.addItem(c.getId());
            }
        });

        cbCursos.addActionListener(e -> {
            model.setRowCount(0);
            Integer an = (Integer) cbAnios.getSelectedItem();
            String id = (String) cbCursos.getSelectedItem();
            if(an!=null && id!=null){
                for(Curso c: colegio.getCursosPorAnio(an)){
                    if(c.getId().equals(id)){
                        for(Alumno a: c.getAlumnos()){
                            model.addRow(new Object[]{a.getRut(), a.getNombre()});
                        }
                    }
                }
            }
        });

        JPanel top = new JPanel();
        top.add(new JLabel("Año:")); top.add(cbAnios); top.add(new JLabel(" Curso:")); top.add(cbCursos);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
