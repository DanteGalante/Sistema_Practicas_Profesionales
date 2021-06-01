/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 3 - mar - 2021
 * Descripción:
 * Clase que contiene la información del Proyecto
 */
package Entities;

import Enumerations.EstadoProyecto;

/**
 * Clase que contiene la información del Proyecto
 */
public class Proyecto {
    private int idProyecto;
    private String nombre;
    private String descripcion;
    private int numEstudiantesRequeridos;
    private int numEstudiantesAsignados;
    private String fechaRegistro;
    private EstadoProyecto estado;

    /**
     * Constructor sin parametros de la clase Proyecto.
     * Crea una instancia con id = 0 y valores vacíos.
     */
    public Proyecto() {
        idProyecto = 0;
        nombre = "";
        descripcion = "";
        numEstudiantesRequeridos = 0;
        numEstudiantesAsignados = 0;
        fechaRegistro = "";
        estado = null;
    }

    /**
     * Constructor de clase Proyecto. Crea una instancia a partir
     * de una isntancia existente de la clase Proyecto.
     * @param original
     */
    public Proyecto( Proyecto original ) {
        this( original.idProyecto, original.nombre, original.descripcion, original.numEstudiantesRequeridos,
                original.numEstudiantesAsignados, original.fechaRegistro, original.estado );
    }

    /**
     * Constructor de clase Proyecto. Crea una insancia con los
     * valores introducidos.
     * @param idIn el ID del proyecto.
     * @param nombreIn el nombre del proyecto
     * @param descripcionIn la descripcion del proyecto
     * @param numEstudiantesRequeridosIn la cantidad de estudiantes requeridos
     * @param numEstudiantesAsignadosIn la cantidad de estudiantes asignados
     * @param fechaRegistroIn la fecha de registro del proyecto en el sistema
     * @param estadoIn el estado actual del proyecto
     */
    public Proyecto( int idIn, String nombreIn, String descripcionIn, int numEstudiantesRequeridosIn,
                     int numEstudiantesAsignadosIn, String fechaRegistroIn, EstadoProyecto estadoIn ) {
        idProyecto = idIn;
        nombre = nombreIn;
        descripcion = descripcionIn;
        numEstudiantesRequeridos = numEstudiantesRequeridosIn;
        numEstudiantesAsignados = numEstudiantesAsignadosIn;
        fechaRegistro = fechaRegistroIn;
        estado = estadoIn;
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
    public String getNombre() {
        return nombre;
    }

    /**
     * Regresa la descripción del proyecto
     * @return la descripción del proyecto
     */
    public String GetDescripcion() {
        return descripcion;
    }

    /**
     * Regresa la cantidad de estudiantes requeridos para
     * realizar este proyecto
     * @return cantidad de estudiantes requeridos
     */
    public int getNumEstudiantesRequeridos() {
        return numEstudiantesRequeridos;
    }

    /**
     * Regresa la cantidad de estudiantes asignados a este proyecto
     * @return cantidad de estudiantes asignados
     */
    public int GetEstudiantesAsignados() {
        return numEstudiantesAsignados;
    }

    /**
     * Regresa la fecha de registro en el sistema del proyecto
     * @return la fecha de registro del proyecto al sistema
     */
    public String GetFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * Regresa el estado actual del proyecto
     * @return el estado actual del proyecto
     */
    public EstadoProyecto GetEstado() {
        return estado;
    }

    /**
     * Cambia el nombre del proyecto al valor introducido
     * @param nombreIn el nuevo nombre del proyecto
     */
    public void SetNombre( String nombreIn ) {
        nombre = nombreIn;
    }

    /**
     * Cambia la descripción del proyecto al valor introducido
     * @param descripcionIn la nueva descripción del proyecto
     */
    public void SetDescripcion( String descripcionIn ) {
        descripcion = descripcionIn;
    }

    /**
     * Cambia la cantidad de estudiantes requeridos al valor introducido
     * @param cantidad la nueva cantidad de estudiantes requeridos
     */
    public void SetEstudiantesRequeridos( int cantidad ) {
        numEstudiantesRequeridos = cantidad;
    }

    /**
     * Cambia la cantidad de estudiantes asignados al valor introducido
     * @param cantidad la nueva cantidad de estudiantes asignados
     */
    public void SetEstudiantesAsignados( int cantidad ) {
        numEstudiantesAsignados = cantidad;
    }

    /**
     * Cambia la fecha de registro al valor introducido
     * @param fechaIn la nueva fecha de registro
     */
    public void SetFechaRegistro( String fechaIn ) {
        fechaRegistro = fechaIn;
    }
}