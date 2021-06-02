package Controllers;

import Database.EstudianteDAO;
import Entities.Estudiante;
import Entities.UsuarioUV;
import Utilities.ScreenChanger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import Utilities.LoginSession;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Principal_Coordinador implements Initializable {
    private List< Estudiante > listaGrupos = new ArrayList< Estudiante>();
    private EstudianteDAO estudiantes = new EstudianteDAO();
    private List< Estudiante > listaEstudiantes = new ArrayList< Estudiante>();
    private ScreenChanger screenChanger = new ScreenChanger();

    @FXML
    private Label lbNombres;

    @FXML
    private Label lbApellidos;

    @FXML
    private Label lbNoTrabajador;

    @FXML
    private Button btnAsignarProyecto;

    @FXML
    private Button btnGenerarOficioAsignacion;

    @FXML
    private Button btnConsultarExpediente;

    @FXML
    private Button btnValidarInscripcion;

    @FXML
    private Button btnCerrarSesion;

    @FXML
    private TableView <Estudiante> tbGrupo;

    @FXML
    private TableColumn<Estudiante, String > clnNrc;

    @FXML
    private Button btnGenerarReporte;

    @FXML
    private Button btnVisualizarReportes;

    @FXML
    private Button btnConsultarOrganizacion;

    @FXML
    private ComboBox cbEstudianteDocente;

    @FXML
    private TextField tfBuscar;

    @FXML
    private Button btnBuscar;

    @FXML
    private TableView <UsuarioUV> tbEstudianteDocente;

    @FXML
    private TableColumn<UsuarioUV, String > clnMatriculaNoTrabajador;

    @FXML
    private Text errorText;

    /**
     * Configura los elementos utilizados en la pantalla Principal_Coordinador
     * @param url un url sin utilizar
     * @param resourceBundle un conjunto de recursos no utilizados
     */
    @Override
    public void initialize( URL url, ResourceBundle resourceBundle ) {

        DatosUsuario();
        ValorColumnasGrupo();
        MostrarGruposDisponibles();
        MostrarOpcionesComboBox();
        ValorColumnasEstudianteDocente();
        MostrarEstudiantesDisponibles();

    }

    /**
     * Coloca la información del usuario actual en los campos de texto de
     * nombres, apellidos y No.Trabajador
     */
    public void DatosUsuario(){
        lbNombres.setText( LoginSession.GetInstance().GetCoordinador().getNombres() );
        lbApellidos.setText( LoginSession.GetInstance().GetCoordinador().GetApellidos() );
        lbNoTrabajador.setText( LoginSession.GetInstance().GetCoordinador().GetNumeroPersonal() );
    }

    /**
     * Configura la columna de la tabla Grupo
     */
    private void ValorColumnasGrupo() {
        clnNrc.setCellValueFactory( new PropertyValueFactory<>( "nrc" ) );
    }

    /**
     * Agrega todos los nrc disponibles a la tabla de grupos disponibles
     */
    private void MostrarGruposDisponibles() {
        listaGrupos = estudiantes.ReadAll();
        for( Estudiante estudiante : listaGrupos ) {
            estudiante.getNrc();
            tbGrupo.getItems().add( estudiante );
        }
    }

    /**
     * Muestra las opciones disponibles para el ComboBox
     */
    private void MostrarOpcionesComboBox(){

        cbEstudianteDocente.getItems().add( "Estudiante" );
        cbEstudianteDocente.getItems().add( "Docente" );

    }

    /**
     * Configura la columna de la tabla Estudiantes/Docentes
     */
    private void ValorColumnasEstudianteDocente() {
        clnMatriculaNoTrabajador.setCellValueFactory( new PropertyValueFactory<>( "nombreCompleto" ) );
    }

    /**
     * Agrega todos los Estudiantes enlazados a los NRC de Grupo a la tabla de Estudiantes
     */
    private void MostrarEstudiantesDisponibles() {
        listaEstudiantes = estudiantes.ReadAll();
        for( Estudiante estudiante : listaEstudiantes ) {
            estudiante.getNombres();
            tbEstudianteDocente.getItems().add( estudiante );
        }
    }

    /**
     * Permite cambiar la pantalla a la pantalla GestionarOrganización
     */
    public void MostrarPantallaGestionarOrganizacion( MouseEvent mouseEvent ) {
        screenChanger.MostrarPantallaGestionarOrganizacion( mouseEvent, errorText );
    }

    /**
     * Permite cambiar la pantalla a la pantalla GestionarEstudiante
     */
    public void MostrarPantallaGestionarEstudiante( MouseEvent mouseEvent ) {
        screenChanger.MostrarPantallaGestionarEstudianesCoordinador( mouseEvent, errorText );
    }

    /**
     * Cierra la sesión actual y se regresa a la pantalla "IniciarSesión"
     * @param mouseEvent el evento de mouse que inicio el cambio
     */
    public void CerrarSesion( MouseEvent mouseEvent ) {
        LoginSession.GetInstance().Logout();
        screenChanger.ShowLoginScreen( mouseEvent, errorText );
    }
}