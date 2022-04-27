package ru.maxed.photocleaner.ui.desktop.controllers;

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
import ru.maxed.photocleaner.MainApplication;
import ru.maxed.photocleaner.core.entities.CheckedFile;
import ru.maxed.photocleaner.core.exeptions.TestException;
import ru.maxed.photocleaner.core.services.*;
import ru.maxed.photocleaner.core.utility.Settings;
import ru.maxed.photocleaner.ui.desktop.ErrorAlert;
import ru.maxed.photocleaner.ui.desktop.ErrorStage;
import ru.maxed.photocleaner.ui.desktop.FilePane;
import ru.maxed.photocleaner.ui.desktop.PhotoStage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private ListView<FilePane> processedFileList;

    @FXML
    private ListView<FilePane> originFileList;

    @FXML
    private Button pathChooser;

    @FXML
    private TextField pathInput;
    @FXML
    private TextField originExtension;
    @FXML
    private TextField processedExtension;

    @FXML
    private Label pathToFiles;


    @FXML
    protected void onOpenButtonClick() {
        MainApplication.originFileList.clear();
        MainApplication.processedFileList.clear();
        originFileList.getItems().clear();
        processedFileList.getItems().clear();
        try {
            ExtensionValidator.validate(originExtension.getText(), processedExtension.getText());
        } catch (TestException e) {
            new ErrorStage(e.getMessage());
            return;
        }
        String path = pathInput.getText();
        File dir = new File(path);
        if (!dir.isDirectory()) {
            new ErrorAlert("Неправильный путь,введите путь заного");
            return;
        }
        CheckedFile.setMainPath(path);
        pathToFiles.setText(dir.getAbsolutePath());
        try {
            DirectoryReader.read(dir.getAbsolutePath(), originExtension.getText(), processedExtension.getText(),
                    MainApplication.processedFileList, MainApplication.originFileList);
        } catch (TestException e) {
            new ErrorAlert(e.getMessage());
        }
        for (CheckedFile file : MainApplication.originFileList) {
            FilePane filePane = new FilePane(file, originFileList);
            originFileList.getItems().add(filePane);
        }
        for (CheckedFile file : MainApplication.processedFileList) {
            FilePane filePane = new FilePane(file, processedFileList);
            processedFileList.getItems().add(filePane);
        }
    }

    private FilePane pane = new FilePane();

    @FXML
    protected void listClickHandle(MouseEvent arg0) {
        if (arg0.getTarget().getClass() == pane.getClass()) {
            pane = (FilePane) arg0.getTarget();
            pane.changeCheck();
        }
    }

    @FXML
    protected void onMarkFilesWithoutOriginButtonClick() {
        FileListComparator.compareLists(MainApplication.processedFileList, MainApplication.originFileList);
    }

    @FXML
    protected void onCopySelectButtonClick() {
        CopyOfOriginSelector.selectSame(MainApplication.processedFileList, MainApplication.originFileList);
    }

    @FXML
    protected void onClearSelectionButtonClick() {
        MarkCleaner.clean(MainApplication.originFileList);
        MarkCleaner.clean(MainApplication.processedFileList);
    }

    @FXML
    protected void onPathChooserClick() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(pathChooser.getScene().getWindow());
        if (file != null) pathInput.setText(file.getAbsolutePath());
    }

    @FXML
    protected void onDeleteButtonClick() throws IOException {
        PhotoStage confirmWindow = new PhotoStage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ru/maxed/photocleaner/fxml/confirm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        confirmWindow.setScene(scene);
        confirmWindow.setTitle("Подтвердите удаление");
        confirmWindow.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File settingsFile = new File("settings.cfg");
        if (settingsFile.exists()) {
            Settings settings = new Settings(settingsFile);
            pathInput.setText(settings.getPath());
            processedExtension.setText(settings.getSecondaryExpansive());
            originExtension.setText(settings.getMainExpansive());
        }
    }


    private final javafx.event.EventHandler<WindowEvent> closeEventHandler = event -> {
        Settings settings = new Settings(pathInput.getText(), originExtension.getText(), processedExtension.getText());
        settings.save();
    };

    public javafx.event.EventHandler<WindowEvent> getCloseEventHandler() {
        return closeEventHandler;
    }

}