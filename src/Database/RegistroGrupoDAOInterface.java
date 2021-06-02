/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 30 - abr - 2021
 * Descripción:
 * Interfaz con los métodos necesarios para implementar
 * el patrón de Data Access Object para la entidad RegistroGrupo.
 */
package Database;

import Entities.RegistroGrupo;
import java.util.List;

/**
 * Interfaz con los métodos necesarios para implementar
 * el patrón de Data Access Object para la entidad RegistroGrupo.
 */
public interface RegistroGrupoDAOInterface {

    /**
     * Almacena una instancia de RegistroGrupo a la base de datos
     * @param registro el registroGrupo que se desea crear
     * @return true si la operación fue exitosa, false si no
     */
    boolean Create( RegistroGrupo registro );

    /**
     * Regresa una lista con todos los RegistrosGrupos almacenados
     * en la base de datos
     * @return una lista de RegistroGrupo
     */
    List< RegistroGrupo > ReadAll();

    /**
     * Elimina un RegistroGrupo de la base de datos
     * @param Identificador el identificador del registroGrupo que se desea eliminar
     * @return true si la operación fue exitosa, false si no
     */
    boolean Delete( String Identificador );

    /**
     * Actualiza un RegistroGrupo almacenado en la base de datos
     * @param registro el registrGrupo que se desea actualizar
     * @return true si la operación fue exitosa, false si no
     */
    boolean Update( RegistroGrupo registro );
}
