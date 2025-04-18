/**
 * @author Evelyn Engleman @Aeondromach
 * @version 1
 * @since 03/08/2025
 * The lighter, more display focused objects for characters
 */

package com.aeondromach.system;

import javafx.scene.image.Image;

public class CharView {
    private String name;
    private final String ARCHETYPE;
    private final int RANK;
    private final String SQUAD;
    private final Image IMAGE;
    private final String FILEPATH;

    /**
     * Constructor for CharView
     * @param name name of the character
     * @param archetype archetype of the character
     * @param squad squad of the character
     * @param rank rank of the character
     * @param image image of the character
     * @param filePath filepath of the character
     */
    public CharView(String name, String archetype, String squad, int rank, Image image, String filePath) {
        this.name = name;
        
        if (archetype != null) this.ARCHETYPE = archetype;
        else this.ARCHETYPE = "Typical";

        this.SQUAD = squad;
        this.RANK = rank;
        
        if (image != null) this.IMAGE = image;
        else this.IMAGE = new Image(getClass().getResourceAsStream("/images/Test.png"));

        this.FILEPATH = filePath;
    }

    /**
     * Returns the name of the character
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the archetype of the character
     * @return archetype
     */
    public String getArche() {
        return this.ARCHETYPE;
    }

    /**
     * Returns the squad of the character
     * @return squad
     */
    public String getSquad() {
        return this.SQUAD;
    }

    /**
     * Returns the rank of the character
     * @return rank
     */
    public int getRank() {
        return this.RANK;
    }

    /**
     * Returns the image of the character
     * @return image
     */
    public Image getImage() {
        return this.IMAGE;
    }

    /**
     * Returns the filepath of the character
     * @return filepath
     */
    public String getFILEPATH() {
        return this.FILEPATH;
    }

    /**
     * Returns the filename of the character
     * @return filename
     */
    public String getFileName() {
        int lastSlashIndex = this.FILEPATH.lastIndexOf("/") + 1;
        int extensionIndex = this.FILEPATH.lastIndexOf(".ncf");

        String fileName = this.FILEPATH.substring(lastSlashIndex, extensionIndex);
        fileName = fileName + ".ncf";
        return fileName;
    }

    /**
     * Sets the name of the character
     * @param setter name
     */
    public void setName(String setter) {
        this.name = setter;
    }
}