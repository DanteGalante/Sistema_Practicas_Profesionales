/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 28 - mar - 2021
 * Descripción:
 * Data Access Object para la entidad Expediente. Se
 * encarga de realizar varias funciones relacionadas con Expediente
 * en la base de datos.
 */
package Database;

import Entities.Expediente;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para la entidad Expediente. Se
 * encarga de realizar varias funciones relacionadas con Expediente
 * en la base de datos.
 */
public class ExpedienteDAO implements ExpedienteDAOInterface{
    /**
     * Crea una isntancia de Expediente en la base de datos
     * @param expediente la instancia que se desea crear
     * @return booleano indicando éxito o fracaso
     */
    @Override
    public boolean Create( Expediente expediente ) {
        boolean wasCreated = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "INSERT INTO Expediente( IDProyecto, Matricula, FechaAsignacion, HorasAcumuladas, NumeroArchivos ) " +
                    "VALUES ( ?, ?, ?, ?, ? );";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setInt( 1, expediente.GetIDProyecto() );
            statement.setString( 2, expediente.GetMatricula() );
            statement.setString( 3, expediente.GetFechaAsignacion() );
            statement.setInt( 4, expediente.GetHorasAcumuladas() );
            statement.setInt( 5, expediente.GetNumeroArchivos() );
            statement.executeUpdate();
            wasCreated = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StartConnection();
        return wasCreated;
    }

    /**
     * Regresa una lista todos los expedientes en la base de datos
     * @return una lista de Expedientes
     */
    @Override
    public List< Expediente > ReadAll() {
        List< Expediente > expediente = new ArrayList<>();
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            Statement statement = connection.GetConnection().createStatement();
            ResultSet result = statement.executeQuery( "SELECT * FROM Expediente;" );

            while( result.next() ) {
                expediente.add( new Expediente( result.getInt( 1 ), result.getInt( 2 ),
                        result.getString( 3 ), result.getString( 4 ), result.getInt( 5 ),
                        result.getInt( 6 ) ) );
            }

            result.close();
            statement.close();
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return expediente;
    }

    /**
     * Regresa una instancia de Expediente de la base de datos
     * @param claveExpediente la clave del expediente deseado
     * @return una instancia del Expediente
     */
    @Override
    public Expediente Read( int claveExpediente ) {
        Expediente expediente = null;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "SELECT * FROM Expediente WHERE ClaveExpediente = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setInt( 1, claveExpediente );
            statement.executeQuery();
            ResultSet result = statement.getResultSet();

            if( result.next() ) {
                expediente = new Expediente( result.getInt( 1 ), result.getInt( 2 ),
                        result.getString( 3 ), result.getString( 4 ), result.getInt( 5 ),
                        result.getInt( 6 ) );
            }

            result.close();
            statement.close();
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return expediente;
    }

    /**
     * Actualiza la información de un Expediente en la base de datos
     * @param expediente el expediente con su información actualizada
     * @return booleano indicando éxito o fracaso
     */
    @Override
    public boolean Update( Expediente expediente ) {
        boolean updated = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "UPDATE Expediente SET IDProyecto = ?, Matricula = ?, FechaAsignacion = ?, HorasAcumuladas = ?, NumeroArchivos = ?" +
                    " WHERE ClaveExpediente = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setInt( 1, expediente.GetIDProyecto() );
            statement.setString( 2, expediente.GetMatricula() );
            statement.setString( 3, expediente.GetFechaAsignacion() );
            statement.setInt( 4, expediente.GetHorasAcumuladas() );
            statement.setInt( 5, expediente.GetNumeroArchivos() );
            statement.setInt( 6, expediente.GetClave() );
            statement.executeUpdate();
            updated = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return updated;
    }

    /**
     * Elimina una instancia de Expediente en la base de datos
     * @param claveExpediente la clave del expediente que se desea eliminar
     * @return booleano indicando éxito o fracaso
     */
    @Override
    public boolean Delete( int claveExpediente ) {
        boolean deleted = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "DELETE FROM Expediente WHERE ClaveExpediente = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setInt( 1, claveExpediente );
            statement.executeUpdate();
            deleted = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return deleted;
    }
}