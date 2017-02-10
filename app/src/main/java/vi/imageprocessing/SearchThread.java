package vi.imageprocessing;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by timothyhamby13 on 2/9/2017.
 */

public class SearchThread implements Runnable {
    private Bitmap img;
    private int startX;
    private int stopX;


    public SearchThread(Bitmap img, int startX, int stopX){
        this.img = img;
        this.startX = startX;
        this.stopX = stopX;
    }
    @Override
    public void run() {
        try{
            Bitmap newImage = findColor(img, startX, stopX);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Bitmap findColor(Bitmap image, int startX, int stopX) { //add color as param to recolor found pixels
        //make for loops go from startx to stopx

        /**
         *
         *
         * 1) divide picture region into three areas: left, right, center
         *
         * 2) center region will probably be about 20 pixels wide, but the full heigbt of the image
         *
         * 3) iterate through row column to find a pixel that matches our goal color
         *
         * 4) setup what we want to find as a goal color
         *
         */


        // image size
        int width = image.getWidth();
        int height = image.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, img.getConfig());
        int pixel;

        //divide the picture into 5 regions
        int reg_size = width / 5;

        //track the number of occurences of the target color
        /*int reg1Freq = 0;
        int reg2Freq = 0;
        int reg3Freq = 0;
        int reg4Freq = 0;
        int reg5Freq = 0;*/
        int frequency = 0;

        //set a lower target color threshold to find
        Color2 red = new Color2(125, 50, 50);
        Color2 temp = new Color2();

        // start the scan through the first region
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // get pixel color
                pixel = image.getPixel(x, y);
                //ipdate temp color to be color of current pixel
                temp.setRed(Color.red(pixel));
                temp.setGreen(Color.green(pixel));
                temp.setBlue(Color.blue(pixel));

                if (temp.compareColor(red)) {
                    bmOut.setPixel(x, y, Color.RED);  //change this to recolor with color from param.
                } else {
                    bmOut.setPixel(x, y, pixel);
                }
            }
        }
        return bmOut;
    }
}
