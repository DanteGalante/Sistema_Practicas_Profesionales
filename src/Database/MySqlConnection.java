/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 4 - mar - 2021
 * Descripción:
 * Clase encargada de manejar la conexión con la
 * base de datos de mysql.
 */
package Database;

import java.sql.*;
import Utilities.InformationReader;

/**
 * Clase encargada de manejar la conexión con la
 * base de datos de mysql.
 */
public class MySqlConnection {
    private Connection sqlConnection = null;
    private InformationReader information;
    private boolean connectionEstablished;

    /**
     * Inicia una conexión con la base de datos
     * @return booleano indicando su éxito
     */
    public boolean StartConnection() {
        connectionEstablished = false;
        information = new InformationReader();
        CreateSqlConnection(  );
        return connectionEstablished;
    }

    /**
     * Crea una conexión sql a la base de datos especificada utilizando
     * la informacion proporcionada por la clase InformationReader
     */
    public void CreateSqlConnection() {
        try {
            sqlConnection = DriverManager.getConnection( information.GetUrl(), information.GetUsername(),
                                                         information.GetPassword() );
            connectionEstablished = true;
        } catch( SQLException sqlException ) {
            System.out.println( "Hubo un error en la conexión a la base de datos" );
            sqlException.printStackTrace();
        }
    }

    /**
     * Detiene la conexión a la base de datos que fue creada.
     * @return booleano indicando su éxito
     */
    public boolean StopConnection() {
        boolean connectionStopped = false;
        try {
            sqlConnection.close();
            connectionStopped = true;
        } catch( SQLException sqlException ) {
            System.out.println( "Hubo un error en la conexión a la base de datos" );
            sqlException.printStackTrace();
        }
        return  connectionStopped;
    }

    /**
     * Regresa la conexión a la base de datos
     * @return la conexión sql
     */
    public Connection GetConnection() {
        return sqlConnection;
    }
}