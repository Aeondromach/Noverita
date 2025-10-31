/**
 * @author Evelyn Engleman @Aeondromach
 * @version 2
 * @since 12/11/2024
 * Main controller for Home page
 */

package com.aeondromach.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.aeondromach.Messages;
import com.aeondromach.Settings;
import com.aeondromach.system.CharView;

import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HomeController {
    @FXML private VBox vBoxSquadList;
    @FXML private VBox vBoxCharHub;
    @FXML private TabPane home;

    @FXML private Button btRefresh;

    private NovController nov;
    private ArrayList<CharView> characters = new ArrayList<>();
    private final ArrayList<String> SQUADS = new ArrayList<>();

    /**
     * Runs the initial set up on app start-up for the Home page
     */
    @FXML protected void initialize() {
        Platform.runLater(() -> {
            setHubField();
            Stage stage = (Stage) home.getScene().getWindow();
            home.prefWidthProperty().bind(stage.widthProperty().subtract(.001));
        });
    }

    /**
     * Sets the Character Hub field on the Home subTab
     */
    public void setHubField() {
        Stage stage = (Stage) vBoxSquadList.getScene().getWindow();
        birthCharacters();
        buildCharacters(stage);
    }

    /**
     * Sets the Character Hub field on the Home subTab
     */
    public void buildHubField() {
        Stage stage = (Stage) vBoxSquadList.getScene().getWindow();
        buildCharacters(stage);
    }

    /**
     * Births the characters by reading their files and creating the charview arraylist
     */
    private void birthCharacters() {
        // String filePath = "filePath";
        if (characters != null) characters.clear();
        characters = nov.readCharViews();
    }

    /**
     * Builds the character boxes and side squad browser in the character hub
     * @param stage insert the main stage of the app
     */
    private void buildCharacters(Stage stage) {
        vBoxSquadList.getChildren().clear();
        vBoxCharHub.getChildren().clear();
        SQUADS.clear();

        if (characters == null || characters.isEmpty()) return;

        Map<String, List<CharView>> squadMap = new LinkedHashMap<>();

        for (CharView character : characters) {
            squadMap.computeIfAbsent(character.getSquad(), k -> new ArrayList<>()).add(character);
        }

        List<String> sortedSquads = new ArrayList<>(squadMap.keySet());
        Collections.sort(sortedSquads);

        vBoxCharHub.minWidthProperty().bind(stage.widthProperty().subtract(vBoxSquadList.prefWidthProperty()).subtract(15));
        vBoxCharHub.maxWidthProperty().bind(stage.widthProperty().subtract(vBoxSquadList.prefWidthProperty()).subtract(15));

        final String arrowIcon = "M14 20l8 8 8-8z";
        final double marginX = 20, marginY = 10;
        final double size = (Integer) Settings.getSetting(Settings.DisplaySettings.CHAR_VIEW_SIZE);

        for (int s = 0; s < sortedSquads.size(); s++) {
            String squad = sortedSquads.get(s);
            SQUADS.add(squad);

            // SIDE PANE ENTRY
            HBox squadPane = createSquadPane(stage, arrowIcon, squad, s < sortedSquads.size() - 1);
            vBoxSquadList.getChildren().add(squadPane);

            // MAIN PANE CONTENT
            HBox squadTitleHold = new HBox();
            squadTitleHold.setAlignment(Pos.CENTER_LEFT);
            squadTitleHold.setStyle("-fx-background-color: transparent;");

            Label squadTitle = new Label(squad);
            squadTitle.getStyleClass().add("squadTitle");
            squadTitle.setTextOverrun(OverrunStyle.CLIP);
            squadTitle.setMaxWidth(Double.MAX_VALUE);

            Line squadLine = new Line();
            squadLine.setStyle("-fx-stroke: -brightSecondary; -fx-stroke-width: 1; -fx-opacity: 0.8;");
            squadLine.startXProperty().bind(squadTitle.layoutBoundsProperty().map(bounds -> bounds.getWidth() + 20));
            squadLine.endXProperty().bind(squadTitleHold.widthProperty());
            squadLine.setStartY(0);
            squadLine.setEndY(0);

            squadTitleHold.getChildren().addAll(squadTitle, squadLine);

            FlowPane squadHold = new FlowPane();
            squadHold.setAlignment(Pos.CENTER_LEFT);
            squadHold.getStyleClass().add("squadHold");
            squadHold.setStyle("-fx-padding: 2px 30px;");

            for (CharView character : squadMap.get(squad)) {
                squadHold.getChildren().add(createCharacterTile(character, size, marginX, marginY));
            }

            vBoxCharHub.getChildren().addAll(squadTitleHold, squadHold);

            // Attach toggle click events (expand/collapse squad)
            for (Node clickable : squadPane.getChildren()) {
                clickable.setOnMouseClicked(e -> onHoverClick((SVGPath) squadPane.getChildren().get(0), squadHold));
            }
        }
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
     * Creates a character tile for the character hub
     * @param character the character info
     * @param marginX 
     * @param marginY 
     * @param size 
     * @return the character tile stackpane
     */
    private StackPane createCharacterTile(CharView character, double size, double marginX, double marginY) {
        StackPane charMargin = new StackPane();
        charMargin.setAlignment(Pos.CENTER);
        charMargin.setPrefSize(marginX + size, marginY + size);
        charMargin.setStyle("-fx-background-color: transparent;");

        AnchorPane charHold = new AnchorPane();
        charHold.setPrefSize(size, size);
        charHold.setMaxSize(size, size);
        charHold.setMinSize(size, size);
        charHold.getStyleClass().add("charHold");

        ImageView charImage = new ImageView(character.getImage());
        charImage.setPreserveRatio(true);
        charImage.setFitHeight(size - 2);
        charImage.setFitWidth(size - 2);
        charImage.setViewport(getCenteredViewport(character.getImage(), charImage.getFitWidth(), charImage.getFitHeight()));
        charImage.setLayoutX(1);
        charImage.setLayoutY(1);
        charImage.setSmooth(true);
        charImage.setCache(true);

        VBox charHighlight = new VBox();
        charHighlight.setAlignment(Pos.TOP_LEFT);
        charHighlight.setPrefWidth(size - 2);
        charHighlight.setPrefHeight(70);
        charHighlight.setLayoutX(1);
        charHighlight.setLayoutY((size) - 71);
        charHighlight.getStyleClass().add("charHighlight");

        Label charTitle = new Label(character.getName());
        charTitle.getStyleClass().add("charTitle");

        Label charDesc = new Label(character.getArche() != null
                ? "Rank " + character.getRank() + " " + character.getArche()
                : "Rank " + character.getRank() + ", Clean Slate");
        charDesc.getStyleClass().add("charDesc");

        charHold.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
                Runnable loadAction = () -> {
                    Task<Void> loadCharacterTask = new Task<>() {
                        @Override protected Void call() {
                            nov.createCharacter(character.getFILEPATH(), character.getImage());
                            return null;
                        }
                        @Override protected void succeeded() {
                            selectCharacter(character);
                        }
                    };
                    new Thread(loadCharacterTask).start();
                };

                if ((Boolean) Settings.getSetting(Settings.GeneralSettings.CHARLOADALERT)) {
                    Messages.yesNoAlert(
                            "Load Character - " + character.getFILEPATH(),
                            "Do you really wish to load " + character.getName() + "?",
                            character.getFILEPATH() + "\n" + character.getName() +
                                    " may take a while to load. Confirm before proceeding.",
                            character.getImage(),
                            loadAction
                    );
                } else {
                    loadAction.run();
                }
            }
        });

        charHighlight.getChildren().addAll(charTitle, charDesc);
        charHold.getChildren().addAll(charImage, charHighlight);
        charMargin.getChildren().add(charHold);

        System.out.println("charHold=" + charHold.getPrefWidth() + "x" + charHold.getPrefHeight() + ", " + charHold.getMaxWidth() + "x" + charHold.getMaxHeight());
        return charMargin;
    }

    private Rectangle2D getCenteredViewport(Image img, double viewWidth, double viewHeight) {
        double imageWidth = img.getWidth();
        double imageHeight = img.getHeight();

        double scaleX = viewWidth / imageWidth;
        double scaleY = viewHeight / imageHeight;
        double scale = Math.max(scaleX, scaleY);

        double cropWidth = viewWidth / scale;
        double cropHeight = viewHeight / scale;

        double xOffset = (imageWidth - cropWidth) / 2;
        double yOffset = (imageHeight - cropHeight) / 2;

        return new Rectangle2D(xOffset, yOffset, cropWidth, cropHeight);
    }

    private void selectCharacter(CharView character) {
        nov.addAction("Loaded " + character.getName());
        nov.setFooterPath(character.getFILEPATH());
        nov.loadCharacter();
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
                birthCharacters();
                return null;
            }
        };

        loadTask.setOnSucceeded(ev -> {
            buildHubField();
            btRefresh.setDisable(false);
        });

        loadTask.setOnFailed(ev -> {
            Throwable ex = loadTask.getException();
            ex.printStackTrace();
        });

        new Thread(loadTask, "LoadCharactersThread").start();
    }
}