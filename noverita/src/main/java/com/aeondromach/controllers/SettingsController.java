package com.aeondromach.controllers;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Collections;

import com.aeondromach.App;
import com.aeondromach.Settings;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SettingsController {
    @FXML private Button closeBtn;
    @FXML private BorderPane titleBar;

    @FXML private ToggleButton generalHeader;
    @FXML private ToggleButton displayHeader;
    @FXML private ToggleButton customHeader;
    @FXML private ToggleButton characterHeader;
    @FXML private ToggleButton pdfHeader;
    @FXML private ToggleButton aboutHeader;
    private final ArrayList<ToggleButton> HEADERS = new ArrayList<>();

    @FXML private ToggleButton themeButton1;
    @FXML private ToggleButton themeButton2;
    @FXML private ToggleButton themeButton3;
    @FXML private ToggleButton themeButton4;
    @FXML private ToggleButton themeButton5;
    @FXML private ToggleButton themeButton6;
    @FXML private ToggleButton themeButton7;
    @FXML private ToggleButton themeButton8;
    private final ArrayList<ToggleButton> THEME_BUTTONS = new ArrayList<>();

    @FXML private Slider charBlockSizeSlider;

    @FXML private Button charHubRefreshBt;

    @FXML private CheckBox customThemeCheck;
    @FXML private VBox customThemesHold;

    @FXML private ColorPicker primaryThemePicker;
    @FXML private ColorPicker secondaryThemePicker;
    @FXML private ColorPicker tertiaryThemePicker;
    @FXML private ColorPicker quartaryThemePicker;

    @FXML private ColorPicker primaryBackgroundPicker;
    @FXML private ColorPicker secondaryBackgroundPicker;
    @FXML private ColorPicker tertiaryBackgroundPicker;
    @FXML private ColorPicker quartaryBackgroundPicker;

    @FXML private ColorPicker primaryTextPicker;
    @FXML private ColorPicker secondaryTextPicker;
    @FXML private ColorPicker headerTextPicker;
    @FXML private ColorPicker favoriteTextPicker;

    @FXML private ColorPicker primaryBrightPicker;
    @FXML private ColorPicker secondaryBrightPicker;

    @FXML private ColorPicker primaryHoverPicker;
    @FXML private ColorPicker secondaryHoverPicker;

    @FXML private ColorPicker colorBorderPicker;

    private final ArrayList<ColorPicker> COLOR_PICKERS = new ArrayList<>();

    @FXML private CheckBox darkModeCheck;

    @FXML private ScrollPane generalScrollPane;
    @FXML private ScrollPane displayScrollPane;
    @FXML private ScrollPane customScrollPane;
    @FXML private ScrollPane characterScrollPane;
    @FXML private ScrollPane pdfScrollPane;
    @FXML private ScrollPane aboutScrollPane;
    private final ArrayList<ScrollPane> SCROLL_PANES = new ArrayList<>();

    @FXML private CheckBox charLoadCheck;

    private double mousePosX, mousePosY;
    private NovController nov;

    /**
     * Runs initial Settings set up
     */
    @FXML
    protected void initialize() {
        App.setSettingsController(this);
        nov = App.getNovController();
        App.setTheme();
        Platform.runLater(() -> {
            charLoadCheck.setSelected((Boolean) Settings.getSetting(Settings.GeneralSettings.CHARLOADALERT));
            darkModeCheck.setSelected((Boolean) Settings.getSetting(Settings.DisplaySettings.DARK_MODE));
            int theme = (Integer) Settings.getSetting(Settings.DisplaySettings.THEME);
            setActiveThemeButton(theme);

            customThemesHold.setVisible(theme == 9);
            customThemesHold.setManaged(theme == 9);

            charBlockSizeSlider.setValue((Integer) Settings.getSetting(Settings.DisplaySettings.CHAR_VIEW_SIZE));
            charBlockSizeSlider.valueProperty().addListener((obs, oldval, newVal) -> {
                charBlockSizeSlider.setValue(newVal.intValue());
                int val = 0;
                if (newVal.intValue() % 25 < 12) {
                    val = newVal.intValue() - (newVal.intValue() % 25);
                    charBlockSizeSlider.setValue(val);
                } else {
                    val = newVal.intValue() + (25 - (newVal.intValue() % 25));
                    charBlockSizeSlider.setValue(val);
                }
                Settings.setSetting(Settings.DisplaySettings.CHAR_VIEW_SIZE, val);
                Settings.saveSettings();
            });

            charHubRefreshBt.setOnMouseClicked(e -> {
                nov.refreshHubCharacters();
            });

            Collections.addAll(COLOR_PICKERS,
                primaryThemePicker,
                secondaryThemePicker,
                tertiaryThemePicker,
                quartaryThemePicker,
                primaryBackgroundPicker,
                secondaryBackgroundPicker,
                tertiaryBackgroundPicker,
                quartaryBackgroundPicker,
                primaryTextPicker,
                secondaryTextPicker,
                headerTextPicker,
                favoriteTextPicker,
                primaryBrightPicker,
                secondaryBrightPicker,
                primaryHoverPicker,
                secondaryHoverPicker,
                colorBorderPicker
            );

            for (ColorPicker picker: COLOR_PICKERS) {
                @SuppressWarnings("unchecked") ArrayList<String> customColors = (ArrayList<String>) Settings.getSetting(Settings.DisplaySettings.CUSTOM_THEMES);
                String color = customColors.get(COLOR_PICKERS.indexOf(picker));
                String[] nums = color.replace("rgba(", "").replace("rgb(", "").replace(")", "").split(",");
                if (nums.length == 4) {
                    picker.setValue(javafx.scene.paint.Color.rgb(
                        Integer.parseInt(nums[0].trim()),
                        Integer.parseInt(nums[1].trim()),
                        Integer.parseInt(nums[2].trim()),
                        Double.parseDouble(nums[3].trim())
                    ));
                } else if (nums.length == 3) {
                    picker.setValue(javafx.scene.paint.Color.rgb(
                        Integer.parseInt(nums[0].trim()),
                        Integer.parseInt(nums[1].trim()),
                        Integer.parseInt(nums[2].trim())
                    ));
                }
            }
        });
    }

    @FXML
    protected void handleGeneralClick(MouseEvent event) {
        setActiveHeader(generalHeader, generalScrollPane);
    }

    @FXML
    protected void handleDisplayClick(MouseEvent event) {
        setActiveHeader(displayHeader, displayScrollPane);
    }

    @FXML
    protected void handleCustomClick(MouseEvent event) {
        setActiveHeader(customHeader, customScrollPane);
    }

    @FXML
    protected void handleCharacterClick(MouseEvent event) {
        setActiveHeader(characterHeader, characterScrollPane);
    }

    @FXML
    protected void handlePDFClick(MouseEvent event) {
        setActiveHeader(pdfHeader, pdfScrollPane);
    }

    @FXML
    protected void handleAboutClick(MouseEvent event) {
        setActiveHeader(aboutHeader, aboutScrollPane);
    }

    private void setActiveHeader(ToggleButton activeHeader, ScrollPane activeScrollPane) {
        if (HEADERS.isEmpty()) {
            Collections.addAll(HEADERS,
                generalHeader,
                displayHeader,
                customHeader,
                characterHeader,
                pdfHeader
            );
        }

        if (SCROLL_PANES.isEmpty()) {
            Collections.addAll(SCROLL_PANES,
                generalScrollPane,
                displayScrollPane,
                customScrollPane,
                characterScrollPane,
                pdfScrollPane
            );
        }

        for (ToggleButton header : HEADERS) {
            if (header.equals(activeHeader)) {
                header.setSelected(true);
            } else {
                header.setSelected(false);
            }
        }

        for (ScrollPane pane : SCROLL_PANES) {
            if (pane.equals(activeScrollPane)) {
                pane.setVisible(true);
            } else {
                pane.setVisible(false);
            }
        }
    }

    /**
     * Handles the close button action
     * @param event
     */
    @FXML
    protected void handleCloseClick(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY)){
            nov.refreshHubCharacters();

            Stage stage = (Stage) closeBtn.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Handles when title bar is let go of
     * @param event
     */
    @FXML protected void handleTitleBarUnPress(MouseEvent event) {
        Stage stage = (Stage) titleBar.getScene().getWindow();
        if (stage.getY() < 0) stage.setY(2);
    }

    /**
     * Handles dragging app
     * @param event
     */
    @FXML
    protected void handleTitleBarDrag(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY)){
            Stage stage = (Stage) titleBar.getScene().getWindow();
            Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
            java.awt.Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
            int taskBarHeight = scrnSize.height - winSize.height;
            double maxThreshold = Screen.getPrimary().getBounds().getHeight() - taskBarHeight;

            
            stage.setX(event.getScreenX() - mousePosX);
            if (event.getScreenY() < maxThreshold) {
                stage.setY(event.getScreenY() - mousePosY);
            }
        }
    }

    /**
     * Activates when title bar is clicked, copies mouse position
     * @param event
     */
    @FXML
    protected void handleTitleBarPress(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY)){
            mousePosX = event.getX();
            mousePosY = event.getY();
        }
    }

    @FXML
    protected void handleCharLoadClick(MouseEvent event) {
        Settings.setSetting(Settings.GeneralSettings.CHARLOADALERT, charLoadCheck.isSelected());
        Settings.saveSettings();
    }

    @FXML protected void handleTheme1Click(MouseEvent event) {
        changeTheme(1);
        setActiveThemeButton(themeButton1);
    }

    @FXML
    protected void handleTheme2Click(MouseEvent event) {
        changeTheme(2);
        setActiveThemeButton(themeButton2);
    }

    @FXML
    protected void handleTheme3Click(MouseEvent event) {
        changeTheme(3);
        setActiveThemeButton(themeButton3);
    }

    @FXML
    protected void handleTheme4Click(MouseEvent event) {
        changeTheme(4);
        setActiveThemeButton(themeButton4);
    }

    @FXML
    protected void handleTheme5Click(MouseEvent event) {
        changeTheme(5);
        setActiveThemeButton(themeButton5);
    }

    @FXML
    protected void handleTheme6Click(MouseEvent event) {
        changeTheme(6);
        setActiveThemeButton(themeButton6);
    }

    @FXML
    protected void handleTheme7Click(MouseEvent event) {    
        changeTheme(7);
        setActiveThemeButton(themeButton7);
    }

    @FXML
    protected void handleTheme8Click(MouseEvent event) {
        changeTheme(8);
        setActiveThemeButton(themeButton8);
    }

    private void changeTheme(int theme) {
        Settings.setSetting(Settings.DisplaySettings.THEME, theme);
        Settings.saveSettings();
        App.setTheme();

        Stage stage = (Stage) titleBar.getScene().getWindow();
        stage.getScene().getRoot().setStyle(App.getTheme());
    }

    private void setActiveThemeButton(ToggleButton activeButton) {
        customThemeCheck.setSelected(false);
        customThemesHold.setVisible(false);
        customThemesHold.setManaged(false);
        for (ToggleButton button : THEME_BUTTONS) {
            if (button.equals(activeButton)) {
                button.setSelected(true);
            } else {
                button.setSelected(false);
            }
        }
    }

    private void setActiveThemeButton(int theme) {
        if (THEME_BUTTONS.isEmpty()) {
            Collections.addAll(THEME_BUTTONS,
                themeButton1,
                themeButton2,
                themeButton3,
                themeButton4,
                themeButton5,
                themeButton6,
                themeButton7,
                themeButton8
            );
        }

        if (theme == 9) {
            customThemeCheck.setSelected(true);
        }
        else {
            customThemeCheck.setSelected(false);
            for (ToggleButton button : THEME_BUTTONS) {
                if (THEME_BUTTONS.indexOf(button) == theme - 1) {
                    button.setSelected(true);
                } else {
                    button.setSelected(false);
                }
            }
        }
    }

    @FXML
    protected void handleDarkModeClick(MouseEvent event) {
        if (customThemeCheck.isSelected()) {
            Settings.setSetting(Settings.DisplaySettings.THEME, 1);
            setActiveThemeButton(1);
            customThemesHold.setVisible(false);
            customThemesHold.setManaged(false);
        }

        Settings.setSetting(Settings.DisplaySettings.DARK_MODE, darkModeCheck.isSelected());
        Settings.saveSettings();
        App.setTheme();

        Stage stage = (Stage) titleBar.getScene().getWindow();
        stage.getScene().getRoot().setStyle(App.getTheme());
    }

    @FXML
    protected void handleCustomThemeClick(MouseEvent event) {
        if (customThemeCheck.isSelected()) {
            Settings.setSetting(Settings.DisplaySettings.THEME, 9);
            setActiveThemeButton(9);
            customThemesHold.setVisible(true);
            customThemesHold.setManaged(true);
        } else {
            Settings.setSetting(Settings.DisplaySettings.THEME, 1);
            setActiveThemeButton(1);
            customThemesHold.setVisible(false);
            customThemesHold.setManaged(false);
        }
        Settings.saveSettings();
        App.setTheme();

        Stage stage = (Stage) titleBar.getScene().getWindow();
        stage.getScene().getRoot().setStyle(App.getTheme());
    }

    @FXML
    protected void handleColorPickerAction(ActionEvent event) {
        ColorPicker picker = (ColorPicker) event.getSource();

        String color = String.format("rgba(%d, %d, %d, %.2f)",
            (int) (picker.getValue().getRed() * 255),
            (int) (picker.getValue().getGreen() * 255),
            (int) (picker.getValue().getBlue() * 255),
            picker.getValue().getOpacity()
        );

        @SuppressWarnings("unchecked") ArrayList<String> customColors = (ArrayList<String>) Settings.getSetting(Settings.DisplaySettings.CUSTOM_THEMES);
        customColors.set(COLOR_PICKERS.indexOf(picker), color);
        Settings.setSetting(Settings.DisplaySettings.CUSTOM_THEMES, customColors);
        Settings.saveSettings();
        App.setTheme();

        Stage stage = (Stage) titleBar.getScene().getWindow();
        stage.getScene().getRoot().setStyle(App.getTheme());
    }
}