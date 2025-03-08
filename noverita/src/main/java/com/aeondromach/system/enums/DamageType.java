/**
 * @author Evelyn Engleman @Aeondromach
 * @version 1
 * @since 03/08/2025
 * The 15 damage types in the Noverita system
 */

package com.aeondromach.system.enums;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

public enum DamageType {
    IMPACT,
    PUNCTURE,
    SLASH,
    HEAT,
    COLD,
    ELECTRIC,
    CORROSION,
    IONIC,
    MIASMIC,
    TOXIC,
    BLIGHT,
    PSYCHIC,
    KINETIC,
    CHRONIC,
    VOID;

    private static final Map<DamageType, ResistanceLevel> resistances = new EnumMap<>(DamageType.class);

    /**
     * Sets the resistance of the given damage type for the session
     * @param type input the chosen damage type you wish to update
     * @param level input the resistance level you wish to update the damage type to
     */
    public static void setResistance(DamageType type, ResistanceLevel level) {
        resistances.put(type, level);
    }

    /**
     * Returns the resistance of a given damage type
     * @param type input which damage type you would like to recieve information for
     * @return returns the damage resistance
     */
    public static ResistanceLevel getResistance(DamageType type) {
        return resistances.getOrDefault(type, ResistanceLevel.NORMAL);
    }

    /**
     * Sets the resistances of a group of damage types, typically for the ID_DAMAGE_CATEGORY damage types
     * @param types insert damage category to change
     * @param level insert level to set the entire category to
     */
    public static void setResistances(EnumSet<DamageType> types, ResistanceLevel level) {
        for (DamageType type : types) {
            resistances.put(type, level); 
        }
    }

    /**
     * Returns the damage type from ID_DAMAGE_TYPE
     * @param id insert the id to match to the corresponding damage type
     * @return return the damage type
     */
    public static DamageType getDamageType(String id) {
        switch (id) {
            case "ID_DAMAGE_TYPE_IMPACT":
                return DamageType.IMPACT;
            case "ID_DAMAGE_TYPE_PUNCTURE":
                return DamageType.PUNCTURE;
            case "ID_DAMAGE_TYPE_SLASH":
                return DamageType.SLASH;
            case "ID_DAMAGE_TYPE_HEAT":
                return DamageType.HEAT;
            case "ID_DAMAGE_TYPE_COLD":
                return DamageType.COLD;
            case "ID_DAMAGE_TYPE_ELECTRIC":
                return DamageType.ELECTRIC;
            case "ID_DAMAGE_TYPE_CORROSION":
                return DamageType.CORROSION;
            case "ID_DAMAGE_TYPE_IONIC":
                return DamageType.IONIC;
            case "ID_DAMAGE_TYPE_TOXIC":
                return DamageType.TOXIC;
            case "ID_DAMAGE_TYPE_MIASMIC":
                return DamageType.MIASMIC;
            case "ID_DAMAGE_TYPE_BLIGHT":
                return DamageType.BLIGHT;
            case "ID_DAMAGE_TYPE_PSYCHIC":
                return DamageType.PSYCHIC;
            case "ID_DAMAGE_TYPE_KINETIC":
                return DamageType.KINETIC;
            case "ID_DAMAGE_TYPE_CHRONIC":
                return DamageType.CHRONIC;
            case "ID_DAMAGE_TYPE_VOID":
                return DamageType.VOID;
            default:
                return null;
        }
    }

    /**
     * Returns the damage type from ID_DAMAGE_CATEGORY
     * @param id insert the id to match to the corresponding damage category
     * @return return the damage category
     */
    public static EnumSet<DamageType> getDamageCategory(String id) {
        EnumSet<DamageType> category;
        switch (id) {
            case "ID_DAMAGE_CATEGORY_PHYSICAL":
                category = EnumSet.of(DamageType.SLASH, DamageType.PUNCTURE, DamageType.IMPACT);
                return category;
            case "ID_DAMAGE_CATEGORY_ELEMENTAL":
                category = EnumSet.of(DamageType.HEAT, DamageType.COLD, DamageType.CORROSION, DamageType.ELECTRIC, DamageType.IONIC);
                return category;
            case "ID_DAMAGE_CATEGORY_DISEASE":
                category = EnumSet.of(DamageType.TOXIC, DamageType.MIASMIC, DamageType.BLIGHT);
                return category;
            case "ID_DAMAGE_CATEGORY_TEMPORAL":
                category = EnumSet.of(DamageType.PSYCHIC, DamageType.KINETIC, DamageType.CHRONIC, DamageType.VOID);
                return category;
            case "ID_DAMAGE_CATEGORY_ALL":
                category = EnumSet.allOf(DamageType.class);
                return category;
            default:
                return null;
        }
    }
}