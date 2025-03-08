/**
 * @author Evelyn Engleman @Aeondromach
 * @version 1
 * @since 03/08/2025
 * The XmlParser object for Noverita
 */

package com.aeondromach.system;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

public class XmlParser {
    /**
     * Checks if id goes into fellow map, then reads out if so
     * @param id the id of the input
     * @param map the map the id is going into
     * @return a document Jsoup
     */
    public static Document check(String id, String map) {
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
}