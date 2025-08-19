package com.aeondromach;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public abstract class Messages {
    public static void errorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR - " + title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        setAlert(alert);

        alert.showAndWait();
    }

    public static void debugAlertRemoveOnLaunch(String title, String header, String content) {
        errorAlert(title, header, content);
    }

    public static void errorAlert(String title, String header, String content, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR - " + title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String stackTrace = sw.toString();

        TextArea textArea = new TextArea(stackTrace);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);

        VBox vbox = new VBox(textArea);

        alert.getDialogPane().setExpandableContent(vbox);

        setAlert(alert);

        alert.showAndWait();
    }

    public static void warningAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("WARNING - " + title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        setAlert(alert);

        alert.showAndWait();
    }

    public static void yesNoAlert(String title, String header, String content, @SuppressWarnings("exports") Image image, Runnable yesAction, Runnable noAction) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        ButtonType yesButton = ButtonType.YES, noButton = ButtonType.NO;

        alert.getButtonTypes().addAll(yesButton, noButton);

        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(45);
        imageView.setFitWidth(45);

        alert.setGraphic(imageView);

        setAlert(alert);

        alert.showAndWait().ifPresent(response -> {
            if (response == yesButton && yesAction != null) {
                yesAction.run();
            } else if (response == noButton && noAction != null) {
                noAction.run();
            }
        });
    }

    public static void yesNoAlert(String title, String header, String content, @SuppressWarnings("exports") Image image, Runnable yesAction) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        ButtonType yesButton = ButtonType.YES, noButton = ButtonType.NO;

        alert.getButtonTypes().addAll(yesButton, noButton);

        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(45);
        imageView.setFitWidth(45);

        alert.setGraphic(imageView);

        setAlert(alert);

        alert.showAndWait().ifPresent(response -> {
            if (response == yesButton && yesAction != null) {
                yesAction.run();
            }
        });
    }

    private static void setAlert(Alert alert) {
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(App.class.getResourceAsStream("images/noveritaBackground.png")));

        alert.getDialogPane().setMinHeight(200);
        alert.getDialogPane().setMinWidth(600);

        alert.getDialogPane().getScene().getStylesheets().add(App.class.getResource("styles/alert.css").toString());
    }
}
