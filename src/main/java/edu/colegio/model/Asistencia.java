package edu.colegio.model;
import java.time.LocalDate;

public class Asistencia {
    private LocalDate fecha;
    private EstadoAsistencia estado;

    public Asistencia(LocalDate fecha, EstadoAsistencia estado){
        this.fecha = fecha; this.estado = estado;
    }
    public LocalDate getFecha(){ return fecha; }
    public EstadoAsistencia getEstado(){ return estado; }
}
