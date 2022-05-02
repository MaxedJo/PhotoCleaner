package ru.maxed.photocleaner.core.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.maxed.photocleaner.core.entities.CheckedFile;

class FileListComparatorTest {
    final ObservableList<CheckedFile> originFileList = FXCollections.observableArrayList();
    final ObservableList<CheckedFile> processedFileList = FXCollections.observableArrayList();
    final ObservableList<CheckedFile> expectedFileList = FXCollections.observableArrayList();

    @Test
    void compareLists() {
        originFileList.add(new CheckedFile("File1.png"));
        originFileList.add(new CheckedFile("File2.png"));
        originFileList.add(new CheckedFile("File5.PNg"));
        originFileList.add(new CheckedFile("File4.PNg"));
        processedFileList.add(new CheckedFile("File1.Bmp"));
        processedFileList.add(new CheckedFile("File2.Bmp"));
        processedFileList.add(new CheckedFile("File3.bmp"));
        processedFileList.add(new CheckedFile("File4.bmp"));
        expectedFileList.add(new CheckedFile("File1.Bmp"));
        expectedFileList.add(new CheckedFile("File2.Bmp"));
        expectedFileList.add(new CheckedFile("File3.bmp", true));
        expectedFileList.add(new CheckedFile("File4.bmp"));
        FileListComparator.compareLists(processedFileList, originFileList);
        Assertions.assertEquals(expectedFileList, processedFileList);
    }
}