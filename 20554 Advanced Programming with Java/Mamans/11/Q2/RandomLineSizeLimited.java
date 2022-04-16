/*Represents Random Line With size limit*/
import javafx.scene.canvas.GraphicsContext;
import java.util.Random;

public class RandomLineSizeLimited {
    private static Random rand = new Random();
    private Point point1;
    private Point point2;

    public RandomLineSizeLimited(double canvasWidth,double canvasHeight , double sizeLimit){
        point1 = new Point(rand.nextInt((int) canvasWidth ),rand.nextInt((int) canvasHeight));
        point2 = new Point(point1.getX() + rand.nextInt((int)(canvasWidth * sizeLimit)),
                point1.getY() + rand.nextInt((int)(canvasHeight * sizeLimit)));
    }

    public void draw(GraphicsContext gc){
        gc.setStroke(RandomColor.create());
        gc.strokeLine(point1.getX(),point1.getY(),
                point2.getX(),point2.getY());
    }
}
