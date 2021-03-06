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
import Database.ExpedienteDAO;
import Database.ProyectoDAO;
import Entities.*;
import Enumerations.EstadoEstudiante;
import Utilities.OutputMessages;
import Utilities.ScreenChanger;
import Utilities.SelectionContainer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import Utilities.LoginSession;
import javafx.stage.Screen;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Principal_Docente implements Initializable {

    private EstudianteDAO estudianteDAO = new EstudianteDAO();
    private List< Estudiante > grupo = new ArrayList< Estudiante >();
    private List< ArchivoConsulta > archivoConsultas = new ArrayList< ArchivoConsulta >();
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
        ConfigurarModoSeleccionTablas();
        ConfigurarColumnasTablas();
        MostrarGrupo();
        MostrarArchivosSubidos();
    }

    /**
     * Se configura el modo de seleccion de cada tabla de la pantalla.
     * La tabla de grupos solo se puede seleccionar un elemento a la vez, mientras que la de
     * archivos se puede seleccionar varios elementos
     */
    private void ConfigurarModoSeleccionTablas() {
        tbvGrupo.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tbvArchivosSubidos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    /**
     * Muestra los archivos de consulta subidos por el docente al sistema
     */
    public void MostrarArchivosSubidos() {
        tbvArchivosSubidos.getItems().clear();
        for ( ArchivoConsulta archivoConsulta : archivoConsultas ) {
            tbvArchivosSubidos.getItems().add( archivoConsulta );
        }
    }

    /**
     * Muestra los estudiantes pertenecientes al grupo asignado al docente.
     */
    public void MostrarGrupo() {
        tbvGrupo.getItems().clear();
        for ( Estudiante estudiante : grupo) {
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
        tcProyectoAsignado.setCellValueFactory( new PropertyValueFactory<>("proyecto") );

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

        try{
            grupo = ObtenerEstudiantesGrupo(LoginSession.GetInstance().GetDocente().GetNrc());
        }catch ( Exception exception ){
            exception.printStackTrace();
            errorText.setText( outputMessages.DatabaseConnectionFailed3() );
        }
    }

    /**
     * Obtiene los estudiantes de un grupo, especificado por el NRC del docente
     * @param nrc nrc del docente
     * @return Coleccion de estudiantes de un grupo. La coleccion estara vacia si no hay estudiantes
     * para el grupo del docente
     */
    private List<Estudiante> ObtenerEstudiantesGrupo( String nrc ) {
        List<Estudiante> auxiliar = ObtenerEstudiantesValidos();
        List<Estudiante> estudiantes = new ArrayList<>();

        for (Estudiante estudiante : auxiliar ) {
            if( estudiante.getNrc().equals( nrc ) ) {
                estudiantes.add( estudiante );
            }
        }

        return estudiantes;
    }

    /**
     * Obtiene todos los estudiantes con estatus RegistroAprobado, AsignacionPendiente
     * y ProyectoAsignado
     * @return Lista con estudiantes validos. La lista estara vacia si no hay estudiantes
     * con estatus validos
     */
    private List<Estudiante> ObtenerEstudiantesValidos() {
        List<Estudiante> auxiliar = estudianteDAO.ReadAll();
        List<Estudiante> estudiantes = new ArrayList<>();

        for( Estudiante estudiante : auxiliar ) {
            if( estudiante.getEstado().ordinal() != EstadoEstudiante.RegistroPendiente.ordinal()
            && estudiante.getEstado().ordinal() != EstadoEstudiante.Eliminado.ordinal()
            && estudiante.getEstado().ordinal() != EstadoEstudiante.Evaluado.ordinal() ) {

                String nombreProyecto = ObtenerNombreProyecto( estudiante );
                estudiante.setProyecto( nombreProyecto );
                estudiantes.add( estudiante );
            }
        }

        return estudiantes;
    }

    /**
     * Obtiene el nombre del proyecto de un estudiante, si cuenta con uno asignado
     * @param estudiante estudiante con el que se realiza la busqueda
     * @return Nombre del estudiante, si no encuentra nada devuelve null
     */
    private String ObtenerNombreProyecto( Estudiante estudiante ) {
        String nombreProyecto = null;
        List<Expediente> expedientes = ObtenerExpedientesValidos();

        int i = 0;
        Expediente expediente = new Expediente();
        while ( i < expedientes.size() && nombreProyecto == null){
            expediente = expedientes.get(i);
            if( expediente.GetMatricula().equals( estudiante.getMatricula() ) ) {
                nombreProyecto = RecuperarNombreProyecto( expediente.GetIDProyecto() );
            }
            i++;
        }

        return nombreProyecto;
    }

    /**
     * Recupera el nombre de un proyecto, dado su id
     * @param idProyecto id del proyecto en cuestion
     * @return nombre del proyecto en cuestion
     */
    private String RecuperarNombreProyecto(int idProyecto) {
        ProyectoDAO proyectoDAO = new ProyectoDAO();
        Proyecto proyecto = proyectoDAO.Read(idProyecto);
        return proyecto.getNombre();
    }

    /**
     * Obtiene los expedientes activos almacenados en la base de datos
     * @return Colección de Expedientes activos, devuelve una coleccion vacia si no encuentra nada
     */
    private List<Expediente> ObtenerExpedientesValidos() {
        ExpedienteDAO expedienteDAO = new ExpedienteDAO();
        List<Expediente> auxiliar = expedienteDAO.ReadAll();
        List<Expediente> expedientes = new ArrayList<>();

        for( Expediente expediente : auxiliar ){
            if( expediente.GetActivo() ) {
                expedientes.add( expediente );
            }
        }

        return expedientes;
    }

    /**
     * Recupera los archivos de consulta existentes en la base de datos.
     */
    public void RecuperarArchivosConsulta() {
        try{
            archivoConsultas = archivoConsultaDAO.ReadFilesByDocente( LoginSession.GetInstance().GetDocente().GetNumeroPersonal() );
        } catch ( Exception exception ){
            errorText.setText( outputMessages.DatabaseConnectionFailed3() );
        }
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
     * Cierra sesion y se muestra la pantalla de inicio de sesion
     *
     * @param mouseEvent evento del mouse que inicia el metodo
     */
    public void Logout( MouseEvent mouseEvent ) {
        LoginSession.GetInstance().Logout();
        screenChanger.ShowLoginScreen( mouseEvent, errorText );
    }

    /**
     * Revisa que el nombre del archivo de consulta introducido no exista en la base de datos
     * @param archivo el archivo que se desea revisar
     * @return true - si NO existe el nombre en la base de datos<p>
     *     falso - si existe el nombre en la base de datos
     */
    private boolean FileNameDoesNotExist( ArchivoConsulta archivo ) {
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
    private boolean verificarExtension( String tipoArchivo ) {
        return tipoArchivo.equals( "pdf" )  || tipoArchivo.equals( "docx" ) || tipoArchivo.equals( "doc" );
    }

    /**
     * Regresa la extension del documento
     * @return el tipo de documento
     */
    public String getTipoArchivo( File archivo ){
        int separador = archivo.getName().lastIndexOf('.');
        String tipo = ( separador == -1 ) ? "" : archivo.getName().substring( separador + 1 );
        return tipo;
    }

    /**
     * Genera una entidad ArchivoConsulta a partir de un File, con el fin de guardarlo en la base de datos
     * @param file Archivo que sera usado para hacer crear un ArchivoConsulta
     * @return Archivo consulta que sera guardado en la base de datos
     */
    public ArchivoConsulta GenerarArchivoConsulta( File file ) {
        ArchivoConsulta archivoConsulta = new ArchivoConsulta();

        archivoConsulta.SetTitulo( file.getName() );
        archivoConsulta.SetNumeroPersonal( LoginSession.GetInstance().GetDocente().GetNumeroPersonal() );
        archivoConsulta.SetDescripcion( file );

        return archivoConsulta;
    }

    /**
     * Regresa el archivo seleccionado por el usuario
     *
     * @param mouseEvent el evento que invocó el método
     * @return una instancia del archivo seleccionado tipo File
     */
    private File getFile( MouseEvent mouseEvent ) {
        LimpiarMensajesPantalla();
        return fileChooser.showOpenDialog( ( ( Node ) mouseEvent.getSource() ).getScene().getWindow() );
    }

    /**
     * Muestra la pantalla de consultar expediente de un estudiante especificado en esta pantalla.
     * @param mouseEvent evento que invoco el método
     */
    public void ClicConsultarExpediente( MouseEvent mouseEvent ){
        Estudiante estudianteElegido = ( Estudiante ) tbvGrupo.getSelectionModel().getSelectedItem();

        if ( estudianteElegido != null ) {
            SelectionContainer.GetInstance().setEstudianteElegido( estudianteElegido );
            screenChanger.ShowScreenConsultarExpediente( mouseEvent, errorText );
        } else {
            errorText.setText( outputMessages.EstudianteNoSeleccionado() );
        }
    }

    /**
     * Muestra la pantalla de "ReportarProblema" y cierra la actual
     * @param mouseEvent evento del mouse que inicia el método
     */
    public void ClicReportarProblema( MouseEvent mouseEvent ){
        screenChanger.ShowScreenReportarProblema_Docente( mouseEvent, errorText );
    }

    /**
     * Muestra una pantalla exploradora de archivos en la que se seleccionara un documento
     * que se almacenara en la base de datos como un archivo de consulta
     * @param mouseEvent evento del mouse que inicia el método
     */
    public void irPantallaSubirArchivos( MouseEvent mouseEvent ) {
        LimpiarMensajesPantalla();

        fileChooser.setTitle("Descargando archivo...");
        File file = getFile( mouseEvent );
        if ( file != null ) {
            if( verificarExtension( getTipoArchivo( file ) ) ){

                ArchivoConsulta nuevoArchivo = GenerarArchivoConsulta( file );

                if( FileNameDoesNotExist( nuevoArchivo ) ){
                    try {
                        archivoConsultaDAO.Create( nuevoArchivo );
                        successText.setText( outputMessages.UploadSuccesful() );
                    } catch ( Exception exception) {
                        errorText.setText( outputMessages.DatabaseConnectionFailed3() );
                    }
                }

                RecuperarArchivosConsulta();
                MostrarArchivosSubidos();
            }else{
                errorText.setText( outputMessages.InvalidFileExtension() );
            }
        }
    }

    /**
     * Elimina del sistema el archivo de consulta que se seleccione en esta pantalla.
     * @param mouseEvent evento del mouse que inicia el método
     */
    public void ClicEliminarArchivo( MouseEvent mouseEvent ) {
        LimpiarMensajesPantalla();

        if( tbvArchivosSubidos.getSelectionModel().getSelectedItems().size() > 0 ) {
            ArchivoConsulta[] archivosElim =
                    new ArchivoConsulta[ tbvArchivosSubidos.getSelectionModel().getSelectedItems().size() ];
            int i = 0;

            for ( ArchivoConsulta archivoConsultaElim :
            tbvArchivosSubidos.getSelectionModel().getSelectedItems() ) {
                archivosElim[ i ] = archivoConsultaElim;
                i++;
            }

            SelectionContainer.GetInstance().setArchivosConsulta( archivosElim );

            screenChanger.ShowScreenDeleteArchivosConsulta( mouseEvent, errorText );
        } else {
            errorText.setText( outputMessages.FileNotSelectedToDelete() );
        }
    }

    /**
     * Muestra la pantalla "DescargarArchivos" y cierra la actual.
     *
     * @param mouseEvent evento del mouse que inicia el metodo.
     */
    public void irPantallaDescargarArchivos( MouseEvent mouseEvent ) {
        LimpiarMensajesPantalla();

        Estudiante estudianteElegido = ( Estudiante ) tbvGrupo.getSelectionModel().getSelectedItem();

        if (estudianteElegido != null) {
            SelectionContainer.GetInstance().setEstudianteElegido( estudianteElegido );
            screenChanger.ShowScreenDescargarArchivoDocente( mouseEvent, errorText );
        } else {
            errorText.setText( outputMessages.EstudianteNoSeleccionado() );
        }
    }

    private void LimpiarMensajesPantalla() {
        errorText.setText("");
        successText.setText("");
    }
}