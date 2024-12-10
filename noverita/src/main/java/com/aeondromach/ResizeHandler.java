/**
 * @author Evelyn Engleman @Æondromach
 */

package com.aeondromach;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
        private final Pane bodyPane;
        
        /**
         * Constructor for width resizer
         * @param mainPane
         */
        public NewResizeWidthChange(@SuppressWarnings("exports") BorderPane mainPane) {
            this.mainPane = mainPane;
            bodyPane = (Pane) mainPane.lookup("#bodyAppPane");
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

            // body Stretch
            bodyPane.setPrefWidth(mainPane.getPrefWidth() - 0.3);

            // Header Stretch
            headerPane.setPrefWidth(mainPane.getPrefWidth() - 0.3);

            // Header Children Pos set
            if (mainPane.getPrefWidth() + delta > 1150.0) {
                titleHold.setLayoutX((headerPane.getPrefWidth()/2) - (titleHold.getWidth()/2));
                titleButtonHold.setLayoutX(headerPane.getPrefWidth() - titleButtonHold.getWidth());
            }

            // footer Stretch
            footerPane.setPrefWidth(mainPane.getPrefWidth() - 0.3);

            // Footer Children Pos set
            if (mainPane.getPrefWidth() + delta > 1150.0) {
                versionHold.setLayoutX(footerPane.getPrefWidth() - versionHold.getWidth());
                pathFileHold.setLayoutX((footerPane.getPrefWidth()/2) - (pathFileHold.getWidth()/2));
            }
        }
    }

    public static class NewResizeHeightChange implements ChangeListener<Number> {
        private final BorderPane mainPane;
        private final Pane bodyPane;

        /**
         * Constructor for height change
         * @param mainPane
         */
        public NewResizeHeightChange(@SuppressWarnings("exports") BorderPane mainPane) {
            this.mainPane = mainPane;
            bodyPane = (Pane) mainPane.lookup("#bodyAppPane");
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

            bodyPane.setPrefHeight(bodyPane.getPrefHeight() + delta);
        }
    }
}