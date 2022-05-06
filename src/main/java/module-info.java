/**
 * Главный модуль приложения для обработки файлов фотографий.
 */
module ru.maxed.photocleaner {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.maxed.photocleaner to javafx.fxml;
    exports ru.maxed.photocleaner;
    exports ru.maxed.photocleaner.ui.desktop.controllers;
    opens ru.maxed.photocleaner.ui.desktop.controllers to javafx.fxml;
    opens ru.maxed.photocleaner.core.utility to javafx.fxml;
    exports ru.maxed.photocleaner.core.utility;
    exports ru.maxed.photocleaner.core.entities;
    exports ru.maxed.photocleaner.core.exeptions;
    opens ru.maxed.photocleaner.core.entities to javafx.fxml;
    exports ru.maxed.photocleaner.ui.desktop.components;
    opens ru.maxed.photocleaner.ui.desktop.components to javafx.fxml;
    exports ru.maxed.photocleaner.ui.desktop.services;
    opens ru.maxed.photocleaner.ui.desktop.services to javafx.fxml;
    exports ru.maxed.photocleaner.ui.desktop.services.filecount;
    opens ru.maxed.photocleaner.ui.desktop.services.filecount to javafx.fxml;
}