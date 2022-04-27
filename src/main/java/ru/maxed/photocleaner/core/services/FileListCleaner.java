package ru.maxed.photocleaner.core.services;

import javafx.collections.ObservableList;
import ru.maxed.photocleaner.core.entities.CheckedFile;
import ru.maxed.photocleaner.core.exeptions.TestException;

import java.util.Iterator;
/*
Удаление всех помеченных файлов
 */
public class FileListCleaner {
    private FileListCleaner(){
        throw new IllegalStateException("Utility class");
    }
    public static void clean(ObservableList<CheckedFile> fileList) throws TestException {
        Iterator<CheckedFile> iterator = fileList.iterator();
        while (iterator.hasNext()) {
            final CheckedFile file = iterator.next();
            if (file.isMustDelete()){
                 iterator.remove();
                if (!file.delete()) throw  new TestException("Не удалос удалить "+file.getName());
            }
        }
    }
}
