/**
 * @author Evelyn Engleman @Æondromach
 * @version 2
 * @since 12/08/2024
 * Handles resizing all app elements whenever stage width/height changes
 */

package com.aeondromach;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class ResizeHandler {
    public static class NewResizeWidthChange implements ChangeListener<Number> {
        private final BorderPane mainPane;
        private final Pane footerPane;
        private final HBox versionHold;
        private final HBox pathFileHold;
        private final Pane headerPane;
        private final StackPane titleHold;
        private final AnchorPane titleButtonHold;
        private final TabPane homePane;
        private final TabPane charPane;
        private final TabPane equipPane;
        private final TabPane archePane;
        private final TabPane viewPane;
        
        /**
         * Constructor for width resizer
         * @param mainPane
         */
        public NewResizeWidthChange(@SuppressWarnings("exports") BorderPane mainPane) {
            this.mainPane = mainPane;
            homePane = (TabPane) mainPane.lookup("#homeBod");
            charPane = (TabPane) mainPane.lookup("#charBod");
            equipPane = (TabPane) mainPane.lookup("#equipBod");
            archePane = (TabPane) mainPane.lookup("#archeBod");
            viewPane = (TabPane) mainPane.lookup("#viewBod");
            headerPane = (Pane) mainPane.lookup("#headsHeader");
            titleHold = (StackPane) headerPane.lookup("#titleHold");
            titleButtonHold = (AnchorPane) headerPane.lookup("#titleButtonHold");
            footerPane = (Pane) mainPane.lookup("#headsFooter");
            versionHold = (HBox) footerPane.lookup("#versionHold");
            pathFileHold = (HBox) footerPane.lookup("#pathFileHold");
        }

        /**
         * Changes all values according to old width, new width, and difference
         */
        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
            if (oldSceneWidth.intValue() <= 0) {
                return;
            }

            double delta = newSceneWidth.doubleValue() - oldSceneWidth.doubleValue();

            // entire pane stretch
            mainPane.setPrefWidth(mainPane.getPrefWidth() + delta);

            // Header Stretch
            headerPane.setPrefWidth(headerPane.getPrefWidth() + delta);

            // Header Children Pos set
            if (mainPane.getPrefWidth() + delta > 1150.0) {
                titleHold.setLayoutX((headerPane.getPrefWidth()/2) - (titleHold.getWidth()/2));
                titleButtonHold.setLayoutX(headerPane.getPrefWidth() - titleButtonHold.getWidth());
            }

            // footer Stretch
            footerPane.setPrefWidth(footerPane.getPrefWidth() + delta);

            // Footer Children Pos set
            if (mainPane.getPrefWidth() + delta > 1150.0) {
                versionHold.setLayoutX(footerPane.getPrefWidth() - versionHold.getWidth());
                pathFileHold.setLayoutX((footerPane.getPrefWidth()/2) - (pathFileHold.getWidth()/2));
            }

            // body Stretch
            homePane.setPrefWidth(homePane.getPrefWidth() + delta);
            charPane.setPrefWidth(charPane.getPrefWidth() + delta);
            equipPane.setPrefWidth(equipPane.getPrefWidth() + delta);
            archePane.setPrefWidth(archePane.getPrefWidth() + delta);
            viewPane.setPrefWidth(viewPane.getPrefWidth() + delta);
        }
    }

    public static class NewResizeHeightChange implements ChangeListener<Number> {
        private final BorderPane mainPane;
        private final TabPane homePane;
        private final TabPane charPane;
        private final TabPane equipPane;
        private final TabPane archePane;
        private final TabPane viewPane;
        private final ScrollPane squadsPane;
        private final StackPane newCharHold;

        /**
         * Constructor for height change
         * @param mainPane
         */
        public NewResizeHeightChange(@SuppressWarnings("exports") BorderPane mainPane) {
            this.mainPane = mainPane;
            homePane = (TabPane) mainPane.lookup("#homeBod");
            charPane = (TabPane) mainPane.lookup("#charBod");
            equipPane = (TabPane) mainPane.lookup("#equipBod");
            archePane = (TabPane) mainPane.lookup("#archeBod");
            viewPane = (TabPane) mainPane.lookup("#viewBod");
            squadsPane = (ScrollPane) mainPane.lookup("#squadsPane");
            newCharHold = (StackPane) mainPane.lookup("#newCharButtonHold");
        }

        /**
         * Changes all height elements (Same as width)
         */
        @Override
        public void changed(ObservableValue<?extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
            if (oldSceneHeight.intValue() <= 0) {
                return;
            }

            double delta = newSceneHeight.doubleValue() - oldSceneHeight.doubleValue();

            mainPane.setPrefHeight(mainPane.getPrefHeight() + delta);

            homePane.setPrefHeight(homePane.getPrefHeight() + delta);
            charPane.setPrefHeight(charPane.getPrefHeight() + delta);
            equipPane.setPrefHeight(equipPane.getPrefHeight() + delta);
            archePane.setPrefHeight(archePane.getPrefHeight() + delta);
            viewPane.setPrefHeight(viewPane.getPrefHeight() + delta);

            squadsPane.setPrefHeight(squadsPane.getPrefHeight() + delta);

            newCharHold.setLayoutY(newCharHold.getLayoutY() + delta);
        }
    }
}