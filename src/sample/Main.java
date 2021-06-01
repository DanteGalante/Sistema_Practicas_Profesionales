/*
* Autor:Christian Felipe de Jesus Avila Valdes
* Versión: 1.0
* Fecha Creación: 22 - mar - 2021
* Descripción:
* Clase Utilizada como punto de entrada de la aplicación.
*/
package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase Utilizada como punto de entrada de la aplicación.
 */
public class Main extends Application {

    @Override
    public void start( Stage primaryStage ) throws Exception{
        Parent root = FXMLLoader.load( getClass().getResource("../Resources/IniciarSesion.fxml") );
        primaryStage.setTitle( "" );
        primaryStage.setScene( new Scene( root, 310, 435 ) );
        primaryStage.show();
    }


    public static void main( String[] args ) {
        launch( args );
    }
}