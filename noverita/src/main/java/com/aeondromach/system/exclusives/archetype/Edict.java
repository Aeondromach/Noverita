package com.aeondromach.system.exclusives.archetype;

import org.jsoup.nodes.Document;

import com.aeondromach.system.IdClassList;
import com.aeondromach.system.abstracts.Exclusive;

public class Edict extends Exclusive {
    private final String id;
    private final String name;
    private final String description;
    private final EdictActivationType activationType;
    private final int cooldown;
    
    public enum EdictActivationType {
        ACTION,
        REACTION,
        PASSIVE;
    }
    
    public Edict(String id) {
        setMapSignifier(IdClassList.IdType.EDICT);

        this.id = id;
        this.name = "";
        this.description = "";
        this.activationType = EdictActivationType.ACTION;
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

    public EdictActivationType getActivationType() {
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
