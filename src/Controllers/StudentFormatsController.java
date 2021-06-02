/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 30 - abr - 2021
 * Descripción:
 * Clase encargada de manejar los eventos de la pantalla
 * Formatos_Estudiante.
 */
package Controllers;

import Database.*;
import Entities.ArchivoConsulta;
import Entities.Docente;
import Entities.Expediente;
import Enumerations.EstadoProyecto;
import Utilities.ScreenChanger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import Utilities.LoginSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Clase encargada de manejar los eventos de la pantalla
 * Formatos_Estudiante.
 */
public class StudentFormatsController implements Initializable{
    private ScreenChanger screenChanger = new ScreenChanger();
    private ArchivoConsultaDAO archivos = new ArchivoConsultaDAO();
    private RegistroGrupoDAO registros = new RegistroGrupoDAO();
    private ProyectoDAO proyectos = new ProyectoDAO();
    private ExpedienteDAO expedientes = new ExpedienteDAO();
    private DocenteDAO docentes = new DocenteDAO();
    private DirectoryChooser directoryChooser = new DirectoryChooser();

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
    private TableView< ArchivoConsulta > formatosTable;

    @FXML
    private TableColumn< ArchivoConsulta, String > nameColumn;

    @FXML
    private Button entregarReporte;

    /**
     * Configura los componentes de la pantalla Formatos_Estudiante
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SetUserInformation();
        SetCellValueFactory();
        ShowFiles();
    }

    /**
     * Coloca la información del usuario actual en los campos de texto
     * nameText, lastNameText y matriculaText
     */
    private void SetUserInformation() {
        nameText.setText( LoginSession.GetInstance().GetEstudiante().getNombres() );
        lastNameText.setText( LoginSession.GetInstance().GetEstudiante().GetApellidos() );
        matriculaText.setText( LoginSession.GetInstance().GetEstudiante().getMatricula() );
        SetProjectName();
    }

    /**
     * Configura las columnas de la tabla mostrada en esta pantalla
     */
    private void SetCellValueFactory() {
        nameColumn.setCellValueFactory( new PropertyValueFactory<>( "titulo" ) );
    }

    /**
     * Muestra todos los reportes almacenados en la base de datos del estudiante
     * actual
     */
    private void ShowFiles() {
        formatosTable.getItems().clear();
        for( ArchivoConsulta archivo : archivos.ReadAll() )
        {
            if( archivo.GetNumeroPersonal().equals( GetNumeroPersonal() ) ) {
                formatosTable.getItems().add( archivo );
            }
        }
    }

    /**
     * Regresa el número personal del docente encargado del grupo del
     * estudiante
     * @return el número personal de docente
     */
    private String GetNumeroPersonal() {
        String numeroPersonal = "";
        String nrcEstudiante = LoginSession.GetInstance().GetEstudiante().getNrc();
        for( Docente docente : docentes.ReadAll() ) {
            if( docente != null && docente.GetNrc().equals( nrcEstudiante ) ) {
                numeroPersonal = docente.GetNumeroPersonal();
            }
        }
        return numeroPersonal;
    }

    /**
     * Descarga un archivo de formato a la máquina local del usuario
     * @param mouseEvent el evento de mouse que inicio la descarga
     */
    @FXML
    void DownloadFormat( MouseEvent mouseEvent ) {
        if( IsFileSelected() ) {
            File directoryFile = GetDirectory( mouseEvent );
            CopyFile( archivos.Read( formatosTable.getSelectionModel().getSelectedItem().GetId() ).GetDescripcion(),
                    directoryFile );
        }
    }

    /**
     * Cambia la pantalla actual a la pantalla MenuPrincipal_Estudiante
     * @param mouseEvent el evento de mouse que inicio el cambio
     */
    @FXML
    void Return( MouseEvent mouseEvent ) {
        screenChanger.ShowStudentMainMenuScreen( mouseEvent, errorText );
    }

    /**
     * Revisa si se ha seleccionado un elemento de la tabla
     * formatosTable
     * @return true si hay un elemento seleccionado, false si no
     */
    private boolean IsFileSelected() {
        boolean isSelected = false;
        if( formatosTable.getSelectionModel().getSelectedItem() != null ) {
            isSelected = true;
        }
        return isSelected;
    }

    /**
     * Regresa un archivo con el directorio seleccionado por el usuario
     * @param mouseEvent el evento de mouse que inicio el cambio
     * @return un archivo con el directorio seleccionado por el usuario
     */
    private File GetDirectory( MouseEvent mouseEvent ) {
        return directoryChooser.showDialog( ( (Node)mouseEvent.getSource() ).getScene().getWindow() );
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

    /**
     * Recupera el expediente del usuario actual
     * @return una instancia del expediente
     */
    private Expediente GetUserExpediente() {
        List< Expediente > expedienteList = expedientes.ReadAll();
        Expediente userExpediente = null;
        for( Expediente expediente : expedienteList ) {
            if( expediente.GetMatricula().equals( LoginSession.GetInstance().GetEstudiante().getMatricula() ) &&
                    proyectos.Read( expediente.GetIDProyecto() ).GetEstado() == EstadoProyecto.Asignado ) {
                userExpediente = expediente;
            }
        }
        return userExpediente;
    }
}
