/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 3 - mar - 2021
 * Descripción:
 * Data Access Object para la entidad Coordinador. Se
 * encarga de realizar varias funciones relacionadas con Coordinadores
 * en la base de datos.
 */
package Database;

import Entities.Coordinador;
import Entities.UsuarioUV;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para la entidad Coordinador. Se
 * encarga de realizar varias funciones relacionadas con Coordinadores
 * en la base de datos.
 */
public class CoordinadorDAO implements CoordinadorDAOInterface{
    private UsuarioUVDAO usuarios = new UsuarioUVDAO();

    /**
     * Crea un Coordinador en la base de datos.
     * @param coordinador el coordinador que se desea crear en la base de datos
     * @return booleano indicando éxito o fracaso
     */
    @Override
    public boolean Create( Coordinador coordinador ) {
        boolean wasCreated = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            usuarios.Create( new UsuarioUV( coordinador.GetID(), coordinador.getNombres(), coordinador.GetApellidos(),
                    coordinador.GetUsuario(), coordinador.GetContrasena(), coordinador.GetCorreo(),
                    coordinador.GetTelefono() ) );
            UsuarioUV usuarioTemp = usuarios.Read( coordinador.GetUsuario() );

            String query = "INSERT INTO Coordinador( NumeroPersonal, IDUsuario ) VALUES( ?, ? );";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setString( 1, coordinador.GetNumeroPersonal() );
            statement.setInt( 2, usuarioTemp.GetID() );
            statement.executeUpdate();

            wasCreated = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }
        return wasCreated;
    }

    /**
     * Regresa una lista de todos los coordinadores en la base de datos.
     * @return una lista de coordinadores
     */
    @Override
    public List< Coordinador > ReadAll() {
        List< Coordinador > coordinadores = new ArrayList<>();
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            Statement statement = connection.GetConnection().createStatement();
            ResultSet result = statement.executeQuery( "SELECT * FROM Coordinador;" );

            while( result.next() )
            {
                UsuarioUV usuarioTemp = usuarios.Read( result.getInt( 2 ) );
                coordinadores.add( new Coordinador( usuarioTemp, result.getString( 1 ) ) );
            }

            result.close();
            statement.close();
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return coordinadores;
    }

    /**
     * Regresa un Coordinador de la base de datos. Utiliza el número de personal
     * introducido para ubicar la instancia deseada de coordiandor
     * @param numeroPersonal el número personal del coordinador deseado
     * @return una instancia del coordinador
     */
    @Override
    public Coordinador Read( String numeroPersonal ) {
        Coordinador coordinador = null;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "SELECT * FROM Coordinador WHERE NumeroPersonal = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setString( 1,  numeroPersonal );
            statement.executeQuery();
            ResultSet result = statement.getResultSet();

            if( result.next() ) {
                int idUsuario = result.getInt( 2 );

                UsuarioUV usuario = usuarios.Read( idUsuario );
                coordinador = new Coordinador( usuario, numeroPersonal );
            }
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return coordinador;
    }

    /**
     * Actualiza la información de un coordinador en la base de datos
     * @param coordinador el coordinador con su información actualizada
     * @return booleano indicando éxito o fracaso
     */
    @Override
    public boolean Update( Coordinador coordinador ) {
        boolean updated = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            usuarios.Update( new UsuarioUV( coordinador.GetID(), coordinador.getNombres(), coordinador.GetApellidos(),
                    coordinador.GetUsuario(), coordinador.GetContrasena(), coordinador.GetCorreo(),
                    coordinador.GetTelefono() ) );

            updated = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return updated;
    }

    /**
     * Elimina un coordinador de la base de datos utilizando el número de personal introducido
     * @param numeroPersonal el número personal del coordinador que se desea eliminar
     * @return boolean indicando éxito o fracaso
     */
    @Override
    public boolean Delete( String numeroPersonal ) {
        boolean deleted = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            Coordinador coordinador  = Read( numeroPersonal );
            String query = "DELETE FROM Docente WHERE NumeroPersonal = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setString( 1,  numeroPersonal );
            statement.executeUpdate();
            usuarios.Delete( Integer.toString( coordinador.GetID() ) );

            deleted = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return deleted;
    }
}