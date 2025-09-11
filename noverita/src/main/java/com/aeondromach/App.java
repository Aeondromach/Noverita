/**
 * @author Evelyn Engleman @Æondromach
 * @version 3
 * @since 12/07/2024
 * App driver for Noverita
 */

package com.aeondromach;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import static com.aeondromach.controllers.HeaderController.isMax;
import static com.aeondromach.controllers.NovController.isHover;
import com.aeondromach.system.IdClassList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static final int RESIZE_MARGIN = 5;
    private BorderPane mainPane;
    private double mainHeight, mainWidth;
    private static Parent root;
    private static final String[] styles = new String[1];

    /**
     * Initial set-up of Noverita
     */
    @Override
    public void start(@SuppressWarnings("exports") Stage stage) throws IOException {
        Folderer.checkSettings();

        // Set task bar title
        stage.setTitle("Noverita");

        // get and set icon
        Image icon = new Image(App.class.getResourceAsStream("images/noveritaBackground.png"));
        stage.getIcons().add(icon);

        // Set app background to transparent and remove windows decorations
        stage.initStyle(StageStyle.TRANSPARENT);

        mainWidth = 1160.0;
        mainHeight = 550.0;
        
        // get and set scene
        root = loadFXML("NovFX");
        scene = new Scene(root, mainWidth, mainHeight);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);

        IdClassList.setIds(Paths.get(String.valueOf(Settings.getSetting(Settings.CustomSettings.CUSTOM_PATH))));

        stage.setMinHeight(mainHeight);
        stage.setMinWidth(mainWidth);

        mainPane = (BorderPane) scene.lookup("#mainAppPane");

        addResizeHandlers(stage, scene);

        TabPane homePane = (TabPane) mainPane.lookup("#homeBod");
        TabPane charPane = (TabPane) mainPane.lookup("#charBod");
        TabPane equipPane = (TabPane) mainPane.lookup("#equipBod");
        TabPane archePane = (TabPane) mainPane.lookup("#archeBod");
        TabPane viewPane = (TabPane) mainPane.lookup("#viewBod");

        homePane.prefWidthProperty().bind(stage.widthProperty());
        charPane.prefWidthProperty().bind(stage.widthProperty());
        equipPane.prefWidthProperty().bind(stage.widthProperty());
        archePane.prefWidthProperty().bind(stage.widthProperty());
        viewPane.prefWidthProperty().bind(stage.widthProperty());

        homePane.prefHeightProperty().bind(stage.heightProperty().subtract(120.0));
        charPane.prefHeightProperty().bind(stage.heightProperty().subtract(120.0));
        equipPane.prefHeightProperty().bind(stage.heightProperty().subtract(120.0));
        archePane.prefHeightProperty().bind(stage.heightProperty().subtract(120.0));
        viewPane.prefHeightProperty().bind(stage.heightProperty().subtract(120.0));

        stage.focusedProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if (newValue) {
                    scene.lookup("#noveritaText").setStyle("-fx-opacity: 1;");
                    if (!isMax) { setTheme("-borderColor", "rgba(100, 100, 100, 1);"); }
                } else {
                    scene.lookup("#noveritaText").setStyle("-fx-opacity: 0.75;");
                    if (!isMax) { setTheme("-borderColor", "rgba(75, 75, 75, 1);"); }
                }
            });            
        });

        setTheme();

        stage.show(); // run
    }

    /**
     * Adds resize handles to all edges/corners
     * @param stage
     * @param scene
     */
    private void addResizeHandlers(Stage stage, Scene scene) {
        scene.addEventFilter(MouseEvent.MOUSE_MOVED, e -> updateCursor(e, stage));
        scene.addEventFilter(MouseEvent.MOUSE_DRAGGED, e -> resize(stage, e));
    }

    /**
     * Updates cursor type based on handler
     * @param e
     * @param stage
     */
    private void updateCursor(javafx.scene.input.MouseEvent e, Stage stage) {
        if (!isHover) {
            if (!isMax) {
                double x = e.getSceneX(), y = e.getSceneY();

                if (x < RESIZE_MARGIN) {
                    stage.getScene().setCursor(javafx.scene.Cursor.W_RESIZE);
                    if (y < RESIZE_MARGIN) {
                        stage.getScene().setCursor(javafx.scene.Cursor.NW_RESIZE);
                    }
                    else if (y > mainPane.getHeight() - RESIZE_MARGIN) {
                        stage.getScene().setCursor(javafx.scene.Cursor.SW_RESIZE);
                    }
                } 
                else if (x > mainPane.getWidth() - RESIZE_MARGIN) {
                    stage.getScene().setCursor(javafx.scene.Cursor.E_RESIZE);
                    if (y < RESIZE_MARGIN) {
                        stage.getScene().setCursor(javafx.scene.Cursor.NE_RESIZE);
                    }
                    else if (y > mainPane.getHeight() - RESIZE_MARGIN) {
                        stage.getScene().setCursor(javafx.scene.Cursor.SE_RESIZE);
                    }
                }
                else if (y < RESIZE_MARGIN) {
                    stage.getScene().setCursor(javafx.scene.Cursor.N_RESIZE);
                } 
                else if (y > mainPane.getHeight() - RESIZE_MARGIN) {
                    stage.getScene().setCursor(javafx.scene.Cursor.S_RESIZE);
                } 
                else {
                    stage.getScene().setCursor(javafx.scene.Cursor.DEFAULT);
                }
            }
            else {
                stage.getScene().setCursor(javafx.scene.Cursor.DEFAULT);
            }
        }
    }

    /**
     * Resizes stage
     * @param stage
     * @param e
     */
    private void resize(Stage stage, javafx.scene.input.MouseEvent e) {
        double x = e.getSceneX(), y = e.getSceneY();
        double screenX = e.getScreenX(), screenY = e.getScreenY();

        if (stage.getScene().getCursor() == javafx.scene.Cursor.NW_RESIZE) {
            double width = stage.getWidth() - (screenX - stage.getX());
            double height = stage.getHeight() - (screenY - stage.getY());
            if (width > RESIZE_MARGIN) {
                if (width >= stage.getMinWidth()) {
                    stage.setWidth(width);
                    stage.setX(screenX);
                }
                else {
                    stage.setWidth(stage.getMinWidth());
                }
            }
            if (height > RESIZE_MARGIN) {
                if (height >= stage.getMinHeight()) {
                    stage.setHeight(height);
                    stage.setY(screenY);
                }
                else {
                    stage.setHeight(stage.getMinHeight());
                }
            }
        } 
        else if (stage.getScene().getCursor() == javafx.scene.Cursor.NE_RESIZE) {
            double width = x;
            double height = stage.getHeight() - (screenY - stage.getY());
            if (width >= stage.getMinWidth()) {
                stage.setWidth(width);
            }
            else {
                stage.setWidth(stage.getMinWidth());
            }

            if (height > RESIZE_MARGIN) {
                if (height >= stage.getMinHeight()) {
                    stage.setHeight(height);
                    stage.setY(screenY);
                }
                else {
                    stage.setHeight(stage.getMinHeight());
                }
            }
        } 
        else if (stage.getScene().getCursor() == javafx.scene.Cursor.SW_RESIZE) {
            double width = stage.getWidth() - (screenX - stage.getX());
            double height = y;
            if (width > RESIZE_MARGIN) {
                if (width >= stage.getMinWidth()) {
                    stage.setWidth(width);
                    stage.setX(screenX);
                }
                else {
                    stage.setWidth(stage.getMinWidth());
                }
            }
            if (height > RESIZE_MARGIN) {
                if (height >= stage.getMinHeight()) {
                    stage.setHeight(height);
                }
                else {
                    stage.setHeight(stage.getMinHeight());
                }
            }
        } 
        else if (stage.getScene().getCursor() == javafx.scene.Cursor.SE_RESIZE) {
            double width = x;
            double height = y;
            if (width >= stage.getMinWidth()) {
                stage.setWidth(width);
            }
            else {
                stage.setWidth(stage.getMinWidth());
            }

            if (height >= stage.getMinHeight()) {
                stage.setHeight(height);
            }
            else {
                stage.setHeight(stage.getMinHeight());
            }
        } 
        else if (stage.getScene().getCursor() == javafx.scene.Cursor.W_RESIZE) {
            double width = stage.getWidth() - (screenX - stage.getX());
            if (width > RESIZE_MARGIN) {
                if (width >= stage.getMinWidth()) {
                    stage.setWidth(width);
                    stage.setX(screenX);
                }
                else {
                    stage.setWidth(stage.getMinWidth());
                }
            }
        } 
        else if (stage.getScene().getCursor() == javafx.scene.Cursor.E_RESIZE) {
            double width = x;
            if (width > RESIZE_MARGIN) {
                if (width >= stage.getMinWidth()) {
                    stage.setWidth(width);
                }
                else {
                    stage.setWidth(stage.getMinWidth());
                }
            }
        } 
        else if (stage.getScene().getCursor() == javafx.scene.Cursor.N_RESIZE) {
            double height = stage.getHeight() - (screenY - stage.getY());
            if (height > RESIZE_MARGIN) {
                if (height >= stage.getMinHeight()) {
                    stage.setHeight(height);
                    stage.setY(screenY);
                }
                else {
                    stage.setHeight(stage.getMinHeight());
                }
            }
        } 
        else if (stage.getScene().getCursor() == javafx.scene.Cursor.S_RESIZE) {
            double height = y;
            if (height >= stage.getMinHeight()) {
                stage.setHeight(height);
            }
            else {
                stage.setHeight(stage.getMinHeight());
            }
        }
    }

    /**
     * Ease of access load fxml file
     * @param fxml
     * @return fxml file with .fxml ending
     * @throws IOException
     */
    @SuppressWarnings("exports")
    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Main Driver
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }

    public static void setTheme() {
        String style = buildCSS();
        for (String string: styles) {
            if (string != null) style += string;
        }
        root.setStyle(style);
    }

    public static void setTheme(String setter, String value) {
        setStyles(setter, value);
        String style = buildCSS();
        for (String string: styles) {
            if (string != null) style += string;
        }
        root.setStyle(style);
    }

    private static void setStyles(String setter, String value) {
        boolean check = false;
    
        for (int i = 0; i < styles.length; i++) {
            if (styles[i] != null && styles[i].contains(setter + ":")) {
                styles[i] = setter + ": " + value + ";";
                check = true;
                break;
            }
        }
    
        if (!check) {
            for (int i = 0; i < styles.length; i++) {
                if (styles[i] == null || styles[i].isBlank()) {
                    styles[i] = setter + ": " + value + ";";
                    return;
                }
            }
        }
    }    

    public static String getTheme() {
        String style = buildCSS();
        for (String string: styles) {
            if (string != null) style += string;
        }
        return style;
    }

    private static String buildCSS() {
        int theme = (Integer) Settings.getSetting(Settings.DisplaySettings.THEME);
        boolean darkmode = (Boolean) Settings.getSetting(Settings.DisplaySettings.DARK_MODE);

        String fullStyle;
        String primary = "-primaryColor: ";
        String secondary = " -secondaryColor: ";
        String tertiary = " -tertiaryColor: ";
        String quartary = " -quartaryColor: ";

        String bPrim = " -brightPrimary: ";
        String bSec = " -brightSecondary: ";

        String hPrim = "-hoverPrimary: ";
        String hSec = "-hoverSecondary: ";

        String colorBorder = "-colorBorder: ";

        String textHead = " -headerTextColor: ";
        String textFav = " -favoriteTextColor: ";
        String textPrim = " -primaryTextColor: ";
        String textSec = " -secondaryTextColor: ";

        String backPrim = " -background: ";
        String backSec = " -backgroundSec: ";
        String backTer = " -backgroundTer: ";
        String backQuad = " -backgroundQuad: ";

        if (theme != 9) {
            if (darkmode) { 
                backPrim += "rgb(20, 20, 26);"; backSec += "rgb(16, 16, 22);";
                backTer += "rgb(12, 12, 18);"; backQuad += "rgba(0, 0, 0, 0.85);";
                textPrim += "aliceblue;"; textSec += "rgb(165, 165, 165);";
            }
            else { 
                backPrim += "rgb(250, 240, 250);"; backSec  += "rgb(240, 230, 240);";
                backTer  += "rgb(220, 215, 230);"; backQuad += "rgba(245, 250, 255, 0.85);";
                textPrim += "black;"; textSec += "rgb(14, 14, 14);";
            }
        }
        
        switch (theme) {
            case 1:
                primary += "rgb(31, 18, 43);"; secondary += "rgb(21, 8, 33);";
                tertiary += "aliceblue;"; quartary += "rgb(140, 148, 155);";
                bPrim += "rgb(115, 81, 148);"; bSec += "rgb(81, 47, 112);";
                textHead += "aliceblue;"; textFav += "gold;";
                hPrim += "rgba(115, 81, 148, 0.3);"; hSec += "rgba(81, 47, 112, 0.3);";
                colorBorder += "rgb(79, 41, 114);";
                
                break;
            case 2:
                primary += "rgb(55, 67, 55);"; secondary += "rgb(34, 42, 34);";
                tertiary += "rgb(180, 205, 172);"; quartary += "rgb(92, 104, 85);";
                bPrim += "rgb(45, 61, 42);"; bSec += "rgb(67, 85, 68);";
                textHead += "aliceblue;"; textFav += "gold;";
                hPrim += "rgba(115, 81, 148, 0.3);"; hSec += "rgba(81, 47, 112, 0.3);";
                colorBorder += "rgb(91, 52, 127);";

                break;
            case 3:
                primary += "rgb(210, 150, 174);"; secondary += "rgb(146, 106, 125);";
                tertiary += "rgb(236, 218, 232);"; quartary += "rgb(186, 149, 161);";
                bPrim += "rgb(158, 89, 111);"; bSec += "rgb(138, 98, 124);";
                textHead += "aliceblue;"; textFav += "gold;";
                hPrim += "rgba(115, 81, 148, 0.3);"; hSec += "rgba(81, 47, 112, 0.3);";
                colorBorder += "rgb(91, 52, 127);";

                if (darkmode) { backPrim += "rgb(18, 18, 24);"; backSec += "rgb(14, 14, 18);"; }
                else { backPrim += "rgb(186, 186, 210);"; backSec += "rgb(157, 157, 184);"; }
                
                break;
            case 4:
                primary += "rgb(126, 34, 34);"; secondary += "rgb(79, 61, 61);";
                tertiary += "rgb(211, 163, 163);"; quartary += "rgb(140, 112, 112);";
                bPrim += "rgb(85, 46, 46);"; bSec += "rgb(108, 72, 72);";
                textHead += "aliceblue;"; textFav += "gold;";
                hPrim += "rgba(115, 81, 148, 0.3);"; hSec += "rgba(81, 47, 112, 0.3);";
                colorBorder += "rgb(91, 52, 127);";

                if (darkmode) { backPrim += "rgb(18, 18, 24);"; backSec += "rgb(14, 14, 18);"; }
                else { backPrim += "rgb(186, 186, 210);"; backSec += "rgb(157, 157, 184);"; }
                
                break;
            case 5:
                primary += "rgb(63, 79, 107);"; secondary += "rgb(38, 48, 63);";
                tertiary += "rgb(201, 221, 238);"; quartary += "rgb(111, 131, 157);";
                bPrim += "rgb(42, 63, 92);"; bSec += "rgb(62, 84, 117);";
                textHead += "aliceblue;"; textFav += "gold;";
                hPrim += "rgba(115, 81, 148, 0.3);"; hSec += "rgba(81, 47, 112, 0.3);";
                colorBorder += "rgb(91, 52, 127);";

                if (darkmode) { backPrim += "rgb(18, 18, 24);"; backSec += "rgb(14, 14, 18);"; }
                else { backPrim += "rgb(186, 186, 210);"; backSec += "rgb(157, 157, 184);"; }
                
                break;
            case 6:
                primary += "rgb(102, 51, 27);"; secondary += "rgb(63, 31, 15);";
                tertiary += "rgb(243, 211, 186);"; quartary += "rgb(137, 97, 69);";
                bPrim += "rgb(81, 43, 21);"; bSec += "rgb(106, 57, 32);";
                textHead += "aliceblue;"; textFav += "gold;";
                hPrim += "rgba(115, 81, 148, 0.3);"; hSec += "rgba(81, 47, 112, 0.3);";
                colorBorder += "rgb(91, 52, 127);";

                if (darkmode) { backPrim += "rgb(18, 18, 24);"; backSec += "rgb(14, 14, 18);"; }
                else { backPrim += "rgb(186, 186, 210);"; backSec += "rgb(157, 157, 184);"; }
                
                break;
            case 7:
                primary += "rgb(86, 64, 104);"; secondary += "rgb(50, 35, 55);";
                tertiary += "rgb(174, 146, 188);"; quartary += "rgb(98, 81, 111);";
                bPrim += "rgb(60, 32, 56);"; bSec += "rgb(77, 49, 78);";
                textHead += "aliceblue;"; textFav += "gold;";
                hPrim += "rgba(115, 81, 148, 0.3);"; hSec += "rgba(81, 47, 112, 0.3);";
                colorBorder += "rgb(91, 52, 127);";

                if (darkmode) { backPrim += "rgb(18, 18, 24);"; backSec += "rgb(14, 14, 18);"; }
                else { backPrim += "rgb(186, 186, 210);"; backSec += "rgb(157, 157, 184);"; }
                
                break;
            case 8:
                primary += "rgb(77, 91, 65);"; secondary += "rgb(47, 56, 40);";
                tertiary += "rgb(184, 203, 157);"; quartary += "rgb(114, 130, 94);";
                bPrim += "rgb(56, 76, 53);"; bSec += "rgb(75, 98, 68);";
                textHead += "aliceblue;"; textFav += "gold;";
                hPrim += "rgba(115, 81, 148, 0.3);"; hSec += "rgba(81, 47, 112, 0.3);";
                colorBorder += "rgb(91, 52, 127);";

                if (darkmode) { backPrim += "rgb(18, 18, 24);"; backSec += "rgb(14, 14, 18);"; }
                else { backPrim += "rgb(186, 186, 210);"; backSec += "rgb(157, 157, 184);"; }
                
                break;
            case 9:
                @SuppressWarnings("unchecked") ArrayList<String> customColors = (ArrayList<String>) Settings.getSetting(Settings.DisplaySettings.CUSTOM_THEMES);

                primary += (customColors.get(0) + ";"); secondary += (customColors.get(1) + ";");
                tertiary += (customColors.get(2) + ";"); quartary += (customColors.get(3) + ";");

                backPrim += (customColors.get(4) + ";"); backSec += (customColors.get(5) + ";");
                backTer += (customColors.get(6) + ";"); backQuad += (customColors.get(7) + ";");

                textPrim += (customColors.get(8) + ";"); textSec += (customColors.get(9) + ";");
                textHead += (customColors.get(10) + ";"); textFav += (customColors.get(11) + ";");

                bPrim += (customColors.get(12) + ";"); bSec += (customColors.get(13) + ";");

                hPrim += (customColors.get(14) + ";"); hSec += (customColors.get(15) + ";");
                colorBorder += (customColors.get(16) + ";");

                break;
            default:
                break;
        }

        fullStyle = primary + secondary + tertiary + quartary + bPrim + bSec + textPrim + textSec + textFav + textHead + backPrim + backSec + backTer + backQuad + hPrim + hSec + colorBorder;

        return fullStyle;
    }
}