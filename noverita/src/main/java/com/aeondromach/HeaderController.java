/**
 * @author Evelyn Engleman @Æondromach
 * Controller for the header and titlebar
 */

package com.aeondromach;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HeaderController {
    @FXML private AnchorPane titleBar;
    @FXML private Button btClose;
    @FXML private Button btMax;
    @FXML private Button btMin;
    @FXML private Button btSet;
    @FXML private Button btWeb;
    @FXML private Text titleText;

    private double mousePosX, mousePosY;
    private Boolean isDoubleClick = false;

    private NovController nov;

    @FXML
    protected void initialize() {
    }

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
     * Handles the Maximize and Restore button actions
     * @param event
     */
    @FXML
    protected void handleMaximizeClick(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY)){
            Stage stage = (Stage) btMax.getScene().getWindow();
            if (stage.isMaximized()) {
                btMax.setText("⬜");
                stage.setMaximized(false);
            }
            else {
                btMax.setText("🗗");
                stage.setMaximized(true);
            }
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
        Stage stage = (Stage) scene.getWindow();
        if(event.getButton().equals(MouseButton.PRIMARY) && scene.getCursor().equals(javafx.scene.Cursor.DEFAULT)){
            if(event.getClickCount() == 2){
                isDoubleClick = true;
                handleMaximizeClick(event);
            }
            else {
                isDoubleClick = false;
                mousePosX = event.getX();
                mousePosY = event.getY();
            }
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
            stage.setX(event.getScreenX() - mousePosX);
            stage.setY(event.getScreenY() - mousePosY);
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
}