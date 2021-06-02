package Database;

import Entities.RegistroGrupo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RegistroGrupoDAO implements RegistroGrupoDAOInterface {

    /**
     * Almacena una instancia de RegistroGrupo a la base de datos
     * @param registro el registroGrupo que se desea crear
     * @return true si la operación fue exitosa, false si no
     */
    @Override
    public boolean Create( RegistroGrupo registro ) {
        boolean wasCreated = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "INSERT INTO RegistroGrupo( NRC, Identificador ) " +
                    "VALUES ( ?, ? );";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setString( 1, registro.getNRC() );
            statement.setString( 2, registro.getIdentificador() );
            statement.executeUpdate();
            wasCreated = true;
        } catch ( Exception exception ) {
            exception.printStackTrace();
        }
        connection.StartConnection();
        return wasCreated;
    }

    /**
     * Regresa una lista con todos los RegistrosGrupos almacenados
     * en la base de datos
     * @return una lista de RegistroGrupo
     */
    @Override
    public List< RegistroGrupo > ReadAll() {
        List< RegistroGrupo > registros = new ArrayList<>();
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "SELECT * FROM RegistroGrupo;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.executeQuery();
            ResultSet result = statement.getResultSet();

            while( result.next() ) {
                registros.add( new RegistroGrupo( result.getString( 2 ), result.getString( 3 ) ) );
            }

            result.close();
            statement.close();
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return registros;
    }

    /**
     * Actualiza un RegistroGrupo almacenado en la base de datos
     * @param registro el registrGrupo que se desea actualizar
     * @return true si la operación fue exitosa, false si no
     */
    @Override
    public boolean Update( RegistroGrupo registro ) {
        boolean updated = Delete( registro.getIdentificador() );
        updated = Create( registro );
        return updated;
    }

    /**
     * Elimina un RegistroGrupo de la base de datos
     * @param Identificador el identificador del registroGrupo que se desea eliminar
     * @return true si la operación fue exitosa, false si no
     */
    @Override
    public boolean Delete( String Identificador ) {
        boolean deleted = false;
        MySqlConnection connection = new MySqlConnection();
        connection.StartConnection();

        try {
            String query = "DELETE FROM RegistroGrupo WHERE Identificador = ?;";
            PreparedStatement statement = connection.GetConnection().prepareStatement( query );
            statement.setString( 1, Identificador );
            statement.executeUpdate();
            deleted = true;
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

        connection.StopConnection();
        return deleted;
    }
}
