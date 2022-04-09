package ru.maxed.photocleaner.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.WindowEvent;
import ru.maxed.photocleaner.entities.ErrorStage;
import ru.maxed.photocleaner.entities.FilePane;
import ru.maxed.photocleaner.entities.PhotoStage;
import ru.maxed.photocleaner.utility.Directory;
import ru.maxed.photocleaner.utility.Settings;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private ListView<FilePane> secondaryList;
    private static ListView<FilePane> secondaryListCopy;
    ObservableList<FilePane> items = FXCollections.observableArrayList();
    ObservableList<String> stringItems = FXCollections.observableArrayList();
    @FXML
    private ListView<FilePane> mainList;
    private static ListView<FilePane> mainListCopy;

    @FXML
    private Button openButton;
    @FXML
    private Button pathChooser;

    @FXML
    private TextField pathInput;
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
        mainList.getItems().clear();
        secondaryList.getItems().clear();
        pathToFiles.setText("Путь к файлам");
        if (secondaryExpansive.getText().equals("") || mainExpansive.getText().equals("")) {
            new ErrorStage("Пожалуйста, введите необходимые расширения файлов.");
            return;
        }
        if (secondaryExpansive.getText().equals(mainExpansive.getText())) {
            new ErrorStage("Расширение файлов не может быть одинаковым");
            return;
        }
        String path = pathInput.getText();
        Directory dir = new Directory(path);
        if (!dir.isDirectory()) {
            new ErrorStage("Неправильный путь, введите путь заного");
            return;
        }
        pathToFiles.setText(dir.getAbsolutePath());
        items.clear();
        stringItems = dir.getFileList(dir.getAbsolutePath(), secondaryExpansive.getText()).sorted();
        for (String fileName : stringItems) {
            FilePane pane = new FilePane(fileName);
            items.add(pane);
        }
        if (items != null) {
            secondaryList.getItems().setAll(items);
        }
        items.clear();
        stringItems = dir.getFileList(dir.getAbsolutePath(), mainExpansive.getText()).sorted();
        for (String fileName : stringItems) {
            items.add(new FilePane(fileName));
        }
        if (items != null) {
            mainList.getItems().setAll(items);
        }

    }
    private FilePane pane = new FilePane();
    @FXML
    protected void listClickHandle(MouseEvent arg0) {
         if (arg0.getTarget().getClass() == pane.getClass()){
             pane = (FilePane) arg0.getTarget();
             pane.changeCheck();
         }
    }

    @FXML
    protected void onMarkFilesWithoutOriginButtonClick() {
        for (FilePane copyOfFile : secondaryList.getItems()) {
            File copiedFile = new File(pathToFiles.getText() + File.separator + copyOfFile.getText());
            String searching = copiedFile.getName();
            searching = searching.substring(0, searching.length() - secondaryExpansive.getLength()) + mainExpansive.getText();
            boolean mustDelete = true;
            for (FilePane mainFile : mainList.getItems()) {
                String copyText = mainFile.getText();
                int start = copyText.lastIndexOf(File.separator);
                copyText = copyText.substring(start + 1);
                if (copyText.equals(searching)) {
                    mustDelete = false;
                };
            }
            if (mustDelete) {
                copyOfFile.setCheck(true);
            };
        }
    }

    @FXML
    protected void onPathChooserClick() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(pathChooser.getScene().getWindow());
        if (file != null) pathInput.setText(file.getAbsolutePath());
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
        mainListCopy = mainList;
        secondaryListCopy = secondaryList;
        pathToFilesCopy = pathToFiles;
        secondaryExpansiveCopy = secondaryExpansive;
        mainExpansiveCopy = mainExpansive;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File settingsFile = new File("settings.cfg");
        if (settingsFile.exists()) {
            Settings settings = new Settings(settingsFile);
            pathInput.setText(settings.getPath());
            secondaryExpansive.setText(settings.getSecondaryExpansive());
            mainExpansive.setText(settings.getMainExpansive());
        }
    }


    public static ListView<FilePane> getMainList() {
        return mainListCopy;
    }

    public static ListView<FilePane> getSecondaryList() {
        return secondaryListCopy;
    }

    public static String getPath() {
        return pathToFilesCopy.getText();
    }

    private javafx.event.EventHandler<WindowEvent> closeEventHandler = new javafx.event.EventHandler<WindowEvent>() {
        @Override
        public void handle(WindowEvent event) {
            Settings settings = new Settings(pathInput.getText(), mainExpansive.getText(), secondaryExpansive.getText());
            settings.save();
        }
    };


    public javafx.event.EventHandler<WindowEvent> getCloseEventHandler() {
        return closeEventHandler;
    }

}