/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 6 - abr - 2021
 * Descripción:
 * Clase encargada de crear archivos temporales para
 * poder leer archivos de la base de datos
 */
package Utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

/**
 * Clase encargada de crear archivos temporales para
 * poder leer archivos de la base de datos
 */
public class FileCreator {

    /**
     * Crea un archivo temporal utilizando el nombre del archivo y
     * su contenido de la base de datos
     * @param filename el nombre del archivo
     * @param fileContent el contenido del archivo
     * @return instancia de un archivo de la base de datos
     */
    public File CreateFile( String filename, Blob fileContent ) {
        File temp = null;
        // new File( "../Database/" + fileName );
        try {
            temp = File.createTempFile( GetNamePrefix( filename ), GetNameSuffix( filename ) );
            FileOutputStream outputStream = new FileOutputStream( temp );
            InputStream inputStream = fileContent.getBinaryStream();
            byte[] buffer = fileContent.getBytes( 1, ( int )fileContent.length() );

            while( inputStream.read( buffer ) != -1 ) {
                outputStream.write( buffer );
            }

            inputStream.close();
            outputStream.close();
        } catch( SQLException | IOException exception ) {
            exception.printStackTrace();
        }
        return temp;
    }

    /**
     * Regresa una cadena con el contenido del nombre antes del
     * caracter "."
     * @param filename el nombre del archivo
     * @return una cadena con un substring
     */
    private String GetNamePrefix( String filename ) {
        return filename.substring( 0, GetPeriodIndex( filename ) - 1 );
    }

    /**
     * Regresa una cadena con el contenido del nombre a partir e incluyendo el
     * caracter "."
     * @param filename el nombre del archivo
     * @return una cadena con un substring
     */
    private String GetNameSuffix( String filename ) {
        return filename.substring( GetPeriodIndex( filename ) );
    }

    /**
     * Regresa un int con el índice donde se ubica el
     * caracter "." en el nombre del archivo
     * @param filename el nombre del archivo
     * @return índice del caracter "."
     */
    private int GetPeriodIndex( String filename ) {
        int periodIndex = 0;
        char[] input = filename.toCharArray();
        for( char currentChar : input ) {
            if( currentChar == '.' ) {
                break;
            }
            periodIndex++;
        }
        return periodIndex;
    }
}
