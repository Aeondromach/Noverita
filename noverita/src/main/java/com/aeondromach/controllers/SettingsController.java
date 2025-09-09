package com.aeondromach.controllers;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import com.aeondromach.App;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SettingsController {
    @FXML private Button closeBtn;
    @FXML private BorderPane titleBar;

    private double mousePosX, mousePosY;

    /**
     * Runs initial Settings set up
     */
    @FXML
    protected void initialize() {
        App.setTheme();
    }

    /**
     * Handles the close button action
     * @param event
     */
    @FXML
    protected void handleCloseClick(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY)){
            Stage stage = (Stage) closeBtn.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Handles when title bar is let go of
     * @param event
     */
    @FXML protected void handleTitleBarUnPress(MouseEvent event) {
        Stage stage = (Stage) titleBar.getScene().getWindow();
        if (stage.getY() < 0) stage.setY(2);
    }

    /**
     * Handles dragging app
     * @param event
     */
    @FXML
    protected void handleTitleBarDrag(MouseEvent event) {
        Scene scene = (Scene) titleBar.getScene();
        if(event.getButton().equals(MouseButton.PRIMARY)){
            Stage stage = (Stage) titleBar.getScene().getWindow();
            Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
            java.awt.Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
            int taskBarHeight = scrnSize.height - winSize.height;
            double maxThreshold = Screen.getPrimary().getBounds().getHeight() - taskBarHeight;

            
            stage.setX(event.getScreenX() - mousePosX);
            if (event.getScreenY() < maxThreshold) {
                stage.setY(event.getScreenY() - mousePosY);
            }
        }
    }

    /**
     * Activates when title bar is clicked, copies mouse position
     * @param event
     */
    @FXML
    protected void handleTitleBarPress(MouseEvent event) {
        Scene scene = (Scene) titleBar.getScene();
        if(event.getButton().equals(MouseButton.PRIMARY)){
            mousePosX = event.getX();
            mousePosY = event.getY();
        }
    }
}