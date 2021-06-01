package Controllers;

import Utilities.LoginSession;
import Utilities.ScreenChanger;
import Utilities.SelectionContainer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class DescargarDocumento_Docente implements Initializable {
    ScreenChanger screenChanger = new ScreenChanger();

    @FXML
    private Label lbNombre;
    @FXML
    private Label lbApellidos;
    @FXML
    private Label lbCedulaProfesional;
    @FXML
    private Label lbNombreEstudiante;
    @FXML
    private TableView tbvArchivosSubidos;
    @FXML
    private TableColumn tcNombreArchivo;
    @FXML
    private TableColumn tcDescripcion;
    @FXML
    private Text errorText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SetUsuario();
        lbNombreEstudiante.setText( SelectionContainer.GetInstance().getEstudianteElegido().getNombres() );
    }

    /**
     * Coloca la informacion del usuario actual en las etiquetas. Se coloca nombres, apellidos,
     * y numero personal.
     */
    public void SetUsuario() {
        lbNombre.setText( LoginSession.GetInstance().GetDocente().getNombres() );
        lbApellidos.setText( LoginSession.GetInstance().GetDocente().GetApellidos() );
        lbCedulaProfesional.setText( LoginSession.GetInstance().GetDocente().GetNumeroPersonal() );
    }

    public void ClicRegresar( MouseEvent mouseEvent ) {
        screenChanger.ShowScreenPrincipalDocente( mouseEvent, errorText );
        SelectionContainer.GetInstance().setEstudianteElegido( null );
    }
}
