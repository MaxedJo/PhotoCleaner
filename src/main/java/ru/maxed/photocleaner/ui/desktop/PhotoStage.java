package ru.maxed.photocleaner.ui.desktop;

import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;

public class PhotoStage extends Stage {
    public PhotoStage() {
        InputStream iconStream = getClass().getResourceAsStream("/ru/maxed/photocleaner/images/icon.png");
        assert iconStream != null;
        Image image = new Image(iconStream);
        this.setTitle("Photo cleaner");
        this.getIcons().add(image);
    }
}
