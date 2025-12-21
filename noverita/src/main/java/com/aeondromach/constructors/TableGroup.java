package com.aeondromach.constructors;

import java.util.ArrayList;

import javafx.scene.layout.AnchorPane;

public class TableGroup {
    private final ArrayList<Table> TABLES;

    public TableGroup(ArrayList<Table> tables) {
        this.TABLES = tables;
    }

    public void addTable(Table table) {
        if (table == null || this.TABLES.contains(table)) return;

        table.setConnectedTables(this);
        this.TABLES.add(table);
    }

    public void removeTable(Table table) {
        if (table == null || !this.TABLES.contains(table)) return;

        table.setConnectedTables(null);
        this.TABLES.remove(table);
    }

    public void clickHoverSet(Table table, AnchorPane tableElem) {
        for (Table t : this.TABLES) {
            t.unsetAllClickedElems();
            if (t.equals(table)) {
                tableElem.getStyleClass().add("tableElemClicked");
            }
        }
    }
}
