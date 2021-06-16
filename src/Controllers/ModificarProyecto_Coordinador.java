package Controllers;

import Database.OrganizacionVinculadaDAO;
import Database.ProyectoDAO;
import Entities.Estudiante;
import Entities.OrganizacionVinculada;
import Entities.Proyecto;
import Entities.ResponsableProyecto;
import Enumerations.EstadoEstudiante;
import Enumerations.EstadoProyecto;
import Utilities.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ModificarProyecto_Coordinador implements Initializable {
    private ProyectoDAO proyecto = new ProyectoDAO();
    private OutputMessages outputMessages = new OutputMessages();
    private ScreenChanger screenChanger = new ScreenChanger();
    private InputValidator inputValidator = new InputValidator();
    private OrganizacionVinculadaDAO organizacionVinculadaDAO = new OrganizacionVinculadaDAO();
    private List<OrganizacionVinculada> listaOrganizaciones = new ArrayList<>();
    private OrganizacionVinculadaDAO organizacionVinculada = new OrganizacionVinculadaDAO();

    @FXML
    private Text TxNombres;

    @FXML
    private Text TxApellidos;

    @FXML
    private Text TxNoTrabajador;

    @FXML
    private TextField TbNombreProyecto;

    @FXML
    private TextArea TbDescripcionProyecto;

    @FXML
    private TableView<OrganizacionVinculada> TvOrganizacion;

    @FXML
    private TableColumn< OrganizacionVinculada, String > TcNombreOrg;

    @FXML
    private TextField TbEstudiantesRequeridos;

    @FXML
    private Text TxError;

    @FXML
    private Text TxSuccess;

    @FXML
    private Button BtRegistrar;

    @FXML
    private Button BtRegresar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatosProyecto();
        DatosUsuario();
        ValorColumnasProyectoSeleccionados();
        LlenarTablaResponsables();
    }

    /**
     * Configura las columnas de la tabla preferencias proyecto en esta pantalla
     */
    private void ValorColumnasProyectoSeleccionados() {
        TcNombreOrg.setCellValueFactory( new PropertyValueFactory<>("Nombre") );
    }

    /**
     * Llena la tabla con las organizaciones vinculadas existentes en la BD
     */
    private void LlenarTablaResponsables() {
        try {
            TvOrganizacion.getItems().clear();
            listaOrganizaciones.clear();
            listaOrganizaciones = organizacionVinculada.ReadAll();
            if (listaOrganizaciones.size() > 0) {
                for (OrganizacionVinculada organizacionVinculada : listaOrganizaciones) {
                    if (organizacionVinculada.getActiveStatus() != false) {
                        TvOrganizacion.getItems().add(organizacionVinculada);
                    }
                }
            }
        } catch (Exception exception) {

            TxError.setText(outputMessages.DatabaseConnectionFailed4());
        }
    }

    public void ManejoModificarProyecto() {
        ValidarDatos();
        ModificarProyecto();
    }

    /**
     * Cambia la pantalla de ModificarProyecto_Coordinador a GestionarProyecto_Coordinador.
     * @param mouseEvent el evento de mouse que activo la acción.
     */
    public void ClicRegresar ( MouseEvent mouseEvent ){
        screenChanger.MostrarPantallaGestionarProyecto( mouseEvent, TxError );
    }

    /**
     * Coloca la información del usuario actual en los campos de texto de
     * nombres, apellidos y No.Trabajador
     */
    public void DatosProyecto(){
        TbNombreProyecto.setText( SelectionContainer.GetInstance().getProyectoElegido().getNombre() );
        TbDescripcionProyecto.setText( SelectionContainer.GetInstance().getProyectoElegido().GetDescripcion() );
        TbEstudiantesRequeridos.setText( "" + ( SelectionContainer.GetInstance().getProyectoElegido().getNumEstudiantesRequeridos() ) );
    }

    private ResponsableProyecto ObtenerResponsableProyecto() {
        return SelectionContainer.GetInstance().getResponsableElegido();
    }

    public int RecuperarId(){
        int id = SelectionContainer.GetInstance().getProyectoElegido().getIdProyecto();
        return id;
    }

    public int RecuperarEstudiantesAsignados(){
        int cantidad = SelectionContainer.GetInstance().getProyectoElegido().GetEstudiantesAsignados();
        return cantidad;
    }

    public String RecuperarFechaRegistro(){
        String fecha = SelectionContainer.GetInstance().getProyectoElegido().GetFechaRegistro();
        return fecha;
    }

    public EstadoProyecto RecuperarEstadoProyecto(){
        EstadoProyecto estado = SelectionContainer.GetInstance().getProyectoElegido().GetEstado();
        return estado;
    }

    private Proyecto ObtenerProyecto() {
        return new Proyecto (RecuperarId(),TbNombreProyecto.getText(),TbDescripcionProyecto.getText(),Integer.parseInt(TbEstudiantesRequeridos.getText()),
                RecuperarEstudiantesAsignados(),RecuperarFechaRegistro(),RecuperarEstadoProyecto());
    }

    private void ValidarDatos() {
        NombreValido();
        DescripcionValida();
        EsEstudiantesRequeridosValidos();
        SeleccionValida();
    }

    /**
     * Revisa que el nombre introducido sea valido.
     */
    private void NombreValido() {
        if( !inputValidator.AreNamesValid( TbNombreProyecto.getText() ) ) {
            TxError.setText( outputMessages.InvalidNames() );
            TxSuccess.setText( "" );
        }
    }

    /**
     * Revisa que los nombres introducidos sean validos.
     */
    private void DescripcionValida() {
        if( !inputValidator.DescripcionValida( TbDescripcionProyecto.getText() ) ) {
            TxError.setText( outputMessages.DescripcionInvalida() );
            TxSuccess.setText( "" );
        }
    }

    /**
     * Revisa que la cantidad de estudiantes requeridos sea valido.
     */
    private void EsEstudiantesRequeridosValidos(){
        if( !inputValidator.EstudiantesRequeridosValidos( Integer.parseInt( TbEstudiantesRequeridos.getText() ) ) ) {
            TxError.setText( outputMessages.EstudiantesRequeridosInvalidos() );
            TxSuccess.setText( "" );
        }
    }

    /**
     * Revisa que la cantidad de estudiantes requeridos sea valido.
     */
    private void SeleccionValida() {
        if(TvOrganizacion.getSelectionModel().getSelectedItem() != null){
            OrganizacionVinculada orgVinculadaElegida = (OrganizacionVinculada)TvOrganizacion.getSelectionModel().getSelectedItem();
            SelectionContainer.GetInstance().setOrganizacionElegida(orgVinculadaElegida);
            TxSuccess.setText("");
            TxError.setText("");
        }else{
            TxError.setText(outputMessages.SeleccionInvalidaOrganizacion());
        }
    }

    private void ModificarProyecto() {
        if( proyecto.Update( ObtenerProyecto() ) ) {
            TxError.setText( "" );
            TxSuccess.setText( outputMessages.ModificacionProyectoExitoso() );
        }
        else {
            TxError.setText( outputMessages.DatabaseConnectionFailed() );
            TxSuccess.setText( "" );
        }
    }

    /**
     * Coloca la información del usuario actual en los campos de texto de
     * nombres, apellidos y No.Trabajador
     */
    public void DatosUsuario(){
        TxNombres.setText( LoginSession.GetInstance().GetCoordinador().getNombres() );
        TxApellidos.setText( LoginSession.GetInstance().GetCoordinador().GetApellidos() );
        TxNoTrabajador.setText( LoginSession.GetInstance().GetCoordinador().GetNumeroPersonal() );
    }
}
