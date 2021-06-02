/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 3 - mar - 2021
 * Descripción:
 * Clase que contiene la información de Docente. Hereda
 * de la superclase UsuarioUV.
 */
package Entities;

/**
 * Clase que contiene la información de Docente. Hereda
 * de la superclase UsuarioUV.
 */
public class Docente extends UsuarioUV {
    private String numeroPersonal;
    private String nrc;

    /**
     * Constructor sin parametros de Docente. Crea una instancia
     * con un ID = 0 y cadenas vacías.
     */
    public Docente() {
        super();
        numeroPersonal = "";
        nrc = "";
    }

    /**
     * Constructor de la clase Docente. Crea una nueva
     * instancia de Docente a partir de una instancia existente.
     * @param original instancia existente de coordinador.
     */
    public Docente( Docente original ) {
        this( original.idUsuario, original.nombres, original.apellidos, original.usuario, original.contrasena,
                original.correoElectronico, original.telefono, original.numeroPersonal, original.nrc );
    }

    public Docente( UsuarioUV usuario, String numeroPersonalIn, String nrcIn ) {
        this( usuario.idUsuario, usuario.nombres, usuario.apellidos, usuario.usuario, usuario.contrasena,
                usuario.correoElectronico, usuario.telefono, numeroPersonalIn, nrcIn );
    }

    /**
     * Constructor de la clase Docente. Crea una insancia con
     * los valores introducidos.
     * @param idIn el ID del Docente asignado por el SMBDR.
     * @param nombresIn los nombres del Docente.
     * @param apellidosIn los apellidos del Docente.
     * @param usuarioIn utilizado para iniciar sesión al SPP.
     * @param contrasenaIn utilizada para iniciar sesión al SPP.
     * @param correoElectronicoIn correo electrónico del Docente.
     * @param telefonoIn teléfono del Docente.
     * @param numeroPersonalIn número de personal del Docente, utilizado como clave única.
     */
    public Docente( int idIn, String nombresIn, String apellidosIn, String usuarioIn, String contrasenaIn,
                   String correoElectronicoIn, String telefonoIn, String numeroPersonalIn, String nrcIn ) {
        super( idIn, nombresIn, apellidosIn, usuarioIn, contrasenaIn, correoElectronicoIn, telefonoIn );
        numeroPersonal = numeroPersonalIn;
        nrc = nrcIn;
    }

    /**
     * Regresa el numero de personal del Docente
     * @return
     */
    public String GetNumeroPersonal() {
        return numeroPersonal;
    }

    /**
     * Regresa el nrc del Docente
     * @return
     */
    public String GetNrc() {
        return nrc;
    }

    /**
     * Cambia el nrc del docente por el valor introducido
     * @param nrcIn
     */
    public void SetNrc( String nrcIn ) {
        nrc = nrcIn;
    }
}