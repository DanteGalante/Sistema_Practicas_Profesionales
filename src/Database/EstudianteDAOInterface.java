/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 3 - mar - 2021
 * Descripción:
 * Interfaz con los métodos necesarios para implementar
 * el patrón de Data Access Object para la entidad Estudiante.
 */
package Database;

import Entities.Estudiante;
import java.util.List;

/**
 * Interfaz con los métodos necesarios para implementar
 * el patrón de Data Access Object para la entidad Estudiante.
 */
public interface EstudianteDAOInterface {

    /**
     * Crea un Estudiante en la base de datos.
     * @param estudiante el Estudiante que se desea crear en la base de datos
     * @return booleano indicando el éxito o fracaso
     */
    boolean Create( Estudiante estudiante );

    /**
     * Regresa una lista con todos los Estudiantes de la base de datos.
     * @return lista con todos los Estudiantes de la base de datos.
     */
    List< Estudiante > ReadAll();

    /**
     * Regresa una lista con todos los estudiantes de la base de datos, y el nombre de sus proyecto asignados
     * @return lista con todos los Estudiantes de la base de datos, con nombres de proyecto asignado
     */
    List< Estudiante > ReadAllWithProjects();

    /**
     * Regresa un estudiante de la base de datos. Utiliza una matrícula
     * para encontrar el Estudiante deseado. Regresa Null en caso de no
     * encontrar al Estudiante
     * @param matricula la matrícula del Estudiante deseado
     * @return una instancia del Estudiante
     */
    Estudiante Read( String matricula );

    /**
     * Regresa una lista de estudiantes del mismo grupo de la base de datos.
     * @param NRC numero del grupo con el que se desea buscar en la base de datos
     * @return una lista con los estudiantes de un grupo
     */
    List< Estudiante > ReadStudentsByGroup( String NRC );

    /**
     * Reresa una lista de estudiantes con un mismo estado especificado
     * @param estado estado del estudiante
     * @return una lista ocn los estudiantes de un mismo estado;
     */
    List< Estudiante > ReadByState( int estado );
    /**
     * Regresa un estudiante de la base de datos. Utiliza la matrícula
     * del estudiante para ubicarlo en la base de datos.
     * @param idUsuario el id del usuario
     * @return estudiante con la información de base de datos.
     */
    Estudiante ReadPorID( int idUsuario );

    /**
     * Actualiza un Estudiante en la base de datos con los datos del
     * Estudiante introducido.
     * @param estudiante la versión actualizada del Estudiante
     * @return booleano indicando el éxito o fracaso
     */
    boolean Update( Estudiante estudiante );

    /**
     * Elimina un Estudiante de la base de datos utilizando la
     * mastrícula introducida.
     * @param matricula la matrícula del Estudiante que se desea eliminar
     * @return booleano indicando el éxito o fracaso
     */
    boolean Delete( String matricula );

    boolean PermaDelete(String matricula);
}