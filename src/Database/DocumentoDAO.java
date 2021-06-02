/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 28 - mar - 2021
 * Descripción:
 * Data Access Object para la entidad Documento. Se
 * encarga de realizar varias funciones relacionadas con Documento
 * en la base de datos.
 */
package Database;

import Entities.Documento;
import Utilities.FileCreator;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para la entidad Documento. Se
 * encarga de realizar varias funciones relacionadas con Documento
 * en la base de datos.
 */
public class DocumentoDAO implements DocumentoDAOInterface {
    private FileCreator creator = new FileCreator();

    /**
     * Crea una instancia de Documento en la base de datos
     * @param documento el documento que se desea crear
     * @return booleano indicando éxito o fracaso
     */
    @Override
    public boolean Create( Documento documento ) {
        boolean wasCreated = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            InputStream fileStream = new FileInputStream( documento.GetDescripcion() );
            String query = "INSERT INTO Documento( Descripcion, FechaEntrega, Titulo, ClaveExpediente ) " +
                    "VALUES ( ?, ?, ?, ? );";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setBlob( 1, fileStream );
            statement.setString( 2, documento.getFechaEntrega() );
            statement.setString( 3, documento.getTitulo() );
            statement.setInt( 4, documento.GetClaveExpediente() );
            statement.executeUpdate();
            wasCreated = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StartConnection();
        return wasCreated;
    }

    /**
     * Regresa una lista de todos los documentos en la base de datos
     * @returnuna lista de documentos
     */
    @Override
    public List< Documento > ReadAll() {
        List< Documento > documentos = new ArrayList<>();
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            Statement statement = connection.GetConnection().createStatement();
            ResultSet result = statement.executeQuery( "SELECT * FROM Documento;" );

            while( result.next() ) {
                String titulo = result.getString( 4 );
                documentos.add( new Documento( result.getInt( 1 ), titulo,
                        creator.CreateFile( titulo, result.getBlob( 2 ) ), result.getString( 3 ),
                        result.getInt( 5 ), result.getString( 6 ), result.getFloat( 7 ) ) );
            }

            result.close();
            statement.close();
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return documentos;
    }

    /**
     * Regresa una instancia de Documento
     * @param idDocumento el ID del documento deseado
     * @return la instancia del documento
     */
    @Override
    public Documento Read( int idDocumento ) {
        Documento documento = null;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "SELECT * FROM Documento WHERE IDDocumento = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setInt( 1, idDocumento );
            statement.executeQuery();
            ResultSet result = statement.getResultSet();

            if( result.next() ) {
                String retrievedTitulo = result.getString( 4 );
                documento = new Documento( result.getInt( 1 ), retrievedTitulo,
                        creator.CreateFile( retrievedTitulo, result.getBlob( 2 ) ), result.getString( 3 ),
                        result.getInt( 5 ), result.getString( 6 ), result.getFloat( 7 ) );
            }

            result.close();
            statement.close();
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return documento;
    }

    public Documento Read( String titulo, int claveExpediente ) {
        Documento documento = null;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "SELECT * FROM Documento WHERE ClaveExpediente = ? AND Titulo = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setInt( 1, claveExpediente );
            statement.setString( 2, titulo );
            statement.executeQuery();
            ResultSet result = statement.getResultSet();

            if( result.next() ) {
                String retrievedTitulo = result.getString( 4 );
                documento = new Documento( result.getInt( 1 ), retrievedTitulo,
                        creator.CreateFile( retrievedTitulo, result.getBlob( 2 ) ), result.getString( 3 ),
                        result.getInt( 5 ), result.getString( 6 ), result.getFloat( 7 )  );
            }

            result.close();
            statement.close();
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return documento;
    }

    /**
     * Actualiza la información de un documento en la base de datos
     * @param documento el documento con su información actualizada
     * @return booleano indicando éxito o fracaso
     */
    @Override
    public boolean Update( Documento documento ) {
        boolean updated = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            InputStream fileStream = new FileInputStream( documento.GetDescripcion() );
            String query = "UPDATE Documento SET Descripcion = ?, FechaEntrega = ?, Titulo = ?, ClaveExpediente = ?" +
                    " WHERE IDDocumento = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setBlob( 1, fileStream );
            statement.setString( 2, documento.getFechaEntrega() );
            statement.setString( 3, documento.getTitulo() );
            statement.setInt( 4, documento.GetClaveExpediente() );
            statement.setInt( 5, documento.getIdDocumento() );
            statement.executeUpdate();
            updated = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return updated;
    }

    /**
     * Elimina una instancia de Documento de la base de datos
     * utiliizando el ID del documento
     * @param idDocumento el ID del documento que se desea eliminar
     * @return booleano indicando éxito o fracaso
     */
    @Override
    public boolean Delete( int idDocumento ) {
        boolean deleted = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "DELETE FROM Documento WHERE IDDocumento = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setInt( 1, idDocumento );
            statement.executeUpdate();
            deleted = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return deleted;
    }
}