package Controllers;

import Database.EstudianteDAO;
import Database.ExpedienteDAO;
import Database.ProyectoDAO;
import Database.ProyectosSeleccionadosDAO;
import Entities.Estudiante;
import Entities.Expediente;
import Entities.Proyecto;
import Enumerations.EstadoEstudiante;
import Enumerations.EstadoProyecto;
import Utilities.LoginSession;
import Utilities.OutputMessages;
import Utilities.ScreenChanger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.time.LocalDate;

public class AsignarProyectoAEstudianteController implements Initializable {
    private ScreenChanger screenChanger = new ScreenChanger();
    private EstudianteDAO estudiantes = new EstudianteDAO();
    private List< Estudiante > listaEstudiantes = new ArrayList< Estudiante>();
    private ProyectoDAO proyectos = new ProyectoDAO();
    private List <Proyecto> listaProyecto = new ArrayList<>();
    private OutputMessages outputMessages = new OutputMessages();
    private ExpedienteDAO expediente = new ExpedienteDAO();
    private ProyectosSeleccionadosDAO proyectosSeleccionados = new ProyectosSeleccionadosDAO();
    private List<Integer> listaProyectosSeleccionados = new ArrayList<Integer>();
    private List<Proyecto> listaProyectoEstudiante = new ArrayList<>();

    @FXML
    private Text TxNombres;

    @FXML
    private Text TxApellidos;

    @FXML
    private Text TxNoTrabajador;

    @FXML
    private Text TxSuccess;

    @FXML
    private Text TxError;

    @FXML
    private TableColumn < Estudiante, String> TcNombre;

    @FXML
    private TableColumn < Estudiante, String> TcMatricula;

    @FXML
    private TableColumn < Proyecto, String> TcProyecto;

    @FXML
    private TableColumn TcPreferenciasProyecto;

    @FXML
    private TableView < Estudiante> TvEstudiante;

    @FXML
    private TableView < Proyecto> TvProyecto;


    @FXML
    private TableView TvPreferenciaProyecto;

    @FXML
    private Button BtRegresar;

    @FXML
    private Button BtAsignarProyecto;

    /**
     * Configura los elementos utilizados en la pantalla CrearProyecto
     *
     * @param url un url sin utilizar
     * @param resourceBundle un conjunto de recursos no utilizados
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatosUsuario();
        ValorColumnasEstudiante();
        ValorColumnasProyecto();
        ValorColumnasProyectoSeleccionados();
        try {
            MostrarEstudiantes();
            MostrarProyectos();
        } catch ( Exception exception ) {
            TxError.setText(outputMessages.DatabaseConnectionFailed4());
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
     * Configura las columnas de la tabla estudiantes en esta pantalla
     */
    private void ValorColumnasEstudiante() {
        TcNombre.setCellValueFactory( new PropertyValueFactory<>( "nombreCompleto" ) );
        TcMatricula.setCellValueFactory( new PropertyValueFactory<>( "matricula"));
        TvEstudiante.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Estudiante>() {
            @Override
            public void changed(ObservableValue<? extends Estudiante> observableValue, Estudiante estudiante, Estudiante t1) {
                if (t1 != null) {
                    String matricula = t1.getMatricula();
                    listaProyectosSeleccionados = proyectosSeleccionados.Read(matricula);
                    ActualizarTablaPreferenciaProyectos();
                }
            }
        });

    }

    /**
     * Configura las columnas de la tabla proyectos en esta pantalla
     */
    private void ValorColumnasProyecto() {
        TcProyecto.setCellValueFactory( new PropertyValueFactory<>("nombre"));
    }

    /**
     * Configura las columnas de la tabla preferencias proyecto en esta pantalla
     */
    private void ValorColumnasProyectoSeleccionados() {
        TcPreferenciasProyecto.setCellValueFactory( new PropertyValueFactory<>("nombre"));
    }

    /**
     * Agrega todos los estudiantes disponibles a la tabla de estudiantes disponibles
     */
    private void MostrarEstudiantes() {
        listaEstudiantes = estudiantes.ReadAll();
        for( Estudiante estudiante : listaEstudiantes ){
            if( estudiante.getEstado() == EstadoEstudiante.AsignacionPendiente ) {
                estudiante.getNombreCompleto();
                estudiante.getMatricula();
                TvEstudiante.getItems().add( estudiante );
            }
        }
    }

    /**
     * Agrega todos los proyectos disponibles a la tabla de proyectos disponibles
     */
    private void MostrarProyectos() {
        listaProyecto = proyectos.ReadAll();
        for( Proyecto proyecto : listaProyecto ) {
            if( proyecto.GetEstado() == EstadoProyecto.Disponible ) {
                proyecto.getNombre();
                TvProyecto.getItems().add( proyecto );
            }
        }
    }

    /*
    public void HandleBuscarPreferenciasProyecto( MouseEvent mouseEvent ) {
        TvPreferenciaProyecto.getItems().clear();
        Estudiante estudiante = (Estudiante) TvEstudiante.getSelectionModel().getSelectedItem();
        String matricula = estudiante.getMatricula();
        listaProyectosSeleccionados = proyectosSeleccionados.Read(matricula);
        MostrarPreferenciasProyecto();
    }
    */

    public void MostrarPreferenciasProyecto(){
        for( int id : listaProyectosSeleccionados) {
            Proyecto proyectoSeleccionado = proyectos.Read(id);
            listaProyectoEstudiante.add(proyectoSeleccionado);
        }
        for (Proyecto proyecto : listaProyectoEstudiante) {
            if (proyecto.GetEstado() == EstadoProyecto.Disponible) {
                proyecto.getNombre();
                TvPreferenciaProyecto.getItems().add(proyecto);
            }
        }

        listaProyectosSeleccionados.clear();
    }

    public void HandleAsignarProyecto ( MouseEvent mouseEvent ) {
        TxError.setText("");
        TxSuccess.setText("");
        if(TvEstudiante.getSelectionModel().getSelectedItem() != null){
            if(TvProyecto.getSelectionModel().getSelectedItem() != null){
                Estudiante estudiante = (Estudiante) TvEstudiante.getSelectionModel().getSelectedItem();
                Proyecto proyecto = (Proyecto) TvProyecto.getSelectionModel().getSelectedItem();
                if(estudiante.getEstado() == EstadoEstudiante.AsignacionPendiente && proyecto.GetEstado() ==  EstadoProyecto.Disponible){
                    String matricula = estudiante.getMatricula();
                    int idProyecto = proyecto.getIdProyecto();
                    RegisterNewExpediente(matricula, idProyecto);
                    ActualizarTablaEstudiantes();
                    ActualizarTablaProyectos();
                }else{
                    TxError.setText(outputMessages.ExpedienteAlreadyExist());
                }

            }else{
                TxError.setText(outputMessages.SelectionProjectNull());
            }
        }else{
            TxError.setText(outputMessages.SelectionStudentNull());
        }
    }
    private void RegisterNewExpediente(String matricula, int idProyecto) {
        if( expediente.Create( GetExpediente( matricula,  idProyecto) ) ) {
            TxError.setText( "" );
            CambiarEstadoEstudiante(matricula);
            CambiarEstudiantesRequeridos(idProyecto);
            TxSuccess.setText(outputMessages.ExpedienteCreate());
        }
        else {
            TxError.setText( outputMessages.DatabaseConnectionFailed() );
            TxSuccess.setText( "" );
        }
    }

    private void CambiarEstadoEstudiante(String matricula){
        Estudiante estudianteElegido = estudiantes.Read(matricula);
        estudianteElegido.SetEstadoEstudiante(EstadoEstudiante.ProyectoAsignado);
        estudiantes.Update(estudianteElegido);
    }

    private void CambiarEstudiantesRequeridos(int idProyecto){
        Proyecto proyectoElegido = proyectos.Read(idProyecto);
        int estudiantesAsignados = proyectoElegido.GetEstudiantesAsignados();
        proyectoElegido.SetEstudiantesAsignados(estudiantesAsignados + 1);
        proyectos.Update(proyectoElegido);
        CambiarEstadoEstadoProyecto(idProyecto);
    }

    private void CambiarEstadoEstadoProyecto(int idProyecto){
        Proyecto proyectoElegido = proyectos.Read(idProyecto);
        int estudiantesAsignados = proyectoElegido.GetEstudiantesAsignados();
        if (estudiantesAsignados  == proyectoElegido.getNumEstudiantesRequeridos()) {
            proyectoElegido. SetEstado(EstadoProyecto.Asignado);
            proyectos.Update(proyectoElegido);
        }
    }

    private void ActualizarTablaEstudiantes(){
        TvEstudiante.getItems().clear();
        MostrarEstudiantes();
    }

    private void ActualizarTablaProyectos(){
        TvProyecto.getItems().clear();
        MostrarProyectos();
    }

    private void ActualizarTablaPreferenciaProyectos(){
        TvPreferenciaProyecto.getItems().clear();
        MostrarPreferenciasProyecto();
    }

    private Expediente GetExpediente(String matricula, int idProyecto ){
        LocalDate currentDate = LocalDate.now();
        return new Expediente(0, idProyecto, matricula, currentDate.toString(), 0,0, true);
    }


    public void MostrarPantallaPrincipalCoordinador ( MouseEvent mouseEvent ) {
        screenChanger.MostrarPantallaGestionarEstudianesCoordinador( mouseEvent, TxError );
    }

}