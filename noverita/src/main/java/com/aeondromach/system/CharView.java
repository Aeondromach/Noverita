package com.aeondromach.system;

import javafx.scene.image.Image;

public class CharView {
    private String name;
    private final String ARCHETYPE;
    private final int RANK;
    private final String SQUAD;
    private final Image IMAGE;
    private final String FILEPATH;

    public CharView(String name, String archetype, String squad, int rank, Image image, String filePath) {
        this.name = name;
        this.ARCHETYPE = archetype;
        this.SQUAD = squad;
        this.RANK = rank;
        this.IMAGE = image;
        this.FILEPATH = filePath;
    }

    public CharView(String name, String squad, int rank, Image image, String filePath) {
        this.name = name;
        this.ARCHETYPE = null;
        this.SQUAD = squad;
        this.RANK = rank;
        this.IMAGE = image;
        this.FILEPATH = filePath;
    }


    public CharView(String name, String archetype, String squad, int rank, String filePath) {
        this.name = name;
        this.ARCHETYPE = archetype;
        this.SQUAD = squad;
        this.RANK = rank;
        this.IMAGE = new Image(getClass().getResource("/com/aeondromach/images/Test.png").toExternalForm());
        this.FILEPATH = filePath;
    }

    public CharView(String name, String squad, int rank, String filePath) {
        this.name = name;
        this.ARCHETYPE = null;
        this.SQUAD = squad;
        this.RANK = rank;
        this.IMAGE = new Image(getClass().getResource("/com/aeondromach/images/Test.png").toExternalForm());
        this.FILEPATH = filePath;
    }



    public String getName() {
        return this.name;
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

    public String getFILEPATH() {
        return this.FILEPATH;
    }

    public String getFileName() {
        int lastSlashIndex = this.FILEPATH.lastIndexOf("/") + 1;
        int extensionIndex = this.FILEPATH.lastIndexOf(".ncf");

        String fileName = this.FILEPATH.substring(lastSlashIndex, extensionIndex);
        fileName = fileName + ".ncf";
        return fileName;
    }

    public void setName(String setter) {
        this.name = setter;
    }
}