package ru.maxed.photocleaner.ui.desktop.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import ru.maxed.photocleaner.MainApplication;
import ru.maxed.photocleaner.core.entities.CheckedFile;
import ru.maxed.photocleaner.core.exeptions.TestException;
import ru.maxed.photocleaner.core.services.FileListCleaner;
import ru.maxed.photocleaner.ui.desktop.ErrorStage;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmController implements Initializable {
    @FXML
    private Button cancel;

    @FXML
    private ListView<String> preparedToDelete;

    @FXML
    protected void onConfirmButtonClick() {
        try {
            FileListCleaner.clean(MainApplication.processedFileList);
        } catch (TestException e){
            new ErrorStage(e.getMessage());
        }
        try {
            FileListCleaner.clean(MainApplication.originFileList);
        } catch (TestException e) {
            new ErrorStage(e.getMessage());
        }
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onCancelButtonClick() {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        preparedToDelete.getItems().clear();
        for (CheckedFile file :
                MainApplication.originFileList) {
            if (file.isMustDelete()) preparedToDelete.getItems().add(file.getPathFromStartDir());
        }
        for (CheckedFile file :
                MainApplication.processedFileList) {
            if (file.isMustDelete()) preparedToDelete.getItems().add(file.getPathFromStartDir());
        }

    }
}
