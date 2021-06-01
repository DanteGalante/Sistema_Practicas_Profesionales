/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 3 - mar - 2021
 * Descripción:
 * Clase que contiene la información del responsable del proyecto
 */
package Entities;

import java.util.List;

/**
 * Clase que contiene la información del responsable del proyecto
 */
public class ResponsableProyecto {
    private int idResponsableProyecto;
    private String nombres;
    private String apellidos;
    private String correoElectronico;
    private String telefono;
    private List< Integer > idProyectos;

    /**
     * Constructor sin parametros de ResponsableProyecto. Crea una instancia
     * con un ID = 0 y cadenas vacías.
     */
    public ResponsableProyecto() {
        idResponsableProyecto = 0;
        nombres = "";
        apellidos = "";
        correoElectronico = "";
        telefono = "";
        idProyectos = null;
    }

    /**
     * Constructor de la clase ResponsableProyecto. Crea una nueva
     * instancia de ResponsableProyecto a partir de una instancia existente.
     * @param original la instancia existente de ResponsableProyecto
     */
    public ResponsableProyecto( ResponsableProyecto original ) {
        this( original.idResponsableProyecto, original.nombres, original.apellidos, original.correoElectronico,
                original.telefono, original.idProyectos );
    }

    /**
     * Constructor de la clase ResponsableProyecto. Crea una insancia con
     * los valores introducidos.
     * @param idResponsableIn
     * @param nombresIn los nombres del ResponsableProyecto.
     * @param apellidosIn los apellidos del ResponsableProyecto.
     * @param correoIn el correo electrónico del ResponsableProyecto.
     * @param telefonoIn el teléfono del ResponsableProyecto.
     */
    public ResponsableProyecto( int idResponsableIn, String nombresIn, String apellidosIn,
                                String correoIn, String telefonoIn, List< Integer > idProyectosIn ) {
        idResponsableProyecto = idResponsableIn;
        nombres = nombresIn;
        apellidos = apellidosIn;
        correoElectronico = correoIn;
        telefono = telefonoIn;
        idProyectos = idProyectosIn;
    }

    /**
     * Regresa el id de la instancia del responsable
     * @return el Id de la instancia actual
     */
    public int getIdResponsableProyecto() { return idResponsableProyecto; }

    /**
     * Regresa los nombres del ResponsableProyecto
     * @return
     */
    public String GetNombres() {
        return nombres;
    }

    /**
     * Regresa los apellidos del ResponsableProyecto
     * @return
     */
    public String GetApellidos() {
        return apellidos;
    }

    /**
     * Regresa el correo electrónico del ResponsableProyecto
     * @return
     */
    public String GetCorreo() {
        return correoElectronico;
    }

    /**
     * Regresa el teléfono del ResponsableProyecto
     * @return
     */
    public String GetTelefono() {
        return telefono;
    }

    /**
     * Regresa una lista de todos los proyectos relacionados a un responsable
     * @return una lista de los IDs de los proyectos del responsable
     */
    public List< Integer > getIdProyectos() { return idProyectos; }

    /**
     * Cambia los nombres del ResponsableProyecto por el valor introducido
     * @param nombresIn
     */
    public void SetNombres( String nombresIn ) {
        nombres = nombresIn;
    }

    /**
     * Cambia los apellidos del ResponsableProyecto por el valor introducido
     * @param apellidosIn
     */
    public void SetApellidos( String apellidosIn ) {
        apellidos = apellidosIn;
    }

    /**
     * Cambia el correo electrónico del ResponsableProyecto por el valor introducido
     * @param correoIn
     */
    public void SetCorreo( String correoIn ) {
        correoElectronico = correoIn;
    }

    /**
     * Cambia el teléfono del ResponsableProyecto por el valor introducido
     * @param telefonoIn
     */
    public void SetTelefono( String telefonoIn ) {
        telefono = telefonoIn;
    }

    /**
     * Agrega una ID a la lista de ID proyectos de esta instancia
     * de responsable de proyecto
     * @param idProyecto el ID del proyecto que se desea agregar
     */
    public void AddProyecto( int idProyecto ) {
        if( !idProyectos.contains( idProyecto ) ) {
            idProyectos.add( idProyecto );
        }
    }

    /**
     * Elimina un proyecto de la lista de ID proyectos de esta
     * instancia de responsable proyecto
     * @param idProyecto el ID del proyecto que se desea eliminar
     */
    public void RemoveProyecto( int idProyecto ) {
        for( int i = 0; i < idProyectos.size(); i++ ) {
            if( idProyectos.get( i ) == idProyecto ) {
                idProyectos.remove( i );
            }
        }
    }
}