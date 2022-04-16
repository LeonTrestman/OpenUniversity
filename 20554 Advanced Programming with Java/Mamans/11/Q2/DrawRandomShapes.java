/*
Maman 11 Q 2, Leon Trestman 
JavaFx program with a single button that generates 10 random color shapes on canvas
the shape are : lines , rectangles , ovals
the shapes are limited in size to quarter of canvas size
*/

/*Represents DrawRandomShapes  Class*/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DrawRandomShapes extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("DrawRandomShapes.fxml"));
        primaryStage.setTitle("Maman11Q2,Drawing Ten random colored shapes");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
