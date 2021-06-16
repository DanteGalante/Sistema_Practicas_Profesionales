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
import Database.ExpedienteDAO;
import Database.ProyectoDAO;
import Entities.Estudiante;
import Entities.Expediente;
import Entities.Proyecto;
import Enumerations.EstadoEstudiante;
import Enumerations.EstadoProyecto;
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
import java.util.List;
import java.util.ResourceBundle;

/**
 * Clase encargada de manejar los eventos de la pantalla
 * GestionarEstudiantes_Coordinador.
 */
public class GestionarEstudiantes_Coordinador implements Initializable {
    private EstudianteDAO estudiantes = new EstudianteDAO();
    private ExpedienteDAO expedientes = new ExpedienteDAO();
    private ProyectoDAO proyectos = new ProyectoDAO();
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
        ClearText();
        if( IsStudentSelected() ) {
            Alert deleteAlert = new Alert( Alert.AlertType.CONFIRMATION, outputMessages.EliminarEstudianteConfirmation() );
            deleteAlert.showAndWait().ifPresent( response -> {
                if( response == ButtonType.OK ) {
                    try {
                        estudiantes.Delete( estudiantesTable.getSelectionModel().getSelectedItem().getMatricula() );
                        UpdateProyectoCupo( proyectos.Read( GetUserExpediente( estudiantesTable.getSelectionModel().getSelectedItem() ).GetIDProyecto() ) );
                        UpdateEstadoExpedienteToDesactivado( estudiantesTable.getSelectionModel().getSelectedItem() );
                        successText.setText( outputMessages.DeleteStudentSuccessful() );
                        errorText.setText( "" );
                        ShowStudents();
                    } catch( Exception exception ) {
                        successText.setText( "" );
                        errorText.setText( outputMessages.DatabaseConnectionFailed2() );
                    }
                }
            } );
        }
    }

    /**
     * Metodo llamado al hacer clic en el boton modificar de la pantalla
     * @param event el evento de mouse que inicia el metodo
     */
    @FXML
    void ClicModificar( MouseEvent event){
        if ( estudiantesTable.getSelectionModel().getSelectedItem() != null ) {
            SelectionContainer.GetInstance().setEstudianteElegido( RecuperarEstudiante() );
            screenChanger.MostrarPantallaModificarEstudiante( event, errorText );
        } else {
            errorText.setText( outputMessages.SelectionStudentNull() );
        }
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

    private void ClearText() {
        errorText.setText( "" );
        successText.setText( "" );
    }

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

    /**
     * Busca el expediente del estudiante actual en la
     * base de datos
     * @return el expediente del estudiante actual
     */
    private Expediente GetUserExpediente( Estudiante estudiante ) {
        List< Expediente > expedientesUsuarios = expedientes.ReadAll();
        Expediente userExpediente = null;
        for( Expediente expediente : expedientesUsuarios ) {
            if( expediente.GetMatricula().equals( estudiante.getMatricula() ) &&
                    expediente.GetActivo() ) {
                userExpediente = expediente;
            }
        }
        return userExpediente;
    }

    private void UpdateEstadoExpedienteToDesactivado( Estudiante estudiante ) {
        expedientes.Update( GetExpedienteDesactivado( GetUserExpediente( estudiante ) ) );
    }

    private Expediente GetExpedienteDesactivado( Expediente currentExpediente ) {
        return new Expediente( currentExpediente.GetClave(), currentExpediente.GetIDProyecto(), currentExpediente.GetMatricula(),
                currentExpediente.GetFechaAsignacion(), currentExpediente.GetHorasAcumuladas(),
                currentExpediente.GetNumeroArchivos(), false );
    }

    private void UpdateProyectoCupo( Proyecto proyecto ) {
        proyectos.Update( GetProyectoCupoModificado( proyecto ) );
    }

    private Proyecto GetProyectoCupoModificado( Proyecto proyecto ) {
        int newCupo = 0;
        EstadoProyecto newEstado;
        if( proyecto.GetEstudiantesAsignados() == proyecto.getNumEstudiantesRequeridos() ) {
            newCupo = proyecto.GetEstudiantesAsignados() - 1;
            newEstado = EstadoProyecto.Disponible;
        } else {
            newCupo = proyecto.GetEstudiantesAsignados() - 1;
            newEstado = proyecto.GetEstado();
        }
        return new Proyecto( proyecto.getIdProyecto(), proyecto.getNombre(), proyecto.GetDescripcion(),
                             proyecto.getNumEstudiantesRequeridos(), newCupo, proyecto.GetFechaRegistro(), newEstado );
    }
}