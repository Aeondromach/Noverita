/**
 * @author Evelyn Engleman @Aeondromach
 * @version 1
 * @since 12/11/2024
 * Main controller for Character page
 */

package com.aeondromach.controllers;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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

    @FXML private TabPane character;

    @FXML private Text speciesTitle;

    @FXML private Text textPoints;

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
            setTf(tfStr);
            setTf(tfDex);
            setTf(tfCon);
            setTf(tfInt);
            setTf(tfWis);
            setTf(tfCha);

            Stage stage = (Stage) character.getScene().getWindow();
            character.prefWidthProperty().bind(stage.widthProperty().subtract(.001));
        });
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
                        curChar.setBaseSTR(curChar.getBaseSTR() + 1);
                        tfStr.setPromptText(curChar.getFinalSTR() + " (" + curChar.getModifierString(curChar.getFinalSTR()) + ")");
                    }
                    break;
                case "btDexIncUp":
                    if (curChar.getStatPoints() != 0 && 27 >= curChar.getPointCost(1, 1)) {
                        curChar.setBaseDEX(curChar.getBaseDEX() + 1);
                        tfDex.setPromptText(curChar.getFinalDEX() + " (" + curChar.getModifierString(curChar.getFinalDEX()) + ")");
                    }                    
                    break;
                case "btConIncUp":
                    if (curChar.getStatPoints() != 0 && 27 >= curChar.getPointCost(1, 2)) {
                        curChar.setBaseCON(curChar.getBaseCON() + 1);
                        tfCon.setPromptText(curChar.getFinalCON() + " (" + curChar.getModifierString(curChar.getFinalCON()) + ")");
                    }                    
                    break;
                case "btIntIncUp":
                    if (curChar.getStatPoints() != 0 && 27 >= curChar.getPointCost(1, 3)) {
                        curChar.setBaseINT(curChar.getBaseINT() + 1);
                        tfInt.setPromptText(curChar.getBaseINT() + " (" + curChar.getModifierString(curChar.getFinalINT()) + ")");
                    }                    
                    break;
                case "btWisIncUp":
                    if (curChar.getStatPoints() != 0 && 27 >= curChar.getPointCost(1, 4)) {
                        curChar.setBaseWIS(curChar.getBaseWIS() + 1);
                        tfWis.setPromptText(curChar.getFinalWIS() + " (" + curChar.getModifierString(curChar.getFinalWIS()) + ")");
                    }                    
                    break;
                case "btChaIncUp":
                    if (curChar.getStatPoints() != 0 && 27 >= curChar.getPointCost(1, 5)) {
                        curChar.setBaseCHA(curChar.getBaseCHA() + 1);
                        tfCha.setPromptText(curChar.getFinalCHA() + " (" + curChar.getModifierString(curChar.getFinalCHA()) + ")");
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
                    curChar.setBaseSTR(curChar.getBaseSTR() - 1);
                    tfStr.setPromptText(curChar.getFinalSTR() + " (" + curChar.getModifierString(curChar.getFinalSTR()) + ")");
                    break;
                case "btDexIncDown":
                    curChar.setBaseDEX(curChar.getBaseDEX() - 1);
                    tfDex.setPromptText(curChar.getFinalDEX() + " (" + curChar.getModifierString(curChar.getFinalDEX()) + ")");
                    break;
                case "btConIncDown":
                    curChar.setBaseCON(curChar.getBaseCON() - 1);
                    tfCon.setPromptText(curChar.getFinalCON() + " (" + curChar.getModifierString(curChar.getFinalCON()) + ")");
                    break;
                case "btIntIncDown":
                    curChar.setBaseINT(curChar.getBaseINT() - 1);
                    tfInt.setPromptText(curChar.getFinalINT() + " (" + curChar.getModifierString(curChar.getFinalINT()) + ")");
                    break;
                case "btWisIncDown":
                    curChar.setBaseWIS(curChar.getBaseWIS() - 1);
                    tfWis.setPromptText(curChar.getFinalWIS() + " (" + curChar.getModifierString(curChar.getFinalWIS()) + ")");
                    break;
                case "btChaIncDown":
                    curChar.setBaseCHA(curChar.getBaseCHA() - 1);
                    tfCha.setPromptText(curChar.getFinalCHA() + " (" + curChar.getModifierString(curChar.getFinalCHA()) + ")");
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
                textFieldInput(tfStr, curChar);
                break;
            case "tfDex":
                textFieldInput(tfDex, curChar);
                break;
            case "tfCon":
                textFieldInput(tfCon, curChar);
                break;
            case "tfInt":
                textFieldInput(tfInt, curChar);
                break;
            case "tfWis":
                textFieldInput(tfWis, curChar);
                break;
            case "tfCha":
                textFieldInput(tfCha, curChar);
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
    private void textFieldInput(TextField tf, com.aeondromach.system.Character character) {
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
                    if (character.getStatPoints() != 0 && 27 >= character.getPointCost(input - character.getBaseSTR(), 0)) {
                        character.setBaseSTR(Integer.parseInt(tf.getText()));
                        tf.setPromptText(character.getBaseSTR() + " (" + character.getModifierString(character.getFinalSTR()) + ")");
                    }
                    break;
                case "tfDex":
                    if (character.getStatPoints() != 0 && 27 >= character.getPointCost(input - character.getBaseDEX(), 1)) {
                        character.setBaseDEX(Integer.parseInt(tf.getText()));
                        tf.setPromptText(character.getBaseDEX() + " (" + character.getModifierString(character.getFinalDEX()) + ")");
                    }
                    break;
                case "tfCon":
                    if (character.getStatPoints() != 0 && 27 >= character.getPointCost(input - character.getBaseCON(), 2)) {
                        character.setBaseCON(Integer.parseInt(tf.getText()));
                        tf.setPromptText(character.getBaseCON() + " (" + character.getModifierString(character.getFinalCON()) + ")");
                    }
                    break;
                case "tfInt":
                    if (character.getStatPoints() != 0 && 27 >= character.getPointCost(input - character.getBaseINT(), 3)) {
                        character.setBaseINT(Integer.parseInt(tf.getText()));
                        tf.setPromptText(character.getBaseINT() + " (" + character.getModifierString(character.getFinalINT()) + ")");
                    }
                    break;
                case "tfWis":
                    if (character.getStatPoints() != 0 && 27 >= character.getPointCost(input - character.getBaseWIS(), 4)) {
                        character.setBaseWIS(Integer.parseInt(tf.getText()));
                        tf.setPromptText(character.getBaseWIS() + " (" + character.getModifierString(character.getFinalWIS()) + ")");
                    }
                    break;
                case "tfCha":
                    if (character.getStatPoints() != 0 && 27 >= character.getPointCost(input - character.getBaseCHA(), 5)) {
                        character.setBaseCHA(Integer.parseInt(tf.getText()));
                        tf.setPromptText(character.getBaseCHA() + " (" + character.getModifierString(character.getFinalCHA()) + ")");
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
}