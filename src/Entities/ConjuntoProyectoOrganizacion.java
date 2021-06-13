/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 3 - mayo - 2021
 * Descripción:
 * Clase que contiene la información del Proyecto y
 * organizacion vinculada. Utilizada en la seleccion de
 * proyectos por el estudiante.
 */
package Entities;

import Enumerations.EstadoProyecto;
import Enumerations.TipoSector;

import java.util.List;

/**
 * Clase que contiene la información del Proyecto y
 * organizacion vinculada. Utilizada en la seleccion de
 * proyectos por el estudiante.
 */
public class ConjuntoProyectoOrganizacion {
    private int idProyecto;
    private String nombreProyecto;
    private String descripcion;
    private int numEstudiantesRequeridos;
    private int numEstudiantesAsignados;
    private String fechaRegistro;
    private EstadoProyecto estado;
    private String nombreOrganizacion;
    private String direccion;
    private TipoSector sector;
    private String telefono;
    private String correoElectronico;
    private int idOrganizacion;
    private List< Integer > idResponsables;
    private boolean activa;

    /**
     * Constructor de la clase ConjuntoProyectoOrganizcion. Crea una nueva
     * instancia a partir de un proyecto y una organizacion vinculada.
     * @param proyecto el proyecto ligado a la organizacion vinculada
     * @param organizacion la organizacion vinculada que propone el proyecto
     */
    public ConjuntoProyectoOrganizacion( Proyecto proyecto, OrganizacionVinculada organizacion ) {
        idProyecto = proyecto.getIdProyecto();
        nombreProyecto = proyecto.getNombre();
        descripcion = proyecto.GetDescripcion();
        numEstudiantesRequeridos = proyecto.getNumEstudiantesRequeridos();
        numEstudiantesAsignados = proyecto.GetEstudiantesAsignados();
        fechaRegistro = proyecto.GetFechaRegistro();
        estado = proyecto.GetEstado();
        nombreOrganizacion = organizacion.getNombre();
        direccion = organizacion.getDireccion();
        sector = organizacion.getSector();
        telefono = organizacion.getTelefono();
        correoElectronico = organizacion.getCorreo();
        idOrganizacion = organizacion.getIdOrganizacion();
        idResponsables = organizacion.getResponsables();
        activa = organizacion.getActiveStatus();
    }

    /**
     * Regresa el ID del proyecto
     * @return el ID del proyecto
     */
    public int getIdProyecto() { return idProyecto; }

    /**
     * Regresa el nombre del proyecto
     * @return el nombre del proyecto
     */
    public String getNombreProyecto() { return nombreProyecto; }

    /**
     * Regresa la descripcion del proyecto
     * @return la descripcion del proyecto
     */
    public String getDescripcion() { return descripcion; }

    /**
     * Regresa el numero de estudiantes que son requeridos para el proyecto
     * @return el numero de estudiantes que son requeridos para el proyecto
     */
    public int getNumEstudiantesRequeridos() { return numEstudiantesRequeridos; }

    /**
     * Regresa el numero de estudiantes que ya estan asignados al proyecto
     * @return el numero de estudiantes que ya estan asignados al proyecto
     */
    public int getNumEstudiantesAsignados() { return numEstudiantesAsignados; }

    /**
     * Regresa la fecha de registro del proyecto a al sistema
     * @return la fecha de registro del proyecto a al sistema
     */
    public String getFechaRegistro() { return fechaRegistro; }

    /**
     * Regresa el estado del proyecto
     * @return el estado del proyecto
     */
    public EstadoProyecto getEstado() { return estado; }

    /**
     * Regresa el nombre de la organizacion vinculada
     * @return el nombre de la organizacion vinculada
     */
    public String getNombreOrganizacion() { return nombreOrganizacion; }

    /**
     * Regresa la direccion de la organizacion vinculada
     * @return la direccion de la organizacion vinculada
     */
    public String getDireccion() { return direccion; }

    /**
     * Regresa el sector de la organizacion vinculada
     * @return el sector de la organizacion vinculada
     */
    public TipoSector getSector() { return sector; }

    /**
     * Regresa el telefono de la organizacion vinculada
     * @return el telefono de la organizacion vinculada
     */
    public String getTelefono() { return telefono; }

    /**
     * Regresa el correo electronico de contacto de la organizacion vinculada
     * @return el correo electronico de contacto de la organizacion vinculada
     */
    public String getCorreoElectronico() { return correoElectronico; }

    /**
     * Regresa el ID de la organizacion vinculada
     * @return el ID de la organizacion vinculada
     */
    public int getIdOrganizacion() { return idOrganizacion; }

    /**
     * Regresa una lista de los responsables de la organizacion vinculada
     * @return una lista de los responsables de lo organizacion vinculada
     */
    public List< Integer > getIdResponsables() { return idResponsables; }

    /**
     * Regresa el estado de la organizacion vinculada.
     * @return el estado de organizacion vinculada
     */
    public boolean getActivo() { return activa; }

}
