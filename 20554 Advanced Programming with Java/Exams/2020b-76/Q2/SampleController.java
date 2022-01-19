
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javax.swing.*;
import java.util.*;

public class SampleController {
    @FXML
    private Canvas canvas;

    private Map<Point2D,String> map;

    @FXML
    public void initialize() {
        map = new HashMap<Point2D,String>();
    }

    @FXML
    public void onCanvasClick(MouseEvent event) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double x = event.getX();
        double y = event.getY();


        Iterator<Map.Entry<Point2D, String>> itr = map.entrySet().iterator();
        while(itr.hasNext()) {
            Map.Entry<Point2D, String> entry = itr.next();
            if (entry.getKey().distance(x,y) < 10) {
                itr.remove();
            }
        }

        String s = JOptionPane.showInputDialog("Enter a string");
        map.put(new Point2D(x,y),s);
        gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());

        itr = map.entrySet().iterator();
        while(itr.hasNext()) {
            Map.Entry<Point2D, String> entry = itr.next();
            gc.strokeText(entry.getValue(), entry.getKey().getX(), entry.getKey().getY());
        }

    }

}
