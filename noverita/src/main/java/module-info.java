module com.aeondromach {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;

    opens com.aeondromach to javafx.fxml;
    exports com.aeondromach;
}