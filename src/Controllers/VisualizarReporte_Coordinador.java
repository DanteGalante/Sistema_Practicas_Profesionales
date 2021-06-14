package Controllers;

import Database.InformeProblemaDAO;
import Entities.InformeProblema;
import Entities.OrganizacionVinculada;
import Utilities.LoginSession;
import Utilities.OutputMessages;
import Utilities.ScreenChanger;
import Utilities.SelectionContainer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class VisualizarReporte_Coordinador implements Initializable {
    List<InformeProblema> listaInformesProblemas = new ArrayList<InformeProblema>();
    InformeProblemaDAO informeProblema = new InformeProblemaDAO();
    ScreenChanger screenChanger = new ScreenChanger();
    OutputMessages outputMessages = new OutputMessages();

    @FXML
    private Label lbNombres;

    @FXML
    private Label lbApellidos;

    @FXML
    private Label lbNoTrabajador;

    @FXML
    private Button btnRegresar;

    @FXML
    private TableView<InformeProblema> tbReportes;

    @FXML
    private TableColumn<InformeProblema, String> clnDocente;

    @FXML
    private TableColumn<InformeProblema, String> clnAsunto;

    @FXML
    private TableColumn<InformeProblema, Date> clnFechaEntrega;

    @FXML
    private TableColumn<InformeProblema, String> clnCausante;

    @FXML
    private Button btnVisualizar;

    @FXML
    private Text errorText;

    @FXML
    private  Text successText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatosDeUsuario();
        MostrarReportes();
        ValorColumnaOrganizacion();
    }

    public void DatosDeUsuario(){
        lbNombres.setText( LoginSession.GetInstance().GetCoordinador().getNombres() );
        lbApellidos.setText( LoginSession.GetInstance().GetCoordinador().GetApellidos() );
        lbNoTrabajador.setText( LoginSession.GetInstance().GetCoordinador().GetNumeroPersonal() );
    }

    public void MostrarReportes(){
        tbReportes.getItems().clear();
        listaInformesProblemas = informeProblema.ReadAll();
        for( InformeProblema informeAsunto : listaInformesProblemas){
            tbReportes.getItems().add( informeAsunto );
        }
    }

    private void ValorColumnaOrganizacion() {
        clnAsunto.setCellValueFactory( new PropertyValueFactory<>( "asunto" ) );
        clnCausante.setCellValueFactory( new PropertyValueFactory<>( "estudiante" ) );
        clnDocente.setCellValueFactory( new PropertyValueFactory<>( "numeroPersonal" ) );
        clnFechaEntrega.setCellValueFactory( new PropertyValueFactory<>( "fechaEnviada" ) );
    }

    /**
     * Cambia la pantalla de GestionarOrganizacion_Coordinador a la pantalla Principal_Coordinador.
     * @param mouseEvent el evento de mouse que activo la acción.
     */
    public void ClicRegresar ( MouseEvent mouseEvent ){
        screenChanger.MostrarPantallaGestionarReporteCoordinador( mouseEvent, errorText );
    }


    @FXML
    public void ClicVisualizarReporte( MouseEvent mouseEvent ) {
        SelectionContainer.GetInstance().setInformeElegido( RecuperarSeleccionReporte() );
        if(!tbReportes.getSelectionModel().isEmpty()){
            errorText.setText("");
            screenChanger.MostrarPantallaReporteSeleccionado( mouseEvent, errorText );
        }else{
            errorText.setText(outputMessages.SeleccionInvalidaReportes());
        }
    }

    public InformeProblema RecuperarSeleccionReporte(){
        return tbReportes.getSelectionModel().getSelectedItem();
    }
}
