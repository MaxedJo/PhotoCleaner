package core.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.maxed.photocleaner.core.entities.CheckedFile;
import ru.maxed.photocleaner.core.services.CopyOfOriginSelector;
import ru.maxed.photocleaner.core.services.FileListComparator;

import static org.junit.jupiter.api.Assertions.*;

class FileListComparatorTest {
    ObservableList<CheckedFile> originFileList= FXCollections.observableArrayList();
    ObservableList<CheckedFile> processedFileList= FXCollections.observableArrayList();
    ObservableList<CheckedFile> expectedFileList= FXCollections.observableArrayList();
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
        expectedFileList.add(new CheckedFile("File3.bmp",true));
        expectedFileList.add(new CheckedFile("File4.bmp"));
        FileListComparator.compareLists(processedFileList,originFileList);
        Assertions.assertEquals(expectedFileList,processedFileList);
    }
}