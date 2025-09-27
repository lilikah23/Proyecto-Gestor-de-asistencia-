package edu.colegio.persistence;

import edu.colegio.model.Alumno;
import edu.colegio.model.Asistencia;
import edu.colegio.model.Curso;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;

public class ReporteManager {

    /**
     * Genera un reporte de asistencia en formato TXT para un curso específico.
     * @param curso El curso del cual se generará el reporte.
     * @param rutaArchivo La ruta completa donde se guardará el archivo (ej. "C:/reportes/reporte_1A.txt").
     */
    public static void generarReporteAsistenciaCurso(Curso curso, String rutaArchivo) throws Exception {
        // Usamos try-with-resources para asegurar que el archivo se cierre automáticamente
        try (PrintWriter writer = new PrintWriter(rutaArchivo, StandardCharsets.UTF_8)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            writer.println("=======================================================");
            writer.println(" REPORTE DE ASISTENCIA - CURSO: " + curso.getId());
            writer.println(" AÑO: " + curso.getYear());
            writer.println("=======================================================");
            writer.println();

            if (curso.getAlumnos().isEmpty()) {
                writer.println("El curso no tiene alumnos registrados.");
            } else {
                // Itera sobre cada alumno del curso
                for (Alumno alumno : curso.getAlumnos()) {
                    writer.println("-------------------------------------------------------");
                    writer.println(" Alumno: " + alumno.getNombre() + " (RUT: " + alumno.getRut() + ")");
                    writer.println("-------------------------------------------------------");

                    if (alumno.getAsistencias().isEmpty()) {
                        writer.println("  - Sin registros de asistencia.");
                    } else {
                        // Itera sobre cada registro de asistencia del alumno
                        for (Asistencia a : alumno.getAsistencias()) {
                            writer.println("  - Fecha: " + a.getFecha().format(formatter) + " | Estado: " + a.getEstado());
                        }
                    }
                    writer.println(); // Espacio entre alumnos
                }
            }
            writer.println("==================== FIN DEL REPORTE ====================");
        }
    }
}
