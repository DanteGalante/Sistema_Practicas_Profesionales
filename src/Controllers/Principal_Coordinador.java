package Controllers;

import Database.EstudianteDAO;
import Entities.Estudiante;
import Entities.Expediente;
import Entities.UsuarioUV;
import Enumerations.EstadoEstudiante;
import Utilities.OutputMessages;
import Utilities.ScreenChanger;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import Utilities.LoginSession;

import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;


public class Principal_Coordinador implements Initializable {
    private List< Estudiante > listaGrupos = new ArrayList< Estudiante>();
    private EstudianteDAO estudiantes = new EstudianteDAO();
    private List< Estudiante > listaEstudiantes = new ArrayList< Estudiante>();
    private ScreenChanger screenChanger = new ScreenChanger();
    private List< Expediente > listaExpedientes = new ArrayList<>();
    private OutputMessages outputMessages = new OutputMessages();

    @FXML
    private Label lbNombres;

    @FXML
    private Label lbApellidos;

    @FXML
    private Label lbNoTrabajador;

    @FXML
    private Button btnAsignarProyecto;

    @FXML
    private Button btnGenerarOficioAsignacion;

    @FXML
    private Button btnConsultarExpediente;

    @FXML
    private Button btnValidarInscripcion;

    @FXML
    private Button btnCerrarSesion;

    @FXML
    private TableView <Estudiante> tbGrupo;

    @FXML
    private TableColumn<Estudiante, String > clnNrc;

    @FXML
    private Button btnGenerarReporte;

    @FXML
    private Button btnVisualizarReportes;

    @FXML
    private Button btnConsultarOrganizacion;

    @FXML
    private ComboBox cbEstudianteDocente;

    @FXML
    private TextField tfBuscar;

    @FXML
    private Button btnBuscar;

    @FXML
    private TableView <UsuarioUV> tbEstudianteDocente;

    @FXML
    private TableColumn<UsuarioUV, String > clnMatriculaNoTrabajador;

    @FXML
    private Text errorText;

    /**
     * Configura los elementos utilizados en la pantalla Principal_Coordinador
     * @param url un url sin utilizar
     * @param resourceBundle un conjunto de recursos no utilizados
     */
    @Override
    public void initialize( URL url, ResourceBundle resourceBundle ) {

        DatosUsuario();
        ValorColumnasGrupo();
        MostrarGruposDisponibles();
        MostrarOpcionesComboBox();
        ValorColumnasEstudianteDocente();
        MostrarEstudiantesDisponibles();

    }

    /**
     * Coloca la información del usuario actual en los campos de texto de
     * nombres, apellidos y No.Trabajador
     */
    public void DatosUsuario(){
        lbNombres.setText( LoginSession.GetInstance().GetCoordinador().getNombres() );
        lbApellidos.setText( LoginSession.GetInstance().GetCoordinador().GetApellidos() );
        lbNoTrabajador.setText( LoginSession.GetInstance().GetCoordinador().GetNumeroPersonal() );
    }

    /**
     * Configura la columna de la tabla Grupo
     */
    private void ValorColumnasGrupo() {
        clnNrc.setCellValueFactory( new PropertyValueFactory<>( "nrc" ) );
    }

    /**
     * Agrega todos los nrc disponibles a la tabla de grupos disponibles
     */
    private void MostrarGruposDisponibles() {
        listaGrupos = estudiantes.ReadAll();
        for( Estudiante estudiante : listaGrupos ) {
            estudiante.getNrc();
            tbGrupo.getItems().add( estudiante );
        }
    }

    /**
     * Muestra las opciones disponibles para el ComboBox
     */
    private void MostrarOpcionesComboBox(){

        cbEstudianteDocente.getItems().add( "Estudiante" );
        cbEstudianteDocente.getItems().add( "Docente" );

    }

    /**
     * Configura la columna de la tabla Estudiantes/Docentes
     */
    private void ValorColumnasEstudianteDocente() {
        clnMatriculaNoTrabajador.setCellValueFactory( new PropertyValueFactory<>( "nombreCompleto" ) );
    }

    /**
     * Agrega todos los Estudiantes enlazados a los NRC de Grupo a la tabla de Estudiantes
     */
    private void MostrarEstudiantesDisponibles() {
        listaEstudiantes = estudiantes.ReadAll();
        for( Estudiante estudiante : listaEstudiantes ) {
            estudiante.getNombres();
            tbEstudianteDocente.getItems().add( estudiante );
        }
    }

    /**
     * Permite cambiar la pantalla a la pantalla GestionarOrganización
     */
    public void MostrarPantallaGestionarOrganizacion( MouseEvent mouseEvent ) {
        screenChanger.MostrarPantallaGestionarOrganizacion( mouseEvent, errorText );
    }

    /**
     * Permite cambiar la pantalla a la pantalla GestionarEstudiante
     */
    public void MostrarPantallaGestionarEstudiante( MouseEvent mouseEvent ) {
        screenChanger.MostrarPantallaGestionarEstudianesCoordinador( mouseEvent, errorText );
    }

    /**
     * Cierra la sesión actual y se regresa a la pantalla "IniciarSesión"
     * @param mouseEvent el evento de mouse que inicio el cambio
     */
    public void CerrarSesion( MouseEvent mouseEvent ) {
        LoginSession.GetInstance().Logout();
        screenChanger.ShowLoginScreen( mouseEvent, errorText );
    }

    public void ClicGenerarReporte( MouseEvent mouseEvent ) {
        Alert confirmAlert = new Alert( Alert.AlertType.CONFIRMATION, outputMessages.GenerarReporteConfirmation());
        confirmAlert.showAndWait().ifPresent( response -> {
            if( response == ButtonType.OK ) {
                GenerarReporte();
            }
        });
    }

    /**
     * Permite cambiar la pantalla a la pantalla VisualizarReporte_Coordinador
     */
    public void MostrarVisualizarReportes(MouseEvent mouseEvent){
        screenChanger.MostrarPantallaVisualizarReportesCoordinador( mouseEvent, errorText );
    }

    public void GenerarReporte() {
        try {
            var doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("ReporteSistema.pdf"));
            doc.open();

            var bold = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            var style = new Font(Font.FontFamily.HELVETICA, 14);
            var paragraph = new Paragraph("Reporte Inscripciones \n",bold);
            var table = new PdfPTable(1);
            var paragraphIntro = new Paragraph("Mediante la presente, se le comunica el avance academico de la " +
                    "experiencia educativa de prácticas profesionales durante el més de Junio. \n" +
                    "Se informa que actualmente contamos con " + EstudiantesInscritos() +  " estudiantes " +
                    " inscritos y " + EstudiantesBaja() + " estudiantes de baja. \n" +
                    "A continuación se le muestra una tabla de con las matriculas de los estudiantes inscritos", style);

            Stream.of("Matricula").forEach(table::addCell);

            Arrays.stream(MatriculaEstudianteInscrito().stream().toArray()).forEach(val ->{
                table.addCell(val.toString());
            });

            doc.add(paragraph);
            paragraphIntro.add(table);
            doc.add(paragraphIntro);
            doc.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Integer EstudiantesInscritos(){
        int cont = 0;
        List<String> listaAuxiliar = new ArrayList<>();
        for( Expediente expediente : listaExpedientes ) {
            listaAuxiliar.add( expediente.GetMatricula() );
        }
        for (Estudiante estudiante : listaEstudiantes){
            if(estudiante.getEstado() == EstadoEstudiante.ProyectoAsignado){
                cont ++;
            }
        }
        return cont;
    }

    public Integer EstudiantesBaja(){
        int cont = 0;
        List<String> listaAuxiliar = new ArrayList<>();
        for( Expediente expediente : listaExpedientes ) {
            listaAuxiliar.add( expediente.GetMatricula() );
        }
        for (Estudiante estudiante : listaEstudiantes){
            if(estudiante.getEstado() == EstadoEstudiante.Eliminado ){
                cont ++;
            }
        }
        return cont;
    }

    public List MatriculaEstudianteInscrito(){
        List<String> listaAuxiliar = new ArrayList<>();
        for (Estudiante estudiante : listaEstudiantes){
            if(estudiante.getEstado() == EstadoEstudiante.ProyectoAsignado){
                listaAuxiliar.add( estudiante.getMatricula() );
            }
        }
        return listaAuxiliar;
    }

    /**
     * Cambia a la pantalla ValidarInscripcion
     * @param mouseEvent el evento del mouse que inicio el cambio
     */
    public void ClicValidarInscripcion(MouseEvent mouseEvent) {
        screenChanger.ShowScreenValidarInscripcion( mouseEvent, errorText );
    }
}