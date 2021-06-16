package Controllers;

/*
 * Autor: Alan Adair Morgado Morales
 * Versión: 1.0
 * Fecha Creación: 03 - abril - 2021
 * Descripción:
 * Clase que se encarga de manejar las acciones de la pantalla
 * CrearProyecto del sistema de prácticas profesionales.
 */

import Database.*;
import Entities.OrganizacionVinculada;
import Entities.Proyecto;
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
    private Proyecto proyectoNuevo = new Proyecto();
    private ProyectoDAO proyectoDAO = new ProyectoDAO();
    private ResponsableProyectoDAO responsableProyectoDAO = new ResponsableProyectoDAO();
    private OrganizacionVinculadaDAO organizacionVinculadaDAO = new OrganizacionVinculadaDAO();
    private List<OrganizacionVinculada> listaOrganizaciones = new ArrayList<>();

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
    private TableView< OrganizacionVinculada > TvOrganizacion;

    @FXML
    private TableColumn< OrganizacionVinculada, String > TcNombreOrg;

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
        TcNombreOrg.setCellValueFactory( new PropertyValueFactory<>("Nombre") );
    }

    /**
     * Llena la tabla con las organizaciones vinculadas existentes en la BD
     */
    private void LlenarTablaResponsables(){
        try {
            listaOrganizaciones = organizacionVinculadaDAO.ReadAll();
            if(listaOrganizaciones.size() > 0) {
                for( OrganizacionVinculada organizacionVinculada : listaOrganizaciones){
                    TvOrganizacion.getItems().add( organizacionVinculada );
                }
            }
        } catch (Exception exception) {

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
     * Permite guardar la informacion del proyecto tomando la informacion
     * de los campos de texto y los combobox de la pantalla "Crear proyecto"
     */
    public void HandleRegistrar( MouseEvent mouseEvent ) {
        TxError.setText("");
        TxSuccess.setText("");
        if ( !TbNombreProyecto.getText().trim().isEmpty() && !TbDescripcionProyecto.getText().trim().isEmpty() && !TbEstudiantesRequeridos.getText().trim().isEmpty() ) {
            if( isNumeric() ) {
                if( VerificarDatos() ) {
                    if( TvOrganizacion.getSelectionModel().getSelectedItem() != null ){
                        RegistrarProyecto();
                    } else {
                        TxError.setText("No se ha seleccionado una organización");
                    }
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

        if( !inputValidator.EstudiantesRequeridosValidos( estudiantesRequeridos )){
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
            GenerarRelacionProyectoOrganizacion();
            TxSuccess.setText( outputMessages.RegistrationProjectSuccessfull() );
        }
        else {
            TxError.setText( outputMessages.DatabaseConnectionFailed() );
            TxSuccess.setText( "" );
        }
    }

    /**
     * Genera las relaciones necesarias en la base de datos, para saber a que organización
     * se relaciona un proyecto
     */
    private void GenerarRelacionProyectoOrganizacion() {
        ProyectosDeResponsablesDAO proyect_RespDAO = new ProyectosDeResponsablesDAO();
        ResponsablesOrganizacionDAO responsablesOrganizacionDAO = new ResponsablesOrganizacionDAO();

        OrganizacionVinculada organizacionVinculada = TvOrganizacion.getSelectionModel().getSelectedItem();

        List<Integer> idsResponsable = responsablesOrganizacionDAO.ReadResponsables( organizacionVinculada.getIdOrganizacion() );
        List<Integer> listaProyectos = new ArrayList<Integer>();
        listaProyectos.add( RecuperarIDNuevoProyecto( GetProyecto() ) );

        proyect_RespDAO.Create(idsResponsable.get(0), listaProyectos);
    }

    /**
     * Busca un proyecto en la BD
     * @param proyectoABuscar Entidad proyecto que se desea buscar
     * @return
     */
    private int RecuperarIDNuevoProyecto(Proyecto proyectoABuscar) {
        int idNuevoProyecto = 0;

        int i = 0;
        List<Proyecto> todosProyectosBD = proyectoDAO.ReadAll();
        boolean flag = false;
        while ( flag == false || i < todosProyectosBD.size() ) {
            if( todosProyectosBD.get(i).getNombre().equals( proyectoABuscar.getNombre() )
             && todosProyectosBD.get(i).GetDescripcion().equals( proyectoABuscar.GetDescripcion() )
             && todosProyectosBD.get(i).getNumEstudiantesRequeridos() == proyectoABuscar.getNumEstudiantesRequeridos()
             && todosProyectosBD.get(i).GetEstudiantesAsignados() == proyectoABuscar.GetEstudiantesAsignados()
             && todosProyectosBD.get(i).GetFechaRegistro().equals( proyectoABuscar.GetFechaRegistro() )
             && todosProyectosBD.get(i).GetEstado().ordinal() == proyectoABuscar.GetEstado().ordinal()
            ){
                idNuevoProyecto = todosProyectosBD.get(i).getIdProyecto();
                flag = true;
            }
            i++;
        }

        return idNuevoProyecto;
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
        Proyecto proyecto = new Proyecto( 0, TbNombreProyecto.getText(), TbDescripcionProyecto.getText(), estudiantesRequeridos, 0,
                            currentDate.toString(), EstadoProyecto.Disponible);
        return proyecto;
    }


    /**
     * Permite cambiar la pantalla a la pantalla Principal de coordinador
    */
     public void MostrarPantallaPrincipalCoordinador( MouseEvent mouseEvent ) {
     screenChanger.MostrarPantallaGestionarProyecto( mouseEvent, TxError );
     }

}