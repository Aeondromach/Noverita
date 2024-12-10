package com.aeondromach;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class HomeController {
    @FXML private Button btTest;

    private NovController nov;

    public void init(NovController nov) {
        this.nov = nov;
    }

    @FXML protected void handleTestClick(MouseEvent event) {
        btTest.setText("test");
    }
}
