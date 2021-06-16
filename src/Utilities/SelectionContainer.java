/*
 * Autor: Dan Javier Olvera Villeda
 * Versión: 1.0
 * Fecha Creación: 06 - mar - 2021
 * Descripción:
 * Singleton encargado de almacenar la información de las elecciones hechas por los usuarios en una ventana,
 * para que se pueda pasar esa informacion a la siguiente pantalla
 */
package Utilities;

import Entities.*;

/**
 * Clase utilizada para pasar entidades de una pantalla a otra
 */
public final class SelectionContainer {
    private static SelectionContainer selectionContainer;
    private Proyecto proyectoElegido;
    private Estudiante estudianteElegido;
    private OrganizacionVinculada organizacionElegida;
    private ResponsableProyecto responsableElegido;
    private InformeProblema informeElegido;
    private ArchivoConsulta[] archivosConsulta;
    private ConjuntoDocenteInforme conjuntoDocenteInforme;

    /**
     * Constructor privado default de la clase
     */
    private SelectionContainer() {
        this.estudianteElegido = null;
        this.proyectoElegido = null;
        this.organizacionElegida = null;
        this.responsableElegido = null;
        this.informeElegido = null;
        this.archivosConsulta = null;
        this.conjuntoDocenteInforme = null;
    }

    /**
     * Retorna una intancia de la clase
     * @return
     */
    public static SelectionContainer GetInstance() {
        if( selectionContainer == null ) {
            selectionContainer = new SelectionContainer();
        }
        return selectionContainer;
    }

    /**
     * Regresa el proyecto almacenado en la clase
     * @return un proyecto
     */
    public Proyecto getProyectoElegido() {
        return proyectoElegido;
    }

    /**
     * Regresa el estudiante almacenado en la clase
     * @return un estudiante
     */
    public Estudiante getEstudianteElegido() {
        return estudianteElegido;
    }

    /**
     * Regresa el resposnable almacenado en la clase
     * @return un responsableProyecto
     */
    public ResponsableProyecto getResponsableElegido() {
        return responsableElegido;
    }

    /**
     * Regresa la organizacion vinculada almacenada en la clase
     * @return una organizacion vinculada
     */
    public OrganizacionVinculada getOrganizacionElegida(){
        return organizacionElegida;
    }

    /**
     * Regresa un arreglo de los archivos consulta almacenados en la clase
     * @return un arreglo de archivos consulta
     */
    public ArchivoConsulta[] getArchivosConsulta() {
        return archivosConsulta;
    }

    /**
     * Regresa el conjunto docente informe almacenado en la clase
     * @return un ConjuntoDocenteInforme
     */
    public ConjuntoDocenteInforme getConjuntoDocenteInforme(){return conjuntoDocenteInforme;}

    /**
     * Hace un set de conjunto docente informe que sera utilizado en otra pantalla
     * @param conjuntoDocenteInforme el conjunto docente informe que sera utilizado en otra pantalla
     */
    public void setConjuntoDocenteInforme(ConjuntoDocenteInforme conjuntoDocenteInforme){
        if( conjuntoDocenteInforme != null ){
            this.conjuntoDocenteInforme = conjuntoDocenteInforme;
        }
    }

    /**
     * Hace un set de proyecto que sera utilizado en otra pantalla
     * @param proyectoElegido el proyecto que sera utilizado en otra pantalla
     */
    public void setProyectoElegido(Proyecto proyectoElegido) {
        if( proyectoElegido != null ) {
            this.proyectoElegido = proyectoElegido;
        }
    }

    /**
     * Retorna el informe de problema almacenado en la clase
     * @return el informe problema
     */
    public InformeProblema getInformeElegido(){
        return informeElegido;
    }

    /**
     * Hace un set del estudiante que sera utilizada en otra pantalla
     * @param estudianteElegido el estudiante que sera utilizada en otra pantalla
     */
    public void setEstudianteElegido(Estudiante estudianteElegido) {
        if( estudianteElegido != null ) {
            this.estudianteElegido = estudianteElegido;
        }
    }

    /**
     * Hace un set de la organizacion que sera utilizada en otra pantalla
     * @param organizacionElegida la organizacion que sera utilizada en otra pantalla
     */
    public void setOrganizacionElegida( OrganizacionVinculada organizacionElegida ){
        if( organizacionElegida != null ) {
            this.organizacionElegida = organizacionElegida;
        }
    }

    /**
     * Hace un set de un responsable que sera utilizado en otra pantalla
     * @param responsableElegido el responsable que sera utilizado en otra pantalla
     */
    public void setResponsableElegido( ResponsableProyecto responsableElegido ){
        if( responsableElegido != null ) {
            this.responsableElegido = responsableElegido;
        }
    }

    /**
     * Hace un set de un informe que sera utilizado en otra pantalla
     * @param informeElegido el informe que sera utilizado en otra pantalla
     */
    public void setInformeElegido( InformeProblema informeElegido ){
        if( informeElegido != null ) {
            this.informeElegido = informeElegido;
        }
    }

    /**
     * Hace un set de los archivos de consula que seran utilizados en otras pantallas
     * @param archivosConsulta los archivos consulta que seran utilizados en otras pantallas
     */
    public void setArchivosConsulta(ArchivoConsulta[] archivosConsulta) {
        this.archivosConsulta = archivosConsulta;
    }
}