# Gestión de Asistencia Escolar

Breve aplicación de consola en Java para registrar alumnos y sus asistencias, con validación de entradas y soporte UTF-8 (tildes y caracteres especiales).

---

## Compilación y Ejecución

Requisitos:  
- Java Development Kit (JDK) 11 o superior  

1. Abre una terminal en la raíz del proyecto (donde está la carpeta `src/`).  

2. Compila los archivos Java en UTF-8 y coloca las clases en `out/`:

   ```bash
   javac -encoding UTF-8 -d out src/gestionAsistencia/*.java
   ```

3. Ejecuta la aplicación forzando la codificación UTF-8:

   ```bash
   java -Dfile.encoding=UTF-8 -cp out gestionAsistencia.Main
   ```  

Listo: la aplicación mostrará el menú de gestión de alumnos y asistencias correctamente en la consola.
