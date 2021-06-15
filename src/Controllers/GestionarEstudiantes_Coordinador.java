/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 30 - abr - 2021
 * Descripción:
 * Clase encargada de manejar los eventos de la pantalla
 * GestionarEstudiantes_Coordinador.
 */
package Controllers;

import Database.EstudianteDAO;
import Entities.Estudiante;
import Enumerations.EstadoEstudiante;
import Utilities.OutputMessages;
import Utilities.ScreenChanger;
import Utilities.SelectionContainer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import Utilities.LoginSession;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase encargada de manejar los eventos de la pantalla
 * GestionarEstudiantes_Coordinador.
 */
public class GestionarEstudiantes_Coordinador implements Initializable {
    private EstudianteDAO estudiantes = new EstudianteDAO();
    private OutputMessages outputMessages = new OutputMessages();
    private ScreenChanger screenChanger = new ScreenChanger();

    @FXML
    private Label lbNombres;

    @FXML
    private Label lbApellidos;

    @FXML
    private Label lbNoTrabajador;

    @FXML
    private Text errorText;

    @FXML
    private Text successText;

    @FXML
    private TableView< Estudiante > estudiantesTable;

    @FXML
    private TableColumn< Estudiante, String > nameColumn;

    @FXML
    private TableColumn< Estudiante, String > matriculaColumn;

    @FXML
    private Button modificarBoton;

    @FXML
    private Button eliminarBoton;

    @FXML
    private Button asignarBoton;

    @FXML
    private Button desasignarProyecto;

    @FXML
    private Button consultarExpedienteBoton;

    @FXML
    private Button generarOficioBoton;


    /**
     * Configura los componentes de la pantalla GestionarEstudiantes_Coordinador
     * @param url no se utiliza, lo requiere la interfaz
     * @param resourceBundle no se utiliza, lo requiere la interfaz
     */
    @Override
    public void initialize( URL url, ResourceBundle resourceBundle ) {
        SetUserInformation();
        SetCellValueFactory();
        ShowStudents();
    }

    /**
     * Coloca la información del usuario actual en los campos de texto
     * nameText, lastNameText y matriculaText
     */
    private void SetUserInformation() {
        lbNombres.setText( LoginSession.GetInstance().GetCoordinador().getNombres() );
        lbApellidos.setText( LoginSession.GetInstance().GetCoordinador().GetApellidos() );
        lbNoTrabajador.setText( LoginSession.GetInstance().GetCoordinador().GetNumeroPersonal() );
    }

    /**
     * Configura las columnas de la tabla mostrada en esta pantalla
     */
    private void SetCellValueFactory() {
        nameColumn.setCellValueFactory( new PropertyValueFactory<>( "nombres" ) );
        matriculaColumn.setCellValueFactory( new PropertyValueFactory<>( "matricula" ) );
    }

    /**
     * Muestra todos los estudiantes almacenados en el sistema en la tabla
     * de la pantalla
     */
    private void ShowStudents() {
        estudiantesTable.getItems().clear();
        try {
            for( Estudiante estudiante : estudiantes.ReadAll() ) {
                if( estudiante.getEstado() != EstadoEstudiante.Eliminado && estudiante.getEstado() != EstadoEstudiante.RegistroPendiente ) {
                    estudiantesTable.getItems().add( estudiante );
                }
            }
        } catch( Exception exception ) {
            errorText.setText( outputMessages.DatabaseConnectionFailed2() );
        }
    }

    /**
     * Elimina un estudiante seleccionado de la tabla estudiantesTable
     */
    @FXML
    void EliminarEstudiante() {
        ClearErrorText();
        if( IsStudentSelected() ) {
            Alert deleteAlert = new Alert( Alert.AlertType.CONFIRMATION, outputMessages.DeleteDocumentConfirmation() );
            deleteAlert.showAndWait().ifPresent( response -> {
                if( response == ButtonType.OK ) {
                    try {
                        estudiantes.Delete( estudiantesTable.getSelectionModel().getSelectedItem().getMatricula() );
                        ShowStudents();
                    } catch( Exception exception ) {
                        errorText.setText( outputMessages.DatabaseConnectionFailed2() );
                    }
                }
            } );
        }
    }

    @FXML
    void ClicModificar( MouseEvent event){
        SelectionContainer.GetInstance().setEstudianteElegido( RecuperarEstudiante() );
        screenChanger.MostrarPantallaModificarEstudiante( event, errorText );
    }

    /**
     * Permite cambiar la pantalla a la pantalla AsignarProyectoAEstudiante
     */
    @FXML
    void HandleAsignar( MouseEvent event ){
        screenChanger.ShowAsignarProyecto ( event, errorText );
    }

    /**
     * Permite cambiar la pantalla a la pantalla consultar al seleccionar un estudiante con expediente
     */
    @FXML
    void HandleConsultarExpediente ( MouseEvent event ) {
        if (estudiantesTable.getSelectionModel().getSelectedItem() != null) {
            Estudiante estudiante = (Estudiante) estudiantesTable.getSelectionModel().getSelectedItem();
            if(estudiante.getEstado().ordinal() == 0 || estudiante.getEstado().ordinal() == 4 || estudiante.getEstado().ordinal() == 5 ){
                SelectionContainer.GetInstance().setEstudianteElegido(estudiante);
                screenChanger.ShowScreenConsultarExpediente_Coordinador( event, errorText);
            }else{
                errorText.setText(outputMessages.StudentExpedienteNull());
            }
        }else{
            errorText.setText(outputMessages.SelectionStudentNull());
        }
    }

    /**
     * Permite cambiar la pantalla a la pantalla AsignarProyectoAEstudiante
     */
    public void MostrarPantallaGenerarOficio( MouseEvent mouseEvent ) {
        screenChanger.ShowGenerarOficio( mouseEvent, errorText );
    }

    /**
     * Permite cambiar la pantalla a la pantalla DesasignarProyectoAEstudiante
     */
    @FXML
    void MostrarPantallaDesasignarProyecto( MouseEvent event ){
        screenChanger.ShowScreenDesasignarProyecto ( event, errorText );
    }

    /**
     * Revisa si un estudiante ha sido seleccionado de la tabla
     * estudiantesTable
     * @return true si un elemento ha sido seleccionado de la tabla, false si no
     */
    private boolean IsStudentSelected() {
        boolean isSelected = false;
        if( estudiantesTable.getSelectionModel().getSelectedItem() != null ) {
            isSelected = true;
        }
        return isSelected;
    }

    private void ClearErrorText() { errorText.setText( "" ); }

    public Estudiante RecuperarEstudiante(){
        return estudiantesTable.getSelectionModel().getSelectedItem();
    }

    /**
     * Permite cambiar la pantalla a la pantalla GestionarEstudiante
     */
    public void MostrarPantallaGestionarEstudiante( MouseEvent mouseEvent ) {
        screenChanger.MostrarPantallaGestionarEstudianesCoordinador( mouseEvent, errorText );
    }

    /**
     * Permite cambiar la pantalla a la pantalla GestionarReportes
     */
    public void MostrarPantallaGestionarReporte( MouseEvent mouseEvent ) {
        screenChanger.MostrarPantallaGestionarReporteCoordinador( mouseEvent, errorText );
    }

    /**
     * Permite cambiar la pantalla a la pantalla GestionarReportes
     */
    public void MostrarPantallaGestionarOrganizacion( MouseEvent mouseEvent ) {
        screenChanger.MostrarPantallaGestionarOrganizacion( mouseEvent, errorText );
    }

    /**
     * Permite cambiar la pantalla a la pantalla GestionarReportes
     */
    public void MostrarPantallaGestionarProyecto( MouseEvent mouseEvent ) {
        screenChanger.MostrarPantallaGestionarProyecto( mouseEvent, errorText );
    }

    /**
     * Cierra la sesión actual y se regresa a la pantalla "IniciarSesión"
     * @param mouseEvent el evento de mouse que inicio el cambio
     */
    public void CerrarSesion( MouseEvent mouseEvent ) {
        LoginSession.GetInstance().Logout();
        screenChanger.ShowLoginScreen( mouseEvent, errorText );
    }

    /**
     * Permite cambiar a la pantalla ValidarInscripcion
     * @param mouseEvent evento del mouse que inicio el cambio
     */
    public void ClicValidarInscripcion(MouseEvent mouseEvent) {
        screenChanger.ShowScreenValidarInscripcion( mouseEvent, errorText );
    }
}