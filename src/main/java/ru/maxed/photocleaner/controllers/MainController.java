package ru.maxed.photocleaner.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.maxed.photocleaner.entities.CheckListCell;
import ru.maxed.photocleaner.entities.ErrorStage;
import ru.maxed.photocleaner.entities.FilePane;
import ru.maxed.photocleaner.entities.PhotoStage;
import ru.maxed.photocleaner.utility.Directory;
import ru.maxed.photocleaner.utility.Settings;


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
    private Button pathChooser;

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
        if (secondaryExpansive.getText().equals("") || mainExpansive.getText().equals("")) {
            new ErrorStage("Пожалуйста, введите необходимые расширения файлов.");
            return;
        }
        if (secondaryExpansive.getText().equals(mainExpansive.getText())) {
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
//             System.out.println(arg0.getTarget());
//             System.out.println(cell);
        }
    }

    @FXML
    protected void onCopyDeleteButtonClick() {
        for (FilePane copyOfFile : list.getItems()) {
            File copiedFile = new File(pathToFiles.getText() + File.separator + copyOfFile.getText());
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
    protected void onPathChooserClick() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(pathChooser.getScene().getWindow());
        if (file != null) input.setText(file.getAbsolutePath());
    }

    @FXML
    protected void onDeleteButtonClick() throws IOException {
        copyStatic();
        PhotoStage confirmWindow = new PhotoStage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ru/maxed/photocleaner/fxml/confirm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        confirmWindow.setScene(scene);
        confirmWindow.setTitle("Подтвердите удаление");
        confirmWindow.show();
    }

    private void copyStatic() {
        filteredListCopy = filteredList;
        listCopy = list;
        pathToFilesCopy = pathToFiles;
        secondaryExpansiveCopy = secondaryExpansive;
        mainExpansiveCopy = mainExpansive;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File settingsFile = new File("settings.cfg");
        if (settingsFile.exists()) {
            Settings settings = new Settings(settingsFile);
            input.setText(settings.getPath());
            secondaryExpansive.setText(settings.getSecondaryExpansive());
            mainExpansive.setText(settings.getMainExpansive());
        }
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


    private javafx.event.EventHandler<WindowEvent> closeEventHandler = new javafx.event.EventHandler<WindowEvent>() {
        @Override
        public void handle(WindowEvent event) {
            Settings settings = new Settings(input.getText(), mainExpansive.getText(), secondaryExpansive.getText());
            settings.save();
        }
    };


    public javafx.event.EventHandler<WindowEvent> getCloseEventHandler() {
        return closeEventHandler;
    }

}