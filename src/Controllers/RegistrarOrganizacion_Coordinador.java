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
import java.util.Random;
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
    private Random randomGenerator = new Random();
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
        ClearMessages();
        tfNombre.setText("");
        tfDireccion.setText("");
        tfCorreoElectronico.setText("");
        tfTelefono.setText("");
        tfNombresRepresentante.setText("");
        tfApellidosRepresentante.setText("");
        tfCorreoRepresentante.setText("");
        tfTelefonoRepresentante.setText("");
    }

    /**
     * Se encarga de crear la organizacion vinculada y el representante de proyecto
     * en la base de datos
     */
    public void ManejoRegistroOrganizacion() {
        ClearMessages();
        if( VerificarDatosOrganizacion() && VerificarDatosRepresentante() ) {
            GetOrganizacionYResponsable();
            if( !OrganizacionExistente() && !ResponsableExistente() ) {
                try {
                    if( RegistrarOrganizacion() && RegistrarResponsableProyecto() && LigarResponsableYOrganizacion() ) {
                        errorText.setText( "" );
                        successText.setText( outputMessages.OrganizacionYRepresentanteCreateSuccess() );
                    }
                } catch( Exception exception ) {
                    errorText.setText( outputMessages.DatabaseConnectionFailed() );
                    successText.setText( "" );
                }
            }
        }
    }

    /**
     * Intenta crear una Organización en la base de datos y coloca
     * el mensaje correspondiente en caso de éxito o fracaso.
     */
    private boolean RegistrarOrganizacion() {
        return organizacionVinculada.Create ( organizacion );
    }

    /**
     * Intenta crear un ResponsableProyecto en la base de datos y coloca
     * el mensaje correspondiente en caso de éxito o fracaso.
     */
    private boolean RegistrarResponsableProyecto() {
        return responsableProyecto.Create ( responsable );
    }

    /**
     * Crea la relacion logica entre la organizacion vinculada y su representante de proyecto
     */
    private boolean LigarResponsableYOrganizacion() {
        return responsablesOrganizacion.Create( organizacionVinculada.Read( organizacion.GetKey() ).getIdOrganizacion(),
                GetRepresentanteIdList() );
    }

    /**
     * Regresa una lista con el id del representante de proyecto
     * @return una lista de interos
     */
    private List< Integer > GetRepresentanteIdList() {
        List< Integer > idRepresentante = new ArrayList<>();
        idRepresentante.add( responsableProyecto.Read( responsable.GetKey() ).getIdResponsableProyecto() );
        return idRepresentante;
    }

    /**
     * Revisa si ya existe un Estudiante en la base de datos con
     * la misma información que fue introducida.
     * @return true si se encuentra una instancia con la misma información, false sí no.
     */
    private boolean OrganizacionExistente() {
        boolean organizacionExistente = false;
        if( organizacionVinculada.Read( organizacion.GetKey() ) != null ) {
            errorText.setText( outputMessages.OrganizacionExistente() );
            successText.setText( "" );
            organizacionExistente = true;
        }
        return organizacionExistente;
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
     * Revisa si ya existe un Estudiante en la base de datos con
     * la misma información que fue introducida.
     * @return true si se encuentra una instancia con la misma información, false sí no.
     */
    private boolean ResponsableExistente() {
        boolean responsableExistente = false;
        if( responsableProyecto.Read( responsable.GetKey() ) != null ) {
            errorText.setText( outputMessages.ResponsableExistente() );
            successText.setText( "" );
            responsableExistente = true;
        }
        return responsableExistente;
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
        return new OrganizacionVinculada ( tfNombre.getText(), tfDireccion.getText(), TipoSector.Publico,
                tfTelefono.getText(),tfCorreoElectronico.getText(),0, new ArrayList<>(), true,
                GetRandomOrganizacionKey() );

    }

    /**
     * Crea una instancia de Organizacion Vinculada utilizando la información
     * introducida por el usuario en todos los campos de texto.
     * @return una instancia de OrganizacionVinculada
     */
    private ResponsableProyecto ObtenerResponsableProyecto() {
        return new ResponsableProyecto ( 0, tfNombresRepresentante.getText(), tfApellidosRepresentante.getText(),
                tfCorreoRepresentante.getText(), tfTelefonoRepresentante.getText(), new ArrayList<>(), GetRandomRepresentanteKey() );
    }

    /**
     * Genera una clave unica para la organizacion vinculada
     * revisando que la clave no existe en la base de datos
     * @return una cadena con la llave unica
     */
    private String GetRandomOrganizacionKey() {
        String key = CreateKey();
        while( DoesOrganizacionKeyExist( key ) ) {
            key = CreateKey();
        }
        return key;
    }

    /**
     * Genera una clave unica para el respresentante de proyecto
     * revisando que la clave no existe en la base de datos
     * @return una cadena con la llave unica
     */
    private String GetRandomRepresentanteKey() {
        String key = CreateKey();
        while( DoesRepresentanteKeyExist( key ) ) {
            key = CreateKey();
        }
        return key;
    }

    /**
     * Revisa si la clave unica de la organizacion vinculada existe en la
     * base de datos
     * @param key la clave unica que se debe revisar
     * @return true si la clave ya existe, false si no
     */
    private boolean DoesOrganizacionKeyExist( String key ) {
        boolean keyExists = false;
        if( organizacionVinculada.Read( key ) != null ) {
            keyExists = true;
        }
        return keyExists;
    }

    /**
     * Revisa si la clave unica del representante de proyecto existe en la
     * base de datos
     * @param key la clave unica que se debe revisar
     * @return true si la clave ya existe, false si no
     */
    private boolean DoesRepresentanteKeyExist( String key ) {
        boolean keyExists = false;
        if( responsableProyecto.Read( key ) != null ) {
            keyExists = true;
        }
        return keyExists;
    }

    /**
     * Utilizado para crear una llave unica a para identificar
     * los responsables y organizaciones
     * @return una cadena unica
     */
    private String CreateKey() {
        String key = "";
        for( int current = 0; current < 10; current++ ) {
            key = key + GetRandomNumber();
        }
        return key;
    }

    /**
     * Metodo utilizado para crear un numero al azar del rango de 0 a 9
     * @return un numero al azar  del 0 al 9
     */
    private int GetRandomNumber() {
        return randomGenerator.nextInt( 10 );
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