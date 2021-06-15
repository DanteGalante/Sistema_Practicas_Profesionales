package Controllers;

import Database.DocumentoDAO;
import Database.ExpedienteDAO;
import Database.ProyectoDAO;
import Entities.Documento;
import Entities.Estudiante;
import Entities.Expediente;
import Utilities.LoginSession;
import Utilities.OutputMessages;
import Utilities.ScreenChanger;
import Utilities.SelectionContainer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ConsultarExpediente_Docente implements Initializable {

    ScreenChanger screenChanger = new ScreenChanger();
    OutputMessages outputMessages = new OutputMessages();
    Estudiante estudianteSeleccionado = SelectionContainer.GetInstance().getEstudianteElegido();
    ExpedienteDAO expedienteDAO = new ExpedienteDAO();
    DocumentoDAO documentoDAO = new DocumentoDAO();
    ProyectoDAO proyectoDAO = new ProyectoDAO();
    List< Documento > documentosSubidos = new ArrayList< Documento >();
    Expediente expedienteEstudiante = new Expediente();

    @FXML
    private Label lbNombre;
    @FXML
    private Label lbApellidos;
    @FXML
    private Label lbCedulaProfesional;
    @FXML
    private Text errorText;
    @FXML
    private Label lbNombreEstudiante;
    @FXML
    private Label lbNombreProyecto;
    @FXML
    private TableView< Documento > tbvDocumentosSubidos;
    @FXML
    private TableColumn< Documento, String > tcNombre;
    @FXML
    private TableColumn< Documento, String > tcDescripcion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SetUsuario();
        MostrarInfoEstudiante();
    }

    /**
     * Muestra en pantalla la informacion del expediente del estudiante elegido
     * y su expediente
     */
    private void MostrarInfoEstudiante() {
        String nombreProyecto = "";
        String nombreEstudiante = "";

        if ( EstudianteTieneExpediente() ) {
            RecuperarExpediente();
            try{
                nombreProyecto = proyectoDAO.Read( expedienteEstudiante.GetIDProyecto() ).getNombre();
                nombreEstudiante = estudianteSeleccionado.getNombreCompleto();
                lbNombreEstudiante.setText( nombreEstudiante );
                lbNombreProyecto.setText( nombreProyecto );
                ConfigurarColumnasTabla();
                RecuperarArchivosExpediente();
                MostrarArchivosSubidos();
            }catch (Exception exception) {
                errorText.setText( outputMessages.DatabaseConnectionFailed3() );
            }
        }
    }

    /**
     * Recupera el expediente de un estudiante
     */
    private void RecuperarExpediente() {
        try {
            expedienteEstudiante = expedienteDAO.ReadPorMatricula( estudianteSeleccionado.getMatricula() );
        } catch ( Exception exception ) {
            errorText.setText( outputMessages.DatabaseConnectionFailed3() );
        }
    }

    /**
     * Verifica si el estudiante tiene expediente
     * @return True si el estudiante tiene expediente, False si no tiene
     */
    private boolean EstudianteTieneExpediente() {
        boolean estudianteTieneExpediente = false;

        try {
            if ( expedienteDAO.ReadPorMatricula( estudianteSeleccionado.getMatricula() ) != null ) {
                estudianteTieneExpediente = true;
            }
        } catch (Exception exception) {
            errorText.setText( outputMessages.DatabaseConnectionFailed3() );
        }

        return estudianteTieneExpediente;
    }

    /**
     * Recupera los archivos subidos por el estudiante seleccionado a su expediente
     */
    private void RecuperarArchivosExpediente() {
        try {
            documentosSubidos = documentoDAO.ReadByExpediente( expedienteEstudiante.GetClave() );
        } catch ( Exception exception ) {
            errorText.setText( outputMessages.DatabaseConnectionFailed3() );
        }
    }

    /**
     * Muestra en una tabla los archivos subidos por el estudiante seleccionado
     */
    private void MostrarArchivosSubidos() {
        tbvDocumentosSubidos.getItems().clear();
        for (Documento documento : documentosSubidos) {
            tbvDocumentosSubidos.getItems().add(documento);
        }
    }

    /**
     * Se configuran las columnas de las tablas, indicando que atributos de la entidad
     * van a ser mostrados por cada columna.
     */
    private void ConfigurarColumnasTabla() {
        tcNombre.setCellValueFactory( new PropertyValueFactory<>( "titulo" ) );
        tcDescripcion.setCellValueFactory( new PropertyValueFactory<>( "descripcionArchivo" ) );
    }

    /**
     * Coloca la informacion del usuario actual en las etiquetas. Se coloca nombres, apellidos,
     * y numero personal.
     */
    public void SetUsuario() {
        lbNombre.setText(LoginSession.GetInstance().GetDocente().getNombres());
        lbApellidos.setText(LoginSession.GetInstance().GetDocente().GetApellidos());
        lbCedulaProfesional.setText(LoginSession.GetInstance().GetDocente().GetNumeroPersonal());
    }

    /**
     * Vuelve a la pantalla principal del docente
     * @param mouseEvent evento del mouse que inicia el metodo
     */
    public void ClicRegresar(MouseEvent mouseEvent) {
        screenChanger.ShowScreenPrincipalDocente(mouseEvent, errorText);
    }
}
