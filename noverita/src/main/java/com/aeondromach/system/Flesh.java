/**
 * @author Evelyn Engleman @Aeondromach
 * @version 1
 * @since 03/08/2025
 * The flesh object/reader for Noverita
 */

package com.aeondromach.system;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.aeondromach.system.enums.DamageType;
import com.aeondromach.system.enums.ResistanceLevel;

public class Flesh {
    private final String ID;

    /**
     * The constructor for flesh
     * @param id the id being read
     */
    public Flesh(String id) {
        this.ID = id;

        checkId();
    }

    /**
     * Parses the Flesh xml file
     * @param doc gets the Jsoup document
     */
    private void parseXML(Document doc) {
        Elements elements = doc.select("element[id]");
        for (Element element: elements) {
            if (element.attr("id").equals(ID)) {
                Element exclusive = element.select("exclusive").first();
                Elements grants = exclusive.select("grant[id]");
                for (Element grant: grants) {
                    if (grant.attr("id").startsWith("ID_DAMAGE_TYPE_")) {
                        DamageType.setResistance(DamageType.getDamageType(grant.attr("id")), ResistanceLevel.getResistanceLevel(grant.attr("value")));
                    }
                    else if (grant.attr("id").startsWith("ID_DAMAGE_CATEGORY_")) {
                        DamageType.setResistances(DamageType.getDamageCategory(grant.attr("id")), ResistanceLevel.getResistanceLevel(grant.attr("value")));
                    }
                    
                }
            }
        }
    }

    /**
     * Return the id of fleh
     * @return id
     */
    public String getID() {
        return ID;
    }

    /**
     * Checks the id of flesh to make sure it compares with known ids
     */
    private void checkId() {
        if (ID != null) {
            try {
                parseXML(XmlParser.check(ID, "FLESH"));
            } 
            catch (NullPointerException e) {
            }
        }
    }
}