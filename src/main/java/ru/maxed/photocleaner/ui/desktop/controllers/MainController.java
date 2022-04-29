package ru.maxed.photocleaner.ui.desktop.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import ru.maxed.photocleaner.MainApplication;
import ru.maxed.photocleaner.core.entities.CheckedFile;
import ru.maxed.photocleaner.core.exeptions.TestException;
import ru.maxed.photocleaner.core.services.*;
import ru.maxed.photocleaner.core.utility.Settings;
import ru.maxed.photocleaner.ui.desktop.ConfirmationAlert;
import ru.maxed.photocleaner.ui.desktop.ErrorAlert;
import ru.maxed.photocleaner.ui.desktop.FilePane;

import java.io.File;
import java.net.URL;
import java.util.Optional;
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
    private Tooltip deleteTooltip;
    @FXML
    private Tooltip clearTooltip;
    @FXML
    private Tooltip copyTooltip;
    @FXML
    private Tooltip cleanTooltip;
    @FXML
    private  Button openButton;
    @FXML
    private Tooltip openTooltip;
    private TextField[] inputs ;
    @FXML
    protected void onEnter(){
        boolean mustOpen = true;
        for (int i = 0; i < inputs.length; i++) {
            if (inputs[i%inputs.length].getText().equals("")){
                inputs[i%inputs.length].requestFocus();
                mustOpen = false;
            }
        }
        if (mustOpen) onOpenButtonClick();
    }

    @FXML
    protected void onOpenButtonClick() {
        try {
            MainApplication.originFileList.clear();
            MainApplication.processedFileList.clear();
            originFileList.getItems().clear();
            processedFileList.getItems().clear();
            ExtensionValidator.validate(originExtension.getText(), processedExtension.getText());
            String path = pathInput.getText();
            File dir = new File(path);
            if (!dir.isDirectory()) {
                throw new TestException("Неправильный путь,введите путь заного");
            }
            CheckedFile.setMainPath(path);
            DirectoryReader.read(dir.getAbsolutePath(), originExtension.getText(), processedExtension.getText(),
                    MainApplication.processedFileList, MainApplication.originFileList, true);
            for (CheckedFile file : MainApplication.originFileList) {
                FilePane filePane = new FilePane(file, originFileList);
                originFileList.getItems().add(filePane);
            }
            for (CheckedFile file : MainApplication.processedFileList) {
                FilePane filePane = new FilePane(file, processedFileList);
                processedFileList.getItems().add(filePane);
            }
            openButton.getStyleClass().remove("open-button-start");
            openButton.getStyleClass().add("open-button-refresh");
        } catch (TestException e) {
            new ErrorAlert(e.getMessage());
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
        if (file != null) {
            pathInput.setText(file.getAbsolutePath());
            onEnter();
        }
    }

    @FXML
    protected void onDeleteButtonClick() {
        ConfirmationAlert alert = new ConfirmationAlert();
        Optional<ButtonType> option = alert.showAndWait();
        if (option.isPresent() && option.get() == ButtonType.OK) {
            try {
                FileListCleaner.clean(MainApplication.processedFileList);
                FileListCleaner.clean(MainApplication.originFileList);
            } catch (TestException e) {
                new ErrorAlert(e.getMessage());
            }
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inputs = new TextField[]{pathInput,originExtension,processedExtension};
        openButton.getStyleClass().add("open-button-start");
        deleteTooltip.setShowDelay(new Duration(100));
        clearTooltip.setShowDelay(new Duration(100));
        copyTooltip.setShowDelay(new Duration(100));
        cleanTooltip.setShowDelay(new Duration(100));
        openTooltip.setShowDelay(new Duration(100));
        pathInput.setPromptText("Введите путь");
        originExtension.setPromptText("Эталонное расширение");
        processedExtension.setPromptText("Расширение для обработки");
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