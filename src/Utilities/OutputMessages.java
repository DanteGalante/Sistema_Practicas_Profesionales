/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 31 - mar - 2021
 * Descripción:
 * Clase que contiene todos los mensajes dirigidos al usuario
 * por el sistema.
 */
package Utilities;

/**
 * Clase que contiene todos los mensajes dirigidos al usuario
 * por el sistema.
 */
public class OutputMessages {
    /**
     * Mensaje mostrado en caso de no encontrar el archivo FXML de la pantalla
     * Registro_Estudiante
     * @return una cadena con el mensaje de error
     */
    public String RegistryScreenMissing() { return "No se encontró el archivo FXML de pantalla Registro."; }

    /**
     * Mensaje mostrado en caso de no encontrar el archivo FXML de la pantalla
     * IniciarSesión
     * @return una cadena con el mensaje de error
     */
    public String LoginScreenMissing() { return "No se encontró el archivo FXML de pantalla Login."; }

    /**
     * Mensaje mostrado en caso de no encontrar el archivo fxml del menú principal
     * de Estudiantes
     * @return una cadena con el mensaje de error
     */
    public String StudentMainMenuMissing() { return "No se encontró el archivo FXML del menú principal de estudiante"; }

    /**
     * Mensaje mostrado en caso de no encontrar el archivo FXML de la pantalla
     * IniciarSesion
     * @return una cadena con el mensaje de error
     */
    public String MainScreenDocenteMissing() {
        return "No se encontro el archivo FXML de la pantalla principal de los docentes";
    }

    /**
     * Mensaje mostrado en caso de no encontrar el archivo fxml de la pantalla
     * EscogerProyectos_Estudiante
     * @return una cadena con el mensaje de error
     */
    public String ChooseProjectsMissing() { return "No se encontró el archivo FXML de la pantalla Escoger Proyectos."; }

    /**
     * Mensaje mostrado cuando se intenta cambiar a la pantalla EscogerProyectos_Estudiante
     * y el usuario ya ha seleccionado 3 proyectos
     * @return una cadena con el mensjae de error
     */
    public String AlreadyChoseProjects() { return "Ya seleccionaste 3 proyectos para asignación."; }

    /**
     * Mensaje mostrado cuando se intenta agregar un proyecto a la lista de proyectos seleccionados
     * en la pantalla EscogerProyectos_Estudiantes.
     * @return una cadena con el mensaje de error
     */
    public String AlreadySelectedMaxAmountProjects() { return "Ya tienes 3 proyectos seleccionados."; }

    /**
     * Mensaje mostrado cuando se intenta mandar una selección de proyectos sin tener
     * 3 proyectos seleccionados en la pantalla EscogerProyectos_Estudiante
     * @return una cadena con el mensaje de error
     */
    public String NotEnoughProjectsSelected() { return "No has seleccionado 3 proyectos."; }

    /**
     * Mensaje mostrado cuando se realiza la selección de proyectos por parte del
     * Estudiante de manera exitosa.
     * @return una cadena con el mensaje de éxito
     */
    public String ProjectSelectionSuccessful() { return "Selección de proyectos se realizó con éxito."; }

    /**
     * Mensaje mostrado en caso de no encontrar el archivo FXML de la pantalla
     * Reportes_Estudiante
     * @return una cadena con el mensaje de error
     */
    public String StudentReportScreenMissing() { return "No se encontró el archivo FXML de la pantalla Reportes_Estudiante."; }

    /**
     * Mensaje mostrado en caso de no encontrar el archivo FXML de la pantalla
     * DocumentosAdicionales_Estudiante
     * @return una cadena con el mensaje de error
     */
    public String StudentAdditionalDocumentsMissing() {
        return "No se encontró el archivo FXML de la pantalla DocumentosAdicionales_Estudiante";
    }

    /**
     * Mensaje mostrado en caso de no encontrar el archivo FXML de la pantalla
     * ProyectoAsignado_Estudiante
     * @return una cadena con el mensaje de error
     */
    public String ProjectDetailsMissing() { return "No se encontró el archivo FXML de la pantalla ProyectoAsignado_Estudiante"; }

    /**
     * Mensaje mostrado en caso de no encontrar el archivo FXML de la pantalla
     * Formatos_Estudiante
     * @return una cadena con el mensaje de error
     */
    public String StudentFormatsMissing() { return "No se encontró el archivo FXML de la pantalla Formatos_Estudiante"; }

    /**
     * Mensaje mostrado en caso de no encontrar el archivo FXML de la pantalla
     * GestionarEstudiantes_Coordinador
     * @return una cadena con el mensaje de error
     */
    public String PantallaGestionarEstudiantesPerdida() {
        return "No se encontró el archivo FXML dela pantalla GestionarEstudiantes_Coordinador";
    }

    /**
     * Mensaje mostrado en caso de no encontrar el archivo FXML de la pantalla
     * Principal_Coordinador
     * @return una cadena con el mensaje de error
     */
    public String PantallaPrincipalCoordinadorPerdido() { return "No se encontró el archivo FXML de la pantalla " +
            "Principal_Coordinador."; }

    /**
     * Mensaje mostrado en caso de no encontrar el archivo FXML de la pantalla
     * GestionarOrganizacion_Coordinador
     * @return una cadena con el mensaje de error
     */
    public String PantallaGestionarOrganizacionPerdido() { return "No se encontró el archivo FXML de la pantalla " +
            "GestionarOrganizacion_Coordinador."; }

    /**
     * Mensaje mostrado en caso de no encontrar el archivo FXML de la pantalla
     * RegistrarOrganizacion_Coordinador
     * @return una cadena con el mensaje de error
     */
    public String PantallaRegistrarOrganizacionPerdido() { return "No se encontró el archivo FXML de la pantalla " +
            "RegistrarOrganizacion_Coordinador."; }

    /**
     * Mensaje mostrado en caso de no encontrar el archivo FXML de la pantalla
     * GestionarOrganizacion_Coordinador
     * @return una cadena con el mensaje de error
     */
    public String PantallaGestionarProyectoPerdido() { return "No se encontró el archivo FXML de la pantalla " +
            "GestionarProyecto_Coordinador."; }

    /**
     * Mensaje mostrado cuando se registra una nueva Organización a la
     * base de datos de manera exitosa.
     * @return una cadena con el mensaje de éxito
     */
    public String RegistroOrganizacionExitoso() { return "Registro de Organizacion Exitoso"; }

    /**
     * Mensaje mostrado cuando se registra un nuevo Responsable a la
     * base de datos de manera exitosa.
     * @return una cadena con el mensaje de éxito
     */
    public String RegistroResponsableExitoso() { return "Registro de Responsable Exitoso"; }

    /**
     * Mensaje mostrado en caso de no encontrar el archivo FXML de la pantalla
     * ModificarProyecto_Coordinador
     * @return una cadena con el mensaje de error
     */
    public String PantallaModificarProyectoPerdido() { return "No se encontró el archivo FXML de la pantalla " +
            "ModificarProyecto_Coordinador."; }

    /**
     * Mensaje mostrado cuando se registra un nuevo Estudiante a la
     * base de datos de manera exitosa.
     * @return una cadena con el mensaje de éxito
     */
    public String RegistrationSuccessful() { return "Registro Exitoso"; }

    /**
     * Mensaje mostrado cuando ocurre un error en la base de datos
     * @return una cadena con el mensaje de error
     */
    public String DatabaseConnectionFailed() { return "No hay conexión a la base de datos. Intente más tarde."; }

    /**
     * Mensaje mostrado cuando ya existe un Estudiante en base de datos
     * @return una cadena con el mensaje de error
     */
    public String StudentAlreadyExists() { return "Ya existe un registro con esa información"; }

    /**
     * Mensaje mostrado cuando ya existe una OrganizacionVinculada en base de datos
     * @return una cadena con el mensaje de error
     */
    public String OrganizacionExistente() { return "Ya existe una organización con esa información"; }

    /**
     * Mensaje mostrado cuando ya existe un ResponsableProyecto en base de datos
     * @return una cadena con el mensaje de error
     */
    public String ResponsableExistente() { return "Ya existe un responsable con esa información"; }

    /**
     * Mensaje mostrado cuando se quiere acceder a funcionalidad que requiere tener
     * un proyecto asigando.
     * @return una cadena con el mensaje de error
     */
    public String ProjectNotAssigned() { return "Aún no te han asignado un proyecto."; }

    /**
     * Mensaje mostrado cuando se intenta entregar un reporte con
     * el mismo nombre que otro en el expediente del estudiante
     * @return una cadena con el mensaje de error
     */
    public String ReportNameAlreadyExists() { return "Ya existe un reporte con ese nombre en tu expediente."; }

    /**
     * Mensaje mostrado cuando se intenta entregar un documento con
     * el mismo nombre que otro en el expediente del estudiante
     * @return una cadena con el mensaje de error
     */
    public String DocumentNameAlreadyExists() { return "Ya existe un documento con ese nombre en tu expediente."; }

    /**
     * Mensaje mostrado cuando se intenta eliminar un documento en
     * la pantalla DocumentosAdicionales_Estudiante
     * @return una cadena con el mensaje de confirmacion
     */
    public String DeleteDocumentConfirmation() { return "¿Estas seguro/a que deseas eliminar el archivo?"; }

    /**
     * Mensaje mostrado cuando se intenta eliminar un estudiante en
     * la pantalla GestionarEstudiantes_Coordinador
     * @return una cadena con el mensaje de confirmacion
     */
    public String DeleteStudentConfirmation() { return "¿Estas seguro/a que deseas eliminar el estudiante? " +
                                                       "Esta acción es permanente y no se podrá deshacer."; }
    /**
     * Mensaje mostrado cuando se intenta eliminar un estudiante en
     * la pantalla GestionarEstudiantes_Coordinador
     * @return una cadena con el mensaje de confirmacion
     */
    public String ConfirmacionEliminarOrganizacion() { return "¿Estas seguro/a que deseas eliminar la organización? " +
            "Esta acción es permanente y no se podrá deshacer."; }

    /**
     * mensaje mostrado cuando se introduce información de login que no
     * existe en la base de datos
     * @return una cadena con el mensaje de error
     */
    public String InvalidLoginInformation() { return "El usuario o contraseña es incorrecta."; }

    /**
     * Mensaje mostrado cuando los nombres introducidos por el usuario
     * son inválidos
     * @return una cadena con el mensaje de error
     */
    public String InvalidNames() { return "Los nombres no son validos."; }

    /**
     * Mensaje mostrado cuando los apellidos introducidos por el usuario
     * son inválidos
     * @return una cadena con el mensaje de error
     */
    public String InvalidLastNames() { return "Los apellidos no son validos."; }

    /**
     * Mensaje mostrado cuando el correo electrónico introducido por el usuario
     * es inválido
     * @return una cadena con el mensaje de error
     */
    public String InvalidEmail() { return "El correo electrónico no es valido"; }

    /**
     * Mensaje mostrado cuando la matrícula introducida por el usuario
     * es inválida
     * @return una cadena con el mensaje de error
     */
    public String InvalidMatricula() { return "La matrícula no es valida."; }

    /**
     * Mensaje mostrado cuando el NRC introducido por el usuario
     * es inválido
     * @return una cadena con el mensaje de error
     */
    public String InvalidNRC() { return "El nrc no es vlido."; }

    /**
     * Mensaje mostrado cuando el teléfono introducido por el usuario
     * es inválido
     * @return una cadena con el mensaje de error
     */
    public String InvalidTelephone() { return "El teléfono no es valido."; }

    /**
     * Mensaje mostrado cuando la contraseña introducida por el usuario
     * es inválida
     * @return una cadena con el mensaje de error
     */
    public String InvalidPassword() { return "La contraseña no es valida."; }

    /**
     * Mensaje mostrado el nombre de usuario introducido en la pantalla InicioSesión
     * no es valido.
     * @return una cadena con el mensaje de error
     */
    public String InvalidUsername() { return "El número personal o matrícula no es valida."; }

    /**
     * Mensaje mostrado cuando las contraseñas introducidas por el usuario
     * son inválidas
     * @return una cadena con el mensaje de error
     */
    public String PasswordsDontMatch() { return "Las contraseñas no coinciden."; }

    /**
     * Mensaje mostrado en caso de no encontrar el archivo FXML de la pantalla
     * DescargarArchivo_Docente
     * @return una cadena con el mensaje de error
     */
    public String DescargarArchivoScreenMissing() {
        return "No se encontro el archivo FXML de la pantalla descargar archivo";
    }

    /**
     * Mensaje mostrado cuando se ha subido al sistema con exito un archivo
     * @return una cadena con el mensaje de error
     */
    public String UploadSuccesful() {
        return "El archivo a sido subido al sistema de manera exitosa";
    }

    /**
     * Mensaje mostrado cuando no se ha seleccionado ningun estudiante en una tabla
     * @return una cadena con el mensaje de error
     */
    public String EstudianteNoSeleccionado() { return "No se ha seleccionado ningun estudiante "; }

    /**
     * Mensaje mostrado cuando la dirección introducida por el usuario
     * es inválida
     * @return una cadena con el mensaje de error
     */
    public String DireccionInvalida() { return "La dirección es inválida.."; }

    /**
     * Mensaje mostrado en caso de no encontrar el archivo FXML de la pantalla
     * ValidarInscripcion
     * @return una cadena con el mensaje de error
     */
    public String ValidarInscripcionScreenMissing() {
        return "No se encontro el archivo FXML de la pantalla validar inscripcion";
    }

    /**
     * Mensaje mostrado en caso de no encontrar el archivo FXML de la pantalla
     * ConsultarExpediente_Docente
     * @return una cadena con el mensaje de error
     */
    public String ConsultarExpedienteDocenteScreenMissing() {
        return "No se encontro el archivo FXML de la pantalla consultar expediente";
    }

    /**
     * Mensaje mostrado cuando un informe de problema es guardado con éxito en el sistema
     * @return una cadena con el mensaje de éxito
     */
    public String SavingProblemFormSuccessful() {
        return "Se ha guardado el informe en el sistema";
    }

    /**
     * Mensaje mostrado en caso de no encontrar el archivo FXML de la pantalla
     * ReportarProblema_Docente
     * @return una cadena con el mensaje de error
     */
    public String ReportarProblemaScreenMissing() {
        return "No se encontro el archivo FXML de la pantalla reportar problema";
    }

    /**
     * Mensaje mostrado en caso de no en encontrar un expediente para un estudiante especifico
     * @return una cadena con uel mensaje de error
     */
    public String NoExpedient() {
        return "El estudiante no tiene expediente";
    }

    /**
     * Mensaje mostrad en caso de encontrar que la extensión de un archivo es invalido, es decir
     * que no sea ni pdf, ni doc, ni docx
     * @return una cadena con el mensaje de error
     */
    public String InvalidFileExtension() {
        return "El archivo que se trata de subir no es de un tipo valido";
    }

    /**
     * Mensaje mostrado al eliminar un archivo de consulta de manera exitosa
     * @return una cadena con el mensaje de exito
     */
    public String DeleteFileSucceded() {
        return "El archivo ha sido eliminado";
    }
}