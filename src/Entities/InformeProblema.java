/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 3 - mar - 2021
 * Descripción:
 * Clase que contiene la información de InformeProblema
 */
package Entities;

/**
 * Clase que contiene la información de InformeProblema
 */
public class InformeProblema {
    private int identificador;
    private String fechaEnviada;
    private String numeroPersonal;
    private String estudiante;
    private String asunto;
    private String contenido;

    /**
     * Crea una instancia con un identificador = 0 y cadenas
     * vacías
     */
    public InformeProblema() {
        identificador = 0;
        fechaEnviada = "";
        numeroPersonal = "";
        estudiante = "";
        asunto = "";
        contenido = "";
    }

    /**
     * Crea una instancia a partir de otra instancia existente.
     * @param original la instancia existente
     */
    public InformeProblema( InformeProblema original ) {
        this( original.identificador, original.fechaEnviada, original.numeroPersonal, original.estudiante,
                original.asunto, original.contenido );
    }

    /**
     * Crea una instancia a partir de los valores introducidos
     * @param identificadorIn el identificador del informe
     * @param fechaEnviadaIn la fecha en la que fue enviado el informe
     * @param numeroPersonalIn el docente que envio el informe
     * @param estudianteIn la matrícula del estudiante del cual trata el informe
     * @param asuntoIn el asunto del informe
     * @param contenidoIn el contenido del informe
     */
    public InformeProblema( int identificadorIn, String fechaEnviadaIn, String numeroPersonalIn, String estudianteIn,
                            String asuntoIn, String contenidoIn ) {
        identificador = identificadorIn;
        fechaEnviada = fechaEnviadaIn;
        numeroPersonal = numeroPersonalIn;
        estudiante = estudianteIn;
        asunto = asuntoIn;
        contenido = contenidoIn;
    }

    /**
     * Regresa la fecha en la cual fue enviado el informe
     * @return la fecha de envio
     */
    public String GetFechaEnviada() {
        return fechaEnviada;
    }

    /**
     * Regresa el número de personal de la persona que envió el informe
     * @return el número de personal
     */
    public String GetNumeroPersonal() {
        return numeroPersonal;
    }

    /**
     * Regresa la matrícula del estudiante del cual trata el informe
     * @return la matrícula del estudiante
     */
    public String GetEstudiante() {
        return estudiante;
    }

    /**
     * Regresa el asunto del informe
     * @return el asunto del informe
     */
    public String GetAsunto() {
        return asunto;
    }

    /**
     * Regresa el contenido del informe
     * @return el contenido del informe
     */
    public String GetContenido() {
        return contenido;
    }

    /**
     * Cambia la fecha enviada por el valor introducido
     * @param fechaIn la nueva fecha
     */
    public void SetFechaEnviada( String fechaIn ) {
        fechaEnviada = fechaIn;
    }

    /**
     * Cambia el número de personal por el valor introducido
     * @param enviadoIn el nuevo número de personal
     */
    public void SetNumeroPersonal( String enviadoIn ) {
        numeroPersonal = enviadoIn;
    }

    /**
     * Cambia la matrícula del estudiante por el valor introducido
     * @param estudianteIn la nueva matrícula
     */
    public void SetEstudiante( String estudianteIn ) {
        estudiante = estudianteIn;
    }

    /**
     * Cambia el asunto del informr por el valor introducido
     * @param asuntoIn el nuevo asunto
     */
    public void SetAsunto( String asuntoIn ) {
        asunto = asuntoIn;
    }

    /**
     * Cambia el contenido del informe por el valor introducido
     * @param contenidoIn el nuevo contenido
     */
    public void SetContenido( String contenidoIn ) {
        contenido = contenidoIn;
    }
}