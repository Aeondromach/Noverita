/**
 * @author Evelyn Engleman @Æondromach
 * @version 1
 * @since 02/16/2025
 * Handles resizing all Home elements whenever stage width/height changes
 */

package com.aeondromach;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class HomeResizeHandler {
    public static class NewResizeWidthChange implements ChangeListener<Number> {
        private final AnchorPane sidePane;
        
        /**
         * Constructor for width resizer
         * @param mainPane
         */
        public NewResizeWidthChange(@SuppressWarnings("exports") AnchorPane sidePane) {
            this.sidePane = sidePane;
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

            // sidePane stretch
            // sidePane.setPrefWidth(sidePane.getPrefWidth() + delta;
        }
    }

    public static class NewResizeHeightChange implements ChangeListener<Number> {
        private final AnchorPane sidePane;
        private final ScrollPane squadsPane;
        private final StackPane newCharHold;

        /**
         * Constructor for height change
         * @param sidePane
         */
        public NewResizeHeightChange(@SuppressWarnings("exports") AnchorPane sidePane) {
            this.sidePane = sidePane;
            squadsPane = (ScrollPane) sidePane.lookup("#squadsPane");
            newCharHold = (StackPane) sidePane.lookup("#newCharButtonHold");
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

            sidePane.setPrefHeight(sidePane.getPrefHeight() + delta);

            squadsPane.setPrefHeight(squadsPane.getPrefHeight() + delta);

            newCharHold.setLayoutY(newCharHold.getLayoutY() + delta);
        }
    }
}