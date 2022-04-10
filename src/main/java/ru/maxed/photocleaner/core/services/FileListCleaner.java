package ru.maxed.photocleaner.core.services;

import javafx.collections.ObservableList;
import ru.maxed.photocleaner.core.entities.CheckedFile;

import java.util.Iterator;

public class FileListCleaner {
    public static void clean(ObservableList<CheckedFile> fileList){
        Iterator<CheckedFile> iterator = fileList.iterator();
        while (iterator.hasNext()) {
            final CheckedFile file = iterator.next();
            if (file.isMustDelete()){
                 iterator.remove();
                 file.delete();
            }
        }
    }
}
