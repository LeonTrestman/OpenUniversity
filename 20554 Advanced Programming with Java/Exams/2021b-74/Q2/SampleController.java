
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class SampleController {

    @FXML
    private Button go;

    @FXML
    private Canvas canvas;


    private boolean finishedDrawing = false;
    private boolean gameFinished = false;
    private int rectSize = 10;
    private Random rand = new Random();
    private ArrayList<Rectangle> rects = new ArrayList<>();
    private long startTime = System.currentTimeMillis();


    @FXML
    private void onGoButtonPressed(ActionEvent event) {
        //start new game
        if (gameFinished) {
            finishedDrawing = false;
            gameFinished = false;
        }

        if(!finishedDrawing) {
            for (int i = 0; i < 10; i++) {
                draw();
                finishedDrawing = true;
            }
        }
    }

    @FXML
    public void onCanvasClicked(MouseEvent event) {

        GraphicsContext gc = canvas.getGraphicsContext2D();
        Point2D point = new Point2D(event.getX(), event.getY());
        Iterator<Rectangle> iterator = rects.iterator();

        //checking if clicked on a rectangle
        while (iterator.hasNext()) {
            Rectangle rect = iterator.next();
            if (rect.contains(point)) {
                gc.clearRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
                iterator.remove();
            }
        }

        //if end of game show alert
        if (rects.isEmpty()) {
            gameFinished = true;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game finished");
            alert.setHeaderText("Congratulations! You've beaten the game!");
            alert.setContentText("Your time is " + (System.currentTimeMillis() - startTime) / 1000 + " seconds");
            alert.showAndWait();
        }
    }

    private void draw() {
        int x =rand.nextInt((int)canvas.getHeight()-rectSize);
        int y = rand.nextInt((int)canvas.getWidth()-rectSize);
        Rectangle rect = new Rectangle (x, y,rectSize,rectSize);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.fillRect(x,y,rectSize,rectSize);
        rects.add(rect);
    }


}
