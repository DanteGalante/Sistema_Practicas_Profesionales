/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 31 - mar - 2021
 * Descripción:
 * Singleton encargado de almacenar la información de la
 * sesión actual del sistema de prácticas profesionales.
 */
package Utilities;

import Entities.Coordinador;
import Entities.Docente;
import Entities.Estudiante;

public final class LoginSession {
    private static LoginSession loginInstance;
    private Coordinador usuarioCoordinador;
    private Docente usuarioDocente;
    private Estudiante usuarioEstudiante;

    private LoginSession() {
        usuarioCoordinador = null;
        usuarioDocente = null;
        usuarioEstudiante = null;
    }

    public static LoginSession GetInstance() {
        if( loginInstance == null ) {
            loginInstance = new LoginSession();
        }
        return loginInstance;
    }

    public boolean IsLoggedIn() {
        return !( usuarioCoordinador == null && usuarioDocente == null && usuarioEstudiante == null );
    }

    public void Login( Coordinador coordinador ) {
        if( !IsLoggedIn() && coordinador != null ) {
            usuarioCoordinador = coordinador;
        }
    }

    public void Login( Docente docente ) {
        if( !IsLoggedIn() && docente != null ) {
            usuarioDocente = docente;
        }
    }

    public void Login( Estudiante estudiante ) {
        if( !IsLoggedIn() && estudiante != null ) {
            usuarioEstudiante = estudiante;
        }
    }

    public void Logout() {
        if( IsLoggedIn() ) {
            usuarioCoordinador = null;
            usuarioDocente = null;
            usuarioEstudiante = null;
        }
    }

    public Coordinador GetCoordinador() {
        return usuarioCoordinador;
    }

    public Docente GetDocente() {
        return  usuarioDocente;
    }

    public Estudiante GetEstudiante() {
        return usuarioEstudiante;
    }
}