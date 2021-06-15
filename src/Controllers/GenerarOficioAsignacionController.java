package Controllers;

import Database.EstudianteDAO;
import Entities.Estudiante;
import Enumerations.EstadoEstudiante;
import Utilities.LoginSession;
import Utilities.OutputMessages;
import Utilities.ScreenChanger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GenerarOficioAsignacionController implements Initializable {
    private ScreenChanger screenChanger = new ScreenChanger();
    private EstudianteDAO estudiantes = new EstudianteDAO();
    private List< Estudiante > listaEstudiantes = new ArrayList< Estudiante>();
    private OutputMessages outputMessages = new OutputMessages();

    @FXML
    private Text TxNombres;

    @FXML
    private Text TxApellidos;

    @FXML
    private Text TxNoTrabajador;

    @FXML
    private Text TxError;

    @FXML
    private TableView< Estudiante> TvEstudiante;

    @FXML
    private TableColumn< Estudiante, String> TcNombre;

    @FXML
    private TableColumn < Estudiante, String> TcMatricula;

    @FXML
    private Button BtGenerar;

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
        ValorColumnasEstudiante();
        MostrarEstudiantes();
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
    }

    /**
     * Agrega todos los estudiantes disponibles a la tabla de estudiantes disponibles
     */
    private void MostrarEstudiantes() {
        listaEstudiantes = estudiantes.ReadAll();
        for( Estudiante estudiante : listaEstudiantes ){
            if( estudiante.getEstado() == EstadoEstudiante.ProyectoAsignado ) {
                estudiante.getNombreCompleto();
                estudiante.getMatricula();
                TvEstudiante.getItems().add( estudiante );
            }
        }
    }

    public void HandleGenerarOficioAsignacion( MouseEvent mouseEvent ){
        if(TvEstudiante.getSelectionModel().getSelectedItem() != null){
            Estudiante estudiante = (Estudiante) TvEstudiante.getSelectionModel().getSelectedItem();
            FileSystem fs = FileSystems.getDefault();//Creamos un File System para poder manejar ficheros

            Path home=fs.getPath("");

            try {
                Process p = new ProcessBuilder("explorer.exe", "/select,"+home.toAbsolutePath()).start();
            } catch (  IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            /*try {
                var doc = new Document();
                PdfWriter.getInstance(doc, new FileOutputStream("OficioAsignación.pdf"));
                doc.open();

                var bold = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
                var style = new Font(Font.FontFamily.HELVETICA, 14);
                var paragraph = new Paragraph("Oficio Asignación \n", bold);
                var table = new PdfPTable(1);
                var paragraphIntro = new Paragraph("El estudiante " + estudiante.getNombres() +" oficialmente " +
                        " cursante de la experiencia educativa de practicas profesionales, se le comunica la asignación " +
                        "de un proyecto \n", style);

                doc.add(paragraph);
                paragraphIntro.add(table);
                doc.add(paragraphIntro);
                doc.close();


            }catch (Exception e){
                e.printStackTrace();
            }*/
        }else{
            TxError.setText(outputMessages.SelectionStudentNull());
        }
    }


    /**
     * Permite cambiar la pantalla a la pantalla Principal de coordinador
     */
    public void MostrarPantallaPrincipalCoordinador( MouseEvent mouseEvent ) {
        screenChanger.MostrarPantallaGestionarEstudianesCoordinador( mouseEvent, TxError );
    }
}


