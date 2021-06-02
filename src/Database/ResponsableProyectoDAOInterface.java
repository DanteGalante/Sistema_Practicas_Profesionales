/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 26 - mar - 2021
 * Descripción:
 * Interfaz con los métodos necesarios para implementar
 * el patrón de Data Access Object para la entidad ResponsableProyecto.
 */
package Database;

import Entities.ResponsableProyecto;
import java.util.List;

/**
 * Interfaz con los métodos necesarios para implementar
 * el patrón de Data Access Object para la entidad ResponsableProyecto.
 */
public interface ResponsableProyectoDAOInterface {

    /**
     * Crea una instancia de ResponsableProyecto en la base de datos
     * @param responsable la instancia de ResponsableProyecto que se desea crear
     * @return booleano indicando éxito o fracaso
     */
    boolean Create( ResponsableProyecto responsable );

    /**
     * Regresa una lista de Responsables de Proyecto.
     * @return una lista de ResponsableProyecto
     */
    List< ResponsableProyecto > ReadAll();

    /**
     * Regresa una instancia de ResponsableProyecto ubicada en la base de datos
     * @param idResponsable el ID del responsable deseado
     * @return na instancia de ResponsableProyecto
     */
    ResponsableProyecto Read( int idResponsable );

    /**
     * Actualiza la información de un ResponsableProyecto en la base de datos.
     * @param responsable el responsable con su información actualizada
     * @return booleano indicando éxito o fracaso
     */
    boolean Update( ResponsableProyecto responsable );

    /**
     * Elimina una instancia de ResponsableProyecto de la base de datos.
     * Utiliza el id del responsable
     * @param idResponsable el ID del ResponsableProyecto que se desea eliminar
     * @return booleano indicando éxito o fracaso
     */
    boolean Delete( int idResponsable, List< Integer > idProyectos );
}
