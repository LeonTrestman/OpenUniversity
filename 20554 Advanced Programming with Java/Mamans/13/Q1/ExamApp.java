/*
Maman 13 Q1, Leon Trestman , Exam application
*/

/*Represents Exam App*/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ExamApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ExamApp.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Exam application");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
