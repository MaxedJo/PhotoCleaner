package ru.maxed.photocleaner.ui.desktop;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ru.maxed.photocleaner.MainApplication;
import ru.maxed.photocleaner.core.entities.CheckedFile;

import java.io.InputStream;



public class ConfirmationAlert extends Alert {

    public ConfirmationAlert() {
        super(AlertType.CONFIRMATION);
        ListView<String> preparedToDelete = new ListView<>();
        preparedToDelete.setMaxHeight(10);
        InputStream iconStream = getClass().getResourceAsStream("/ru/maxed/photocleaner/images/icon.png");
        assert iconStream != null;
        Image image = new Image(iconStream);
        Stage stage = (Stage) this.getDialogPane().getScene().getWindow();
        stage.getIcons().add(image);
        this.setTitle("Подтверждение");
        this.setHeaderText("Вы действительно хотите удалить данные файлы?");
        Button cancelButton = (Button) this.getDialogPane().lookupButton( ButtonType.CANCEL );
        cancelButton.setText("Oтмена");
        preparedToDelete.getItems().clear();
        for (CheckedFile file :
                MainApplication.originFileList) {
            if (file.isMustDelete()) preparedToDelete.getItems().add(file.getPathFromStartDir());
        }
        for (CheckedFile file :
                MainApplication.processedFileList) {
            if (file.isMustDelete()) preparedToDelete.getItems().add(file.getPathFromStartDir());
        }
        this.getDialogPane().setContent(preparedToDelete);
    }
}
