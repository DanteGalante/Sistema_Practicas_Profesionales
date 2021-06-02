package Controllers;

import Database.*;
import Entities.*;
import Enumerations.EstadoProyecto;
import Utilities.ScreenChanger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import Utilities.LoginSession;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProjectDetailsController implements Initializable {
    private ScreenChanger screenChanger = new ScreenChanger();
    private ExpedienteDAO expedientes = new ExpedienteDAO();
    private ProyectoDAO proyectos = new ProyectoDAO();
    private EstudianteDAO estudiantes = new EstudianteDAO();
    private OrganizacionVinculadaDAO organizaciones = new OrganizacionVinculadaDAO();
    private ResponsableProyectoDAO responsables = new ResponsableProyectoDAO();
    private ResponsablesOrganizacionDAO responsablesOrganizacion = new ResponsablesOrganizacionDAO();
    private ProyectosDeResponsablesDAO responsablesProyectos = new ProyectosDeResponsablesDAO();
    private Proyecto proyecto;
    private OrganizacionVinculada organizacion;
    private ResponsableProyecto responsable;

    @FXML
    private Text nameText;

    @FXML
    private Text lastNameText;

    @FXML
    private Text matriculaText;

    @FXML
    private Text projectText;

    @FXML
    private Text projectDetailText;

    @FXML
    private Button returnButton;

    @FXML
    private Text errorText;

    @FXML
    private Text studentName1;

    @FXML
    private Text studentName2;

    @FXML
    private Text studentName3;

    @FXML
    private Text studentName4;

    @FXML
    private Text projectNameText;

    @FXML
    private Text cupoText;

    @FXML
    private Text organizacionText;

    @FXML
    private Text direccionText;

    @FXML
    private Text responsableText;

    @FXML
    private Text correoText;

    @FXML
    private Text telefonoText;

    @Override
    public void initialize( URL url, ResourceBundle resourceBundle ) {
        SetUserInformation();
        SetProjectTexts();
        SetResponsableTexts();
        SetOrganizacionTexts();
        SetStudentNames( GetStudentNames() );
    }

    private void SetUserInformation() {
        nameText.setText( LoginSession.GetInstance().GetEstudiante().getNombres() );
        lastNameText.setText( LoginSession.GetInstance().GetEstudiante().GetApellidos() );
        matriculaText.setText( LoginSession.GetInstance().GetEstudiante().getMatricula() );
    }

    @FXML
    void Return( MouseEvent mouseEvent ) {
        screenChanger.ShowStudentMainMenuScreen( mouseEvent, errorText );
    }

    private void SetProjectTexts() {
        proyecto = proyectos.Read( GetUserExpediente().GetIDProyecto() );
        projectNameText.setText( proyecto.getNombre() );
        projectText.setText( proyecto.getNombre() );
        projectDetailText.setText( proyecto.GetDescripcion() );
        cupoText.setText( Integer.toString( proyecto.getNumEstudiantesRequeridos() ) );
    }

    private void SetResponsableTexts() {
        responsable = responsables.Read( responsablesProyectos.ReadResponsable( proyecto.getIdProyecto() ) );
        responsableText.setText( responsable.GetNombres() + " " + responsable.GetApellidos() );
        correoText.setText( responsable.GetCorreo() );
        telefonoText.setText( responsable.GetTelefono() );
    }

    private void SetOrganizacionTexts() {
        organizacion = organizaciones.Read( responsablesOrganizacion.ReadOrganizacion( responsable.getIdResponsableProyecto() ) );
        organizacionText.setText( organizacion.getNombre() );
        direccionText.setText( organizacion.getDireccion() );
    }

    private void SetStudentNames( List< String > students ) {
        switch( students.size() ) {
            case 1:
                studentName1.setText( students.get( 0 ) );
                break;

            case 2:
                studentName1.setText( students.get( 0 ) );
                studentName2.setText( students.get( 1 ) );
                break;

            case 3:
                studentName1.setText( students.get( 0 ) );
                studentName2.setText( students.get( 1 ) );
                studentName3.setText( students.get( 2 ) );
                break;

            case 4:
                studentName1.setText( students.get( 0 ) );
                studentName2.setText( students.get( 1 ) );
                studentName3.setText( students.get( 2 ) );
                studentName4.setText( students.get( 3 ) );
                break;
        }
    }

    private List< String > GetStudentNames() {
        List< String > studentNames = new ArrayList<>();
        for( Expediente expedienteEstudiante : expedientes.ReadAll() )
        {
            if( expedienteEstudiante.GetIDProyecto() == proyecto.getIdProyecto() ) {
                studentNames.add( estudiantes.Read( expedienteEstudiante.GetMatricula() ).getNombres() + " " +
                                  estudiantes.Read( expedienteEstudiante.GetMatricula() ).GetApellidos() );
            }
        }
        return studentNames;
    }

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