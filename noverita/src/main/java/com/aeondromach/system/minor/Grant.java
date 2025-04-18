package com.aeondromach.system.minor;

public class Grant {
    private String id;
    private String type;
    private String bonus;
    private Integer value;

    public Grant (String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Boolean hasBonus() {
        return bonus != null;
    }

    public Boolean hasValue() {
        return value != null;
    }
}
