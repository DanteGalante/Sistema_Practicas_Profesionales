/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 3 - mar - 2021
 * Descripción:
 * Data Access Object para la entidad ResponsableProyecto. Se
 * encarga de realizar varias funciones relacionadas con ResponsableProyecto
 * en la base de datos.
 */
package Database;

import Entities.ResponsableProyecto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para la entidad ResponsableProyecto. Se
 * encarga de realizar varias funciones relacionadas con ResponsableProyecto
 * en la base de datos.
 */
public class ResponsableProyectoDAO implements ResponsableProyectoDAOInterface{
    private ProyectosDeResponsablesDAO proyectos = new ProyectosDeResponsablesDAO();

    /**
     * Crea una instancia de ResponsableProyecto en la base de datos
     * @param responsable la instancia de ResponsableProyecto que se desea crear
     * @return booleano indicando éxito o fracaso
     */
    @Override
    public boolean Create( ResponsableProyecto responsable ) {
        boolean wasCreated = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "INSERT INTO ResponsableProyecto( Nombres, Apellidos, CorreoElectronico, Telefono ) " +
                           "VALUES ( ?, ?, ?, ? );";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setString( 1, responsable.GetNombres() );
            statement.setString( 2, responsable.GetApellidos() );
            statement.setString( 3, responsable.GetCorreo() );
            statement.setString( 4, responsable.GetTelefono() );
            statement.executeUpdate();

            proyectos.Create( responsable.getIdResponsableProyecto(), responsable.getIdProyectos() );

            wasCreated = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StartConnection();
        return wasCreated;
    }

    /**
     * Regresa una lista de Responsables de Proyecto.
     * @return una lista de ResponsableProyecto
     */
    @Override
    public List< ResponsableProyecto > ReadAll() {
        List< ResponsableProyecto > responsables = new ArrayList<>();
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            Statement statement = connection.GetConnection().createStatement();
            ResultSet result = statement.executeQuery( "SELECT * FROM ResponsableProyecto;" );

            while( result.next() ) {
                int idResponsable = result.getInt( 1 );
                responsables.add( new ResponsableProyecto( idResponsable, result.getString( 2 ),
                        result.getString( 3 ), result.getString( 4 ), result.getString( 5 ),
                        proyectos.ReadProyectos( idResponsable ) ) );
            }

            result.close();
            statement.close();
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return responsables;
    }

    /**
     * Regresa una instancia de ResponsableProyecto ubicada en la base de datos
     * @param idResponsable el ID del responsable deseado
     * @return na instancia de ResponsableProyecto
     */
    @Override
    public ResponsableProyecto Read( int idResponsable ) {
        ResponsableProyecto responsable = null;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "SELECT * FROM ResponsableProyecto WHERE IDResponsableProyecto = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setInt( 1, idResponsable );
            statement.executeQuery();
            ResultSet result = statement.getResultSet();

            if( result.next() ) {
                responsable = new ResponsableProyecto( idResponsable, result.getString( 2 ),
                        result.getString( 3 ), result.getString( 4 ), result.getString( 5 ),
                        proyectos.ReadProyectos( idResponsable ) );
            }

            result.close();
            statement.close();
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return responsable;
    }

    /**
     * Actualiza la información de un ResponsableProyecto en la base de datos.
     * @param responsable el responsable con su información actualizada
     * @return booleano indicando éxito o fracaso
     */
    @Override
    public boolean Update( ResponsableProyecto responsable ) {
        boolean updated = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "UPDATE ResponsableProyecto SET Nombres = ?, Apellidos = ?, CorreoElectronico = ?, Telefono = ?" +
                           " WHERE IDResponsableProyecto = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setString( 1, responsable.GetNombres() );
            statement.setString( 2, responsable.GetApellidos() );
            statement.setString( 3, responsable.GetCorreo() );
            statement.setString( 4, responsable.GetTelefono() );
            statement.setString( 5, responsable.GetCorreo() );

            proyectos.Update( responsable.getIdResponsableProyecto(), responsable.getIdProyectos() );

            statement.executeUpdate();
            updated = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return updated;
    }

    /**
     * Elimina una instancia de ResponsableProyecto de la base de datos.
     * Utiliza el id del responsable
     * @param idResponsable el ID del ResponsableProyecto que se desea eliminar
     * @return booleano indicando éxito o fracaso
     */
    @Override
    public boolean Delete( int idResponsable, List< Integer > idProyectos ) {
        boolean deleted = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "DELETE FROM ResponsableProyecto WHERE IDResponsableProyecto = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setInt( 1, idResponsable );
            statement.executeUpdate();

            for( int proyecto : idProyectos ) {
                proyectos.Delete( proyecto );
            }

            deleted = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return deleted;
    }
}