package com.example.slideshow;


import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ImageViewController {

    private  final List<ImageObject> images = new ArrayList<>();


    private  int currentImageIndex = 0;

    private boolean isSlideShowRunning = false;



    @FXML
    private Label redCount, greenCount, blueCount, mixedCount;



    @FXML
    private ChoiceBox<Integer> timeChoiceBox;

    @FXML
    private Label imageTitle;

    PixelCounter px = new PixelCounter();


    @FXML
    Parent root;

    @FXML
    private ImageView imageView;


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
                try {
                    getColorCountFromImageFilePath(f.getAbsolutePath());
                    ImageObject image = new ImageObject(f.getAbsolutePath(), f.getName(), px.getRc(), px.getGc(), px.getBc(), px.getMc());
                    images.add(image);
                    px.reset();

                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            displayImage();
        }
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
        if (!images.isEmpty())
        {
            currentImageIndex = (currentImageIndex + 1) % images.size();
            displayImage();

        }
    }

    private Timer timer;

    @FXML
    private void handleBtnStartSlideShow() {


        isSlideShowRunning = true;

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.execute(() -> {

        if (!images.isEmpty()) {
            timer = new Timer(1000, e -> {
                currentImageIndex = (currentImageIndex + 1) % images.size();
                try {
                    displayImage();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            timer.start();

        }
        });



        executorService.shutdown();
    }

    @FXML
    private void handleBtnStopSlideShow() {
        isSlideShowRunning = false;
        timer.stop();
        System.out.println(isSlideShowRunning);
    }


    public void initialize() throws IOException {


        timeChoiceBox.getItems().addAll(1, 2, 3, 4, 5);
        timeChoiceBox.setValue(1);
        timeChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (isSlideShowRunning) {
                timer.setDelay(newValue * 1000);
            }
        });




    }

    private void displayImage() throws IOException {
        if (!images.isEmpty())
        {

            imageView.setImage(new Image(images.get(currentImageIndex).getFilepath()));


            
            Platform.runLater(() -> {
                imageTitle.setText(images.get(currentImageIndex).getFileName());
                redCount.setText(String.valueOf(images.get(currentImageIndex).getR()));
                greenCount.setText(String.valueOf(images.get(currentImageIndex).getG()));
                blueCount.setText(String.valueOf(images.get(currentImageIndex).getB()));
                mixedCount.setText(String.valueOf(images.get(currentImageIndex).getM()));
            });




        }
    }


    public void getColorCountFromImageFilePath(String filepath) throws IOException, InterruptedException {


        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.execute(() -> {
            try {
                px.countColorPixels((ImageIO.read(new File(filepath))));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        executorService.shutdown();

        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES); // Wait for thread to finish
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }


}