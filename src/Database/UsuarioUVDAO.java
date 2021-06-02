/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 3 - mar - 2021
 * Descripción:
 * Data Access Object para la entidad UsuarioUV. Se
 * encarga de realizar varias funciones relacionadas con UsuarioUV
 * en la base de datos.
 */
package Database;

import Entities.UsuarioUV;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * Data Access Object para la entidad UsuarioUV. Se
 * encarga de realizar varias funciones relacionadas con UsuarioUV
 * en la base de datos.
 */
public class UsuarioUVDAO implements UsuarioUVDAOInterface{

    /**
     * Crea una nueva instancia de UsuarioUV en la base de datos
     * @param usuario el usuario que se desea crear en la base de datos
     * @return booleano indicando éxito o fracaso
     */
    @Override
    public boolean Create( UsuarioUV usuario ) {
        boolean wasCreated = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "INSERT INTO UsuarioUV( Nombres, Apellidos, Usuario, Contrasena, CorreoElectronico, " +
                           "Telefono ) VALUES ( ?, ?, ?, ?, ?, ? );";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setString( 1, usuario.getNombres() );
            statement.setString( 2, usuario.GetApellidos() );
            statement.setString( 3, usuario.GetUsuario() );
            statement.setString( 4, usuario.GetContrasena() );
            statement.setString( 5, usuario.GetCorreo() );
            statement.setString( 6, usuario.GetTelefono() );
            statement.executeUpdate();
            wasCreated = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StartConnection();
        return wasCreated;
    }

    /**
     * Regresa una lista con todos los UsuariosUV en la base de datos
     * @return lista con todos los UsuariosUV
     */
    @Override
    public List< UsuarioUV > ReadAll() {
        List< UsuarioUV > usuarios = new ArrayList<>();
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            Statement statement = connection.GetConnection().createStatement();
            ResultSet result = statement.executeQuery( "SELECT * FROM UsuarioUV;" );

            while( result.next() ) {
                usuarios.add( new UsuarioUV( result.getInt( 1 ), result.getString( 2 ),
                              result.getString( 3 ), result.getString( 4 ), result.getString( 5 ),
                              result.getString( 6 ), result.getString( 7 ) ) );
            }

            result.close();
            statement.close();
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return usuarios;
    }

    /**
     * Regresa un UsuarioUV de la base de base de datos. Utiliza el id del
     * usuario para encontrar el usuario deseado. Regresa Null en caso de no
     * encontrar el UsuarioUV
     * @param idUsuario el id del usuario deseado
     * @return una instancia del UsuarioUV
     */
    @Override
    public UsuarioUV Read( int idUsuario ) {
        UsuarioUV usuario = null;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "SELECT * FROM UsuarioUV WHERE IDUsuario = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setInt( 1, idUsuario );
            statement.executeQuery();
            ResultSet result = statement.getResultSet();

            if( result.next() ) {
                usuario = new UsuarioUV( result.getInt( 1 ), result.getString( 2 ), result.getString( 3 ),
                                         result.getString( 4 ), result.getString( 5 ), result.getString( 6 ),
                                         result.getString( 7 ) );
            }

            result.close();
            statement.close();
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return usuario;
    }

    /**
     * Regresa un UsuarioUV de la base de base de datos. Utiliza el nombre usuario del
     * para encontrar el usuario deseado. Regresa Null en caso de no
     * encontrar el UsuarioUV
     * @param nombreUsuario el nombre usuario
     * @return una instancia del UsuarioUV
     */
    public UsuarioUV Read( String nombreUsuario ) {
        UsuarioUV usuario = null;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "SELECT * FROM UsuarioUV WHERE Usuario = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setString( 1, nombreUsuario );
            statement.executeQuery();
            ResultSet result = statement.getResultSet();

            if( result.next() ) {
                usuario = new UsuarioUV( result.getInt( 1 ), result.getString( 2 ), result.getString( 3 ),
                        result.getString( 4 ), result.getString( 5 ), result.getString( 6 ),
                        result.getString( 7 ) );
            }

            result.close();
            statement.close();
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return usuario;
    }

    /**
     * Actualiza un UsuarioUV en la base de datos con los datos del
     * UsuarioUV introducido
     * @param usuario el usuario con datos actualizados
     * @return booleano indicando éxito o fracaso
     */
    @Override
    public boolean Update( UsuarioUV usuario ) {
        boolean updated = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "UPDATE UsuarioUV SET Nombres = ?, Apellidos = ?, Usuario = ?, Contrasena = ?," +
                           " CorreoElectronico = ?, Telefono = ? WHERE IDUsuario = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setString( 1, usuario.getNombres() );
            statement.setString( 2, usuario.GetApellidos() );
            statement.setString( 3, usuario.GetUsuario() );
            statement.setString( 4, usuario.GetContrasena() );
            statement.setString( 5, usuario.GetCorreo() );
            statement.setString( 6, usuario.GetTelefono() );
            statement.setInt( 7, usuario.GetID() );
            statement.executeUpdate();
            updated = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return updated;
    }

    /**
     * Elimina un UsuarioUV de la base de datos utilizando el id
     * introducido
     * @param idUsuario el id del usuario que se desea eliminar
     * @return booleano indicando éxito o fracaso
     */
    @Override
    public boolean Delete( String idUsuario ) {
        boolean deleted = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "DELETE FROM UsuarioUV WHERE IDUsuario = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setInt( 1, Integer.parseInt( idUsuario ) );
            statement.executeUpdate();
            deleted = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return deleted;
    }
}