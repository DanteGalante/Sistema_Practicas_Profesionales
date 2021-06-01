/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 26 - mar - 2021
 * Descripción:
 * Interfaz con los métodos necesarios para implementar
 * el patrón de Data Access Object para la entidad Proyecto.
 */
package Database;

import Entities.Proyecto;

import java.util.List;

public interface ProyectoDAOInterface {
    boolean Create( Proyecto proyecto );
    List< Proyecto > ReadAll();
    Proyecto Read( int idProyecto );
    boolean Update( Proyecto proyecto );
    boolean Delete( int idProyecto );
}
