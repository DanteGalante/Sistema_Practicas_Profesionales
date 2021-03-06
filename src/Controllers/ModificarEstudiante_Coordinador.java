package Controllers;

import Enumerations.EstadoEstudiante;
import Utilities.SelectionContainer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import Database.EstudianteDAO;
import Entities.Estudiante;
import Utilities.ScreenChanger;
import Utilities.InputValidator;
import Utilities.OutputMessages;

import java.net.URL;
import java.util.ResourceBundle;

public class ModificarEstudiante_Coordinador implements Initializable {
    private ScreenChanger screenChanger = new ScreenChanger();
    private InputValidator inputValidator = new InputValidator();
    private OutputMessages outputMessages = new OutputMessages();
    private EstudianteDAO estudiantes = new EstudianteDAO();

    @FXML
    private Button modificarButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField nrcField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Text errorText;

    @FXML
    private Text successText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatosEstudiante();
    }

    /**
     * Realiza la modificación de un Estudiante en la base de datos.
     */
    public void ManejoRegistroEstudiante() {
        ValidarDatos();
        if( inputValidator.IsStudentInformationValid( ObtenerEstudiante(), confirmPasswordField.getText() ) ) {
            if( !DoesStudentExist() ) {
                try{
                    ModificarEstudiante();
                }catch (Exception exception) {
                    errorText.setText( outputMessages.DatabaseConnectionFailed2() );
                }
            }
        }
    }

    public void ClicCancelar(MouseEvent event){
        screenChanger.MostrarPantallaGestionarEstudianesCoordinador( event, errorText );
    }

    /**
     * Revisa si ya existe un Estudiante en la base de datos con
     * la misma información que fue introducida.
     * @return true si se encuentra una instancia con la misma información, false sí no.
     */
    private boolean DoesStudentExist() {
        boolean doesStudentExist = false;
        Estudiante estudiante = SelectionContainer.GetInstance().getEstudianteElegido();

        if( estudiante.getNombres().equals(ObtenerEstudiante().getNombres()) && estudiante.GetApellidos().equals(ObtenerEstudiante().GetApellidos()) &&
        estudiante.GetKey().equals(ObtenerEstudiante().GetKey()) && estudiante.GetContrasena().equals(ObtenerEstudiante().GetContrasena()) &&
        estudiante.GetCorreo().equals(ObtenerEstudiante().GetCorreo()) && estudiante.GetTelefono().equals(ObtenerEstudiante().GetTelefono()) &&
        estudiante.getEstado() == ObtenerEstudiante().getEstado() && estudiante.getProyecto().equals(ObtenerEstudiante().getProyecto())){
            errorText.setText( outputMessages.StudentAlreadyExists() );
            successText.setText( "" );
            doesStudentExist = true;
        }
        return doesStudentExist;
    }

    /**
     * Intenta crear un Estudiante en la base de datos y coloca
     * el mensaje correspondiente en caso de éxito o fracaso.
     */
    private void ModificarEstudiante() {
        if( estudiantes.Update( ObtenerEstudiante() ) ) {
            errorText.setText( "" );
            successText.setText( outputMessages.ModificacionEstudianteExitoso() );
        }
        else {
            errorText.setText( outputMessages.DatabaseConnectionFailed() );
            successText.setText( "" );
        }
    }

    /**
     * Revisa que toda la información introducida por el usuario
     * sea valida.
     */
    private void ValidarDatos() {
        DoPasswordsMatch();
        CheckPassword();
        CheckEmail();
        CheckTelephone();
        CheckNRC();
        CheckLastNames();
        CheckNames();
    }

    /**
     * Revisa que los nombres introducidos sean validos.
     */
    private void CheckNames() {
        if( !inputValidator.AreNamesValid( nameField.getText() ) ) {
            errorText.setText( outputMessages.InvalidNames() );
            successText.setText( "" );
        }
    }

    /**
     * Revisa que los apellidos introducidos sean validos.
     */
    private void CheckLastNames() {
        if( !inputValidator.AreLastNamesValid( lastNameField.getText() ) ) {
            errorText.setText( outputMessages.InvalidLastNames() );
            successText.setText( "" );
        }
    }

    /**
     * Revisa que el NRC introducido sea valido.
     */
    private void CheckNRC() {
        if( !inputValidator.IsNRCValid( nrcField.getText() ) ) {
            errorText.setText( outputMessages.InvalidNRC() );
            successText.setText( "" );
        }
    }

    /**
     * Revisa que el correo electrónico introducido sea valido.
     */
    private void CheckEmail() {
        if( !inputValidator.IsEmailValid( emailField.getText() ) ) {
            errorText.setText( outputMessages.InvalidEmail() );
            successText.setText( "" );
        }
    }

    /**
     * Revisa que el teléfono introducido sea valido.
     */
    private void CheckTelephone() {
        if( !inputValidator.IsTelephoneValid( phoneField.getText() ) ) {
            errorText.setText( outputMessages.InvalidTelephone() );
            successText.setText( "" );
        }
    }

    /**
     * Revisa que la contraseña introducida sea valida.
     */
    private void CheckPassword() {
        if( !inputValidator.IsPasswordValid( passwordField.getText() ) ) {
            errorText.setText( outputMessages.InvalidPassword() );
            successText.setText( "" );
        }
    }

    /**
     * Revisa que las contraseñas introducidas coincidan.
     */
    private void DoPasswordsMatch() {
        if( !inputValidator.DoPasswordsMatch( passwordField.getText(), confirmPasswordField.getText() ) ) {
            errorText.setText( outputMessages.PasswordsDontMatch() );
            successText.setText( "" );
        }
    }

    /**
     * Crea una instancia de Estudiante utilizando la información
     * introducida por el usuario en todos los campos de texto.
     * @return una instancia de Estudiante
     */
    private Estudiante ObtenerEstudiante() {
        return new Estudiante( RecuperarId(), nameField.getText(), lastNameField.getText(), SelectionContainer.GetInstance().getEstudianteElegido().GetKey(), passwordField.getText(),
                emailField.getText(), phoneField.getText(), SelectionContainer.GetInstance().getEstudianteElegido().getMatricula(), nrcField.getText(),
                SelectionContainer.GetInstance().getEstudianteElegido().getEstado(), SelectionContainer.GetInstance().getEstudianteElegido().getProyecto());
    }

    public void DatosEstudiante(){
        nameField.setText( SelectionContainer.GetInstance().getEstudianteElegido().getNombres() );
        lastNameField.setText( SelectionContainer.GetInstance().getEstudianteElegido().GetApellidos() );
        nrcField.setText( SelectionContainer.GetInstance().getEstudianteElegido().getNrc() );
        phoneField.setText( SelectionContainer.GetInstance().getEstudianteElegido().GetTelefono() );
        emailField.setText( SelectionContainer.GetInstance().getEstudianteElegido().GetCorreo() );
    }

    public int RecuperarId(){
        int id = SelectionContainer.GetInstance().getEstudianteElegido().GetID();
        return id;
    }
}

