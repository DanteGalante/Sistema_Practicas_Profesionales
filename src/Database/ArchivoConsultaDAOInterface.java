/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 27 - mar - 2021
 * Descripción:
 * Interfaz con los métodos necesarios para implementar
 * el patrón de Data Access Object para la entidad ArchivoConsulta.
 */
package Database;

import Entities.ArchivoConsulta;

import java.util.List;

/**
 * Data Access Object para la entidad ArchivoConsulta. Se
 * encarga de realizar varias funciones relacionadas con ArchivoConsulta
 * en la base de datos.
 */
public interface ArchivoConsultaDAOInterface {

    /**
     * Crea una instancia de ArchivoConsulta en a base de datos
     * @param archivo la instancia de ArchivoConsulta que se desea crear
     * @return booleano indicando éxito o fracaso
     */
    boolean Create( ArchivoConsulta archivo );

    /**
     * Regresa una lista de ArchivoConsulta
     * @return una lista de ArchivoConsulta
     */
    List< ArchivoConsulta > ReadAll();

    /**
     * Regresa una lista de ArchivoConsulta subidos por un docente especificado
     * @return una lista de ArchivoConsulta
     */
    List< ArchivoConsulta > ReadFilesByDocente(String numeroPersonal);

    /**
     * Regresa una instancia de ArchivoConsulta de la base de datos
     * @param idArchivo el ID del archivo deseado
     * @return una instancia ArchivoConsulta
     */
    ArchivoConsulta Read( int idArchivo );

    /**
     * Actualiza la información de un archivo en la base de datos.
     * @param archivo el ArchivoConsulta con su información actualizada
     * @return booleano indicando éxito o fracaso
     */
    boolean Update( ArchivoConsulta archivo );

    /**
     * Elimina una instancia de ArchivoConsulta de la base de datos
     * utilizando el ID del archivo
     * @param idArchivo el ID del archivo
     * @return booleano indicando éxito o fracaso
     */
    boolean Delete( int idArchivo );
}