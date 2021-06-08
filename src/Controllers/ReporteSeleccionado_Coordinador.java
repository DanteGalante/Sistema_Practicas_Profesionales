package Controllers;

import Utilities.LoginSession;
import Utilities.ScreenChanger;
import Utilities.SelectionContainer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ReporteSeleccionado_Coordinador implements Initializable {
    ScreenChanger screenChanger = new ScreenChanger();


    @FXML
    private Label lbNombres;

    @FXML
    private Label lbApellidos;

    @FXML
    private Label lbNoTrabajador;

    @FXML
    private Button btnRegresar;

    @FXML
    private Text asuntoText;

    @FXML
    private Text docenteText;

    @FXML
    private Text fechaEntregaText;

    @FXML
    private Text causanteText;

    @FXML
    private TextArea taContenido;

    @FXML
    private Text errorText;

    @FXML
    private Text successText;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatosDeUsuario();
        DatosInforme();
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
    public void DatosInforme(){
        asuntoText.setText( SelectionContainer.GetInstance().getInformeElegido().GetAsunto() );
        docenteText.setText( SelectionContainer.GetInstance().getInformeElegido().GetNumeroPersonal() );
        fechaEntregaText.setText( SelectionContainer.GetInstance().getInformeElegido().GetFechaEnviada() );
        causanteText.setText( SelectionContainer.GetInstance().getInformeElegido().GetEstudiante() );
        taContenido.setText( SelectionContainer.GetInstance().getInformeElegido().GetContenido() );
    }

    @FXML
    public void ClicRegresar(MouseEvent mouseEvent){
        screenChanger.MostrarPantallaVisualizarReportesCoordinador( mouseEvent, errorText );
    }
}