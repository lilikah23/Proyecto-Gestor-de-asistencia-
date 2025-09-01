package gestionAsistencia;

public class Alumno {
    private int id;
    private String nombre;
    private String curso;

    // Constructor
    public Alumno(int id, String nombre, String curso) {
        this.id = id;
        this.nombre = nombre;
        this.curso = curso;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    // Para mostrar el alumno en consola
    @Override
    public String toString() {
        return "Alumno [ID=" + id + ", Nombre=" + nombre + ", Curso=" + curso + "]";
    }
}