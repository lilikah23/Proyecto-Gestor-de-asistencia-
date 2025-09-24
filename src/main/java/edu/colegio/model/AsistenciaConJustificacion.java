package edu.colegio.model;

import java.time.LocalDate;

public class AsistenciaConJustificacion extends Asistencia {
    private boolean justificada;
    private String motivo;

    public AsistenciaConJustificacion(LocalDate fecha, EstadoAsistencia estado, boolean justificada, String motivo) {
        super(fecha, estado); // Llama al constructor de la clase padre
        this.justificada = justificada;
        this.motivo = motivo;
    }

    public boolean isJustificada() {
        return justificada;
    }

    public String getMotivo() {
        return motivo;
    }

    @Override
    public String toString() {
        return super.getFecha() + " - " + super.getEstado() +
               (justificada ? " (Justificada: " + motivo + ")" : "");
    }
}
