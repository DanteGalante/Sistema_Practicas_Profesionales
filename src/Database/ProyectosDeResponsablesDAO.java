/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 21 - abr - 2021
 * Descripción:
 * Data Access Object para los proyectos que estan relacionados con
 * un responsable de proyecto.
 */
package Database;

import Entities.Proyecto;
import Enumerations.EstadoProyecto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para los proyectos que estan relacionados con
 * un responsable de proyecto.
 */
public class ProyectosDeResponsablesDAO implements ProyectosDeResponsablesDAOInterface{

    /**
     * Almacena una lista de IDs de proyectos relacionados con un responsable de proyecto
     * en la base de datos.
     * @param idResponsable el responsable encargado de los proyectos
     * @param idProyectos la lista de proyectos del responsable
     * @return booleano indicando exito o fracaso
     */
    @Override
    public boolean Create( int idResponsable, List< Integer > idProyectos ) {
        boolean wasCreated = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        for( int i = 0; i < idProyectos.size(); i++ ) {
            try {
                String query = "INSERT INTO ProyectosDeResponsables( IDResponsableProyecto, IDProyecto ) " +
                        "VALUES ( ?, ? );";
                PreparedStatement statement = connection.GetConnection().prepareStatement( query );
                statement.setInt( 1, idResponsable );
                statement.setInt( 2, idProyectos.get( i ) );
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
     * Regresa una lista de IDs de todos los proyectos
     * relacionados con el responsable de proyecto
     * @param idResponsable el ID del responsable
     * @return lista de IDs de poyectos
     */
    @Override
    public List< Integer > ReadProyectos( int idResponsable ) {
        List< Integer > idProyectos = new ArrayList<>();
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "SELECT * FROM ProyectosDeResponsables WHERE IDResponsableProyecto = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setInt( 1, idResponsable );
            statement.executeQuery();
            ResultSet result = statement.getResultSet();

            while( result.next() ) {
                idProyectos.add( result.getInt( 2 ) );
            }

            result.close();
            statement.close();
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return idProyectos;
    }

    @Override
    public int ReadResponsable( int idProyecto ) {
        int idResponsable = -1;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "SELECT * FROM ProyectosDeResponsables WHERE IDProyecto = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setInt( 1, idProyecto );
            statement.executeQuery();
            ResultSet result = statement.getResultSet();

            while( result.next() ) {
                idResponsable = result.getInt( 1 );
            }

            result.close();
            statement.close();
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return idResponsable;
    }

    /**
     * Elimina un proyecto relacionado a un responsable de proyecto
     * @param idProyecto el proyecto que se desea eliminar de la lista
     * @return booleano indicando exito o fracaso
     */
    @Override
    public boolean Delete( int idProyecto ) {
        boolean deleted = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "DELETE FROM ProyectosDeResponsables WHERE IDProyecto = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setInt( 1, idProyecto );
            statement.executeUpdate();
            deleted = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return deleted;
    }

    /**
     * Actualiza la lista de proyectos relacionados a un responsable de proyecto
     * en la base de datos.
     * @param idResponsable el ID del responsable que se desea acualizar
     * @param idProyectos la lista de IDs de proyectos actualizados
     * @return booleano indicando exito o fracaso
     */
    @Override
    public boolean Update( int idResponsable, List< Integer > idProyectos ) {
        boolean wasCreated = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();
        List< Integer > proyectosActuales = ReadProyectos( idResponsable );

        for( int i = 0; i < idProyectos.size(); i++ ) {
            if( !proyectosActuales.contains( idProyectos.get( i ) ) ) {
                try {
                    String query = "INSERT INTO ResponsablesOrganizacion( IDOrganizacion, IDResponsableProyecto ) " +
                            "VALUES ( ?, ? );";
                    PreparedStatement statement = connection.GetConnection().prepareStatement( query );
                    statement.setInt( 1, idResponsable );
                    statement.setInt( 2, idProyectos.get( i ) );
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
