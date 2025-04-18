package com.aeondromach.constructors;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Consumer;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.aeondromach.system.parsers.XmlParser;

import javafx.geometry.Pos;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;

public class Table {
    private Map<String, String> map;
    private String title;
    private String type;
    private Consumer<String> singleClick;
    private Consumer<String> doubleClick;

    public Table(Map<String, String> map, String title, String type, Consumer<String> singleClick, Consumer<String> doubleClick) {
        this.doubleClick = doubleClick;
        this.map = map;
        this.singleClick = singleClick;
        this.title = title;
        this.type = type;
    }

    public VBox setTable() {
        int height = 20;

        VBox table = new VBox();
        table.setAlignment(Pos.TOP_CENTER);
        table.getStyleClass().add("table");

        AnchorPane tableHead = new AnchorPane();
        tableHead.getStyleClass().add("tableHead");
        tableHead.setPrefHeight(height);

        Text headText = new Text();
        headText.setText(title.toUpperCase());
        headText.getStyleClass().add("tableHeadText");

        SVGPath headIcon = new SVGPath();
        headIcon.setContent("M14 20l8 8 8-8z");
        headIcon.setStyle("-fx-fill: -headerTextColor;");

        AnchorPane paddingLeft = new AnchorPane();
        paddingLeft.setPrefWidth(10);
        paddingLeft.setPrefHeight(height);

        AnchorPane paddingRight = new AnchorPane();
        paddingRight.setPrefWidth(8);
        paddingRight.setPrefHeight(height);

        HBox leftAlign = new HBox();
        leftAlign.setAlignment(Pos.CENTER_LEFT);
        leftAlign.prefWidthProperty().bind(tableHead.widthProperty().divide(2));
        leftAlign.setPrefHeight(height);

        leftAlign.getChildren().addAll(paddingLeft, headText);

        HBox rightAlign = new HBox();
        rightAlign.setAlignment(Pos.CENTER_RIGHT);
        rightAlign.prefWidthProperty().bind(tableHead.widthProperty().divide(2));
        rightAlign.translateXProperty().bind(leftAlign.widthProperty());
        rightAlign.setPrefHeight(height);

        rightAlign.getChildren().addAll(headIcon, paddingRight);

        tableHead.getChildren().addAll(leftAlign, rightAlign);

        table.getChildren().add(tableHead);

        int i = 1;
        for (String id: map.keySet()) {
            ArrayList<String> arrayList = parseXml(XmlParser.check(id, map));

            AnchorPane tableElem = new AnchorPane();
            tableElem.getStyleClass().add("tableElem");
            if (i == map.size()) tableElem.getStyleClass().add("tableFinal");
            tableElem.setPrefHeight(20);

            Text tableTitle = new Text();
            tableTitle.setText(arrayList.get(0).toUpperCase());
            tableTitle.getStyleClass().add("tableText");

            Text tableSource = new Text();
            tableSource.setText(arrayList.get(1));
            tableSource.getStyleClass().add("tableSource");

            AnchorPane paddingLeftElem = new AnchorPane();
            paddingLeftElem.setPrefWidth(10);
            paddingLeftElem.setPrefHeight(tableElem.getPrefHeight());

            AnchorPane paddingRightElem = new AnchorPane();
            paddingRightElem.setPrefWidth(8);
            paddingRightElem.setPrefHeight(tableElem.getPrefHeight());

            HBox leftAlignElem = new HBox();
            leftAlignElem.setAlignment(Pos.CENTER_LEFT);
            leftAlignElem.prefWidthProperty().bind(tableElem.widthProperty().divide(2));
            leftAlignElem.prefHeightProperty().bind(tableElem.heightProperty());

            leftAlignElem.getChildren().addAll(paddingLeftElem, tableTitle);

            HBox rightAlignElem = new HBox();
            rightAlignElem.setAlignment(Pos.CENTER_RIGHT);
            rightAlignElem.prefWidthProperty().bind(tableElem.widthProperty().divide(2));
            rightAlignElem.translateXProperty().bind(leftAlignElem.widthProperty());
            rightAlignElem.prefHeightProperty().bind(tableElem.heightProperty());

            rightAlignElem.getChildren().addAll(tableSource, paddingRightElem);

            tableElem.getChildren().addAll(leftAlignElem, rightAlignElem);

            tableElem.setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == 1) {
                    singleClick.accept(id);
                }
                else if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == 2) {
                    doubleClick.accept(id);
                }
            });

            table.getChildren().add(tableElem);
            i++;
        }

        return table;
    }

    private ArrayList<String> parseXml(Document doc) {
        ArrayList<String> arrayList = new ArrayList<>();
        Elements elements = doc.select("element[id]");
        for (Element element: elements) {
            if (element.attr("type").toLowerCase().equals(type.toLowerCase()) && !element.attr("name").isEmpty()) {
                arrayList.add(element.attr("name"));

                if (!element.attr("source").isEmpty()) arrayList.add(element.attr("source"));
                else arrayList.add("No Source");

                return arrayList;
            }
        }
        return null;
    }

    public String getTableElemId() {
        String id = "";
        return id;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}