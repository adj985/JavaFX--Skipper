/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skipper;

import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;




/**
 *
 * @author Coa
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        URL location = getClass().getClassLoader().getResource("skipper/SkipperView.fxml");
        Parent root = FXMLLoader.<Parent>load(location);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            System.exit(1);
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

       
}