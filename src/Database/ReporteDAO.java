/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 28 - mar - 2021
 * Descripción:
 * Data Access Object para la entidad Reporte. Se
 * encarga de realizar varias funciones relacionadas con Reportes
 * en la base de datos.
 */
package Database;

import Entities.Documento;
import Entities.Reporte;
import Enumerations.TipoReporte;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para la entidad Reporte. Se
 * encarga de realizar varias funciones relacionadas con Reportes
 * en la base de datos.
 */
public class ReporteDAO implements ReporteDAOInterface{
    private DocumentoDAO documentos = new DocumentoDAO();

    /**
     * Crea una instancia de Reporte en la base de datos
     * @param reporte la Reporte que se desea crear en la base de datos
     * @return booleano indicando éxito o fracaso
     */
    @Override
    public boolean Create( Reporte reporte ) {
        boolean wasCreated = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            documentos.Create( new Documento( reporte.getIdDocumento(), reporte.getTitulo(), reporte.GetDescripcion(),
                               reporte.getFechaEntrega(), reporte.GetClaveExpediente(), reporte.getComentarios(),
                               reporte.getCalificacion() ) );
            Documento documento = documentos.Read( reporte.getTitulo(), reporte.GetClaveExpediente() );
            String query = "INSERT INTO Reporte( HorasReportadas, Tipo, ClaveExpediente, IDDocumento ) VALUES( ?, ?, ?, ? );";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setInt( 1, reporte.GetHorasReportadas() );
            statement.setInt( 2, reporte.GetTipoReporte().ordinal() );
            statement.setInt( 3, reporte.GetClaveExpediente() );
            statement.setInt( 4, documento.getIdDocumento() );
            statement.executeUpdate();

            wasCreated = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }
        return wasCreated;
    }

    /**
     * Regresa una lista con todos los reportes en la base de datos
     * @return una lista de Reportes
     */
    @Override
    public List< Reporte > ReadAll() {
        List< Reporte > reportes = new ArrayList<>();
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            Statement statement = connection.GetConnection().createStatement();
            ResultSet result = statement.executeQuery( "SELECT * FROM Reporte;" );

            while( result.next() )
            {
                Documento documento = documentos.Read( result.getInt( 5 ) );
                reportes.add( new Reporte( documento, TipoReporte.values()[ result.getInt( 3 ) ],
                        result.getInt( 2 ), result.getInt( 1 ) ) );
            }

            result.close();
            statement.close();
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return reportes;
    }

    /**
     * Regresa una instancia de Reporte de la base de datos
     * @param idReporte el ID del Reporte deseado
     * @return una instancia del reporte
     */
    @Override
    public Reporte Read( int idReporte ) {
        Reporte reporte = null;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "SELECT * FROM Reporte WHERE IDReporte = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setInt( 1,  idReporte );
            statement.executeQuery();
            ResultSet result = statement.getResultSet();

            if( result.next() ) {
                int idDocumento = result.getInt( 5 );
                int horasReportadas = result.getInt( 2 );
                TipoReporte tipo = TipoReporte.values()[ result.getInt( 3 ) ];

                Documento documento = documentos.Read( idDocumento );
                reporte = new Reporte( documento, tipo, horasReportadas, idReporte );
            }
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return reporte;
    }

    /**
     * Actualiza la información de un reporte en la base de datos
     * @param reporte el reporte con su información actualizada
     * @return booleano indicando éxito o fracaso
     */
    @Override
    public boolean Update( Reporte reporte ) {
        boolean updated = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "UPDATE Reporte SET HorasReportadas = ?, Tipo = ? WHERE IDReporte = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setInt( 1, reporte.GetHorasReportadas() );
            statement.setInt( 2, reporte.GetTipoReporte().ordinal() );
            statement.setInt( 3, reporte.GetIdReporte() );
            statement.executeUpdate();

            documentos.Update( new Documento( reporte.getIdDocumento(), reporte.getTitulo(), reporte.GetDescripcion(),
                               reporte.getFechaEntrega(), reporte.GetClaveExpediente(), reporte.getComentarios(),
                               reporte.getCalificacion() ) );

            updated = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return updated;
    }

    /**
     * Elimina un reporte de la base de datos utilizando el ID
     * introducido
     * @param idReporte el ID del reporte que s desea eliminar
     * @return booleano indicando éxito o fracaso
     */
    @Override
    public boolean Delete( int idReporte ) {
        boolean deleted = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            Reporte reporte = Read( idReporte );
            String query = "DELETE FROM Reporte WHERE IDReporte = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setInt( 1,  idReporte );
            statement.executeUpdate();
            documentos.Delete( reporte.getIdDocumento() );

            deleted = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return deleted;
    }
}