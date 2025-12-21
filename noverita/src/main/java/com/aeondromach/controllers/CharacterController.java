/**
 * @author Evelyn Engleman @Aeondromach
 * @version 1
 * @since 12/11/2024
 * Main controller for Character page
 */

package com.aeondromach.controllers;

import java.util.ArrayList;
import java.util.LinkedList;

import org.jsoup.nodes.Element;

import com.aeondromach.Messages;
import com.aeondromach.constructors.Table;
import com.aeondromach.constructors.TableGroup;
import com.aeondromach.system.Character.StatIndex;
import com.aeondromach.system.IdClassList;
import com.aeondromach.system.IdClassList.IdType;
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
import javafx.scene.layout.AnchorPane;
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

    @FXML private Text formTitle;

    @FXML private Text textPoints;

    @FXML private VBox formSidePane;
    @FXML private VBox formSidePaneV;

    @FXML private ScrollPane formScroll;
    @FXML private VBox formVbox;
    @FXML private VBox formSideBarHtml;

    private TableGroup formGroup = new TableGroup(new ArrayList<Table>());
    private Table formTable;
    private Table aspectTable;
    private final VBox aspectHold = new VBox();
    private final LinkedList<Table> tables = new LinkedList<>();

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

            formVbox.prefWidthProperty().bind(character.widthProperty().subtract(formSidePane.widthProperty()));
            formVbox.maxWidthProperty().bind(character.widthProperty().subtract(formSidePane.widthProperty()));

            tables.addAll(
                java.util.Arrays.asList(
                    formTable,
                    aspectTable
                )
            );
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
        try {
            aspectHold.getChildren().clear();
            formVbox.getChildren().addAll(setFormTable(), aspectHold);
        } catch (NullPointerException e) {
            try {
                aspectHold.getChildren().clear();
                formVbox.getChildren().addAll(setFormTable());
            } catch (NullPointerException e2) {
                aspectHold.getChildren().clear();
                formVbox.getChildren().clear();
                Messages.errorAlert("Forms and/or Aspects failed to load", "Forms and/or Aspects failed to load", "The forms and/or aspects files failed to load, check to make sure you are on an up to date build and recheck your custom path settings.");
            }
        }
    }

    private VBox setFormTable() {
        formVbox.getChildren().clear(); // Deletes previous table instances

        formTable = new Table(IdClassList.IdType.FORM, "form", "Form", this::formClick, this::formDualClick);

        formGroup.addTable(formTable);

        VBox formTableBox = formTable.setTable();
        formTableBox.prefWidthProperty().bind(formVbox.widthProperty().subtract(10));

        if (nov.getCharacter().hasForm()) {
            setAspectTable(nov.getCharacter().getForm().getId());
        }

        return formTableBox;
    }

    private void formClick(String id, @SuppressWarnings("unused") AnchorPane tableElem) {
        for (Table table: tables) {
            if (table != null) table.unsetAllClickedElems();
        }

        Element element = XmlParser.getElement(IdClassList.getIdMap(IdType.FORM), id);
        formTitle.setText(element.attr("name"));
        formSidePaneV.getChildren().add(HtmlParser.parseHtml(id, IdClassList.getIdMap(IdType.FORM)));
    }

    private void formDualClick(String id, AnchorPane tableElem) {
        com.aeondromach.system.Character curChar = nov.getCharacter();

        if (!curChar.getForm().matchingIds(id)) curChar.getForm().setId(id);
        else {
            formTable.unsetAllClickedElems();
            curChar.getForm().resetStats(); // If the user clicks on their equipped form, remove it
            curChar.getForm().getASPECT().resetStats();
        }
        aspectHold.getChildren().clear();

        if (curChar.getForm().matchingIds(id)) setAspectTable(id);

        nov.updateHeaderDescription();
    }

    private void setAspectTable(String id) {
        formGroup.removeTable(aspectTable);

        aspectTable = new Table(IdClassList.IdType.ASPECT, "aspect", "Aspect", this::aspectClick, this::aspectDualClick, id, IdClassList.IdType.FORM);
        formGroup.addTable(aspectTable);

        VBox aspectTableBox = aspectTable.setTable();
        
        if (aspectTable.hasParent()) {
            aspectTableBox.prefWidthProperty().bind(formVbox.widthProperty().subtract(10));
            aspectHold.getChildren().add(aspectTableBox);
        }
    }

    private void aspectClick(String id, @SuppressWarnings("unused") AnchorPane tableElem) {
        for (Table table: tables) {
            if (table != null) table.unsetAllClickedElems();
        }
        
        Element element = XmlParser.getElement(IdClassList.getIdMap(IdType.ASPECT), id);
        formTitle.setText(element.attr("name"));
        formSidePaneV.getChildren().add(HtmlParser.parseHtml(id, IdClassList.getIdMap(IdType.ASPECT)));
    }

    private void aspectDualClick(String id, @SuppressWarnings("unused") AnchorPane tableElem) {
        com.aeondromach.system.Character curChar = nov.getCharacter();
        
        if (!curChar.getForm().getASPECT().matchingIds(id)) curChar.getForm().getASPECT().setId(id);
        else {
            aspectTable.unsetAllClickedElems();
            curChar.getForm().getASPECT().resetStats(); // If the user clicks on their equipped aspect, remove it
        } 
        nov.updateHeaderDescription();
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
                    if (curChar.getStatPoints() != 0 && 27 >= curChar.getPointCost(1, StatIndex.STRENGTH)) {
                        curChar.setBaseStat(curChar.getBaseStat(StatIndex.STRENGTH) + 1, StatIndex.STRENGTH);
                        tStr.setText(curChar.getFinalStat(StatIndex.STRENGTH) + " (" + curChar.getModifierString(curChar.getFinalStat(StatIndex.STRENGTH)) + ")");
                        tfStr.setPromptText(String.valueOf(curChar.getBaseStat(StatIndex.STRENGTH)));
                    }
                    break;
                case "btDexIncUp":
                    if (curChar.getStatPoints() != 0 && 27 >= curChar.getPointCost(1, StatIndex.DEXTERITY)) {
                        curChar.setBaseStat(curChar.getBaseStat(StatIndex.DEXTERITY) + 1, StatIndex.DEXTERITY);
                        tDex.setText(curChar.getFinalStat(StatIndex.DEXTERITY) + " (" + curChar.getModifierString(curChar.getFinalStat(StatIndex.DEXTERITY)) + ")");
                        tfDex.setPromptText(String.valueOf(curChar.getBaseStat(StatIndex.DEXTERITY)));
                    }                    
                    break;
                case "btConIncUp":
                    if (curChar.getStatPoints() != 0 && 27 >= curChar.getPointCost(1, StatIndex.CONSTITUTION)) {
                        curChar.setBaseStat(curChar.getBaseStat(StatIndex.CONSTITUTION) + 1, StatIndex.CONSTITUTION);
                        tCon.setText(curChar.getFinalStat(StatIndex.CONSTITUTION) + " (" + curChar.getModifierString(curChar.getFinalStat(StatIndex.CONSTITUTION)) + ")");
                        tfCon.setPromptText(String.valueOf(curChar.getBaseStat(StatIndex.CONSTITUTION)));
                    }                    
                    break;
                case "btIntIncUp":
                    if (curChar.getStatPoints() != 0 && 27 >= curChar.getPointCost(1, StatIndex.INTELLIGENCE)) {
                        curChar.setBaseStat(curChar.getBaseStat(StatIndex.INTELLIGENCE) + 1, StatIndex.INTELLIGENCE);
                        tInt.setText(curChar.getFinalStat(StatIndex.INTELLIGENCE) + " (" + curChar.getModifierString(curChar.getFinalStat(StatIndex.INTELLIGENCE)) + ")");
                        tfInt.setPromptText(String.valueOf(curChar.getBaseStat(StatIndex.INTELLIGENCE)));
                    }                    
                    break;
                case "btWisIncUp":
                    if (curChar.getStatPoints() != 0 && 27 >= curChar.getPointCost(1, StatIndex.WISDOM)) {
                        curChar.setBaseStat(curChar.getBaseStat(StatIndex.WISDOM) + 1, StatIndex.WISDOM);
                        tWis.setText(curChar.getFinalStat(StatIndex.WISDOM) + " (" + curChar.getModifierString(curChar.getFinalStat(StatIndex.WISDOM)) + ")");
                        tfWis.setPromptText(String.valueOf(curChar.getBaseStat(StatIndex.WISDOM)));
                    }                    
                    break;
                case "btChaIncUp":
                    if (curChar.getStatPoints() != 0 && 27 >= curChar.getPointCost(1, StatIndex.CHARISMA)) {
                        curChar.setBaseStat(curChar.getBaseStat(StatIndex.CHARISMA) + 1, StatIndex.CHARISMA);
                        tCha.setText(curChar.getFinalStat(StatIndex.CHARISMA) + " (" + curChar.getModifierString(curChar.getFinalStat(StatIndex.CHARISMA)) + ")");
                        tfCha.setPromptText(String.valueOf(curChar.getBaseStat(StatIndex.CHARISMA)));
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
                    curChar.setBaseStat(curChar.getBaseStat(StatIndex.STRENGTH) - 1, StatIndex.STRENGTH);
                    tStr.setText(curChar.getFinalStat(StatIndex.STRENGTH) + " (" + curChar.getModifierString(curChar.getFinalStat(StatIndex.STRENGTH)) + ")");
                    tfStr.setPromptText(String.valueOf(curChar.getBaseStat(StatIndex.STRENGTH)));
                    break;
                case "btDexIncDown":
                    curChar.setBaseStat(curChar.getBaseStat(StatIndex.DEXTERITY) - 1, StatIndex.DEXTERITY);
                    tDex.setText(curChar.getFinalStat(StatIndex.DEXTERITY) + " (" + curChar.getModifierString(curChar.getFinalStat(StatIndex.DEXTERITY)) + ")");
                    tfDex.setPromptText(String.valueOf(curChar.getBaseStat(StatIndex.DEXTERITY)));
                    break;
                case "btConIncDown":
                    curChar.setBaseStat(curChar.getBaseStat(StatIndex.CONSTITUTION) - 1, StatIndex.CONSTITUTION);
                    tCon.setText(curChar.getFinalStat(StatIndex.CONSTITUTION) + " (" + curChar.getModifierString(curChar.getFinalStat(StatIndex.CONSTITUTION)) + ")");
                    tfCon.setPromptText(String.valueOf(curChar.getBaseStat(StatIndex.CONSTITUTION)));
                    break;
                case "btIntIncDown":
                    curChar.setBaseStat(curChar.getBaseStat(StatIndex.INTELLIGENCE) - 1, StatIndex.INTELLIGENCE);
                    tInt.setText(curChar.getFinalStat(StatIndex.INTELLIGENCE) + " (" + curChar.getModifierString(curChar.getFinalStat(StatIndex.INTELLIGENCE)) + ")");
                    tfInt.setPromptText(String.valueOf(curChar.getBaseStat(StatIndex.INTELLIGENCE)));
                    break;
                case "btWisIncDown":
                    curChar.setBaseStat(curChar.getBaseStat(StatIndex.WISDOM) - 1, StatIndex.WISDOM);
                    tWis.setText(curChar.getFinalStat(StatIndex.WISDOM) + " (" + curChar.getModifierString(curChar.getFinalStat(StatIndex.WISDOM)) + ")");
                    tfWis.setPromptText(String.valueOf(curChar.getBaseStat(StatIndex.WISDOM)));
                    break;
                case "btChaIncDown":
                    curChar.setBaseStat(curChar.getBaseStat(StatIndex.CHARISMA) - 1, StatIndex.CHARISMA);
                    tCha.setText(curChar.getFinalStat(StatIndex.CHARISMA) + " (" + curChar.getModifierString(curChar.getFinalStat(StatIndex.CHARISMA)) + ")");
                    tfCha.setPromptText(String.valueOf(curChar.getBaseStat(StatIndex.CHARISMA)));
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
                    if (character.getStatPoints() != 0 && 27 >= character.getPointCost(input - character.getBaseStat(StatIndex.STRENGTH), StatIndex.STRENGTH)) {
                        character.setBaseStat(Integer.parseInt(tf.getText()), StatIndex.STRENGTH);
                        tf.setPromptText(String.valueOf(character.getBaseStat(StatIndex.STRENGTH)));
                        t.setText(character.getFinalStat(StatIndex.STRENGTH) + " (" + character.getModifierString(character.getFinalStat(StatIndex.STRENGTH)) + ")");
                    }
                    break;
                case "tfDex":
                    if (character.getStatPoints() != 0 && 27 >= character.getPointCost(input - character.getBaseStat(StatIndex.DEXTERITY), StatIndex.DEXTERITY)) {
                        character.setBaseStat(Integer.parseInt(tf.getText()), StatIndex.DEXTERITY);
                        tf.setPromptText(String.valueOf(character.getBaseStat(StatIndex.DEXTERITY)));
                        t.setText(character.getFinalStat(StatIndex.DEXTERITY) + " (" + character.getModifierString(character.getFinalStat(StatIndex.DEXTERITY)) + ")");
                    }
                    break;
                case "tfCon":
                    if (character.getStatPoints() != 0 && 27 >= character.getPointCost(input - character.getBaseStat(StatIndex.CONSTITUTION), StatIndex.CONSTITUTION)) {
                        character.setBaseStat(Integer.parseInt(tf.getText()), StatIndex.CONSTITUTION);
                        tf.setPromptText(String.valueOf(character.getBaseStat(StatIndex.CONSTITUTION)));
                        t.setText(character.getFinalStat(StatIndex.CONSTITUTION) + " (" + character.getModifierString(character.getFinalStat(StatIndex.CONSTITUTION)) + ")");
                    }
                    break;
                case "tfInt":
                    if (character.getStatPoints() != 0 && 27 >= character.getPointCost(input - character.getBaseStat(StatIndex.INTELLIGENCE), StatIndex.INTELLIGENCE)) {
                        character.setBaseStat(Integer.parseInt(tf.getText()), StatIndex.INTELLIGENCE);
                        tf.setPromptText(String.valueOf(character.getBaseStat(StatIndex.INTELLIGENCE)));
                        t.setText(character.getFinalStat(StatIndex.INTELLIGENCE) + " (" + character.getModifierString(character.getFinalStat(StatIndex.INTELLIGENCE)) + ")");
                    }
                    break;
                case "tfWis":
                    if (character.getStatPoints() != 0 && 27 >= character.getPointCost(input - character.getBaseStat(StatIndex.WISDOM), StatIndex.WISDOM)) {
                        character.setBaseStat(Integer.parseInt(tf.getText()), StatIndex.WISDOM);
                        tf.setPromptText(String.valueOf(character.getBaseStat(StatIndex.WISDOM)));
                        t.setText(character.getFinalStat(StatIndex.WISDOM) + " (" + character.getModifierString(character.getFinalStat(StatIndex.WISDOM)) + ")");
                    }
                    break;
                case "tfCha":
                    if (character.getStatPoints() != 0 && 27 >= character.getPointCost(input - character.getBaseStat(StatIndex.CHARISMA), StatIndex.CHARISMA)) {
                        character.setBaseStat(Integer.parseInt(tf.getText()), StatIndex.CHARISMA);
                        tf.setPromptText(String.valueOf(character.getBaseStat(StatIndex.CHARISMA)));
                        t.setText(character.getFinalStat(StatIndex.CHARISMA) + " (" + character.getModifierString(character.getFinalStat(StatIndex.CHARISMA)) + ")");
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

    public void setFormTitle(String title) {
        formTitle.setText(title);
    }

    public void setFormSidePaneWidth(double width) {
        formSidePane.setPrefWidth(width);
    }
}