/**
 * @author Evelyn Engleman @Aeondromach
 * @version 1
 * @since 03/08/2025
 * The species object for Noverita
 */

package com.aeondromach.system;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Species {
    private String id;
    private String title;
    private String description;

    private Flesh flesh;

    private String subId;
    private String raceTitle;
    private String raceDescription;

    /**
     * The constructor for species
     * @param id the species id
     * @param subId the race id
     */
    public Species(String id, String subId) {
        this.id = id;
        this.subId = subId;

        checkIfId();
    }

    /**
     * Check if species id is fit to read
     */
    private void checkIfId() {
        if (id != null) {
            try {
                parseXML(XmlParser.check(id, "SPECIES"));
                checkIfSubId();
            } 
            catch (NullPointerException e) {
            }
        }
    }

    /**
     * Check if race id is fit to read
     */
    private void checkIfSubId() {
        if (subId != null) {
            try {
                parseXML(XmlParser.check(subId, "RACE"));
            } 
            catch (NullPointerException e) {
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
            if (element.attr("id").equals(this.id) && element.attr("type").equals("Species") && !element.attr("name").isEmpty() && !element.select("description").isEmpty()) {
                Element descriptionXML = element.select("description").first();

                setTitle(element.attr("name"));
                setDescription(descriptionXML.html().trim());

                Element exclusive = element.selectFirst("exclusive");
                Elements grants = exclusive.select("grant");
                for (Element grant: grants) {
                    if (grant.attr("type").equals("Flesh")) {
                        setFlesh(new Flesh(grant.attr("id")));
                    }
                }
            }
            else if (element.attr("id").equals(this.subId) && element.attr("type").equals("Race") && !element.attr("name").isEmpty() && !element.select("description").isEmpty()) {
                Element descriptionXML = element.select("description").first();

                setRaceTitle(element.attr("name"));
                setRaceDescription(descriptionXML.html().trim());
            }
        }
    }

    /**
     * returns the id of species
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id of species
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
        checkIfId();
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
        checkIfSubId();
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
}