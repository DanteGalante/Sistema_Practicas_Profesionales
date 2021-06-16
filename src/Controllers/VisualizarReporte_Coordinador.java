package Controllers;

import Database.DocenteDAO;
import Database.InformeProblemaDAO;
import Entities.ConjuntoDocenteInforme;
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
    DocenteDAO docenteDAO = new DocenteDAO();

    @FXML
    private Label lbNombres;

    @FXML
    private Label lbApellidos;

    @FXML
    private Label lbNoTrabajador;

    @FXML
    private Button btnRegresar;

    @FXML
    private TableView<ConjuntoDocenteInforme> tbReportes;

    @FXML
    private TableColumn<ConjuntoDocenteInforme, String> clnDocente;

    @FXML
    private TableColumn<ConjuntoDocenteInforme, String> clnAsunto;

    @FXML
    private TableColumn<ConjuntoDocenteInforme, Date> clnFechaEntrega;

    @FXML
    private TableColumn<ConjuntoDocenteInforme, String> clnCausante;

    @FXML
    private TableColumn<ConjuntoDocenteInforme, Date> clnNombre;

    @FXML
    private Button btnVisualizar;

    @FXML
    private Text errorText;

    @FXML
    private  Text successText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DatosDeUsuario();
            MostrarReportes();
            ValorColumnaOrganizacion();
        } catch ( Exception exception ) {
            errorText.setText( outputMessages.DatabaseConnectionFailed3() );
        }
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
            tbReportes.getItems().add( new ConjuntoDocenteInforme(docenteDAO.Read(informeAsunto.getNumeroPersonal() ), informeAsunto )  );
        }
    }

    private void ValorColumnaOrganizacion() {
        clnAsunto.setCellValueFactory( new PropertyValueFactory<>( "asunto" ) );
        clnCausante.setCellValueFactory( new PropertyValueFactory<>( "estudiante" ) );
        clnDocente.setCellValueFactory( new PropertyValueFactory<>( "numeroPersonal" ) );
        clnFechaEntrega.setCellValueFactory( new PropertyValueFactory<>( "fechaEnviada" ) );
        clnNombre.setCellValueFactory( new PropertyValueFactory<>( "nombreDocente" ) );
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
            SelectionContainer.GetInstance().setConjuntoDocenteInforme( tbReportes.getSelectionModel().getSelectedItem() );
            screenChanger.MostrarPantallaReporteSeleccionado( mouseEvent, errorText );
        }else{
            errorText.setText(outputMessages.SeleccionInvalidaReportes());
        }
    }

    public InformeProblema RecuperarSeleccionReporte(){
        return tbReportes.getSelectionModel().getSelectedItem().getInforme();
    }
}
