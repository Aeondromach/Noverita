/**
 * @author Evelyn Engleman @Aeondromach
 * @version 1
 * @since 03/08/2025
 * The species object for Noverita
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

public class Species implements Exclusive {
    private String id;
    private String title;
    private String description;

    private Flesh flesh;

    private String subId;
    private String raceTitle;
    private String raceDescription;

    private ArrayList<Grant> grantList;
    private ArrayList<OtherStat> statList;

    /**
     * The constructor for species
     * @param id the species id
     * @param subId the race id
     */
    public Species(String id, String subId) {
        this.id = id;
        this.subId = subId;

        this.grantList = new ArrayList<>();
        this.statList = new ArrayList<>();

        checkId();
    }

    /**
     * Check if species id is fit to read
     */
    private void checkId() {
        if (this.id != null) {
            try {
                if (!grantList.isEmpty() || !this.grantList.isEmpty()) grantList.clear();
                if (!statList.isEmpty() || !this.statList.isEmpty()) statList.clear();
                parseXML(XmlParser.check(this.id, "SPECIES"));
                checkSubId();
            } 
            catch (NullPointerException e) {
            }
        }
    }

    /**
     * Check if race id is fit to read
     */
    private void checkSubId() {
        if (subId != null) {
            try {
                parseXML(XmlParser.check(subId, "RACE"));
                System.out.println(raceTitle + " || " + raceDescription);
            } 
            catch (NullPointerException e) {
                System.out.println("Race NULL");
            }
        }
    }

    /**
     * Parse and find the xml files to read
     * @param doc Jsoup document
     */
    private void parseXML(Document doc) {
        Elements elements = doc.select("element[id]");
        for (Element element: elements) {
            if (element.attr("id").equals(this.id) && element.attr("type").toLowerCase().equals("species") && !element.attr("name").isEmpty() && !element.select("description").isEmpty()) {
                Element descriptionXML = element.selectFirst("description");

                setTitle(element.attr("name"));
                setDescription(descriptionXML.html().trim());

                Element exclusive = element.selectFirst("exclusive");

                grantList = XmlParser.parseExclusiveGrants(exclusive);

                if (!element.attr("name").isEmpty()) statList = XmlParser.parseExclusiveStats(exclusive, element.attr("name"));
                else statList = XmlParser.parseExclusiveStats(exclusive, "Species");
            }
            else if (element.attr("id").equals(this.subId) && element.attr("type").toLowerCase().equals("race") && !element.attr("name").isEmpty() && !element.select("description").isEmpty()) {
                Element descriptionXML = element.selectFirst("description");
                Element exclusive = element.selectFirst("exclusive");

                setRaceTitle(element.attr("name"));
                setRaceDescription(descriptionXML.html().trim());

                ArrayList<Grant> array = XmlParser.parseExclusiveGrants(exclusive);
                try {
                    for (Grant granter: array) {
                        grantList.add(granter);
                    }
                } catch (NullPointerException e) {
                }
                
                ArrayList<OtherStat> arrayList;
                if (!element.attr("name").isEmpty()) 
                    arrayList = XmlParser.parseExclusiveStats(exclusive, element.attr("name"));
                else 
                    arrayList = XmlParser.parseExclusiveStats(exclusive, "Race");

                if (arrayList != null) {
                    statList.addAll(arrayList);
                }
            }
        }
    }

    /**
     * returns the id of species
     * @return id
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Sets the id of species
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
        checkId();
    }

    /**
     * returns the title of species
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * returns the description of species
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of species
     * @param description description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * returns the subId of species
     * @return subId
     */
    public String getSubId() {
        return subId;
    }

    /**
     * Sets the subId of species
     * @param subId subId
     */
    public void setSubId(String subId) {
        this.subId = subId;
        checkSubId();
    }

    /**
     * returns the raceTitle of species
     * @return raceTitle
     */
    public String getRaceTitle() {
        return raceTitle;
    }

    /**
     * Sets the raceTitle of species
     * @param raceTitle raceTitle
     */
    public void setRaceTitle(String raceTitle) {
        this.raceTitle = raceTitle;
    }

    /**
     * returns the raceDescription of species
     * @return raceDescription
     */
    public String getRaceDescription() {
        return raceDescription;
    }

    /**
     * Sets the raceDescription of species
     * @param raceDescription raceDescription
     */
    public void setRaceDescription(String raceDescription) {
        this.raceDescription = raceDescription;
    }

    /**
     * Sets the title of species
     * @param title title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * returns the flesh of species
     * @return flesh
     */
    public Flesh getFlesh() {
        return flesh;
    }

    /**
     * Sets the flesh of species
     * @param flesh flesh
     */
    public void setFlesh(Flesh flesh) {
        this.flesh = flesh;
    }

    /**
     * Returns the Grants hash map, to be used by Character.java
     * @return map of grant list for Species
     */
    @Override
    public ArrayList<Grant> getGrantList() {
        return grantList;
    }

    /**
     * Returns the stats array list, to be used by Character.java
     * @return array of stat list for Species
     */
    @Override
    public ArrayList<OtherStat> getStatList() {
        return statList;
    }

    @Override
    public void reCheck() {
        checkId();
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

    public Boolean hasFlesh() {
        try {
            return this.flesh != null;
        } catch (NullPointerException e) {
            return false;
        }
    }
}