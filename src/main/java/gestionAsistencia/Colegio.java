package gestionAsistencia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Colegio {
    private String nombre;
    private List<Alumno> alumnos; 
    private Map<String, Alumno> mapaAlumnos; // búsqueda rápida por RUT
    private Map<Alumno, List<Asistencia>> registroAsistencias; // alumno -> lista de asistencias
    
    // Constructor
    public Colegio(String nombre) {
        this.nombre = nombre;
        this.alumnos = new ArrayList<>();
        this.mapaAlumnos = new HashMap<>();
        this.registroAsistencias = new HashMap<>();
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

    public Map<String, Alumno> getMapaAlumnos() {
        return mapaAlumnos;
    }

    public Map<Alumno, List<Asistencia>> getRegistroAsistencias() {
        return registroAsistencias;
    }

    // Métodos para agregar alumnos
    public void agregarAlumno(Alumno alumno) {
        alumnos.add(alumno);
        mapaAlumnos.put(alumno.getRut(), alumno);
        registroAsistencias.put(alumno, new ArrayList<>()); // inicializa su lista de asistencias
    }

    // Método para registrar asistencia
    public void registrarAsistencia(Alumno alumno, Asistencia asistencia) {
        if (registroAsistencias.containsKey(alumno)) {
            registroAsistencias.get(alumno).add(asistencia);
        }
    }

    // Sobrecarga de métodos: registrar asistencia por ID de alumno
    public void registrarAsistencia(String rut, Asistencia asistencia) {
        Alumno alumno = mapaAlumnos.get(rut);
        if (alumno != null) {
            registrarAsistencia(alumno, asistencia);
        }
    }

    // Mostrar alumnos
    public void mostrarAlumnos() {
        for (Alumno a : alumnos) {
            System.out.println(a);
        }
    }
}
