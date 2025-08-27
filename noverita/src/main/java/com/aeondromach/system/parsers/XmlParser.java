/**
 * @author Evelyn Engleman @Aeondromach
 * @version 1
 * @since 03/08/2025
 * The XmlParser object for Noverita
 */

package com.aeondromach.system.parsers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import com.aeondromach.App;
import com.aeondromach.Settings;
import com.aeondromach.system.IdClassList;
import com.aeondromach.system.minor.Grant;
import com.aeondromach.system.minor.OtherStat;

import javafx.scene.image.Image;

public class XmlParser {
    /**
     * Checks if id goes into fellow map, then reads out if so
     * @param id the id of the input
     * @param map the map the id is going into
     * @return a document Jsoup
     */
    public static Document check(String id, IdClassList.IdType map) {
        if (IdClassList.getIdMap(map).get(id) != null) {
            Path filePath = Paths.get(IdClassList.getIdMap(map).get(id));
            String path = IdClassList.getIdMap(map).get(id);

            if (Files.exists(Paths.get(path)) && Files.isRegularFile(filePath) && !IdClassList.getIdMap(map).get(id).contains("com/aeondromach/Standard_System")) {
                try {
                    return Jsoup.parse(filePath.toFile(), "UTF-8"); // Directly parse the file
                } catch (IOException e) {
                    return null;
                }
            } 
            else if (path.startsWith("com")) { // Ensures it's a resource path
                try (InputStream inputStream = IdClassList.class.getResourceAsStream("/" + path)) {
                    if (inputStream != null) {
                        Document doc = Jsoup.parse(inputStream, "UTF-8", "", Parser.xmlParser());

                        return doc; // Directly parse the file
                    } else {
                        return null;
                    }
                } catch (IOException e) {
                    return null;
                }
            }
        }
        return null;
    }

    public static ArrayList<Document> findAll(IdClassList.IdType map) {
        ArrayList<Document> docs = new ArrayList<>();
        for (String foundId: IdClassList.getIdMap(map).keySet()) {
            Path filePath = Paths.get(IdClassList.getIdMap(map).get(foundId));
            String path = IdClassList.getIdMap(map).get(foundId);

            if (Files.exists(Paths.get(path)) && Files.isRegularFile(filePath) && !IdClassList.getIdMap(map).get(foundId).contains("com/aeondromach/Standard_System")) {
                try {
                    docs.add(Jsoup.parse(filePath.toFile(), "UTF-8")); // Directly parse the file
                } catch (IOException e) {
                }
            } 
            else if (path.startsWith("com")) { // Ensures it's a resource path
                try (InputStream inputStream = IdClassList.class.getResourceAsStream("/" + path)) {
                    if (inputStream != null) {
                        Document doc = Jsoup.parse(inputStream, "UTF-8", "", Parser.xmlParser());

                        docs.add(doc); // Directly parse the file
                    } else {
                    }
                } catch (IOException e) {
                }
            }
        }
        
        if (!docs.isEmpty()) return docs;
        else return null;
    }

    public static ArrayList<Document> findAll(Map<String, String> map) {
        ArrayList<Document> docs = new ArrayList<>();
        for (String foundId: map.keySet()) {
            Path filePath = Paths.get(map.get(foundId));
            String path = map.get(foundId);

            if (Files.exists(Paths.get(path)) && Files.isRegularFile(filePath) && !map.get(foundId).contains("com/aeondromach/Standard_System")) {
                try {
                    docs.add(Jsoup.parse(filePath.toFile(), "UTF-8")); // Directly parse the file
                } catch (IOException e) {
                }
            } 
            else if (path.startsWith("com")) { // Ensures it's a resource path
                try (InputStream inputStream = IdClassList.class.getResourceAsStream("/" + path)) {
                    if (inputStream != null) {
                        Document doc = Jsoup.parse(inputStream, "UTF-8", "", Parser.xmlParser());

                        docs.add(doc); // Directly parse the file
                    } else {
                    }
                } catch (IOException e) {
                }
            }
        }
        
        if (!docs.isEmpty()) return docs;
        else return null;
    }

    public static Document check(String id, Map<String, String> map) {
        if (map.get(id) != null) {
            Path filePath = Paths.get(map.get(id));
            String path = map.get(id);

            if (Files.exists(Paths.get(path)) && Files.isRegularFile(filePath) && !map.get(id).contains("com/aeondromach/Standard_System")) {
                try {
                    return Jsoup.parse(filePath.toFile(), "UTF-8"); // Directly parse the file
                } catch (IOException e) {
                    return null;
                }
            } 
            else if (path.startsWith("com")) { // Ensures it's a resource path
                try (InputStream inputStream = IdClassList.class.getResourceAsStream("/" + path)) {
                    if (inputStream != null) {
                        Document doc = Jsoup.parse(inputStream, "UTF-8", "", Parser.xmlParser());

                        return doc; // Directly parse the file
                    } else {
                        return null;
                    }
                } catch (IOException e) {
                    return null;
                }
            }
        }
        return null;
    }

    public static ArrayList<Grant> parseExclusiveGrants(Element exclusive) {
        ArrayList<Grant> array = new ArrayList<>();
        Elements grants = exclusive.select("grant[id][type]");

        for (Element grant: grants) {
            Grant curGrant = new Grant(grant.attr("id"), grant.attr("type"));
            if (grant.attr("bonus") != null || grant.attr("bonus").isBlank()) curGrant.setBonus(grant.attr("bonus"));
            try {
                if (grant.attr("value") != null || grant.attr("value").isBlank()) curGrant.setValue(Integer.parseInt(grant.attr("value")));
            }
            catch (NumberFormatException e) {
            }
            array.add(curGrant);
        }

        return array;
    }

    public static ArrayList<OtherStat> parseExclusiveStats(Element exclusive, String origin) {
        ArrayList<OtherStat> statList = new ArrayList<>();
        Elements stats = exclusive.select("stat[id][type]");

        for (Element stat: stats) {
            if (isInteger(stat.attr("value"))) {
                statList.add(new OtherStat(stat.attr("name"), Integer.parseInt(stat.attr("value")), origin));
            }
            
        }

        return statList;
    }

    private static boolean isInteger(String str) {
        try {
            Integer.valueOf(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static int readStatId(String statId) {
        switch (statId.toLowerCase().trim()) {
            case "omni":
                return 7;
            case "all":
                return 8;
            case "strength":
                return 0;
            case "dexterity":
                return 1;
            case "constitution":
                return 2;
            case "intelligence":
                return 3;
            case "wisdom":
                return 4;
            case "charisma":
                return 5;
            default:
                return -1;
        }
    }

    public static Image findImage(Element portrait) {
        Element localPath = portrait.selectFirst("local");
        String localPathTag;
        
        if (localPath != null) localPathTag = localPath.ownText().trim();
        else localPathTag = null;

        if (localPathTag != null) {
            int lastSlashIndex = localPathTag.lastIndexOf("/") + 1;
            String fileName = localPathTag.substring(lastSlashIndex);

            Path filePath = Paths.get(localPathTag); // Convert the string path into a Path object
            Path customFilePath = Paths.get(Settings.getSetting(Settings.CustomSettings.PORTRAIT_PATH) + "\\" + fileName);
            if (Files.exists(filePath) && Files.isRegularFile(filePath)) {
                return new Image(new File(localPathTag).toURI().toString());
            }
            else if (Files.exists(customFilePath) && Files.isRegularFile(customFilePath)) {
                return new Image(new File(customFilePath.toString()).toURI().toString());
            }
            else {
                Element base64 = portrait.selectFirst("base64");

                return doBase64(base64);
            }
        }
        else {

            Element base64 = portrait.selectFirst("base64");

            return doBase64(base64);
        }
    }

    /**
     * Read a base64 line within a character's .ncf file, returning an image if successful or unsuccessful.
     * @param base64 Grab the XML portion for base64
     * @return the image
     */
    private static Image doBase64(Element base64) {
        if (base64 != null) {
            String base64Image = base64.ownText().replace("<![CDATA[", "").replace("]]>", "");
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
    
            return new Image(new ByteArrayInputStream(imageBytes));
        } else {
            try {
                return new Image(App.class.getResource("/com/aeondromach/images/noverita.png").toURI().toString());
            } catch (URISyntaxException e) {
            }
        }
        return null;  // In case of error, return null
    }

    public static Element getElement(Map<String, String> map, String id) {
        if (map.get(id) != null) {
            Path filePath = Paths.get(map.get(id));
            String path = map.get(id);

            if (Files.exists(Paths.get(path)) && Files.isRegularFile(filePath) && !map.get(id).contains("com/aeondromach/Standard_System")) {
                try {
                    Document doc = Jsoup.parse(filePath.toFile(), "UTF-8");

                    Elements elements = doc.select("element[id]");
                    for (Element element: elements) {
                        if (isIdMatching(element, id)) {
                            return element;
                        }
                    }
                } catch (IOException e) {
                    return null;
                }
            } 
            else if (path.startsWith("com")) { // Ensures it's a resource path
                try (InputStream inputStream = IdClassList.class.getResourceAsStream("/" + path)) {
                    if (inputStream != null) {
                        Document doc = Jsoup.parse(inputStream, "UTF-8", "", Parser.xmlParser());

                        Elements elements = doc.select("element[id]");
                        for (Element element: elements) {
                            if (isIdMatching(element, id)) {
                                return element;
                            }
                        }
                    } else {
                        return null;
                    }
                } catch (IOException e) {
                    return null;
                }
            }
        }
        return null;
    }

    public static boolean isIdMatching(Element element, String id) {
        return element.attr("id").toLowerCase().equals(id.toLowerCase());
    }

    public static boolean isTypeMatching(Element element, String type) {
        return element.attr("type").toLowerCase().equals(type.toLowerCase());
    }
}