/**
 * @author Evelyn Engleman @Æondromach
 * @version 1
 * @since 12/11/2024
 * Main controller for Footer
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

    private NovController nov;

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

    /**
     * Takes instantiated Novcontroller and links self to it.
     * @param nov Sets instance of Novcontroller
     */
    public void init(NovController nov) {
        this.nov = nov;
    }

    /**
     * Calls NovController to set last action
     */
    public void setLastAction() {
        labLastAction.setText(nov.getLastAction());
    }
}
