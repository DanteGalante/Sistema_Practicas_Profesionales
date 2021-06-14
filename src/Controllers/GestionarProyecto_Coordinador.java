package Controllers;

import Database.ProyectoDAO;
import Entities.Proyecto;
import Utilities.ScreenChanger;
import Utilities.SelectionContainer;
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

public class GestionarProyecto_Coordinador implements Initializable {
    private List< Proyecto > listaProyectos = new ArrayList<>();
    private ProyectoDAO proyecto = new ProyectoDAO();
    private ScreenChanger screenChanger = new ScreenChanger();

    @FXML
    private Label lbNombres;

    @FXML
    private Label lbApellidos;

    @FXML
    private Label lbNoTrabajador;

    @FXML
    private TableView <Proyecto> tbProyectos;

    @FXML
    private TableColumn <Proyecto, String> clnProyectos;

    @FXML
    private Button btnConsultarProyecto;

    @FXML
    private Button btnRegistrarProyecto;

    @FXML
    private Button btnEliminarProyecto;

    @FXML
    private Button btnModificarProyecto;

    @FXML
    private Text errorText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DatosDeUsuario();
        ValorColumnasProyecto();
        MostrarProyectosDisponibles();

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

    public void ValorColumnasProyecto(){
        clnProyectos.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    }

    public void MostrarProyectosDisponibles(){
        listaProyectos = proyecto.ReadAll();
        for( Proyecto proyecto : listaProyectos){
            proyecto.getNombre();
            tbProyectos.getItems().add( proyecto );
        }
    }

    public void ClicRegresar ( MouseEvent mouseEvent ){
        screenChanger.MostrarPantallaPrincipalCoordinador( mouseEvent, errorText );
    }

    @FXML
    void ClicModificarProyecto( MouseEvent event){
        SelectionContainer.GetInstance().setProyectoElegido( RecuperarProyecto() );
        screenChanger.MostrarPantallaModificarProyecto( event, errorText );
    }

    public Proyecto RecuperarProyecto(){
        return tbProyectos.getSelectionModel().getSelectedItem();
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