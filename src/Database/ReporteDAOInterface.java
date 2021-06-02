/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 27 - mar - 2021
 * Descripción:
 * Interfaz con los métodos necesarios para implementar
 * el patrón de Data Access Object para la entidad Reporte.
 */
package Database;

import Entities.Reporte;
import java.util.List;

/**
 * Interfaz con los métodos necesarios para implementar
 * el patrón de Data Access Object para la entidad Reporte.
 */
public interface ReporteDAOInterface {
    /**
     * Crea una instancia de Reporte en la base de datos
     * @param reporte la Reporte que se desea crear en la base de datos
     * @return booleano indicando éxito o fracaso
     */
    boolean Create( Reporte reporte );

    /**
     * Regresa una lista con todos los reportes en la base de datos
     * @return una lista de Reportes
     */
    List< Reporte > ReadAll();

    /**
     * Regresa una instancia de Reporte de la base de datos
     * @param idReporte el ID del Reporte deseado
     * @return una instancia del reporte
     */
    Reporte Read( int idReporte);

    /**
     * Actualiza la información de un reporte en la base de datos
     * @param reporte el reporte con su información actualizada
     * @return booleano indicando éxito o fracaso
     */
    boolean Update( Reporte reporte );

    /**
     * Elimina un reporte de la base de datos utilizando el ID
     * introducido
     * @param idReporte el ID del reporte que s desea eliminar
     * @return booleano indicando éxito o fracaso
     */
    boolean Delete( int idReporte );
}
