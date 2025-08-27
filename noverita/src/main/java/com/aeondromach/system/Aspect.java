package com.aeondromach.system;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.aeondromach.system.abstracts.Exclusive;
import com.aeondromach.system.parsers.XmlParser;

public class Aspect extends Exclusive {
    private String title;
    private String description;

    public Aspect(String id) {
        setMapSignifier(IdClassList.IdType.ASPECT);
        this.id = id;

        this.grantList = new ArrayList<>();
        this.statList = new ArrayList<>();

        checkId();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    protected void parseXML(Document doc) {
        Elements elements = doc.select("element[id]");
        for (Element element: elements) {
            if (element.attr("id").equals(id) && element.attr("type").toLowerCase().equals("aspect") && !element.attr("name").isEmpty()) {
                Element exclusive = element.selectFirst("exclusive");

                title = element.attr("name");
                if (element.hasAttr("description")) description = element.attr("description");
                else description = "No Description";

                grantList = XmlParser.parseExclusiveGrants(exclusive);
                statList = XmlParser.parseExclusiveStats(exclusive, element.attr("name"));
            }
        }
    }
    
}
