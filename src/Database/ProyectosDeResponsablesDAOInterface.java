/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 21 - abr - 2021
 * Descripción:
 * Interfaz con los métodos necesarios para implementar
 * el patrón de Data Access Object para registrar, obtener y eliminar
 * listas de proyectos relacionados con un responsable proyecto.
 */
package Database;

import java.util.List;

/**
 * Interfaz con los métodos necesarios para implementar
 * el patrón de Data Access Object para registrar, obtener y eliminar
 * listas de proyectos relacionados con un responsable proyecto.
 */
public interface ProyectosDeResponsablesDAOInterface {

    /**
     * Almacena una lista de IDs de proyectos relacionados con un responsable de proyecto
     * en la base de datos.
     * @param idResponsable el responsable encargado de los proyectos
     * @param idProyectos la lista de proyectos del responsable
     * @return booleano indicando exito o fracaso
     */
    boolean Create( int idResponsable, List< Integer > idProyectos );

    /**
     * Regresa una lista de IDs de todos los proyectos
     * relacionados con el responsable de proyecto
     * @param idResponsable el ID del responsable
     * @return lista de IDs de poyectos
     */
    List< Integer > ReadProyectos( int idResponsable );

    /**
     * Regresa el ID del resposnable de proyecto relacionado
     * al ID proyecto introducido
     * @param idProyecto id del proyecto
     * @return el ID del responsable del proyecto
     */
    int ReadResponsable( int idProyecto );

    /**
     * Elimina un proyecto relacionado a un responsable de proyecto
     * @param idProyecto el proyecto que se desea eliminar de la lista
     * @return booleano indicando exito o fracaso
     */
    boolean Delete( int idProyecto );

    /**
     * Actualiza la lista de proyectos relacionados a un responsable de proyecto
     * en la base de datos.
     * @param idResponsable el ID del responsable que se desea acualizar
     * @param idProyectos la lista de IDs de proyectos actualizados
     * @return booleano indicando exito o fracaso
     */
    boolean Update( int idResponsable, List< Integer > idProyectos );
}
