/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 27 - mar - 2021
 * Descripción:
 * Interfaz con los métodos necesarios para implementar
 * el patrón de Data Access Object para la entidad Expediente.
 */
package Database;

import Entities.Expediente;
import java.util.List;

/**
 * Interfaz con los métodos necesarios para implementar
 * el patrón de Data Access Object para la entidad Expediente.
 */
public interface ExpedienteDAOInterface {
    /**
     * Crea una isntancia de Expediente en la base de datos
     * @param expediente la instancia que se desea crear
     * @return booleano indicando éxito o fracaso
     */
    boolean Create( Expediente expediente );

    /**
     * Regresa una lista todos los expedientes en la base de datos
     * @return una lista de Expedientes
     */
    List< Expediente > ReadAll();

    /**
     * Regresa una instancia de Expediente de la base de datos
     * @param claveExpediente la clave del expediente deseado
     * @return una instancia del Expediente
     */
    Expediente Read( int claveExpediente );

    /**
     * Actualiza la información de un Expediente en la base de datos
     * @param expediente el expediente con su información actualizada
     * @return booleano indicando éxito o fracaso
     */
    boolean Update( Expediente expediente );

    /**
     * Elimina una instancia de Expediente en la base de datos
     * @param claveExpediente la clave del expediente que se desea eliminar
     * @return booleano indicando éxito o fracaso
     */
    boolean Delete( int claveExpediente );
}
