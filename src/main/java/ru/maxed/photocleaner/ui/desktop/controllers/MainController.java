package ru.maxed.photocleaner.ui.desktop.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import ru.maxed.photocleaner.ui.desktop.Counter;
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
    private Button deleteButton;

    @FXML
    private TextField pathInput;
    @FXML
    private TextField originExtension;
    @FXML
    private TextField processedExtension;
    private final javafx.event.EventHandler<WindowEvent> closeEventHandler = event -> {
        Settings settings = new Settings(pathInput.getText(), originExtension.getText(), processedExtension.getText());
        settings.save();
    };
    @FXML
    private Tooltip deleteTooltip;
    @FXML
    private Tooltip clearTooltip;
    @FXML
    private Tooltip copyTooltip;
    @FXML
    private Tooltip cleanTooltip;
    @FXML
    private Tooltip originFilterTooltip;
    @FXML
    private Tooltip processedFilterTooltip;
    @FXML
    private Button openButton;
    @FXML
    private Tooltip openTooltip;
    @FXML
    private Tooltip refreshTooltip;
    @FXML
    private ToggleButton originFilter;
    @FXML
    private ToggleButton processedFilter;
    private TextField[] inputs;
    private FilePane pane = new FilePane();
    private Counter counter;

    @FXML
    protected void onFilterButtonClick(ActionEvent e) {
        ToggleButton button = (ToggleButton) e.getTarget();
        if (button.equals(originFilter)) {
            loadList(MainApplication.originFileList, originFileList, originFilter);
        } else if (button.equals(processedFilter)) {
            loadList(MainApplication.processedFileList, processedFileList, processedFilter);
        }

    }

    private void loadList(ObservableList<CheckedFile> list, ListView<FilePane> listView, ToggleButton filter) {
        listView.getItems().clear();
        if (filter.isSelected()) {
            for (CheckedFile file : list) {
                if (file.isMustDelete()) {
                    FilePane filePane = new FilePane(file, listView, filter, counter);
                    listView.getItems().add(filePane);
                }
            }
        } else {
            for (CheckedFile file : list) {
                FilePane filePane = new FilePane(file, listView, filter, counter);
                listView.getItems().add(filePane);
            }
        }
    }

    @FXML
    protected void onEnter() {
        boolean mustOpen = true;
        for (int i = 0; i < inputs.length; i++) {
            if (inputs[i % inputs.length].getText().equals("")) {
                inputs[i % inputs.length].requestFocus();
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
            ExtensionValidator.validate(originExtension.getText(), processedExtension.getText());
            String path = pathInput.getText();
            File dir = new File(path);
            if (!dir.isDirectory()) {
                throw new TestException("Неправильный путь,введите путь заного");
            }
            CheckedFile.setMainPath(path);
            DirectoryReader.read(dir.getAbsolutePath(), originExtension.getText(), processedExtension.getText(),
                    MainApplication.processedFileList, MainApplication.originFileList, true);
            loadList(MainApplication.processedFileList, processedFileList, processedFilter);
            loadList(MainApplication.originFileList, originFileList, originFilter);
            openButton.getStyleClass().remove("open-button-start");
            openButton.getStyleClass().add("open-button-refresh");
        } catch (TestException e) {
            new ErrorAlert(e.getMessage());
        }
    }

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
        inputs = new TextField[]{pathInput, originExtension, processedExtension};
        Tooltip[] tooltips = new Tooltip[]
                {deleteTooltip, cleanTooltip, clearTooltip, copyTooltip, openTooltip, refreshTooltip,originFilterTooltip,processedFilterTooltip};
        openButton.getStyleClass().add("open-button-start");
        for (Tooltip tooltip :
                tooltips) {
            tooltip.setShowDelay(new Duration(100));
        }
        counter = new Counter(deleteButton);
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

    public javafx.event.EventHandler<WindowEvent> getCloseEventHandler() {
        return closeEventHandler;
    }

}