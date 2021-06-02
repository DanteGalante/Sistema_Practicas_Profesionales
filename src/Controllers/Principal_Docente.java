/*
 * Autor: Dan Javier Olvera Villeda
 * Versión: 1.0
 * Fecha Creación: 30 - mar - 2021
 * Descripción:
 * Clase encargada de manejar los eventos de la pantalla
 * Principal_Docente.
 */
package Controllers;

import Database.ArchivoConsultaDAO;
import Database.EstudianteDAO;
import Entities.*;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import Utilities.LoginSession;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Principal_Docente implements Initializable {

    private EstudianteDAO estudianteDAO = new EstudianteDAO();
    private List<Estudiante> grupo = new ArrayList<Estudiante>();
    private List<ArchivoConsulta> archivoConsultas = new ArrayList<ArchivoConsulta>();
    private ScreenChanger screenChanger = new ScreenChanger();
    private OutputMessages outputMessages = new OutputMessages();
    private FileChooser fileChooser = new FileChooser();
    private ArchivoConsultaDAO archivoConsultaDAO = new ArchivoConsultaDAO();

    @FXML
    private Text errorText;
    @FXML
    private Text successText;
    @FXML
    private AnchorPane root;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbApellidos;
    @FXML
    private Label lbCedulaProfesional;
    @FXML
    private TableView<UsuarioUV> tbvGrupo;
    @FXML
    private TableColumn<UsuarioUV, String> tcMatricula;
    @FXML
    private TableColumn<UsuarioUV, String> tcNombre;
    @FXML
    private TableColumn<Proyecto, String> tcProyectoAsignado;
    @FXML
    private TableView<ArchivoConsulta> tbvArchivosSubidos;
    @FXML
    private TableColumn<ArchivoConsulta, String> tcNombreArchivo;
    @FXML
    private TableColumn<ArchivoConsulta, String> tcTipo;
    @FXML
    private TableColumn<ArchivoConsulta, String> tcTamanio;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SetUsuario();
        RecuperarGrupo();
        RecuperarArchivosConsulta();
        ConfigurarColumnasTablas();
        MostrarGrupo();
        MostrarArchivosSubidos();
    }

    public void MostrarArchivosSubidos() {
        tbvArchivosSubidos.getItems().clear();
        for (ArchivoConsulta archivoConsulta : archivoConsultas) {
            tbvArchivosSubidos.getItems().add(archivoConsulta);
        }
    }

    /**
     * Muestra los estudiantes pertenecientes al grupo asignado al docente.
     */
    public void MostrarGrupo() {
        tbvGrupo.getItems().clear();
        for (Estudiante estudiante : grupo) {
            tbvGrupo.getItems().add(estudiante);
        }
    }

    /**
     * Se configuran las columnas de las tablas, indicando que atributos de la entidad
     * van a ser mostrados por cada columna.
     */
    public void ConfigurarColumnasTablas() {
        //Tabla de grupo
        tcMatricula.setCellValueFactory( new PropertyValueFactory<>( "matricula" ) );
        tcNombre.setCellValueFactory( new PropertyValueFactory<>( "nombres" ) );
        //tcProyectoAsignado.setCellValueFactory( new PropertyValueFactory<>("") );

        //Tabla de archivos subidos por el docente
        tcNombreArchivo.setCellValueFactory( new PropertyValueFactory<>( "titulo" ) );
        tcTipo.setCellValueFactory( new PropertyValueFactory<>( "tipo" ) );
        tcTamanio.setCellValueFactory( new PropertyValueFactory<>( "tamanio" ) );
    }

    /**
     * Recupera los estudiantes existentes en la base de datos, tomando en cuenta el grupo al que pertenece
     * el docente que inicio sesion
     */
    public void RecuperarGrupo() {
        String nrc = LoginSession.GetInstance().GetDocente().GetNrc();
        grupo = estudianteDAO.ReadStudentsByGroup(nrc);
    }

    /**
     * Recupera los archivos de consulta existentes en la base de datos.
     */
    public void RecuperarArchivosConsulta() {
        archivoConsultas = archivoConsultaDAO.ReadFilesByDocente( LoginSession.GetInstance().GetDocente().GetNumeroPersonal() );
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
     * Cierra sesion y se muestra la pantalla de inicio de sesion
     *
     * @param mouseEvent evento del mouse que inicia el metodo
     */
    public void Logout(MouseEvent mouseEvent) {
        LoginSession.GetInstance().Logout();
        screenChanger.ShowLoginScreen(mouseEvent, errorText);
    }

    /**
     * Muestra la pantalla "DescargarArchivos" y cierra la actual.
     *
     * @param mouseEvent evento del mouse que inicia el metodo.
     */
    public void irPantallaDescargarArchivos(MouseEvent mouseEvent) {
        Estudiante estudianteElegido = (Estudiante) tbvGrupo.getSelectionModel().getSelectedItem();

        if (estudianteElegido != null) {
            SelectionContainer.GetInstance().setEstudianteElegido(estudianteElegido);
            screenChanger.ShowScreenDescargarArchivoDocente(mouseEvent, errorText);
        } else {
            errorText.setText(outputMessages.EstudianteNoSeleccionado());
        }
    }

    /**
     * Muestra una pantalla exploradora de archivos en la que se seleccionara un documento
     * que se almacenara en la base de datos como un archivo de consulta
     * @param mouseEvent evento del mouse que inicia el método
     */
    public void irPantallaSubirArchivos(MouseEvent mouseEvent) {
        fileChooser.setTitle("Descargando archivo...");
        File file = getFile( mouseEvent );
        if ( file != null ) {
            if( verificarExtension( getTipoArchivo(file) ) ){

                ArchivoConsulta nuevoArchivo = GenerarArchivoConsulta(file);

                if( FileNameDoesNotExist(nuevoArchivo) ){
                    archivoConsultaDAO.Create(nuevoArchivo);
                    successText.setText(outputMessages.UploadSuccesful());
                }

                RecuperarArchivosConsulta();
                MostrarArchivosSubidos();
            }else{
                errorText.setText( outputMessages.InvalidFileExtension() );
            }
        }
    }

    /**
     * Revisa que el nombre del archivo de consulta introducido no exista en la base de datos
     * @param archivo el archivo que se desea revisar
     * @return true - si NO existe el nombre en la base de datos<p>
     *     falso - si existe el nombre en la base de datos
     */
    private boolean FileNameDoesNotExist(ArchivoConsulta archivo ) {
        boolean nombreNoExiste = true;

        for( ArchivoConsulta ejemplar : archivoConsultas ) {
            if( ejemplar.GetNumeroPersonal().equals( archivo.GetNumeroPersonal() ) &&
                    ejemplar.getTitulo().equals( archivo.getTitulo() ) ) {
                nombreNoExiste = false;
                errorText.setText( outputMessages.DocumentNameAlreadyExists() );
            }
        }
        return nombreNoExiste;
    }

    /**
     * Verifica que la extension sea la correspondiente a los tipos de archivos validos
     * @param tipoArchivo cadena de texto que tiene la extension del archivo
     * @return true - es un archivo del tipo valido, es decir pdf, docx o doc. <p>
     *     false - es un tipo de archivo no valido, por ejemplo png, exe, jpg
     */
    private boolean verificarExtension(String tipoArchivo) {
        return tipoArchivo.equals("pdf")  || tipoArchivo.equals("docx") || tipoArchivo.equals("doc");
    }

    /**
     * Regresa la extension del documento
     * @return el tipo de documento
     */
    public String getTipoArchivo(File archivo){
        int separador = archivo.getName().lastIndexOf('.');
        String tipo = (separador == -1) ? "" : archivo.getName().substring(separador + 1);
        return tipo;
    }

    /**
     * Genera una entidad ArchivoConsulta a partir de un File, con el fin de guardarlo en la base de datos
     * @param file Archivo que sera usado para hacer crear un ArchivoConsulta
     * @return Archivo consulta que sera guardado en la base de datos
     */
    public ArchivoConsulta GenerarArchivoConsulta(File file) {
        ArchivoConsulta archivoConsulta = new ArchivoConsulta();

        archivoConsulta.SetTitulo(file.getName());
        archivoConsulta.SetNumeroPersonal(LoginSession.GetInstance().GetDocente().GetNumeroPersonal());
        archivoConsulta.SetDescripcion(file);

        return archivoConsulta;
    }

    /**
     * Regresa el archivo seleccionado por el usuario
     *
     * @param mouseEvent el evento que invocó el método
     * @return una instancia del archivo seleccionado tipo File
     */
    private File getFile(MouseEvent mouseEvent) {
        return fileChooser.showOpenDialog( ( (Node)mouseEvent.getSource() ).getScene().getWindow() );
    }

    /**
     *
     * @param mouseEvent
     */
    public void ClicConsultarExpediente( MouseEvent mouseEvent){
        Estudiante estudianteElegido = (Estudiante) tbvGrupo.getSelectionModel().getSelectedItem();

        if (estudianteElegido != null) {
            SelectionContainer.GetInstance().setEstudianteElegido(estudianteElegido);
            screenChanger.ShowScreenConsultarExpediente(mouseEvent, errorText);
        } else {
            errorText.setText(outputMessages.EstudianteNoSeleccionado());
        }
    }

    public void ClicReportarProblema( MouseEvent mouseEvent ){
        screenChanger.ShowScreenReportarProblema_Docente( mouseEvent, errorText );
    }

    public void ClicEliminarArchivo(MouseEvent mouseEvent) {
        ArchivoConsulta archivoEliminar = (ArchivoConsulta) tbvArchivosSubidos.getSelectionModel().getSelectedItem();
        if( archivoEliminar != null ){
            archivoConsultaDAO.Delete( archivoEliminar.GetId() );
            successText.setText( outputMessages.DeleteFileSucceded() );

            RecuperarArchivosConsulta();
            MostrarArchivosSubidos();
        }
    }
}