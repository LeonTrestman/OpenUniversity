import javafx.scene.canvas.GraphicsContext;

public class XShape extends Shape{
    private int x,y,size;

    public XShape(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    @Override
    public void draw(GraphicsContext gc) throws Exception {
        if (x< 0 || y <0)
            throw new Exception("Invalid position");
        gc.strokeLine(x,y,x+size,y+size);
        gc.strokeLine(x+size,y,x,y+size);
    }
}
