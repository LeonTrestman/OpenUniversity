
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
    private Canvas canvas;

    private GraphicsContext gc;


    @FXML
    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        XShape x = new XShape(0,0,35);
        XShape y = new XShape(20,20,35);

        DrawBoard db = new DrawBoard(canvas);
        db.add(x);
        db.add(y);
        db.remove(x);

    }


}
