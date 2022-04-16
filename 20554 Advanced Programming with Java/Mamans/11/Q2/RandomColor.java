/*Class of a random color with full opacity*/

import javafx.scene.paint.Color;

import java.util.Random;

public class RandomColor {
    private final static int MAX_RGB_VALUE = 256; //nextInt is n exclusive
    private final static int FULL_OPACITY = 1;

    public static Color create() {
        Random rand = new Random();
        return Color.rgb(rand.nextInt(MAX_RGB_VALUE), rand.nextInt(MAX_RGB_VALUE), rand.nextInt(MAX_RGB_VALUE), FULL_OPACITY);
    }


}
