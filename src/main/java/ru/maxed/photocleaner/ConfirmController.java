package ru.maxed.photocleaner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class ConfirmController {
    @FXML
    private Button cancel;

    @FXML
    private ListView onDeleteList;
    ObservableList<String> items = FXCollections.observableArrayList("123","333");

    @FXML
    protected void onConfirmButtonClick(){

        onDeleteList.getItems().setAll(items);
    }

    @FXML
    protected void onCancelButtonClick() {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
}
