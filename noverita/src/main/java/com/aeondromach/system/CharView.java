/**
 * @author Evelyn Engleman @Aeondromach
 * @version 1
 * @since 03/08/2025
 * The lighter, more display focused objects for characters
 */

package com.aeondromach.system;

import com.aeondromach.Settings;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class CharView {
    private String name;
    private String archetype;
    private int rank;
    private String squad;
    private Image image;
    private final String filepath;

    private final StackPane CHARACTER_BLOCK;
    private final AnchorPane CHAR_HOLD;
    private final ImageView CHAR_IMAGE;
    private final VBox CHAR_HIGHLIGHT;
    private final Label CHAR_TITLE;
    private final Label CHAR_DESC;

    private Squad owningSquad;

    /**
     * Constructor for CharView
     * @param name name of the character
     * @param archetype archetype of the character
     * @param squad squad of the character
     * @param rank rank of the character
     * @param image image of the character
     * @param filePath filepath of the character
     */
    public CharView(String name, String archetype, String squad, int rank, Image image, String filePath) {
        this.name = name;
        
        if (archetype != null) this.archetype = archetype;
        else this.archetype = "";

        this.squad = squad;
        this.rank = rank;
        
        if (image != null) this.image = image;
        else this.image = new Image(getClass().getResourceAsStream("/images/Test.png"));

        this.filepath = filePath;

        CHARACTER_BLOCK = new StackPane();
        CHAR_HOLD = new AnchorPane();
        CHAR_IMAGE = new ImageView(image);
        CHAR_HIGHLIGHT = new VBox();
        CHAR_TITLE = new Label(name);
        CHAR_DESC = new Label(archetype != null
                ? "Rank " + rank + " " + archetype
                : "Rank " + rank + ", Clean Slate");

        buildUI();
    }

    public void setNewSizes() {
        final double marginX = 20, marginY = 10;
        final double size = (Integer) Settings.getSetting(Settings.DisplaySettings.CHAR_VIEW_SIZE);

        CHARACTER_BLOCK.setPrefSize(marginX + size, marginY + size);

        CHAR_HOLD.setPrefSize(size, size);
        CHAR_HOLD.setMaxSize(size, size);
        CHAR_HOLD.setMinSize(size, size);

        CHAR_IMAGE.setFitHeight(size - 2);
        CHAR_IMAGE.setFitWidth(size - 2);

        CHAR_HIGHLIGHT.setPrefWidth(size - 2);
        CHAR_HIGHLIGHT.setLayoutY((size) - 71);
    }

    /**
     * Creates a character tile for the character hub
     */
    private void buildUI() {
        final double marginX = 20, marginY = 10;
        final double size = (Integer) Settings.getSetting(Settings.DisplaySettings.CHAR_VIEW_SIZE);
        
        CHARACTER_BLOCK.setAlignment(Pos.CENTER);
        CHARACTER_BLOCK.setPrefSize(marginX + size, marginY + size);
        CHARACTER_BLOCK.setStyle("-fx-background-color: transparent;");

        CHAR_HOLD.setPrefSize(size, size);
        CHAR_HOLD.setMaxSize(size, size);
        CHAR_HOLD.setMinSize(size, size);
        CHAR_HOLD.getStyleClass().add("charHold");

        CHAR_IMAGE.setPreserveRatio(true);
        CHAR_IMAGE.setFitHeight(size - 2);
        CHAR_IMAGE.setFitWidth(size - 2);
        CHAR_IMAGE.setViewport(getCenteredViewport(image, CHAR_IMAGE.getFitWidth(), CHAR_IMAGE.getFitHeight()));
        CHAR_IMAGE.setLayoutX(1);
        CHAR_IMAGE.setLayoutY(1);
        CHAR_IMAGE.setSmooth(true);
        CHAR_IMAGE.setCache(true);

        CHAR_HIGHLIGHT.setAlignment(Pos.TOP_LEFT);
        CHAR_HIGHLIGHT.setPrefWidth(size - 2);
        CHAR_HIGHLIGHT.setPrefHeight(70);
        CHAR_HIGHLIGHT.setLayoutX(1);
        CHAR_HIGHLIGHT.setLayoutY((size) - 71);
        CHAR_HIGHLIGHT.getStyleClass().add("charHighlight");

        CHAR_TITLE.getStyleClass().add("charTitle");

        CHAR_DESC.getStyleClass().add("charDesc");

        CHAR_HIGHLIGHT.getChildren().addAll(CHAR_TITLE, CHAR_DESC);
        CHAR_HOLD.getChildren().addAll(CHAR_IMAGE, CHAR_HIGHLIGHT);
        CHARACTER_BLOCK.getChildren().add(CHAR_HOLD);

        System.out.println("charHold=" + CHAR_HOLD.getPrefWidth() + "x" + CHAR_HOLD.getPrefHeight() + ", " + CHAR_HOLD.getMaxWidth() + "x" + CHAR_HOLD.getMaxHeight());
    }

    public void setCharHoldAction(Runnable action) {
        CHAR_HOLD.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
                action.run();
            }
        });
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

    public StackPane getCHARACTER_BLOCK() {
        return this.CHARACTER_BLOCK;
    }

    /**
     * Returns the name of the character
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the archetype of the character
     * @return archetype
     */
    public String getArche() {
        return this.archetype;
    }

    /**
     * Returns the squad of the character
     * @return squad
     */
    public String getSquad() {
        return this.squad;
    }

    /**
     * Returns the rank of the character
     * @return rank
     */
    public int getRank() {
        return this.rank;
    }

    /**
     * Returns the image of the character
     * @return image
     */
    public Image getImage() {
        return this.image;
    }

    /**
     * Returns the filepath of the character
     * @return filepath
     */
    public String getFILEPATH() {
        return this.filepath;
    }

    /**
     * Returns the filename of the character
     * @return filename
     */
    public String getFileName() {
        int lastSlashIndex = this.filepath.lastIndexOf("/") + 1;
        int extensionIndex = this.filepath.lastIndexOf(".ncf");

        String fileName = this.filepath.substring(lastSlashIndex, extensionIndex);
        fileName = fileName + ".ncf";
        return fileName;
    }

    /**
     * Sets the name of the character
     * @param setter name
     */
    public void setName(String setter) {
        this.name = setter;
    }

    public void SetOwningSquad(Squad setter) {
        this.owningSquad = setter;
    }

    public Squad GetOwningSquad() {
        return this.owningSquad;
    }
}