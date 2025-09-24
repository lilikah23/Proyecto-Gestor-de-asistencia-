package edu.colegio.ui;

import com.toedter.calendar.JDateChooser;
import edu.colegio.model.*;
import edu.colegio.persistence.PersistenceManager;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AttendanceWindow extends JFrame {

    private final PersistenceManager pm;
    private final Colegio colegio;
    private final int year;

    private JComboBox<Integer> cbAnios;
    private JComboBox<Curso> cbCursos;
    private JTable tablaAlumnos;
    private JDateChooser selectorFecha;
    private JButton btnGuardar;
    private DefaultTableModel tableModel;

    public AttendanceWindow(Colegio colegio, PersistenceManager pm, int year) {
        this.colegio = colegio;
        this.pm = pm;
        this.year = year;

        initComponents();
        initListeners();

        cbAnios.setSelectedItem(year);
        AutoCompleteDecorator.decorate(cbCursos);
    }

    private void initComponents() {
        setTitle("Registrar Asistencia - Año " + year);
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Año:"));
        cbAnios = new JComboBox<>(colegio.getAniosMap().keySet().toArray(new Integer[0]));
        topPanel.add(cbAnios);

        topPanel.add(new JLabel(" Curso:"));
        cbCursos = new JComboBox<>();
        cbCursos.setEditable(true);
        topPanel.add(cbCursos);

        topPanel.add(new JLabel(" Fecha:"));
        selectorFecha = new JDateChooser();
        selectorFecha.setDate(new Date());
        selectorFecha.setPreferredSize(new Dimension(120, 25));
        topPanel.add(selectorFecha);

        String[] columnas = {"RUT", "Nombre", "Estado"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };
        tablaAlumnos = new JTable(tableModel);
        
        JComboBox<EstadoAsistencia> comboBoxEstado = new JComboBox<>(EstadoAsistencia.values());
        tablaAlumnos.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(comboBoxEstado));

        JPanel bottomPanel = new JPanel();
        btnGuardar = new JButton("Guardar Asistencia");
        bottomPanel.add(btnGuardar);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(tablaAlumnos), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void initListeners() {
        // ActionListener para el ComboBox de Años (CON EL FILTRO AÑADIDO)
        cbAnios.addActionListener(e -> {
            Object selected = cbCursos.getSelectedItem();
            cbCursos.removeAllItems();
            Integer selectedYear = (Integer) cbAnios.getSelectedItem();
            if (selectedYear != null) {
                for (Curso c : colegio.getCursosPorAnio(selectedYear)) {
                    // Filtro para mostrar solo cursos hasta 4to
                    String cursoId = c.getId();
                    if (cursoId != null && !cursoId.isEmpty()) {
                        char primerCaracter = cursoId.charAt(0);
                        if (Character.isDigit(primerCaracter)) {
                            int nivel = Character.getNumericValue(primerCaracter);
                            if (nivel <= 4) {
                                cbCursos.addItem(c);
                            }
                        }
                    }
                }
            }
            if (selected instanceof Curso) {
                cbCursos.setSelectedItem(selected);
            }
        });

        // ActionListener para el ComboBox de Cursos
        cbCursos.addActionListener(e -> {
            if (e.getActionCommand().equals("comboBoxChanged")) {
                Curso selectedCourse = (Curso) cbCursos.getSelectedItem();
                cargarAlumnosEnTabla(selectedCourse);
            }
        });

        // ActionListener para el Botón Guardar
        btnGuardar.addActionListener(e -> guardarAsistencia());
    }

    private void cargarAlumnosEnTabla(Curso curso) {
        tableModel.setRowCount(0);
        if (curso != null) {
            for (Alumno alumno : curso.getAlumnos()) {
                tableModel.addRow(new Object[]{
                    alumno.getRut(),
                    alumno.getNombre(),
                    EstadoAsistencia.PRESENTE
                });
            }
        }
    }

    private void guardarAsistencia() {
        Date fechaUtil = selectorFecha.getDate();
        if (fechaUtil == null) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una fecha.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        LocalDate fecha = fechaUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Curso cursoSeleccionado = (Curso) cbCursos.getSelectedItem();
        if (cursoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un curso.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Map<String, EstadoAsistencia> asistenciasDelDia = new HashMap<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String rut = (String) tableModel.getValueAt(i, 0);
            EstadoAsistencia estado = (EstadoAsistencia) tableModel.getValueAt(i, 2);
            asistenciasDelDia.put(rut, estado);
        }

        try {
            colegio.registrarAsistenciaCurso(cursoSeleccionado.getId(), fecha, asistenciasDelDia);
            pm.saveYear(colegio, this.year);
            JOptionPane.showMessageDialog(this, "Asistencia para el " + fecha + " guardada correctamente.");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar la asistencia: " + ex.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }
}