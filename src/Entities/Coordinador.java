/*
* Autor: Christian Felipe de Jesus Avila Valdes
* Versión: 1.0
* Fecha Creación: 3 - mar - 2021
* Descripción:
* Clase que contiene la información de Coordinador. Hereda
* de la superclase UsuarioUV.
 */
package Entities;

/**
 * Clase que contiene la información de Coordinador. Hereda
 * de la superclase UsuarioUV.
 */
public class Coordinador extends UsuarioUV {
    private String numeroPersonal;

    /**
     * Constructor sin parametros de Coordinador. Crea una instancia
     * con un ID = 0 y cadenas vacías.
     */
    public Coordinador() {
        super();
        numeroPersonal = "";
    }

    /**
     * Constructor de la clase Coordinador. Crea una nueva
     * instancia de Coordinador a partir de una instancia existente.
     * @param original instancia existente de coordinador.
     */
    public Coordinador( Coordinador original ) {
        this( original.idUsuario, original.nombres, original.apellidos, original.usuario, original.contrasena,
                original.correoElectronico, original.telefono, original.numeroPersonal );
    }

    public Coordinador( UsuarioUV usuario, String numeroPersonalIn ) {
        this( usuario.idUsuario, usuario.nombres, usuario.apellidos, usuario.usuario, usuario.contrasena,
                usuario.correoElectronico, usuario.telefono, numeroPersonalIn );
    }

    /**
     * Constructor de la clase Coordinador. Crea una insancia con
     * los valores introducidos.
     * @param idIn el ID del coordinador asignado por el SMBDR.
     * @param nombresIn los nombres del coordinador.
     * @param apellidosIn los apellidos del coordinador.
     * @param usuarioIn utilizado para iniciar sesión al SPP.
     * @param contrasenaIn utilizada para iniciar sesión al SPP.
     * @param correoIn correo electrónico del coordinador.
     * @param telefonoIn teléfono del coordinador.
     * @param numeroPersonalIn número de personal del coordinador, utilizado como clave única.
     */
    public Coordinador( int idIn, String nombresIn, String apellidosIn, String usuarioIn, String contrasenaIn,
                        String correoIn, String telefonoIn, String numeroPersonalIn ) {
        super( idIn, nombresIn, apellidosIn, usuarioIn, contrasenaIn, correoIn, telefonoIn );
        numeroPersonal = numeroPersonalIn;
    }

    /**
     * Regresa el numero de personal del Coordinador
     * @return
     */
    public String GetNumeroPersonal() {
        return numeroPersonal;
    }
}