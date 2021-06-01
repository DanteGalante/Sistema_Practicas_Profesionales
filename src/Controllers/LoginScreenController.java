/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 31 - mar - 2021
 * Descripción:
 * Clase encargada de manejar los eventos de la pantalla
 * IniciarSesión.
 */
package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;
import Utilities.LoginSession;
import Database.CoordinadorDAO;
import Database.DocenteDAO;
import Database.EstudianteDAO;
import Entities.Coordinador;
import Entities.Docente;
import Entities.Estudiante;
import Utilities.ScreenChanger;
import Utilities.InputValidator;
import Utilities.OutputMessages;

/**
 * Clase encargada de manejar los eventos de la pantalla
 * IniciarSesión.
 */
public class LoginScreenController {
    private ScreenChanger screenChanger = new ScreenChanger();
    private InputValidator inputValidator = new InputValidator();
    private OutputMessages outputMessages = new OutputMessages();
    private CoordinadorDAO coordinadores = new CoordinadorDAO();
    private DocenteDAO docentes = new DocenteDAO();
    private EstudianteDAO estudiantes = new EstudianteDAO();
    private Coordinador coordinador = null;
    private Docente docente = null;
    private Estudiante estudiante = null;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Text register;

    @FXML
    private Text recoverPassword;

    @FXML
    private Text errorText;

    /**
     * Cambia la pantalla de IniciarSesión a la pantalla Registro_Estudiante
     * @param mouseEvent el evento de mouse que inicia el cambio
     */
    public void ShowRegistryScreen( MouseEvent mouseEvent ) {
        screenChanger.ShowRegistryScreen( mouseEvent, errorText );
    }

    /**
     * Intenta Realizar un login utilizando la información introducida
     * por el usuario
     * @param mouseEvent el evento de mouse que es utilizado para cambiar la pantalla
     */
    public void HandleLogin( MouseEvent mouseEvent ) {
        CheckUserInput();
        if( inputValidator.IsLoginInformationValid( usernameField.getText(), passwordField.getText() ) ) {
            if( DoesUsernameExist() && DoesPasswordMatchUserPassword() ) {
                Login( mouseEvent );
            } else {
                errorText.setText( outputMessages.InvalidLoginInformation() );
            }
        }
    }

    /**
     * Revisa si la contraseña introducida coincide con algún usuario
     * @return true sí la contraseña coincide con algún usuario, false si no
     */
    private boolean DoesPasswordMatchUserPassword() {
        return DoesPasswordMatchCoordinador() || DoesPasswordMatchDocente() || DoesPasswordMatchEstudiante();
    }

    /**
     * Revisa si la contraseña introducida coincide con el usuario de
     * tipo coordinador
     * @return true si coincide, false si no
     */
    private boolean DoesPasswordMatchCoordinador() {
        boolean passwordsMatch = false;
        if( coordinador != null && coordinador.GetContrasena().equals( passwordField.getText() ) ) {
            passwordsMatch = true;
        }
        return passwordsMatch;
    }

    /**
     * Revisa si la contraseña introducida coincide con el usuario de
     * tipo docente
     * @return true si coincide, false si no
     */
    private boolean DoesPasswordMatchDocente() {
        boolean passwordsMatch = false;
        if( docente != null && docente.GetContrasena().equals( passwordField.getText() ) ) {
            passwordsMatch = true;
        }
        return passwordsMatch;
    }

    /**
     * Revisa si la contraseña introducida coincide con el usuario de
     * tipo estudiante
     * @return true si coincide, false si no
     */
    private boolean DoesPasswordMatchEstudiante() {
        boolean passwordsMatch = false;
        if( estudiante != null && estudiante.GetContrasena().equals( passwordField.getText() ) ) {
            passwordsMatch = true;
        }
        return passwordsMatch;
    }

    /**
     * Intenta recuperar una instancia de usuario de cualquier tipo
     * utilizando la constraseña introducida por el usuario
     * @return true si se logra recuperar algún usuario
     */
    private boolean DoesUsernameExist() {
        coordinador = coordinadores.Read( usernameField.getText() );
        docente = docentes.Read( usernameField.getText() );
        estudiante = estudiantes.Read( usernameField.getText() );
        return coordinador != null || docente != null || estudiante != null;
    }

    /**
     * Inicia sesión y cambia la pantalla a la pantalla del usuario correspondiente
     */
    private void Login( MouseEvent mouseEvent ) {
        if( coordinador != null ) {
            LoginSession.GetInstance().Login( coordinador );
            screenChanger.MostrarPantallaPrincipalCoordinador( mouseEvent, errorText );
        } else if( docente != null ) {
            LoginSession.GetInstance().Login( docente );
        } else if( estudiante != null ) {
            LoginSession.GetInstance().Login( estudiante );
            screenChanger.ShowStudentMainMenuScreen( mouseEvent, errorText );
        }
    }

    /**
     * Revisa que toda la información introducida por el usuario
     * sea valida.
     */
    private void CheckUserInput() {
        CheckPassword();
        CheckNumeroPersonalMatricula();
    }

    /**
     * Revisa que la contraseña introducida sea valida
     */
    private void CheckPassword() {
        if( !inputValidator.IsPasswordValid( passwordField.getText() ) ) {
            errorText.setText( outputMessages.InvalidPassword() );
        }
    }

    /**
     * Revisa que el nombre de usuario introducido sea valido
     */
    private void CheckNumeroPersonalMatricula() {
        if( !inputValidator.IsMatriculaValid( usernameField.getText() ) ||
                !inputValidator.IsNumeroPersonalCoordinadorValid( usernameField.getText() ) ||
                !inputValidator.IsNumeroPersonalDocenteValid( usernameField.getText() ) ) {
            errorText.setText( outputMessages.InvalidUsername() );
        }
    }
}