package ru.maxed.photocleaner.core.services;

import javafx.collections.ObservableList;
import ru.maxed.photocleaner.core.entities.CheckedFile;

public class MarkCleaner {
    public static void clean(ObservableList<CheckedFile> fileList){
        for (CheckedFile file :
                fileList) {
            file.setMustDelete(false);
        }
    }
}
