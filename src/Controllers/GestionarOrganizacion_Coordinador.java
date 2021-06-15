package Controllers;

import Database.OrganizacionVinculadaDAO;
import Database.ProyectosDeResponsablesDAO;
import Database.ResponsableProyectoDAO;
import Database.ResponsablesOrganizacionDAO;
import Entities.OrganizacionVinculada;
import Entities.ResponsableProyecto;
import Utilities.LoginSession;
import Utilities.OutputMessages;
import Utilities.ScreenChanger;
import Utilities.SelectionContainer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class GestionarOrganizacion_Coordinador implements Initializable {
    private ScreenChanger screenChanger = new ScreenChanger();
    private OrganizacionVinculadaDAO organizacionVinculada = new OrganizacionVinculadaDAO();
    private ResponsableProyectoDAO responsableProyecto = new ResponsableProyectoDAO();
    private ResponsablesOrganizacionDAO responsablesOrganizacion = new ResponsablesOrganizacionDAO();
    private List< OrganizacionVinculada > listaOrganizaciones = new ArrayList<>();
    private OutputMessages outputMessages = new OutputMessages();
    private List< ProyectosDeResponsablesDAO > listaProyectos = new ArrayList<>();

    @FXML
    private Label lbNombres;

    @FXML
    private Label lbApellidos;

    @FXML
    private Label lbNoTrabajador;

    @FXML
    private Button btnGestionarOrganizaciones;

    @FXML
    private Button btnGestionarProyectos;

    @FXML
    private Button btnRegresar;

    @FXML
    private TextField tfBuscar;

    @FXML
    private Button btnBuscar;

    @FXML
    private TableView<OrganizacionVinculada> tbOrganizaciones;

    @FXML
    private TableColumn<OrganizacionVinculada, String> clnOrganizaciones;

    @FXML
    private Button btnConsultarOrganizacion;

    @FXML
    private Button btnRegistrarOrganizacion;

    @FXML
    private Button btnEliminarOrganizacion;

    @FXML
    private Button btnModificarOrganizacion;

    @FXML
    private Text errorText;

    @FXML
    private Text successText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatosDeUsuario();
        MostrarOrganizaciones();
        ValorColumnaOrganizacion();
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

    public void MostrarOrganizaciones(){
        tbOrganizaciones.getItems().clear();
        listaOrganizaciones = organizacionVinculada.ReadAll();
        for( OrganizacionVinculada organizacion : listaOrganizaciones ){
            if( organizacion.getActiveStatus() != false){
                tbOrganizaciones.getItems().add( organizacion );
            }
        }
    }

    private void ValorColumnaOrganizacion() {
        clnOrganizaciones.setCellValueFactory( new PropertyValueFactory<>( "nombre" ) );
    }

    /**
     * Cambia la pantalla de GestionarOrganizacion_Coordinador a la pantalla Principal_Coordinador.
     * @param mouseEvent el evento de mouse que activo la acción.
     */
    public void ClicRegresar ( MouseEvent mouseEvent ){
        screenChanger.MostrarPantallaPrincipalCoordinador( mouseEvent, errorText );
    }

    /**
     * Permite cambiar la pantalla a la pantalla ConsultarOrganizacion
     */
    public void MostrarPantallaConsultarOrganizacion( MouseEvent mouseEvent ) {
        if(tbOrganizaciones.getSelectionModel().getSelectedItem() != null){
            OrganizacionVinculada orgVinculadaElegida = (OrganizacionVinculada)tbOrganizaciones.getSelectionModel().getSelectedItem();
            SelectionContainer.GetInstance().setOrganizacionElegida(orgVinculadaElegida);
            screenChanger.ShowConsultarOrganizacion( mouseEvent, errorText );
        }else{
            errorText.setText("Seleccione una organizacion");
        }
    }

    /**
     * Cambia la pantalla de GestionarOrganizacion_Coordinador a la pantalla RegistrarOrganizacion_Coordinador.
     * @param mouseEvent el evento de mouse que activo la acción.
     */
    public void ClicRegistrar( MouseEvent mouseEvent ){
        screenChanger.MostrarPantallaRegistrarOrganizacion( mouseEvent, errorText );
    }

    /**
     * Recupera la selección de la tabla para Elimina una OrganizacionVinculada de la base de datos.
     * @param mouseEvent el evento de mouse que activo la acción.
     */
    public void ClicEliminarOrganizacion( MouseEvent mouseEvent){
        if( ExisteSeleccion() ) {
            Alert deleteAlert = new Alert( Alert.AlertType.CONFIRMATION, outputMessages.ConfirmacionEliminarOrganizacion() );
            deleteAlert.showAndWait().ifPresent( response -> {
                if( response == ButtonType.OK ) {
                    EliminadoLogico();
                    MostrarOrganizaciones();
                }
            } );
        }
    }

    public void EliminadoLogico(){
        //Cambiar las tablas para que solo reciban a las organizaciones con el atributo Active = true;
        OrganizacionVinculada seleccionOrganizacion = OrganizacionSeleccionada();
        if(!ProyectosRelacionados(seleccionOrganizacion)){
            organizacionVinculada.Update( ObtenerOrganizacionEliminada(seleccionOrganizacion ) );
        }else{
            Alert deleteAlert = new Alert( Alert.AlertType.CONFIRMATION, outputMessages.ConfirmacionEliminarProyecto() );
            deleteAlert.showAndWait().ifPresent( response -> {
                if( response == ButtonType.OK ) {
                    EliminadoLogico();
                    MostrarOrganizaciones();
                }
            });
        }
    }

    public OrganizacionVinculada OrganizacionSeleccionada(){
        return organizacionVinculada.Read(tbOrganizaciones.getSelectionModel().getSelectedItem().getIdOrganizacion());
    }

    public boolean ProyectosRelacionados( OrganizacionVinculada seleccionOrganizacion){
        boolean proyectosRelacionados = false;
        ResponsableProyecto responsableActual;
        for( int i = 0; i < seleccionOrganizacion.getResponsables().size(); i++ ){
            responsableActual = responsableProyecto.Read(seleccionOrganizacion.getResponsables().get(i));
            if(responsableActual.getIdProyectos().size() > 0){
                proyectosRelacionados = true;
            }
        }
        return proyectosRelacionados;
    }

    public OrganizacionVinculada ObtenerOrganizacionEliminada(OrganizacionVinculada seleccionOrganizacion){
        return new OrganizacionVinculada ( seleccionOrganizacion.getNombre(),seleccionOrganizacion.getDireccion(),
                seleccionOrganizacion.getSector(),seleccionOrganizacion.getTelefono(),seleccionOrganizacion.getCorreo(),
                seleccionOrganizacion.getIdOrganizacion(),seleccionOrganizacion.getResponsables(),false,
                seleccionOrganizacion.GetKey());
    }

    /**
     * Permite cambiar la pantalla a la pantalla GestionarEstudiante
     */
    @FXML
    public void ClicModificarOrganizacion( MouseEvent mouseEvent ) {
        SelectionContainer.GetInstance().setOrganizacionElegida( RecuperarOrganizacion() );
        SelectionContainer.GetInstance().setResponsableElegido( RecuperarResponsable() );
        screenChanger.MostrarPantallaModificarOrganizacion( mouseEvent, errorText );
    }

    public boolean ExisteSeleccion(){
        boolean Seleccion = false;
        if( tbOrganizaciones.getSelectionModel().getSelectedItem() != null ) {
            errorText.setText("");
            Seleccion = true;
        }else{
            errorText.setText(outputMessages.SeleccionInvalidaOrganizacion());
        }
        return Seleccion;
    }

    public OrganizacionVinculada RecuperarOrganizacion(){
        return tbOrganizaciones.getSelectionModel().getSelectedItem();
    }

    public ResponsableProyecto RecuperarResponsable() {
        List< Integer > idResponsables = responsablesOrganizacion.ReadResponsables(
                                         tbOrganizaciones.getSelectionModel().getSelectedItem().getIdOrganizacion() );
        return responsableProyecto.Read( idResponsables.get( 0 ) );
    }

    /**
     * Permite cambiar la pantalla a la pantalla GestionarEstudiante
     */
    public void MostrarPantallaGestionarEstudiante( MouseEvent mouseEvent ) {
        screenChanger.MostrarPantallaGestionarEstudianesCoordinador( mouseEvent, errorText );
    }

    /**
     * Permite cambiar la pantalla a la pantalla GestionarReportes
     */
    public void MostrarPantallaGestionarReporte( MouseEvent mouseEvent ) {
        screenChanger.MostrarPantallaGestionarReporteCoordinador( mouseEvent, errorText );
    }

    /**
     * Permite cambiar la pantalla a la pantalla GestionarReportes
     */
    public void MostrarPantallaGestionarOrganizacion( MouseEvent mouseEvent ) {
        screenChanger.MostrarPantallaGestionarOrganizacion( mouseEvent, errorText );
    }

    /**
     * Permite cambiar la pantalla a la pantalla GestionarReportes
     */
    public void MostrarPantallaGestionarProyecto( MouseEvent mouseEvent ) {
        screenChanger.MostrarPantallaGestionarProyecto( mouseEvent, errorText );
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