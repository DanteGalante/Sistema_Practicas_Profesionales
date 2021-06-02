package Controllers;

import Database.EstudianteDAO;
import Entities.Estudiante;
import Utilities.ScreenChanger;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class GenerarReporte_Coordinador implements Initializable {
    private List< Estudiante > listaGrupos = new ArrayList< Estudiante>();
    private EstudianteDAO estudiantes = new EstudianteDAO();
    private List< Estudiante > listaEstudiantes = new ArrayList< Estudiante>();
    private ScreenChanger screenChanger = new ScreenChanger();
    EstudianteDAO estudiante = new EstudianteDAO();
    List listaEstudiante = estudiante.ReadAll();

    @FXML
    private Label lbNombres;

    @FXML
    private Label lbApellidos;

    @FXML
    private Label lbNoTrabajador;

    @FXML
    private Button btnGestionarProyectos;

    @FXML
    private Button btnRegresar;

    @FXML
    private Text errorText;

    @FXML
    private Text successText;

    @FXML
    private Rectangle tfReporteGenerado;

//la posible libreria: com.itextpdf.maven:itextdoc:2.0.0

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GenerarReporte();
    }

     public void GenerarReporte() {
        try {
            var doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("ReporteSistema.pdf"));
            doc.open();

            var bold = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            var paragraph = new Paragraph("Reporte Inscripciones");
            var table = new PdfPTable(3);
            Stream.of("Estudiante","Matricula","Proyecto").forEach(table::addCell);

            Arrays.stream(RecuperarNombreEstudiante().stream().toArray()).forEach(val ->{
               table.addCell(val.toString());
               table.addCell(val.toString());
               table.addCell(val.toString());
            });
            paragraph.add(table);

            doc.add(paragraph);
            doc.close();

            tfReporteGenerado = (PdfReader.getNormalizedRectangle(null));
        }catch (Exception e){
            e.printStackTrace();
        }
     }

    public List RecuperarNombreEstudiante(){
        List<String> listaAuxiliar = new ArrayList<>();
        listaEstudiantes = estudiantes.ReadAll();
        for( Estudiante estudiante : listaEstudiantes ) {
            listaAuxiliar.add( estudiante.getNombres());
        }
        List<String> arregloNombres = listaAuxiliar;
        return arregloNombres;
    }


    /**
     * Cambia la pantalla de GestionarOrganizacion_Coordinador a la pantalla Principal_Coordinador.
     * @param mouseEvent el evento de mouse que activo la acción.
     */
    public void ClicRegresar ( MouseEvent mouseEvent ){
        screenChanger.MostrarPantallaPrincipalCoordinador( mouseEvent, errorText );
    }
}
