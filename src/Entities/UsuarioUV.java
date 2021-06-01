/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 3 - mar - 2021
 * Descripción:
 * Superclase de los usuarios del sistema. Contiene la información
 * básica de cualquier tipo de usuario. Estudiante, docente y
 * coordinador heredan de esta clase.
 */
package Entities;

/**
 * Superclase de los usuarios del sistema. Contiene la información
 * básica de cualquier tipo de usuario. Estudiante, docente y
 * coordinador heredan de esta clase.
 */
public class UsuarioUV {
    protected int idUsuario;
    protected String nombres;
    protected String apellidos;
    protected String usuario;
    protected String contrasena;
    protected String correoElectronico;
    protected String telefono;
    private String nombreCompleto;

    /**
     * constructor sin parametros de la superclase UsuarioUV.
     * Crea una instancia con id = 0 y cadenas vacías.
     */
    public UsuarioUV() {
        idUsuario = 0;
        nombres = "";
        apellidos = "";
        usuario = "";
        contrasena = "";
        correoElectronico = "";
        telefono = "";
    }

    /**
     * Constructor de la superclase UsuarioUV. Crea una nueva
     * instancia de UsuarioUV a partir de una instancia existente.
     * @param original instancia existente de UsuarioUV
     */
    public UsuarioUV( UsuarioUV original ) {
        this( original.idUsuario, original.nombres, original.apellidos, original.usuario, original.contrasena,
                original.correoElectronico, original.telefono );
    }

    /**
     * Constructor de la superclase UsuarioUV. Crea una instancia
     * con los valores introducidos al constructor.
     * @param idIn el ID del usuario asignado por el SMBDR.
     * @param nombresIn los nombres del usuario.
     * @param apellidosIn los apellidos del usuario.
     * @param usuarioIn utilizado para iniciar sesión al SPP.
     * @param contrasenaIn utilizada para iniciar sesión al SPP.
     * @param correoIn correo electrónico del usuario.
     * @param telefonoIn teléfono del usuario.
     */
    public UsuarioUV( int idIn, String nombresIn, String apellidosIn, String usuarioIn, String contrasenaIn,
                      String correoIn, String telefonoIn ) {
        idUsuario = idIn;
        nombres = nombresIn;
        apellidos = apellidosIn;
        usuario = usuarioIn;
        contrasena = contrasenaIn;
        correoElectronico = correoIn;
        telefono = telefonoIn;
    }

    /**
     * Regresa el id del usuario
     * @return el id del usuario
     */
    public int GetID() {
        return idUsuario;
    }

    /**
     * Regresa los nombres del UsuarioUV.
     * @return
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * Regresa los apellidos del UsuarioUV
     * @return
     */
    public String GetApellidos() {
        return apellidos;
    }

    /**
     * Regresa el nombre de usuario del UsuarioUV
     * @return
     */
    public String GetUsuario() {
        return usuario;
    }

    /**
     * Regresa la contraseña del UsuarioUV
     * @return
     */
    public String GetContrasena() {
        return contrasena;
    }

    /**
     * Regresa el correo electrónico del UsuarioUV
     * @return
     */
    public String GetCorreo() {
        return correoElectronico;
    }

    /**
     * Regresa el teléfono del UsuarioUV
     * @return
     */
    public String GetTelefono() {
        return telefono;
    }

    /**
     * Regresa el nombreCompleto del UsuarioUV
     * @return
     */
    public String getNombreCompleto(){return nombres + " " + apellidos;}

    /**
     * Cambio los nombres del UsuarioUV al valor introducido
     * @param nombresIn
     */
    public void SetNombres( String nombresIn ) {
        nombres = nombresIn;
    }

    /**
     * Cambia los apellidos del usuarioUV al valor introducido
     * @param apellidosIn
     */
    public void SetApellidos( String apellidosIn ) {
        apellidos = apellidosIn;
    }

    /**
     * Cambia el nombre de usuario del UsuarioUV al valor introducido
     * @param usuarioIn
     */
    public void SetUsuario( String usuarioIn ) {
        usuario = usuarioIn;
    }

    /**
     * Cambia la contraseña del UsuarioUv al valor introducido
     * @param contrasenaIn
     */
    public void SetContrasena( String contrasenaIn ) {
        contrasena = contrasenaIn;
    }

    /**
     * Cambia el correo electrónico del UsuarioUv al valor introducido
     * @param correoIn
     */
    public void SetCorreoElectronico( String correoIn ) {
        correoElectronico = correoIn;
    }

    /**
     * Cambia el teléfono del UsuarioUv al valor introducido
     * @param telefonoIn
     */
    public void SetTelefono( String telefonoIn ) {
        telefono = telefonoIn;
    }
}