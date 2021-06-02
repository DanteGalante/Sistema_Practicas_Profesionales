/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 29 - abr - 2021
 * Descripción:
 * Clase encargada de manejar los eventos de la pantalla
 * DocumentosAdicionales_Estudiante.
 */
package Controllers;

import Database.DocumentoDAO;
import Database.ExpedienteDAO;
import Database.ProyectoDAO;
import Database.ReporteDAO;
import Entities.Documento;
import Entities.Expediente;
import Entities.Reporte;
import Utilities.OutputMessages;
import Utilities.ScreenChanger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import Utilities.LoginSession;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Clase encargada de manejar los eventos de la pantalla
 * DocumentosAdicionales_Estudiante.
 */
public class AdditionalDocumentsController implements Initializable {
    private ScreenChanger screenChanger = new ScreenChanger();
    private FileChooser fileChooser = new FileChooser();
    private DirectoryChooser directoryChooser = new DirectoryChooser();
    private DocumentoDAO documentos = new DocumentoDAO();
    private ReporteDAO reportes = new ReporteDAO();
    private ProyectoDAO proyectos = new ProyectoDAO();
    private ExpedienteDAO expedientes = new ExpedienteDAO();
    private List< Documento > documentosEstudiante = new ArrayList<>();
    private OutputMessages outputMessages = new OutputMessages();
    private Documento documento = null;

    @FXML
    private Text nameText;

    @FXML
    private Text lastNameText;

    @FXML
    private Text matriculaText;

    @FXML
    private Text projectText;

    @FXML
    private Button returnButton;

    @FXML
    private Text errorText;

    @FXML
    private TableView< Documento > studentDocumentsTable;

    @FXML
    private TableColumn< Documento, String > nameColumn;

    @FXML
    private TableColumn< Documento, String > dateColumn;

    @FXML
    private Button uploadDocumento;

    @FXML
    private Button deleteDocument;

    @FXML
    private Button downloadDocumento;

    /**
     * Configura los componentes de la tabla en la pantalla
     * @param url no se utiliza como tal, lo requiere la interfaz
     * @param resourceBundle no se utiliza como tal, lo requiere la interfaz
     */
    @Override
    public void initialize( URL url, ResourceBundle resourceBundle ) {
        SetUserInformation();
        SetCellValueFactory();
        ConfigureFileChoosers();
        ShowDocuments();
    }

    /**
     * Coloca la información del usuario actual en los campos de texto requeridos
     */
    private void SetUserInformation() {
        nameText.setText( LoginSession.GetInstance().GetEstudiante().getNombres() );
        lastNameText.setText( LoginSession.GetInstance().GetEstudiante().GetApellidos() );
        matriculaText.setText( LoginSession.GetInstance().GetEstudiante().getMatricula() );
        SetProjectName();
    }

    /**
     * Configura las columnas de la tabla de la pantalla para que busquen el título
     * y la fecha de entrega de los objetos que se le agregan
     */
    private void SetCellValueFactory() {
        nameColumn.setCellValueFactory( new PropertyValueFactory<>( "titulo" ) );
        dateColumn.setCellValueFactory( new PropertyValueFactory<>( "fechaEntrega" ) );
    }

    /**
     * COnfigura el seleccionador de archivos con un título personalizado.
     */
    private void ConfigureFileChoosers() { fileChooser.setTitle( "Buscar Documento..." ); }

    /**
     * Muestra todos los documentos que se ubican dentro del expediente del estudiante
     * actual
     */
    private void ShowDocuments() {
        studentDocumentsTable.getItems().clear();
        documentosEstudiante = GetOnlyDocuments( documentos.ReadAll() );
        int claveExpediente = GetUserExpediente().GetClave();
        for( Documento documento : documentosEstudiante ) {
            if( documento.GetClaveExpediente() == claveExpediente ) {
                studentDocumentsTable.getItems().add( documento );
            }
        }
    }

    /**
     * Regresa una lista poblada únicamente con documentos del estudiante
     * actual
     * @param initialList lista de todos los documentos en la base de datos
     * @return lista con documentos
     */
    private List< Documento > GetOnlyDocuments( List< Documento > initialList ) {
        List< Reporte > reportList = reportes.ReadAll();
        for( int i = 0; i < initialList.size(); i++ ) {
            boolean removedItem = false;
            for( Reporte reporte : reportList ) {
                if( initialList.get( i ).getIdDocumento() == reporte.getIdDocumento() ) {
                    initialList.remove( i );
                    removedItem = true;
                }
            }
            if( removedItem ) {
                i--;
            }
        }
        return initialList;
    }

    /**
     * Busca el expediente del estudiante actual en la
     * base de datos
     * @return el expediente del estudiante actual
     */
    private Expediente GetUserExpediente() {
        List< Expediente > expedientesUsuarios = expedientes.ReadAll();
        Expediente userExpediente = null;
        for( Expediente expediente : expedientesUsuarios ) {
            if( expediente.GetMatricula().equals( LoginSession.GetInstance().GetEstudiante().getMatricula() ) ) {
                userExpediente = expediente;
            }
        }
        return userExpediente;
    }

    /**
     * Elimina un documento del expediente del estudiante
     * @param mouseEvent el clic del ratón que llamó al método
     */
    @FXML
    public void DeleteDocument( MouseEvent mouseEvent ) {
        if( IsDocumentSelected() ) {
            Alert deleteAlert = new Alert( Alert.AlertType.CONFIRMATION, outputMessages.DeleteDocumentConfirmation() );
            deleteAlert.showAndWait().ifPresent( response -> {
                if( response == ButtonType.OK ) {
                    documentos.Delete( studentDocumentsTable.getSelectionModel().getSelectedItem().getIdDocumento() );
                    ShowDocuments();
                }
            } );
        }
    }

    /**
     * Descarga un archivo de la base de datos a la máquina local del usuario
     * @param mouseEvent el clic del ratón que llamó al método
     */
    @FXML
    public void DownloadDocument( MouseEvent mouseEvent ) {
        if( IsDocumentSelected() ) {
            File directoryFile = GetDirectory( mouseEvent );
            CopyFile( documentos.Read( studentDocumentsTable.getSelectionModel().getSelectedItem().getIdDocumento() ).GetDescripcion(),
                      directoryFile );
        }
    }

    /**
     * Se regresa a la pantalla principal de estudiante
     * @param mouseEvent el clic del ratón que llamó al método
     */
    @FXML
    public void Return( MouseEvent mouseEvent ) { screenChanger.ShowStudentMainMenuScreen( mouseEvent, errorText ); }

    /**
     * Almace un documento seleccionado de la máquina local del usuario a
     * la base de datos
     * @param mouseEvent el clic del ratón que llamó al método
     */
    @FXML
    public void UploadDocument( MouseEvent mouseEvent ) {
        File document = GetFile( mouseEvent );
        if( document != null && DocumentNameDoesNotExist( GetDocument( document ) ) ) {
            documentos.Create( GetDocument( document ) );
            ShowDocuments();
        }
    }

    /**
     * Revisa que el nombre del documento introducido no exista en la base de datos
     * @param document el documento que se desea revisar
     * @return verdadero si NO existe en nombre en la base de datos, falso si sí
     */
    private boolean DocumentNameDoesNotExist( Documento document ) {
        boolean nameDoesNotExist = true;
        List< Documento > listaDocumentos = documentos.ReadAll();
        for( Documento ejemplar : listaDocumentos ) {
            if( ejemplar.GetClaveExpediente() == documento.GetClaveExpediente() &&
                    ejemplar.getTitulo().equals( documento.getTitulo() ) ) {
                nameDoesNotExist = false;
                errorText.setText( outputMessages.DocumentNameAlreadyExists() );
            }
        }
        return nameDoesNotExist;
    }

    /**
     * Recupera un archivo de la máquina local del usuario
     * @param mouseEvent el clic del ratón que llamó al método
     * @return el archivo de la máquina local
     */
    private File GetFile( MouseEvent mouseEvent ) {
        return fileChooser.showOpenDialog( ( ( Node )mouseEvent.getSource() ).getScene().getWindow() );
    }

    /**
     * Regresa un archivo con el directorio que seleccionó el usuario
     * @param mouseEvent el clic del ratón que llamó al método
     * @return un archivo
     */
    private File GetDirectory( MouseEvent mouseEvent ) {
        return directoryChooser.showDialog( ( ( Node )mouseEvent.getSource() ).getScene().getWindow() );
    }

    /**
     * Crea una instancia de Documento utilizando el archivo introducido
     * @param documentFile el archivo que se desea convertir a la intancia de Documento
     * @return una instancia de Documento
     */
    private Documento GetDocument( File documentFile ) {
        LocalDate currentDate = LocalDate.now();
        documento = new Documento( 0 , documentFile.getName(), documentFile, currentDate.toString(),
                GetUserExpediente().GetClave(), "", 0.0f );
        return documento;
    }

    /**
     * Revisa si se seleccionó un elemento en la tabla de StudentDocumentsTable
     * @return falso si NO se seleccionó un elemento en la tabla, verdadero si sí
     */
    private boolean IsDocumentSelected() {
        boolean isSelected = false;
        if( studentDocumentsTable.getSelectionModel().getSelectedItem() != null ) {
            isSelected = true;
        }
        return isSelected;
    }

    /**
     * Copia un archivo recuperado de la base de datos a la máquina local del
     * usuario.
     * @param dataBaseFile el archivo recuperado de la base de datos
     * @param directoryFile el archivo que contiene el path de la máquina local donde se desea almacenar el archivo
     */
    private void CopyFile( File dataBaseFile, File directoryFile ) {
        try {
            FileInputStream input = new FileInputStream( dataBaseFile );
            File outputFile = new File( FixFilePath( directoryFile.getAbsolutePath() + "\\" + dataBaseFile.getName() ) );
            FileOutputStream output = new FileOutputStream( outputFile );
            CreateFile( outputFile );
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
     * Crea un archivo en la máquina local del usuario en caso de no existir previamente
     * @param targetFile el archivo que se desea crear
     * @throws IOException
     */
    private void CreateFile( File targetFile ) throws IOException {
        if( !targetFile.exists() ) {
            targetFile.createNewFile();
        }
    }

    /**
     * Recupera el proyecto asignado del usuario y coloca su nombre en el
     * campo de texto projectText
     */
    private void SetProjectName() {
        projectText.setText( proyectos.Read( GetUserExpediente().GetIDProyecto() ).getNombre() );
    }
}