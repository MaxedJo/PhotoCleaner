package ru.maxed.photocleaner.ui.desktop.components;

import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;

/**
 * Изменнёное окно приложения.
 */
public class PhotoStage extends Stage {
    /**
     * Создание объекта окна.
     */
    public PhotoStage() {
        InputStream iconStream = getClass().getResourceAsStream(
                "/ru/maxed/photocleaner/images/icon.png"
        );
        assert iconStream != null;
        Image image = new Image(iconStream);
        this.setTitle("Photo cleaner");
        this.getIcons().add(image);
    }

    /**
     * Приведение окна к используемому виду.
     *
     * @param stage Изменяемое окно
     */
    public static void cast(final Stage stage) {
        InputStream iconStream = PhotoStage.class.getResourceAsStream(
                "/ru/maxed/photocleaner/images/icon.png"
        );
        assert iconStream != null;
        Image image = new Image(iconStream);
        stage.setTitle("Photo cleaner");
        stage.getIcons().add(image);
    }
}
