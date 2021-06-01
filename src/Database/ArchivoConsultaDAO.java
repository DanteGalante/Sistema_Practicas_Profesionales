/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 28 - mar - 2021
 * Descripción:
 * Data Access Object para la entidad ArchivoConsulta. Se
 * encarga de realizar varias funciones relacionadas con ArchivoConsulta
 * en la base de datos.
 */
package Database;

import Entities.ArchivoConsulta;
import Utilities.FileCreator;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para la entidad ArchivoConsulta. Se
 * encarga de realizar varias funciones relacionadas con ArchivoConsulta
 * en la base de datos.
 */
public class ArchivoConsultaDAO implements ArchivoConsultaDAOInterface {
    private FileCreator creator = new FileCreator();
    /**
     * Crea una instancia de ArchivoConsulta en a base de datos
     * @param archivo la instancia de ArchivoConsulta que se desea crear
     * @return booleano indicando éxito o fracaso
     */
    @Override
    public boolean Create( ArchivoConsulta archivo ) {
        boolean wasCreated = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            InputStream fileStream = new FileInputStream( archivo.GetDescripcion() );
            String query = "INSERT INTO ArchivoConsulta( Titulo, Descripcion, NumeroPersonal ) " +
                    "VALUES ( ?, ?, ? );";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setString( 1, archivo.getTitulo() );
            statement.setBlob( 2, fileStream );
            statement.setString( 3, archivo.GetNumeroPersonal() );
            statement.executeUpdate();
            wasCreated = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StartConnection();
        return wasCreated;
    }

    /**
     * Regresa una lista de ArchivoConsulta
     * @return una lista de ArchivoConsulta
     */
    @Override
    public List< ArchivoConsulta > ReadAll() {
        List< ArchivoConsulta > archivos = new ArrayList<>();
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            Statement statement = connection.GetConnection().createStatement();
            ResultSet result = statement.executeQuery( "SELECT * FROM ArchivoConsulta;" );

            while( result.next() ) {
                String titulo = result.getString( 2 );
                archivos.add( new ArchivoConsulta( result.getInt( 1 ),
                        titulo, creator.CreateFile( titulo, result.getBlob( 3 ) ),
                        result.getString( 4 ) ) );
            }

            result.close();
            statement.close();
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return archivos;
    }

    /**
     * Regresa una instancia de ArchivoConsulta de la base de datos
     * @param idArchivo el ID del archivo deseado
     * @return una instancia ArchivoConsulta
     */
    @Override
    public ArchivoConsulta Read( int idArchivo ) {
        ArchivoConsulta archivo = null;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "SELECT * FROM ArchivoConsulta WHERE IDArchivo = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setInt( 1, idArchivo );
            statement.executeQuery();
            ResultSet result = statement.getResultSet();

            if( result.next() ) {
                String titulo = result.getString( 2 );
                archivo = new ArchivoConsulta( result.getInt( 1 ),
                        titulo, creator.CreateFile( titulo, result.getBlob( 3 ) ),
                        result.getString( 4 ) );
            }

            result.close();
            statement.close();
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return archivo;
    }

    /**
     * Actualiza la información de un archivo en la base de datos.
     * @param archivo el ArchivoConsulta con su información actualizada
     * @return booleano indicando éxito o fracaso
     */
    @Override
    public boolean Update( ArchivoConsulta archivo ) {
        boolean updated = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            InputStream fileStream = new FileInputStream( archivo.GetDescripcion() );
            String query = "UPDATE ArchivoConsulta SET Titulo = ?, Descripcion = ?, NumeroPersonal = ? " +
                           "WHERE IDResponsableProyecto = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setString( 1, archivo.getTitulo() );
            statement.setBlob( 2, fileStream );
            statement.setString( 3, archivo.GetNumeroPersonal() );
            statement.setInt( 4, archivo.GetId() );
            statement.executeUpdate();
            updated = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return updated;
    }

    /**
     * Elimina una instancia de ArchivoConsulta de la base de datos
     * utilizando el ID del archivo
     * @param idArchivo el ID del archivo
     * @return booleano indicando éxito o fracaso
     */
    @Override
    public boolean Delete( int idArchivo ) {
        boolean deleted = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "DELETE FROM ArchivoConsulta WHERE IDArchivo = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setInt( 1, idArchivo );
            statement.executeUpdate();
            deleted = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return deleted;
    }
}