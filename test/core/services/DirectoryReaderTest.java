package core.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.maxed.photocleaner.core.entities.CheckedFile;
import ru.maxed.photocleaner.core.services.DirectoryReader;

import java.io.File;
import java.io.IOException;

class DirectoryReaderTest {
    private static String testPath = "TestingDir";
    static ObservableList<String> files = FXCollections.observableArrayList();
    static int originFileCount = 0;
    static int processedFilesCount = 0;
    static  int filesCount =0;
    static String absolutePath;
    @BeforeAll
    static void init() {
        File dir = new File(testPath);
        dir.mkdir();
        absolutePath=dir.getAbsolutePath();
        for (int i = 0; i < 100; i++) {
            File file = new File(testPath+"/Test"+i+".png");
            try {
                file.createNewFile();
                originFileCount++;
                filesCount++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 100; i+=2) {
            File file = new File(testPath+"/Test"+i+".bMp");
            try {
                file.createNewFile();
                filesCount++;
                processedFilesCount++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 11; i+=2) {
            File file = new File(testPath+"/Test"+i+".dox.bMp");
            try {
                file.createNewFile();
                filesCount++;
                processedFilesCount++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File secondaryDir = new File(testPath+"/Something");
        secondaryDir.mkdir();
        for (int i = 100; i < 200; i+=4) {
            File file = new File(testPath+"/Something/Test"+i+".bmp");
            try {
                file.createNewFile();
                filesCount++;
                processedFilesCount++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 100; i < 200; i+=4) {
            File file = new File(testPath+"/Something/Test"+i+".dox");
            try {
                file.createNewFile();
                filesCount++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 100; i < 200; i+=3) {
            File file = new File(testPath+"/Something/Test"+i+".bmp.dox");
            try {
                file.createNewFile();
                filesCount++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void read() {
        ObservableList<CheckedFile> originList =FXCollections.observableArrayList();
        ObservableList<CheckedFile> processedList =FXCollections.observableArrayList();
        String processedExtension = ".bmp";
        String originExtension = ".png";
        DirectoryReader.read(testPath,originExtension,processedExtension,processedList,originList);
        Assertions.assertEquals(processedFilesCount,processedList.size());
        Assertions.assertEquals(originFileCount,originList.size());
    }

    @Test
    void readWithoutDots() {
        ObservableList<CheckedFile> originList =FXCollections.observableArrayList();
        ObservableList<CheckedFile> processedList =FXCollections.observableArrayList();
        String processedExtension = "bmp";
        String originExtension = "png";
        DirectoryReader.read(testPath,originExtension,processedExtension,processedList,originList);
        Assertions.assertEquals(processedFilesCount,processedList.size());
        Assertions.assertEquals(originFileCount,originList.size());
    }
    @Test
    void readLowerUpper() {
        ObservableList<CheckedFile> originList =FXCollections.observableArrayList();
        ObservableList<CheckedFile> processedList =FXCollections.observableArrayList();
        String processedExtension = ".BmP";
        String originExtension = "pnG";
        DirectoryReader.read(testPath,originExtension,processedExtension,processedList,originList);
        Assertions.assertEquals(processedFilesCount,processedList.size());
        Assertions.assertEquals(originFileCount,originList.size());
    }

    @Test
    void readWrongExtension() {
        ObservableList<CheckedFile> originList =FXCollections.observableArrayList();
        ObservableList<CheckedFile> processedList =FXCollections.observableArrayList();
        String processedExtension = ".yui";
        String originExtension = ".";
        DirectoryReader.read(testPath,originExtension,processedExtension,processedList,originList);
        Assertions.assertEquals(0, processedList.size());
        Assertions.assertEquals(0, originList.size());
    }
}