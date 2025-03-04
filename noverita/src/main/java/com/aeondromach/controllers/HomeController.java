/**
 * @author Evelyn Engleman @Æondromach
 * @version 1
 * @since 12/11/2024
 * Main controller for Home page
 */

package com.aeondromach.controllers;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.aeondromach.system.CharView;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

public class HomeController {
    @FXML private VBox vBoxSquadList;
    @FXML private VBox vBoxCharHub;

    private NovController nov;
    private final ArrayList<CharView> CHARACTERS = new ArrayList<>();
    private final ArrayList<String> SQUADS = new ArrayList<>();

    @FXML protected void initialize() {
        Platform.runLater(() -> {
            setHubField();
        });
    }

    public void setHubField() {
        Stage stage = (Stage) vBoxSquadList.getScene().getWindow();
        birthCharacters();
        buildCharacters(stage);
    }

    public List<Path> findNCFFiles(Path startDir) {
        List<Path> ncfFiles = new ArrayList<>();
        
        try {
            Files.walkFileTree(startDir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (file.toString().toLowerCase().endsWith(".ncf")) {
                        ncfFiles.add(file);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
        }

        return ncfFiles;
    }

    private void birthCharacters() {
        // String filePath = "filePath";
        CHARACTERS.clear();

        // Path directory = Paths.get(filePath);

        CHARACTERS.add(new CharView("Aemilius Novaric", "Historian", "Tutorial Squad", 3, new Image(getClass().getResource("/com/aeondromach/images/noverita.png").toExternalForm()), "path/to/aemilius-novaric.ncf"));
        CHARACTERS.add(new CharView("Vera Brown", "Martyr", "Tutorial Squad", 1, "path/to/vera-brown.ncf"));
        CHARACTERS.add(new CharView("Cassius Nightingale", "Worker", "Tutorial Squad", 8, "path/to/cassius-nightingale.ncf"));

        CHARACTERS.add(new CharView("Evelyn", "Developer", "The Englemans", 9, new Image(getClass().getResource("/com/aeondromach/images/Aeondromach.png").toExternalForm()), "path/to/evelyn.ncf"));
        CHARACTERS.add(new CharView("Aiden", "Writer", "The Eaglesons", 2, "path/to/aiden.ncf"));
        CHARACTERS.add(new CharView("Adam", "Worker", "The Eagles", 1, "path/to/adam.ncf"));

        CHARACTERS.add(new CharView("George", "King", "The Welkins", 6, "path/to/george.ncf"));
        CHARACTERS.add(new CharView("Georgina", "The Welki", 5, "path/to/georgina.ncf"));
        CHARACTERS.add(new CharView("Jeannine", "Priestess", "The Welkins", 3, "path/to/jeannine.ncf"));

        CHARACTERS.add(new CharView("Bodger", "Blacksmith", "The Bodgers", 9, "path/to/bodger.ncf"));
        CHARACTERS.add(new CharView("Dark Bodger", "Bodger", "The Bodgers", 9, "path/to/rodger.ncf"));
        CHARACTERS.add(new CharView("Quare", "Child", "The Bodgers", 9, "path/to/quare.ncf"));
        CHARACTERS.add(new CharView("Pudgy", "Dead Body", "The Bodgers", 9, "path/to/pudgy.ncf"));
        CHARACTERS.add(new CharView("Lodger", "Mother", "The Bodgers", 9, "path/to/lodger.ncf"));
    }

    private void buildCharacters(Stage stage) {
        vBoxSquadList.getChildren().clear();
        vBoxCharHub.getChildren().clear();

        for (CharView character: CHARACTERS){
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
            icon.setStyle("-fx-fill: -tertiaryColor;");
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

            Line squadLine = new Line();
            squadLine.setStyle("-fx-stroke: -tertiaryColor; -fx-stroke-width: 1;");
            squadLine.startXProperty().bind(squadTitle.layoutBoundsProperty().map(bounds -> bounds.getWidth() + 20));
            squadLine.endXProperty().bind(squadTitleHold.widthProperty());
            squadLine.setStartY(0);
            squadLine.setEndY(0);
            
            squadTitleHold.getChildren().addAll(squadTitle, squadLine);

            FlowPane squadHold = new FlowPane();
            squadHold.setAlignment(Pos.CENTER_LEFT);
            squadHold.getStyleClass().add("squadHold");

            double marginX = 205, marginY = 185, marginInX = 35, marginInY = 15;

            for (int i = 0; i < CHARACTERS.size(); i++) {
                CharView character = CHARACTERS.get(i);
                if (character.getSquad().equals(squad)) {
                    StackPane charMargin = new StackPane();
                    charMargin.setAlignment(Pos.CENTER);
                    charMargin.setPrefHeight(marginY);
                    charMargin.setPrefWidth(marginX);
                    charMargin.setStyle("-fx-background-color: transparent;");

                    AnchorPane charHold =  new AnchorPane();
                    charHold.setPrefHeight(marginY - marginInY);
                    charHold.setPrefWidth(marginX - marginInX);
                    charHold.setMaxHeight(marginY - marginInY);
                    charHold.setMaxWidth(marginX - marginInX);
                    charHold.setMinHeight(marginY - marginInY);
                    charHold.setMinWidth(marginX - marginInX);
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
                    charHighlight.setLayoutY((marginY - marginInY) - 71);
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
                        nov.createCharacter(character.getFILEPATH());
                        nov.loadCharacter();
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

    private void onHoverEnter(Stage stage) {
        nov.setIsHover(true);
        stage.getScene().setCursor(Cursor.HAND);
    }

    private void onHoverExit() {
        nov.setIsHover(false);
    }

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

    @FXML protected void handleRefreshClick(MouseEvent event) {
        setHubField();
    }
}