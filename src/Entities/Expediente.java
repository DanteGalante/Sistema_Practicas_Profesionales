/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 3 - mar - 2021
 * Descripción:
 * Clase que contiene la información de Expediente
 */
package Entities;

/**
 * Clase que contiene la información de Expediente
 */
public class Expediente {
    private int claveExpediente;
    private int idProyecto;
    private String matricula;
    private String fechaAsignacion;
    private int horasAcumuladas;
    private int numeroArchivos;

    /**
     * Crea una instancia con valores en 0 y cadenas
     * vacías.
     */
    public Expediente() {
        claveExpediente = 0;
        idProyecto = 0;
        matricula = "";
        fechaAsignacion = "";
        horasAcumuladas = 0;
        numeroArchivos = 0;
    }

    /**
     * Crea una nueva instancia de expediente a partir de otra que ya existe.
     * @param original la instancia de expediente que ya existe
     */
    public Expediente( Expediente original ) {
        this( original.claveExpediente, original.idProyecto, original.matricula, original.fechaAsignacion,
                original.horasAcumuladas, original.numeroArchivos );
    }

    /**
     * Crea una instancia con los valores introducidos.
     * @param claveIn la clave del expediente
     * @param fechaAsignacionIn la fecha en la cual se asignó el proyecto al estudiante
     * @param horasAcumuladasIn las horas acumuladas del estudiante
     * @param numeroArchivosIn el numero de archivos contenidos en el expediente
     */
    public Expediente( int claveIn, int idProyectoIn, String matriculaIn, String fechaAsignacionIn,
                       int horasAcumuladasIn, int numeroArchivosIn ) {
        claveExpediente = claveIn;
        idProyecto = idProyectoIn;
        matricula = matriculaIn;
        fechaAsignacion = fechaAsignacionIn;
        horasAcumuladas = horasAcumuladasIn;
        numeroArchivos = numeroArchivosIn;
    }

    /**
     * Regresa la clave del expediente
     * @return la clave del expediente
     */
    public int GetClave() {
        return claveExpediente;
    }

    /**
     * Regresa el ID del proyecto asociado al expediente
     * @return el ID del proyecto
     */
    public int GetIDProyecto() { return idProyecto; }

    /**
     * Regresa la matrícula del estudiante asociado al expediente
     * @return la matrícula de estudiante
     */
    public String GetMatricula() { return matricula; }

    /**
     * Regresa la fecha en la cual se asignó el proyecto al estudiante
     * @return la fecha de asignación
     */
    public String GetFechaAsignacion() {
        return fechaAsignacion;
    }

    /**
     * Regresa las horas que el estudiante ha acumulado
     * @return las horas acumuladas
     */
    public int GetHorasAcumuladas() {
        return horasAcumuladas;
    }

    /**
     * Regresa el número de archivos almacenados en este expediente
     * @return la cantidad de archivos
     */
    public int GetNumeroArchivos() {
        return numeroArchivos;
    }

    /**
     * Cambia la fecha de asignación al valor introducido
     * @param fechaIn la nueva fecha
     */
    public void SetFechaAsignacion( String fechaIn ) {
        fechaAsignacion = fechaIn;
    }

    /**
     * Cambia el valor de las horas acumuladas al valor introducido
     * @param horasIn la nueva cantidad de horas
     */
    public void SetHorasAcumuladas( int horasIn ) {
        horasAcumuladas = horasIn;
    }

    /**
     * Cambia la cantidad de archivos almacenados por el valor introducido
     * @param cantidad la nueva cantidad de archivos
     */
    public void SetNumeroArchivos( int cantidad ) {
        numeroArchivos = cantidad;
    }
}