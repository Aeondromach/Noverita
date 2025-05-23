/**
 * @author Evelyn Engleman @Aeondromach
 * @version 1
 * @since 12/11/2024
 * Main controller for Equipment page
 */

package com.aeondromach.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class EquipmentController {
    private NovController nov;

    /**
     * Takes instantiated Novcontroller and links self to it.
     * @param nov Sets instance of Novcontroller
     */
    public void init(NovController nov) {
        this.nov = nov;
    }

    /**
     * TEST BUTTON, REMOVE SOON
     * @param event
     */
    @FXML protected void handleTestClick(MouseEvent event) {
        nov.addAction("Equip");
    }
}