package Controllers;

import Database.*;
import Entities.*;
import Enumerations.EstadoEstudiante;
import Enumerations.EstadoProyecto;
import Utilities.LoginSession;
import Utilities.OutputMessages;
import Utilities.ScreenChanger;
import Utilities.SelectionContainer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class GestionarOrganizacion_Coordinador implements Initializable {
    private ScreenChanger screenChanger = new ScreenChanger();
    private OrganizacionVinculadaDAO organizacionVinculada = new OrganizacionVinculadaDAO();
    private ResponsableProyectoDAO responsableProyecto = new ResponsableProyectoDAO();
    private ResponsablesOrganizacionDAO responsablesOrganizacion = new ResponsablesOrganizacionDAO();
    private List< OrganizacionVinculada > listaOrganizaciones = new ArrayList<>();
    private OutputMessages outputMessages = new OutputMessages();
    private List< Proyecto > listaProyectos = new ArrayList<>();
    private ProyectoDAO proyectoCiclo = new ProyectoDAO();
    private ProyectoDAO proyectoAux = new ProyectoDAO();
    private ExpedienteDAO expedientes = new ExpedienteDAO();
    private EstudianteDAO estudiante = new EstudianteDAO();
    ProyectosDeResponsablesDAO proyectosDeResponsablesDAO = new ProyectosDeResponsablesDAO();

    @FXML
    private Label lbNombres;

    @FXML
    private Label lbApellidos;

    @FXML
    private Label lbNoTrabajador;

    @FXML
    private Button btnGestionarOrganizaciones;

    @FXML
    private Button btnGestionarProyectos;

    @FXML
    private Button btnRegresar;

    @FXML
    private TextField tfBuscar;

    @FXML
    private Button btnBuscar;

    @FXML
    private TableView<OrganizacionVinculada> tbOrganizaciones;

    @FXML
    private TableColumn<OrganizacionVinculada, String> clnOrganizaciones;

    @FXML
    private Button btnConsultarOrganizacion;

    @FXML
    private Button btnRegistrarOrganizacion;

    @FXML
    private Button btnEliminarOrganizacion;

    @FXML
    private Button btnModificarOrganizacion;

    @FXML
    private Text errorText;

    @FXML
    private Text successText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DatosDeUsuario();
            MostrarOrganizaciones();
            ValorColumnaOrganizacion();
        } catch ( Exception e) {
            errorText.setText( outputMessages.DatabaseConnectionFailed3() );
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

    public void MostrarOrganizaciones(){
        tbOrganizaciones.getItems().clear();
        listaOrganizaciones = organizacionVinculada.ReadAll();
        for( OrganizacionVinculada organizacion : listaOrganizaciones ){
            if( organizacion.getActiveStatus() != false){
                tbOrganizaciones.getItems().add( organizacion );
            }
        }
    }

    private void ValorColumnaOrganizacion() {
        clnOrganizaciones.setCellValueFactory( new PropertyValueFactory<>( "nombre" ) );
    }

    /**
     * Cambia la pantalla de GestionarOrganizacion_Coordinador a la pantalla Principal_Coordinador.
     * @param mouseEvent el evento de mouse que activo la acción.
     */
    public void ClicRegresar ( MouseEvent mouseEvent ){
        screenChanger.MostrarPantallaPrincipalCoordinador( mouseEvent, errorText );
    }

    /**
     * Permite cambiar la pantalla a la pantalla ConsultarOrganizacion
     */
    public void MostrarPantallaConsultarOrganizacion( MouseEvent mouseEvent ) {
        if(tbOrganizaciones.getSelectionModel().getSelectedItem() != null){
            OrganizacionVinculada orgVinculadaElegida = (OrganizacionVinculada)tbOrganizaciones.getSelectionModel().getSelectedItem();
            SelectionContainer.GetInstance().setOrganizacionElegida(orgVinculadaElegida);
            screenChanger.ShowConsultarOrganizacion( mouseEvent, errorText );
        }else{
            errorText.setText( outputMessages.SelectionOrganizacionNull());
        }
    }

    /**
     * Cambia la pantalla de GestionarOrganizacion_Coordinador a la pantalla RegistrarOrganizacion_Coordinador.
     * @param mouseEvent el evento de mouse que activo la acción.
     */
    public void ClicRegistrar( MouseEvent mouseEvent ){
        screenChanger.MostrarPantallaRegistrarOrganizacion( mouseEvent, errorText );
    }

    /**
     * Recupera la selección de la tabla para Elimina una OrganizacionVinculada de la base de datos.
     * @param mouseEvent el evento de mouse que activo la acción.
     */
    public void ClicEliminarOrganizacion( MouseEvent mouseEvent){
        if( ExisteSeleccion() ) {
            Alert deleteAlert = new Alert( Alert.AlertType.CONFIRMATION, outputMessages.ConfirmacionEliminarOrganizacion() );
            deleteAlert.showAndWait().ifPresent( response -> {
                if( response == ButtonType.OK ) {
                    EliminadoOrganizacionLogico();
                    MostrarOrganizaciones();
                }
            } );
        }
    }

    public void EliminadoOrganizacionLogico(){
        OrganizacionVinculada seleccionOrganizacion = OrganizacionSeleccionada();
        if(!ExistenProyectosRelacionados(seleccionOrganizacion)){
            try {
                organizacionVinculada.Update(ObtenerOrganizacionEliminada(seleccionOrganizacion));
                successText.setText(outputMessages.EliminacionExitosa());
            }catch (Exception exception){
                errorText.setText( outputMessages.DatabaseConnectionFailed2() );
            }
        }else{
            Alert deleteAlert = new Alert( Alert.AlertType.CONFIRMATION, outputMessages.ConfirmacionEliminarProyecto() );
            deleteAlert.showAndWait().ifPresent( response -> {
                if( response == ButtonType.OK ) {
                    EliminadoProyectoLogico(seleccionOrganizacion);
                    MostrarOrganizaciones();
                }
            });
        }
    }

    public  void EliminadoProyectoLogico(OrganizacionVinculada seleccionOrganizacion){
        ResponsableProyecto responsableActual;
        //int proyectoCiclo;
        if(!ExistenEstudiantesAsignados(seleccionOrganizacion)){
            for ( int i = 0; i< RecuperarListaProyectos(seleccionOrganizacion).size() ; i++) {
                organizacionVinculada.Update(ObtenerOrganizacionEliminada(seleccionOrganizacion));
                ModificarProyecto(proyectoCiclo.Read(listaProyectos.get(i).getIdProyecto()));
                successText.setText(outputMessages.EliminacionExitosa());
            }
        }else{
            EliminarExpedienteLogico(seleccionOrganizacion);
        }
    }

    public void EliminarExpedienteLogico(OrganizacionVinculada seleccionOrganizacion){
        ResponsableProyecto responsableActual;
        List<Expediente> listaExpedientes = new ArrayList<>();
        Proyecto proyectoCicloAux;
        Expediente expediente;
        listaExpedientes.clear();
        listaExpedientes = expedientes.ReadAll();
        EstudianteDAO estudianteDAO = new EstudianteDAO();
        int proyectoId;

        for( int i = 0; i< seleccionOrganizacion.getResponsables().size(); i++){
            responsableActual = responsableProyecto.Read( seleccionOrganizacion.getResponsables().get(i) );
            for (int i1 = 0; i1 < proyectosDeResponsablesDAO.ReadProyectos(responsableActual.getIdResponsableProyecto()).size(); i1++) {
                proyectoCicloAux = proyectoCiclo.Read(proyectosDeResponsablesDAO.ReadProyectos(responsableActual.getIdResponsableProyecto()).get(i1));
                listaProyectos.add(proyectoCicloAux);
                for (int i2 = 0; i2 < listaExpedientes.size(); i2++) {
                    expediente = listaExpedientes.get(i2);
                    if (expediente.GetIDProyecto() == proyectoCicloAux.getIdProyecto()) {
                        organizacionVinculada.Update(ObtenerOrganizacionEliminada(seleccionOrganizacion));
                        ModificarProyecto(proyectoCiclo.Read(listaProyectos.get(i1).getIdProyecto()));
                        expedientes.Update(ModificarExpediente(expediente));
                        Estudiante estudianteAux = estudianteDAO.Read(expediente.GetMatricula());
                        estudianteAux.SetEstadoEstudiante(EstadoEstudiante.AsignacionPendiente);
                        estudianteDAO.Update(estudianteAux);
                    }
                }
            }
        }
    }

    public Expediente ModificarExpediente(Expediente expediente){
        expediente.SetActivo(false);
        return expediente;
    }

    public void ModificarProyecto(Proyecto proyecto){
        proyectoAux.Update(RecuperarProyecto(proyecto));
    }

    public Proyecto RecuperarProyecto(Proyecto proyecto){
        return new Proyecto(proyecto.getIdProyecto(),proyecto.getNombre(),proyecto.GetDescripcion(),proyecto.getNumEstudiantesRequeridos(),
                proyecto.GetEstudiantesAsignados(),proyecto.GetFechaRegistro(), EstadoProyecto.Eliminado);
    }

    public List<Proyecto> RecuperarListaProyectos(OrganizacionVinculada seleccionOrganizacion){
        ResponsableProyecto responsableActual;
        Proyecto proyectoCicloAux;
        int proyectoId;
        listaProyectos.clear();

        for( int i = 0; i< seleccionOrganizacion.getResponsables().size(); i++) {
            responsableActual = responsableProyecto.Read(seleccionOrganizacion.getResponsables().get(i));
            for (int i1 = 0; i1 < proyectosDeResponsablesDAO.ReadProyectos(responsableActual.getIdResponsableProyecto()).size(); i1++) {
                proyectoCicloAux = proyectoCiclo.Read(proyectosDeResponsablesDAO.ReadProyectos(responsableActual.getIdResponsableProyecto()).get(i1));
                listaProyectos.add(proyectoCicloAux);
            }
        }
        return  listaProyectos;
    }

    public OrganizacionVinculada OrganizacionSeleccionada(){
        return organizacionVinculada.Read(tbOrganizaciones.getSelectionModel().getSelectedItem().getIdOrganizacion());
    }

    public boolean ExistenProyectosRelacionados( OrganizacionVinculada seleccionOrganizacion){
        boolean proyectosRelacionados = false;
        ResponsableProyecto responsableActual;
        for( int i = 0; i < seleccionOrganizacion.getResponsables().size(); i++ ){
            responsableActual = responsableProyecto.Read(seleccionOrganizacion.getResponsables().get(i));
            if(responsableActual.getIdProyectos().size() > 0){
                proyectosRelacionados = true;
            }
        }
        return proyectosRelacionados;
    }

    public boolean ExistenEstudiantesAsignados(OrganizacionVinculada seleccionOrganizacion){
        ResponsableProyecto responsableActual;
        ResponsableProyecto proyectoActual;
        List<Expediente> listaExpedientes = new ArrayList<>();
        Proyecto proyectoCicloAux;
        Expediente expediente;
        listaExpedientes.clear();
        listaExpedientes = expedientes.ReadAll();
        int proyectoId;
        boolean existeEstudiante = false;

        for( int i = 0; i< seleccionOrganizacion.getResponsables().size(); i++){
            responsableActual = responsableProyecto.Read( seleccionOrganizacion.getResponsables().get(i) );
            for (int i1 = 0 ; i1< responsableActual.getIdProyectos().size(); i1++){
                proyectoId = responsableActual.getIdProyectos().get(i1);
                proyectoCicloAux = proyectoCiclo.Read(proyectoId);
                for ( int i2=0; i2< listaExpedientes.size(); i2++){
                    expediente = listaExpedientes.get(i2);
                    if(expediente.GetIDProyecto() == proyectoCicloAux.getIdProyecto()){
                        existeEstudiante = true;
                    }
                }
            }
        }
        return existeEstudiante;
    }

    public OrganizacionVinculada ObtenerOrganizacionEliminada(OrganizacionVinculada seleccionOrganizacion){
        return new OrganizacionVinculada ( seleccionOrganizacion.getNombre(),seleccionOrganizacion.getDireccion(),
                seleccionOrganizacion.getSector(),seleccionOrganizacion.getTelefono(),seleccionOrganizacion.getCorreo(),
                seleccionOrganizacion.getIdOrganizacion(),seleccionOrganizacion.getResponsables(),false,
                seleccionOrganizacion.GetKey());
    }

    /**
     * Permite cambiar la pantalla a la pantalla GestionarEstudiante
     */
    @FXML
    public void ClicModificarOrganizacion( MouseEvent mouseEvent ) {
        if ( tbOrganizaciones.getSelectionModel().getSelectedItem() != null ) {
            SelectionContainer.GetInstance().setOrganizacionElegida( RecuperarOrganizacion() );
            SelectionContainer.GetInstance().setResponsableElegido( RecuperarResponsable() );
            screenChanger.MostrarPantallaModificarOrganizacion( mouseEvent, errorText );
        } else {
            errorText.setText( outputMessages.SelectionOrganizacionNull() );
        }
    }

    public boolean ExisteSeleccion(){
        boolean Seleccion = false;
        if( tbOrganizaciones.getSelectionModel().getSelectedItem() != null ) {
            errorText.setText("");
            Seleccion = true;
        }else{
            errorText.setText(outputMessages.SeleccionInvalidaOrganizacion());
        }
        return Seleccion;
    }

    public OrganizacionVinculada RecuperarOrganizacion(){
        return tbOrganizaciones.getSelectionModel().getSelectedItem();
    }

    public ResponsableProyecto RecuperarResponsable() {
        List< Integer > idResponsables = responsablesOrganizacion.ReadResponsables(
                                         tbOrganizaciones.getSelectionModel().getSelectedItem().getIdOrganizacion() );
        return responsableProyecto.Read( idResponsables.get( 0 ) );
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