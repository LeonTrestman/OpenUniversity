
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;


public class SampleController {

    @FXML
    private Canvas canvas;
    private GraphicsContext gc;
    private Rectangle[][] rects;


    private static int matSize = 10;


    @FXML
    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        rects = new Rectangle[matSize][matSize];
        gc.setFill(javafx.scene.paint.Color.WHITE);

        for (int i = 0; i < matSize; i++) {
            for (int j = 0; j < matSize; j++) {
                rects[i][j] = new Rectangle(i*canvas.getWidth() / matSize,j*canvas.getHeight() / matSize,canvas.getWidth() / matSize, canvas.getHeight() / matSize);
            }
        }
        onClearClick();
    }

    @FXML
    private void onCanvasClick (MouseEvent event) {
        for (int i = 0; i < matSize; i++) {
            for (int j = 0; j < matSize; j++) {
                //finding which rectangle was clicked
                if (rects[i][j].contains(event.getX(), event.getY())) {
                    //set color
                        if (rects[i][j].getFill().equals(javafx.scene.paint.Color.WHITE) ) {
                            rects[i][j].setFill(javafx.scene.paint.Color.BLACK);
                        }else
                            rects[i][j].setFill(javafx.scene.paint.Color.WHITE);
                    //draw rectangle with new color
                        gc.setFill(rects[i][j].getFill());
                        gc.fillRect(rects[i][j].getX(), rects[i][j].getY(), rects[i][j].getWidth(), rects[i][j].getHeight());
                }
            }
        }
    }

    @FXML
    private void onClearClick () {
        resetReactsColor();
        gc.setFill(javafx.scene.paint.Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void resetReactsColor() {
        for (int i = 0; i < matSize; i++) {
            for (int j = 0; j < matSize; j++) {
                rects[i][j].setFill(javafx.scene.paint.Color.WHITE);
            }
        }
    }


}
