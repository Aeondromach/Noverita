package com.aeondromach.system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.aeondromach.controllers.NovController;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Squad {
    private final String NAME;
    private final HashMap<String, CharView> CHAR_VIEWS = new HashMap<>();
    private final VBox VBOX_SQUAD;

    private final VBox VBOX_CHAR_HUB;
    private final VBox VBOX_SQUAD_LIST;
    private FlowPane SQUAD_HOLD = new FlowPane();

    private boolean s = false;

    public Squad(String name, CharView charView, VBox vBoxCharHub, VBox vBoxSquadList, NovController nov) {
        this.NAME = name;
        this.CHAR_VIEWS.put(charView.getFILEPATH(), charView);
        VBOX_SQUAD = new VBox();
        this.VBOX_CHAR_HUB = vBoxCharHub;
        this.VBOX_SQUAD_LIST = vBoxSquadList;

        buildSquadUI();

        SQUAD_HOLD.getChildren().add(charView.getCHARACTER_BLOCK());
    }

    public void addCharacter(CharView charView) {
        if (CHAR_VIEWS.containsKey(charView.getFILEPATH())) {
            CHAR_VIEWS.replace(charView.getFILEPATH(), charView);
        } else {
            CHAR_VIEWS.put(charView.getFILEPATH(), charView);
        }
        SQUAD_HOLD.getChildren().add(charView.getCHARACTER_BLOCK());
    }

    private void buildSquadUI() {
        Stage stage = (Stage) VBOX_CHAR_HUB.getScene().getWindow();

        if (CHAR_VIEWS == null || CHAR_VIEWS.isEmpty()) return;

        Map<String, List<CharView>> squadMap = new LinkedHashMap<>();

        for (CharView character : CHAR_VIEWS.values()) {
            squadMap.computeIfAbsent(character.getSquad(), k -> new ArrayList<>()).add(character);
        }

        List<String> sortedSquads = new ArrayList<>(squadMap.keySet());
        Collections.sort(sortedSquads);

        VBOX_CHAR_HUB.minWidthProperty().bind(stage.widthProperty().subtract(VBOX_SQUAD_LIST.prefWidthProperty()).subtract(15));
        VBOX_CHAR_HUB.maxWidthProperty().bind(stage.widthProperty().subtract(VBOX_SQUAD_LIST.prefWidthProperty()).subtract(15));

        // MAIN PANE CONTENT
        HBox squadTitleHold = new HBox();
        squadTitleHold.setAlignment(Pos.CENTER_LEFT);
        squadTitleHold.setStyle("-fx-background-color: transparent;");

        Label squadTitle = new Label(NAME);
        squadTitle.getStyleClass().add("squadTitle");
        squadTitle.setTextOverrun(OverrunStyle.CLIP);
        squadTitle.setMaxWidth(Double.MAX_VALUE);

        Line squadLine = new Line();
        squadLine.setStyle("-fx-stroke: -brightSecondary; -fx-stroke-width: 1; -fx-opacity: 0.8;");
        squadLine.startXProperty().bind(squadTitle.layoutBoundsProperty().map(bounds -> bounds.getWidth() + 20));
        squadLine.endXProperty().bind(squadTitleHold.widthProperty());
        squadLine.setStartY(0);
        squadLine.setEndY(0);

        squadTitleHold.getChildren().addAll(squadTitle, squadLine);

        SQUAD_HOLD = new FlowPane();
        SQUAD_HOLD.setAlignment(Pos.CENTER_LEFT);
        SQUAD_HOLD.getStyleClass().add("squadHold");
        SQUAD_HOLD.setStyle("-fx-padding: 2px 30px;");

        VBOX_SQUAD.getChildren().addAll(squadTitleHold, SQUAD_HOLD);
    }

    public String getNAME() {
        return NAME;
    }

    public HashMap<String, CharView> getCHAR_VIEWS() {
        return CHAR_VIEWS;
    }

    public VBox getVBOX_SQUAD() {
        return VBOX_SQUAD;
    }

    public boolean isS() {
        return s;
    }

    public void setS(boolean s) {
        this.s = s;
    }

    public FlowPane getSQUAD_HOLD() {
        return SQUAD_HOLD;
    }
}