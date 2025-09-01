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

    // Sobrecarga de métodos
    public Asistencia(LocalDate fecha, boolean presente, boolean salidaAnticipada) {
        this.fecha = fecha;
        this.presente = presente;
        this.salidaAnticipada = salidaAnticipada;
        this.inasistenciaJustificada = false;
    }

    // Getters y Setters (sin cambios)
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
        // Convertimos los valores booleanos a texto "SI" o "NO"
        String presenteStr = presente ? "SI" : "NO";
        String salidaAnticipadaStr = salidaAnticipada ? "SI" : "NO";
        String inasistenciaJustificadaStr = inasistenciaJustificada ? "SI" : "NO";

        // Usamos las nuevas variables de texto en el resultado final
        return "Asistencia [Fecha=" + fecha + ", Presente=" + presenteStr +
               ", Salida anticipada=" + salidaAnticipadaStr +
               ", Inasistencia justificada=" + inasistenciaJustificadaStr + "]";
    }

}