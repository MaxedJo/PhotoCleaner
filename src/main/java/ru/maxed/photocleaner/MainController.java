package ru.maxed.photocleaner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;


import java.io.IOException;
import java.io.InputStream;

public class MainController{

    @FXML
    private ListView<FilePane> list;
    private static ListView<FilePane> listCopy;
    ObservableList<FilePane> items = FXCollections.observableArrayList();
    ObservableList<String> stringItems = FXCollections.observableArrayList();
    @FXML
    private ListView<FilePane> filteredList;
    private static ListView<FilePane> filteredListCopy;

    @FXML
    private Button openButton;
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
    private static Label pathToFilesCopy;



    @FXML
    protected void onOpenButtonClick() {
        counter++;
        String path = input.getText();
        Directory dir = new Directory(path);
        if (dir.isDirectory()){
            pathToFiles.setText(path);
            stringItems = dir.getFileList(dir.getAbsolutePath(),secondaryExpansive.getText()).sorted();
            for (String fileName:stringItems) {
                FilePane pane = new FilePane(fileName);
                items.add( pane);
            }
            if (items != null) {
                list.setCellFactory(param -> new CheckListCell());
                list.getItems().setAll(items);

            }
            items.clear();
            stringItems = dir.getFileList(dir.getAbsolutePath(),mainExpansive.getText()).sorted();
            for (String fileName:stringItems) {
                items.add( new FilePane(fileName));
            }
            if (items != null) {
                filteredList.getItems().setAll(items);
                filteredList.setCellFactory(param -> new CheckListCell());
            }
        } else {
            text.setText("Неправильный путь, введите путь заного");
        }
        openButton.setText("Открыть. (Нажато " + counter + " раз.)");
    }

    @FXML
    protected  void listClickHandle(MouseEvent arg0){
        CheckListCell cell = new CheckListCell();
        if (arg0.getTarget().getClass() == cell.getClass()){
            cell = (CheckListCell) arg0.getTarget();
            System.out.println(arg0.getTarget());
            System.out.println(cell);
        }
    }

    @FXML
    protected void onDeleteButtonClick() throws IOException {
        filteredListCopy = filteredList;
        listCopy = list;
        pathToFilesCopy = pathToFiles;
        Stage confirmWindow = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("confirm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        confirmWindow.setScene(scene);
        InputStream iconStream = getClass().getResourceAsStream("icon.png");
        assert iconStream != null;
        Image image = new Image(iconStream);
        confirmWindow.setTitle("Подтвердите удаление");
        confirmWindow.getIcons().add(image);
        confirmWindow.show();

        System.out.println(listCopy.getItems());
    }

    public static ListView<FilePane> getFilteredList() {
        return filteredListCopy;
    }
    public static ListView<FilePane> getList() {
        return listCopy;
    }
    public static String getPath(){
        return pathToFilesCopy.getText();
    }

}