# README - Gestor de Asistencia

## Descripción
Sistema de gestión de asistencia escolar desarrollado en **Java** con interfaz gráfica **Swing**.

## Requisitos del Sistema
- Java Development Kit (JDK) 8 o superior  
- Sistema operativo compatible con Java (Windows, macOS o Linux)

## Estructura del Proyecto
El proyecto sigue la estructura estándar de **Maven**, con paquetes organizados de la siguiente forma:

- `edu.colegio.model` → Clases del modelo de datos  
- `edu.colegio.persistence` → Gestión de persistencia de datos  
- `edu.colegio.ui` → Interfaces gráficas de usuario  

## Compilación

### Opción 1: Compilación manual con `javac`
```bash
# Crear directorio para clases compiladas
mkdir -p build/classes

# Compilar todas las clases Java
javac -d build/classes -cp "." src/main/java/edu/colegio/**/*.java

# Crear archivo JAR ejecutable
jar cfm gestor-asistencia.jar MANIFEST.MF -C build/classes .
```

### Opción 2: Usando Maven (si existe `pom.xml`)
```bash
# Compilar el proyecto
mvn compile

# Crear JAR ejecutable
mvn package
```

## Ejecución
```bash
# Ejecutar desde clases compiladas
java -cp build/classes edu.colegio.ui.LoadWindow

# O ejecutar desde el JAR
java -jar gestor-asistencia.jar
```

## Funcionalidades Principales
- Creación y gestión de años académicos  
- Gestión de cursos y alumnos  
- Registro de asistencias  
- Generación de reportes en formato TXT  

## Almacenamiento de Datos
Los datos se almacenan en la carpeta `data/` en formato **CSV**, organizados por año y curso.  

---

## Notas
El proyecto es una aplicación Java Swing que gestiona la asistencia escolar.  
La clase principal de entrada es **`LoadWindow`**, que permite crear nuevos años académicos o cargar años existentes.  
El sistema utiliza **archivos CSV** para la persistencia de datos y mantiene una clara separación entre el modelo, la persistencia y la interfaz gráfica.
