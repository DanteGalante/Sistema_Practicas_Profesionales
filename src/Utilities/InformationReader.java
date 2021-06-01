/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 4 - mar - 2021
 * Descripción:
 * Clase encargada de leer un archivo externo para
 * conseguir información sensible para poder acceder
 * a la base de datos de MySQL
 */
package Utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * conseguir información sensible para poder acceder
 * a la base de datos de MySQL
 */
public class InformationReader {
    private String fileName;
    private List< String > information;

    /**
     * Crea una instancia de la clase y lee el archivo especificado
     * para conseguir la información necesaria para crear una conexión
     * a la base de datos
     */
    public InformationReader() {
        fileName = "Information.txt";
        information = new ArrayList< String >();

        try{
            File targetFile = new File( fileName );
            Scanner fileScanner = new Scanner( targetFile );
            while( fileScanner.hasNextLine() ) {
                information.add( fileScanner.nextLine() );
            }
        } catch( FileNotFoundException fileNotFound ) {
            System.out.println( "El archivo especificado no se encuentra" );
            fileNotFound.printStackTrace();
        }
    }

    /**
     * Regresa el driver necesario para acceder a la base de datos
     * @return el driver de la base de datos
     */
    public String GetDriver() {
        return information.get( 0 );
    }

    /**
     * Regresa el nombre de la base de datos que se utiliza en
     * el sistema
     * @return el nombre de la base de datos
     */
    public String GetDatabase() {
        return information.get( 1 );
    }

    /**
     * Regresa el hostname de la base de datos
     * @return el hostname de la base de datos
     */
    public String GetHostname() {
        return information.get( 2 );
    }

    /**
     * Regresa el puerto en donde se puede acceder a la base de datos
     * @return el puerto de la base de datos
     */
    public String GetPort() {
        return information.get( 3 );
    }

    /**
     * Regresa el usuario de la base de datos
     * @return el usuario de la base de datos
     */
    public String GetUsername() {
        return information.get( 4 );
    }

    /**
     * Regresa la contraseña de la base de datos
     * @return la contraseña de la base de datos
     */
    public String GetPassword() {
        return information.get( 5 );
    }

    public String GetUrl() {
        return information.get( 6 ) + GetHostname() + ":" + GetPort() + "/" + GetDatabase();
    }
}
