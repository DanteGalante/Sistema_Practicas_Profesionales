package Controllers;

import Entities.Estudiante;
import Entities.Expediente;
import Enumerations.EstadoEstudiante;
import Utilities.LoginSession;
import Utilities.OutputMessages;
import Utilities.ScreenChanger;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

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
                GenerarReporte();
            }
        });
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
}
