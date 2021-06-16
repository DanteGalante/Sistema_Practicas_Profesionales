/*
 * Autor: Dan Javier Olvera Villeda
 * Versión: 1.0
 * Fecha Creación: 14 - jun - 2021
 * Descripción:
 * Clase encargada de manejar los eventos de la pantalla
 * EliminarArchivosConsulta_Docente.
 */

package Controllers;

import Database.ArchivoConsultaDAO;
import Entities.ArchivoConsulta;
import Entities.Documento;
import Utilities.LoginSession;
import Utilities.OutputMessages;
import Utilities.ScreenChanger;
import Utilities.SelectionContainer;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
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

public class EliminarArchivosConsulta_Docente implements Initializable {

    private ArchivoConsultaDAO archivoConsultaDAO = new ArchivoConsultaDAO();
    private List<ArchivoConsulta> archivosElegidos = new ArrayList<>();
    private OutputMessages outputMessages = new OutputMessages();
    private ScreenChanger screenChanger = new ScreenChanger();

    @FXML
    Label lbApellidos;
    @FXML
    Label lbCedulaProfesional;
    @FXML
    Label lbNombre;
    @FXML
    Text errorText;
    @FXML
    TableView<ArchivoConsulta> tbvArchivosEliminar;
    @FXML
    TableColumn<ArchivoConsulta, String> tcNombreArchivo;
    @FXML
    TableColumn<ArchivoConsulta, String> tcTamanio;
    @FXML
    TableColumn<ArchivoConsulta, String> tcTipo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SetUsuario();
        ConfigurarColumna();
        RecuperarArchivosConsulta();
        MostrarArchivosConsulta();
    }

    /**
     * Muestra en una tabla los archivos de consulta a eliminar
     */
    private void MostrarArchivosConsulta() {
        tbvArchivosEliminar.getItems().clear();
        for (ArchivoConsulta archivoConsulta : archivosElegidos) {
            tbvArchivosEliminar.getItems().add(archivoConsulta);
        }
    }

    private void RecuperarArchivosConsulta() {
        archivosElegidos = Arrays.asList(SelectionContainer.GetInstance().getArchivosConsulta());
    }

    /**
     * Coloca la informacion del usuario actual en las etiquetas. Se coloca nombres, apellidos,
     * y numero personal.
     */
    public void SetUsuario() {
        lbNombre.setText( LoginSession.GetInstance().GetDocente().getNombres() );
        lbApellidos.setText( LoginSession.GetInstance().GetDocente().GetApellidos() );
        lbCedulaProfesional.setText( LoginSession.GetInstance().GetDocente().GetNumeroPersonal() );
    }

    /**
     * Se configuran las columnas de las tablas, indicando que atributos de la entidad
     * van a ser mostrados por cada columna.
     */
    private void ConfigurarColumna() {
        tcNombreArchivo.setCellValueFactory( new PropertyValueFactory<>( "titulo" ) );
        tcTipo.setCellValueFactory( new PropertyValueFactory<>( "tipo" ) );
        tcTamanio.setCellValueFactory( new PropertyValueFactory<>( "Tamanio" ) );
    }

    /**
     * Elimina del sistema los archivos de consulta seleccionados en la pantalla anterior.
     */
    private void EliminarArchivosConsulta() {
        for (ArchivoConsulta archivoEliminar : archivosElegidos) {
            archivoConsultaDAO.Delete( archivoEliminar.GetId() );
        }
    }

    /**
     * Regresa a la pantalla principal del docente
     * @param mouseEvent evento que inicia el metodo
     */
    public void ClicCancelar(MouseEvent mouseEvent) {
        screenChanger.ShowScreenPrincipalDocente(mouseEvent,errorText);
    }

    /**
     * Elimina los archivos de consulta elegidos y mostrados en la tabla
     * @param mouseEvent evento que inicia el metodo
     */
    public void ClicAceptar(MouseEvent mouseEvent) {
        if( archivosElegidos.size() > 0 ){
            try {
                EliminarArchivosConsulta();
                MostrarMensajeExito();
                screenChanger.ShowScreenPrincipalDocente(mouseEvent, errorText);
            } catch (Exception exception) {
                errorText.setText( outputMessages.DatabaseConnectionFailed3() );
            }
        } else {
            errorText.setText( outputMessages.FileNotSelectedToDelete() );
        }
    }

    private void MostrarMensajeExito() {
        Alert mensajeExito = new Alert( Alert.AlertType.CONFIRMATION );

        mensajeExito.setContentText( outputMessages.DeleteFileSucceded() );
        mensajeExito.setHeaderText(null);
        mensajeExito.setTitle(null);

        mensajeExito.show();
    }
}
