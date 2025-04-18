package com.aeondromach.system.parsers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public abstract class HtmlParser {
    public static VBox parseHtml(String id, Map<String, String> map) {
        Document doc = XmlParser.check(id, map);
        VBox htmlBox = new VBox();

        Elements elements = doc.select("element");
        for (Element element: elements) {
            if (XmlParser.isIdMatching(element, id)) {

            }
        }
        
        return htmlBox;
    }

    private static VBox readHtml(Element description) {
        VBox htmlBlock = readSections(description, new VBox());
        return htmlBlock;
    }

    private static VBox readSections(Element element, VBox block) {
        if (hasSections(element)) {
            Elements sections = element.select("p, div, section");
            for (Element section: sections) {
                switch (section.tag().toString()) {
                    case "p":
                        // FlowPane p = readBaseTags(element, textHold, tagStack);
                        // p.getStyleClass().add("paragraph");
                        // block.getChildren().add(p);
                        break;
                    case "div":
                        FlowPane div = new FlowPane();
                        div.getStyleClass().add("div");
                        block.getChildren().add(div);
                        break;
                    case "section":
                        FlowPane sect = new FlowPane();
                        sect.getStyleClass().add("section");
                        block.getChildren().add(sect);
                        break;
                }
            }
        }
        return block;
    }

    private static boolean hasSections(Element element) {
        Elements sections = element.select("p, div, section");
        return !sections.isEmpty();
    }

    private static void readBaseTags(Element element, FlowPane textHold, List<String> tagStack) {
        for (org.jsoup.nodes.Node node : element.childNodes()) {
            if (node instanceof TextNode) {
                String textContent = ((TextNode) node).text().trim();
                if (!textContent.isEmpty()) {
                    Text text = new Text(textContent);
                    text.getStyleClass().addAll(getStyles(tagStack));
                    textHold.getChildren().add(text);
                }
            } else if (node instanceof Element) {
                Element childElement = (Element) node;
                List<String> newTagStack = new ArrayList<>(tagStack);
                newTagStack.add(childElement.tagName());
                readBaseTags(childElement, textHold, newTagStack);
            }
        }
    }

    private static String readText(Element element) {
        return element.ownText();
    }

    private static boolean hasBaseTags(Element element) {
        Elements baseTags = element.select("p, div, section");
        return !baseTags.isEmpty();
    }

    private static List<String> getStyles(List<String> tags) {
    List<String> styles = new ArrayList<>();
    for (String tag : tags) {
        switch (tag) {
            case "b":
                styles.add("bold");
                break;
            case "strong":
                styles.add("bold");
                styles.add("semantic");
                break;
            case "i":
                styles.add("italic");
                break;
            case "em":
                styles.add("italic");
                styles.add("semantic");
                break;
            case "u":
                styles.add("underline");
                break;
            case "sub":
                styles.add("subscript");
                break;
            case "sup":
                styles.add("superscript");
                break;
            case "mark":
                styles.add("highlight");
                break;
            case "span":
                // Optionally apply styles based on span attributes
                break;
            }
        }
        return styles;
    }
}