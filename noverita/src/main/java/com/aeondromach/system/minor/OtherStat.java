/**
 * @author Evelyn Engleman @Aeondromach
 * @version 1
 * @since 03/08/2025
 * The otherstat object for Noverita
 * Collects stat buffs from other sources to add to character
 */

package com.aeondromach.system.minor;

import com.aeondromach.system.parsers.XmlParser;

public class OtherStat {
    private int stat;
    private int mod;
    private String source;

    /**
     * The constructor for otherstat
     * @param stat the stat to be modded
     * @param mod the modifier
     * @param source the source location
     */
    public OtherStat(int stat, int mod, String source) {
        this.stat = stat;
        this.mod = mod;
        this.source = source;
    }

    /**
     * The constructor for otherstat
     * @param stat the stat to be modded
     * @param mod the modifier
     * @param source the source location
     */
    public OtherStat(String statId, int mod, String source) {
        this.stat = XmlParser.readStatId(statId);
        this.mod = mod;
        if (this.mod > 0) this.source = source + " (+" + this.mod + ")";
        else this.source = source + " (" + this.mod + ")";
    }

    /**
     * Return the stat of otherstat
     * @return stat
     */
    public int getStat() {
        return stat;
    }

    /**
     * Set the stat of otherstat
     * @param stat stat
     */
    public void setStat(int stat) {
        this.stat = stat;
    }

    /**
     * Return the mod of otherstat
     * @return mod
     */
    public int getMod() {
        return mod;
    }

    /**
     * Set the mod of otherstat
     * @param mod mod
     */
    public void setMod(int mod) {
        this.mod = mod;
    }

    /**
     * Return the source of otherstat
     * @return source
     */
    public String getSource() {
        return source;
    }

    /**
     * Set the source of otherstat
     * @param source source
     */
    public void setSource(String source) {
        this.source = source;
    }
}
