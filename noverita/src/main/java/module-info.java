module com.aeondromach {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.aeondromach to javafx.fxml;
    exports com.aeondromach;
}
