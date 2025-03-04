/**
 * @author Evelyn Engleman @Æondromach
 * @version 2
 * @since 12/08/2024
 * Handles resizing all app elements whenever stage width/height changes
 */

package com.aeondromach;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class ResizeHandler {
    public static class NewResizeWidthChange implements ChangeListener<Number> {
        private final BorderPane mainPane;
        private final Pane footerPane;
        private final Pane headerPane;
        private final ScrollPane scrollCharHubs;
        
        /**
         * Constructor for width resizer
         * @param mainPane
         */
        public NewResizeWidthChange(@SuppressWarnings("exports") BorderPane mainPane) {
            this.mainPane = mainPane;
            headerPane = (Pane) mainPane.lookup("#headsHeader");
            footerPane = (Pane) mainPane.lookup("#headsFooter");
            scrollCharHubs = (ScrollPane) mainPane.lookup("#scrollCharHubs");
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

            // footer Stretch
            footerPane.setPrefWidth(footerPane.getPrefWidth() + delta);

            Platform.runLater(() -> {
                scrollCharHubs.setPrefWidth(scrollCharHubs.getPrefWidth() + delta);
            });
        }
    }

    public static class NewResizeHeightChange implements ChangeListener<Number> {
        private final BorderPane mainPane;
        private final ScrollPane squadsPane;
        private final StackPane newCharHold;

        /**
         * Constructor for height change
         * @param mainPane
         */
        public NewResizeHeightChange(@SuppressWarnings("exports") BorderPane mainPane) {
            this.mainPane = mainPane;
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

            squadsPane.setPrefHeight(squadsPane.getPrefHeight() + delta);

            newCharHold.setLayoutY(newCharHold.getLayoutY() + delta);
        }
    }
}