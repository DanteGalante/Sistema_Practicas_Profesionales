package Controllers;

import Database.ProyectoDAO;
import Entities.Estudiante;
import Entities.Proyecto;
import Entities.ResponsableProyecto;
import Enumerations.EstadoEstudiante;
import Enumerations.EstadoProyecto;
import Utilities.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ModificarProyecto_Coordinador implements Initializable {
    private ProyectoDAO proyecto = new ProyectoDAO();
    private OutputMessages outputMessages = new OutputMessages();
    private ScreenChanger screenChanger = new ScreenChanger();
    private InputValidator inputValidator = new InputValidator();

    @FXML
    private Label lbNombres;

    @FXML
    private Label lbApellidos;

    @FXML
    private Label lbNoTrabajador;

    @FXML
    private Button btnRegresar;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfDescripcion;

    @FXML
    private TextField tfEstudiantesRequeridos;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnModificar;

    @FXML
    private Text errorText;

    @FXML
    private Text successText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatosProyecto();
        DatosDeUsuario();
    }

    public void ManejoModificarProyecto() {
        ValidarDatos();
        ModificarProyecto();
    }

    /**
     * Cambia la pantalla de ModificarProyecto_Coordinador a GestionarProyecto_Coordinador.
     * @param mouseEvent el evento de mouse que activo la acción.
     */
    public void ClicRegresar ( MouseEvent mouseEvent ){
        screenChanger.MostrarPantallaGestionarProyecto( mouseEvent, errorText );
    }

    /**
     * Espera la acción del clic para modificar la información ingresada.
     * @param event el evento de mouse que activo la acción.
     */
    public void ClicCancelar ( MouseEvent event ){
        DatosProyecto();
    }


    /**
     * Coloca la información del usuario actual en los campos de texto de
     * nombres, apellidos y No.Trabajador
     */
    public void DatosProyecto(){
        tfNombre.setText( SelectionContainer.GetInstance().getProyectoElegido().getNombre() );
        tfDescripcion.setText( SelectionContainer.GetInstance().getProyectoElegido().GetDescripcion() );
        tfEstudiantesRequeridos.setText( "" + ( SelectionContainer.GetInstance().getProyectoElegido().getNumEstudiantesRequeridos() ) );
    }

    private ResponsableProyecto ObtenerResponsableProyecto() {
        return SelectionContainer.GetInstance().getResponsableElegido();
    }

    public int RecuperarId(){
        int id = SelectionContainer.GetInstance().getProyectoElegido().getIdProyecto();
        return id;
    }

    public int RecuperarEstudiantesAsignados(){
        int cantidad = SelectionContainer.GetInstance().getProyectoElegido().GetEstudiantesAsignados();
        return cantidad;
    }

    public String RecuperarFechaRegistro(){
        String fecha = SelectionContainer.GetInstance().getProyectoElegido().GetFechaRegistro();
        return fecha;
    }

    public EstadoProyecto RecuperarEstadoProyecto(){
        EstadoProyecto estado = SelectionContainer.GetInstance().getProyectoElegido().GetEstado();
        return estado;
    }

    private Proyecto ObtenerProyecto() {
        return new Proyecto (RecuperarId(),tfNombre.getText(),tfDescripcion.getText(),Integer.parseInt(tfEstudiantesRequeridos.getText()),
                RecuperarEstudiantesAsignados(),RecuperarFechaRegistro(),RecuperarEstadoProyecto());
    }

    private void ValidarDatos() {
        NombreValido();
        DescripcionValida();
        EsEstudiantesRequeridosValidos();
    }

    /**
     * Revisa que el nombre introducido sea valido.
     */
    private void NombreValido() {
        if( !inputValidator.AreNamesValid( tfNombre.getText() ) ) {
            errorText.setText( outputMessages.InvalidNames() );
            successText.setText( "" );
        }
    }

    /**
     * Revisa que los nombres introducidos sean validos.
     */
    private void DescripcionValida() {
        if( !inputValidator.DescripcionValida( tfDescripcion.getText() ) ) {
            errorText.setText( outputMessages.DescripcionInvalida() );
            successText.setText( "" );
        }
    }

    private void EsEstudiantesRequeridosValidos(){
        if( !inputValidator.EstudiantesRequeridosValidos( Integer.parseInt( tfEstudiantesRequeridos.getText() ) ) ) {
            errorText.setText( outputMessages.EstudiantesRequeridosInvalidos() );
            successText.setText( "" );
        }
    }

    private void ModificarProyecto() {
        if( proyecto.Update( ObtenerProyecto() ) ) {
            errorText.setText( "" );
            successText.setText( outputMessages.ModificacionProyectoExitoso() );
        }
        else {
            errorText.setText( outputMessages.DatabaseConnectionFailed() );
            successText.setText( "" );
        }
    }

    public void DatosDeUsuario(){
        lbNombres.setText( LoginSession.GetInstance().GetCoordinador().getNombres() );
        lbApellidos.setText( LoginSession.GetInstance().GetCoordinador().GetApellidos() );
        lbNoTrabajador.setText( LoginSession.GetInstance().GetCoordinador().GetNumeroPersonal() );
    }
}
