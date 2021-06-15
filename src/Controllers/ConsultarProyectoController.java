package Controllers;

/*
 * Autor: Alan Adair Morgado Morales
 * Versión: 1.0
 * Fecha Creación: 02 - mayo - 2021
 * Descripción:
 * Clase que se encarga de manejar las acciones de la pantalla
 * Consultar Proyecto del sistema de prácticas profesionales.
 */

import Database.ExpedienteDAO;
import Database.ProyectosDeResponsablesDAO;
import Entities.Expediente;
import Entities.OrganizacionVinculada;
import Entities.Proyecto;
import Utilities.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ConsultarProyectoController implements Initializable{
    private ScreenChanger screenChanger = new ScreenChanger();
    private Proyecto proyectoElegido = SelectionContainer.GetInstance().getProyectoElegido();

    @FXML
    private Text TxNombres;

    @FXML
    private Text TxApellidos;

    @FXML
    private Text TxNoTrabajador;

    @FXML
    private Text TxNombreProyecto;

    @FXML
    private Text TxNumEstudiantesRequeridos;

    @FXML
    private Text TxNumEstudiantesAsignados;

    @FXML
    private Text TxFechaRegistroProyecto;

    @FXML
    private Text TxEstadoProyecto;

    @FXML
    private Text TxNombreOrgProyecto;

    @FXML
    private Text TxNombreResProyecto;

    @FXML
    private Text TxDescripcion;

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
        DatosProyecto();
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
     * Coloca la información del proyecto seleccionado en los campos de texto de
     * nombre, direccion, sector, correo y telefono
     */
    public void DatosProyecto(){
        TxNombreProyecto.setText( SelectionContainer.GetInstance().getProyectoElegido().getNombre() );
        TxDescripcion.setText( SelectionContainer.GetInstance().getProyectoElegido().GetDescripcion() );
        TxNumEstudiantesRequeridos.setText(String.valueOf(SelectionContainer.GetInstance().getProyectoElegido().getNumEstudiantesRequeridos()));
        TxNumEstudiantesAsignados.setText(String.valueOf(SelectionContainer.GetInstance().getProyectoElegido().GetEstudiantesAsignados()));
        TxFechaRegistroProyecto.setText( SelectionContainer.GetInstance().getProyectoElegido().GetFechaRegistro() );
        TxEstadoProyecto.setText(String.valueOf(SelectionContainer.GetInstance().getProyectoElegido().GetEstado()));
    }

    /**
     * Permite cambiar la pantalla a la pantalla Principal de coordinador
     */
    public void MostrarPantallaPrincipalCoordinador( MouseEvent mouseEvent ) {
        screenChanger.MostrarPantallaGestionarProyecto ( mouseEvent, TxError );
    }
}
