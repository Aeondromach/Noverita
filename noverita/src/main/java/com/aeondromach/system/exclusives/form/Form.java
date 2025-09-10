/**
 * @author Evelyn Engleman @Aeondromach
 * @version 1
 * @since 03/08/2025
 * The aspect object for Noverita
 */

package com.aeondromach.system.exclusives.form;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.aeondromach.system.IdClassList;
import com.aeondromach.system.abstracts.Exclusive;
import com.aeondromach.system.minor.Grant;
import com.aeondromach.system.parsers.XmlParser;

public class Form extends Exclusive {
    private String title;
    private String description;

    private final Aspect ASPECT;
    private final Flesh FLESH;

    public Aspect getASPECT() {
        return ASPECT;
    }

    /**
     * The constructor for aspect
     * @param id the aspect id
     * @param aspectId the race id
     */
    public Form(String id, String aspectId) {
        setMapSignifier(IdClassList.IdType.FORM);
        this.id = id;

        this.ASPECT = new Aspect(null);
        this.FLESH = new Flesh(null);

        this.grantList = new ArrayList<>();
        this.statList = new ArrayList<>();

        this.ASPECT.setId(aspectId, id);
        checkId();
    }

    /**
     * returns the title of aspect
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * returns the description of aspect
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of aspect
     * @param description description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the title of aspect
     * @param title title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * returns the flesh of aspect
     * @return flesh
     */
    public Flesh getFLESH() {
        return FLESH;
    }

    public Boolean hasFlesh() {
        try {
            return this.FLESH != null;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public Boolean hasAspect() {
        try {
            return this.ASPECT != null;
        } catch (NullPointerException e) {
            return false;
        }
    }

    @Override
    protected void parseXML(Document doc) {
        this.grantList.clear();
        this.statList.clear();

        Elements elements = doc.select("element[id]");
        for (Element element: elements) {
            if (element.attr("id").equals(this.id) && element.attr("type").toLowerCase().equals("form") && !element.attr("name").isEmpty() && !element.select("description").isEmpty()) {
                Element descriptionXML = element.selectFirst("description");

                setTitle(element.attr("name"));
                setDescription(descriptionXML.html().trim());

                Element exclusive = element.selectFirst("exclusive");

                grantList = XmlParser.parseExclusiveGrants(exclusive);
                for (Grant grant: grantList) {
                    if (grant.getId().startsWith("ID_FLESH")) {
                        this.FLESH.setId(grant.getId());
                        grantList.remove(grant);
                        break;
                    }
                }

                if (!element.attr("name").isEmpty()) statList = XmlParser.parseExclusiveStats(exclusive, element.attr("name"));
                else statList = XmlParser.parseExclusiveStats(exclusive, "Form");
                if (!isAspectChild()) this.ASPECT.setId(null, id);
            }
        }
    }

    private boolean isAspectChild() {
        String requisites = XmlParser.getChildrenOf(id, IdClassList.getIdMap(IdClassList.IdType.FORM));
        String parents = XmlParser.getParentsOf(this.ASPECT.getId(), IdClassList.getIdMap(IdClassList.IdType.ASPECT));

        return XmlParser.isChildOrParent(this.ASPECT.getId(), requisites) || XmlParser.isChildOrParent(id, parents);
    }
}