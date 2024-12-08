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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(@SuppressWarnings("exports") Stage stage) throws IOException {
        // get and set scene
        scene = new Scene(loadFXML("NovFX"), 1150, 550);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);

        // Set task bar title
        stage.setTitle("Noverita");

        // get and set icon
        Image icon = new Image(getClass().getResourceAsStream("images/AeondromachBackground.png"));
        stage.getIcons().add(icon);

        // Set app background to transparent and remove windows decorations
        stage.initStyle(StageStyle.TRANSPARENT);

        stage.focusedProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if (newValue) {
                    scene.lookup("#noveritaText").setStyle("-fx-opacity: 1;");
                } else {
                    scene.lookup("#noveritaText").setStyle("-fx-opacity: 0.75;");
                }
            });            
        });

        stage.show(); // run
    }

    private static Parent loadFXML(String fxml) throws IOException { // Ease of access load fxml file
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) { // Launch main drive
        launch();
    }

}