/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 7 - abr - 2021
 * Descripción:
 * Interfaz con los métodos necesarios para implementar
 * el patrón de Data Access Object para la entidad OrganizacionVinculada.
 */
package Database;

import Entities.OrganizacionVinculada;

import java.util.List;

/**
 * Interfaz con los métodos necesarios para implementar
 * el patrón de Data Access Object para la entidad OrganizacionVinculada.
 */
public interface OrganizacionVinculadaDAOInterface {

    /**
     * Crea una nueva organizacion vinculada en la base de datos
     * @param organizacion la instancia de organizacion vinculada que se desea crear
     * @return booleano indicando éxito o fracaso
     */
    boolean Create( OrganizacionVinculada organizacion );

    /**
     * Regresa una lista con todas las organizaciones vinculadas de la base de datos
     * @return una lista de OrganizacionVinculada
     */
    List< OrganizacionVinculada > ReadAll();

    /**
     * Regresa una instancia de OrganizacionVinculada
     * @param idProyecto el ID del proyecto asociado con la organizacion vinculada
     * @return una instancia de OrganizacionVinculada, null en caso de no encontrarla
     */
    OrganizacionVinculada Read( int idProyecto );

    /**
     * Actualiza la información de una organizacion vinculada de la base de datos
     * @param organizacion la instancia de OrganizacionVinculada con su información actualizada
     * @return booleano indicando éxito o fracaso
     */
    boolean Update( OrganizacionVinculada organizacion );

    /**
     * Elimina una organizacion vinculada de la base de datos
     * @param idOrganizacion el ID del proyecto asociado a la organizacion
     * @return booleano indicando éxito o fracaso
     */
    boolean Delete( int idOrganizacion, List< Integer > responsablesOrganizacion );
}
