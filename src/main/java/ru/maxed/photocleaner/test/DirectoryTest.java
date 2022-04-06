package ru.maxed.photocleaner.test;

import org.junit.jupiter.api.Assertions;
import ru.maxed.photocleaner.Directory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;



class DirectoryTest {
    private String testPath = "TestingDir";
    ObservableList<String> files = FXCollections.observableArrayList();
    int fileCount = 0;
    int secondaryFilesCount = 0;
    String absolutePath;
    @BeforeEach
    void setUp() {
        File dir = new File(testPath);
        dir.mkdir();
        absolutePath=dir.getAbsolutePath();
        for (int i = 0; i < 100; i++) {
            File file = new File(testPath+"/Test"+i+".png");
            try {
                file.createNewFile();
                fileCount++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 100; i+=2) {
            File file = new File(testPath+"/Test"+i+".bmp");
            try {
                file.createNewFile();
                fileCount++;
                secondaryFilesCount++;
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
                fileCount++;
                secondaryFilesCount++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void getFileListTest() {
        Directory newDir = new Directory(testPath);
        files = newDir.getFileList(absolutePath);
        Assertions.assertEquals(fileCount,files.size());
    }
    @Test
    void getFilteredFileListTest() {
        Directory newDir = new Directory(testPath);
        files = newDir.getFileList(absolutePath ,"bmp");
        for (String str:files ) {
            System.out.println(str);
        }
        Assertions.assertEquals(secondaryFilesCount,files.size());
    }
}