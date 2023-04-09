module com.timeline {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.timeline to javafx.fxml;
    opens com.timeline.controllers to javafx.fxml;
    opens com.timeline.models to javafx.fxml, javafx.base;
    opens com.timeline.daos to javafx.fxml;

    exports com.timeline;
    exports com.timeline.controllers;
}
