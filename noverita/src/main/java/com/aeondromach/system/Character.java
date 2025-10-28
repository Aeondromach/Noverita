/**
 * @author Evelyn Engleman @Aeondromach
 * @version 1
 * @since 03/08/2025
 * The character object for Noverita
 */

package com.aeondromach.system;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.aeondromach.Messages;
import com.aeondromach.system.exclusives.form.Form;
import com.aeondromach.system.minor.Grant;
import com.aeondromach.system.minor.OtherStat;
import com.aeondromach.system.parsers.XmlParser;

import javafx.scene.image.Image;

public class Character {
    private String filePath;
    private Image image;

    private int rank;
    private String squad;

    // Appearance
    private String name;
    private String gender;
    private int age;
    private String weight;
    private String eyes;
    private String height;
    private String skin;
    private String hair;

    // Stats
    private final Integer[] baseStats = new Integer[6];
    private final ArrayList<OtherStat> otherStats = new ArrayList<>();
    private final ArrayList<Grant> grantList = new ArrayList<>();
    private final Integer[] finalStats = new Integer[6];

    private int statPoints;
    private static final Map<Integer, Integer> COSTS = new HashMap<>();

    static {
        COSTS.put(8, 0);
        COSTS.put(9, 1);
        COSTS.put(10, 2);
        COSTS.put(11, 3);
        COSTS.put(12, 4);
        COSTS.put(13, 5);
        COSTS.put(14, 7);
        COSTS.put(15, 9);
    }

    // Personality
    private String religion;
    private String personality;
    private String ideals;
    private String bonds;
    private String flaws;

    // Form
    private Form form;

    // Settings
    // private Perset settings;

    // Archetype
    // private Archetype arche;

    // Inventory & Equipment
    // private final Inventory INVENTORY;

    // Augments
    // private final AugmentList AUGMENTS;

    /**
     * The constructor for the character
     * @param filePath the character's filepath to be read
     */
    public Character(String filePath) {
        try {
            this.filePath = filePath;
            Document doc = Jsoup.parse(Files.readString(Paths.get(filePath)));
            Element character = doc.selectFirst("character");
                Element information = character.selectFirst("information");
                Element inclusiveTag = character.selectFirst("inclusive");
                    Element stats = inclusiveTag.selectFirst("stats");
                    Element appearance = inclusiveTag.selectFirst("appearance");
                    Element beliefs = inclusiveTag.selectFirst("beliefs");
                     
            this.name = safeText(information, "name", "Vagrant");
            this.rank = safeInt(information, "rank");
            this.squad = safeText(information, "squad");
            Element charPortrait = information.selectFirst("charportrait");
            this.form = new Form(safeId(information, "form"), safeId(information, "aspect"));
            this.image = XmlParser.findImage(charPortrait);

            String[] statValues = stats.selectFirst("stats").ownText().split(",");
            for (int i = 0; i < statValues.length && i < 6; i++) {
                this.baseStats[i] = Integer.parseInt(statValues[i].trim());
            }
            this.statPoints = 27;

            this.gender = safeText(appearance, "gender");
            this.age = safeInt(appearance, "age");
            this.weight = safeText(appearance, "weight");
            this.eyes = safeText(appearance, "eyes");
            this.height = safeText(appearance, "height");
            this.skin = safeText(appearance, "skin");
            this.hair = safeText(appearance, "hair");

            this.religion = safeText(beliefs, "religion");
            this.personality = safeText(beliefs, "personality");
            this.ideals = safeText(beliefs, "ideals");
            this.bonds = safeText(beliefs, "bonds");
            this.flaws = safeText(beliefs, "flaws");

            runSetOtherStats();
        } catch (Exception e) {
        }
        
    }

    public static String generateDefaultName() {
        String[] titles = {
            "", "", "", "Roaming", "Strayed",
            "Lost", "Errant", "Forsaken", "Unbound", "Tetherless",
            "", "Journeyed", "Drifting", "", "Far-Flung"
        };
        String[] names = {
            "Vagrant", "Wanderer", "Hiker", "Drifter", "Peregrine",
            "Explorer", "Seeker", "Pilgrim", "Adventurer", "Wayfarer",
            "Nomad", "Rover", "Voyager", "Strider", "Outrider"
        };
        String[] adjectives = {
            "Lonely", "Restless", "Curious", "Bold", "Weary",
            "Determined", "Feral", "Silent", "Haunted", "Wistful",
            "Resolute", "Stoic", "Fleeting", "Wild", "Aimless"
        };
        String[] suffixes = {
            "the Wilds", "Open Road", "", "the Unknown", "the Beyond",
            "Nowhere", "Forgotten Paths", "", "the Lost Lands", "the Crossroads",
            "Dust", "Dreams", "Kahndel", "Candelva", "No Name"
        };
        Random rand = new Random();
        int nameRoll = rand.nextInt(1, 5);
        int firstRoll = rand.nextInt(0, 15);
        int secondRoll = rand.nextInt(0, 15);

        switch (nameRoll) {
            case 1:
                return (titles[firstRoll] + " " + names[secondRoll]);
            case 2:
                return adjectives[firstRoll] + " " + names[secondRoll];
            case 3:
                return names[firstRoll] + " " + suffixes[secondRoll];
            case 4:
                return adjectives[firstRoll] + " " + names[secondRoll] + " of " + suffixes[rand.nextInt(0, 15)];
            case 5:
                return titles[firstRoll] + " " + names[secondRoll] + " of " + suffixes[rand.nextInt(0, 15)];
        }
        return "Vagrant";
    }

    public static void generateAscendantTitle() {

    }

    private static String safeText(Element parent, String selector) {
        if (parent == null) return "";
        Element child = parent.selectFirst(selector);
        return (child != null && !child.ownText().trim().isEmpty()) 
            ? child.ownText().trim() 
            : "";
    }

    private static String safeId(Element parent, String selector) {
        if (parent == null) return "";
        Element child = parent.selectFirst(selector);
        return (child != null && !child.attr("id").isEmpty()) 
            ? child.attr("id")
            : "";
    }

    private static String safeText(Element parent, String selector, String defaultValue) {
        if (parent == null) return defaultValue;
        Element child = parent.selectFirst(selector);
        return (child != null && !child.ownText().trim().isEmpty()) 
            ? child.ownText().trim() 
            : defaultValue;
    }

    private static int safeInt(Element parent, String selector) {
        try {
            return Integer.parseInt(safeText(parent, selector));
        } catch (Exception e) {
            return 0;
        }
    }

    /* --------- */
    /* File Path */
    /* --------- */

    /**
     * Return the filepath the character
     * @return filepath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Set the filepath the character
     * @param filePath filepath
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /* ------------- */
    /* Personal Info */
    /* ------------- */

    /**
     * Return the name the character
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name the character
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the gender the character
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Set the gender the character
     * @param gender gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Return the age the character
     * @return age
     */
    public int getAge() {
        return age;
    }

    /**
     * Set the age the character
     * @param age age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Return the weight the character
     * @return weight
     */
    public String getWeight() {
        return weight;
    }

    /**
     * Set the weight the character
     * @param weight weight
     */
    public void setWeight(String weight) {
        this.weight = weight;
    }

    /**
     * Return the eyes the character
     * @return eyes
     */
    public String getEyes() {
        return eyes;
    }

    /**
     * Set the eyes the character
     * @param eye eyes
     */
    public void setEyes(String eye) {
        this.eyes = eye;
    }

    /**
     * Return the height the character
     * @return height
     */
    public String getHeight() {
        return height;
    }

    /**
     * Set the height the character
     * @param height height
     */
    public void setHeight(String height) {
        this.height = height;
    }

    /**
     * Return the skin the character
     * @return skin
     */
    public String getSkin() {
        return skin;
    }

    /**
     * Set the skin the character
     * @param skin skin
     */
    public void setSkin(String skin) {
        this.skin = skin;
    }

    /**
     * Return the hair the character
     * @return hair
     */
    public String getHair() {
        return hair;
    }

    /**
     * Set the hair the character
     * @param hair hair
     */
    public void setHair(String hair) {
        this.hair = hair;
    }

    /**
     * Return the religion the character
     * @return religion
     */
    public String getReligion() {
        return religion;
    }

    /**
     * Set the religion the character
     * @param religion religion
     */
    public void setReligion(String religion) {
        this.religion = religion;
    }

    /* ---- */
    /* Stat */
    /* ---- */

    /**
     * Return the strength the character
     * @return strength
     */
    public int getBaseStat(int index) {
        return baseStats[index];
    }

    /**
     * Set the strength the character
     * @param base strength
     */
    public void setBaseStat(int base, int index) {
        if (index >= 0 && index <= 5) this.baseStats[index] = setBaseStat(base);
    }

    /**
     * Set the base stats the selected stat
     * @param stat base stats
     * @return adjusted stat
     */
    private int setBaseStat(int stat) {
        if (stat > 15) {
            return 15;
        }
        else if (stat < 1) {
            return 1;
        }
        else {
            return stat;
        }
    }

    /**
     * Get the point cost a stat
     * @param test How much is being added
     * @param index which stat is being charged
     * @return the cost
     */
    public int getPointCost(int test, int index) {
        this.baseStats[index] += test;
        int cost = 0;
        for (Integer stat: this.baseStats) {
            if (COSTS.containsKey(stat)) {
                cost += COSTS.get(stat);
            }
        }
        this.baseStats[index] -= test;
        return cost;
    }

    /**
     * Returns the current point cost for all stats (27/27)
     * @return current point cost
     */
    public int getCurrentPointCost() {
        int cost = 0;
        for (Integer stat: this.baseStats) {
            if (COSTS.containsKey(stat)) {
                cost += COSTS.get(stat);
            }
        }
        return cost;
    }


    /**
     * Set the current stat points
     */
    public void setStatPoints() {
        this.statPoints = 27 - getCurrentPointCost();
    }

    /**
     * Return the current stat points
     * @return stat points
     */
    public int getStatPoints() {
        return this.statPoints;
    }

    /**
     * Return the final strength after adding up all other bonuses
     * @return final strength
     */
    public int getFinalStat(int index) {
        int finalMod = setFinalMod(index);
        finalStats[index] = baseStats[index] + finalMod; // Also needs Form, ASI, Expertise/Mastery, Archetype, etc.
        return finalStats[index];
    }

    /* -------- */
    /* Personal */
    /* -------- */

    /**
     * Return the personality the character
     * @return personality
     */
    public String getPersonality() {
        return personality;
    }

    /**
     * Set the personality the character
     * @param personality personality
     */
    public void setPersonality(String personality) {
        this.personality = personality;
    }

    /**
     * Return the ideal the character
     * @return ideal
     */
    public String getIdeals() {
        return ideals;
    }

    /**
     * Set the ideal the character
     * @param ideal ideal
     */
    public void setIdeals(String ideal) {
        this.ideals = ideal;
    }

    /**
     * Return the bond the character
     * @return bond
     */
    public String getBonds() {
        return bonds;
    }

    /**
     * Set the bond the character
     * @param bond bond
     */
    public void setBonds(String bond) {
        this.bonds = bond;
    }

    /**
     * Return the flaw the character
     * @return flaw
     */
    public String getFlaws() {
        return flaws;
    }

    /**
     * Set the flaw the character
     * @param flaw flaw
     */
    public void setFlaws(String flaw) {
        this.flaws = flaw;
    }

    /**
     * Return the modifier the stat
     * @return modifier
     */
    public int getModifier(int stat) {
        return (int) Math.floor(stat / 2.0) - 5;
    }

    /**
     * Return the modifier the stat (String)
     * @return modifier (String)
     */
    public String getModifierString(int stat) {
        int mod = (int) Math.floor(stat / 2.0) - 5;
        if (mod > 0) {
            return "+" + mod;
        }
        else {
            return "" + mod;
        }
    }

    /* ------- */
    /* Form */
    /* ------- */

    /**
     * Return the form the character
     * @return form
     */
    public Form getForm() {
        return this.form;
    }

    public void setGrants() {
        if (this.grantList != null || !this.grantList.isEmpty()) grantList.clear();
        if (this.form.hasGrantList()) for (Grant grant: this.form.getGrantList()) this.grantList.add(grant);
        if (this.form.hasFlesh() && this.form.getFLESH().hasGrantList()) for (Grant grant: this.form.getFLESH().getGrantList()) this.grantList.add(grant);

        for (Grant grant: this.grantList) {
            switch (grant.getType()) {
                case "":
                    
                    break;
                default:
                    break; //Put error message here
            }
        }
    }

    private int setFinalMod(int index) {
        int finalMod = 0;

        for (OtherStat stat: otherStats) {
            if (stat.getStat() == index) finalMod += stat.getMod();
            else if (stat.getStat() == 8) finalMod += stat.getMod();
        }

        return finalMod;
    }

    public void setOtherStats() {
        if (this.otherStats != null || !this.otherStats.isEmpty()) otherStats.clear();
        if (this.form.hasStatList()) for (OtherStat formStat: this.form.getStatList()) this.otherStats.add(formStat);
        if (this.form.hasFlesh() && this.form.getFLESH().hasStatList()) for (OtherStat fleshStat: this.form.getFLESH().getStatList()) this.otherStats.add(fleshStat);
    }

    private void runSetOtherStats() {
        setOtherStats();
    }

    public ArrayList<OtherStat> getOtherStats() {
        return this.otherStats;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Boolean hasForm() {
        return (this.form.getId() != null && this.form.getTitle() != null && this.form.getDescription() != null);
    }

    public String getSquad() {
        return this.squad;
    }
}