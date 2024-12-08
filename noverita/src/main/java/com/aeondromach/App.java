/**
 * @author Evelyn Engleman @Æondromach
 * App driver for Noverita
 */

package com.aeondromach;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static final int RESIZE_MARGIN = 10;
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
                    scene.lookup("#bodyAppPane").setStyle("-fx-border-color: rgba(100, 100, 100, 0.75);");
                    scene.lookup("#headsHeader").setStyle("-fx-border-color: rgba(100, 100, 100, 0.75);");
                    scene.lookup("#headsFooter").setStyle("-fx-border-color: rgba(100, 100, 100, 0.75);");
                } else {
                    scene.lookup("#noveritaText").setStyle("-fx-opacity: 0.75;");
                    scene.lookup("#bodyAppPane").setStyle("-fx-border-color: rgba(100, 100, 100, 0.4);");
                    scene.lookup("#headsHeader").setStyle("-fx-border-color: rgba(100, 100, 100, 0.4);");
                    scene.lookup("#headsFooter").setStyle("-fx-border-color: rgba(100, 100, 100, 0.4);");
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
        scene.setOnMousePressed(e -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });

        scene.setOnMouseDragged(e -> {
            if (!isInResizeZone(e, stage)) {
                stage.setX(e.getScreenX() - xOffset);
                stage.setY(e.getScreenY() - yOffset);
            }
        });

        scene.setOnMouseMoved(e -> updateCursor(e, stage));

        scene.setOnMouseDragged(e -> resize(stage, e));
    }

    /**
     * Updates cursor type based on handler
     * @param e
     * @param stage
     */
    private void updateCursor(javafx.scene.input.MouseEvent e, Stage stage) {
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

    /**
     * Resizes stage
     * @param stage
     * @param e
     */
    private void resize(Stage stage, javafx.scene.input.MouseEvent e) {
        double x = e.getSceneX(), y = e.getSceneY();
        double screenX = e.getScreenX(), screenY = e.getScreenY();

        if (stage.getScene().getCursor() == javafx.scene.Cursor.NW_RESIZE) {
            double newWidth = stage.getWidth() - (screenX - stage.getX());
            double newHeight = stage.getHeight() - (screenY - stage.getY());
            if (newWidth > RESIZE_MARGIN) {
                if (newWidth > stage.getMinWidth()) {
                    stage.setWidth(newWidth);
                    stage.setX(screenX);
                }
                else {
                    stage.setWidth(newWidth);
                }
            }
            if (newHeight > RESIZE_MARGIN) {
                if (newHeight >= stage.getMinHeight()) {
                    stage.setHeight(newHeight);
                    stage.setY(screenY);
                }
            }
        } 
        else if (stage.getScene().getCursor() == javafx.scene.Cursor.NE_RESIZE) {
            double newWidth = x;
            double newHeight = stage.getHeight() - (screenY - stage.getY());
            if (newWidth > RESIZE_MARGIN) stage.setWidth(newWidth);
            if (newHeight > RESIZE_MARGIN) {
                if (newHeight >= stage.getMinHeight()) {
                    stage.setHeight(newHeight);
                    stage.setY(screenY);
                }
            }
        } 
        else if (stage.getScene().getCursor() == javafx.scene.Cursor.SW_RESIZE) {
            double newWidth = stage.getWidth() - (screenX - stage.getX());
            double newHeight = y;
            if (newWidth > RESIZE_MARGIN) {
                if (newWidth >= stage.getMinWidth()) {
                    stage.setWidth(newWidth);
                    stage.setX(screenX);
                }
            }
            if (newHeight > RESIZE_MARGIN) stage.setHeight(newHeight);
        } 
        else if (stage.getScene().getCursor() == javafx.scene.Cursor.SE_RESIZE) {
            double newWidth = x;
            double newHeight = y;
            if (newWidth > RESIZE_MARGIN) stage.setWidth(newWidth);
            if (newHeight > RESIZE_MARGIN) stage.setHeight(newHeight);
        } 
        else if (stage.getScene().getCursor() == javafx.scene.Cursor.W_RESIZE) {
            double newWidth = stage.getWidth() - (screenX - stage.getX());
            if (newWidth > RESIZE_MARGIN) {
                if (newWidth >= stage.getMinWidth()) {
                    stage.setWidth(newWidth);
                    stage.setX(screenX);
                }
                else {
                    stage.setWidth(stage.getMinWidth());
                }
            }
        } 
        else if (stage.getScene().getCursor() == javafx.scene.Cursor.E_RESIZE) {
            double newWidth = x;
            if (newWidth > RESIZE_MARGIN) stage.setWidth(newWidth);
        } 
        else if (stage.getScene().getCursor() == javafx.scene.Cursor.N_RESIZE) {
            double newHeight = stage.getHeight() - (screenY - stage.getY());
            if (newHeight > RESIZE_MARGIN) {
                if (newHeight >= stage.getMinHeight()) {
                    stage.setHeight(newHeight);
                    stage.setY(screenY);
                }
            }
        } 
        else if (stage.getScene().getCursor() == javafx.scene.Cursor.S_RESIZE) {
            double newHeight = y;
            if (newHeight > RESIZE_MARGIN) stage.setHeight(newHeight);
        }
    }

    /**
     * Checks if in resize zone
     * @param e
     * @param stage
     * @return
     */
    private boolean isInResizeZone(javafx.scene.input.MouseEvent e, Stage stage) {
        double x = e.getSceneX(), y = e.getSceneY();
        double width = stage.getWidth(), height = stage.getHeight();

        return (x < RESIZE_MARGIN || x > width - RESIZE_MARGIN ||
                y < RESIZE_MARGIN || y > height - RESIZE_MARGIN);
    }

    /**
     * Ease of access load fxml file
     * @param fxml
     * @return
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