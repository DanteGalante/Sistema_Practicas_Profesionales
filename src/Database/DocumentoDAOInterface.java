/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 28 - mar - 2021
 * Descripción:
 * Interfaz con los métodos necesarios para implementar
 * el patrón de Data Access Object para la entidad Documento.
 */
package Database;

import Entities.Documento;
import java.util.List;

public interface DocumentoDAOInterface {
    boolean Create( Documento documento );
    List< Documento > ReadAll();
    Documento Read( int idDocumento );
    Documento Read( String titulo, int claveExpediente );
    boolean Update( Documento documento );
    boolean Delete( int idDocumento );
}
