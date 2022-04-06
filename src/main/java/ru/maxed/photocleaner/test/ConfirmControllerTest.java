package ru.maxed.photocleaner.test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.maxed.photocleaner.Directory;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ConfirmControllerTest {

    private String testPath = "TestingDir";
    ObservableList<String> files = FXCollections.observableArrayList();
    int fileCount = 0;
    int secondaryFilesCount = 0;
    @BeforeEach
    void setUp() {
        File dir = new File(testPath);
        dir.delete();
        dir.mkdir();
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
    void onConfirmButtonClick() {
        Directory newDir = new Directory(testPath);
        files = newDir.getFileList(newDir.getAbsolutePath(),"bmp");
        for (String str:files ) {
            File file = new File(newDir.getAbsolutePath()+"\\"+ str);
            System.out.println(str);
            file.delete();
        }
        files = newDir.getFileList(newDir.getAbsolutePath(),"bmp");
        Assertions.assertEquals(0,files.size());
        files = newDir.getFileList(newDir.getAbsolutePath());
        Assertions.assertEquals(fileCount-secondaryFilesCount,files.size());
    }

    @Test
    void initialize() {
    }
}