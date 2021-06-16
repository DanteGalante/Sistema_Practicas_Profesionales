package Controllers;

import Database.EstudianteDAO;
import Entities.Estudiante;
import Entities.Expediente;
import Enumerations.EstadoEstudiante;
import Utilities.LoginSession;
import Utilities.OutputMessages;
import Utilities.ScreenChanger;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class Reportes_Coordinador implements Initializable {

    private ScreenChanger screenChanger = new ScreenChanger();
    private OutputMessages outputMessages = new OutputMessages();
    private List< Estudiante > listaEstudiantes = new ArrayList< Estudiante>();
    private List< Expediente > listaExpedientes = new ArrayList<>();
    private DirectoryChooser directoryChooser = new DirectoryChooser();
    private EstudianteDAO estudianteDAO = new EstudianteDAO();

    @FXML
    private Label lbNombres;

    @FXML
    private Label lbApellidos;

    @FXML
    private Label lbNoTrabajador;

    @FXML
    private Button btnReportes;

    @FXML
    private Button btnEstudiantes;

    @FXML
    private Button btnOrganizaciones;

    @FXML
    private Button btnProyectos;

    @FXML
    private Button btnCerrarSesion;

    @FXML
    private Text errorText;

    @FXML
    private Text successText;

    @FXML
    private Button btnReporteInscripcion;

    @FXML
    private Button btnVisualizarReportes;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listaEstudiantes = estudianteDAO.ReadAll();
        DatosUsuario();
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
     * Permite cambiar la pantalla a la pantalla GestionarEstudiante
     */
    public void MostrarPantallaGestionarEstudiante( MouseEvent mouseEvent ) {
        screenChanger.MostrarPantallaGestionarEstudianesCoordinador( mouseEvent, errorText );
    }

    /**
     * Permite cambiar la pantalla a la pantalla GestionarReportes
     */
    public void MostrarPantallaGestionarReporte( MouseEvent mouseEvent ) {
        screenChanger.MostrarPantallaGestionarReporteCoordinador( mouseEvent, errorText );
    }

    /**
     * Permite cambiar la pantalla a la pantalla GestionarReportes
     */
    public void MostrarPantallaGestionarOrganizacion( MouseEvent mouseEvent ) {
        screenChanger.MostrarPantallaGestionarOrganizacion( mouseEvent, errorText );
    }

    /**
     * Permite cambiar la pantalla a la pantalla GestionarReportes
     */
    public void MostrarPantallaGestionarProyecto( MouseEvent mouseEvent ) {
        screenChanger.MostrarPantallaGestionarProyecto( mouseEvent, errorText );
    }

    /**
     * Cierra la sesión actual y se regresa a la pantalla "IniciarSesión"
     * @param mouseEvent el evento de mouse que inicio el cambio
     */
    public void CerrarSesion( MouseEvent mouseEvent ) {
        LoginSession.GetInstance().Logout();
        screenChanger.ShowLoginScreen( mouseEvent, errorText );
    }

    /**
     * Permite cambiar la pantalla a la pantalla VisualizarReporte_Coordinador
     */
    public void MostrarVisualizarReportes(MouseEvent mouseEvent){
        screenChanger.MostrarPantallaVisualizarReportesCoordinador( mouseEvent, errorText );
    }

    public void ClicGenerarReporte( MouseEvent mouseEvent ) {
        Alert confirmAlert = new Alert( Alert.AlertType.CONFIRMATION, outputMessages.GenerarReporteConfirmation());
        confirmAlert.showAndWait().ifPresent( response -> {
            if( response == ButtonType.OK ) {
                GenerarReporte(mouseEvent);
            }
        });
    }

    public void GenerarReporte(MouseEvent mouseEvent) {
        try {
            var doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream( GetTargetFile( mouseEvent, "ReporteSistema.pdf" ) ) );
            doc.open();

            var bold = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            var style = new Font(Font.FontFamily.HELVETICA, 14);
            var paragraph = new Paragraph("Reporte Inscripciones \n",bold);
            var table = new PdfPTable(3);
            var paragraphIntro = new Paragraph("Mediante la presente, se le comunica el avance academico de la " +
                    "experiencia educativa de prácticas profesionales durante el més de Junio. \n" +
                    "Se informa que actualmente contamos con " + EstudiantesInscritos() +  " estudiantes " +
                    " inscritos y " + EstudiantesBaja() + " estudiantes de baja. \n" +
                    "A continuación se le muestra una tabla de con las matriculas de los estudiantes inscritos", style);


            table.addCell(new PdfPCell(new Paragraph("Nombres")));
            table.addCell(new PdfPCell(new Paragraph("Apellidos")));
            table.addCell(new PdfPCell(new Paragraph("Matricula")));

            for( Estudiante estudiante : AllStudents() ){
                table.addCell( new PdfPCell( new Paragraph( estudiante.getNombres() ) ) );
                table.addCell( new PdfPCell( new Paragraph( estudiante.GetApellidos() ) ) );
                table.addCell( new PdfPCell( new Paragraph( estudiante.getMatricula()) ) );
            }



            doc.add(paragraph);
            paragraphIntro.add(table);
            doc.add(paragraphIntro);
            doc.close();

        }catch(NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private File GetTargetFile(MouseEvent mouseEvent, String fileName ) {
        File outputFile = new File( FixFilePath( GetDirectory( mouseEvent ).getAbsolutePath() + "\\" + fileName ) );
        return outputFile;
    }

    private File GetDirectory( MouseEvent mouseEvent ) {
        return directoryChooser.showDialog( ( (Node)mouseEvent.getSource() ).getScene().getWindow() );
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

    public Integer EstudiantesInscritos(){
        int cont = 0;
        List<String> listaAuxiliar = new ArrayList<>();
        for( Expediente expediente : listaExpedientes ) {
            listaAuxiliar.add( expediente.GetMatricula() );
        }
        for (Estudiante estudiante : listaEstudiantes){
            if(estudiante.getEstado() == EstadoEstudiante.Evaluado ||
                    estudiante.getEstado() == EstadoEstudiante.RegistroAprobado ||
                    estudiante.getEstado() == EstadoEstudiante.AsignacionPendiente ||
                    estudiante.getEstado() == EstadoEstudiante.ProyectoAsignado){
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
            if(estudiante.getEstado() == EstadoEstudiante.Eliminado || estudiante.getEstado() == EstadoEstudiante.RegistroPendiente ){
                cont ++;
            }
        }
        return cont;
    }

    public List MatriculaEstudianteInscrito(){
        List<String> listaAuxiliar = new ArrayList<>();
        for (Estudiante estudiante : listaEstudiantes){
            if(estudiante.getEstado() == EstadoEstudiante.Evaluado ||
                    estudiante.getEstado() == EstadoEstudiante.RegistroAprobado ||
                    estudiante.getEstado() == EstadoEstudiante.AsignacionPendiente ||
                    estudiante.getEstado() == EstadoEstudiante.ProyectoAsignado){
                listaAuxiliar.add( estudiante.getMatricula() );
            }
        }
        return listaAuxiliar;
    }

    public List<Estudiante> AllStudents(){
        List<Estudiante> listaAuxiliar = new ArrayList<>();
        for (Estudiante estudiante : listaEstudiantes){
            if(estudiante.getEstado() == EstadoEstudiante.Evaluado ||
                    estudiante.getEstado() == EstadoEstudiante.RegistroAprobado ||
                    estudiante.getEstado() == EstadoEstudiante.AsignacionPendiente ||
                    estudiante.getEstado() == EstadoEstudiante.ProyectoAsignado){
                listaAuxiliar.add( estudiante );
            }
        }
        return listaAuxiliar;
    }
}
