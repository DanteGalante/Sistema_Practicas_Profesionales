package Controllers;

import Database.OrganizacionVinculadaDAO;
import Database.ResponsableProyectoDAO;
import Database.ResponsablesOrganizacionDAO;
import Entities.ResponsableProyecto;
import Enumerations.TipoSector;
import Entities.OrganizacionVinculada;
import Utilities.InputValidator;
import Utilities.LoginSession;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RegistrarOrganizacion_Coordinador implements Initializable {
    private OrganizacionVinculadaDAO organizacionVinculada = new OrganizacionVinculadaDAO();
    private OutputMessages outputMessages = new OutputMessages();
    private ScreenChanger screenChanger = new ScreenChanger();
    private InputValidator inputValidator = new InputValidator();
    private List<ResponsableProyecto> listaResponsables = new ArrayList<>();
    private ResponsableProyectoDAO responsableProyecto = new ResponsableProyectoDAO();
    private ResponsablesOrganizacionDAO responsablesOrganizacion = new ResponsablesOrganizacionDAO();
    private List<Integer> listaResponsablesProyecto = new ArrayList<>();

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
    private TextField tfNombresRepresentante;

    @FXML
    private TextField tfApellidosRepresentante;

    @FXML
    private TextField tfCorreoRepresentante;

    @FXML
    private TextField tfTelefonoRepresentante;

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
        DatosDeUsuario();
    }

    /**
     * Cambia la pantalla de Registro_Estudiante a IniciarSesión.
     * @param mouseEvent el evento de mouse que activo la acción.
     */
    public void ClicRegresar ( MouseEvent mouseEvent ){
        screenChanger.MostrarPantallaGestionarOrganizacion( mouseEvent, errorText );
    }

    public void ClicCancelar ( MouseEvent mouseEvent ){

        tfNombre.setText("");
        tfDireccion.setText("");
        tfCorreoElectronico.setText("");
        tfTelefono.setText("");
        tfNombresRepresentante.setText("");
        tfApellidosRepresentante.setText("");
        tfCorreoRepresentante.setText("");
        tfTelefonoRepresentante.setText("");
    }

    public void ManejoRegistroOrganizacion(){
        if(ManejoRegistroRepresentante()){
            VerificarDatos();
            if( NombreValido() == true && DireccionValida() == true && CorreoValido() == true && TelefonoValido() == true ){
                if( inputValidator.IsOrganizationInformationValid( ObtenerOrganizacionVinculada() ) ) {
                    if ( !OrganizacionExistente() ) {
                        RegistrarOrganizacion();
                        listaResponsablesProyecto.clear();
                        listaResponsablesProyecto.add(ObtenerResponsableProyecto().getIdResponsableProyecto());
                        responsablesOrganizacion.Create( ObtenerOrganizacionVinculada().getIdOrganizacion(),listaResponsablesProyecto );
                    }
                }
            }
        }
    }

    public List ObtenerListaResponsables(){
        listaResponsables = responsableProyecto.ReadAll();
        for( ResponsableProyecto responsableProyecto : listaResponsables) {
            responsableProyecto.getIdResponsableProyecto();
        }
        return listaResponsables;
    }

    /**
     * Crea una instancia de Organizacion Vinculada utilizando la información
     * introducida por el usuario en todos los campos de texto.
     * @return una instancia de OrganizacionVinculada
     */
    private OrganizacionVinculada ObtenerOrganizacionVinculada() {
        return new OrganizacionVinculada ( tfNombre.getText(), tfDireccion.getText(), TipoSector.Publico,
                tfTelefono.getText(),tfCorreoElectronico.getText(),0,ObtenerListaResponsables(), true);

    }

    /**
     * Intenta crear una Organización en la base de datos y coloca
     * el mensaje correspondiente en caso de éxito o fracaso.
     */
    private void RegistrarOrganizacion() {
        if( organizacionVinculada.Create ( ObtenerOrganizacionVinculada() ) ) {
            errorText.setText( "" );
            successText.setText( outputMessages.RegistroOrganizacionExitoso() );
        }
        else {
            errorText.setText( outputMessages.DatabaseConnectionFailed() );
            successText.setText( "" );
        }
    }

    /**
     * Revisa si ya existe un Estudiante en la base de datos con
     * la misma información que fue introducida.
     * @return true si se encuentra una instancia con la misma información, false sí no.
     */
    private boolean OrganizacionExistente() {
        boolean organizacionExistente = false;
        if( organizacionVinculada.Read( ObtenerOrganizacionVinculada().getIdOrganizacion() ) != null ) { //Cambiar el DAO el READ esta mal
            errorText.setText( outputMessages.OrganizacionExistente() );
            successText.setText( "" );
            organizacionExistente = true;
        }
        return organizacionExistente;
    }

    /**
     * Verifica que la información introducida por el usuario
     * sea valida.
     */
    private void VerificarDatos() {
        NombreValido();
        DireccionValida();
        CorreoValido();
        TelefonoValido();
    }

    /**
     * Revisa que el nombre introducido sea valido.
     */
    private boolean NombreValido() {
        boolean valido = false;
        if ( !inputValidator.AreNamesValid(tfNombre.getText() ) ) {
            errorText.setText(outputMessages.InvalidNames() );
            successText.setText( "" );
            valido = false;
        }else{
            valido = true;
        }
        return valido;
    }

    /**
     * Revisa que la dirección introducida sea valida.
     */
    private boolean DireccionValida() {
        boolean valido = false;
        if ( !inputValidator.DireccionValida( tfDireccion.getText() ) ) {
            errorText.setText( outputMessages.DireccionInvalida() );
            successText.setText( "" );
        }else{
            valido = true;
        }
        return valido;
    }

    /**
     * Revisa que el correo eléctronico introducido sea valido.
     */
    private boolean CorreoValido() {
        boolean valido = false;
        if ( !inputValidator.IsEmailValid( tfCorreoElectronico.getText() ) ) {
            errorText.setText( outputMessages.InvalidEmail() );
            successText.setText( "" );
            valido = false;
        }else{
            valido = true;
        }
        return valido;
    }

    /**
     * Revisa que el télefono introducido sea valido.
     */
    private boolean TelefonoValido() {
        boolean valido = false;
        if ( !inputValidator.IsTelephoneValid( tfTelefono.getText() ) ) {
            errorText.setText( outputMessages.InvalidTelephone() );
            successText.setText( "" );
            valido = false;
        }else{
            valido = true;
        }
        return valido;
    }

    /**
     * Coloca la información del usuario actual en los campos de texto de
     * nombres, apellidos y No.Trabajador
     */
    public void DatosDeUsuario(){
        lbNombres.setText( LoginSession.GetInstance().GetCoordinador().getNombres() );
        lbApellidos.setText( LoginSession.GetInstance().GetCoordinador().GetApellidos() );
        lbNoTrabajador.setText( LoginSession.GetInstance().GetCoordinador().GetNumeroPersonal() );
    }

    /**
     * Crea una instancia de Organizacion Vinculada utilizando la información
     * introducida por el usuario en todos los campos de texto.
     * @return una instancia de OrganizacionVinculada
     */
    private ResponsableProyecto ObtenerResponsableProyecto() {
        return new ResponsableProyecto ( 0, tfNombresRepresentante.getText(), tfApellidosRepresentante.getText(),
                tfCorreoRepresentante.getText(), tfTelefonoRepresentante.getText(), null );
    }

    /**
     * Revisa si ya existe un Estudiante en la base de datos con
     * la misma información que fue introducida.
     * @return true si se encuentra una instancia con la misma información, false sí no.
     */
    private boolean ResponsableExistente() {
        boolean responsableExistente = false;
        if( responsableProyecto.Read( ObtenerResponsableProyecto().getIdResponsableProyecto() ) != null ) { //Cambiar el DAO el READ esta mal
            errorText.setText( outputMessages.ResponsableExistente() );
            successText.setText( "" );
            responsableExistente = true;
        }
        return responsableExistente;
    }

    /**
     * Intenta crear un ResponsableProyecto en la base de datos y coloca
     * el mensaje correspondiente en caso de éxito o fracaso.
     */
    private void RegistrarResponsableProyecto() {
        try{
            if( responsableProyecto.Create ( ObtenerResponsableProyecto() ) ) {
                errorText.setText( "" );
                successText.setText( outputMessages.RegistroResponsableExitoso() );
            }
        }catch (Exception e){
            errorText.setText( outputMessages.DatabaseConnectionFailed() );
            successText.setText( "" );
        }
    }

    public boolean ManejoRegistroRepresentante(){
        boolean creado = false;
        VerificarDatosResponsable();
        if(NombresResponsableValido() == true && ApellidosResponsableValido() == true && CorreoResponsableValido() == true && TelefonoResponsableValido() == true ){
            if( inputValidator.IsResponsableInformationValid( ObtenerResponsableProyecto() ) ) {
                if ( !ResponsableExistente() ) {
                RegistrarResponsableProyecto();
                creado = true;
                }
            }
        }
        return creado;
    }

    private void VerificarDatosResponsable() {
        NombresResponsableValido();
        ApellidosResponsableValido();
        CorreoResponsableValido();
        TelefonoResponsableValido();
    }

    /**
     * Revisa que el nombre introducido sea valido.
     */
    private boolean NombresResponsableValido() {
        boolean valido = false;
        if ( !inputValidator.AreNamesValid(tfNombresRepresentante.getText() ) ) {
            errorText.setText(outputMessages.InvalidNames() );
            successText.setText( "" );
        }else{
            valido = true;
        }
        return valido;
    }

    /**
     * Revisa que la dirección introducida sea valida.
     */
    private boolean ApellidosResponsableValido() {
        boolean valido = false;
        if ( !inputValidator.AreLastNamesValid( tfApellidosRepresentante.getText() ) ) {
            errorText.setText( outputMessages.InvalidLastNames() );
            successText.setText( "" );
        }else{
            valido = true;
        }
        return valido;
    }

    /**
     * Revisa que el correo eléctronico introducido sea valido.
     */
    private boolean CorreoResponsableValido() {
        boolean valido = false;
        if ( !inputValidator.IsEmailValid( tfCorreoRepresentante.getText() ) ) {
            errorText.setText( outputMessages.InvalidEmail() );
            successText.setText( "" );
            valido = false;
        }else{
            valido = true;
        }
        return valido;
    }

    /**
     * Revisa que el télefono introducido sea valido.
     */
    private boolean TelefonoResponsableValido() {
        boolean valido = false;
        if ( !inputValidator.IsTelephoneValid( tfTelefonoRepresentante.getText() ) ) {
            errorText.setText( outputMessages.InvalidTelephone() );
            successText.setText( "" );
        }else{
            valido = true;
        }
        return valido;
    }
}