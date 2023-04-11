package com.example.slideshow;


import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

public class ImageViewController {

    private  final List<Image> images = new ArrayList<>();
    private  int currentImageIndex = 0;

    private boolean isSlideShowRunning = false;

    PixelCounter pixelCounter = new PixelCounter();

    @FXML
    Parent root;

    @FXML
    private ImageView imageView;

    @FXML
    private Label redCount, greenCount, blueCount, mixedCount;

    @FXML
    private void handleBtnLoadAction() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image files");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images",
                "*.png", "*.jpg", "*.gif", "*.tif", "*.bmp"));
        List<File> files = fileChooser.showOpenMultipleDialog(new Stage());

        if (!files.isEmpty())
        {
            files.forEach((File f) ->
            {
                images.add(new Image(f.toURI().toString()));
            });
            displayImage();



        }
    }


    public BufferedImage getCurrentImage() throws IOException {

        BufferedImage in = ImageIO.read((ImageInputStream) images.get(currentImageIndex));

        BufferedImage newImage = new BufferedImage(
                in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = newImage.createGraphics();
        g.drawImage(in, 0, 0, null);
        g.dispose();

        newImage.getGraphics().drawImage(in, 0, 0, null);

        return newImage;
    }
    @FXML
    private void handleBtnPreviousAction() throws IOException {
        if (!images.isEmpty())
        {
            currentImageIndex =
                    (currentImageIndex - 1 + images.size()) % images.size();
            displayImage();
        }
    }



    @FXML
    private void handleBtnNextAction() throws IOException {
        if (!images.isEmpty()) {
            currentImageIndex = (currentImageIndex + 1) % images.size();
            displayImage();
    
        }
    }


    @FXML
    private void handleBtnStartSlideShow()
    {

        isSlideShowRunning = true;

        if (!images.isEmpty())
        {
            Timer timer = new Timer(1000, e -> {
                currentImageIndex = (currentImageIndex + 1) % images.size();
                try {
                    displayImage();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            timer.start();
            System.out.println("SlideShow is running");
        }
        System.out.println(isSlideShowRunning);
    }

    @FXML
    private void handleBtnStopSlideShow()
    {

        isSlideShowRunning = false;
        System.out.println(isSlideShowRunning);

    }


    public void initialize() {


    }

    private void displayImage() throws IOException {
        if (!images.isEmpty())
        {
            imageView.setImage(images.get(currentImageIndex));
            showColorCount();
        }
    }

    public void showColorCount() throws IOException {
        String filepath = images.get(currentImageIndex).getUrl();

        PixelCounter.countColorPixels((ImageIO.read(new File(filepath.substring(6)))));


        redCount.setText(String.valueOf(PixelCounter.getRc()));
        greenCount.setText(String.valueOf(PixelCounter.getGc()));
        blueCount.setText(String.valueOf(PixelCounter.getBc()));
        mixedCount.setText(String.valueOf(PixelCounter.getMc()));
    }
}