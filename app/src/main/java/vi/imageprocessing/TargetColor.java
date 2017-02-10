package vi.imageprocessing;

/**
 *
 * this class just keeps track of color values and possibly sets some of the
 * RGB components.
 *
 * Created by Alerik Vi on 2/9/2017.
 */

public class TargetColor {

    int red;
    int green;
    int blue;

    public TargetColor(int r, int g, int b) {
        red = r;
        green = g;
        blue = b;
    }

    //getters
    public int getRed() {
        return red;
    }
    public int getGreen() {
        return green;
    }
    public int getBlue() {
        return blue;
    }

    //setters
    public void setRed(int r) {
        red = r;
    }
    public void setGreen(int g) {
        green = g;
    }
    public void setBlue(int b) {
        blue = b;
    }




}