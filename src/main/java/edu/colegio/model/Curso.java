package edu.colegio.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Curso {
    private String id;
    private int year;
    private List<Alumno> alumnos = new ArrayList<>();

    public Curso(String id, int year) {
        this.id = id;
        this.year = year;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public List<Alumno> getAlumnos() { return alumnos; }

    public void agregarAlumno(Alumno a) {
        alumnos.add(a);
    }

    /**
     * Este método le dice a Java cómo debe "verse" un objeto Curso en texto.
     * Es utilizado por componentes como JComboBox para mostrar el item.
     * @return El ID del curso (ej. "Primero Medio A").
     */
    @Override
    public String toString() {
        return this.id;
    }

    /**
     * Compara dos objetos Curso para ver si son el mismo,
     * basándose en su ID y año.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Curso curso = (Curso) o;
        return year == curso.year && Objects.equals(id, curso.id);
    }

    /**
     * Genera un código hash único para el objeto,
     * necesario cuando se sobrescribe `equals`.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, year);
    }
}