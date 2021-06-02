/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 3 - mar - 2021
 * Descripción:
 * Clase que contiene la información de Reporte
 */
package Entities;

import Enumerations.TipoReporte;

import java.io.File;

/**
 * Clase que contiene la información de Reporte
 */
public class Reporte extends Documento {
    private TipoReporte tipo;
    private int horasReportadas;
    private int idReporte;

    /**
     * Crea una instancia de la clase con un id = 0, 0 horas reportadas
     * y cadenas vacías.
     */
    public Reporte() {
        super();
        horasReportadas = 0;
        tipo = null;
        idReporte = 0;
    }

    /**
     * Crea una instancia de la clase a partir de una instancia existente.
     * @param original la instancia existente
     */
    public Reporte( Reporte original ) {
        this( original.idDocumento, original.idReporte, original.titulo, original.descripcion, original.fechaEntrega,
                original.GetClaveExpediente(), original.horasReportadas, original.tipo );
    }

    /**
     * Crea una instancia de Reporte utilizando un Documento
     * @param documento la instancia de documento
     * @param tipoIn el tipo de reporte
     * @param horasReportadasIn las horas que se reportan en este reporte
     */
    public Reporte( Documento documento, TipoReporte tipoIn, int horasReportadasIn, int idReporteIn ) {
        this( documento.getIdDocumento(), idReporteIn, documento.getTitulo(), documento.GetDescripcion(), documento.getFechaEntrega(),
                documento.GetClaveExpediente(), horasReportadasIn, tipoIn );
    }

    /**
     * Crea una instancia de la clase con los valores introducidos.
     * @param idDocumentoIn el id del Documento
     * @param tituloIn el título del reporte
     * @param descripcionIn la descripción del reporte
     * @param fechaIn la fecha en la cual fue entregada el reporte
     * @param horasReportadasIn las horas que se reportan en el archivo
     * @param tipoIn el tipo de reporte
     */
    public Reporte(int idDocumentoIn, int idReporteIn, String tituloIn, File descripcionIn, String fechaIn, int claveExpedienteIn,
                   int horasReportadasIn, TipoReporte tipoIn ) {
        super( idDocumentoIn, tituloIn, descripcionIn, fechaIn, claveExpedienteIn );
        horasReportadas = horasReportadasIn;
        tipo = tipoIn;
        idReporte = idReporteIn;
    }

    /**
     * Regresa el id del reporte
     * @return el id del reporte
     */
    public int GetIdReporte() { return idReporte; }

    /**
     * Regresa las horas reportadas en el reporte
     * @return las horas reportadas
     */
    public int GetHorasReportadas() {
        return horasReportadas;
    }

    /**
     * Regresa el tipo de reporte
     * @return el tipo de reporte
     */
    public TipoReporte GetTipoReporte() {
        return tipo;
    }

    /**
     * Cambia la cantidad de horas que se reportan por el valor introducido
     * @param horasIn la nueva cantidad de horas
     */
    public void SetHorasReportadas( int horasIn ) {
        horasReportadas = horasIn;
    }
}