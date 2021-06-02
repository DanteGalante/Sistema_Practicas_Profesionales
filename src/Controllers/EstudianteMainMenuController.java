/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 4 - abr - 2021
 * Descripción:
 * Clase encargada de manejar los eventos de la pantalla
 * MenuPrincipal_Estudiante.
 */
package Controllers;

import Database.ExpedienteDAO;
import Database.ProyectoDAO;
import Entities.Expediente;
import Enumerations.EstadoEstudiante;
import Enumerations.EstadoProyecto;
import Utilities.OutputMessages;
import Utilities.ScreenChanger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import Utilities.LoginSession;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Clase encargada de manejar los eventos de la pantalla
 * MenuPrincipal_Estudiante.
 */
public class EstudianteMainMenuController implements Initializable {
    private ScreenChanger screenChanger = new ScreenChanger();
    private OutputMessages outputMessages = new OutputMessages();
    private ProyectoDAO proyectos = new ProyectoDAO();
    private ExpedienteDAO expedientes = new ExpedienteDAO();

    @FXML
    private Text nameText;

    @FXML
    private Text lastNameText;

    @FXML
    private Text matriculaText;

    @FXML
    private Text projectText;

    @FXML
    private Text errorText;

    @FXML
    private Button reportButton;

    @FXML
    private Button DocumentsButton;

    @FXML
    private Button formButton;

    @FXML
    private Button projectButton;

    @FXML
    private Button chooseProjectButton;

    @FXML
    private Button logoutButton;

    /**
     * Configura los componentes de la pantalla
     * @param location no se utiliza como tal, lo requiere la interfaz
     * @param resources no se utiliza como tal, lo requiere la interfaz
     */
    @Override
    public void initialize( URL location, ResourceBundle resources ) {
        nameText.setText( LoginSession.GetInstance().GetEstudiante().getNombres() );
        lastNameText.setText( LoginSession.GetInstance().GetEstudiante().GetApellidos() );
        matriculaText.setText( LoginSession.GetInstance().GetEstudiante().getMatricula() );
        SetProjectName();
    }

    /**
     * Cambia a la pantalla Reportes_Estudiante
     * @param mouseEvent el evento de mouse que inicio el cambio
     */
    public void ShowReports( MouseEvent mouseEvent ){
        if( DoesStudentHaveProjectAssigned() ) {
            screenChanger.ShowStudentReportsScreen(mouseEvent, errorText);
        } else {
            errorText.setText( outputMessages.ProjectNotAssigned() );
        }
    }

    /**
     * Cambia a la pantalla DocumentosAdicionales_Estudiante
     * @param mouseEvent el evento de mouse que inicio el cambio
     */
    public void ShowAdditionalDocuments( MouseEvent mouseEvent ) {
        if( DoesStudentHaveProjectAssigned() ) {
            screenChanger.ShowStudentAdditionalDocumentsScreen( mouseEvent, errorText );
        } else {
            errorText.setText( outputMessages.ProjectNotAssigned() );
        }
    }

    /**
     * Cambia a la pantalla Formatos_Estudiante
     * @param mouseEvent el evento de mouse que inicio el cambio
     */
    public void ShowFormats( MouseEvent mouseEvent ) {
        if( DoesStudentHaveProjectAssigned() ) {
            screenChanger.ShowStudentFormatsScreen( mouseEvent, errorText );
        } else {
            errorText.setText( outputMessages.StudentFormatsMissing() );
        }
    }

    /**
     * Cambia a la pantalla ProyectoAsignado_Estudiante
     * @param mouseEvent el evento de mouse que inicio el cambio
     */
    public void ShowAssignedProject( MouseEvent mouseEvent) {
        if( DoesStudentHaveProjectAssigned() ) {
            screenChanger.ShowProjectDetailsScreen( mouseEvent, errorText );
        } else {
            errorText.setText( outputMessages.ProjectNotAssigned() );
        }
    }

    /**
     * Cambia a la pantalla EscogerProyectos_Estudiante
     * @param mouseEvent el evento de mouse que inicio el cambio
     */
    public void ShowChooseProjects( MouseEvent mouseEvent ) {
        if( !HasStudentChosenProjects() ) {
            screenChanger.ShowChooseProjectsScreen( mouseEvent, errorText );
        } else {
            errorText.setText( outputMessages.AlreadyChoseProjects() );
        }
    }

    /**
     * Cierra la sesión actual y se regresa a la pantalla "IniciarSesión"
     * @param mouseEvent el evento de mouse que inicio el cambio
     */
    public void Logout( MouseEvent mouseEvent ) {
        LoginSession.GetInstance().Logout();
        screenChanger.ShowLoginScreen( mouseEvent, errorText );
    }

    /**
     * Revisa si un estudiante ha escogido 3 proyectos para la asignación
     * @return true sí ya tiene 3 proyectos seleccionados, false si no
     */
    private boolean HasStudentChosenProjects() {
        return LoginSession.GetInstance().GetEstudiante().GetEstado() == EstadoEstudiante.AsignacionPendiente ||
                LoginSession.GetInstance().GetEstudiante().GetEstado() == EstadoEstudiante.ProyectoAsignado ||
                LoginSession.GetInstance().GetEstudiante().GetEstado() == EstadoEstudiante.Evaluado;
    }

    /**
     * Revisa si el estudiante actual tiene un proyecto asignado
     * @return true si sí tiene un proyecto asignado, false si no
     */
    private boolean DoesStudentHaveProjectAssigned() {
        return LoginSession.GetInstance().GetEstudiante().GetEstado() == EstadoEstudiante.ProyectoAsignado ||
                LoginSession.GetInstance().GetEstudiante().GetEstado() == EstadoEstudiante.Evaluado;
    }

    /**
     * Recupera el proyecto asignado del usuario y coloca su nombre en el
     * campo de texto projectText
     */
    private void SetProjectName() {
        if( DoesStudentHaveProjectAssigned() ) {
            projectText.setText( proyectos.Read( GetUserExpediente().GetIDProyecto() ).getNombre() );
        }
    }

    /**
     * Recupera el expediente del usuario actual
     * @return una instancia del expediente
     */
    private Expediente GetUserExpediente() {
        List< Expediente > expedienteList = expedientes.ReadAll();
        Expediente userExpediente = null;
        for( Expediente expediente : expedienteList ) {
            if( expediente.GetMatricula().equals( LoginSession.GetInstance().GetEstudiante().getMatricula() ) &&
                    proyectos.Read( expediente.GetIDProyecto() ).GetEstado() == EstadoProyecto.Asignado ) {
                userExpediente = expediente;
            }
        }
        return userExpediente;
    }
}