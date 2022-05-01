package ru.maxed.photocleaner.ui.desktop.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
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
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Контроллер главного окна приложения.
 */
public class MainController implements Initializable {
    /**
     * Задержка появления тултипа.
     */
    private static final int TOOLTIP_WAIT_DURATION = 100;
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
            event -> Settings.saveSettings(
                    pathInput.getText(),
                    originExtension.getText(),
                    processedExtension.getText()
            );
    /**
     * Тултип кнопки удаления.
     */
    @FXML
    private Tooltip deleteTooltip;
    /**
     * Тултип кнопки выделения файлов без оригинала.
     */
    @FXML
    private Tooltip clearTooltip;
    /**
     * Тултип кнопки выделения файлов для обработки
     * аналогичных выделенным оригинальным файлам.
     */
    @FXML
    private Tooltip copyTooltip;
    /**
     * Тултип кнопки очистки выделения.
     */
    @FXML
    private Tooltip cleanTooltip;
    /**
     * Тултип кнопки фильтрации списка оригинальных файлов.
     */
    @FXML
    private Tooltip originFilterTooltip;
    /**
     * Тултип кнопки фильтрации списка файлов для обработки.
     */
    @FXML
    private Tooltip processedFilterTooltip;
    /**
     * Тултип кнопки считывания директории.
     */
    @FXML
    private Tooltip openTooltip;
    /**
     * Тултип кнопки обновления.
     */
    @FXML
    private Tooltip refreshTooltip;
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
    private Counter counter;

    /**
     * Л
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
                    originFilter
            );
        } else if (button.equals(processedFilter)) {
            loadList(
                    mainProcessedFileList,
                    processedFileList,
                    processedFilter
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
            final ToggleButton filter
    ) {
        listView.getItems().clear();
        if (filter.isSelected()) {
            for (CheckedFile file : list) {
                if (file.isMustDelete()) {
                    FilePane filePane =
                            new FilePane(file, listView, filter, counter);
                    listView.getItems().add(filePane);
                }
            }
        } else {
            for (CheckedFile file : list) {
                FilePane filePane =
                        new FilePane(file, listView, filter, counter);
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
            CheckedFile.setMainPath(path);
            DirectoryReader.read(
                    dir.getAbsolutePath(),
                    originExtension.getText(),
                    processedExtension.getText(),
                    mainProcessedFileList,
                    mainOriginFileList,
                    true
            );
            loadList(
                    mainProcessedFileList,
                    processedFileList,
                    processedFilter
            );
            loadList(
                    mainOriginFileList,
                    originFileList,
                    originFilter
            );
            openButton.getStyleClass().remove("open-button-start");
            openButton.getStyleClass().add("open-button-refresh");
        } catch (TestException e) {
            new ErrorAlert(e.getMessage());
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
                new ErrorAlert(e.getMessage());
            }
        }

    }

    /**
     * Функция инициализации стартовых значений.
     *
     * @see Initializable#initialize(URL, ResourceBundle)
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        inputs = new TextField[]{
                pathInput,
                originExtension,
                processedExtension
        };
        Tooltip[] tooltips = new Tooltip[]{
                deleteTooltip,
                cleanTooltip,
                clearTooltip,
                copyTooltip,
                openTooltip,
                refreshTooltip,
                originFilterTooltip,
                processedFilterTooltip
        };
        openButton.getStyleClass().add("open-button-start");
        for (Tooltip tooltip
                : tooltips) {
            tooltip.setShowDelay(new Duration(TOOLTIP_WAIT_DURATION));
        }
        counter = new Counter(deleteButton);
        pathInput.setPromptText("Введите путь");
        originExtension.setPromptText("Эталонное расширение");
        processedExtension.setPromptText("Расширение для обработки");
        File settingsFile = new File("settings.properties");
        if (settingsFile.exists()) {
            Properties properties = Settings.loadSettings();
            pathInput.setText(properties.getProperty("path"));
            processedExtension
                    .setText(properties.getProperty("processedExtension"));
            originExtension.setText(properties.getProperty("originExtension"));
        }
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
