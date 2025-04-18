/**
 * @author Evelyn Engleman @Aeondromach
 * @version 2
 * @since 12/11/2024
 * Main controller for Home page
 */

package com.aeondromach.controllers;

import java.util.ArrayList;
import java.util.Collections;

import com.aeondromach.Messages;
import com.aeondromach.Settings;
import com.aeondromach.system.CharView;

import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.TabPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
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
     * Births the characters by reading their files and creating the charview arraylist
     */
    private void birthCharacters() {
        // String filePath = "filePath";
        characters.clear();
        characters = nov.readCharViews();
    }

    /**
     * Builds the character boxes and side squad browser in the character hub
     * @param stage insert the main stage of the app
     */
    private void buildCharacters(Stage stage) {
        vBoxSquadList.getChildren().clear();
        vBoxCharHub.getChildren().clear();

        if (characters != null) {
            if (!SQUADS.isEmpty()) SQUADS.clear();

            for (CharView character: characters){
                if (!SQUADS.isEmpty()) {
                    if (!SQUADS.contains(character.getSquad())) {
                        SQUADS.add(character.getSquad());
                    }
                }
                else {
                    SQUADS.add(character.getSquad());
                }
            }

            Collections.sort(SQUADS);

            for (String squad: SQUADS) {
                // Start side pane squads
                HBox squadPane = new HBox();
                squadPane.setMaxWidth(345);
                squadPane.setMaxHeight(25);
                squadPane.setAlignment(Pos.CENTER_LEFT);
                if (!squad.equals(SQUADS.get(SQUADS.size() - 1))) {
                    squadPane.setStyle("-fx-padding: 0 0 5 15; -fx-spacing: 7;");
                }
                else { 
                    squadPane.setStyle("-fx-padding: 0 0 0 15; -fx-spacing: 7;");
                }
                squadPane.setPickOnBounds(true);
                
                SVGPath icon = new SVGPath();
                icon.setContent("M14 20l8 8 8-8z");
                icon.setStyle("-fx-fill: -primaryTextColor;");
                squadPane.getChildren().add(icon);

                Label title = new Label();
                title.setText(squad);
                title.setStyle("-fx-font-size: 19px; -fx-text-fill: -primaryTextColor;");
                squadPane.getChildren().add(title);

                icon.setOnMouseEntered(e -> {
                    onHoverEnter(stage);
                });

                title.setOnMouseEntered(e -> {
                    onHoverEnter(stage);
                });

                icon.setOnMouseExited(e -> {
                    onHoverExit();
                });


                title.setOnMouseExited(e -> {
                    onHoverExit();
                });

                vBoxSquadList.getChildren().add(squadPane);

                vBoxCharHub.minWidthProperty().bind(stage.widthProperty().subtract(vBoxSquadList.prefWidthProperty()).subtract(15));
                vBoxCharHub.maxWidthProperty().bind(stage.widthProperty().subtract(vBoxSquadList.prefWidthProperty()).subtract(15));

                // End side pane squads, Start main pane squads
                HBox squadTitleHold = new HBox();
                squadTitleHold.setAlignment(Pos.CENTER_LEFT);
                squadTitleHold.setStyle("-fx-background-color: transparent;");

                Label squadTitle = new Label();
                squadTitle.getStyleClass().add("squadTitle");
                squadTitle.setText(squad);

                squadTitle.setTextOverrun(OverrunStyle.CLIP);

                squadTitleHold.setPrefWidth(Region.USE_COMPUTED_SIZE);
                squadTitleHold.setMinWidth(Region.USE_PREF_SIZE);

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

                double marginX = 20, marginY = 10, size = (Integer) Settings.getSetting(Settings.DisplaySettings.CHAR_VIEW_SIZE);

                for (int i = 0; i < characters.size(); i++) {
                    CharView character = characters.get(i);
                    if (character.getSquad().equals(squad)) {
                        StackPane charMargin = new StackPane();
                        charMargin.setAlignment(Pos.CENTER);
                        charMargin.setPrefHeight(marginY + size);
                        charMargin.setPrefWidth(marginX + size);
                        charMargin.setStyle("-fx-background-color: transparent;");

                        AnchorPane charHold =  new AnchorPane();
                        charHold.setPrefHeight(size);
                        charHold.setPrefWidth(size);
                        charHold.setMaxHeight(size);
                        charHold.setMaxWidth(size);
                        charHold.setMinHeight(size);
                        charHold.setMinWidth(size);
                        charHold.getStyleClass().add("charHold");

                        ImageView charImage = new ImageView();
                        charImage.setImage(character.getImage());
                        charImage.setPreserveRatio(true);
                        charImage.setFitHeight(charHold.getPrefHeight() - 2);
                        charImage.setFitWidth(charHold.getPrefWidth() - 2);

                        // Get Image and ImageView separate size discrepencies
                        double imageWidth = character.getImage().getWidth();
                        double imageHeight = character.getImage().getHeight();
                        double viewWidth = charImage.getFitWidth();
                        double viewHeight = charImage.getFitHeight();

                        // Get Scales
                        double scaleX = viewWidth / imageWidth;
                        double scaleY = viewHeight / imageHeight;
                        double scale = Math.max(scaleX, scaleY); // Scale to cover fully

                        // Get Crop Size
                        double cropWidth = viewWidth / scale;
                        double cropHeight = viewHeight / scale;

                        // Set center
                        double xOffset = (imageWidth - cropWidth) / 2;
                        double yOffset = (imageHeight - cropHeight) / 2;

                        // Set viewport to crop the image
                        charImage.setViewport(new Rectangle2D(xOffset, yOffset, cropWidth, cropHeight));

                        charImage.setLayoutX(1);
                        charImage.setLayoutY(1);

                        VBox charHighlight = new VBox();
                        charHighlight.setAlignment(Pos.TOP_LEFT);
                        charHighlight.setPrefWidth(charHold.getPrefWidth() - 2);
                        charHighlight.setPrefHeight(70);
                        charHighlight.setLayoutX(1);
                        charHighlight.setLayoutY((size) - 71);
                        charHighlight.getStyleClass().add("charHighlight");

                        Label charTitle = new Label();
                        charTitle.getStyleClass().add("charTitle");
                        charTitle.setText(character.getName());

                        Label charDesc = new Label();
                        charDesc.getStyleClass().add("charDesc");
                        if (character.getArche() != null) {
                            charDesc.setText("Rank " + character.getRank() + " " + character.getArche());
                        }
                        else {
                            charDesc.setText("Rank " + character.getRank() + ", Clean Slate");
                        }

                        charHold.setOnMouseClicked(e -> {
                            if(e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == 2){
                                if((Boolean) Settings.getSetting(Settings.GeneralSettings.CHARLOADALERT)){
                                    Messages.yesNoAlert("Load Character - " + character.getFILEPATH(), "Do you really wish to load " + character.getName() + "?", character.getFILEPATH() + "\n" + character.getName() + " may possibly take a while to load, make sure that this is absolutely the character you wish to use.", character.getImage(), 
                                    () -> {
                                        Task<Void> loadCharacterTask = new Task<>() {
                                            @Override
                                            protected Void call() {
                                                nov.createCharacter(character.getFILEPATH(), character.getImage());
                                                return null;
                                            }
                                            
                                            @Override
                                            protected void succeeded() {
                                                selectCharacter(character);
                                            }
                                        };
                    
                                        new Thread(loadCharacterTask).start();
                                        }
                                    );
                                }
                                else {
                                    Task<Void> loadCharacterTask = new Task<>() {
                                        @Override
                                        protected Void call() {
                                            nov.createCharacter(character.getFILEPATH(), character.getImage());
                                            return null;
                                        }
                                        
                                        @Override
                                        protected void succeeded() {
                                            selectCharacter(character);
                                        }
                                    };
                
                                    new Thread(loadCharacterTask).start();
                                }
                            }
                        });

                        charHighlight.getChildren().addAll(charTitle, charDesc);
                        charHold.getChildren().addAll(charImage, charHighlight);
                        charMargin.getChildren().add(charHold);
                        squadHold.getChildren().add(charMargin);

                        squadHold.setStyle("-fx-padding: 2px 30px;");
                    }
                }

                vBoxCharHub.getChildren().addAll(squadTitleHold, squadHold);

                icon.setOnMouseClicked(e -> {
                    onHoverClick(icon, squadHold);
                });

                title.setOnMouseClicked(e -> {
                    onHoverClick(icon, squadHold);
                });
            }
        }
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
        RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), btRefresh);
        rotate.setByAngle(360);
        rotate.setCycleCount(1);
        rotate.setAutoReverse(false);

        rotate.playFromStart();

        setHubField();
    }
}