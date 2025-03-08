/**
 * @author Evelyn Engleman @Aeondromach
 * @version 1
 * @since 12/11/2024
 * Main controller for Footer
 */

package com.aeondromach.controllers;

import static com.aeondromach.controllers.NovController.VERSION;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FooterController {
    @FXML private HBox pathHold;
    @FXML private HBox verHold;
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
            Stage stage = (Stage) pathHold.getScene().getWindow();
            pathHold.translateXProperty().bind(stage.widthProperty().subtract(pathHold.prefWidthProperty()).divide(2).subtract(pathHold.getLayoutX()));
 
            verHold.translateXProperty().bind(stage.widthProperty().subtract(verHold.prefWidthProperty()).subtract(verHold.getLayoutX()));
            
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
     * Takes a filepath and displays it in the middle of the footer
     * @param filePath Insert the current character filepath to be displayed in the middle of the footer
     */
    public void setCharFilePath(String filePath) {
        pathHold.setVisible(true);
        labFilePath.setText("🗀 " + filePath);
    }

    /**
     * Calls NovController to set last action
     */
    public void setLastAction() {
        labLastAction.setText(nov.getLastAction());
    }
}