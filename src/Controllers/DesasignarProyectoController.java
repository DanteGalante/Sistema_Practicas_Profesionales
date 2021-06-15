package Controllers;

import Database.EstudianteDAO;
import Database.ExpedienteDAO;
import Database.ProyectoDAO;
import Entities.Estudiante;
import Entities.Expediente;
import Entities.Proyecto;
import Enumerations.EstadoEstudiante;
import Enumerations.EstadoProyecto;
import Utilities.LoginSession;
import Utilities.OutputMessages;
import Utilities.ScreenChanger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DesasignarProyectoController implements Initializable {
    private ScreenChanger screenChanger = new ScreenChanger();
    private EstudianteDAO estudiantes = new EstudianteDAO();
    private List< Estudiante > listaEstudiantes = new ArrayList< Estudiante>();
    private OutputMessages outputMessages = new OutputMessages();
    private ExpedienteDAO expediente = new ExpedienteDAO();
    private ProyectoDAO proyectos = new ProyectoDAO();

    @FXML
    private Text TxNombres;

    @FXML
    private Text TxApellidos;

    @FXML
    private Text TxNoTrabajador;

    @FXML
    private Text TxError;

    @FXML
    private Text TxSuccess;

    @FXML
    private TableView < Estudiante> TvEstudiante;

    @FXML
    private TableColumn < Estudiante, String> TcNombre;

    @FXML
    private TableColumn < Estudiante, String> TcMatricula;

    @FXML
    private Button BtDesasignarProyecto;

    @FXML
    private Button BtRegresar;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DatosUsuario();
        ValorColumnasEstudiante();
        MostrarEstudiantes();
    }

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

    /**
     * Permite cambiar la pantalla a la pantalla Principal
     */
    public void HandleDesasignarProyecto ( MouseEvent mouseEvent ) {
        TxError.setText("");
        TxSuccess.setText("");
        if (TvEstudiante.getSelectionModel().getSelectedItem() != null) {
            Estudiante estudiante = (Estudiante) TvEstudiante.getSelectionModel().getSelectedItem();
            if (estudiante.getEstado() == EstadoEstudiante.ProyectoAsignado) {
                String matricula = estudiante.getMatricula();
                BuscarExpediente(matricula);
                ActualizarTabla();
            } else {
                TxError.setText(outputMessages.SelectionStudentNull());
            }
        }
    }

    private void BuscarExpediente(String matricula){
        Expediente expedienteElegido = expediente.ReadPorMatricula(matricula);
        int idExpediente = expedienteElegido.GetClave();
        int idProyecto = expedienteElegido.GetIDProyecto();
        expedienteElegido.SetActivo(false);
        CambiarEstadoEstudiante(matricula);
        CambiarEstadoEstadoProyecto (idProyecto);
        expediente.Update(expedienteElegido);
        TxSuccess.setText(outputMessages.ExpedienteEliminado());
    }

    private void CambiarEstadoEstudiante(String matricula){
        Estudiante estudianteElegido = estudiantes.Read(matricula);
        estudianteElegido.SetEstadoEstudiante(EstadoEstudiante.AsignacionPendiente);
        estudiantes.Update(estudianteElegido);
    }

    private void CambiarEstadoEstadoProyecto(int idProyecto){
        Proyecto proyectoElegido = proyectos.Read(idProyecto);
        int estudiantesAsignados = proyectoElegido.GetEstudiantesAsignados();
        proyectoElegido.SetEstudiantesAsignados(estudiantesAsignados-1);
        //if(proyectoElegido.GetEstudiantesAsignados() < proyectoElegido.getNumEstudiantesRequeridos())
            proyectoElegido.SetEstado(EstadoProyecto.Disponible);
        proyectos.Update(proyectoElegido);
    }

    private void ActualizarTabla(){
        listaEstudiantes = estudiantes.ReadAll();
        for( Estudiante estudiante : listaEstudiantes ){
            if( estudiante.getEstado() == EstadoEstudiante.AsignacionPendiente ) {
                TvEstudiante.getItems().clear();
            }
        }
        MostrarEstudiantes();
    }


    /**
     * Permite cambiar la pantalla a la pantalla Principal de coordinador
     */
    public void MostrarPantallaPrincipalCoordinador( MouseEvent mouseEvent ) {
        screenChanger.MostrarPantallaGestionarEstudianesCoordinador( mouseEvent, TxError );
    }

}
