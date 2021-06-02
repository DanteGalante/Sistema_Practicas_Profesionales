/*
* Autor: Dan Javier Olvera Villeda
* Versión: 1.0
* Fecha Creación: 30 - abr - 2021
* Descripción:
* Clase encargada de manejar los eventos de la pantalla
* ValidarInscripcion.
*/
package Controllers;

import Database.EstudianteDAO;
import Entities.Estudiante;
import Enumerations.EstadoEstudiante;
import Utilities.LoginSession;
import Utilities.OutputMessages;
import Utilities.ScreenChanger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Clase que se encarga de manejar los eventos de la pantalla ValidarInscripcion
 * del Sistema de Practicas Profesionales
 */
public class ValidarInscripcion implements Initializable {
    ScreenChanger screenChanger = new ScreenChanger();
    EstudianteDAO estudianteDAO = new EstudianteDAO();
    List<Estudiante> estudiantes = new ArrayList<Estudiante>();
    OutputMessages outputMessages = new OutputMessages();

    @FXML
    private Label lbNombre;
    @FXML
    private Label lbApellidos;
    @FXML
    private Label lbCedulaProfesional;
    @FXML
    private Text errorText;
    @FXML
    private Text confirmationText;
    @FXML
    private Label lbPeriodo;
    @FXML
    private Label lbFecha;
    @FXML
    private TableView< Estudiante > tbvEstudiantes;
    @FXML
    private TableColumn< Estudiante, String> tcNombre;
    @FXML
    private TableColumn< Estudiante, String> tcMatricula;
    @FXML
    private TableColumn< Estudiante, String> tcValidar;
    @FXML
    private TableColumn< Estudiante, String> tcDepurar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbFecha.setText( String.valueOf(LocalDate.now() ) );
        SetUsuario();
        RecuperarEstudiantes();
        ConfigurarColumnas();
        LlenarTablaEstudiantes();
    }

    /**
     * Se llena la tabla de estudiantes con estudiantes con estado de
     * inscripcion pendiente
     */
    public void LlenarTablaEstudiantes() {
        tbvEstudiantes.getItems().clear();
        for ( Estudiante estudiante : estudiantes ){
            tbvEstudiantes.getItems().add(estudiante);
        }
    }

    /**
     * Se configuran las columnas de las tablas, indicando que atributos de la entidad
     * van a ser mostrados por cada columna.
     */
    public void ConfigurarColumnas() {
        tcNombre.setCellValueFactory( new PropertyValueFactory<>( "nombre" ) );
        tcMatricula.setCellValueFactory( new PropertyValueFactory<>( "matricula" ) );
        tcValidar.setCellValueFactory( new PropertyValueFactory<>( "validado") );
        tcDepurar.setCellValueFactory( new PropertyValueFactory<>( "depurado" ) );
    }

    /**
     * Coloca la informacion del usuario actual en las etiquetas. Se coloca nombres, apellidos,
     * y numero personal.
     */
    public void SetUsuario() {
        lbNombre.setText(LoginSession.GetInstance().GetCoordinador().getNombres());
        lbApellidos.setText(LoginSession.GetInstance().GetCoordinador().GetApellidos());
        lbCedulaProfesional.setText(LoginSession.GetInstance().GetCoordinador().GetNumeroPersonal());
    }

    /**
     * Recupera los estudiantes con estado de registro pendiente
     */
    public void RecuperarEstudiantes() {
        estudiantes = estudianteDAO.ReadByState(EstadoEstudiante.RegistroPendiente.ordinal());

    }

    /**
     * Cierra la pantalla actual y regresa a la pantalla principal del coordinador
     * @param mouseEvent evento del mouse que inicia el metodo
     */
    public void ClicRegresar(MouseEvent mouseEvent) {
        screenChanger.MostrarPantallaPrincipalCoordinador(mouseEvent, errorText);
    }

    /**
     * Verifica los checkbox de la tabla y dependiendo de las selecciones, depura o valida las
     * inscripciones de los estudiantes
     */
    public void ClicAceptar(){
        if( VerificarValidezSeleccion() ){
            for( Estudiante estudiante : tbvEstudiantes.getItems() ){
                if( estudiante.getValidado().isSelected() ){
                    estudiante.SetEstadoEstudiante(EstadoEstudiante.RegistroAprobado);
                    estudianteDAO.Update(estudiante);
                }else if( estudiante.getDepurado().isSelected() ){
                    estudianteDAO.Delete(estudiante.getMatricula());
                }
            }
            confirmationText.setText( "Se ha realizado la operación con éxito" );
        }
    }

    /**
     * Verifica que la selección realizada sea valida. Que sea valida o no depende de dos factores:
     * <p>
     * - Que solo se seleccione una checkbox por estudiante*<p>
     * - Que todos los estudiantes tengan un checkbox seleccionado
     * <p>
     * Si encuentra alguna situación en la que los dos checkbox esten seleccionados, se deselecciona
     * ambos. Si se encuentra que no todos los estudiantes tienen seleccionado al menos un checkbox
     * se muestra un mensaje de error para notificar al usuario
     */
    public boolean VerificarValidezSeleccion() {
        boolean seleccionesValidas = false;
        int contador = 0; //variable que va a llevar la cuenta de cuantos estudiantes tiene marcado un checkbox

        for( Estudiante estudiante : tbvEstudiantes.getItems() ){
            if( estudiante.getValidado().isSelected() && estudiante.getDepurado().isSelected() ){
                estudiante.setValidado(false);
                estudiante.setDepurado(false);
            } else if( estudiante.getValidado().isSelected() == true||
                    estudiante.getDepurado().isSelected() == true ) {
                contador++;
            }
        }

        if( contador != tbvEstudiantes.getItems().size() ){
            errorText.setText( outputMessages.EstudianteNoSeleccionado() );
        }else{
            seleccionesValidas = true;
        }

        return seleccionesValidas;
    }

    /**
     *  Deselecciona todos los checkbox de la tabla de estudiantes
     */
    public void ClicCancelar(){
        for( Estudiante estudiante : tbvEstudiantes.getItems() ){
            estudiante.setValidado(false);
            estudiante.setDepurado(false);
        }
    }
}