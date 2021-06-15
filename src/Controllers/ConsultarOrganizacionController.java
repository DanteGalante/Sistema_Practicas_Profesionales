package Controllers;

/*
 * Autor: Alan Adair Morgado Morales
 * Versión: 1.0
 * Fecha Creación: 02 - mayo - 2021
 * Descripción:
 * Clase que se encarga de manejar las acciones de la pantalla
 * Consultar Organizacion del sistema de prácticas profesionales.
 */

import Entities.OrganizacionVinculada;
import Enumerations.TipoSector;
import Utilities.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import java.awt.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ConsultarOrganizacionController implements Initializable {
    private ScreenChanger screenChanger = new ScreenChanger();
    private OrganizacionVinculada organizacionVinculadaElegida = SelectionContainer.GetInstance().getOrganizacionElegida();

    @FXML
    private Text TxNombres;

    @FXML
    private Text TxApellidos;

    @FXML
    private Text TxNoTrabajador;

    @FXML
    private Text TxNombreOrganizacion;

    @FXML
    private Text TxDireccionOrganizacion;

    @FXML
    private Text TxSectorOrganizacion;

    @FXML
    private Text TxCorreoOrganizacion;

    @FXML
    private Text TxTelefonoOrganizacion;

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
        DatosOrganizacion();
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
     * Coloca la información de la organizacion seleccionada en los campos de texto de
     * nombre, direccion, sector, correo y telefono
     */
    public void DatosOrganizacion(){
        TxNombreOrganizacion.setText( SelectionContainer.GetInstance().getOrganizacionElegida().getNombre() );
        TxDireccionOrganizacion.setText( SelectionContainer.GetInstance().getOrganizacionElegida().getDireccion() );
        TxCorreoOrganizacion.setText( SelectionContainer.GetInstance().getOrganizacionElegida().getCorreo() );
        TxTelefonoOrganizacion.setText( SelectionContainer.GetInstance().getOrganizacionElegida().getTelefono() );
    }

    /**
     * Permite cambiar la pantalla a la pantalla Principal de coordinador
     */
    public void MostrarPantallaPrincipalCoordinador( MouseEvent mouseEvent ) {
        screenChanger.MostrarPantallaGestionarOrganizacion( mouseEvent, TxError );
    }
}
