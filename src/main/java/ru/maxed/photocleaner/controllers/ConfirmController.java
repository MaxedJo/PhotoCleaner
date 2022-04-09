package ru.maxed.photocleaner.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import ru.maxed.photocleaner.entities.FilePane;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmController implements Initializable {
    @FXML
    private Button cancel;

    @FXML
    private ListView onDeleteList;
    ObservableList<String> items = FXCollections.observableArrayList();
    ObservableList<String> filteredItems = FXCollections.observableArrayList();
    ObservableList<FilePane> test = FXCollections.observableArrayList();
    String path = MainController.getPath();

    @FXML
    CheckBox deleteCopies;


    @FXML
    protected void onConfirmButtonClick() {
        if (deleteCopies.isSelected()) {
            for (String str : items) {
                File file = new File(path + File.separator + str);
                String text = file.getName();
                text = text.substring(0, text.length() - MainController.mainExpansiveCopy.getLength()) + MainController.secondaryExpansiveCopy.getText();
                for (FilePane copy : test) {
                    String copyText = copy.getText();
                    int start = copyText.lastIndexOf(File.separator);
                    copyText = copyText.substring(start + 1);
                    if (copyText.equals(text)) {
                        File delCopy = new File(path + File.separator + copy.getText());
                        delCopy.delete();
                    }
                    ;

                }
                file.delete();
            }
        } else {
            for (String str : items) {
                File file = new File(path + File.separator + str);
                file.delete();
            }
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

        test.addAll(MainController.getFilteredList().getItems());
        test.addAll(MainController.getList().getItems());
        for (FilePane file : test) {
            if (file.getCheck()) items.add(file.getText());
        }
        test = MainController.getList().getItems();
        for (FilePane file : test) {
            String text = file.getText();
            int start = text.lastIndexOf(File.separator);
            filteredItems.add(text.substring(start + 1));
        }
        onDeleteList.getItems().setAll(items);
    }
}
