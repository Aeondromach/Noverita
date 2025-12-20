package com.aeondromach.controllers;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Desktop.Action;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

import com.aeondromach.App;
import com.aeondromach.Messages;
import com.aeondromach.Settings;
import com.aeondromach.Settings.DisplaySettings;
import com.aeondromach.system.IdClassList;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.Size;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SettingsController {
    @FXML private Button closeBtn;
    @FXML private BorderPane titleBar;

    @FXML private ToggleButton generalHeader;
    @FXML private ToggleButton displayHeader;
    @FXML private ToggleButton savingHeader;
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
    @FXML private ScrollPane savingScrollPane;
    @FXML private ScrollPane customScrollPane;
    @FXML private ScrollPane characterScrollPane;
    @FXML private ScrollPane pdfScrollPane;
    @FXML private ScrollPane aboutScrollPane;
    private final ArrayList<ScrollPane> SCROLL_PANES = new ArrayList<>();

    @FXML private CheckBox charLoadCheck;
    @FXML private CheckBox reloadCharactersCheck;
    @FXML private CheckBox utilizeBase64Check;

    @FXML private Button baseFolderButton;
    @FXML private Button customFolderButton;
    @FXML private Button characterFolderButton;
    @FXML private Button imagesFolderButton;

    @FXML private TextField baseFolderField;
    @FXML private TextField customFolderField;
    @FXML private TextField characterFolderField;
    @FXML private TextField imagesFolderField;

    @FXML private StackPane baseFolderPane;
    @FXML private StackPane customFolderPane;
    @FXML private StackPane characterFolderPane;
    @FXML private StackPane imagesFolderPane;

    @FXML private ChoiceBox<AppSizes> settingsAppSizeChoice;

    private final Size COMPACT_SIZE = new Size(1140, 555);
    private final Size MEDIUM_SIZE = new Size(1160, 610);
    private final Size LARGE_SIZE = new Size(1200, 660);

    private double mousePosX, mousePosY;
    private NovController nov;
    private boolean hubNeedRefresh = false;
    private boolean appNeedsRefresh = false;

    /**
     * Runs initial Settings set up
     * @param <T>
     */
    @FXML
    protected void initialize() {
        App.setSettingsController(this);
        nov = App.getNovController();
        App.setTheme();
        Platform.runLater(() -> {
            Collections.addAll(HEADERS,
                generalHeader,
                displayHeader,
                savingHeader,
                customHeader,
                characterHeader,
                pdfHeader,
                aboutHeader
            );
            Collections.addAll(SCROLL_PANES,
                generalScrollPane,
                displayScrollPane,
                savingScrollPane,
                customScrollPane,
                characterScrollPane,
                pdfScrollPane,
                aboutScrollPane
            );

            charLoadCheck.setSelected((Boolean) Settings.getSetting(Settings.GeneralSettings.CHARLOADALERT));
            darkModeCheck.setSelected((Boolean) Settings.getSetting(Settings.DisplaySettings.DARK_MODE));
            utilizeBase64Check.setSelected((Boolean) Settings.getSetting(Settings.SaveLoadSettings.BASE64));
            reloadCharactersCheck.setSelected((Boolean) Settings.getSetting(Settings.SaveLoadSettings.RELOAD_ON_SAVE));
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
                hubNeedRefresh = true;
            });

            charHubRefreshBt.setOnMouseClicked(e -> {
                nov.refreshHubCharacters();
                hubNeedRefresh = false;
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

            setFolderButtons(baseFolderButton, baseFolderPane);
            setFolderButtons(customFolderButton, customFolderPane);
            setFolderButtons(characterFolderButton, characterFolderPane);
            setFolderButtons(imagesFolderButton, imagesFolderPane);

            baseFolderField.setText(String.valueOf(Settings.getSetting(Settings.CustomSettings.BASE_PATH)));
            customFolderField.setText(String.valueOf(Settings.getSetting(Settings.CustomSettings.CUSTOM_PATH)));
            characterFolderField.setText(String.valueOf(Settings.getSetting(Settings.CustomSettings.CHAR_PATH)));
            imagesFolderField.setText(String.valueOf(Settings.getSetting(Settings.CustomSettings.PORTRAIT_PATH)));

            settingsAppSizeChoice.getItems().addAll(
                AppSizes.COMPACT,
                AppSizes.MEDIUM,
                AppSizes.LARGE
            );

            Size currentSize = new Size(
                (double) Settings.getSetting(Settings.DisplaySettings.APP_VIEW_SIZE_X),
                (double) Settings.getSetting(Settings.DisplaySettings.APP_VIEW_SIZE_Y)
            );

            if (currentSize.equals(COMPACT_SIZE)) settingsAppSizeChoice.setValue(AppSizes.COMPACT);
            else if (currentSize.equals(MEDIUM_SIZE)) settingsAppSizeChoice.setValue(AppSizes.MEDIUM);
            else if (currentSize.equals(LARGE_SIZE)) settingsAppSizeChoice.setValue(AppSizes.LARGE);
            else settingsAppSizeChoice.setValue(AppSizes.MEDIUM);

            settingsAppSizeChoice.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                switch (newVal) {
                    case COMPACT:
                        setAppSize(COMPACT_SIZE);
                        break;
                    case MEDIUM:
                        setAppSize(MEDIUM_SIZE);
                        break;
                    case LARGE:
                        setAppSize(LARGE_SIZE);
                        break;
                    default:
                        break;
                }
            });
        });
    }

    private void setAppSize(Size size) {
        Settings.setSetting(Settings.DisplaySettings.APP_VIEW_SIZE_X, size.WIDTH);
        Settings.setSetting(Settings.DisplaySettings.APP_VIEW_SIZE_Y, size.HEIGHT);
        Settings.saveSettings();

        if (!size.equals(new Size(App.mainWidth, App.mainHeight))) {
            appNeedsRefresh = true;
        }
        else appNeedsRefresh = false;
    }

    private class Size {
        public final double WIDTH;
        public final double HEIGHT;

        public Size(double width, double height) {
            this.WIDTH = width;
            this.HEIGHT = height;
        }

        public boolean equals(Size other) {
            return this.WIDTH == other.WIDTH && this.HEIGHT == other.HEIGHT;
        }
    }

    enum AppSizes {
        COMPACT("Compact (1140px x 555px)"),
        MEDIUM("Medium (1160px x 610px)"),
        LARGE("Large (1200px x 660px)");

        private final String displayName;

        AppSizes(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    private void setFolderButtons(Button button, StackPane stackPane) {
        button.setOnMouseEntered(e -> {
            button.getStyleClass().add("settingsHoverCondition");
            stackPane.getStyleClass().add("settingsHoverCondition");
        });

        button.setOnMouseExited(e -> {
            button.getStyleClass().remove("settingsHoverCondition");
            stackPane.getStyleClass().remove("settingsHoverCondition");
        });

        stackPane.setOnMouseEntered(e -> {
            button.getStyleClass().add("settingsHoverCondition");
            stackPane.getStyleClass().add("settingsHoverCondition");
        });

        stackPane.setOnMouseExited(e -> {
            button.getStyleClass().remove("settingsHoverCondition");
            stackPane.getStyleClass().remove("settingsHoverCondition");
        });
    }

    @FXML
    protected void handleHeaderClick(MouseEvent event) {
        ToggleButton tButton = (ToggleButton) event.getSource();
        ScrollPane sPane;

        if (tButton.equals(generalHeader)) sPane = generalScrollPane;
        else if (tButton.equals(displayHeader)) sPane = displayScrollPane;
        else if (tButton.equals(savingHeader)) sPane = savingScrollPane;
        else if (tButton.equals(customHeader)) sPane = customScrollPane;
        else if (tButton.equals(characterHeader)) sPane = characterScrollPane;
        else if (tButton.equals(pdfHeader)) sPane = pdfScrollPane;
        else if (tButton.equals(aboutHeader)) sPane = aboutScrollPane;
        else return;

        setActiveHeader(tButton, sPane);
    }

    private void setActiveHeader(ToggleButton activeHeader, ScrollPane activeScrollPane) {
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
            Stage stage = (Stage) closeBtn.getScene().getWindow();

            if (appNeedsRefresh) {
                Messages.yesNoAlert("Restart App?", "App requires restart", "Some content you have changed requires an app restart to take effect, would you like to restart the app?\n(If no, changes will apply next time app restarts)", null, () -> {stage.close(); nov.restartApp();}, () -> stage.close());
            }
            if (hubNeedRefresh) nov.refreshHubCharacters();

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

    @FXML
    protected void handleFolderButtonClick(MouseEvent event) {
        Button button = (Button) event.getSource();

        if (button.equals(baseFolderButton)) {
            folderSelected(
                String.valueOf(Settings.getSetting(Settings.CustomSettings.BASE_PATH)),
                Settings.CustomSettings.BASE_PATH,
                baseFolderField
            );
        } else if (button.equals(customFolderButton)) {
            folderSelected(
                String.valueOf(Settings.getSetting(Settings.CustomSettings.CUSTOM_PATH)),
                Settings.CustomSettings.CUSTOM_PATH,
                customFolderField
            );
            IdClassList.resetMaps();
            IdClassList.setIds(Paths.get(String.valueOf(Settings.getSetting(Settings.CustomSettings.CUSTOM_PATH))));
        } else if (button.equals(characterFolderButton)) {
            folderSelected(
                String.valueOf(Settings.getSetting(Settings.CustomSettings.CHAR_PATH)),
                Settings.CustomSettings.CHAR_PATH,
                characterFolderField
            );
            nov.refreshHubCharacters();
        } else if (button.equals(imagesFolderButton)) {
            folderSelected(
                String.valueOf(Settings.getSetting(Settings.CustomSettings.PORTRAIT_PATH)),
                Settings.CustomSettings.PORTRAIT_PATH,
                imagesFolderField
            );
            nov.refreshHubCharacters();
        }
    }

    private void folderSelected(String folderPath, Settings.CustomSettings settings, TextField field) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose Target Folder");

        File file = new File(folderPath);
        if (!file.exists()) file = new File(System.getProperty("user.home"));

        directoryChooser.setInitialDirectory(file);

        File selectedDirectory;
        selectedDirectory = directoryChooser.showDialog(null);

        if (selectedDirectory != null) {
            try {
                Settings.setSetting(settings, selectedDirectory.getCanonicalPath());
                Settings.saveSettings();

                field.setText(selectedDirectory.getCanonicalPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void handleFolderTextFieldEnter(ActionEvent event) {
        TextField field = (TextField) event.getSource();

        if (field.equals(baseFolderField)) {
            Settings.setSetting(Settings.CustomSettings.BASE_PATH, field.getText());
        } else if (field.equals(customFolderField)) {
            Settings.setSetting(Settings.CustomSettings.CUSTOM_PATH, field.getText());
            IdClassList.resetMaps();
            IdClassList.setIds(Paths.get(String.valueOf(Settings.getSetting(Settings.CustomSettings.CUSTOM_PATH))));
        } else if (field.equals(characterFolderField)) {
            Settings.setSetting(Settings.CustomSettings.CHAR_PATH, field.getText());
            nov.refreshHubCharacters();
        } else if (field.equals(imagesFolderField)) {
            Settings.setSetting(Settings.CustomSettings.PORTRAIT_PATH, field.getText());
            nov.refreshHubCharacters();
        }
    }

    @FXML
    protected void handleBase64UtilizationClick(MouseEvent event) {
        Settings.setSetting(Settings.SaveLoadSettings.BASE64, utilizeBase64Check.isSelected());
        Settings.saveSettings();
    }

    @FXML
    protected void reloadCharacterSaveClick(MouseEvent event) {
        Settings.setSetting(Settings.SaveLoadSettings.RELOAD_ON_SAVE, reloadCharactersCheck.isSelected());
        Settings.saveSettings();
    }
}