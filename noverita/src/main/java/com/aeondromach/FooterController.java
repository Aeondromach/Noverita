/**
 * @author Evelyn Engleman @Æondromach
 * Controller for all Footer items
 */

package com.aeondromach;

import static com.aeondromach.NovController.VERSION;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class FooterController {
    @FXML private HBox pathHold;
    @FXML private AnchorPane footerPane;
    @FXML private Text labVersion;
    @FXML private Text labFilePath;
    @FXML private Text labLastAction;

    /**
     * Runs initial footer set up
     */
    @FXML
    protected void initialize() {
        Platform.runLater(() -> {
            pathHold.setLayoutX((footerPane.getWidth()/2) - (pathHold.getWidth()/2));
            labVersion.setText("nov" + VERSION);
        });
    }
}
