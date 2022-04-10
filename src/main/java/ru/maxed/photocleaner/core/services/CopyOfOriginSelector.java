package ru.maxed.photocleaner.core.services;

import javafx.collections.ObservableList;
import ru.maxed.photocleaner.core.entities.CheckedFile;

public class CopyOfOriginSelector {
    public static void selectSame(ObservableList<CheckedFile> processedFileList, ObservableList<CheckedFile> originFileList){
        for (CheckedFile originFile:
             originFileList) {
            if (originFile.isMustDelete()){
                for (CheckedFile processedFile :
                        processedFileList) {
                    if (originFile.getShortName().equals(processedFile.getShortName()))
                        processedFile.setMustDelete(true);
                }
            }
        }
    }
}
