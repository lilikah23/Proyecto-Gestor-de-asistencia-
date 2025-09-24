package edu.colegio.model;
import java.util.*;
import java.time.LocalDate;

public class Alumno {
    private String rut;
    private String nombre;
    private String cursoId;
    private List<Asistencia> asistencias = new ArrayList<>();

    public Alumno(String rut, String nombre, String cursoId){
        this.rut = rut; this.nombre = nombre; this.cursoId = cursoId;
    }
    public String getRut(){ return rut; }
    public String getNombre(){ return nombre; }
    public String getCursoId(){ return cursoId; }
    public List<Asistencia> getAsistencias(){ return asistencias; }

    public void registrarAsistencia(Asistencia a){
        asistencias.removeIf(x -> x.getFecha().equals(a.getFecha()));
        asistencias.add(a);
    }
}
