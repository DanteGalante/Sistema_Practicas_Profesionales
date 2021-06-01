/*
 * Autor: Dan Javier Olvera Villeda
 * Versión: 1.0
 * Fecha Creación: 31 - mar - 2021
 * Descripción:
 * Clase encargada de manejar los eventos de la pantalla
 * Principal_Docente.
 */
package Controllers;

import Database.ArchivoConsultaDAO;
import Database.EstudianteDAO;
import Entities.ArchivoConsulta;
import Entities.Estudiante;
import Entities.Proyecto;
import Entities.UsuarioUV;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

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
    private TableColumn<ArchivoConsulta, String> tcDescripcion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TODO
        SetUsuario();
        RecuperarGrupo();
        RecuperarArchivosConsulta();
        ConfigurarColumnasTablas();
        MostrarGrupo();
        MostrarArchivosSubidos();
    }

    /**
     * Muestra los archivos subidos por el docente al sistema
     */
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
        //TODO
        //Tabla de grupo
        tcMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        //tcProyectoAsignado.setCellValueFactory( new PropertyValueFactory<>("") );

        //Tabla de archivos subidos por el docente
        tcNombreArchivo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        //tcDescripcion.setCellValueFactory( new PropertyValueFactory<>("descripcion") );
    }

    /**
     * Recupera los estudiantes existentes en la base de datos, tomando en cuenta el grupo al que pertenece
     * el docente que inicio sesion
     */
    public void RecuperarGrupo() {
        String nrc = LoginSession.GetInstance().GetDocente().GetNrc();
        grupo = estudianteDAO.ReadByGroup(nrc);
    }

    /**
     * Recupera los archivos de consulta existentes en la base de datos.
     */
    public void RecuperarArchivosConsulta() {
        archivoConsultas = archivoConsultaDAO.ReadAll();
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

    public void irPantallaSubirArchivos(MouseEvent mouseEvent) {
        fileChooser.setTitle("Descargando archivo...");
        File file = getFile(mouseEvent);
        if ( file != null ) {
            if ( archivoValido(file) ) {
                archivoConsultaDAO.Create(generarArchivoConsulta(file));
                successText.setText(outputMessages.UploadSuccesful());

                RecuperarArchivosConsulta();
                MostrarArchivosSubidos();
            }
        }
    }

    private boolean archivoValido(File file) {
        boolean esValido = false;


        if (tipoArchivoValido(file)) {

        }

        return esValido;
    }

    public ArchivoConsulta generarArchivoConsulta(File file) {
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
        return fileChooser.showOpenDialog(((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    private boolean tipoArchivoValido(File file) {
        boolean tipoArchivoValido = false;
        String nombreArchivoSinExtension = "";
        //String
        char[] nombreArchivoConExtension = file.getName().toCharArray();

        for( char caracter : nombreArchivoConExtension ) {
            nombreArchivoSinExtension += caracter;
            if( caracter == '.' ){

            }
        }

        return tipoArchivoValido;
    }
}