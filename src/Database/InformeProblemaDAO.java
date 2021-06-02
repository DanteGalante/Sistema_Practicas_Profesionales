/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 27 - mar - 2021
 * Descripción:
 * Data Access Object para la entidad InformeProblema. Se
 * encarga de realizar varias funciones relacionadas con InformeProblema
 * en la base de datos.
 */
package Database;

import Entities.InformeProblema;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para la entidad InformeProblema. Se
 * encarga de realizar varias funciones relacionadas con InformeProblema
 * en la base de datos.
 */
public class InformeProblemaDAO implements InformeProblemaDAOInterface {

    /**
     * rea una instancia de InformeProblema en la base de datos
     * @param informe el informe que se desea crear
     * @return booleano indicando éxito o fracaso
     */
    @Override
    public boolean Create( InformeProblema informe ) {
        boolean wasCreated = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "INSERT INTO InformeProblema( FechaEnviada, NumeroPersonal, Estudiante, Asunto, Contenido ) " +
                    "VALUES ( ?, ?, ?, ?, ? );";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setString( 1, informe.GetFechaEnviada() );
            statement.setString( 2, informe.GetNumeroPersonal() );
            statement.setString( 3, informe.GetEstudiante() );
            statement.setString( 4, informe.GetAsunto() );
            statement.setString( 5, informe.GetContenido() );
            statement.executeUpdate();
            wasCreated = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StartConnection();
        return wasCreated;
    }

    /**
     * Regresa una lista de InformeProblema
     * @return una lista de InformeProblema
     */
    @Override
    public List< InformeProblema > ReadAll() {
        List< InformeProblema > informes = new ArrayList<>();
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            Statement statement = connection.GetConnection().createStatement();
            ResultSet result = statement.executeQuery( "SELECT * FROM InformeProblema;" );

            while( result.next() ) {
                informes.add( new InformeProblema( result.getInt( 1 ), result.getString( 2 ),
                        result.getString( 3 ), result.getString( 4 ), result.getString( 5 ),
                        result.getString( 6 ) ) );
            }

            result.close();
            statement.close();
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return informes;
    }

    /**
     * Regresa una instancia de InformeProblema ubicado en la base de datos
     * @param identificador el identificador del informe deseado
     * @return una instancia del InformeProblema
     */
    @Override
    public InformeProblema Read( int identificador ) {
        InformeProblema informe = null;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "SELECT * FROM InformeProblema WHERE InformeProblema = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setInt( 1, identificador );
            statement.executeQuery();
            ResultSet result = statement.getResultSet();

            if( result.next() ) {
                informe = new InformeProblema( result.getInt( 1 ), result.getString( 2 ),
                        result.getString( 3 ), result.getString( 4 ), result.getString( 5 ),
                        result.getString( 6 ) );
            }

            result.close();
            statement.close();
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return informe;
    }

    /**
     * Actualiza la información de un InformeProblema en la base de datos.
     * @param informe el informe con su información actualizada
     * @return booleano indicando éxito o fracaso
     */
    @Override
    public boolean Update( InformeProblema informe ) {
        boolean updated = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "UPDATE InformeProyect SET FechaEnviada = ?, NumeroPersonal = ?, Estudiante = ?, Asunto = ?, Contenido = ?" +
                    " WHERE Identificador = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setString( 1, informe.GetFechaEnviada() );
            statement.setString( 2, informe.GetNumeroPersonal() );
            statement.setString( 3, informe.GetEstudiante() );
            statement.setString( 4, informe.GetAsunto() );
            statement.setString( 5, informe.GetContenido() );
            statement.executeUpdate();
            updated = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return updated;
    }

    /**
     * Elimina una instancia de InformeProblema de la base de datos.
     * Utiliza el identificador del informe
     * @param identificador el identificador del informe
     * @return booleano indicando éxito o fracaso
     */
    @Override
    public boolean Delete( int identificador ) {
        boolean deleted = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "DELETE FROM InformeProblema WHERE Identificador = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setInt( 1, identificador );
            statement.executeUpdate();
            deleted = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return deleted;
    }
}