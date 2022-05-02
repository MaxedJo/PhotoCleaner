package ru.maxed.photocleaner.core.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.maxed.photocleaner.core.entities.CheckedFile;

class CopyOfOriginSelectorTest {
    final ObservableList<CheckedFile> originFileList = FXCollections.observableArrayList();
    final ObservableList<CheckedFile> processedFileList = FXCollections.observableArrayList();
    final ObservableList<CheckedFile> expectedFileList = FXCollections.observableArrayList();


    @Test
    void selectSame() {
        originFileList.add(new CheckedFile("File1.png", false));
        originFileList.add(new CheckedFile("File2.png", true));
        originFileList.add(new CheckedFile("File3.PNg", true));
        originFileList.add(new CheckedFile("File4.PNg", false));
        processedFileList.add(new CheckedFile("File1.Bmp"));
        processedFileList.add(new CheckedFile("File2.Bmp"));
        processedFileList.add(new CheckedFile("File3.bmp"));
        processedFileList.add(new CheckedFile("File4.bmp"));
        expectedFileList.add(new CheckedFile("File1.Bmp", false));
        expectedFileList.add(new CheckedFile("File2.Bmp", true));
        expectedFileList.add(new CheckedFile("File3.bmp", true));
        expectedFileList.add(new CheckedFile("File4.bmp", false));
        CopyOfOriginSelector.selectSame(processedFileList, originFileList);
        Assertions.assertEquals(expectedFileList, processedFileList);
    }
}