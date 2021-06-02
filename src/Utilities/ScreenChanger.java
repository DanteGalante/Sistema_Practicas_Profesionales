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
}