package edu.colegio.model;
import java.util.*;
public class Anio {
    private int anio;
    private List<Curso> cursos = new ArrayList<>();
    public Anio(int anio){ this.anio = anio; }
    public int getAnio(){ return anio; }
    public List<Curso> getCursos(){ return cursos; }
    public void agregarCurso(Curso c){ cursos.add(c); }
}
