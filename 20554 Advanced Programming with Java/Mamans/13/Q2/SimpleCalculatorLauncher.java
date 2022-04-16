/*
Maman 13 Q2, Leon Trestman , simple calculator application
*/

/*Represents launcher for simple calculator*/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SimpleCalculatorLauncher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("SimpleCalculator.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Simple Calculator");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
