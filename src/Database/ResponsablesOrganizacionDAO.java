/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 21 - abr - 2021
 * Descripción:
 * Data Access Object de responsalbesOrganizacion. Crea, Recupera y elimina una
 * lista de ids de los responsables relacionados con una organizacion
 * vinculada.
 */
package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object de responsalbesOrganizacion. Crea, Recupera y elimina una
 * lista de ids de los responsables relacionados con una organizacion
 * vinculada.
 */
public class ResponsablesOrganizacionDAO implements ResponsablesOrganizacionDAOInterface {

    /**Almacena una lista de IDs de todos los responsables de proyecto relacionados con una
     * organizacion vinculada en la base de datos.
     * @param idOrganizacion El ID de la organizacion vinculada
     * @param idResponsablesProyecto la lista de IDs de responsables de proyectos
     * @return booleano indicando éxito o fracaso
     */
    @Override
    public boolean Create( int idOrganizacion, List< Integer > idResponsablesProyecto ) {
        boolean wasCreated = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        for( int i = 0; i < idResponsablesProyecto.size(); i++ ) {
            try {
                String query = "INSERT INTO ResponsablesOrganizacion( IDOrganizacion, IDResponsableProyecto ) " +
                        "VALUES ( ?, ? );";
                PreparedStatement statement = connection.GetConnection().prepareStatement( query );
                statement.setInt( 1, idOrganizacion );
                statement.setInt( 2, idResponsablesProyecto.get( i ) );
                statement.executeUpdate();
                wasCreated = true;
            } catch ( Exception exception ) {
                exception.printStackTrace();
            }
        }
        connection.StartConnection();
        return wasCreated;
    }

    /**
     * Regresa una lista con todos os IDs de los responsables proyectos
     * relacionados con una organización vinculada
     * @param idOrganizacion
     * @return
     */
    @Override
    public List< Integer > ReadResponsables( int idOrganizacion ) {
        List< Integer > idResponsables = new ArrayList<>();
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "SELECT * FROM ResponsablesOrganizacion WHERE IDOrganizacion = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setInt( 1, idOrganizacion );
            statement.executeQuery();
            ResultSet result = statement.getResultSet();

            while( result.next() ) {
                idResponsables.add( result.getInt( 2 ) );
            }

            result.close();
            statement.close();
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return idResponsables;
    }

    @Override
    public int ReadOrganizacion( int idResponsable ) {
        int idOrganizacion = -1;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "SELECT * FROM ResponsablesOrganizacion WHERE IDResponsableProyecto = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setInt( 1, idResponsable );
            statement.executeQuery();
            ResultSet result = statement.getResultSet();

            while( result.next() ) {
                idOrganizacion = result.getInt( 1 );
            }

            result.close();
            statement.close();
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return idOrganizacion;
    }

    /**
     * Elimina un ID de responsable proyecto relacionado con la organizacion vinculada
     * @param idResponsableProyecto El id del responsable que se desea eliminar
     * @return booleano indicando éxito o fracaso
     */
    @Override
    public boolean Delete( int idResponsableProyecto ) {
        boolean deleted = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "DELETE FROM ResponsablesOrganizacion WHERE IDResponsableProyecto = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setInt( 1, idResponsableProyecto );
            statement.executeUpdate();
            deleted = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return deleted;
    }

    /**
     * Actualiza la lista de IDs de los responsables de proyectos relacionados a
     * una organización vinculada
     * @param idOrganizacion El ID de la organización vinculada
     * @param idResponsablesProyecto la lista actualizada de responsables de proyecto
     * @return booleano indicando éxito o fracaso
     */
    @Override
    public boolean Update( int idOrganizacion, List< Integer > idResponsablesProyecto ) {
        boolean wasCreated = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();
        List< Integer > responsablesActuales = ReadResponsables( idOrganizacion );

        for( int i = 0; i < idResponsablesProyecto.size(); i++ ) {
            if( !responsablesActuales.contains( idResponsablesProyecto.get( i ) ) ) {
                try {
                    String query = "INSERT INTO ResponsablesOrganizacion( IDOrganizacion, IDResponsableProyecto ) " +
                            "VALUES ( ?, ? );";
                    PreparedStatement statement = connection.GetConnection().prepareStatement( query );
                    statement.setInt( 1, idOrganizacion );
                    statement.setInt( 2, idResponsablesProyecto.get( i ) );
                    statement.executeUpdate();
                    wasCreated = true;
                } catch ( Exception exception ) {
                    exception.printStackTrace();
                }
            }
        }
        connection.StartConnection();
        return wasCreated;
    }
}
