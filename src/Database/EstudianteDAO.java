/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 3 - mar - 2021
 * Descripción:
 * Data Access Object para la entidad Estudiante. Se
 * encarga de realizar varias funciones relacionadas con Estudiantes
 * en la base de datos.
 */
package Database;

import Entities.Estudiante;
import Entities.UsuarioUV;
import Enumerations.EstadoEstudiante;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para la entidad Estudiante. Se
 * encarga de realizar varias funciones relacionadas con Estudiantes
 * en la base de datos.
 */
public class EstudianteDAO implements EstudianteDAOInterface{
    private UsuarioUVDAO usuarios = new UsuarioUVDAO();

    /**
     * Crea un nuevo estudiante en la base de datos.
     * @param estudiante el Estudiante que se desea crear en la base de datos
     * @return booleano indicando éxito o fracaso
     */
    @Override
    public boolean Create( Estudiante estudiante ) {
        boolean wasCreated = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            usuarios.Create( new UsuarioUV( estudiante.GetID(), estudiante.getNombres(), estudiante.GetApellidos(),
                                            estudiante.GetUsuario(), estudiante.GetContrasena(), estudiante.GetCorreo(),
                                            estudiante.GetTelefono() ) );
            UsuarioUV usuarioTemp = usuarios.Read( estudiante.GetUsuario() );
            String query = "INSERT INTO Estudiante( Matricula, IDUsuario, NRC, Estado ) VALUES( ?, ?, ?, ? );";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setString( 1, estudiante.getMatricula() );
            statement.setInt( 2, usuarioTemp.GetID() );
            statement.setString( 3, estudiante.getNrc() );
            statement.setInt( 4, estudiante.GetEstado().ordinal() );
            statement.executeUpdate();

            wasCreated = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }
        return wasCreated;
    }

    /**
     * Regresa una lista con todos los estudiantes en la base de datos.
     * @return lista de estudiantes
     */
    @Override
    public List< Estudiante > ReadAll() {
        List< Estudiante > estudiantes = new ArrayList<>();
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            Statement statement = connection.GetConnection().createStatement();
            ResultSet result = statement.executeQuery( "SELECT * FROM Estudiante;" );

            while( result.next() )
            {
                UsuarioUV usuarioTemp = usuarios.Read( result.getInt( 2 ) );
                estudiantes.add( new Estudiante( usuarioTemp, result.getString( 1 ), result.getString( 3 ),
                                                 EstadoEstudiante.values()[ result.getInt( 4 ) ] ) );
            }

            result.close();
            statement.close();
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return estudiantes;
    }

    /**
     * Regresa un estudiante de la base de datos. Utiliza la matrícula
     * del estudiante para ubicarlo en la base de datos.
     * @param matricula la matrícula del Estudiante deseado
     * @return estudiante con la información de base de datos.
     */
    @Override
    public Estudiante Read( String matricula ) {
        Estudiante estudiante = null;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "SELECT * FROM Estudiante WHERE Matricula = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setString( 1,  matricula );
            statement.executeQuery();
            ResultSet result = statement.getResultSet();

            if( result.next() ) {
                int idUsuario = result.getInt( 2 );
                String nrc = result.getString( 3 );
                EstadoEstudiante estado = EstadoEstudiante.values()[ result.getInt( 4 ) ];

                UsuarioUV usuario = usuarios.Read( idUsuario );
                estudiante = new Estudiante( usuario, matricula, nrc, estado );
            }
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return estudiante;
    }

    @Override
    public List< Estudiante > ReadByGroup( String NRC ) {
        List< Estudiante > estudiantes = new ArrayList<>();
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "SELECT * FROM Estudiante WHERE NRC = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setString( 1,  NRC );
            statement.executeQuery();
            ResultSet result = statement.getResultSet();

            while( result.next() )
            {
                UsuarioUV usuarioTemp = usuarios.Read( result.getInt( 2 ) );
                estudiantes.add( new Estudiante( usuarioTemp, result.getString( 1 ), result.getString( 3 ),
                        EstadoEstudiante.values()[ result.getInt( 4 ) ] ) );
            }

            result.close();
            statement.close();
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return estudiantes;
    }

    /**
     * Reresa una lista de estudiantes con un mismo estado especificado
     * @param estado estado del estudiante
     * @return una lista con los estudiantes de un mismo estado;
     */
    public List< Estudiante > ReadByState( int estado ){
        List< Estudiante > estudiantes = new ArrayList<>();
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "SELECT * FROM Estudiante WHERE Estado = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setInt( 1,  estado );
            statement.executeQuery();
            ResultSet result = statement.getResultSet();

            while( result.next() )
            {
                UsuarioUV usuarioTemp = usuarios.Read( result.getInt( 2 ) );
                estudiantes.add( new Estudiante( usuarioTemp, result.getString( 1 ), result.getString( 3 ),
                        EstadoEstudiante.values()[ result.getInt( 4 ) ] ) );
            }

            result.close();
            statement.close();
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return estudiantes;
    }

    /**
     * Actualiza la información de un estudiante en la base de datos.
     * @param estudiante la versión actualizada del Estudiante
     * @return booleano indicando éxito o fracaso
     */
    @Override
    public boolean Update( Estudiante estudiante ) {
        boolean updated = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "UPDATE Estudiante SET NRC = ?, Estado = ? WHERE Matricula = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setString( 1, estudiante.getNrc() );
            statement.setInt( 2, estudiante.GetEstado().ordinal() );
            statement.setString( 3, estudiante.getMatricula() );
            statement.executeUpdate();

            usuarios.Update( new UsuarioUV( estudiante.GetID(), estudiante.getNombres(), estudiante.GetApellidos(),
                                            estudiante.GetUsuario(), estudiante.GetContrasena(), estudiante.GetCorreo(),
                                            estudiante.GetTelefono() ) );

            updated = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return updated;
    }

    /**
     *Elimina un estudiante de la base de datos utilizando la matrícula introducida.
     * @param matricula la matrícula del Estudiante que se desea eliminar
     * @return booleano indica éxito o fracaso
     */
    @Override
    public boolean Delete( String matricula ) {
        boolean deleted = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            Estudiante estudiante = Read( matricula );
            String query = "DELETE FROM Estudiante WHERE Matricula = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setString( 1,  matricula );
            statement.executeUpdate();
            usuarios.Delete( Integer.toString( estudiante.GetID() ) );

            deleted = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return deleted;
    }
}
