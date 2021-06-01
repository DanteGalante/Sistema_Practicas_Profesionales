/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 26 - mar - 2021
 * Descripción:
 * Interfaz con los métodos necesarios para implementar
 * el patrón de Data Access Object para la entidad Docente.
 */
package Database;

import Entities.Docente;
import java.util.List;

/**
 * Interfaz con los métodos necesarios para implementar
 * el patrón de Data Access Object para la entidad Docente.
 */
public interface DocenteDAOInterface {

    /**
     * Crea un docente en la base de datos.
     * @param docente el docente que se desea crear
     * @return booleano indicando éxito o fracaso
     */
    boolean Create( Docente docente );

    /**
     * Regresa una lista con todos los docentes en la base de datos
     * @return una lista de docentes
     */
    List< Docente > ReadAll();

    /**
     * Regresa un docente de la base de datos. Utiliza el numero de personal
     * introducido para la búsqueda.
     * @param numeroPersonal el número de personal del docente deseado
     * @return la instancia del docente
     */
    Docente Read( String numeroPersonal );

    /**
     * Actualiza la información de un un docente en la base de datos
     * @param docente el docente con su información actualizada
     * @return booleano indicando éxito o fracaso
     */
    boolean Update( Docente docente );

    /**
     * Elimina un docente de la base de datos utilizando el numero personal introducido
     * @param numeroPersonal el numero personal del docente que se desea eliminar
     * @return booleano indicando éxito o fracaso
     */
    boolean Delete( String numeroPersonal );
}
