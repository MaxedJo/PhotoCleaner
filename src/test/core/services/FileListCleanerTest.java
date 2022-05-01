package core.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.maxed.photocleaner.core.entities.CheckedFile;
import ru.maxed.photocleaner.core.exeptions.TestException;
import ru.maxed.photocleaner.core.services.DirectoryReader;
import ru.maxed.photocleaner.core.services.FileListCleaner;

import java.io.File;
import java.io.IOException;

class FileListCleanerTest {
    final ObservableList<CheckedFile> originFileList = FXCollections.observableArrayList();
    final ObservableList<CheckedFile> processedFileList = FXCollections.observableArrayList();
    final ObservableList<CheckedFile> expectedFileList = FXCollections.observableArrayList();

    @Test
    void clean() {
        File dir = new File("TestingDir");
        if (!dir.mkdir()) {
            System.err.println("Ошибка создания");
        }
        for (int i = 0; i < 30; i++) {
            CheckedFile file = new CheckedFile("TestingDir/Test" + i + ".bmp", i % 2 == 0);
            processedFileList.add(file);
            if (!(i % 2 == 0)) expectedFileList.add(file);
            try {
                if (!file.createNewFile()) {
                    System.err.println("Ошибка создания");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileListCleaner.clean(processedFileList);
        } catch (TestException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(expectedFileList, processedFileList);
        processedFileList.clear();
        try {
            DirectoryReader.read("TestingDir", ".png", ".bmp", processedFileList, originFileList, true);
        } catch (TestException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(expectedFileList.size(), processedFileList.size());
        Directory.recursiveDelete(dir);
    }
}