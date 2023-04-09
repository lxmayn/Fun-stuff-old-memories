module com.timeline {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.timeline to javafx.fxml;
    exports com.timeline;
}
