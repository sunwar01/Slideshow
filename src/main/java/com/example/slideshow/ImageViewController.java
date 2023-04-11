package com.example.slideshow;


import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import java.util.List;

public class ImageViewController {

    private  final List<Image> images = new ArrayList<>();
    private  int currentImageIndex = 0;

    private boolean isSlideShowRunning = false;

    @FXML
    Parent root;

    @FXML
    private ImageView imageView;

    @FXML
    private Label redCount, greenCount, blueCount, mixedCount;

    @FXML
    private void handleBtnLoadAction()
    {
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


    public BufferedImage getCurrentImage()
    {
        return images.get(currentImageIndex);
    }
    @FXML
    private void handleBtnPreviousAction()
    {
        if (!images.isEmpty())
        {
            currentImageIndex =
                    (currentImageIndex - 1 + images.size()) % images.size();
            displayImage();
        }
    }



    @FXML
    private void handleBtnNextAction()
    {
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
                displayImage();
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
            System.out.println("SlideShow is stopped");
            System.out.println(isSlideShowRunning);

    }


    private void displayImage()
    {
        if (!images.isEmpty())
        {
            imageView.setImage(images.get(currentImageIndex));
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