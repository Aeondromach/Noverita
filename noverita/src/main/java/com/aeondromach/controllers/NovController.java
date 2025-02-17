/**
 * @author Evelyn Engleman @Æondromach
 * @version 2
 * @since 12/07/2024
 * Main Controller for Noverita
 */

package com.aeondromach.controllers;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import static com.aeondromach.controllers.HeaderController.isMax;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
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
            stageX = stage.getX();
            stageY = stage.getY();
            stageW = stage.getWidth();
            stageH = stage.getHeight();
        });
    }

    public void addAction(String text) {
        LAST_ACTIONS.add(text);
        footerController.setLastAction();
    }

    public String getLastAction() {
        return LAST_ACTIONS.get(LAST_ACTIONS.size() - 1);
    }

    public void setStageX(double setter) {
        this.stageX = setter;
    }

    public void setStageY(double setter) {
        this.stageY = setter;
    }

    @SuppressWarnings("exports")
    public Rectangle2D getOriginalSize() {
        Rectangle2D rect = new Rectangle2D(1150, 550, stageW, stageH);
        return rect;
    }

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
            
            stage.setHeight(Screen.getPrimary().getBounds().getHeight() - taskBarHeight);

            root.setStyle("-borderColor: transparent");
            headerPane.setStyle("-fx-background-radius: 0 0 0 0;");
            footerPane.setStyle("-fx-background-radius: 0 0 0 0;");
        }
        else {
            stage.setX(stageX);
            stage.setY(stageY);
            stage.setWidth(stageW);
            stage.setHeight(stageH);

            root.setStyle("-borderColor: rgba(100, 100, 100, 0.75);");
            headerPane.setStyle("-fx-background-radius: 8 8 0 0;");
            footerPane.setStyle("-fx-background-radius: 0 0 8 8;");
        }
        Platform.runLater(() -> {
            isMax = !isMax;
            stage.setWidth(stage.getWidth() + 0.00000000001);
            stage.setWidth(stage.getWidth() - 0.00000000001);
            stage.setHeight(stage.getHeight() + 0.00000000001);
            stage.setHeight(stage.getHeight() - 0.00000000001);
        });
    }
}