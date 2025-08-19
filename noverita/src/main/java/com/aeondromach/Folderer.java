package com.aeondromach;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Folderer {
    private static final String DOCUMENT_PATH = System.getProperty("user.home") + "\\documents";
    private static String customPath;

    public static void checkSettings() {
        if (Files.exists(Paths.get(DOCUMENT_PATH + "\\Noverita\\settings.json")) && Files.isRegularFile(Paths.get(DOCUMENT_PATH + "\\Noverita\\settings.json"))) {
            Settings.readConfigFile();
        }
        else {
            Messages.debugAlertRemoveOnLaunch("Creating Settings file - REMOVE ON FULL RELEASE", "No settings.json file found, creating.", "Settings.json file created:\n" + DOCUMENT_PATH + "\\Noverita\\settings.json");
            if (!Files.exists(Paths.get(DOCUMENT_PATH + "\\Noverita"))) {
                try {
                    Files.createDirectories(Paths.get(DOCUMENT_PATH + "\\Noverita"));
                } catch (IOException e) {
                }
            }
            Settings.saveSettings();
            Settings.readConfigFile();
        }

        checkForPath(Settings.CustomSettings.CUSTOM_PATH, "custom");

        checkForPath(Settings.CustomSettings.CHAR_PATH, "character");

        checkForPath(Settings.CustomSettings.PORTRAIT_PATH, "portrait");
    }

    private static void checkForPath(Settings.CustomSettings pathEnum, String pathName) {
        if (!Files.exists(Paths.get("" + Settings.getSetting(pathEnum)))) {
            Messages.debugAlertRemoveOnLaunch("Creating " + pathName + " folder - REMOVE ON FULL RELEASE", "No " + pathName + " folder found, creating.", pathName + " folder created:\n" + Settings.getSetting(pathEnum));
            try {
                Files.createDirectories(Paths.get("" + Settings.getSetting(Settings.CustomSettings.PORTRAIT_PATH)));
            } catch (IOException e) {
            }
        }
    }

    public static void checkFolder() {
        if (Files.exists(Paths.get(DOCUMENT_PATH + "\\Noverita")) && Files.isRegularFile(Paths.get(DOCUMENT_PATH + "\\Noverita"))) {
            
        }
    }

    public static void recheckCustom() {
        customPath = (String) Settings.getSetting(Settings.CustomSettings.BASE_PATH);
    }
}
