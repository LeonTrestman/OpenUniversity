import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;


public class DrawBoard extends Canvas {
    private Canvas canv;
    private List<Shape> shapes;

    public DrawBoard(Canvas canvas) {
        this.canv = canvas;
        shapes =  new ArrayList<Shape>();
    }

    public void add (Shape shape) {
        shapes.add(shape);
        paintComponent();
    }

    public void remove (Shape shape) {
        shapes.remove(shape);
        paintComponent();
    }

    private void paintComponent() {
        GraphicsContext gc = canv.getGraphicsContext2D();
        gc.clearRect(0, 0, canv.getWidth(), canv.getHeight());
        for (Shape shape : shapes) {
            try {
                shape.draw(gc);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error drawing shape");
            }
        }
    }

}
