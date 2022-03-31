package ru.maxed.photocleaner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainController{

    @FXML
    private ListView list;
    ObservableList<String> items = FXCollections.observableArrayList();
    @FXML
    private  ListView filteredList;

    @FXML
    private Button test;
    private int counter = 0;

    @FXML
    private TextField input;
    @FXML
    private TextField mainExpansive;
    @FXML
    private TextField secondaryExpansive;

    @FXML
    private Label text;
    @FXML
    private Label pathToFiles;

    @FXML
    protected void onOpenButtonClick() {
        counter++;
        String path = input.getText();
        if (list != null)  list.getItems().clear();
        if (filteredList != null) filteredList.getItems().clear();
        Directory dir = new Directory(path);
        if (dir.isDirectory()){
            pathToFiles.setText(path);
            items = dir.getFileList(dir.getAbsolutePath(),secondaryExpansive.getText()).sorted();
            if (items != null) {
                list.getItems().addAll(items);
                list.setCellFactory(param -> new CheckListCell());
            }
            items = dir.getFileList(dir.getAbsolutePath(),mainExpansive.getText()).sorted();
            if (items != null) {
                filteredList.getItems().addAll(items);
                filteredList.setCellFactory(param -> new CheckListCell());
            }
        } else {
            text.setText("Неправильный путь, введите путь заного");
        }
        test.setText("Открыть. (Нажато " + counter + " раз.)");
    }
}