package com.aeondromach.system.abstracts;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.aeondromach.system.IdClassList;
import com.aeondromach.system.minor.Grant;
import com.aeondromach.system.minor.OtherStat;
import com.aeondromach.system.parsers.XmlParser;

public abstract class Exclusive {
    public String id;

    protected ArrayList<Grant> grantList;
    protected ArrayList<OtherStat> statList;
    protected IdClassList.IdType mapSignifier;

    public IdClassList.IdType getMapSignifier() {
        return mapSignifier;
    }

    public void setMapSignifier(IdClassList.IdType mapSignifier) {
        this.mapSignifier = mapSignifier;
    }

     /**
     * returns the id of Exclusive Object
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id of Exclusive object
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
        checkId();
    }

    /**
     * Check if id is fit to read
     */
    protected void checkId() {
        if (this.id != null) {
            try {
                if (!grantList.isEmpty() || !this.grantList.isEmpty()) grantList.clear();
                if (!statList.isEmpty() || !this.statList.isEmpty()) statList.clear();
                parseXML(XmlParser.check(this.id, mapSignifier));
            } 
            catch (NullPointerException e) {
            }
        }
    }

    /**
     * Returns the Grants hash map, to be used by Character.java
     * @return map of grant list for Exclusive Object
     */
    public ArrayList<Grant> getGrantList() {
        return grantList;
    }

    /**
     * Returns the stats array list, to be used by Character.java
     * @return array of stat list for Exclusive Object
     */
    public ArrayList<OtherStat> getStatList() {
        return statList;
    }

    public void reCheck() {
        checkId();
    }

    public Boolean hasStatList() {
        try {
            return this.statList != null;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public Boolean hasGrantList() {
        try {
            return this.grantList != null;
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Parse and find the xml files to read
     * @param doc Jsoup document
     */
    protected abstract void parseXML(Document doc);
}