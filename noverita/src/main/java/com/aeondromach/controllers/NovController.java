/**
 * @author Evelyn Engleman @Aeondromach
 * @version 3
 * @since 12/07/2024
 * Main Controller for Noverita
 */

package com.aeondromach.controllers;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.aeondromach.App;
import com.aeondromach.Settings;
import static com.aeondromach.controllers.HeaderController.isMax;
import com.aeondromach.system.CharView;
import com.aeondromach.system.Character;
import com.aeondromach.system.parsers.XmlParser;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class NovController {
    @FXML private BorderPane novPane;
    @FXML private Pane tabView;

    @FXML private Pane homeRoot;
    @FXML private Pane charRoot;
    @FXML private Pane equipRoot;
    @FXML private Pane archeRoot;
    @FXML private Pane viewRoot;

    @FXML private HeaderController headerController;
    @FXML private FooterController footerController;
    @FXML private HomeController homeController;
    @FXML private CharacterController characterController;
    @FXML private EquipmentController equipmentController;
    @FXML private ArchetypeController archetypeController;
    @FXML private ViewController viewController;

    protected static final String NAME = getProperty("app.name");
    protected static final String VERSION = getProperty("app.version");
    public static Boolean isHover = false;
    private final ArrayList<String> LAST_ACTIONS = new ArrayList<>();
    private double stageX, stageY, stageW, stageH;
    private Character character;
    private final ArrayList<String> SQUADS = new ArrayList<>();
    private Scene scene;

    /**
     * Get config.properties items and return
     * @param key
     * @return
     */
    private static String getProperty(String key) {
        Properties properties = new Properties();
        try (InputStream input = NovController.class.getResourceAsStream("/config.properties")) {
            properties.load(input);
            return properties.getProperty(key);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Sets if the user is currently hovering in the app
     * @param setter set is hovering
     */
    public void setIsHover(Boolean setter) {
        isHover = setter;
    }

    /**
     * Initial set up
     */
    @FXML
    protected void initialize() {
        headerController.init(this);
        footerController.init(this);
        homeController.init(this);
        characterController.init(this);
        equipmentController.init(this);
        archetypeController.init(this);
        viewController.init(this);
        addAction("Entered Noverita");
        Platform.runLater(() -> {
            Stage stage = (Stage) novPane.getScene().getWindow();
            scene = (Scene) stage.getScene();

            stageX = stage.getX();
            stageY = stage.getY();
            stageW = stage.getWidth();
        });
    }

    /**
     * Reads the character files within the path, returning them in an Arraylist
     * @return an Arraylist of character views
     */
    public ArrayList<CharView> readCharViews() {
        Path path = Paths.get(String.valueOf(Settings.getSetting(Settings.CustomSettings.CHAR_PATH)));
        SQUADS.clear();
        ArrayList<CharView> charViews = new ArrayList<>();
        try (Stream<Path> stream = Files.walk(path)) {
            stream.filter(Files::isRegularFile)
                  .filter(file -> file.toString().endsWith(".ncf"))
                  .forEach(file -> ncfCharViewRead(Paths.get(file.toString()), charViews)); // Pass each file to parser
        } catch (IOException e) { 
            return null;
        }
        return charViews;
    }

    /**
     * Read all of the character files and set all of the information in the readCharViews() function
     * @param path The path of the file
     * @param charViews the arraylist to parse to
     */
    private void ncfCharViewRead(Path path, ArrayList<CharView> charViews) {
        if (Files.exists(path) && Files.isRegularFile(path)) {
            String nameTag;
            String archetypeTag;
            String squadTag;
            int rankTag;
            try {
                Document doc = Jsoup.parse(Files.readString(path), "UTF-8");

                Element charTag = doc.selectFirst("character");
                if (charTag == null) return;
                Element information = charTag.selectFirst("information");
                if (information == null) return;
                    Element name = information.selectFirst("name");
                    Element archetype = information.selectFirst("archetype");
                    Element squad = information.selectFirst("squad");
                    Element rank = information.selectFirst("rank");
                    Element portrait = information.selectFirst("charPortrait");

                    if (name == null) nameTag = "Enygma";
                    else nameTag = name.ownText();
                    if (archetype == null) archetypeTag = "Typical";
                    else archetypeTag = archetype.ownText();
                    if (squad == null || squad.ownText().isBlank()) squadTag = "Characters";
                    else squadTag = squad.ownText();
                    if (rank == null) rankTag = 1;
                    else rankTag = Integer.parseInt(rank.ownText());

                    charViews.add(new CharView(nameTag, archetypeTag, squadTag, rankTag, XmlParser.findImage(portrait), path.toString()));
            } 
            catch (IOException e) {
            }
        }
    }

    /**
     * Set the footer path with a new path
     * @param path character path
     */
    public void setFooterPath(String path) {
        footerController.setCharFilePath(path);
    }

    /**
     * Add an action to the bottom left action lister
     * @param text action
     */
    public void addAction(String text) {
        LAST_ACTIONS.add(text);
        footerController.setLastAction();
    }

    /**
     * Returns the last action commit
     * @return last action
     */
    public String getLastAction() {
        return LAST_ACTIONS.get(LAST_ACTIONS.size() - 1);
    }

    /**
     * Set the stageX
     * @param setter stageX
     */
    public void setStageX(double setter) {
        this.stageX = setter;
    }

    /**
     * Set the stageY
     * @param setter stageY
     */
    public void setStageY(double setter) {
        this.stageY = setter;
    }

    /**
     * Returns the original size of the app when restoring
     * @return original size Rectangle2d
     */
    public Rectangle2D getOriginalSize() {
        Rectangle2D rect = new Rectangle2D(1150, 550, stageW, stageH);
        return rect;
    }

    /**
     * Set the active tab of the main tabs
     * @param activeTab tab to activate
     */
    public void setTab(String activeTab) {
        for (Node pane: tabView.getChildren()) {
            if (pane.getId().equals(activeTab)) {
                pane.setVisible(true);
            }
            else {
                pane.setVisible(false);
            }
        }
    }

    /**
     * set the app to maximized
     */
    public void setMaximum() {
        Stage stage = (Stage) novPane.getScene().getWindow();
        Parent root = (Parent) novPane.getScene().getRoot();
        AnchorPane headerPane = (AnchorPane) novPane.lookup("#headsHeader");
        AnchorPane footerPane = (AnchorPane) novPane.lookup("#headsFooter");

        if (!isMax) {
            stageX = stage.getX();
            stageY = stage.getY();
            stageW = stage.getWidth();
            stageH = stage.getHeight();
            stage.setX(0);
            stage.setY(0);
            stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());

            Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
            java.awt.Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

            int taskBarHeight = scrnSize.height - winSize.height;
            
            stage.setMaximized(!isMax);
            stage.setHeight(Screen.getPrimary().getBounds().getHeight() - taskBarHeight);

            App.setTheme("-borderColor", "transparent");
            headerPane.setStyle("-fx-background-radius: 0 0 0 0;");
            footerPane.setStyle("-fx-background-radius: 0 0 0 0;");
        }
        else {
            stage.setX(stageX);
            if (stageY > 2) stage.setY(stageY);
            else stage.setY(2);
            stage.setWidth(stageW);
            stage.setHeight(stageH);

            App.setTheme("-borderColor", "rgba(100, 100, 100, 1)");
            headerPane.setStyle("-fx-background-radius: 8 8 0 0;");
            footerPane.setStyle("-fx-background-radius: 0 0 8 8;");
        }
        isMax = !isMax;
    }

    /**
     * Create a character with a given filepath
     * @param filePath the path to the ncf file
     */
    public void createCharacter(String filePath, Image image) {
        character = new Character(filePath, image);
    }

    /**
     * Clear the current character
     */
    public void clearCharacter() {
        character = null;
    }

    /**
     * Returns the currently used character
     * @return current character
     */
    public Character getCharacter() {
        return this.character;
    }

    /**
     * Loads the current character to all available UI elements
     */
    public void loadCharacter() {
        headerController.setChar();
        headerController.setHeadCharInfo();
        headerController.setHeadCharVisible(true);
        headerController.setHeaderButtonsDisabled(false);

        characterController.setCharTab();

        characterController.gettStr().setText(character.getFinalStat(0) + " (" + character.getModifierString(character.getFinalStat(0)) + ")");
        characterController.gettDex().setText(character.getFinalStat(1) + " (" + character.getModifierString(character.getFinalStat(1)) + ")");
        characterController.gettCon().setText(character.getFinalStat(2) + " (" + character.getModifierString(character.getFinalStat(2)) + ")");
        characterController.gettInt().setText(character.getFinalStat(3) + " (" + character.getModifierString(character.getFinalStat(3)) + ")");
        characterController.gettWis().setText(character.getFinalStat(4) + " (" + character.getModifierString(character.getFinalStat(4)) + ")");
        characterController.gettCha().setText(character.getFinalStat(5) + " (" + character.getModifierString(character.getFinalStat(5)) + ")");

        characterController.getTfStr().setPromptText(String.valueOf(character.getBaseStat(0)));
        characterController.getTfDex().setPromptText(String.valueOf(character.getBaseStat(1)));
        characterController.getTfCon().setPromptText(String.valueOf(character.getBaseStat(2)));
        characterController.getTfInt().setPromptText(String.valueOf(character.getBaseStat(3)));
        characterController.getTfWis().setPromptText(String.valueOf(character.getBaseStat(4)));
        characterController.getTfCha().setPromptText(String.valueOf(character.getBaseStat(5)));

        character.setStatPoints();
        characterController.getPointText().setText(character.getStatPoints() + "/27");

        if (character.hasSpecies()) {
            
        }
    }
}