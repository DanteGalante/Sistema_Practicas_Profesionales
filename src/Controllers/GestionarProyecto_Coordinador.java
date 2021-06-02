package Controllers;

import Database.ProyectoDAO;
import Entities.Proyecto;
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
    private Button btnGestionarOrganizacion;

    @FXML
    private Button btnRegresar;

    @FXML
    private TextField tfBuscar;

    @FXML
    private Button btnBuscar;

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

    public void ClicGestionarOrganizacion ( MouseEvent mouseEvent ){
        screenChanger.MostrarPantallaGestionarOrganizacion( mouseEvent,errorText );
    }

    public void ClicModificarProyecto( MouseEvent mouseEvent ){
        screenChanger.MostrarPantallaModificarProyecto( mouseEvent, errorText );
    }
}