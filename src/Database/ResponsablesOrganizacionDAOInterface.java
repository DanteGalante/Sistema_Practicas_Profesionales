/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 21 - abr - 2021
 * Descripción:
 * Interfaz con los métodos necesarios para implementar
 * el patrón de Data Access Object para registrar, obtener y eliminar
 * listas de responsables proyectos por organizacion vinculada.
 */
package Database;

import java.util.List;

/**
 * Interfaz con los métodos necesarios para implementar
 * el patrón de Data Access Object para registrar, obtener y eliminar
 * listas de responsables proyectos por organizacion vinculada.
 */
public interface ResponsablesOrganizacionDAOInterface {

    /**Almacena una lista de IDs de todos los responsables de proyecto relacionados con una
     * organizacion vinculada en la base de datos.
     * @param idOrganizacion El ID de la organizacion vinculada
     * @param idResponsablesProyecto la lista de IDs de responsables de proyectos
     * @return booleano indicando éxito o fracaso
     */
    boolean Create( int idOrganizacion, List< Integer > idResponsablesProyecto );

    /**
     * Regresa una lista con todos os IDs de los responsables proyectos
     * relacionados con una organización vinculada
     * @param idOrganizacion
     * @return
     */
    List< Integer > ReadResponsables( int idOrganizacion );

    int ReadOrganizacion( int idResponsable );

    /**
     * Elimina un ID de responsable proyecto relacionado con la organizacion vinculada
     * @param idResponsableProyecto El id del responsable que se desea eliminar
     * @return booleano indicando éxito o fracaso
     */
    boolean Delete( int idResponsableProyecto );

    /**
     * Actualiza la lista de IDs de los responsables de proyectos relacionados a
     * una organización vinculada
     * @param idOrganizacion El ID de la organización vinculada
     * @param idResponsablesProyecto la lista actualizada de responsables de proyecto
     * @return booleano indicando éxito o fracaso
     */
    boolean Update( int idOrganizacion, List< Integer > idResponsablesProyecto );
}
