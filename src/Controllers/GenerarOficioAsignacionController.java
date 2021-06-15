package Controllers;

import Database.*;
import Entities.Estudiante;
import Entities.Expediente;
import javafx.stage.DirectoryChooser;
import Entities.ResponsableProyecto;
import Enumerations.EstadoEstudiante;
import Utilities.LoginSession;
import Utilities.OutputMessages;
import Utilities.ScreenChanger;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class GenerarOficioAsignacionController implements Initializable {
    private ScreenChanger screenChanger = new ScreenChanger();
    private EstudianteDAO estudiantes = new EstudianteDAO();
    private List< Estudiante > listaEstudiantes = new ArrayList< Estudiante>();
    private OutputMessages outputMessages = new OutputMessages();
    private ExpedienteDAO expedientes = new ExpedienteDAO();
    private ProyectoDAO proyectos = new ProyectoDAO();
    private ResponsableProyectoDAO responsables = new ResponsableProyectoDAO();
    private OrganizacionVinculadaDAO organizaciones = new OrganizacionVinculadaDAO();
    private ProyectosDeResponsablesDAO proyectosResponsables = new ProyectosDeResponsablesDAO();
    private ResponsablesOrganizacionDAO responsablesOrganizaciones = new ResponsablesOrganizacionDAO();
    private DirectoryChooser directoryChooser = new DirectoryChooser();
    private Paragraph presentacionParagraph;
    private Paragraph parrafoUno;
    private Paragraph parrafoDos;
    private Paragraph parragoTres;
    private Paragraph parrafoCuatro;
    private Paragraph parrafoFinal;
    private String documentName;
    private String nombresEstudiante;
    private String nombreProyecto;
    private String direccionOrganizacion;
    private String nombreOrganizacion;
    private String nombreCompletoResponsable;
    private String nombreCompletoCoordinador;

    @FXML
    private Text TxNombres;

    @FXML
    private Text TxApellidos;

    @FXML
    private Text TxNoTrabajador;

    @FXML
    private Text TxError;

    @FXML
    private TableView< Estudiante > TvEstudiante;

    @FXML
    private TableColumn< Estudiante, String > TcNombre;

    @FXML
    private TableColumn < Estudiante, String > TcMatricula;

    @FXML
    private Button BtGenerar;

    @FXML
    private Button BtRegresar;

    /**
     * Configura los elementos utilizados en la pantalla CrearProyecto
     * @param url un url sin utilizar
     * @param resourceBundle un conjunto de recursos no utilizados
     */
    @Override
    public void initialize( URL url, ResourceBundle resourceBundle ) {

        DatosUsuario();
        ValorColumnasEstudiante();
        MostrarEstudiantes();
    }

    /**
     * Coloca la información del usuario actual en los campos de texto de
     * nombres, apellidos y No.Trabajador
     */
    public void DatosUsuario(){
        TxNombres.setText( LoginSession.GetInstance().GetCoordinador().getNombres() );
        TxApellidos.setText( LoginSession.GetInstance().GetCoordinador().GetApellidos() );
        TxNoTrabajador.setText( LoginSession.GetInstance().GetCoordinador().GetNumeroPersonal() );
    }

    /**
     * Configura las columnas de la tabla estudiantes en esta pantalla
     */
    private void ValorColumnasEstudiante() {
        TcNombre.setCellValueFactory( new PropertyValueFactory<>( "nombreCompleto" ) );
        TcMatricula.setCellValueFactory( new PropertyValueFactory<>( "matricula"));
    }

    /**
     * Agrega todos los estudiantes disponibles a la tabla de estudiantes disponibles
     */
    private void MostrarEstudiantes() {
        listaEstudiantes = estudiantes.ReadAll();
        for( Estudiante estudiante : listaEstudiantes ){
            if( estudiante.getEstado() == EstadoEstudiante.ProyectoAsignado ) {
                estudiante.getNombreCompleto();
                estudiante.getMatricula();
                TvEstudiante.getItems().add( estudiante );
            }
        }
    }

    public void HandleGenerarOficioAsignacion( MouseEvent mouseEvent ) {
        if( TvEstudiante.getSelectionModel().getSelectedItem() != null ) {
            try {
                Estudiante estudiante = TvEstudiante.getSelectionModel().getSelectedItem();
                SetStrings( estudiante );
                
                var doc = new Document();
                PdfWriter.getInstance( doc, new FileOutputStream( GetTargetFile( mouseEvent, documentName ) ) );
                doc.open();

                var bold = new Font( Font.FontFamily.HELVETICA, 12, Font.BOLD );
                var style = new Font( Font.FontFamily.HELVETICA, 10 );
                presentacionParagraph = new Paragraph(
                        nombreCompletoResponsable.toUpperCase( Locale.ROOT ) + "\n" +
                        "RESPONSABLE DE PROYECTO \n" +
                        nombreOrganizacion.toUpperCase( Locale.ROOT ) + "\n" +
                        direccionOrganizacion.toUpperCase( Locale.ROOT ) + "\n" +
                        "XALAPA, VERACRUZ \n", bold );

                parrafoUno = new Paragraph( "En atención a su solicitud expresada a la Coordinación de Prácticas " +
                        "Profesionales de la Licenciatura en Ingeniería de Software, hacemos de su conocimiento que el C. " +
                        nombresEstudiante  + "estudiante de la Licenciatura con matrícula " + estudiante.getMatricula() +
                        ", ha sido asignado al proyecto " + nombreProyecto + " de la Universidad Veracruzana a su digno cargo a " +
                        "partir del 13 de Agosto del presente hasta cubrir 200 horas. Cabe mencionar que el estudiante cuenta con la " +
                        "formación y el perfil para las actividades a desempeñar.\n", style );

                parrafoDos = new Paragraph( "Anexo a este documento usted encontrará una copia del horario de las experiencias" +
                        " educativas que el estudiante asignado se encuentra cursando para que sea respetado y tomado en cuenta al momento " +
                        "de establecer el horario de realización de sus prácticas profesionales. Por otra parte, le solicito de la manera más" +
                        " atenta, haga llegar a la brevedad con el estudiante, el oficio de aceptación así como el plan de trabajo detallado" +
                        " del estudiante, así como el horario que cubrirá. Deberá indicar además, la forma en que se registrará la evidencia" +
                        " de asistencia y número de horas cubiertas. Es importante mencionar que el estudiante deberá presentar mensualmente " +
                        "un reporte de avances de sus prácticas. Este reporte de avances puede entregarse hasta con una semana de atraso por" +
                        " lo que le solicito de la manera más atenta sean elaborados y avalados (incluyendo sello si aplica) de manera oportuna" +
                        " para su entrega al Académico responsable de la experiencia de Prácticas de Ingeniería de Software. En relación a lo" +
                        " anterior, es importante que en el oficio de aceptación proporcione el nombre de la persona que supervisará y avalará" +
                        " en su dependencia la prestación de las Prácticas profesionales así como número telefónico, extensión (cuando aplique)" +
                        " y correo electrónico. Lo anterior con el fin de contar con el canal de comunicación que permita dar seguimiento al " +
                        "desempeño del estudiante. \n", style );

                parragoTres = new Paragraph( "Le informo que las prácticas de Ingeniería de Software forman parte de la currícula de" +
                        " la Licenciatura en Ingeniería de Software, por lo cual es necesaria su evaluación y de ahí la necesidad de realizar el" +
                        " seguimiento correspondiente. Es por ello que, durante el semestre el Académico a cargo de la experiencia educativa de" +
                        " prácticas de Ingeniería de Software realizará al menos un seguimiento de las actividades del estudiante por lo que será" +
                        " necesario mostrar evidencias de la asistencia del estudiante, así como de sus actividades. Este seguimiento podrá ser" +
                        " vía correo electrónico, teléfono o incluso mediante una visita a sus oficinas, por lo que le solicito de la manera más" +
                        " atenta, proporcione las facilidades requeridas en su caso. \n", style );

                parrafoCuatro = new Paragraph( "Sin más por el momento, agradezco su atención al presente reiterándome a sus apreciables" +
                        " órdenes. \n", style );

                parrafoFinal = new Paragraph( "Atentamente \n \n" + "________________________________ \n" + "Dr. " +
                        nombreCompletoCoordinador + "\n" + "Coordinador de prácticas profesionales \n" + "Licenciatura en Ingeniería de Software \n"
                        , style );

                ConfigureParagraphs();

                doc.add( presentacionParagraph );
                doc.add( parrafoUno );
                doc.add( parrafoDos );
                doc.add( parragoTres );
                doc.add( parrafoCuatro );
                doc.add( parrafoFinal );
                doc.close();

            } catch( Exception e ) {
                e.printStackTrace();
            }
        } else{
            TxError.setText( outputMessages.SelectionStudentNull() );
        }
    }

    /**
     * Permite cambiar la pantalla a la pantalla Principal de coordinador
     */
    public void MostrarPantallaPrincipalCoordinador( MouseEvent mouseEvent ) {
        screenChanger.MostrarPantallaGestionarEstudianesCoordinador( mouseEvent, TxError );
    }

    private Expediente GetExpedienteEstudiante( String matriculaEstudiante ) {
        List< Expediente > expedientesUsuarios = expedientes.ReadAll();
        Expediente userExpediente = null;
        for( Expediente expediente : expedientesUsuarios ) {
            if( expediente.GetMatricula().equals( matriculaEstudiante ) &&
                    expediente.GetActivo() ) {
                userExpediente = expediente;
            }
        }
        return userExpediente;
    }
    
    private void SetStrings( Estudiante estudiante ) {
        documentName = "OficioAsinacion_" + estudiante.getNombres() + estudiante.GetApellidos() + ".pdf";
        nombresEstudiante = estudiante.getNombres() + " " + estudiante.GetApellidos();
        nombreProyecto = GetNombreProyecto( GetExpedienteEstudiante( estudiante.getMatricula() ).GetIDProyecto() );
        direccionOrganizacion = GetDireccionOrganizacion( GetIdOrganizacion(
                GetIdResponsable( GetExpedienteEstudiante( estudiante.getMatricula() ).GetIDProyecto() ) ) );
        nombreOrganizacion = GetNombreOrganizacion( GetIdOrganizacion(
                GetIdResponsable( GetExpedienteEstudiante( estudiante.getMatricula() ).GetIDProyecto() ) ) );
        nombreCompletoResponsable = GetNombreCompletoResponsableProyecto(
                GetIdResponsable( GetExpedienteEstudiante( estudiante.getMatricula() ).GetIDProyecto() ) );
        nombreCompletoCoordinador = LoginSession.GetInstance().GetCoordinador().getNombres().toUpperCase( Locale.ROOT ) + " " +
                LoginSession.GetInstance().GetCoordinador().GetApellidos().toUpperCase();
    }
    
    private void ConfigureParagraphs() {
        presentacionParagraph.setAlignment( Element.ALIGN_LEFT );
        presentacionParagraph.setSpacingAfter( 22f );
        parrafoUno.setAlignment( Element.ALIGN_JUSTIFIED );
        parrafoUno.setFirstLineIndent( 75f );
        parrafoUno.setSpacingAfter( 20f );
        parrafoDos.setAlignment( Element.ALIGN_JUSTIFIED );
        parrafoDos.setFirstLineIndent( 75f );
        parrafoDos.setSpacingAfter( 20f );
        parragoTres.setAlignment( Element.ALIGN_JUSTIFIED );
        parragoTres.setFirstLineIndent( 75f );
        parragoTres.setSpacingAfter( 30f );
        parrafoCuatro.setAlignment( Element.ALIGN_JUSTIFIED );
        parrafoCuatro.setFirstLineIndent( 75f );
        parrafoCuatro.setSpacingAfter( 40f );
        parrafoFinal.setAlignment( Element.ALIGN_CENTER );
    }

    private File GetTargetFile( MouseEvent mouseEvent, String fileName ) {
        File outputFile = new File( FixFilePath( GetDirectory( mouseEvent ).getAbsolutePath() + "\\" + fileName ) );
        return outputFile;
    }

    private File GetDirectory( MouseEvent mouseEvent ) {
        return directoryChooser.showDialog( ( (Node)mouseEvent.getSource() ).getScene().getWindow() );
    }

    private String GetDireccionOrganizacion( int idOrganizacion ) {
        return organizaciones.Read( idOrganizacion ).getDireccion();
    }

    private int GetIdOrganizacion( int idResponsable ) {
        return responsablesOrganizaciones.ReadOrganizacion( idResponsable );
    }

    private int GetIdResponsable( int idProyecto ) {
        return proyectosResponsables.ReadResponsable( idProyecto );
    }

    private String GetNombreOrganizacion( int idOrganizacion ) {
        return organizaciones.Read( idOrganizacion ).getNombre();
    }

    private String GetNombreProyecto( int idProyecto ) {
        return proyectos.Read( idProyecto ).getNombre();
    }

    private String GetNombreCompletoResponsableProyecto( int idResponsable ) {
        ResponsableProyecto tempResponsable = responsables.Read( idResponsable );
        return tempResponsable.getNombres() + " " + tempResponsable.GetApellidos();
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
}


