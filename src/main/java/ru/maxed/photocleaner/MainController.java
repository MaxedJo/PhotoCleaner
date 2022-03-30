package ru.maxed.photocleaner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController{

    @FXML
    private ListView<String> list;
    ObservableList<String> items = FXCollections.observableArrayList();
    @FXML
    private  ListView<String> filteredList;

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
        Directory dir = new Directory(path);
        if (dir.isDirectory()){
            pathToFiles.setText(path);
            items = dir.getFileList();
            list.getItems().addAll(items);
            items = dir.getFileList(".bmp");
            filteredList.getItems().addAll(items);
        } else {
            text.setText("Неправильный путь, введите путь заного");
        }
        test.setText("Открыть. (Нажато " + counter + " раз.)");
    }
}