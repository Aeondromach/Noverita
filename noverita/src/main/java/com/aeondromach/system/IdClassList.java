/**
 * @author Evelyn Engleman @Aeondromach
 * @version 1
 * @since 03/08/2025
 * The Id/Class reader for Noverita
 */

package com.aeondromach.system;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import com.aeondromach.App;
import com.aeondromach.Messages;
import com.aeondromach.Settings;

public class IdClassList {
    private static final Map<String, String> FORM = new HashMap<>();
    private static final Map<String, String> FORM_SPECIALTY = new HashMap<>();
    private static final Map<String, String> FORM_HUNGER = new HashMap<>();
    private static final Map<String, String> ASPECT = new HashMap<>();
    private static final Map<String, String> ASPECT_INTRINSIC = new HashMap<>();
    private static final Map<String, String> ASPECT_SPECIALTY = new HashMap<>();
    private static final Map<String, String> FLESH = new HashMap<>();
    private static final Map<String, String> CLOAK = new HashMap<>();

    public enum IdType {
        FORM,
        FORM_SPECIALTY,
        FORM_HUNGER,
        ASPECT,
        ASPECT_INTRINSIC,
        ASPECT_SPECIALTY,
        FLESH,
        CLOAK
    }

    /**
     * Returns an ID map depending on the string grabber name
     * @param grabber the identifier for the map
     * @return the map of ids
     */
    public static Map<String, String> getIdMap(IdType grabber) {
        switch (grabber) {
            case FORM:
                return FORM;
            case FORM_SPECIALTY:
                return FORM_SPECIALTY;
            case FORM_HUNGER:
                return FORM_HUNGER;
            case ASPECT:
                return ASPECT;
            case ASPECT_INTRINSIC:
                return ASPECT_INTRINSIC;
            case ASPECT_SPECIALTY:
                return ASPECT_SPECIALTY;
            case FLESH:
                return FLESH;
            case CLOAK:
                return CLOAK;
            default:
                return null;
        }
    }
    
    /**
     * The constructor for IdClassList
     * @param folder the folder at which all ids are read (besides standard system)
     */
    public static void setIds(Path folder) {
        try {
            Files.walk(folder)
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".xml"))
                .forEach(path -> parseXML(path.toFile()));
        } catch (IOException e) {
            Messages.errorAlert(folder + " not loaded.", "Error L241: XML loading error", "Issue with loading custom file directory (IOException), switching to default directory.", e);
            folder = Paths.get(String.valueOf(Settings.getDefaultSetting(Settings.CustomSettings.CUSTOM_PATH)));
            try {
                Files.walk(folder)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".xml"))
                    .forEach(path -> parseXML(path.toFile()));
            } catch (IOException e1) {
                Messages.errorAlert(folder + " not loaded.", "Error L251: XML loading error", "Issue with loading default file directory (IOException).", e1);
                
            }
        }

        try {
            URL jarUrl = App.class.getProtectionDomain().getCodeSource().getLocation();
            
            try (InputStream jarStream = jarUrl.openStream();
                 ZipInputStream zipStream = new ZipInputStream(jarStream)) {

                ZipEntry entry;
                while ((entry = zipStream.getNextEntry()) != null) {
                    if (entry.getName().startsWith("/com/aeondromach/Standard_System/".substring(1)) && entry.getName().endsWith(".xml")) {
                        parseXML(zipStream, entry.getName()); // Read XML directly from the stream
                    }
                }
            }
        } catch (IOException e) {
            Messages.errorAlert("Internal components not loaded.", "Error L240: XML loading error", "Issue with loading internal system directory (IOException)", e);
        }
    }

    public static void resetMaps() {
        FORM.clear();
        FORM_HUNGER.clear();
        FORM_SPECIALTY.clear();
        ASPECT.clear();
        ASPECT_INTRINSIC.clear();
        ASPECT_SPECIALTY.clear();
        FLESH.clear();
        CLOAK.clear();
    }

    /**
     * Parse xml for computer files
     * @param file the file to be read
     */
    private static void parseXML(File file) {
        try {
            Document doc = Jsoup.parse(file, "UTF-8");
            readXML(doc, file.getPath());
        } catch (IOException e) {
            Messages.errorAlert(file + "Refused to load.", "Error L242: XML loading error", "Issue with loading custom system file (IOException)", e);
        }
    }

    /**
     * Parse xml for jar/exe files
     * @param zipStream The zip entry to be read
     * @param entry the string entry to save
     */
    private static void parseXML(ZipInputStream zipStream, String entry) {
        try {
            byte[] data = zipStream.readAllBytes();
            InputStream inputStream = new ByteArrayInputStream(data);

            Document doc = Jsoup.parse(inputStream, "UTF-8", "", Parser.xmlParser());
            readXML(doc, entry);
        } catch (IOException e) {
            Messages.errorAlert(entry + "Refused to load.", "Error L243: XML loading error", "Issue with loading internal system file (IOException)", e);
        }
    }

    /**
     * Read out the xml and take all ids that it can find
     * @param doc the Jsoup document
     * @param path the path to the file to be saved
     */
    private static void readXML(Document doc, String path) {
        Elements elements = doc.select("element[id]"); // Get all <element> tags with an "id" attribute
        for (Element element: elements) {
            if (!element.attr("id").isEmpty() && !element.attr("type").isEmpty()) {
                switch (element.attr("type").toLowerCase()) {
                    case "form":
                        FORM.putIfAbsent(element.attr("id"), path);
                        break;
                    case "form specialty":
                        FORM_SPECIALTY.putIfAbsent(element.attr("id"), path);
                        break;
                    case "form hunger":
                        FORM_HUNGER.putIfAbsent(element.attr("id"), path);
                        break;
                    case "aspect":
                        ASPECT.putIfAbsent(element.attr("id"), path);
                        break;
                    case "aspect intrinsic":
                        ASPECT_INTRINSIC.putIfAbsent(element.attr("id"), path);
                        break;
                    case "aspect specialty":
                        ASPECT_SPECIALTY.putIfAbsent(element.attr("id"), path);
                        break;
                    case "flesh":
                        FLESH.putIfAbsent(element.attr("id"), path);
                        break;
                    default:
                        break;
                }

                Elements inclusives = element.select("inclusive");
                grabCloaks(inclusives, path);

                Elements exclusives = element.select("exclusive");
                grabCloaks(exclusives, path);
            }
        }
    }

    /**
     * Grab overwritable IDs
     * @param elements elements within hiding
     * @param path path of the cloak
     */
    private static void grabCloaks(Elements elements, String path) {
        for (Element element: elements) {
            Elements grants = element.select("grant");
            for (Element grant: grants) {
                if (!grant.attr("cloak").isEmpty()) {
                    CLOAK.putIfAbsent(grant.attr("cloak"), path);
                } 
            }
            
            Elements stats = element.select("stat");
            for (Element stat: stats) {
                if (!stat.attr("cloak").isEmpty()) {
                    CLOAK.putIfAbsent(stat.attr("cloak"), path);
                } 
            }   
        }
    }
}