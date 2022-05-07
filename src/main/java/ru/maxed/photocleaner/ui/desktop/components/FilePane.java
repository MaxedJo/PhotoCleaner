package ru.maxed.photocleaner.ui.desktop.components;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import ru.maxed.photocleaner.core.entities.CheckedFile;
import ru.maxed.photocleaner.ui.desktop.services.CounterShare;

/**
 * Компонент файлового элемента списка.
 */
public class FilePane extends BorderPane {
    /**
     * Текстовое поле элемента.
     */
    private final Label text = new Label();
    /**
     * Чекбокс элемента.
     */
    private final CheckBox check = new CheckBox();
    /**
     * Кнопка переключения фильтра контролирующая родительский список.
     */
    private ToggleButton filter;
    /**
     * Родительский список.
     */
    private ListView<FilePane> list;
    /**
     * Счётчик количества выделенных файлов.
     */
    private CounterShare deleteCounter;
    /**
     * Файл привязанный к данному компоненту.
     */
    private CheckedFile checkedFile;

    /**
     * Конструктор файлового экомпонентся сниска.
     *
     * @param file         Привязанный файл
     * @param parentList   Родительский список
     * @param buttonFilter Фильтр родительского списка
     * @param counter      Счётчик выделенных элементов
     */
    public FilePane(
            final CheckedFile file,
            final ListView<FilePane> parentList,
            final ToggleButton buttonFilter,
            final CounterShare counter
    ) {
        super();
        checkedFile = file;
        this.filter = buttonFilter;
        this.setLeft(text);
        this.list = parentList;
        this.deleteCounter = counter;
        text.setText(file.getPathFromStartDir());
        check.setSelected(file.isMustDelete());
        this.check.setOnAction(event -> file.setMustDelete(check.isSelected()));
        file.setDeleteHandler(() -> {
            parentList.getItems().remove(this);
            counter.update(false);
        });
        file.setCheckedHandler(this::setSelected);
        this.setRight(check);
    }

    /**
     * Пустой конструктор.
     */
    public FilePane() {
        super();
        this.setLeft(text);
        this.setRight(check);
        check.setOnAction(e -> changeCheck());
    }

    /**
     * Сеттер состояния выделения.
     *
     * @param checked Состояние выделения.
     */
    public void setSelected(final boolean checked) {
        if (check.isSelected() != checked || checkedFile.isMustDelete() != checked) {
            changeCounter(checked);
        }
        this.check.setSelected(checked);
        removeFiltered();

    }

    /**
     * Вызов функции изменения счётчика.
     *
     * @param rise Растёт ли счётчик.
     */
    private void changeCounter(final boolean rise) {
        if (rise) {
            deleteCounter.update(true);
        } else {
            deleteCounter.update(false);
        }
    }

    /**
     * Удаление элемента списка если он отфильтрован.
     */
    private void removeFiltered() {
        if (filter.isSelected() && !check.isSelected()) {
            list.getItems().remove(this);
        }
    }

    /**
     * Смена состояния выделения на противоположное.
     */
    public void changeCheck() {
        this.check.setSelected(!this.check.isSelected());
        checkedFile.setMustDelete(check.isSelected());
        removeFiltered();
    }

    /**
     * Генерация объекта в виде строки.
     *
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "{"
                + "text="
                + this.text.getText()
                + ", check="
                + this.check.isSelected()
                + '}';
    }
}
