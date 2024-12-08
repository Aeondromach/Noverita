/**
 * @author Evelyn Engleman @Æondromach
 * Main Controller for Noverita
 */

package com.aeondromach;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class NovController {
    @FXML private BorderPane novPane;

    private HeaderController headCon;

    @FXML
    protected void initialize() {
        headCon.init(this);
    }
}
