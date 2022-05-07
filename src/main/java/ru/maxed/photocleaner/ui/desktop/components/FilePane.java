package ru.maxed.photocleaner.ui.desktop.components;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import ru.maxed.photocleaner.core.entities.CheckedFile;

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
    private CounterChanger deleteCounter;
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
            final CounterChanger counter
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
            counter.change(false);
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
            deleteCounter.change(checked);
        }
        this.check.setSelected(checked);
        removeFiltered();

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

    public interface CounterChanger {
        void change(boolean b);
    }
}
