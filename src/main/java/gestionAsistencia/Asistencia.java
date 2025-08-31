package gestionAsistencia;

import java.time.LocalDate;

public class Asistencia {
    private LocalDate fecha;
    private boolean presente;
    private boolean salidaAnticipada;
    private boolean inasistenciaJustificada;

    // Constructor principal
    public Asistencia(LocalDate fecha, boolean presente) {
        this.fecha = fecha;
        this.presente = presente;
        this.salidaAnticipada = false;
        this.inasistenciaJustificada = false;
    }

    // Sobrecarga de métodos (SIA1.6)
    public Asistencia(LocalDate fecha, boolean presente, boolean salidaAnticipada) {
        this.fecha = fecha;
        this.presente = presente;
        this.salidaAnticipada = salidaAnticipada;
        this.inasistenciaJustificada = false;
    }

    // Getters y Setters
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public boolean isPresente() {
        return presente;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }

    public boolean isSalidaAnticipada() {
        return salidaAnticipada;
    }

    public void setSalidaAnticipada(boolean salidaAnticipada) {
        this.salidaAnticipada = salidaAnticipada;
    }

    public boolean isInasistenciaJustificada() {
        return inasistenciaJustificada;
    }

    public void setInasistenciaJustificada(boolean inasistenciaJustificada) {
        this.inasistenciaJustificada = inasistenciaJustificada;
    }

    @Override
    public String toString() {
        return "Asistencia [Fecha=" + fecha + ", Presente=" + presente +
               ", Salida anticipada=" + salidaAnticipada +
               ", Inasistencia justificada=" + inasistenciaJustificada + "]";
    }
}