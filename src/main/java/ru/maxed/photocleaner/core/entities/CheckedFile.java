package ru.maxed.photocleaner.core.entities;

import java.io.File;
import java.util.Objects;

/**
 * Класс для хранения файла в особом виде для быстрого
 * доступа к различным состояниям с возможностью
 * выделения файла.
 */
public class CheckedFile extends File implements Comparable<File> {
    /**
     * Пусть к директории для работы с файлами.
     */
    private static String mainPath;
    /**
     * Имя без расширения.
     */
    private final String shortName;
    /**
     * Отметка о удалении.
     */
    private boolean mustDelete;
    /**
     * Хендлер смены выделения.
     */
    private transient OnCheckedHandle checkedHandler = null;
    /**
     * Хендлер удаления файла.
     */
    private transient OnDeleteHandler deleteHandler = null;

    /**
     * Конструктор.
     *
     * @param pathname Путь к файлу
     */
    public CheckedFile(final String pathname) {
        super(pathname);
        this.shortName =
                this.getName().substring(0, this.getName().lastIndexOf("."));
        this.mustDelete = false;
    }

    /**
     * Конструктор с отметкой о удалении.
     *
     * @param pathname Путь к файлу
     * @param delete   Отметка о удалении
     */
    public CheckedFile(final String pathname, final boolean delete) {
        super(pathname);
        this.shortName =
                this.getName().substring(0, this.getName().lastIndexOf("."));
        this.mustDelete = delete;
    }

    /**
     * Сеттер основного пути.
     *
     * @param mainPathValue Основной путь рабоччей директории
     */
    public static void setMainPath(final String mainPathValue) {
        CheckedFile.mainPath = mainPathValue;
    }

    /**
     * Сеттер обработчика смены выделения.
     *
     * @param checkedHandlerValue Обработчик смены выделения
     */
    public void setCheckedHandler(final OnCheckedHandle checkedHandlerValue) {
        this.checkedHandler = checkedHandlerValue;
    }

    /**
     * Получение пути относитеьно основного.
     *
     * @return Относительный путь
     */
    public String getPathFromStartDir() {
        return this.getAbsolutePath().replace(mainPath + File.separator, "");
    }

    /**
     * Получение короткиго имени файла.
     *
     * @return Имя файла без расширения
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * Информация о выделении файла.
     *
     * @return Файл помечен к удалению?
     */
    public boolean isMustDelete() {
        return mustDelete;
    }

    /**
     * Сеттер метки удаления.
     *
     * @param mustDeleteValue Выделить файл?
     */
    public void setMustDelete(final boolean mustDeleteValue) {
        this.mustDelete = mustDeleteValue;
        if (checkedHandler != null) {
            checkedHandler.set(mustDeleteValue);
        }
    }

    /**
     * Перегруженная функция удаления с обработчиком.
     *
     * @return Получилось ли удалить?
     * @see File#delete()
     */
    @Override
    public boolean delete() {
        if (deleteHandler != null) {
            deleteHandler.delete();
        }
        return super.delete();
    }

    /**
     * Сеттер обработчика удаления.
     *
     * @param deleteHandlerInit Обработчик удаления
     */
    public void setDeleteHandler(final OnDeleteHandler deleteHandlerInit) {
        this.deleteHandler = deleteHandlerInit;
    }

    /**
     * Приведение к строке.
     *
     * @return Строковое представление
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "CheckedFile{"
                + "shortName='"
                + shortName
                + '\''
                + ", mustDelete="
                + mustDelete + '}';
    }

    /**
     * Переопределение метода сравниения.
     *
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        CheckedFile that = (CheckedFile) o;
        return mustDelete == that.mustDelete;
    }

    /**
     * Переопределение метода получения хешкода.
     *
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mustDelete);
    }

    /**
     * Функция для сравнения.
     *
     * @param file файл
     * @return -1 - меньше 0-равны 1- больше
     */
    @Override
    public int compareTo(final File file) {
        return this.getName().compareTo(file.getName());
    }

    /**
     * Интерфейс класса для обработки удаления.
     */
    public interface OnDeleteHandler {
        /**
         * Функция удаления.
         */
        void delete();
    }

    /**
     * Интерфейс класса для обработки изменения выделения.
     */
    public interface OnCheckedHandle {
        /**
         * Функция изменения флага.
         *
         * @param value Значение флага.
         */
        void set(boolean value);
    }
}
