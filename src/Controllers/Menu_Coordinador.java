package Controllers;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class Menu_Coordinador implements Initializable {

    @FXML
    private Label lbNombres;

    @FXML
    private Label lbApellidos;

    @FXML
    private Label lbNoTrabajador;

    @FXML
    private Button btnReportes;

    @FXML
    private Button btnEstudiantes;

    @FXML
    private Button btnOrganizaciones;

    @FXML
    private Button btnProyectos;

    @FXML
    private Button btnCerrarSesion;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }
}
