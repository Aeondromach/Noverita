/**
 * @author Evelyn Engleman @Æondromach
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class CharacterController {
    private NovController nov;

    @FXML private TextField tfStr;
    @FXML private TextField tfDex;
    @FXML private TextField tfCon;
    @FXML private TextField tfInt;
    @FXML private TextField tfWis;
    @FXML private TextField tfCha;

    @FXML private Text textPoints;

    /**
     * Takes instantiated Novcontroller and links self to it.
     * @param nov Sets instance of Novcontroller
     */
    public void init(NovController nov) {
        this.nov = nov;
    }

    @FXML protected void initialize() {
        Platform.runLater(() -> {
            setTf(tfStr);
            setTf(tfDex);
            setTf(tfCon);
            setTf(tfInt);
            setTf(tfWis);
            setTf(tfCha);
        });
    }

    private void setTf(TextField tf) {
        tf.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    @FXML protected void handleIncrementUp(MouseEvent event) {
        com.aeondromach.system.Character character = nov.getCharacter();

        if(event.getButton().equals(MouseButton.PRIMARY)){
            Button sourceButton = (Button) event.getSource();
    
            switch (sourceButton.getId()) {
                case "btStrIncUp":
                    if (character.getStatPoints() != 0 && 27 >= character.getPointCost(character.getBaseSTR() + 1, 0)) {
                        character.setBaseSTR(character.getBaseSTR() + 1);
                        tfStr.setPromptText(character.getFinalSTR() + " (" + character.getModifierString(character.getFinalSTR()) + ")");
                    }
                    break;
                case "btDexIncUp":
                    if (character.getStatPoints() != 0 && 27 >= character.getPointCost(character.getBaseDEX() + 1, 1)) {
                        character.setBaseDEX(character.getBaseDEX() + 1);
                        tfDex.setPromptText(character.getFinalDEX() + " (" + character.getModifierString(character.getFinalDEX()) + ")");
                    }                    
                    break;
                case "btConIncUp":
                    if (character.getStatPoints() != 0 && 27 >= character.getPointCost(character.getBaseCON() + 1, 2)) {
                        character.setBaseCON(character.getBaseCON() + 1);
                        tfCon.setPromptText(character.getFinalCON() + " (" + character.getModifierString(character.getFinalCON()) + ")");
                    }                    
                    break;
                case "btIntIncUp":
                    if (character.getStatPoints() != 0 && 27 >= character.getPointCost(character.getBaseINT() + 1, 3)) {
                        character.setBaseINT(character.getBaseINT() + 1);
                        tfInt.setPromptText(character.getBaseINT() + " (" + character.getModifierString(character.getFinalINT()) + ")");
                    }                    
                    break;
                case "btWisIncUp":
                    if (character.getStatPoints() != 0 && 27 >= character.getPointCost(character.getBaseWIS() + 1, 4)) {
                        character.setBaseWIS(character.getBaseWIS() + 1);
                        tfWis.setPromptText(character.getFinalWIS() + " (" + character.getModifierString(character.getFinalWIS()) + ")");
                    }                    
                    break;
                case "btChaIncUp":
                    if (character.getStatPoints() != 0 && 27 >= character.getPointCost(character.getBaseCHA() + 1, 5)) {
                        character.setBaseCHA(character.getBaseCHA() + 1);
                        tfCha.setPromptText(character.getFinalCHA() + " (" + character.getModifierString(character.getFinalCHA()) + ")");
                    }                    
                    break;
            }
            character.setStatPoints();
            textPoints.setText(character.getStatPoints() + "/27");
        }
    }

    @FXML protected void handleIncrementDown(MouseEvent event) {
        com.aeondromach.system.Character character = nov.getCharacter();

        if(event.getButton().equals(MouseButton.PRIMARY)){
            Button sourceButton = (Button) event.getSource();
    
            switch (sourceButton.getId()) {
                case "btStrIncDown":
                    character.setBaseSTR(character.getBaseSTR() - 1);
                    tfStr.setPromptText(character.getFinalSTR() + " (" + character.getModifierString(character.getFinalSTR()) + ")");
                    break;
                case "btDexIncDown":
                    character.setBaseDEX(character.getBaseDEX() - 1);
                    tfDex.setPromptText(character.getFinalDEX() + " (" + character.getModifierString(character.getFinalDEX()) + ")");
                    break;
                case "btConIncDown":
                    character.setBaseCON(character.getBaseCON() - 1);
                    tfCon.setPromptText(character.getFinalCON() + " (" + character.getModifierString(character.getFinalCON()) + ")");
                    break;
                case "btIntIncDown":
                    character.setBaseINT(character.getBaseINT() - 1);
                    tfInt.setPromptText(character.getFinalINT() + " (" + character.getModifierString(character.getFinalINT()) + ")");
                    break;
                case "btWisIncDown":
                    character.setBaseWIS(character.getBaseWIS() - 1);
                    tfWis.setPromptText(character.getFinalWIS() + " (" + character.getModifierString(character.getFinalWIS()) + ")");
                    break;
                case "btChaIncDown":
                    character.setBaseCHA(character.getBaseCHA() - 1);
                    tfCha.setPromptText(character.getFinalCHA() + " (" + character.getModifierString(character.getFinalCHA()) + ")");
                    break;
            }
            character.setStatPoints();
            textPoints.setText(character.getStatPoints() + "/27");
        }
    }

    @FXML protected void handleStatInput(ActionEvent event) {
        com.aeondromach.system.Character character = nov.getCharacter();
        TextField sourceTextField = (TextField) event.getSource();

        switch (sourceTextField.getId()) {
            case "tfStr":
                textFieldInput(tfStr, character);
                break;
            case "tfDex":
                textFieldInput(tfDex, character);
                break;
            case "tfCon":
                textFieldInput(tfCon, character);
                break;
            case "tfInt":
                textFieldInput(tfInt, character);
                break;
            case "tfWis":
                textFieldInput(tfWis, character);
                break;
            case "tfCha":
                textFieldInput(tfCha, character);
                break;
        }
        character.setStatPoints();
        textPoints.setText(character.getStatPoints() + "/27");
    }

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

    public Text getPointText() {
        return textPoints;
    }

    public TextField getTfStr() {
        return tfStr;
    }

    public TextField getTfDex() {
        return tfDex;
    }

    public TextField getTfCon() {
        return tfCon;
    }

    public TextField getTfInt() {
        return tfInt;
    }

    public TextField getTfWis() {
        return tfWis;
    }

    public TextField getTfCha() {
        return tfCha;
    }
}