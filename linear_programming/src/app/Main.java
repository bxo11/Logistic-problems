package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("input.fxml"));
        primaryStage.setTitle("Linear programming");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();

       System.out.println( "w4".matches("[a-zA-Z]*[0-9]"));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
