package ru.maxed.photocleaner.core.services;

import javafx.collections.ObservableList;
import ru.maxed.photocleaner.core.entities.CheckedFile;

/**
 * Утилитный клас для очистки выделения в списке.
 */
public final class MarkCleaner {
    /**
     * Закрывающий конструктор.
     */
    private MarkCleaner() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Очистка выделений в спске.
     *
     * @param fileList Список файлов
     */
    public static void clean(final ObservableList<CheckedFile> fileList) {
        for (CheckedFile file : fileList) {
            file.setMustDelete(false);
        }
    }
}
