/**
 * @author Evelyn Engleman @Aeondromach
 * @version 2
 * @since 12/11/2024
 * Main controller for Home page
 */

package com.aeondromach.controllers;

import java.util.Comparator;
import java.util.LinkedList;

import com.aeondromach.Messages;
import com.aeondromach.system.CharView;
import com.aeondromach.system.Squad;

import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HomeController {
    @FXML private VBox vBoxSquadList;
    @FXML private VBox vBoxCharHub;
    @FXML private TabPane home;

    @FXML private Button btRefresh;

    private NovController nov;
    protected LinkedList<Squad> squadList = new LinkedList<>();

    /**
     * Runs the initial set up on app start-up for the Home page
     */
    @FXML protected void initialize() {
        Platform.runLater(() -> {
            freshLoad();
            Stage stage = (Stage) home.getScene().getWindow();
            home.prefWidthProperty().bind(stage.widthProperty().subtract(.001));
        });
    }

    protected void addCharacter(CharView character) {
        if (character == null) return;
        if (squadList.isEmpty())  {
            squadList.add(new Squad(character.getSquad(), character, vBoxCharHub, vBoxSquadList, nov));
            return;
        }
        for (Squad squad: squadList) {
            if (squad.getNAME().equals(character.getSquad())) {
                squad.addCharacter(character);
                return;
            }
        }
        squadList.add(new Squad(character.getSquad(), character, vBoxCharHub, vBoxSquadList, nov));

        squadList.sort(
            Comparator.comparing(
                Squad::getNAME,
                Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)
            )
        );
    }

    private HBox createSquadPane(Stage stage, String iconPath, String squad, boolean hasPaddingBelow) {
        HBox squadPane = new HBox();
        squadPane.setMaxWidth(345);
        squadPane.setMaxHeight(25);
        squadPane.setAlignment(Pos.CENTER_LEFT);
        squadPane.setStyle(hasPaddingBelow
                ? "-fx-padding: 0 0 5 15; -fx-spacing: 7;"
                : "-fx-padding: 0 0 0 15; -fx-spacing: 7;");
        squadPane.setPickOnBounds(true);

        SVGPath icon = new SVGPath();
        icon.setContent(iconPath);
        icon.setStyle("-fx-fill: -primaryTextColor;");

        Label title = new Label(squad);
        title.setStyle("-fx-font-size: 19px; -fx-text-fill: -primaryTextColor;");

        EventHandler<MouseEvent> enterHandler = e -> onHoverEnter(stage);
        EventHandler<MouseEvent> exitHandler = e -> onHoverExit();
        icon.setOnMouseEntered(enterHandler);
        title.setOnMouseEntered(enterHandler);
        icon.setOnMouseExited(exitHandler);
        title.setOnMouseExited(exitHandler);

        
        squadPane.getChildren().addAll(icon, title);
        return squadPane;
    }

    /**
     * The code for checking on hover enter in home screen, for the squad and character buttons
     * @param stage insert the main stage
     */
    private void onHoverEnter(Stage stage) {
        nov.setIsHover(true);
        stage.getScene().setCursor(Cursor.HAND);
    }

    /**
     * Remove hover effect on exit
     */
    private void onHoverExit() {
        nov.setIsHover(false);
    }

    /**
     * The function for the squad side pane view toggles
     * @param icon the arrow svg
     * @param squadHold the pane holding the character boxes
     */
    private void onHoverClick(SVGPath icon, FlowPane squadHold) {
        if (icon.getRotate() == -90) {
            icon.setRotate(0);

            squadHold.setVisible(true);
            squadHold.setManaged(true);
        }
        else {
            icon.setRotate(-90);

            squadHold.setVisible(false);
            squadHold.setManaged(false);
        }
    }

    public void placeCharacters() {
        vBoxCharHub.getChildren().clear();
        vBoxSquadList.getChildren().clear();
        for (Squad squad: squadList) {
            for (CharView charView: squad.getCHAR_VIEWS().values()) {
                charView.setNewSizes();
            }
        }

        checkSquadUIs();
        for (Squad squad: squadList) {
            vBoxCharHub.getChildren().add(squad.getVBOX_SQUAD());

            final String arrowIcon = "M14 20l8 8 8-8z";

            Stage stage  = (Stage) home.getScene().getWindow();

            // SIDE PANE ENTRY
            HBox squadPane = createSquadPane(stage, arrowIcon, squad.getNAME(), squad.isS());
            vBoxSquadList.getChildren().add(squadPane);

            // Attach toggle click events (expand/collapse squad)
            for (Node clickable : squadPane.getChildren()) {
                clickable.setOnMouseClicked(e -> onHoverClick((SVGPath) squadPane.getChildren().get(0), squad.getSQUAD_HOLD()));
            }
        }
    }

    public void checkSquadUIs() {
        for (Squad squad: squadList) 
            squad.setS(false);
        squadList.getLast().setS(true);
    }

    public void freshLoad() {
        birthCharacters();
        placeCharacters();
    }

    // public void placeExistingCharacter(CharView character) {
    //     for (HBox squad: vBoxCharHub.getChildren()) {
    //         if (squad.equals(character.GetOwningSquad().getHBOX_SQUAD_LIST())) {
    //             squad = 
    //             return;
    //         }
    //     }
    // }

    /**
     * Births the characters by reading their files and creating the charview arraylist
     */
    private void birthCharacters() {
        squadList.clear();
        nov.readCharViews();
    }

    /**
     * Get the character hub main vBox
     * @return character hub vbox
     */
    protected VBox getCharHub() {
        return this.vBoxCharHub;
    }

    /**
     * Takes instantiated Novcontroller and links self to it.
     * @param nov Sets instance of Novcontroller
     */
    public void init(NovController nov) {
        this.nov = nov;
    }

    /**
     * Handles the refresh button and reset the hub field
     * @param event handles mouse input
     */
    @FXML protected void handleRefreshClick(MouseEvent event) {
        btRefresh.setDisable(true);
        RotateTransition rotate = new RotateTransition(Duration.seconds(.5), btRefresh);
        rotate.setByAngle(360);
        rotate.setCycleCount(1);
        rotate.setAutoReverse(false);
        rotate.play();

        Task<Void> loadTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                return null;
            }
        };

        loadTask.setOnSucceeded(ev -> {
            freshLoad();
            btRefresh.setDisable(false);
        });

        loadTask.setOnFailed(ev -> {
            Throwable ex = loadTask.getException();
            Messages.errorAlert("Failed to Reload!", "Failed to Reload!", "Failed to reload characters in hub. Please try again.", ex);
        });

        new Thread(loadTask, "LoadCharactersThread").start();
    }
}