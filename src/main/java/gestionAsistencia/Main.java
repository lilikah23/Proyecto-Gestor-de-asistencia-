package gestionAsistencia;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Forzar UTF-8 en salida y en Scanner
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

        Colegio colegio = new Colegio("Colegio Ejemplo");
        int opcion;

        do {
            opcion = leerEntero(scanner,
                "\n--- Menú de Gestión de Asistencia ---\n" +
                "1. Agregar alumno\n" +
                "2. Registrar asistencia\n" +
                "3. Listar alumnos\n" +
                "4. Listar asistencias de un alumno\n" +
                "0. Salir\n" +
                "Elija una opción: "
            );

            switch (opcion) {
                case 1 -> {
                    int id = leerRut(scanner);
                    if (colegio.existeAlumno(id)) {
                        System.out.println("Error: El alumno con ID " + id + " ya se encuentra registrado.");
                    } else {
                        System.out.print("Ingrese nombre: ");
                        String nombre = scanner.nextLine();

                        System.out.print("Ingrese curso: ");
                        String curso = scanner.nextLine();

                        colegio.agregarAlumno(new Alumno(id, nombre, curso));
                        System.out.println("Alumno agregado correctamente.");
                    }
                }
                case 2 -> {
                    int idAlumno = leerEntero(scanner, "Ingrese ID del alumno: ");
                    LocalDate fecha = leerFecha(scanner, "Ingrese fecha (YYYY-MM-DD): ");

                    boolean presente = leerSiNo(scanner, "¿Presente? (SI/NO): ");
                    boolean salidaAnticipada = false;
                    if (presente) {
                        salidaAnticipada = leerSiNo(scanner, "¿Salida anticipada? (SI/NO): ");
                    }

                    colegio.registrarAsistencia(idAlumno, fecha, presente, salidaAnticipada);
                    System.out.println("Asistencia registrada correctamente.");
                }
                case 3 -> {
                    System.out.println("Listado de alumnos:");
                    colegio.mostrarAlumnos();
                }
                case 4 -> {
                    int idMostrar = leerEntero(scanner, "Ingrese ID del alumno para mostrar asistencias: ");
                    colegio.mostrarAsistenciasDeAlumno(idMostrar);
                }
                case 0 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);

        scanner.close();
    }

    /**
     * Pide y valida un entero.
     */
    private static int leerEntero(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String linea = scanner.nextLine();
            try {
                return Integer.parseInt(linea.trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor ingrese un número entero.");
            }
        }
    }

    /**
     * Pide y valida un RUT (solo dígitos, sin puntos ni guion).
     */
    private static int leerRut(Scanner scanner) {
        while (true) {
            System.out.print("Ingrese RUT (solo dígitos): ");
            String raw = scanner.nextLine()
                                .replace(".", "")
                                .replace("-", "")
                                .trim();
            try {
                return Integer.parseInt(raw);
            } catch (NumberFormatException e) {
                System.out.println("RUT inválido. Debe contener únicamente números.");
            }
        }
    }

    /**
     * Pide y valida una fecha con formato YYYY-MM-DD.
     */
    private static LocalDate leerFecha(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String fechaStr = scanner.nextLine().trim();
            try {
                return LocalDate.parse(fechaStr);
            } catch (DateTimeParseException e) {
                System.out.println("Fecha inválida. Use el formato YYYY-MM-DD.");
            }
        }
    }

    /**
     * Pide y valida una respuesta SI/NO.
     */
    private static boolean leerSiNo(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String resp = scanner.nextLine().trim();
            if (resp.equalsIgnoreCase("SI"))   return true;
            if (resp.equalsIgnoreCase("NO"))   return false;
            System.out.println("Entrada no válida. Escriba SI o NO.");
        }
    }
}
