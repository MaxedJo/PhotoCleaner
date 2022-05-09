package ru.maxed.photocleaner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.maxed.photocleaner.core.entities.CheckedFile;
import ru.maxed.photocleaner.core.utility.Settings;
import ru.maxed.photocleaner.ui.desktop.components.PhotoStage;
import ru.maxed.photocleaner.ui.desktop.controllers.MainController;

import java.io.IOException;

/**
 * Главный класс оконной версии приложения.
 */
public final class MainApplication extends Application {
    /**
     * Минимальна ширина окна приложения.
     */
    private static final int MIN_WIDTH = 400;
    /**
     * Минимальная высота окна приложения.
     */
    private static final int MIN_HEIGHT = 350;
    /**
     * Главный список для хранения "оригинальных" файлов.
     */
    private static final ObservableList<CheckedFile> ORIGIN_FILE_LIST =
            FXCollections.observableArrayList();
    /**
     * Главный список для хранение файлов "для обработки".
     */
    private static final ObservableList<CheckedFile> PROCESSED_FILE_LIST =
            FXCollections.observableArrayList();

    /**
     * Запуск оконной версии приложения.
     *
     * @param args Параметры запуска
     */
    public static void main(final String[] args) {
        launch();
    }

    /**
     * Геттер главного списка файлов для обработки.
     *
     * @return Список файлов для обработки
     */
    public static ObservableList<CheckedFile> getProcessedFileList() {
        return PROCESSED_FILE_LIST;
    }

    /**
     * Геттер главного списка оригинальных файлов.
     *
     * @return Список оригинальных файлов
     */
    public static ObservableList<CheckedFile> getOriginFileList() {
        return ORIGIN_FILE_LIST;
    }

    /**
     * Старт оконной версии приложения.
     *
     * @see Application#start(Stage)
     */
    @Override
    public void start(final Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/ru/maxed/photocleaner/fxml/main-view.fxml"
        ));
        fxmlLoader.setResources(Settings.getLangBundle());
        Scene scene = new Scene(fxmlLoader.load());
        PhotoStage.cast(stage);
        stage.setScene(scene);
        MainController controller = fxmlLoader.getController();
        stage.setOnCloseRequest(controller.getCloseEventHandler());
        stage.setMinWidth(MIN_WIDTH);
        stage.setMinHeight(MIN_HEIGHT);
        stage.show();
    }
}
