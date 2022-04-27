package ru.maxed.photocleaner.ui.desktop;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;

public class ErrorAlert extends Alert {
    public ErrorAlert(String contentText) {
        super(AlertType.ERROR, contentText, ButtonType.OK);
        this.setHeaderText(contentText);
        this.setContentText(null);
        this.setTitle("Ошибка");
        InputStream iconStream = getClass().getResourceAsStream("/ru/maxed/photocleaner/icon.png");
        assert iconStream != null;
        Image image = new Image(iconStream);
        Stage stage = (Stage) this.getDialogPane().getScene().getWindow();
        stage.getIcons().add(image);
        this.showAndWait();
    }

}
