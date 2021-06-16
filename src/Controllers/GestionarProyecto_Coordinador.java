package Controllers;

import Database.EstudianteDAO;
import Database.ExpedienteDAO;
import Database.ProyectoDAO;
import Entities.Estudiante;
import Entities.Expediente;
import Entities.Proyecto;
import Enumerations.EstadoEstudiante;
import Enumerations.EstadoProyecto;
import Utilities.OutputMessages;
import Utilities.ScreenChanger;
import Utilities.SelectionContainer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import Utilities.LoginSession;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class GestionarProyecto_Coordinador implements Initializable {
    private List< Proyecto > listaProyectos = new ArrayList<>();
    private ProyectoDAO proyecto = new ProyectoDAO();
    private ScreenChanger screenChanger = new ScreenChanger();
    private OutputMessages outputMessages = new OutputMessages();

    @FXML
    private Label lbNombres;

    @FXML
    private Label lbApellidos;

    @FXML
    private Label lbNoTrabajador;

    @FXML
    private TableView <Proyecto> tbProyectos;

    @FXML
    private TableColumn <Proyecto, String> clnProyectos;

    @FXML
    private Button btnConsultarProyecto;

    @FXML
    private Button btnRegistrarProyecto;

    @FXML
    private Button btnEliminarProyecto;

    @FXML
    private Button btnModificarProyecto;

    @FXML
    private Text errorText;

    @FXML
    private Text successText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DatosDeUsuario();
            ValorColumnasProyecto();
            MostrarProyectosDisponibles();
        } catch ( Exception exception ) {
            exception.printStackTrace();
            errorText.setText(outputMessages.DatabaseConnectionFailed4());
        }
    }

    /**
     * Coloca la información del usuario actual en los campos de texto de
     * nombres, apellidos y No.Trabajador
     */
    public void DatosDeUsuario(){
        lbNombres.setText( LoginSession.GetInstance().GetCoordinador().getNombres() );
        lbApellidos.setText( LoginSession.GetInstance().GetCoordinador().GetApellidos() );
        lbNoTrabajador.setText( LoginSession.GetInstance().GetCoordinador().GetNumeroPersonal() );
    }

    public void ValorColumnasProyecto(){
        clnProyectos.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    }

    public void MostrarProyectosDisponibles(){
        listaProyectos = proyecto.ReadAll();
        for( Proyecto proyecto : listaProyectos){
            if ( proyecto.GetEstado() != EstadoProyecto.Eliminado ) {
                tbProyectos.getItems().add( proyecto );
            }
        }
    }

    public void ClicRegresar ( MouseEvent mouseEvent ){
        screenChanger.MostrarPantallaPrincipalCoordinador( mouseEvent, errorText );
    }

    @FXML
    void MostrarPantallaConsultarProyecto( MouseEvent event ){
        if(tbProyectos.getSelectionModel().getSelectedItem() != null){
            Proyecto proyecto = (Proyecto)tbProyectos.getSelectionModel().getSelectedItem();
            SelectionContainer.GetInstance().setProyectoElegido(proyecto);
            screenChanger.ShowConsultarProyecto( event, errorText );
        }else{
            errorText.setText("Seleccione un proyecto");
        }
    }

    @FXML
    void MostrarCrearProyecto( MouseEvent event ){
        screenChanger.ShowCrearProyecto ( event, errorText );
    }

    @FXML
    void ClicModificarProyecto( MouseEvent event){
        if (tbProyectos.getSelectionModel().getSelectedItem() != null){
            SelectionContainer.GetInstance().setProyectoElegido( RecuperarProyecto() );
            screenChanger.MostrarPantallaModificarProyecto( event, errorText );
        }else{
            errorText.setText(outputMessages.SeleccionInvalidaProyecto());
        }
    }

    public void EliminarProyecto( MouseEvent mouseEvent){
        try {
            if (tbProyectos.getSelectionModel().getSelectedItem() != null) {

                Proyecto seleccionProyecto = (Proyecto) tbProyectos.getSelectionModel().getSelectedItem();
                int idProyecto = seleccionProyecto.getIdProyecto();
                int estudiantesRequeridos = seleccionProyecto.getNumEstudiantesRequeridos();
                if (seleccionProyecto.GetEstudiantesAsignados() > 0){
                    ExpedienteDAO expedienteDAO = new ExpedienteDAO();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    Optional<ButtonType> respuesta = alert.showAndWait();

                    if ( respuesta.get() == ButtonType.OK ) {
                        seleccionProyecto.SetEstado( EstadoProyecto.Eliminado );
                        proyecto.Update(seleccionProyecto);
                        List<Expediente> expedientes = RecuperarExpedientePorProyecto( seleccionProyecto.getIdProyecto() );
                        BorradoLogicoExpedientes(expedientes);
                        ActualizarTablaProyectos();
                        successText.setText(outputMessages.ProjectDelete());
                    }


                } else {
                    seleccionProyecto.SetEstado(EstadoProyecto.Eliminado);
                    proyecto.Update(seleccionProyecto);
                    ActualizarTablaProyectos();
                    successText.setText(outputMessages.ProjectDelete());
                }
            } else {
                errorText.setText(outputMessages.SelectionProjectNull());
            }
        } catch ( Exception exception ) {
            errorText.setText( outputMessages.DeleteProjectFailedBD() );
        }
    }

    /**
     * Borrado lógico de los expedientes de un proyecto que se esta borrando
     * @param expedientes lista de expedientes de un proyecto
     */
    private void BorradoLogicoExpedientes(List<Expediente> expedientes) {
        ExpedienteDAO expedienteDAO = new ExpedienteDAO();
        for (Expediente expedienteAborrar : expedientes ) {
            expedienteAborrar.SetActivo(false);
            expedienteDAO.Update(expedienteAborrar);
            CambioEstadoEstudianteAsignado( expedienteAborrar.GetMatricula() );
        }
    }

    /**
     * Cambio de estado de los estudiantes que fueron asignado a un proyecto que es eliminado.
     * El estado al que pasan es "Asignación pendiente"
     * @param getMatricula matricula del estudiante asignado a un proyecto que va a ser borrado
     */
    private void CambioEstadoEstudianteAsignado(String getMatricula) {
        EstudianteDAO estudianteDAO = new EstudianteDAO();

        Estudiante estudianteRecuperado = estudianteDAO.Read(getMatricula);

        estudianteRecuperado.SetEstadoEstudiante( EstadoEstudiante.AsignacionPendiente );

        estudianteDAO.Update( estudianteRecuperado );
    }

    /**
     * Recupera los expedientes de un proyecto especificado
     * @param idProyecto id del proyecto que se le busca su expediente
     * @return lista de expedientes recuperados
     */
    private List<Expediente> RecuperarExpedientePorProyecto(int idProyecto) {
        List<Expediente> expedientesRecuperados = new ArrayList<>();
        ExpedienteDAO expedienteDAO = new ExpedienteDAO();

        for ( Expediente expediente : expedienteDAO.ReadAll() ){
            if ( expediente.GetIDProyecto() == idProyecto ) {
                expedientesRecuperados.add(expediente);
            }
        }

        return expedientesRecuperados;
    }

    public void ActualizarTablaProyectos(){
        tbProyectos.getItems().clear();
        MostrarProyectosDisponibles();
    }

    public Proyecto RecuperarProyecto(){
        return tbProyectos.getSelectionModel().getSelectedItem();
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

}