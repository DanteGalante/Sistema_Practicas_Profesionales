/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 31 - mar - 2021
 * Descripción:
 * Clase que se encarga de manejar las acciones de la pantalla
 * Registro_Estudiantes del sistema de prácticas profesionales.
 */
package Controllers;

import Enumerations.EstadoEstudiante;
import javafx.fxml.FXML;
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

/**
 * Clase que se encarga de manejar las acciones de la pantalla
 * Registro_Estudiantes del sistema de prácticas profesionales.
 */
public class RegistryScreenController {
    private ScreenChanger screenChanger = new ScreenChanger();
    private InputValidator inputValidator = new InputValidator();
    private OutputMessages outputMessages = new OutputMessages();
    private EstudianteDAO estudiantes = new EstudianteDAO();

    @FXML
    private Button registerButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField matriculaField;

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

    /**
     * Cambia la pantalla de Registro_Estudiante a IniciarSesión.
     * @param mouseEvent el evento de mouse que activo la acción.
     */
    public void ShowLoginScreen( MouseEvent mouseEvent ) {
        screenChanger.ShowLoginScreen( mouseEvent, errorText );
    }

    /**
     * Realiza el registro de un nuevo Estudiante en la base de datos.
     */
    public void HandleStudentRegistration() {
        CheckUserInput();
        if( inputValidator.IsStudentInformationValid( GetStudent(), confirmPasswordField.getText() ) ) {
            if( !DoesStudentExist() ) {
                RegisterStudent();
            }
        }
    }

    /**
     * Revisa si ya existe un Estudiante en la base de datos con
     * la misma información que fue introducida.
     * @return true si se encuentra una instancia con la misma información, false sí no.
     */
    private boolean DoesStudentExist() {
        boolean doesStudentExist = false;
        if( estudiantes.Read( GetStudent().getMatricula() ) != null ) {
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
    private void RegisterStudent() {
        if( estudiantes.Create( GetStudent() ) ) {
            errorText.setText( "" );
            successText.setText( outputMessages.RegistrationSuccessful() );
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
    private void CheckUserInput() {
        DoPasswordsMatch();
        CheckPassword();
        CheckMatricula();
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
     * Revisa que la matrícula introducida sea valida.
     */
    private void CheckMatricula() {
        if( !inputValidator.IsMatriculaValid( matriculaField.getText() ) ) {
            errorText.setText( outputMessages.InvalidMatricula() );
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
    private Estudiante GetStudent() {
        return new Estudiante( 0, nameField.getText(), lastNameField.getText(), "", passwordField.getText(),
                emailField.getText(), phoneField.getText(), matriculaField.getText(), nrcField.getText(),
                EstadoEstudiante.RegistroPendiente );
    }
}