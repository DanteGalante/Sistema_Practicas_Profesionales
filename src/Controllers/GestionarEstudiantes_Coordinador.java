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
import Utilities.OutputMessages;
import Utilities.ScreenChanger;
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
    private Text nameText;

    @FXML
    private Text lastNameText;

    @FXML
    private Text numeroTrabajadorText;

    @FXML
    private Button chooseProjectButton;

    @FXML
    private Button returnButton;

    @FXML
    private Text errorText;

    @FXML
    private TableView< Estudiante > estudiantesTable;

    @FXML
    private TableColumn< Estudiante, String > nameColumn;

    @FXML
    private TableColumn< Estudiante, String > matriculaColumn;

    @FXML
    private Button consultarBoton;

    @FXML
    private Button modificarBoton;

    @FXML
    private Button eliminarBoton;

    @FXML
    private Button buscarBoton;

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
        nameText.setText( LoginSession.GetInstance().GetCoordinador().getNombres() );
        lastNameText.setText( LoginSession.GetInstance().GetCoordinador().GetApellidos() );
        numeroTrabajadorText.setText( LoginSession.GetInstance().GetCoordinador().GetNumeroPersonal() );
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
        for( Estudiante estudiante : estudiantes.ReadAll() ) {
            estudiantesTable.getItems().add( estudiante );
        }
    }

    /**
     * Elimina un estudiante seleccionado de la tabla estudiantesTable
     */
    @FXML
    void EliminarEstudiante() {
        if( IsStudentSelected() ) {
            Alert deleteAlert = new Alert( Alert.AlertType.CONFIRMATION, outputMessages.DeleteDocumentConfirmation() );
            deleteAlert.showAndWait().ifPresent( response -> {
                if( response == ButtonType.OK ) {
                    estudiantes.Delete( estudiantesTable.getSelectionModel().getSelectedItem().getMatricula() );
                    ShowStudents();
                }
            } );
        }
    }

    @FXML
    void Return( MouseEvent event ) {
        screenChanger.MostrarPantallaPrincipalCoordinador( event, errorText );
    }

    @FXML
    void ShowGestionarReportes( MouseEvent event ) {

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
}