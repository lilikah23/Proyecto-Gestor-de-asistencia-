package edu.colegio.persistence;

import edu.colegio.model.*;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class PersistenceManager {
    private Path baseDir; // data/

    public PersistenceManager(String baseDataDir){
        this.baseDir = Paths.get(baseDataDir);
    }

    // Crea estructura de carpetas de un año completo
    public void createYear(int year) throws IOException {
        Path yearDir = baseDir.resolve(String.valueOf(year));
        if(!Files.exists(yearDir)) Files.createDirectories(yearDir);

        // Cursos básicos (1A, 1B ... 8A, 8B)
        for(int i=1; i<=8; i++){
            createCourseDir(yearDir, i + "A");
            createCourseDir(yearDir, i + "B");
        }
        // Cursos medios (1M ... 4M, secciones A y B)
        for(int i=1; i<=4; i++){
            createCourseDir(yearDir, i + "MA");
            createCourseDir(yearDir, i + "MB");
        }
    }

    private void createCourseDir(Path yearDir, String courseId) throws IOException {
        Path courseDir = yearDir.resolve(courseId);
        if(!Files.exists(courseDir)) Files.createDirectories(courseDir);

        Path alumnos = courseDir.resolve("alumnos.csv");
        if(!Files.exists(alumnos)){
            Files.write(alumnos, Arrays.asList("rut,nombre,cursoId,anio"));
        }
        Path asistencias = courseDir.resolve("asistencias.csv");
        if(!Files.exists(asistencias)){
            Files.write(asistencias, Arrays.asList("rut,fecha,estado,justificada,motivo"));
        }
    }

    // Lista de años disponibles
    public List<Integer> listYears() throws IOException {
        if(!Files.exists(baseDir)) return Collections.emptyList();
        List<Integer> res = new ArrayList<>();
        try(DirectoryStream<Path> ds = Files.newDirectoryStream(baseDir)){
            for(Path p : ds){
                if(Files.isDirectory(p)){
                    try { res.add(Integer.parseInt(p.getFileName().toString())); }
                    catch(NumberFormatException ignore){}
                }
            }
        }
        Collections.sort(res);
        return res;
    }

    // Carga todos los cursos de un año
    public void loadYear(Colegio colegio, int year) throws IOException {
        Path yearDir = baseDir.resolve(String.valueOf(year));
        if(!Files.exists(yearDir)) throw new IOException("No existe carpeta para el año " + year);

        try(DirectoryStream<Path> ds = Files.newDirectoryStream(yearDir)){
            for(Path courseDir : ds){
                if(!Files.isDirectory(courseDir)) continue;
                String courseId = courseDir.getFileName().toString();

                // alumnos
                Path alumnosFile = courseDir.resolve("alumnos.csv");
                if(Files.exists(alumnosFile)){
                    List<String> lines = Files.readAllLines(alumnosFile);
                    for(int i=1;i<lines.size();i++){
                        String[] f = lines.get(i).split(",");
                        if(f.length<4) continue;
                        Alumno a = new Alumno(f[0], f[1], f[2]);
                        colegio.agregarAlumnoACurso(a, year);
                    }
                }

                // asistencias
                Path asistFile = courseDir.resolve("asistencias.csv");
                if(Files.exists(asistFile)){
                    List<String> lines = Files.readAllLines(asistFile);
                    for(int i=1;i<lines.size();i++){
                        String[] f = lines.get(i).split(",",5);
                        if(f.length<3) continue;
                        String rut = f[0];
                        LocalDate fecha = LocalDate.parse(f[1]);
                        EstadoAsistencia estado = EstadoAsistencia.valueOf(f[2]);
                        boolean just = (f.length>=4) && Boolean.parseBoolean(f[3]);
                        String motivo = (f.length==5)? f[4] : "";
                        Asistencia a;
                        if(just){
                            a = new AsistenciaConJustificacion(fecha, estado, true, motivo);
                        } else {
                            a = new Asistencia(fecha, estado);
                        }
                        colegio.getAlumnosMap().get(rut).registrarAsistencia(a);
                    }
                }
            }
        }
    }

    // Guarda todos los cursos de un año
    public void saveYear(Colegio colegio, int year) throws IOException {
        Path yearDir = baseDir.resolve(String.valueOf(year));
        if(!Files.exists(yearDir)) Files.createDirectories(yearDir);
        Anio an = colegio.getAniosMap().get(year);
        if(an==null) return;

        for(Curso c : an.getCursos()){
            Path courseDir = yearDir.resolve(c.getId());
            if(!Files.exists(courseDir)) Files.createDirectories(courseDir);

            Path alumnosFile = courseDir.resolve("alumnos.csv");
            List<String> aLines = new ArrayList<>();
            aLines.add("rut,nombre,cursoId,anio");
            for(Alumno al : c.getAlumnos()){
                aLines.add(String.join(",", al.getRut(), al.getNombre(), al.getCursoId(), String.valueOf(year)));
            }
            Files.write(alumnosFile, aLines);

            Path asistFile = courseDir.resolve("asistencias.csv");
            List<String> asLines = new ArrayList<>();
            asLines.add("rut,fecha,estado,justificada,motivo");
            for(Alumno al : c.getAlumnos()){
                for(Asistencia as : al.getAsistencias()){
                    boolean just=false; String motivo="";
                    if(as instanceof AsistenciaConJustificacion){
                        just=((AsistenciaConJustificacion)as).isJustificada();
                        motivo=((AsistenciaConJustificacion)as).getMotivo();
                    }
                    asLines.add(String.join(",", al.getRut(), as.getFecha().toString(), as.getEstado().name(), String.valueOf(just), motivo));
                }
            }
            Files.write(asistFile, asLines);
        }
    }
}