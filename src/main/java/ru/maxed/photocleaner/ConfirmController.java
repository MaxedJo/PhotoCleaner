package ru.maxed.photocleaner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmController implements Initializable {
    @FXML
    private Button cancel;

    @FXML
    private ListView onDeleteList ;
    ObservableList<String> items = FXCollections.observableArrayList();
    ObservableList<FilePane> test = FXCollections.observableArrayList();
    String path = MainController.getPath();



    @FXML
    protected void onConfirmButtonClick(){
        for (String str:items ) {
            File file = new File(path+"\\"+ str);
            file.delete();
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
        test=MainController.getFilteredList().getItems();
        for (FilePane file:test) {
            if (file.getCheck()) items.add(file.getText());
        }
        onDeleteList.getItems().setAll(items);
    }
}
