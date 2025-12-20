package com.aeondromach.system.exclusives.archetype;

import org.jsoup.nodes.Document;

import com.aeondromach.system.IdClassList;
import com.aeondromach.system.abstracts.Exclusive;

public class Ability extends Exclusive {
    private final String id;
    private final String name;
    private final String description;
    private final AbilityActivationType activationType;
    private final int cooldown;
    
    public enum AbilityActivationType {
        ACTION,
        BONUS_ACTION,
        REACTION;
    }
    
    public Ability(String id) {
        setMapSignifier(IdClassList.IdType.ABILITY);

        this.id = id;
        this.name = "";
        this.description = "";
        this.activationType = AbilityActivationType.ACTION;
        this.cooldown = 0;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public AbilityActivationType getActivationType() {
        return activationType;
    }

    @Override
    protected void resetUniqueStats() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resetUniqueStats'");
    }

    @Override
    protected void parseXML(Document doc) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parseXML'");
    }
}
