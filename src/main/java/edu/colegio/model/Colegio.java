package edu.colegio.model;

import java.time.LocalDate;
import java.util.*;

public class Colegio {
    private Map<Integer, Anio> anios = new HashMap<>();
    private Map<String, Alumno> alumnosMap = new HashMap<>();

    public Map<Integer, Anio> getAniosMap() { return anios; }
    public Map<String, Alumno> getAlumnosMap() { return alumnosMap; }

    public void agregarAlumnoACurso(Alumno a, int anio) {
        Anio an = anios.computeIfAbsent(anio, k -> new Anio(k));
        Curso c = an.getCursos().stream().filter(x -> x.getId().equals(a.getCursoId())).findFirst().orElse(null);
        if (c == null) {
            c = new Curso(a.getCursoId(), anio);
            an.agregarCurso(c);
        }
        boolean exists = c.getAlumnos().stream().anyMatch(x -> x.getRut().equals(a.getRut()));
        if (!exists) {
            c.agregarAlumno(a);
            alumnosMap.put(a.getRut(), a);
        } else {
            alumnosMap.put(a.getRut(), c.getAlumnos().stream().filter(x -> x.getRut().equals(a.getRut())).findFirst().get());
        }
    }

    public Optional<Alumno> buscarAlumnoPorRut(String rut) {
        return Optional.ofNullable(alumnosMap.get(rut));
    }

    public void eliminarAlumno(String rut) {
        Alumno a = alumnosMap.remove(rut);
        if (a == null) return;
        anios.values().forEach(an -> an.getCursos().forEach(c -> c.getAlumnos().removeIf(al -> al.getRut().equals(rut))));
    }

    public List<Curso> getCursosPorAnio(int anio) {
        return anios.containsKey(anio) ? anios.get(anio).getCursos() : Collections.emptyList();
    }

    public void registrarAsistenciaCurso(String cursoId, LocalDate fecha, Map<String, EstadoAsistencia> asistenciasDelDia) {
        Curso curso = anios.values().stream()
                .flatMap(anio -> anio.getCursos().stream())
                .filter(c -> c.getId().equals(cursoId))
                .findFirst()
                .orElse(null);

        if (curso == null) {
            System.err.println("Error: Curso no encontrado con ID: " + cursoId);
            return;
        }

        for (Alumno alumno : curso.getAlumnos()) {
            String rut = alumno.getRut();
            EstadoAsistencia estado = asistenciasDelDia.getOrDefault(rut, EstadoAsistencia.AUSENTE);
            Asistencia nuevaAsistencia = new Asistencia(fecha, estado);
            alumno.registrarAsistencia(nuevaAsistencia);
        }
    }

    // ================================================================= //
    // == NUEVO MÉTODO PARA CALCULAR ESTADÍSTICAS DE CURSOS           == //
    // ================================================================= //
    public List<CursoResumen> generarResumenCursos(int anio, double umbralCritico) {
        List<CursoResumen> resumenes = new ArrayList<>();
        List<Curso> cursosDelAnio = getCursosPorAnio(anio);

        for (Curso curso : cursosDelAnio) {
            double sumaPorcentajes = 0;
            int alumnosConAsistencia = 0;
            int alumnosCriticos = 0;

            if (curso.getAlumnos().isEmpty()) {
                resumenes.add(new CursoResumen(curso, 0, 0));
                continue;
            }

            for (Alumno alumno : curso.getAlumnos()) {
                long presentes = alumno.getAsistencias().stream()
                        .filter(a -> a.getEstado() == EstadoAsistencia.PRESENTE)
                        .count();
                long totalAsistencias = alumno.getAsistencias().size();

                if (totalAsistencias > 0) {
                    double porcentajeAlumno = ((double) presentes / totalAsistencias) * 100.0;
                    sumaPorcentajes += porcentajeAlumno;
                    alumnosConAsistencia++;
                    if (porcentajeAlumno < umbralCritico) {
                        alumnosCriticos++;
                    }
                }
            }

            double porcentajePromedioCurso = (alumnosConAsistencia > 0) ? (sumaPorcentajes / alumnosConAsistencia) : 0;
            resumenes.add(new CursoResumen(curso, porcentajePromedioCurso, alumnosCriticos));
        }
        return resumenes;
    }
}