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
    private ListView preparedToDelete;
    ObservableList<String> deleteList = FXCollections.observableArrayList();
    ObservableList<String> filteredItems = FXCollections.observableArrayList();
    ObservableList<FilePane> filePanes = FXCollections.observableArrayList();
    String pathExtender = MainController.getPath()+File.separator;

    @FXML
    CheckBox deleteCopies;


    @FXML
    protected void onConfirmButtonClick() {
        if (deleteCopies.isSelected()) {
            for (String fileToDeletePath : deleteList) {
                File fileToDelete = new File(pathExtender + fileToDeletePath);
                String fileToDeleteName = fileToDelete.getName();
                fileToDeleteName = fileToDeleteName.substring(0, fileToDeleteName.length() - MainController.mainExpansiveCopy.getLength()) + MainController.secondaryExpansiveCopy.getText();
                if (fileToDelete.getName().endsWith(MainController.mainExpansiveCopy.getText())){
                    for (FilePane secondaryFile : filePanes) {
                        String secondaryFileName = secondaryFile.getText();
                        int start = secondaryFileName.lastIndexOf(File.separator);
                        secondaryFileName = secondaryFileName.substring(start + 1);
                        if (secondaryFileName.equals(fileToDeleteName)) {
                            File delCopy = new File(pathExtender + secondaryFile.getText());
                            delCopy.delete();
                        }
                        ;
                    }
                }
                fileToDelete.delete();
            }
        } else {
            for (String filetoDeletePath : deleteList) {
                File fileToDelete = new File(pathExtender + filetoDeletePath);
                fileToDelete.delete();
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
        filePanes.addAll(MainController.getMainList().getItems());
        filePanes.addAll(MainController.getSecondaryList().getItems());
        for (FilePane file : filePanes) {
            if (file.getCheck()) deleteList.add(file.getText());
        }
        filePanes.clear();
        filePanes.addAll(MainController.getSecondaryList().getItems());
        for (FilePane file : filePanes) {
            String text = file.getText();
            int start = text.lastIndexOf(File.separator);
            filteredItems.add(text.substring(start + 1));
        }
        preparedToDelete.getItems().setAll(deleteList);
    }
}
