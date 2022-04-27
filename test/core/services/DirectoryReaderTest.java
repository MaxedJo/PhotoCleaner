package core.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;
import ru.maxed.photocleaner.core.entities.CheckedFile;
import ru.maxed.photocleaner.core.exeptions.TestExeption;
import ru.maxed.photocleaner.core.services.DirectoryReader;

import java.io.File;
import java.io.IOException;

class DirectoryReaderTest {
    ObservableList<CheckedFile> originList =FXCollections.observableArrayList();
    ObservableList<CheckedFile> processedList =FXCollections.observableArrayList();
    @BeforeAll
    static void init() {
        Directory.create();
    }
    @BeforeEach
    void setUp(){
        originList =FXCollections.observableArrayList();
        processedList =FXCollections.observableArrayList();
    }

    @Test
    void read() {

        String processedExtension = ".bmp";
        String originExtension = ".png";
        try {
            DirectoryReader.read(Directory.testPath,originExtension,processedExtension,processedList,originList);
        } catch (TestExeption e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(Directory.processedFilesCount,processedList.size());
        Assertions.assertEquals(Directory.originFileCount,originList.size());
    }

    @Test
    void readWithoutDots() {
        String processedExtension = "bmp";
        String originExtension = "png";
        try {
            DirectoryReader.read(Directory.testPath,originExtension,processedExtension,processedList,originList);
        } catch (TestExeption e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(Directory.processedFilesCount,processedList.size());
        Assertions.assertEquals(Directory.originFileCount,originList.size());
    }
    @Test
    void readLowerUpper() {
        String processedExtension = ".BmP";
        String originExtension = "pnG";
        try {
            DirectoryReader.read(Directory.testPath,originExtension,processedExtension,processedList,originList);
        } catch (TestExeption e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(Directory.processedFilesCount,processedList.size());
        Assertions.assertEquals(Directory.originFileCount,originList.size());
    }

    @Test
    void readEmptyLists() {
        String processedExtension = ".yui";
        String originExtension = ".";
        Assertions.assertThrows(TestExeption.class,()->DirectoryReader.read(Directory.testPath,originExtension,processedExtension,processedList,originList));
    }
    @AfterAll
    static void clean(){
        Directory.clean();
    }
}