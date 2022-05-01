package ru.maxed.photocleaner.core.services;

import javafx.collections.ObservableList;
import ru.maxed.photocleaner.core.entities.CheckedFile;

public final class MarkCleaner {
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
