/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 7 - abr - 2021
 * Descripción:
 * Data Access Object para la entidad OrganizacionVinculada. Se
 * encarga de realizar varias funciones relacionadas con OrganizacionVinculada
 * en la base de datos.
 */
package Database;

import Entities.OrganizacionVinculada;
import Enumerations.TipoSector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para la entidad OrganizacionVinculada. Se
 * encarga de realizar varias funciones relacionadas con OrganizacionVinculada
 * en la base de datos.
 */
public class OrganizacionVinculadaDAO implements OrganizacionVinculadaDAOInterface{
    private ResponsablesOrganizacionDAO responsables = new ResponsablesOrganizacionDAO();

    /**
     * Crea una nueva organizacion vinculada en la base de datos
     * @param organizacion la instancia de organizacion vinculada que se desea crear
     * @return booleano indicando éxito o fracaso
     */
    @Override
    public boolean Create( OrganizacionVinculada organizacion ) {
        boolean wasCreated = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "INSERT INTO OrganizacionVinculada( IDOrganizacion, Nombre, Direccion, " +
                    "Sector, Telefono, CorreoElectronico ) VALUES ( ?, ?, ?, ?, ?, ?, ? );";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setInt( 1, organizacion.getIdOrganizacion() );
            statement.setString( 3, organizacion.getNombre() );
            statement.setString( 4, organizacion.getDireccion() );
            statement.setInt( 5, organizacion.getSector().ordinal() );
            statement.setString( 6, organizacion.getTelefono() );
            statement.setString( 7, organizacion.getCorreo() );
            statement.executeUpdate();

            responsables.Create( organizacion.getIdOrganizacion(), organizacion.getResponsables() );

            wasCreated = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StartConnection();
        return wasCreated;
    }

    /**
     * Regresa una lista con todas las organizaciones vinculadas de la base de datos
     * @return una lista de OrganizacionVinculada
     */
    @Override
    public List< OrganizacionVinculada > ReadAll() {
        List< OrganizacionVinculada > organizaciones = new ArrayList<>();
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            Statement statement = connection.GetConnection().createStatement();
            ResultSet result = statement.executeQuery( "SELECT * FROM OrganizacionVinculada;" );

            while( result.next() )
            {
                int idOrganizacion = result.getInt( 1 );
                organizaciones.add( new OrganizacionVinculada( result.getString( 2 ),
                        result.getString( 3 ), TipoSector.values()[ result.getInt( 4 ) ],
                        result.getString( 5 ), result.getString( 6 ),
                        idOrganizacion, responsables.ReadResponsables( idOrganizacion ), result.getBoolean( 7 ) ) );
            }

            result.close();
            statement.close();
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return organizaciones;
    }

    /**
     * Regresa una instancia de OrganizacionVinculada
     * @param idOrganizacion el ID del proyecto asociado con la organizacion vinculada
     * @return una instancia de OrganizacionVinculada, null en caso de no encontrarla
     */
    @Override
    public OrganizacionVinculada Read( int idOrganizacion ) {
        OrganizacionVinculada organizacion = null;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "SELECT * FROM OrganizacionVinculada WHERE IDOrganizacion = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setInt( 1,  idOrganizacion );
            statement.executeQuery();
            ResultSet result = statement.getResultSet();

            if( result.next() ) {
                organizacion = new OrganizacionVinculada( result.getString( 2 ),
                        result.getString( 3 ), TipoSector.values()[ result.getInt( 4 ) ],
                        result.getString( 5 ), result.getString( 6 ),
                        idOrganizacion, responsables.ReadResponsables( idOrganizacion ), result.getBoolean( 7 ) );
            }
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return organizacion;
    }

    /**
     * Actualiza la información de una organizacion vinculada de la base de datos
     * @param organizacion la instancia de OrganizacionVinculada con su información actualizada
     * @return booleano indicando éxito o fracaso
     */
    @Override
    public boolean Update( OrganizacionVinculada organizacion ) {
        boolean updated = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "UPDATE OrganizacionVinculada SET Nombre = ?, Direccion = ?, " +
                    "Sector = ?, Telefono = ?, CorreoElectronico = ? WHERE IDOrganizacion = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setString( 1, organizacion.getNombre() );
            statement.setString( 2, organizacion.getDireccion() );
            statement.setInt( 3, organizacion.getSector().ordinal() );
            statement.setString(  4, organizacion.getTelefono() );
            statement.setString( 5, organizacion.getCorreo() );
            statement.setInt( 6, organizacion.getIdOrganizacion() );
            statement.executeUpdate();

            responsables.Update( organizacion.getIdOrganizacion(), organizacion.getResponsables() );

            updated = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return updated;
    }

    /**
     * Elimina una organizacion vinculada de la base de datos
     * @param idOrganizacion el ID del proyecto asociado a la organizacion
     * @param responsablesOrganizacion los responsables relacionados a esta organizacion vinculada
     * @return booleano indicando exito o fracaso
     */
    @Override
    public boolean Delete( int idOrganizacion, List< Integer > responsablesOrganizacion ) {
        boolean deleted = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "DELETE FROM OrganizacionVinculada WHERE IDOrganizacion = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setInt( 1,  idOrganizacion );
            statement.executeUpdate();

            for( int responsable : responsablesOrganizacion ) {
                responsables.Delete( responsable );
            }

            deleted = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return deleted;
    }
}