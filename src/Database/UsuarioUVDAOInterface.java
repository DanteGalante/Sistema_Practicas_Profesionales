/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 3 - mar - 2021
 * Descripción:
 * Interfaz con los métodos necesarios para implementar
 * el patrón de Data Access Object para la entidad UsuarioUV.
 */
package Database;

import Entities.UsuarioUV;
import java.util.List;

/**
 * Interfaz con los métodos necesarios para implementar
 * el patrón de Data Access Object para la entidad UsuarioUV.
 */
public interface UsuarioUVDAOInterface {

    /**
     * Crea una nueva instancia de UsuarioUV en la base de datos
     * @param usuario el usuario que se desea crear en la base de datos
     * @return booleano indicando éxito o fracaso
     */
    boolean Create( UsuarioUV usuario );

    /**
     * Regresa una lista con todos los UsuariosUV en la base de datos
     * @return lista con todos los UsuariosUV
     */
    List< UsuarioUV > ReadAll();

    /**
     * Regresa un UsuarioUV de la base de base de datos. Utiliza el id del
     * usuario para encontrar el usuario deseado. Regresa Null en caso de no
     * encontrar el UsuarioUV
     * @param idUsuario el id del usuario deseado
     * @return una instancia del UsuarioUV
     */
    UsuarioUV Read( int idUsuario );

    /**
     * Regresa un UsuarioUV de la base de base de datos. Utiliza el nombre usuario del
     * para encontrar el usuario deseado. Regresa Null en caso de no
     * encontrar el UsuarioUV
     * @param nombreUsuario el nombre usuario
     * @return una instancia del UsuarioUV
     */
    UsuarioUV Read( String nombreUsuario );

    /**
     * Actualiza un UsuarioUV en la base de datos con los datos del
     * UsuarioUV introducido
     * @param usuario el usuario con datos actualizados
     * @return booleano indicando éxito o fracaso
     */
    boolean Update( UsuarioUV usuario );

    /**
     * Elimina un UsuarioUV de la base de datos utilizando el id
     * introducido
     * @param idUsuario el id del usuario que se desea eliminar
     * @return booleano indicando éxito o fracaso
     */
    boolean Delete( String idUsuario );
}
