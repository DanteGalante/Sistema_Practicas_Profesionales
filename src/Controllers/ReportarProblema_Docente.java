package Controllers;

import Database.EstudianteDAO;
import Database.InformeProblemaDAO;
import Entities.Estudiante;
import Entities.InformeProblema;
import Utilities.InputValidator;
import Utilities.LoginSession;
import Utilities.OutputMessages;
import Utilities.ScreenChanger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReportarProblema_Docente implements Initializable {
    ScreenChanger screenChanger = new ScreenChanger();
    OutputMessages outputMessages = new OutputMessages();
    EstudianteDAO estudianteDAO = new EstudianteDAO();
    InformeProblemaDAO informeProblemaDAO = new InformeProblemaDAO();
    List< Estudiante > grupo = new ArrayList< Estudiante >();
    InputValidator inputValidator = new InputValidator();

    @FXML
    private Text errorText;
    @FXML
    private Text successText;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbApellidos;
    @FXML
    private Label lbCedulaProfesional;
    @FXML
    private TextField tfTitulo;
    @FXML
    private TextArea taContenido;
    @FXML
    private ChoiceBox< Estudiante > cbEstudianteProblema;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SetUsuario();
        RecuperarGrupo();
        MostrarEstudiantes();
    }

    /**
     * Muestra los estudiantes del grupo del docente en el componente Choicebox
     */
    private void MostrarEstudiantes() {
        for(Estudiante estudiante : grupo){
            cbEstudianteProblema.getItems().add(estudiante);
        }
    }

    /**
     * Recupera los estudiantes existentes en la base de datos, tomando en cuenta el grupo al que pertenece
     * el docente que inicio sesion
     */
    private void RecuperarGrupo() {
        String nrc = LoginSession.GetInstance().GetDocente().GetNrc();
        grupo = estudianteDAO.ReadStudentsByGroup(nrc);
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
     * Limpia los campos de texto en pantalla y el choicebox
     */
    private void LimpiarPantalla() {
        tfTitulo.setText("");
        taContenido.setText("");
        cbEstudianteProblema.getSelectionModel().clearSelection();
    }

    /**
     * Verifica que la información del informe sea valida
     * @param informeProblema
     * @return True si el informe tiene información valida, False si la información no es valida
     *         para introducir en la BD
     */
    private boolean InformeEsValido(InformeProblema informeProblema) {
        return inputValidator.IsInformeProblemaInformationValid( informeProblema );
    }

    /**
     * Verifica si hay campos vacios en los campos de texto
     * @return True Si los campos tienen valores, False si hay algun campo vacio
     */
    private boolean NoHayCamposVacios() {
        boolean noHayCamposVacios = tfTitulo.getLength() > 0 && taContenido.getLength() > 0;

        if( noHayCamposVacios == false ){
            errorText.setText( "Faltan campos por llenar" );
        } else {
           //TODO tfTitulo.getText().trim().
        }

        return noHayCamposVacios;
    }

    /**
     * Se ha seleccionado un estudiante para el reporte de problema
     * @return True si se ha seleccionado un estudiante, False si no se ha seleccionado un estudiante
     */
    private boolean HayEstudianteSeleccionado() {
        return ( cbEstudianteProblema.getSelectionModel().getSelectedItem() != null );
    }

    /**
     * Vuelve a la pantalla principal del docente
     * @param mouseEvent evento del mouse que inicia el metodo
     */
    public void ClicRegresar(MouseEvent mouseEvent) {
        screenChanger.ShowScreenPrincipalDocente(mouseEvent, errorText);
    }

    /**
     * Crea el reporte de problema, verificando si hay un estudiantes seleccionado, no hay campos vacios
     * el informe tenga informacion valida y el reporte no este repetido en la BD
     */
    public void ClicEnviar() {
        LimpiarMensajes();
        if( HayEstudianteSeleccionado() && NoHayCamposVacios()){
            InformeProblema nuevoInforme = new InformeProblema(
                    0, //ID InformeProblema
                    LocalDate.now().toString(), //Fecha de envio
                    LoginSession.GetInstance().GetDocente().GetNumeroPersonal(), //Numero de personal
                    cbEstudianteProblema.getSelectionModel().getSelectedItem().getNombreCompleto(),
                    tfTitulo.getText(), //Asunto del informe de problema
                    taContenido.getText() //Contenido del informe de problema
            );

            if( InformeEsValido( nuevoInforme ) ){
                informeProblemaDAO.Create(nuevoInforme);
                successText.setText( outputMessages.SavingProblemFormSuccessful() );
                LimpiarPantalla();
            }
        } else {
            errorText.setText( outputMessages.FaltanCamposPorLlenar() );
        }
    }

    /**
     * Limpia los mensajes de pantalla
     */
    private void LimpiarMensajes() {
        errorText.setText("");
        successText.setText("");
    }
}
