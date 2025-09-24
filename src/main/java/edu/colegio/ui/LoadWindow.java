package edu.colegio.ui;

import edu.colegio.model.Colegio;
import edu.colegio.persistence.PersistenceManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LoadWindow extends JFrame {
    private PersistenceManager pm = new PersistenceManager("data");

    public LoadWindow() {
        setTitle("Seleccionar Año");
        setSize(420, 180);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 40));

        JButton btnCreate = new JButton("Crear nuevo año");
        JButton btnExisting = new JButton("Años existentes");

        btnCreate.addActionListener(e -> crearNuevoAnio());
        btnExisting.addActionListener(e -> cargarAnioExistente());

        add(btnCreate);
        add(btnExisting);
        setLocationRelativeTo(null);
    }

    private void crearNuevoAnio() {
        String input = JOptionPane.showInputDialog(this, "Ingresa el año (ej. 2026):");
        if (input == null || input.trim().isEmpty()) return;

        try {
            int year = Integer.parseInt(input.trim());

            // <-- CAMBIO: Se añade la validación para no crear años duplicados
            List<Integer> yearsExistentes = pm.listYears();
            if (yearsExistentes.contains(year)) {
                JOptionPane.showMessageDialog(this, "El año " + year + " ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Detiene el proceso si el año ya existe
            }
            // --- Fin del cambio ---

            pm.createYear(year);
            
            Colegio colegio = new Colegio();
            pm.loadYear(colegio, year);
            
            JOptionPane.showMessageDialog(this, "Año " + year + " creado y cargado.");
            
            MainMenuWindow mainMenu = new MainMenuWindow(colegio, pm, year);
            mainMenu.setVisible(true);
            
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa un número válido para el año.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void cargarAnioExistente() {
        try {
            List<Integer> years = pm.listYears();
            if (years.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron años guardados en la carpeta 'data'.");
                return;
            }

            Integer sel = (Integer) JOptionPane.showInputDialog(this, "Selecciona un año:",
                    "Años existentes", JOptionPane.QUESTION_MESSAGE, null, years.toArray(), years.get(0));

            if (sel != null) {
                Colegio colegio = new Colegio();
                pm.loadYear(colegio, sel);
                
                MainMenuWindow mainMenu = new MainMenuWindow(colegio, pm, sel);
                mainMenu.setVisible(true);
                
                dispose();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error listando años: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoadWindow loadWindow = new LoadWindow();
            loadWindow.setVisible(true);
        });
    }
}