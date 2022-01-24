

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class SampleController {

    @FXML
    private Canvas canvas;


    private Random rand;
    private GraphicsContext gc;
    public static final int RECT_SIZE = 10;
    private int numOfRects = 3; //change here num of drawn reacts
    public int rectDone;
    private int score;
    private ArrayList<Rectangle> rects;

    @FXML
    public void initialize() {
        rects = new ArrayList<>();
        gc = canvas.getGraphicsContext2D();
        rand = new Random();
        score = 0;
        rectDone = 0;

        Thread[] threads = new Thread[numOfRects];
        for (int i = 0; i < numOfRects; i++) {
            threads[i] = new Thread(new DrawingRectThread(gc,i,this));
            threads[i].start();
        }
    }


    public class DrawingRectThread implements Runnable {
        int id ;
        GraphicsContext gc2;
        SampleController controller;
        int x = rand.nextInt(((int)canvas.getWidth())-RECT_SIZE);
        int y = rand.nextInt(((int)canvas.getHeight())-RECT_SIZE);

        public DrawingRectThread(GraphicsContext gc,int id,SampleController controller) {
            this.id = id;
            this.gc2 = gc;
            this.controller = controller;
        }

        @Override
        public void run() {

            Rectangle rect = new Rectangle(x, y, RECT_SIZE, RECT_SIZE);
            controller.drawRect(rect);

            try {
                Thread.sleep(1000 * rand.nextInt(4));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //remove recs
            controller.removeReact(rect);
            if (rectDone == numOfRects) {
                System.out.println("score: " + score);
            }
        }

    }


    private synchronized void drawRect(Rectangle rect) {
        rects.add(rect);
        gc.setFill(Color.BLACK);
        gc.fillRect(rect.getX(),rect.getY(), RECT_SIZE, RECT_SIZE);

    }

    private synchronized void removeReact(Rectangle rect) {
        gc.setFill(Color.WHITE);
        rectDone ++;
        gc.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        rects.remove(rect);
    }


    @FXML
    private synchronized void onCanvasClick(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        int oldscore = score;

        Iterator<Rectangle> iterator = rects.iterator();
        while (iterator.hasNext()) {
            Rectangle rect = iterator.next();
            if (rect.contains(x, y)) {
                score = score +2;
                iterator.remove();
                gc.setFill(Color.WHITE);
                gc.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
            }
        }

        //missing click
        if (score == oldscore)
            score = score - 1;

        }
    }


