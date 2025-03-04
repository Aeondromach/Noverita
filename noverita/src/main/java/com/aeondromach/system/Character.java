package com.aeondromach.system;

import java.util.HashMap;
import java.util.Map;

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

    // Settings
    // private Settings settings;

    // Archetype
    // private Archetype arche;

    // Inventory & Equipment
    // private final Inventory INVENTORY;

    // Augments
    // private final AugmentList AUGMENTS;

    public Character(String filePath) {
        this.filePath = filePath;
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
    }

    /* ----------------- */
    /* File Path Get/Set */
    /* ----------------- */

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /* --------------------- */
    /* Personal Info Get/Set */
    /* --------------------- */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getEye() {
        return eye;
    }

    public void setEye(String eye) {
        this.eye = eye;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public String getHair() {
        return hair;
    }

    public void setHair(String hair) {
        this.hair = hair;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    /* ------------ */
    /* Stat Get/Set */
    /* ------------ */

    public int getBaseSTR() {
        return baseStats[0];
    }

    public void setBaseSTR(int base) {
        this.baseStats[0] = setBaseStat(base);
    }

    public int getBaseDEX() {
        return baseStats[1];
    }

    public void setBaseDEX(int base) {
        this.baseStats[1] = setBaseStat(base);
    }

    public int getBaseCON() {
        return baseStats[2];
    }

    public void setBaseCON(int base) {
        this.baseStats[2] = setBaseStat(base);
    }

    public int getBaseINT() {
        return baseStats[3];
    }

    public void setBaseINT(int base) {
        this.baseStats[3] = setBaseStat(base);
    }

    public int getBaseWIS() {
        return baseStats[4];
    }

    public void setBaseWIS(int base) {
        this.baseStats[4] = setBaseStat(base);
    }

    public int getBaseCHA() {
        return baseStats[5];
    }

    public void setBaseCHA(int base) {
        this.baseStats[5] = setBaseStat(base);
    }

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

    public int getCurrentPointCost() {
        int cost = 0;
        for (Integer stat: this.baseStats) {
            if (COSTS.containsKey(stat)) {
                cost += COSTS.get(stat);
            }
        }
        return cost;
    }


    public void setStatPoints() {
        this.statPoints = 27 - getCurrentPointCost();
    }

    public int getStatPoints() {
        return this.statPoints;
    }

    public int getFinalSTR() {
        finalStats[0] = baseStats[0]; // Also needs Species, ASI, Expertise/Mastery, Archetype, etc.
        return finalStats[0];
    }

    public int getFinalDEX() {
        finalStats[1] = baseStats[1]; // Also needs Species, ASI, Expertise/Mastery, Archetype, etc.
        return finalStats[1];
    }

    public int getFinalCON() {
        finalStats[2] = baseStats[2]; // Also needs Species, ASI, Expertise/Mastery, Archetype, etc.
        return finalStats[2];
    }

    public int getFinalINT() {
        finalStats[3] = baseStats[3]; // Also needs Species, ASI, Expertise/Mastery, Archetype, etc.
        return finalStats[3];
    }

    public int getFinalWIS() {
        finalStats[4] = baseStats[4]; // Also needs Species, ASI, Expertise/Mastery, Archetype, etc.
        return finalStats[4];
    }

    public int getFinalCHA() {
        finalStats[5] = baseStats[5]; // Also needs Species, ASI, Expertise/Mastery, Archetype, etc.
        return finalStats[5];
    }

    /* ---------------- */
    /* Personal Get/Set */
    /* ---------------- */

    public String getPersonality() {
        return personality;
    }

    public void setPersonality(String personality) {
        this.personality = personality;
    }

    public String getIdeal() {
        return ideal;
    }

    public void setIdeal(String ideal) {
        this.ideal = ideal;
    }

    public String getBond() {
        return bond;
    }

    public void setBond(String bond) {
        this.bond = bond;
    }

    public String getFlaw() {
        return flaw;
    }

    public void setFlaw(String flaw) {
        this.flaw = flaw;
    }

    public int getModifier(int stat) {
        return (int) Math.floor(stat / 2.0) - 5;
    }

    public String getModifierString(int stat) {
        int mod = (int) Math.floor(stat / 2.0) - 5;
        if (mod > 0) {
            return "+" + mod;
        }
        else {
            return "" + mod;
        }
    }    
}