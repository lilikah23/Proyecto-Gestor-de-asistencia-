package gestionAsistencia;

import java.util.*;
import java.time.LocalDate;

public class Colegio {
    private String nombre;
    private List<Alumno> alumnos;  
    private Map<Integer, List<Asistencia>> registroAsistencias; // <AlumnoID, Lista de asistencias>

    // Constructor
    public Colegio(String nombre) {
        this.nombre = nombre;
        this.alumnos = new ArrayList<>();
        this.registroAsistencias = new HashMap<>();
    }

    // Métodos para manejar alumnos
    public void agregarAlumno(Alumno alumno) {
        alumnos.add(alumno);
        registroAsistencias.put(alumno.getId(), new ArrayList<>());
    }

    public void mostrarAlumnos() {
        for (Alumno a : alumnos) {
            System.out.println(a);
        }
    }

    // Métodos para registrar asistencia
    public void registrarAsistencia(int idAlumno, LocalDate fecha, boolean presente) {
        Asistencia asistencia = new Asistencia(fecha, presente);
        if (registroAsistencias.containsKey(idAlumno)) {
            registroAsistencias.get(idAlumno).add(asistencia);
        } else {
            System.out.println("Alumno con ID " + idAlumno + " no encontrado.");
        }
    }

    // Sobrecarga (SIA1.6): asistencia con salida anticipada
    public void registrarAsistencia(int idAlumno, LocalDate fecha, boolean presente, boolean salidaAnticipada) {
        Asistencia asistencia = new Asistencia(fecha, presente, salidaAnticipada);
        if (registroAsistencias.containsKey(idAlumno)) {
            registroAsistencias.get(idAlumno).add(asistencia);
        } else {
            System.out.println("Alumno con ID " + idAlumno + " no encontrado.");
        }
    }

    // Mostrar asistencias de un alumno
    public void mostrarAsistenciasDeAlumno(int idAlumno) {
        if (registroAsistencias.containsKey(idAlumno)) {
            System.out.println("Asistencias del alumno " + idAlumno + ":");
            for (Asistencia a : registroAsistencias.get(idAlumno)) {
                System.out.println(a);
            }
        } else {
            System.out.println("Alumno con ID " + idAlumno + " no encontrado.");
        }
    }

    // Mostrar todo el registro del colegio
    public void mostrarAsistenciasGenerales() {
        for (Map.Entry<Integer, List<Asistencia>> entry : registroAsistencias.entrySet()) {
            System.out.println("Alumno ID " + entry.getKey() + ":");
            for (Asistencia a : entry.getValue()) {
                System.out.println("  " + a);
            }
        }
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }
}