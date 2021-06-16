package Controllers;

/*
 * Autor: Alan Adair Morgado Morales
 * Versión: 1.0
 * Fecha Creación: 02 - mayo - 2021
 * Descripción:
 * Clase que se encarga de manejar las acciones de la pantalla
 * Consultar Expediente del sistema de prácticas profesionales.
 */

import Database.EstudianteDAO;
import Database.ExpedienteDAO;
import Database.ProyectoDAO;
import Entities.Estudiante;
import Entities.Expediente;
import Entities.OrganizacionVinculada;
import Entities.UsuarioUV;
import Utilities.LoginSession;
import Utilities.OutputMessages;
import Utilities.ScreenChanger;
import Utilities.SelectionContainer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ResourceBundle;


public class ConsultarExpedienteController implements Initializable {
    private ScreenChanger screenChanger = new ScreenChanger();
    private ExpedienteDAO expediente = new ExpedienteDAO();
    private Expediente expedienteInstancia = new Expediente();
    private EstudianteDAO estudiante = new EstudianteDAO();
    private Estudiante estudianteElegido= SelectionContainer.GetInstance().getEstudianteElegido();
    private OutputMessages outputMessages = new OutputMessages();

    @FXML
    private Text TxNombres;

    @FXML
    private Text TxApellidos;

    @FXML
    private Text TxNoTrabajador;

    @FXML
    private Text TxNombreEstudiante;

    @FXML
    private Text TxMatriculaEstudiante;

    @FXML
    private Text TxNRCEstudiante;

    @FXML
    private Text TxEstadoEstudiante;

    @FXML
    private Text TxCorreoEstudiante;

    @FXML
    private Text TxTelefonoEstudiante;

    @FXML
    private Text TxFechaAsignacionExpediente;

    @FXML
    private Text TxArchivosExpediente;

    @FXML
    private Text TXHrsAcumuladasExpediente;

    @FXML
    private Text TxError;

    @FXML
    private Button BtRegresar;

    /**
     * Configura los elementos utilizados en la pantalla CrearProyecto
     *
     * @param url            un url sin utilizar
     * @param resourceBundle un conjunto de recursos no utilizados
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatosUsuario();
        try {
            DatosExpediente();
        } catch ( Exception exception ) {
            TxError.setText( outputMessages.DatabaseConnectionFailed4() );
        }

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
     * Coloca la información del estudiante seleccionado en los campos de texto de
     * nombres, apellidos y No.Trabajador
     */
    public void DatosExpediente() {
        String matricula = estudianteElegido.getMatricula();
        TxNombreEstudiante.setText(estudianteElegido.getNombreCompleto());
        TxMatriculaEstudiante.setText(estudianteElegido.getMatricula());
        TxNRCEstudiante.setText(estudianteElegido.getNrc());
        TxEstadoEstudiante.setText(String.valueOf(estudianteElegido.getEstado()));
        TxCorreoEstudiante.setText(estudianteElegido.GetCorreo());
        TxTelefonoEstudiante.setText(estudianteElegido.GetTelefono());

        expedienteInstancia = expediente.ReadPorMatricula(matricula);
        TxFechaAsignacionExpediente.setText(expedienteInstancia.GetFechaAsignacion());
        TxArchivosExpediente.setText(String.valueOf(expedienteInstancia.GetNumeroArchivos()));
        TXHrsAcumuladasExpediente.setText(String.valueOf(expedienteInstancia.GetHorasAcumuladas()));
    }

    /**
     * Permite cambiar la pantalla a la pantalla Principal de coordinador
     */
    public void MostrarPantallaPrincipalCoordinador( MouseEvent mouseEvent ) {
        screenChanger.MostrarPantallaGestionarEstudianesCoordinador( mouseEvent, TxError );
    }

    public void MostrarPantallaDescargarArchivo_Coordinador(MouseEvent mouseEvent) {
        screenChanger.ShowScreenDescargarArchivoCoordinador(mouseEvent, TxError);
    }
}


