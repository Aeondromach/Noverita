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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Character {
    private String filePath;

    // Personal Info
    private String name;
    private String gender;
    private int age;
    private String weight;
    private String eye;
    private String height;
    private String skin;
    private String hair;
    private String religion;

    // Stats
    private final Integer[] baseStats = new Integer[6];
    private final ArrayList<OtherStat> otherStats = new ArrayList<>();
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
    private String personality;
    private String ideal;
    private String bond;
    private String flaw;

    // Species
    private Species species;

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
            this.name = "Test Char";
            this.baseStats[0] = 10;
            this.baseStats[1] = 10;
            this.baseStats[2] = 10;
            this.baseStats[3] = 10;
            this.baseStats[4] = 10;
            this.baseStats[5] = 10;
            this.statPoints = 27;
            this.gender = "Male";
            this.age = 23;
            this.weight = "3kg, 223g";
            this.eye = "Red, black Sclera";
            this.height = "217cm";
            this.skin = "Pale white";
            this.hair = "Black, spiky with mohawk";
            this.religion = "Agnostic";
            this.personality = "Edgy, goth";
            this.ideal = "Believes short people are evil";
            this.bond = "Strives to be the best a person can be.";
            this.flaw = "Hates short people";
            this.species = new Species("ID_SPECIES_HUMAN", "ID_RACE_HUMAN_PURE");
        } catch (IOException e) {
            
        }
    }

    /* --------- */
    /* File Path */
    /* --------- */

    /**
     * Return the filepath of the character
     * @return filepath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Set the filepath of the character
     * @param filePath filepath
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /* ------------- */
    /* Personal Info */
    /* ------------- */

    /**
     * Return the name of the character
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the character
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the gender of the character
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Set the gender of the character
     * @param gender gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Return the age of the character
     * @return age
     */
    public int getAge() {
        return age;
    }

    /**
     * Set the age of the character
     * @param age age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Return the weight of the character
     * @return weight
     */
    public String getWeight() {
        return weight;
    }

    /**
     * Set the weight of the character
     * @param weight weight
     */
    public void setWeight(String weight) {
        this.weight = weight;
    }

    /**
     * Return the eyes of the character
     * @return eyes
     */
    public String getEye() {
        return eye;
    }

    /**
     * Set the eyes of the character
     * @param eye eyes
     */
    public void setEye(String eye) {
        this.eye = eye;
    }

    /**
     * Return the height of the character
     * @return height
     */
    public String getHeight() {
        return height;
    }

    /**
     * Set the height of the character
     * @param height height
     */
    public void setHeight(String height) {
        this.height = height;
    }

    /**
     * Return the skin of the character
     * @return skin
     */
    public String getSkin() {
        return skin;
    }

    /**
     * Set the skin of the character
     * @param skin skin
     */
    public void setSkin(String skin) {
        this.skin = skin;
    }

    /**
     * Return the hair of the character
     * @return hair
     */
    public String getHair() {
        return hair;
    }

    /**
     * Set the hair of the character
     * @param hair hair
     */
    public void setHair(String hair) {
        this.hair = hair;
    }

    /**
     * Return the religion of the character
     * @return religion
     */
    public String getReligion() {
        return religion;
    }

    /**
     * Set the religion of the character
     * @param religion religion
     */
    public void setReligion(String religion) {
        this.religion = religion;
    }

    /* ---- */
    /* Stat */
    /* ---- */

    /**
     * Return the strength of the character
     * @return strength
     */
    public int getBaseSTR() {
        return baseStats[0];
    }

    /**
     * Set the strength of the character
     * @param base strength
     */
    public void setBaseSTR(int base) {
        this.baseStats[0] = setBaseStat(base);
    }

    /**
     * Return the dexterity of the character
     * @return dexterity
     */
    public int getBaseDEX() {
        return baseStats[1];
    }

    /**
     * Set the dexterity of the character
     * @param base dexterity
     */
    public void setBaseDEX(int base) {
        this.baseStats[1] = setBaseStat(base);
    }

    /**
     * Return the constitution of the character
     * @return constitution
     */
    public int getBaseCON() {
        return baseStats[2];
    }

    /**
     * Set the constitution of the character
     * @param base constitution
     */
    public void setBaseCON(int base) {
        this.baseStats[2] = setBaseStat(base);
    }

    /**
     * Return the intelligence of the character
     * @return intelligence
     */
    public int getBaseINT() {
        return baseStats[3];
    }

    /**
     * Set the intelligence of the character
     * @param base intelligence
     */
    public void setBaseINT(int base) {
        this.baseStats[3] = setBaseStat(base);
    }

    /**
     * Return the wisdom of the character
     * @return wisdom
     */
    public int getBaseWIS() {
        return baseStats[4];
    }

    /**
     * Set the wisdom of the character
     * @param base wisdom
     */
    public void setBaseWIS(int base) {
        this.baseStats[4] = setBaseStat(base);
    }

    /**
     * Return the charisma of the character
     * @return charisma
     */
    public int getBaseCHA() {
        return baseStats[5];
    }

    /**
     * Set the charisma of the character
     * @param base charisma
     */
    public void setBaseCHA(int base) {
        this.baseStats[5] = setBaseStat(base);
    }

    /**
     * Set the base stats of the selected stat
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
     * Get the point cost of a stat
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
    public int getFinalSTR() {
        int finalMod = 0;
        for (OtherStat stat: otherStats) {
            finalMod += stat.getMod();
        }
        finalStats[0] = baseStats[0] + finalMod; // Also needs Species, ASI, Expertise/Mastery, Archetype, etc.
        return finalStats[0];
    }

    /**
     * Return the final dexterity after adding up all other bonuses
     * @return final dexterity
     */
    public int getFinalDEX() {
        int finalMod = 0;
        for (OtherStat stat: otherStats) {
            finalMod += stat.getMod();
        }
        finalStats[1] = baseStats[1] + finalMod; // Also needs Species, ASI, Expertise/Mastery, Archetype, etc.
        return finalStats[1];
    }

    /**
     * Return the final constitution after adding up all other bonuses
     * @return final constitution
     */
    public int getFinalCON() {
        int finalMod = 0;
        for (OtherStat stat: otherStats) {
            finalMod += stat.getMod();
        }
        finalStats[2] = baseStats[2] + finalMod; // Also needs Species, ASI, Expertise/Mastery, Archetype, etc.
        return finalStats[2];
    }

    /**
     * Return the final intelligence after adding up all other bonuses
     * @return final intelligence
     */
    public int getFinalINT() {
        int finalMod = 0;
        for (OtherStat stat: otherStats) {
            finalMod += stat.getMod();
        }
        finalStats[3] = baseStats[3] + finalMod; // Also needs Species, ASI, Expertise/Mastery, Archetype, etc.
        return finalStats[3];
    }

    /**
     * Return the final wisdom after adding up all other bonuses
     * @return final wisdom
     */
    public int getFinalWIS() {
        int finalMod = 0;
        for (OtherStat stat: otherStats) {
            finalMod += stat.getMod();
        }
        finalStats[4] = baseStats[4] + finalMod; // Also needs Species, ASI, Expertise/Mastery, Archetype, etc.
        return finalStats[4];
    }

    /**
     * Return the final charisma after adding up all other bonuses
     * @return final charisma
     */
    public int getFinalCHA() {
        int finalMod = 0;
        for (OtherStat stat: otherStats) {
            finalMod += stat.getMod();
        }
        finalStats[5] = baseStats[5] + finalMod; // Also needs Species, ASI, Expertise/Mastery, Archetype, etc.
        return finalStats[5];
    }

    /* -------- */
    /* Personal */
    /* -------- */

    /**
     * Return the personality of the character
     * @return personality
     */
    public String getPersonality() {
        return personality;
    }

    /**
     * Set the personality of the character
     * @param personality personality
     */
    public void setPersonality(String personality) {
        this.personality = personality;
    }

    /**
     * Return the ideal of the character
     * @return ideal
     */
    public String getIdeal() {
        return ideal;
    }

    /**
     * Set the ideal of the character
     * @param ideal ideal
     */
    public void setIdeal(String ideal) {
        this.ideal = ideal;
    }

    /**
     * Return the bond of the character
     * @return bond
     */
    public String getBond() {
        return bond;
    }

    /**
     * Set the bond of the character
     * @param bond bond
     */
    public void setBond(String bond) {
        this.bond = bond;
    }

    /**
     * Return the flaw of the character
     * @return flaw
     */
    public String getFlaw() {
        return flaw;
    }

    /**
     * Set the flaw of the character
     * @param flaw flaw
     */
    public void setFlaw(String flaw) {
        this.flaw = flaw;
    }

    /**
     * Return the modifier of the stat
     * @return modifier
     */
    public int getModifier(int stat) {
        return (int) Math.floor(stat / 2.0) - 5;
    }

    /**
     * Return the modifier of the stat (String)
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
    /* Species */
    /* ------- */

    /**
     * Return the species of the character
     * @return species
     */
    public Species getSpecies() {
        return this.species;
    }
}