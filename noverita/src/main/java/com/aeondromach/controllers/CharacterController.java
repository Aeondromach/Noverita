/**
 * @author Evelyn Engleman @Aeondromach
 * @version 1
 * @since 12/11/2024
 * Main controller for Character page
 */

package com.aeondromach.controllers;

import org.jsoup.nodes.Element;

import com.aeondromach.constructors.Table;
import com.aeondromach.system.IdClassList;
import com.aeondromach.system.parsers.HtmlParser;
import com.aeondromach.system.parsers.XmlParser;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CharacterController {
    private NovController nov;

    @FXML private TextField tfStr;
    @FXML private TextField tfDex;
    @FXML private TextField tfCon;
    @FXML private TextField tfInt;
    @FXML private TextField tfWis;
    @FXML private TextField tfCha;

    @FXML private Text tStr;
    @FXML private Text tDex;
    @FXML private Text tCon;
    @FXML private Text tInt;
    @FXML private Text tWis;
    @FXML private Text tCha;

    @FXML private TabPane character;

    @FXML private Text speciesTitle;

    @FXML private Text textPoints;

    @FXML private VBox speciesSidePane;
    @FXML private VBox speciesSidePaneV;

    @FXML private ScrollPane speciesScroll;
    @FXML private VBox speciesVbox;
    @FXML private VBox speciesSideBarHtml;

    private Table speciesTable;
    private Table raceTable;

    /**
     * Takes instantiated Novcontroller and links self to it.
     * @param nov Sets instance of Novcontroller
     */
    public void init(NovController nov) {
        this.nov = nov;
    }

    /**
     * Initialize the FXML project, setting up all features for the character tab
     */
    @FXML protected void initialize() {
        Platform.runLater(() -> {
            Stage stage = (Stage) character.getScene().getWindow();
            character.prefWidthProperty().bind(stage.widthProperty().subtract(.001));
        });
    }

    public void setCharTab() {
        setTf(tfStr);
        setTf(tfDex);
        setTf(tfCon);
        setTf(tfInt);
        setTf(tfWis);
        setTf(tfCha);

        setTables();
    }

    public void setTables() {
        setSpeciesTable();
    }

    private void setSpeciesTable() {
        speciesTable = new Table(IdClassList.getIdMap("SPECIES"), "species", "Species", this::speciesClick, this::speciesDualClick);
        VBox speciesTableBox = speciesTable.setTable();
        speciesTableBox.prefWidthProperty().bind(speciesVbox.widthProperty().subtract(10));
        speciesVbox.getChildren().add(speciesTableBox);
    }

    private void speciesClick(String id) {
        Element element = XmlParser.getElement(IdClassList.getIdMap("SPECIES"), id);
        speciesTitle.setText(element.attr("name"));
        speciesSidePaneV.getChildren().add(HtmlParser.parseHtml(id, IdClassList.getIdMap("SPECIES")));
    }

    private void speciesDualClick(String id) {
        System.out.println(id + " | " + id);
    }

    /**
     * Set the text value for the text fields (Number values that display something like "10 (0)")
     * @param tf Which text field to set
     */
    private void setTf(TextField tf) {
        tf.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    /**
     * Handles the increment up button in the character stat subTab
     * @param event recieves mouse input
     */
    @FXML protected void handleIncrementUp(MouseEvent event) {
        com.aeondromach.system.Character curChar = nov.getCharacter();

        if(event.getButton().equals(MouseButton.PRIMARY)){
            Button sourceButton = (Button) event.getSource();
    
            switch (sourceButton.getId()) {
                case "btStrIncUp":
                    if (curChar.getStatPoints() != 0 && 27 >= curChar.getPointCost(1, 0)) {
                        curChar.setBaseStat(curChar.getBaseStat(0) + 1, 0);
                        tStr.setText(curChar.getFinalStat(0) + " (" + curChar.getModifierString(curChar.getFinalStat(0)) + ")");
                        tfStr.setPromptText(String.valueOf(curChar.getBaseStat(0)));
                    }
                    break;
                case "btDexIncUp":
                    if (curChar.getStatPoints() != 0 && 27 >= curChar.getPointCost(1, 1)) {
                        curChar.setBaseStat(curChar.getBaseStat(1) + 1, 1);
                        tDex.setText(curChar.getFinalStat(1) + " (" + curChar.getModifierString(curChar.getFinalStat(1)) + ")");
                        tfDex.setPromptText(String.valueOf(curChar.getBaseStat(1)));
                    }                    
                    break;
                case "btConIncUp":
                    if (curChar.getStatPoints() != 0 && 27 >= curChar.getPointCost(1, 2)) {
                        curChar.setBaseStat(curChar.getBaseStat(2) + 1, 2);
                        tCon.setText(curChar.getFinalStat(2) + " (" + curChar.getModifierString(curChar.getFinalStat(2)) + ")");
                        tfCon.setPromptText(String.valueOf(curChar.getBaseStat(2)));
                    }                    
                    break;
                case "btIntIncUp":
                    if (curChar.getStatPoints() != 0 && 27 >= curChar.getPointCost(1, 3)) {
                        curChar.setBaseStat(curChar.getBaseStat(3) + 1, 3);
                        tInt.setText(curChar.getFinalStat(3) + " (" + curChar.getModifierString(curChar.getFinalStat(3)) + ")");
                        tfInt.setPromptText(String.valueOf(curChar.getBaseStat(3)));
                    }                    
                    break;
                case "btWisIncUp":
                    if (curChar.getStatPoints() != 0 && 27 >= curChar.getPointCost(1, 4)) {
                        curChar.setBaseStat(curChar.getBaseStat(4) + 1, 4);
                        tWis.setText(curChar.getFinalStat(4) + " (" + curChar.getModifierString(curChar.getFinalStat(4)) + ")");
                        tfWis.setPromptText(String.valueOf(curChar.getBaseStat(4)));
                    }                    
                    break;
                case "btChaIncUp":
                    if (curChar.getStatPoints() != 0 && 27 >= curChar.getPointCost(1, 5)) {
                        curChar.setBaseStat(curChar.getBaseStat(5) + 1, 5);
                        tCha.setText(curChar.getFinalStat(5) + " (" + curChar.getModifierString(curChar.getFinalStat(5)) + ")");
                        tfCha.setPromptText(String.valueOf(curChar.getBaseStat(5)));
                    }                    
                    break;
            }
            curChar.setStatPoints();
            textPoints.setText(curChar.getStatPoints() + "/27");
        }
    }

    /**
     * Handles the increment down button in the character stat subTab
     * @param event recieves mouse input
     */
    @FXML protected void handleIncrementDown(MouseEvent event) {
        com.aeondromach.system.Character curChar = nov.getCharacter();

        if(event.getButton().equals(MouseButton.PRIMARY)){
            Button sourceButton = (Button) event.getSource();
    
            switch (sourceButton.getId()) {
                case "btStrIncDown":
                    curChar.setBaseStat(curChar.getBaseStat(0) - 1, 0);
                    tStr.setText(curChar.getFinalStat(0) + " (" + curChar.getModifierString(curChar.getFinalStat(0)) + ")");
                    tfStr.setPromptText(String.valueOf(curChar.getBaseStat(0)));
                    break;
                case "btDexIncDown":
                    curChar.setBaseStat(curChar.getBaseStat(1) - 1, 1);
                    tDex.setText(curChar.getFinalStat(1) + " (" + curChar.getModifierString(curChar.getFinalStat(1)) + ")");
                    tfDex.setPromptText(String.valueOf(curChar.getBaseStat(1)));
                    break;
                case "btConIncDown":
                    curChar.setBaseStat(curChar.getBaseStat(2) - 1, 2);
                    tCon.setText(curChar.getFinalStat(2) + " (" + curChar.getModifierString(curChar.getFinalStat(2)) + ")");
                    tfCon.setPromptText(String.valueOf(curChar.getBaseStat(2)));
                    break;
                case "btIntIncDown":
                    curChar.setBaseStat(curChar.getBaseStat(3) - 1, 3);
                    tInt.setText(curChar.getFinalStat(3) + " (" + curChar.getModifierString(curChar.getFinalStat(3)) + ")");
                    tfInt.setPromptText(String.valueOf(curChar.getBaseStat(3)));
                    break;
                case "btWisIncDown":
                    curChar.setBaseStat(curChar.getBaseStat(4) - 1, 4);
                    tWis.setText(curChar.getFinalStat(4) + " (" + curChar.getModifierString(curChar.getFinalStat(4)) + ")");
                    tfWis.setPromptText(String.valueOf(curChar.getBaseStat(4)));
                    break;
                case "btChaIncDown":
                    curChar.setBaseStat(curChar.getBaseStat(5) - 1, 5);
                    tCha.setText(curChar.getFinalStat(5) + " (" + curChar.getModifierString(curChar.getFinalStat(5)) + ")");
                    tfCha.setPromptText(String.valueOf(curChar.getBaseStat(5)));
                    break;
            }
            curChar.setStatPoints();
            textPoints.setText(curChar.getStatPoints() + "/27");
        }
    }

    /**
     * Handles the input in the character stat subTab
     * @param event recieves action/key input
     */
    @FXML protected void handleStatInput(ActionEvent event) {
        com.aeondromach.system.Character curChar = nov.getCharacter();
        TextField sourceTextField = (TextField) event.getSource();

        switch (sourceTextField.getId()) {
            case "tfStr":
                textFieldInput(tfStr, tStr, curChar);
                break;
            case "tfDex":
                textFieldInput(tfDex, tDex, curChar);
                break;
            case "tfCon":
                textFieldInput(tfCon, tCon, curChar);
                break;
            case "tfInt":
                textFieldInput(tfInt, tInt, curChar);
                break;
            case "tfWis":
                textFieldInput(tfWis, tWis, curChar);
                break;
            case "tfCha":
                textFieldInput(tfCha, tCha, curChar);
                break;
        }
        curChar.setStatPoints();
        textPoints.setText(curChar.getStatPoints() + "/27");
    }

    /**
     * Changes the text field input for all text fields in the stat character subTab
     * @param tf which textfield to change
     * @param character the current character
     */
    private void textFieldInput(TextField tf, Text t, com.aeondromach.system.Character character) {
        if (!tf.getText().equals("")) {
            Integer input = Integer.valueOf(tf.getText());
            if (input > 15) {
                input = 15;
            }
            else if (input < 1) {
                input = 1;
            }

            switch (tf.getId()) {
                case "tfStr":
                    if (character.getStatPoints() != 0 && 27 >= character.getPointCost(input - character.getBaseStat(0), 0)) {
                        character.setBaseStat(Integer.parseInt(tf.getText()), 0);
                        tf.setPromptText(String.valueOf(character.getBaseStat(0)));
                        t.setText(character.getFinalStat(0) + " (" + character.getModifierString(character.getFinalStat(0)) + ")");
                    }
                    break;
                case "tfDex":
                    if (character.getStatPoints() != 0 && 27 >= character.getPointCost(input - character.getBaseStat(1), 1)) {
                        character.setBaseStat(Integer.parseInt(tf.getText()), 1);
                        tf.setPromptText(String.valueOf(character.getBaseStat(1)));
                        t.setText(character.getFinalStat(1) + " (" + character.getModifierString(character.getFinalStat(1)) + ")");
                    }
                    break;
                case "tfCon":
                    if (character.getStatPoints() != 0 && 27 >= character.getPointCost(input - character.getBaseStat(2), 2)) {
                        character.setBaseStat(Integer.parseInt(tf.getText()), 2);
                        tf.setPromptText(String.valueOf(character.getBaseStat(2)));
                        t.setText(character.getFinalStat(2) + " (" + character.getModifierString(character.getFinalStat(2)) + ")");
                    }
                    break;
                case "tfInt":
                    if (character.getStatPoints() != 0 && 27 >= character.getPointCost(input - character.getBaseStat(3), 3)) {
                        character.setBaseStat(Integer.parseInt(tf.getText()), 3);
                        tf.setPromptText(String.valueOf(character.getBaseStat(3)));
                        t.setText(character.getFinalStat(3) + " (" + character.getModifierString(character.getFinalStat(3)) + ")");
                    }
                    break;
                case "tfWis":
                    if (character.getStatPoints() != 0 && 27 >= character.getPointCost(input - character.getBaseStat(4), 4)) {
                        character.setBaseStat(Integer.parseInt(tf.getText()), 4);
                        tf.setPromptText(String.valueOf(character.getBaseStat(4)));
                        t.setText(character.getFinalStat(4) + " (" + character.getModifierString(character.getFinalStat(4)) + ")");
                    }
                    break;
                case "tfCha":
                    if (character.getStatPoints() != 0 && 27 >= character.getPointCost(input - character.getBaseStat(5), 5)) {
                        character.setBaseStat(Integer.parseInt(tf.getText()), 5);
                        tf.setPromptText(String.valueOf(character.getBaseStat(5)));
                        t.setText(character.getFinalStat(5) + " (" + character.getModifierString(character.getFinalStat(5)) + ")");
                    }
                    break;
            }
            
        }
        tf.setText("");
    }

    /**
     * Gets the point text "27/27" object
     * @return the point "27/27" text object
     */
    public Text getPointText() {
        return textPoints;
    }

    /**
     * Returns the strength textfield
     * @return Strength Textfield
     */
    public TextField getTfStr() {
        return tfStr;
    }

    /**
     * Returns the dexterity textfield
     * @return Dexterity Textfield
     */
    public TextField getTfDex() {
        return tfDex;
    }

    /**
     * Returns the constitution textfield
     * @return Constitution Textfield
     */
    public TextField getTfCon() {
        return tfCon;
    }

    /**
     * Returns the intelligence textfield
     * @return Intelligence Textfield
     */
    public TextField getTfInt() {
        return tfInt;
    }

    /**
     * Returns the wisdom textfield
     * @return Wisdom Textfield
     */
    public TextField getTfWis() {
        return tfWis;
    }

    /**
     * Returns the charisma textfield
     * @return Charisma Textfield
     */
    public TextField getTfCha() {
        return tfCha;
    }

    public Text gettStr() {
        return tStr;
    }

    public Text gettDex() {
        return tDex;
    }

    public Text gettCon() {
        return tCon;
    }

    public Text gettInt() {
        return tInt;
    }

    public Text gettWis() {
        return tWis;
    }

    public Text gettCha() {
        return tCha;
    }

    public void setSpeciesTitle(String title) {
        speciesTitle.setText(title);
    }

    public void setSpeciesSidePaneWidth(double width) {
        speciesSidePane.setPrefWidth(width);
    }
}