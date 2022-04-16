/*Represents controller for RandomShapes */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;

import java.util.Random;

public class DrawRandomShapesController {

    final int NUM_OF_SHAPES = 3; //number of different shapes
    final int NUM_OF_TIMES_TO_DRAW_BUTTON = 10; //number of time to draw for draw shapes button
    final static double QUARTER = 0.25;

    @FXML
    private Canvas canvas;


    //draws 10 random colored shapes, limited in size to quarter of the canvas, in use with same name button
    public void drawTenRandomColoredShapesQuarterCanvasMaxSizedButton(ActionEvent e) {
        clearCanvas();
        for (int i = 0; i < NUM_OF_TIMES_TO_DRAW_BUTTON; i++) {
            drawRandomColoredShapeSizeLimited(QUARTER);
        }
    }

    //clears the canvas
    private void clearCanvas() {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    //draws on canvas a random colored shape
    private void drawRandomColoredShapeSizeLimited(double sizeLimit) {
        // 0:rectangle, 1:oval, 2:line
        switch (getRandomShapeIntCase()) {
            case 0: //rectangle
                new RandomRectangleSizeLimited(canvas.getWidth(), canvas.getHeight(), sizeLimit).draw(canvas.getGraphicsContext2D());
                break;
            case 1: //oval
                new RandomOvalSizeLimited(canvas.getWidth(), canvas.getHeight(), sizeLimit).draw(canvas.getGraphicsContext2D());
                break;
            case 2: //line
                new RandomLineSizeLimited(canvas.getWidth(), canvas.getHeight(), sizeLimit).draw(canvas.getGraphicsContext2D());
                break;
        }
    }

    //returns random int within 0-number of random shapes, in use for switch case of drawQuarterRandomColoredShape
    private int getRandomShapeIntCase() {
        Random rand = new Random();
        return rand.nextInt(NUM_OF_SHAPES);
    }

}
