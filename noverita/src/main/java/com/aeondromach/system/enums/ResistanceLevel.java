/**
 * @author Evelyn Engleman @Aeondromach
 * @version 1
 * @since 03/08/2025
 * The 4 resistance levels in the Noverita system
 */

package com.aeondromach.system.enums;

public enum ResistanceLevel {
    IMMUNE,      // 0.0x damage
    RESISTANT,   // 0.5x damage
    NORMAL,      // 1.0x damage
    WEAKNESS;    // 2.0x damage

    /**
     * Return the chosen resistance level by getting value from xml parsing
     * @param value the value from xml
     * @return the chosen resistance level
     */
    public static ResistanceLevel getResistanceLevel(String value) {
        switch (value) {
            case "Immunity":
                return ResistanceLevel.IMMUNE;
            case "Resistance":
                return ResistanceLevel.RESISTANT;
            case "Normal":
                return ResistanceLevel.NORMAL;
            case "Weakness":
                return ResistanceLevel.WEAKNESS;
            default:
                return null;
        }
    }
}
