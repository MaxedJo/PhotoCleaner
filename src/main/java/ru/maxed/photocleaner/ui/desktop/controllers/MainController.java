package ru.maxed.photocleaner.ui.desktop.controllers;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.WindowEvent;
import ru.maxed.photocleaner.ListKey;
import ru.maxed.photocleaner.MainApplication;
import ru.maxed.photocleaner.core.entities.CheckedFile;
import ru.maxed.photocleaner.core.exeptions.TestException;
import ru.maxed.photocleaner.core.services.CopyOfOriginSelector;
import ru.maxed.photocleaner.core.services.DirectoryReader;
import ru.maxed.photocleaner.core.services.ExtensionValidator;
import ru.maxed.photocleaner.core.services.FileListCleaner;
import ru.maxed.photocleaner.core.services.FileListComparator;
import ru.maxed.photocleaner.core.services.MarkCleaner;
import ru.maxed.photocleaner.core.utility.Settings;
import ru.maxed.photocleaner.ui.desktop.components.ConfirmationAlert;
import ru.maxed.photocleaner.ui.desktop.components.ErrorAlert;
import ru.maxed.photocleaner.ui.desktop.components.FilePane;
import ru.maxed.photocleaner.ui.desktop.services.Counter;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Контроллер главного окна приложения.
 */
public class MainController implements Initializable {
    /**
     * Список файлов для обработки.
     */
    private final ObservableList<CheckedFile> mainProcessedFileList =
            MainApplication.getProcessedFileList();
    /**
     * Список оригинальных файлов.
     */
    private final ObservableList<CheckedFile> mainOriginFileList =
            MainApplication.getOriginFileList();
    int test = 0;
    /**
     * Список файлов для обработки.
     */
    @FXML
    private ListView<FilePane> processedFileList;
    /**
     * Список оригинальных файлов.
     */
    @FXML
    private ListView<FilePane> originFileList;
    /**
     * Кнопка "Обзор".
     */
    @FXML
    private Button pathChooser;
    /**
     * Кнопка "Удаление".
     */
    @FXML
    private Button deleteButton;
    /**
     * Кнопка "Открыть".
     */
    @FXML
    private Button openButton;
    /**
     * Поле ввода пути.
     */
    @FXML
    private TextField pathInput;
    /**
     * Поле ввода оргинального расширения.
     */
    @FXML
    private TextField originExtension;
    /**
     * Поле ввода разширеня файлов для обработки.
     */
    @FXML
    private TextField processedExtension;
    /**
     * Эвент сохранения настроек при закрытиии окна.
     */
    private final javafx.event.EventHandler<WindowEvent> closeEventHandler =
            event -> {
                Settings.update(Settings.PATH, pathInput.getText());
                Settings.update(Settings.ORIGIN_EXTENSION,
                        originExtension.getText());
                Settings.update(Settings.PROCESSED_EXTENSION,
                        processedExtension.getText());
            };
    /**
     * Кнопка переключения фильтра файлов оригиналов.
     */
    @FXML
    private ToggleButton originFilter;
    /**
     * Кнопка переключения фильтра файлов для обработки.
     */
    @FXML
    private ToggleButton processedFilter;
    /**
     * Масиив полей ввода.
     */
    private TextField[] inputs;
    /**
     * Объект строки списка для обработки нажатия.
     */
    private FilePane pane = new FilePane();
    /**
     * Счётчик выделенных файлов.
     */
    private Counter mainCounter;
    @FXML
    private Label processedListCounter;
    @FXML
    private Label originListCounter;

    /**
     * Обработчик нажатия накнопку фильтра.
     *
     * @param e Эвент действия
     */
    @FXML
    protected void onFilterButtonClick(final ActionEvent e) {
        ToggleButton button = (ToggleButton) e.getTarget();
        if (button.equals(originFilter)) {
            loadList(
                    mainOriginFileList,
                    originFileList,
                    originFilter,
                    ListKey.ORIGIN
            );
        } else if (button.equals(processedFilter)) {
            loadList(
                    mainProcessedFileList,
                    processedFileList,
                    processedFilter,
                    ListKey.PROCESSED
            );
        }
    }

    /**
     * Загрузка списка файлов в ListView.
     *
     * @param list     загружаемый список
     * @param listView список назначение
     * @param filter   Кнопка фильтра
     */
    private void loadList(
            final ObservableList<CheckedFile> list,
            final ListView<FilePane> listView,
            final ToggleButton filter,
            final ListKey key
    ) {
        listView.getItems().clear();
        if (filter.isSelected()) {
            for (CheckedFile file : list) {
                if (file.isMustDelete()) {
                    FilePane filePane =
                            new FilePane(file, listView, filter, b -> mainCounter.change(b, key));
                    listView.getItems().add(filePane);
                }
            }
        } else {
            for (CheckedFile file : list) {
                FilePane filePane =
                        new FilePane(file, listView, filter, b -> mainCounter.change(b, key));
                listView.getItems().add(filePane);
            }
        }
    }

    /**
     * Переключение между полями ввода ("Enter").
     */
    @FXML
    protected void onEnter() {
        boolean mustOpen = true;
        for (int i = 0; i < inputs.length; i++) {
            if (inputs[i % inputs.length].getText().equals("")) {
                inputs[i % inputs.length].requestFocus();
                mustOpen = false;
            }
        }
        if (mustOpen) {
            onOpenButtonClick();
        }
    }

    /**
     * Функция загрузки списков файлов.
     */
    @FXML
    protected void onOpenButtonClick() {
        try {
            mainOriginFileList.clear();
            mainProcessedFileList.clear();
            ExtensionValidator.validate(
                    originExtension.getText(), processedExtension.getText()
            );
            String path = pathInput.getText();
            File dir = new File(path);
            if (!dir.isDirectory()) {
                throw new TestException(
                        "Неправильный путь,введите путь заного"
                );
            }
            CheckedFile.setMainPath(dir.getAbsolutePath());
            DirectoryReader.read(
                    dir.getAbsolutePath(),
                    originExtension.getText(),
                    processedExtension.getText(),
                    mainProcessedFileList,
                    mainOriginFileList,
                    true
            );
            mainOriginFileList.setAll(
                    mainOriginFileList.stream().sorted().toList()
            );
            mainProcessedFileList.setAll(
                    mainProcessedFileList.stream().sorted().toList()
            );
            loadList(
                    mainProcessedFileList,
                    processedFileList,
                    processedFilter,
                    ListKey.PROCESSED
            );
            loadList(
                    mainOriginFileList,
                    originFileList,
                    originFilter,
                    ListKey.ORIGIN
            );
        } catch (TestException e) {
            (new ErrorAlert(e.getMessage())).showAndWait();
        }
    }

    /**
     * Обработка нажатия по элементу списка.
     *
     * @param arg0 Эвент нажатия
     */
    @FXML
    protected void listClickHandle(final MouseEvent arg0) {
        if (arg0.getTarget().getClass() == pane.getClass()) {
            pane = (FilePane) arg0.getTarget();
            pane.changeCheck();
        }
    }

    /**
     * Обработка нажати кнопки выделения файлов без оригиналов.
     */
    @FXML
    protected void onMarkFilesWithoutOriginButtonClick() {
        FileListComparator.compareLists(
                mainProcessedFileList,
                mainOriginFileList
        );
    }

    /**
     * Обработка нажатия кнопки выбора файлов аналогичных выбраным оригиналам.
     */
    @FXML
    protected void onCopySelectButtonClick() {
        CopyOfOriginSelector.selectSame(
                mainProcessedFileList,
                mainOriginFileList
        );
    }

    /**
     * Обработка нажатия кнопки снятия выделения.
     */
    @FXML
    protected void onClearSelectionButtonClick() {
        MarkCleaner.clean(mainOriginFileList);
        MarkCleaner.clean(mainProcessedFileList);
    }

    /**
     * Обработка нажатия кнопки выбора директории.
     */
    @FXML
    protected void onPathChooserClick() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file =
                directoryChooser.showDialog(pathChooser.getScene().getWindow());
        if (file != null) {
            pathInput.setText(file.getAbsolutePath());
            onEnter();
        }
    }

    /**
     * Обработка нажатия на кнопку удаления.
     */
    @FXML
    protected void onDeleteButtonClick() {
        ConfirmationAlert alert = new ConfirmationAlert();
        Optional<ButtonType> option = alert.showAndWait();
        if (option.isPresent() && option.get() == ButtonType.OK) {
            try {
                FileListCleaner.clean(mainProcessedFileList);
                FileListCleaner.clean(mainOriginFileList);
            } catch (TestException e) {
                (new ErrorAlert(e.getMessage())).showAndWait();
            }
        }

    }

    /**
     * Функция инициализации стартовых значений.
     *
     * @param location  The location used to resolve relative paths
     *                  for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object,
     *                  or {@code null} if
     *                  the root object was not localized.
     * @see Initializable#initialize(URL, ResourceBundle)
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        inputs = new TextField[]{
                pathInput,
                originExtension,
                processedExtension
        };
        mainCounter = new Counter(deleteButton, originListCounter, processedListCounter);
        addListeners(originFileList, ListKey.ORIGIN);
        addListeners(processedFileList, ListKey.PROCESSED);
        pathInput.setPromptText("Введите путь");
        originExtension.setPromptText("Эталонное расширение");
        processedExtension.setPromptText("Расширение для обработки");
        pathInput.setText(Settings.get(Settings.PATH));
        originExtension.setText(Settings.get(Settings.ORIGIN_EXTENSION));
        processedExtension.setText(Settings.get(Settings.PROCESSED_EXTENSION));
    }

    private void addListeners(final ListView<FilePane> list, ListKey key) {
        list.getItems().addListener((ListChangeListener.Change<? extends FilePane> change) -> {
            while (change.next()) {
                for (int i = 0; i < change.getRemovedSize(); i++) {
                    mainCounter.changeMax(false, key);
                }
                for (int i = 0; i < change.getAddedSize(); i++) {
                    mainCounter.changeMax(true, key);
                }
            }
        });
    }

    /**
     * Функция возврающая хендлер закрытия приложения.
     *
     * @return хендлер закрытия
     */
    public javafx.event.EventHandler<WindowEvent> getCloseEventHandler() {
        return closeEventHandler;
    }

}
