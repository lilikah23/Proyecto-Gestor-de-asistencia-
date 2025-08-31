package gestionAsistencia;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Colegio colegio = new Colegio("Colegio Ejemplo");
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- Menú de Gestión de Asistencia ---");
            System.out.println("1. Agregar alumno");
            System.out.println("2. Registrar asistencia");
            System.out.println("3. Listar alumnos");
            System.out.println("4. Listar asistencias de un alumno");
            System.out.println("0. Salir");
            System.out.print("Elija una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea

            switch (opcion) {
                case 1 -> {
                    System.out.print("Ingrese RUT (ID numérico sin puntos ni guion): ");
                    String rutStr = scanner.nextLine();
                    int id;
                    try {
                        // Convertir RUT a número eliminando puntos y guion
                        String rutNumerico = rutStr.replace(".", "").replace("-", "");
                        id = Integer.parseInt(rutNumerico);
                    } catch (NumberFormatException e) {
                        System.out.println("RUT inválido. Debe ser un número.");
                        break;
                    }

                    System.out.print("Ingrese nombre: ");
                    String nombre = scanner.nextLine();

                    System.out.print("Ingrese curso: ");
                    String curso = scanner.nextLine();

                    Alumno alumno = new Alumno(id, nombre, curso);
                    colegio.agregarAlumno(alumno);
                    System.out.println("Alumno agregado correctamente.");
                }

                case 2 -> {
                    System.out.print("Ingrese ID del alumno: ");
                    int idAlumno = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Ingrese fecha (YYYY-MM-DD): ");
                    String fechaStr = scanner.nextLine();
                    LocalDate fecha;
                    try {
                        fecha = LocalDate.parse(fechaStr);
                    } catch (Exception e) {
                        System.out.println("Fecha inválida.");
                        break;
                    }

                    System.out.print("¿Presente? (true/false): ");
                    boolean presente = scanner.nextBoolean();

                    System.out.print("¿Salida anticipada? (true/false): ");
                    boolean salidaAnticipada = scanner.nextBoolean();
                    scanner.nextLine();

                    // Registrar asistencia con sobrecarga
                    colegio.registrarAsistencia(idAlumno, fecha, presente, salidaAnticipada);
                    System.out.println("Asistencia registrada correctamente.");
                }

                case 3 -> {
                    System.out.println("Listado de alumnos:");
                    colegio.mostrarAlumnos();
                }

                case 4 -> {
                    System.out.print("Ingrese ID del alumno para mostrar asistencias: ");
                    int idMostrar = scanner.nextInt();
                    scanner.nextLine();
                    colegio.mostrarAsistenciasDeAlumno(idMostrar);
                }

                case 0 -> System.out.println("Saliendo del programa...");

                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 0);

        scanner.close();
    }
}