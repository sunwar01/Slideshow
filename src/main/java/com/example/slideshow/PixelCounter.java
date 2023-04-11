package com.example.slideshow;


import java.awt.image.BufferedImage;
import java.io.IOException;



public class PixelCounter  {

    //private final ImageViewController imageViewController = new ImageViewController();

    private static int rc = 0;
    private static int gc = 0;
    private static int bc = 0;
    private static int mc = 0;
    private static int pc = 0;



    public static void countColorPixels(BufferedImage img) throws IOException {

        BufferedImage image = img;
        int width = image.getWidth();
        int height = image.getHeight();

        int redCount = 0;
        int blueCount = 0;
        int greenCount = 0;
        int mixedCount = 0;
        int pixelCount = 0;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height ; y++) {

                int rgb = image.getRGB(x, y);

                int red   = (rgb >>> 16) & 0xFF;
                int green = (rgb >>>  8) & 0xFF;
                int blue  = (rgb >>>  0) & 0xFF;

                if (red > blue && red > green) {
                    redCount++;
                }
                if (blue > red && blue > green){
                    blueCount++;
                }
                if (green > red && green > blue){
                    greenCount++;
                }else if (green == red && red > blue || blue == red && red > green || green == blue && blue > red){
                    mixedCount++;
                }

                pixelCount++;
            }
        }

        rc = redCount;
        gc = greenCount;
        bc = blueCount;
        mc = mixedCount;
        pc = pixelCount;

        System.out.println("Red Pixel Count:" + redCount);
        System.out.println("Green Pixel Count:" + greenCount);
        System.out.println("Blue Pixel Count:" + blueCount);
        System.out.println("Mixed color Pixel Count:" + mixedCount);
        System.out.println("Pixel Count:" + pixelCount);
    }

    public static int getRc(){
        return rc;
    }

    public static int getGc(){
        return gc;
    }

    public static int getBc(){
        return bc;
    }

    public static int getMc(){
        return mc;
    }

}
