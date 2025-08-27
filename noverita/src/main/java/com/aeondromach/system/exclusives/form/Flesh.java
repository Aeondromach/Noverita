/**
 * @author Evelyn Engleman @Aeondromach
 * @version 1
 * @since 03/08/2025
 * The flesh object/reader for Noverita
 */

package com.aeondromach.system.exclusives.form;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.aeondromach.system.IdClassList;
import com.aeondromach.system.abstracts.Exclusive;
import com.aeondromach.system.parsers.XmlParser;

public class Flesh extends Exclusive {

    /**
     * The constructor for flesh
     * @param id the id being read
     */
    public Flesh(String id) {
        setMapSignifier(IdClassList.IdType.FLESH);
        this.id = id;

        this.grantList = new ArrayList<>();
        this.statList = new ArrayList<>();

        checkId();
    }

    /**
     * Parses the Flesh xml file
     * @param doc gets the Jsoup document
     */
    @Override
    protected void parseXML(Document doc) {
        Elements elements = doc.select("element[id]");
        for (Element element: elements) {
            if (element.attr("id").equals(id) && element.attr("type").toLowerCase().equals("flesh") && !element.attr("name").isEmpty()) {
                Element exclusive = element.selectFirst("exclusive");

                grantList = XmlParser.parseExclusiveGrants(exclusive);

                statList = XmlParser.parseExclusiveStats(exclusive, element.attr("name"));

                    // if (grant.attr("id").startsWith("ID_DAMAGE_TYPE_")) {
                    //     DamageType.setResistance(DamageType.getDamageType(grant.attr("id")), ResistanceLevel.getResistanceLevel(grant.attr("value")));
                    // }
                    // else if (grant.attr("id").startsWith("ID_DAMAGE_CATEGORY_")) {
                    //     DamageType.setResistances(DamageType.getDamageCategory(grant.attr("id")), ResistanceLevel.getResistanceLevel(grant.attr("value")));
                    // }
            }
        }
    }
}