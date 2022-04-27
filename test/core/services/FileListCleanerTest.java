package core.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.maxed.photocleaner.core.entities.CheckedFile;
import ru.maxed.photocleaner.core.exeptions.TestExeption;
import ru.maxed.photocleaner.core.services.DirectoryReader;
import ru.maxed.photocleaner.core.services.FileListCleaner;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileListCleanerTest {
    ObservableList<CheckedFile> originFileList= FXCollections.observableArrayList();
    ObservableList<CheckedFile> processedFileList= FXCollections.observableArrayList();
    ObservableList<CheckedFile> expectedFileList= FXCollections.observableArrayList();
    @Test
    void clean() {
        File dir = new File("TestingDir");
        dir.mkdir();
        for (int i = 0; i < 30; i++) {
            CheckedFile file = new CheckedFile("TestingDir/Test" + i + ".bmp", i % 2 == 0);
            processedFileList.add(file);
            if (!(i % 2 == 0)) expectedFileList.add(file);
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileListCleaner.clean(processedFileList);
        } catch (TestExeption e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(expectedFileList, processedFileList);
        processedFileList.clear();
        try {
            DirectoryReader.read("TestingDir",".png",".bmp",processedFileList,originFileList);
        } catch (TestExeption e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(expectedFileList.size(),processedFileList.size());
        Directory.recusiveDelete(dir);
    }
}