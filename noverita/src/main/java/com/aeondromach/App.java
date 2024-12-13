/**
 * @author Evelyn Engleman @Æondromach
 * @version 3
 * @since 12/07/2024
 * App driver for Noverita
 */

package com.aeondromach;

import java.io.IOException;

import static com.aeondromach.HeaderController.isMax;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static final int RESIZE_MARGIN = 5;
    private double xOffset = 0, yOffset = 0;
    private BorderPane mainPane;

    /**
     * Initial set-up of Noverita
     */
    @Override
    public void start(@SuppressWarnings("exports") Stage stage) throws IOException {
        // get and set scene
        scene = new Scene(loadFXML("NovFX"), 1150, 550);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);

        stage.setMinHeight(550);
        stage.setMinWidth(1150);

        mainPane = (BorderPane) scene.lookup("#mainAppPane");

        stage.widthProperty().addListener(new ResizeHandler.NewResizeWidthChange(mainPane));
        stage.heightProperty().addListener(new ResizeHandler.NewResizeHeightChange(mainPane));

        // Set task bar title
        stage.setTitle("Noverita");

        Screen screen = Screen.getPrimary();
        double dpi = screen.getDpi();
        double scaleFactor = dpi / 96.0;

        stage.outputScaleXProperty().add(scaleFactor);
        stage.outputScaleYProperty().add(scaleFactor);

        addResizeHandlers(stage, scene);

        // get and set icon
        Image icon = new Image(getClass().getResourceAsStream("images/AeondromachBackground.png"));
        stage.getIcons().add(icon);

        // Set app background to transparent and remove windows decorations
        stage.initStyle(StageStyle.TRANSPARENT);

        stage.focusedProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if (newValue) {
                    scene.lookup("#noveritaText").setStyle("-fx-opacity: 1;");
                    if (!isMax) { scene.getRoot().setStyle("-borderColor: rgba(100, 100, 100, 0.75);"); }
                } else {
                    scene.lookup("#noveritaText").setStyle("-fx-opacity: 0.75;");
                    if (!isMax) { scene.getRoot().setStyle("-borderColor: rgba(100, 100, 100, 0.4);"); }
                }
            });            
        });

        stage.show(); // run
    }

    /**
     * Adds resize handles to all edges/corners
     * @param stage
     * @param scene
     */
    private void addResizeHandlers(Stage stage, Scene scene) {
        scene.addEventFilter(MouseEvent.MOUSE_MOVED, e -> updateCursor(e, stage));


        scene.addEventFilter(MouseEvent.MOUSE_DRAGGED, e -> resize(stage, e));
    }

    /**
     * Updates cursor type based on handler
     * @param e
     * @param stage
     */
    private void updateCursor(javafx.scene.input.MouseEvent e, Stage stage) {
        if (!isMax) {
            double x = e.getSceneX(), y = e.getSceneY();

            if (x < RESIZE_MARGIN && y < RESIZE_MARGIN) {
                stage.getScene().setCursor(javafx.scene.Cursor.NW_RESIZE);
            } 
            else if (x > mainPane.getWidth() - RESIZE_MARGIN && y < RESIZE_MARGIN) {
                stage.getScene().setCursor(javafx.scene.Cursor.NE_RESIZE);
            } 
            else if (x < RESIZE_MARGIN && y > mainPane.getHeight() - RESIZE_MARGIN) {
                stage.getScene().setCursor(javafx.scene.Cursor.SW_RESIZE);
            } 
            else if (x > mainPane.getWidth() - RESIZE_MARGIN && y > mainPane.getHeight() - RESIZE_MARGIN) {
                stage.getScene().setCursor(javafx.scene.Cursor.SE_RESIZE);
            } 
            else if (x < RESIZE_MARGIN) {
                stage.getScene().setCursor(javafx.scene.Cursor.W_RESIZE);
            } 
            else if (x > mainPane.getWidth() - RESIZE_MARGIN) {
                stage.getScene().setCursor(javafx.scene.Cursor.E_RESIZE);
            } 
            else if (y < RESIZE_MARGIN) {
                stage.getScene().setCursor(javafx.scene.Cursor.N_RESIZE);
            } 
            else if (y > mainPane.getHeight() - RESIZE_MARGIN) {
                stage.getScene().setCursor(javafx.scene.Cursor.S_RESIZE);
            } 
            else {
                stage.getScene().setCursor(javafx.scene.Cursor.DEFAULT);
            }
        }
    }

    /**
     * Resizes stage
     * @param stage
     * @param e
     */
    private void resize(Stage stage, javafx.scene.input.MouseEvent e) {
        double x = e.getSceneX(), y = e.getSceneY();
        double screenX = e.getScreenX(), screenY = e.getScreenY();

        if (stage.getScene().getCursor() == javafx.scene.Cursor.NW_RESIZE) {
            double width = stage.getWidth() - (screenX - stage.getX());
            double height = stage.getHeight() - (screenY - stage.getY());
            if (width > RESIZE_MARGIN) {
                if (width >= stage.getMinWidth()) {
                    stage.setWidth(width);
                    stage.setX(screenX);
                }
                else {
                    stage.setWidth(stage.getMinWidth());
                }
            }
            if (height > RESIZE_MARGIN) {
                if (height >= stage.getMinHeight()) {
                    stage.setHeight(height);
                    stage.setY(screenY);
                }
                else {
                    stage.setHeight(stage.getMinHeight());
                }
            }
        } 
        else if (stage.getScene().getCursor() == javafx.scene.Cursor.NE_RESIZE) {
            double width = x;
            double height = stage.getHeight() - (screenY - stage.getY());
            if (width >= stage.getMinWidth()) {
                stage.setWidth(width);
            }
            else {
                stage.setWidth(stage.getMinWidth());
            }

            if (height > RESIZE_MARGIN) {
                if (height >= stage.getMinHeight()) {
                    stage.setHeight(height);
                    stage.setY(screenY);
                }
                else {
                    stage.setHeight(stage.getMinHeight());
                }
            }
        } 
        else if (stage.getScene().getCursor() == javafx.scene.Cursor.SW_RESIZE) {
            double width = stage.getWidth() - (screenX - stage.getX());
            double height = y;
            if (width > RESIZE_MARGIN) {
                if (width >= stage.getMinWidth()) {
                    stage.setWidth(width);
                    stage.setX(screenX);
                }
                else {
                    stage.setWidth(stage.getMinWidth());
                }
            }
            if (height > RESIZE_MARGIN) {
                if (height >= stage.getMinHeight()) {
                    stage.setHeight(height);
                }
                else {
                    stage.setHeight(stage.getMinHeight());
                }
            }
        } 
        else if (stage.getScene().getCursor() == javafx.scene.Cursor.SE_RESIZE) {
            double width = x;
            double height = y;
            if (width >= stage.getMinWidth()) {
                stage.setWidth(width);
            }
            else {
                stage.setWidth(stage.getMinWidth());
            }

            if (height >= stage.getMinHeight()) {
                stage.setHeight(height);
            }
            else {
                stage.setHeight(stage.getMinHeight());
            }
        } 
        else if (stage.getScene().getCursor() == javafx.scene.Cursor.W_RESIZE) {
            double width = stage.getWidth() - (screenX - stage.getX());
            if (width > RESIZE_MARGIN) {
                if (width >= stage.getMinWidth()) {
                    stage.setWidth(width);
                    stage.setX(screenX);
                }
                else {
                    stage.setWidth(stage.getMinWidth());
                }
            }
        } 
        else if (stage.getScene().getCursor() == javafx.scene.Cursor.E_RESIZE) {
            double width = x;
            if (width > RESIZE_MARGIN) {
                if (width >= stage.getMinWidth()) {
                    stage.setWidth(width);
                }
                else {
                    stage.setWidth(stage.getMinWidth());
                }
            }
        } 
        else if (stage.getScene().getCursor() == javafx.scene.Cursor.N_RESIZE) {
            double height = stage.getHeight() - (screenY - stage.getY());
            if (height > RESIZE_MARGIN) {
                if (height >= stage.getMinHeight()) {
                    stage.setHeight(height);
                    stage.setY(screenY);
                }
                else {
                    stage.setHeight(stage.getMinHeight());
                }
            }
        } 
        else if (stage.getScene().getCursor() == javafx.scene.Cursor.S_RESIZE) {
            double height = y;
            if (height >= stage.getMinHeight()) {
                stage.setHeight(height);
            }
            else {
                stage.setHeight(stage.getMinHeight());
            }
        }
    }

    /**
     * Ease of access load fxml file
     * @param fxml
     * @return fxml file with .fxml ending
     * @throws IOException
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Main Driver
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }

}