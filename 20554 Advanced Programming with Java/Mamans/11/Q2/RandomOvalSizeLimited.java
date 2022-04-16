/*Represents Random oval With size limit*/
import javafx.scene.canvas.GraphicsContext;
import java.util.Random;

public class RandomOvalSizeLimited {
    private static Random rand = new Random();
    private Point point;
    private int width;
    private int height;

    public RandomOvalSizeLimited(double canvasWidth,double canvasHeight , double sizeLimit){
        point = new Point(rand.nextInt( (int) canvasWidth ),rand.nextInt((int) canvasHeight));
        width = rand.nextInt((int)(canvasWidth * sizeLimit)) ;
        height = rand.nextInt((int)(canvasHeight * sizeLimit));
    }

    public void draw(GraphicsContext gc){
        gc.setFill(RandomColor.create());
        gc.fillOval(point.getX(), point.getY(), width, height);
    }

}
