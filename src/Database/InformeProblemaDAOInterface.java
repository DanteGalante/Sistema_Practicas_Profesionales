/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 27 - mar - 2021
 * Descripción:
 * Interfaz con los métodos necesarios para implementar
 * el patrón de Data Access Object para la entidad InformeProblema.
 */
package Database;

import Entities.InformeProblema;

import java.util.List;

/**
 * Interfaz con los métodos necesarios para implementar
 * el patrón de Data Access Object para la entidad InformeProblema.
 */
public interface InformeProblemaDAOInterface {

    /**
     * rea una instancia de InformeProblema en la base de datos
     * @param informe el informe que se desea crear
     * @return booleano indicando éxito o fracaso
     */
    boolean Create( InformeProblema informe );

    /**
     * Regresa una lista de InformeProblema
     * @return una lista de InformeProblema
     */
    List< InformeProblema > ReadAll();

    /**
     * Regresa una instancia de InformeProblema ubicado en la base de datos
     * @param identificador el identificador del informe deseado
     * @return una instancia del InformeProblema
     */
    InformeProblema Read( int identificador );

    /**
     * Actualiza la información de un InformeProblema en la base de datos.
     * @param informe el informe con su información actualizada
     * @return booleano indicando éxito o fracaso
     */
    boolean Update( InformeProblema informe );

    /**
     * Elimina una instancia de InformeProblema de la base de datos.
     * Utiliza el identificador del informe
     * @param identificador el identificador del informe
     * @return booleano indicando éxito o fracaso
     */
    boolean Delete( int identificador );
}
