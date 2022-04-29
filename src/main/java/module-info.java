module ru.maxed.photocleaner {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.junit.jupiter.api;

    opens ru.maxed.photocleaner to javafx.fxml;
    exports ru.maxed.photocleaner;
    exports ru.maxed.photocleaner.ui.desktop.controllers;
    opens ru.maxed.photocleaner.ui.desktop.controllers to javafx.fxml;
    opens ru.maxed.photocleaner.core.utility to javafx.fxml;
    exports ru.maxed.photocleaner.core.utility;
    exports ru.maxed.photocleaner.ui.desktop;
    opens ru.maxed.photocleaner.ui.desktop to javafx.fxml;
    exports ru.maxed.photocleaner.core.entities;
    opens ru.maxed.photocleaner.core.entities to javafx.fxml;
}