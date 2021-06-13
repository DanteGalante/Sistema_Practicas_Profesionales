package Controllers;

import Database.OrganizacionVinculadaDAO;
import Database.ProyectoDAO;
import Database.ResponsableProyectoDAO;
import Entities.Proyecto;
import Entities.ResponsableProyecto;
import Enumerations.TipoSector;
import Entities.OrganizacionVinculada;
import Utilities.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ModificarOrganizacion_Coordinador implements Initializable {
    private OrganizacionVinculadaDAO organizacionVinculada = new OrganizacionVinculadaDAO();
    private OutputMessages outputMessages = new OutputMessages();
    private ScreenChanger screenChanger = new ScreenChanger();
    private InputValidator inputValidator = new InputValidator();
    private List<ResponsableProyecto> listaResponsables = new ArrayList<>();
    private List<Proyecto> listaProyectos = new ArrayList<>();
    private ProyectoDAO proyecto = new ProyectoDAO();
    private ResponsableProyectoDAO responsableProyecto = new ResponsableProyectoDAO();

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
    private Button btnModificar;

    @FXML
    private Text errorText;

    @FXML
    private Text successText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatosDeUsuario();
        DatosOrganizacion();
        DatosResponsable();
    }

    /**
     * Cambia la pantalla de RegistrarOrganizacion_Coordinador a GestionarOrganizacion_Coordinador.
     * @param mouseEvent el evento de mouse que activo la acción.
     */
    public void ClicRegresar ( MouseEvent mouseEvent ){
        screenChanger.MostrarPantallaGestionarOrganizacion( mouseEvent, errorText );
    }

    /**
     * Limpia los campos de datos de organización.
     * @param mouseEvent el evento de mouse que activo la acción.
     */
    public void ClicCancelar ( MouseEvent mouseEvent ){
        DatosOrganizacion();
        DatosResponsable();
    }

    public void ManejoModificarOrganizacion(){
        ManejoModificarRepresentante();
        VerificarDatos();
        //if( inputValidator.IsOrganizationInformationValid( ObtenerOrganizacionVinculada() ) ) {
        //if ( !OrganizacionExistente() ) {
        ModificarOrganizacion();
        //}
        //}
    }

    /**
     * Crea una instancia de Organizacion Vinculada utilizando la información
     * introducida por el usuario en todos los campos de texto.
     * @return una instancia de OrganizacionVinculada
     */
    private OrganizacionVinculada ObtenerOrganizacionVinculada() {
        return SelectionContainer.GetInstance().getOrganizacionElegida();
    }

    private ResponsableProyecto ObtenerResponsableProyecto() {
        return SelectionContainer.GetInstance().getResponsableElegido();
    }

    /**
     * Intenta crear una Organización en la base de datos y coloca
     * el mensaje correspondiente en caso de éxito o fracaso.
     */
    private void ModificarOrganizacion() {
        if( organizacionVinculada.Update ( organizacionModificada() ) ) { //Cambiar el metodo a el nuevo
            errorText.setText( "" );
            successText.setText( outputMessages.ModificacionOrganizacionExitoso() );
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
    /*private boolean OrganizacionExistente() {
        boolean organizacionExistente = false;
        if( organizacionVinculada.ReadNombre( ObtenerOrganizacionVinculada().getIdOrganizacion() ) != null ) { //Cambiar el DAO el READ esta mal
            errorText.setText( outputMessages.OrganizacionExistente() );
            successText.setText( "" );
            organizacionExistente = true;
        }
        return organizacionExistente;
    }*/ //Volver a poner hasta que se revise ES EL -------------READNOMBRE----------------------

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
    private void NombreValido() {
        if ( !inputValidator.AreNamesValid(tfNombre.getText() ) ) {
            errorText.setText(outputMessages.InvalidNames() );
            successText.setText( "" );
        }
    }

    /**
     * Revisa que la dirección introducida sea valida.
     */
    private void DireccionValida() {
        if ( !inputValidator.DireccionValida( tfDireccion.getText() ) ) {
            errorText.setText( outputMessages.DireccionInvalida() );
            successText.setText( "" );
        }
    }

    /**
     * Revisa que el correo eléctronico introducido sea valido.
     */
    private void CorreoValido() {
        if ( !inputValidator.IsEmailValid( tfCorreoElectronico.getText() ) ) {
            errorText.setText( outputMessages.InvalidEmail() );
            successText.setText( "" );
        }
    }

    /**
     * Revisa que el télefono introducido sea valido.
     */
    private void TelefonoValido() {
        if ( !inputValidator.IsTelephoneValid( tfTelefono.getText() ) ) {
            errorText.setText( outputMessages.InvalidTelephone() );
            successText.setText( "" );
        }
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
     * Coloca la información de la OrganizacionVinculada seleccionada en los campos de texto de
     * nombres, apellidos y No.Trabajador
     */
    public void DatosOrganizacion(){
        tfNombre.setText( SelectionContainer.GetInstance().getOrganizacionElegida().getNombre() );
        tfDireccion.setText( SelectionContainer.GetInstance().getOrganizacionElegida().getDireccion() );
        tfCorreoElectronico.setText( SelectionContainer.GetInstance().getOrganizacionElegida().getCorreo() );
        tfTelefono.setText( SelectionContainer.GetInstance().getOrganizacionElegida().getTelefono() );
    }

    /**
     * Coloca la información del usuario actual en los campos de texto de
     * nombres, apellidos y No.Trabajador
     */
    public void DatosResponsable(){
        tfNombresRepresentante.setText( SelectionContainer.GetInstance().getResponsableElegido().GetNombres() );
        tfApellidosRepresentante.setText( SelectionContainer.GetInstance().getResponsableElegido().GetApellidos() );
        tfCorreoRepresentante.setText( SelectionContainer.GetInstance().getResponsableElegido().GetCorreo() );
        tfTelefonoRepresentante.setText( SelectionContainer.GetInstance().getResponsableElegido().GetTelefono() );
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
    private void ModificarResponsableProyecto() {
        if( responsableProyecto.Update ( responsableModificado() ) ) {
            errorText.setText( "" );
            successText.setText( outputMessages.ModificacionResponsableExitoso() );
        }
        else {
            errorText.setText( outputMessages.DatabaseConnectionFailed() );
            successText.setText( "" );
        }
    }

    public void ManejoModificarRepresentante(){
        VerificarDatosResponsable();
        //if( inputValidator.IsResponsableInformationValid( ObtenerResponsableProyecto() ) ) {
        //if ( !ResponsableExistente() ) {
        ModificarResponsableProyecto();
        //}
        //}
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
    private void NombresResponsableValido() {
        if ( !inputValidator.AreNamesValid(tfNombresRepresentante.getText() ) ) {
            errorText.setText(outputMessages.InvalidNames() );
            successText.setText( "" );
        }
    }

    /**
     * Revisa que la dirección introducida sea valida.
     */
    private void ApellidosResponsableValido() {
        if ( !inputValidator.DireccionValida( tfApellidosRepresentante.getText() ) ) {
            errorText.setText( outputMessages.DireccionInvalida() );
            successText.setText( "" );
        }
    }

    /**
     * Revisa que el correo eléctronico introducido sea valido.
     */
    private void CorreoResponsableValido() {
        if ( !inputValidator.IsEmailValid( tfCorreoRepresentante.getText() ) ) {
            errorText.setText( outputMessages.InvalidEmail() );
            successText.setText( "" );
        }
    }

    /**
     * Revisa que el télefono introducido sea valido.
     */
    private void TelefonoResponsableValido() {
        if ( !inputValidator.IsTelephoneValid( tfTelefonoRepresentante.getText() ) ) {
            errorText.setText( outputMessages.InvalidTelephone() );
            successText.setText( "" );
        }
    }

    private OrganizacionVinculada organizacionModificada(){
        return new OrganizacionVinculada ( tfNombre.getText(), tfDireccion.getText(), TipoSector.Publico,
                tfTelefono.getText(),tfCorreoElectronico.getText(),RecuperarIdOrganizacion(),ObtenerListaResponsables(), true);
    }

    public List ObtenerListaResponsables(){
        listaResponsables = responsableProyecto.ReadAll();
        for( ResponsableProyecto responsableProyecto : listaResponsables) {
            responsableProyecto.getIdResponsableProyecto();
        }
        return listaResponsables;
    }

    private ResponsableProyecto responsableModificado(){
        return new ResponsableProyecto ( RecuperarIdResponsable(), tfNombresRepresentante.getText(), tfApellidosRepresentante.getText(),
                tfCorreoRepresentante.getText(), tfTelefonoRepresentante.getText(), ObtenerListaProyectos() );
    }

    public int RecuperarIdResponsable(){
        int idProyecto = SelectionContainer.GetInstance().getResponsableElegido().getIdResponsableProyecto();
        return idProyecto;
    }

    public List ObtenerListaProyectos(){
        listaProyectos = proyecto.ReadAll();
        System.out.println("Proyectos:"+ listaProyectos);
        return listaResponsables;
    }

    public int RecuperarIdOrganizacion(){
        int idOrganizacion = SelectionContainer.GetInstance().getOrganizacionElegida().getIdOrganizacion();
        return idOrganizacion;
    }
}
