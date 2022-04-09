package ru.maxed.photocleaner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

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

    @FXML
    private TextField input;
    @FXML
    private TextField mainExpansive;
    public static TextField mainExpansiveCopy;
    @FXML
    private TextField secondaryExpansive;
    public static TextField secondaryExpansiveCopy;

    @FXML
    private Label pathToFiles;
    private static Label pathToFilesCopy;


    @FXML
    protected void onOpenButtonClick() {
        filteredList.getItems().clear();
        list.getItems().clear();
        pathToFiles.setText("Путь к файлам");
        if (secondaryExpansive.getText().equals("") || mainExpansive.getText().equals("")){
            new ErrorStage("Пожалуйста, введите необходимые расширения файлов.");
            return;
        }
        if (secondaryExpansive.getText().equals(mainExpansive.getText())){
            new ErrorStage("Расширение файлов не может быть одинаковым");
            return;
        }
        String path = input.getText();
        Directory dir = new Directory(path);
        if (dir.isDirectory()) {
            pathToFiles.setText(path);
            items.clear();
            stringItems = dir.getFileList(dir.getAbsolutePath(), secondaryExpansive.getText()).sorted();
            for (String fileName : stringItems) {
                FilePane pane = new FilePane(fileName);
                items.add(pane);
            }
            if (items != null) {
                list.setCellFactory(param -> new CheckListCell());
                list.getItems().setAll(items);

            }
            items.clear();
            stringItems = dir.getFileList(dir.getAbsolutePath(), mainExpansive.getText()).sorted();
            for (String fileName : stringItems) {
                items.add(new FilePane(fileName));
            }
            if (items != null) {
                filteredList.getItems().setAll(items);
                filteredList.setCellFactory(param -> new CheckListCell());
            }
        } else {
           new ErrorStage("Неправильный путь, введите путь заного");
        }
    }

    @FXML
    protected void listClickHandle(MouseEvent arg0) {
        CheckListCell cell = new CheckListCell();
        if (arg0.getTarget().getClass() == cell.getClass()) {
            cell = (CheckListCell) arg0.getTarget();
           // System.out.println(arg0.getTarget());
           // System.out.println(cell);
        }
    }

    @FXML
    protected void onCopyDeleteButtonClick() {
        for (FilePane copyOfFile : list.getItems()) {
            File copiedFile = new File(pathToFiles.getText() + "\\" + copyOfFile.getText());
            String searching = copiedFile.getName();
            searching = searching.substring(0, searching.length() - secondaryExpansive.getLength()) + mainExpansive.getText();
            boolean mustDelete = true;
            for (FilePane mainFile : filteredList.getItems()) {
                String copyText = mainFile.getText();
                int start = copyText.lastIndexOf(File.separator);
                copyText = copyText.substring(start + 1);
                if (copyText.equals(searching)) {
                    mustDelete = false;
                }
                ;
            }
            if (mustDelete) {
                copyOfFile.setCheck(true);
            }
            ;
        }
    }

    @FXML
    protected void onDeleteButtonClick() throws IOException {
        filteredListCopy = filteredList;
        listCopy = list;
        pathToFilesCopy = pathToFiles;
        secondaryExpansiveCopy = secondaryExpansive;
        mainExpansiveCopy = mainExpansive;
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
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        input.setText("C:\\Users\\vvmax\\Downloads\\wireless-industry-dev-1.7.10-main\\PhotoCleaner\\TestingDir");
        secondaryExpansive.setText(".bmp");
         mainExpansive.setText(".png");
    }

    public static ListView<FilePane> getFilteredList() {
        return filteredListCopy;
    }

    public static ListView<FilePane> getList() {
        return listCopy;
    }

    public static String getPath() {
        return pathToFilesCopy.getText();
    }

}