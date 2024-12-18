/**
 * @author Evelyn Engleman @Æondromach
 * @version 2
 * @since 12/11/2024
 * Main controller for Header and Titlebar
 */

package com.aeondromach;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HeaderController {
    @FXML private AnchorPane titleBar;
    @FXML private Button btClose;
    @FXML private Button btMax;
    @FXML private Button btMin;
    @FXML private Button btSet;
    @FXML private Button btWeb;

    // Header Buttons
    @FXML private Button btHome;
    @FXML private Button btChar;
    @FXML private Button btEquip;
    @FXML private Button btArche;
    @FXML private Button btView;

    private double mousePosX, mousePosY;
    private Boolean isDoubleClick = false;
    public static Boolean isMax = false;
    private Boolean isMaximizeCooldown = false;
    private Boolean isMaxDragging = false;

    private NovController nov;

    /**
     * Runs initial header set up
     */
    @FXML
    protected void initialize() {
    }

    /**
     * Takes instantiated Novcontroller and links self to it.
     * @param nov Sets instance of Novcontroller
     */
    public void init(NovController nov) {
        this.nov = nov;
    }

    /**
     * Handles the close button action
     * @param event
     */
    @FXML
    protected void handleCloseClick(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY)){
            Stage stage = (Stage) btClose.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Handles Header maximize/restore actions
     */
    private void maximizeApp() {
        if (isMax) {
            btMax.setText("⬜");
            btClose.setStyle("-fx-background-radius: 0 7 0 0");
            nov.setMaximum();
        }
        else {
            btMax.setText("🗗");
            btClose.setStyle("-fx-background-radius: 0 0 0 0");
            nov.setMaximum();
        }
    }

    /**
     * Handles the Maximize and Restore button actions
     * @param event
     */
    @FXML
    protected void handleMaximizeClick(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY)){
            maximizeApp();
        }
    }

    /**
     * Handles the minimize to tray action
     * @param event
     */
    @FXML
    protected void handleMinimizeClick(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY)){
            Stage stage = (Stage) btMin.getScene().getWindow();
            stage.setIconified(true);
        }
    }

    /**
     * Activates when title bar is clicked, copies mouse position
     * @param event
     */
    @FXML
    protected void handleTitleBarPress(MouseEvent event) {
        Scene scene = (Scene) titleBar.getScene();
        if(event.getButton().equals(MouseButton.PRIMARY) && scene.getCursor().equals(javafx.scene.Cursor.DEFAULT)){
            if(event.getClickCount() == 2){
                isDoubleClick = true;
                maximizeApp();
                isMaximizeCooldown = true;
                final Timeline setCooldownMax = new Timeline(
                    new KeyFrame(Duration.seconds(0.2), (ActionEvent actionEvent) -> {
                        isMaximizeCooldown = false;
                    }
                ));
                setCooldownMax.setCycleCount(1);
                setCooldownMax.play(); 
            }
            else {
                isDoubleClick = false;
                mousePosX = event.getX();
                mousePosY = event.getY();
            }
        }
    }

    /**
     * Handles when title bar is let go of
     * @param event
     */
    @FXML protected void handleTitleBarUnPress(MouseEvent event) {
        Stage stage = (Stage) titleBar.getScene().getWindow();
        isMaxDragging = false;
        if (stage.getY() >= -25 && stage.getY() < 10 && (stage.getX() + (stage.getWidth()/2)) > ((Screen.getPrimary().getVisualBounds().getWidth() / 8) * 3) && (stage.getX() + (stage.getWidth()/2)) < ((Screen.getPrimary().getVisualBounds().getWidth() / 8) * 5) && !isMax && !isMaximizeCooldown) {
            maximizeApp();
        }
    }

    /**
     * Handles dragging app
     * @param event
     */
    @FXML
    protected void handleTitleBarDrag(MouseEvent event) {
        if (isDoubleClick) {
            return;
        }

        Scene scene = (Scene) titleBar.getScene();
        if(event.getButton().equals(MouseButton.PRIMARY) && scene.getCursor().equals(javafx.scene.Cursor.DEFAULT)){
            Stage stage = (Stage) titleBar.getScene().getWindow();
            if (!isMaxDragging && !isMax) {
                stage.setX(event.getScreenX() - mousePosX);
                stage.setY(event.getScreenY() - mousePosY);
            }
            else if (!isMaxDragging && isMax) {
                isMaxDragging = true;
                mousePosX = nov.getOriginalSize().getWidth() / 2;
                nov.setStageX(event.getScreenX() - mousePosX);
                nov.setStageY(event.getScreenY() - mousePosY);
                Platform.runLater(() -> {
                    maximizeApp();
                });
            }
            else {
                Platform.runLater(() -> {
                    stage.setX(event.getScreenX() - mousePosX);
                    stage.setY(event.getScreenY() - mousePosY);
                });
            }
        }
    }

    /**
     * Handles entering settings click
     * @param event
     */
    @FXML
    protected void handleSettingsClick(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY)){
            System.out.println("Settings Button Clicked");
        }
    }

    /**
     * Handles the web button, redirects to Novera.com (Or Aeondromach.com)
     * @param event
     */
    @FXML
    protected void handleWebClick(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY)){
            try {
                URI uri = new URI("https://novera.aeondromach.com");
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(uri);
                }
                else {
                    System.out.println("error");
                }
            } 
            catch (IOException | URISyntaxException e) {
            }
        }
    }
    
    /**
     * Handles opening the home folder
     * @param event
     */
    @FXML
    protected void handleMenuOpenHomeFolder(ActionEvent event) {
        System.out.println("Open Home Folder");
    }

    /**
     * Handles saving the character
     * @param event
     */
    @FXML
    protected void handleMenuSaveCharacter(ActionEvent event) {
        System.out.println("Save NCF Character");
    }

    /**
     * Handles Saving a PDF of the character
     * @param event
     */
    @FXML
    protected void handleMenuSavePDF(ActionEvent event) {
        System.out.println("Save PDF Character");
    }

    /**
     * Handles opening the images folder
     * @param event
     */
    @FXML
    protected void handleMenuOpenImagesFolder(ActionEvent event) {
        System.out.println("Open Images Folder");
    }

    /**
     * Handles opening the pack log
     * @param event
     */
    @FXML
    protected void handleMenuPackLog(ActionEvent event) {
        System.out.println("Open Pack Log");
    }

    /**
     * Handles opening the character file location
     * @param event
     */
    @FXML
    protected void handleMenuCharFileLoc(ActionEvent event) {
        System.out.println("Open Character Location");
    }

    /**
     * Handles opening the character sheet
     * @param event
     */
    @FXML
    protected void handleMenuCharSheet(ActionEvent event) {
        System.out.println("Open Chararacter Sheet /ie Menu");
    }

    /**
     * Handles opening the combat calculator
     * @param event
     */
    @FXML
    protected void handleMenuCombatCalc(ActionEvent event) {
        System.out.println("Open Combat Calc /ie Menu");
    }

    /**
     * Handles opening the settings
     * @param event
     */
    @FXML
    protected void handleMenuSettings(ActionEvent event) {
        System.out.println("Open Settings /ie Menu");
    }

    /**
     * Handles the next suggested action
     * @param event
     */
    @FXML
    protected void handleMenuNextAction(ActionEvent event) {
        System.out.println("Next Action");
    }

    /**
     * handles toggling suggested action
     * @param event
     */
    @FXML
    protected void handleMenuSuggestedAction(ActionEvent event) {
        System.out.println("Suggested Action");
    }

    /**
     * Handles new content redirect
     * @param event
     */
    @FXML
    protected void handleMenuNewContent(ActionEvent event) {
        try {
            URI uri = new URI("https://novera.aeondromach.com/Noverita/help/new-content");
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(uri);
            }
            else {
                System.out.println("error");
            }
        } 
        catch (IOException | URISyntaxException e) {
        }
    }

    /**
     * Handles supported packs redirect
     * @param event
     */
    @FXML
    protected void handleMenuSupportedPacks(ActionEvent event) {
        try {
            URI uri = new URI("https://novera.aeondromach.com/Noverita/packs");
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(uri);
            }
            else {
                System.out.println("error");
            }
        } 
        catch (IOException | URISyntaxException e) {
        }
    }

    /**
     * Handles support page redirect
     * @param event
     */
    @FXML
    protected void handleMenuSupportPage(ActionEvent event) {
        try {
            URI uri = new URI("https://novera.aeondromach.com/Noverita/support");
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(uri);
            }
            else {
                System.out.println("error");
            }
        } 
        catch (IOException | URISyntaxException e) {
        }
    }

    /**
     * Handles road map redirect
     * @param event
     */
    @FXML
    protected void handleMenuRoadMap(ActionEvent event) {
        try {
            URI uri = new URI("https://novera.aeondromach.com/Noverita/roadmap");
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(uri);
            }
            else {
                System.out.println("error");
            }
        } 
        catch (IOException | URISyntaxException e) {
        }
    }

    /**
     * Handles when Home button is clicked
     * @param event
     */
    @FXML protected void handleHomeClick(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            setActiveTab("home", btHome);
        }
    }

    /**
     * Handles when Character button is clicked
     * @param event
     */
    @FXML protected void handleCharClick(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            setActiveTab("char", btChar);
        }
    }

    /**
     * Handles when Equipment button is clicked
     * @param event
     */
    @FXML protected void handleEquipClick(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            setActiveTab("equip", btEquip);
        }
    }

    /**
     * Handles when Archetype button is clicked
     * @param event
     */
    @FXML protected void handleArcheClick(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            setActiveTab("arche", btArche);
        }
    }

    /**
     * Handles when View button is clicked
     * @param event
     */
    @FXML protected void handleViewClick(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            setActiveTab("view", btView);
        }
    }

    /**
     * Sets active page
     * @param tab
     * @param button
     */
    private void setActiveTab(String tab, Button button) {
        btHome.setStyle("-fx-opacity: 0.8;");
        btChar.setStyle("-fx-opacity: 0.8;");
        btEquip.setStyle("-fx-opacity: 0.8;");
        btArche.setStyle("-fx-opacity: 0.8;");
        btView.setStyle("-fx-opacity: 0.8;");
        button.setStyle("-fx-opacity: 1;");
        nov.setTab(tab + "Root");
    }
}