/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 4 - abr - 2021
 * Descripción:
 * Clase encargada de manejar los eventos de la pantalla
 * EscogerProyectos_Estudiante.
 */
package Controllers;


import Database.EstudianteDAO;
import Database.ProyectoDAO;
import Database.ProyectosSeleccionadosDAO;
import Database.ProyectosDeResponsablesDAO;
import Database.ResponsablesOrganizacionDAO;
import Database.OrganizacionVinculadaDAO;
import Entities.Proyecto;
import Entities.OrganizacionVinculada;
import Entities.ConjuntoProyectoOrganizacion;
import Enumerations.EstadoEstudiante;
import Enumerations.EstadoProyecto;
import Utilities.OutputMessages;
import Utilities.ScreenChanger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import Utilities.LoginSession;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SelectProjectsController implements Initializable {
    private ScreenChanger screenChanger = new ScreenChanger();
    private OutputMessages outputMessages = new OutputMessages();
    private ProyectoDAO proyectos = new ProyectoDAO();
    private ProyectosDeResponsablesDAO proyectosResponsables = new ProyectosDeResponsablesDAO();
    private ResponsablesOrganizacionDAO responsablesOrganizacion = new ResponsablesOrganizacionDAO();
    private OrganizacionVinculadaDAO organizaciones = new OrganizacionVinculadaDAO();
    private EstudianteDAO estudiantes = new EstudianteDAO();
    private ProyectosSeleccionadosDAO proyectosSeleccionados = new ProyectosSeleccionadosDAO();
    private List< Proyecto > listaProyectos = new ArrayList< Proyecto >();
    private final int maxSelectedProjects = 3;

    @FXML
    private Text nameText;

    @FXML
    private Text lastNameText;

    @FXML
    private Text matriculaText;

    @FXML
    private Text projectText;

    @FXML
    private Text projectDetails;

    @FXML
    private Text errorText;

    @FXML
    private Text successText;

    @FXML
    private TableView< ConjuntoProyectoOrganizacion > availableProjectsTable;

    @FXML
    private TableView< ConjuntoProyectoOrganizacion > selectedProjectsTable;

    @FXML
    private TableColumn< ConjuntoProyectoOrganizacion, String > availableProjectName;

    @FXML
    private TableColumn< ConjuntoProyectoOrganizacion, String > availableTotalSpace;

    @FXML
    private TableColumn< ConjuntoProyectoOrganizacion, String > availableOrganization;

    @FXML
    private TableColumn< ConjuntoProyectoOrganizacion, String > chosenName;

    @FXML
    private TableColumn< Proyecto, String > chosenOrganization;

    @FXML
    private Button returnButton;

    @FXML
    private Button deleteProjectButton;

    @FXML
    private Button sendSelectedProjectsButton;

    @FXML
    private Button selectProjectButton;

    /**
     * Configura los elementos utilizados en la pantalla EscogerProyectos_Estudiante
     * @param url un url sin utilizar
     * @param resourceBundle un conjunto de recursos no utilizados
     */
    @Override
    public void initialize( URL url, ResourceBundle resourceBundle ) {
        SetUserInformation();
        SetTableCellValueFactory();
        ShowAvailableProjects();
    }

    /**
     * Se regresa al menú principal de Estudiante
     * @param mouseEvent el evento de mouse que invocó el método
     */
    @FXML
    public void Return( MouseEvent mouseEvent ) {
        screenChanger.ShowStudentMainMenuScreen( mouseEvent, errorText );
    }

    /**
     * Agrega un proyecto seleccionado de la tabla availableProjects a la tabla
     * selectedProjects
     */
    public void SelectProject() {
        ClearTextFields();
        int idProyecto = availableProjectsTable.getSelectionModel().getSelectedItem().getIdProyecto();
        for( Proyecto proyecto : listaProyectos ) {
            if( DoesSelectedProjectTableHaveSpace() && idProyecto == proyecto.getIdProyecto() &&
                    !IsProjectSelected( idProyecto ) ) {
                ConjuntoProyectoOrganizacion conjunto = new ConjuntoProyectoOrganizacion( proyecto,
                        organizaciones.Read( responsablesOrganizacion.ReadOrganizacion( proyectosResponsables.ReadResponsable( proyecto.getIdProyecto() ) ) ) );
                selectedProjectsTable.getItems().add( conjunto );
            }
        }
    }

    /**
     * Elimina un proyecto seleccionado de la tabla selectedProjects
     */
    public void RemoveProject() {
        ClearTextFields();
        selectedProjectsTable.getItems().remove( selectedProjectsTable.getSelectionModel().getSelectedItem() );
    }

    /**
     * Manda los proyectos seleccionados a la base de datos y actualiza el estado del estudiante
     */
    public void SendSelection() {
        ClearTextFields();
        if( Selected3Projects() ) {
            if( LoginSession.GetInstance().GetEstudiante().getEstado() != EstadoEstudiante.AsignacionPendiente ) {
                try {
                    proyectosSeleccionados.Create( LoginSession.GetInstance().GetEstudiante().getMatricula(), GetSelectedProjects() );
                    LoginSession.GetInstance().GetEstudiante().SetEstadoEstudiante( EstadoEstudiante.AsignacionPendiente );
                    estudiantes.Update( LoginSession.GetInstance().GetEstudiante() );
                    errorText.setText( "" );
                    successText.setText( outputMessages.ProjectSelectionSuccessful() );
                } catch( Exception exception ) {
                    successText.setText( "" );
                    errorText.setText( outputMessages.DatabaseConnectionFailed2() );
                }
            }
            else{
                errorText.setText( outputMessages.AlreadyChoseProjects() );
            }
        }
    }

    /**
     * Configura las columnas de las tablas availableProjects y selectedProjects
     */
    private void SetTableCellValueFactory() {
        availableProjectName.setCellValueFactory( new PropertyValueFactory<>( "nombreProyecto" ) );
        availableTotalSpace.setCellValueFactory( new PropertyValueFactory<>( "numEstudiantesRequeridos" ) );
        availableOrganization.setCellValueFactory( new PropertyValueFactory<>( "nombreOrganizacion" ) );
        chosenName.setCellValueFactory( new PropertyValueFactory<>( "nombreProyecto" ) );
        chosenOrganization.setCellValueFactory( new PropertyValueFactory<>( "nombreOrganizacion" ) );
    }

    /**
     * Agrega todos los proyectos disponibles a la tabla de proyectos disponibles
     */
    private void ShowAvailableProjects() {
        listaProyectos = proyectos.ReadAll();
        for( Proyecto proyecto : listaProyectos ) {
            if( IsProjectAvailable( proyecto ) ) {
                ConjuntoProyectoOrganizacion conjunto = new ConjuntoProyectoOrganizacion( proyecto,
                        organizaciones.Read( responsablesOrganizacion.ReadOrganizacion( proyectosResponsables.ReadResponsable( proyecto.getIdProyecto() ) ) ) );
                availableProjectsTable.getItems().add( conjunto );
            }
        }
    }

    /**
     * Coloca la información del usuario actual en los campos de texto de
     * nombre, apellidos y matrícula
     */
    private void SetUserInformation() {
        nameText.setText( LoginSession.GetInstance().GetEstudiante().getNombres() );
        lastNameText.setText( LoginSession.GetInstance().GetEstudiante().GetApellidos() );
        matriculaText.setText( LoginSession.GetInstance().GetEstudiante().getMatricula() );
    }

    /**
     * Revisa que el estado de un proyecto sea Disponible
     * @param proyecto el proyecto que se desea revisar
     * @return true si el estado del proyecto es igual a Disponible
     */
    private boolean IsProjectAvailable( Proyecto proyecto ) {
        return proyecto.GetEstado() == EstadoProyecto.Disponible;
    }

    /**
     * Revisa si un proyecto ya fue seleccionado por el usuario
     * @param idProyecto el id del proyecto que se quiere revisar
     * @return true si ya se encuentra en la tabla de proyectos seleccionados, false si no
     */
    private boolean IsProjectSelected( int idProyecto ) {
        boolean isProjectSelected = false;
        for( ConjuntoProyectoOrganizacion conjunto : selectedProjectsTable.getItems() ) {
            if( conjunto.getIdProyecto() == idProyecto ) {
                isProjectSelected = true;
            }
        }
        return isProjectSelected;
    }

    /**
     * Revisa si la aún se pueden agregar proyectos a la tabla de
     * proyectos seleccionados
     * @return booleano indicando si la tabla de proyectos seleccionados tiene cupo
     */
    private boolean DoesSelectedProjectTableHaveSpace(){
        boolean hasSpace = false;
        if( selectedProjectsTable.getItems().size() < maxSelectedProjects ){
            hasSpace = true;
        } else {
            successText.setText( "" );
            errorText.setText( outputMessages.AlreadySelectedMaxAmountProjects() );
        }
        return hasSpace;
    }

    /**
     * Revisa si el usuario ha seleccionado 3 proyectos
     * @return booleano indicando si se han seleccionado 3 proyectos
     */
    private boolean Selected3Projects() {
        boolean selected3Projects = false;
        if( selectedProjectsTable.getItems().size() == maxSelectedProjects ) {
            selected3Projects = true;
        } else {
            successText.setText( "" );
            errorText.setText( outputMessages.NotEnoughProjectsSelected() );
        }
        return selected3Projects;
    }

    /**
     * Regresa una lista con los IDs de la tabla de proyectos seleccionados
     * @return una lista de int con los IDs de los proyectos
     */
    private List< Integer > GetSelectedProjects() {
        List< Integer > idProyectos = new ArrayList<>();
        for( ConjuntoProyectoOrganizacion conjunto : selectedProjectsTable.getItems() ) {
            idProyectos.add( conjunto.getIdProyecto() );
        }
        return idProyectos;
    }

    /**
     * Muestra los detalles del proyecto seleccionado de la tabla de
     * proyectos disponibles.
     * @param mouseEvent el evento de mouse que invoca el método
     */
    @FXML
    private void ShowProjectDetails( MouseEvent mouseEvent ) {
        projectDetails.setText( availableProjectsTable.getSelectionModel().getSelectedItem().getDescripcion() );
    }

    private void ClearTextFields() {
        successText.setText( "" );
        errorText.setText( "" );
    }
}