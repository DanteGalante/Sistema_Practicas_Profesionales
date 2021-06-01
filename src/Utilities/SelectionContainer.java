/*
 * Autor: Dan Javier Olvera Villeda
 * Versión: 1.0
 * Fecha Creación: 06 - mar - 2021
 * Descripción:
 * Singleton encargado de almacenar la información de las elecciones hechas por los usuarios en una ventana,
 * para que se pueda pasar esa informacion a la siguiente pantalla
 */
package Utilities;

import Entities.Estudiante;
import Entities.OrganizacionVinculada;
import Entities.Proyecto;
import Entities.ResponsableProyecto;

public final class SelectionContainer {
    private static SelectionContainer selectionContainer;
    private Proyecto proyectoElegido;
    private Estudiante estudianteElegido;
    private OrganizacionVinculada organizacionElegida;
    private ResponsableProyecto responsableElegido;

    private SelectionContainer() {
        this.estudianteElegido = null;
        this.proyectoElegido = null;
    }

    public static SelectionContainer GetInstance() {
        if( selectionContainer == null ) {
            selectionContainer = new SelectionContainer();
        }
        return selectionContainer;
    }

    public Proyecto getProyectoElegido() {
        return proyectoElegido;
    }

    public Estudiante getEstudianteElegido() {
        return estudianteElegido;
    }

    public ResponsableProyecto getResponsableElegido() {
        return responsableElegido;
    }

    public OrganizacionVinculada getOrganizacionElegida(){
        return organizacionElegida;
    }

    public void setProyectoElegido(Proyecto proyectoElegido) {
        if( proyectoElegido != null ) {
            this.proyectoElegido = proyectoElegido;
        }
    }

    public void setEstudianteElegido(Estudiante estudianteElegido) {
        if( estudianteElegido != null ) {
            this.estudianteElegido = estudianteElegido;
        }
    }

    public void setOrganizacionElegida( OrganizacionVinculada organizacionElegida ){
        if( organizacionElegida != null ) {
            this.organizacionElegida = organizacionElegida;
        }
    }

    public void setResponsableElegido( ResponsableProyecto responsableElegido ){
        if( responsableElegido != null ) {
            this.responsableElegido = responsableElegido;
        }
    }
}