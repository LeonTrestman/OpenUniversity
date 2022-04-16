/*Represents Random rectangle With size limit*/
import javafx.scene.canvas.GraphicsContext;
import java.util.Random;

public class RandomRectangleSizeLimited {
    private static Random rand = new Random();
    private Point point;
    private int width;
    private int height;

    public RandomRectangleSizeLimited(double canvasWidth,double canvasHeight , double sizeLimit){
        point = new Point(rand.nextInt( (int) canvasWidth ),rand.nextInt((int) canvasHeight));
        width = rand.nextInt((int)(canvasWidth * sizeLimit)) ;
        height = rand.nextInt((int)(canvasHeight * sizeLimit));
    }

    public void draw(GraphicsContext gc){
        gc.setFill(RandomColor.create());
        gc.fillRect(point.getX(), point.getY(), width, height);
    }

}
