/*
* Autor: Dan Javier Olvera Villeda
* Versión: 1.0
* Fecha Creación: 30 - abr - 2021
* Descripción:
* Clase encargada de manejar los eventos de la pantalla
* ValidarInscripcion.
*/
package Controllers;

import Entities.Estudiante;
import Utilities.LoginSession;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase que se encarga de manejar los eventos de la pantalla ValidarInscripcion
 * del Sistema de Practicas Profesionales
 */
public class ValidarInscripcion implements Initializable {
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbApellidos;
    @FXML
    private Label lbCedulaProfesional;
    @FXML
    private Text errorText;
    @FXML
    private Label lbPeriodo;
    @FXML
    private Label lbFecha;
    @FXML
    private TableView< Estudiante > tbvEstudiantes;
    @FXML
    private TableColumn< Estudiante, String > tcNombre;
    @FXML
    private TableColumn< Estudiante, String> tcMatricula;
    @FXML
    private TableColumn< Estudiante, CheckBox> tcValidar;
    @FXML
    private TableColumn< Estudiante, CheckBox> tcDepurar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Coloca la informacion del usuario actual en las etiquetas. Se coloca nombres, apellidos,
     * y numero personal.
     */
    public void SetUsuario() {
        lbNombre.setText(LoginSession.GetInstance().GetDocente().getNombres());
        lbApellidos.setText(LoginSession.GetInstance().GetDocente().GetApellidos());
        lbCedulaProfesional.setText(LoginSession.GetInstance().GetDocente().GetNumeroPersonal());
    }
}