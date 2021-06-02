/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 26 - mar - 2021
 * Descripción:
 * Interfaz con los métodos necesarios para implementar
 * el patrón de Data Access Object para la entidad Coordinador.
 */
package Database;

import Entities.Coordinador;
import java.util.List;

/**
 * Interfaz con los métodos necesarios para implementar
 * el patrón de Data Access Object para la entidad Coordinador.
 */
public interface CoordinadorDAOInterface {

    /**
     * Crea un Coordinador en la base de datos.
     * @param coordinador el coordinador que se desea crear en la base de datos
     * @return booleano indicando éxito o fracaso
     */
    boolean Create( Coordinador coordinador );

    /**
     * Regresa una lista de todos los coordinadores en la base de datos.
     * @return una lista de coordinadores
     */
    List< Coordinador > ReadAll();

    /**
     * Regresa un Coordinador de la base de datos. Utiliza el número de personal
     * introducido para ubicar la instancia deseada de coordiandor
     * @param numeroPersonal el número personal del coordinador deseado
     * @return una instancia del coordinador
     */
    Coordinador Read( String numeroPersonal );

    /**
     * Actualiza la información de un coordinador en la base de datos
     * @param coordinador el coordinador con su información actualizada
     * @return booleano indicando éxito o fracaso
     */
    boolean Update( Coordinador coordinador );

    /**
     * Elimina un coordinador de la base de datos utilizando el número de personal introducido
     * @param numeroPersonal el número personal del coordinador que se desea eliminar
     * @return boolean indicando éxito o fracaso
     */
    boolean Delete( String numeroPersonal );
}
