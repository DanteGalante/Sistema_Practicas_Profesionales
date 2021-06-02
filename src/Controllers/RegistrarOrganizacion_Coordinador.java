package Controllers;

import Database.OrganizacionVinculadaDAO;
import Enumerations.TipoSector;
import Entities.OrganizacionVinculada;
import Utilities.InputValidator;
import Utilities.OutputMessages;
import Utilities.ScreenChanger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistrarOrganizacion_Coordinador implements Initializable {
    private OrganizacionVinculadaDAO organizacionVinculada = new OrganizacionVinculadaDAO();
    private OutputMessages outputMessages = new OutputMessages();
    private ScreenChanger screenChanger = new ScreenChanger();
    private InputValidator inputValidator = new InputValidator();

    @FXML
    private Label lbNombres;

    @FXML
    private Label lbApellidos;

    @FXML
    private Label lbNoTrabajador;

    @FXML
    private Button btnRegresar;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfDireccion;

    @FXML
    private TextField tfCorreoElectronico;

    @FXML
    private TextField tfTelefono;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnRegistrar;

    @FXML
    private Text errorText;

    @FXML
    private Text successText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
 /*       VerificarDatos();
        DatosRegistro();
        RegistrarOrganizacion();   */
    }

    /*

    private OrganizacionVinculada DatosRegistro(){

        return new OrganizacionVinculada ( tfNombre.getText(), tfDireccion.getText(), TipoSector.Publico,
                tfTelefono.getText(),tfCorreoElectronico.getText(), organizacionVinculada.getIdResponsable(),
                organizacionVinculada.getIdProyecto());
    }

     */

    /**
     * Cambia la pantalla de Registro_Estudiante a IniciarSesión.
     * @param mouseEvent el evento de mouse que activo la acción.
     */
    public void ClicRegresar ( MouseEvent mouseEvent ){
        screenChanger.MostrarPantallaGestionarOrganizacion( mouseEvent, errorText );
    }

    /**
     * Intenta crear un Estudiante en la base de datos y coloca
     * el mensaje correspondiente en caso de éxito o fracaso.
     */

    /*
    private void RegistrarOrganizacion() {
        if( organizacionVinculada.Create (new OrganizacionVinculada()) ) {
            errorText.setText( "" );
            successText.setText( outputMessages.RegistroOrganizacionExitoso() );
        }
        else {
            errorText.setText( outputMessages.DatabaseConnectionFailed() );
            successText.setText( "" );
        }
    }

     */

    /**
     * Verifica que la información introducida por el usuario
     * sea valida.
     */

    /*
    private void VerificarDatos(){
        NombreValido();
    }

     */

    /**
     * Revisa que los nombres introducidos sean validos.
     */
    /*
    private void NombreValido() {
        if (!inputValidator.AreNamesValid(tfNombre.getText())) {
            errorText.setText(outputMessages.InvalidNames());
            successText.setText("");
        }
    }

    */
}