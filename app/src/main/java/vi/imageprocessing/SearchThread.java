package vi.imageprocessing;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

/**
 * Created by timothyhamby13 on 2/9/2017.
 */

public class SearchThread implements Runnable {
    private Bitmap img;
    private int startX;
    private int stopX;
    private Color2 target;
    private int frequency = 0;


    public SearchThread(Bitmap img, int startX, int stopX, Color2 target){
        this.img = img;
        this.startX = startX;
        this.stopX = stopX;
        this.target = target;
        Log.d("SearchThread", "Thread created");
    }
    @Override
    public void run() {
        try{
            Log.d("SearchThreadRun", "starting search");
            findColor(img, startX, stopX);
            System.out.println("This thread found " + frequency + " pixels matching target");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void findColor(Bitmap image, int startX, int stopX) {

        // image size
        int height = image.getHeight();
        int pixel;

        Color2 temp = new Color2();

        // start the scan through the first region
        for (int y = 0; y < height; y++) {
            for (int x = startX; x < stopX; x++) {
                // get pixel color
                pixel = image.getPixel(x, y);
                //update temp color to be color of current pixel
                temp.setRed(Color.red(pixel));
                temp.setGreen(Color.green(pixel));
                temp.setBlue(Color.blue(pixel));

                if (temp.compareColor(target)) {
                    frequency++;
                }
            }
        }
    }
}
