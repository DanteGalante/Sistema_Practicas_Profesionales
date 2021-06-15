package Controllers;

/*
 * Autor: Alan Adair Morgado Morales
 * Versión: 1.0
 * Fecha Creación: 03 - abril - 2021
 * Descripción:
 * Clase que se encarga de manejar las acciones de la pantalla
 * CrearProyecto del sistema de prácticas profesionales.
 */

import Database.ProyectoDAO;
import Database.ResponsableProyectoDAO;
import Entities.Proyecto;
import Entities.ResponsableProyecto;
import Enumerations.EstadoProyecto;
import Utilities.InputValidator;
import Utilities.LoginSession;
import Utilities.OutputMessages;
import Utilities.ScreenChanger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class CrearProyectoController implements Initializable {
    private ScreenChanger screenChanger = new ScreenChanger();
    private InputValidator inputValidator = new InputValidator();
    private OutputMessages outputMessages = new OutputMessages();
    private Proyecto proyecto = new Proyecto();
    private ProyectoDAO proyectoDAO = new ProyectoDAO();
    private ResponsableProyectoDAO responsableProyecto = new ResponsableProyectoDAO();
    private List<ResponsableProyecto> listaResponsables = new ArrayList<>();

    @FXML
    private Text TxNombres;

    @FXML
    private Text TxApellidos;

    @FXML
    private Text TxNoTrabajador;

    @FXML
    private TextField TbNombreProyecto;

    @FXML
    private TextArea TbDescripcionProyecto;

    @FXML
    private TableView TvOrganizacion;

    @FXML
    private TableColumn TcNombreOrg;

    @FXML
    private TextField TbEstudiantesRequeridos;

    @FXML
    private Text TxError;

    @FXML
    private Text TxSuccess;

    @FXML
    private Button BtRegistrar;

    @FXML
    private Button BtRegresar;


    /**
     * Configura los elementos utilizados en la pantalla CrearProyecto
     * @param url un url sin utilizar
     * @param resourceBundle un conjunto de recursos no utilizados
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle ) {

        DatosUsuario();
        ValorColumnasProyectoSeleccionados();
        LlenarTablaResponsables();
    }

    /**
     * Configura las columnas de la tabla preferencias proyecto en esta pantalla
     */
    private void ValorColumnasProyectoSeleccionados() {
        TcNombreOrg.setCellValueFactory( new PropertyValueFactory<>("Nombres"));
    }

    private void LlenarTablaResponsables(){
        listaResponsables = responsableProyecto.ReadAll();
        for( ResponsableProyecto responsableProyecto : listaResponsables ){
                responsableProyecto.getNombres();
                TvOrganizacion.getItems().add( responsableProyecto );
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
     * Permite guardar la informacion del proyecto tomando la informacion
     * de los campos de texto y los combobox de la pantalla "Crear proyecto"
     */
    public void HandleRegistrar( MouseEvent mouseEvent ) {
        TxError.setText("");
        TxSuccess.setText("");
        if (!TbNombreProyecto.getText().trim().isEmpty() && !TbDescripcionProyecto.getText().trim().isEmpty() && !TbEstudiantesRequeridos.getText().trim().isEmpty() ) {
            if(isNumeric()){
                if(VerificarDatos()){
                    RegistrarProyecto();
                }
            }else{
                TxError.setText("No ha ingresado un numero");
                TxSuccess.setText("");
            }

        } else {
            TxError.setText(outputMessages.CamposVacios());
            TxSuccess.setText("");
        }
    }

    private boolean isNumeric() {
        String numeroEstudiantesRequeridos = TbEstudiantesRequeridos.getText();
        try {
            Integer.parseInt(numeroEstudiantesRequeridos);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private boolean VerificarDatos(){
        boolean resultado = false;

        if(NombreValido() && DescripcionValida() && NumeroEstudiantesRequeridosValido()) {
            resultado = true;
        }
        return resultado;
    }

    private boolean NombreValido(){

        boolean resultado = true;
        if( !inputValidator.NombreValidoProyecto(TbNombreProyecto.getText() ) ){
            TxError.setText(outputMessages.InvalidNames() );
            TxSuccess.setText( "" );
            resultado = false;
        }
        return resultado;
    }

    private boolean DescripcionValida() {

        boolean resultado = true;
        if( !inputValidator.DescripcionValidaProyecto(TbDescripcionProyecto.getText() ) ){
            TxError.setText(outputMessages.DescripcionInvalida() );
            TxSuccess.setText( "" );
            resultado = false;
        }
        return resultado;
    }

    private boolean NumeroEstudiantesRequeridosValido() {
        boolean resultado = true;
        String numeroEstudiantesRequeridos = TbEstudiantesRequeridos.getText();
        int estudiantesRequeridos = Integer.parseInt(numeroEstudiantesRequeridos);

        if( !inputValidator.NumeroValidoProyecto( estudiantesRequeridos )){
            TxError.setText(outputMessages.NumeroEstudiantesInvalido() );
            TxSuccess.setText( "" );
            resultado = false;
        }
        return resultado;
    }

    /**
     * Intenta crear un Proyecto en la base de datos y coloca
     * el mensaje correspondiente n caso de éxito o fracaso.
     */
    private void RegistrarProyecto() {
        if( proyectoDAO.Create( GetProyecto() ) ) {
            TxError.setText( "" );
            TxSuccess.setText( outputMessages.RegistrationProjectSuccessfull() );
        }
        else {
            TxError.setText( outputMessages.DatabaseConnectionFailed() );
            TxSuccess.setText( "" );
        }
    }


    /**
     * Crea una instancia de Proyecto utilizando la información
     * introducida por el usuario en todos los campos de texto.
     * @return una instancia de Proyecto
     */
    private Proyecto GetProyecto() {
        String numeroEstudiantesRequeridos = TbEstudiantesRequeridos.getText();
        int estudiantesRequeridos = Integer.parseInt(numeroEstudiantesRequeridos);
        LocalDate currentDate = LocalDate.now();
        return new Proyecto( 0, TbNombreProyecto.getText(), TbDescripcionProyecto.getText(), estudiantesRequeridos, 0,
                currentDate.toString(), EstadoProyecto.Disponible);
    }


    /**
     * Permite cambiar la pantalla a la pantalla Principal de coordinador
    */
     public void MostrarPantallaPrincipalCoordinador( MouseEvent mouseEvent ) {
     screenChanger.MostrarPantallaGestionarProyecto( mouseEvent, TxError );
     }

}