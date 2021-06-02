/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 3 - mar - 2021
 * Descripción:
 * Data Access Object para la entidad Proyecto. Se
 * encarga de realizar varias funciones relacionadas con Proyecto
 * en la base de datos.
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
 * Data Access Object para la entidad Proyecto. Se
 * encarga de realizar varias funciones relacionadas con Proyecto
 * en la base de datos.
 */
public class ProyectoDAO implements ProyectoDAOInterface{
    /**
     * Crea una instancia de Proyecto en la base de datos
     * @param proyecto el proyecto que se desea crear
     * @return booleano indicando éxito o fracaso
     */
    @Override
    public boolean Create( Proyecto proyecto ) {
        boolean wasCreated = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "INSERT INTO Proyecto( Nombre, Descripcion, NumEstudiantesRequeridos, NumEstudiantesAsignados, FechaRegistro, " +
                           "Estado ) VALUES ( ?, ?, ?, ?, ?, ? );";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setString( 1, proyecto.getNombre() );
            statement.setString( 2, proyecto.GetDescripcion() );
            statement.setInt( 3, proyecto.getNumEstudiantesRequeridos() );
            statement.setInt( 4, proyecto.GetEstudiantesAsignados() );
            statement.setString( 5, proyecto.GetFechaRegistro() );
            statement.setInt( 6, proyecto.GetEstado().ordinal() );
            statement.executeUpdate();
            wasCreated = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StartConnection();
        return wasCreated;
    }

    /**
     * Regresa una lista con todos los proyectos de la base de datos
     * @return una lista de proyectos
     */
    @Override
    public List< Proyecto > ReadAll() {
        List< Proyecto > proyectos = new ArrayList<>();
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            Statement statement = connection.GetConnection().createStatement();
            ResultSet result = statement.executeQuery( "SELECT * FROM Proyecto;" );

            while( result.next() ) {
                proyectos.add( new Proyecto( result.getInt( 1 ), result.getString( 2 ),
                        result.getString( 3 ), result.getInt( 4 ), result.getInt( 5 ),
                        result.getString( 6 ), EstadoProyecto.values()[ result.getInt( 7 ) ] ) );
            }

            result.close();
            statement.close();
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return proyectos;
    }

    /**
     * Regresa una instancia de Proyecto ubicado en la base de datos
     * @param idProyecto el ID del proyecto que se desea
     * @return una instancia del proyecto
     */
    @Override
    public Proyecto Read( int idProyecto ) {
        Proyecto proyecto = null;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "SELECT * FROM Proyecto WHERE IDProyecto = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setInt( 1, idProyecto );
            statement.executeQuery();
            ResultSet result = statement.getResultSet();

            if( result.next() ) {
                proyecto = new Proyecto( result.getInt( 1 ), result.getString( 2 ), result.getString( 3 ),
                        result.getInt( 4 ), result.getInt( 5 ), result.getString( 6 ),
                        EstadoProyecto.values()[ result.getInt( 7 ) ] );
            }

            result.close();
            statement.close();
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return proyecto;
    }

    /**
     * Actualiza la información de un proyecto en la base de datos.
     * @param proyecto el proyecto con su información actualizada
     * @return booleano indicando éxito o fracaso
     */
    @Override
    public boolean Update( Proyecto proyecto ) {
        boolean updated = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "UPDATE Proyecto SET Nombre = ?, Descripcion = ?, NumEstudiantesRequeridos = ?, NumEstudiantesAsignados = ?," +
                    " FechaRegistro = ?, Estado = ? WHERE IDProyecto = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setString( 1, proyecto.getNombre() );
            statement.setString( 2, proyecto.GetDescripcion() );
            statement.setInt( 3, proyecto.getNumEstudiantesRequeridos() );
            statement.setInt( 4, proyecto.GetEstudiantesAsignados() );
            statement.setString( 5, proyecto.GetFechaRegistro() );
            statement.setInt( 6, proyecto.GetEstado().ordinal() );
            statement.setInt( 7, proyecto.getIdProyecto() );
            statement.executeUpdate();
            updated = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return updated;
    }

    /**
     * Elimina un proyecto de la base de datos utilizando el ID
     * del proyecto
     * @param idProyecto el ID del proyecto que se desea eliminar
     * @return booleano indicando éxito o fracaso
     */
    @Override
    public boolean Delete( int idProyecto ) {
        boolean deleted = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "DELETE FROM Proyecto WHERE IDProyecto = ?;";
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
}
