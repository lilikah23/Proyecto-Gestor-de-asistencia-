package edu.colegio.model;

public class CursoResumen {

    private final Curso curso;
    private final double porcentajeAsistencia;
    private final int alumnosCriticos;

    public CursoResumen(Curso curso, double porcentajeAsistencia, int alumnosCriticos) {
        this.curso = curso;
        this.porcentajeAsistencia = porcentajeAsistencia;
        this.alumnosCriticos = alumnosCriticos;
    }

    public String getNombreCurso() {
        return curso.getId();
    }
    
    public int getNumeroAlumnos() {
        return curso.getAlumnos().size();
    }
    
    public double getPorcentajeAsistencia() {
        return porcentajeAsistencia;
    }
    
    public int getAlumnosCriticos() {
        return alumnosCriticos;
    }
    
    public Curso getCurso() {
        return curso;
    }
}