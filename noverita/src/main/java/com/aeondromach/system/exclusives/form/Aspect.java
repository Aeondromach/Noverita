package com.aeondromach.system.exclusives.form;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.aeondromach.system.IdClassList;
import com.aeondromach.system.abstracts.Exclusive;
import com.aeondromach.system.parsers.XmlParser;

public class Aspect extends Exclusive {
    private String title;
    private String description;

    private String formId;

    public Aspect(String id) {
        setMapSignifier(IdClassList.IdType.ASPECT);
        this.id = id;

        this.grantList = new ArrayList<>();
        this.statList = new ArrayList<>();

        checkId();
    }

    public void setId(String id, String formId) {
        String requisites = "";
        String parents = "";
        
        if (formId != null) requisites = XmlParser.getChildrenOf(formId, IdClassList.getIdMap(IdClassList.IdType.FORM)); // grabs form children, ex Albedo, Cyborg, etc
        if (id != null) parents = XmlParser.getParentsOf(id, IdClassList.getIdMap(IdClassList.IdType.ASPECT)); // grabs aspect parents, ex Human, Protean, etc
        
        // checks if aspect id = one of the children, ex ID_ASPECT_ALBEDO = ID_ASPECT_ALBEDO, ID_ASPECT_CYBORG, etc
        // OR statement checks if the form id = one of the aspect's parents, ex ID_FORM_HUMAN = ID_FORM_HUMAN, ID_FORM_PROTEAN, etc
        if (id != null && formId != null && (XmlParser.isChildOrParent(id, requisites) || XmlParser.isChildOrParent(formId, parents))) { 
            this.id = id;
    
            checkId();
        }
        else {
            this.id = null;
            this.title = null;
            this.description = null;
            this.grantList.clear();
            this.statList.clear();
        }
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
        this.grantList.clear();
        this.statList.clear();

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