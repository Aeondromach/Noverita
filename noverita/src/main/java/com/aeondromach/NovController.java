/**
 * @author Evelyn Engleman @Æondromach
 * Main Controller for Noverita
 */

package com.aeondromach;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class NovController {
    @FXML private BorderPane novPane;

    @FXML private HeaderController headerController;
    @FXML private FooterController footerController;

    public static final String NAME = getProperty("app.name");
    public static final String VERSION = getProperty("app.version");
    private ArrayList<String> lastActions = new ArrayList<>();

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
     * Initial set up
     */
    @FXML
    protected void initialize() {
        headerController.init(this);
        footerController.init(this);
        addAction("Entered Noverita");
    }

    public void addAction(String text) {
        lastActions.add(text);
        footerController.setLastAction();
    }

    public String getLastAction() {
        return lastActions.get(lastActions.size() - 1);
    }
}
