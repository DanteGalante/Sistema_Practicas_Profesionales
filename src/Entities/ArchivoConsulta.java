/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 3 - mar - 2021
 * Descripción:
 * Clase que contiene la información de ArchivoConsulta
 */
package Entities;

import java.io.File;

/**
 * Clase que contiene la información de ArchivoConsulta
 */
public class ArchivoConsulta {
    private int idArchivo;
    private String titulo;
    private File descripcion;
    private String numeroPersonal;

    /**
     * Crea una instancia de la clase con un id = 0
     * y cadenas vacías
     */
    public ArchivoConsulta() {
        idArchivo = 0;
        titulo = "";
        descripcion = null;
        numeroPersonal = "";
    }

    /**
     * Crea una instancia de la clase a partir de  una instancia existente
     * @param original la instancia existente
     */
    public ArchivoConsulta( ArchivoConsulta original ) {
        this( original.idArchivo, original.titulo, original.descripcion, original.numeroPersonal );
    }

    /**
     * Crea una instancia de la clase a partir de los valores introducidos
     * @param idIn el id del archivo
     * @param tituloIn el título del archivo
     * @param descipcionIn la descripción del archivo
     */
    public ArchivoConsulta( int idIn, String tituloIn, File descipcionIn, String numeroPersonalIn ) {
        idArchivo = idIn;
        titulo = tituloIn;
        descripcion = descipcionIn;
        numeroPersonal = numeroPersonalIn;
    }

    /**
     * Regresa el id del archivo
     * @returnel id del archivo
     */
    public int GetId() { return idArchivo; }
    /**
     * Regresa el título del archivo
     * @return el título del archivo
     */
    public String getTitulo() { return titulo; }

    /**
     * Regresa la descripción del archivo
     * @return la descripción del archivo
     */
    public File GetDescripcion() { return descripcion; }

    /**
     * Regresa el número de personal del docente que subió el archivo
     * @return el número de personal
     */
    public String GetNumeroPersonal() { return numeroPersonal; }

    /**
     * Cambia el título del arhivo al valor introducido
     * @param tituloIn el nuevo título
     */
    public void SetTitulo( String tituloIn ) { titulo = tituloIn; }

    /**
     * Cambia la descripción del archivo al valor introducido
     * @param descripcionIn la nueva descripción
     */
    public void SetDescripcion( File descripcionIn ) { descripcion = descripcionIn; }

    /**
     * Cambia el número de personal por el valor introducido
     * @param numeroPersonalIn el nuevo número de personal
     */
    public void SetNumeroPersonal( String numeroPersonalIn ) { numeroPersonal = numeroPersonalIn; }
}