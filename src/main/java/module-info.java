module ru.maxed.photocleaner {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.junit.jupiter.api;

    opens ru.maxed.photocleaner to javafx.fxml;
    exports ru.maxed.photocleaner;
    exports ru.maxed.photocleaner.controllers;
    opens ru.maxed.photocleaner.controllers to javafx.fxml;
    exports ru.maxed.photocleaner.entities;
    opens ru.maxed.photocleaner.entities to javafx.fxml;
    opens ru.maxed.photocleaner.utility to javafx.fxml;
    exports ru.maxed.photocleaner.utility;
}