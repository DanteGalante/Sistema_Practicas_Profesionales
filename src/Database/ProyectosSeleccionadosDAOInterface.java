/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 27 - mar - 2021
 * Descripción:
 * Interfaz con los métodos necesarios para implementar
 * el patrón de Data Access Object para registrar, obtener y eliminar
 * listas de proyectos seleccionados por Estudiantes de la base de datos.
 */
package Database;

import java.util.ArrayList;
import java.util.List;

/**
 * Interfaz con los métodos necesarios para implementar
 * el patrón de Data Access Object para registrar, obtener y eliminar
 * listas de proyectos seleccionados por Estudiantes de la base de datos.
 */
public interface ProyectosSeleccionadosDAOInterface {
    boolean Create( String matricula, List< Integer > idProyectos );
    List< Integer > Read( String matricula );
    boolean Delete( String matricula );
}
