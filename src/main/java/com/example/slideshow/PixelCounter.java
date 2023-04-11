package com.example.slideshow;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class PixelCounter  {



    private static int rc = 0;
    private static int gc = 0;
    private static int bc = 0;
    private static int mc = 0;
    private static int pc = 0;



    public  void countColorPixels(BufferedImage img) throws IOException {

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.execute(() -> {

            BufferedImage image = img;
            int width = image.getWidth();
            int height = image.getHeight();

            int redCount = 0;
            int blueCount = 0;
            int greenCount = 0;
            int mixedCount = 0;
            int pixelCount = 0;

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {

                    int rgb = image.getRGB(x, y);

                    int red = (rgb >>> 16) & 0xFF;
                    int green = (rgb >>> 8) & 0xFF;
                    int blue = (rgb >>> 0) & 0xFF;

                    if (red > blue && red > green) {
                        redCount++;
                    }
                    if (blue > red && blue > green) {
                        blueCount++;
                    }
                    if (green > red && green > blue) {
                        greenCount++;
                    } else if (green == red && red > blue || blue == red && red > green || green == blue && blue > red) {
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

        });

        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES); // Wait for thread to finish
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    public void reset(){
        rc = 0;
        gc = 0;
        bc = 0;
        mc = 0;
        pc = 0;
    }

    public  int getRc(){
        return rc;
    }

    public  int getGc(){
        return gc;
    }

    public  int getBc(){
        return bc;
    }

    public  int getMc(){
        return mc;
    }

}
