package Controllers;

import Database.DocumentoDAO;
import Database.ExpedienteDAO;
import Entities.Documento;
import Entities.Estudiante;
import Entities.Expediente;
import Utilities.LoginSession;
import Utilities.OutputMessages;
import Utilities.ScreenChanger;
import Utilities.SelectionContainer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DescargarDocumento_Docente implements Initializable {
    ScreenChanger screenChanger = new ScreenChanger();
    Estudiante estudianteElegido = SelectionContainer.GetInstance().getEstudianteElegido();
    ExpedienteDAO expedienteDAO = new ExpedienteDAO();
    DocumentoDAO documentoDAO = new DocumentoDAO();
    OutputMessages outputMessages = new OutputMessages();
    DirectoryChooser directoryChooser = new DirectoryChooser();

    List< Documento > documentosSubidos = new ArrayList<>();
    Expediente expedienteEstudiante = expedienteDAO.ReadByStudent( estudianteElegido.getMatricula() );

    @FXML
    private Label lbNombre;
    @FXML
    private Label lbApellidos;
    @FXML
    private Label lbCedulaProfesional;
    @FXML
    private Label lbNombreEstudiante;
    @FXML
    private TableView< Documento > tbvDocumentosSubidos;
    @FXML
    private TableColumn< Documento, String> tcNombreDocumento;
    @FXML
    private TableColumn< Documento, String > tcTipo;
    @FXML
    private TableColumn< Documento, String > tcPeso;
    @FXML
    private Text errorText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SetUsuario();
        MostrarEstudianteElegido();
        ConfigurarColumna();
        RecuperarArchivosExpediente();
        MostrarArchivosSubidos();
    }

    /**
     * Se configuran las columnas de las tablas, indicando que atributos de la entidad
     * van a ser mostrados por cada columna.
     */
    private void ConfigurarColumna() {
        tcNombreDocumento.setCellValueFactory( new PropertyValueFactory<>( "titulo" ) );
        tcTipo.setCellValueFactory( new PropertyValueFactory<>( "tipo" ) );
        tcPeso.setCellValueFactory( new PropertyValueFactory<>( "Tamanio" ) );
    }

    /**
     * Recupera los archivos subidos por el estudiante seleccionado a su expediente
     */
    private void RecuperarArchivosExpediente() {
        try{
            documentosSubidos = documentoDAO.ReadByExpediente( expedienteEstudiante.GetClave() );
        }catch (NullPointerException exception){
            errorText.setText(outputMessages.NoExpedient());
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
     * Muestra el nombre del estudiante elegido por el docente para descargar sus documentos
     */
    private void MostrarEstudianteElegido() {
        lbNombreEstudiante.setText( estudianteElegido.getNombreCompleto() );
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

    /**
     * Vuelve a la pantalla principal del docente
     * @param mouseEvent evento del mouse que inicia el metodo
     */
    public void ClicRegresar( MouseEvent mouseEvent ) {
        screenChanger.ShowScreenPrincipalDocente( mouseEvent, errorText );
        SelectionContainer.GetInstance().setEstudianteElegido( null );
    }

    /**
     * Obtiene la dirección donde se quiere descargar el documento y procede a recuperar
     * de la base de datos el documento seleccionado previamente, con el fin de poder
     * descargarlo en la direcciòn
     * @param mouseEvent evento del mouse que inicia el método
     */
    public void ClicDescargarDocumento(MouseEvent mouseEvent) {
        if( HayDocumentoSeleccionado() ){
            File file = directoryChooser.showDialog( ( (Node)mouseEvent.getSource() ).getScene().getWindow() );
            DescargarDocumento( documentoDAO.Read( tbvDocumentosSubidos.getSelectionModel().getSelectedItem().
                    getIdDocumento() ).GetDescripcion(), file );
        }
    }
    /**
     * Copia un documento recuperado de la base de datos a la máquina local del
     * usuario.
     * @param documentoBD el archivo recuperado de la base de datos
     * @param documentoDestino el archivo que contiene el path de la máquina local donde se desea almacenar el archivo
     */
    private void DescargarDocumento(File documentoBD, File documentoDestino) {
        try {
            FileInputStream input = new FileInputStream( documentoBD );
            File outputFile = new File( FixFilePath( documentoDestino.getAbsolutePath() + "\\" + documentoBD.getName() ) );
            FileOutputStream output = new FileOutputStream( outputFile );
            CreateDocument( outputFile );
            int inputValue;

            while( ( inputValue = input.read() ) != -1 ) {
                output.write( inputValue );
            }
            input.close();
            output.close();
        } catch( IOException exception ) {
            exception.printStackTrace();
        }
    }

    /**
     * Agrega characteres necesarios para poder almacenar un archivo en un path
     * en específico. (Windows)
     * @param targetString la cadena inicial
     * @return una cadena modificada
     */
    private String FixFilePath( String targetString ) {

            for( int i = 0; i < targetString.length(); i++ ) {
                if( targetString.charAt( i ) == 92 ) {
                    targetString = targetString.substring( 0, i ) + "\\" + targetString.substring( i );
                    i++;
                }
            }
            return targetString;
    }

    /**
     * Crea un documento en la máquina local del usuario en caso de no existir previamente
     * @param targetFile el documento que se desea crear
     * @throws IOException
     */
    private void CreateDocument( File targetFile ) throws IOException {
        if( !targetFile.exists() ) {
            targetFile.createNewFile();
        }
    }

    /**
     * Verifica si se ha seleccionado un documento de la tabla
     * @return true - Hay un documento seleccionado <p>
     *     false - No se ha seleccionado ningún documento
     */
    private boolean HayDocumentoSeleccionado() {
        boolean estaSeleccionado = false;
        if( tbvDocumentosSubidos.getSelectionModel().getSelectedItem() != null ) {
            estaSeleccionado = true;
        }
        return estaSeleccionado;
    }
}
