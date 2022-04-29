package ru.maxed.photocleaner.core.services;

import javafx.collections.ObservableList;
import ru.maxed.photocleaner.core.entities.CheckedFile;

/*
Очистка выделения в списке
 */
public class MarkCleaner {
    private MarkCleaner() {
        throw new IllegalStateException("Utility class");
    }

    public static void clean(ObservableList<CheckedFile> fileList) {
        for (CheckedFile file :
                fileList) {
            file.setMustDelete(false);
        }
    }
}
