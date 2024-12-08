/**
 * @author Evelyn Engleman @Æondromach
 * Main Controller for Noverita
 */

package com.aeondromach;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class NovController {
    @FXML private BorderPane novPane;

    private HeaderController headCon;

    public static final String NAME = getProperty("app.name");
    public static final String VERSION = getProperty("app.version");

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
        headCon = new FXMLLoader(getClass().getResource("header.fxml")).getController();
    }
}
