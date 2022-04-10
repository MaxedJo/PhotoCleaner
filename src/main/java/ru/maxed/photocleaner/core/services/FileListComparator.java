package ru.maxed.photocleaner.core.services;

import javafx.collections.ObservableList;
import ru.maxed.photocleaner.core.entities.CheckedFile;

public class FileListComparator {
    public static void compareLists(ObservableList<CheckedFile> processedFileList, ObservableList<CheckedFile> originFileList){
        for (CheckedFile processedFile:
                processedFileList) {
            if (processedFile.isMustDelete()) continue;
            processedFile.setMustDelete(true);
            for (CheckedFile originFile:
                 originFileList) {
                if (processedFile.getShortName().equals(originFile.getShortName())){
                    processedFile.setMustDelete(false);
                    break;
                }
            }
        }
    }
}
