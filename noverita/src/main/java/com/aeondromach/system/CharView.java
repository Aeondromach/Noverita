package com.aeondromach.system;

import javafx.scene.image.Image;

public class CharView {
    private final String NAME;
    private final String ARCHETYPE;
    private final int RANK;
    private final String SQUAD;
    private final Image IMAGE;

    public CharView(String name, String archetype, String squad, int rank, Image image) {
        this.NAME = name;
        this.ARCHETYPE = archetype;
        this.SQUAD = squad;
        this.RANK = rank;
        this.IMAGE = image;
    }

    public String getName() {
        return this.NAME;
    }

    public String getArche() {
        return this.ARCHETYPE;
    }

    public String getSquad() {
        return this.SQUAD;
    }

    public int getRank() {
        return this.RANK;
    }

    public Image getImage() {
        return this.IMAGE;
    }
}