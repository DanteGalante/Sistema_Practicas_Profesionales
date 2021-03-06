/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 30 - mar - 2021
 * Descripción:
 * Clase encargada de realizar los cambios de pantallas de
 * la aplicación.
 */
package Utilities;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.text.Text;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Clase encargada de realizar los cambios de pantallas de
 * la aplicación.
 */
public class ScreenChanger {
    private OutputMessages outputMessages = new OutputMessages();
    private String loginScreen = "../Resources/IniciarSesion.fxml";
    private String registryScreen = "../Resources/Registro_Estudiante.fxml";
    private String studentMainMenu = "../Resources/MenuPrincipal_Estudiante.fxml";
    private String chooseProjectsScreen = "../Resources/EscogerProyectos_Estudiante.fxml";
    private String studentReportsScreen = "../Resources/Reportes_Estudiante.fxml";
    private String projectDetails = "../Resources/ProyectoAsignado_Estudiante.fxml";
    private String studentFormats = "../Resources/Formatos_Estudiante.fxml";
    private String studentAdditionalDocuments = "../Resources/DocumentosAdicionales_Estudiante.fxml";
    private String pantallaPrincipalCoordinador = "../Resources/Principal_Coordinador.fxml";
    private String pantallaGestionarOrganizacion = "../Resources/GestionarOrganizacion_Coordinador.fxml";
    private String pantallaRegistrarOrganizacion = "../Resources/RegistrarOrganizacion_Coordinador.fxml";
    private String pantallaGestionarEstudiantes = "../Resources/GestionarEstudiantes_Coordinador.fxml";
    private String pantallaGestionarProyecto = "../Resources/GestionarProyecto_Coordinador.fxml";
    private String pantallaModificarProyecto = "../Resources/ModificarProyecto_Coordinador.fxml";
    private String mainScreenDocente = "../Resources/Principal_Docente.fxml";
    private String descargarArchivoScreen = "../Resources/DescargarDocumento_Docente.fxml";
    private String validarArchivoScreen = "../Resources/ValidarInscripcion.fxml";
    private String consultarExpedienteDocenteScreen = "../Resources/ConsultarExpediente_Docente.fxml";
    private String reportarProblema_DocenteScreen = "../Resources/ReportarProblema_Docente.fxml";
    private String pantallaVisualizarReportes = "../Resources/VisualizarReporte_Coordinador.fxml";
    private String pantallaReporteSeleccionado = "../Resources/ReporteSeleccionado_Coordinador.fxml";
    private String pantallaModificarOrganizacion = "../Resources/ModificarOrganizacion_Coordinador.fxml";
    private String pantallaGestionarReporte = "../Resources/Reportes_Coodinador.fxml";
    private String pantallaModificarEstudiante = "../Resources/ModificarEstudiante_Coordinador.fxml";
    private String consultarExpedienteCoordinadorScreen = "../Resources/ConsultarExpediente.fxml";
    private String descargarArchivos_Coordinador = "../Resources/DescargarArchivo_Coordinador.fxml";
    private String eliminarArchivoConsulta_Docente = "../Resources/EliminarArchivosConsulta_Docente.fxml";
    private String menu_Coordinador = "../Resources/Menu_Coordinador.fxml";
    private String asignarProyectoAEstudianteScreen = "../Resources/AsignarProyectoAEstudiante.fxml";
    private String consultarOrganizacionScreen = "../Resources/ConsultarOrganizacion.fxml";
    private String consultarProyectoScreen = "../Resources/ConsultarProyecto.fxml";
    private String desasignarProyectoAEstudianteScreen = "../Resources/DesasignarProyectoAEstudiante.fxml";
    private String generarOficioScreen = "../Resources/GenerarOficioAsignacion.fxml";
    private String crearProyectoScreen = "../Resources/CrearProyecto.fxml";


    /**
     * Hace el cambio de pantalla a la pantalla de IniciarSesión.
     * @param mouseEvent el evento de mouse que inicio el cambio
     * @param errorText el campo de texto donde se coloca un mensaje en caso de error
     */
    public void ShowLoginScreen( MouseEvent mouseEvent, Text errorText ) {
        try {
            SetScene( mouseEvent, loginScreen );
        } catch( IOException exception ) {
            errorText.setText( outputMessages.LoginScreenMissing() );
        }
    }

    /**
     * Hace el cambio de pantalla a la pantalla de Registro_Estudiante.
     * @param mouseEvent el evento de mouse que inicio el cambio
     * @param errorText el campo de texto donde se coloca un mensaje en caso de error
     */
    public void ShowRegistryScreen( MouseEvent mouseEvent, Text errorText ) {
        try {
            SetScene( mouseEvent, registryScreen );
        } catch( IOException exception ) {
            errorText.setText( outputMessages.RegistryScreenMissing() );
        }
    }

    /**
     * Hace el cambio de pantalla a la pantalla del menú principal de
     * estudiante
     * @param mouseEvent el evento de mouse que inicio el cambio
     * @param errorText el campo de texto donde se coloca un mensaje en caso de error
     */
    public void ShowStudentMainMenuScreen( MouseEvent mouseEvent, Text errorText ) {
        try {
            SetScene( mouseEvent, studentMainMenu );
        } catch( IOException exception ) {
            errorText.setText( outputMessages.StudentMainMenuMissing() );
            exception.printStackTrace();
        }
    }

    /**
     * Hace el cambio de pantalla a la pantalla EscogerProyectos_Estudiante
     * @param mouseEvent el evento de mouse que inicio el cambio
     * @param errorText el campo de texto donde se coloca un mensaje en caso de error
     */
    public void ShowChooseProjectsScreen( MouseEvent mouseEvent, Text errorText ) {
        try {
            SetScene( mouseEvent, chooseProjectsScreen );
        } catch( IOException exception ) {
            errorText.setText( outputMessages.ChooseProjectsMissing() );
            exception.printStackTrace();
        }
    }

    /**
     * Hace el cambio de pantalla a la pantalla Reportes_Estudiante
     * @param mouseEvent el evento de mouse que inicio el cambio
     * @param errorText el campo de texto donde se coloca un mensaje en caso de error
     */
    public void ShowStudentReportsScreen( MouseEvent mouseEvent, Text errorText ) {
        try {
            SetScene( mouseEvent, studentReportsScreen );
        } catch( IOException exception ) {
            errorText.setText( outputMessages.StudentReportScreenMissing() );
            exception.printStackTrace();
        }
    }

    /**
     * Hace el cambio de pantalla a la pantalla DocumentosAdicionales_Estudiante
     * @param mouseEvent el evento de mouse que inicio el cambio
     * @param errorText el campo de texto donde se coloca un mensaje en caso de error
     */
    public void ShowStudentAdditionalDocumentsScreen( MouseEvent mouseEvent, Text errorText ) {
        try{
            SetScene( mouseEvent, studentAdditionalDocuments );
        } catch( IOException exception ) {
            errorText.setText( outputMessages.StudentAdditionalDocumentsMissing() );
            exception.printStackTrace();
        }
    }

    /**
     * Hace el cambio de pantalla a la pantalla ProyectoAsignado_Estudiante
     * @param mouseEvent el evento de mouse que inicio el cambio
     * @param errorText el campo de texto donde se coloca un mensaje en caso de error
     */
    public void ShowProjectDetailsScreen( MouseEvent mouseEvent, Text errorText ) {
        try {
            SetScene( mouseEvent, projectDetails );
        } catch( IOException exception ) {
            errorText.setText( outputMessages.ProjectDetailsMissing() );
            exception.printStackTrace();
        }
    }

    /**
     * Hace el cambio de pantalla a la pantalla Formatos_Estudiante
     * @param mouseEvent el evento de mouse que inicio el cambio
     * @param errorText el campo de texto donde se coloca un mensaje en caso de error
     */
    public void ShowStudentFormatsScreen( MouseEvent mouseEvent, Text errorText ) {
        try {
            SetScene( mouseEvent, studentFormats );
        } catch( IOException exception ) {
            errorText.setText( outputMessages.StudentFormatsMissing() );
            exception.printStackTrace();
        }
    }

    public void MostrarPantallaGestionarEstudianesCoordinador( MouseEvent mouseEvent, Text errorText  ) {
        try {
            SetScene( mouseEvent, pantallaGestionarEstudiantes );
        } catch( IOException exception ) {
            errorText.setText( outputMessages.PantallaGestionarEstudiantesPerdida() );
            exception.printStackTrace();
        }
    }

    /**
     * Hace el cambio de pantalla a la pantalla Principal_Coordinador
     *
     * @param mouseEvent el evento de mouse que inicio el cambio
     * @param errorText  el campo de texto donde se coloca un mensaje en caso de error
     */
    public void MostrarPantallaPrincipalCoordinador( MouseEvent mouseEvent, Text errorText ) {
        try {
            SetScene( mouseEvent, pantallaPrincipalCoordinador );
        } catch (IOException exception) {
            errorText.setText( outputMessages.PantallaPrincipalCoordinadorPerdido() );
            exception.printStackTrace();
        }
    }

    /**
     * Hace el cambio de pantalla a la pantalla Principal_Coordinador
     *
     * @param mouseEvent el evento de mouse que inicio el cambio
     * @param errorText  el campo de texto donde se coloca un mensaje en caso de error
     */
    public void MostrarPantallaMenuCoordinador( MouseEvent mouseEvent, Text errorText ) {
        try {
            SetScene( mouseEvent, menu_Coordinador );
        } catch (IOException exception) {
            errorText.setText( outputMessages.PantallaMenuCoordinadorPerdido() );
            exception.printStackTrace();
        }
    }

    /**
     * Hace el cambio de pantalla a la pantalla GestionarOrganizacion
     * @param mouseEvent el evento de mouse que inicio el cambio
     * @param errorText el campo de texto donde se coloca un mensaje en caso de error
     */
    public void MostrarPantallaGestionarOrganizacion( MouseEvent mouseEvent, Text errorText ){
        try {
            SetScene( mouseEvent, pantallaGestionarOrganizacion );
        } catch( IOException exception ) {
            errorText.setText( outputMessages.PantallaGestionarOrganizacionPerdido() );
            exception.printStackTrace();
        }
    }

    /**
     * Hace el cambio de pantalla a la pantalla ModificarOrganizacion_Coordinador
     * @param mouseEvent el evento de mouse que inicio el cambio
     * @param errorText el campo de texto donde se coloca un mensaje en caso de error
     */
    public void  MostrarPantallaModificarOrganizacion( MouseEvent mouseEvent, Text errorText ){
        try {
            SetScene( mouseEvent, pantallaModificarOrganizacion );
        } catch( IOException exception ) {
            errorText.setText( outputMessages.PantallaRegistrarOrganizacionPerdido() );
            exception.printStackTrace();
        }
    }

    /**
     * Hace el cambio de pantalla a la pantalla RegistrarOrganizacion_Coordinador
     * @param mouseEvent el evento de mouse que inicio el cambio
     * @param errorText el campo de texto donde se coloca un mensaje en caso de error
     */
    public void MostrarPantallaRegistrarOrganizacion( MouseEvent mouseEvent, Text errorText ){
        try {
            SetScene( mouseEvent, pantallaRegistrarOrganizacion );
        } catch( IOException exception ) {
            errorText.setText( outputMessages.PantallaRegistrarOrganizacionPerdido() );
            exception.printStackTrace();
        }
    }

    /**
     * Hace el cambio de pantalla a la pantalla GestionarProyecto
     * @param mouseEvent el evento de mouse que inicio el cambio
     * @param errorText el campo de texto donde se coloca un mensaje en caso de error
     */
    public void MostrarPantallaGestionarProyecto( MouseEvent mouseEvent, Text errorText ){
        try {
            SetScene( mouseEvent, pantallaGestionarProyecto );
        } catch( IOException exception ) {
            errorText.setText( outputMessages.PantallaGestionarProyectoPerdido() );
            exception.printStackTrace();
        }
    }

    /**
     * Hace el cambio de pantalla a la pantalla Reportes
     * @param mouseEvent el evento de mouse que inicio el cambio
     * @param errorText el campo de texto donde se coloca un mensaje en caso de error
     */
    public void MostrarPantallaGestionarReporteCoordinador( MouseEvent mouseEvent, Text errorText ){
        try {
            SetScene( mouseEvent, pantallaGestionarReporte );
        } catch( IOException exception ) {
            errorText.setText( outputMessages.PantallaReportesPerdido() );
            exception.printStackTrace();
        }
    }

    /**
     * Hace el cambio de la pantalla Principal_Coordinador a la pantalla VisualizarReporte_Coordinador
     * @param mouseEvent el evento de mouse que inicio el cambio
     * @param errorText el campo de texto donde se coloca un mensaje en caso de error
     */
    public void MostrarPantallaVisualizarReportesCoordinador( MouseEvent mouseEvent, Text errorText ) {
        try {
            SetScene(mouseEvent, pantallaVisualizarReportes);
        } catch (IOException exception) {
            errorText.setText(outputMessages.PantallaVisualizarReportePerdido());
            exception.printStackTrace();
        }
    }

    /**
     * Hace el cambio de la pantalla Principal_Coordinador a la pantalla ReporteSeleccionado_Coordinador
     * @param mouseEvent el evento de mouse que inicio el cambio
     * @param errorText el campo de texto donde se coloca un mensaje en caso de error
     */
    public void MostrarPantallaReporteSeleccionado( MouseEvent mouseEvent, Text errorText ) {
        try {
            SetScene(mouseEvent, pantallaReporteSeleccionado);
        } catch (IOException exception) {
            errorText.setText(outputMessages.PantallaReporteSeleccionadoPerdido());
            exception.printStackTrace();
        }
    }

    /**
     * Hace el cambio de pantalla a la pantalla principal del docente.
     * @param mouseEvent el evento de mouse que inicio el cambio
     * @param errorText el campo de texto donde se coloca un mensaje en caso de error
     */
    public void ShowScreenPrincipalDocente(MouseEvent mouseEvent, Text errorText ) {
        try {
            SetScene( mouseEvent, mainScreenDocente);
        } catch( IOException exception ) {
            errorText.setText( outputMessages.MainScreenDocenteMissing() );
            exception.printStackTrace();
        }
    }

    /**
     * Hace el cambio de pantalla a la pantalla GestionarProyecto
     * @param mouseEvent el evento de mouse que inicio el cambio
     * @param errorText el campo de texto donde se coloca un mensaje en caso de error
     */
    public void MostrarPantallaModificarProyecto( MouseEvent mouseEvent, Text errorText ){
        try {
            SetScene( mouseEvent, pantallaModificarProyecto );
        } catch( IOException exception ) {
            errorText.setText( outputMessages.PantallaModificarProyectoPerdido() );
            exception.printStackTrace();
        }
    }

    /**
     * Hace el cambio de pantalla a la pantalla ModificarEstudiante_Coordinador
     * @param mouseEvent el evento de mouse que inicio el cambio
     * @param errorText el campo de texto donde se coloca un mensaje en caso de error
     */
    public void MostrarPantallaModificarEstudiante( MouseEvent mouseEvent, Text errorText ){
        try {
            SetScene( mouseEvent, pantallaModificarEstudiante );
        } catch( IOException exception ) {
            errorText.setText( outputMessages.PantallaModificarEstudiante() );
            exception.printStackTrace();
        }
    }

    /**
     * Método utiliado en todos los cambios de pantalla.
     * @param mouseEvent el evento de mouse utilizado para conseguir la ventana actual
     * @param resourceName el nombre del archivo FXML de la pantalla deseada
     * @throws IOException ocurre cuando no se encuentra el archivo
     */
    private void SetScene( MouseEvent mouseEvent, String resourceName ) throws IOException {
        try {
            Parent newView = FXMLLoader.load( getClass().getResource( resourceName ) );
            Scene sceneView = new Scene( newView );
            Stage window = ( Stage )( ( Node )mouseEvent.getSource() ).getScene().getWindow();
            window.setScene( sceneView );
            window.show();
            window.setX( ( Screen.getPrimary().getBounds().getWidth() - window.getWidth() ) / 2 );
            window.setY( ( Screen.getPrimary().getBounds().getHeight() - window.getHeight() ) / 2 );
        } catch( IOException exception ) {
            throw exception;
        }
    }

    /**
     * Hace el cambio de pantalla a la pantalla de descargar archivos de un estudiante.
     * @param mouseEvent el evento de mouse que inicio el cambio
     * @param errorText el campo de texto donde se coloca un mensaje en caso de error
     */
    public void ShowScreenDescargarArchivoDocente( MouseEvent mouseEvent, Text errorText ) {
        try {
            SetScene( mouseEvent, descargarArchivoScreen);
        } catch( IOException exception ) {
            errorText.setText( outputMessages.DescargarArchivoScreenMissing() );
            exception.printStackTrace();
        }
    }

    public void ShowScreenValidarInscripcion( MouseEvent mouseEvent, Text errorText ) {
        try {
            SetScene( mouseEvent, validarArchivoScreen );
        }catch( IOException exception){
            errorText.setText( outputMessages.ValidarInscripcionScreenMissing() );
            exception.printStackTrace();
        }
    }

    public void ShowScreenConsultarExpediente( MouseEvent mouseEvent, Text errorText ) {
        try {
            SetScene( mouseEvent, consultarExpedienteDocenteScreen );
        }catch( IOException exception){
            errorText.setText( outputMessages.ConsultarExpedienteDocenteScreenMissing() );
            exception.printStackTrace();
        }
    }

    public void ShowScreenReportarProblema_Docente( MouseEvent mouseEvent, Text errorText ){
        try {
            SetScene( mouseEvent, reportarProblema_DocenteScreen );
        }catch( IOException exception){
            errorText.setText( outputMessages.ReportarProblemaScreenMissing() );
            exception.printStackTrace();
        }
    }

    public void ShowScreenConsultarExpediente_Coordinador(MouseEvent mouseEvent, Text errorText ) {
        try {
            SetScene( mouseEvent, consultarExpedienteCoordinadorScreen );
        }catch( IOException exception){
            errorText.setText( outputMessages.ConsultarExpedienteDocenteScreenMissing() );
            errorText.setText("");
            exception.printStackTrace();
        }
    }

    /**
     * Hace el cambio de pantalla a la pantalla de descargar archivos de un estudiante.
     * @param mouseEvent el evento de mouse que inicio el cambio
     * @param errorText el campo de texto donde se coloca un mensaje en caso de error
     */
    public void ShowScreenDescargarArchivoCoordinador( MouseEvent mouseEvent, Text errorText ) {
        try {
            SetScene( mouseEvent, descargarArchivos_Coordinador);
        } catch( IOException exception ) {
            errorText.setText( outputMessages.DescargarArchivoScreenMissing() );
            exception.printStackTrace();
        }
    }

    public void ShowScreenDeleteArchivosConsulta(MouseEvent mouseEvent, Text errorText) {
        try {
            SetScene( mouseEvent, eliminarArchivoConsulta_Docente);
        } catch( IOException exception ) {
            errorText.setText( outputMessages.EliminarArchivoScreenMissing() );
            exception.printStackTrace();
        }
    }

    /**
     * Hace el cambio de pantalla a la pantalla de asignar proyecto a
     * estudiante
     * @param mouseEvent el evento de mouse que inicio el cambio
     * @param errorText el campo de texto donde se coloca un mensaje en caso de error
     */
    public void ShowAsignarProyecto( MouseEvent mouseEvent, Text errorText ) {
        try {
            SetScene( mouseEvent, asignarProyectoAEstudianteScreen );
        } catch( IOException exception ) {
            errorText.setText( outputMessages.AsignarProyectoScreenMissing() );
            exception.printStackTrace();
        }
    }

    /**
     * Hace el cambio de pantalla a la pantalla de consultar organizacion
     * @param mouseEvent el evento de mouse que inicio el cambio
     * @param errorText el campo de texto donde se coloca un mensaje en caso de error
     */
    public void ShowConsultarOrganizacion( MouseEvent mouseEvent, Text errorText ) {
        try {
            SetScene( mouseEvent, consultarOrganizacionScreen );
        } catch( IOException exception ) {
            errorText.setText( outputMessages.ConsultarOrganizacionScreenMissing() );
            exception.printStackTrace();
        }
    }

    /**
     * Hace el cambio de pantalla a la pantalla de consultar proyecto
     * @param mouseEvent el evento de mouse que inicio el cambio
     * @param errorText el campo de texto donde se coloca un mensaje en caso de error
     */
    public void ShowConsultarProyecto( MouseEvent mouseEvent, Text errorText ) {
        try {
            SetScene( mouseEvent, consultarProyectoScreen );
        } catch( IOException exception ) {
            errorText.setText( outputMessages.ConsultarProyectoScreenMissing() );
            exception.printStackTrace();
        }
    }

    /**
     * Hace el cambio de pantalla a la pantalla desasignar proyecto.
     * @param mouseEvent el evento de mouse que inicio el cambio
     * @param errorText el campo de texto donde se coloca un mensaje en caso de error
     */
    public void ShowScreenDesasignarProyecto(MouseEvent mouseEvent, Text errorText ) {
        try {
            SetScene( mouseEvent, desasignarProyectoAEstudianteScreen);
        } catch( IOException exception ) {
            errorText.setText( outputMessages.MainScreenDocenteMissing() );
            exception.printStackTrace();
        }
    }

    /**
     * Hace el cambio de pantalla a la pantalla generar oficio de
     * asignacion para el estudiante
     * @param mouseEvent el evento de mouse que inicio el cambio
     * @param errorText el campo de texto donde se coloca un mensaje en caso de error
     */
    public void ShowGenerarOficio( MouseEvent mouseEvent, Text errorText ) {
        try {
            SetScene( mouseEvent, generarOficioScreen );
        } catch( IOException exception ) {
            errorText.setText( outputMessages.GenerarOficioAsignacionScreenMissing() );
            exception.printStackTrace();
        }
    }

    /**
     * Hace el cambio de pantalla a la pantalla de crear proyecto
     * @param mouseEvent el evento de mouse que inicio el cambio
     * @param errorText el campo de texto donde se coloca un mensaje en caso de error
     */
    public void ShowCrearProyecto( MouseEvent mouseEvent, Text errorText ) {
        try {
            SetScene( mouseEvent, crearProyectoScreen );
        } catch( IOException exception ) {
            errorText.setText( outputMessages.CrearProyectoScreenMissing() );
            exception.printStackTrace();
        }
    }


}