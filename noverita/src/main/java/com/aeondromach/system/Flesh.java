/**
 * @author Evelyn Engleman @Aeondromach
 * @version 1
 * @since 03/08/2025
 * The flesh object/reader for Noverita
 */

package com.aeondromach.system;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.aeondromach.system.interfaces.system.Exclusive;
import com.aeondromach.system.minor.Grant;
import com.aeondromach.system.minor.OtherStat;
import com.aeondromach.system.parsers.XmlParser;

public class Flesh implements Exclusive {
    private final String ID;
    private ArrayList<Grant> grantList;
    private ArrayList<OtherStat> statList;

    /**
     * The constructor for flesh
     * @param id the id being read
     */
    public Flesh(String id) {
        this.ID = id;

        this.grantList = new ArrayList<>();
        this.statList = new ArrayList<>();

        checkId();
    }

    /**
     * Parses the Flesh xml file
     * @param doc gets the Jsoup document
     */
    private void parseXML(Document doc) {
        System.out.println("this");
        Elements elements = doc.select("element[id]");
        for (Element element: elements) {
            if (element.attr("id").equals(ID) && element.attr("type").toLowerCase().equals("flesh") && !element.attr("name").isEmpty()) {
                Element exclusive = element.selectFirst("exclusive");

                grantList = XmlParser.parseExclusiveGrants(exclusive);

                statList = XmlParser.parseExclusiveStats(exclusive, element.attr("name"));

                System.out.println(grantList + " | " + statList);
                    // if (grant.attr("id").startsWith("ID_DAMAGE_TYPE_")) {
                    //     DamageType.setResistance(DamageType.getDamageType(grant.attr("id")), ResistanceLevel.getResistanceLevel(grant.attr("value")));
                    // }
                    // else if (grant.attr("id").startsWith("ID_DAMAGE_CATEGORY_")) {
                    //     DamageType.setResistances(DamageType.getDamageCategory(grant.attr("id")), ResistanceLevel.getResistanceLevel(grant.attr("value")));
                    // }
            }
        }
    }

    /**
     * Checks the id of flesh to make sure it compares with known ids
     */
    private void checkId() {
        if (ID != null) {
            try {
                if (!grantList.isEmpty() || !this.grantList.isEmpty()) grantList.clear();
                if (!statList.isEmpty() || !this.statList.isEmpty()) statList.clear();
                parseXML(XmlParser.check(ID, "FLESH"));
            } 
            catch (NullPointerException e) {
                System.out.println("null");
            }
        }
    }

    @Override
    public ArrayList<OtherStat> getStatList() {
        return statList;
    }

    @Override
    public void reCheck() {
        checkId();
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public ArrayList<Grant> getGrantList() {
       return grantList;
    }

    @Override
    public Boolean hasStatList() {
        try {
            return this.statList != null;
        } catch (NullPointerException e) {
            return false;
        }
    }

    @Override
    public Boolean hasGrantList() {
        try {
            return this.grantList != null;
        } catch (NullPointerException e) {
            return false;
        }
    }
}