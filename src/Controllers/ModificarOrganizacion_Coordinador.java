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
    private ResponsableProyectoDAO responsableProyectoId = new ResponsableProyectoDAO();
    private ResponsableProyecto responsable = null;
    private OrganizacionVinculada organizacion = null;

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

    /**
     * Coloca la información del usuario actual en los campos de texto
     * nameText, lastNameText y matriculaText
     */
    private void DatosDeUsuario() {
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
        tfNombresRepresentante.setText( SelectionContainer.GetInstance().getResponsableElegido().getNombres() );
        tfApellidosRepresentante.setText( SelectionContainer.GetInstance().getResponsableElegido().GetApellidos() );
        tfCorreoRepresentante.setText( SelectionContainer.GetInstance().getResponsableElegido().GetCorreo() );
        tfTelefonoRepresentante.setText( SelectionContainer.GetInstance().getResponsableElegido().GetTelefono() );
    }

    /**
     * Se encarga de modificar la organizacion vinculada y el representante de proyecto
     * en la base de datos
     */
    public void ManejoModificarOrganizacion() {
        ClearMessages();
        if( VerificarDatosOrganizacion() && VerificarDatosRepresentante() ) {
            GetOrganizacionYResponsable();
            if( !OrganizacionNoHaCambiado() && !ResponsableNoHaCambiado() ) {
                try {
                    if( ModificarOrganizacion() && ModificarResponsableProyecto() ) {
                        errorText.setText( "" );
                        successText.setText( outputMessages.OrganizacionYRepresentanteUpdateSuccess() );
                    }
                } catch( Exception exception ) {
                    errorText.setText( outputMessages.DatabaseConnectionFailed() );
                    successText.setText( "" );
                }
            } else if (!OrganizacionNoHaCambiado() && ResponsableNoHaCambiado() ) {
                try{
                    ModificarOrganizacion();
                    errorText.setText( "" );
                    successText.setText( outputMessages.OrganizacionUpdateSuccess() );
                }catch ( Exception exception ) {
                    errorText.setText( outputMessages.DatabaseConnectionFailed() );
                    successText.setText("");
                }
            } else if ( OrganizacionNoHaCambiado() && !ResponsableNoHaCambiado() ) {
                try{
                    ModificarResponsableProyecto();
                    errorText.setText( "" );
                    successText.setText( outputMessages.RepresentanteUpdateSuccess() );
                }catch ( Exception exception) {
                    errorText.setText( outputMessages.DatabaseConnectionFailed() );
                    successText.setText("");
                }
            }
        }
    }

    /**
     * Intenta crear una Organización en la base de datos y coloca
     * el mensaje correspondiente en caso de éxito o fracaso.
     */
    private boolean ModificarOrganizacion() {
        return organizacionVinculada.Update ( organizacion );
    }

    /**
     * Intenta crear un ResponsableProyecto en la base de datos y coloca
     * el mensaje correspondiente en caso de éxito o fracaso.
     */
    private boolean ModificarResponsableProyecto() {
        return responsableProyecto.Update ( responsable );
    }

    /**
     * Revisa si ya existe un Estudiante en la base de datos con
     * la misma información que fue introducida.
     * @return true si se encuentra una instancia con la misma información, false sí no.
     */
    private boolean ResponsableNoHaCambiado() {
        boolean responsableNoHaCambiado = false;
        if( responsableProyecto.Read(responsable.getIdResponsableProyecto() ).getNombres().equals( responsable.getNombres() ) &&
        responsableProyecto.Read( responsable.getIdResponsableProyecto() ).GetApellidos().equals( responsable.GetApellidos() ) &&
        responsableProyecto.Read( responsable.getIdResponsableProyecto() ).GetCorreo().equals( responsable.GetCorreo() ) &&
        responsableProyecto.Read( responsable.getIdResponsableProyecto() ).GetTelefono().equals( responsable.GetTelefono() ) ) {
            errorText.setText( outputMessages.ResponsableExistente() );
            successText.setText( "" );
            responsableNoHaCambiado = true;
        }
        return responsableNoHaCambiado;
    }

    private boolean OrganizacionNoHaCambiado() {
        boolean organizacionNoHaCambiado = false;

        if(  organizacionVinculada.Read( organizacion.getIdOrganizacion() ).getNombre().equals( organizacion.getNombre() ) &&
        organizacionVinculada.Read( organizacion.getIdOrganizacion() ).getDireccion().equals( organizacion.getDireccion() ) &&
        organizacionVinculada.Read(organizacion.getIdOrganizacion() ).getTelefono().equals( organizacion.getTelefono() ) &&
        organizacionVinculada.Read(organizacion.getIdOrganizacion() ).getCorreo().equals( organizacion.getCorreo() ) ) {
            errorText.setText( outputMessages.OrganizacionExistente() );
            successText.setText( "" );
            organizacionNoHaCambiado = true;
        }
        return organizacionNoHaCambiado;
    }

    /**
     * Crea una oraganizacion vinculada y representante de proyecto y los
     * guarda en sus variables correspondientes.
     */
    private void GetOrganizacionYResponsable() {
        organizacion = ObtenerOrganizacionVinculada();
        responsable = ObtenerResponsableProyecto();
    }

    /**
     * Crea una instancia de Organizacion Vinculada utilizando la información
     * introducida por el usuario en todos los campos de texto.
     * @return una instancia de OrganizacionVinculada
     */
    private OrganizacionVinculada ObtenerOrganizacionVinculada() {
        return new OrganizacionVinculada ( tfNombre.getText(), tfDireccion.getText(),
                SelectionContainer.GetInstance().getOrganizacionElegida().getSector(),
                tfTelefono.getText(),tfCorreoElectronico.getText(),
                SelectionContainer.GetInstance().getOrganizacionElegida().getIdOrganizacion(),
                SelectionContainer.GetInstance().getOrganizacionElegida().getResponsables(),
                SelectionContainer.GetInstance().getOrganizacionElegida().getActiveStatus(),
                SelectionContainer.GetInstance().getOrganizacionElegida().GetKey() );
    }

    /**
     * Crea una instancia de Organizacion Vinculada utilizando la información
     * introducida por el usuario en todos los campos de texto.
     * @return una instancia de ResponsableProyecto
     */
    private ResponsableProyecto ObtenerResponsableProyecto() {
        ResponsableProyecto responsableProyectoAux;
        responsableProyectoAux = RecuperarReposnsable();
        return new ResponsableProyecto ( responsableProyectoAux.getIdResponsableProyecto(),
                tfNombresRepresentante.getText(), tfApellidosRepresentante.getText(),
                tfCorreoRepresentante.getText(), tfTelefonoRepresentante.getText(),
                responsableProyectoAux.getIdProyectos(),
                responsableProyectoAux.GetKey());
    }

    private ResponsableProyecto RecuperarReposnsable(){
        return responsableProyectoId.Read( SelectionContainer.GetInstance().getResponsableElegido().getIdResponsableProyecto());
    }

    /**
     * Verifica que la información introducida por el usuario
     * sea valida.
     */
    private boolean VerificarDatosOrganizacion() {
        return IsNombreOrganizacionValid() && IsDireccionOrganizacionValida() && IsCorreoOrganizacionValid() &&
                IsTelefonoOrganizacionValid();
    }

    /**
     * Verifica que la informacionn introducida del representante
     * sea valida
     * @return
     */
    private boolean VerificarDatosRepresentante() {
        return AreNombresRepresentanteValid() && AreApellidosRepresentanteValid() && IsCorreoRepresentanteValid() &&
                IsTelefonoRepresentanteValid();
    }

    /**
     * Revisa que el nombre introducido sea valido.
     */
    private boolean IsNombreOrganizacionValid() {
        boolean valido = true;
        if ( !inputValidator.AreNamesValid( tfNombre.getText() ) ) {
            errorText.setText( outputMessages.InvalidOrganizationName() );
            successText.setText( "" );
            valido = false;
        }
        return valido;
    }

    /**
     * Revisa que la dirección introducida sea valida.
     */
    private boolean IsDireccionOrganizacionValida() {
        boolean valido = true;
        if ( !inputValidator.DireccionValida( tfDireccion.getText() ) ) {
            errorText.setText( outputMessages.InvalidOrganizationDirection() );
            successText.setText( "" );
            valido = false;
        }
        return valido;
    }

    /**
     * Revisa que el correo eléctronico introducido sea valido.
     */
    private boolean IsCorreoOrganizacionValid() {
        boolean valido = true;
        if ( !inputValidator.IsEmailValid( tfCorreoElectronico.getText() ) ) {
            errorText.setText( outputMessages.InvalidOrganizationEmail() );
            successText.setText( "" );
            valido = false;
        }
        return valido;
    }

    /**
     * Revisa que el télefono introducido sea valido.
     */
    private boolean IsTelefonoOrganizacionValid() {
        boolean valido = true;
        if ( !inputValidator.IsTelephoneValid( tfTelefono.getText() ) ) {
            errorText.setText( outputMessages.InvalidOrganizationPhone() );
            successText.setText( "" );
            valido = false;
        }
        return valido;
    }

    /**
     * Revisa que el nombre introducido sea valido.
     */
    private boolean AreNombresRepresentanteValid() {
        boolean valido = true;
        if ( !inputValidator.AreNamesValid( tfNombresRepresentante.getText() ) ) {
            errorText.setText( outputMessages.InvalidRepresentativeName() );
            successText.setText( "" );
            valido = false;
        }
        return valido;
    }

    /**
     * Revisa que la dirección introducida sea valida.
     */
    private boolean AreApellidosRepresentanteValid() {
        boolean valido = true;
        if ( !inputValidator.AreLastNamesValid( tfApellidosRepresentante.getText() ) ) {
            errorText.setText( outputMessages.InvalidRepresentativeLastNames() );
            successText.setText( "" );
            valido = false;
        }
        return valido;
    }

    /**
     * Revisa que el correo eléctronico introducido sea valido.
     */
    private boolean IsCorreoRepresentanteValid() {
        boolean valido = true;
        if ( !inputValidator.IsEmailValid( tfCorreoRepresentante.getText() ) ) {
            errorText.setText( outputMessages.InvalidRepresentativeEmail() );
            successText.setText( "" );
            valido = false;
        }
        return valido;
    }

    /**
     * Revisa que el télefono introducido sea valido.
     */
    private boolean IsTelefonoRepresentanteValid() {
        boolean valido = true;
        if ( !inputValidator.IsTelephoneValid( tfTelefonoRepresentante.getText() ) ) {
            errorText.setText( outputMessages.InvalidRepresentativePhone() );
            successText.setText( "" );
            valido = false;
        }
        return valido;
    }

    /**
     * Limpia los mensajes de error o exito al usuario
     */
    private void ClearMessages() {
        errorText.setText( "" );
        successText.setText( "" );
    }
}
