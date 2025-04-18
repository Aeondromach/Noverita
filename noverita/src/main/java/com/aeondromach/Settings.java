package com.aeondromach;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public abstract class Settings {
    private static final String EXTERNAL_PATH = System.getProperty("user.home") + "\\documents\\Noverita\\settings.json";
    private static final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    public static Map<String, Map<String, Object>> settingsMap;
    
    public static void readConfigFile() {
        File externalFile = new File(EXTERNAL_PATH);

        try (InputStream inputStream = new FileInputStream(externalFile)) {
            byte[] data = inputStream.readAllBytes();
            processFileData(data);

        } catch (IOException e) {
            Messages.errorAlert("Settings refused to load.", "Error L151: Config loading error", 
                    "Issue with loading the requested settings file (IOException)", e);
        }
    }

    @SuppressWarnings("unchecked")
    private static void processFileData(byte[] data) {
        try {
            String content = new String(data, StandardCharsets.UTF_8);
            ObjectMapper objectMapperer = new ObjectMapper();

            // Convert JSON to a Map with Sections as Keys
            settingsMap = objectMapperer.readValue(content, Map.class);
        } catch (IOException e) {
            Messages.errorAlert("Settings could not be parsed.", "Error L152: JSON Parsing Error", 
                    "Failed to parse the settings file.", e);
        }
    }

    public static Map<String, Map<String, Object>> getSettingsMap() {
        return settingsMap;
    }

    public static void setSetting(Enum<?> setting, Object value) {
        String sectionName = setting.getClass().getSimpleName().replace("Settings", "").toLowerCase();
        String key = "";

        // Determine key based on Enum type
        if (setting instanceof GeneralSettings) {
            key = ((GeneralSettings) setting).getKey();
        } else if (setting instanceof DisplaySettings) {
            key = ((DisplaySettings) setting).getKey();
        } else if (setting instanceof CustomSettings) {
            key = ((CustomSettings) setting).getKey();
        }

        // Update in-memory settings
        settingsMap.computeIfAbsent(sectionName, k -> new java.util.HashMap<>()).put(key, value);

        // Write changes to file
        saveSettingsToFile();
    }

    private static void saveSettingsToFile() {
        if (Files.exists(Paths.get(EXTERNAL_PATH)) && Files.isRegularFile(Paths.get(EXTERNAL_PATH))) {
            try (FileWriter file = new FileWriter(EXTERNAL_PATH, StandardCharsets.UTF_8)) {
                objectMapper.writeValue(file, settingsMap);
            } catch (IOException e) {
                Messages.errorAlert("Settings could not be saved.", "Error L153: JSON Writing Error", 
                        "Failed to save the updated settings file.", e);
            }
        }
        else {
            try (FileWriter file = new FileWriter(EXTERNAL_PATH, StandardCharsets.UTF_8)) {
                Map<String, Object> generalSettings = new HashMap<>();
                    generalSettings.put(GeneralSettings.CHARLOADALERT.getKey(), GeneralSettings.CHARLOADALERT.getDefaultValue());
    
                Map<String, Object> displaySettings = new HashMap<>();
                    displaySettings.put(DisplaySettings.CHAR_VIEW_SIZE.getKey(), DisplaySettings.CHAR_VIEW_SIZE.getDefaultValue());
                    displaySettings.put(DisplaySettings.DARK_MODE.getKey(), DisplaySettings.DARK_MODE.getDefaultValue());
                    displaySettings.put(DisplaySettings.THEME.getKey(), DisplaySettings.THEME.getDefaultValue());
    
                Map<String, Object> customSettings = new HashMap<>();
                    customSettings.put(CustomSettings.BASE_PATH.getKey(), CustomSettings.BASE_PATH.getDefaultValue());
                    customSettings.put(CustomSettings.CHAR_PATH.getKey(), CustomSettings.CHAR_PATH.getDefaultValue());
                    customSettings.put(CustomSettings.CUSTOM_PATH.getKey(), CustomSettings.CUSTOM_PATH.getDefaultValue());
    
                Map<String, Map<String, Object>> setterMap = new HashMap<>();
                    setterMap.put("general", generalSettings);
                    setterMap.put("display", displaySettings);
                    setterMap.put("custom", customSettings);
                objectMapper.writeValue(file, setterMap);
            } catch (IOException e) {
                Messages.errorAlert("Settings could not be saved.", "Error L159: JSON Writing Error", 
                        "Failed to save the updated settings file.", e);
            }
        }
    }

    public static void saveSettings() {
        saveSettingsToFile();
    }

    public static Object getSetting(Enum<?> setting) {
        String sectionName = setting.getClass().getSimpleName().replace("Settings", "").toLowerCase();
        String key = "";
    
        // Get the key and default value from the Enum
        Object defaultValue = null;
        if (setting instanceof GeneralSettings) {
            key = ((GeneralSettings) setting).getKey();
            defaultValue = ((GeneralSettings) setting).getDefaultValue();
        } else if (setting instanceof DisplaySettings) {
            key = ((DisplaySettings) setting).getKey();
            defaultValue = ((DisplaySettings) setting).getDefaultValue();
        } else if (setting instanceof CustomSettings) {
            key = ((CustomSettings) setting).getKey();
            defaultValue = ((CustomSettings) setting).getDefaultValue();
        }
    
        // Fetch the setting, or return the default if missing
        Object value = settingsMap.getOrDefault(sectionName, Map.of()).getOrDefault(key, defaultValue);
    
        // Type-safe return
        return value;
    }

    public static Object getDefaultSetting(Enum<?> setting) {
        if (setting instanceof GeneralSettings) {
            return ((GeneralSettings) setting).getDefaultValue();
        } else if (setting instanceof DisplaySettings) {
            return ((DisplaySettings) setting).getDefaultValue();
        } else if (setting instanceof CustomSettings) {
            return ((CustomSettings) setting).getDefaultValue();
        }
        throw new IllegalArgumentException("Unknown setting type: " + App.class.getSimpleName());
    }

    public enum GeneralSettings {
        CHARLOADALERT("character loading alert", true);

        private final String key;
        private final Object defaultValue;

        GeneralSettings(String key, Object defaultValue) {
            this.key = key;
            this.defaultValue = defaultValue;
        }

        public String getKey() {
            return key;
        }

        public Object getDefaultValue() {
            return defaultValue;
        }

        public static GeneralSettings fromKey(String key) {
            for (GeneralSettings setting : values()) {
                if (setting.key.equalsIgnoreCase(key)) {
                    return setting;
                }
            }
            throw new IllegalArgumentException("Unknown key: " + key);
        }
    }

    public enum DisplaySettings {
        DARK_MODE("dark mode", false),
        THEME("theme", 1),
        CUSTOM_THEMES("custom themes", new ArrayList<String>(
            Arrays.asList(
                "rgb(31, 18, 43)", // primary
                "rgb(14, 14, 14)", // secondary
                "aliceblue", // tertiary
                "rgb(140, 148, 155)", // quartary
                "rgb(81, 47, 112)", // b primary
                "rgb(115, 81, 148)", // b secondary
                "aliceblue", // text primary
                "whitesmoke", // text secondary
                "aliceblue", // text header
                "gold", // text favorite
                "rgb(20, 20, 26)", // background primary
                "rgba(115, 81, 148, 0.3)", // hover primary
                "rgba(115, 81, 148, 0.3)", // hover secondary
                "rgb(91, 52, 127)" // color border
            )
        )), // Length 10
        CHAR_VIEW_SIZE("character hub block size", 175);

        private final String key;
        private final Object defaultValue;

        DisplaySettings(String key, Object defaultValue) {
            this.key = key;
            this.defaultValue = defaultValue;
        }

        public String getKey() {
            return key;
        }

        public Object getDefaultValue() {
            return defaultValue;
        }

        public static DisplaySettings fromKey(String key) {
            for (DisplaySettings setting : values()) {
                if (setting.key.equalsIgnoreCase(key)) {
                    return setting;
                }
            }
            throw new IllegalArgumentException("Unknown key: " + key);
        }
    }

    public enum CustomSettings {
        CHAR_PATH("character folder path", /*System.getProperty("user.home") + "\\documents\\Noverita"*/ "C:\\Users\\AdamE.2026\\OneDrive\\Evelyn\\Noverita Characters"),
        BASE_PATH("base path", System.getProperty("user.home") + "\\documents\\Noverita"),
        CUSTOM_PATH("custom folder", /*System.getProperty("user.home") + "\\documents\\Noverita\\custom"*/ "C:\\Users\\AdamE.2026\\OneDrive\\Evelyn\\Noverita\\noverita\\src\\main\\resources\\com\\aeondromach\\Standard_System");

        private final String key;
        private final Object defaultValue;

        CustomSettings(String key, Object defaultValue) {
            this.key = key;
            this.defaultValue = defaultValue;
        }

        public String getKey() {
            return key;
        }

        public Object getDefaultValue() {
            return defaultValue;
        }

        public static CustomSettings fromKey(String key) {
            for (CustomSettings setting : values()) {
                if (setting.key.equalsIgnoreCase(key)) {
                    return setting;
                }
            }
            throw new IllegalArgumentException("Unknown key: " + key);
        }
    }
}