/*
Maman 14 Q2, Leon Trestman , Phone Book application
*/

/*Represents launcher for Phone book application*/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PhoneBookLauncher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("PhoneBook.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Phone Book");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
