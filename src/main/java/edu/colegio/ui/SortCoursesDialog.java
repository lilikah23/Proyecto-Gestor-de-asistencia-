package edu.colegio.ui;

import javax.swing.*;
import java.awt.*;

public class SortCoursesDialog extends JDialog {

    public enum TipoOrden {
        ALFABETICO,
        MEJOR_ASISTENCIA,
        PEOR_ASISTENCIA,
        MAS_CRITICOS
    }

    private TipoOrden seleccion;

    public SortCoursesDialog(Frame owner) {
        super(owner, "Ordenar Lista de Cursos", true);
        setLayout(new BorderLayout(10, 10));

        JPanel panelOpciones = new JPanel(new GridLayout(0, 1));
        panelOpciones.setBorder(BorderFactory.createTitledBorder("Seleccione un criterio para ordenar"));
        
        JRadioButton rbAlfabetico = new JRadioButton("Alfabéticamente", true);
        JRadioButton rbMejorAsistencia = new JRadioButton("De mejor a peor asistencia");
        JRadioButton rbPeorAsistencia = new JRadioButton("De peor a mejor asistencia");
        JRadioButton rbMasCriticos = new JRadioButton("Por más alumnos críticos");

        ButtonGroup group = new ButtonGroup();
        group.add(rbAlfabetico);
        group.add(rbMejorAsistencia);
        group.add(rbPeorAsistencia);
        group.add(rbMasCriticos);
        
        panelOpciones.add(rbAlfabetico);
        panelOpciones.add(rbMejorAsistencia);
        panelOpciones.add(rbPeorAsistencia);
        panelOpciones.add(rbMasCriticos);

        JPanel panelBotones = new JPanel();
        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        btnAceptar.addActionListener(e -> {
            if (rbAlfabetico.isSelected()) seleccion = TipoOrden.ALFABETICO;
            else if (rbMejorAsistencia.isSelected()) seleccion = TipoOrden.MEJOR_ASISTENCIA;
            else if (rbPeorAsistencia.isSelected()) seleccion = TipoOrden.PEOR_ASISTENCIA;
            else if (rbMasCriticos.isSelected()) seleccion = TipoOrden.MAS_CRITICOS;
            dispose();
        });

        btnCancelar.addActionListener(e -> {
            seleccion = null;
            dispose();
        });
        
        add(panelOpciones, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(owner);
    }

    public TipoOrden getSeleccion() {
        return seleccion;
    }
}