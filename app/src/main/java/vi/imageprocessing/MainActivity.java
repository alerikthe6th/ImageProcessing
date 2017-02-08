package vi.imageprocessing;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;


/**
 * GOALS:
 *
 *  0)RETRIEVE AN IMAGE (dont)
 *
 *  1) TRANSLATE TO A BITMAP
 *
 *  2) DETECT COLOR
 */

public class MainActivity extends AppCompatActivity {


    private ImageView imgView;
    private Bitmap img;
    private TextView tvColorDet;

    public static final int IMAGE_GALLERY_REQUEST = 20;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // load image view control
        imgView =(ImageView)findViewById(R.id.imgView);
        tvColorDet = (TextView) findViewById(R.id.tvColorDet);

    }

    public void doStuff(View view) {

        Bitmap newBMP = doBrightness(img, 90);
        imgView.setImageBitmap(newBMP);
    }

    public void doMoreStuff(View view) {

        getPixelData(img);
    }

    //this method retrieves the RGBA values of the middle pixel of a picture using a bit map
    public void getPixelData(Bitmap src) {
        // image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;


        pixel = src.getPixel(width/2, height/2);
        A = Color.alpha(pixel);
        R = Color.red(pixel);
        G = Color.green(pixel);
        B = Color.blue(pixel);

        tvColorDet.setText("A: " + A + " R: " + R + " G: " + G + " B: " + B);

    }



    public void findColor(Bitmap src) {

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
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;

        int leftBoundCenter = width/2 - 10;  // left bound for the center region
        int rightBoundCenter = width/2 + 10; // right bound for the center region


        //target color
        //int targetA = 255; // i dont think we care about the transparency since image is coming from a camera
        int targetR = 150;
        int targetG = 50;
        int targetB = 50;

        // scan through middle column of pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                //A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);

                //if the detected red value is more red or at least 90% as red as our target red
                if(R >= targetR || R < targetR*.9) {
                    //red detected?
                }
                //if the detected green value is more red or at least 90% as green as our target green
                if(G >= targetG || G < targetG*.9) {
                    //red detected?
                }
                //if the detected blue value is more blue or at least 90% as red as our target blue
                if(B >= targetB || B < targetB*.9) {
                    //red detected?
                }


            }
        }

    }

    /**
     * doBrightness increases the "brightess" of a bmp based on a passed in value
     *
     * @param src
     * @param value
     * @return
     */
    public static Bitmap doBrightness(Bitmap src, int value) {
        // image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);

                // increase/decrease each channel
                R += value;
                if(R > 255) { R = 255; }
                else if(R < 0) { R = 0; }

                G += value;
                if(G > 255) { G = 255; }
                else if(G < 0) { G = 0; }

                B += value;
                if(B > 255) { B = 255; }
                else if(B < 0) { B = 0; }

                // apply new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image
        return bmOut;
    }

    /**
     * This method will be invoked when the user clicks a button
     * @param v
     */
    public void onImageGalleryClicked(View v) {
        // invoke the image gallery using an implict intent.
        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);

        // where do we want to find the data?
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        // finally, get a URI representation
        Uri data = Uri.parse(pictureDirectoryPath);

        // set the data and type.  Get all image types.
        photoPickerIntent.setDataAndType(data, "image/*");

        // we will invoke this activity, and get something back from it.
        startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // if we are here, everything processed successfully.
            if (requestCode == IMAGE_GALLERY_REQUEST) {
                // if we are here, we are hearing back from the image gallery.

                // the address of the image on the SD Card.
                Uri imageUri = data.getData();

                // declare a stream to read the image data from the SD Card.
                InputStream inputStream;

                // we are getting an input stream, based on the URI of the image.
                try {



                    inputStream = getContentResolver().openInputStream(imageUri);

                    // get a bitmap from the stream.
                    Bitmap image = BitmapFactory.decodeStream(inputStream);
                    img = image;

                    // show the image to the user
                    imgView.setImageBitmap(image);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    // show a message to the user indictating that the image is unavailable.
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
                }

            }
        }
    }
}
