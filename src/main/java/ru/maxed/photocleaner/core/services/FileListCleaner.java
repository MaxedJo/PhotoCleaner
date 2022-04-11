package ru.maxed.photocleaner.core.services;

import javafx.collections.ObservableList;
import ru.maxed.photocleaner.core.entities.CheckedFile;
import ru.maxed.photocleaner.ui.desktop.ErrorStage;

import java.util.Iterator;

public class FileListCleaner {
    private FileListCleaner(){
        throw new IllegalStateException("Utility class");
    }
    public static void clean(ObservableList<CheckedFile> fileList){
        Iterator<CheckedFile> iterator = fileList.iterator();
        while (iterator.hasNext()) {
            final CheckedFile file = iterator.next();
            if (file.isMustDelete()){
                 iterator.remove();
                if (!file.delete()) new ErrorStage("Не удалос удалить "+file.getName());
            }
        }
    }
}
