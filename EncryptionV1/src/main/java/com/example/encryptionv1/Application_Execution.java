//----------------------------------------------------------------------------------------------------
/////=CMPG215 P - Group 40=/////
//Anderson Brian, 40851842
//Bezuidenhout Nicolette, 33770182
//Coetzee Christian, 40513262
//Patel Riya, 41914228
//Ramsunaar Angelina, 41081269
//------------------------------------------------------------------------------------------------------
package com.example.encryptionv1;

//Appropriate files imported
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Application_Execution extends Application {

    //Launches application
    public static void main(String[] args) throws Exception
    {
        launch();
    }

    //Shows the GUI and arranges necessary components/ classes
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader load_Application = new FXMLLoader(Application_Execution.class.getResource("Component_View.fxml"));
        Scene design_scene = new Scene(load_Application.load());
        primaryStage.setTitle("CMPG 215P - Encryption Project");
        primaryStage.setScene(design_scene);
        primaryStage.show();
    }
}
